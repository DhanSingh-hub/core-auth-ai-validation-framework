import copy
import importlib.util
import json
import sys
import unittest
from pathlib import Path


SCRIPTS = Path(__file__).resolve().parent
sys.path.insert(0, str(SCRIPTS))
SPEC = importlib.util.spec_from_file_location("petroleum_context_training", SCRIPTS / "train-petroleum-context.py")
train = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(train)
chain_module = train.petroleum_chain
CONTEXT = train.KB / "mastercard-fleet"


class PetroleumChainTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.outputs = train.build("mastercard-fleet")
        cls.package = json.loads(cls.outputs["training-package.json"])
        cls.chain = json.loads(cls.outputs["chain-package.json"])
        cls.input = json.loads((CONTEXT / "chain-input.json").read_text(encoding="utf-8"))
        cls.sources = train.load_sources()
        cls.context = {k: cls.package[k] for k in ("contextId", "scope", "inventory", "reconciliation", "catalog")}

    def rebuild(self, mutate):
        chain = copy.deepcopy(self.input)
        mutate(chain)
        return chain_module.build_chain(chain, self.context, self.sources, train.atl105_evidence)

    def assertRejected(self, mutate, message):
        with self.assertRaisesRegex(ValueError, message):
            self.rebuild(mutate)

    def test_check_digit_helper_agrees_with_independent_implementation(self):
        for pan in ("5568432211445331", "5567550123456780"):
            self.assertTrue(chain_module.luhn_valid(pan))
            self.assertTrue(train.luhn(pan))
        for td in self.chain["testData"]:
            account = td["payload"]["cardData"].get("account") or ""
            if len(account) == 16:
                self.assertEqual(chain_module.luhn_valid(account), train.luhn(account))

    def test_context_matrix_maps_transactions_and_prompt_codes_from_both_releases(self):
        matrix = self.chain["contextMatrix"]
        codes = {t["petroleum"]: t["code"] for t in matrix["transactions"]}
        self.assertEqual(codes["POS Purchase/Capture Transaction"], "0")
        self.assertEqual(codes["CAT Authorization Only Transaction"], "5")
        self.assertEqual(codes["POS Reversal/Void Transaction"], "8")
        fleet = {t["code"]: t["promptCodes"]["fleet"] for t in matrix["transactions"]}
        self.assertEqual(fleet["0"], "0080")
        statuses = [c["status"] for c in matrix["cells"]]
        self.assertEqual((statuses.count("SUPPORTED"), statuses.count("REVIEW_REQUIRED")), (13, 4))
        for t in matrix["transactions"]:
            self.assertEqual({e["version"] for e in t["atl105"]}, {"2026-3", "2026-4"})
            self.assertEqual(t["reviewRequired"], t["basis"] != "EXACT_NAME" or t["role"] == "REVIEW")
        self.assertRejected(lambda c: c["contextMatrix"]["transactions"][0].update(petroleum="Invented"), "supported transactions")
        self.assertRejected(lambda c: c["contextMatrix"]["transactions"][1]["atl105"][0].update(extractedPage=200), "Quote not found")
        self.assertRejected(lambda c: c["contextMatrix"]["productTypePrompts"].update({"4": ["ODOMETER", "DRIVER_ID"]}), "disagree")
        self.assertRejected(lambda c: c["contextMatrix"]["cardTypes"].update(fleet="081"), "lacks reconciled evidence")

    def test_draft_brs_are_atomic_anchored_and_cover_every_rule(self):
        brs = self.chain["businessRequirements"]
        self.assertEqual(len(brs), 24)
        rules = {i["id"] for i in self.package["inventory"] if i["classification"] == "RULE"}
        anchored = {a["inventoryId"] for b in brs for a in b["sourceAnchors"]}
        self.assertLessEqual(rules, anchored)
        for b in brs:
            self.assertEqual(b["status"], "DRAFT_REVIEW_REQUIRED")
            for a in b["sourceAnchors"]:
                page = self.sources["pages"][a["pdfPage"]]
                self.assertEqual(a["textSha256"], page["textSha256"])
        self.assertRejected(lambda c: c["businessRequirements"].pop(9), "Rule items without a BR")
        self.assertRejected(lambda c: c["businessRequirements"][0].update(inventoryRefs=["MCF-I99"]), "unknown inventory")
        self.assertRejected(lambda c: c["businessRequirements"][1].update(statement=c["businessRequirements"][0]["statement"]), "Duplicate BR statements")

    def test_every_chain_is_linked_and_validator_outcomes_match_expectations(self):
        tc_by_ts = {c["scenarioIds"][0]: c for c in self.chain["testCases"]}
        td_by_tc = {d["testCaseIds"][0]: d for d in self.chain["testData"]}
        model = chain_module.validator_model(self.chain["contextMatrix"], self.package["catalog"])
        for s in self.chain["testScenarios"]:
            case = tc_by_ts[s["id"]]
            data = td_by_tc[case["id"]]
            self.assertTrue(s["sourceAnchors"])
            self.assertEqual(case["expectedOutcome"]["host"], chain_module.HOST_OUTCOME)
            self.assertEqual(data["readiness"], "DRAFT_SYNTHETIC_NOT_EXECUTED")
            actual, _ = chain_module.validate_test_data(data["payload"], model)
            self.assertEqual(actual, case["expectedOutcome"]["violatedRequirements"])
        self.assertRejected(lambda c: c["scenarios"][13].update(expect=[]), "oracle mismatch")
        self.assertRejected(lambda c: c["scenarios"][0].update(expect=["MCF-BR-18"]), "positive but expects")
        self.assertRejected(lambda c: c["scenarios"][0].update(matrix=["7", "TRACK_2_DEFAULT"]), "unsupported matrix cell")
        self.assertRejected(lambda c: c["scenarios"][0].update(oracleConflict="MCF-R01"), "not a recorded source conflict")
        conflicted = {c["scenarioIds"][0]: c["expectedOutcome"]["oracleConflict"] for c in self.chain["testCases"]
                      if c["expectedOutcome"]["oracleStatus"] == "CONFLICTING_SOURCES_REVIEW_REQUIRED"}
        self.assertEqual(conflicted, {"MCF-TS-029": "MCF-R19", "MCF-TS-043": "MCF-R20", "MCF-TS-058": "MCF-R22"})
        self.assertRejected(lambda c: c["scenarios"].pop(12), "Supported matrix cells without positive")
        self.assertRejected(lambda c: c.update(scenarios=[s for s in c["scenarios"] if s["id"] != "MCF-TS-038"]), "BRs without scenarios: MCF-BR-11")

    def test_fleet_rules_do_not_leak_into_regular_mastercard_fallback(self):
        model = chain_module.validator_model(self.chain["contextMatrix"], self.package["catalog"])
        defaults = self.input["testDataDefaults"]
        fallback = chain_module.make_test_data(defaults, {"terminalSpecialCode034": False, "cardType": "020", "products": [],
                                                          "prompts": {}, "cardholderReceiptShowsExpiry": True}, self.chain["contextMatrix"])
        self.assertEqual(chain_module.validate_test_data(fallback, model), ([], False))
        source_track2 = chain_module.make_test_data(defaults, {}, self.chain["contextMatrix"])
        source_track2["cardData"]["track2"] = ";5567550123456780=15037779876543210915?"
        violations, fleet = chain_module.validate_test_data(source_track2, model)
        self.assertTrue(fleet)
        self.assertNotIn("MCF-BR-21", violations)

    def test_flows_and_mutations_are_fully_covered(self):
        for t in self.chain["flows"]["transitions"]:
            self.assertTrue(t["coveredBy"], t["id"])
        suite = self.chain["mutationSuite"]
        self.assertEqual(suite["caught"], suite["total"])
        self.assertEqual({m["operator"] for m in suite["results"]}, set(suite["operators"]))
        for m in suite["results"]:
            self.assertEqual(m["expectedViolations"], m["actualViolations"])
        self.assertRejected(lambda c: [s.update(transitions=[t for t in s.get("transitions", []) if t != "T08"]) for s in c["scenarios"]],
                            "Flow transitions without scenarios")
        self.assertEqual(self.chain["atl105Serialization"]["status"], "BLOCKED")

    def test_draft_chains_stay_out_of_independent_counts(self):
        self.assertEqual(self.chain["manifest"]["independence"], "NOT_INDEPENDENT_TEST_SOLUTION_EVIDENCE_UNTIL_SME_APPROVAL")
        for key in ("independentBrCount", "testScenarioCount", "testCaseCount", "testDataCount", "executedCaseCount"):
            self.assertEqual(self.package[key], 0)
        counts = self.package["draftCounts"]
        self.assertEqual((counts["draftBusinessRequirements"], counts["draftTestData"]), (24, 60))
        status = json.loads(train.STATUS_FILE.read_text(encoding="utf-8"))
        entry = status["supplementalSourceTraining"]["PETROLEUM_2025_1"]["contextTraining"]["MASTERCARD_FLEET"]
        self.assertEqual(entry["draftCounts"], {
            "businessRequirements": counts["draftBusinessRequirements"], "testScenarios": counts["draftTestScenarios"],
            "testCases": counts["draftTestCases"], "testData": counts["draftTestData"], "mutationsCaught": counts["mutationsCaught"]})
        for key in ("chainInput", "chainEvidence", "chainGenerator", "chainRegressionTest"):
            self.assertTrue((train.MODULE / entry[key]).is_file(), key)

    def test_published_chain_outputs_are_current(self):
        self.assertIn("chain-package.json", self.outputs)
        train.check_outputs(self.outputs, CONTEXT)


if __name__ == "__main__":
    unittest.main()
