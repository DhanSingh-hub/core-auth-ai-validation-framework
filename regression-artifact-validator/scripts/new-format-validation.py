"""Release-aware, independently source-bound BR/TS/TC/TD validation and readiness evidence."""

import argparse
import copy
import importlib.util
import json
import re
import sys
from collections import Counter
from pathlib import Path


HELPER_PATH = Path(__file__).with_name("build-all-network-requirements-review.py")
SPEC = importlib.util.spec_from_file_location("network_review_v2", HELPER_PATH)
assert SPEC is not None and SPEC.loader is not None
network = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(network)
shared = network.shared
PACK = shared.PACK
OUTPUT = PACK / "test-output" / "test-solution-independent-review" / "new-format-release-readiness-v2"
VERSIONS = {"2026-3", "2026-4"}
KINDS = ("businessRequirements", "testScenarios", "testCases", "testData")
OUTCOMES = {"PASS", "FAIL", "NOT_ASSESSED"}
SOURCE_FILES = {
    "2026-3": ("docs/specs/extracted_text.txt",
               "6d03bf09f25b9126975b584aef83c21d1b7b081cabccfb9af3b89d46a26d06aa"),
    "2026-4": ("docs/specs/extracted_text_new.txt",
               "93f11336ae817266df786c8a83541f96ae680ea08629117a775d362c8b45a497"),
}
NETWORKS = {*network.NETWORK_GROUPS, "NYCE", "PULSE", "ACCEL", "SHAZAM", "INTERLINK", "MAESTRO", "STAR"}


def load(path: Path) -> dict:
    def pairs(items):
        result = {}
        for key, value in items:
            if key in result:
                raise ValueError(f"Duplicate JSON key: {key}")
            result[key] = value
        return result

    def constant(value):
        raise ValueError(f"Invalid JSON number: {value}")

    return json.loads(path.read_text(encoding="utf-8-sig"), object_pairs_hook=pairs, parse_constant=constant)


def identity(value) -> str:
    return json.dumps(value, sort_keys=True, separators=(",", ":"), ensure_ascii=True, allow_nan=False)


def result(status: str, reason: str, **details) -> dict:
    return {"status": status, "reason": reason, **details}


def text(value) -> bool:
    return isinstance(value, str) and bool(value.strip())


def digits(value, width: int) -> bool:
    return isinstance(value, str) and re.fullmatch(rf"[0-9]{{{width}}}", value) is not None


def evidence(version: str, pages: list[int], quotes: list[str], root: Path) -> list[dict]:
    relative, digest = SOURCE_FILES[version]
    source = (root / relative).resolve()
    if not source.is_relative_to(root.resolve()):
        raise ValueError("Trusted source escapes specification pack")
    raw = source.read_bytes()
    if shared.fingerprint(raw) != digest:
        raise ValueError(f"Independent {version} source hash changed; re-review profiles before validation")
    lines = raw.decode("utf-8-sig").splitlines()
    records = []
    for page in pages:
        marker = f"--- PAGE {page} ---"
        starts = [index for index, line in enumerate(lines) if line == marker]
        if len(starts) != 1:
            raise ValueError(f"Independent source page is not unique: {version}/{page}")
        start = starts[0]
        end = next((index for index in range(start + 1, len(lines))
                    if re.fullmatch(r"--- PAGE [0-9]+ ---", lines[index])), len(lines))
        records.append({"path": relative, "sha256": digest, "pdfPage": page,
                        "startLine": start + 1, "endLine": end,
                        "quote": "\n".join(lines[start:end]),
                        "scope": "PINNED_EXTRACTED_SOURCE_NOT_PDF_VISUAL_OR_APPROVAL"})
    normalized = re.sub(r"\s+", " ", " ".join(row["quote"] for row in records))
    for quote in quotes:
        if quote not in normalized:
            raise ValueError(f"Independent source profile quote not found: {version}: {quote}")
    return records


