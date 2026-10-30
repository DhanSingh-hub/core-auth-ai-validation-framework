import copy
import hashlib
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch


SCRIPT = Path(__file__).with_name("ingest-iin-reference.py")
SPEC = importlib.util.spec_from_file_location("iin_ingestion", SCRIPT)
ingest = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(ingest)


class IinIngestionTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.outputs = ingest.build()
        cls.config = json.loads(ingest.INPUT.read_text(encoding="utf-8"))
        cls.document = json.loads(cls.outputs["pages.json"])
        cls.pages = cls.document["pages"]
        cls.training = json.loads(cls.outputs["training-lessons.json"])

    def test_source_identity_and_complete_page_coverage(self):
        self.assertEqual(len(self.pages), 10)
        self.assertEqual([p["pdfPage"] for p in self.pages], list(range(1, 11)))
        self.assertEqual(len({p["id"] for p in self.pages}), 10)
        self.assertEqual(self.document["source"]["version"], "2024-1")
        self.assertEqual(hashlib.sha256(ingest.PDF.read_bytes()).hexdigest(), self.config["sourceSha256"])
        for p in self.pages:
            self.assertEqual(p["textSha256"], ingest.fingerprint(p["text"].encode("utf-8")))
        manifest = json.loads(self.outputs["ingestion-manifest.json"])
        self.assertEqual(manifest["textPageCount"] + len(manifest["emptyPagesRequiringReview"]), 10)
        self.assertEqual(manifest["bookmarkCount"], 3)

    def test_outline_bookmarks_match_registered_source(self):
        outline = json.loads(self.outputs["source-index.json"])["outline"]
        self.assertEqual([(o["title"], o["pdfPage"]) for o in outline],
                          [(e["title"], e["pdfPage"]) for e in self.config["expectedOutline"]])

    def test_draft_training_is_bounded_and_citations_resolve(self):
        self.assertEqual(len(self.training["lessons"]), 8)
        self.assertEqual(self.training["status"], "DRAFT_REVIEW_REQUIRED")
        self.assertFalse(self.training["certified"])
        self.assertFalse(self.training["executionReady"])
        self.assertFalse(self.training["baselineChanged"])
        self.assertFalse(self.training["modelWeightsChanged"])
        for key in ("independentBrCount", "testScenarioCount", "testCaseCount", "testDataCount", "executedCaseCount"):
            self.assertEqual(self.training[key], 0)
        for lesson in self.training["lessons"]:
            self.assertEqual(lesson["status"], ingest.STATUS)
            self.assertTrue(lesson["scope"])
            for c in lesson["evidence"]:
                p = self.pages[c["pdfPage"] - 1]
                self.assertIn(ingest.normalized(c["quote"]), ingest.normalized(p["text"]))
                self.assertEqual(c["textSha256"], p["textSha256"])
                self.assertEqual(c["pageId"], p["id"])
                self.assertEqual(c["sourceSha256"], self.config["sourceSha256"])

    def test_missing_quote_and_wrong_page_fail(self):
        for mutate in (
            lambda c: c["lessons"][0]["evidence"][0].update(quote="UNSUPPORTED INVENTED IIN RULE"),
            lambda c: c["lessons"][0]["evidence"][0].update(pdfPage=0),
            lambda c: c["lessons"][0]["evidence"][0].update(pdfPage=11),
            lambda c: c["lessons"][0]["evidence"][0].update(quote=""),
        ):
            config = copy.deepcopy(self.config)
            mutate(config)
            with self.assertRaises(ValueError):
                ingest.build_lessons(config, self.pages)

    def test_empty_duplicate_and_promoted_lessons_fail(self):
        for mutate in (
            lambda c: c.update(status="APPROVED"),
            lambda c: c.update(version="2021-2"),
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
                ingest.build_lessons(config, self.pages)

    def test_changed_source_fails_before_extraction(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / "changed.pdf"
            path.write_bytes(b"not the registered source")
            with patch.object(ingest, "PdfReader") as reader:
                with self.assertRaisesRegex(ValueError, "fingerprint"):
                    ingest.extract_source(path, self.config)
                reader.assert_not_called()

    def test_manifest_fingerprints_cover_generated_evidence(self):
        manifest = json.loads(self.outputs["ingestion-manifest.json"])
        self.assertEqual(manifest["inputSha256"], ingest.fingerprint(ingest.INPUT.read_bytes()))
        self.assertEqual(manifest["generatorSha256"], ingest.fingerprint(SCRIPT.read_bytes()))
        for name, digest in manifest["outputs"].items():
            self.assertEqual(digest, ingest.fingerprint(self.outputs[name].encode("utf-8")))

    def test_stale_and_missing_outputs_are_detected_without_writes(self):
        with tempfile.TemporaryDirectory() as directory:
            folder = Path(directory)
            with self.assertRaisesRegex(ValueError, "Missing or stale"):
                ingest.check_outputs(self.outputs, folder)
            for name, text in self.outputs.items():
                (folder / name).write_text(text, encoding="utf-8")
            ingest.check_outputs(self.outputs, folder)
            (folder / "training-lessons.json").write_text("{}", encoding="utf-8")
            before = {p.name: p.read_bytes() for p in folder.iterdir()}
            with self.assertRaisesRegex(ValueError, "training-lessons.json"):
                ingest.check_outputs(self.outputs, folder)
            self.assertEqual(before, {p.name: p.read_bytes() for p in folder.iterdir()})

    def test_lexical_retrieval_returns_page_evidence_not_approval(self):
        hits = ingest.search_pages("Visa Fleet PAN", self.pages)
        self.assertTrue(hits)
        for hit in hits:
            self.assertEqual(hit["status"], "RETRIEVAL_CANDIDATE_NOT_VALIDATED_RULE")
            self.assertTrue(hit["excerpt"])
            self.assertTrue(1 <= hit["pdfPage"] <= 10)
        self.assertEqual(ingest.search_pages("unfindablezzziin", self.pages), [])
        with self.assertRaises(ValueError):
            ingest.search_pages("  !!! ", self.pages)

    def test_registered_launcher_and_training_status_are_review_only(self):
        tasks = json.loads((ingest.MODULE / "framework-tasks.json").read_text(encoding="utf-8"))
        by_name = {t["name"]: t for t in tasks["tasks"]}
        for name in ("iin-ingest-build", "iin-ingest-check", "iin-ingest-test"):
            self.assertIn(name, by_name)
            self.assertTrue((ingest.MODULE / by_name[name]["script"]).is_file())
        status = json.loads((ingest.PACK / "training-status.json").read_text(encoding="utf-8"))
        entry = status["supplementalSourceTraining"]["IIN_REFERENCE_MANUAL_2024_1"]
        self.assertTrue(entry["reviewRequired"])
        self.assertFalse(entry["executionCertified"])
        self.assertTrue(entry["countsExcludedFromSegmentCatalogSummary"])
        for key in ("source", "evidence", "trainingInput", "generator", "regressionTest",
                    "atl105ReferenceMap", "atl105ReferenceReport",
                    "atl105ReferenceGenerator", "atl105ReferenceRegressionTest"):
            self.assertTrue((ingest.MODULE / entry[key]).is_file(), key)
        self.assertEqual(entry["atl105ReferenceStatus"], "DRAFT_REVIEW_REQUIRED")


if __name__ == "__main__":
    unittest.main()
