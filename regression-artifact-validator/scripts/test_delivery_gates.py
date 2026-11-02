import copy
import csv
import json
import subprocess
import sys
import tempfile
import unittest
import zipfile
from pathlib import Path
from unittest.mock import patch

import delivery_gates as gates
import repair_delivery as repair


def fixture(root: Path) -> None:
    for folder in ["requirements_scenarios", "test_cases", "reports", "test_data"]:
        (root / folder).mkdir(parents=True)
    emv = {
        "SegmentType": "130", "SegmentLength": "0040", "CAPublicKeyFileChecksum": "",
        "EMVCardSequenceNumber": "01", "EMVChipDataLength": "015",
        "EMVChipData": "00135F3401019F0206000000001500",
        "EMVAdditionalInformationIndicator": "001",
        "EMVAdditionalInformationLength": "000", "EMVAdditionalInformation": "",
    }
    inline = {
        "Standard Message Data Segment": {"Sequence Number": {"value": "000001"}},
        "EMV Request Data Segment": {
            "EMV Card Sequence Number": {"value": "01"}, "Segment Length": {"value": None},
        },
    }
    tc = {
        "id": "TC-1", "scenario_id": "TS-1", "requirement_id": "BR-1", "name": "Purchase",
        "build_id": "build-1", "status": "needs_client_input", "request": inline,
        "tx_legs": [{"role": "purchase"}], "template_id": "T1",
        "verification": {"status": "PASS"}, "fingerprint": "producer-hash",
        "test_data_file": "test_data/TD-1.json", "awaiting_inputs": ["client simulator"],
    }
    (root / "test_cases" / "test_cases.jsonl").write_text(json.dumps(tc) + "\n", encoding="utf-8")
    repair.write_json(root / "test_data" / "TD-1.json", {"EMV Financial Transaction Request": {
        "MessageFormatVersionIdentifier": "ATL105", "NumberofSegments": "02",
        "Standard Message Data Segment": {"SegmentType": "100", "NonfuelAmount": "1500"},
        "EMV Request Data Segment": emv,
    }})
    repair.write_json(root / "requirements_scenarios" / "business_requirements.json", {
        "meta": {"build_id": "build-1", "spec_version": "2026-4"},
        "business_requirements": [{"id": "BR-1", "name": "Scope"}],
    })
    repair.write_json(root / "requirements_scenarios" / "business_scenarios.json", {
        "scenarios": [{"id": "TS-1", "business_requirement_id": "BR-1", "name": "Scenario"}],
    })
    repair.write_json(root / "run_set.json", {
        "build_id": "build-1", "files": 1, "test_cases": 1, "index": [{
            "id": "TC-1", "status": "needs_client_input", "test_data_files": ["TD-1.json"],
        }],
    })
    repair.write_json(root / "reports" / "traceability_matrix_full.json", {
        "build": {"build_id": "build-1"}, "summary": {"full_chain_coverage_percent": 100},
        "requirements": [{"scenarios": [{"test_cases": [{
            "test_case_id": "TC-1", "test_data": {
                "test_data_file": "qe_shaped_test_data/TD-1.json", "file_exists": True,
            },
        }]}]}],
        "flat_rows": [{"test_case_id": "TC-1", "test_data_file": "qe_shaped_test_data/TD-1.json"}],
    })
    with (root / "test_cases" / "test_cases.csv").open("w", newline="", encoding="utf-8") as stream:
        writer = csv.DictWriter(stream, fieldnames=["tc_id", "status", "verification"])
        writer.writeheader()
        writer.writerow({"tc_id": "TC-1", "status": "needs_client_input", "verification": "PASS"})
    (root / "README.md").write_text("Obsolete build", encoding="utf-8")
    with zipfile.ZipFile(root / "old.zip", "w") as archive:
        archive.writestr("test_cases/test_cases.jsonl", json.dumps(dict(tc, build_id="old")))


