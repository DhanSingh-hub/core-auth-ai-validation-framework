import copy
import importlib.util
import json
import subprocess
import sys
import tempfile
import unittest
from pathlib import Path


SCRIPT = Path(__file__).with_name("new-format-validation.py")
FIXTURE_PATH = Path(__file__).with_name("build-new-format-release-fixtures.py")
SPEC = importlib.util.spec_from_file_location("release_fixture_tests", FIXTURE_PATH)
assert SPEC is not None and SPEC.loader is not None
fixtures = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(fixtures)
validator = fixtures.validator


def only_rule(document: dict, rule: str) -> dict:
    selected = copy.deepcopy(document)
    for kind in validator.KINDS:
        selected[kind] = [row for row in selected[kind] if row["sourceAnchors"][0]["rule"] == rule]
    return selected


class NewFormatValidationTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.documents = {version: fixtures.sample(version) for version in sorted(validator.VERSIONS)}

    def setUp(self):
        self.document = copy.deepcopy(self.documents["2026-4"])

    def test_independent_release_chains_execute_without_expectation_mismatches(self):
        for version, document in self.documents.items():
            report, normalized = validator.validate(document)
            self.assertEqual(report["mismatchedExpectationCount"], 0)
            self.assertEqual(report["semanticContradictionCount"], 0)
            self.assertEqual(report["wireFailureCount"], 0)
            self.assertGreater(report["assessedAssertionCount"], 0)
            self.assertGreater(report["unassessedAssertionCount"], 0)
            self.assertEqual(report["testExpectationAgreement"], "NOT_ASSESSED")
            for kind in validator.KINDS:
                self.assertEqual(normalized[kind], document[kind])
            self.assertEqual(normalized["manifest"]["specificationVersion"], version)

    def test_baseline_profile_counts_and_no_false_full_readiness(self):
        expected = {"2026-3": (3, 23), "2026-4": (10, 80)}
        for version, (rules, cases) in expected.items():
            report, normalized = validator.validate(self.documents[version])
            self.assertEqual(len(report["supportedRuleProfiles"]), rules)
            self.assertEqual(report["counts"], dict(zip(validator.KINDS, [rules, rules, cases, cases])))
            self.assertFalse(report["readiness"]["completeValidationReady"])
            self.assertEqual(report["readiness"]["status"], "BLOCKED")
            self.assertIsNone(report["readiness"]["wholeSpecCoveragePercent"])
            self.assertEqual(report["overallStatus"], "REVIEW_REQUIRED")
            self.assertEqual(report["hostOutcome"], "NOT_ASSESSED")
            self.assertEqual(report["semanticProseEquivalence"], "NOT_ASSESSED")
            self.assertFalse(report["certified"])
            self.assertFalse(report["coveragePromotion"])
            self.assertFalse(normalized["adapter"]["canonicalExecutionReady"])

    def test_all_profiles_bind_pinned_source_pages_and_exact_quotes(self):
        for version in validator.VERSIONS:
            for profile in validator.profiles(version).values():
                self.assertEqual(profile["status"], "DRAFT_REVIEW_REQUIRED")
                for source in profile["sourceEvidence"]:
                    self.assertEqual(source["sha256"], validator.SOURCE_FILES[version][1])
                    self.assertGreaterEqual(source["endLine"], source["startLine"])
                    self.assertIn(f"--- PAGE {source['pdfPage']} ---", source["quote"])

    def test_source_hash_mismatch_is_not_silently_retrained(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            target = root / validator.SOURCE_FILES["2026-4"][0]
            target.parent.mkdir(parents=True)
            target.write_text("modified independent source", encoding="utf-8")
            with self.assertRaisesRegex(ValueError, "hash changed"):
                validator.validate(self.document, root)

    def test_source_symlink_cannot_escape_trusted_root(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            target = root / validator.SOURCE_FILES["2026-4"][0]
            target.parent.mkdir(parents=True)
            # Path traversal is independently tested even on Windows without symlink privilege.
            old = validator.SOURCE_FILES["2026-4"]
            try:
                validator.SOURCE_FILES["2026-4"] = ("../outside.txt", old[1])
                with self.assertRaisesRegex(ValueError, "escapes"):
                    validator.validate(self.document, root)
            finally:
                validator.SOURCE_FILES["2026-4"] = old

    def test_mixed_release_manifest_context_and_anchors_are_rejected(self):
        mutations = [
            lambda d: d["manifest"].update(specificationVersion="2027-1"),
            lambda d: d["testData"][0]["businessContext"].update(version="2026-3"),
            lambda d: d["testCases"][0]["sourceAnchors"][0].update(version="2026-3"),
            lambda d: d["manifest"].update(strictExecutionContract=True),
            lambda d: d.update(schemaVersion=True),
        ]
        for mutate in mutations:
            document = copy.deepcopy(self.document)
            mutate(document)
            with self.assertRaises(ValueError):
                validator.validate(document)

    def test_multi_context_chain_and_nine_group_views_do_not_change_oracle(self):
        for group in sorted(validator.network.NETWORK_GROUPS | validator.network.GENERIC_GROUPS):
            document = only_rule(self.document, "sequence")
            for kind in validator.KINDS:
                for row in document[kind]:
                    row["businessContext"] = fixtures.context("2026-4", group)
            report, _ = validator.validate(document)
            self.assertEqual(report["testExpectationAgreement"], "PASS", group)
            self.assertEqual(report["networkEligibility"], "REVIEW_REQUIRED")

    def test_unknown_context_is_retained_and_unassessed_not_eligible(self):
        for changes in [{"groupId": "UNSUPPORTED-NETWORK", "network": "UNSUPPORTED-NETWORK"},
                        {"messageFamily": "CAPK_FILE_LOAD_REQUEST"},
                        {"domain": "OPERATIONS"}]:
            document = only_rule(self.document, "sequence")
            for kind in validator.KINDS:
                for row in document[kind]:
                    row["businessContext"].update(changes)
            report, _ = validator.validate(document)
            self.assertEqual(report["testExpectationAgreement"], "NOT_ASSESSED")
            self.assertTrue(all(row["status"] == "NOT_ASSESSED" for row in report["brAssessments"]))

    def test_graph_breaks_and_type_errors_are_explicit(self):
        mutations = [
            lambda d: d["testScenarios"][0].update(requirementIds=["MISSING"]),
            lambda d: d["testCases"][0].update(scenarioIds=["MISSING"]),
            lambda d: d["testData"][0].update(testCaseIds=["MISSING"]),
            lambda d: d["testData"][0]["businessContext"].update(network="VISA"),
            lambda d: d["testCases"][0].update(assertions=[]),
            lambda d: d["testCases"][0]["assertions"].append(copy.deepcopy(d["testCases"][0]["assertions"][0])),
            lambda d: d["testData"][0].update(payload={}),
            lambda d: d["testData"][0]["payload"].update(observations=[]),
            lambda d: d["testData"].pop(0),
            lambda d: d["businessRequirements"].append(copy.deepcopy(d["businessRequirements"][0])),
            lambda d: d["testCases"][0].update(steps="not-a-list"),
            lambda d: d["testCases"][0].update(id=d["businessRequirements"][0]["id"]),
            lambda d: d["testData"][0].update(sourceAnchors=[]),
            lambda d: d["testCases"][0]["sourceAnchors"][0].update(section="wrong"),
        ]
        for mutate in mutations:
            document = copy.deepcopy(self.document)
            mutate(document)
            with self.assertRaises(ValueError):
                validator.validate(document)

    def test_claim_contradiction_fails_instead_of_becoming_unsupported(self):
        document = only_rule(self.document, "sequence")
        document["businessRequirements"][0]["claim"]["minimum"] = 0
        report, _ = validator.validate(document)
        self.assertEqual(report["testExpectationAgreement"], "FAIL")
        self.assertEqual(report["semanticContradictionCount"], 1)
        self.assertEqual(report["brAssessments"][0]["status"], "FAIL")

    def test_unknown_rule_not_assessed_and_records_not_dropped(self):
        document = only_rule(self.document, "sequence")
        for kind in validator.KINDS:
            for row in document[kind]:
                row["sourceAnchors"][0]["rule"] = "not-implemented"
        for case in document["testCases"]:
            case["assertions"][0]["rule"] = "not-implemented"
        report, normalized = validator.validate(document)
        self.assertEqual(report["testExpectationAgreement"], "NOT_ASSESSED")
        self.assertEqual(normalized["businessRequirements"], document["businessRequirements"])

    def test_producer_claimed_approval_never_promotes_and_metadata_is_preserved(self):
        document = only_rule(self.document, "sequence")
        document["manifest"]["producer"] = "AI_SOLUTION"
        document["businessRequirements"][0]["statement"] = "Producer prose is not semantically certified."
        for kind in validator.KINDS:
            for row in document[kind]:
                row.update(status="APPROVED", producerConfidence=1.0, customMetadata={"preserve": True})
        document["testData"][0]["payload"]["observations"]["extra-unassessed"] = {"anything": "retained"}
        report, normalized = validator.validate(document)
        self.assertEqual(report["testExpectationAgreement"], "PASS")
        self.assertFalse(report["readiness"]["completeValidationReady"])
        self.assertEqual(report["semanticProseEquivalence"], "NOT_ASSESSED")
        self.assertIn("extra-unassessed", report["unconsumedObservationIds"][document["testData"][0]["id"]])
        for kind in validator.KINDS:
            self.assertEqual(normalized[kind], document[kind])

    def test_old_refund_claim_cannot_grant_target_success(self):
        document = only_rule(self.document, "online-refund")
        document["businessRequirements"][0]["claim"] = copy.deepcopy(
            validator.profiles("2026-3")["online-refund"]["claim"])
        report, _ = validator.validate(document)
        self.assertEqual(report["semanticContradictionCount"], 1)
        self.assertEqual(report["testExpectationAgreement"], "FAIL")
        report, _ = validator.validate(only_rule(self.document, "online-refund"))
        self.assertEqual(report["observations"][0]["assessment"]["status"], "NOT_ASSESSED")
        self.assertIn("RETIRED_SOURCE", report["observations"][0]["assessment"]["reason"])

    def test_refund_and_ia_rules_do_not_infer_applicability_from_network_alone(self):
        for rule in ["online-refund", "ia-table82", "ia-lifecycle"]:
            document = only_rule(self.document, rule)
            for kind in validator.KINDS:
                for row in document[kind]:
                    row["businessContext"]["transactionCategory"] = "SALE"
            report, _ = validator.validate(document)
            self.assertEqual(report["assessedAssertionCount"], 0)
            self.assertEqual(report["testExpectationAgreement"], "NOT_ASSESSED")

    def test_requested_unknown_wire_does_not_become_pass_by_matching_unknown_expectation(self):
        document = only_rule(self.document, "sequence")
        for row in document["testCases"]:
            row["assertions"][0]["expectedWireResult"] = "NOT_ASSESSED"
        report, _ = validator.validate(document)
        self.assertEqual(report["testExpectationAgreement"], "NOT_ASSESSED")

    def test_negative_predicate_failure_is_not_an_ai_defect_when_expected(self):
        report, _ = validator.validate(only_rule(self.document, "sequence"))
        failures = [row for row in report["observations"] if row["assessment"]["status"] == "FAIL"]
        self.assertTrue(failures)
        self.assertTrue(all(row["expectationAgreement"] == "PASS" for row in failures))
        self.assertEqual(report["testExpectationAgreement"], "PASS")
        document = only_rule(self.document, "sequence")
        document["testCases"][2]["assertions"][0]["expectedResult"] = "PASS"
        self.assertEqual(validator.validate(document)[0]["mismatchedExpectationCount"], 1)

    def test_release_token_size_numeric_and_date_boundaries(self):
        for version in validator.VERSIONS:
            for size in [1, 12, 13, 18, 19, 20]:
                observation = fixtures.token_observation(version, size=size)
                expected = 1 <= size <= 19 if version == "2026-4" else 13 <= size <= 18
                self.assertEqual(validator.token_tables(observation, version, False)["status"],
                                 "PASS" if expected else "FAIL")
        observation = fixtures.token_observation("2026-4")
        observation["tables"][0]["value"] = "A" * 18
        self.assertEqual(validator.token_tables(observation, "2026-4", False)["status"], "FAIL")
        for expiry, expected in [("3001", "PASS"), ("3012", "PASS"), ("3000", "FAIL"),
                                 ("3013", "FAIL"), ("30AA", "FAIL"), ("30011", "FAIL")]:
            observation = fixtures.token_observation("2026-4")
            observation["tables"][1]["value"] = expiry
            self.assertEqual(validator.token_tables(observation, "2026-4", False)["status"], expected)

    def test_response_table_ids_are_not_interpreted_as_request_expiry(self):
        observation = fixtures.token_observation("2026-4", response=True)
        for value, expected in [("T", "PASS"), ("P", "PASS"), ("Y", "FAIL"), ("3012", "FAIL")]:
            observation["tables"][1].update(value=value, length=f"{len(value):03}")
            self.assertEqual(validator.token_tables(observation, "2026-4", True)["status"], expected)
        self.assertEqual(validator.token_tables(observation, "2026-4", False)["status"], "FAIL")

    def test_table007_is_new_and_unknown_tables_are_not_silent_pass(self):
        observation = fixtures.token_observation("2026-4")
        observation["tables"] = [observation["tables"][-1]]
        self.assertEqual(validator.token_tables(observation, "2026-4", False)["status"], "PASS")
        self.assertEqual(validator.token_tables(observation, "2026-3", False)["status"], "NOT_ASSESSED")
        observation["tables"][0]["value"] = "N"
        self.assertEqual(validator.token_tables(observation, "2026-4", False)["status"], "FAIL")

    def test_token_section_999_byte_cap(self):
        observation = fixtures.token_observation("2026-4")
        observation["tables"] = [{"id": "001", "length": "019", "value": "8" * 19}] * 40
        self.assertEqual(validator.token_tables(observation, "2026-4", False)["status"], "FAIL")

    def test_wire_bytes_exact_separator_length_and_missing_evidence(self):
        for response in [False, True]:
            observation = fixtures.token_observation("2026-4", response)
            self.assertEqual(validator.token_wire(observation, "2026-4", response)["status"], "PASS")
            for change in [{"wireHex": "00"}, {"segmentLength": "001"}, {"wireHex": "z0"},
                           {"wireHex": "00 11"}, {"wireHex": 10}]:
                bad = {**observation, **change}
                self.assertEqual(validator.token_wire(bad, "2026-4", response)["status"], "FAIL")
            no_wire = copy.deepcopy(observation)
            no_wire.pop("wireHex")
            self.assertEqual(validator.token_wire(no_wire, "2026-4", response)["status"], "NOT_ASSESSED")
            self.assertEqual(validator.token_wire(observation, "2026-3", response)["status"], "NOT_ASSESSED")

    def test_fixed_wire_vectors_independent_of_fixture_length_builder(self):
        request = {"segmentType": "153", "segmentLength": "014",
                   "tables": [{"id": "004", "length": "001", "value": "Y"}],
                   "wireHex": b"153\x1c014004001Y".hex()}
        response = {"segmentType": "154", "segmentLength": "013",
                    "tables": [{"id": "002", "length": "001", "value": "T"}],
                    "wireHex": b"154013002001T".hex()}
        self.assertEqual(validator.token_wire(request, "2026-4", False)["status"], "PASS")
        self.assertEqual(validator.token_wire(response, "2026-4", True)["status"], "PASS")
        request["wireHex"] = b"153\x1c014\x1c004001Y".hex()
        response["wireHex"] = b"154\x1c013002001T".hex()
        self.assertEqual(validator.token_wire(request, "2026-4", False)["status"], "FAIL")
        self.assertEqual(validator.token_wire(response, "2026-4", True)["status"], "FAIL")

    def test_expected_wire_negative_is_separate_from_field_result(self):
        document = only_rule(self.document, "token-response")
        report, _ = validator.validate(document)
        wire_negative = next(row for row in report["observations"] if row["testCaseId"].endswith("WIRE_CHANGED"))
        self.assertEqual(wire_negative["assessment"]["status"], "PASS")
        self.assertEqual(wire_negative["wire"]["status"], "FAIL")
        self.assertEqual(wire_negative["wireExpectationAgreement"], "PASS")
        self.assertEqual(report["wireFailureCount"], 0)
        next(row for row in document["testCases"] if row["id"].endswith("WIRE_CHANGED"))["assertions"][0]["expectedWireResult"] = "PASS"
        self.assertEqual(validator.validate(document)[0]["wireFailureCount"], 1)

    def test_lifecycle_observations_cannot_be_replaced_by_producer_pass(self):
        observation = fixtures.ia_observation()
        self.assertEqual(validator.ia_lifecycle(observation)["status"], "PASS")
        observation["steps"].append(copy.deepcopy(observation["steps"][1]))
        self.assertEqual(validator.ia_lifecycle(observation)["status"], "FAIL")
        observation = fixtures.ia_observation()
        observation.update(historyComplete="true")
        self.assertEqual(validator.ia_lifecycle(observation)["status"], "NOT_ASSESSED")
        observation = fixtures.ia_observation()
        observation["steps"][1].pop("approvalCodeDE39")
        self.assertEqual(validator.ia_lifecycle(observation)["status"], "NOT_ASSESSED")

    def test_network_specific_table82_absence_is_not_universal_failure(self):
        profile = validator.profiles("2026-4")["ia-table82"]
        for group in ["DISCOVER", "VISA", "MASTERCARD", "AMEX"]:
            outcome = validator.assess(profile, {"present": False}, fixtures.context("2026-4", group))
            self.assertEqual(outcome["status"], "FAIL" if group == "DISCOVER" else "NOT_ASSESSED")

    def test_discover_india_country_role_and_recommendation_are_not_generic_mandates(self):
        document = only_rule(self.document, "discover-india056")
        report, _ = validator.validate(document)
        self.assertEqual(report["mismatchedExpectationCount"], 0)
        for changes in [{"issuerCountry": "USA"}, {"issuerCountry": "UNRESOLVED", "merchantCountry": "INDIA"},
                        {"network": "VISA", "groupId": "VISA"}]:
            altered = copy.deepcopy(document)
            for kind in validator.KINDS:
                for row in altered[kind]:
                    row["businessContext"].update(changes)
            report, _ = validator.validate(altered)
            self.assertEqual(report["assessedAssertionCount"], 0)
        profile = validator.profiles("2026-4")["discover-india056"]
        for value, expected in [("01", "FAIL"), ("02", "PASS"), ("99", "PASS"),
                                ("UD", "PASS"), ("UC", "PASS"), ("AA", "FAIL")]:
            observation = {"subtableId": "01", "paymentRole": "INITIAL", "present": True, "length": "02", "value": value}
            self.assertEqual(validator.discover_india(observation, profile)["status"], expected)
        for tag, value, role, expected in [
            ("05", "09", "INITIAL", "FAIL"), ("05", "08", "INITIAL", "PASS"),
            ("06", "2", "SUBSEQUENT", "FAIL"), ("06", "1", "INITIAL", "FAIL"),
        ]:
            observation = {"subtableId": tag, "paymentRole": role, "present": True,
                           "length": f"{len(value):02}", "value": value}
            self.assertEqual(validator.discover_india(observation, profile)["status"], expected)

    def test_device_extensions_do_not_reject_future_reserved_range(self):
        profile = validator.profiles("2026-4")["device022"]
        for value in ["35", "36", "37"]:
            self.assertEqual(validator.assess(profile, {"deviceType": value}, {})["status"], "PASS")
        for value in ["38", "99"]:
            self.assertEqual(validator.assess(profile, {"deviceType": value}, {})["status"], "NOT_ASSESSED")
        self.assertEqual(validator.assess(profile, {"deviceType": 35}, {})["status"], "FAIL")

    def test_table063_network_scope_and_should_continuity_do_not_invent_declines(self):
        profile = validator.profiles("2026-4")["ia-table063"]
        context = fixtures.context("2026-4", "MASTERCARD")
        context["industryUsage"] = "INCREMENTAL_AUTH"
        self.assertTrue(validator.scoped(profile, context))
        self.assertEqual(validator.assess(profile, {"present": False}, context)["status"], "FAIL")
        observation = {"present": True, "role": "SUBSEQUENT", "terminalTransactionNumber": "0042",
                       "originalTerminalTransactionNumber": "0043"}
        self.assertEqual(validator.assess(profile, observation, context)["status"], "NOT_ASSESSED")
        context.update(network="DISCOVER", groupId="DISCOVER")
        self.assertFalse(validator.scoped(profile, context))

    def test_crosswalk_and_training_remain_drafts_and_source_bound(self):
        files = fixtures.build()
        crosswalk = json.loads(files["release-crosswalk.json"])
        self.assertEqual(len(crosswalk["rules"]), 10)
        self.assertEqual(next(row for row in crosswalk["rules"] if row["rule"] == "online-refund")["relationship"],
                         "RETIRED")
        self.assertTrue(all(row["approved"] is False for row in crosswalk["rules"]))
        lessons = json.loads(files["draft-training-lessons.json"])
        self.assertFalse(lessons["modelWeightsUpdated"])
        self.assertEqual(len(lessons["lessons"]), 13)
        manifest = json.loads(files["manifest.json"])
        for name, digest in manifest["files"].items():
            self.assertEqual(validator.shared.fingerprint(files[name].encode("utf-8")), digest)

    def test_registered_supplement_does_not_promote_active_baseline_or_segment_counts(self):
        status = validator.load(validator.PACK / "training-status.json")
        registered = status["referenceTraining"]["newFormatReleaseValidation"]
        self.assertEqual(status["specificationVersion"], "2026-3")
        self.assertEqual(status["summary"]["totalSegments"], 49)
        self.assertEqual(status["summary"]["totalCataloguedRules"], 605)
        self.assertEqual(status["summary"]["trainedForIntake"], 0)
        self.assertEqual(registered["status"], "IN_PROGRESS")
        self.assertFalse(registered["completeValidationReady"])
        self.assertTrue(registered["countsExcludedFromSegmentCatalogSummary"])
        for version, profile_key, case_key in [
            ("2026-3", "baselineProfileCount", "baselineDraftCaseCount"),
            ("2026-4", "targetProfileCount", "targetDraftCaseCount"),
        ]:
            self.assertEqual(registered[profile_key], len(self.documents[version]["businessRequirements"]))
            self.assertEqual(registered[case_key], len(self.documents[version]["testCases"]))
        tasks = validator.load(validator.PACK.parents[1] / "framework-tasks.json")["tasks"]
        names = [row["name"] for row in tasks]
        self.assertEqual(len(names), len(set(names)))
        for name in ["new-format-readiness-build", "new-format-readiness-check", "new-format-readiness-test"]:
            self.assertIn(name, names)

    def test_multiple_brs_per_case_require_all_assertions(self):
        document = only_rule(self.document, "sequence")
        original = document["businessRequirements"][0]
        second = copy.deepcopy(original)
        second["id"] = "OTHER-BR-SAME-INDEPENDENT-OBLIGATION"
        document["businessRequirements"].append(second)
        document["testScenarios"][0]["requirementIds"].append(second["id"])
        for case in document["testCases"]:
            assertion = copy.deepcopy(case["assertions"][0])
            assertion["requirementId"] = second["id"]
            case["assertions"].append(assertion)
        report, _ = validator.validate(document)
        self.assertEqual(report["testExpectationAgreement"], "PASS")
        self.assertEqual(report["assessedAssertionCount"], 18)
        document["testCases"][0]["assertions"].pop()
        with self.assertRaisesRegex(ValueError, "per linked BR"):
            validator.validate(document)

    def test_duplicate_keys_nonfinite_and_deterministic_publication(self):
        with tempfile.TemporaryDirectory() as temporary:
            path = Path(temporary) / "invalid.json"
            for raw in ['{"a":1,"a":2}', '{"a":NaN}', '{"a":Infinity}']:
                path.write_text(raw, encoding="utf-8")
                with self.assertRaises(ValueError):
                    validator.load(path)
        document = only_rule(self.document, "sequence")
        self.assertEqual(validator.outputs(document, "digest"), validator.outputs(document, "digest"))
        self.assertEqual(fixtures.build(), fixtures.build())

    def test_cli_preserves_bom_original_bytes_output_and_exit_dispositions(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            path = root / "ai.json"
            document = only_rule(self.document, "sequence")
            document["manifest"]["producer"] = "AI_SOLUTION"
            raw = b"\xef\xbb\xbf" + validator.shared.json_text(document).encode("utf-8")
            path.write_bytes(raw)
            output = root / "result"
            command = [sys.executable, str(SCRIPT), "--input", str(path), "--output", str(output)]
            completed = subprocess.run(command, capture_output=True, text=True)
            self.assertEqual(completed.returncode, 0, completed.stderr)
            self.assertEqual(path.read_bytes(), raw)
            self.assertEqual((output / "input-snapshot.json").read_bytes(), raw)
            manifest = validator.load(output / "manifest.json")
            for name, digest in manifest["files"].items():
                self.assertEqual(validator.shared.fingerprint((output / name).read_bytes()), digest)
            self.assertEqual(subprocess.run(command, capture_output=True).returncode, 2)
            # The legacy entry point routes V2 without changing the V1 contract.
            legacy = SCRIPT.with_name("new-format-sample.py")
            command[1] = str(legacy)
            command[-1] = str(root / "legacy-route")
            self.assertEqual(subprocess.run(command, capture_output=True).returncode, 0)
            legacy_output = root / "legacy-route"
            self.assertEqual((legacy_output / "manifest.json").read_bytes(), (output / "manifest.json").read_bytes())
            document["testCases"][0]["assertions"][0]["expectedResult"] = "FAIL"
            path.write_text(validator.shared.json_text(document), encoding="utf-8")
            command[1] = str(SCRIPT)
            command[-1] = str(root / "failed")
            self.assertEqual(subprocess.run(command, capture_output=True).returncode, 1)
            document = only_rule(self.document, "online-refund")
            path.write_text(validator.shared.json_text(document), encoding="utf-8")
            command[-1] = str(root / "unassessed")
            self.assertEqual(subprocess.run(command, capture_output=True).returncode, 2)


if __name__ == "__main__":
    unittest.main()
