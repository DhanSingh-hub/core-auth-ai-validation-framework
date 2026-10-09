import argparse
import copy
import hashlib
import json
import re
import xml.etree.ElementTree as ET
from pathlib import Path

import jsonschema


OUTPUT_NAME = "section-10-2-debit-fixture-oracle-candidates-v1"
BLOCKERS = [
    "No debit-capable POS/device implementation is present in this repository.",
    "No approved debit BIN, issuer/network routing profile, or host simulator is supplied.",
    "No captured authorized host response is supplied; response vectors are injected candidates only.",
    "No DUKPT test keys, KSN policy, or DES-compliant PIN-entry device evidence is supplied.",
    "No full converter-ready debit request/response wire capture is supplied.",
]

ORACLE_SPECS = [
    {
        "id": "OR-10-2-EXPIRY-ONLINE-AUTH",
        "ruleKey": "DEBIT_EXPIRATION_ONLINE",
        "start": "The device does not perform expiration date validation",
        "end": "expiration date on the card.",
        "fixture": {"cardState": "DEBIT_CARD_EXPIRATION_CLASSIFIED_EXPIRED_BY_TEST_PROFILE", "deviceAction": "PROCESS_AS_DEBIT"},
        "expected": {"localExpirationRejection": False, "onlineAuthorizationRequested": True},
        "status": "SOURCE_BACKED_CANDIDATE_NOT_EXECUTED",
        "blockers": ["Debit BIN/network profile and device authorization trace are unavailable."],
    },
    {
        "id": "OR-10-2-COMPLETION-RETRY-NEXT-DIAL",
        "ruleKey": "COMPLETION_NO_RESPONSE_RETRY",
        "start": "In the event the device sends a Debit Card Completion transaction",
        "end": "whichever occurs first.",
        "fixture": {"responseReceived": False, "minutesSinceSend": 8, "minutesUntilNextDial": 5},
        "expected": {"retryDueAt": "NEXT_DIAL", "retryDueWithinMinutes": 5},
        "status": "SOURCE_BACKED_CANDIDATE_NOT_EXECUTED",
        "blockers": ["Device clock/dial scheduler and retry persistence are unavailable."],
    },
    {
        "id": "OR-10-2-COMPLETION-RETRY-30-MINUTES",
        "ruleKey": "COMPLETION_NO_RESPONSE_RETRY",
        "start": "In the event the device sends a Debit Card Completion transaction",
        "end": "whichever occurs first.",
        "fixture": {"responseReceived": False, "minutesSinceSend": 8, "minutesUntilNextDial": 35},
        "expected": {"retryDueAt": "THIRTY_MINUTES_AFTER_SEND", "retryDueWithinMinutes": 22},
        "status": "SOURCE_BACKED_CANDIDATE_NOT_EXECUTED",
        "blockers": ["Device clock/dial scheduler and retry persistence are unavailable."],
    },
    {
        "id": "OR-10-2-COMPLETION-DECLINE24-ALREADY-CAPTURED",
        "ruleKey": "COMPLETION_DECLINE24_CAPTURED",
        "start": "If the device receives a Response Code value of 1",
        "end": "update the device totals as \"approved.\"",
        "fixture": {"injectedResponse": {"responseCode": "1", "declineCode": "24"}},
        "expected": {"completionDisposition": "ALREADY_CAPTURED", "deviceTotalsDisposition": "APPROVED"},
        "status": "SOURCE_BACKED_CANDIDATE_NOT_EXECUTED",
        "blockers": ["The response is a test vector, not a response captured from BUYPASS or a debit network."],
    },
    {
        "id": "OR-10-2-COMPLETION-ANY-RESPONSE-STOPS-RETRY",
        "ruleKey": "COMPLETION_RESPONSE_STOPS_RETRY",
        "start": "Any response from BUYPASS to a Debit Card Completion transaction",
        "end": "whether \"approved\" or \"declined.\"",
        "fixture": {"responseClasses": ["APPROVED", "DECLINED"]},
        "expected": {"retryAttemptsAfterResponse": 0},
        "status": "SOURCE_BACKED_CANDIDATE_NOT_EXECUTED",
        "blockers": ["Device retry queue and response-processing runtime are unavailable."],
    },
    {
        "id": "OR-10-2-THREE-UNREADABLE-SWIPES-TERMINATE",
        "ruleKey": "DEBIT_UNREADABLE_CARD_RETRIES",
        "start": "Manual entry is not allowed on a debit card transaction.",
        "end": "a message should be displayed to the clerk and the transaction ended.",
        "fixture": {"readResults": ["UNREADABLE", "UNREADABLE", "UNREADABLE"]},
        "expected": {"swipeAttempts": 3, "clerkMessageDisplayed": True, "transactionEnded": True, "manualEntryOffered": False},
        "status": "SOURCE_BACKED_CANDIDATE_NOT_EXECUTED",
        "blockers": ["POS swipe interface and UI observation channel are unavailable."],
    },
    {
        "id": "OR-10-2-PARTIAL-APPROVAL-RESPONSE-F",
        "ruleKey": "DEBIT_PARTIAL_APPROVAL_RESPONSE",
        "start": "Note: This information is returned when a Partial Approval Indicator value of 1 is sent",
        "end": "the Additional Information Data Segment (Segment No. 112).",
        "fixture": {"partialApprovalIndicator": "1", "networkSupportsPartialApproval": "UNVERIFIED", "responseCode": "F", "approvedAmount": "FROM_INJECTED_RESPONSE"},
        "expected": {"posPromptsForRemainingBalance": True, "issuerBalanceSegment112": "PRESENT_ONLY_IF_RETURNED_BY_ISSUER"},
        "status": "CONDITIONAL_SOURCE_BACKED_CANDIDATE_BLOCKED",
        "blockers": ["Debit network capability, issuer response, and POS remaining-balance prompt are unverified."],
    },
    {
        "id": "OR-10-2-PARTIAL-CASHBACK-AMOUNT-SPLIT",
        "ruleKey": "DEBIT_PARTIAL_CASHBACK_ALLOCATION",
        "start": "If any debit transaction requesting authorization for purchase with",
        "end": "the partially approved amount will be for the purchase only and not cashback.",
        "fixture": {"amountUnits": "SYNTHETIC_MINOR_UNITS_FOR_STATE_VECTOR_ONLY", "purchaseAmount": 8000, "cashbackAmount": 2000, "injectedApprovedAmount": 6000},
        "expected": {"approvedPurchaseAmount": 6000, "approvedCashbackAmount": 0},
        "status": "SOURCE_BACKED_CANDIDATE_NOT_EXECUTED",
        "blockers": ["Numbers are abstract state-vector values, not a converter-ready monetary encoding or issuer observation."],
    },
    {
        "id": "OR-10-2-PARTIAL-REVERSAL-REUSES-APPROVED-AMOUNT",
        "ruleKey": "DEBIT_PARTIAL_REVERSAL_AMOUNT",
        "start": "The amount in a Reversal (Void) request must be the amount approved",
        "end": "when a transaction has been partially approved.",
        "fixture": {"amountUnits": "SYNTHETIC_MINOR_UNITS_FOR_STATE_VECTOR_ONLY", "originalApprovedAmount": 6000},
        "expected": {"reversalAmount": 6000},
        "status": "SOURCE_BACKED_CANDIDATE_NOT_EXECUTED",
        "blockers": ["No original debit authorization/response pair or reversal processor is available."],
    },
    {
        "id": "OR-10-2-PARTIAL-TOR-REUSES-AMOUNT-AND-SEQUENCE",
        "ruleKey": "DEBIT_PARTIAL_TOR_AMOUNT_SEQUENCE",
        "start": "The amount in a TOR request must be the amount approved",
        "end": "The Sequence Number must be the Sequence Number contained in the original transaction request.",
        "fixture": {"amountUnits": "SYNTHETIC_MINOR_UNITS_FOR_STATE_VECTOR_ONLY", "originalApprovedAmount": 6000, "originalSequenceNumber": "123456"},
        "expected": {"torAmount": 6000, "torSequenceNumber": "123456"},
        "status": "SOURCE_BACKED_CANDIDATE_NOT_EXECUTED",
        "blockers": ["No original debit authorization/response pair or retry processor is available."],
    },
    {
        "id": "OR-10-2-AUTH-REVERSAL-MATCHING",
        "ruleKey": "DEBIT_AUTH_REVERSAL_CONTEXT",
        "start": "Successful reversal matching requires the following:",
        "end": "to indicate cancellation.",
        "fixture": {"originalApprovalNumber": "SYNTHETIC-APPROVAL", "reversalApprovalNumber": "SYNTHETIC-APPROVAL", "originalSequenceNumber": "123456", "reversalSequenceNumber": "123456", "reversalPromptCode": "S"},
        "expected": {"element5Matches": True, "element86Matches": True, "element78PromptCode": "S"},
        "status": "SOURCE_BACKED_CANDIDATE_NOT_EXECUTED",
        "blockers": ["Approval-number format, request serialization, and acquirer/issuer behavior are not verified."],
    },
]


