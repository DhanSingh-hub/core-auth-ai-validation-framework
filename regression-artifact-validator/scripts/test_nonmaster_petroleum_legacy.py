"""Verify exact and bounded Petroleum-to-legacy product counterpart decisions."""

import copy
import importlib.util
import unittest
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "petroleum_legacy_review", Path(__file__).with_name("reconcile-nonmaster-petroleum-legacy.py"))
model = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(model)


class PetroleumLegacyTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.doc = model.base.gates.load(model.DECISIONS)
        cls.legacy = model.read_csv(model.parent.parent.OUTPUT / "legacy-semantic-register.csv")
        cls.companions = {rule["id"] for folder in model.completion.COMPANIONS.values()
                          for rule in model.base.gates.load(
                              model.completion.fleet.KB / folder / "chain-package.json")["businessRequirements"]}
        cls.old = model.base.OLD_SOURCE.read_text(encoding="utf-8-sig")
        cls.new = model.base.gates.SOURCE.read_text(encoding="utf-8-sig")

    def reconcile(self, doc=None, legacy=None, companions=None, old=None, new=None):
        return model.reconcile(self.doc if doc is None else doc,
                               self.legacy if legacy is None else legacy,
                               self.companions if companions is None else companions,
                               self.old if old is None else old, self.new if new is None else new)

    def test_all_named_legacy_rules_and_companion_endpoints(self):
        rows = self.reconcile()
        self.assertEqual(len(rows), 8)
        self.assertEqual(sum(len(row["petroleumCounterparts"]) for row in rows), 16)
        self.assertTrue(all(not row["automaticMerge"] and row["denominatorDelta"] is None
                            for row in rows))
        ev = next(row for row in rows if row["legacyBrId"] == "BR-RULE-SEG102-R-008")
        self.assertEqual(ev["petroleumCounterparts"], [])
        self.assertEqual(ev["relation"], "NO_DIRECT_RETAINED_COMPANION_COUNTERPART")

    def test_changed_source_quote_is_rejected(self):
        with self.assertRaises(ValueError):
            self.reconcile(old=self.old.replace(
                "Fuel products must always be the first products in the segment.",
                "Fuel products may be the first products in the segment.", 1))

    def test_unknown_legacy_or_petroleum_endpoint_is_rejected(self):
        for action in ("legacy", "petroleum"):
            doc = copy.deepcopy(self.doc)
            if action == "legacy":
                doc["decisions"][0]["legacyBrId"] = "BR-MISSING"
            else:
                doc["decisions"][0]["targetBrIds"][0] = "VFC-BR-999"
            with self.subTest(action=action), self.assertRaises(ValueError):
                self.reconcile(doc=doc)

    def test_unscoped_or_invalid_no_match_claim_is_rejected(self):
        doc = copy.deepcopy(self.doc)
        doc["decisions"][1]["relation"] = "RELATED_DISTINCT"
        with self.assertRaises(ValueError):
            self.reconcile(doc=doc)
        doc = copy.deepcopy(self.doc)
        doc["decisions"][0]["targetBrIds"] = []
        with self.assertRaises(ValueError):
            self.reconcile(doc=doc)


if __name__ == "__main__":
    unittest.main()
