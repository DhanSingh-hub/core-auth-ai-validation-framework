"""Regression checks for business-delivery inventory and coverage boundaries."""

import importlib.util
import json
import tempfile
import unittest
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "mc_report", Path(__file__).with_name("report-mastercard-business-delivery.py"))
report = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(report)


class DeliveryReportTest(unittest.TestCase):
    def test_duplicate_keys_and_ids_fail(self):
        with self.assertRaises(ValueError):
            report.parse('{"id": 1, "id": 2}')
        with self.assertRaises(ValueError):
            report.parse('{"value": NaN}')
        with self.assertRaises(ValueError):
            report.index([{"id": "TC-1"}, {"id": "TC-1"}])

    def test_path_escape_fails(self):
        with self.assertRaises(ValueError):
            report.resolve(Path(tempfile.gettempdir()) / "mc-package", "..\\outside.json")

    def test_segment_observation_is_not_wire_validation(self):
        result = report.physical_observation({"Request": {
            "NumberofSegments": "01", "A": {"SegmentType": "100"}, "B": {"SegmentType": "130"}}})
        self.assertFalse(result["countMatches"])
        self.assertEqual(result["segments"], ["100", "130"])
        self.assertIsNone(report.physical_observation({"Request": {
            "NumberofSegments": 1, "A": {"SegmentType": "100"}}})["countMatches"])

    def test_lineage_does_not_imply_predicate_or_confirmed_coverage(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)

            def write(name, value):
                path = root / name
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text(json.dumps(value), encoding="utf-8")

            write("requirements_scenarios/business_requirements.json", {
                "meta": {"spec_version": "2026-4"},
                "business_requirements": [{"id": "BR-1", "scenario_ids": ["TS-1", "TS-2"]}]})
            write("requirements_scenarios/business_scenarios.json", {
                "scenarios": [{"id": "TS-1"}, {"id": "TS-2"}]})
            write("requirements_scenarios/scenario_supporting_rules.json", {
                "rules": {identity: {"rule_id": identity} for identity in ("R-1", "R-2", "R-3")},
                "by_scenario": {"TS-1": ["R-1", "R-2"]}})
            write("reports/traceability_matrix_full.json", {"summary": {"total_test_cases": 2}})
            write("test_data/one.json", {"Request": {"NumberofSegments": "01",
                                                   "A": {"SegmentType": "100"}}})
            write("test_data/orphan.json", {"Request": {}})
            cases = [
                {"id": "TC-1", "scenario_id": "TS-1", "requirement_ids": ["BR-1"],
                 "test_data_file": "test_data/one.json",
                 "rule_checks": [{"op": "segment_present", "kb_rule_ids": ["R-1"]}],
                 "response_checks": [{"rule_id": "R-2", "tier": "ASSERT"}]},
                {"id": "TC-2", "scenario_id": "TS-1", "requirement_id": "BR-1",
                 "test_data_file": "test_data/one.json", "test_data_leg_files": [
                     "test_data/one.json", "test_data/missing.json"]},
            ]
            (root / "test_cases").mkdir()
            (root / "test_cases/test_cases.jsonl").write_text(
                "\n".join(json.dumps(case) for case in cases), encoding="utf-8")
            result = report.audit_package(root)
            counts = result["counts"]
            self.assertEqual(counts["supportingRules"], 3)
            self.assertEqual(counts["rulesWithPhysicalLineage"], 2)
            self.assertEqual(counts["rulesWithExplicitPredicateAndTd"], 1)
            self.assertEqual(counts["casesWithAllDeclaredTd"], 1)
            self.assertEqual(counts["missingTdReferences"], 1)
            self.assertEqual(counts["unreferencedTdFiles"], 1)
            self.assertEqual(counts["scenariosWithCases"], 1)
            self.assertEqual(result["rulePhysicalLineagePercent"], 66.67)
            self.assertIsNone(result["confirmedRequirementCoveragePercent"])
            self.assertIsNone(result["independentRuleDenominator"])
            self.assertEqual(result["semanticAssessmentStatus"], "NOT_ASSESSED")


if __name__ == "__main__":
    unittest.main()
