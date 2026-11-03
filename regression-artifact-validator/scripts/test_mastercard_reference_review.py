import copy
import csv
import importlib.util
import io
import json
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch


SCRIPT = Path(__file__).with_name("build-mastercard-reference-review.py")
SPEC = importlib.util.spec_from_file_location("mastercard_review", SCRIPT)
assert SPEC is not None and SPEC.loader is not None
review = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(review)


class MastercardReferenceReviewTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.document = review.read_json(review.INPUT)
        cls.outputs = review.build(review.INPUT, review.PACK)

    def test_complete_reference_identity_and_traceability(self):
        report = json.loads(self.outputs["review.json"])
        self.assertEqual(report["summary"]["scenarioCount"], 37)
        self.assertEqual(report["summary"]["functionalRequirementCount"], 8)
        self.assertEqual(report["summary"]["curatedTrainingLessonCount"], 13)
        self.assertEqual(report["summary"]["questionCount"], 9)
        self.assertEqual(report["summary"]["executedCaseCount"], 0)
        self.assertEqual(report["summary"]["authoredTestDataCount"], 0)
        for row in report["scenarios"]:
            self.assertEqual(row["actualResult"], "Not Executed")
            self.assertEqual(row["testDataReference"], "NOT_AUTHORED")
            self.assertTrue(row["sourceEvidence"])
            self.assertTrue(all(item["line"] > 0 and len(item["sha256"]) == 64 for item in row["sourceEvidence"]))

    def test_only_curated_source_lessons_enter_training(self):
        training = json.loads(self.outputs["training-lessons.json"])
        self.assertFalse(training["certified"])
        self.assertFalse(training["executionReady"])
        self.assertFalse(training["baselineChanged"])
        self.assertNotIn("referenceClaim", self.outputs["training-lessons.json"])
        self.assertNotIn("questions", training)
        self.assertEqual(training["lessons"], [
            {**lesson, "status": "DRAFT_REVIEW_REQUIRED",
             "sourceEvidence": lesson_output["sourceEvidence"], "scenarioIds": lesson_output["scenarioIds"]}
            for lesson, lesson_output in zip(self.document["lessons"], training["lessons"], strict=True)
        ])

    def test_mapping_corrections_are_published(self):
        rows = {row["id"]: row for row in json.loads(self.outputs["review.json"])["scenarios"]}
        for identifier, expected in {
            "TS-002": "Element 12", "TS-004": "0020", "TS-010": "Element 2",
            "TS-016": "Element 86", "TS-022": "Element 5",
        }.items():
            self.assertIn(expected, rows[identifier]["correctedRequirement"])
        self.assertIn("same device", rows["TS-032"]["correctedRequirement"])
        self.assertIn("expired-card", rows["TS-002"]["correctedRequirement"])

    def test_csv_shapes_and_questions(self):
        rows = list(csv.DictReader(io.StringIO(self.outputs["requirements-review.csv"])))
        questions = list(csv.DictReader(io.StringIO(self.outputs["ba-sme-questionnaire.csv"])))
        self.assertEqual(len(rows), 37)
        self.assertEqual(len(questions), 9)
        self.assertTrue(all(row["Affected TS IDs"] and row["Evidence Needed"] for row in questions))
        self.assertTrue(all(row["BA Decision"] == "" for row in rows))

    def test_duplicate_or_missing_scenario_fails(self):
        for mutate in (lambda doc: doc["scenarios"].append(doc["scenarios"][0]),
                       lambda doc: doc["scenarios"].pop()):
            document = copy.deepcopy(self.document)
            mutate(document)
            with self.assertRaises(ValueError):
                review.validate(document, review.PACK)

    def test_missing_source_and_unsafe_path_fail(self):
        for value in ("not-a-real-source.txt", "../outside.txt"):
            document = copy.deepcopy(self.document)
            document["sources"]["account"]["path"] = value
            with self.assertRaises((ValueError, FileNotFoundError)):
                review.validate(document, review.PACK)

    def test_changed_or_ambiguous_source_anchor_fails(self):
        for anchor in ("NONEXISTENT-SOURCE-CLAIM", "Number:"):
            document = copy.deepcopy(self.document)
            document["sources"]["account"]["anchor"] = anchor
            with self.assertRaises(ValueError):
                review.validate(document, review.PACK)

    def test_unresolved_links_and_missing_question_fail(self):
        for field, value in (("sources", ["missing"]), ("lessons", ["missing"]), ("questions", [])):
            document = copy.deepcopy(self.document)
            document["scenarios"][0][field] = value
            with self.assertRaises(ValueError):
                review.validate(document, review.PACK)

    def test_approval_and_baseline_promotion_fail(self):
        document = copy.deepcopy(self.document)
        document["status"] = "APPROVED"
        with self.assertRaises(ValueError):
            review.validate(document, review.PACK)
        document = copy.deepcopy(self.document)
        document["businessRequirement"]["independentBaselineChanged"] = True
        with self.assertRaises(ValueError):
            review.validate(document, review.PACK)

    def test_deterministic_build_and_output_hashes(self):
        self.assertEqual(self.outputs, review.build(review.INPUT, review.PACK))
        manifest = json.loads(self.outputs["manifest.json"])
        for name, expected in manifest["outputHashes"].items():
            self.assertEqual(review.fingerprint(self.outputs[name].encode("utf-8")), expected)

    def test_source_mutation_changes_fingerprint(self):
        with tempfile.TemporaryDirectory() as directory:
            root = Path(directory)
            path = root / "source.txt"
            path.write_text("unique anchor\nbefore", encoding="utf-8")
            sources = {"example": {"path": "source.txt", "section": "test", "anchor": "unique anchor"}}
            first = review.resolve_sources(sources, root)
            path.write_text("unique anchor\nafter", encoding="utf-8")
            second = review.resolve_sources(sources, root)
            self.assertNotEqual(first["example"]["sha256"], second["example"]["sha256"])

    def test_csv_formula_prefix_is_escaped(self):
        output = review.as_csv([{"value": "=external()", "other": "safe"}])
        self.assertEqual(next(csv.DictReader(io.StringIO(output)))["value"], "'=external()")

    def test_check_rejects_stale_outputs_without_writing(self):
        with tempfile.TemporaryDirectory() as directory:
            output = Path(directory)
            for name, content in self.outputs.items():
                (output / name).write_bytes(content.encode("utf-8"))
            target = output / "training-lessons.json"
            target.write_text("stale", encoding="utf-8")
            with patch.object(review, "OUTPUT", output), patch("sys.argv", ["review", "--check"]):
                with self.assertRaisesRegex(ValueError, "training-lessons.json"):
                    review.main()
            self.assertEqual(target.read_text(encoding="utf-8"), "stale")


if __name__ == "__main__":
    unittest.main()
