import hashlib
import importlib.util
import json
import unittest
from pathlib import Path


SCRIPT = Path(__file__).with_name("build-petroleum-atl105-crossrefs.py")
SPEC = importlib.util.spec_from_file_location("petroleum_atl105_crossrefs", SCRIPT)
crossrefs = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(crossrefs)


class PetroleumAtl105CrossReferencesTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.outputs = crossrefs.build()
        cls.training = json.loads(cls.outputs[crossrefs.OUTPUTS[0]])

    def test_all_thirteen_references_are_located_in_both_releases(self):
        refs = self.training["references"]
        self.assertEqual(len(refs), 13)
        self.assertEqual(self.training["referenceCountPerRelease"], 13)
        self.assertEqual(
            [ref["id"] for ref in refs],
            ["ATL105-PETRO-REF-10-6", "ATL105-PETRO-REF-E2"]
            + [f"ATL105-PETRO-REF-E{number}" for number in
               (31, 40, 47, 64, 108, 158, 159, 160, 161, 162, 163)],
        )
        for ref in refs:
            self.assertEqual(ref["status"], "DRAFT_REVIEW_REQUIRED")
            for version, evidence in ref["atl105Evidence"].items():
                source = crossrefs.SOURCES[version]
                extracted = source["text"].read_bytes()
                lines = extracted.decode("utf-8").splitlines()
                marker = f"--- PAGE {evidence['physicalPdfPage']} ---"
                marker_line = lines.index(marker)
                end = next((i for i in range(marker_line + 1, len(lines)) if lines[i].startswith("--- PAGE ")), len(lines))
                page_text = "\n".join(lines[marker_line + 1:end])
                self.assertEqual(evidence["pageTextSha256"], crossrefs.sha256(page_text.encode("utf-8")))
                self.assertIn(crossrefs.normalized(evidence["sourceQuote"]), crossrefs.normalized(page_text))
        self.assertEqual(
            self.training["references"][0]["atl105Evidence"]["2026-4"]["printedPage"], "10-44"
        )
        self.assertEqual(
            self.training["references"][1]["atl105Evidence"]["2026-4"]["printedPage"], "13-25"
        )

    def test_cross_reference_training_preserves_scope_and_review_gates(self):
        self.assertEqual(self.training["status"], "DRAFT_REVIEW_REQUIRED")
        self.assertIn("separately versioned", self.training["trainingInterpretation"]["rule"])
        constraints = self.training["trainingInterpretation"]["constraints"]
        self.assertTrue(any("credit/debit/EBT" in item for item in constraints))
        self.assertTrue(any("Segment 101" in item for item in constraints))
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
        with self.assertRaisesRegex(ValueError, "atl105-petroleum-cross-references.md"):
            altered = dict(self.outputs)
            altered[crossrefs.OUTPUTS[1]] = "stale"
            crossrefs.check_outputs(altered)


if __name__ == "__main__":
    unittest.main()
