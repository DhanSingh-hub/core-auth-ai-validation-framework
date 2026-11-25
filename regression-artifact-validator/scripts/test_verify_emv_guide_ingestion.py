import importlib.util
import unittest
from pathlib import Path


SCRIPT = Path(__file__).with_name("verify-emv-guide-ingestion.py")
SPEC = importlib.util.spec_from_file_location("emv_guide_ingestion_verification", SCRIPT)
assert SPEC is not None and SPEC.loader is not None
verify = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(verify)


def failures(results):
    return [item["check"] for item in results if item["status"] == "FAIL"]


class MutationAssertions(unittest.TestCase):
    def mutate(self, old, new, count=-1):
        mutated = self.record.replace(old, new, count)
        self.assertNotEqual(mutated, self.record, f"mutation target not found: {old!r}")
        return mutated

    def assertFailsOn(self, results, *keywords):
        failed = failures(results)
        for keyword in keywords:
            self.assertTrue(any(keyword in name for name in failed), f"no failing check mentions {keyword!r}: {failed}")


class TrainingRecordConsistencyTest(MutationAssertions):
    @classmethod
    def setUpClass(cls):
        cls.record = verify.read_text(verify.RECORD)
        cls.linked = verify.read_linking_docs()

    def check(self, record=None, linked=None):
        return verify.check_record(self.record if record is None else record, self.linked if linked is None else linked)

    def test_record_is_consistent(self):
        self.assertEqual(failures(self.check()), [])

    def test_shorthand_rule_references_are_expanded(self):
        self.assertEqual(verify.rule_refs("TAG-001, -006; XF-002 and LC-005, GAP-EMV-06"),
                         {"TAG-001", "TAG-006", "XF-002", "LC-005"})

    def test_removed_scenario_is_detected(self):
        row = next(line for line in self.record.splitlines() if line.startswith("| EMV-TS-072 |"))
        self.assertFailsOn(self.check(record=self.mutate(row + "\n", "")), "Scenarios EMV-TS-001", "testable rule")

    def test_unknown_rule_citation_is_detected(self):
        row = next(line for line in self.record.splitlines() if line.startswith("| EMV-TS-072 |"))
        mutated = self.mutate(row, row.replace("| FB-006 |", "| FB-099 |"))
        self.assertFailsOn(self.check(record=mutated), "rule cited by a scenario exists", "testable rule")

    def test_missing_image_only_acknowledgement_is_detected(self):
        mutated = self.mutate("pp.176-177 (§14.6 Fleetcor)", "(§14.6 Fleetcor)")
        self.assertFailsOn(self.check(record=mutated), "image-only")

    def test_unlinked_integration_document_is_detected(self):
        linked = dict(self.linked)
        linked["KB index"] = linked["KB index"].replace(verify.RECORD.name, "removed.md")
        self.assertFailsOn(self.check(linked=linked), "linked from")

    def test_broken_anchor_is_detected(self):
        mutated = self.mutate("(#95-fuelman-parameters", "(#95-fuelman-parameterz", 1)
        self.assertFailsOn(self.check(record=mutated), "anchors")

    def test_missing_pdf_is_reported_as_not_assessed(self):
        results, counts, code = verify.run(pdf=verify.SPECS / "missing-emv-guide.pdf")
        self.assertEqual(failures(results), [])
        self.assertEqual(counts["NOT_ASSESSED"], 1)
        self.assertEqual(code, 2)


class GuideSourceFidelityTest(MutationAssertions):
    @classmethod
    def setUpClass(cls):
        loaded, reason = verify.load_pdf()
        if not loaded:
            raise unittest.SkipTest(reason)
        cls.pages, cls.sha = loaded
        cls.record = verify.read_text(verify.RECORD)
        cls.atl105 = verify.read_atl105()
        cls.internal = verify.read_internal_sources()

    def check(self, record=None, sha=None):
        return verify.check_source(self.pages, self.sha if sha is None else sha,
                                   self.record if record is None else record, self.atl105, self.internal)

    def test_record_matches_source(self):
        self.assertEqual(failures(self.check()), [])

    def test_table_25_change_is_detected(self):
        mutated = self.mutate("| 4F | Application DF Name (AID) | B | Y | Y | N | N | Chip |",
                              "| 4F | Application DF Name (AID) | B | Y | N | N | N | Chip |")
        self.assertFailsOn(self.check(record=mutated), "Table 25")

    def test_uncovered_rq_is_detected(self):
        self.assertFailsOn(self.check(record=self.mutate("RQ4701", "RQ-REMOVED")), "Every RQ in the guide body")

    def test_paraphrased_quotation_is_detected(self):
        mutated = self.mutate("must be used in conjunction", "may be used in conjunction")
        self.assertFailsOn(self.check(record=mutated), "not verbatim")

    def test_wrong_pdf_hash_is_detected(self):
        self.assertFailsOn(self.check(sha="0" * 64), "SHA-256")

    def test_wrong_rq_page_citation_is_detected(self):
        mutated = self.mutate("| 4.3 / 47 | MSG | XF-008, TAG-001 |", "| 4.3 / 147 | MSG | XF-008, TAG-001 |")
        self.assertFailsOn(self.check(record=mutated), "RQ index page citations")

    def test_abbreviated_aid_is_detected(self):
        self.assertFailsOn(self.check(record=self.mutate("A0000000049999D8400321", "…D8400321")), "written in full")


if __name__ == "__main__":
    unittest.main()
