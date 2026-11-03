"""Build the source-pinned POS Entry Mode (Segment 111 Table 005 / Appendix J) supplement.

Derives atomic Test BRs, network-scoped scenarios (7 networks + generic Debit/EBT),
positive/negative/review test cases and test data from the pinned ATL105 text.
Every BR quote must occur literally on its declared source page; every test datum is
evaluated by the reference oracle in this file and the build fails unless the actual
FAIL set equals the case's declared FAIL set. Frozen packages are never modified:
defective prior records are superseded through an explicit overlay.
"""
from __future__ import annotations

import argparse
import csv
import hashlib
import io
import json
import re
import sys
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
ATL105 = ROOT / "specifications" / "ATL105"
DEFAULT_SOURCE = ATL105 / "docs" / "specs" / "extracted_text.txt"
REVIEW_DIR = ATL105 / "test-output" / "test-solution-independent-review"
DEFAULT_COMPLETE = REVIEW_DIR / "complete-all-test-solution-br-ts-tc-td-package.json"
DEFAULT_ANNEXURE = ATL105 / "test-output" / "test-json" / "segment-100-annexure-br-baseline-package.json"
DEFAULT_OUTPUT = REVIEW_DIR / "pos-entry-mode-supplement-v1"
VERSION = "2026-3"
PINNED_SOURCE_SHA256 = {"2026-3": "6d03bf09f25b9126975b584aef83c21d1b7b081cabccfb9af3b89d46a26d06aa"}
SUPPLEMENT_ID = "ATL105-POS-ENTRY-MODE-SUPPLEMENT-V1"

PASS, FAIL, REVIEW = "PASS", "FAIL", "REVIEW_REQUIRED"

NETWORK_GROUPS = ["AMEX", "VISA", "MASTERCARD", "DISCOVER", "DINERS_CLUB", "JCB", "STAR_SIGNATURE_DEBIT"]
GENERIC_GROUPS = ["DEBIT", "EBT"]
GROUP_PROGRAM = {"AMEX": "CREDIT", "VISA": "CREDIT", "MASTERCARD": "CREDIT", "DISCOVER": "CREDIT",
                 "DINERS_CLUB": "CREDIT", "JCB": "CREDIT", "STAR_SIGNATURE_DEBIT": "DEBIT",
                 "DEBIT": "DEBIT", "EBT": "BENEFIT"}
COF_LISTED_CREDIT = {"VISA", "MASTERCARD", "DISCOVER"}
TOKEN_GROUPS = ["AMEX", "VISA", "MASTERCARD", "DISCOVER"]
CODE_TABLE_CONTEXT = "CODE_TABLE"
GIFT_CONTEXT = "GIFT_CARD_PROGRAM"

APPJ = "Appendix J. Valid Point-of-Service Entry Mode Codes"
APPI = "Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table ID 005)"
APPZ = "Appendix Z. Stored Credential"
EVIDENCE = {
    "T005-PRESENCE": (548, APPI, "Note: The POS Entry Mode field must be populated for all transactions, which include EMV, Magstripe, and Key Entered transactions from all POS devices for any given solution."),
    "T005-ID": (548, APPI, "Table ID n Fixed value: 005"),
    "E111-005": (416, "13 Element No. 111 Variable Information Indicator", "005 Point-of-service entry mode indicator"),
    "T005-LEN": (548, APPI, "Table Length n 3 Table Data n Fixed Len.: 3"),
    "APPJ-STRUCT": (631, APPJ, "A valid 3-digit Point-of-Service Entry Mode code has the following components: • 2-digit PAN Entry Mode code in position nos. 1–2 • 1-digit Terminal Capability Mode code in position no. 3"),
    "APPJ-PAN-TABLE": (631, APPJ, "Valid PAN Entry Mode Codes Pos. Nos. 1–2 Description"),
    "APPJ-CAP-TABLE": (632, APPJ, "Position 3 Description"),
    "APPJ-PERSIST": (631, APPJ, "Note: The POS Entry Mode values listed below must persist throughout the lifecycle of the transactions, except as noted below the Valid PAN Entry Mode Codes table."),
    "APPJ-FN1-AUTH": (631, APPJ, "Due to credit authorizer (American Express, Diners Club®, Discover® Network, JCB®, MasterCard®, MasterCard® Fleet, Visa®, and Visa® Fleet) and debit authorizer regulatory requirements regarding storage of full track data"),
    "APPJ-FN1-SCOPE": (631, APPJ, "previously authorized credit and debit transactions (Voids and Completions) can only pass the account number and expiration date to BUYPASS."),
    "APPJ-FN1-SAME": (631, APPJ, "These transactions will use the same PAN Entry Mode code as their corresponding original Purchase or Authorization transaction, with one exception:"),
    "APPJ-FN1-EXC": (631, APPJ, "They will use a PAN Entry Mode code 02 when referencing an original Purchase or Authorization transaction in which a PAN Entry Mode code 90 was used."),
    "APPJ-FN2-SCOPE": (631, APPJ, "Entry Mode 10 is applicable to Visa Credit, MasterCard Credit, Discover Credit, and Amex transactions where merchant or cardholder is initiating a transaction based on the credentials already stored on the file."),
    "APPJ-FN2-DEBIT": (631, APPJ, "The value is also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction."),
    "APPJ-FN2-FIRST": (631, APPJ, "The value is NOT applicable to the first transaction where credentials are being verified or stored."),
    "APPJ-FN2-DISC": (631, APPJ, "For Discover, POS Entry Mode ‘10’ must NOT be present in the following scenarios:"),
    "APPJ-FN2-QR": (631, APPJ, "QR Code/Bar Code/Payment Code transactions"),
    "APPJ-FN2-WALLET": (631, APPJ, "Card Sales with card account information stored by an Issuer or provisioned in a digital wallet"),
    "APPJ-FN3-82": (631, APPJ, "For Discover, Entry Mode 82 must NOT be assigned to card transactions for which the cardholder uses their stored card account information."),
    "APPJ-FN3-COF": (631, APPJ, "Discover Credential on File transactions must use the POS Entry Mode value of '10'."),
    "APPZ-ORIGINAL": (706, APPZ, "The original POS Entry mode (for example: 02, 90 etc. respectively as defined in Appendix J) will be sent"),
    "APPZ-ALL-10": (706, APPZ, "For all card types, POS Entry Mode value of ‘10’ must be submitted in Data Element 111, Table ID 05 - Point-of-Service Entry Mode"),
    "APPZ-SCOPE": (706, APPZ, "The use cases apply to Visa, MasterCard, Discover, and Amex."),
    "T049-SUBSEQUENT": (568, "Appendix I. Variable Information Data Layouts - Table ID 049 Sub Table ID 02", "POS Entry Mode 10 should be used for all subsequent COF transactions (when the COF indicator is ‘C’)."),
    "CAP-8": (632, APPJ, "If a Contactless MSR is attached to the POS device and it has no chip capability, then all transactions will use Terminal PIN Entry Capability Mode code 8"),
    "CAP-EMV": (632, APPJ, "For EMV Contact and Contactless the valid PIN Entry Capability Mode code is 1 and not 8. The PIN Entry Capability Mode code 8 is reserved only for Contactless MSR"),
    "CAP-1": (632, APPJ, "If no Contactless MSR is attached, and the POS device has the PIN debit feature enabled, then all transactions will use Terminal PIN Entry Capability Mode code 1"),
    "CAP-2": (632, APPJ, "If no Contactless MSR is attached, and the POS device has the PIN debit feature disabled, then all transactions will use Terminal PIN Entry Capability Mode code 2."),
    "CAP-3-MC": (632, APPJ, "PIN entry capability mode code 3 is applicable only to MasterCard Mobile POS transactions."),
    "MPOS-1": (632, APPJ, "mPOS Devices Phone/Tablet + Dongle with PIN Pad should have PIN Entry Capability Mode code as 1."),
    "MPOS-3": (632, APPJ, "mPOS Devices Phone/Tablet + Dongle without PIN Pad should have PIN Entry Capability Mode code as 3."),
    "RFID": (84, "10.1.5 Radio Frequency Identification Service", "Even if the transaction is swiped, manually keyed, or uses a card type for which RFID is not applicable, Point-of-Sale Entry Mode must be populated correctly in the transaction request from the point-of-sale when an RFID receiver is present."),
    "GIFT-BAR": (123, "10.7.2.2 Manual Entry/Bar Code", "Bar Code Scanned Gift card should be treated as a manually entered transaction."),
    "GIFT-BAR-01": (123, "10.7.2.2 Manual Entry/Bar Code", "The entry mode should be “01”."),
    "BLACKHAWK-90": (123, "10.7.2.2 Manual Entry/Bar Code", "The entry mode should be received as “90” for Blackhawk Scanned transactions."),
    "FALLBACK-AID": (159, "10.14.4 EMV Fallback Transaction Processing", "Regardless, a Fallback will occur whenever an EMV Certified solution cannot read or obtain a viable matching AID."),
    "FALLBACK-80": (159, "10.14.4 EMV Fallback Transaction Processing", "Always send the Fallback Entry Mode of ‘80’or spec equivalent"),
    "FALLBACK-90": (159, "10.14.4 EMV Fallback Transaction Processing", "Send as a MSR Entry Mode of ‘90’or spec equivalent"),
    "TOKEN": (681, "Appendix O. Payment Token Terminologies", "A value that is used to indicate the token presentment mode through which the token is presented for payment."),
}