def profiles(version: str, root: Path = PACK) -> dict[str, dict]:
    if version not in VERSIONS:
        raise ValueError(f"Unsupported ATL105 release: {version}")
    definitions = {
        "sequence": {
            "section": "13.2", "segment": "100", "element": "86", "layer": "field",
            "claim": {"subject": "Segment100.Element86", "operator": "asciiDigitsRange",
                      "width": 6, "minimum": 1, "maximum": 999999, "modality": "must",
                      "conditions": {"messageFamily": "STANDARD_FINANCIAL_REQUEST"}},
            "pages": [402 if version == "2026-3" else 409],
            "quotes": ["Number: 86 Name: Sequence Number", "Representation: Fixed length of six digits",
                       "Valid Codes/Values: 000001\u2013999999"],
        },
        "online-refund": {
            "section": "Appendix X", "layer": "field",
            "claim": {"subject": "Table032.Subtable11", "operator": "conditionalIndicator",
                      "length": "001", "value": "Y", "modality": "must",
                      "conditions": {"merchantParticipating": True, "onlineApprovalWanted": True,
                                     "transactionCategory": "RETURN", "lifecycleRole": "ORIGINAL"}}
            if version == "2026-3" else
            {"subject": "Table032.Subtable11", "operator": "sourceState", "value": "RESERVED",
             "modality": "reserved"},
            "pages": [702] if version == "2026-3" else [576, 709],
            "quotes": ["Online Refund/Refund Authorization"] if version == "2026-3" else
            ["Reserved", "Appendix X. Reserved"],
        },
        "token-request": {
            "section": "12.38", "segment": "153", "layer": "field",
            "claim": {"subject": "Segment153.Tables", "operator": "releaseTableProfile",
                      "profile": f"ATL105-{version}-153", "modality": "must",
                      "conditions": {"direction": "REQUEST"}},
            "pages": [307, 308, 309] if version == "2026-3" else [310, 311, 312, 313],
            "quotes": ["12.38 Network Token Data Request Segment",
                       "Variable Value: 013-018" if version == "2026-3" else "Format: YYMM"],
        },
    }
    if version == "2026-4":
        definitions.update({
            "token-response": {
                "section": "12.50", "segment": "154", "layer": "field",
                "claim": {"subject": "Segment154.Tables", "operator": "releaseTableProfile",
                          "profile": "ATL105-2026-4-154", "modality": "must",
                          "conditions": {"direction": "RESPONSE"}},
                "pages": [338, 339, 340],
                "quotes": ["12.50 Network Token Data Response Segment", "Auth Indicator 3rd Try",
                           "T \u2013 Network Token", "P \u2013 Clear PAN"],
            },
            "token-carry-forward": {
                "section": "12.38", "segment": "153", "layer": "lifecycle",
                "claim": {"subject": "Segment153.FollowupInformation", "operator": "equalsOriginal",
                          "modality": "must", "conditions": {"provisionedNetworkTokens": True,
                                                           "authOptimization": True}},
                "pages": [310],
                "quotes": ["same and exact information", "new Card Number or a new Expiration Date"],
            },
            "ia-lifecycle": {
                "section": "Appendix AF", "layer": "lifecycle",
                "claim": {"subject": "IncrementalAuthorization", "operator": "orderedLifecycle",
                          "modality": "must", "conditions": {"roles": [
                              "ORIGINAL_AUTH", "INCREMENTAL_AUTH", "IA_REVERSAL", "IA_COMPLETION"]},
                          "exceptions": ["AF_TRANSACTION_ID_WIRE_MAPPING_UNRESOLVED",
                                         "DE39_TO_ATL105_APPROVAL_FIELD_MAPPING_UNRESOLVED"]},
                "pages": [730],
                "quotes": ["must be preceded by an Original Auth", "Once an IA Completion is received",
                           "unique (per terminal)", "Approval Code (DE 39)"],
            },
            "ia-table82": {
                "section": "Appendix I", "segment": "111", "table": "82", "layer": "field",
                "claim": {"subject": "Segment111.IncrementalAuthData", "operator": "tableLayout",
                          "indicator": "082", "tableId": "82", "tableLength": "004",
                          "terminalNumberWidth": 4, "modality": "must",
                          "conditions": {"presenceRequiredForNetwork": "DISCOVER"}},
                "pages": [425, 431, 636, 637],
                "quotes": ["082 Incremental Auth Data", "Table ID N2 Fixed Value: 82",
                           "Fixed Length : 004", "Discover: Mandatory"],
            },
            "device022": {
                "section": "Appendix I", "segment": "111", "table": "022", "layer": "field",
                "claim": {"subject": "Table022.DeviceTypeExtensions", "operator": "releaseValues",
                          "values": {"35": "CHIP_AND_CONTACTLESS_NO_MAGSTRIPE",
                                     "36": "CHIP_ONLY_NO_MAGSTRIPE_NO_CONTACTLESS",
                                     "37": "CONTACTLESS_ONLY_NO_MAGSTRIPE_NO_CHIP"},
                          "futureRange": [38, 99], "modality": "may"},
                "pages": [565],
                "quotes": ["35 = Card with contact chip and contactless interfaces",
                           "38-99 = Reserved for future device types",
                           "may occur within devices and transaction data without prior notice"],
            },
            "discover-india056": {
                "section": "Appendix I", "segment": "111", "table": "056", "layer": "field",
                "claim": {"subject": "Table056.Subtables01Through07", "operator": "scopedStoredCredentials",
                          "modality": "should", "conditions": {"network": "DISCOVER", "issuerCountry": "INDIA",
                          "paymentTypes": ["RECURRING", "INSTALLMENT"]},
                          "roles": {"01": ["INITIAL"], "02": ["INITIAL"], "03": ["INITIAL", "SUBSEQUENT"],
                                    "04": ["INITIAL", "SUBSEQUENT"], "05": ["INITIAL"],
                                    "06": ["SUBSEQUENT"], "07": ["SUBSEQUENT"]},
                          "exceptions": ["CRYPTOGRAM_AUTHENTICITY_AND_REFERENCE_UNIQUENESS_NOT_ASSESSED"]},
                "pages": [604, 605, 606, 607, 608, 609],
                "quotes": ["should be sent for Discover cards issued in India", "02 to 99",
                           "UD - Not Defined", "UC - Until Canceled", "Sub Table ID \u201c07\u201d"],
            },
            "ia-table063": {
                "section": "Appendix I", "segment": "111", "table": "063", "layer": "lifecycle",
                "claim": {"subject": "Table063.TerminalTransactionNumber", "operator": "scopedContinuity",
                          "width": 4, "modality": "should",
                          "conditions": {"networks": ["VISA", "MASTERCARD", "AMEX"],
                                         "mandatoryContexts": ["LODGING", "INCREMENTAL_AUTH", "EV"]},
                          "exceptions": ["RECOMMENDATION_MISMATCH_IS_NOT_A_HOST_REJECTION_ORACLE"]},
                "pages": [615, 616],
                "quotes": ["Fixed Value: 063", "Mandatory for Lodging, Incremental Auth, and EV transactions",
                           "This field is used only for Visa/MasterCard/Amex",
                           "Terminal Transaction Number should be the same"],
            },
        })
    for rule_id, profile in definitions.items():
        profile["id"] = rule_id
        profile["version"] = version
        profile["status"] = "DRAFT_REVIEW_REQUIRED"
        profile["sourceAnchors"] = [{key: value for key, value in {
            "specification": "ATL105", "version": version, "rule": rule_id,
            "section": profile["section"], "segment": profile.get("segment"),
            "element": profile.get("element"), "table": profile.get("table")}.items() if value is not None}]
        profile["sourceEvidence"] = evidence(version, profile.pop("pages"), profile.pop("quotes"), root)
    return definitions


