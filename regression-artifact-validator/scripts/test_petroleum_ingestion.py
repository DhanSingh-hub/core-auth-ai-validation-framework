import copy
import hashlib
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch


SCRIPT = Path(__file__).with_name("ingest-petroleum-spec.py")
SPEC = importlib.util.spec_from_file_location("petroleum_ingestion", SCRIPT)
review = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(review)


class PetroleumIngestionTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.outputs = review.build()
        cls.config = json.loads(review.INPUT.read_text(encoding="utf-8"))
        cls.document = json.loads(cls.outputs["pages.json"])
        cls.pages = cls.document["pages"]
        cls.training = json.loads(cls.outputs["training-lessons.json"])

    def test_source_identity_and_complete_page_coverage(self):
        self.assertEqual(len(self.pages), 226)
        self.assertEqual([p["pdfPage"] for p in self.pages], list(range(1, 227)))
        self.assertEqual(len({p["id"] for p in self.pages}), 226)
        self.assertEqual(self.document["source"]["version"], "2025-1")
        self.assertEqual(hashlib.sha256(review.PDF.read_bytes()).hexdigest(), self.config["sourceSha256"])
        for p in self.pages:
            self.assertEqual(p["textSha256"], review.fingerprint(p["text"].encode("utf-8")))
        manifest = json.loads(self.outputs["ingestion-manifest.json"])
        self.assertEqual(manifest["textPageCount"] + len(manifest["emptyPagesRequiringReview"]), 226)
        self.assertEqual(manifest["chapterCount"], 17)
        self.assertEqual(manifest["appendixCount"], 13)

    def test_draft_training_is_bounded_and_citations_resolve(self):
        self.assertEqual(len(self.training["lessons"]), 13)
        self.assertEqual(self.training["status"], "DRAFT_REVIEW_REQUIRED")
        self.assertFalse(self.training["certified"])
        self.assertFalse(self.training["executionReady"])
        self.assertFalse(self.training["baselineChanged"])
        self.assertFalse(self.training["modelWeightsChanged"])
        for key in ("independentBrCount", "testScenarioCount", "testCaseCount", "testDataCount", "executedCaseCount"):
            self.assertEqual(self.training[key], 0)
        for lesson in self.training["lessons"]:
            self.assertEqual(lesson["status"], review.STATUS)
            self.assertTrue(lesson["scope"])
            for c in lesson["evidence"]:
                p = self.pages[c["pdfPage"] - 1]
                self.assertIn(review.normalized(c["quote"]), review.normalized(p["text"]))
                self.assertEqual(c["textSha256"], p["textSha256"])
                self.assertEqual(c["pageId"], p["id"])
                self.assertEqual(c["sourceSha256"], self.config["sourceSha256"])

    def test_document_versions_and_appendices_remain_separate(self):
        compatibility = self.training["atl105Compatibility"]
        self.assertEqual(compatibility["trainedBaseline"], "2026-3")
        self.assertEqual(compatibility["availableRelease"], "2026-4")
        self.assertEqual(compatibility["crossVersionReconciliation"], "REVIEW_REQUIRED")
        self.assertIn("NEVER_ALIAS", compatibility["appendixIdentity"])
        self.assertEqual(self.training["source"]["version"], "2025-1")

    def test_missing_quote_and_wrong_page_fail(self):
        for mutate in (
            lambda c: c["lessons"][0]["evidence"][0].update(quote="UNSUPPORTED INVENTED PETROLEUM RULE"),
            lambda c: c["lessons"][0]["evidence"][0].update(pdfPage=0),
            lambda c: c["lessons"][0]["evidence"][0].update(pdfPage=227),
            lambda c: c["lessons"][0]["evidence"][0].update(quote=""),
        ):
            config = copy.deepcopy(self.config)
            mutate(config)
            with self.assertRaises(ValueError):
                review.build_lessons(config, self.pages)

    def test_empty_duplicate_and_promoted_lessons_fail(self):
        for mutate in (
            lambda c: c.update(status="APPROVED"),
            lambda c: c.update(version="2026-4"),
            lambda c: c.update(schemaVersion=2),
            lambda c: c.update(lessons=[]),
            lambda c: c["lessons"].append(c["lessons"][0]),
            lambda c: c["lessons"][0].update(statement=""),
            lambda c: c["lessons"][0].update(evidence=[]),
            lambda c: c["openQuestions"][0].update(status="APPROVED"),
        ):
            config = copy.deepcopy(self.config)
            mutate(config)
            with self.assertRaises(ValueError):
                review.build_lessons(config, self.pages)

    def test_changed_source_fails_before_extraction(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / "changed.pdf"
            path.write_bytes(b"not the registered source")
            with patch.object(review, "PdfReader") as reader:
                with self.assertRaisesRegex(ValueError, "fingerprint"):
                    review.extract_source(path, self.config)
                reader.assert_not_called()

    def test_manifest_fingerprints_cover_generated_evidence(self):
        manifest = json.loads(self.outputs["ingestion-manifest.json"])
        self.assertEqual(manifest["inputSha256"], review.fingerprint(review.INPUT.read_bytes()))
        self.assertEqual(manifest["generatorSha256"], review.fingerprint(SCRIPT.read_bytes()))
        for name, digest in manifest["outputs"].items():
            self.assertEqual(digest, review.fingerprint(self.outputs[name].encode("utf-8")))

    def test_stale_and_missing_outputs_are_detected_without_writes(self):
        with tempfile.TemporaryDirectory() as directory:
            folder = Path(directory)
            with self.assertRaisesRegex(ValueError, "Missing or stale"):
                review.check_outputs(self.outputs, folder)
            for name, text in self.outputs.items():
                (folder / name).write_text(text, encoding="utf-8")
            review.check_outputs(self.outputs, folder)
            (folder / "training-lessons.json").write_text("{}", encoding="utf-8")
            before = {p.name: p.read_bytes() for p in folder.iterdir()}
            with self.assertRaisesRegex(ValueError, "training-lessons.json"):
                review.check_outputs(self.outputs, folder)
            self.assertEqual(before, {p.name: p.read_bytes() for p in folder.iterdir()})

    def test_lexical_retrieval_returns_page_evidence_not_approval(self):
        hits = review.search_pages("MasterCard expiration", self.pages)
        self.assertTrue(hits)
        for hit in hits:
            self.assertEqual(hit["status"], "RETRIEVAL_CANDIDATE_NOT_VALIDATED_RULE")
            self.assertTrue(hit["excerpt"])
            self.assertTrue(1 <= hit["pdfPage"] <= 226)
        self.assertEqual(review.search_pages("unfindablezzzzpetroleum", self.pages), [])
        with self.assertRaises(ValueError):
            review.search_pages("  !!! ", self.pages)

    def test_lexical_query_requires_whole_words_and_all_terms(self):
        sample = [{"pdfPage": 1, "printedPage": "1-1", "id": "p1",
                   "textSha256": "hash", "text": "Fleet authorization and receipt"},
                  {"pdfPage": 2, "printedPage": "1-2", "id": "p2",
                   "textSha256": "hash", "text": "fleet prepaid"}]
        self.assertEqual(len(review.search_pages("fleet receipt", sample)), 1)
        self.assertEqual(review.search_pages("auth", sample), [])
        self.assertEqual(review.search_pages("receipt prepaid", sample), [])

    def test_registered_launcher_and_training_status_are_review_only(self):
        tasks = json.loads((review.MODULE / "framework-tasks.json").read_text(encoding="utf-8"))
        by_name = {t["name"]: t for t in tasks["tasks"]}
        for name in ("petroleum-ingest-build", "petroleum-ingest-check", "petroleum-ingest-test"):
            self.assertIn(name, by_name)
            self.assertTrue((review.MODULE / by_name[name]["script"]).is_file())
        status = json.loads((review.PACK / "training-status.json").read_text(encoding="utf-8"))
        entry = status["supplementalSourceTraining"]["PETROLEUM_2025_1"]
        self.assertTrue(entry["reviewRequired"])
        self.assertFalse(entry["executionCertified"])
        self.assertTrue(entry["countsExcludedFromSegmentCatalogSummary"])
        self.assertEqual(status["specificationVersion"], "2026-3")
        for key in ("source", "evidence", "trainingInput", "generator", "regressionTest",
                    "atl105ReferenceMap", "atl105ReferenceReport",
                    "atl105ReferenceGenerator", "atl105ReferenceRegressionTest"):
            self.assertTrue((review.MODULE / entry[key]).is_file(), key)
        self.assertEqual(entry["atl105ReferenceStatus"], "DRAFT_REVIEW_REQUIRED")
        self.assertEqual(entry["atl105ReferenceOccurrencesPerRelease"], 13)
        evidence = review.MODULE / entry["evidence"]
        self.assertEqual(evidence.resolve(), (review.KB / "training-lessons.json").resolve())

    def test_published_outputs_match_current_source_and_generator(self):
        review.check_outputs(self.outputs, review.KB)


if __name__ == "__main__":
    unittest.main()
