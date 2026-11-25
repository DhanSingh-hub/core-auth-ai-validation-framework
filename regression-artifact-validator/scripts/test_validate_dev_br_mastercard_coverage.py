import copy
import importlib.util
import io
import json
import shutil
import tempfile
import unittest
from collections import Counter
from pathlib import Path
from unittest.mock import patch

import openpyxl


SCRIPT = Path(__file__).with_name("validate-dev-br-mastercard-coverage.py")
SPEC = importlib.util.spec_from_file_location("dev_br_mastercard_coverage", SCRIPT)
assert SPEC is not None and SPEC.loader is not None
validation = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(validation)


class DevBrMastercardCoverageTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.report, cls.outputs, cls.xlsx = validation.build()
        cls.curated = json.loads(validation.read_text(validation.CURATED))
        cls.checks = {row["id"]: row for row in cls.report["structuralChecks"]}
        cls.dev = {row["id"]: row for row in cls.report["devScenarios"]}
        cls.spec = {(row["id"], row["program"]): row for row in cls.report["specCoverage"]}

    def delivery(self, md_text=None):
        md_text = md_text if md_text is not None else validation.read_text(validation.DEV_MD)
        csv_text = validation.read_text(validation.DEV_CSV)
        return validation.Delivery(md_text, csv_text, validation.parse_dev_md(md_text),
                                   validation.parse_dev_csv(csv_text))

    def test_delivery_structure_reconciles_and_universe_closes(self):
        self.assertEqual({cid for cid, row in self.checks.items() if row["result"] == "PASS"},
                         {f"S{n:02d}" for n in range(1, 11)})
        self.assertEqual({cid for cid, row in self.checks.items() if row["result"] == "WARN"}, {"S11", "S12", "S13", "S14"})
        self.assertIn("108 scenarios + 212 exclusions; overlap 0, missing 0", self.checks["S05"]["actual"])
        summary = self.report["devDelivery"]["summary"]
        self.assertEqual((summary["Scenarios"], summary["excluded"], summary["supporting rules"]), ("108", "212", "785"))
        self.assertEqual(self.report["devDelivery"]["programs"], {"Credit": 68, "Fleet": 40})

    def test_every_inventory_quote_resolves_on_its_2026_4_page(self):
        self.assertEqual(len(self.curated["specInventory"]), 78)
        self.assertEqual(len(self.report["specCoverage"]), 103)
        pages, _ = validation.load_spec(validation.SPEC_V4)
        broken = copy.deepcopy(self.curated["specInventory"][0])
        broken["sources"][0]["page"] = 1
        with self.assertRaises(ValueError):
            validation.verify_sources(broken, pages, pages)

    def test_atl105_coverage_by_program(self):
        summary = self.report["specSummary"]
        credit, fleet = summary["CREDIT"]["overall"], summary["FLEET"]["overall"]
        self.assertEqual((credit["applicable"], credit["COVERED"], credit["PARTIAL"], credit["NOT_COVERED"]), (74, 45, 5, 18))
        self.assertEqual((fleet["applicable"], fleet["COVERED"], fleet["CONFLICT"], fleet["EXTERNAL_SOURCE"]), (28, 19, 1, 1))
        self.assertEqual(summary["CREDIT"]["groups"]["CREDIT_TRANSACTION_TYPES"]["COVERED"], 12)
        self.assertEqual(summary["CREDIT"]["groups"]["MASTERCARD_SPECIFIC"]["COVERED"], 0)

    def test_known_gaps_and_defects_are_detected(self):
        self.assertEqual(self.spec[("SPEC-TXN-11", "CREDIT")]["disposition"], "NOT_COVERED")
        self.assertEqual(self.spec[("SPEC-TXN-12", "CREDIT")]["disposition"], "COVERED")
        approval = self.spec[("SPEC-FLOW-02", "CREDIT")]
        self.assertEqual(approval["disposition"], "PARTIAL")
        self.assertIn("Approval Number not carried in 8 scenario(s)", approval["detail"])
        self.assertEqual(self.spec[("SPEC-FLOW-04", "CREDIT")]["disposition"], "PARTIAL")
        self.assertEqual(self.spec[("SPEC-FLOW-07", "CREDIT")]["disposition"], "PARTIAL")
        self.assertEqual(self.spec[("SPEC-ENTRY-15", "FLEET")]["disposition"], "CONFLICT")
        self.assertEqual(self.spec[("SPEC-ENTRY-03", "CREDIT")]["disposition"], "NOT_COVERED")

    def test_independent_scenario_verdicts(self):
        counts = Counter((row["program"], row["agreement"]) for row in self.report["devScenarios"])
        self.assertEqual(counts, Counter({("Credit", "AGREE"): 19, ("Credit", "ATL105_PERMITS_PENDING_SME"): 49,
                                          ("Fleet", "UNVERIFIABLE_EXTERNAL_SOURCE"): 35, ("Fleet", "CONFLICT"): 5}))
        conflicts = {sid for sid, row in self.dev.items() if row["independentVerdict"] == "CONFLICT"}
        self.assertTrue(all(self.dev[sid]["entryCodes"] == "10" and self.dev[sid]["program"] == "Fleet" for sid in conflicts))
        self.assertIn("Approval Number (Element 5) not carried", self.dev["BR1_TS53"]["issues"])
        self.assertEqual(len(self.dev["BR1_TS1"]["trainedLinks"]), 24)

    def test_exclusions_and_versions(self):
        verdicts = Counter((row["program"], row["verdict"]) for row in self.report["exclusions"])
        self.assertEqual(verdicts[("Credit", "CONSISTENT_WITH_ATL105")], 92)
        self.assertEqual(verdicts[("Fleet", "EXTERNAL_UNVERIFIED")], 82)
        self.assertNotIn("INCONSISTENT_WITH_ATL105", {row["verdict"] for row in self.report["exclusions"]})
        self.assertTrue(all(row["identical"] for row in self.report["versionChecks"]))
        self.assertEqual({row["rule"] for row in self.report["ruleReferences"]
                          if row["verdict"].startswith("2026-3")}, {"BR-402-1", "BR-402-3", "BR-38-9", "BR-38-4", "BR-97-7"})
        statuses = Counter(row["status"] for row in self.report["citations"])
        self.assertEqual(statuses["EXTERNAL_SOURCE_UNAVAILABLE"], 7)
        self.assertEqual(statuses["PAGE_MISMATCH"] + statuses["NOT_FOUND"], 0)

    def test_trained_solution_coverage(self):
        trained = self.report["trainedSummary"]
        scenarios, brs = trained["businessScenarios"], trained["businessRequirements"]
        self.assertEqual((scenarios["total"], scenarios["applicable"], scenarios["COVERED"], scenarios["PARTIAL"]),
                         (134, 132, 20, 8))
        self.assertEqual((brs["total"], brs["applicable"], brs["COVERED"], brs["PARTIAL"]), (189, 183, 33, 7))
        self.assertEqual(trained["contextualBusinessRequirements"]["applicable"], 17)
        self.assertEqual(trained["functionalRequirements"]["FR-006"]["brs"]["COVERED"], 0)
        self.assertTrue(all(row["devScenarios"] for row in self.report["trainedBrs"]
                            if row["devDisposition"] in ("COVERED", "PARTIAL")))

    def test_absence_claims_and_evidence_are_enforced(self):
        entry = {"disposition": "NOT_COVERED", "reason": "R-PARTIAL-APPROVAL"}
        self.assertEqual(validation.apply_curated(self.curated, self.delivery(), entry, "ok")["disposition"], "NOT_COVERED")
        tampered = self.delivery(validation.read_text(validation.DEV_MD) + "\nPartial Approval is supported.\n")
        with self.assertRaises(ValueError):
            validation.apply_curated(self.curated, tampered, entry, "absence")
        claim = {"disposition": "COVERED", "reason": "x", "evidence": [{"file": "md", "pattern": "NO SUCH TEXT"}]}
        with self.assertRaises(ValueError):
            validation.apply_curated(self.curated, self.delivery(), claim, "evidence")
        with self.assertRaises(ValueError):
            validation.apply_curated(self.curated, self.delivery(), {"disposition": "COVERED", "reason": "x"}, "none")

    def test_changed_delivery_requires_re_review(self):
        with tempfile.TemporaryDirectory() as folder:
            copy_path = Path(folder) / "BUSINESS_REQUIREMENTS.md"
            shutil.copyfile(validation.long_path(validation.DEV_MD), copy_path)
            with open(copy_path, "a", encoding="utf-8") as handle:
                handle.write("\n")
            with patch.object(validation, "DEV_MD", copy_path):
                with self.assertRaises(SystemExit):
                    validation.build_report()

    def test_findings_outcome_and_outputs(self):
        severities = [row["severity"] for row in self.report["findings"]]
        self.assertEqual(severities, sorted(severities, key=("HIGH", "MEDIUM", "LOW", "INFO").index))
        self.assertEqual(self.report["outcome"], "REJECTED_NOT_INTAKE_READY")
        self.assertEqual(self.report["status"], "DRAFT_REVIEW_REQUIRED")
        titles = {row["title"] for row in self.report["findings"]}
        self.assertIn("Partial Approval is not modelled", titles)
        self.assertIn("Authorization Only Reversal (Cancellation S) does not carry the Approval Number", titles)
        self.assertEqual(validation.cell("=1+1"), "'=1+1")
        stale = [name for name, text in self.outputs.items()
                 if validation.read_bytes(validation.OUT_DIR / name).decode("utf-8") != text]
        self.assertEqual(stale, [], "Run: python scripts/validate-dev-br-mastercard-coverage.py")
        workbook = openpyxl.load_workbook(io.BytesIO(self.xlsx))
        self.assertEqual(workbook.sheetnames[0], "Summary")
        self.assertIn("Trained BRs", workbook.sheetnames)
        self.assertEqual(workbook["Dev Scenarios"].max_row, 109)


if __name__ == "__main__":
    unittest.main()