class DeliveryRegressionTest(unittest.TestCase):
    def setUp(self):
        self.temporary = tempfile.TemporaryDirectory()
        self.addCleanup(self.temporary.cleanup)
        self.base = Path(self.temporary.name)
        self.root = self.base / "package"
        fixture(self.root)

    def codes(self):
        return gates.validate_package(self.root)["issue_counts"]

    def test_original_defects_reproduce(self):
        codes = self.codes()
        self.assertEqual(codes["ELEMENT_188_WIDTH"], 1)
        self.assertEqual(codes["TRACE_PATH"], 2)
        self.assertEqual(codes["TRACE_EXISTS"], 1)
        self.assertIn("STALE_ARCHIVE", codes)
        self.assertIn("STALE_README", codes)

    def test_repair_clears_deterministic_gates_but_never_certifies(self):
        before = gates.inventory(self.root)
        completed = subprocess.run([
            sys.executable, str(Path(repair.__file__)), "--input", str(self.root),
            "--output", str(self.base / "corrected"),
        ], capture_output=True, text=True)
        self.assertEqual(completed.returncode, 0, completed.stderr)
        self.assertEqual(gates.inventory(self.root), before)
        corrected = self.base / "corrected" / self.root.name
        report = gates.validate_package(corrected)
        self.assertEqual(report["gate"], "PASS", report["issues"])
        self.assertEqual(report["execution_readiness"], "BLOCKED")
        self.assertEqual(report["semantic_certification"], "NOT_CERTIFIED")
        tc = gates.read_cases(corrected)[0]
        self.assertEqual(tc["verification"]["status"], "NOT_ASSESSED")
        self.assertEqual(tc["producer_original_verification"]["status"], "PASS")
        self.assertIsNone(tc["fingerprint"])
        self.assertEqual(tc["producer_original_hashes"]["fingerprint"], "producer-hash")
        segment = gates.load(corrected / "test_data" / "TD-1.json")
        segment = next(iter(segment.values()))["EMV Request Data Segment"]
        self.assertEqual(segment["EMVCardSequenceNumber"], "001")
        self.assertEqual(segment["SegmentLength"], "0041")
        self.assertTrue((self.base / "corrected" / "repair-evidence" / "package" / "old.zip").is_file())

    def test_repaired_packet_mutations_are_independently_rejected(self):
        repair.repair_package(self.root)
        path = self.root / "test_data" / "TD-1.json"
        wire = gates.load(path)
        body = next(iter(wire.values()))
        body["EMV Request Data Segment"]["SegmentLength"] = "0040"
        repair.write_json(path, wire)
        self.assertIn("EMV_SEGMENT_LENGTH", self.codes())
        body["EMV Request Data Segment"]["SegmentLength"] = "0041"
        body["EMV Request Data Segment"]["EMVCardSequenceNumber"] = "002"
        repair.write_json(path, wire)
        self.assertIn("ELEMENT_188_TAG", self.codes())

    def test_card_sequence_is_numeric_bcd_not_hex_integer(self):
        path = self.root / "test_data" / "TD-1.json"
        segment = next(iter(gates.load(path).values()))["EMV Request Data Segment"]
        segment["EMVChipData"] = segment["EMVChipData"].replace("5F340101", "5F340110")
        segment["EMVCardSequenceNumber"] = "10"
        self.assertTrue(repair.repair_emv(segment))
        self.assertEqual(segment["EMVCardSequenceNumber"], "010")

    def test_refuses_conflicting_card_data_and_unknown_layout(self):
        segment = next(iter(gates.load(self.root / "test_data" / "TD-1.json").values()))["EMV Request Data Segment"]
        original = copy.deepcopy(segment)
        segment["EMVCardSequenceNumber"] = "02"
        with self.assertRaisesRegex(ValueError, "Conflicting"):
            repair.repair_emv(segment)
        original["UnknownRepeat"] = ""
        with self.assertRaisesRegex(ValueError, "Unsupported"):
            repair.repair_emv(original)

    def test_reference_escape_windows_and_posix_rejected(self):
        for ref in ["../secret", "..\\secret", "C:\\secret", "\\\\host\\secret", "/secret", "test_data/x:stream"]:
            with self.subTest(reference=ref), self.assertRaises(ValueError):
                gates.resolve_reference(self.root, ref)

    def test_strict_json_rejects_duplicate_keys_and_nan(self):
        for text in ['{"id":1,"id":2}', '{"amount": NaN}']:
            with self.assertRaises(ValueError):
                gates.parse_json(text, "fixture")

    def test_source_hash_change_blocks_validation(self):
        source = self.base / "changed-source.txt"
        source.write_text("not the approved ATL105 extraction", encoding="utf-8")
        with patch.object(gates, "SOURCE", source):
            with self.assertRaisesRegex(ValueError, "source changed"):
                gates.trusted_source()

    def test_emv_length_rejects_non_bcd_or_truncated_data(self):
        for value in ["000A9C0100", "00049F020601", "00039C010"]:
            with self.assertRaises(ValueError):
                gates.emv_tags(value)

    def test_ready_input_gate_survives_repair(self):
        path = self.root / "test_cases" / "test_cases.jsonl"
        tc = gates.read_cases(self.root)[0]
        tc["status"] = "ready"
        path.write_text(json.dumps(tc) + "\n", encoding="utf-8")
        self.assertIn("READY_INPUTS", self.codes())
        tc["awaiting_inputs"] = []
        tc["flags"] = ["AWAITING_CLIENT_VALUE: Encrypted PIN Block Data required on step 1"]
        path.write_text(json.dumps(tc) + "\n", encoding="utf-8")
        self.assertIn("READY_REQUIRED_PIN", self.codes())

    def test_trace_mutations_and_coverage_inflation_fail(self):
        repair.repair_package(self.root)
        path = self.root / "reports" / "traceability_matrix_full.json"
        trace = gates.load(path)
        trace["flat_rows"][0]["test_data_file"] = "test_data/absent.json"
        trace["requirements"][0]["scenarios"][0]["test_cases"][0]["test_data"]["test_data_files"] = []
        trace["summary"]["semantic_coverage_percent"] = 100
        trace["summary"]["scenarios_with_test_cases"] = 99
        repair.write_json(path, trace)
        codes = self.codes()
        for code in ["TRACE_PATH", "TRACE_POINTER", "TRACE_ALL_LEGS",
                     "TRACE_COUNTS", "SEMANTIC_COVERAGE_UNPROVEN"]:
            self.assertIn(code, codes)

    def test_dynamic_amount_is_not_repaired_into_literal_success(self):
        path = self.root / "test_data" / "TD-1.json"
        wire = gates.load(path)
        next(iter(wire.values()))["Standard Message Data Segment"]["NonfuelAmount"] = "<RemainingBalance>"
        repair.write_json(path, wire)
        repair.repair_package(self.root)
        self.assertIn("EMV_RUNTIME_AMOUNT", self.codes())
        self.assertEqual(next(iter(gates.load(path).values()))["Standard Message Data Segment"]["NonfuelAmount"],
                         "<RemainingBalance>")

    def test_split_tender_mutations_fail(self):
        path = self.root / "test_cases" / "test_cases.jsonl"
        tc = gates.read_cases(self.root)[0]
        tc["steps"] = [dict(request=copy.deepcopy(tc["request"]), role="purchase"),
                       dict(request=copy.deepcopy(tc["request"]), role="split_tender")]
        tc["tx_legs"] = [{"role": "purchase"}, {"role": "split_tender"}]
        tc["test_data_leg_files"] = ["test_data/TD-1.json", "test_data/TD-2.json"]
        path.write_text(json.dumps(tc) + "\n", encoding="utf-8")
        run = gates.load(self.root / "run_set.json")
        run["files"] = 2
        run["index"][0]["test_data_files"].append("TD-2.json")
        repair.write_json(self.root / "run_set.json", run)
        wire = gates.load(self.root / "test_data" / "TD-1.json")
        standard = next(iter(wire.values()))["Standard Message Data Segment"]
        standard["AccountNumber"] = "synthetic-card-A"
        repair.write_json(self.root / "test_data" / "TD-1.json", wire)
        standard["ApprovalNumber"] = "FIRST"
        repair.write_json(self.root / "test_data" / "TD-2.json", wire)
        for code in ["SPLIT_APPROVAL", "SPLIT_ACCOUNT"]:
            self.assertIn(code, self.codes())

    def test_historical_id_needs_build_context(self):
        previous = self.base / "previous"
        fixture(previous)
        path = previous / "test_cases" / "test_cases.jsonl"
        tc = gates.read_cases(previous)[0]
        tc["name"] = "Different old objective"
        path.write_text(json.dumps(tc) + "\n", encoding="utf-8")
        self.assertIn("HISTORICAL_ID", gates.validate_package(self.root, previous)["issue_counts"])
        repair.repair_package(self.root)
        self.assertNotIn("HISTORICAL_ID", gates.validate_package(self.root, previous)["issue_counts"])

    def test_absent_card_sequence_tag_requires_spaces(self):
        path = self.root / "test_data" / "TD-1.json"
        segment = next(iter(gates.load(path).values()))["EMV Request Data Segment"]
        segment["EMVChipData"] = "00099F0206000000001500"
        segment["EMVChipDataLength"] = "011"
        segment["EMVCardSequenceNumber"] = ""
        segment["SegmentLength"] = "0034"
        self.assertTrue(repair.repair_emv(segment))
        self.assertEqual(segment["EMVCardSequenceNumber"], "   ")
        self.assertEqual(segment["SegmentLength"], "0037")

    def test_cli_fail_exit_and_manifest(self):
        result = subprocess.run([
            sys.executable, str(Path(gates.__file__)), "--input", str(self.root),
            "--output", str(self.base / "gate"),
        ], capture_output=True, text=True)
        self.assertEqual(result.returncode, 1, result.stderr)
        self.assertEqual(gates.load(self.base / "gate" / "validation.json")["gate"], "FAIL")
        self.assertTrue((self.base / "gate" / "input-manifest.json").exists())

    def test_repair_refuses_overwrite_and_input_descendant(self):
        for output in [self.root, self.root / "nested"]:
            result = subprocess.run([
                sys.executable, str(Path(repair.__file__)), "--input", str(self.root),
                "--output", str(output),
            ], capture_output=True, text=True)
            self.assertNotEqual(result.returncode, 0)
            self.assertIn("non-overlapping", result.stderr)


if __name__ == "__main__":
    unittest.main()
