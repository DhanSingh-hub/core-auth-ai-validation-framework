import json
import runpy
import tempfile
import unittest
from pathlib import Path


SCRIPTS = Path(__file__).resolve().parent
BUILDER = runpy.run_path(str(SCRIPTS / "build-location-scoped-requirement-inventory.py"))

LAYOUT_TEXT = """--- PAGE 10 ---
11.1.2 Response
Message Format
Sample Response
Data Section No. 1
Field
No.
Element
No.
Element Name
1 83 Response
Code
1 1 R Identifies the type of response.
2 51 Mail Text Data 2 Var. C Identifies the text.
3 194 CA Public
Key File Block
3 9,999 R Identifies the block.
4
or
4-5
101 Terminal
Display/Printer
Message
71 31 R Repeated display message.
Data Section No. 2
Field
No.
Seg.
No.
Segment Name
5 112 Additional Information Data Segment 984 O Optional segment.
1Required/Optional/Conditional
"""

DICTIONARY_TEXT = """--- PAGE 20 ---
13.2 Data Elements
Number: 6 Name: Approved Amount
Character Type: N Maximum Length: 9 bytes
Number: 43 Name: Hardware Version
Character Type: AN Maximum Length: 4/8 bytes
Number: 165 Name: Start Date or End Date
Character Type: N Length: 8 bytes
Number: 202 Name: Cryptogram Token
Data
Character Type: B64 Variable Length: 28/56 bytes
Number: 2 Name: Account Number
Character Type: ANS Maximum Length:
Number: 118 Name: Additional Information
Character Type: AN Maximum Length: 984 bytes
Number: 118 Name: EMV Additional
Information
Character Type: ANSB Maximum Length: 984 bytes
"""


class LayoutParserTest(unittest.TestCase):
    def test_extracts_segment_free_elements_and_keeps_segments_separate(self):
        (layout,) = BUILDER["parse_layouts"](LAYOUT_TEXT.splitlines())
        self.assertEqual((layout["section"], layout["message"]), ("11.1.2", "Sample Response"))
        rows = {row["field"]: row for row in layout["rows"]}
        self.assertEqual([row["field"] for row in layout["rows"]], [1, 2, 3, 4, 5])
        self.assertEqual((rows[1]["number"], rows[1]["name"], rows[1]["length"], rows[1]["entry"]), ("83", "Response Code", 1, "REQUIRED"))
        self.assertEqual((rows[2]["length"], rows[2]["entry"]), ("VARIABLE", "CONDITIONAL"))
        self.assertEqual((rows[3]["name"], rows[3]["length"]), ("CA Public Key File Block", 9999))
        self.assertTrue(rows[4]["repeating"])
        self.assertEqual((rows[4]["number"], rows[4]["position"], rows[4]["length"]), ("101", 71, 31))
        self.assertEqual((rows[5]["kind"], rows[5]["number"], rows[5]["dataSection"]), ("SEGMENT", "112", "2"))
        self.assertTrue(all(row["dataSection"] == "1" for row in layout["rows"][:4]))

    def test_ignores_message_format_headings_outside_chapter_11(self):
        self.assertEqual(BUILDER["parse_layouts"]("13.1 Data\nMessage Format\nVersion Identifier\n".splitlines()), [])


class DictionaryParserTest(unittest.TestCase):
    def test_parses_type_and_length_variants(self):
        definitions = {(item["element"], item["name"]): item for item in BUILDER["parse_element_dictionary"](DICTIONARY_TEXT.splitlines())}
        self.assertEqual(definitions[("6", "Approved Amount")]["lengthKind"], "MAXIMUM")
        self.assertEqual(definitions[("43", "Hardware Version")]["length"], "4/8")
        self.assertEqual(definitions[("165", "Start Date or End Date")]["lengthKind"], "STATED")
        self.assertEqual(definitions[("202", "Cryptogram Token Data")]["characterType"], "B64")
        self.assertIsNone(definitions[("2", "Account Number")]["length"])
        self.assertEqual(sum(element == "118" for element, _ in definitions), 2)


class InventoryBuildTest(unittest.TestCase):
    def test_pinned_source_derivation_matches_committed_inventory(self):
        inventory = BUILDER["build"]()
        committed = json.loads(BUILDER["OUTPUT_JSON"].read_text(encoding="utf-8"))
        self.assertEqual(BUILDER["serialize"](inventory), BUILDER["OUTPUT_JSON"].read_text(encoding="utf-8"))
        self.assertEqual(committed["authority"], "INDEPENDENT_TEST_SOLUTION")
        self.assertFalse(committed["aiArtifactsIncluded"])
        self.assertEqual(committed["baselineInventory"]["ruleCount"], 601)
        self.assertTrue(all(rule["sourceAnchor"]["segment"] is None for rule in committed["businessRequirements"]))
        rules = {rule["ruleId"]: rule for rule in committed["businessRequirements"]}
        self.assertIn("LOC-11.1.2-F01-E083", rules)
        self.assertIn("LOC-11.1.2-F15-E101", rules)
        self.assertEqual(rules["LOC-11.1.1-F01-E055"]["relatedBaselineRuleIds"], ["SEG100-R-013"])
        self.assertEqual(rules["ELEM-014-DEF"]["elementAlsoSegmentScopedIn"], ["DL1", "DL6"])
        self.assertIn("ELEM-118-DEF-B", rules)
        self.assertEqual(committed["validation"]["attributeReviewRequired"], ["ELEM-002-DEF"])
        self.assertEqual(set(committed["layoutReferences"]), {"11.2.2", "11.3.2"})
        self.assertIn("11.7.5", committed["outOfScopeSections"])

    def test_rejects_unpinned_source_text(self):
        with tempfile.TemporaryDirectory() as temporary:
            source = Path(temporary) / "extracted_text.txt"
            source.write_text(LAYOUT_TEXT, encoding="utf-8")
            with self.assertRaisesRegex(ValueError, "not the pinned"):
                BUILDER["build"](source)

    def test_check_mode_detects_stale_output(self):
        with tempfile.TemporaryDirectory() as temporary:
            json_path, markdown_path = Path(temporary) / "inventory.json", Path(temporary) / "inventory.md"
            arguments = ["--output-json", str(json_path), "--output-markdown", str(markdown_path)]
            self.assertEqual(BUILDER["main"](arguments), 0)
            self.assertEqual(BUILDER["main"](arguments + ["--check"]), 0)
            json_path.write_text("{}\n", encoding="utf-8")
            self.assertEqual(BUILDER["main"](arguments + ["--check"]), 1)


class AuditorLocationAwareTest(unittest.TestCase):
    def test_auditor_self_test_covers_location_aware_matching(self):
        runpy.run_path(str(SCRIPTS / "audit-ai-delivery-coverage.py"))["self_test"]()


if __name__ == "__main__":
    unittest.main()