def validate_context(context: dict, version: str) -> None:
    if not isinstance(context, dict):
        raise ValueError("businessContext must be an object")
    required = ("platform", "specification", "version", "domain", "cardProgram", "groupId",
                "messageFamily", "direction", "transactionCategory", "flowId", "lifecycleRole",
                "cardPresence", "entryMode", "channel")
    if any(not text(context.get(key)) for key in required):
        raise ValueError("Incomplete business context")
    if (context["platform"] != "BUYPASS" or context["specification"] != "ATL105"
            or context["version"] != version):
        raise ValueError("Mixed or unsupported source release/platform in business context")
    group = context["groupId"]
    if group in NETWORKS:
        if context.get("network") != group or context.get("genericCategory") is not None:
            raise ValueError("Named network identity differs from group")
    elif group in network.GENERIC_GROUPS:
        if context.get("network") is not None or context.get("genericCategory") != group:
            raise ValueError("Generic Debit/EBT cannot become named networks")
    elif group == "NOT_APPLICABLE":
        if context["domain"] != "OPERATIONS" or context.get("network") is not None:
            raise ValueError("Not-applicable network requires an operational context")
    else:
        # Unknown names are retained, but never grant semantic applicability.
        if context.get("network") != group or context.get("genericCategory") is not None:
            raise ValueError("Unknown network identity must remain explicit")
    if context["cardProgram"] not in {"CREDIT", "DEBIT", "PREPAID", "BENEFIT", "EBT", "NOT_APPLICABLE", "UNRESOLVED"}:
        raise ValueError("Credit/program identity is not a network")
    if context["direction"] not in {"REQUEST", "RESPONSE"}:
        raise ValueError("Invalid request/response direction")
    if context["cardPresence"] == "NOT_PRESENT" and context["entryMode"] in {"CONTACT_CHIP", "CONTACTLESS", "MAGSTRIPE"}:
        raise ValueError("Physical entry contradicts card-not-present")
    if context["channel"] in {"ECOMMERCE", "MOTO"} and (
            context["cardPresence"] == "PRESENT" or context["entryMode"] == "CONTACTLESS"):
        raise ValueError("Remote channel contradicts physical entry")


def scoped(profile: dict, context: dict) -> bool:
    if context.get("network") not in NETWORKS and context.get("genericCategory") not in network.GENERIC_GROUPS:
        return False
    if context["domain"] != "PAYMENT":
        return False
    if profile["id"] == "sequence":
        return context["messageFamily"] == "STANDARD_FINANCIAL_REQUEST" and context["direction"] == "REQUEST"
    if profile["id"] == "online-refund":
        return (context["messageFamily"] == "STANDARD_FINANCIAL_REQUEST" and context["direction"] == "REQUEST"
                and context["transactionCategory"] == "RETURN" and context["lifecycleRole"] == "ORIGINAL")
    if profile["id"] in {"token-request", "token-response"}:
        direction = "RESPONSE" if profile["id"] == "token-response" else "REQUEST"
        return context["direction"] == direction and context["messageFamily"] == f"STANDARD_FINANCIAL_{direction}"
    if profile["id"] == "token-carry-forward":
        return context["direction"] == "REQUEST" and context["messageFamily"] == "STANDARD_FINANCIAL_REQUEST"
    if profile["id"] == "device022":
        return context["direction"] == "REQUEST" and context["messageFamily"] == "STANDARD_FINANCIAL_REQUEST"
    if profile["id"] == "discover-india056":
        return (context["network"] == "DISCOVER" and context.get("issuerCountry") == "INDIA"
                and context.get("paymentType") in {"RECURRING", "INSTALLMENT"}
                and context["direction"] == "REQUEST" and context["messageFamily"] == "STANDARD_FINANCIAL_REQUEST")
    if profile["id"] == "ia-table063":
        return (context["network"] in {"VISA", "MASTERCARD", "AMEX"}
                and context.get("industryUsage") in {"LODGING", "INCREMENTAL_AUTH", "EV"}
                and context["direction"] == "REQUEST" and context["messageFamily"] == "STANDARD_FINANCIAL_REQUEST")
    return (context["direction"] == "REQUEST" and context["messageFamily"] == "STANDARD_FINANCIAL_REQUEST"
            and context["network"] in {"DISCOVER", "VISA", "MASTERCARD", "AMEX"}
            and context["transactionCategory"] == "AUTHORIZATION"
            and context["flowId"] == "INCREMENTAL_AUTHORIZATION")


