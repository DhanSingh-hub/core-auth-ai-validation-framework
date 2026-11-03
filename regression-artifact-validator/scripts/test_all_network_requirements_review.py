import copy
import csv
import importlib.util
import io
import json
import shutil
import tempfile
import unittest
from pathlib import Path


SCRIPT = Path(__file__).with_name("build-all-network-requirements-review.py")
SPEC = importlib.util.spec_from_file_location("all_network_review", SCRIPT)
assert SPEC is not None and SPEC.loader is not None
review = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(review)


class AllNetworkRequirementsReviewTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.document = review.shared.read_json(review.INPUT)
        cls.outputs = review.build(review.INPUT, review.PACK)
        cls.report = json.loads(cls.outputs["review.json"])

    def test_exact_scope_and_nonadditive_dimensions(self):
        summary = self.report["summary"]
        for key, count in {
            "section5CategoryCount": 16, "financialCardTypeCount": 36, "receiptLabelCount": 9,
            "transactionCodeCount": 23, "generalLifecycleFamilyCount": 5,
            "specialPromptCodeCount": 11,
            "specializedLifecycleProfileCount": 6, "lifecycleProfileCount": 11,
            "openQuestionCount": 10, "eligibilityReviewCellCount": 44,
            "networkGroupCount": 7, "genericGroupCount": 2, "primaryGroupCount": 9,
            "reviewProfileCount": 12, "draftRequirementCount": 145,
            "contextualDraftBrCount": 176, "contextualDraftScenarioCount": 176,
            "linkedSharedRequirementCount": 64, "unmappedSharedRequirementCount": 81,
            "completeChainCount": 0, "authoredTestCaseCount": 0,
            "approvedEligibilityRuleCount": 0, "authoredTestDataCount": 0, "executedCaseCount": 0,
        }.items():
            self.assertEqual(summary[key], count, key)
        self.assertEqual(len(self.report["contexts"]), 9)
        self.assertIn("NOT 61 networks", self.outputs["README.md"])
        self.assertEqual({row["id"] for row in self.report["financialCardTypes"]},
                         {"001", "011", "012", "013", "020", "040", "041", "045", "046",
                          "050", "051", "052", "053", "055", "056", "057", "058", "059",
                          "060", "061", "064", "070", "071", "072", "073", "074", "075",
                          "077", "078", "079", "080", "081", "083", "084", "085", "090"})

    def test_primary_navigation_is_seven_networks_plus_two_generic_groups(self):
        groups = self.report["contexts"]
        self.assertEqual(len(groups), 9)
        self.assertEqual({row["id"] for row in groups if row["kind"] == "NETWORK"},
                         review.NETWORK_GROUPS)
        self.assertEqual({row["id"] for row in groups if row["kind"] == "GENERIC_CATEGORY"},
                         {"DEBIT", "EBT"})
        self.assertNotIn("CREDIT_NONPROPRIETARY", {row["id"] for row in groups})

    def test_all_rows_have_source_evidence_and_draft_gates(self):
        requirements = self.report["requirements"]
        self.assertEqual(len(requirements), self.report["summary"]["draftRequirementCount"])
        for key in ("id", "fr", "scenarioId"):
            self.assertEqual(len({row[key] for row in requirements}), len(requirements))
        for row in requirements:
            self.assertEqual(row["status"], "DRAFT_REVIEW_REQUIRED")
            self.assertEqual(row["actualResult"], "Not Executed")
            self.assertEqual(row["testDataReference"], "NOT_AUTHORED")
            self.assertTrue(row["checks"])
            self.assertTrue(row["questions"])
            for source in row["sourceEvidence"]:
                raw = (review.PACK / source["path"]).read_bytes()
                self.assertEqual(review.shared.fingerprint(raw), source["sha256"])
                self.assertIn(source["anchor"],
                              raw.decode("utf-8-sig").splitlines()[source["line"] - 1])

    def test_matrix_is_explicitly_not_an_executable_policy(self):
        rows = list(csv.DictReader(io.StringIO(self.outputs["network-flow-review.csv"])))
        expected = {(profile["groupId"], profile["id"], flow)
                    for profile in self.document["businessNavigation"]["profiles"]
                    for flow in profile["flowIds"]}
        self.assertEqual({(r["Context ID"], r["Profile ID"], r["Flow ID"]) for r in rows}, expected)
        self.assertEqual(len(rows), 44)
        requirements = {row["id"] for row in self.report["requirements"]}
        contextual = {row["id"] for row in self.report["businessNavigation"]["businessRequirements"]}
        for row in rows:
            self.assertEqual(row["Eligibility"], "REVIEW_REQUIRED")
            self.assertIn("NOT a support/denial policy", row["Meaning"])
            self.assertIn(row["Shared Requirement ID"], requirements)
            self.assertIn(row["Context Requirement ID"], contextual)
            self.assertEqual(row["BA Decision"], "")

    def test_training_excludes_questions_matrix_and_release_overlay(self):
        training = json.loads(self.outputs["training-lessons.json"])
        for key in ("certified", "executionReady", "baselineChanged",
                    "modelWeightsUpdated", "sourceCompletenessCertified"):
            self.assertIs(training[key], False)
            self.assertIs(self.report[key], False)
        requirements = {row["id"]: row for row in self.report["requirements"] if row["teaching"]}
        self.assertEqual({row["requirementId"] for row in training["lessons"]}, requirements.keys())
        self.assertEqual(len(training["lessons"]), self.report["summary"]["draftLessonCount"])
        for lesson in training["lessons"]:
            row = requirements[lesson["requirementId"]]
            self.assertEqual(lesson["statement"], row["statement"])
            self.assertEqual(lesson["sourceEvidence"], row["sourceEvidence"])
            self.assertFalse(any(source["sourceId"] == "afOverlay" for source in lesson["sourceEvidence"]))
            self.assertNotIn("questions", lesson)
        self.assertNotIn("NET-REQ-RELEASE-OVERLAY", requirements)
        self.assertNotIn("NET-REQ-DOWNSTREAM-GATE", requirements)

    def test_network_specific_exceptions_are_not_mastercard_copies(self):
        rows = {row["id"]: row for row in self.report["requirements"]}
        for identifier, fragment in {
            "NET-REQ-CREDIT-EXPIRY": "performs expiry validation",
            "NET-REQ-DEBIT-EXPIRY": "do not perform device expiry validation",
            "NET-REQ-EBT-ENTRY": "does not perform device expiry validation",
            "NET-REQ-PINLESS-PROFILE": "specific-BIN POS Plus",
            "NET-REQ-STAR-RESTRICTIONS": "no keyed/MOTO",
            "NET-REQ-SVC-RECEIPT": "masks the last four",
            "NET-REQ-EWIC-PROFILE": "Return transactions are not supported",
            "NET-REQ-SVC-VERIFY": "not associated",
            "NET-REQ-TOR-ORDER": "same device",
            "NET-REQ-AMEX-TRACK": "Track 1",
            "NET-REQ-MASTERCARD-AUTH": "30-day versus 7-day",
            "NET-REQ-VISA-AE": "do not reset",
        }.items():
            self.assertIn(fragment, rows[identifier]["statement"], identifier)
        self.assertEqual(rows["NET-REQ-AMEX-TRACK"]["scope"], "AMEX")
        self.assertEqual(rows["NET-REQ-MASTERCARD-AUTH"]["scope"], "MASTERCARD")

    def test_lifecycle_pairs_and_specialized_roles(self):
        flows = {row["id"]: row for row in self.report["flows"]}
        for identifier, pairs in review.GENERAL_PAIRS.items():
            self.assertEqual(flows[identifier]["pairs"], pairs)
        for identifier in review.SPECIAL_FLOWS:
            self.assertEqual(flows[identifier]["pairs"], [])
        self.assertIn("INCREMENTAL (zero or more)", flows["VISA_ESTIMATED_INCREMENTAL"]["legs"])
        self.assertIn("dedicated", flows["EWIC_AUTH_COMPLETION"]["scope"].lower())

    def test_csv_and_question_links_reconcile(self):
        rows = list(csv.DictReader(io.StringIO(self.outputs["shared-requirements-library.csv"])))
        questions = list(csv.DictReader(io.StringIO(self.outputs["ba-sme-questionnaire.csv"])))
        self.assertEqual(len(rows), self.report["summary"]["draftRequirementCount"])
        by_id = {row["BR ID (supplement only)"]: row for row in rows}
        for question in questions:
            self.assertEqual(question["Register ID"], "SEG100-SME-004")
            self.assertEqual(question["Answer"], "")
            affected = set(question["Affected Requirement IDs"].split(";"))
            expected = {row["id"] for row in self.report["requirements"]
                        if question["Question ID"] in row["questions"]}
            self.assertEqual(affected, expected)
            self.assertTrue(affected <= by_id.keys())
            context_rows = self.report["businessNavigation"]["businessRequirements"]
            context_expected = {row["id"] for row in context_rows if question["Question ID"] in row["questionIds"]}
            self.assertEqual(set(filter(None, question["Affected Context BR IDs"].split(";"))), context_expected)

    def test_missing_or_duplicate_dimension_entries_fail(self):
        for key in ("transactions", "receiptLabels", "specialPrompts", "flows", "questions", "rules"):
            for duplicate in (True, False):
                document = copy.deepcopy(self.document)
                if duplicate:
                    document[key].append(copy.deepcopy(document[key][0]))
                else:
                    document[key].pop()
                with self.assertRaises(ValueError, msg=f"{key} duplicate={duplicate}"):
                    review.validate(document, review.PACK)

    def test_incorrect_transaction_scope_and_code_fail(self):
        for value, index in (("0", 0), ("STANDARD_FINANCIAL", 2)):
            document = copy.deepcopy(self.document)
            document["transactions"][7][index] = value
            with self.assertRaises(ValueError):
                review.validate(document, review.PACK)

    def test_invalid_lifecycle_pairings_fail(self):
        for identifier, pairs in {
            "AUTH_CANCELLATION": [["3", "8"]],
            "SALE_REVERSAL": [["0", "C"], ["6", "8"]],
            "SVC_CANCELLATION": [["3", "S"]],
        }.items():
            document = copy.deepcopy(self.document)
            next(flow for flow in document["flows"] if flow["id"] == identifier)["pairs"] = pairs
            with self.assertRaisesRegex(ValueError, "lifecycle"):
                review.validate(document, review.PACK)

    def test_unknown_links_contexts_and_missing_sources_fail(self):
        for key, value in (("sources", ["missing"]), ("sources", []),
                           ("questions", ["missing"]), ("questions", []),
                           ("scope", ["UNKNOWN_NETWORK"])):
            document = copy.deepcopy(self.document)
            document["rules"][0][key] = value
            with self.assertRaises(ValueError):
                review.validate(document, review.PACK)

    def test_missing_ambiguous_unsafe_evidence_fails(self):
        for field, value in (("anchor", "absent-source-anchor"), ("anchor", ""),
                             ("path", "../outside.txt")):
            document = copy.deepcopy(self.document)
            document["sources"]["account"][field] = value
            with self.assertRaises(ValueError):
                review.validate(document, review.PACK)

    def test_no_automatic_baseline_or_overlay_promotion(self):
        for field, value in (("status", "APPROVED"), ("baselineChanged", True),
                             ("specificationVersion", "2026-4")):
            document = copy.deepcopy(self.document)
            document[field] = value
            with self.assertRaises(ValueError):
                review.validate(document, review.PACK)
        document = copy.deepcopy(self.document)
        next(row for row in document["rules"] if row["id"] == "RELEASE-OVERLAY")["teaching"] = True
        with self.assertRaises(ValueError):
            review.validate(document, review.PACK)

    def test_determinism_and_every_output_hash(self):
        self.assertEqual(self.outputs, review.build(review.INPUT, review.PACK))
        manifest = json.loads(self.outputs["manifest.json"])
        self.assertEqual(manifest["inputSha256"], review.shared.fingerprint(review.INPUT.read_bytes()))
        for name, fingerprint in manifest["outputHashes"].items():
            self.assertEqual(fingerprint, review.shared.fingerprint(self.outputs[name].encode("utf-8")))
        self.assertEqual(manifest["helperSha256"], review.shared.fingerprint(review.HELPER_PATH.read_bytes()))

    def test_read_only_check_rejects_stale_and_missing_outputs(self):
        with tempfile.TemporaryDirectory() as temporary:
            output = Path(temporary)
            review.publish(self.outputs, output, False)
            review.publish(self.outputs, output, True)
            path = output / "requirements-review.csv"
            path.write_bytes(b"returned BA edit")
            with self.assertRaisesRegex(ValueError, "stale"):
                review.publish(self.outputs, output, True)
            self.assertEqual(path.read_bytes(), b"returned BA edit")
            path.unlink()
            with self.assertRaises(ValueError):
                review.publish(self.outputs, output, True)
            self.assertFalse(path.exists())

    def test_source_mutation_is_detected_without_changing_an_anchor(self):
        with tempfile.TemporaryDirectory() as temporary:
            root = Path(temporary)
            for source in self.document["sources"].values():
                target = root / source["path"]
                target.parent.mkdir(parents=True, exist_ok=True)
                shutil.copyfile(review.PACK / source["path"], target)
            before = review.build(review.INPUT, root)
            path = root / self.document["sources"]["account"]["path"]
            path.write_bytes(path.read_bytes() + b"\nSource mutation for regression test.\n")
            after = review.build(review.INPUT, root)
            self.assertNotEqual(before["manifest.json"], after["manifest.json"])
            self.assertNotEqual(before["training-lessons.json"], after["training-lessons.json"])

    def test_spreadsheet_formula_prefixes_are_escaped(self):
        text = review.shared.as_csv([{"value": "=SUM(1,2)"}, {"value": "+1"}, {"value": "@x"}])
        self.assertTrue(all(row["value"].startswith("'") for row in csv.DictReader(io.StringIO(text))))

    def test_governance_registration_matches_generated_counts_and_paths(self):
        registration = review.shared.read_json(review.PACK / "training-status.json")["referenceTraining"]["allNetworkRequirements"]
        for key in ("section5CategoryCount", "financialCardTypeCount", "receiptLabelCount",
                    "transactionCodeCount", "specialPromptCodeCount", "lifecycleProfileCount",
                    "draftRequirementCount", "eligibilityReviewCellCount", "approvedEligibilityRuleCount",
                    "networkGroupCount", "genericGroupCount", "primaryGroupCount", "reviewProfileCount",
                    "contextualDraftBrCount", "contextualDraftScenarioCount",
                    "linkedSharedRequirementCount", "unmappedSharedRequirementCount",
                    "completeChainCount", "authoredTestCaseCount"):
            self.assertEqual(registration[key], self.report["summary"][key], key)
        self.assertEqual(registration["curatedLessonCount"], self.report["summary"]["draftLessonCount"])
        self.assertEqual(registration["openQuestionCount"], self.report["summary"]["openQuestionCount"])
        for key in ("evidence", "trainingPack", "questionnaire"):
            self.assertTrue((review.shared.MODULE / registration[key]).is_file(), key)
        register = review.shared.read_json(review.PACK / "registers" / "atl105-communication-register.json")
        items = review.shared.unique_index(register["items"], "id")
        item = items[registration["communicationRegisterId"]]
        self.assertEqual(item["status"], "OPEN")
        self.assertEqual(item["segments"], ["100"])
        self.assertIn("100", register["segments"])
        self.assertTrue(all((review.shared.MODULE / path).is_file() for path in item["evidence"]))

    def test_all_shared_requirements_are_preserved_linked_or_explicitly_unmapped(self):
        navigation = self.report["businessNavigation"]
        library = {row["id"] for row in self.report["requirements"]}
        linked, unmapped = set(navigation["linkedSharedRequirementIds"]), set(navigation["unmappedSharedRequirementIds"])
        self.assertEqual(linked | unmapped, library)
        self.assertFalse(linked & unmapped)
        rows = list(csv.DictReader(io.StringIO(self.outputs["shared-library-mapping.csv"])))
        self.assertEqual({row["Shared Requirement ID"] for row in rows}, library)
        self.assertEqual(len(rows), 145)
        for row in rows:
            if row["Shared Requirement ID"] in unmapped:
                self.assertEqual(row["Mapping Disposition"], "UNMAPPED_REVIEW_REQUIRED")
                self.assertEqual(row["Group IDs"], "")
                self.assertEqual(row["Profile IDs"], "")
            else:
                self.assertTrue(row["Group IDs"])
                self.assertTrue(row["Profile IDs"])
        self.assertIn("NET-REQ-SVC-ENTRY", unmapped)
        self.assertIn("NET-REQ-RELEASE-OVERLAY", unmapped)
        self.assertIn("NET-REQ-COMMON-FIELDS", linked)

    def test_context_br_ts_links_are_complete_but_no_tc_td_fabricated(self):
        navigation = self.report["businessNavigation"]
        library = {row["id"]: row for row in self.report["requirements"]}
        brs = review.shared.unique_index(navigation["businessRequirements"], "id")
        scenarios = review.shared.unique_index(navigation["testScenarios"], "id")
        self.assertEqual(len(brs), 176)
        self.assertFalse(navigation["completePackage"])
        self.assertEqual(navigation["testCases"], [])
        self.assertEqual(navigation["testData"], [])
        for br in brs.values():
            source = library[br["sharedRequirementId"]]
            self.assertIn(source["statement"], br["statement"])
            self.assertFalse(br["teaching"])
            self.assertEqual(br["status"], review.STATUS)
            self.assertTrue(br["sourceEvidence"])
            self.assertTrue(br["conditions"])
            scenario = scenarios[br["scenarioIds"][0]]
            self.assertEqual(scenario["requirementIds"], [br["id"]])
            self.assertEqual(scenario["validations"], source["checks"])
            self.assertEqual(scenario["expectedResult"], source["expected"])
            self.assertEqual(scenario["testCaseIds"], [])
            self.assertIn("CONCRETE_TC_NOT_AUTHORED", scenario["chainGaps"])
            review.validate_business_context(br["context"])
        for group in navigation["groups"]:
            self.assertEqual(group["draftContextRequirementCount"], len(group["contextRequirementIds"]))
            self.assertEqual(group["completeChainCount"], 0)
            self.assertGreater(len(group["contextRequirementIds"]), 1)
            self.assertTrue(set(group["contextRequirementIds"]) <= brs.keys())
            self.assertIn(group["name"], self.outputs["NETWORK-REVIEW.md"])

    def test_context_csv_exposes_every_ba_dimension_and_chain_gap(self):
        rows = list(csv.DictReader(io.StringIO(self.outputs["requirements-review.csv"])))
        gaps = list(csv.DictReader(io.StringIO(self.outputs["chain-gaps.csv"])))
        self.assertEqual(len(rows), 176)
        self.assertEqual(len(gaps), 176)
        self.assertEqual({row["BR ID (contextual draft)"] for row in rows}, {row["BR ID"] for row in gaps})
        for row in rows:
            for key in ("Platform", "Specification", "Version", "Card Program", "Commercial Usage",
                        "Network / Generic Group", "Processing Profile", "Transaction Category",
                        "Card Presence", "Entry Mode", "Channel", "Lifecycle Role", "Source References"):
                self.assertTrue(row[key], key)
            self.assertEqual(row["TC IDs"], "")
            self.assertEqual(row["TD IDs"], "")
            self.assertEqual(row["TD Status"], "NOT_AUTHORED")
            self.assertEqual(row["Eligibility"], "REVIEW_REQUIRED")
        self.assertTrue(all(row["Complete Chain"] == "False" for row in gaps))

    def test_business_tree_resolves_exact_hierarchy_without_cartesian_expansion(self):
        navigation = self.report["businessNavigation"]
        tree = navigation["businessTree"]
        found = set()
        for br in navigation["businessRequirements"]:
            context = br["context"]
            node = tree[context["platform"]][f"{context['specification']}/{context['version']}"]
            for value in (context["cardProgram"], context["groupId"], context["transactionCategory"],
                          context["cardPresence"], context["entryMode"], context["channel"],
                          context["flowId"] or context["lifecycleRole"]):
                node = node[value]
            self.assertIn(br["id"], node["requirementIds"])
            self.assertTrue(set(br["scenarioIds"]) <= set(node["scenarioIds"]))
            found.update(node["requirementIds"])
        self.assertEqual(found, {row["id"] for row in navigation["businessRequirements"]})
        self.assertEqual(set(tree), {"BUYPASS"})
        self.assertEqual(set(tree["BUYPASS"]), {"ATL105/2026-3"})
        self.assertEqual(set(tree["BUYPASS"]["ATL105/2026-3"]), {"CREDIT", "DEBIT", "BENEFIT"})

    def test_program_and_network_dimensions_never_conflated(self):
        navigation = self.report["businessNavigation"]
        self.assertIn("PREPAID", navigation["dimensionValues"]["cardProgram"])
        self.assertNotIn("COMMERCIAL", navigation["dimensionValues"]["cardProgram"])
        self.assertIn("COMMERCIAL", navigation["dimensionValues"]["commercialUsage"])
        self.assertIn("CONTACTLESS", navigation["dimensionValues"]["entryMode"])
        self.assertNotIn("CONTACTLESS", navigation["dimensionValues"]["channel"])
        self.assertNotIn("ECOMMERCE", navigation["dimensionValues"]["entryMode"])
        for br in navigation["businessRequirements"]:
            context = br["context"]
            self.assertEqual(context["commercialUsage"], "UNRESOLVED")
            self.assertEqual(context["entryMode"], "UNRESOLVED")
            if context["groupId"] in {"DEBIT", "EBT"}:
                self.assertIsNone(context["network"])
            if context["groupId"] in {"DISCOVER", "DINERS_CLUB", "JCB"}:
                self.assertNotIn(br["sharedRequirementId"], {"NET-REQ-AMEX-TRACK", "NET-REQ-MASTERCARD-AUTH", "NET-REQ-VISA-AE"})

    def test_invalid_or_duplicate_group_and_profile_definitions_fail(self):
        mutations = [
            lambda nav: nav["groups"].pop(),
            lambda nav: nav["groups"].append(nav["groups"][0]),
            lambda nav: nav["groups"][0].update(kind="GENERIC_CATEGORY"),
            lambda nav: nav["groups"][0].update(id="CREDIT"),
            lambda nav: nav["groups"][0]["profiles"].append("UNKNOWN"),
            lambda nav: nav["profiles"][0].update(groupId="VISA"),
            lambda nav: nav["profiles"][0].update(cardProgram="COMMERCIAL"),
            lambda nav: nav["profiles"][0].update(cardCodes=["999"]),
            lambda nav: nav["profiles"][0].update(taxonomyIds=["UNKNOWN"]),
            lambda nav: nav["profiles"][0].update(additionalTopics=["MASTERCARD-AUTH"]),
            lambda nav: nav["profiles"][0]["flowIds"].append("VISA_ESTIMATED_INCREMENTAL"),
            lambda nav: nav["profiles"][0]["flowIds"].append("EWIC_AUTH_CANCEL"),
            lambda nav: nav["topicNavigation"]["COMMON-FIELDS"].update(category="Credit"),
            lambda nav: nav.update(specification="ATL104"),
        ]
        for mutation in mutations:
            document = copy.deepcopy(self.document)
            mutation(document["businessNavigation"])
            with self.assertRaises(ValueError):
                review.validate(document, review.PACK)

    def test_business_context_rejects_conflation_and_eligibility_promotion(self):
        original = self.report["businessNavigation"]["businessRequirements"][0]["context"]
        for changes in (
            {"eligibility": "SUPPORTED"}, {"network": "DEBIT"}, {"genericCategory": "DEBIT"},
            {"cardProgram": "Visa"}, {"channel": "CONTACTLESS"}, {"entryMode": "ECOMMERCE"},
            {"cardPresence": "NOT_PRESENT", "entryMode": "CONTACTLESS"},
            {"cardPresence": "PRESENT", "channel": "ECOMMERCE"},
            {"entryMode": "CONTACTLESS", "channel": "ECOMMERCE"},
            {"specification": "Gen2"}, {"version": "2026-4"},
        ):
            with self.assertRaises(ValueError):
                review.validate_business_context({**original, **changes})
        debit = next(row["context"] for row in self.report["businessNavigation"]["businessRequirements"]
                     if row["context"]["groupId"] == "DEBIT")
        with self.assertRaises(ValueError):
            review.validate_business_context({**debit, "network": "STAR_SIGNATURE_DEBIT"})


if __name__ == "__main__":
    unittest.main()
