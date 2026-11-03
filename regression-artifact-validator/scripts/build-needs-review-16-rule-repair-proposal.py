"""Create a source-only, review-required repair proposal for the 16 Test BR gaps."""

import argparse
import csv
import hashlib
import json
import os
from pathlib import Path


SCRIPT = Path(__file__).resolve()
ROOT = SCRIPT.parents[1]
SPEC = ROOT / "specifications/ATL105/docs/specs/extracted_text.txt"
PACKAGE = ROOT / "specifications/ATL105/test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json"
COMMUNICATION = ROOT / "specifications/ATL105/registers/atl105-communication-register.json"
OUTPUT = ROOT / "specifications/ATL105/test-output/test-solution-independent-review/needs-review-16-rule-repair-proposal-v1"


FLEET_TAGS = [
    ("DLS", "an", 3), ("DLN", "an", 22), ("PON", "an", 31), ("INV", "an", 31),
    ("TRP", "an", 15), ("UNT", "an", 31), ("TLH", "n", 6), ("DOB", "n", 8),
    ("ZIP", "an", 9), ("END", "an", 31), ("MID", "an", 31), ("VIN", "an", 17),
    ("TRA", "an", 15), ("HUB", "n", 9), ("TLR", "an", 15), ("CBA", "n", 6), ("VHT", "an", 10),
]
VAU_CODES = ["VAU" + str(value).zfill(3) for value in (*range(1, 15), 16)]
CONNECTOR_CODES = ["001", "002", "003", "100", "101", "102", "103", "200"]


def native(path):
    resolved = str(Path(path).resolve())
    if os.name == "nt" and not resolved.startswith("\\\\?\\"):
        resolved = "\\\\?\\" + resolved
    return Path(resolved)


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
    if not rows:
        raise ValueError("Cannot write an empty CSV")
    fields = list(dict.fromkeys(key for row in rows for key in row))
    with native(path).open("w", encoding="utf-8", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        writer.writerows({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (dict, list)) else value
                          for key, value in row.items()} for row in rows)


def case(case_id, intent, logical_input, proposed_outcome, evidence_ids, *, blocker=None):
    return {
        "id": case_id,
        "intent": intent,
        "logicalInput": logical_input,
        "proposedOutcome": proposed_outcome,
        "sourceEvidenceIds": evidence_ids,
        "reviewStatus": "REVIEW_REQUIRED",
        "readiness": "EXTERNAL_FIXTURE_REQUIRED" if blocker else "REVIEW_REQUIRED",
        "blocker": blocker,
        "testExecution": "NOT_EXECUTED",
    }