def token_tables(observation: dict, version: str, response: bool) -> dict:
    expected_segment = "154" if response else "153"
    if observation.get("segmentType") != expected_segment:
        return result("FAIL", f"Expected Segment {expected_segment}")
    tables = observation.get("tables")
    if not isinstance(tables, list):
        return result("NOT_ASSESSED", "Actual nonempty token tables are required")
    if not tables:
        return result("FAIL", "Required token data section is observed empty")
    findings = []
    encoded_length = 0
    allowed = {f"{i:03}" for i in range(1, 6 if response else 8 if version == "2026-4" else 7)}
    for table in tables:
        if not isinstance(table, dict):
            return result("FAIL", "Token table must be an object")
        tag, length, value = (table.get(key) for key in ("id", "length", "value"))
        if not isinstance(tag, str) or tag not in allowed:
            findings.append(result("NOT_ASSESSED", "Unpublished/unsupported token table", tableId=tag))
            continue
        if not digits(length, 3) or not isinstance(value, str):
            findings.append(result("FAIL", "N3 length and actual text value required", tableId=tag))
            continue
        if not value.isascii() or not value.isprintable() or int(length) != len(value):
            findings.append(result("FAIL", "Declared byte length/ASCII value mismatch", tableId=tag))
            continue
        encoded_length += 6 + len(value)
        valid = True
        if tag == "001":
            valid = 1 <= len(value) <= 19 if version == "2026-4" else 13 <= len(value) <= 18
            if version == "2026-4" and not response:
                valid = valid and re.fullmatch(r"[0-9]+", value) is not None
        else:
            valid = len(value) == (4 if tag == "002" and not response else 1)
        if version == "2026-4":
            if response and tag != "001":
                valid = valid and value in {"T", "P"}
            elif not response and tag == "002":
                valid = valid and digits(value, 4) and 1 <= int(value[2:]) <= 12
            elif not response and tag == "004":
                valid = valid and value in {"Y", "N"}
            elif not response and tag == "007":
                valid = valid and value == "Y"
        findings.append(result("PASS" if valid else "FAIL", "Source table representation", tableId=tag))
    if encoded_length > 999:
        findings.append(result("FAIL", "Network Token Data exceeds its 999-byte maximum"))
    # No duplicate/order/mandatory-all-tags policy is inferred from the layout examples.
    state = "FAIL" if any(row["status"] == "FAIL" for row in findings) else (
        "NOT_ASSESSED" if any(row["status"] == "NOT_ASSESSED" for row in findings) else "PASS")
    return result(state, "Scoped token table checks; eligibility and service context not certified", findings=findings)


def token_wire(observation: dict, version: str, response: bool) -> dict:
    if version == "2026-3":
        return result("NOT_ASSESSED", "2026-3 Segment 153 separator placement is not resolved by this profile")
    field = token_tables(observation, version, response)
    if field["status"] != "PASS":
        return result("NOT_ASSESSED", "Valid scoped token fields required before wire comparison")
    wire = observation.get("wireHex")
    declared = observation.get("segmentLength")
    if wire is None or declared is None:
        return result("NOT_ASSESSED", "Actual wireHex and declared Segment Length required")
    if not digits(declared, 3):
        return result("FAIL", "Segment Length must be N3")
    if not isinstance(wire, str) or re.fullmatch(r"(?:[0-9a-fA-F]{2})+", wire) is None:
        return result("FAIL", "Lossless hexadecimal wire bytes required")
    body = "".join(table["id"] + table["length"] + table["value"] for table in observation["tables"])
    prefix = observation["segmentType"] + ("" if response else "\x1c")
    expected = (prefix + declared + body).encode("ascii")
    actual = bytes.fromhex(wire)
    valid = (len(body) <= 999 and len(expected) <= 999 and int(declared) == len(expected)
             and actual == expected)
    return result("PASS" if valid else "FAIL", "Local single-segment byte comparison; not full-message or capture provenance",
                  measuredBytes=len(actual), independentlyExpectedHex=expected.hex())


def carry_forward(observation: dict) -> dict:
    if (observation.get("provisionedNetworkTokens") is not True
            or observation.get("authOptimization") is not True):
        return result("NOT_ASSESSED", "Both service entitlements must be independently observed true")
    original, steps = observation.get("original"), observation.get("followups")
    if not isinstance(original, dict) or not original or not isinstance(steps, list) or not steps:
        return result("NOT_ASSESSED", "Actual original information and follow-up observations required")
    supported = {"REVERSAL", "COMPLETION", "CANCELLATION", "VOID_RETURN", "TIMEOUT_REVERSAL"}
    if any(not isinstance(step, dict) or step.get("role") not in supported for step in steps):
        return result("NOT_ASSESSED", "Unsupported follow-up role")
    if any(not isinstance(step.get("originalInformation"), dict) for step in steps):
        return result("NOT_ASSESSED", "Follow-up original-information observation missing")
    valid = all(identity(step["originalInformation"]) == identity(original) for step in steps)
    return result("PASS" if valid else "FAIL", "Observed original-information carry-forward only; completeness/authenticity not certified")


