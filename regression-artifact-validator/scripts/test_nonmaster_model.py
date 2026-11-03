"""Verify non-Mastercard source, lifecycle, evidence and no-certification boundaries."""

import copy
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

SPEC = importlib.util.spec_from_file_location(
    "nonmaster_model", Path(__file__).with_name("build-nonmaster-model.py"))
model = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(model)


class NonMasterModelTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        model.base.gates.trusted_source()
        cls.doc = model.document()
        cls.source = model.base.gates.SOURCE.read_text(encoding="utf-8-sig")
        cls.old = model.base.OLD_SOURCE.read_text(encoding="utf-8-sig")

    def build(self, doc=None):
        return model.build_model(doc or self.doc, self.source, self.old)

    def test_exact_populations_and_referential_integrity(self):
        package, denominator = self.build()
        self.assertEqual(len(package["businessRequirements"]), 30)
        self.assertEqual(len(denominator), 66)
        self.assertEqual(len(package["testCases"]), 132)
        self.assertEqual(len(package["testData"]), 132)
        br, ts, tc, td = (model.base.unique(package[key]) for key in
                          ("businessRequirements", "testScenarios", "testCases", "testData"))
        for scenario in ts.values():
            self.assertTrue(set(scenario["requirementIds"]) <= br.keys())
        for case in tc.values():
            self.assertTrue(set(case["scenarioIds"]) <= ts.keys())
            self.assertTrue(set(case["testDataIds"]) <= td.keys())
        for datum in td.values():
            case = tc[datum["testCaseIds"][0]]
            assertion = case["assertions"][0]
            actual = model.evaluate(assertion["predicate"], datum["payload"])
            self.assertEqual(actual, case["expectedOutcome"] == "PASS")
            self.assertEqual(case["expectedOutcome"], datum["observedValidation"])
            self.assertEqual(datum["readiness"], "REVIEW_REQUIRED")
        self.assertTrue(all(x["approval"] == "REVIEW_REQUIRED" for x in denominator))

    def test_source_page_and_quote_drift_fail(self):
        for key, value in (("page", 1), ("quote", "This is not an authoritative quote.")):
            doc = copy.deepcopy(self.doc)
            doc["rules"][0][key] = value
            with self.assertRaisesRegex(ValueError, "Source quote/page"):
                self.build(doc)

    def test_no_promotion_or_silent_scope_change(self):
        for key, value in (("status", "APPROVED"), ("baselineChanged", True),
                           ("specificationVersion", "2026-3"), ("contexts", {})):
            doc = copy.deepcopy(self.doc)
            doc[key] = value
            with self.assertRaises(ValueError):
                self.build(doc)

    def test_duplicate_and_unknown_contexts_fail(self):
        for contexts in ([], ["UNKNOWN"], ["AMEX_CREDIT", "AMEX_CREDIT"]):
            doc = copy.deepcopy(self.doc)
            doc["rules"][0]["contexts"] = contexts
            with self.assertRaises(ValueError):
                self.build(doc)

    def test_mutants_cannot_be_success_shaped(self):
        doc = copy.deepcopy(self.doc)
        doc["rules"][0]["invalid"] = doc["rules"][0]["valid"]
        with self.assertRaisesRegex(ValueError, "Control/mutant"):
            self.build(doc)

    def test_duplicate_rule_fails(self):
        doc = copy.deepcopy(self.doc)
        doc["rules"].append(doc["rules"][0])
        with self.assertRaisesRegex(ValueError, "Duplicate"):
            self.build(doc)

    def test_network_specific_rules_do_not_leak(self):
        rules = {r["id"]: r for r in self.doc["rules"]}
        for number in range(27, 31):
            self.assertEqual(rules[f"NM4-{number:03}"]["contexts"], ["DISCOVER_CREDIT"])
        for number in range(21, 27):
            self.assertEqual(rules[f"NM4-{number:03}"]["contexts"], ["VISA_CREDIT"])
        self.assertEqual(rules["NM4-011"]["contexts"], ["INTERLINK_DEBIT"])
        self.assertNotIn("AMEX_CREDIT", rules["NM4-005"]["contexts"])

    def test_original_precedes_each_subsequent_ia_role(self):
        predicate = next(r["predicate"] for r in self.doc["rules"] if r["id"] == "NM4-028")
        for role in predicate["subsequent"]:
            self.assertTrue(model.evaluate(predicate, {"roles": ["ORIGINAL_AUTH", role]}))
            self.assertFalse(model.evaluate(predicate, {"roles": [role, "ORIGINAL_AUTH"]}))

    def test_completion_closes_all_subsequent_ia_roles(self):
        predicate = next(r["predicate"] for r in self.doc["rules"] if r["id"] == "NM4-030")
        for role in predicate["subsequent"]:
            self.assertFalse(model.evaluate(predicate, {"roles": ["ORIGINAL_AUTH", "IA_COMPLETION", role]}))

    def test_inapplicable_lifecycle_observations_cannot_pass_vacuously(self):
        for rule_id, roles in (("NM4-024", ["INITIAL"]), ("NM4-028", ["ORIGINAL_AUTH"]),
                               ("NM4-030", ["ORIGINAL_AUTH", "INCREMENTAL"])):
            predicate = next(r["predicate"] for r in self.doc["rules"] if r["id"] == rule_id)
            with self.assertRaises(ValueError):
                model.evaluate(predicate, {"roles": roles})

    def test_visa_window_exact_thresholds(self):
        for maximum in (7, 14):
            predicate = {"op": "maxDays", "field": "days", "maximum": maximum}
            for value in (0, maximum - 1, maximum, maximum + 1):
                self.assertEqual(model.evaluate(predicate, {"days": value}), value <= maximum)
            for value in (-1, True, "14", None):
                with self.assertRaises(ValueError):
                    model.evaluate(predicate, {"days": value})

    def test_invalid_or_missing_lifecycle_observation_fails_explicitly(self):
        predicate = {"op": "precededBy", "field": "roles", "initial": "INITIAL",
                     "subsequent": ["INCREMENTAL"]}
        for facts in ({}, {"roles": []}, {"roles": "INITIAL"}, {"roles": ["UNKNOWN"]},
                      {"roles": [1]}, {"roles": ["ORIGINAL_AUTH", "INCREMENTAL"]}):
            with self.assertRaises(ValueError):
                model.evaluate(predicate, facts)

    def test_missing_or_empty_lifecycle_identity_cannot_pass(self):
        predicate = {"op": "same", "field": "approval", "other": "original"}
        for facts in ({}, {"approval": ""}, {"approval": "", "original": ""},
                      {"approval": "A", "original": 1}):
            with self.assertRaises(ValueError):
                model.evaluate(predicate, facts)

    def test_shared_wire_controls_and_isolated_mutants(self):
        rows = model.fixtures()
        self.assertEqual(len(rows), 4)
        for row in rows:
            self.assertEqual(model.validate_frame(bytes.fromhex(row["wireHex"])), row["expectedIssues"])
            self.assertEqual(len(row["expectedIssues"]), 0 if row["id"].endswith("CONTROL") else 1)
            self.assertIn("NOT network identity", row["limits"])

    def test_non_ascii_control_and_wrong_segment_wire_fail(self):
        raw = bytes.fromhex(model.fixtures()[0]["wireHex"])
        self.assertEqual(model.validate_frame(raw + b"\xff"), ["ASCII_OR_CONTROL"])
        self.assertEqual(model.validate_frame(raw.replace(b"100", b"101", 1)), ["SEGMENT_TYPE"])
        with self.assertRaises(ValueError):
            model.validate_frame("not bytes")

    def test_scope_retains_all_categories_without_brand_conflation(self):
        previous = model.base.gates.load(model.NETWORK_REVIEW / "review.json")
        rows = model.scope_register(self.doc, previous)
        self.assertEqual(sum(r["kind"] == "NETWORK_PROGRAM" for r in rows), 7)
        self.assertEqual(sum(r["kind"] == "SECTION5_CATEGORY_NOT_A_BRAND" for r in rows), 16)
        self.assertEqual(sum(r["kind"] == "FINANCIAL_CARD_CODE_NOT_A_NETWORK" for r in rows), 35)
        self.assertNotIn("CARD-080", {r["context"] for r in rows})
        self.assertTrue(all(r["deliveryStatus"] == "NOT_SUPPLIED" for r in rows
                            if r["context"] in model.CONTEXTS - model.DELIVERIES.keys()))

    def test_existing_outputs_refused_and_delivery_overlap_rejected(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            delivery = root / "delivery"
            delivery.mkdir()
            for output in (delivery, delivery / "nested", root):
                with self.assertRaisesRegex(ValueError, "disjoint"):
                    model.run(delivery, output)
            output = root / "output"
            output.mkdir()
            with self.assertRaisesRegex(ValueError, "New output"):
                model.run(delivery, output)

    def test_each_output_surface_and_population_are_checked_read_only(self):
        with tempfile.TemporaryDirectory() as temp:
            output = Path(temp)
            expected = {"summary.json": b'{"a":1}', "register.csv": b"a\r\n1\r\n", "REPORT.md": b"# Draft"}
            for name, value in expected.items():
                (output / name).write_bytes(value)
            model.verify_outputs(output, expected)
            for name, value in expected.items():
                (output / name).write_bytes(value + b"stale")
                with self.assertRaisesRegex(ValueError, "Stale"):
                    model.verify_outputs(output, expected)
                self.assertEqual((output / name).read_bytes(), value + b"stale")
                (output / name).write_bytes(value)
            (output / "extra").write_text("extra")
            with self.assertRaisesRegex(ValueError, "population"):
                model.verify_outputs(output, expected)

    def test_delivery_freeze_detects_content_and_population_changes(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            for name in model.DELIVERIES.values():
                (root / name).mkdir()
                (root / name / "evidence.json").write_text("{}")
            frozen = model.freeze_delivery(root)
            path = root / "american_express_credit" / "evidence.json"
            path.write_text('{"changed":true}')
            self.assertNotEqual(frozen, model.freeze_delivery(root))
            path.write_text("{}")
            self.assertEqual(frozen, model.freeze_delivery(root))
            (path.parent / "extra.json").write_text("{}")
            self.assertNotEqual(frozen, model.freeze_delivery(root))

    def test_missing_delivery_package_cannot_be_silently_excluded(self):
        with tempfile.TemporaryDirectory() as temp:
            with self.assertRaisesRegex(ValueError, "Required delivery package"):
                model.freeze_delivery(Path(temp))

    def test_delivery_candidates_retain_identities_missing_files_and_unmatched_rules(self):
        rule = self.doc["rules"][0]
        support = {
            "meta": {"spec_version": "2026-4"},
            "rules": {
                "AI-MATCH": {"rule_id": "AI-MATCH", "source_id": "SRC-ATL105-PDF-001",
                             "page": rule["page"], "statement": rule["quote"], "condition": "AI condition"},
                "AI-UNMATCHED": {"rule_id": "AI-UNMATCHED", "source_id": "OTHER",
                                 "page": 1, "statement": "Unmatched producer rule"},
            }, "by_scenario": {"TS-AI": ["AI-MATCH"]}}
        case = {"id": "TC-AI", "scenario_id": "TS-AI", "test_data_file": "test_data/existing.json",
                "test_data_leg_files": ["test_data/existing.json", "test_data/missing.json"],
                "rule_checks": [{"kb_rule_ids": ["AI-MATCH"]}]}
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            for name in model.DELIVERIES.values():
                package = root / name
                (package / "requirements_scenarios").mkdir(parents=True)
                (package / "requirements_scenarios" / "scenario_supporting_rules.json").write_text(json.dumps(support))
                (package / "test_data").mkdir()
                (package / "test_data" / "existing.json").write_text(
                    '{"request":{"SegmentType":"130","EMVCardSequenceNumber":"01"}}')
            gate = {"gate": "FAIL", "issues": [{"code": "Z"}, {"code": "A"}, {"code": "A"}]}
            with patch.object(model.base.gates, "validate_package", side_effect=lambda p: {**gate, "package": p.name}), \
                    patch.object(model.base.gates, "read_cases", return_value=[case]):
                native, physical, crosswalk, retained = model.assess_deliveries(
                    self.doc, root, model.freeze_delivery(root))
            self.assertEqual(len(retained), 4)
            self.assertTrue(all(not r["semanticConfirmation"] for r in retained))
            unmatched = [r for r in retained if r["aiRuleId"] == "AI-UNMATCHED"]
            self.assertTrue(all(not r["candidateBrIds"] for r in unmatched))
            self.assertTrue(all([i["code"] for i in r["issues"]] == ["A", "A", "Z"] for r in native))
            self.assertEqual(len(physical), 2)
            self.assertTrue(all(r["assessment"] == "FAIL_ELEMENT188_WIDTH" for r in physical))
            matched = next(r for r in crosswalk if r["independentBrId"] == "BR-NM4-001"
                           and r["context"] == "AMEX_CREDIT")
            binding = matched["candidates"][0]["bindings"][0]
            self.assertEqual(binding["caseId"], "TC-AI")
            self.assertEqual(binding["scenarioId"], "TS-AI")
            self.assertEqual(binding["physicalTdPaths"], ["test_data/existing.json"])
            self.assertEqual(binding["missingTdPaths"], ["test_data/missing.json"])
            self.assertTrue(binding["explicitPredicate"])
            self.assertFalse(matched["confirmed"])
            self.assertTrue(all(r["disposition"] == "DELIVERY_NOT_SUPPLIED" for r in crosswalk
                                if r["context"] == "VISA_CREDIT"))

    def test_empty_csv_and_multishape_rows_round_trip(self):
        self.assertEqual(model.csv_text([]), "record\r\n")
        text = model.csv_text([{"id": "A", "context": ["VISA"]}, {"id": "B", "extra": False}])
        self.assertIn('id,context,extra\r\n', text)

    def test_governance_and_framework_tasks_point_to_current_draft(self):
        status = model.base.gates.load(model.base.ATL / "training-status.json")
        entry = status["transactionContextTraining"]["NONMASTER_2026_4"]
        self.assertEqual(entry["status"], "IN_PROGRESS")
        self.assertEqual(entry["counts"]["draftRules"], len(self.doc["rules"]))
        self.assertIsNone(entry["approvedDenominator"])
        self.assertIsNone(entry["confirmedRequirementCoveragePercent"])
        for key in ("evidence", "coverageReport", "generator", "regressionTest"):
            self.assertTrue((model.base.ROOT / entry[key]).is_file(), key)
        tasks = model.base.gates.load(model.base.ROOT / "framework-tasks.json")["tasks"]
        names = {t["name"] for t in tasks}
        self.assertTrue({"nonmaster-model-build", "nonmaster-model-check", "nonmaster-model-test"} <= names)


if __name__ == "__main__":
    unittest.main()
