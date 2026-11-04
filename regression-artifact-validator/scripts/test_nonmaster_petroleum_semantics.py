"""Verify complete retained-companion population, exact source identities and non-inheritance."""

import copy
import importlib.util
import unittest
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "nonmaster_petroleum_review", Path(__file__).with_name("reconcile-nonmaster-petroleum.py"))
model = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(model)


class PetroleumSemanticTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.doc = model.base.gates.load(model.DECISIONS)
        cls.companions = model.base.gates.load(model.completion.OUTPUT / "fleet-companion-chains.json")
        cls.current = model.base.gates.load(model.completion.OUTPUT / "model-package.json")
        cls.legacy = model.parent.read_csv(model.parent.OUTPUT / "legacy-semantic-register.csv")
        cls.sources = model.completion.fleet.load_sources()
        cls.configurations = {
            context: model.base.gates.load(model.completion.fleet.KB / folder / "context-input.json")
            for context, folder in model.completion.COMPANIONS.items()}

    def reconcile(self, doc=None, companions=None, configurations=None, sources=None):
        return model.reconcile(
            self.doc if doc is None else doc, self.companions if companions is None else companions,
            self.current, self.legacy,
            self.configurations if configurations is None else configurations,
            self.sources if sources is None else sources)

    def test_exact_pinned_companion_population(self):
        self.assertEqual(model.base.digest(model.completion.OUTPUT / "fleet-companion-chains.json"),
                         self.doc["companionChainsSha256"])
        rows, _, _ = self.reconcile()
        self.assertEqual(len(rows), 61)
        self.assertEqual(sum(row["context"] == "VISA_FLEET" for row in rows), 26)
        self.assertEqual(sum(row["context"] == "VOYAGER_FLEET" for row in rows), 35)
        self.assertEqual({row["brId"] for row in rows},
                         {rule["id"] for package in self.companions.values()
                          for rule in package["businessRequirements"]})

    def test_no_missing_duplicate_extra_decision_or_context(self):
        for action in ("missing", "extra", "duplicate", "context"):
            doc, companions = copy.deepcopy(self.doc), copy.deepcopy(self.companions)
            if action == "missing":
                doc["rules"].pop()
            elif action == "extra":
                doc["rules"].append({**doc["rules"][0], "id": "VFC-BR-99"})
            elif action == "duplicate":
                doc["rules"].append(doc["rules"][0])
            else:
                companions["OTHER_FLEET"] = companions.pop("VISA_FLEET")
            with self.subTest(action=action), self.assertRaises(ValueError):
                self.reconcile(doc, companions)

    def test_all_compound_obligations_and_boundaries_remain_draft(self):
        rows, _, links = self.reconcile()
        ids = []
        for row in rows:
            self.assertTrue(row["condition"])
            self.assertTrue(row["assessment"])
            self.assertTrue(row["draftObligations"])
            ids.extend(item["id"] for item in row["draftObligations"])
            self.assertTrue(all(item["status"] == "DRAFT_NOT_ATOMICITY_APPROVED"
                                for item in row["draftObligations"]))
            self.assertEqual(row["otherPrograms"], "NO_AUTOMATIC_INHERITANCE")
            self.assertEqual(row["otherCounterparts"], "NOT_ADJUDICATED_NO_ABSENCE_INFERENCE")
            self.assertFalse(row["automaticMerge"])
            self.assertFalse(row["executionReady"])
            self.assertIsNone(row["approvedDenominatorDelta"])
            self.assertEqual(row["sourceVersion"], "PETROLEUM_2025_1")
        self.assertEqual(len(ids), len(set(ids)))
        self.assertTrue(all(not row["automaticMerge"] for row in links))

    def test_empty_obligations_condition_and_assessment_rejected(self):
        for key in ("kind", "when", "assessment", "obligations"):
            doc = copy.deepcopy(self.doc)
            doc["rules"][0][key] = [] if key == "obligations" else ""
            with self.subTest(key=key), self.assertRaises(ValueError):
                self.reconcile(doc=doc)
        doc = copy.deepcopy(self.doc)
        doc["rules"][0]["obligations"].append(doc["rules"][0]["obligations"][0])
        with self.assertRaises(ValueError):
            self.reconcile(doc=doc)

    def test_unknown_duplicate_or_wrongly_equivalent_counterpart_rejected(self):
        for action in ("current", "legacy", "duplicateCurrent", "duplicateLegacy", "type"):
            doc = copy.deepcopy(self.doc)
            target = doc["rules"][0]
            if action == "current":
                target["current"] = [{"id": "BR-UNKNOWN", "relation": "RELATED_DIFFERENT_CONTEXT"}]
            elif action == "legacy":
                target["legacy"] = ["BR-UNKNOWN"]
            elif action == "duplicateCurrent":
                target["current"] = [{"id": "BR-NM4-004", "relation": "RELATED_DIFFERENT_CONTEXT"}] * 2
            elif action == "duplicateLegacy":
                target["legacy"] = ["BR-RULE-SEG100-R-018"] * 2
            else:
                target["current"] = [{"id": "BR-NM4-004", "relation": "APPROVED_EQUIVALENCE"}]
            with self.subTest(action=action), self.assertRaises(ValueError):
                self.reconcile(doc=doc)

    def test_source_page_quote_hash_and_page_identity_required(self):
        for key, value in (("pdfPage", 1), ("quote", "This invented quote is not in the source"),
                           ("textSha256", "0" * 64), ("pageId", "OTHER-PAGE"), ("inventoryId", "VFC-I01")):
            companions = copy.deepcopy(self.companions)
            companions["VISA_FLEET"]["businessRequirements"][0]["sourceAnchors"][0][key] = value
            with self.subTest(key=key), self.assertRaisesRegex(ValueError, "source quote/page/hash"):
                self.reconcile(companions=companions)

    def test_version_and_source_authority_not_promoted(self):
        for action in ("version", "context", "status", "authority"):
            cfg, companions = copy.deepcopy(self.configurations), copy.deepcopy(self.companions)
            if action == "version":
                cfg["VISA_FLEET"]["sourceVersion"] = "2026-4"
            elif action == "context":
                cfg["VISA_FLEET"]["contextId"] = "VISA_CREDIT"
            elif action == "status":
                cfg["VISA_FLEET"]["status"] = "APPROVED"
            else:
                companions["VISA_FLEET"]["manifest"]["authority"] = "APPROVED_DENOMINATOR"
            with self.subTest(action=action), self.assertRaisesRegex(ValueError, "authority"):
                self.reconcile(companions=companions, configurations=cfg)

    def test_every_open_source_decision_lists_all_impacted_rules(self):
        rows, questions, _ = self.reconcile()
        lookup = {(row["context"], row["id"]): row for row in questions}
        self.assertTrue(lookup)
        for row in rows:
            for identity in row["unresolvedSourceRefs"]:
                self.assertIn(row["brId"], lookup[(row["context"], identity)]["impactedRuleIds"])
        for (context, identity), question in lookup.items():
            self.assertEqual(set(question["impactedRuleIds"]),
                             {row["brId"] for row in rows
                              if row["context"] == context and identity in row["unresolvedSourceRefs"]})
        self.assertTrue(any(row["id"] == "VFC-R19" for row in questions))
        self.assertTrue(any(row["id"] == "VFC-R20" for row in questions))
        self.assertTrue(any(row["id"] == "VFC-R22" for row in questions))
        self.assertTrue(any(row["id"] == "VYR-R21" for row in questions))
        expected = {(context, item["id"]) for context, cfg in self.configurations.items()
                    for item in cfg["reconciliation"] if item["reviewRequired"] or item.get("question")}
        self.assertEqual(set(lookup), expected)
        distinct = lookup[("VISA_FLEET", "VFC-R13")]
        self.assertEqual(distinct["disposition"], "DISTINCT_PROGRAM_DO_NOT_ALIAS")
        self.assertEqual(distinct["impactScope"], "CONTEXT_LEVEL_DECISION_NO_EXPLICIT_BR_BINDING")

    def test_unknown_source_comparison_and_wrong_atl105_quote_rejected(self):
        companions = copy.deepcopy(self.companions)
        companions["VISA_FLEET"]["businessRequirements"][0]["reconciliationRefs"].append("MISSING")
        with self.assertRaisesRegex(ValueError, "comparison reference"):
            self.reconcile(companions=companions)
        cfg = copy.deepcopy(self.configurations)
        anchor = next(anchor for row in cfg["VISA_FLEET"]["reconciliation"]
                      for anchor in row.get("atl105", []))
        anchor["quote"] = "Invented ATL105 comparison quote"
        with self.assertRaisesRegex(ValueError, "ATL105 comparison"):
            self.reconcile(configurations=cfg)

    def test_concrete_links_do_not_become_full_obligation_execution_proof(self):
        rows, _, _ = self.reconcile()
        for row in rows:
            self.assertTrue(row["scenarioIds"])
            self.assertTrue(row["caseIds"])
            self.assertTrue(row["dataIds"])
            self.assertEqual(row["logicalChainMeaning"],
                             "LINKED_RULE_NOT_COMPLETE_OBLIGATION_OR_HOST_EXECUTION_PROOF")
        companions = copy.deepcopy(self.companions)
        companions["VISA_FLEET"]["testScenarios"] = [
            {**row, "requirementIds": [identity for identity in row["requirementIds"] if identity != "VFC-BR-01"]}
            for row in companions["VISA_FLEET"]["testScenarios"]]
        with self.assertRaisesRegex(ValueError, "concrete TS/TC/TD"):
            self.reconcile(companions=companions)

    def test_dangling_case_and_td_bindings_rejected(self):
        for action in ("case", "data"):
            companions = copy.deepcopy(self.companions)
            package = companions["VISA_FLEET"]
            if action == "case":
                package["testCases"][0]["scenarioIds"].append("UNKNOWN")
            else:
                package["testData"][0]["testCaseIds"].append("UNKNOWN")
            with self.subTest(action=action), self.assertRaisesRegex(ValueError, "binding"):
                self.reconcile(companions=companions)

    def test_card_removal_retains_post_authorization_reversal(self):
        rows, _, _ = self.reconcile()
        row = next(item for item in rows if item["brId"] == "VYR-BR-24")
        self.assertIn("requests card removal", row["condition"])
        self.assertTrue(any("reversal" in item["statement"] and "already" in item["statement"]
                            for item in row["draftObligations"]))
        self.assertIn("VYR-R17", row["unresolvedSourceRefs"])
        self.assertIn("VYR-R19", row["unresolvedSourceRefs"])

    def test_optional_sda_and_conditional_offline_cvm_preserved(self):
        rows, _, _ = self.reconcile()
        lookup = {row["brId"]: row for row in rows}
        self.assertIn("offline-capable", lookup["VYR-BR-33"]["condition"])
        statements = [item["statement"] for item in lookup["VYR-BR-33"]["draftObligations"]]
        self.assertIn("Treat SDA as optional", statements)
        statements = [item["statement"] for item in lookup["VYR-BR-32"]["draftObligations"]]
        self.assertIn("Prohibit offline PIN", statements)
        self.assertIn("Allow signature only at attended terminals", statements)

    def test_published_governance_and_counts_do_not_claim_whole_spec_completion(self):
        module = Path(__file__).resolve().parent.parent
        training = model.base.gates.load(module / "specifications" / "ATL105" / "training-status.json")
        entry = training["transactionContextTraining"]["NONMASTER_PROGRAMS_2026_4"]
        for key in ("petroleumSemanticReviewReport", "petroleumSemanticReviewGenerator",
                    "petroleumSemanticReviewRegressionTest"):
            self.assertTrue((module / entry[key]).is_file())
        self.assertIn("NOT_WHOLE_SPEC", entry["petroleumSemanticReviewStatus"])
        tasks = model.base.gates.load(module / "framework-tasks.json")["tasks"]
        selected = [task for task in tasks if task["name"].startswith("nonmaster-petroleum-semantics-")]
        self.assertEqual(len(selected), 3)
        for task in selected:
            self.assertTrue((module / task["script"]).is_file())
        summary = model.base.gates.load(model.OUTPUT / "summary.json")
        self.assertEqual(summary["counts"]["companionRules"], 61)
        self.assertFalse(summary["exhaustiveLegacyOverlapComplete"])
        self.assertFalse(summary["wholeSpecSemanticsComplete"])
        self.assertFalse(summary["executionReady"])
        self.assertIsNone(summary["approvedDenominator"])
        self.assertIsNone(summary["confirmedRequirementCoveragePercent"])


if __name__ == "__main__":
    unittest.main()