def ia_lifecycle(observation: dict) -> dict:
    steps = observation.get("steps")
    if not isinstance(steps, list) or not steps:
        return result("NOT_ASSESSED", "Ordered actual lifecycle observations required")
    original = None
    closed = False
    failures, missing = [], []
    for step in steps:
        if not isinstance(step, dict) or step.get("role") not in {
                "ORIGINAL_AUTH", "INCREMENTAL_AUTH", "IA_REVERSAL", "IA_COMPLETION"}:
            missing.append("Unsupported or malformed IA leg")
            continue
        if step["role"] == "ORIGINAL_AUTH":
            if original is not None:
                failures.append("Second Original Auth requires a separate flow")
                continue
            original = step
            terminal, transaction = observation.get("terminalId"), step.get("transactionId")
            history = observation.get("openTransactions")
            if (not text(terminal) or not text(transaction) or not isinstance(history, list)
                    or observation.get("historyComplete") is not True):
                missing.append("Terminal, transaction identity and complete open history required")
            elif any(not isinstance(row, dict) or not text(row.get("terminalId"))
                     or not text(row.get("transactionId")) for row in history):
                missing.append("Incomplete open history entry")
            elif any(row["terminalId"] == terminal and row["transactionId"] == transaction for row in history):
                failures.append("Duplicate open purchase identity at this terminal")
            continue
        if original is None:
            failures.append("Subsequent IA leg has no Original Auth")
        else:
            if closed:
                failures.append("Subsequent IA leg after completion")
            if original.get("approved") is False:
                failures.append("Original Auth was not approved")
            elif original.get("approved") is not True:
                missing.append("Original approval observation missing")
            if not text(original.get("approvalCodeDE39")) or not text(step.get("approvalCodeDE39")):
                missing.append("Original and subsequent AF DE39 observations required; wire mapping unresolved")
            elif original["approvalCodeDE39"] != step["approvalCodeDE39"]:
                failures.append("Subsequent approval reference differs from Original Auth")
            if not text(original.get("transactionId")) or not text(step.get("transactionId")):
                missing.append("Original/subsequent AF identity observation missing")
            elif original["transactionId"] != step["transactionId"]:
                missing.append("Different AF identity requires separately observed flow association")
        if step["role"] == "IA_COMPLETION":
            closed = True
    if original is None and not failures:
        missing.append("No Original Auth")
    return result("FAIL" if failures else "NOT_ASSESSED" if missing else "PASS",
                  "Logical AF observations, not a mapping of AF identity/DE39 to ATL105 wire fields",
                  contradictions=failures, missingEvidence=missing)


def discover_india(observation: dict, profile: dict) -> dict:
    tag = observation.get("subtableId")
    if not isinstance(tag, str) or tag not in profile["claim"]["roles"]:
        return result("NOT_ASSESSED", "Only Discover India subtables 01-07 implemented")
    role = observation.get("paymentRole")
    if role not in {"INITIAL", "SUBSEQUENT"} or not isinstance(observation.get("present"), bool):
        return result("NOT_ASSESSED", "Actual payment role and presence required")
    if observation["present"] is False:
        return result("NOT_ASSESSED", "Source recommends sending; absence is not promoted to a hard rejection")
    if role not in profile["claim"]["roles"][tag]:
        return result("FAIL", "Supplied Discover subtable is outside its source initial/subsequent role")
    value, length = observation.get("value"), observation.get("length")
    width = {"01": 2, "02": 1, "03": 12, "04": 14, "05": 2, "06": 1, "07": 20}[tag]
    valid = (digits(length, 2) and int(length) == width and isinstance(value, str)
             and value.isascii() and value.isprintable() and len(value) == width)
    if valid:
        if tag == "01":
            valid = value in {"UD", "UC"} or (digits(value, 2) and 2 <= int(value) <= 99)
        elif tag == "02":
            valid = value in {"F", "V"}
        elif tag == "03":
            valid = digits(value, 12)
        elif tag == "05":
            valid = digits(value, 2) and 1 <= int(value) <= 8
        elif tag == "06":
            valid = value in {"0", "1"}
    return result("PASS" if valid else "FAIL",
                  "Scoped Discover representation/role only; no cryptogram authenticity, currency or ID uniqueness certification")


