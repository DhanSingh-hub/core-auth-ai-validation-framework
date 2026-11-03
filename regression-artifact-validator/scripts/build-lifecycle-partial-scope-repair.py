"""Build source-bound, review-only case proposals for six Lifecycle BR gaps."""

import argparse
import collections
import csv
import hashlib
import json
import os
from pathlib import Path


SCRIPT = Path(__file__).resolve()
ROOT = SCRIPT.parents[1]
SPEC = ROOT / "specifications/ATL105/docs/specs/extracted_text.txt"
PACKAGE = ROOT / "specifications/ATL105/test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json"
OUTPUT = ROOT / "specifications/ATL105/test-output/test-solution-independent-review/lifecycle-partial-scope-repair-v1"


EVIDENCE_RANGES = {
    "AUTH_ONLY_REVERSAL_REFERENCES": (3938, 3965),
    "PURCHASE_AND_TIMEOUT_REVERSAL_REFERENCES": (4144, 4168),
    "FLEET_SEGMENT_FIELD_ORDER_AND_COMPLETION_TAGS": (11396, 11555),
    "EBT_FOOD_VOUCHERS_AND_SUPPORTED_TYPES": (5145, 5187),
    "EWIC_RETURN_AND_INITIAL_FLOWS": (5287, 5320),
    "EWIC_COMPLETION_AND_VOUCHER_CLEAR": (5320, 5410),
    "EBT_SEGMENT_103_FIELDS": (11735, 11805),
    "EMV_FINANCIAL_REQUEST_LAYOUT": (10015, 10095),
    "EMV_REVERSAL_EXCEPTION": (7335, 7355),
    "EMV_SEGMENT_130_LAYOUT": (14012, 14115),
    "APPENDIX_T_EMV_TABLE_001": (35562, 35598),
    "EMV_SEGMENT_131_LAYOUT": (14145, 14225),
}


def native(path):
    value = str(Path(path).resolve())
    if os.name == "nt" and not value.startswith("\\\\?\\"):
        value = "\\\\?\\" + value
    return Path(value)


def sha256(path):
    digest = hashlib.sha256()
    with native(path).open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_json(path):
    return json.loads(native(path).read_text(encoding="utf-8-sig"))


def write_json(path, value):
    native(path).write_text(json.dumps(value, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")


def write_jsonl(path, rows):
    with native(path).open("w", encoding="utf-8", newline="\n") as stream:
        for row in rows:
            stream.write(json.dumps(row, sort_keys=True, ensure_ascii=True) + "\n")


def write_csv(path, rows):
    rows = list(rows)
    if not rows:
        raise ValueError("Cannot write an empty CSV")
    fields = list(dict.fromkeys(key for row in rows for key in row))
    with native(path).open("w", encoding="utf-8", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, sort_keys=True, ensure_ascii=True) if isinstance(value, (dict, list)) else value
                             for key, value in row.items()})


def draft_case(case_id, rule_id, intent, source_ids, input_shape, assertions, *, unresolved=None):
    return {
        "caseId": case_id,
        "testBrId": "BR-RULE-" + rule_id,
        "intent": intent,
        "sourceEvidenceIds": source_ids,
        "inputShape": input_shape,
        "assertions": assertions,
        "expectedOutcome": "REVIEW_REQUIRED",
        "readiness": "REVIEW_REQUIRED" if unresolved else "REVIEW_REQUIRED_SOURCE_DERIVED_DRAFT",
        "unresolvedQuestion": unresolved,
        "executionStatus": "NOT_EXECUTED",
        "independence": "DERIVED_FROM_ATL105_ONLY",
    }


