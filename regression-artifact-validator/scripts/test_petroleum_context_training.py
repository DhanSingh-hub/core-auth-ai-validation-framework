import copy
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch


SCRIPT = Path(__file__).with_name("train-petroleum-context.py")
SPEC = importlib.util.spec_from_file_location("petroleum_context_training", SCRIPT)
train = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(train)
CONTEXT = train.KB / "mastercard-fleet"


class PetroleumContextTrainingTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.outputs = train.build("mastercard-fleet")
        cls.package = json.loads(cls.outputs["training-package.json"])
        cls.config = json.loads((CONTEXT / "context-input.json").read_text(encoding="utf-8"))
        cls.plan = json.loads(train.PLAN.read_text(encoding="utf-8"))
        cls.sources = train.load_sources()

    def build_with(self, mutate):
        config = copy.deepcopy(self.config)
        mutate(config)
        with tempfile.TemporaryDirectory() as directory:
            folder = Path(directory) / "mastercard-fleet"
            folder.mkdir()
            (folder / "context-input.json").write_text(json.dumps(config), encoding="utf-8")
            with patch.object(train, "KB", Path(directory)):
                with patch.object(train, "load_sources", lambda: self.sources):
                    return train.build("mastercard-fleet")

    def test_plan_defines_every_phase_with_steps_and_maps_every_gate_once(self):
        train.validate_plan(self.plan, self.sources)
        self.assertEqual([p["phase"] for p in self.package["phaseProgress"]], list(range(11)))
        self.assertTrue(all(p["steps"] >= 3 for p in self.package["phaseProgress"]))
        self.assertEqual(sorted(self.package["frameworkGates"]), sorted(self.sources["requiredGates"]))
        for mutate in (
            lambda p: p["phases"].pop(),
            lambda p: p["phases"][1].update(frameworkGate="CONTEXT_MATRIX"),
            lambda p: p["phases"][2].update(steps=[]),
            lambda p: p["phases"][3]["steps"][0].update(id="P3.9"),
            lambda p: p["rolloutOrder"][0].update(pdfPageRanges=[[51, 60]]),
            lambda p: p["rolloutOrder"].pop(),
        ):
            plan = copy.deepcopy(self.plan)
            mutate(plan)
            with self.assertRaises(ValueError):
                train.validate_plan(plan, self.sources)

    def test_pilot_progress_is_draft_and_never_promoted(self):
        statuses = {p["phase"]: p["status"] for p in self.package["phaseProgress"]}
        self.assertEqual(statuses[0], "DRAFTED_PENDING_SME_SIGNOFF")
        self.assertEqual({statuses[n] for n in range(1, 8)}, {"DRAFTED_PENDING_SME_REVIEW"})
        self.assertEqual(statuses[8], "PARTIAL_ATL105_SERIALIZATION_BLOCKED")
        self.assertEqual(statuses[9], "BLOCKED_NO_AI_PACKAGE")
        self.assertEqual(statuses[10], "BLOCKED_PENDING_EXECUTION_AND_SME_APPROVAL")
        self.assertNotIn("TRAINED_FOR_INTAKE", self.package["frameworkGates"].values())
        self.assertEqual(self.package["status"], "DRAFT_REVIEW_REQUIRED")
        for key in ("certified", "executionReady", "baselineChanged", "modelWeightsChanged"):
            self.assertFalse(self.package[key])
        for key in ("independentBrCount", "testScenarioCount", "testCaseCount", "testDataCount", "executedCaseCount"):
            self.assertEqual(self.package[key], 0)
        with self.assertRaises(ValueError):
            self.build_with(lambda c: c.update(status="APPROVED"))
        with self.assertRaises(ValueError):
            self.build_with(lambda c: c["approvals"].append({"phase": 1, "decision": "APPROVED"}))

    def test_inventory_covers_every_scoped_bookmark_with_page_local_quotes(self):
        coverage = self.package["bookmarkCoverage"]
        self.assertEqual(coverage["covered"], coverage["expected"])
        self.assertEqual(coverage["expected"], 17)
        for item in self.package["inventory"]:
            page = self.sources["pages"][item["pdfPage"]]
            self.assertIn(train.norm(item["quote"]), train.norm(page["text"]))
            self.assertEqual(item["textSha256"], page["textSha256"])
            self.assertTrue(51 <= item["pdfPage"] <= 57 or 143 <= item["pdfPage"] <= 145 or 148 <= item["pdfPage"] <= 168)
        for mutate in (
            lambda c: c["inventory"].pop(27),
            lambda c: c["inventory"][0].update(quote="INVENTED MASTERCARD FLEET RULE"),
            lambda c: c["inventory"][3].update(pdfPage=58),
            lambda c: c["inventory"].append(copy.deepcopy(c["inventory"][0])),
            lambda c: c["inventory"][0].update(classification="APPROVED_RULE"),
        ):
            with self.assertRaises(ValueError):
                self.build_with(mutate)

    def test_reconciliation_is_evidenced_in_both_releases_and_absences_are_verified(self):
        rules = {i["id"] for i in self.package["inventory"] if i["classification"] == "RULE"}
        referenced = {e["inventoryId"] for r in self.package["reconciliation"] for e in r["petroleum"]}
        self.assertLessEqual(rules, referenced)
        for item in self.package["reconciliation"]:
            if item["disposition"] in train.EVIDENCED:
                self.assertEqual({e["version"] for e in item["atl105"]}, {"2026-3", "2026-4"})
            if item["disposition"] != "ALIGNED":
                self.assertTrue(item["reviewRequired"])
                self.assertTrue(item["question"])
        by_id = {r["id"]: r for r in self.config["reconciliation"]}
        self.assertEqual(by_id["MCF-R03"]["disposition"], "PETROLEUM_ONLY")
        for mutate in (
            lambda c: c["reconciliation"][2]["atl105Absent"].append("Luhn"),
            lambda c: c["reconciliation"][0]["atl105"][0].update(extractedPage=100),
            lambda c: c["reconciliation"][0].update(atl105=c["reconciliation"][0]["atl105"][:2]),
            lambda c: c["reconciliation"][1].update(question=""),
            lambda c: c["reconciliation"].pop(0),
            lambda c: c["reconciliation"][0].update(disposition="CERTIFIED"),
        ):
            with self.assertRaises(ValueError):
                self.build_with(mutate)

    def test_layouts_bins_and_enumerations_are_verified_against_the_source(self):
        catalog = self.package["catalog"]
        self.assertEqual(catalog["trackLayouts"]["TRACK_1"]["totalLength"], 79)
        self.assertEqual(catalog["trackLayouts"]["TRACK_2"]["totalLength"], 40)
        self.assertEqual(catalog["binRanges"]["consistentPages"], [52, 54, 56, 57])
        layouts = ("catalog", "trackLayouts")
        for mutate in (
            lambda c: c["catalog"]["binRanges"]["prefixes"][0].__setitem__(1, "553381"),
            lambda c: c["catalog"]["trackLayouts"]["TRACK_2"]["fields"][2].update(len=11),
            lambda c: c["catalog"]["trackLayouts"]["TRACK_1"]["fields"][9].update(pos=54),
            lambda c: c["catalog"]["enumerations"]["productTypeCode"].update({"6": "Invented"}),
            lambda c: c["catalog"]["promptDataLengths"][0].update(max=7),
        ):
            with self.assertRaises(ValueError):
                self.build_with(mutate)

    def test_example_checks_flag_source_anomalies_without_hiding_them(self):
        examples = {e["layout"]: e for e in self.package["catalog"]["exampleChecks"]}
        self.assertEqual(examples["TRACK_2"]["status"], "PASS")
        track1 = {c["check"]: c for c in examples["TRACK_1"]["checks"]}
        self.assertEqual(examples["TRACK_1"]["status"], "REVIEW_REQUIRED")
        self.assertEqual(track1["suffixLength"]["status"], "REVIEW_REQUIRED")
        self.assertEqual(track1["mod10"]["status"], "PASS")
        self.assertEqual(track1["binRange"]["status"], "PASS")
        self.assertTrue(train.luhn("5567550123456780"))
        self.assertFalse(train.luhn("5567550123456781"))
        layout = self.package["catalog"]["trackLayouts"]["TRACK_2"]
        enums = self.package["catalog"]["enumerations"]
        bins = [tuple(p) for p in self.package["catalog"]["binRanges"]["prefixes"]]
        result = train.check_example("TRACK_2", layout, {"pdfPage": 56, "quote": ";5567550123456780=15037779876543210915?"},
                                     bins, enums, self.sources)
        self.assertEqual(result["status"], "PASS")

    def test_appendix_scope_and_product_codes_are_verified_against_both_sources(self):
        codes = self.package["catalog"]["productCodes"]
        self.assertEqual(codes["rowCount"], 377)
        self.assertIn("001", codes["fuelCodes"])
        self.assertIn("019", codes["fuelCodes"])
        self.assertNotIn("102", codes["fuelCodes"])
        self.assertIn("102", codes["definedCodes"])
        self.assertNotIn("541", codes["definedCodes"])
        for version in ("2026-3", "2026-4"):
            comparison = codes["atl105Comparison"][version]
            self.assertEqual((comparison["appendix"], comparison["shared"], comparison["onlyAtl105"]), ("F", 377, ["541-549"]))
        self.assertEqual(len(self.package["bookmarkCoverage"]["appendices"]), 2)
        for mutate, message in (
            (lambda c: c["inventory"][28].update(pdfPage=146), "outside the context scope"),
            (lambda c: c["scope"]["appendixPages"][0].update(bookmark="Appendix B. Valid Payment Systems Product Codes"), "document-local bookmark"),
            (lambda c: c["scope"]["appendixPages"][1].update(pdfPages=[148, 170]), "crosses its boundary"),
            (lambda c: c.update(inventory=[i for i in c["inventory"] if not i["bookmark"].startswith("Appendix B")],
                                reconciliation=[r for r in c["reconciliation"] if r["id"] != "MCF-R22"]), "without inventory items"),
            (lambda c: c["catalog"]["productCodes"]["expectedDifferences"].update(onlyAtl105=[]), "declared exceptions"),
            (lambda c: c["catalog"]["productCodes"].update(fuelCategories=["Motor fuel codes", "Jet fuel codes"]), "Unknown fuel category"),
            (lambda c: c["catalog"]["productCodes"]["atl105"]["2026-4"].update(pages=[516, 537]), "is not Appendix F"),
            (lambda c: c["reconciliation"][19].update(inventoryRefs=["MCF-I30"]), "both sides"),
        ):
            with self.assertRaisesRegex(ValueError, message):
                self.build_with(mutate)

    def test_stale_or_missing_outputs_are_detected_without_writes(self):
        with tempfile.TemporaryDirectory() as directory:
            folder = Path(directory)
            with self.assertRaisesRegex(ValueError, "Missing or stale"):
                train.check_outputs(self.outputs, folder)
            for name, text in self.outputs.items():
                (folder / name).write_text(text, encoding="utf-8")
            train.check_outputs(self.outputs, folder)
            (folder / "training-report.md").write_text("edited", encoding="utf-8")
            with self.assertRaisesRegex(ValueError, "training-report.md"):
                train.check_outputs(self.outputs, folder)

    def test_stale_petroleum_ingestion_blocks_context_training(self):
        manifest = json.loads(train.MANIFEST.read_text(encoding="utf-8"))
        manifest["outputs"]["pages.json"] = "0" * 64
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / "ingestion-manifest.json"
            path.write_text(json.dumps(manifest), encoding="utf-8")
            with patch.object(train, "MANIFEST", path):
                with self.assertRaisesRegex(ValueError, "stale"):
                    train.load_sources()

    def test_registration_matches_generated_gates(self):
        tasks = {t["name"]: t for t in json.loads((train.MODULE / "framework-tasks.json").read_text(encoding="utf-8"))["tasks"]}
        for name in ("petroleum-train-build", "petroleum-train-check", "petroleum-train-test"):
            self.assertIn(name, tasks)
            self.assertTrue((train.MODULE / tasks[name]["script"]).is_file())
        status = json.loads(train.STATUS_FILE.read_text(encoding="utf-8"))
        entry = status["supplementalSourceTraining"]["PETROLEUM_2025_1"]["contextTraining"]["MASTERCARD_FLEET"]
        self.assertEqual(entry["gates"], self.package["frameworkGates"])
        self.assertEqual(entry["phaseStatus"], {str(p["phase"]): p["status"] for p in self.package["phaseProgress"]})
        for key in ("trainingPlan", "input", "evidence", "report", "generator", "regressionTest"):
            self.assertTrue((train.MODULE / entry[key]).is_file(), key)
        self.assertFalse(entry["executionCertified"])

    def test_published_outputs_match_current_inputs(self):
        train.check_outputs(self.outputs, CONTEXT)


if __name__ == "__main__":
    unittest.main()