def assess(profile: dict, observation: dict, context: dict) -> dict:
    rule_id, version = profile["id"], profile["version"]
    if rule_id == "sequence":
        value = observation.get("sequenceNumber")
        return result("PASS" if digits(value, 6) and 1 <= int(value) <= 999999 else "FAIL",
                      "Six ASCII digits in 000001-999999; not lifetime uniqueness")
    if rule_id == "online-refund":
        if version == "2026-4":
            return result("NOT_ASSESSED", "RETIRED_SOURCE: X and I/032/11 Reserved; no replacement host behavior inferred")
        present = observation.get("indicatorPresent")
        if not isinstance(present, bool):
            return result("NOT_ASSESSED", "Actual indicator presence required")
        if any(not isinstance(observation.get(key), bool) for key in ("merchantParticipating", "onlineApprovalWanted")):
            return result("NOT_ASSESSED", "Merchant participation and approval intent required")
        applicable = observation["merchantParticipating"] and observation["onlineApprovalWanted"]
        valid = present == applicable and (not present or (
            observation.get("tableId") == "032" and observation.get("subtableId") == "11"
            and observation.get("length") == "001" and observation.get("value") == "Y"))
        return result("PASS" if valid else "FAIL", "2026-3 original return indicator only; lifecycle/order not assessed")
    if rule_id in {"token-request", "token-response"}:
        return token_tables(observation, version, rule_id == "token-response")
    if rule_id == "token-carry-forward":
        return carry_forward(observation)
    if rule_id == "ia-lifecycle":
        return ia_lifecycle(observation)
    if rule_id == "device022":
        value = observation.get("deviceType")
        if not digits(value, 2):
            return result("FAIL", "Observed device indicator must be two ASCII digits")
        if value in profile["claim"]["values"]:
            return result("PASS", "New source-defined device interface value", meaning=profile["claim"]["values"][value])
        return result("NOT_ASSESSED", "Reserved future values may occur without prior notice; older values outside this extension profile")
    if rule_id == "discover-india056":
        return discover_india(observation, profile)
    if rule_id == "ia-table063":
        if not isinstance(observation.get("present"), bool):
            return result("NOT_ASSESSED", "Actual Table063 terminal-number presence required")
        if not observation["present"]:
            return result("FAIL", "Terminal Transaction Number is mandatory in the scoped lodging/IA/EV context")
        number = observation.get("terminalTransactionNumber")
        if not digits(number, 4):
            return result("FAIL", "Table063 Terminal Transaction Number uses N4 representation")
        if observation.get("role") == "ORIGINAL":
            return result("PASS", "Original terminal-number representation only; uniqueness not certified")
        if observation.get("role") != "SUBSEQUENT" or not digits(observation.get("originalTerminalTransactionNumber"), 4):
            return result("NOT_ASSESSED", "Observed original/subsequent binding required")
        same = number == observation["originalTerminalTransactionNumber"]
        return result("PASS" if same else "NOT_ASSESSED",
                      "Source says terminal number should remain the same; mismatch requires review, not an invented decline")
    if rule_id == "ia-table82":
        present = observation.get("present")
        if not isinstance(present, bool):
            return result("NOT_ASSESSED", "Actual table presence required")
        if not present:
            return result("FAIL" if context["network"] == "DISCOVER" else "NOT_ASSESSED",
                          "Discover mandatory; absence for other named networks is recommendation-only")
        valid = (observation.get("indicator") == "082" and observation.get("tableId") == "82"
                 and observation.get("length") == "004" and digits(observation.get("terminalTransactionNumber"), 4))
        return result("PASS" if valid else "FAIL", "Table 82 logical layout only; nested wire/identity mapping not certified")
    raise ValueError(f"Independent profile lacks an executor: {rule_id}")


