import copy
import importlib.util
import json
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path


SCRIPT = Path(__file__).with_name("new-format-sample.py")
SPEC = importlib.util.spec_from_file_location("new_format_sample", SCRIPT)
assert SPEC is not None and SPEC.loader is not None
review = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(review)


class NewFormatSampleTest(unittest.TestCase):
    def setUp(self):
        self.document = review.sample()

    def test_concrete_sample_counts_and_local_results(self):
        report, normalized = review.validate(self.document)
        self.assertEqual(report["counts"], {"businessRequirements": 1, "testScenarios": 2,
                                          "testCases": 6, "testData": 6})
        self.assertEqual(report["assessedTdCount"], 6)
        self.assertEqual(report["mismatchedExpectationCount"], 0)
        self.assertEqual(report["testExpectationAgreement"], "PASS")
        self.assertEqual([row["observedFieldValidation"] for row in report["observations"]],
                         ["PASS", "PASS", "FAIL", "FAIL", "FAIL", "FAIL"])
        for key in ("businessRequirements", "testScenarios", "testCases", "testData"):
            self.assertEqual(normalized[key], self.document[key])

    def test_not_execution_or_semantic_certification(self):
        report, normalized = review.validate(self.document)
        self.assertEqual(report["overallStatus"], "REVIEW_REQUIRED")
        self.assertEqual(report["networkEligibility"], "REVIEW_REQUIRED")
        for key in ("semanticProseEquivalence", "completeMessageAndWire", "hostOutcome"):
            self.assertEqual(report[key], "NOT_ASSESSED")
        self.assertFalse(report["coveragePromotion"])
        self.assertFalse(report["certified"])
        self.assertFalse(normalized["adapter"]["canonicalExecutionReady"])
        self.assertTrue(report["independentSourceEvidence"]["sequence"]["checkedQuotesWithinElement86"])

    def test_producer_local_ids_and_producer_name_do_not_supply_the_oracle(self):
        self.document["manifest"]["producer"] = "AI_SOLUTION"
        self.document["businessRequirements"][0]["id"] = "BR-AI-999"
        for row in self.document["testScenarios"]:
            row["requirementIds"] = ["BR-AI-999"]
        for row in self.document["testCases"]:
            row["assertions"][0]["requirementId"] = "BR-AI-999"
        self.assertEqual(review.validate(self.document)[0]["testExpectationAgreement"], "PASS")

    def test_wrong_expected_results_are_detected(self):
        case = self.document["testCases"][2]
        case["expectedOutcome"] = "PASS"
        case["assertions"][0]["expectedResult"] = "PASS"
        self.document["testData"][2]["expectedValidation"] = "PASS"
        report, _ = review.validate(self.document)
        self.assertEqual(report["testExpectationAgreement"], "FAIL")
        self.assertEqual(report["mismatchedExpectationCount"], 1)

    def test_wrong_actual_payload_is_detected(self):
        self.document["testData"][0]["payload"]["standardSegment"]["sequenceNumber"] = "000000"
        self.assertEqual(review.validate(self.document)[0]["testExpectationAgreement"], "FAIL")

    def test_exact_range_ascii_width_and_types(self):
        for value, expected in (("000001", True), ("999999", True), ("000000", False),
                                ("1000000", False), ("12345", False), ("12A456", False),
                                ("123456\n", False), ("１２３４５６", False),
                                (123456, False), (None, False), (True, False)):
            self.assertEqual(review.valid_sequence({"standardSegment": {"sequenceNumber": value}}), expected)
        self.assertFalse(review.valid_sequence({"standardSegment": {}}))

    def test_unimplemented_claim_or_anchor_is_not_assessed(self):
        for change in ("claim", "anchor", "assertion"):
            document = copy.deepcopy(self.document)
            if change == "claim":
                document["businessRequirements"][0]["claim"]["minimum"] = 0
            elif change == "anchor":
                for key in ("businessRequirements", "testScenarios", "testCases", "testData"):
                    for row in document[key]:
                        row["sourceAnchors"][0]["element"] = "80"
            else:
                document["testCases"][0]["assertions"][0]["rule"] = "producer-chosen-rule"
            report, _ = review.validate(document)
            self.assertEqual(report["testExpectationAgreement"], "NOT_ASSESSED")
            self.assertEqual(report["assessedTdCount"], 0)

    def test_unknown_message_or_flow_context_is_not_assessed(self):
        for changes in ({"flowId": "AUTH_COMPLETION"}, {"messageFamily": "CAPK_FILE_LOAD"},
                        {"candidateCardCodes": ["079"]}):
            document = copy.deepcopy(self.document)
            for key in ("businessRequirements", "testScenarios", "testCases", "testData"):
                for row in document[key]:
                    row["businessContext"].update(changes)
            self.assertEqual(review.validate(document)[0]["supportedStructuredClaim"], "NOT_ASSESSED")

    def test_broken_links_orphans_duplicates_and_context_conflicts_fail(self):
        mutations = [
            lambda doc: doc["testScenarios"][0].update(requirementIds=["UNKNOWN"]),
            lambda doc: doc["testCases"][0].update(scenarioIds=["UNKNOWN"]),
            lambda doc: doc["testData"][0].update(testCaseIds=["UNKNOWN"]),
            lambda doc: doc["businessRequirements"].append(copy.deepcopy(doc["businessRequirements"][0])),
            lambda doc: doc["testData"].pop(0),
            lambda doc: doc["testData"][0]["businessContext"].update(network="VISA"),
            lambda doc: doc["testCases"][0].update(sourceAnchors=[]),
            lambda doc: doc["testCases"][0].update(assertions=[]),
            lambda doc: doc["testData"][0].update(expectedValidation="FAIL"),
            lambda doc: doc["testData"][0].update(readiness="EXECUTABLE"),
            lambda doc: doc["testData"][0].update(payload={}),
            lambda doc: doc["testData"][0]["payload"]["standardSegment"].update(approvalNumber="123456"),
            lambda doc: doc["manifest"].update(strictExecutionContract=True),
        ]
        for mutation in mutations:
            document = copy.deepcopy(self.document)
            mutation(document)
            with self.assertRaises(ValueError):
                review.validate(document)

    def test_duplicate_json_keys_fail_and_deterministic_report(self):
        with tempfile.TemporaryDirectory() as temporary:
            path = Path(temporary) / "input.json"
            path.write_text('{"schemaVersion":1,"schemaVersion":2}', encoding="utf-8")
            with self.assertRaisesRegex(ValueError, "Duplicate"):
                review.load(path)
        raw = review.shared.json_text(self.document).encode("utf-8")
        digest = review.shared.fingerprint(raw)
        self.assertEqual(review.outputs(self.document, digest), review.outputs(self.document, digest))
        self.assertEqual(json.loads(review.outputs(self.document, digest)["validation-report.json"])["inputSha256"], digest)

    def test_cli_external_input_is_immutable_and_reports_failure(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            path = root / "ai-input.json"
            raw = b"\xef\xbb\xbf" + review.shared.json_text(self.document).encode("utf-8")
            path.write_bytes(raw)
            output = root / "result"
            result = subprocess.run([sys.executable, str(SCRIPT), "--input", str(path), "--output", str(output)],
                                    capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stderr)
            self.assertEqual(path.read_bytes(), raw)
            self.assertEqual((output / "input-snapshot.json").read_bytes(), raw)
            second = subprocess.run([sys.executable, str(SCRIPT), "--input", str(path), "--output", str(output)],
                                    capture_output=True, text=True)
            self.assertNotEqual(second.returncode, 0)
            self.document["testData"][0]["payload"]["standardSegment"]["sequenceNumber"] = "000000"
            path.write_text(review.shared.json_text(self.document), encoding="utf-8")
            failed = root / "failed"
            result = subprocess.run([sys.executable, str(SCRIPT), "--input", str(path), "--output", str(failed)],
                                    capture_output=True, text=True)
            self.assertEqual(result.returncode, 1, result.stderr)
            report = json.loads((failed / "validation-report.json").read_text())
            self.assertEqual(report["mismatchedExpectationCount"], 1)
            self.document["businessRequirements"][0]["claim"]["minimum"] = 0
            path.write_text(review.shared.json_text(self.document), encoding="utf-8")
            unknown = root / "unassessed"
            result = subprocess.run([sys.executable, str(SCRIPT), "--input", str(path), "--output", str(unknown)],
                                    capture_output=True, text=True)
            self.assertEqual(result.returncode, 2, result.stderr)
            self.assertEqual(json.loads((unknown / "validation-report.json").read_text())["assessedTdCount"], 0)
            path.write_text('{"schemaVersion":1,"schemaVersion":1}', encoding="utf-8")
            malformed = root / "malformed"
            result = subprocess.run([sys.executable, str(SCRIPT), "--input", str(path), "--output", str(malformed)],
                                    capture_output=True, text=True)
            self.assertEqual(result.returncode, 1)
            self.assertIn("Duplicate JSON key", result.stderr)
            self.assertFalse(malformed.exists())


if __name__ == "__main__":
    unittest.main()
