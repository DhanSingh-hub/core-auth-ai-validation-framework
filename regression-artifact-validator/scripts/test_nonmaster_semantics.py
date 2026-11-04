"""Verify explicit semantic decisions, exact catalog recovery and non-promotion boundaries."""

import copy
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "nonmaster_semantic_review", Path(__file__).with_name("review-nonmaster-semantics.py"))
model = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(model)


class SemanticReviewTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.doc = model.base.gates.load(model.DECISIONS)
        cls.package = model.base.gates.load(model.completion.OUTPUT / "model-package.json")
        cls.legacy = model.read_csv(model.v1.OUTPUT / "legacy-release-register.csv")
        cls.units = model.read_csv(model.v1.OUTPUT / "source-unit-register.csv")
        cls.original = model.base.gates.load(model.base.LEGACY)
        cls.catalogs = [(path, model.base.gates.load(path)) for path in model.RECOVERY_CATALOGS]
        cls.old_pages = model.base.source_pages(
            model.base.OLD_SOURCE.read_text(encoding="utf-8-sig"))

    def review(self, doc=None, package=None, legacy=None, old_pages=None):
        return model.review(
            self.doc if doc is None else doc, self.package if package is None else package,
            self.legacy if legacy is None else legacy, self.units,
            self.old_pages if old_pages is None else old_pages)

    def test_exact_reviewed_snapshots(self):
        self.assertEqual(model.base.digest(model.completion.OUTPUT / "model-package.json"),
                         self.doc["reviewedModelSha256"])
        self.assertEqual(model.base.digest(model.v1.OUTPUT / "legacy-release-register.csv"),
                         self.doc["reviewedLegacySha256"])

    def test_every_current_rule_has_one_explicit_classification(self):
        classified, _, _, _ = self.review()
        self.assertEqual(len(classified), 71)
        self.assertEqual({row["brId"] for row in classified},
                         {row["id"] for row in self.package["businessRequirements"]})
        for row in classified:
            self.assertTrue(row["condition"])
            self.assertTrue(row["contexts"])
            self.assertTrue(row["predicate"])
            self.assertTrue(row["sourceEvidence"])
            evidence = row["sourceEvidence"]
            self.assertTrue(evidence.get("pages") or row["sourceAnchors"])
            self.assertEqual(row["status"], "DRAFT_CLASSIFIED_NOT_APPROVED")
            self.assertTrue(row["denominatorEligibility"].startswith("BLOCKED"))

    def test_no_missing_duplicate_or_unknown_classification(self):
        for action in ("missing", "duplicate", "unknown", "kind", "empty"):
            doc = copy.deepcopy(self.doc)
            groups = doc["classificationGroups"]
            if action == "missing":
                groups[0]["rules"].pop()
            elif action == "duplicate":
                groups[0]["rules"].append(groups[0]["rules"][0])
            elif action == "unknown":
                groups[0]["rules"].append("NM4-999")
            elif action == "empty":
                groups[0]["rules"] = []
            else:
                groups[0]["kind"] = "GUESS_FROM_TITLE"
            with self.subTest(action=action), self.assertRaises(ValueError):
                self.review(doc=doc)

    def test_every_relation_is_explicit_and_never_merged(self):
        _, relations, _, _ = self.review()
        self.assertEqual(len(relations), 24)
        for row in relations:
            self.assertFalse(row["automaticMerge"])
            self.assertIsNone(row["denominatorDelta"])
            self.assertTrue(row["reason"])
            self.assertTrue(row["currentEvidence"])
            for evidence in row["currentEvidence"]:
                self.assertTrue(evidence.get("pages") or evidence["sourceAnchors"])
            self.assertIn("NO_LEGACY_PROFILE_PROMOTION", row["scopeConclusion"])

    def test_unknown_self_duplicate_or_legacy_only_pairs_rejected(self):
        for action in ("unknown", "self", "duplicate", "legacy", "kind", "reason", "duplicateId"):
            doc = copy.deepcopy(self.doc)
            relation = doc["relations"][0]
            if action == "unknown":
                relation["right"] = "BR-MISSING"
            elif action == "self":
                relation["right"] = relation["left"]
            elif action == "duplicate":
                doc["relations"].append({**relation, "id": "NMS-999"})
            elif action == "legacy":
                relation["left"] = "BR-RULE-SEG100-R-019"
            elif action == "kind":
                relation["relation"] = "CONFIRMED_FULL_EQUIVALENCE"
            elif action == "duplicateId":
                doc["relations"][1]["id"] = relation["id"]
            else:
                relation["reason"] = " "
            with self.subTest(action=action), self.assertRaises(ValueError):
                self.review(doc=doc)

    def test_equivalent_core_requires_same_source_clause_and_legacy_locator(self):
        for action in ("quote", "locator", "source", "ambiguous", "page", "heading"):
            doc, old = copy.deepcopy(self.doc), copy.deepcopy(self.old_pages)
            relation = doc["relations"][0]
            if action == "quote":
                relation["legacyQuote"] = "Sequence numbers reset after every transaction."
            elif action == "locator":
                relation["legacyLocator"] = "ELEMENT:2"
            elif action == "source":
                old[402] = "Not the six-digit representation clause"
            elif action == "ambiguous":
                old[402] += " " + relation["legacyHeading"]
            elif action == "page":
                relation["legacyPage"] = 115
            else:
                relation["legacyHeading"] = "Number: 85 Name: Wrong element"
            with self.subTest(action=action), self.assertRaisesRegex(ValueError, "both pinned releases"):
                self.review(doc=doc, old_pages=old)

    def test_source_clause_equivalence_does_not_close_legacy_population(self):
        _, relations, legacy, units = self.review()
        equivalents = [row for row in relations if row["relation"] == "EQUIVALENT_CORE"]
        self.assertEqual(len(equivalents), 2)
        self.assertEqual(len(legacy), 1343)
        self.assertTrue(any(row["semanticRelationIds"] for row in legacy))
        unmatched = [row for row in legacy if not row["semanticRelationIds"]]
        self.assertTrue(unmatched)
        self.assertTrue(all(row["overlapDisposition"] == "NOT_ADJUDICATED_NO_ABSENCE_INFERENCE"
                            for row in unmatched))
        self.assertEqual(len(units), len(self.units))
        self.assertTrue(any(row["explicitQuotedRuleIds"] for row in units))
        self.assertTrue(all(row["semanticDisposition"] == "REPOSITORY_WHOLE_UNIT_DERIVATION_REQUIRED"
                            for row in units))

    def test_program_exceptions_and_compound_rules_survive(self):
        _, relations, _, _ = self.review()
        lookup = {row["id"]: row for row in relations}
        self.assertEqual(lookup["NMS-019"]["relation"], "SPECIALIZES")
        self.assertEqual(lookup["NMS-002"]["relation"], "COMPONENT_OF")
        self.assertEqual(lookup["NMS-022"]["relation"], "EXCLUSIVE_PROFILES")
        self.assertEqual(lookup["NMS-023"]["relation"], "DIFFERENT_SCOPE")
        self.assertEqual(lookup["NMS-024"]["relation"], "DIFFERENT_SCOPE")
        self.assertEqual(lookup["NMS-007"]["relation"], "RELATED_DISTINCT")

    def test_all_missing_legacy_statements_recovered_exactly_without_mutation(self):
        before = copy.deepcopy(self.legacy)
        recovered = model.recover_legacy_statements(self.legacy, self.original, self.catalogs)
        self.assertEqual(self.legacy, before)
        self.assertTrue(all(row["title"].strip() for row in recovered))
        restored = [row for row in recovered if row["statementRecovery"]]
        self.assertEqual(len(restored), 42)
        for row in restored:
            evidence = row["statementRecovery"]
            self.assertTrue(evidence["aliasBrId"].startswith("BR-RULE-"))
            self.assertEqual(evidence["catalogSha256"],
                             model.base.digest(Path(evidence["catalogPath"])))
            self.assertEqual(evidence["identityConclusion"], "SAME_CATALOG_RULE_NOT_SEMANTIC_APPROVAL")
        dl3 = next(row for row in restored if row["legacyBrId"] == "BR-SEGDL3-001")
        self.assertIn("SEGDL3-SME-001", json.dumps(dl3["statementRecovery"]["catalogProvisionalItems"]))
        self.assertNotIn("SEGDL8-SME-001", json.dumps(dl3["statementRecovery"]["catalogProvisionalItems"]))

    def test_recovery_rejects_missing_ambiguous_stale_or_promoted_catalog(self):
        for action in ("missing", "ambiguous", "anchor", "title", "version"):
            catalogs = copy.deepcopy(self.catalogs)
            if action == "missing":
                catalogs[0][1]["rules"].pop(0)
            elif action == "ambiguous":
                catalogs[0][1]["rules"].append(catalogs[0][1]["rules"][0])
            elif action == "anchor":
                catalogs[0][1]["rules"][0]["sourceAnchor"]["section"] = "12.99"
            elif action == "title":
                catalogs[0][1]["rules"][0]["title"] = "An unreviewed replacement statement"
            else:
                catalogs[0][1]["specificationVersion"] = "2026-4"
            with self.subTest(action=action), self.assertRaises(ValueError):
                model.recover_legacy_statements(self.legacy, self.original, catalogs)

    def test_recovery_is_retained_in_semantic_ledger(self):
        recovered = model.recover_legacy_statements(self.legacy, self.original, self.catalogs)
        _, _, ledger, _ = self.review(legacy=recovered)
        self.assertEqual(sum(row["statementDisposition"] == "RECOVERED_EXACT_CATALOG_RULE"
                             for row in ledger), 42)
        self.assertTrue(all(row["title"] for row in ledger))
        self.assertTrue(all(row["applicability"] == "NOT_APPROVED_FOR_2026_4_NONMASTER_CONTEXTS"
                            for row in ledger))

    def test_exact_output_check_rejects_changes_and_extra_files(self):
        with tempfile.TemporaryDirectory() as name:
            folder = Path(name)
            (folder / "summary.json").write_bytes(b"expected")
            model.v1.verify_outputs(folder, {"summary.json": b"expected"})
            with self.assertRaises(ValueError):
                model.v1.verify_outputs(folder, {"summary.json": b"changed"})
            (folder / "extra.json").write_bytes(b"extra")
            with self.assertRaises(ValueError):
                model.v1.verify_outputs(folder, {"summary.json": b"expected"})

    def test_published_governance_and_task_paths(self):
        module = Path(__file__).resolve().parent.parent
        training = model.base.gates.load(module / "specifications" / "ATL105" / "training-status.json")
        entry = training["transactionContextTraining"]["NONMASTER_PROGRAMS_2026_4"]
        for key in ("semanticReviewReport", "semanticReviewGenerator", "semanticReviewRegressionTest"):
            self.assertTrue((module / entry[key]).is_file())
        self.assertEqual(entry["semanticReviewStatus"], "INCOMPLETE_REPOSITORY_SEMANTIC_WORK_REMAINS")
        tasks = model.base.gates.load(module / "framework-tasks.json")["tasks"]
        selected = [task for task in tasks if task["name"].startswith("nonmaster-semantics-")]
        self.assertEqual(len(selected), 3)
        for task in selected:
            self.assertTrue((module / task["script"]).is_file())
        summary = model.base.gates.load(model.OUTPUT / "summary.json")
        self.assertFalse(summary["wholeSpecSemanticsComplete"])
        self.assertFalse(summary["executionReady"])
        self.assertIsNone(summary["approvedDenominator"])
        self.assertIsNone(summary["confirmedRequirementCoveragePercent"])


if __name__ == "__main__":
    unittest.main()
