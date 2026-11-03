"""Verify independent overlay boundaries and fail-closed crosswalk behavior."""

import copy
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "mm_model", Path(__file__).with_name("build-mastercard-maestro-model.py"))
model = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(model)


class ModelTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        model.gates.trusted_source()
        cls.document = model.gates.load(model.MODEL)
        cls.source = model.gates.SOURCE.read_text(encoding="utf-8-sig")
        cls.old_source = model.OLD_SOURCE.read_text(encoding="utf-8-sig")

    def build(self, document=None):
        return model.build_overlay(document or self.document, self.source, self.old_source)

    def test_source_pinned_chains_and_isolated_mutants(self):
        package, register = self.build()
        self.assertEqual(len(package["businessRequirements"]), 26)
        self.assertEqual(len(register), len(package["testScenarios"]))
        self.assertEqual(len(package["testCases"]), 2 * len(register))
        self.assertEqual(len(package["testData"]), len(package["testCases"]))
        brs, ts, tc = (model.unique(package[key]) for key in
                       ("businessRequirements", "testScenarios", "testCases"))
        for scenario in ts.values():
            self.assertTrue(set(scenario["requirementIds"]) <= brs.keys())
        for case in tc.values():
            self.assertTrue(set(case["scenarioIds"]) <= ts.keys())
        for datum in package["testData"]:
            case = tc[datum["testCaseIds"][0]]
            assertion = case["assertions"][0]
            actual = model.evaluate(assertion["predicate"], datum["payload"])
            self.assertEqual(actual, datum["expectedValidation"] == "PASS")
            self.assertEqual(datum["readiness"], "REVIEW_REQUIRED")
            self.assertEqual(case["expectedOutcome"], datum["expectedValidation"])
        for rule in self.document["rules"]:
            changed = {key for key in rule["valid"].keys() | rule["invalid"].keys()
                       if rule["valid"].get(key) != rule["invalid"].get(key)}
            self.assertEqual(len(changed), 1)
            self.assertTrue(changed <= {rule["predicate"]["field"], rule["predicate"].get("other")})
        self.assertTrue(all(row["approval"] == "REVIEW_REQUIRED" for row in register))

    def test_wrong_page_or_quote_fails(self):
        for key, value in (("page", 1), ("quote", "This is not an authoritative source quote.")):
            document = copy.deepcopy(self.document)
            document["rules"][0][key] = value
            with self.assertRaisesRegex(ValueError, "Source quote/page"):
                self.build(document)

    def test_approval_and_scope_cannot_be_invented(self):
        for key, value in (("status", "APPROVED"), ("baselineChanged", True)):
            document = copy.deepcopy(self.document)
            document[key] = value
            with self.assertRaises(ValueError):
                self.build(document)
        document = copy.deepcopy(self.document)
        document["rules"][0]["contexts"] = ["UNSUPPORTED"]
        with self.assertRaises(ValueError):
            self.build(document)

    def test_bad_fixture_or_duplicate_rule_fails(self):
        document = copy.deepcopy(self.document)
        document["rules"][0]["invalid"] = document["rules"][0]["valid"]
        with self.assertRaisesRegex(ValueError, "Control/mutant"):
            self.build(document)
        document = copy.deepcopy(self.document)
        document["rules"].append(document["rules"][0])
        with self.assertRaisesRegex(ValueError, "Duplicate"):
            self.build(document)

    def test_missing_or_wrong_type_facts_raise(self):
        with self.assertRaises(ValueError):
            model.evaluate({"op": "equals", "field": "x", "expected": False}, {})
        with self.assertRaises(ValueError):
            model.evaluate({"op": "equals", "field": "x", "expected": False}, {"x": 0})
        with self.assertRaises(ValueError):
            model.evaluate({"op": "same", "field": "x", "other": "y"}, {"x": "", "y": ""})
        with self.assertRaises(ValueError):
            model.evaluate({"op": "invented", "field": "x"}, {"x": "value"})

    def test_crosswalk_requires_same_primary_source_and_page(self):
        rule = self.document["rules"][0]
        ai = {"statement": rule["quote"], "source_id": "SRC-ATL105-PDF-001", "page": rule["page"]}
        self.assertIn("CANDIDATE", model.candidate_match(rule, ai))
        self.assertIsNone(model.candidate_match(rule, {**ai, "page": 82}))
        self.assertIsNone(model.candidate_match(rule, {**ai, "source_id": "COMPANION"}))
        self.assertIsNone(model.candidate_match(rule, {**ai, "statement": "Unrelated mandate"}))

    def test_legacy_rows_preserve_every_br_without_promotion(self):
        legacy = {"businessRequirements": [{"id": "B", "status": "OLD"}],
                  "testScenarios": [{"id": "S", "requirementIds": ["B"]}],
                  "testCases": [{"id": "C", "scenarioIds": ["S"]}],
                  "testData": [{"id": "D", "testCaseIds": ["C"], "payload": {"x": 1}}]}
        before = copy.deepcopy(legacy)
        rows = model.legacy_register(legacy)
        self.assertEqual(legacy, before)
        self.assertEqual(len(rows), 1)
        self.assertEqual(rows[0]["linkedPayloadTd"], 1)
        self.assertEqual(rows[0]["denominatorEligibility"], "BLOCKED")
        self.assertEqual(set(rows[0]["contextDecisions"].values()), {"REVIEW_REQUIRED"})

    def test_unresolved_savings_and_ach_do_not_enter_denominator(self):
        _, rows = self.build()
        for identity in ("BR-MM4-022", "BR-MM4-023"):
            row = next(item for item in rows if item["independentBrId"] == identity)
            self.assertEqual(row["applicability"], "REVIEW_REQUIRED")
            self.assertTrue(row["denominatorEligibility"].startswith("BLOCKED"))

    def test_delivery_crosswalk_retains_unmatched_rules_without_confirmation(self):
        rule = self.document["rules"][0]
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            ledger = []
            for package in model.CONTEXT_PACKAGE.values():
                folder = root / package / "requirements_scenarios"
                folder.mkdir(parents=True)
                ai = {"meta": {"spec_version": "2026-4"}, "rules": {
                    "MATCH": {"rule_id": "MATCH", "source_id": "SRC-ATL105-PDF-001",
                              "page": rule["page"], "statement": rule["quote"]},
                    "OTHER": {"rule_id": "OTHER", "source_id": "SRC-ATL105-PDF-001",
                              "page": 1, "statement": "Another obligation"}}}
                (folder / "scenario_supporting_rules.json").write_text(json.dumps(ai), encoding="utf-8")
                ledger.append({"package": package, "ruleId": "MATCH", "scenarioCount": "1",
                               "caseCount": "1", "casesWithAllDeclaredTd": "1",
                               "casesWithExplicitPredicateAndTd": "0"})
            document = {"rules": [rule]}
            rows, retained, _ = model.build_crosswalk(document, root, ledger)
            self.assertEqual(len(rows), 1)
            self.assertEqual(rows[0]["candidateCount"], 1)
            self.assertFalse(rows[0]["confirmed"])
            self.assertEqual(len(retained), 6)
            self.assertEqual(sum(row["disposition"] == "NOT_ASSESSED" for row in retained), 5)
            with self.assertRaisesRegex(ValueError, "missing from frozen"):
                model.build_crosswalk(document, root, [])

    def test_existing_companion_remains_draft_and_separate(self):
        companion = model.companion_inventory()
        self.assertEqual(companion["disposition"], "EXISTING_DRAFT_INDEXED_NOT_ADDED_TO_DENOMINATOR")
        self.assertEqual(companion["trainingStatus"], "DRAFT_REVIEW_REQUIRED")

    def test_training_governance_matches_draft_package(self):
        status = model.gates.load(model.ATL / "training-status.json")
        entry = status["transactionContextTraining"]["MASTERCARD_MAESTRO_2026_4"]
        package = model.gates.load(model.ROOT / entry["evidence"])
        self.assertEqual(entry["status"], "IN_PROGRESS")
        self.assertFalse(entry["baselineChanged"])
        self.assertTrue(entry["countsExcludedFromSegmentCatalogSummary"])
        self.assertIsNone(entry["approvedDenominator"])
        self.assertIsNone(entry["confirmedRequirementCoveragePercent"])
        self.assertEqual(entry["counts"]["draftRules"], len(package["businessRequirements"]))
        self.assertEqual(entry["counts"]["contextRows"], len(package["testScenarios"]))
        self.assertEqual(set(entry["gates"]), set(status["requiredGates"]))


if __name__ == "__main__":
    unittest.main()