def make_repairs():
    repairs = {
        "SEG100-R-012": {
            "classification": "SOURCE_DERIVED_RULE",
            "proposedTitle": "TCP/IP TPDU Protocol ID is fixed at 0x60",
            "proposedRequirement": "For the standard BUYPASS TCP/IP header, the one-byte TPDU Protocol ID shall be 0x60.",
            "evidence": ["TPDU_HEADER"],
            "cases": [
                case("TPDU-012-POS", "Accept the source-defined TPDU Protocol ID", {"protocolIdHex": "60"}, "ACCEPT", ["TPDU_HEADER"]),
                case("TPDU-012-NEG", "Reject a non-source Protocol ID", {"protocolIdHex": "61"}, "REJECT", ["TPDU_HEADER"]),
            ],
            "notes": "Logical-header probes only; full-message transport/wire validation remains separate.",
        },
        "SEG100-R-024": {
            "classification": "SOURCE_DERIVED_RULE",
            "proposedTitle": "Reserved TPDU destination and source addresses are zero",
            "proposedRequirement": "In messages sent to BUYPASS, the two-byte TPDU Destination Address and two-byte TPDU Source Address shall each be 0x0000.",
            "evidence": ["TPDU_HEADER"],
            "cases": [
                case("TPDU-024-POS", "Accept all four reserved TPDU address bytes as zero", {"destinationHex": "0000", "sourceHex": "0000"}, "ACCEPT", ["TPDU_HEADER"]),
                case("TPDU-024-NEG", "Reject nonzero reserved address bytes", {"destinationHex": "0001", "sourceHex": "0000"}, "REJECT", ["TPDU_HEADER"]),
            ],
            "notes": "The specification states this for messages to BUYPASS; do not generalize to other transports or peers.",
        },
        "SEG100-R-066": {
            "classification": "SOURCE_DERIVED_TITLE_CORRECTION",
            "proposedTitle": "Financial Response Code (Element 83) identifies decline",
            "proposedRequirement": "Element 83 is the one-byte Financial Response Code and identifies a financial response as an approval with purchase/capture, approval without purchase/capture, or decline. Keep the separate Element 26 Decline Code distinct.",
            "evidence": ["RESPONSE_CODE_83", "RESPONSE_CODE_S"],
            "cases": [
                case("RESP-066-CLASSIFY", "Classify the source-defined financial response family, not a reason code", {"responseElement": "83", "responseCode": "S"}, "DECLINED_RESPONSE_FAMILY", ["RESPONSE_CODE_83", "RESPONSE_CODE_S"]),
                case("RESP-066-DISTINCT", "Keep response status and decline reason as distinct fields", {"responseElement": "83", "declineReasonElement": "26"}, "DISTINCT_FIELDS", ["RESPONSE_CODE_83"]),
            ],
            "notes": "Response-family value validation must use the separate Element 83 family matrix; do not treat response code as Element 26.",
        },
        "SEG101-R-022": {
            "classification": "SOURCE_DERIVED_RULE",
            "proposedTitle": "Fleet Tag uses a fixed three-byte tag and up to 31 data bytes",
            "proposedRequirement": "Fleet Tag fields 1-5, when applicable, use a three-byte fixed tag followed by zero to 31 data bytes; the source limits these fields to Auth Completion messages when Host Prompts are supported.",
            "evidence": ["FLEET_TAGS"],
            "cases": [
                case("FLEET-022-POS", "Accept a three-byte Fleet Tag with data at the maximum length", {"messageType": "0220", "hostPrompts": True, "tag": "DLS", "dataBytes": 31}, "ACCEPT", ["FLEET_TAGS"]),
                case("FLEET-022-NEG", "Reject tag data exceeding the 31-byte limit", {"messageType": "0220", "hostPrompts": True, "tag": "DLS", "dataBytes": 32}, "REJECT", ["FLEET_TAGS"]),
            ],
            "notes": "The generic maximum does not replace the per-code maximum in SEG101-R-024.",
        },
        "SEG101-R-023": {
            "classification": "SOURCE_DERIVED_ENUMERATION_COMPLETION",
            "proposedTitle": "Fleet Tag code is one of the 17 source-listed Auth Completion codes",
            "proposedRequirement": "When Host Prompts are supported on an Auth Completion (0220), each three-byte Fleet Tag code shall be one of DLS, DLN, PON, INV, TRP, UNT, TLH, DOB, ZIP, END, MID, VIN, TRA, HUB, TLR, CBA, or VHT.",
            "evidence": ["FLEET_TAGS"],
            "cases": [
                case("FLEET-023-ENUM", "Accept each of the 17 source-listed codes and reject an unknown code", {"allowedCodes": [row[0] for row in FLEET_TAGS], "unknownCode": "ZZZ", "messageType": "0220", "hostPrompts": True}, "ACCEPT_LIST_REJECT_UNKNOWN", ["FLEET_TAGS"]),
                case("FLEET-023-CONTEXT", "Do not treat the code list as valid outside its stated Auth Completion/Host Prompts context", {"messageType": "0100", "hostPrompts": True, "tag": "DLS"}, "CONTEXT_REVIEW_OR_REJECT", ["FLEET_TAGS"]),
            ],
            "notes": "This repairs the missing eleven codes in the current partial AI-derived comparison; the source table is the authority.",
        },
        "SEG101-R-024": {
            "classification": "SOURCE_DERIVED_ENUMERATION_COMPLETION",
            "proposedTitle": "Fleet Tag data obeys the source type and maximum for its tag code",
            "proposedRequirement": "For each Auth Completion Fleet Tag, data shall obey that code's source type and maximum length: " + "; ".join(f"{code} {kind}{length}" for code, kind, length in FLEET_TAGS) + ".",
            "evidence": ["FLEET_TAGS"],
            "cases": [
                case("FLEET-024-BOUNDARIES", "Exercise each code at its source type/maximum and one byte over", {"perCodeBoundaries": [{"code": code, "type": kind, "maxLength": length, "validLength": length, "invalidLength": length + 1} for code, kind, length in FLEET_TAGS]}, "MAX_ACCEPT_OVERFLOW_REJECT", ["FLEET_TAGS"]),
            ],
            "notes": "Semantic validation of values such as DOB/state/ZIP remains separate from declared type and length.",
        },
        "SEG102-R-017": {
            "classification": "SOURCE_DERIVED_RULE",
            "proposedTitle": "Fuel merchants send fuel and nonfuel product data",
            "proposedRequirement": "A fuel merchant shall send both fuel and nonfuel product data in the Product Code Data Segment.",
            "evidence": ["FUEL_NONFUEL"],
            "cases": [
                case("PROD-017-POS", "Accept a fuel-merchant product segment containing both fuel and nonfuel products", {"merchantContext": "FUEL", "fuelProductCount": 1, "nonfuelProductCount": 1}, "ACCEPT", ["FUEL_NONFUEL"]),
                case("PROD-017-NEG-FUEL", "Detect omitted fuel product data for a fuel merchant", {"merchantContext": "FUEL", "fuelProductCount": 0, "nonfuelProductCount": 1}, "REJECT_OR_REVIEW", ["FUEL_NONFUEL"]),
                case("PROD-017-NEG-NONFUEL", "Detect omitted nonfuel product data for a fuel merchant", {"merchantContext": "FUEL", "fuelProductCount": 1, "nonfuelProductCount": 0}, "REJECT_OR_REVIEW", ["FUEL_NONFUEL"]),
            ],
            "notes": "Comdata exclusion and product/amount reconciliation remain separate rules, not silently included here.",
        },
        "SEG103-R-020": {
            "classification": "SOURCE_DERIVED_PROHIBITION",
            "proposedTitle": "eWIC does not support Return transactions",
            "proposedRequirement": "The eWIC transaction flow shall not issue a Return transaction; this is an operation prohibition, not a response-code lookup.",
            "evidence": ["EWIC_NO_RETURN"],
            "cases": [
                case("EWIC-020-PROHIBIT", "Block an attempted eWIC Return before emitting a host request", {"program": "EWIC", "operation": "RETURN", "hostRequestEmitted": False}, "BLOCK_LOCAL_REQUEST", ["EWIC_NO_RETURN"]),
            ],
            "notes": "ATL105 does not define a host decline response for an unsupported Return; do not invent one.",
        },
        "SEG109-R-019": {
            "classification": "SOURCE_DERIVED_COMPOSITE",
            "proposedTitle": "Electronic Mail Retrieval uses Prompt Code 981, or 996 for proprietary card data; print data is at most 750 bytes per request",
            "proposedRequirement": "A retrieval request shall use Prompt Code 981; retrieval of proprietary card data shall use 996. Print data shall not exceed 750 bytes per request.",
            "evidence": ["EMAIL_RETRIEVAL_SUBMISSION"],
            "cases": [
                case("MAIL-019-981", "Use retrieval Prompt Code 981", {"purpose": "GENERAL_RETRIEVAL", "promptCode": "981"}, "ACCEPT", ["EMAIL_RETRIEVAL_SUBMISSION"]),
                case("MAIL-019-996", "Use Prompt Code 996 for proprietary card data retrieval", {"purpose": "PROPRIETARY_CARD_DATA", "promptCode": "996"}, "ACCEPT", ["EMAIL_RETRIEVAL_SUBMISSION"]),
                case("MAIL-019-BOUNDARY", "Check print-data byte boundary", {"maxBytes": 750, "boundaryLengths": [750, 751]}, "750_ACCEPT_751_REJECT", ["EMAIL_RETRIEVAL_SUBMISSION"]),
            ],
            "notes": "Byte length is measured on encoded data; complete electronic-mail envelope/response behavior requires its own source-backed rules.",
        },
        "SEG109-R-020": {
            "classification": "SOURCE_DERIVED_COMPOSITE",
            "proposedTitle": "Electronic Mail Submission uses Prompt Code 995, at most 217 data bytes, and a receipt confirmation response",
            "proposedRequirement": "A submission request shall use Prompt Code 995, contain no more than 217 data bytes, and receive an Electronic Mail Response confirming receipt.",
            "evidence": ["EMAIL_RETRIEVAL_SUBMISSION"],
            "cases": [
                case("MAIL-020-BOUNDARY", "Check submission data byte boundary", {"promptCode": "995", "maxDataBytes": 217, "boundaryLengths": [217, 218]}, "217_ACCEPT_218_REJECT", ["EMAIL_RETRIEVAL_SUBMISSION"]),
                case("MAIL-020-CONFIRM", "Require a receipt-confirmation response after accepted submission", {"promptCode": "995", "responseFamily": "ELECTRONIC_MAIL_RESPONSE"}, "CONFIRMATION_REQUIRED", ["EMAIL_RETRIEVAL_SUBMISSION"]),
            ],
            "notes": "The response must be bound to the same request before integration execution; no host behavior is claimed here.",
        },
        "SEG119-R-035": {
            "classification": "POLICY_ONLY_BLOCKED",
            "proposedTitle": "Settlement cutoff/time-zone approval prerequisite (policy register; not an ATL105 BR)",
            "proposedRequirement": "No source-derived runtime requirement is proposed. Preserve the certification policy prerequisite only in the policy/SME register until the policy owner defines the cutoff, timezone, and approval authority.",
            "evidence": [],
            "cases": [],
            "blocker": {"registerId": "SEG119-SME-005", "owner": "SME/TBA or settlement policy owner", "status": "OPEN", "requiredDecision": "Approve cutoff/time-zone policy or withdraw the certification gate."},
            "notes": "Do not count this as an ATL105 source-derived BR or AI semantic gap; do not create an execution case without the policy definition.",
        },
        "SEG145-R-004": {
            "classification": "DATED_SOURCE_NOTICE_CONTEXT_BLOCKED",
            "proposedTitle": "MasterCard Enhanced Fleet availability notice (ATL105 2026-3, historical date condition)",
            "proposedRequirement": "The 2026-3 specification states that MasterCard Enhanced functionality was expected in First Data production no earlier than June 2026 and, until enabled, was for development/certification preparation rather than live transactions.",
            "evidence": ["MASTER_CARD_AVAILABILITY"],
            "cases": [],
            "blocker": {"registerId": "SEG145-SME-002", "owner": "SME/TBA or product owner", "status": "OPEN", "requiredDecision": "Confirm current production enablement, effective date, and environment before deriving a current live-use test."},
            "notes": "Keep the dated source statement; do not infer that the June 2026 milestone occurred.",
        },
        "SEG155-R-005": {
            "classification": "SOURCE_DERIVED_ENUMERATION_COMPLETION",
            "proposedTitle": "Visa Account Updater Result Code is a six-character Table 006 value from the source enumeration",
            "proposedRequirement": "When the response field is applicable (Visa and an Account Updater Request Indicator of Y or I), Table 006 has ID 006, length 006, and one of VAU001-VAU014 or VAU016; VAU015 is not defined in the source list.",
            "evidence": ["ACCOUNT_UPDATER_RESULT_ENUMERATION"],
            "cases": [
                case("UPDATER-005-ENUM", "Accept every source-listed VAU result and reject the unlisted VAU015", {"tableId": "006", "tableLength": "006", "validValues": VAU_CODES, "invalidValue": "VAU015"}, "ACCEPT_LIST_REJECT_VAU015", ["ACCOUNT_UPDATER_RESULT_ENUMERATION"]),
                case("UPDATER-005-GATE", "Apply response-field presence only for Visa with request indicator Y or I", {"eligibleContext": {"network": "VISA", "requestIndicator": ["Y", "I"]}, "ineligibleContexts": [{"network": "MASTERCARD", "requestIndicator": "Y"}, {"network": "VISA", "requestIndicator": "N"}]}, "ELIGIBLE_ONLY", ["ACCOUNT_UPDATER_RESULT_ENUMERATION"]),
            ],
            "notes": "Segment 155 has no separators; layout and parent transaction/Segment 111 context need independent fixture coverage.",
        },
        "SEG156-R-006": {
            "classification": "SOURCE_DERIVED_ENUMERATION_COMPLETION",
            "proposedTitle": "Visa EV Connector Type is one of the eight source-defined codes",
            "proposedRequirement": "Connector Type Table 12, when present for the applicable Visa EV charging context, shall use 001, 002, 003, 100, 101, 102, 103, or 200.",
            "evidence": ["EV_CONNECTOR_ENUMERATION"],
            "cases": [
                case("EV-006-ENUM", "Accept all eight Visa Connector Type codes and reject an unknown value", {"tableId": "12", "validValues": CONNECTOR_CODES, "invalidValue": "999"}, "ACCEPT_LIST_REJECT_UNKNOWN", ["EV_CONNECTOR_ENUMERATION"]),
            ],
            "notes": "Do not synthesize EV transaction tokens or infer network support beyond the source-defined Visa list.",
        },
        "SEGDL3-R-008": {
            "classification": "SOURCE_DERIVED_RULE_WITH_EXTERNAL_TIME_FIXTURE",
            "proposedTitle": "Date and Time Load request is device-initiated with Load Type D; response uses initiating-device timezone/DST",
            "proposedRequirement": "The request is the positional string '?' + 13-character Terminal Identifier + 'D', with no Field Separators; no merchant load flag is required. The response time is adjusted for the initiating device's timezone and daylight-saving setting.",
            "evidence": ["DATE_TIME_LOAD"],
            "cases": [
                case("DL3-008-REQUEST", "Validate exact request prefix, TID width, fixed D and no separator", {"request": "?AB12345678901D", "tidLength": 13, "fieldSeparatorPresent": False}, "ACCEPT", ["DATE_TIME_LOAD"]),
                case("DL3-008-NEG", "Reject wrong fixed load type or inserted separator", {"requestVariants": ["?AB12345678901P", "?AB12345678901|D"]}, "REJECT", ["DATE_TIME_LOAD"]),
                case("DL3-008-TIMEZONE", "Compare response time against an approved timezone/DST fixture", {"timezoneFixtureRequired": True, "deviceTimeZone": None, "dstRule": None}, "REVIEW_REQUIRED_UNTIL_FIXTURE", ["DATE_TIME_LOAD"], blocker="No approved device timezone/DST fixture is supplied."),
            ],
            "notes": "Request serialization is locally source-testable; response clock conversion is not executable without a source-bound timezone fixture.",
        },
        "SEGDL4-R-007": {
            "classification": "SOURCE_DERIVED_SEQUENCE_WITH_EXTERNAL_DEVICE_FIXTURE",
            "proposedTitle": "Software update follows the eight-step profile/download/load/schedule/completion sequence",
            "proposedRequirement": "Follow the source sequence: profile bit; Download Indicator 1 on the next transaction response; Table Load request; DL4/DL5 response; store version/record ID/address/date/time; scheduled full application load; on success request Table Load; BUYPASS sends that Table Load without the profile bit. Automated full-load attempts are limited to three; unsuccessful load prints the decline message.",
            "evidence": ["SOFTWARE_UPDATE_SEQUENCE"],
            "cases": [
                case("DL4-007-PATH", "Exercise the ordered source-defined software update state machine", {"orderedEvents": ["PROFILE_BIT_SET", "NEXT_RESPONSE_DOWNLOAD_INDICATOR_1", "DEVICE_TABLE_LOAD_REQUEST", "BUYPASS_DL4_DL5_RESPONSE", "STORE_LOAD_PARAMETERS", "SCHEDULED_FULL_APPLICATION_LOAD", "SUCCESSFUL_LOAD", "TABLE_LOAD_REQUEST", "TABLE_LOAD_WITHOUT_PROFILE_BIT"]}, "ORDERED_PATH_REQUIRED", ["SOFTWARE_UPDATE_SEQUENCE"]),
                case("DL4-007-RETRY", "Stop automated attempts after three and print decline on unsuccessful load", {"failedAttempts": 3, "fourthAttemptAllowed": False, "declineMessageOnFailure": True}, "THREE_ATTEMPT_LIMIT", ["SOFTWARE_UPDATE_SEQUENCE"]),
                case("DL4-007-DEVICE", "Run the load state machine using an approved device-management fixture", {"deviceFirmwareFixtureRequired": True, "schedule": None, "network": None}, "REVIEW_REQUIRED_UNTIL_FIXTURE", ["SOFTWARE_UPDATE_SEQUENCE"], blocker="Application download/device-management protocol is external to ATL105."),
            ],
            "notes": "No actual application image, network endpoint, device-management response, or production software data is included.",
        },
    }
    if len(repairs) != 16:
        raise ValueError("Repair proposal must cover exactly 16 Test BRs")
    return repairs


