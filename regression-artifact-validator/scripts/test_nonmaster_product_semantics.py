"""Verify complete scoped derivation, exact classification comparisons and explicit unfinished work."""

import copy
import importlib.util
import unittest
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "nonmaster_product_review", Path(__file__).with_name("derive-nonmaster-product-semantics.py"))
model = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(model)


class ProductSemanticTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.scope = model.base.gates.load(model.SCOPE)
        cls.old = model.base.OLD_SOURCE.read_text(encoding="utf-8-sig")
        cls.new = model.base.gates.SOURCE.read_text(encoding="utf-8-sig")
        cls.oracle = model.base.gates.load(model.ORACLE)
        cls.legacy = model.parent.read_csv(model.parent.OUTPUT / "legacy-semantic-register.csv")
        cls.units = model.parent.read_csv(model.parent.v1.OUTPUT / "source-unit-register.csv")
        cls.current = model.base.gates.load(model.completion.OUTPUT / "model-package.json")
        cls.companions = model.base.gates.load(model.petroleum.DECISIONS)
        sources = model.completion.fleet.load_sources()
        cfg = model.base.gates.load(model.completion.fleet.KB / "visa-fleet" / "context-input.json")
        cls.numeric_petroleum_rows = model.completion.fleet.validate_product_codes(
            cfg["catalog"]["productCodes"], sources)["rows"]
        cls.sources = sources
        cls.petroleum_rows = model.complete_petroleum_inventory(cls.numeric_petroleum_rows, sources)

    def derive(self, scope=None, old=None, new=None, oracle=None, legacy=None, units=None, companions=None):
        return model.derive(
            self.scope if scope is None else scope, self.old if old is None else old,
            self.new if new is None else new, self.oracle if oracle is None else oracle,
            self.legacy if legacy is None else legacy, self.units if units is None else units,
            self.current, self.companions if companions is None else companions)

    def test_exact_row_population_and_nonacceptance_boundary(self):
        rows, _, _, _ = self.derive()
        self.assertEqual(len(rows), 380)
        self.assertEqual({row["legacyBrId"] for row in rows},
                         {row["legacyBrId"] for row in self.legacy
                          if row["legacyBrId"].startswith("BR-APPF-ROW-")})
        for row in rows:
            self.assertEqual(row["transactionAcceptance"], "NOT_ESTABLISHED_BY_CLASSIFICATION")
            self.assertFalse(row["atomicBusinessDenominator"])
            self.assertIsNone(row["approvedDenominatorDelta"])
            self.assertEqual(row["otherLegacyOverlap"], "NOT_ADJUDICATED_OUTSIDE_THIS_BATCH")

    def test_every_code_and_every_row_in_both_releases(self):
        for release in self.scope["releases"]:
            text = self.old if release["version"] == "2026-3" else self.new
            rows, lines, obligations = model.parse_appendix(text, release, self.scope)
            codes = [number for row in rows
                     for number in range(int(row["startCode"]), int(row["endCode"]) + 1)]
            self.assertEqual(sorted(codes), list(range(1000)))
            self.assertEqual(len(rows), 380)
            self.assertEqual(len(lines), 630)
            self.assertEqual(len(obligations), 5)

    def test_every_retained_line_in_all_23_units_accounted_for(self):
        _, units, _, _ = self.derive()
        self.assertEqual(len(units), 23)
        for unit in units:
            lines = unit["classifications"]
            self.assertEqual(len({line["line"] for line in lines}), unit["classifiedLineCount"])
            self.assertTrue(all(line["kind"] in {
                "CLASSIFICATION_ROW", "CLASSIFICATION_ROW_CONTINUATION",
                "DERIVED_PROSE_OBLIGATION", "REPEATED_OBLIGATION",
                "SOURCE_HEADING_OR_CATALOG_INDEX"} for line in lines))
            self.assertEqual(unit["approvalStatus"], "DRAFT_NOT_APPROVED")
            self.assertEqual(unit["derivationStatus"], "ALL_RETAINED_LINES_DERIVED_OR_NON_OBLIGATION_CLASSIFIED")
        self.assertEqual(sum(row["classifiedLineCount"] for row in units), 608)

    def test_unknown_text_cannot_disappear_into_header_or_obligation(self):
        for old, new in (
                ("Product Code Description", "Product Code Description\nNew requirement must be followed"),
                ("Code assigned to it in the following tables.",
                 "An additional hidden obligation\nCode assigned to it in the following tables."),
                ("The following table contains motor fuel codes:",
                 "The following table contains mandatory rejected codes:"),
                ("\u2022 Motor fuel codes (Code Nos. 001\u2013099)",
                 "\u2022 Motor fuel codes (Code Nos. 001\u2013099) must never be sent")):
            with self.subTest(old=old), self.assertRaises(ValueError):
                self.derive(new=self.new.replace(old, new))

    def test_missing_duplicate_overlapping_or_changed_rows_rejected(self):
        for replacement in ("", "001 Unleaded (88 octane or less)\n001 Duplicate",
                            "002 Unleaded (88 octane or less)", "001 Changed source description"):
            new = self.new.replace("001 Unleaded (88 octane or less)", replacement)
            with self.subTest(replacement=replacement), self.assertRaises(ValueError):
                self.derive(new=new)

    def test_original_oracle_description_anchor_assignment_and_role_are_checked(self):
        for key, value in (("description", "Changed description"), ("assignment", "RESERVED"),
                           ("role", "NEGATIVE"), ("table", "OTHER_FUEL")):
            oracle = copy.deepcopy(self.oracle)
            oracle["rows"][1][key] = value
            with self.subTest(key=key), self.assertRaises(ValueError):
                self.derive(oracle=oracle)
        oracle = copy.deepcopy(self.oracle)
        oracle["rows"][1]["sourceAnchor"]["startLine"] += 1
        with self.assertRaises(ValueError):
            self.derive(oracle=oracle)

    def test_missing_or_changed_legacy_assertion_cannot_be_adjudicated(self):
        for action in ("missing", "title", "anchor"):
            legacy = copy.deepcopy(self.legacy)
            target = next(row for row in legacy if row["legacyBrId"] == "BR-APPF-ROW-001")
            if action == "missing":
                legacy.remove(target)
            elif action == "title":
                target["title"] = "Code 001 is accepted for every fleet transaction."
            else:
                target["sourceAnchors"] = "[]"
            with self.subTest(action=action), self.assertRaises(ValueError):
                self.derive(legacy=legacy)

    def test_all_132_counterparts_are_partitioned_once(self):
        _, _, _, groups = self.derive()
        members = [identity for row in groups for identity in row["ids"]]
        self.assertEqual(len(members), 132)
        self.assertEqual(len(set(members)), 132)
        expected = {row["id"] for row in self.current["businessRequirements"]}
        expected.update(row["id"] for row in self.companions["rules"])
        self.assertEqual(set(members), expected)
        for row in groups:
            self.assertTrue(row["reason"])
            self.assertNotIn("EQUIVALENT", row["relation"])

    def test_missing_duplicate_or_unknown_companion_comparison_rejected(self):
        for action in ("missing", "duplicate", "unknown", "current"):
            scope = copy.deepcopy(self.scope)
            group = scope["companionComparison"][-1]
            if action == "missing":
                group["ids"].pop()
            elif action == "duplicate":
                group["ids"].append(group["ids"][0])
            elif action == "unknown":
                group["ids"][0] = "VFC-BR-999"
            else:
                scope["currentComparison"]["programEligibility"].pop()
            with self.subTest(action=action), self.assertRaises(ValueError):
                self.derive(scope=scope)

    def test_reserved_table_assigned_rows_not_false_blanket_reservations(self):
        rows, _, _, _ = self.derive()
        lookup = {row["legacyBrId"]: row for row in rows}
        for code in ("600", "601", "624", "650", "657"):
            self.assertEqual(lookup["BR-APPF-ROW-" + code]["assignment"], "ASSIGNED")
        for code in ("606", "625", "658", "700", "810", "897"):
            self.assertEqual(lookup["BR-APPF-ROW-" + code]["assignment"], "RESERVED")
        self.assertEqual(lookup["BR-APPF-ROW-071"]["assignment"], "UNDEFINED")
        self.assertEqual(lookup["BR-APPF-ROW-000"]["assignment"], "UNUSED")

    def test_surcharge_row_roles_not_all_debit_or_all_administrative(self):
        rows, _, obligations, _ = self.derive()
        lookup = {row["legacyBrId"]: row for row in rows}
        self.assertEqual(lookup["BR-APPF-ROW-800"]["role"], "ADMINISTRATIVE")
        self.assertEqual(lookup["BR-APPF-ROW-803"]["role"], "MERCHANDISE")
        self.assertEqual(lookup["BR-APPF-ROW-807"]["role"], "MOTOR_FUEL")
        self.assertEqual(lookup["BR-APPF-ROW-809"]["role"], "NEGATIVE")
        code800 = next(row for row in obligations if row["id"] == "NMP-O04")
        self.assertIn("credit/debit", code800["statement"])

    def test_source_conflict_cannot_be_silently_removed(self):
        rows, units, _, _ = self.derive()
        disputed = [row for row in rows if row["disputedCodes"]]
        self.assertEqual({row["legacyBrId"] for row in disputed},
                         {"BR-APPF-ROW-895", "BR-APPF-ROW-896"})
        self.assertTrue(all(row["sourcePrecedence"] == "REVIEW_REQUIRED" for row in disputed))
        self.assertEqual({row["page"] for row in units if row["sourceConflicts"]}, {523, 541, 542})
        scope = copy.deepcopy(self.scope)
        scope["conflict"]["codes"] = []
        with self.assertRaises(ValueError):
            self.derive(scope=scope)

    def test_unknown_source_unit_drift_rejected(self):
        units = copy.deepcopy(self.units)
        row = next(item for item in units if item["page"] == "523")
        row["sha256"] = "0" * 64
        with self.assertRaisesRegex(ValueError, "source-unit"):
            self.derive(units=units)
        units = [row for row in self.units if row["page"] != "523"]
        with self.assertRaisesRegex(ValueError, "23 retained"):
            self.derive(units=units)

    def test_all_1000_codes_compared_to_petroleum_without_acceptance(self):
        records, _, _, _ = self.derive()
        rows = model.petroleum_code_crosswalk(records, self.petroleum_rows)
        self.assertEqual(len(rows), 1000)
        self.assertEqual({row["code"] for row in rows if row["petroleumRow"] is None},
                         {f"{value:03}" for value in range(541, 550)})
        self.assertTrue(all(not row["automaticMerge"] for row in rows))
        self.assertTrue(all(row["cardProductPermission"] == "NOT_ESTABLISHED_BY_EITHER_CATALOG" for row in rows))
        self.assertEqual({row["code"] for row in rows if row["disputedAtl105Classification"]}, {"895", "896"})

    def test_petroleum_overlap_and_missing_code_differences_rejected(self):
        records, _, _, _ = self.derive()
        with self.assertRaisesRegex(ValueError, "overlap"):
            model.petroleum_code_crosswalk(records, self.petroleum_rows + [self.petroleum_rows[0]])
        with self.assertRaisesRegex(ValueError, "differences changed"):
            model.petroleum_code_crosswalk(records, self.petroleum_rows[1:])

    def test_prose_reservations_not_confused_with_missing_numeric_rows(self):
        records, _, _, _ = self.derive()
        rows = model.petroleum_code_crosswalk(records, self.petroleum_rows)
        prose = [row for row in rows if row["petroleumRow"] is not None
                 and row["petroleumRow"]["extractionKind"] == "PROSE_DECLARED_RESERVATION"]
        self.assertEqual(len(prose), 83)
        self.assertEqual({row["code"] for row in prose},
                         {f"{value:03}" for value in [*range(810, 890), *range(897, 900)]})
        sources = copy.deepcopy(self.sources)
        sources["pages"][165]["text"] = sources["pages"][165]["text"].replace(
            "Code nos. 810\u2013889 are reserved for proprietary use.", "No reservation specified")
        with self.assertRaisesRegex(ValueError, "prose reservation"):
            model.complete_petroleum_inventory(self.numeric_petroleum_rows, sources)

    def test_published_integration_retains_global_incomplete_status(self):
        module = Path(__file__).resolve().parent.parent
        tasks = model.base.gates.load(module / "framework-tasks.json")["tasks"]
        selected = [task for task in tasks if task["name"].startswith("nonmaster-product-semantics-")]
        self.assertEqual(len(selected), 3)
        for task in selected:
            self.assertTrue((module / task["script"]).is_file())
        summary = model.base.gates.load(model.OUTPUT / "summary.json")
        self.assertEqual(summary["counts"]["legacyClassificationAssertions"], 380)
        self.assertEqual(summary["counts"]["remainingLegacyWithoutNewOrPriorDecisions"], 946)
        self.assertEqual(summary["counts"]["remainingSourceUnits"], 2913)
        for key in ("wholeSpecSemanticsComplete", "allLegacyOverlapComplete",
                    "petroleumToAllLegacyReviewComplete", "executionReady", "baselineChanged"):
            self.assertFalse(summary[key])
        self.assertIsNone(summary["approvedDenominator"])
        self.assertIsNone(summary["confirmedRequirementCoveragePercent"])


if __name__ == "__main__":
    unittest.main()
