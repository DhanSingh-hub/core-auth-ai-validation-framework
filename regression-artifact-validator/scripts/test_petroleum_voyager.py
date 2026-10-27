import importlib.util
import json
import sys
import tempfile
import unittest
from pathlib import Path


SCRIPTS = Path(__file__).resolve().parent
sys.path.insert(0, str(SCRIPTS))
SPEC = importlib.util.spec_from_file_location("petroleum_context_training", SCRIPTS / "train-petroleum-context.py")
train = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(train)
voyager = train.PROGRAMS["VOYAGER_FLEET"]
CONTEXT = train.KB / "voyager-fleet"


class PetroleumVoyagerTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.outputs = train.build("voyager-fleet")
        cls.package = json.loads(cls.outputs["training-package.json"])
        cls.chain = json.loads(cls.outputs["chain-package.json"])
        cls.input = json.loads((CONTEXT / "context-input.json").read_text(encoding="utf-8"))
        cls.sources = train.load_sources()

    def test_chapter_nine_and_document_local_appendix_b_are_covered(self):
        self.assertEqual(self.package["contextId"], "VOYAGER_FLEET")
        self.assertEqual(self.package["scope"]["chapter"], "9")
        self.assertEqual(self.package["scope"]["pdfPageRange"], [72, 92])
        coverage = self.package["bookmarkCoverage"]
        self.assertEqual(coverage["expected"], 41)
        self.assertEqual(coverage["covered"], 41)
        self.assertIn("Appendix B. Valid Payment Systems Product Codes", coverage["appendices"])
        self.assertEqual(len(self.package["inventory"]), 60)

    def test_all_rule_items_have_reviewed_reconciliation_questions(self):
        rule_ids = {i["id"] for i in self.package["inventory"] if i["classification"] == "RULE"}
        reconciled_ids = {i["inventoryId"] for r in self.package["reconciliation"] for i in r["petroleum"]}
        self.assertLessEqual(rule_ids, reconciled_ids)
        for item in self.package["reconciliation"]:
            self.assertTrue(item["reviewRequired"], item["id"])
            self.assertTrue(item["question"], item["id"])

    def test_voyager_specific_catalog_is_source_grounded(self):
        catalog = self.package["catalog"]
        self.assertEqual(catalog["accountPrefixes"]["values"], ["85", "86", "87", "88", "89"])
        self.assertEqual(catalog["enumerations"]["promptRestrictionCode"]["values"].keys(), {"0", "1", "2", "3"})
        self.assertEqual(catalog["enumerations"]["fuelRestrictionCode"]["values"].keys(), {"0", "1"})
        self.assertEqual([len(x["fields"]) for x in catalog["trackLayouts"].values()], [14, 11])
        self.assertEqual([x["totalLength"] for x in catalog["trackLayouts"].values()], [68, 39])
        self.assertEqual(catalog["productCodes"]["rowCount"], 377)
        self.assertEqual(catalog["productCodes"]["atl105Comparison"]["2026-3"]["onlyAtl105"], ["541-549"])
        self.assertEqual(catalog["productCodes"]["atl105Comparison"]["2026-4"]["onlyAtl105"], ["541-549"])

    def test_emv_rules_and_known_source_gaps_are_explicit(self):
        catalog = self.package["catalog"]
        self.assertEqual(catalog["emvEvidence"]["serviceCodeSelection"]["pdfPage"], 76)
        self.assertIn("A0000000049999C00016", catalog["emvEvidence"]["aid"]["quote"])
        self.assertEqual(catalog["emvEvidence"]["offlineLimits"]["pdfPage"], 87)
        questions = {q["id"]: q for q in self.package["openQuestions"]}
        self.assertIn("Voyager EMV terminal Specifications v1.5", questions["VYR-Q-EMV-SPEC"]["question"])
        self.assertIn("Appendices I-L", questions["VYR-Q-APPENDICES"]["question"])

    def test_source_examples_remain_review_required(self):
        examples = self.package["catalog"]["exampleChecks"]
        self.assertEqual({e["layout"] for e in examples}, {"TRACK_1", "TRACK_2"})
        self.assertTrue(all(e["status"] == "REVIEW_REQUIRED" for e in examples))

    def test_phase_gates_are_draft_only(self):
        self.assertEqual(self.package["status"], "DRAFT_REVIEW_REQUIRED")
        self.assertFalse(self.package["certified"])
        self.assertFalse(self.package["executionReady"])
        self.assertFalse(self.package["modelWeightsChanged"])
        self.assertEqual(self.package["draftCounts"], {
            "draftBusinessRequirements": 35,
            "draftTestScenarios": 53,
            "draftTestCases": 53,
            "draftTestData": 53,
            "mutationsCaught": 78,
        })
        self.assertTrue(all(
            self.package["phaseProgress"][phase]["status"] == "DRAFTED_PENDING_SME_REVIEW"
            for phase in range(4, 8)
        ))
        self.assertEqual(self.package["phaseProgress"][8]["status"], "PARTIAL_ATL105_SERIALIZATION_BLOCKED")
        self.assertEqual(len(self.chain["businessRequirements"]), 35)
        self.assertEqual(len(self.chain["testScenarios"]), 53)
        self.assertEqual(len(self.chain["testCases"]), 53)
        self.assertEqual(len(self.chain["testData"]), 53)
        self.assertEqual(self.chain["mutationSuite"]["caught"], 78)
        self.assertEqual(self.chain["mutationSuite"]["total"], 78)
        self.assertEqual(self.chain["atl105Serialization"]["status"], "BLOCKED")
        self.assertEqual(self.chain["atl105Serialization"]["referenceCandidate"],
                         "atl105-voyager-wire-shape-candidate-v2.json")
        self.assertIn("not output from the producer converter", self.chain["atl105Serialization"]["reason"])
        self.assertIn("[atl105-voyager-wire-shape-candidate-v2.json](atl105-voyager-wire-shape-candidate-v2.json)",
                      self.outputs["training-report.md"])
        self.assertNotIn("does not compile", self.chain["atl105Serialization"]["reason"])
        self.assertEqual(len(self.chain["flows"]["transitions"]), 10)
        status = json.loads(train.STATUS_FILE.read_text(encoding="utf-8"))
        entry = status["supplementalSourceTraining"]["PETROLEUM_2025_1"]["contextTraining"]["VOYAGER_FLEET"]
        self.assertEqual(entry["gates"], self.package["frameworkGates"])
        self.assertEqual(entry["phaseStatus"], {
            str(phase["phase"]): phase["status"] for phase in self.package["phaseProgress"]
        })
        self.assertEqual(entry["draftCounts"], {
            "businessRequirements": 35,
            "testScenarios": 53,
            "testCases": 53,
            "testData": 53,
            "mutationsCaught": 78,
        })
        self.assertFalse(entry["executionCertified"])

    def test_stale_outputs_are_detected_and_published_outputs_match(self):
        with tempfile.TemporaryDirectory() as directory:
            folder = Path(directory)
            for name, text in self.outputs.items():
                (folder / name).write_text(text, encoding="utf-8")
            train.check_outputs(self.outputs, folder)
            (folder / "training-report.md").write_text("edited", encoding="utf-8")
            with self.assertRaisesRegex(ValueError, "training-report.md"):
                train.check_outputs(self.outputs, folder)
        train.check_outputs(self.outputs, CONTEXT)

    def test_task_registration_points_to_existing_scripts(self):
        tasks = {item["name"]: item for item in json.loads(
            (train.MODULE / "framework-tasks.json").read_text(encoding="utf-8"))["tasks"]}
        for name in ("petroleum-voyager-train-build", "petroleum-voyager-train-check", "petroleum-voyager-train-test"):
            self.assertIn(name, tasks)
            self.assertTrue((train.MODULE / tasks[name]["script"]).is_file())


if __name__ == "__main__":
    unittest.main()