def source_ranges():
    return {
        "TPDU_HEADER": (25527, 25561),
        "RESPONSE_CODE_83": (21186, 21214),
        "RESPONSE_CODE_S": (21276, 21284),
        "FLEET_TAGS": (11490, 11555),
        "FUEL_NONFUEL": (11575, 11596),
        "EWIC_NO_RETURN": (5280, 5298),
        "EMAIL_RETRIEVAL_SUBMISSION": (6208, 6238),
        "MASTER_CARD_AVAILABILITY": (15198, 15214),
        "ACCOUNT_UPDATER_RESULT_ENUMERATION": (16573, 16622),
        "EV_CONNECTOR_ENUMERATION": (16864, 16891),
        "DATE_TIME_LOAD": (9347, 9382),
        "SOFTWARE_UPDATE_SEQUENCE": (6162, 6199),
    }


def find_register_item(value, item_id):
    if isinstance(value, dict):
        if value.get("id") == item_id:
            return value
        for child in value.values():
            found = find_register_item(child, item_id)
            if found is not None:
                return found
    elif isinstance(value, list):
        for child in value:
            found = find_register_item(child, item_id)
            if found is not None:
                return found
    return None


def render(output):
    if native(output).exists():
        raise ValueError("Refusing to overwrite existing repair proposal")
    source = native(SPEC).read_text(encoding="utf-8-sig").splitlines()
    package = read_json(PACKAGE)
    comms = read_json(COMMUNICATION)
    current = {row["id"].removeprefix("BR-RULE-"): row for row in package["businessRequirements"]}
    repairs = make_repairs()
    if not set(repairs).issubset(current):
        raise ValueError("Independent Test package is missing a target BR")
    blocked_refs = {"SEG119-R-035": "SEG119-SME-005", "SEG145-R-004": "SEG145-SME-002"}
    for item_id in blocked_refs.values():
        item = find_register_item(comms, item_id)
        if item is None or item.get("status") != "OPEN":
            raise ValueError("Expected open communication-register blocker: " + item_id)
    evidence = []
    for evidence_id, (start, end) in source_ranges().items():
        evidence.append({"evidenceId": evidence_id, "source": str(SPEC), "sha256": sha256(SPEC),
                         "startLine": start, "endLine": end, "quote": "\n".join(source[start - 1:end]),
                         "interpretationStatus": "REVIEW_REQUIRED"})

    proposed_requirements = []
    proposed_cases = []
    for rule_id, repair in repairs.items():
        existing = current[rule_id]
        open_ref = blocked_refs.get(rule_id)
        blocker = repair.get("blocker")
        if open_ref:
            blocker = {**(blocker or {}), "communicationRegisterId": open_ref}
        proposed_requirements.append({
            "id": existing["id"], "currentTitle": existing["title"], "proposedTitle": repair["proposedTitle"],
            "proposedRequirement": repair["proposedRequirement"], "sourceAnchors": existing.get("sourceAnchors") or [],
            "repairClass": repair["classification"], "sourceEvidenceIds": repair["evidence"],
            "status": "DRAFT_REVIEW_REQUIRED", "authorizedDecision": "NOT_RECORDED",
            "blocker": blocker, "notes": repair.get("notes", ""),
        })
        for item in repair["cases"]:
            proposed_cases.append({"testBrId": existing["id"], "title": existing["title"], **item,
                                   "canonicalPackageMutation": False, "aiArtifactsUsedAsTrainingTruth": False})

    blocked = [row for row in proposed_requirements if row["blocker"] or not row["sourceEvidenceIds"]]
    active_draft = [row for row in proposed_requirements if row not in blocked]
    proposal = {
        "manifest": {"proposalId": "ATL105-NEEDS-REVIEW-16-RULE-REPAIR", "specification": "ATL105",
                     "specificationVersion": "2026-3", "authority": "INDEPENDENT_TEST_SOLUTION",
                     "aiArtifactsIncluded": False, "status": "DRAFT_REVIEW_REQUIRED",
                     "canonicalPackageMutated": False, "executionCertified": False},
        "repairSummary": {"targetRules": 16, "sourceGroundedDrafts": len(active_draft), "blockedOrContextGated": len(blocked),
                          "draftCaseProposals": len(proposed_cases), "authorizedDecisions": 0, "executedCases": 0},
        "businessRequirements": proposed_requirements,
        "blockedItems": blocked,
        "draftCaseProposals": proposed_cases,
        "testDataPolicy": "Logical source-derived vectors only. Every vector is REVIEW_REQUIRED and NOT_EXECUTED; none is an executable canonical TD.",
        "inputs": {"testPackageSha256": sha256(PACKAGE), "sourceSha256": sha256(SPEC), "communicationRegisterSha256": sha256(COMMUNICATION)},
    }
    native(output).mkdir(parents=True)
    write_json(output / "repair-proposal.json", proposal)
    write_jsonl(output / "source-evidence.jsonl", evidence)
    write_jsonl(output / "draft-case-proposals.jsonl", proposed_cases)
    native(output / ".gitattributes").write_text("* -text whitespace=cr-at-eol\n", encoding="ascii")
    report = ["# Needs Review: 16-Rule Draft Repair Proposal", "",
              "This source-only Test Solution supplement repairs the draft wording and test design for the 16-rule batch. It does not modify the canonical Test package, execute tests, or record authorized decisions.",
              "", "## Repair Scope", "", "| Treatment | Rules |", "|---|---:|"]
    counts = {}
    for row in proposed_requirements:
        counts[row["repairClass"]] = counts.get(row["repairClass"], 0) + 1
    for key, value in sorted(counts.items()):
        report.append("| " + key + " | " + str(value) + " |")
    report += ["", "## Required Follow-up", "",
        "- Review all 16 corrected rule statements and anchors against source excerpts.",
        "- Move `SEG119-R-035` to a policy gate or retain it blocked pending `SEG119-SME-005`; it is not sourced as an ATL105 runtime obligation.",
        "- Keep `SEG145-R-004` historical and blocked pending `SEG145-SME-002` current product availability confirmation.",
        "- Add approved independent expected outcomes and concrete request/response envelopes before promoting any draft cases to the canonical package.",
        "- Wire/header and stateful software-load cases require a harness/device-management fixture; no host or production operation was attempted.",
        "", "## Rule-by-Rule Corrections", ""]
    for row in proposed_requirements:
        report += ["### " + row["id"], "", "Current: " + row["currentTitle"], "", "Proposed: " + row["proposedTitle"],
                   "", row["proposedRequirement"], "", "Repair class: `" + row["repairClass"] + "`; status remains `DRAFT_REVIEW_REQUIRED`."]
        if row["blocker"]:
            report.append("Blocker: " + json.dumps(row["blocker"], ensure_ascii=True))
        if row["notes"]:
            report.append("Note: " + row["notes"])
        related = [item for item in proposed_cases if item["testBrId"] == row["id"]]
        report.append("Draft cases: " + (", ".join(item["id"] for item in related) if related else "none; blocked or context-gated"))
        report.append("")
    report += ["## Governance", "", "The 2026-3 ATL105 source is the authority for its enumerations and protocol statements. The independent Test Solution remains separate from AI artifacts. All draft vectors retain `REVIEW_REQUIRED`; they are not canonical Test Data, SME decisions, coverage credit, or execution evidence.",
               "", "The attached Authorization.json was not ingested; no PAN, Track data, or production values are copied into this supplement.", ""]
    native(output / "REPAIR-REPORT.md").write_text("\n".join(report), encoding="utf-8")
    artifacts = {path.name: sha256(path) for path in native(output).iterdir() if path.is_file()}
    write_json(output / "manifest.json", {"algorithm": "SHA-256", "artifacts": artifacts})
    return proposal


def self_test():
    repairs = make_repairs()
    assert len(repairs) == 16
    assert len(FLEET_TAGS) == 17 and len(set(row[0] for row in FLEET_TAGS)) == 17
    assert len(VAU_CODES) == 15 and "VAU015" not in VAU_CODES and "VAU016" in VAU_CODES
    assert len(CONNECTOR_CODES) == 8
    assert repairs["SEG119-R-035"]["classification"] == "POLICY_ONLY_BLOCKED"
    assert repairs["SEG145-R-004"]["classification"] == "DATED_SOURCE_NOTICE_CONTEXT_BLOCKED"
    assert all(case_row["readiness"] != "EXECUTABLE" for repair in repairs.values() for case_row in repair["cases"])
    assert all(case_row["testExecution"] == "NOT_EXECUTED" for repair in repairs.values() for case_row in repair["cases"])
    print("PASS: 16 source-scoped repairs, complete 17-tag/15-VAU/8-connector lists, and approval/execution guards")


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--self-test", action="store_true")
    parser.add_argument("--output", type=Path, default=OUTPUT)
    args = parser.parse_args()
    if args.self_test:
        self_test()
    else:
        proposal = render(args.output)
        print(json.dumps(proposal["repairSummary"], indent=2))