import hashlib
import importlib.util
import json
import unittest
from pathlib import Path


SCRIPT = Path(__file__).with_name("build-iin-atl105-crossrefs.py")
SPEC = importlib.util.spec_from_file_location("iin_atl105_crossrefs", SCRIPT)
crossrefs = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(crossrefs)


class IinAtl105CrossReferencesTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.outputs = crossrefs.build()
        cls.training = json.loads(cls.outputs[crossrefs.OUTPUTS[0]])

    def test_single_reference_is_located_in_both_releases(self):
        refs = self.training["references"]
        self.assertEqual(len(refs), 1)
        self.assertEqual(self.training["referenceCountPerRelease"], 1)
        self.assertEqual(refs[0]["id"], "ATL105-IIN-REF-E2")
        for version, evidence in refs[0]["atl105Evidence"].items():
            source = crossrefs.SOURCES[version]
            extracted = source["text"].read_bytes()
            lines = extracted.decode("utf-8").splitlines()
            marker = f"--- PAGE {evidence['physicalPdfPage']} ---"
            marker_line = lines.index(marker)
            end = next((i for i in range(marker_line + 1, len(lines)) if lines[i].startswith("--- PAGE ")), len(lines))
            page_text = "\n".join(lines[marker_line + 1:end])
            self.assertEqual(evidence["pageTextSha256"], crossrefs.sha256(page_text.encode("utf-8")))
            self.assertIn(crossrefs.normalized(evidence["sourceQuote"]), crossrefs.normalized(page_text))
        self.assertEqual(refs[0]["atl105Evidence"]["2026-3"]["physicalPdfPage"], 358)
        self.assertEqual(refs[0]["atl105Evidence"]["2026-4"]["physicalPdfPage"], 365)
        self.assertEqual(refs[0]["atl105Evidence"]["2026-4"]["printedPage"], "13-25")

    def test_cross_reference_training_preserves_scope_and_review_gates(self):
        self.assertEqual(self.training["status"], "DRAFT_REVIEW_REQUIRED")
        constraints = self.training["trainingInterpretation"]["constraints"]
        self.assertTrue(any("credit, debit and EBT" in item for item in constraints))
        self.assertTrue(any("most recent" in item for item in constraints))
        self.assertEqual(self.training["authority"],
                         "ATL105_REFERENCE_LOCATOR_AND_TRAINING_CUE_NOT_AN_APPROVED_REQUIREMENT_ORACLE")

    def test_source_pdfs_and_extracted_text_are_fingerprinted(self):
        for version, source in crossrefs.SOURCES.items():
            fingerprint = self.training["sourceFingerprints"][version]
            self.assertEqual(fingerprint["pdfSha256"], hashlib.sha256(source["pdf"].read_bytes()).hexdigest())
            self.assertEqual(fingerprint["extractedTextSha256"],
                             hashlib.sha256(source["text"].read_bytes()).hexdigest())

    def test_generated_files_match_the_published_evidence(self):
        crossrefs.check_outputs(self.outputs)
        with self.assertRaisesRegex(ValueError, "atl105-iin-cross-references.md"):
            altered = dict(self.outputs)
            altered[crossrefs.OUTPUTS[1]] = "stale"
            crossrefs.check_outputs(altered)

    def test_wrong_page_or_quote_fails_to_resolve(self):
        broken = dict(crossrefs.definitions()[0])
        broken["expectedPdfPages"] = {"2026-3": 1, "2026-4": 1}
        source_pages = crossrefs.pages(crossrefs.SOURCES["2026-3"]["text"].read_text(encoding="utf-8"))
        with self.assertRaises(ValueError):
            crossrefs.find_reference_page(source_pages, broken, "2026-3")


if __name__ == "__main__":
    unittest.main()
