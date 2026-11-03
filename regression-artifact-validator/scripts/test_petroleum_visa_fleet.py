import copy
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
chain_module = train.petroleum_chain
visa = train.PROGRAMS["VISA_FLEET"]
CONTEXT = train.KB / "visa-fleet"


class PetroleumVisaFleetTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.outputs = train.build("visa-fleet")
        cls.package = json.loads(cls.outputs["training-package.json"])
        cls.chain = json.loads(cls.outputs["chain-package.json"])
        cls.input = json.loads((CONTEXT / "chain-input.json").read_text(encoding="utf-8"))
        cls.sources = train.load_sources()
        cls.context = {k: cls.package[k] for k in ("contextId", "scope", "inventory", "reconciliation", "catalog")}

    def rebuild(self, mutate):
        chain = copy.deepcopy(self.input)
        mutate(chain)
        return chain_module.build_chain(chain, self.context, self.sources, train.atl105_evidence, visa.PROGRAM)

    def assertRejected(self, mutate, message):
        with self.assertRaisesRegex(ValueError, message):
            self.rebuild(mutate)

    def test_scope_is_chapter_8_and_every_bookmark_is_covered(self):
        scope = self.package["scope"]
        self.assertEqual(self.package["contextId"], "VISA_FLEET")
        self.assertEqual(scope["chapter"], "8")
        self.assertEqual(scope["pdfPageRange"], [58, 71])
        self.assertEqual(len(scope["supportedTransactions"]["values"]), 6)
        coverage = self.package["bookmarkCoverage"]
        self.assertEqual((coverage["expected"], coverage["covered"]), (18, 18))
        self.assertIn("8.1.9. Service Enhancement Indicator", coverage["bookmarks"])

    def test_bin_list_differences_are_explicit_and_reconciled(self):
        bins = self.package["catalog"]["binRanges"]
        self.assertEqual(len(bins["prefixes"]), 63)
        self.assertEqual(bins["appendixA"]["chapterOnly"], ["448616", "471562", "471595"])
        self.assertIn("448460-448461", bins["appendixA"]["appendixOnly"])
        dispositions = {r["id"]: r["disposition"] for r in self.package["reconciliation"]}
        for rid in ("VFC-R19", "VFC-R20", "VFC-R22"):
            self.assertEqual(dispositions[rid], "POTENTIAL_CONFLICT")
        conflicts = {c["expectedOutcome"]["oracleConflict"] for c in self.chain["testCases"]} - {None}
        self.assertEqual(conflicts, {"VFC-R19", "VFC-R20", "VFC-R22", "VFC-R23"})

    def test_source_examples_stay_review_required(self):
        examples = {e["layout"]: e for e in self.package["catalog"]["exampleChecks"]}
        self.assertEqual(set(examples), {"TRACK_1", "TRACK_2"})
        for example in examples.values():
            self.assertEqual(example["status"], "REVIEW_REQUIRED")
            checks = {c["check"]: c["status"] for c in example["checks"]}
            self.assertEqual(checks["mod10"], "REVIEW_REQUIRED")
            self.assertEqual(checks["binRange"], "REVIEW_REQUIRED")

    def test_matrix_uses_card_type_020_and_service_prompt_digits(self):
        matrix = self.chain["contextMatrix"]
        self.assertEqual([t["code"] for t in matrix["transactions"]], ["0", "3", "7", "8", "4", "5"])
        self.assertEqual({t["promptCodes"]["fleet"] for t in matrix["transactions"]},
                         {c + "020" for c in "037845"})
        statuses = [c["status"] for c in matrix["cells"]]
        self.assertEqual((statuses.count("SUPPORTED"), statuses.count("REVIEW_REQUIRED")), (13, 4))
        self.assertEqual(matrix["appendixAPrompts"]["ODOMETER"]["servicePrompts"], ["1", "2", "3", "4"])
        self.assertRejected(lambda c: c["contextMatrix"]["servicePromptPrompts"].update({"4": []}), "disagree")
        self.assertRejected(lambda c: c["contextMatrix"].update(cardType="081"), "does not quote the code")
        self.assertRejected(lambda c: c["contextMatrix"]["transactions"][2].pop("reviewReason"), "review reason")

    def test_every_chain_is_linked_and_every_mutation_is_caught(self):
        self.assertEqual(len(self.chain["businessRequirements"]), 26)
        for kind in ("testScenarios", "testCases", "testData"):
            self.assertEqual(len(self.chain[kind]), 62)
        for td in self.chain["testData"]:
            self.assertEqual(td["readiness"], "DRAFT_SYNTHETIC_NOT_EXECUTED")
        suite = self.chain["mutationSuite"]
        self.assertEqual(len(suite["operators"]), len(visa.MUTATIONS))
        self.assertEqual(suite["caught"], suite["total"])
        self.assertEqual({m["operator"] for m in suite["results"]}, set(suite["operators"]))
        self.assertRejected(lambda c: c["scenarios"][13].update(expect=[]), "oracle mismatch")
        self.assertRejected(lambda c: c["scenarios"].pop(12), "matrix cells without positive")
        self.assertRejected(lambda c: c["scenarios"][16].update(oracleConflict="VFC-R04"), "not a recorded source conflict")

    def test_validator_reads_service_enhancement_and_prompt_positions(self):
        model = visa.validator_model(self.chain["contextMatrix"], self.package["catalog"])
        defaults = self.input["testDataDefaults"]
        matrix = self.chain["contextMatrix"]
        cases = {
            "fuel only with car wash": ({"serviceEnhancementIndicator": "2", "products": [{"code": "102", "amount": 4500}]}, ["VFC-BR-21"]),
            "maintenance with merchandise": ({"serviceEnhancementIndicator": "1", "products": [{"code": "400", "amount": 4500}]}, ["VFC-BR-22"]),
            "unrequested vehicle": ({"prompts": {"ODOMETER": "1", "VEHICLE_NUMBER": "1"}}, ["VFC-BR-10"]),
            "outside BIN list": ({"bin": "400000", "products": []}, []),
        }
        for name, (data, expected) in cases.items():
            with self.subTest(name):
                actual, _ = visa.validate_test_data(visa.make_test_data(defaults, data, matrix), model)
                self.assertEqual(actual, expected)

    def test_status_stays_draft_and_registration_matches_gates(self):
        self.assertEqual(self.package["status"], "DRAFT_REVIEW_REQUIRED")
        self.assertFalse(any(p["status"] in {"APPROVED", "CERTIFIED"} for p in self.package["phaseProgress"]))
        tasks = {t["name"]: t for t in json.loads((train.MODULE / "framework-tasks.json").read_text(encoding="utf-8"))["tasks"]}
        for name in ("petroleum-visa-train-build", "petroleum-visa-train-check", "petroleum-visa-train-test"):
            self.assertIn(name, tasks)
            self.assertTrue((train.MODULE / tasks[name]["script"]).is_file())
        status = json.loads(train.STATUS_FILE.read_text(encoding="utf-8"))
        entry = status["supplementalSourceTraining"]["PETROLEUM_2025_1"]["contextTraining"]["VISA_FLEET"]
        self.assertEqual(entry["gates"], self.package["frameworkGates"])
        self.assertEqual(entry["phaseStatus"], {str(p["phase"]): p["status"] for p in self.package["phaseProgress"]})
        self.assertFalse(entry["executionCertified"])

    def test_stale_outputs_are_detected_and_published_outputs_are_current(self):
        with tempfile.TemporaryDirectory() as directory:
            folder = Path(directory)
            for name, text in self.outputs.items():
                (folder / name).write_text(text, encoding="utf-8")
            train.check_outputs(self.outputs, folder)
            (folder / "chain-package.json").write_text("{}", encoding="utf-8")
            with self.assertRaisesRegex(ValueError, "chain-package.json"):
                train.check_outputs(self.outputs, folder)
        train.check_outputs(self.outputs, CONTEXT)


if __name__ == "__main__":
    unittest.main()