def validate(document: dict, root: Path = PACK) -> tuple[dict, dict]:
    if (not isinstance(document, dict) or type(document.get("schemaVersion")) is not int
            or document["schemaVersion"] != 2 or document.get("format") != "BA_BUSINESS_CONTEXT_REVIEW_V2"):
        raise ValueError("Unsupported new-format schema/version")
    manifest = document.get("manifest")
    if (not isinstance(manifest, dict) or manifest.get("specification") != "ATL105"
            or manifest.get("specificationVersion") not in VERSIONS
            or not text(manifest.get("packageId"))
            or manifest.get("producer") not in {"INDEPENDENT_TEST_SOLUTION", "AI_SOLUTION"}
            or manifest.get("strictExecutionContract") is not False):
        raise ValueError("Invalid manifest, unsupported release or execution promotion")
    version = manifest["specificationVersion"]
    catalog = profiles(version, root)
    indices, seen = {}, set()
    for kind in KINDS:
        rows = document.get(kind)
        if not isinstance(rows, list) or not rows:
            raise ValueError(f"Nonempty {kind} array required")
        index = {}
        for row in rows:
            if not isinstance(row, dict) or not text(row.get("id")) or row["id"] in seen:
                raise ValueError(f"Invalid or duplicate cross-kind ID in {kind}")
            identifier = row["id"]
            seen.add(identifier)
            index[identifier] = row
            validate_context(row.get("businessContext"), version)
            anchors = row.get("sourceAnchors")
            if (not isinstance(anchors, list) or not anchors or any(
                    not isinstance(anchor, dict) or anchor.get("specification") != "ATL105"
                    or anchor.get("version") != version or not text(anchor.get("rule")) for anchor in anchors)
                    or len({identity(anchor) for anchor in anchors}) != len(anchors)):
                raise ValueError(f"Invalid/mixed-release source anchors: {identifier}")
            if not text(row.get("status")):
                raise ValueError(f"Producer status must be retained: {identifier}")
        indices[kind] = index
    brs, scenarios, cases, data = (indices[key] for key in KINDS)
    used = {kind: set() for kind in KINDS[:3]}

    def links(row: dict, key: str, target: dict) -> set[str]:
        values = row.get(key)
        if (not isinstance(values, list) or not values or any(not text(value) for value in values)
                or len(set(values)) != len(values) or set(values) - target.keys()):
            raise ValueError(f"Invalid {key}: {row['id']}")
        for identifier in values:
            if row["businessContext"] != target[identifier]["businessContext"]:
                raise ValueError(f"Cross-edge business context mismatch: {row['id']}")
        return set(values)

    def union_anchors(rows: list[dict]) -> set[str]:
        return {identity(anchor) for row in rows for anchor in row["sourceAnchors"]}

    for row in brs.values():
        if not text(row.get("statement")) or not isinstance(row.get("claim"), dict):
            raise ValueError(f"BR statement and structured claim required: {row['id']}")
    for row in scenarios.values():
        selected = links(row, "requirementIds", brs)
        used["businessRequirements"].update(selected)
        if (not text(row.get("intent")) or not isinstance(row.get("preconditions"), list)
                or not row["preconditions"] or any(not text(item) for item in row["preconditions"])):
            raise ValueError(f"Scenario intent and preconditions required: {row['id']}")
        if union_anchors([row]) != union_anchors([brs[key] for key in selected]):
            raise ValueError(f"Scenario source discontinuity: {row['id']}")
    for row in cases.values():
        selected = links(row, "scenarioIds", scenarios)
        used["testScenarios"].update(selected)
        assertions = row.get("assertions")
        required_brs = {key for scenario in selected for key in scenarios[scenario]["requirementIds"]}
        if (not isinstance(assertions, list) or not assertions
                or any(not isinstance(item, dict) or not text(item.get("requirementId")) for item in assertions)
                or len({item["requirementId"] for item in assertions}) != len(assertions)
                or {item["requirementId"] for item in assertions} != required_brs):
            raise ValueError(f"Exactly one assertion per linked BR required: {row['id']}")
        if (not isinstance(row.get("steps"), list) or not row["steps"]
                or any(not text(step) for step in row["steps"])):
            raise ValueError(f"Concrete TC steps required: {row['id']}")
        for assertion in assertions:
            if (not text(assertion.get("rule")) or assertion.get("expectedResult") not in OUTCOMES
                    or not text(assertion.get("observationId"))
                    or ("expectedWireResult" in assertion and assertion["expectedWireResult"] not in OUTCOMES)):
                raise ValueError(f"Rule, observationId and expectedResult required: {row['id']}")
        if union_anchors([row]) != union_anchors([scenarios[key] for key in selected]):
            raise ValueError(f"TC source discontinuity: {row['id']}")
    observations, br_results = [], []
    for row in brs.values():
        anchors = row["sourceAnchors"]
        profile = catalog.get(anchors[0]["rule"]) if len(anchors) == 1 else None
        if profile is None or anchors != profile["sourceAnchors"]:
            match = result("NOT_ASSESSED", "No exact independently implemented source-anchor profile")
        elif not scoped(profile, row["businessContext"]):
            match = result("NOT_ASSESSED", "Context outside independently implemented scope")
        elif identity(row["claim"]) != identity(profile["claim"]):
            match = result("FAIL", "Declared structured claim contradicts independent release profile")
        else:
            match = result("PASS", "Exact supported structured claim; prose interpretation/approval not established")
        br_results.append({"requirementId": row["id"], "rule": anchors[0]["rule"], **match})
    by_br = {row["requirementId"]: row for row in br_results}
    for row in data.values():
        selected = links(row, "testCaseIds", cases)
        used["testCases"].update(selected)
        payload = row.get("payload")
        if not isinstance(payload, dict) or not isinstance(payload.get("observations"), dict):
            raise ValueError(f"TD must carry actual observations: {row['id']}")
        actuals = payload["observations"]
        needed = {assertion["observationId"] for key in selected for assertion in cases[key]["assertions"]}
        if needed - actuals.keys() or any(not isinstance(value, dict) for value in actuals.values()):
            raise ValueError(f"Missing or malformed declared observations: {row['id']}")
        if union_anchors([row]) != union_anchors([cases[key] for key in selected]):
            raise ValueError(f"TD source discontinuity: {row['id']}")
        for key in sorted(selected):
            for assertion in cases[key]["assertions"]:
                br_result = by_br[assertion["requirementId"]]
                profile = catalog.get(br_result["rule"])
                if assertion["rule"] != br_result["rule"]:
                    assessed = result("FAIL", "TC rule differs from its linked independent BR rule")
                elif br_result["status"] != "PASS":
                    assessed = result("NOT_ASSESSED", "BR semantic/context gate has not passed")
                else:
                    assessed = assess(profile, actuals[assertion["observationId"]], row["businessContext"])
                agreement = ("NOT_ASSESSED" if assessed["status"] == "NOT_ASSESSED" else
                             "PASS" if assessed["status"] == assertion["expectedResult"] else "FAIL")
                wire = (token_wire(actuals[assertion["observationId"]], version, profile["id"] == "token-response")
                        if profile is not None and profile["id"] in {"token-request", "token-response"}
                        and br_result["status"] == "PASS" else result("NOT_ASSESSED", "No supported token-wire profile"))
                wire_expected = assertion.get("expectedWireResult")
                wire_agreement = ("NOT_ASSESSED" if wire["status"] == "NOT_ASSESSED" or wire_expected is None else
                                  "PASS" if wire["status"] == wire_expected else "FAIL")
                observations.append({"testDataId": row["id"], "testCaseId": key,
                                     "requirementId": assertion["requirementId"], "rule": assertion["rule"],
                                     "observationId": assertion["observationId"],
                                     "expectedResult": assertion["expectedResult"],
                                     "assessment": assessed, "expectationAgreement": agreement,
                                     "layer": profile["layer"] if profile else "unsupported",
                                     "wire": wire, "expectedWireResult": wire_expected,
                                     "wireExpectationAgreement": wire_agreement})
    for kind in used:
        if used[kind] != indices[kind].keys():
            raise ValueError(f"Orphan artefacts in {kind}")
    semantic_failures = sum(row["status"] == "FAIL" for row in br_results)
    mismatches = sum(row["expectationAgreement"] == "FAIL" for row in observations)
    unassessed = sum(row["expectationAgreement"] == "NOT_ASSESSED" for row in observations)
    wire_failures = sum(row["wireExpectationAgreement"] == "FAIL" or
                        (row["wire"]["status"] == "FAIL" and row["expectedWireResult"] is None) for row in observations)
    pending_wire = sum(row["expectedWireResult"] is not None and row["wire"]["status"] == "NOT_ASSESSED"
                       for row in observations)
    expectation = ("FAIL" if semantic_failures or mismatches or wire_failures else
                   "NOT_ASSESSED" if unassessed or pending_wire else "PASS")
    blockers = [
        "Whole-spec atomic obligation denominator and all-profile implementation incomplete",
        "Source-derived interpretations require authorized review; producer statuses are not approval",
        "Network/service eligibility and observed-history authenticity not established",
        "Full-message framing, independent converter/capture provenance and host outcomes not supplied",
        "AF transaction-identity and DE39 wire mappings unresolved",
        "Device022/056/063 profiles are bounded extensions, not complete applicability/physical implementations",
        "Java canonical semantic evidence runner remains 2026-3-only; V2 is a scoped review-envelope path",
        "Remaining chapter/appendix source-to-code/case reconciliation and held-out AI delivery evaluation required",
    ]
    report = {
        "schemaVersion": 2, "specificationVersion": version,
        "counts": {kind: len(index) for kind, index in indices.items()},
        "structuralTraceability": "PASS", "brAssessments": br_results, "observations": observations,
        "testExpectationAgreement": expectation, "mismatchedExpectationCount": mismatches,
        "semanticContradictionCount": semantic_failures, "wireFailureCount": wire_failures,
        "assessedAssertionCount": len(observations) - unassessed, "unassessedAssertionCount": unassessed,
        "supportedRuleProfiles": sorted(catalog), "semanticProseEquivalence": "NOT_ASSESSED",
        "networkEligibility": "REVIEW_REQUIRED", "completeMessageAndWire": "NOT_ASSESSED",
        "hostOutcome": "NOT_ASSESSED", "coveragePromotion": False, "certified": False,
        "overallStatus": "REVIEW_REQUIRED", "independentProfiles": catalog,
        "readiness": {"status": "BLOCKED", "completeValidationReady": False,
                      "supportedProfileOfflineAssessment": expectation, "blockers": blockers,
                      "wholeSpecCoveragePercent": None},
        "unconsumedObservationIds": {row["id"]: sorted(set(row["payload"]["observations"]) - {
            assertion["observationId"] for key in row["testCaseIds"] for assertion in cases[key]["assertions"]})
            for row in data.values()},
    }
    normalized = copy.deepcopy(document)
    normalized["adapter"] = {
        "name": "BA_BUSINESS_CONTEXT_REVIEW_V2", "canonicalExecutionReady": False,
        "producerStatusesAreClaims": True, "inputCounts": report["counts"],
        "policy": "All producer records/fields retained; no status promotion or invented semantic mapping",
    }
    return report, normalized