def norm(value):
    return re.sub(r"\s+", " ", value).strip()


def source_window(lines, start_marker, end_marker, first_line):
    start = next((i for i in range(first_line, len(lines)) if start_marker in lines[i]), None)
    if start is None:
        raise ValueError("Source start marker not found: " + start_marker)
    for end in range(start, min(start + 30, len(lines))):
        excerpt = "\n".join(lines[start:end + 1])
        if norm(end_marker) in norm(excerpt):
            return start + 1, end + 1, excerpt
    raise ValueError("Source end marker not found after: " + start_marker)


def unresolved_rule_reason(rule_key):
    if rule_key.startswith("DEBIT_PIN"):
        return "No DES-compliant PIN-entry device, approved DUKPT test keys, KSN policy, or PIN-block lifecycle capture."
    if rule_key.startswith("DEBIT_HEALTHCARE"):
        return "No verified eligible-benefit BIN, issuer/network capability profile, QHP product fixture, or companion-segment host response."
    if rule_key.startswith("DEBIT_RECEIPT"):
        return "No printer/output capture; inherited Section 10.1 receipt chains remain DRAFT_REVIEW_REQUIRED."
    if rule_key.startswith(("DEBIT_TRACK", "DEBIT_HOST", "DEBIT_INFLIGHT")):
        return "No compliant host wire capture or controlled storage/clearance observation; full track data is intentionally not copied into this evidence pack."
    if rule_key in {"DEBIT_POS_TRANSACTION_TYPES", "DEBIT_CAT_TRANSACTION_TYPES"}:
        return "No complete per-transaction request/response fixture matrix or validated debit transaction-code mapping."
    if rule_key.startswith("DEBIT_PARTIAL") or rule_key == "INTERLINK_CASHBACK_MANDATE":
        return "No verified debit-network support, merchant mandate profile, issuer response, or approved-amount host oracle."
    return "No debit-capable device/runtime fixture and independently bound request/response outcome evidence."