def repairs():
    return {
        "SEG100-R-023": {
            "proposedSlices": [
                "Authorization Only Reversal (Prompt Code transaction type S): preserve original Approval Number and Sequence Number when present and identical.",
                "Purchase Reversal / Time-out Reversal: preserve original Sequence Number; for partial approval, use the source-defined approved/requested amount in the corresponding reversal path.",
            ],
            "cases": [
                draft_case("LIFE-023-AUTH-REV-MATCH", "SEG100-R-023", "Authorization Only Reversal matches original references", ["AUTH_ONLY_REVERSAL_REFERENCES"], {"original": {"transactionType": "3", "approvalNumber": "SYNTHETIC-APPROVAL", "sequenceNumber": "SYN001"}, "reversal": {"transactionType": "S", "approvalNumber": "SYNTHETIC-APPROVAL", "sequenceNumber": "SYN001"}}, ["reversal Prompt Code transaction type is S", "Approval Number matches original when present", "Sequence Number matches original when present"]),
                draft_case("LIFE-023-AUTH-REV-SEQ-MISMATCH", "SEG100-R-023", "Reject Authorization Only Reversal with mismatched Sequence Number", ["AUTH_ONLY_REVERSAL_REFERENCES"], {"originalSequenceNumber": "SYN001", "reversalSequenceNumber": "SYN002"}, ["reject or source-validator failure cites Sequence Number mismatch"]),
                draft_case("LIFE-023-AUTH-REV-APPROVAL-MISSING", "SEG100-R-023", "Require original Approval Number on matched Authorization Only Reversal", ["AUTH_ONLY_REVERSAL_REFERENCES"], {"originalApprovalNumberPresent": True, "reversalApprovalNumberPresent": False}, ["reject or source-validator failure cites required identical Approval Number"]),
                draft_case("LIFE-023-PURCHASE-TOR-SEQ", "SEG100-R-023", "Preserve original Sequence Number on Purchase Reversal and TOR", ["PURCHASE_AND_TIMEOUT_REVERSAL_REFERENCES"], {"originalSequenceNumber": "SYN001", "purchaseReversalSequenceNumber": "SYN001", "timeoutReversalSequenceNumber": "SYN001"}, ["each applicable reversal Sequence Number equals the original request"]),
                draft_case("LIFE-023-PARTIAL-AMOUNT", "SEG100-R-023", "Use source-defined original amount on partial-approval reversal paths", ["PURCHASE_AND_TIMEOUT_REVERSAL_REFERENCES"], {"originalRequestedAmount": "SYNTHETIC-REQUEST-AMOUNT", "originalApprovedAmount": "SYNTHETIC-APPROVED-AMOUNT", "reversalAmount": "SYNTHETIC-APPROVED-AMOUNT"}, ["reversal amount matches the source-specified approved/requested amount for the selected reversal class"]),
            ],
            "openScope": "The broad Test title must be split/scoped by transaction class; do not apply Authorization Only cancellation rules to every lifecycle message.",
        },
        "SEG101-R-007": {
            "proposedSlices": ["Base Fleet Data fields 1-13 retain Section 12.2 order.", "Fleet Tag fields 14-18 follow the base fields in Auth Completion when applicable."],
            "cases": [
                draft_case("LIFE-101-ORDER-POS", "SEG101-R-007", "Accept Section 12.2 base fields followed by Fleet Tag fields", ["FLEET_SEGMENT_FIELD_ORDER_AND_COMPLETION_TAGS"], {"messageType": "AUTH_COMPLETION_0220", "fieldNumbers": list(range(1, 19))}, ["base fields occupy positions 1-13 in source order", "Fleet Tag fields occupy positions 14-18"]),
                draft_case("LIFE-101-ORDER-NEG", "SEG101-R-007", "Reject reordered or interleaved Fleet Tag fields", ["FLEET_SEGMENT_FIELD_ORDER_AND_COMPLETION_TAGS"], {"messageType": "AUTH_COMPLETION_0220", "fieldNumbers": [1, 2, 14, *range(3, 14), *range(15, 19)]}, ["field order violation is detected against the Section 12.2 order"]),
                draft_case("LIFE-101-TAGS-CONTEXT", "SEG101-R-007", "Scope fields 14-18 to Auth Completion/Host Prompts", ["FLEET_SEGMENT_FIELD_ORDER_AND_COMPLETION_TAGS"], {"messageType": "AUTH_REQUEST", "hostPrompts": True, "fleetTagFieldPresent": True}, ["context-gate is enforced; expected rejection requires Test Team confirmation of validator behavior"] , unresolved="Section 12.2 says Fleet Tags are sent only on Auth Completion; exact rejection-vs-ignore behavior is not specified."),
            ],
            "openScope": None,
        },
        "SEG103-R-024": {
            "proposedSlices": [
                "Food Stamp Electronic Voucher includes the manually entered Voucher ID in the EBT Data context.",
                "eWIC Purchase Completion and Voucher Clear use Segment 103 WIC data where those operations require WIC product/discount data.",
                "eWIC Return is unsupported.",
                "Presence/absence classification for every other EBT/eWIC transaction type remains unresolved unless a message-layout clause makes it explicit.",
            ],
            "cases": [
                draft_case("LIFE-103-VOUCHER-ID", "SEG103-R-024", "Require Voucher ID for Food Stamp Electronic Voucher case", ["EBT_FOOD_VOUCHERS_AND_SUPPORTED_TYPES", "EBT_SEGMENT_103_FIELDS"], {"transactionType": "FOOD_STAMP_ELECTRONIC_VOUCHER", "segment103": {"voucherId": "SYNTHETIC-VOUCHER-ID"}}, ["Voucher ID field is represented in the EBT Data Segment for the voucher flow"]),
                draft_case("LIFE-103-EWIC-COMPLETION", "SEG103-R-024", "Exercise Segment 103 WIC fields on eWIC Purchase Completion", ["EWIC_COMPLETION_AND_VOUCHER_CLEAR", "EBT_SEGMENT_103_FIELDS"], {"transactionType": "EWIC_PURCHASE_COMPLETION", "segment103": {"wicProductData": "SYNTHETIC-WIC-DATA"}}, ["WIC-specific data remains scoped to eWIC Purchase Completion"]),
                draft_case("LIFE-103-EWIC-VOUCHER-CLEAR", "SEG103-R-024", "Exercise Segment 103 WIC fields on eWIC Voucher Clear", ["EWIC_COMPLETION_AND_VOUCHER_CLEAR", "EBT_SEGMENT_103_FIELDS"], {"transactionType": "EWIC_VOUCHER_CLEAR", "segment103": {"voucherId": "SYNTHETIC-VOUCHER-ID", "wicProductData": "SYNTHETIC-WIC-DATA"}}, ["voucher-clear WIC data is scoped to the source-defined operation"]),
                draft_case("LIFE-103-EWIC-RETURN", "SEG103-R-024", "Block unsupported eWIC Return", ["EWIC_RETURN_AND_INITIAL_FLOWS"], {"program": "EWIC", "transactionType": "RETURN", "segment103Present": True}, ["reject/block unsupported eWIC Return before host request"]),
                draft_case("LIFE-103-OTHER-TYPES", "SEG103-R-024", "Keep remaining EBT/eWIC Segment 103 presence matrix open", ["EBT_FOOD_VOUCHERS_AND_SUPPORTED_TYPES", "EWIC_RETURN_AND_INITIAL_FLOWS"], {"transactionTypes": ["FOOD_STAMP_PURCHASE", "FOOD_STAMP_REVERSAL", "FOOD_STAMP_RETURN", "CASH_BENEFIT_PURCHASE", "CASH_BENEFIT_REVERSAL", "BALANCE_INQUIRY", "CASH_BACK", "TIMEOUT_REVERSAL"]}, ["derive required/optional/prohibited independently per message type; no inferred blanket optionality"], unresolved="Sections 10.5.3/10.5.5 enumerate supported operations but do not by themselves settle Segment 103 presence for every operation."),
            ],
            "openScope": "Do not promote the proposed Optional classification for all other EBT/eWIC types without a message-layout source or SME decision.",
        },
        "SEG130-R-001": {
            "proposedSlices": ["Segment 130 is required in EMV Financial Transaction Requests under Section 11.8.1.", "Section 10.14.2.4 explicitly allows EMV data to be absent on Reversal transactions."],
            "cases": [
                draft_case("LIFE-130-REQ-POS", "SEG130-R-001", "Accept EMV authorization-class request containing Segment 130", ["EMV_FINANCIAL_REQUEST_LAYOUT", "EMV_SEGMENT_130_LAYOUT"], {"messageFamily": "EMV_FINANCIAL_TRANSACTION_REQUEST", "segment100Present": True, "segment130Present": True}, ["Segment 130 present in the EMV Financial Transaction Request"]),
                draft_case("LIFE-130-REQ-NEG", "SEG130-R-001", "Reject EMV authorization-class request missing Segment 130", ["EMV_FINANCIAL_REQUEST_LAYOUT", "EMV_SEGMENT_130_LAYOUT"], {"messageFamily": "EMV_FINANCIAL_TRANSACTION_REQUEST", "segment100Present": True, "segment130Present": False}, ["required Segment 130 absence is detected"]),
                draft_case("LIFE-130-REVERSAL-EXCEPTION", "SEG130-R-001", "Allow source-defined EMV reversal without Segment 130", ["EMV_REVERSAL_EXCEPTION"], {"messageFamily": "FINANCIAL_TRANSACTION_REQUEST", "transactionType": "EMV_PURCHASE_REVERSAL", "segment130Present": False}, ["do not flag missing Segment 130 as invalid for source-defined reversal context"]),
            ],
            "openScope": "The authorization-class qualifier and product/network exceptions require context binding; do not generalize this rule to every EMV-related message.",
        },
        "SEG130-R-020": {
            "proposedSlices": ["Appendix T Table 001 uses six-character EMVYES or EMVNOT only on an EMV Transaction request.", "If the table is returned in an authorization response, echo it on subsequent advice or batch upload."],
            "cases": [
                draft_case("LIFE-130-T001-YES", "SEG130-R-020", "Accept EMVYES request value at six characters", ["APPENDIX_T_EMV_TABLE_001"], {"tableId": "001", "tableLength": "006", "value": "EMVYES", "messageDirection": "REQUEST", "emvTransaction": True}, ["fixed Table ID 001", "six-character value EMVYES", "EMV request context"]),
                draft_case("LIFE-130-T001-NOT", "SEG130-R-020", "Accept EMVNOT request value at six characters", ["APPENDIX_T_EMV_TABLE_001"], {"tableId": "001", "tableLength": "006", "value": "EMVNOT", "messageDirection": "REQUEST", "emvTransaction": True}, ["fixed Table ID 001", "six-character value EMVNOT", "EMV request context"]),
                draft_case("LIFE-130-T001-NEG", "SEG130-R-020", "Reject wrong Table ID, length, value or non-EMV request context", ["APPENDIX_T_EMV_TABLE_001"], {"invalidVariants": [{"tableId": "002"}, {"length": "005"}, {"value": "EMVXXX"}, {"messageDirection": "REQUEST", "emvTransaction": False}]}, ["each source-defined constraint is checked independently"]),
                draft_case("LIFE-130-T001-ECHO", "SEG130-R-020", "Echo response Table 001 data on subsequent advice/batch upload", ["APPENDIX_T_EMV_TABLE_001"], {"authorizationResponseIncludesTable001": True, "followUpType": ["ADVICE", "BATCH_UPLOAD"], "sameValueRequired": True}, ["follow-up value equals value included in authorization response"]),
            ],
            "openScope": "Link response/follow-up cases to an authorized response oracle; this proposal does not execute a host flow.",
        },
        "SEG130-R-023": {
            "proposedSlices": ["Segment 131 is positional/no-separator and host-originated.", "Maximum segment length is 3,834; EMV Additional Information cap is 2,800 bytes.", "Checksum is required; the Section 12.21 field list has no EMV Card Sequence Number."],
            "cases": [
                draft_case("LIFE-131-NO-SEPARATORS", "SEG130-R-023", "Parse Segment 131 without Field Separators", ["EMV_SEGMENT_131_LAYOUT"], {"segmentType": "131", "serialization": "POSITIONAL", "fieldSeparatorsPresent": False}, ["all Segment 131 fields are positional; no Field Separators"]),
                draft_case("LIFE-131-SEGMENT-LENGTH", "SEG130-R-023", "Enforce Segment 131 maximum length 3834", ["EMV_SEGMENT_131_LAYOUT"], {"lengthBoundary": [3834, 3835]}, ["3834 accepted at length gate; 3835 rejected at length gate"]),
                draft_case("LIFE-131-ADDITIONAL-INFO", "SEG130-R-023", "Enforce Segment 131 Additional Information cap 2800 bytes", ["EMV_SEGMENT_131_LAYOUT"], {"additionalInformationBytesBoundary": [2800, 2801]}, ["2800 accepted at cap gate; 2801 rejected at cap gate"]),
                draft_case("LIFE-131-CHECKSUM-REQUIRED", "SEG130-R-023", "Require CA Public Key File Checksum and omit card-sequence field", ["EMV_SEGMENT_131_LAYOUT"], {"checksumPresent": True, "emvCardSequenceNumberField": "NOT_IN_SEGMENT_131_LAYOUT"}, ["checksum required", "do not parse EMV Card Sequence Number as a Segment 131 field"]),
                draft_case("LIFE-131-NOT-SEG130-PARSER", "SEG130-R-023", "Use Segment 131 response layout, not Segment 130 request parser", ["EMV_SEGMENT_131_LAYOUT", "EMV_SEGMENT_130_LAYOUT"], {"requestSegment130Separators": True, "responseSegment131Separators": False}, ["response parser accepts the source-defined positional response layout independently of the request parser"]),
            ],
            "openScope": "Length-boundary vectors are logical validator tests only; complete EMV response bytes and authentic cryptograms require separately approved fixtures/oracles.",
        },
    }


