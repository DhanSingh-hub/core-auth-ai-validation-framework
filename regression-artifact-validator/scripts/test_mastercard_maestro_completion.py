"""Verify release/overlap boundaries, additional source predicates and bounded physical fixtures."""

import copy
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path

import mastercard_maestro_offline as offline
import mastercard_maestro_reconciliation as release

SPEC = importlib.util.spec_from_file_location(
    "mm_v2", Path(__file__).with_name("complete-mastercard-maestro-model.py"))
model = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(model)


class CompletionTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.document = model.expanded_document()
        cls.source = model.base.gates.SOURCE.read_text(encoding="utf-8-sig")
        cls.old_source = model.base.OLD_SOURCE.read_text(encoding="utf-8-sig")

    def test_expanded_source_pinned_chains(self):
        package, denominator = model.base.build_overlay(self.document, self.source, self.old_source)
        self.assertEqual(len(package["businessRequirements"]), 38)
        self.assertEqual(len(denominator), 66)
        self.assertEqual(len(package["testCases"]), 132)
        self.assertEqual(len(package["testData"]), 132)
        for rule in self.document["rules"]:
            changed = {key for key in rule["valid"].keys() | rule["invalid"].keys()
                       if rule["valid"].get(key) != rule["invalid"].get(key)}
            self.assertEqual(len(changed), 1, rule["id"])
        self.assertTrue(all(row["approval"] == "REVIEW_REQUIRED" for row in denominator))
        self.assertFalse(package["manifest"]["baselineChanged"])

    def test_additional_source_and_mutant_drift_rejected(self):
        for field, value in (("page", 1), ("quote", "This claim is not in the primary source."),
                             ("invalid", self.document["rules"][-1]["valid"])):
            document = copy.deepcopy(self.document)
            document["rules"][-1][field] = value
            with self.assertRaises(ValueError):
                model.base.build_overlay(document, self.source, self.old_source)

    def test_section_content_not_section_number_determines_identity(self):
        old, _ = release.source_index("--- PAGE 33 ---\n1. Introduction\n10.1 Rules\nMust retain track.\n")
        new, _ = release.source_index("--- PAGE 34 ---\n1. Introduction\n10.1 Rules\nMust discard track.\n")
        row = release.compare_anchor({"section": "10.1"}, old, new)
        self.assertEqual(row["status"], "CHANGED_BLOCK_REVIEW_REQUIRED")
        self.assertNotEqual(row["oldSha256"], row["newSha256"])
        same, _ = release.source_index("--- PAGE 35 ---\n1. Introduction\n10.1 Rules\nMust retain track.\n")
        self.assertEqual(release.compare_anchor({"section": "10.1"}, old, same)["status"],
                         "IDENTICAL_NORMALIZED_BLOCK")

    def test_missing_ambiguous_and_companion_locators_never_pass(self):
        index, _ = release.source_index("--- PAGE 33 ---\n1. Introduction\n10.1 Rules\nMust do A.\n")
        missing = release.compare_anchor({"section": "10.2"}, index, index)
        self.assertEqual(missing["status"], "MISSING_OR_AMBIGUOUS_LOCATOR")
        ambiguous = {**index, "SECTION:10.1": index["SECTION:10.1"] * 2}
        self.assertEqual(release.compare_anchor({"section": "10.1"}, ambiguous, index)["status"],
                         "MISSING_OR_AMBIGUOUS_LOCATOR")
        for anchor in ({"section": "10.1 / 10.2"}, {"specification": "PETROLEUM", "element": "86"}):
            self.assertEqual(release.compare_anchor(anchor, index, index)["status"], "UNRESOLVED_LOCATOR")

    def test_element_locator_survives_section_and_page_shift(self):
        old, _ = release.source_index("--- PAGE 33 ---\n1. Introduction\n13.1 Elements\nNumber: 86 Name: Sequence\nSix digits.\n")
        new, _ = release.source_index("--- PAGE 34 ---\n1. Introduction\n13.2 Elements\nNumber: 86 Name: Sequence\nSix digits.\n")
        row = release.compare_anchor({"section": "13.1", "element": "86"}, old, new)
        self.assertEqual(row["status"], "IDENTICAL_NORMALIZED_BLOCK")
        self.assertEqual(row["newPage"], 34)

    def test_front_matter_is_retained_but_not_resolved_as_body(self):
        index, pages = release.source_index("--- PAGE 1 ---\n10.1 Rules ........ 10-1\nMust retain history.\n"
                                           "--- PAGE 33 ---\n1. Introduction\n10.1 Rules\nMust do A.\n")
        self.assertEqual(len(index["SECTION:10.1"]), 1)
        units, scan = release.obligation_inventory(pages, [])
        self.assertEqual(len(scan), 2)
        self.assertTrue(any("history" in row["quote"] for row in units))
        self.assertTrue(all(row["atomicity"] == "NOT_ASSERTED" for row in units))

    def test_all_legacy_rows_retained_and_overlap_not_merged(self):
        legacy = [{"legacyBrId": "B", "title": self.document["rules"][0]["statement"],
                   "sourceAnchors": [{"section": "10.1"}]},
                  {"legacyBrId": "C", "title": "Composite", "sourceAnchors": []}]
        index, _ = release.source_index("--- PAGE 33 ---\n1. Introduction\n10.1 Rules\nMust do A.\n")
        before = copy.deepcopy(legacy)
        rows, overlaps = release.reconcile_legacy(legacy, self.document["rules"], index, index)
        self.assertEqual(legacy, before)
        self.assertEqual(len(rows), 2)
        self.assertTrue(all(row["denominatorEligibility"].startswith("BLOCKED") for row in rows))
        self.assertEqual(overlaps[0]["evidence"], "EXACT_TITLE")
        self.assertEqual(overlaps[0]["disposition"], "REVIEW_REQUIRED_NO_AUTOMATIC_MERGE")

    def test_wire_controls_and_isolated_mutations(self):
        rows = offline.fixture_suite()
        self.assertEqual(len(rows), 15)
        self.assertEqual(sum(not row["observedIssues"] for row in rows), 3)
        for row in rows:
            self.assertEqual(offline.validate(bytes.fromhex(row["wireHex"]), row["context"]), row["expectedIssues"])
            self.assertEqual(len(row["expectedIssues"]), 0 if row["expectedValidation"] == "PASS" else 1)
            self.assertEqual(row["readiness"], "REVIEW_REQUIRED")
        with self.assertRaises(ValueError):
            offline.validate(b"", "UNKNOWN")
        with self.assertRaises(ValueError):
            offline.validate("not bytes", "MC_CREDIT")
        self.assertEqual(offline.validate(b"\xff", "MC_CREDIT"), ["NON_ASCII"])

    def test_serializer_rejects_missing_extra_and_control_bytes(self):
        fields = dict.fromkeys(offline.FIELD_NAMES, "")
        fields.pop("SegmentLength")
        fields.update(SegmentType="100", PromptCode="0020", SequenceNumber="123456")
        for invalid in ({**fields, "extra": "x"}, {**fields, "AccountNumber": "\x1c"},
                        {**fields, "AccountNumber": "A" * 1000}, {**fields, "InformationByte": 0}):
            with self.assertRaises(ValueError):
                offline.serialize(invalid)
        fields.pop("SequenceNumber")
        with self.assertRaises(ValueError):
            offline.serialize(fields)

    def test_no_segment_is_exempt_only_for_identified_nonfinancial_layout(self):
        row = offline.field_observations({"Totals Request": {"SequenceNumber": "x"}}, "MC_CREDIT")
        self.assertEqual(row["assessment"], "NOT_APPLICABLE_NO_SEGMENT100")
        row = offline.field_observations({"Financial Transaction Request": {}}, "MC_CREDIT")
        self.assertEqual(row["assessment"], "FAIL")
        for value in ({}, [], None):
            with self.assertRaises(ValueError):
                offline.field_observations(value, "MC_CREDIT")

    def test_json_observations_do_not_claim_wire_or_maestro_alternative_approval(self):
        seg = {"SegmentType": "100", "SequenceNumber": "123456",
               "PromptCode": {"TransactionType": "0", "CardType": "011"}}
        emv = {"SegmentType": "130", "EMVCardSequenceNumber": "01"}
        row = offline.field_observations({"Financial": {"a": seg, "b": emv}}, "MAESTRO_DEBIT")
        self.assertEqual(row["violations"], ["ELEMENT188_REPRESENTATION"])
        alternative = {**seg, "PromptCode": {"TransactionType": "0", "CardType": "012"}}
        row = offline.field_observations({"Financial": alternative}, "MAESTRO_DEBIT")
        self.assertEqual(row["assessment"], "REVIEW_REQUIRED")
        self.assertEqual(row["unknowns"], ["MAESTRO_ALTERNATIVE_APPLICABILITY"])
        duplicate = offline.field_observations({"Financial": [seg, seg]}, "MAESTRO_DEBIT")
        self.assertEqual(duplicate["violations"], ["SEGMENT100_MULTIPLICITY"])
        dynamic = {**seg, "SequenceNumber": "<RUNTIME>"}
        self.assertEqual(offline.field_observations({"Financial": dynamic}, "MAESTRO_DEBIT")["assessment"],
                         "REVIEW_REQUIRED")

    def test_scope_review_axes_are_not_positive_combinations(self):
        rows = model.scope_matrix(self.document, [])
        self.assertEqual(len(rows), 27)
        self.assertTrue(all(row["excludedFromConfirmedDenominator"] for row in rows))
        self.assertTrue(all(row["disposition"] == "SOURCE_SCOPE_REVIEW_REQUIRED" for row in rows))

    def test_read_only_verification_rejects_each_stale_surface(self):
        with tempfile.TemporaryDirectory() as directory:
            output = Path(directory)
            outputs = {"summary.json": {"coverage": None}}
            tables = {"register.csv": [{"id": "B", "approved": False, "evidence": ["X"]}]}

            def reset():
                (output / "summary.json").write_text(json.dumps(outputs["summary.json"]), encoding="utf-8")
                model.base.write_csv(output / "register.csv", tables["register.csv"])
                (output / "REPORT.md").write_text("Draft\n", encoding="utf-8")

            reset()
            before = {path.name: path.read_bytes() for path in output.iterdir()}
            model.verify_generated(output, outputs, tables, "Draft\n")
            self.assertEqual(before, {path.name: path.read_bytes() for path in output.iterdir()})
            for name in ("summary.json", "register.csv", "REPORT.md"):
                reset()
                (output / name).write_text("{}" if name.endswith(".json") else "Changed\n", encoding="utf-8")
                with self.assertRaises(ValueError):
                    model.verify_generated(output, outputs, tables, "Draft\n")
            reset()
            (output / "extra.json").write_text("{}", encoding="utf-8")
            with self.assertRaisesRegex(ValueError, "population"):
                model.verify_generated(output, outputs, tables, "Draft\n")

    def test_current_governance_matches_rederived_v2_and_preserves_v1(self):
        package, register = model.base.build_overlay(self.document, self.source, self.old_source)
        status = model.base.gates.load(model.base.ATL / "training-status.json")
        entry = status["transactionContextTraining"]["MASTERCARD_MAESTRO_2026_4"]
        self.assertEqual(entry["counts"]["draftRules"], len(package["businessRequirements"]))
        self.assertEqual(entry["counts"]["contextRows"], len(register))
        self.assertEqual(entry["counts"]["boundedWireFixtures"], len(offline.fixture_suite()))
        self.assertEqual(entry["generator"], "scripts/complete-mastercard-maestro-model.py")
        self.assertIn("mc-maestro-model-v1", entry["previousEvidence"])
        self.assertTrue((model.base.ROOT / entry["previousEvidence"]).is_file())
        self.assertIsNone(entry["approvedDenominator"])
        self.assertIsNone(entry["confirmedRequirementCoveragePercent"])
        self.assertEqual(set(entry["gates"]), set(status["requiredGates"]))

    def test_candidate_bindings_preserve_conditions_and_fail_on_identity_drift(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            for package in model.base.CONTEXT_PACKAGE.values():
                folder = root / package
                for child in ("requirements_scenarios", "test_cases", "test_data"):
                    (folder / child).mkdir(parents=True)
                support = {"rules": {"R": {"condition": "offline", "constraint": "track",
                                         "related_entity_ids": ["12"]}}, "by_scenario": {"S": ["R"]}}
                (folder / "requirements_scenarios" / "scenario_supporting_rules.json").write_text(
                    json.dumps(support), encoding="utf-8")
                case = {"id": "C", "scenario_id": "S", "test_data_file": "test_data\\x.json",
                        "rule_checks": [{"kb_rule_ids": ["R"]}]}
                (folder / "test_cases" / "test_cases.jsonl").write_text(json.dumps(case) + "\n", encoding="utf-8")
                (folder / "test_data" / "x.json").write_text("{}", encoding="utf-8")
            rows = [{"context": "MC_CREDIT", "independentBrId": "BR-MM4-001",
                     "candidates": [{"aiRuleId": "R", "linkedCaseCount": 1, "linkedScenarioCount": 1}]}]
            model.delivery_bindings(self.document, root, rows)
            candidate = rows[0]["candidates"][0]
            self.assertEqual(candidate["caseIds"], ["C"])
            self.assertEqual(candidate["explicitPredicateCaseIds"], ["C"])
            self.assertEqual(candidate["aiCondition"], "offline")
            self.assertEqual(candidate["conditionAgreement"], "UNASSESSED_NO_SEMANTIC_PROMOTION")
            candidate["linkedCaseCount"] = 2
            with self.assertRaisesRegex(ValueError, "counts differ"):
                model.delivery_bindings(self.document, root, rows)

    def test_physical_observations_require_frozen_bytes(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            file = root / "mastercard_credit" / "test_data" / "x.json"
            file.parent.mkdir(parents=True)
            file.write_text('{"Totals Request": {}}', encoding="utf-8")
            frozen = [{"path": str(file.relative_to(root)), "sha256": model.base.digest(file)}]
            self.assertEqual(len(model.physical_inventory(root, frozen)), 1)
            file.write_text('{"Totals Request": {"changed": true}}', encoding="utf-8")
            with self.assertRaisesRegex(ValueError, "frozen bytes"):
                model.physical_inventory(root, frozen)

    def test_native_finding_order_does_not_change_frozen_evidence(self):
        issues = [{"entity_id": "S2", "status": "FAIL"}, {"entity_id": "S1", "status": "FAIL"},
                  {"entity_id": "S2", "status": "FAIL"}]
        original = {"gate": "FAIL", "issues": issues, "issue_counts": {"uncovered": 3}}
        before = copy.deepcopy(original)
        first = model.canonical_gate_report(original)
        second = model.canonical_gate_report({**original, "issues": list(reversed(issues))})
        self.assertEqual(first, second)
        self.assertEqual(len(first["issues"]), 3)
        self.assertEqual(original, before)


if __name__ == "__main__":
    unittest.main()