def outputs(document: dict, input_hash: str) -> dict[str, str]:
    report, normalized = validate(document)
    report["inputSha256"] = input_hash
    report["validatorSha256"] = shared.fingerprint(Path(__file__).read_bytes())
    report["helperHashes"] = {
        HELPER_PATH.name: shared.fingerprint(HELPER_PATH.read_bytes()),
        network.HELPER_PATH.name: shared.fingerprint(network.HELPER_PATH.read_bytes()),
    }
    return {"validation-report.json": shared.json_text(report),
            "normalized-review-envelope.json": shared.json_text(normalized)}


def external_outputs(document: dict, input_hash: str) -> dict[str, str]:
    files = outputs(document, input_hash)
    files["manifest.json"] = shared.json_text({
        "files": {**{name: shared.fingerprint(content.encode("utf-8")) for name, content in files.items()},
                  "input-snapshot.json": input_hash}, "excludesSelf": True,
    })
    return files


def main() -> None:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--input", type=Path)
    parser.add_argument("--output", type=Path)
    parser.add_argument("--check", action="store_true")
    args = parser.parse_args()
    if args.input:
        if args.output is None or args.check or args.output.exists():
            parser.error("--input requires a new --output directory; --check is only for generated evidence")
        raw = args.input.read_bytes()
        document = load(args.input)
        files = external_outputs(document, shared.fingerprint(raw))
        if args.input.read_bytes() != raw:
            raise ValueError("Input changed during validation")
        network.publish(files, args.output, False)
        (args.output / "input-snapshot.json").write_bytes(raw)
        report = json.loads(files["validation-report.json"])
        print(f"Scoped assessment: {report['testExpectationAgreement']}; complete-validation readiness: BLOCKED")
        raise SystemExit({"PASS": 0, "FAIL": 1, "NOT_ASSESSED": 2}[report["testExpectationAgreement"]])
    if args.output is not None:
        parser.error("--output is only supported with --input")
    fixture_path = Path(__file__).with_name("build-new-format-release-fixtures.py")
    spec = importlib.util.spec_from_file_location("release_fixtures", fixture_path)
    assert spec is not None and spec.loader is not None
    fixtures = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(fixtures)
    network.publish(fixtures.build(), OUTPUT, args.check)


if __name__ == "__main__":
    try:
        main()
    except (KeyError, TypeError, ValueError, OSError) as error:
        print(f"Release-aware new-format validation failed: {error}", file=sys.stderr)
        raise SystemExit(1) from error