def source_excerpts():
    lines = native(SPEC).read_text(encoding="utf-8-sig").splitlines()
    digest = sha256(SPEC)
    result = []
    for identifier, (start, end) in EVIDENCE_RANGES.items():
        if end > len(lines):
            raise ValueError("Source range exceeds frozen ATL105 text: " + identifier)
        result.append({"evidenceId": identifier, "source": str(SPEC), "sha256": digest,
                       "startLine": start, "endLine": end, "quote": "\n".join(lines[start - 1:end]),
                       "interpretationStatus": "REVIEW_REQUIRED"})
    return result


def self_test():
    plan = repairs()
    assert len(plan) == 6
    assert sum(len(item["cases"]) for item in plan.values()) == 25
    evidence_ids = set(EVIDENCE_RANGES)
    assert all(set(case["sourceEvidenceIds"]) <= evidence_ids for item in plan.values() for case in item["cases"])
    fleet_order = repairs()["SEG101-R-007"]["cases"][0]["inputShape"]["fieldNumbers"]
    assert fleet_order == list(range(1, 19))
    assert plan["SEG103-R-024"]["cases"][-1]["unresolvedQuestion"] is not None
    assert all(case["readiness"].startswith("REVIEW_REQUIRED") and case["executionStatus"] == "NOT_EXECUTED"
               for item in plan.values() for case in item["cases"])
    assert plan["SEG130-R-023"]["cases"][2]["sourceEvidenceIds"] == ["EMV_SEGMENT_131_LAYOUT"]
    print("PASS: six source-scoped repair plans, 25 review-only cases, EBT uncertainty and no execution promotion")