def normalize(text: str) -> str:
    return re.sub(r"\s+", " ", text).strip()


def sha256_bytes(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def record_sha256(record) -> str:
    return sha256_bytes(json.dumps(record, sort_keys=True, separators=(",", ":"), ensure_ascii=False).encode("utf-8"))


def split_pages(text: str) -> dict[int, str]:
    parts = re.split(r"^--- PAGE (\d+) ---[ \t]*$", text, flags=re.MULTILINE)
    pages = {1: normalize(parts[0])}
    for index in range(1, len(parts), 2):
        pages[int(parts[index])] = normalize(parts[index + 1])
    return pages


def appendix_j_lines(text: str) -> list[str]:
    start = text.index("Appendix J.  Valid Point-of-Service Entry Mode Codes \nThis appendix")
    end = text.index("Appendix K.", start)
    return text[start:end].split("\n")


def parse_code_table(lines: list[str], header: str, stop: str, code_pattern: str) -> list[dict]:
    start = next(i for i, line in enumerate(lines) if line.strip() == header)
    rows: list[dict] = []
    for line in lines[start + 1:]:
        stripped = line.strip()
        if stripped.startswith(stop):
            break
        if not stripped:
            continue
        match = re.match(rf"^({code_pattern})\s+(.+)$", stripped)
        if match:
            rows.append({"code": match.group(1), "sourceRow": normalize(stripped), "parts": [match.group(2)]})
        elif rows:
            rows[-1]["parts"].append(stripped)
        else:
            raise ValueError(f"Unexpected line before first code row: {stripped!r}")
    for row in rows:
        parts = [re.sub(r"(?<=[a-z])[1-3](?=\s|$)", "", part).strip() for part in row.pop("parts")]
        description = parts[0]
        for part in parts[1:]:
            description += (" / " if part[:1].isupper() else " ") + part
        row["description"] = normalize(description)
    return rows


def parse_appendix_j(text: str) -> dict:
    lines = appendix_j_lines(text)
    pan = parse_code_table(lines, "Pos. Nos. 1–2 Description", "1Due", r"\d{2}")
    cap = parse_code_table(lines, "Position 3 Description", "1If", r"\d")
    if len(pan) != 14 or len(cap) != 6:
        raise ValueError(f"Appendix J table drift: {len(pan)} PAN rows, {len(cap)} capability rows")
    return {"pan": pan, "capability": cap}


def rule(identifier, family, title, statement, evidence, scope="ALL", questions=(), related=(), pass_assertable=True):
    return {"id": identifier, "family": family, "title": title, "statement": statement, "evidenceKeys": list(evidence),
            "networkScope": scope, "questionIds": list(questions), "relatedBaselineRuleIds": list(related),
            "passAssertable": pass_assertable}


BEHAVIOR_RULES = [
    rule("BR-POSEM-001", "FORMAT", "Table 005 is present on every transaction request",
         "Segment 111 shall carry exactly one Variable Information record for Table ID 005 (POS Entry Mode) on every transaction, including EMV, magnetic-stripe and key-entered transactions from all POS devices.",
         ["T005-PRESENCE"], questions=["Q-POSEM-010"], related=["SEG111-R-003", "SEG111-R-004"]),
    rule("BR-POSEM-002", "FORMAT", "POS Entry Mode is selected by Table ID / Element 111 value 005",
         "The POS Entry Mode record shall be identified by the three-digit Variable Information Indicator 005.",
         ["T005-ID", "E111-005"], related=["SEG111-R-003"]),
    rule("BR-POSEM-003", "FORMAT", "Table 005 declares a fixed length of 3",
         "The Table 005 record shall declare Table Length 003 for its fixed three-character data.", ["T005-LEN"], related=["SEG111-R-004"]),
    rule("BR-POSEM-004", "FORMAT", "POS Entry Mode is a 3-digit code",
         "Table 005 data shall be exactly three numeric digits: PAN Entry Mode in positions 1-2 and Terminal Capability Mode in position 3.",
         ["APPJ-STRUCT"]),
    rule("BR-POSEM-005", "FORMAT", "Positions 1-2 are a source-listed PAN Entry Mode code",
         "Positions 1-2 shall be one of the Valid PAN Entry Mode Codes listed in Appendix J.", ["APPJ-PAN-TABLE"]),
    rule("BR-POSEM-006", "FORMAT", "Position 3 is a source-listed Terminal PIN Entry Capability code",
         "Position 3 shall be one of the Valid Terminal PIN Entry Capability Mode Codes listed in Appendix J.", ["APPJ-CAP-TABLE"]),
    rule("BR-POSEM-010", "LIFECYCLE", "POS Entry Mode persists through the transaction lifecycle",
         "A follow-on transaction shall carry the original transaction's POS Entry Mode value, except where Appendix J defines an exception.",
         ["APPJ-PERSIST"], questions=["Q-POSEM-002", "Q-POSEM-003"]),
    rule("BR-POSEM-011", "LIFECYCLE", "Voids and Completions reuse the original PAN Entry Mode",
         "Voids and Completions of previously authorized credit and debit transactions shall use the same PAN Entry Mode code as the original Purchase or Authorization.",
         ["APPJ-FN1-AUTH", "APPJ-FN1-SCOPE", "APPJ-FN1-SAME"], scope="Credit authorizers and debit authorizers; EBT unconfirmed",
         questions=["Q-POSEM-004"]),
    rule("BR-POSEM-012", "LIFECYCLE", "Voids and Completions of a 90 original use 02",
         "A Void or Completion that references an original Purchase or Authorization sent with PAN Entry Mode 90 shall use PAN Entry Mode 02.",
         ["APPJ-FN1-SCOPE", "APPJ-FN1-EXC"], scope="Credit authorizers and debit authorizers; EBT unconfirmed",
         questions=["Q-POSEM-002", "Q-POSEM-004"]),
    rule("BR-POSEM-020", "COF", "Entry Mode 10 is limited to stored-credential transactions on listed brands",
         "PAN Entry Mode 10 shall be used only when a transaction is initiated from credentials already stored on file, and is source-listed for Visa Credit, MasterCard Credit, Discover Credit and Amex.",
         ["APPJ-FN2-SCOPE"], scope="VISA/MASTERCARD/DISCOVER credit and AMEX; other groups review",
         questions=["Q-POSEM-001"]),
    rule("BR-POSEM-021", "COF", "The first credential-storing transaction does not use 10",
         "The first transaction that verifies or stores credentials shall send the original POS Entry Mode, not PAN Entry Mode 10.",
         ["APPJ-FN2-FIRST", "APPZ-ORIGINAL"], scope="VISA/MASTERCARD/DISCOVER credit and AMEX"),
    rule("BR-POSEM-022", "COF", "Debit Completions/Reversals after a credit COF original use 10",
         "A subsequent Debit Completion or Debit Reversal whose original was initiated as a Credit COF transaction shall carry PAN Entry Mode 10.",
         ["APPJ-FN2-DEBIT"], scope="DEBIT; STAR Signature Debit unconfirmed", questions=["Q-POSEM-011"]),
    rule("BR-POSEM-023", "COF", "Subsequent COF transactions use 10",
         "A subsequent credential-on-file transaction (COF indicator 'C') shall send PAN Entry Mode 10.",
         ["T049-SUBSEQUENT", "APPZ-ALL-10", "APPZ-SCOPE"], scope="VISA/MASTERCARD/DISCOVER credit and AMEX; other groups review",
         questions=["Q-POSEM-001"]),
    rule("BR-POSEM-024", "DISCOVER", "Discover QR/Bar/Payment Code transactions do not use 10",
         "For Discover, PAN Entry Mode 10 shall not be present on QR Code, Bar Code or Payment Code transactions.",
         ["APPJ-FN2-DISC", "APPJ-FN2-QR"], scope="DISCOVER", questions=["Q-POSEM-012"]),
    rule("BR-POSEM-025", "DISCOVER", "Discover issuer-stored or wallet-provisioned sales do not use 10",
         "For Discover, PAN Entry Mode 10 shall not be present on card sales whose account information is stored by an issuer or provisioned in a digital wallet.",
         ["APPJ-FN2-DISC", "APPJ-FN2-WALLET"], scope="DISCOVER", questions=["Q-POSEM-012"]),
    rule("BR-POSEM-026", "DISCOVER", "Discover stored-credential transactions do not use 82",
         "For Discover, PAN Entry Mode 82 shall not be assigned when the cardholder uses stored card account information.",
         ["APPJ-FN3-82"], scope="DISCOVER"),
    rule("BR-POSEM-027", "DISCOVER", "Discover credential-on-file transactions use 10",
         "Discover credential-on-file transactions shall use PAN Entry Mode 10.", ["APPJ-FN3-COF"], scope="DISCOVER"),
    rule("BR-POSEM-030", "CAPABILITY", "Contactless MSR without chip uses capability 8",
         "When a contactless MSR is attached and the POS device has no chip capability, position 3 shall be 8.", ["CAP-8"]),
    rule("BR-POSEM-031", "CAPABILITY", "Chip-capable (EMV) devices never use capability 8",
         "An EMV contact/contactless capable device shall not send position 3 value 8; 8 is reserved for contactless MSR only.", ["CAP-EMV"]),
    rule("BR-POSEM-032", "CAPABILITY", "PIN-debit-enabled device without contactless MSR uses capability 1",
         "When no contactless MSR is attached and the PIN debit feature is enabled, position 3 shall be 1.", ["CAP-1"]),
    rule("BR-POSEM-033", "CAPABILITY", "PIN-debit-disabled device without contactless MSR uses capability 2",
         "When no contactless MSR is attached and the PIN debit feature is disabled, position 3 shall be 2.", ["CAP-2"]),
    rule("BR-POSEM-034", "CAPABILITY", "Capability 3 is limited to MasterCard Mobile POS",
         "Position 3 value 3 shall be used only on MasterCard Mobile POS transactions.", ["CAP-3-MC"],
         scope="MASTERCARD; named non-MasterCard networks reject; generic groups review", questions=["Q-POSEM-006"]),
    rule("BR-POSEM-035", "CAPABILITY", "mPOS with a PIN pad uses capability 1",
         "A phone/tablet mPOS device with a dongle that has a PIN pad shall send position 3 value 1.", ["MPOS-1"]),
    rule("BR-POSEM-036", "CAPABILITY", "mPOS without a PIN pad uses capability 3",
         "A phone/tablet mPOS device with a dongle that has no PIN pad shall send position 3 value 3.", ["MPOS-3"],
         scope="MASTERCARD; other groups review", questions=["Q-POSEM-005"]),
    rule("BR-POSEM-040", "RFID", "An attached RFID receiver requires a correct POS Entry Mode on every transaction",
         "When an RFID receiver is present, every transaction request (including swiped, keyed and non-RFID card types) shall populate POS Entry Mode with the value that matches the actual entry method.",
         ["RFID"]),
    rule("BR-POSEM-041", "GIFT", "Bar-code-scanned gift cards use PAN Entry Mode 01",
         "A bar-code-scanned gift card transaction shall be sent as a manual transaction with PAN Entry Mode 01.",
         ["GIFT-BAR", "GIFT-BAR-01"], scope="Gift card program context"),
    rule("BR-POSEM-042", "GIFT", "Blackhawk scanned transactions use PAN Entry Mode 90",
         "A Blackhawk scanned gift card transaction shall be treated as swiped and carry PAN Entry Mode 90.",
         ["BLACKHAWK-90"], scope="Gift card program context"),
    rule("BR-POSEM-043", "FALLBACK", "EMV fallback swipe uses PAN Entry Mode 80",
         "When the device is EMV enabled, the card is a chip card and the card is swiped, the original request shall carry PAN Entry Mode 80.",
         ["FALLBACK-AID", "FALLBACK-80"], questions=["Q-POSEM-009"], related=["SEG130-R-019"]),
    rule("BR-POSEM-044", "FALLBACK", "Non-chip or non-chip-capable swipe uses PAN Entry Mode 90",
         "When the card is not a chip card or the device is not chip capable and the card is swiped, the original request shall carry PAN Entry Mode 90.",
         ["FALLBACK-90"], questions=["Q-POSEM-009"], related=["SEG130-R-019"]),
    rule("BR-POSEM-045", "TOKEN", "Tokenized transactions carry a POS Entry Mode for token presentment",
         "A tokenized transaction shall carry a valid Appendix J POS Entry Mode indicating the token presentment mode; which codes represent token presentment is not source-defined.",
         ["TOKEN"], scope="AMEX/VISA/MASTERCARD/DISCOVER", questions=["Q-POSEM-007"], pass_assertable=False),
]


def value_rules(tables: dict) -> list[dict]:
    rules = []
    for kind, key, prefix, field in (("pan", "PAN", "positions 1-2", "PAN Entry Mode"),
                                     ("capability", "CAP", "position 3", "Terminal PIN Entry Capability Mode")):
        page = 631 if kind == "pan" else 632
        for row in tables[kind]:
            identifier = f"BR-POSEM-{key}-{row['code']}"
            rules.append({
                "id": identifier, "family": "CODE_VALUES",
                "title": f"{field} {row['code']} means {row['description']}",
                "statement": f"{field} value {row['code']} in {prefix} shall be sent only when the entry method/terminal capability is: {row['description']}.",
                "evidence": [{"page": page, "section": APPJ, "quote": row["sourceRow"]}],
                "networkScope": "ALL", "questionIds": ["Q-POSEM-008"] if row["code"] == "04" and kind == "pan" else [],
                "relatedBaselineRuleIds": [], "passAssertable": True, "codeValue": row["code"],
                "sourceDescription": row["description"]})
    return rules


def find_segment111(node):
    if isinstance(node, dict):
        if isinstance(node.get("segment111"), dict):
            return node["segment111"]
        if node.get("segmentType") == "111":
            return node
        for value in node.values():
            found = find_segment111(value)
            if found is not None:
                return found
    elif isinstance(node, list):
        for value in node:
            found = find_segment111(value)
            if found is not None:
                return found
    return None


def evaluate(payload: dict, tables: dict) -> dict[str, str]:
    """Reference oracle: returns a verdict for every BR whose trigger the payload exercises."""
    pan_desc = {row["code"]: row["description"] for row in tables["pan"]}
    cap_desc = {row["code"]: row["description"] for row in tables["capability"]}
    ctx = payload.get("context") or {}
    device, card = ctx.get("device"), ctx.get("card") or {}
    group, network, program = ctx.get("groupId"), ctx.get("network"), ctx.get("cardProgram")
    step = ctx.get("lifecycleStep", "ORIGINAL")
    verdicts: dict[str, str] = {}
    segment = find_segment111(payload.get("request") or {}) or {}
    records = [s for s in segment.get("variableInformationSections") or []
               if str(s.get("variableInformationIndicator", "")).lstrip("0") == "5"]
    if not records:
        verdicts["BR-POSEM-001"] = FAIL
        if device and device.get("rfidReceiverPresent"):
            verdicts["BR-POSEM-040"] = FAIL
        if ctx.get("tokenized"):
            verdicts["BR-POSEM-045"] = FAIL
        return verdicts
    verdicts["BR-POSEM-001"] = PASS if len(records) == 1 else REVIEW
    record = records[0]
    verdicts["BR-POSEM-002"] = PASS if record.get("variableInformationIndicator") == "005" else FAIL
    verdicts["BR-POSEM-003"] = PASS if record.get("variableInformationLength") == "003" else FAIL
    mode = record.get("variableInformation")
    if not (isinstance(mode, str) and re.fullmatch(r"[0-9]{3}", mode)):
        verdicts["BR-POSEM-004"] = FAIL
        if ctx.get("tokenized"):
            verdicts["BR-POSEM-045"] = FAIL
        return verdicts
    verdicts["BR-POSEM-004"] = PASS
    pan, cap = mode[:2], mode[2]
    verdicts["BR-POSEM-005"] = PASS if pan in pan_desc else FAIL
    verdicts["BR-POSEM-006"] = PASS if cap in cap_desc else FAIL
    if FAIL in (verdicts["BR-POSEM-005"], verdicts["BR-POSEM-006"]):
        if ctx.get("tokenized"):
            verdicts["BR-POSEM-045"] = FAIL
        return verdicts
    if ctx.get("tokenized"):
        verdicts["BR-POSEM-045"] = REVIEW
    declared_pan = ctx.get("declaredPanEntryMethod")
    if declared_pan is not None:
        verdicts[f"BR-POSEM-PAN-{pan}"] = PASS if pan_desc[pan] == declared_pan else FAIL
    if ctx.get("declaredTerminalCapability") is not None:
        verdicts[f"BR-POSEM-CAP-{cap}"] = PASS if cap_desc[cap] == ctx["declaredTerminalCapability"] else FAIL

    ebt = group == "EBT"
    original = (payload.get("original") or {}).get("posEntryMode")
    debit_follow_on = step in ("DEBIT_COMPLETION", "DEBIT_REVERSAL") and ctx.get("originalWasCreditCof")
    if debit_follow_on:
        verdicts["BR-POSEM-022"] = (PASS if pan == "10" else FAIL) if group == "DEBIT" else REVIEW
    elif step != "ORIGINAL" and isinstance(original, str) and re.fullmatch(r"[0-9]{3}", original):
        original_pan, original_cap = original[:2], original[2]
        if original_pan == "90" and step in ("VOID", "COMPLETION"):
            verdicts["BR-POSEM-012"] = REVIEW if ebt else (PASS if pan == "02" else FAIL)
        elif original_pan == "90" and step == "REVERSAL" and pan == "02":
            verdicts["BR-POSEM-010"] = REVIEW
        else:
            if step in ("VOID", "COMPLETION"):
                verdicts["BR-POSEM-011"] = REVIEW if ebt else (PASS if pan == original_pan else FAIL)
            verdicts["BR-POSEM-010"] = FAIL if pan != original_pan else (REVIEW if cap != original_cap else PASS)

    cof = ctx.get("credentialOnFile", "NONE")
    listed = (network in COF_LISTED_CREDIT and program == "CREDIT") or network == "AMEX"
    if not debit_follow_on:
        if pan == "10":
            if cof == "INITIAL":
                verdicts["BR-POSEM-021"] = FAIL
            elif cof == "SUBSEQUENT":
                verdicts["BR-POSEM-020"] = PASS if listed else REVIEW
            else:
                verdicts["BR-POSEM-020"] = FAIL
        elif cof == "INITIAL":
            verdicts["BR-POSEM-021"] = PASS
        if cof == "SUBSEQUENT":
            verdicts["BR-POSEM-023"] = (PASS if pan == "10" else FAIL) if listed else REVIEW
    if network == "DISCOVER":
        if ctx.get("discoverQrBarPaymentCode"):
            verdicts["BR-POSEM-024"] = FAIL if pan == "10" else PASS
        if ctx.get("issuerStoredOrWallet"):
            verdicts["BR-POSEM-025"] = FAIL if pan == "10" else PASS
        if ctx.get("cardholderUsesStoredInfo"):
            verdicts["BR-POSEM-026"] = FAIL if pan == "82" else PASS
        if cof == "SUBSEQUENT":
            verdicts["BR-POSEM-027"] = PASS if pan == "10" else FAIL

    if device:
        mpos = device.get("mpos", "NONE")
        if cap == "3":
            verdicts["BR-POSEM-034"] = ((PASS if mpos == "SOFTWARE_PIN" else FAIL) if network == "MASTERCARD"
                                        else REVIEW if network is None else FAIL)
        if mpos == "HARDWARE_PIN_PAD":
            verdicts["BR-POSEM-035"] = PASS if cap == "1" else FAIL
        elif mpos == "SOFTWARE_PIN":
            verdicts["BR-POSEM-036"] = (PASS if cap == "3" else FAIL) if network == "MASTERCARD" else REVIEW
        elif device.get("contactlessMsrAttached") and not device.get("chipCapable"):
            verdicts["BR-POSEM-030"] = PASS if cap == "8" else FAIL
        else:
            if device.get("chipCapable"):
                verdicts["BR-POSEM-031"] = FAIL if cap == "8" else PASS
            if not device.get("contactlessMsrAttached"):
                key = "BR-POSEM-032" if device.get("pinDebitEnabled") else "BR-POSEM-033"
                verdicts[key] = PASS if cap == ("1" if device.get("pinDebitEnabled") else "2") else FAIL
        if device.get("rfidReceiverPresent"):
            verdicts["BR-POSEM-040"] = PASS if declared_pan is None or pan_desc[pan] == declared_pan else FAIL
        if step == "ORIGINAL" and ctx.get("presentment") == "SWIPE":
            if device.get("emvEnabled") and card.get("chip"):
                verdicts["BR-POSEM-043"] = PASS if pan == "80" else FAIL
            elif not card.get("chip") or not device.get("chipCapable"):
                verdicts["BR-POSEM-044"] = PASS if pan == "90" else FAIL
    gift = ctx.get("giftProgram")
    if gift == "BAR_CODE_SCANNED_GIFT":
        verdicts["BR-POSEM-041"] = PASS if pan == "01" else FAIL
    elif gift == "BLACKHAWK_SCANNED":
        verdicts["BR-POSEM-042"] = PASS if pan == "90" else FAIL
    return verdicts


def device(chip=False, cmsr=False, pin=False, mpos="NONE", emv=False, rfid=False) -> dict:
    return {"chipCapable": chip, "contactlessMsrAttached": cmsr, "pinDebitEnabled": pin, "mpos": mpos,
            "emvEnabled": emv, "rfidReceiverPresent": rfid}


def td_payload(group, mode, *, indicator="005", length="003", absent=False, original=None, network="AUTO",
               program=None, **ctx) -> dict:
    sections = [] if absent else [{"variableInformationIndicator": indicator, "variableInformationLength": length,
                                   "variableInformation": mode}]
    request = {"specificationVersion": VERSION, "dataSection2": {
        "segment111": {"segmentType": "111", "variableInformationSections": sections}}}
    if ctx.get("tokenized"):
        request["dataSection2"]["standardSegment"] = {"accountNumber": "5555555555554444"}
    context = {"groupId": group,
               "network": (group if group in NETWORK_GROUPS else None) if network == "AUTO" else network,
               "cardProgram": program or GROUP_PROGRAM.get(group), "lifecycleStep": "ORIGINAL", "credentialOnFile": "NONE"}
    context.update({k: v for k, v in ctx.items() if v is not None})
    payload = {"request": request, "context": context}
    if original is not None:
        payload["original"] = {"posEntryMode": original}
    return payload


def case_specs(group: str, tables: dict) -> list[tuple]:
    """(family, title, expectedVerdicts, payload) tuples for one network/generic/context group."""
    specs = []
    add = lambda family, title, expected, payload: specs.append((family, title, expected, payload))
    if group == CODE_TABLE_CONTEXT:
        for kind, key, field in (("pan", "PAN", "declaredPanEntryMethod"), ("capability", "CAP", "declaredTerminalCapability")):
            rows = tables[kind]
            for index, row in enumerate(rows):
                code, other = row["code"], rows[(index + 1) % len(rows)]
                mode = code + "0" if kind == "pan" else "05" + code
                cof = "SUBSEQUENT" if kind == "pan" and code == "10" else None
                extra = {"BR-POSEM-020": PASS, "BR-POSEM-023": PASS} if cof else {}
                add("CODE_TABLE", f"{key} {code} sent for its source meaning",
                    {f"BR-POSEM-{key}-{code}": PASS, **extra},
                    td_payload(group, mode, network="VISA", program="CREDIT", credentialOnFile=cof, **{field: row["description"]}))
                add("CODE_TABLE", f"{key} {code} sent for a different entry method ({other['description']})",
                    {f"BR-POSEM-{key}-{code}": FAIL, **extra},
                    td_payload(group, mode, network="VISA", program="CREDIT", credentialOnFile=cof, **{field: other["description"]}))
        return specs
    if group == GIFT_CONTEXT:
        gift = dict(network=None, program="GIFT")
        add("GIFT", "Bar-code-scanned gift card sent as manual 01", {"BR-POSEM-041": PASS},
            td_payload(group, "011", giftProgram="BAR_CODE_SCANNED_GIFT", **gift))
        add("GIFT", "Bar-code-scanned gift card sent as bar code 04", {"BR-POSEM-041": FAIL},
            td_payload(group, "041", giftProgram="BAR_CODE_SCANNED_GIFT", **gift))
        add("GIFT", "Blackhawk scanned gift card sent as 90", {"BR-POSEM-042": PASS},
            td_payload(group, "901", giftProgram="BLACKHAWK_SCANNED", **gift))
        add("GIFT", "Blackhawk scanned gift card sent as manual 01", {"BR-POSEM-042": FAIL},
            td_payload(group, "011", giftProgram="BLACKHAWK_SCANNED", **gift))
        return specs

    ebt = group == "EBT"
    network = group if group in NETWORK_GROUPS else None
    listed = (network in COF_LISTED_CREDIT and GROUP_PROGRAM[group] == "CREDIT") or network == "AMEX"
    fmt = {f"BR-POSEM-00{i}": PASS for i in range(1, 7)}
    add("FORMAT", "Valid Table 005 with 3-digit code 051", fmt, td_payload(group, "051"))
    add("FORMAT", "Table 005 absent from Segment 111", {"BR-POSEM-001": FAIL}, td_payload(group, None, absent=True))
    add("FORMAT", "Indicator sent as 05 instead of 005", {"BR-POSEM-002": FAIL, "BR-POSEM-003": PASS}, td_payload(group, "051", indicator="05"))
    add("FORMAT", "Table length sent as 002", {"BR-POSEM-002": PASS, "BR-POSEM-003": FAIL}, td_payload(group, "051", length="002"))
    add("FORMAT", "Two-digit value 10 (defective prior token TD pattern)", {"BR-POSEM-004": FAIL}, td_payload(group, "10"))
    add("FORMAT", "Unlisted PAN Entry Mode 03", {"BR-POSEM-005": FAIL, "BR-POSEM-006": PASS}, td_payload(group, "031"))
    add("FORMAT", "Unlisted capability 9", {"BR-POSEM-005": PASS, "BR-POSEM-006": FAIL}, td_payload(group, "059"))

    same = REVIEW if ebt else PASS
    add("LIFECYCLE", "Completion reuses original 051", {"BR-POSEM-010": PASS, "BR-POSEM-011": same},
        td_payload(group, "051", original="051", lifecycleStep="COMPLETION"))
    add("LIFECYCLE", "Void of a 90 original sent as 02", {"BR-POSEM-012": REVIEW if ebt else PASS},
        td_payload(group, "021", original="901", lifecycleStep="VOID"))
    add("LIFECYCLE", "Completion changes PAN Entry Mode 05 to 07",
        {"BR-POSEM-010": FAIL, "BR-POSEM-011": REVIEW if ebt else FAIL},
        td_payload(group, "071", original="051", lifecycleStep="COMPLETION"))
    add("LIFECYCLE", "Void of a 90 original keeps 90", {"BR-POSEM-012": REVIEW if ebt else FAIL},
        td_payload(group, "901", original="901", lifecycleStep="VOID"))
    add("LIFECYCLE", "Completion keeps PAN mode but changes capability 1 to 2", {"BR-POSEM-010": REVIEW, "BR-POSEM-011": same},
        td_payload(group, "052", original="051", lifecycleStep="COMPLETION"))
    add("LIFECYCLE", "Reversal of a 90 original sent as 02", {"BR-POSEM-010": REVIEW},
        td_payload(group, "021", original="901", lifecycleStep="REVERSAL"))

    if listed:
        disc = {"BR-POSEM-027": PASS} if group == "DISCOVER" else {}
        add("COF", "Subsequent COF sends 10", {"BR-POSEM-020": PASS, "BR-POSEM-023": PASS, **disc},
            td_payload(group, "101", credentialOnFile="SUBSEQUENT"))
    else:
        add("COF", "Subsequent COF on a group not listed for code 10", {"BR-POSEM-020": REVIEW, "BR-POSEM-023": REVIEW},
            td_payload(group, "101", credentialOnFile="SUBSEQUENT"))
    add("COF", "Initial credential-storing transaction sends keyed 01", {"BR-POSEM-021": PASS},
        td_payload(group, "011", credentialOnFile="INITIAL"))
    add("COF", "Initial credential-storing transaction sends 10", {"BR-POSEM-021": FAIL},
        td_payload(group, "101", credentialOnFile="INITIAL"))
    if listed:
        disc = {"BR-POSEM-027": FAIL} if group == "DISCOVER" else {}
        add("COF", "Subsequent COF sends keyed 01", {"BR-POSEM-023": FAIL, **disc},
            td_payload(group, "011", credentialOnFile="SUBSEQUENT"))
    add("COF", "Code 10 without stored credentials", {"BR-POSEM-020": FAIL}, td_payload(group, "101"))
    if group in ("DEBIT", "STAR_SIGNATURE_DEBIT"):
        outcome = PASS if group == "DEBIT" else REVIEW
        add("COF", "Debit Completion after a credit COF original sends 10", {"BR-POSEM-022": outcome},
            td_payload(group, "101", lifecycleStep="DEBIT_COMPLETION", originalWasCreditCof=True))
        if group == "DEBIT":
            add("COF", "Debit Reversal after a credit COF original sends 05", {"BR-POSEM-022": FAIL},
                td_payload(group, "051", lifecycleStep="DEBIT_REVERSAL", originalWasCreditCof=True))

    if group == "DISCOVER":
        add("DISCOVER", "QR code transaction sends bar code 04", {"BR-POSEM-024": PASS},
            td_payload(group, "041", discoverQrBarPaymentCode=True))
        add("DISCOVER", "QR code transaction sends 10", {"BR-POSEM-020": FAIL, "BR-POSEM-024": FAIL},
            td_payload(group, "101", discoverQrBarPaymentCode=True))
        add("DISCOVER", "Wallet-provisioned card sale sends contactless 07", {"BR-POSEM-025": PASS},
            td_payload(group, "071", issuerStoredOrWallet=True))
        add("DISCOVER", "Wallet-provisioned card sale sends 10", {"BR-POSEM-020": FAIL, "BR-POSEM-025": FAIL},
            td_payload(group, "101", issuerStoredOrWallet=True))
        add("DISCOVER", "Stored card account information sends 10",
            {"BR-POSEM-020": PASS, "BR-POSEM-023": PASS, "BR-POSEM-026": PASS, "BR-POSEM-027": PASS},
            td_payload(group, "101", credentialOnFile="SUBSEQUENT", cardholderUsesStoredInfo=True))
        add("DISCOVER", "Stored card account information sends 82",
            {"BR-POSEM-023": FAIL, "BR-POSEM-026": FAIL, "BR-POSEM-027": FAIL},
            td_payload(group, "821", credentialOnFile="SUBSEQUENT", cardholderUsesStoredInfo=True))

    msr = device(cmsr=True)
    add("CAPABILITY", "Contactless MSR without chip sends capability 8", {"BR-POSEM-030": PASS}, td_payload(group, "918", device=msr))
    add("CAPABILITY", "Contactless MSR without chip sends capability 1", {"BR-POSEM-030": FAIL}, td_payload(group, "911", device=msr))
    add("CAPABILITY", "EMV device sends capability 8", {"BR-POSEM-031": FAIL}, td_payload(group, "078", device=device(chip=True, cmsr=True)))
    pin_on, pin_off = device(chip=True, pin=True), device(chip=True)
    add("CAPABILITY", "PIN-debit-enabled EMV device sends 1", {"BR-POSEM-031": PASS, "BR-POSEM-032": PASS}, td_payload(group, "051", device=pin_on))
    add("CAPABILITY", "PIN-debit-enabled EMV device sends 2", {"BR-POSEM-031": PASS, "BR-POSEM-032": FAIL}, td_payload(group, "052", device=pin_on))
    add("CAPABILITY", "PIN-debit-disabled EMV device sends 2", {"BR-POSEM-031": PASS, "BR-POSEM-033": PASS}, td_payload(group, "052", device=pin_off))
    add("CAPABILITY", "PIN-debit-disabled EMV device sends 1", {"BR-POSEM-031": PASS, "BR-POSEM-033": FAIL}, td_payload(group, "051", device=pin_off))
    hardware, software = device(chip=True, mpos="HARDWARE_PIN_PAD"), device(chip=True, mpos="SOFTWARE_PIN")
    add("CAPABILITY", "mPOS dongle with PIN pad sends 1", {"BR-POSEM-035": PASS}, td_payload(group, "071", device=hardware))
    add("CAPABILITY", "mPOS dongle with PIN pad sends 2", {"BR-POSEM-035": FAIL}, td_payload(group, "072", device=hardware))
    if group == "MASTERCARD":
        add("CAPABILITY", "MasterCard mPOS without PIN pad sends 3", {"BR-POSEM-034": PASS, "BR-POSEM-036": PASS},
            td_payload(group, "073", device=software))
        add("CAPABILITY", "Non-mPOS EMV device sends 3", {"BR-POSEM-031": PASS, "BR-POSEM-032": FAIL, "BR-POSEM-034": FAIL},
            td_payload(group, "053", device=pin_on))
        add("CAPABILITY", "MasterCard mPOS without PIN pad sends 2", {"BR-POSEM-036": FAIL}, td_payload(group, "072", device=software))
    elif network:
        add("CAPABILITY", "Software mPOS on a non-MasterCard network sends 3", {"BR-POSEM-034": FAIL, "BR-POSEM-036": REVIEW},
            td_payload(group, "073", device=software))
    else:
        add("CAPABILITY", "Software mPOS on a generic group sends 3", {"BR-POSEM-034": REVIEW, "BR-POSEM-036": REVIEW},
            td_payload(group, "073", device=software))

    rfid = device(chip=True, pin=True, rfid=True)
    add("RFID", "Keyed transaction with an RFID receiver present sends 01",
        {"BR-POSEM-040": PASS, "BR-POSEM-PAN-01": PASS, "BR-POSEM-032": PASS},
        td_payload(group, "011", device=rfid, declaredPanEntryMethod="Manual/keyed"))
    add("RFID", "RFID receiver present but Table 005 absent", {"BR-POSEM-001": FAIL, "BR-POSEM-040": FAIL},
        td_payload(group, None, absent=True, device=rfid))
    add("RFID", "Keyed transaction with an RFID receiver sends swipe 90",
        {"BR-POSEM-040": FAIL, "BR-POSEM-PAN-90": FAIL, "BR-POSEM-032": PASS},
        td_payload(group, "901", device=rfid, declaredPanEntryMethod="Manual/keyed"))

    emv = device(chip=True, pin=True, emv=True)
    add("FALLBACK", "Chip card swiped on EMV device sends fallback 80", {"BR-POSEM-043": PASS},
        td_payload(group, "801", device=emv, presentment="SWIPE", card={"chip": True}))
    add("FALLBACK", "Chip card swiped on EMV device sends 90", {"BR-POSEM-043": FAIL},
        td_payload(group, "901", device=emv, presentment="SWIPE", card={"chip": True}))
    add("FALLBACK", "Non-chip card swiped sends 90", {"BR-POSEM-044": PASS},
        td_payload(group, "901", device=emv, presentment="SWIPE", card={"chip": False}))
    add("FALLBACK", "Non-chip card swiped sends fallback 80", {"BR-POSEM-044": FAIL},
        td_payload(group, "801", device=emv, presentment="SWIPE", card={"chip": False}))

    if group in TOKEN_GROUPS:
        add("TOKEN", "Tokenized contactless transaction sends valid 071 (presentment mapping unconfirmed)",
            {"BR-POSEM-045": REVIEW}, td_payload(group, "071", tokenized=True))
        add("TOKEN", "Tokenized transaction sends 2-digit 10", {"BR-POSEM-004": FAIL, "BR-POSEM-045": FAIL},
            td_payload(group, "10", tokenized=True))
    return specs


def build_cases(tables: dict, rule_ids: set[str]):
    scenarios, cases, data = {}, [], []
    for group in NETWORK_GROUPS + GENERIC_GROUPS + [GIFT_CONTEXT, CODE_TABLE_CONTEXT]:
        counters: dict[tuple, int] = {}
        for family, title, expected, payload in case_specs(group, tables):
            unknown = set(expected) - rule_ids
            if unknown:
                raise ValueError(f"{group}/{family} references unknown BRs {sorted(unknown)}")
            actual = evaluate(payload, tables)
            expected_fail = {k for k, v in expected.items() if v == FAIL}
            actual_fail = {k for k, v in actual.items() if v == FAIL}
            wrong = {k: (v, actual.get(k)) for k, v in expected.items() if actual.get(k) != v}
            if expected_fail != actual_fail or wrong:
                raise ValueError(f"{group}/{family} '{title}': expected FAILs {sorted(expected_fail)} got {sorted(actual_fail)}; mismatches {wrong}")
            kind = "N" if expected_fail else ("P" if PASS in expected.values() else "R")
            counters[(family, kind)] = counters.get((family, kind), 0) + 1
            scenario_id = f"TS-POSEM-{group}-{family}"
            suffix = f"{kind}{counters[(family, kind)]:02d}"
            case_id, data_id = f"TC-POSEM-{group}-{family}-{suffix}", f"TD-POSEM-{group}-{family}-{suffix}"
            scenario = scenarios.setdefault(scenario_id, {"id": scenario_id, "groupId": group, "family": family,
                                                          "groupType": "NETWORK" if group in NETWORK_GROUPS else "GENERIC" if group in GENERIC_GROUPS else "CONTEXT",
                                                          "requirementIds": [], "testCaseIds": []})
            for rule_id in expected:
                if rule_id not in scenario["requirementIds"]:
                    scenario["requirementIds"].append(rule_id)
            scenario["testCaseIds"].append(case_id)
            case_type = {"P": "POSITIVE", "N": "NEGATIVE", "R": "REVIEW"}[kind]
            cases.append({"id": case_id, "scenarioId": scenario_id, "groupId": group, "family": family, "caseType": case_type,
                          "title": title, "requirementIds": sorted(expected), "expectedVerdicts": dict(sorted(expected.items())),
                          "testDataIds": [data_id]})
            data.append({"id": data_id, "testCaseIds": [case_id], "payload": payload,
                         "oracleVerdicts": dict(sorted(actual.items())), "payloadSha256": record_sha256(payload)})
    return list(scenarios.values()), cases, data


def coverage_gaps(rules: list[dict], cases: list[dict]) -> list[str]:
    gaps = []
    for item in rules:
        verdicts = [c["expectedVerdicts"][item["id"]] for c in cases if item["id"] in c["expectedVerdicts"]]
        if item["passAssertable"] and PASS not in verdicts:
            gaps.append(f"{item['id']} has no positive case")
        if FAIL not in verdicts:
            gaps.append(f"{item['id']} has no negative case")
    return gaps


def find_record(package: dict, collection: str, identifier: str) -> dict | None:
    return next((r for r in package.get(collection, []) if r.get("id") == identifier), None)


def build_overlay(complete: dict, complete_sha: str, annexure: dict, annexure_sha: str, tables: dict, corrected_td: dict) -> dict:
    old_td = find_record(complete, "testData", "TD-SEG100-APPO-ENTRY-MODE")
    old_ann = find_record(annexure, "businessRequirements", "BR-SEG100-ANN-J")
    old_appo = find_record(complete, "businessRequirements", "BR-SEG100-APPO-ENTRY-MODE")
    if old_td is None or old_ann is None or old_appo is None:
        raise ValueError("Frozen package no longer contains the records this overlay supersedes")
    old_mode = (((old_td.get("payload") or {}).get("request") or {}).get("dataSection2") or {}).get("standardSegment", {}).get("posEntryMode")
    old_eval = evaluate({**old_td["payload"], "context": {"tokenized": True}}, tables)
    decomposed = (["BR-POSEM-004", "BR-POSEM-005", "BR-POSEM-006"]
                  + [f"BR-POSEM-PAN-{r['code']}" for r in tables["pan"]]
                  + [f"BR-POSEM-CAP-{r['code']}" for r in tables["capability"]]
                  + ["BR-POSEM-010", "BR-POSEM-011", "BR-POSEM-012"])
    return {
        "policy": "Frozen packages are not edited; these records supersede prior records for review and coverage purposes.",
        "supersededTestData": [{
            "id": old_td["id"], "sourcePackage": "complete-all-test-solution-br-ts-tc-td-package.json",
            "sourcePackageSha256": complete_sha, "recordSha256": record_sha256(old_td),
            "defects": [
                "POS Entry Mode placed in dataSection2.standardSegment.posEntryMode instead of Segment 111 Table 005 (Element 113).",
                f"Value '{old_mode}' is two digits; Appendix J requires a 3-digit code.",
                "PAN Entry Mode 10 means credential on file, not a token presentment mode.",
                "Expected REVIEW masked a deterministic structural failure."],
            "oracleVerdictsOnOldPayload": dict(sorted(old_eval.items())),
            "codeRecognitionOfOldValue": "INVALID_FORMAT" if not re.fullmatch(r"[0-9]{3}", str(old_mode)) else "VALID_FORMAT",
            "supersededBy": corrected_td["id"], "correctedPayloadSha256": corrected_td["payloadSha256"],
            "producerFix": "src/main/java/com/coreauth/validator/coverage/AppendixSyntheticFixtureDrafts.java (TD-SEG100-APPO-ENTRY-MODE now emits Segment 111 Table 005 value 071)"}],
        "decomposedRequirements": [{
            "id": old_ann["id"], "sourcePackage": "segment-100-annexure-br-baseline-package.json",
            "sourcePackageSha256": annexure_sha, "recordSha256": record_sha256(old_ann),
            "defects": ["Composite title combines structure, code-value and lifecycle rules.", "No atomic statement or source quote.",
                        "Linked only to generic TS/TC/TD-SEG100-ANNEXURE-APPLICABILITY anchored to Appendix A."],
            "decomposedInto": decomposed,
            "supersededLinks": ["TS-SEG100-ANNEXURE-APPLICABILITY -> BR-SEG100-ANN-J",
                                "TC-SEG100-ANNEXURE-APPLICABILITY -> BR-SEG100-ANN-J",
                                "TD-SEG100-ANNEXURE-APPLICABILITY -> BR-SEG100-ANN-J"],
            "note": "The ANNEXURE-APPLICABILITY records remain valid for the other annexures they reference."}],
        "narrowedRequirements": [{
            "id": old_appo["id"], "recordSha256": record_sha256(old_appo), "narrowedTo": ["BR-POSEM-045"],
            "note": "Token presentment code mapping is not source-defined; PASS cannot be asserted until BA/SME answers Q-POSEM-007."}],
    }


QUESTIONS = [
    {"id": "Q-POSEM-001", "severity": "HIGH",
     "question": "Should PAN Entry Mode 10 assertions be considered applicable to STAR Signature Debit and generic Debit beyond the explicit Appendix J footnote wording?"},
    {"id": "Q-POSEM-002", "severity": "MEDIUM",
     "question": "Do you want lifecycle persistence (BR-POSEM-010) to include Reversal with the same 90->02 exception, or keep exception scope strictly to Voids/Completions?"},
    {"id": "Q-POSEM-003", "severity": "MEDIUM",
     "question": "When lifecycle follows an original with missing/invalid capability digit, should capability persistence remain review-only or be an explicit failure?"},
    {"id": "Q-POSEM-004", "severity": "HIGH",
     "question": "Appendix J footnote 1 lists credit/debit authorizers but not EBT. Should EBT inherit the same lifecycle persistence and 90->02 exception?"},
    {"id": "Q-POSEM-005", "severity": "MEDIUM",
     "question": "Capability code 3 is MasterCard mPOS-specific. For other networks/generic groups, should this be FAIL or REVIEW_REQUIRED until network supplements are approved?"},
    {"id": "Q-POSEM-006", "severity": "MEDIUM",
     "question": "For MasterCard non-mPOS traffic, should capability code 3 be treated as hard fail or conditional review?"},
    {"id": "Q-POSEM-007", "severity": "HIGH",
     "question": "Appendix O defines token presentment mode conceptually but does not map concrete Table 005 values. Which code(s) are approved for each token presentment channel?"},
    {"id": "Q-POSEM-008", "severity": "LOW",
     "question": "Appendix J code 04 row combines barcode scan and OCR-for-EMV wording. Should these be split into separate operational constraints?"},
    {"id": "Q-POSEM-009", "severity": "MEDIUM",
     "question": "When fallback-to-swipe conditions are met but EMV payload still appears, should this be modeled as a separate BR failure or a lifecycle-level conflict?"},
    {"id": "Q-POSEM-010", "severity": "HIGH",
     "question": "Table 005 is required for all transactions. Please confirm if any host/program exceptions exist that intentionally omit Segment 111."},
    {"id": "Q-POSEM-011", "severity": "MEDIUM",
     "question": "Appendix J footnote 2 references subsequent Debit Completions/Reversals after Credit COF. Confirm this applies to STAR Signature Debit in your implementation."},
    {"id": "Q-POSEM-012", "severity": "MEDIUM",
     "question": "For Discover exclusions under code 10, do your QR/bar/payment-code and wallet flows include additional sub-conditions not captured in ATL105 text?"},
]


def evidence_for(keys: list[str], pages: dict[int, str]) -> list[dict]:
    refs = []
    for key in keys:
        page, section, quote = EVIDENCE[key]
        normalized = normalize(quote)
        if normalized not in pages.get(page, ""):
            raise ValueError(f"EVIDENCE {key} quote not found on page {page}")
        refs.append({"key": key, "page": page, "section": section, "quote": quote})
    return refs


def to_business_requirement(item: dict, pages: dict[int, str]) -> dict:
    refs = evidence_for(item["evidenceKeys"], pages)
    status = REVIEW if not item["passAssertable"] else "ACTIVE"
    return {
        "id": item["id"], "title": item["title"], "statement": item["statement"],
        "family": item["family"], "networkScope": item["networkScope"], "status": status,
        "passAssertable": item["passAssertable"], "questionIds": item["questionIds"],
        "relatedBaselineRuleIds": item["relatedBaselineRuleIds"], "sourceEvidence": refs,
    }


def render_markdown(package: dict) -> str:
    out = io.StringIO()
    m = package["manifest"]
    print(f"# {m['packageId']}", file=out)
    print("", file=out)
    print(f"- Source SHA-256: `{m['sourceProfile']['sourceSha256']}`", file=out)
    print(f"- Atomic BRs: {m['counts']['businessRequirements']}", file=out)
    print(f"- Scenarios: {m['counts']['testScenarios']}  Cases: {m['counts']['testCases']}  Data: {m['counts']['testData']}", file=out)
    print("", file=out)
    print("## BR families", file=out)
    for family, count in sorted(package["summary"]["countsByFamily"].items()):
        print(f"- {family}: {count}", file=out)
    print("", file=out)
    print("## Coverage", file=out)
    for line in package["summary"]["coverageSummary"]:
        print(f"- {line}", file=out)
    print("", file=out)
    print("## Supersession", file=out)
    for row in package["summary"]["superseded"]:
        print(f"- {row}", file=out)
    print("", file=out)
    print("## BA/SME questions", file=out)
    for q in package["clarificationQuestions"]:
        print(f"- [{q['severity']}] {q['id']}: {q['question']}", file=out)
    return out.getvalue()


def write_csv(rows: list[dict], path: Path) -> None:
    if not rows:
        path.write_text("", encoding="utf-8")
        return
    fields = sorted({k for row in rows for k in row})
    with path.open("w", encoding="utf-8", newline="") as handle:
        writer = csv.DictWriter(handle, fieldnames=fields)
        writer.writeheader()
        writer.writerows(rows)


def build(source_text: Path, complete: Path, annexure: Path) -> tuple[dict, list[dict]]:
    source = source_text.read_text(encoding="utf-8")
    source_sha = sha256_bytes(source_text.read_bytes())
    if source_sha != PINNED_SOURCE_SHA256[VERSION]:
        raise ValueError(f"Unpinned source text: expected {PINNED_SOURCE_SHA256[VERSION]} got {source_sha}")
    pages = split_pages(source)
    tables = parse_appendix_j(source)
    behavior = [to_business_requirement(item, pages) for item in BEHAVIOR_RULES]
    code_values = value_rules(tables)
    for row in code_values:
        row["sourceEvidence"] = row.pop("evidence")
        row["status"] = "ACTIVE"
    rules = behavior + code_values
    rule_ids = {row["id"] for row in rules}
    if len(rule_ids) != len(rules):
        raise ValueError("Duplicate BR IDs in supplement")
    scenarios, cases, data = build_cases(tables, rule_ids)
    gaps = coverage_gaps(rules, cases)
    if gaps:
        raise ValueError("Coverage gaps:\n- " + "\n- ".join(gaps))

    complete_obj = json.loads(complete.read_text(encoding="utf-8"))
    annexure_obj = json.loads(annexure.read_text(encoding="utf-8"))
    corrected_td = next(item for item in data if item["id"] == "TD-POSEM-AMEX-TOKEN-R01")
    overlay = build_overlay(
        complete_obj, sha256_bytes(complete.read_bytes()),
        annexure_obj, sha256_bytes(annexure.read_bytes()),
        tables, corrected_td)

    counts = {
        "businessRequirements": len(rules),
        "testScenarios": len(scenarios),
        "testCases": len(cases),
        "testData": len(data),
    }
    by_family = {}
    for item in rules:
        by_family[item["family"]] = by_family.get(item["family"], 0) + 1
    by_group = {}
    for scenario in scenarios:
        by_group[scenario["groupId"]] = by_group.get(scenario["groupId"], 0) + 1
    for question in QUESTIONS:
        if not any(question["id"] in row["questionIds"] for row in rules):
            raise ValueError(f"Question {question['id']} is not referenced by any BR")
    package = {
        "manifest": {
            "packageId": SUPPLEMENT_ID, "specification": "ATL105", "specificationVersion": VERSION,
            "authority": "INDEPENDENT_TEST_SOLUTION", "aiArtifactsIncluded": False,
            "generationScript": "scripts/build-pos-entry-mode-supplement.py",
            "sourceProfile": {"path": str(source_text), "sourceSha256": source_sha},
            "frozenInputs": {
                "completePackagePath": str(complete),
                "completePackageSha256": sha256_bytes(complete.read_bytes()),
                "annexurePackagePath": str(annexure),
                "annexurePackageSha256": sha256_bytes(annexure.read_bytes()),
            },
            "counts": counts,
        },
        "businessRequirements": rules,
        "testScenarios": scenarios,
        "testCases": cases,
        "testData": data,
        "clarificationQuestions": QUESTIONS,
        "supersessionOverlay": overlay,
        "summary": {
            "countsByFamily": dict(sorted(by_family.items())),
            "countsByGroup": dict(sorted(by_group.items())),
            "coverageSummary": [
                "Every pass-assertable BR has at least one PASS case and at least one FAIL case.",
                "Network-aware scenarios cover AMEX, VISA, MASTERCARD, DISCOVER, DINERS_CLUB, JCB, STAR_SIGNATURE_DEBIT, DEBIT and EBT.",
                "Two extra contexts cover gift-program entry modes and direct Appendix J code-table semantics.",
            ],
            "superseded": [
                "TD-SEG100-APPO-ENTRY-MODE superseded by TD-POSEM-AMEX-TOKEN-R01 with Segment 111/Table 005 payload.",
                "BR-SEG100-ANN-J decomposed into atomic BR-POSEM-* rules for format, values and lifecycle.",
                "BR-SEG100-APPO-ENTRY-MODE narrowed to review-only BR-POSEM-045 until token code mapping is source-approved.",
            ],
        },
    }
    csv_rows = []
    for item in rules:
        for ref in item["sourceEvidence"]:
            csv_rows.append({
                "brId": item["id"], "family": item["family"], "scope": item["networkScope"], "status": item["status"],
                "page": ref["page"], "section": ref["section"], "quote": ref["quote"],
            })
    return package, csv_rows


def write_outputs(output_dir: Path, package: dict, evidence_rows: list[dict]) -> None:
    output_dir.mkdir(parents=True, exist_ok=True)
    package_path = output_dir / "pos-entry-mode-supplement.json"
    package_text = json.dumps(package, indent=2, ensure_ascii=False) + "\n"
    package_path.write_text(package_text, encoding="utf-8")
    (output_dir / "pos-entry-mode-supplement.md").write_text(render_markdown(package), encoding="utf-8")
    write_csv(evidence_rows, output_dir / "pos-entry-mode-source-evidence.csv")
    (output_dir / "pos-entry-mode-supplement.sha256").write_text(
        f"{sha256_bytes(package_text.encode('utf-8'))}  pos-entry-mode-supplement.json\n", encoding="utf-8")


def check_outputs(output_dir: Path, package: dict, evidence_rows: list[dict]) -> int:
    expected_json = json.dumps(package, indent=2, ensure_ascii=False) + "\n"
    expected_md = render_markdown(package)
    expected_sha = f"{sha256_bytes(expected_json.encode('utf-8'))}  pos-entry-mode-supplement.json\n"
    package_path = output_dir / "pos-entry-mode-supplement.json"
    markdown_path = output_dir / "pos-entry-mode-supplement.md"
    csv_path = output_dir / "pos-entry-mode-source-evidence.csv"
    sha_path = output_dir / "pos-entry-mode-supplement.sha256"
    stale = []
    if package_path.read_text(encoding="utf-8") != expected_json:
        stale.append(str(package_path))
    if markdown_path.read_text(encoding="utf-8") != expected_md:
        stale.append(str(markdown_path))
    if sha_path.read_text(encoding="utf-8") != expected_sha:
        stale.append(str(sha_path))
    with csv_path.open("r", encoding="utf-8", newline="") as handle:
        existing_rows = list(csv.DictReader(handle))
    normalized_existing = [dict(sorted(row.items())) for row in existing_rows]
    normalized_expected = [dict(sorted({k: str(v) for k, v in row.items()}.items())) for row in evidence_rows]
    if normalized_existing != normalized_expected:
        stale.append(str(csv_path))
    if stale:
        print("STALE: " + ", ".join(stale), file=sys.stderr)
        return 1
    print("PASS: POS Entry Mode supplement outputs are up to date")
    return 0


def main(argv: list[str] | None = None) -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--source-text", type=Path, default=DEFAULT_SOURCE)
    parser.add_argument("--complete-package", type=Path, default=DEFAULT_COMPLETE)
    parser.add_argument("--annexure-package", type=Path, default=DEFAULT_ANNEXURE)
    parser.add_argument("--output-directory", type=Path, default=DEFAULT_OUTPUT)
    parser.add_argument("--check", action="store_true")
    args = parser.parse_args(argv)

    package, evidence_rows = build(args.source_text, args.complete_package, args.annexure_package)
    if args.check:
        return check_outputs(args.output_directory, package, evidence_rows)
    write_outputs(args.output_directory, package, evidence_rows)
    print(json.dumps({
        "packageId": package["manifest"]["packageId"],
        "ruleCount": package["manifest"]["counts"]["businessRequirements"],
        "scenarioCount": package["manifest"]["counts"]["testScenarios"],
        "caseCount": package["manifest"]["counts"]["testCases"],
        "dataCount": package["manifest"]["counts"]["testData"],
        "outputDirectory": str(args.output_directory),
    }, indent=2))
    return 0


if __name__ == "__main__":
    raise SystemExit(main())
