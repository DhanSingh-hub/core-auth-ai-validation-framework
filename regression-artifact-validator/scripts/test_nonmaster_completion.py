"""Verify program-specific authority and complete scoped application-body checks."""

import copy
import importlib.util
import json
import tempfile
import unittest
from pathlib import Path
from unittest.mock import patch

import nonmaster_application as app

SPEC = importlib.util.spec_from_file_location(
    "nonmaster_completion", Path(__file__).with_name("complete-nonmaster-model.py"))
model = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(model)


def fields():
    result = dict.fromkeys(set(app.FIELDS) - {"SegmentLength"}, "")
    result.update(SegmentType="100", InformationByte="0", TerminalIdentifier="ZZ00000000000",
                  PromptCode="0020", AccountNumber="SYNTHETIC-NOT-A-CARD",
                  CardDiscretionaryBlockData="1249", NonfuelAmount="100",
                  SequenceNumber="123456", PartialApprovalIndicator="1")
    return result


def body(values=None, profile="MANUAL"):
    return b"ATL105" + app.FS + b"01" + app.FS + app.serialize_standard(values or fields(), profile)


class ApplicationTest(unittest.TestCase):
    def test_complete_standard_not_fourteen_field_prefix(self):
        parsed = app.parse_application(body())
        self.assertEqual(len(parsed), 1)
        values = parsed[0]["wire"][:-1].split(app.FS)
        self.assertEqual(len(values), 17)
        self.assertEqual(values[-1], b"1")
        self.assertEqual(int(values[1]), len(parsed[0]["wire"]))
        self.assertEqual(app.application_findings(body(), "VISA")["failures"], [])
        self.assertEqual(app.application_findings(body(), "VISA")["readiness"], "BLOCKED")

    def test_all_entry_modes_preserve_correct_account_delimiter_and_lengths(self):
        for profile in ("MANUAL", "TRACK1", "TRACK2"):
            raw = body(profile=profile)
            self.assertEqual(app.application_findings(raw, "VISA", entry_profile=profile)["failures"], [])
            standard = app.parse_application(raw)[0]["wire"]
            values = standard[:-1].split(app.FS)
            self.assertEqual(len(values), 17 if profile == "MANUAL" else 16)
            self.assertEqual(int(values[1]), len(standard))
            if profile != "MANUAL":
                self.assertIn(b"^" if profile == "TRACK1" else b"=", values[5])

    def test_wrong_entry_profile_is_not_silently_inferred(self):
        self.assertEqual(app.application_findings(body(profile="TRACK2"), "VISA")["failures"],
                         ["STANDARD_FIELD_POPULATION"])
        with self.assertRaisesRegex(ValueError, "profile"):
            app.application_findings(body(), "VISA", entry_profile="UNKNOWN")

    def test_track_delimiter_mutation_isolated(self):
        raw = body(profile="TRACK2").replace(b"=", b"!", 1)
        self.assertEqual(app.application_findings(raw, "VISA", entry_profile="TRACK2")["failures"],
                         ["TRACK_ACCOUNT_DELIMITER"])

    def test_source_pinned_fixture_suite(self):
        rows = app.fixture_suite()
        self.assertEqual(len(rows), 9)
        for row in rows:
            self.assertFalse(row["executionReady"])
            self.assertEqual(row["expectedFailures"], row["observedFailures"])
            self.assertLessEqual(len(row["observedFailures"]), 1)
            if row["expectedFailures"] == ["PARSER"]:
                self.assertTrue(row["parserFailureReason"])
            else:
                observation = app.application_findings(bytes.fromhex(row["wireHex"]), "VISA",
                                                       row["family"], row["entryProfile"])
                self.assertEqual(observation["failures"], row["expectedFailures"])

    def test_no_missing_blank_or_defaults_are_manufactured(self):
        for name in app.FIELDS:
            if name == "SegmentLength":
                continue
            data = fields()
            del data[name]
            with self.assertRaisesRegex(ValueError, "all 16"):
                app.serialize_standard(data)

    def test_placeholders_controls_and_non_ascii_cannot_be_serialized(self):
        for value in ("<account>", "{{account}}", "${account}", "A\x1cB", "A\nB", "\u00e9"):
            with self.assertRaisesRegex(ValueError, "ASCII"):
                app.serialize_standard({**fields(), "AccountNumber": value})

    def test_length_capacity_exact_threshold(self):
        original = fields()
        baseline = len(app.serialize_standard(original))
        target = {**original, "AccountNumber": "A" * (len(original["AccountNumber"]) + 218 - baseline)}
        self.assertEqual(len(app.serialize_standard(target)), 218)
        target["AccountNumber"] += "A"
        with self.assertRaisesRegex(ValueError, "capacity"):
            app.serialize_standard(target)

    def test_header_count_and_truncation_are_rejected(self):
        raw = body()
        for bad in (b"BAD" + raw[6:], raw.replace(b"01\x1c", b"00\x1c", 1),
                    raw.replace(b"01\x1c", b"02\x1c", 1), raw[:-1],
                    raw + b"trailing", raw + b"\xff", raw + b"\n"):
            with self.assertRaises(ValueError):
                app.parse_application(bad)

    def test_opaque_companion_never_passes_content_oracle(self):
        opaque = b"111\x1c010\x1cX\x1c"
        self.assertEqual(len(opaque), 10)
        raw = body().replace(b"01\x1c", b"02\x1c", 1) + opaque
        result = app.application_findings(raw, "VISA")
        self.assertEqual(result["failures"], [])
        self.assertIn("COMPANION_CONTENT_ORACLE_REQUIRED:111", result["review"])

    def test_four_digit_segment_length_and_emv_presence(self):
        opaque = b"130\x1c0012\x1cX\x1c\x1c"
        self.assertEqual(len(opaque), 12)
        raw = body().replace(b"01\x1c", b"02\x1c", 1) + opaque
        result = app.application_findings(raw, "VISA", "EMV Financial Transaction Request")
        self.assertEqual(result["failures"], [])
        self.assertEqual(result["review"], ["COMPANION_CONTENT_ORACLE_REQUIRED:130"])
        self.assertIn("EMV_FAMILY_REQUIRED", app.application_findings(raw, "VISA")["failures"])

    def test_emv_family_cannot_pass_without_emv_segment(self):
        result = app.application_findings(body(), "VISA", "EMV Financial Transaction Request")
        self.assertEqual(result["failures"], ["EMV_REQUEST_SEGMENT_REQUIRED"])

    def test_sequence_and_partial_indicator_boundaries(self):
        for seq, valid in (("000000", False), ("000001", True), ("999999", True), ("1000000", False)):
            result = app.application_findings(body({**fields(), "SequenceNumber": seq}), "VISA")
            self.assertEqual("REPRESENTATION:SequenceNumber" not in result["failures"], valid)
        for indicator in ("0", "1", "5", "9", ""):
            result = app.application_findings(body({**fields(), "PartialApprovalIndicator": indicator}), "VISA")
            self.assertEqual(not result["failures"], indicator in {"0", "1", "5"})
            if indicator == "5":
                self.assertIn("AMEX_PREPAID_ONLY_INDICATOR5_ELIGIBILITY", result["review"])

    def test_amex_amount_capacity_not_applied_to_other_brands(self):
        raw = body({**fields(), "NonfuelAmount": "12345678"})
        self.assertEqual(app.application_findings(raw, "AMERICAN_EXPRESS")["failures"], [])
        self.assertEqual(app.application_findings(raw, "VISA")["failures"], ["REPRESENTATION:NonfuelAmount"])

    def test_absent_observation_is_not_a_default_or_a_pass(self):
        failures, review = app.standard_findings({}, "VISA")
        self.assertEqual(failures, [])
        self.assertEqual(set(review), {"MISSING_FIELD:" + key for key in app.FIELDS})

    def test_calendar_invalidity_is_explicit(self):
        result = app.application_findings(body({**fields(), "LocalDateandLocalTime": "0231251200"}), "VISA")
        self.assertEqual(result["failures"], ["CALENDAR:LocalDateandLocalTime"])

    def test_json_without_wire_cannot_become_wire_pass(self):
        standard = fields()
        standard["SegmentLength"] = "100"
        standard["PromptCode"] = {"TransactionType": "0", "CardType": "020"}
        payload = {"Financial Transaction Request": {
            "MessageFormatVersionIdentifier": "ATL105", "NumberofSegments": "01",
            "Standard Message Data Segment": standard}}
        result = app.observe_json(payload, "VISA")
        self.assertEqual(result["assessment"], "REVIEW_REQUIRED")
        self.assertIn("SERIALIZED_APPLICATION_BODY_NOT_SUPPLIED", result["review"])
        payload["Financial Transaction Request"]["NumberofSegments"] = "02"
        self.assertIn("SEGMENT_COUNT_POPULATION", app.observe_json(payload, "VISA")["failures"])

    def test_unknown_family_not_exempted_as_totals(self):
        self.assertEqual(app.observe_json({"Totals Request": {}}, "VISA")["assessment"], "NOT_APPLICABLE_TOTALS")
        self.assertEqual(app.observe_json({"Unknown": {}}, "VISA")["assessment"], "REVIEW_REQUIRED")
        self.assertEqual(app.observe_json({}, "VISA")["review"], ["UNKNOWN_ENVELOPE"])


class ProgramTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.doc = model.program_document()
        cls.sources, _ = model.source_catalog()

    def test_source_pins_and_complete_concrete_program_chains(self):
        package, register = model.build_program_chains(self.doc, self.sources)
        self.assertEqual(len(package["businessRequirements"]), 41)
        self.assertEqual(len(register), 49)
        self.assertEqual(len(package["testCases"]), 98)
        self.assertEqual(len(package["testData"]), 98)
        indexes = {key: model.base.unique(value) for key, value in package.items()}
        for case in package["testCases"]:
            self.assertTrue(set(case["scenarioIds"]) <= indexes["testScenarios"].keys())
            self.assertTrue(set(case["testDataIds"]) <= indexes["testData"].keys())
        for datum in package["testData"]:
            case = indexes["testCases"][datum["testCaseIds"][0]]
            self.assertEqual(model.v1.evaluate(case["assertions"][0]["predicate"], datum["payload"]),
                             case["expectedOutcome"] == "PASS")
            self.assertEqual(datum["expectedValidation"], datum["observedValidation"])

    def test_each_source_quote_and_page_must_match(self):
        for key, value in (("page", 1), ("quote", "This is not an authoritative program rule.")):
            doc = copy.deepcopy(self.doc)
            doc["rules"][0][key] = value
            with self.assertRaisesRegex(ValueError, "source quote/page"):
                model.build_program_chains(doc, self.sources)

    def test_additional_program_pages_are_verified(self):
        doc = copy.deepcopy(self.doc)
        rule = next(r for r in doc["rules"] if r["id"] == "NP-031")
        rule["additionalPages"] = [1]
        with self.assertRaisesRegex(ValueError, "source quote/page"):
            model.build_program_chains(doc, self.sources)

    def test_program_authority_versions_do_not_get_relabelled(self):
        package, _ = model.build_program_chains(self.doc, self.sources)
        for rule in package["businessRequirements"]:
            source = rule["sourceEvidence"]
            self.assertEqual(source["version"], "2025-1" if source["source"] == "PETROLEUM" else "2026-4")
            self.assertEqual(source["sha256"], self.sources[source["source"]]["sha256"])

    def test_program_policies_do_not_leak_between_siblings(self):
        rules = {r["id"]: r for r in self.doc["rules"]}
        self.assertEqual(rules["NP-029"]["contexts"], ["WEX_FLEET"])
        self.assertTrue(rules["NP-029"]["valid"]["storageResponsibilityAssigned"])
        self.assertFalse(rules["NP-030"]["valid"]["storeForward"])
        self.assertEqual(rules["NP-030"]["contexts"], ["COMDATA_FLEET"])
        self.assertTrue(rules["NP-035"]["valid"]["retryLatestOnly"])
        self.assertFalse(rules["NP-036"]["valid"]["manualEntry"])
        self.assertTrue(rules["NP-039"]["valid"]["manualEntry"])

    def test_no_approval_unknown_context_or_duplicate_identity(self):
        for change in ("approval", "unknown", "duplicate", "mutant"):
            doc = copy.deepcopy(self.doc)
            if change == "approval":
                doc["status"] = "APPROVED"
            elif change == "unknown":
                doc["rules"][0]["contexts"] = ["MASTERCARD"]
            elif change == "duplicate":
                doc["rules"].append(doc["rules"][0])
            else:
                doc["rules"][0]["invalid"] = doc["rules"][0]["valid"]
            with self.assertRaises(ValueError):
                model.build_program_chains(doc, self.sources)

    def test_existing_fleet_mutations_reexecuted_without_authority_promotion(self):
        evidence, chains, inputs = model.companion_chains()
        self.assertEqual(sum(r["counts"]["businessRequirements"] for r in evidence), 61)
        self.assertEqual(sum(r["counts"]["testCases"] for r in evidence), 115)
        self.assertEqual(set(chains), {"VISA_FLEET", "VOYAGER_FLEET"})
        self.assertTrue(all(path.is_file() for path in inputs))
        for row in evidence:
            self.assertEqual(row["mutationSuite"]["caught"], row["mutationSuite"]["total"])
            self.assertIn("DRAFT", row["authority"])
            self.assertEqual(row["serialization"]["status"], "BLOCKED")

    def test_repository_work_is_not_mislabeled_as_external_approval(self):
        work = model.work_register()
        self.assertTrue(any(r["disposition"] == "REPOSITORY_SEMANTIC_WORK_REMAINS" for r in work))
        self.assertTrue(any(r["disposition"] == "REPOSITORY_IMPLEMENTATION_REMAINS" for r in work))
        self.assertTrue(any(r["disposition"] == "EXTERNAL_APPROVAL_REQUIRED" for r in work))

    def test_application_source_pins_include_track_exceptions_and_final_required_field(self):
        for pin in app.SOURCE_PINS:
            self.assertIn(model.base.norm(pin["quote"]), self.sources["ATL105"]["pages"][pin["page"]])
        self.assertTrue({225, 364, 365, 440} <= {pin["page"] for pin in app.SOURCE_PINS})

    def test_physical_failures_keep_negative_intent_without_copying_account_payload(self):
        with tempfile.TemporaryDirectory() as temp:
            delivery = Path(temp)
            frozen = []
            for package in ("american_express_credit", "interlink_debit"):
                root = delivery / package
                (root / "test_data").mkdir(parents=True)
                data = fields()
                data["SegmentLength"] = "100"
                data["AccountNumber"] = ""
                payload = {"Financial Transaction Request": {
                    "MessageFormatVersionIdentifier": "ATL105", "NumberofSegments": "01",
                    "Standard Message Data Segment": data}}
                path = root / "test_data" / "negative.json"
                path.write_text(json.dumps(payload))
                (root / "run_set.json").write_text(json.dumps({
                    "index": [{"id": "TC-NEG", "test_data_files": ["negative.json"]}]}))
                frozen.append({"path": str(path.relative_to(delivery)), "sha256": model.base.digest(path)})
            case = {"id": "TC-NEG", "scenario_id": "TS-NEG", "polarity": "NEGATIVE",
                    "negative_class": "MISSING_REQUIRED", "violated_element_name": "AccountNumber"}
            with patch.object(model.base.gates, "read_cases", return_value=[case]):
                rows = model.physical_observations(delivery, frozen)
            for row in rows:
                self.assertIn("REPRESENTATION:AccountNumber", row["failures"])
                self.assertEqual(row["caseIntents"][0]["polarity"], "NEGATIVE")
                self.assertEqual(row["caseIntents"][0]["violatedElementName"], "AccountNumber")
                self.assertEqual(row["defectClassification"], "OBSERVATION_REQUIRES_OBJECTIVE_AND_SOURCE_REVIEW")
                self.assertNotIn("payload", row)
                self.assertNotIn("TerminalIdentifier", json.dumps(row))

    def test_v2_governance_and_framework_tasks_are_consistent(self):
        entry = model.base.gates.load(model.base.ATL / "training-status.json")[
            "transactionContextTraining"]["NONMASTER_PROGRAMS_2026_4"]
        self.assertEqual(entry["status"], "IN_PROGRESS")
        self.assertEqual(entry["counts"]["additionalProgramRules"], len(self.doc["rules"]))
        self.assertEqual(entry["counts"]["applicationFixtures"], len(app.fixture_suite()))
        self.assertIsNone(entry["approvedDenominator"])
        self.assertIsNone(entry["confirmedRequirementCoveragePercent"])
        for key in ("evidence", "coverageReport", "completionRegister", "previousEvidence",
                    "generator", "regressionTest"):
            self.assertTrue((model.base.ROOT / entry[key]).is_file(), key)
        tasks = model.base.gates.load(model.base.ROOT / "framework-tasks.json")["tasks"]
        names = [row["name"] for row in tasks]
        self.assertEqual(len(names), len(set(names)))
        self.assertTrue({"nonmaster-v2-build", "nonmaster-v2-check", "nonmaster-v2-test"} <= set(names))

    def test_missing_outputs_and_delivery_overlap_fail_explicitly(self):
        with tempfile.TemporaryDirectory() as temp:
            root = Path(temp)
            delivery = root / "delivery"
            delivery.mkdir()
            with self.assertRaisesRegex(ValueError, "disjoint"):
                model.run(delivery, root)
            output = root / "output"
            output.mkdir()
            with self.assertRaisesRegex(ValueError, "New output"):
                model.run(delivery, output)


if __name__ == "__main__":
    unittest.main()
