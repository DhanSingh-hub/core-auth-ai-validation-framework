import copy
import importlib.util
import json
import re
import unittest
from pathlib import Path


SCRIPT = Path(__file__).with_name("verify-secure-transport-ingestion.py")
SPEC = importlib.util.spec_from_file_location("secure_transport_ingestion_verification", SCRIPT)
assert SPEC is not None and SPEC.loader is not None
verify = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(verify)


def failures(results):
    return [item["check"] for item in results if item["status"] == "FAIL"]


def rows(record, number, pattern):
    return [line for line in verify.section(record, number).splitlines() if re.match(pattern, line)]


class MutationAssertions(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.record = verify.read_text(verify.RECORD)
        cls.catalog = verify.read_text(verify.CATALOG)
        cls.linked = verify.read_linking_docs()
        cls.manifest = verify.load_manifest()
        cls.gitignore = verify.read_text(verify.GITIGNORE)
        cls.atl105 = verify.read_atl105()
        cls.status = verify.read_text(verify.TRAINING_STATUS)

    def mutate(self, old, new, count=1, text=None):
        original = self.record if text is None else text
        mutated = original.replace(old, new, count)
        self.assertNotEqual(mutated, original, f"mutation target not found: {old!r}")
        return mutated

    def mutate_catalog(self, old, new, count=1):
        return self.mutate(old, new, count, text=self.catalog)

    def assertFailsOn(self, results, *keywords):
        failed = failures(results)
        for keyword in keywords:
            self.assertTrue(any(keyword in name for name in failed), f"no failing check mentions {keyword!r}: {failed}")

    def quoted_rule(self, kind):
        """A rule row whose quotation sits on a cited PDF page (kind PAGE) or Word phase (kind PHASE)."""
        pattern = verify.citation_pattern({doc["id"] for doc in self.manifest["documents"]})
        for cells in verify.rule_rows(self.catalog):
            found = [c for c in verify.citations(cells[4], pattern) if c[1] == kind]
            if found and verify.quotes(cells[1]) and len(verify.citations(cells[4], pattern)) == 1:
                return cells, found[0], verify.quotes(cells[1])[0]
        self.skipTest(f"no single-citation {kind} rule with a quotation")


class TrainingRecordConsistencyTest(MutationAssertions):
    def check(self, record=None, linked=None, manifest=None, gitignore=None, atl105=None, status=None,
              catalog=None):
        return verify.check_record(self.record if record is None else record,
                                   self.catalog if catalog is None else catalog,
                                   self.linked if linked is None else linked,
                                   self.manifest if manifest is None else manifest,
                                   self.gitignore if gitignore is None else gitignore,
                                   self.atl105 if atl105 is None else atl105,
                                   self.status if status is None else status)

    def test_record_is_consistent(self):
        self.assertEqual(failures(self.check()), [])

    def test_removed_chapter_row_is_detected(self):
        row = rows(self.record, 4, r"^\| ST-XML-API 06 \|")[0]
        self.assertFailsOn(self.check(record=self.mutate(row + "\n", "")), "maps every chapter")

    def test_wrong_chapter_page_range_is_detected(self):
        row = rows(self.record, 4, r"^\| ST-XML-API 06 \|")[0]
        self.assertFailsOn(self.check(record=self.mutate(row, row.replace("| 19-29 |", "| 19-28 |"))), "page ranges")

    def test_wrong_document_hash_is_detected(self):
        sha = next(doc["sha256"] for doc in self.manifest["documents"] if doc["id"] == "ST-XML-API")
        self.assertFailsOn(self.check(record=self.mutate(sha, "0" * 64)), "binds every document")

    def test_removed_scenario_is_detected(self):
        row = rows(self.record, 9, r"^\| ST-TS-\d{3} \|")[-1]
        self.assertFailsOn(self.check(record=self.mutate(row + "\n", "")), "Scenarios ST-TS-001")

    def test_unknown_rule_citation_is_detected(self):
        row = rows(self.record, 9, r"^\| ST-TS-\d{3} \|")[0]
        rule = verify.RULE_ID.search(row.split("|")[3]).group(0)
        mutated = self.mutate(row, row.replace(rule, rule[:-3] + "999", 1))
        self.assertFailsOn(self.check(record=mutated), "rule cited by a scenario exists")

    def test_citation_beyond_the_document_is_detected(self):
        cells, (doc, _, first, _), _ = self.quoted_rule("PAGE")
        row = next(line for line in self.catalog.splitlines() if line.startswith(f"| {cells[0]} |"))
        mutated = self.mutate_catalog(row, row.replace(f"{doc} p.{first}", f"{doc} p.999", 1))
        self.assertFailsOn(self.check(catalog=mutated), "existing page")

    def test_removed_rule_is_detected(self):
        row = next(line for line in self.catalog.splitlines() if verify.RULE_ID.match(line.strip("| ")))
        self.assertFailsOn(self.check(catalog=self.mutate_catalog(row + "\n", "")), "Rule counts per family")

    def test_unlinked_integration_document_is_detected(self):
        linked = dict(self.linked)
        linked["KB index"] = linked["KB index"].replace(verify.RECORD.name, "removed.md")
        self.assertFailsOn(self.check(linked=linked), "linked from")

    def test_broken_anchor_is_detected(self):
        anchor = re.search(r"\]\(#([^)]+)\)", self.record).group(1)
        self.assertFailsOn(self.check(record=self.mutate(f"(#{anchor})", f"(#{anchor}-missing)")), "anchors resolve")

    def test_page_gap_in_the_manifest_is_detected(self):
        manifest = copy.deepcopy(self.manifest)
        doc = next(doc for doc in manifest["documents"] if doc["id"] == "ST-XML-API")
        doc["chapters"][3]["startPage"] += 2
        self.assertFailsOn(self.check(manifest=manifest), "tile its pages")

    def test_missing_gitignore_rule_is_detected(self):
        gitignore = self.gitignore.replace("/Secure_Transport_Guide/\n", "")
        self.assertNotEqual(gitignore, self.gitignore)
        self.assertFailsOn(self.check(gitignore=gitignore), "git-ignored")

    def test_moved_atl105_anchor_is_detected(self):
        atl105 = dict(self.atl105)
        atl105["2026-4"] = "inserted line\n" * 5 + atl105["2026-4"]
        self.assertFailsOn(self.check(atl105=atl105), "Section 3.3")

    def test_missing_training_status_entry_is_detected(self):
        status = json.loads(self.status)
        del status["referenceTraining"]["secureTransport"]
        self.assertFailsOn(self.check(status=json.dumps(status)), "training-status.json")

    def test_missing_local_split_is_reported_as_not_assessed(self):
        results, counts, code = verify.run(local=verify.SPECS / "missing-secure-transport-split")
        self.assertEqual(failures(results), [])
        self.assertEqual(counts["NOT_ASSESSED"], 1)
        self.assertEqual(code, 2)


class SourceFidelityTest(MutationAssertions):
    @classmethod
    def setUpClass(cls):
        super().setUpClass()
        cls.local = verify.load_local(cls.manifest)
        if cls.local is None:
            raise unittest.SkipTest("the local Secure Transport split is not available (it is never committed)")
        cls.local["__root__"] = verify.LOCAL

    def check(self, record=None, local=None, reextract=False, catalog=None):
        return verify.check_source(self.manifest, self.local if local is None else local,
                                   self.record if record is None else record,
                                   self.catalog if catalog is None else catalog, self.atl105, reextract=reextract)

    def test_record_and_split_match_the_sources_including_pdf_reextraction(self):
        self.assertEqual(failures(self.check(reextract=True)), [])

    def test_changed_chapter_text_is_detected(self):
        local = copy.deepcopy(self.local)
        chapters = local["ST-RC-TLS"]["chapters"]
        chapters["01"] = chapters["01"].replace("TLS", "SSL", 1)
        self.assertFailsOn(self.check(local=local), "holds its manifest text", "concatenate")

    def test_dropped_page_is_detected(self):
        local = copy.deepcopy(self.local)
        text = local["ST-XML-API"]["text"]
        start = text.index("--- PAGE 30 ---")
        local["ST-XML-API"]["text"] = text[:start] + text[text.index("--- PAGE 31 ---"):]
        self.assertFailsOn(self.check(local=local), "extracted text has its manifest SHA-256", "concatenate")

    def test_changed_source_file_is_detected(self):
        local = copy.deepcopy(self.local)
        local["ST-UNIX-API"]["sourceSha"] = "0" * 64
        self.assertFailsOn(self.check(local=local), "pinned SHA-256")

    def test_paraphrased_quotation_is_detected(self):
        _, _, quoted = self.quoted_rule("PAGE")
        words = quoted.split()
        paraphrase = " ".join(words[:-1] + ["paraphrased"]).replace("|", "\\|")
        raw = quoted.replace("|", "\\|")
        mutated = self.mutate_catalog('"' + raw + '"', '"' + paraphrase + '"')
        self.assertFailsOn(self.check(catalog=mutated), "verbatim in a source")

    def test_quotation_cited_on_the_wrong_page_is_detected(self):
        cells, (doc, _, first, last), _ = self.quoted_rule("PAGE")
        pages = next(d["pages"] for d in self.manifest["documents"] if d["id"] == doc)
        wrong = 2 if last != 2 and first != 2 else pages - 1
        row = next(line for line in self.catalog.splitlines() if line.startswith(f"| {cells[0]} |"))
        cited = f"{doc} p.{first}" if first == last else f"{doc} pp.{first}-{last}"
        mutated = self.mutate_catalog(row, row.replace(cited, f"{doc} p.{wrong}", 1))
        self.assertFailsOn(self.check(catalog=mutated), "on the page, phase or lines")

    def test_missing_rendered_image_is_detected(self):
        local = copy.deepcopy(self.local)
        local["ST-XML-API"]["images"] = ["images/ST-XML-API-p029.png"]
        self.assertFailsOn(self.check(local=local), "rendered page image")


if __name__ == "__main__":
    unittest.main()