def run(output):
    if native(output).exists():
        raise ValueError("Refusing to overwrite repair supplement")
    package = read_json(PACKAGE)
    current = {row["id"]: row for row in package["businessRequirements"]}
    plans = repairs()
    if any("BR-RULE-" + rule not in current for rule in plans):
        raise ValueError("Current Test package no longer contains a target rule")
    if package.get("manifest", {}).get("aiArtifactsIncluded") is not False:
        raise ValueError("Repair source must be the independent Test package")
    evidence = source_excerpts()
    evidence_by_id = {row["evidenceId"]: row for row in evidence}
    business = []
    cases = []
    for rule_id, plan in plans.items():
        record = current["BR-RULE-" + rule_id]
        source_ids = sorted({source_id for case_row in plan["cases"] for source_id in case_row["sourceEvidenceIds"]})
        business.append({"id": record["id"], "currentTitle": record["title"],
                         "sourceAnchors": record.get("sourceAnchors") or [], "currentStatus": record.get("status"),
                         "scopeExpansion": plan["proposedSlices"], "draftCaseIds": [item["caseId"] for item in plan["cases"]],
                         "sourceEvidenceIds": source_ids, "openScope": plan["openScope"],
                         "status": "DRAFT_REVIEW_REQUIRED", "authorizedDecision": "NOT_RECORDED"})
        for case_row in plan["cases"]:
            missing_evidence = set(case_row["sourceEvidenceIds"]) - set(evidence_by_id)
            if missing_evidence:
                raise ValueError("Missing source evidence for " + case_row["caseId"])
            cases.append({**case_row, "canonicalPackageMutation": False, "aiArtifactsUsedAsTrainingTruth": False})
    output.mkdir(parents=True)
    write_json(output / "repair-proposal.json", {
        "manifest": {"proposalId": "ATL105-LIFECYCLE-SIX-PARTIAL-SCOPE-REPAIR", "specification": "ATL105",
                     "specificationVersion": package["manifest"].get("specificationVersion", "2026-3"),
                     "authority": "INDEPENDENT_TEST_SOLUTION", "aiArtifactsIncluded": False,
                     "status": "DRAFT_REVIEW_REQUIRED", "canonicalPackageMutated": False, "executionCertified": False},
        "inputs": {"canonicalTestPackageSha256": sha256(PACKAGE), "sourceTextSha256": sha256(SPEC)},
        "repairSummary": {"partialRulesTargeted": len(business), "draftCases": len(cases), "authorizedDecisions": 0, "executedCases": 0},
        "businessRequirements": business, "testCaseProposals": cases,
        "testDataProposals": [{"caseId": item["caseId"], "logicalInput": item["inputShape"],
                                "readiness": item["readiness"], "expectedOutcome": item["expectedOutcome"],
                                "payloadValuesAreSyntheticOrSymbolic": True} for item in cases],
        "sourceEvidence": evidence,
        "limitations": ["All proposed cases remain review-required and are not inserted into the canonical package.",
                        "The EBT applicability matrix retains unresolved presence branches where source operation lists do not specify segment presence.",
                        "No network/host execution, approved independent outcome oracle, wire capture, or production cryptogram is supplied.",
                        "The external Authorization.json attachment was not read or used; no PAN, track or cryptogram values are copied."]
    })
    write_jsonl(output / "source-evidence.jsonl", evidence)
    write_jsonl(output / "draft-case-proposals.jsonl", cases)
    write_csv(output / "scope-matrix.csv", [{"testBrId": item["id"], "currentTitle": item["currentTitle"],
        "scopeExpansion": item["scopeExpansion"], "draftCaseIds": item["draftCaseIds"], "sourceEvidenceIds": item["sourceEvidenceIds"],
        "openScope": item["openScope"], "currentStatus": item["currentStatus"], "status": item["status"]} for item in business])
    native(output / ".gitattributes").write_text("* -text whitespace=cr-at-eol\n", encoding="ascii")
    report = ["# Lifecycle: Six Partial-Scope Repair Proposal", "",
        "This supplement expands six existing Lifecycle Test BR scopes into 23 source-backed review cases. It does not modify the canonical package or authorize matches.",
        "", "## Disposition", "", "| Test BR | Draft cases | Remaining scope / gate |", "|---|---:|---|"]
    for item in business:
        report.append("| " + item["id"].removeprefix("BR-RULE-") + " | " + str(len(item["draftCaseIds"])) + " | " + str(item["openScope"] or "Source-derived cases drafted; reviewer sign-off required") + " |")
    report += ["", "## Proposed Cases", ""]
    for item in business:
        report += ["### " + item["id"], "", "Current: " + item["currentTitle"], ""]
        for case_row in plans[item["id"].removeprefix("BR-RULE-")]["cases"]:
            report.append("- `" + case_row["caseId"] + "`: " + case_row["intent"] + " — expected assertion: "
                          + "; ".join(case_row["assertions"]) + ". Evidence: " + ", ".join(case_row["sourceEvidenceIds"]) + ".")
        if item["openScope"]:
            report += ["", "Open scope: " + item["openScope"]]
        report.append("")
    report += ["## Governance", "", "All cases have `expectedOutcome=REVIEW_REQUIRED` and `executionStatus=NOT_EXECUTED`. They are logical source-derived designs, not executable fixture payloads or authorized Test results. Approve the BR interpretation and independent oracle before promoting any to the canonical package.",
               "", "The two source windows for EMV differ by request/response: Segment 130 uses separators and 2,000-byte EMV Additional Information; Segment 131 has no separators and a 2,800-byte cap. Keep their parsers independent.",
               "", "The source-backed EBT/eWIC operation matrix does not explicitly decide presence for every other EBT operation; those branches remain unresolved rather than being inferred optional.", ""]
    native(output / "REPAIR-REPORT.md").write_text("\n".join(report), encoding="utf-8")
    manifest = {"algorithm": "SHA-256", "artifacts": {path.name: sha256(path) for path in native(output).iterdir() if path.is_file()}}
    write_json(output / "manifest.json", manifest)
    print(json.dumps({"partialRules": len(business), "draftCases": len(cases), "sourceExcerpts": len(evidence),
                      "canonicalPackageMutated": False, "authorizedDecisions": 0, "executedCases": 0}, indent=2))


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--self-test", action="store_true")
    parser.add_argument("--output", type=Path, default=OUTPUT)
    args = parser.parse_args()
    if args.self_test:
        self_test()
    else:
        run(args.output)