"""Verify exact Appendix G source derivation and non-promotion boundaries."""

import copy
import importlib.util
import unittest
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "appendix_g_review", Path(__file__).with_name("derive-nonmaster-appendix-g.py"))
model = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(model)


class AppendixGSemanticTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.scope = model.base.gates.load(model.SCOPE)
        cls.old = model.base.OLD_SOURCE.read_text(encoding="utf-8-sig")
        cls.new = model.base.gates.SOURCE.read_text(encoding="utf-8-sig")
        cls.catalog = model.CATALOG.read_text(encoding="utf-8")
        cls.legacy = model.read_csv(
            model.parent.parent.parent.OUTPUT / "legacy-semantic-register.csv")
        cls.units = model.read_csv(
            model.parent.parent.completion.v1.OUTPUT / "source-unit-register.csv")

    def derive(self, scope=None, old=None, new=None, catalog=None, legacy=None, units=None):
        return model.derive(
            self.scope if scope is None else scope,
            self.old if old is None else old,
            self.new if new is None else new,
            self.catalog if catalog is None else catalog,
            self.legacy if legacy is None else legacy,
            self.units if units is None else units)

    def test_exact_release_code_rows_and_semantic_boundaries(self):
        entries, units, obligations, assertions, releases = self.derive()
        self.assertEqual(len(entries), 23)
        self.assertEqual(len(assertions), 24)
        self.assertEqual(len(obligations), 7)
        self.assertEqual(releases["2026-3"]["codes"], releases["2026-4"]["codes"])
        self.assertEqual(releases["2026-3"]["bodySha256"], releases["2026-4"]["bodySha256"])
        self.assertTrue(all(not row["atomicBusinessDenominator"] for row in assertions))
        negative = next(row for row in assertions if row["legacyBrId"].endswith("UNKNOWN-OR-MALFORMED"))
        self.assertEqual(negative["disposition"], "TEST_NEGATIVE_BOUNDARY_NOT_A_SOURCE_CODE_ROW")

    def test_all_88_retained_source_lines_are_classified(self):
        _, units, _, _, _ = self.derive()
        self.assertEqual([row["page"] for row in units], [545, 545, 546])
        self.assertEqual(sum(row["classifiedLineCount"] for row in units), 88)
        for unit in units:
            self.assertEqual(len(unit["classifications"]), unit["classifiedLineCount"])
            self.assertTrue(all(row["kind"] for row in unit["classifications"]))
            self.assertEqual(unit["approvalStatus"], "DRAFT_NOT_APPROVED")

    def test_unknown_or_changed_table_code_is_rejected(self):
        mutated = self.new.replace(
            "7 Merchandise return Merchandise return/refund",
            "X Merchandise return Merchandise return/refund", 1)
        with self.assertRaises(ValueError):
            self.derive(new=mutated)

    def test_changed_authored_prose_span_is_rejected(self):
        mutated = self.new.replace(
            "combined with Card Type codes 00 and 81–97.",
            "combined with any Card Type code.", 1)
        with self.assertRaises(ValueError):
            self.derive(new=mutated)

    def test_missing_legacy_assertion_or_source_unit_is_rejected(self):
        legacy = copy.deepcopy(self.legacy)
        legacy.remove(next(row for row in legacy
                          if row["legacyBrId"] == "BR-APPG-CODE-9"))
        with self.assertRaises(ValueError):
            self.derive(legacy=legacy)
        units = copy.deepcopy(self.units)
        units.remove(next(row for row in units if row["page"] == "546"))
        with self.assertRaises(ValueError):
            self.derive(units=units)

    def test_changed_catalog_classification_or_source_anchor_is_rejected(self):
        with self.assertRaises(ValueError):
            self.derive(catalog=self.catalog.replace(
                'Scope.SPECIALIZED);', 'Scope.STANDARD_FINANCIAL);', 1))
        legacy = copy.deepcopy(self.legacy)
        next(row for row in legacy if row["legacyBrId"] == "BR-APPG-CODE-9")["title"] = "Code 9 is financial."
        with self.assertRaises(ValueError):
            self.derive(legacy=legacy)


if __name__ == "__main__":
    unittest.main()
