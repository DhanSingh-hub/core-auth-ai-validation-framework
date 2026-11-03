import importlib.util
import json
import tempfile
import unittest
from pathlib import Path


SCRIPT = Path(__file__).with_name("train-iin-context.py")
SPEC = importlib.util.spec_from_file_location("iin_context_training", SCRIPT)
train = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(train)
CONTEXT = train.KB / "card-type-catalog"


class IinContextTrainingTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.outputs = train.build("card-type-catalog")
        cls.package = json.loads(cls.outputs["training-package.json"])
        cls.config = json.loads((CONTEXT / "context-input.json").read_text(encoding="utf-8"))
        cls.plan = json.loads(train.PLAN.read_text(encoding="utf-8"))
        cls.sources = train.load_sources()

    def test_plan_defines_every_phase_and_maps_every_gate_once(self):
        self.assertEqual([p["phase"] for p in self.package["phaseProgress"]], list(range(11)))
        self.assertTrue(all(p["steps"] >= 3 for p in self.package["phaseProgress"]))
        gates = [p["frameworkGate"] for p in self.plan["phases"] if p["frameworkGate"]]
        self.assertEqual(len(gates), len(set(gates)))
        self.assertEqual(sorted(gates), sorted(self.package["frameworkGates"]))

    def test_pilot_progress_is_draft_and_never_promoted(self):
        statuses = {p["phase"]: p["status"] for p in self.package["phaseProgress"]}
        self.assertEqual(statuses[0], "DRAFTED_PENDING_SME_SIGNOFF")
        self.assertEqual({statuses[n] for n in range(1, 8)}, {"DRAFTED_PENDING_SME_REVIEW"})
        self.assertEqual(statuses[8], "PARTIAL_ATL105_SERIALIZATION_BLOCKED")
        self.assertEqual(statuses[9], "BLOCKED_NO_AI_PACKAGE")
        self.assertEqual(statuses[10], "BLOCKED_PENDING_EXECUTION_AND_SME_APPROVAL")
        self.assertEqual(self.package["status"], "DRAFT_REVIEW_REQUIRED")
        for key in ("certified", "executionReady", "baselineChanged", "modelWeightsChanged"):
            self.assertFalse(self.package[key])
        for key in ("independentBrCount", "testScenarioCount", "testCaseCount", "testDataCount", "executedCaseCount"):
            self.assertEqual(self.package[key], 0)

    def test_catalog_derives_one_hundred_ranges_across_sixteen_card_types_with_known_duplicates(self):
        catalog = self.package["catalog"]
        self.assertEqual(catalog["totalRanges"], 100)
        self.assertEqual(len(catalog["cards"]), 16)
        visa_fleet = next(c for c in catalog["cards"] if c["name"] == "Visa Fleet")
        self.assertEqual(len(visa_fleet["ranges"]), 63)
        mastercard_fleet = next(c for c in catalog["cards"] if c["name"] == "MasterCard Fleet")
        self.assertEqual(len(mastercard_fleet["ranges"]), 4)
        diners = next(c for c in catalog["cards"] if c["name"] == "Diners Club")
        self.assertEqual(sorted({r["length"] for r in diners["ranges"]}), [14, 16])
        debit = next(c for c in catalog["cards"] if c["name"] == "Debit")
        self.assertTrue(debit["wildcard"])
        self.assertEqual(set(catalog["duplicateCardTypeIds"]), {"DS", "GF", "VISA"})
        self.assertEqual(set(catalog["duplicateCardTypeIds"]["DS"]), {"Discover Network", "UnionPay"})
        self.assertEqual(set(catalog["duplicateCardTypeIds"]["GF"]), {"Fleetwide", "Fuelman"})
        for card in catalog["cards"]:
            for r in card["ranges"]:
                self.assertLess(int(r["low"]), int(r["high"]))
                self.assertEqual(len(r["low"]), len(r["high"]))

    def test_derivation_is_source_anchored_not_config_declared(self):
        # Even if a (hypothetical) per-range config existed, the script must re-derive every
        # range directly from the page text; prove the derived low/high values are literal
        # substrings of the IIN Reference Manual page text they are attributed to.
        pages_text = "".join(p["text"] for p in self.sources["document"]["pages"])
        for card in self.package["catalog"]["cards"]:
            for r in card["ranges"][:1]:
                self.assertIn(r["low"], pages_text)
                self.assertIn(r["high"], pages_text)

    def test_visa_fleet_prefixes_cross_check_against_independently_trained_petroleum_data(self):
        cross = self.package["crossDocument"]["visaFleetPrefixCrossCheck"]
        self.assertEqual(cross["iinManualPrefixPairs"], 63)
        self.assertEqual(cross["petroleumPrefixPairs"], 63)
        self.assertEqual(cross["matched"], 63)
        self.assertEqual(cross["iinOnly"], [])
        self.assertEqual(cross["petroleumOnly"], [])
        self.assertEqual(self.package["crossDocument"]["masterCardFleetMod10"]["citedLessonId"], "PETRO-2025-1-L011")

    def test_reconciliation_includes_atl105_element2_and_internal_consistency_findings(self):
        by_id = {r["id"]: r for r in self.package["reconciliation"]}
        self.assertEqual(by_id["IIN-RECON-01"]["disposition"], "ALIGNED")
        self.assertEqual({e["version"] for e in by_id["IIN-RECON-01"]["atl105"]}, {"2026-3", "2026-4"})
        self.assertEqual(by_id["IIN-RECON-02"]["disposition"], "ALIGNED_WITH_EXCEPTIONS")
        self.assertIn("STAR", by_id["IIN-RECON-02"]["note"])
        for internal_id in ("IIN-IC-01", "IIN-IC-02", "IIN-IC-03", "IIN-IC-04", "IIN-IC-05"):
            self.assertIn(internal_id, by_id)
            self.assertTrue(by_id[internal_id]["reviewRequired"])
            self.assertTrue(by_id[internal_id]["question"])

    def test_mutation_suite_catches_every_invalid_case_and_accepts_every_boundary(self):
        mutation = self.package["chain"]["mutationSuite"]
        self.assertGreater(mutation["total"], 0)
        self.assertEqual(mutation["caught"], mutation["total"])
        valid_count = sum(1 for td in self.package["chain"]["testData"] if td["expected"] == "VALID")
        self.assertEqual(valid_count + mutation["total"], len(self.package["chain"]["testData"]))
        self.assertEqual(self.package["draftCounts"]["draftBusinessRequirements"], 14)

    def test_validate_pan_enforces_length_range_and_mod10(self):
        catalog = self.package["catalog"]
        amex = next(c for c in catalog["cards"] if c["name"] == "American Express")
        self.assertEqual(train.validate_pan(amex, amex["ranges"][0]["low"]), "VALID")
        self.assertEqual(train.validate_pan(amex, amex["ranges"][0]["low"][:-1]), "INVALID_LENGTH")
        self.assertEqual(train.validate_pan(amex, str(int(amex["ranges"][0]["high"]) + 1).zfill(15)), "INVALID_RANGE")
        debit = next(c for c in catalog["cards"] if c["name"] == "Debit")
        self.assertEqual(train.validate_pan(debit, "1234567890123456789"), "VALID")

    def test_luhn_matches_known_vectors(self):
        self.assertTrue(train.luhn("4532015112830366"))
        self.assertFalse(train.luhn("4532015112830367"))

    def test_atl105_evidence_rejects_wrong_quote_page_or_printed_label(self):
        with self.assertRaises(ValueError):
            train.atl105_evidence(self.sources, "2026-4", 365, "INVENTED QUOTE NOT IN ATL105")
        with self.assertRaises(ValueError):
            train.atl105_evidence(self.sources, "2026-4", 365,
                                   "Credit, debit, and EBT cards: Please refer to the most recent version of BUYPASS",
                                   "99-99")

    def test_stale_or_missing_outputs_are_detected_without_writes(self):
        with tempfile.TemporaryDirectory() as directory:
            folder = Path(directory)
            with self.assertRaisesRegex(ValueError, "Missing or stale"):
                train.check_outputs(self.outputs, folder)
            for name, text in self.outputs.items():
                (folder / name).write_text(text, encoding="utf-8")
            train.check_outputs(self.outputs, folder)
            (folder / "training-report.md").write_text("stale", encoding="utf-8")
            with self.assertRaisesRegex(ValueError, "training-report.md"):
                train.check_outputs(self.outputs, folder)

    def test_registration_matches_generated_gates(self):
        status = json.loads((train.PACK / "training-status.json").read_text(encoding="utf-8"))
        entry = status["supplementalSourceTraining"]["IIN_REFERENCE_MANUAL_2024_1"]["contextTraining"]["CARD_TYPE_CATALOG"]
        self.assertEqual(entry["draftCounts"]["businessRequirements"], self.package["draftCounts"]["draftBusinessRequirements"])
        self.assertEqual(entry["draftCounts"]["testData"], self.package["draftCounts"]["draftTestData"])
        self.assertEqual(sorted(entry["gates"]), sorted(self.package["frameworkGates"]))


if __name__ == "__main__":
    unittest.main()