def blocked_rule_records(v2_package, chains, candidate_rule_keys):
    records = []
    for br in v2_package["businessRequirements"]:
        anchor = br["sourceAnchors"][0]
        rule_key = anchor["rule"].removeprefix("section-10-2-").upper().replace("-", "_")
        if rule_key in candidate_rule_keys:
            continue
        chain = chains[rule_key]
        records.append({
            "ruleKey": rule_key,
            "businessRequirementId": br["id"],
            "scenarioId": chain["scenarioId"], "testCaseId": chain["testCaseId"], "testDataId": chain["testDataId"],
            "disposition": "NO_CANDIDATE_VECTOR_MATERIALIZED",
            "status": "REVIEW_REQUIRED",
            "blocker": unresolved_rule_reason(rule_key),
            "executionAllowed": False, "coverageCredit": 0
        })
    return records


def write_json(path, value):
    path.write_text(json.dumps(value, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")


def build(pack_root, output):
    pack_root = Path(pack_root).resolve()
    output = Path(output).resolve()
    if output.exists():
        raise ValueError("Refusing to overwrite candidate evidence output")
    v2_root = pack_root / "test-output/test-solution-independent-review/section-10-2-debit-card-processing-review-v2"
    v2_package = json.loads((v2_root / "section-10-2-br-ts-tc-td-draft-package.json").read_text(encoding="utf-8-sig"))
    v2_assessment = json.loads((v2_root / "section-10-2-coverage-assessment.json").read_text(encoding="utf-8-sig"))
    source_path = pack_root / "docs/specs/extracted_text.txt"
    source_bytes = source_path.read_bytes()
    source = source_bytes.decode("utf-8-sig")
    source_lines = source.splitlines()
    section_start = max(i for i, line in enumerate(source_lines)
                        if line.strip().startswith("10.2 Debit Card Processing Requirements"))
    source_sha = hashlib.sha256(source_bytes).hexdigest()
    chains = {row["ruleKey"]: row for rows in v2_assessment["topicChains"].values() for row in rows}
    br_by_id = {row["id"]: row for row in v2_package["businessRequirements"]}
    scenario_by_id = {row["id"]: row for row in v2_package["testScenarios"]}
    case_by_id = {row["id"]: row for row in v2_package["testCases"]}
    td_by_id = {row["id"]: row for row in v2_package["testData"]}

    pa_chain = chains["DEBIT_PARTIAL_APPROVAL_RESPONSE"]
    pa_br = copy.deepcopy(br_by_id[pa_chain["businessRequirementId"]])
    pa_scenario = copy.deepcopy(scenario_by_id[pa_chain["scenarioId"]])
    pa_case = copy.deepcopy(case_by_id[pa_chain["testCaseId"]])
    pa_data = copy.deepcopy(td_by_id[pa_chain["testDataId"]])
    pa_br = {key: pa_br[key] for key in ("id", "title", "category", "applicability", "priority", "executionStatus", "sourceAnchors")}
    pa_scenario = {key: pa_scenario[key] for key in ("id", "requirementIds", "sourceAnchors", "status")}
    pa_case = {key: pa_case[key] for key in ("id", "scenarioIds", "sourceAnchors", "expectedOutcome", "category", "tags", "priority", "status", "testDataFile")}
    pa_data = {key: pa_data[key] for key in ("id", "testCaseIds", "sourceAnchors", "expectedValidation", "fileName", "readiness", "payload")}
    fixture_filename = "test-data/" + pa_data["id"] + ".json"
    pa_case["testDataFile"] = fixture_filename
    pa_case["expectedOutcome"] = "REVIEW"
    pa_data["fileName"] = fixture_filename
    pa_data["expectedValidation"] = "PASS_WITH_REVIEW"
    pa_data["readiness"] = "REVIEW_REQUIRED"
    pa_data["payload"] = {
        "request": {
            "dataSection1": {"messageFormatVersionIdentifier": "ATL105", "numberOfSegments": "01"},
            "dataSection2": {"standardSegment": {
                "segmentType": "100", "terminalIdentifier": "TSCA000001", "promptCode": "0020",
                "sequenceNumber": "100001", "partialApprovalIndicator": "1"
            }},
            "dataSection3": []
        },
        "testControls": {"validateCoreFields": True, "validatePartialApproval": True,
                         "cardPresent": True, "expectedIndicator": "1"},
        "candidateScope": "SEGMENT_100_CORE_AND_INDICATOR_ONLY_NOT_A_COMPLETE_DEBIT_REQUEST",
        "executionAllowed": False
    }
    candidate_package = {
        "manifest": {
            "packageId": "ATL105-SECTION-10-2-DEBIT-FIXTURE-CANDIDATES-001",
            "specification": "ATL105", "specificationVersion": "2026-3",
            "artifactContractVersion": "2", "strictExecutionContract": False
        },
        "businessRequirements": [pa_br], "testScenarios": [pa_scenario],
        "testCases": [pa_case], "testData": [pa_data]
    }

    oracle_rows = []
    for spec in ORACLE_SPECS:
        chain = chains[spec["ruleKey"]]
        start_line, end_line, quote = source_window(source_lines, spec["start"], spec["end"], section_start)
        if norm(quote) not in norm(source):
            raise ValueError("Oracle quote is not present in the pinned source: " + spec["id"])
        oracle_rows.append({
            "oracleId": spec["id"], "sourceRuleKey": spec["ruleKey"],
            "linkedDraftChain": {"businessRequirementId": chain["businessRequirementId"],
                                 "scenarioId": chain["scenarioId"], "testCaseId": chain["testCaseId"],
                                 "testDataId": chain["testDataId"], "status": "REVIEW_REQUIRED"},
            "fixtureKind": "DEVICE_STATE_VECTOR_NOT_ATL105_WIRE_PAYLOAD",
            "inputVector": spec["fixture"], "expectedDeviceOutcome": spec["expected"],
            "oracleKind": "SOURCE_DEFINED_DETERMINISTIC",
            "ruleCoverageStatus": "CANDIDATE_VECTOR_ONLY_NOT_RULE_COVERAGE",
            "candidateStatus": spec["status"], "executionStatus": "NOT_EXECUTED",
            "sourceEvidence": {"file": "docs/specs/extracted_text.txt", "sha256": source_sha,
                               "startLine": start_line, "endLine": end_line, "quote": quote},
            "blockers": spec["blockers"]
        })
    candidate_rule_keys = {row["sourceRuleKey"] for row in oracle_rows}
    blocked_rules = blocked_rule_records(v2_package, chains, candidate_rule_keys)
    if len(candidate_rule_keys) + len(blocked_rules) != len(chains):
        raise ValueError("Section 10.2 candidate/blocked dispositions do not account for every draft chain")

    output.mkdir(parents=True)
    td_dir = output / "test-data"
    td_dir.mkdir()
    write_json(output / "candidate-test-solution-package.json", candidate_package)
    write_json(td_dir / Path(pa_data["fileName"]).name, {
        "testCaseId": pa_case["id"], "testDataId": pa_data["id"],
        "status": "CANDIDATE_FIXTURE_REVIEW_REQUIRED", "readiness": "REVIEW_REQUIRED",
        "payload": pa_data["payload"], "sourceAnchors": pa_data["sourceAnchors"]
    })
    write_json(output / "source-backed-outcome-oracle-candidates.json", {
        "artifact": "ATL105-SECTION-10-2-SOURCE-BACKED-OUTCOME-ORACLE-CANDIDATES",
        "specification": "ATL105", "specificationVersion": "2026-3",
        "status": "DRAFT_REVIEW_REQUIRED", "sourceSha256": source_sha,
        "oracleCount": len(oracle_rows), "oracles": oracle_rows,
        "candidateVectorRuleKeyCount": len(candidate_rule_keys),
        "draftRuleCount": len(chains), "unmaterializedRuleCount": len(blocked_rules),
        "blockedRules": blocked_rules,
        "executionCertified": False, "coverageCredit": 0
    })
    schema_path = pack_root / "schemas/atl105-segment100-artifact-package.schema.json"
    jsonschema.validate(candidate_package, json.loads(schema_path.read_text(encoding="utf-8")))
    for data in candidate_package["testData"]:
        physical = json.loads((output / data["fileName"]).read_text(encoding="utf-8"))
        if physical["payload"] != data["payload"]:
            raise ValueError("Physical test-data file differs from package payload: " + data["id"])
    for oracle in oracle_rows:
        evidence = oracle["sourceEvidence"]
        physical_quote = "\n".join(source_lines[evidence["startLine"] - 1:evidence["endLine"]])
        if evidence["quote"] != physical_quote or evidence["sha256"] != source_sha:
            raise ValueError("Source evidence failed exact line/hash verification: " + oracle["oracleId"])
        if oracle["linkedDraftChain"]["businessRequirementId"] not in br_by_id:
            raise ValueError("Oracle references unknown debit BR: " + oracle["oracleId"])

    evidence_report = {
        "artifact": "ATL105-SECTION-10-2-DEBIT-FIXTURE-TECHNICAL-EVIDENCE",
        "status": "PARTIAL_TECHNICAL_EVIDENCE_REVIEW_REQUIRED",
        "candidatePackageSchema": {"status": "PASS", "schema": str(schema_path.relative_to(pack_root))},
        "physicalFixtureMatchesPackage": "PASS",
        "sourceQuoteChecks": {"status": "PASS", "passed": len(oracle_rows), "failed": 0,
                              "sourceSha256": source_sha},
        "linkedDraftChainChecks": {"status": "PASS", "checked": len(oracle_rows)},
        "candidateVectorDisposition": {"draftRuleCount": len(chains), "candidateVectorRuleKeyCount": len(candidate_rule_keys),
                        "unmaterializedRuleCount": len(blocked_rules), "coverageCredit": 0},
        "javaFieldValidatorTests": {"status": "PENDING"},
        "fullDebitRequestValidation": "NOT_ASSESSED",
        "deviceOutcomeExecution": "NOT_EXECUTED",
        "hostOutcomeObservation": "NOT_ASSESSED",
        "unresolvedBlockers": BLOCKERS,
        "semanticApproval": "NOT_GRANTED", "coverageCredit": 0, "executionCertified": False
    }
    write_json(output / "technical-evidence.json", evidence_report)
    (output / "README.md").write_text("\n".join([
        "# ATL105 Section 10.2 Debit Fixture and Oracle Candidates", "",
        "Status: DRAFT_REVIEW_REQUIRED. These are independently source-quoted candidates; they do not promote the v2 BR chains or baseline.", "",
        "## Fixture scope", "",
        "`candidate-test-solution-package.json` contains one canonical Segment 100 request fragment for Partial Approval Indicator 1. Existing Segment 100 core-field and indicator validators can assess that fragment. It is deliberately marked REVIEW_REQUIRED and `executionAllowed: false`; it is not a full request, has no debit BIN/network evidence, PIN block, complete Segment 1 fields, complete Segment 100 field set, or captured wire.", "",
        f"`source-backed-outcome-oracle-candidates.json` contains {len(oracle_rows)} source-quoted state vectors across {len(candidate_rule_keys)} of {len(chains)} Section 10.2 draft rules. The other {len(blocked_rules)} rules are listed with explicit missing-evidence blockers. Candidate vectors confer no coverage credit. Quotes are bound to the extracted ATL105 source SHA-256 and exact line ranges.", "",
        "These state vectors are not converter-ready wire fixtures, actual host observations, or device executions. Numeric values are illustrative state-vector units only where explicitly labeled.", "",
        "## Technical evidence", "",
        "The build checks the package schema, physical-fixture/package equality, exact source quote ranges and links to the v2 draft chains. The focused Java test checks only Segment 100 core-field shape, Partial Approval Indicator 1, traceability, and that execution validation remains blocked.",
        "Java validator result: PENDING", "",
        "## Blocked", "",
        *["- " + blocker for blocker in BLOCKERS],
        "- The code-only source oracle cannot establish debit-network support, issuer response authority, or host state.",
        "- Formal SME/TBA interpretation and execution certification remain blocked; technical evidence never sets `executionCertified` true.", "",
        "All candidate BR/TS/TC/TD records remain REVIEW_REQUIRED. No baseline rows, coverage credit, or approvals are changed.", ""
    ]), encoding="utf-8")
    print(json.dumps({"output": str(output), "oracleCandidates": len(oracle_rows),
                      "exactSourceQuotesVerified": len(oracle_rows), "candidatePackageSchema": "PASS",
                      "fullDebitRequestValidation": "NOT_ASSESSED", "executionCertified": False}, indent=2))


def record_validation(pack_root, output):
    pack_root = Path(pack_root).resolve()
    output = Path(output).resolve()
    source_path = pack_root / "docs/specs/extracted_text.txt"
    source_bytes = source_path.read_bytes()
    source_sha = hashlib.sha256(source_bytes).hexdigest()
    source_lines = source_bytes.decode("utf-8-sig").splitlines()
    schema_path = pack_root / "schemas/atl105-segment100-artifact-package.schema.json"
    package_path = output / "candidate-test-solution-package.json"
    package = json.loads(package_path.read_text(encoding="utf-8"))
    jsonschema.validate(package, json.loads(schema_path.read_text(encoding="utf-8")))
    for data in package["testData"]:
        physical = json.loads((output / data["fileName"]).read_text(encoding="utf-8"))
        if physical["testDataId"] != data["id"] or physical["payload"] != data["payload"]:
            raise ValueError("Physical fixture ID/payload does not match canonical package: " + data["id"])

    oracle_path = output / "source-backed-outcome-oracle-candidates.json"
    oracle_doc = json.loads(oracle_path.read_text(encoding="utf-8"))
    v2_root = pack_root / "test-output/test-solution-independent-review/section-10-2-debit-card-processing-review-v2"
    v2_assessment_path = v2_root / "section-10-2-coverage-assessment.json"
    v2_assessment = json.loads(v2_assessment_path.read_text(encoding="utf-8-sig"))
    v2_package = json.loads((v2_root / "section-10-2-br-ts-tc-td-draft-package.json").read_text(encoding="utf-8-sig"))
    chains = {row["ruleKey"]: row for rows in v2_assessment["topicChains"].values() for row in rows}
    chain_ids = {row["businessRequirementId"] for row in chains.values()}
    candidate_rule_keys = {oracle["sourceRuleKey"] for oracle in oracle_doc["oracles"]}
    blocked_rules = blocked_rule_records(v2_package, chains, candidate_rule_keys)
    if len(candidate_rule_keys) + len(blocked_rules) != len(chains):
        raise ValueError("Candidate and blocked dispositions do not account for every Section 10.2 draft rule")
    for oracle in oracle_doc["oracles"]:
        evidence = oracle["sourceEvidence"]
        excerpt = "\n".join(source_lines[evidence["startLine"] - 1:evidence["endLine"]])
        if evidence["sha256"] != source_sha or evidence["quote"] != excerpt:
            raise ValueError("Oracle source citation no longer verifies: " + oracle["oracleId"])
        chain = chains[oracle["sourceRuleKey"]]
        link = oracle["linkedDraftChain"]
        if link["businessRequirementId"] not in chain_ids:
            raise ValueError("Oracle references an unknown v2 chain: " + oracle["oracleId"])
        if any(link[key] != chain[value] for key, value in (("businessRequirementId", "businessRequirementId"),
                ("scenarioId", "scenarioId"), ("testCaseId", "testCaseId"), ("testDataId", "testDataId"))):
            raise ValueError("Oracle chain IDs do not match the v2 draft chain: " + oracle["oracleId"])
        oracle["ruleCoverageStatus"] = "CANDIDATE_VECTOR_ONLY_NOT_RULE_COVERAGE"
    oracle_doc["candidateVectorRuleKeyCount"] = len(candidate_rule_keys)
    oracle_doc["draftRuleCount"] = len(chains)
    oracle_doc["unmaterializedRuleCount"] = len(blocked_rules)
    oracle_doc["blockedRules"] = blocked_rules
    write_json(oracle_path, oracle_doc)

    module_root = pack_root.parents[1]
    report_path = module_root / "target/surefire-reports/TEST-com.coreauth.validator.Section102DebitFixtureEvidenceTest.xml"
    suite = ET.parse(report_path).getroot()
    test_count = int(suite.attrib.get("tests", "0"))
    failures = int(suite.attrib.get("failures", "0"))
    errors = int(suite.attrib.get("errors", "0"))
    if test_count != 2 or failures or errors:
        raise ValueError(f"Focused Java tests did not pass: tests={test_count}, failures={failures}, errors={errors}")

    evidence_path = output / "technical-evidence.json"
    evidence_report = json.loads(evidence_path.read_text(encoding="utf-8"))
    evidence_report["candidatePackageSchema"] = {"status": "PASS", "schema": str(schema_path.relative_to(pack_root))}
    evidence_report["physicalFixtureMatchesPackage"] = "PASS"
    evidence_report["sourceQuoteChecks"] = {"status": "PASS", "passed": len(oracle_doc["oracles"]), "failed": 0,
                                             "sourceSha256": source_sha}
    evidence_report["linkedDraftChainChecks"] = {"status": "PASS", "checked": len(oracle_doc["oracles"])}
    evidence_report["candidateVectorDisposition"] = {"draftRuleCount": len(chains),
        "candidateVectorRuleKeyCount": len(candidate_rule_keys), "unmaterializedRuleCount": len(blocked_rules),
        "coverageCredit": 0}
    evidence_report["javaFieldValidatorTests"] = {
        "status": "PASS", "testCount": test_count, "failures": failures, "errors": errors,
        "report": str(report_path.relative_to(module_root)),
        "scope": ["canonical traceability", "Segment 100 core-field shape", "Partial Approval Indicator 1", "invalid indicator mutation rejected", "execution validation remains blocked"]
    }
    evidence_report["fullDebitRequestValidation"] = "NOT_ASSESSED"
    evidence_report["deviceOutcomeExecution"] = "NOT_EXECUTED"
    evidence_report["hostOutcomeObservation"] = "NOT_ASSESSED"
    evidence_report["executionCertified"] = False
    write_json(evidence_path, evidence_report)
    readme_path = output / "README.md"
    readme = readme_path.read_text(encoding="utf-8")
    readme = readme.replace("Java validator result: PENDING",
                            f"Java validator result: PASS ({test_count} tests, {failures} failures, {errors} errors).")
    readme_path.write_text(readme, encoding="utf-8")
    print(json.dumps({"sourceQuotesVerified": len(oracle_doc["oracles"]), "packageSchema": "PASS",
                      "javaTests": test_count, "failures": failures, "errors": errors,
                      "fullDebitRequestValidation": "NOT_ASSESSED", "executionCertified": False}, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--pack-root", type=Path, required=True)
    parser.add_argument("--output-directory", type=Path, required=True)
    parser.add_argument("--record-validation", action="store_true")
    args = parser.parse_args()
    if args.record_validation:
        record_validation(args.pack_root, args.output_directory)
    else:
        build(args.pack_root, args.output_directory)


if __name__ == "__main__":
    main()