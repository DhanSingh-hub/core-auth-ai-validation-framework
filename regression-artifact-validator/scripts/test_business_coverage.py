"""Mutation checks for reviewed denominators, independent semantics and E2E evidence."""

import copy
import json
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path

import build_companion_coverage_pilot as pilot
import business_coverage as coverage


class BusinessCoverageTest(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory()
        self.addCleanup(self.temporary.cleanup)
        self.output = Path(self.temporary.name) / "pilot"
        pilot.build(self.output)
        self.root = self.output / "input"
        self.document = coverage.gates.load(self.root / "assessment.json")

    def write(self, name, value):
        return pilot.write(self.root, name, value)

    def save(self):
        self.write("assessment.json", self.document)

    def assess(self):
        self.save()
        return coverage.assess(self.root)

    def decision(self, name, subject, role="SME", status="APPROVED"):
        return self.write("reviews/" + name + ".json", {
            "subjectSha256": coverage.fingerprint(subject), "decision": status,
            "reviewer": "SYNTHETIC-TEST-REVIEWER", "role": role,
            "rationale": "Synthetic regression attestation, never a production approval."})

    def review_all(self, complete_semantics=True):
        context = coverage.review_context(self.document)
        self.document["scope"]["review"] = self.decision("scope", context)
        for rule in self.document["requirements"]:
            rule["review"] = self.decision(rule["id"], {
                "context": context, "requirement": coverage.subject(rule)})
        for scenario in self.document["scenarios"]:
            scenario["review"] = self.decision(scenario["id"], {
                "context": context, "scenario": coverage.subject(scenario)})
        for flow in self.document["flows"]:
            flow["review"] = self.decision(flow["id"], {
                "context": context, "flow": coverage.subject(flow)})
        artifacts = {r["id"]: r for name in ("testCases", "testData") for r in self.document[name]}
        for assessment in self.document["semanticAssessments"]:
            artifact = artifacts[assessment["id"]]
            evidence = coverage.gates.load(self.root / assessment["evidence"]["path"])
            if complete_semantics:
                kind = "TC" if artifact["id"].startswith("TC-") else "TD"
                evidence["checks"] = [
                    {"id": key, "status": "PASS", "detail": "Synthetic complete review for regression only."}
                    for key in self.document["semanticPolicy"][kind]]
                assessment["evidence"] = self.write(assessment["evidence"]["path"], evidence)
            assessment["review"] = self.decision(assessment["id"], {
                "context": context, "artifact": artifact, "evidence": assessment["evidence"]})
        return context

    def execution(self, response="APPROVED"):
        context = self.review_all()
        flow = self.document["flows"][0]
        case = next(c for c in self.document["testCases"] if c["id"] == flow["steps"][0]["caseId"])
        data = {r["id"]: r for r in self.document["testData"]}
        environment = self.write("run/environment.json", {"id": "SYNTHETIC-ENV", "mode": "AUTHORIZED_SIMULATOR"})
        run = {
            "id": "RUN-1", "flowId": flow["id"], "environment": environment,
            "authorization": self.decision("environment", {
                "context": context, "environment": environment, "flowId": flow["id"]}, "EXECUTION_OWNER"),
            "prerequisites": {
                key: self.write(f"run/{key}.json", {
                    "id": key, "status": "READY", "environmentId": "SYNTHETIC-ENV"})
                for key in flow["prerequisites"]},
            "steps": [{
                "id": "purchase", "caseSha256": coverage.fingerprint(case),
                "dataHashes": {identity: data[identity]["file"]["sha256"] for identity in case["testDataIds"]},
                "request": self.write("run/request.json", {"synthetic": True}),
                "response": self.write("run/response.json", {"outcome": response}),
                "assertions": copy.deepcopy(flow["steps"][0]["assertions"])}]}
        self.attest(run, context)
        self.document["runs"] = [run]
        return run, context

    def attest(self, run, context):
        flow = next(f for f in self.document["flows"] if f["id"] == run["flowId"])
        run["attestation"] = self.decision("run", {
            "context": context, "flow": coverage.subject(flow),
            "run": {k: v for k, v in run.items() if k != "attestation"}}, "EXECUTION_OWNER")

    def test_pilot_never_promotes_drafts_or_logical_checks(self):
        result = self.assess()
        self.assertFalse(result["denominatorEstablished"])
        for name in ("scenarioCompleteness", "ruleAdequacy", "semanticTC", "semanticTD"):
            self.assertIsNone(result["metrics"][name]["percent"])
        self.assertEqual(result["metrics"]["scenarioCompleteness"]["provisional"]["percent"], 100)
        self.assertEqual(result["metrics"]["semanticTC"]["notFullyAssessed"], 12)
        self.assertEqual(result["metrics"]["execution"]["blocked"], 1)
        self.assertIsNone(result["metrics"]["execution"]["passedOfAttempted"]["percent"])

    def test_complete_reviewed_fixture_has_separate_metrics(self):
        self.review_all()
        metrics = self.assess()["metrics"]
        for name in ("scenarioCompleteness", "ruleAdequacy", "semanticTC", "semanticTD"):
            self.assertEqual(metrics[name]["percent"], 100)
        self.assertIsNone(metrics["execution"]["passedOfAttempted"]["percent"])
        self.assertEqual(metrics["execution"]["executedOfApplicable"]["percent"], 0)

    def test_review_alone_cannot_complete_partial_semantics(self):
        self.review_all(complete_semantics=False)
        metrics = self.assess()["metrics"]
        self.assertEqual(metrics["ruleAdequacy"]["percent"], 0)
        self.assertIsNone(metrics["semanticTC"]["percent"])
        self.assertEqual(metrics["semanticTC"]["notFullyAssessed"], 12)

    def test_missing_scenario_is_uncovered_not_removed_from_denominator(self):
        removed = self.document["scenarios"].pop()
        removed_cases = [c for c in self.document["testCases"] if c["scenarioId"] == removed["id"]]
        identifiers = {c["id"] for c in removed_cases}
        identifiers.update(i for c in removed_cases for i in c["testDataIds"])
        for key in ("testCases", "testData", "semanticAssessments"):
            self.document[key] = [r for r in self.document[key] if r["id"] not in identifiers]
        self.review_all()
        metric = self.assess()["metrics"]["scenarioCompleteness"]
        self.assertEqual(metric["denominator"], 3)
        self.assertEqual(metric["percent"], 66.67)

    def test_unresolved_applicability_blocks_official_percent(self):
        self.document["requirements"][0]["applicability"] = "UNRESOLVED"
        self.review_all()
        result = self.assess()
        self.assertFalse(result["denominatorEstablished"])
        self.assertEqual(result["metrics"]["scenarioCompleteness"]["unresolved"], 1)
        self.assertIsNone(result["metrics"]["ruleAdequacy"]["percent"])

    def test_exclusion_requires_review(self):
        self.document["requirements"][0]["applicability"] = "EXCLUDED"
        self.review_all()
        self.document["requirements"][0]["review"] = None
        result = self.assess()
        self.assertFalse(result["denominatorEstablished"])
        self.assertEqual(result["metrics"]["scenarioCompleteness"]["excluded"], 1)

    def test_rejected_universe_does_not_establish_denominator(self):
        context = self.review_all()
        self.document["scope"]["review"] = self.decision("scope", context, status="REJECTED")
        self.assertFalse(self.assess()["denominatorEstablished"])

    def test_inventory_change_invalidates_review(self):
        self.review_all()
        self.document["testCases"][0]["intent"] += " changed"
        with self.assertRaisesRegex(ValueError, "Stale review"):
            self.assess()

    def test_source_release_or_hash_drift_is_rejected(self):
        self.document["sources"][0]["version"] = "2026-3"
        with self.assertRaisesRegex(ValueError, "release mismatch"):
            self.assess()
        self.document["sources"][0]["version"] = "2026-4"
        self.document["sources"][0]["sha256"] = "0" * 64
        with self.assertRaisesRegex(ValueError, "Stale evidence"):
            self.assess()

    def test_wrong_source_page_is_rejected(self):
        self.document["sources"][0]["anchors"][0]["page"] = 1
        with self.assertRaisesRegex(ValueError, "page/excerpt"):
            self.assess()

    def test_duplicate_ids_and_dangling_links_are_rejected(self):
        self.document["requirements"].append(copy.deepcopy(self.document["requirements"][0]))
        with self.assertRaisesRegex(ValueError, "duplicate ID"):
            self.assess()
        self.document["requirements"].pop()
        self.document["testCases"][0]["testDataIds"].append("MISSING")
        with self.assertRaisesRegex(ValueError, "Unknown TC data"):
            self.assess()

    def test_trace_link_without_rule_evidence_does_not_cover_variation(self):
        for row in self.document["semanticAssessments"]:
            evidence = coverage.gates.load(self.root / row["evidence"]["path"])
            evidence["ruleChecks"] = []
            row["evidence"] = self.write(row["evidence"]["path"], evidence)
        result = self.assess()
        self.assertEqual(result["metrics"]["ruleAdequacy"]["provisional"]["percent"], 0)

    def test_producer_self_validation_is_rejected(self):
        row = self.document["semanticAssessments"][0]
        evidence = coverage.gates.load(self.root / row["evidence"]["path"])
        evidence["authority"] = "AI_SOLUTION"
        row["evidence"] = self.write(row["evidence"]["path"], evidence)
        with self.assertRaisesRegex(ValueError, "not independent"):
            self.assess()

    def test_negative_predicate_failure_is_not_semantic_failure(self):
        result = self.assess()
        self.assertEqual(result["metrics"]["semanticTC"]["failed"], 0)
        self.assertEqual(result["metrics"]["ruleAdequacy"]["provisional"]["percent"], 100)

    def test_unknown_predicate_result_stays_partial(self):
        row = self.document["semanticAssessments"][0]
        evidence = coverage.gates.load(self.root / row["evidence"]["path"])
        evidence["ruleChecks"][0]["observed"] = "NOT_ASSESSED"
        row["evidence"] = self.write(row["evidence"]["path"], evidence)
        result = self.assess()
        self.assertEqual(result["metrics"]["semanticTC"]["failed"], 0)
        self.assertEqual(result["metrics"]["ruleAdequacy"]["provisional"]["percent"], 66.67)

    def test_failed_semantic_check_is_reported(self):
        self.review_all()
        row = self.document["semanticAssessments"][0]
        evidence = coverage.gates.load(self.root / row["evidence"]["path"])
        evidence["checks"][0]["status"] = "FAIL"
        row["evidence"] = self.write(row["evidence"]["path"], evidence)
        row["review"] = None
        result = self.assess()
        self.assertEqual(result["metrics"]["semanticTC"]["failed"], 1)
        self.assertEqual(result["metrics"]["semanticTC"]["percent"], 91.67)

    def test_stale_data_evidence_is_rejected(self):
        item = self.document["testData"][0]
        (self.root / item["file"]["path"]).write_text("{}", encoding="utf-8")
        with self.assertRaisesRegex(ValueError, "Stale evidence"):
            self.assess()

    def test_paths_duplicate_keys_and_invalid_numbers_are_rejected(self):
        with self.assertRaises(ValueError):
            coverage.bound_file(self.root, {"path": "..\\outside", "sha256": "0" * 64})
        for raw in ('{"schemaVersion":1,"schemaVersion":1}', '{"value":NaN}'):
            (self.root / "assessment.json").write_text(raw, encoding="utf-8")
            with self.assertRaises(ValueError):
                coverage.assess(self.root)

    def test_authorized_execution_evidence_has_independent_denominators(self):
        self.execution()
        metrics = self.assess()["metrics"]["execution"]
        self.assertEqual(metrics["passedOfAttempted"]["percent"], 100)
        self.assertEqual(metrics["executedOfApplicable"]["percent"], 100)
        self.assertEqual(metrics["blocked"], 0)

    def test_actual_response_not_declared_pass_controls_result(self):
        self.execution(response="DECLINED")
        metric = self.assess()["metrics"]["execution"]
        self.assertEqual(metric["passedOfAttempted"]["percent"], 0)
        self.assertEqual(metric["executedOfApplicable"]["percent"], 100)
        self.assertEqual(metric["failed"], 1)

    def test_expected_decline_can_pass_a_negative_flow(self):
        self.document["flows"][0]["steps"][0]["assertions"][0]["expected"] = "DECLINED"
        self.execution(response="DECLINED")
        self.assertEqual(self.assess()["metrics"]["execution"]["passedOfAttempted"]["percent"], 100)

    def test_fabricated_assertions_cannot_override_flow_expectation(self):
        run, context = self.execution(response="DECLINED")
        run["steps"][0]["assertions"][0]["expected"] = "DECLINED"
        self.attest(run, context)
        with self.assertRaisesRegex(ValueError, "differ from"):
            self.assess()

    def test_missing_authorization_and_prerequisite_are_rejected(self):
        run, context = self.execution()
        original = run.pop("authorization")
        with self.assertRaisesRegex(ValueError, "Authorized environment"):
            self.assess()
        run["authorization"] = original
        run["prerequisites"].pop(next(iter(run["prerequisites"])))
        self.attest(run, context)
        with self.assertRaisesRegex(ValueError, "unresolved required"):
            self.assess()

    def test_stale_run_leg_binding_and_duplicate_runs_are_rejected(self):
        run, context = self.execution()
        run["steps"][0]["dataHashes"] = {}
        self.attest(run, context)
        with self.assertRaisesRegex(ValueError, "executed TD"):
            self.assess()
        self.document["runs"].append({**run, "id": "RUN-2"})
        with self.assertRaisesRegex(ValueError, "exactly one run"):
            self.assess()

    def test_incomplete_flow_is_attempted_but_not_executed(self):
        second = copy.deepcopy(self.document["flows"][0]["steps"][0])
        second["id"] = "follow-on"
        self.document["flows"][0]["steps"].append(second)
        self.execution()
        metric = self.assess()["metrics"]["execution"]
        self.assertEqual(metric["passedOfAttempted"]["denominator"], 1)
        self.assertEqual(metric["executedOfApplicable"]["numerator"], 0)
        self.assertEqual(metric["incomplete"], 1)

    def test_ready_but_not_run_is_not_blocked_or_executed(self):
        flow = self.document["flows"][0]
        flow["environmentId"] = "SYNTHETIC-ENV"
        flow["readiness"] = {
            key: self.write(f"ready/{key}.json", {
                "id": key, "environmentId": flow["environmentId"], "status": "READY"})
            for key in flow["prerequisites"]}
        self.review_all()
        metric = self.assess()["metrics"]["execution"]
        self.assertEqual(metric["notRun"], 1)
        self.assertEqual(metric["blocked"], 0)
        self.assertEqual(metric["executedOfApplicable"]["percent"], 0)
        self.assertIsNone(metric["passedOfAttempted"]["percent"])

    def test_missing_semantic_evidence_is_visible_in_population(self):
        self.document["semanticAssessments"] = []
        metrics = self.assess()["metrics"]
        self.assertEqual(metrics["semanticTC"]["population"], 12)
        self.assertEqual(metrics["semanticTC"]["notFullyAssessed"], 12)
        self.assertIsNone(metrics["semanticTC"]["boundedChecks"]["percent"])
        self.assertEqual(metrics["ruleAdequacy"]["provisional"]["percent"], 0)

    def test_multi_leg_missing_predicate_evidence_cannot_close_rule(self):
        case = self.document["testCases"][0]
        existing = self.document["testData"][0]
        additional = {**existing, "id": "TD-additional-leg"}
        self.document["testData"].append(additional)
        case["testDataIds"].append(additional["id"])
        for row in self.document["semanticAssessments"]:
            if row["id"] == case["id"]:
                report = coverage.gates.load(self.root / row["evidence"]["path"])
                report["artifactSha256"] = coverage.fingerprint(case)
                row["evidence"] = self.write(row["evidence"]["path"], report)
        metrics = self.assess()["metrics"]
        self.assertEqual(metrics["ruleAdequacy"]["provisional"]["percent"], 66.67)
        self.assertEqual(metrics["semanticTD"]["population"], 13)

    def test_missing_run_response_and_offline_environment_are_rejected(self):
        run, context = self.execution()
        response_path = self.root / run["steps"][0]["response"]["path"]
        response_path.unlink()
        with self.assertRaisesRegex(ValueError, "Missing evidence"):
            self.assess()
        run["steps"][0]["response"] = self.write("run/response.json", {"outcome": "APPROVED"})
        run["environment"] = self.write("run/environment.json", {"id": "SYNTHETIC-ENV", "mode": "OFFLINE"})
        self.attest(run, context)
        with self.assertRaisesRegex(ValueError, "not E2E"):
            self.assess()

    def test_zero_applicable_rules_is_null_not_one_hundred_percent(self):
        for rule in self.document["requirements"]:
            rule["applicability"] = "EXCLUDED"
        self.review_all()
        metric = self.assess()["metrics"]["scenarioCompleteness"]
        self.assertEqual(metric["denominator"], 0)
        self.assertIsNone(metric["percent"])

    def test_pilot_predicates_handle_count_conditions_and_unknowns(self):
        standard = {"profile": "STANDARD_FINANCIAL", "segments": ["100"], "numberOfSegments": "1"}
        self.assertEqual(pilot.evaluate("COUNT-63", standard), "PASS")
        self.assertEqual(pilot.evaluate("COUNT-63", {**standard, "numberOfSegments": "001"}), "FAIL")
        self.assertEqual(pilot.evaluate("COUNT-63", {**standard, "numberOfSegments": "2"}), "FAIL")
        self.assertEqual(pilot.evaluate("EMV-130", standard), "PASS")
        self.assertEqual(pilot.evaluate("EMV-130", {**standard, "profile": "UNKNOWN"}), "REVIEW_REQUIRED")
        with self.assertRaisesRegex(ValueError, "Unimplemented"):
            pilot.evaluate("MISSING", standard)

    def test_output_is_persistent_and_never_overwritten(self):
        result = self.assess()
        target = self.output / "separate-report"
        coverage.publish(result, target)
        self.assertEqual(coverage.gates.load(target / "assessment-results.json"), result)
        self.assertTrue((target / "requirements.csv").is_file())
        with self.assertRaisesRegex(ValueError, "overwrite"):
            coverage.publish(result, target)
        with self.assertRaisesRegex(ValueError, "overwrite"):
            pilot.build(self.output)

    def test_cli_malformed_input_fails_without_success_report(self):
        (self.root / "assessment.json").write_text("{}", encoding="utf-8")
        target = self.output / "invalid-report"
        result = subprocess.run([
            sys.executable, str(Path(coverage.__file__)), "--input", str(self.root),
            "--output", str(target)], capture_output=True, text=True, check=False)
        self.assertEqual(result.returncode, 2)
        self.assertIn("failed", result.stderr)
        self.assertFalse(target.exists())


if __name__ == "__main__":
    unittest.main()
