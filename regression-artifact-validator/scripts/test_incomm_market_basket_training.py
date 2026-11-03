import copy
import hashlib
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch


SCRIPT = Path(__file__).with_name("ingest-incomm-market-basket.py")
SPEC = importlib.util.spec_from_file_location("incomm_market_basket", SCRIPT)
mbd = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(mbd)

EXPECTED_SPANS = {
    "CH-REV": [2, 2], "CH-TOC": [3, 3], "CH-1": [4, 4], "CH-1.1": [4, 4], "CH-1.2": [4, 5],
    "CH-1.3": [5, 6], "CH-1.4": [6, 10], "CH-1.5": [10, 11], "CH-1.6": [11, 12], "CH-1.7": [12, 13],
    "CH-1.8": [13, 14], "CH-1.9": [14, 14], "CH-1.10": [14, 15], "CH-1.11": [16, 16],
}


class InCommMarketBasketTrainingTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.outputs = mbd.build()
        cls.config = json.loads(mbd.INPUT.read_text(encoding="utf-8"))
        cls.pages = json.loads(cls.outputs["pages.json"])["pages"]
        cls.chapters = json.loads(cls.outputs["chapters.json"])["chapters"]
        cls.training = json.loads(cls.outputs["training-lessons.json"])
        cls.catalog = json.loads(cls.outputs["dataset-catalog.json"])
        cls.chapter_ids = {c["id"] for c in cls.chapters}

    def encode(self, tag, values):
        return mbd.build_dataset(tag, values, self.catalog)

    def validate(self, text, direction, txn, request=None):
        return mbd.validate_market_basket(text, direction, txn, self.catalog, request)

    def severities(self, result):
        return {(f["severity"], f["dataset"], f["bit"]) for f in result["findings"]}

    # ------------------------------------------------------------ source and chapters

    def test_source_identity_and_complete_page_coverage(self):
        self.assertEqual([p["pdfPage"] for p in self.pages], list(range(1, 17)))
        self.assertEqual(hashlib.sha256(mbd.PDF.read_bytes()).hexdigest(), self.config["sourceSha256"])
        for p in self.pages:
            self.assertEqual(p["textSha256"], mbd.fingerprint(p["text"].encode("utf-8")))
            self.assertEqual(p["extractionStatus"], "TEXT_EXTRACTED")
        manifest = json.loads(self.outputs["ingestion-manifest.json"])
        self.assertEqual((manifest["pageCount"], manifest["bookmarkCount"], manifest["chapterCount"]), (16, 14, 14))

    def test_chapters_follow_bookmarks_with_verified_headings_and_spans(self):
        self.assertEqual({c["id"]: c["pdfPages"] for c in self.chapters}, EXPECTED_SPANS)
        for chapter in self.chapters:
            start = self.pages[chapter["pdfPages"][0] - 1]
            self.assertIn(mbd.normalized(chapter["title"]), mbd.normalized(start["text"]))
            self.assertEqual(chapter["status"], mbd.STATUS)
        self.assertEqual(self.chapters[6]["printedPages"], ["1-3", "1-4", "1-5", "1-6", "1-7"])

    def test_changed_outline_or_heading_fails(self):
        config = copy.deepcopy(self.config)
        config["chapters"][5]["heading"] = "1.3 Invented Dataset"
        pages, outline = mbd.extract_source(mbd.PDF, self.config)
        with self.assertRaisesRegex(ValueError, "heading"):
            mbd.build_chapters(config, pages, outline)
        config = copy.deepcopy(self.config)
        config["expectedOutline"][6]["pdfPage"] = 7
        with self.assertRaisesRegex(ValueError, "outline"):
            mbd.extract_source(mbd.PDF, config)

    def test_changed_source_fails_before_extraction(self):
        with tempfile.TemporaryDirectory() as directory:
            path = Path(directory) / "changed.pdf"
            path.write_bytes(b"not the registered source")
            with patch.object(mbd, "PdfReader") as reader:
                with self.assertRaisesRegex(ValueError, "fingerprint"):
                    mbd.extract_source(path, self.config)
                reader.assert_not_called()

    # ------------------------------------------------------------ lessons and questions

    def test_lessons_are_draft_chapter_scoped_and_quotes_resolve(self):
        self.assertEqual(len(self.training["lessons"]), 35)
        for key in ("certified", "executionReady", "baselineChanged", "modelWeightsChanged"):
            self.assertFalse(self.training[key])
        for key in ("independentBrCount", "testScenarioCount", "testCaseCount", "testDataCount", "executedCaseCount"):
            self.assertEqual(self.training[key], 0)
        spans = {c["id"]: c["pdfPages"] for c in self.chapters}
        for lesson in self.training["lessons"]:
            self.assertEqual(lesson["status"], mbd.STATUS)
            low, high = spans[lesson["chapter"]]
            for c in lesson["evidence"]:
                page = self.pages[c["pdfPage"] - 1]
                self.assertIn(mbd.normalized(c["quote"]), mbd.normalized(page["text"]))
                self.assertEqual((c["pageId"], c["textSha256"]), (page["id"], page["textSha256"]))
                self.assertTrue(low <= c["pdfPage"] <= high)
        covered = {l["chapter"] for l in self.training["lessons"]}
        self.assertEqual(self.chapter_ids - covered, {"CH-TOC"})

    def test_invalid_lessons_and_questions_fail(self):
        for mutate in (
            lambda c: c["lessons"][0]["evidence"][0].update(quote="INVENTED MARKET BASKET RULE"),
            lambda c: c["lessons"][0]["evidence"][0].update(pdfPage=17),
            lambda c: c["lessons"][0].update(chapter="CH-9"),
            lambda c: c["lessons"].append(c["lessons"][0]),
            lambda c: c["lessons"][0].update(statement=""),
            lambda c: c.update(status="APPROVED"),
            lambda c: c["openQuestions"][0].update(status="APPROVED"),
            lambda c: c["openQuestions"][12].pop("qeDecision"),
        ):
            config = copy.deepcopy(self.config)
            mutate(config)
            with self.assertRaises(ValueError):
                mbd.build_lessons(config, self.pages, self.chapter_ids)

    def test_user_interpretations_are_recorded_pending_sme(self):
        decided = {q["id"] for q in self.config["openQuestions"] if q["status"] == "QE_INTERPRETATION_PENDING_SME"}
        self.assertEqual(decided, {f"INCOMM-MBD-2023-1-Q{n}" for n in range(13, 18)})
        q01 = next(q for q in self.config["openQuestions"] if q["id"] == "INCOMM-MBD-2023-1-Q01")
        self.assertEqual(q01["status"], "REVIEW_REQUIRED")

    # ------------------------------------------------------------ catalog

    def test_dataset_rows_and_usage_rows_resolve_on_source_pages(self):
        counts = {t: len(d["fields"]) for t, d in self.catalog["datasets"].items()}
        self.assertEqual(counts, {"DV": 4, "PI": 19, "PU": 9, "OF": 5, "OC": 5})
        self.assertEqual({t: len(m["rows"]) for t, m in self.catalog["usageMatrices"].items()},
                         {"PI": 18, "PU": 8, "OF": 4, "OC": 4})
        pi = {f["bit"]: f for f in self.catalog["datasets"]["PI"]["fields"]}
        self.assertEqual((pi[2]["type"], pi[2]["length"], pi[2]["evidencePdfPages"]), ("n", 14, [6]))
        self.assertEqual((pi[19]["type"], pi[19]["length"]), ("ans", 20))
        for dataset in self.catalog["datasets"].values():
            for field in dataset["fields"]:
                self.assertTrue(field["descriptionPdfPages"], f"{dataset['tag']} bit {field['bit']}")
                for page in field["descriptionPdfPages"]:
                    self.assertIn(mbd.normalized(field["descriptionQuote"]), mbd.normalized(self.pages[page - 1]["text"]))

    def test_purse_id_presence_depends_on_purse_in_response(self):
        dv = self.encode("DV", {2: "01.01"})
        line = {2: "1", 3: "1", 8: "Y", 12: "100", 13: "1", 19: "NN"}
        purse = self.encode("PU", {2: "OTC", 3: "1", 4: "C", 5: "1000", 6: "100", 7: "00"})
        self.assertIn(("REVIEW_REQUIRED", "PI", 14), self.severities(self.validate(dv + self.encode("PI", line), "response", "920")))
        self.assertIn(("VIOLATION", "PI", 14), self.severities(self.validate(dv + self.encode("PI", line) + purse, "response", "920")))
        self.assertNotIn(("VIOLATION", "PI", 14), self.severities(self.validate(dv + self.encode("PI", {**line, 14: "1"}) + purse, "response", "920")))

    def test_mutated_catalog_rows_fail(self):
        for mutate in (
            lambda c: c["datasets"]["PI"]["fields"][1].update(format="n 15"),
            lambda c: c["datasets"]["PU"]["fields"][1].update(name="Purse Label"),
            lambda c: c["datasets"]["OC"]["fields"][4]["values"].update(invented="inventedType"),
            lambda c: c["usageMatrices"]["PI"]["rows"][0].update(values=["R", "R", "R", "RE"]),
            lambda c: c["usageMatrices"]["OF"]["rows"][1].update(name="Offer Label"),
            lambda c: c["datasets"]["OF"].update(reservedBits=[7, 32]),
            lambda c: c["datasets"]["PU"]["fields"][2].update(descriptionQuote="An invented purse identifier."),
            lambda c: c["datasets"]["OC"]["fields"][2].pop("descriptionQuote"),
        ):
            config = copy.deepcopy(self.config)
            mutate(config)
            with self.assertRaises(ValueError):
                mbd.build_catalog(config, self.pages)

    # ------------------------------------------------------------ codec

    def test_spec_worked_example_round_trips_and_hex_variant_fails(self):
        self.assertEqual(self.encode("DV", {2: "01.01"}), "DV0134000000001.01")
        datasets, issues = mbd.parse_datasets("DV0134000000001.01", self.catalog)
        self.assertEqual((datasets[0]["bitmap"], datasets[0]["fields"], issues), ("40000000", {2: "01.01"}, []))
        self.assertTrue(mbd.parse_datasets("DV009400000001.01", self.catalog)[1])

    def test_bitmap_and_field_format_violations(self):
        cases = {
            "DV0138000000001.01": ("DV", 1),
            "DV0130800000001.01": ("DV", 5),
            "DV0134000000001x01": ("DV", 2),
            "PI022400000000000000000012A": ("PI", 2),
        }
        for text, key in cases.items():
            issues = mbd.parse_datasets(text, self.catalog)[1]
            self.assertIn(key, {(i["dataset"], i["bit"]) for i in issues if i["severity"] == mbd.VIOLATION}, text)
        enum = self.encode("PI", {2: "1", 11: "P", 13: "1"}).replace("P0001", "Z0001")
        self.assertIn(("PI", 11), {(i["dataset"], i["bit"]) for i in mbd.parse_datasets(enum, self.catalog)[1]})

    # ------------------------------------------------------------ market basket rules

    def request_910(self):
        return (self.encode("DV", {2: "01.01"})
                + self.encode("PI", {2: "1234567890123", 3: "2", 5: "500", 13: "1"})
                + self.encode("PI", {2: "98765432109", 3: "1", 5: "250", 10: "50", 11: "P", 13: "2"}))

    def test_valid_request_and_split_response_pass(self):
        request = self.request_910()
        self.assertEqual(self.validate(request, "request", "910")["verdict"], "PASS")
        response = (self.encode("DV", {2: "01.01"})
                    + self.encode("PI", {2: "1234567890123", 3: "1", 5: "500", 8: "Y", 12: "75", 13: "1", 14: "0", 19: "YN"})
                    + self.encode("PI", {2: "1234567890123", 3: "1", 5: "500", 8: "N", 12: "0", 13: "1", 14: "0", 19: "NN"})
                    + self.encode("PI", {2: "98765432109", 3: "1", 5: "250", 8: "N", 12: "0", 13: "2", 14: "0", 19: "NN"}))
        self.assertEqual(self.validate(response, "response", "910", request)["verdict"], "PASS")

    def test_request_composition_and_usage_violations(self):
        dv = self.encode("DV", {2: "01.01"})
        pi = lambda ref, extra=None: self.encode("PI", {2: "1", 5: "100", 13: str(ref), **(extra or {})})
        checks = [
            (pi(1) + dv, ("VIOLATION", None, None)),
            (dv + "".join(pi(n) for n in range(1, 12)), ("VIOLATION", "PI", None)),
            (dv + pi(1) + pi(1), ("VIOLATION", "PI", 13)),
            (dv + pi(1, {8: "Y"}), ("VIOLATION", "PI", 8)),
            (dv + pi(1, {10: "50"}), ("VIOLATION", "PI", 11)),
            (dv + self.encode("PI", {2: "1", 13: "1"}), ("VIOLATION", "PI", 5)),
            (dv + self.encode("OF", {2: "1"}), ("REVIEW_REQUIRED", "OF", None)),
        ]
        for text, expected in checks:
            self.assertIn(expected, self.severities(self.validate(text, "request", "910")), text)
        self.assertEqual(self.validate(dv + self.encode("OF", {2: "1"}), "request", "910")["verdict"], "REVIEW_REQUIRED")
        long_text = dv + "".join(self.encode("OF", {2: str(n), 3: "TAG", 4: "x" * 25, 5: "y" * 100}) for n in range(16))
        self.assertIn(("VIOLATION", None, None), self.severities(self.validate(long_text, "request", "910")))
        self.assertEqual(self.validate(dv, "request", "999")["verdict"], "FAIL")

    def test_response_rules_echo_purse_and_adjustment(self):
        request = self.request_910()
        dv = self.encode("DV", {2: "01.01"})
        base = {2: "1234567890123", 3: "2", 5: "500", 8: "Y", 12: "0", 13: "1", 14: "0", 19: "NN"}
        other = self.encode("PI", {2: "98765432109", 3: "1", 5: "250", 8: "N", 12: "0", 13: "2", 14: "0", 19: "NN"})
        checks = [
            ({**base, 2: "5555"}, ("VIOLATION", "PI", 2), "910"),
            ({**base, 13: "9"}, ("VIOLATION", "PI", 13), "910"),
            ({**base, 14: "7"}, ("VIOLATION", "PI", 14), "910"),
            ({**base, 3: "1"}, ("REVIEW_REQUIRED", "PI", 3), "910"),
            ({k: v for k, v in base.items() if k != 12}, ("REVIEW_REQUIRED", "PI", 12), "910"),
        ]
        for values, expected, txn in checks:
            result = self.validate(dv + self.encode("PI", values) + other, "response", txn, request)
            self.assertIn(expected, self.severities(result), values)
        no_adjust = {k: v for k, v in base.items() if k != 12}
        self.assertEqual(self.validate(dv + self.encode("PI", no_adjust), "response", "310")["verdict"], "PASS")
        self.assertIn(("REVIEW_REQUIRED", "PI", 12), self.severities(self.validate(dv + self.encode("PI", base), "response", "310")))

    def test_redemption_request_amount_is_echoed_on_920_and_200(self):
        dv = self.encode("DV", {2: "01.01"})
        request = dv + self.encode("PI", {2: "1", 3: "2", 9: "1000", 13: "1"})
        line = lambda qty, amount: self.encode("PI", {2: "1", 3: qty, 8: "Y", 9: amount, 12: amount, 13: "1", 14: "0", 19: "NN"})
        for txn in ("920", "200"):
            self.assertEqual(self.validate(dv + line("2", "1000"), "response", txn, request)["verdict"], "PASS")
            self.assertEqual(self.validate(dv + line("1", "600") + line("1", "400"), "response", txn, request)["verdict"], "PASS")
            self.assertIn(("VIOLATION", "PI", 9), self.severities(self.validate(dv + line("2", "9999"), "response", txn, request)))
            self.assertIn(("REVIEW_REQUIRED", "PI", 9),
                          self.severities(self.validate(dv + line("1", "600") + line("1", "300"), "response", txn, request)))

    def test_purse_rules_enforce_only_agreeing_duplicate_columns(self):
        dv = self.encode("DV", {2: "01.01"})
        purse = {2: "OTC", 4: "C", 5: "1000", 6: "500", 7: "00"}
        result = self.validate(dv + self.encode("PU", purse), "response", "920")
        self.assertEqual(result["verdict"], "REVIEW_REQUIRED")
        self.assertTrue(all(f["reference"] == "INCOMM-MBD-2023-1-Q03" for f in result["findings"]))
        missing = {k: v for k, v in purse.items() if k != 6}
        self.assertIn(("VIOLATION", "PU", 6), self.severities(self.validate(dv + self.encode("PU", missing), "response", "920")))
        self.assertIn(("REVIEW_REQUIRED", "PU", 2),
                      self.severities(self.validate(dv + self.encode("PU", {**purse, 2: "OTC PURSE"}), "response", "920")))

    # ------------------------------------------------------------ business requirements

    def test_business_requirements_are_draft_only_and_fully_covering(self):
        package = json.loads(self.outputs["business-requirements.json"])
        brs = package["businessRequirements"]
        self.assertEqual(package["draftCounts"]["businessRequirements"], 36)
        self.assertEqual(package["draftCounts"]["blockedPendingSme"], 8)
        for key in ("independentBrCount", "testScenarioCount", "testCaseCount", "testDataCount", "executedCaseCount"):
            self.assertEqual(package[key], 0)
        for forbidden in ("testScenarios", "testCases", "testData"):
            self.assertNotIn(forbidden, package)
        self.assertEqual(package["phaseProgress"][6]["status"], "OUT_OF_SCOPE_BY_USER_DECISION")
        self.assertEqual(package["coverage"]["lessons"], "33/33 non-informational")
        for br in brs:
            blocked = bool(br["blockingQuestionIds"])
            self.assertEqual(br["status"], "DRAFT_BLOCKED_PENDING_SME" if blocked else mbd.STATUS)
            for anchor in br["sourceAnchors"]["lessons"]:
                lesson = next(l for l in self.training["lessons"] if l["id"] == anchor["id"])
                self.assertEqual(anchor["pdfPages"], sorted({e["pdfPage"] for e in lesson["evidence"]}))
        self.assertEqual({r for b in brs for r in b["enforcement"]["rules"]}, set(mbd.VALIDATOR_RULES))
        chapters = json.loads(self.outputs["chapters.json"])["chapters"]
        self.assertEqual(sum(len(c["brIds"]) for c in chapters), 36)

    def test_invalid_business_requirements_fail(self):
        base = json.loads(mbd.BR_INPUT.read_text(encoding="utf-8"))
        lessons, questions = self.training["lessons"], self.config["openQuestions"]
        crossrefs = json.loads(self.outputs["atl105-incomm-cross-references.json"])
        for mutate in (
            lambda c: c["businessRequirements"][0].update(id="INCOMM-MBD-BR-099"),
            lambda c: c["businessRequirements"][0].update(chapter="CH-9"),
            lambda c: c["businessRequirements"][0].update(lessonIds=["INCOMM-MBD-2023-1-L999"]),
            lambda c: c["businessRequirements"][0].update(lessonIds=["INCOMM-MBD-2023-1-L001"]),
            lambda c: c["businessRequirements"][4]["catalogRefs"][0].update(bits=[7]),
            lambda c: c["businessRequirements"][0]["enforcement"].update(rules=["INVENTED_RULE"]),
            lambda c: c["businessRequirements"][2].update(blockingQuestionIds=["INCOMM-MBD-2023-1-Q13"]),
            lambda c: c["businessRequirements"][11]["enforcement"].update(rules=["MAX_LENGTH"]),
            lambda c: c["businessRequirements"][13]["enforcement"].update(gap=None),
            lambda c: c["businessRequirements"][0]["appliesTo"].update(transactionTypes=["999"]),
            lambda c: c["businessRequirements"].pop(14),
            lambda c: c["businessRequirements"][-1].update(lessonIds=[], catalogRefs=[], atl105RefIds=[]),
            lambda c: c["businessRequirements"][14]["enforcement"].update(rules=["FIELD_FORMAT"]),
            lambda c: c["businessRequirements"][12].update(lessonIds=[]),
        ):
            config = copy.deepcopy(base)
            mutate(config)
            with self.assertRaises(ValueError):
                mbd.build_business_requirements(config, lessons, self.chapters, self.catalog, crossrefs, questions)

    def test_training_plan_keeps_ts_tc_td_out_of_scope(self):
        plan = json.loads(mbd.PLAN.read_text(encoding="utf-8"))
        mbd.check_plan(plan)
        for mutate in (
            lambda p: p["scopeDecision"].update(excludedArtifacts=["testData"]),
            lambda p: p["phases"].pop(6),
            lambda p: p["phases"][5].update(frameworkGate="CONTEXT_MATRIX"),
        ):
            broken = copy.deepcopy(plan)
            mutate(broken)
            with self.assertRaises(ValueError):
                mbd.check_plan(broken)

    def test_every_validator_rule_is_reachable_by_a_mutation(self):
        dv = self.encode("DV", {2: "01.01"})
        pi = lambda values: self.encode("PI", values)
        req_pi = lambda ref, extra=None: pi({2: "1", 5: "100", 13: str(ref), **(extra or {})})
        request = self.request_910()
        resp_pi = {2: "1234567890123", 3: "2", 5: "500", 8: "Y", 12: "0", 13: "1", 14: "0", 19: "NN"}
        resp_other = pi({2: "98765432109", 3: "1", 5: "250", 8: "N", 12: "0", 13: "2", 14: "0", 19: "NN"})
        purse = {2: "OTC", 3: "1", 4: "C", 5: "1000", 6: "500", 7: "00"}
        spend_pi = {2: "1", 3: "1", 8: "Y", 12: "100", 13: "1", 19: "NN"}
        mutations = {
            "TXN_CODE": (dv, "request", "999", None),
            "MAX_LENGTH": (dv + "".join(self.encode("OF", {2: str(n), 3: "TAG", 4: "x" * 25, 5: "y" * 100}) for n in range(16)), "request", "910", None),
            "ENVELOPE": (dv + "XX0054000000001", "request", "910", None),
            "BITMAP_HEX": ("DV013ZZZZZZZZ01.01", "request", "910", None),
            "CONTINUATION_UNUSED": ("DV0138000000001.01", "request", "910", None),
            "RESERVED_BIT": ("DV0130800000001.01", "request", "910", None),
            "LENGTH_MATCH": ("DV0144000000001.01X", "request", "910", None),
            "FIELD_FORMAT": (dv + "PI022400000000000000000012A", "request", "910", None),
            "FIELD_EMBEDDED_SPACE": (dv + self.encode("PU", {**purse, 2: "OTC PURSE"}), "response", "920", None),
            "FIELD_VALUE": ("DV0134000000001x01", "request", "910", None),
            "DV_FIRST": (req_pi(1) + dv, "request", "910", None),
            "COMPOSITION_LIMIT": (dv + "".join(req_pi(n) for n in range(1, 12)), "request", "910", None),
            "COMPOSITION_OUTSIDE_ATL105": (dv + self.encode("OF", {2: "1"}), "request", "910", None),
            "DV_VERSION_REQUIRED": (self.encode("DV", {3: "1"}), "request", "910", None),
            "NON_REAL_TIME_FIELD": (self.encode("DV", {2: "01.01", 3: "1"}), "request", "910", None),
            "DISCOUNT_TYPE_REQUIRED": (dv + req_pi(1, {10: "50"}), "request", "910", None),
            "MBAS_PAIR": (dv + pi({**resp_pi, 6: "1"}) + resp_other, "response", "910", request),
            "PI_CONSOLIDATION": (dv + req_pi(1) + req_pi(2), "request", "910", None),
            "PURSE_ZERO_ON_910": (dv + pi({**resp_pi, 14: "7"}) + resp_other, "response", "910", request),
            "PURSE_LINK": (dv + pi({**spend_pi, 14: "2"}) + self.encode("PU", purse), "response", "920", None),
            "USAGE_REQUIRED": (dv + pi({2: "1", 13: "1"}), "request", "910", None),
            "USAGE_NOT_APPLICABLE": (dv + req_pi(1, {8: "Y"}), "request", "910", None),
            "USAGE_AMBIGUOUS": (dv + self.encode("PU", purse), "response", "920", None),
            "NOT_ON_310": (dv + pi({**resp_pi, 12: "75"}), "response", "310", None),
            "ADJUSTMENT_CONDITION_CONFLICT": (dv + pi({k: v for k, v in resp_pi.items() if k != 12}) + resp_other, "response", "910", request),
            "PURSE_ID_CONDITION_CONFLICT": (dv + pi({k: v for k, v in resp_pi.items() if k != 14}), "response", "310", None),
            "ITEM_REF_UNIQUE": (dv + req_pi(1) + pi({2: "2", 5: "100", 13: "1"}), "request", "910", None),
            "ITEM_REF_KNOWN": (dv + pi({**resp_pi, 13: "9"}) + resp_other, "response", "910", request),
            "ECHO_MATCH": (dv + pi({**resp_pi, 2: "5555"}) + resp_other, "response", "910", request),
            "SPLIT_QUANTITY": (dv + pi({**resp_pi, 3: "1"}) + resp_other, "response", "910", request),
            "OC_TYPE_DIRECTION": (dv + self.encode("OC", {2: "1", 5: "tallyEarn"}), "request", "910", None),
        }
        self.assertEqual(set(mutations), set(mbd.VALIDATOR_RULES))
        for rule, (text, direction, txn, req) in mutations.items():
            result = self.validate(text, direction, txn, req)
            self.assertIn(rule, {f["rule"] for f in result["findings"]}, rule)
            self.assertNotEqual(result["verdict"], "PASS", rule)
        with self.assertRaisesRegex(ValueError, "Unregistered"):
            mbd.finding("INVENTED_RULE", mbd.VIOLATION, None, None, "x", "y")

    # ------------------------------------------------------------ cross-references, outputs, registration

    def test_atl105_references_resolve_in_both_releases(self):
        refs = json.loads(self.outputs["atl105-incomm-cross-references.json"])["references"]
        self.assertEqual({r["id"]: {v: p["pdfPage"] for v, p in r["releases"].items()} for r in refs}, {
            "INCOMM-MBD-ATL105-R01": {"2026-3": 305, "2026-4": 308},
            "INCOMM-MBD-ATL105-R02": {"2026-3": 306, "2026-4": 309},
            "INCOMM-MBD-ATL105-R03": {"2026-3": 586, "2026-4": 592},
        })
        config = copy.deepcopy(self.config)
        config["atl105References"][0]["quotes"][0] = "Consists of one (1) DV dataset followed by up to 99 PI"
        with self.assertRaises(ValueError):
            mbd.build_crossrefs(config)

    def test_manifest_fingerprints_and_stale_detection_without_writes(self):
        manifest = json.loads(self.outputs["ingestion-manifest.json"])
        self.assertEqual(manifest["inputSha256"], mbd.text_fingerprint(mbd.INPUT))
        for name, digest in manifest["outputs"].items():
            self.assertEqual(digest, mbd.fingerprint(self.outputs[name].encode("utf-8")))
        self.assertEqual(set(self.outputs), set(mbd.OUTPUT_NAMES))
        with tempfile.TemporaryDirectory() as directory:
            folder = Path(directory)
            with self.assertRaisesRegex(ValueError, "Missing or stale"):
                mbd.check_outputs(self.outputs, folder)
            for name, text in self.outputs.items():
                (folder / name).write_text(text, encoding="utf-8")
            mbd.check_outputs(self.outputs, folder)
            (folder / "dataset-catalog.json").write_text("{}", encoding="utf-8")
            before = {p.name: p.read_bytes() for p in folder.iterdir()}
            with self.assertRaisesRegex(ValueError, "dataset-catalog.json"):
                mbd.check_outputs(self.outputs, folder)
            self.assertEqual(before, {p.name: p.read_bytes() for p in folder.iterdir()})

    def test_committed_outputs_are_current(self):
        mbd.check_outputs(self.outputs, mbd.KB)

    def test_lexical_retrieval_returns_page_evidence_not_approval(self):
        hits = mbd.search_pages("Item Reference Number", self.pages)
        self.assertEqual(hits[0]["pdfPage"], 8)
        self.assertTrue(all(h["status"] == "RETRIEVAL_CANDIDATE_NOT_VALIDATED_RULE" for h in hits))
        self.assertEqual(mbd.search_pages("unfindablezzzincomm", self.pages), [])
        with self.assertRaises(ValueError):
            mbd.search_pages(" !!! ", self.pages)

    def test_registered_launcher_and_training_status_are_review_only(self):
        tasks = {t["name"]: t for t in json.loads((mbd.MODULE / "framework-tasks.json").read_text(encoding="utf-8"))["tasks"]}
        for name in ("incomm-mbd-train-build", "incomm-mbd-train-check", "incomm-mbd-train-test"):
            self.assertTrue((mbd.MODULE / tasks[name]["script"]).is_file(), name)
        status = json.loads((mbd.PACK / "training-status.json").read_text(encoding="utf-8"))
        entry = status["supplementalSourceTraining"]["INCOMM_MARKET_BASKET_DATA_FORMAT_2023_1"]
        self.assertTrue(entry["reviewRequired"])
        self.assertFalse(entry["executionCertified"])
        self.assertTrue(entry["countsExcludedFromSegmentCatalogSummary"])
        for key in ("source", "evidence", "trainingInput", "chapters", "datasetCatalog", "report",
                    "atl105ReferenceMap", "generator", "regressionTest", "trainingPlan", "businessRequirementsInput",
                    "businessRequirements"):
            self.assertTrue((mbd.MODULE / entry[key]).is_file(), key)
        package = json.loads(self.outputs["business-requirements.json"])
        self.assertEqual(entry["draftCounts"]["businessRequirements"], package["draftCounts"]["businessRequirements"])
        self.assertEqual(entry["draftCounts"]["testScenarios"], 0)
        self.assertEqual({str(p["phase"]): p["status"] for p in package["phaseProgress"]}, entry["phaseStatus"])


if __name__ == "__main__":
    unittest.main()
