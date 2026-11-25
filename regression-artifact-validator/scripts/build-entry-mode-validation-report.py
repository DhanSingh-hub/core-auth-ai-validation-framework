#!/usr/bin/env python3
"""Scenario-wise ATL105 test data validation report (transaction category x entry mode) for one card program.

Converts the records of <data>/test_cases/test_cases.jsonl into the input format of validate-td-against-atl105-spec.py,
validates their test data field by field against ATL105 2026-4 (every quoted spec text is re-verified on its page), and
writes '<out>/':
  README.md                                                       overview and defect matrix
  Consolidated_Defect_Report.md                                   every defect field by field (for the development team)
  Consolidated_Defect_Sheet.xlsx                                  the same findings as filterable sheets
  <category>/<entry mode>/<entry mode>_Findings.md                findings summary per scenario
  <category>/<entry mode>/TC-xxxxxx_ATL105_Field_Validation.md    field-by-field report per test case
Default: MasterCard Credit, up to 5 test cases per scenario. --all validates every test case of the catalog (Totals Requests
and device-only test cases included).
"""

import argparse
import importlib.util
import json
import os
import re
import sys
from collections import Counter
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
_spec = importlib.util.spec_from_file_location("tdv", ROOT / "scripts" / "validate-td-against-atl105-spec.py")
tdv = importlib.util.module_from_spec(_spec)
_spec.loader.exec_module(tdv)
tcv = tdv.tcv

DATA = ROOT / "MasterCard Credit" / "mastercard_credit"
OUT = ROOT / "MasterCard Credit" / "Manual Validation Report"
DATA_LABEL = "MasterCard Credit/mastercard_credit"
PROGRAM = "MasterCard Credit"  # set from the catalog's dimension_labels in main()
ALL = False  # --all: every test case of the catalog instead of the sample
RERUN = "python scripts/build-entry-mode-validation-report.py"
PDF = "specifications/ATL105/docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf"
TODAY = tdv.TODAY
REPORT = "{}_ATL105_Field_Validation.md"

# folder names are kept short: the report folder is ~180 characters deep and Windows paths stop at 259
CATEGORIES = {"PURCHASE": ("Purchase_Capture", "Purchase/Capture"), "MOTO_PURCHASE": ("Mail_Phone_Purchase", "Mail/Phone Purchase"),
              "AUTH_ONLY": ("Auth_Only", "Authorization Only"),
              "MOTO_AUTH_ONLY": ("Mail_Phone_Auth", "Mail/Phone Authorization Only"),
              "RETURN": ("Return", "Merchandise Return"), "BALANCE_INQUIRY": ("Balance_Inquiry", "Balance Inquiry")}
ENTRY_MODES = {"EMV_CONTACT": ("EMV_Contact_Chip", "EMV Contact Chip"), "EMV_CONTACTLESS": ("EMV_Contactless_Chip", "EMV Contactless Chip"),
               "MAGSTRIPE": ("Magnetic_Stripe", "Magnetic Stripe"),
               "CONTACTLESS_MAGSTRIPE": ("Contactless_Magstripe", "Contactless Magnetic Stripe"),
               "MANUAL": ("Manual_Keyed", "Manual/Keyed"), "EMV_FALLBACK": ("Chip_Fallback", "Chip Fallback"),
               "CREDENTIAL_ON_FILE": ("Credential_on_File", "Credential on File"),
               "CONTACTLESS_MOBILE": ("Contactless_Mobile", "Contactless Mobile Commerce Device")}
MAX_PATH = 259
# samples of the first report (Purchase/Capture, 4 entry modes), kept so those results stay comparable
FIXED = {"BR1_TS1": ["TC-001722", "TC-001723", "TC-001724", "TC-001726", "TC-001730"],
         "BR1_TS2": ["TC-001759", "TC-001761", "TC-001762", "TC-001763", "TC-001767"],
         "BR1_TS3": ["TC-001796", "TC-001797", "TC-001798", "TC-001799", "TC-001803"],
         "BR1_TS5": ["TC-001867", "TC-001868", "TC-001869", "TC-001870", "TC-001874"]}
SAMPLE_SIZE = 5
KEYED_CODES = {"01", "79"}  # Appendix J p.638: entry modes in which the card is not read
REVERSAL_ROLES = {"reversal", "moto_reversal", "timeout_reversal", "tor_retry", "void_return"}  # p.161: no EMV data needed
SEG_NAMES = {"100": "Standard Message Data Segment", "111": "Variable Information Data Segment", "130": "EMV Request Data Segment",
             "105": "Totals Data Segment"}
TD_KEY = {name: key for _, _, key, name, _ in tdv.SEG100}
TD_KEY.update({name: key for _, _, key, name, *_ in tdv.S105 if name not in TD_KEY})
P85_REASON = {"8": ("it covers the Authorization Only Reversal (Transaction Type S, reversing Authorization Only codes 3 and 5), "
                    "while this step is a Purchase Reversal (Prompt Code 8020, Transaction Type 8)", "EL5-REV"),
              "C": ("it covers the Authorization Only Reversal (Transaction Type S), while this step is a Mail/Phone Reversal "
                    "(Prompt Code C020, Transaction Type C)", "EL5-REV"),
              "0": ("it covers the Authorization Only Reversal (Transaction Type S), while this step is a preauthorized "
                    "completion (Prompt Code 0020, Transaction Type 0)", "EL5-PRE")}
P87_NOTE = dict(cited_ref="S10.1.6.1.3", primary_ref="S10.1.2.1-REMAIN", reason="that section is about FSA/HRA healthcare benefit cards")
DEVICE_REFS = {"T2-3SWIPES": ["S10.1.1.3", "S10.1.1.3-STAR"]}
OBJECTIVE_REFS = [("EXPIRED", "expired card", ["S10.14.2.1", "EL12-EXP"]), ("ONLINE-PIN", "online PIN", ["S10.14.3-ONLINE"]),
                  ("OFFLINE-PIN", "offline PIN", ["S10.14.3-OFFLINE"]), ("TRACK1", "Track 1 sent", ["S10.1.1.2"]),
                  ("RFID", "RFID-equipped terminal", ["S10.1.5", "APPJ-NOTE1"]),
                  ("3SWIPES", "3 failed swipes then keyed", ["S10.1.1.3"]), ("CHIP-KEYED", "chip fallback to keyed", ["APPJ-79"]),
                  ("CHIP-SWIPED", "chip fallback to swipe", ["S10.14.4-80"]), ("CL-TO-CONTACT", "contactless to contact chip",
                                                                              ["APPJ-86"]),
                  ("T7-", "stored credential (CIT/MIT)", ["APPI-032-08", "APPI-049-02"]), ("-AVS", "AVS", ["S10.1.3-AVS"]),
                  ("SPLIT", "split tender", ["S10.1.2.1-REMAIN"]), ("COMPLETION", "completion of an authorization", ["EL5-PRE"]),
                  ("CANCEL", "cancellation", ["S10.1.2.2"]), ("TOR", "time-out reversal", ["S2.3-TOR", "S2.3.1-TOR-SEQ"]),
                  ("NO-RESPONSE", "time-out reversal", ["S2.3-TOR", "S2.3.1-TOR-SEQ"]),
                  ("REVERSAL", "reversal", ["S10.1.2.1-SEQ", "EL5-REV"]), ("RET-VOID", "void of a return", ["APPG-U"]),
                  ("PARTIAL", "partial approval", ["S10.1.2.1-F"])]
AMOUNT_RULES = {"PROVISIONAL_AMOUNT_TOO_LONG", "AMOUNT_NOT_POSSIBLE", "SEGMENT_LENGTH_IMPOSSIBLE", "EMV_CHIP_AMOUNT_NOT_REMAINDER"}
DEFECTS = [
    dict(id="D-01", rules={"EMV_CSN_WIDTH"}, title="EMV Card Sequence Number (Element 188) has the wrong width"),
    dict(id="D-02", rules={"EMV_ADDL_INFO_CONTENT"}, title="EMV Additional Information (Element 118) does not contain EMV Table Data"),
    dict(id="D-03", rules={"EXPIRED_TEST_CARD_NOT_EXPIRED"}, title="Expired-card test data uses a card that is not expired"),
    dict(id="D-04", rules={"ONLINE_PIN_NOT_SENT"}, title="Online-PIN test data carries no PIN block and a signature CVM"),
    dict(id="D-05", rules=AMOUNT_RULES, roles={"split_tender"},
         title="Split tender: the second card's amount data was built from an impossible value"),
    dict(id="D-06", rules={"TERMINAL_CAPABILITY_CONFLICT"},
         title="The terminal is described two different ways in the same message (Table 005 vs Table 031)"),
    dict(id="D-07", rules={"TRACK1_NOT_SENT"}, title="The Track 1 test sends Track 2 data"),
    dict(id="D-08", rules={"KEYED_ENTRY_TRACK_DATA"}, title="Track data is sent with an entry mode that does not read the card"),
    dict(id="D-09", rules={"COF_INDICATORS_MISSING", "COF_INITIAL_INDICATOR_MISSING"},
         title="Credential on File indicators (Tables 032, 049, 056) are missing"),
    dict(id="D-10", rules={"AVS_DATA_NOT_SENT"}, title="The AVS test sends no address data (Table 003)"),
    dict(id="D-11", rules={"OFFLINE_PIN_NOT_IN_CVM"}, title="Offline-PIN test data shows a signature CVM"),
    dict(id="D-12", rules={"TABLE_030_MISSING"},
         title="Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions"),
    dict(id="D-13", rules={"FOLLOW_ON_FULL_TRACK"}, title="A follow-on message (time-out reversal) carries full track data"),
    dict(id="D-14", rules=AMOUNT_RULES, roles=set(tcv.SAME_SEQ_ROLES),
         title="Follow-on step (completion, cancellation, reversal): amount data built from an impossible value"),
    dict(id="D-15", rules={"CARD_DATA_MISSING"}, title="The card data (Account Number, Card Discretionary Block Data) is empty"),
    dict(id="D-16", rules={"DEBIT_PIN_MISSING"}, title="The debit PIN block (Element 33) is empty"),
    dict(id="D-17", rules={"DEBIT_MANUAL_ENTRY"}, title="A keyed (manual) entry mode is used for a debit card"),
    dict(id="D-18", rules={"EMV_CASHBACK_AMOUNTS"}, title="Cash back: the chip amounts (Tags 9F02, 9F03, 9C) leave out the cash back"),
    dict(id="D-19", rules={"TOTALS_CARD_LABEL_WIDTH"}, title="Totals Request: Card Label (Element 13) is not 4 characters"),
    dict(id="D-20", rules={"TOTALS_SEGMENT_LENGTH"}, title="Totals Request: Segment 105 length is counted with the wrong layout"),
    dict(id="D-21", rules={"TOTALS_FLOW_MISMATCH"}, title="Totals Request: the totals do not match the transactions of the flow"),
    dict(id="D-22", rules={"FOLLOW_ON_PIN_PRESENT"}, title="A debit follow-on message carries a PIN block"),
    dict(id="D-23", rules={"DEBIT_COMPLETION_TOR"}, title="A Time-out Reversal is sent for a debit Completion"),
    dict(id="D-24", rules={"TD_DIFFERS_FROM_TC"}, title="A test data value differs from the test case it was generated from"),
]
DEFECT_RULES = {r for d in DEFECTS for r in d["rules"]}
NEEDED = {"TERMINAL_PLACEHOLDER": "Client merchant list / BUYPASS", "READY_WITH_PLACEHOLDER": "Test case owner",
          "EMV_CA_CHECKSUM_SAMPLE": "Host CA Public Key file", "EMV_PLACEHOLDER_CRYPTO": "Card simulator / HSM",
          "EMV_TVR_CVM_MISMATCH": "Card simulator",
          "EMV_CONTACTLESS_TEMPLATE": "MasterCard contactless (M/Chip) kernel trace",
          "PARTIAL_APPROVAL_PRECONDITION": "Client test card or simulator trigger",
          "CITATION_OUTDATED_PAGE": "Test case generator (citation)", "CITATION_WRONG_SECTION": "Test case generator (citation)",
          "QUOTE_CONTEXT": "Test case generator (citation)", "QUOTE_PAGE_DRIFT": "Test case generator (citation)",
          "QUOTE_WRONG_PAGE": "Test case generator (citation)", "QUOTE_PARAPHRASED": "Test case generator (citation)",
          "SAME_REQUEST_AS_OTHER_TC": "Device-level verification during execution", "CARD_EXPIRED": "Test card",
          "CHIP_CARD_SWIPED": "Test card selection", "SERVICE_CODE_NO_CHIP": "Test card selection",
          "EMV_COMPLETION_NO_CHIP_DATA": "BUYPASS / SME", "COF_EXPIRY_FORMAT": "BUYPASS / SME",
          "EXPECTED_RESPONSE_OPEN": "Test case owner", "RESUBMIT_SEQUENCE_CONFLICT": "BUYPASS / SME",
          "TOTALS_ZERO_BUCKET": "BUYPASS / SME", "TOTALS_AO_BUCKET": "BUYPASS / SME",
          "TOTALS_VERSION_PLACEHOLDER": "Client (versions of the certified device)", "DEBIT_COF_PIN": "BUYPASS / SME"}

# ---------------------------------------------------------------- consolidated defect report
DEFECT_REPORT = "Consolidated_Defect_Report.md"
DEFECT_SHEET = "Consolidated_Defect_Sheet.xlsx"
EVIDENCE_ROWS = 80  # evidence rows per defect in the markdown report; the sheet has every row
TD_LINK = "../mastercard_credit/test_data/"
TC_LINK = "../mastercard_credit/test_cases/test_cases.jsonl"
# JSON key (and occurrence) holding the value of each FAIL rule in the test data file
RULE_KEY = {"EMV_CSN_WIDTH": "EMVCardSequenceNumber", "EMV_ADDL_INFO_CONTENT": "EMVAdditionalInformation",
            "EXPIRED_TEST_CARD_NOT_EXPIRED": "CardDiscretionaryBlockData", "ONLINE_PIN_NOT_SENT": "EncryptedPINBlockData",
            "SEGMENT_LENGTH_IMPOSSIBLE": "SegmentLength", "EMV_CHIP_AMOUNT_NOT_REMAINDER": "EMVChipData",
            "TERMINAL_CAPABILITY_CONFLICT": ("VariableInformation", 2), "TRACK1_NOT_SENT": "AccountNumber",
            "KEYED_ENTRY_TRACK_DATA": "CardDiscretionaryBlockData", "COF_INDICATORS_MISSING": "VariableInformationSection",
            "COF_INITIAL_INDICATOR_MISSING": "VariableInformationSection", "AVS_DATA_NOT_SENT": "VariableInformationSection",
            "OFFLINE_PIN_NOT_IN_CVM": "EMVChipData", "TABLE_030_MISSING": "VariableInformationSection",
            "FOLLOW_ON_FULL_TRACK": "CardDiscretionaryBlockData", "CARD_DATA_MISSING": "AccountNumber",
            "DEBIT_PIN_MISSING": "EncryptedPINBlockData", "DEBIT_MANUAL_ENTRY": ("VariableInformation", 1),
            "EMV_CASHBACK_AMOUNTS": "EMVChipData", "TOTALS_CARD_LABEL_WIDTH": "CardLabel", "TOTALS_SEGMENT_LENGTH": "SegmentLength",
            "TOTALS_FLOW_MISMATCH": "CardTypeTotalCount", "FOLLOW_ON_PIN_PRESENT": "EncryptedPINBlockData",
            "DEBIT_COMPLETION_TOR": "PromptCode"}
TC_VALUE_RULES = {"PROVISIONAL_AMOUNT_TOO_LONG", "AMOUNT_NOT_POSSIBLE"}  # the value sits in test_cases.jsonl, not in the test data
FIELD_RULES = [("Element 2 Account Number + Element 12 Card Discretionary Block Data (empty)", "100", {"CARD_DATA_MISSING"}),
               ("Element 2 Account Number + Element 12 (Track 1 form)", "100", {"TRACK1_NOT_SENT"}),
               ("Element 12 Card Discretionary Block Data", "100",
                {"EXPIRED_TEST_CARD_NOT_EXPIRED", "KEYED_ENTRY_TRACK_DATA", "FOLLOW_ON_FULL_TRACK"}),
               ("Element 33 Encrypted PIN Block Data (+ Tag 9F34)", "100 / 130",
                {"ONLINE_PIN_NOT_SENT", "DEBIT_PIN_MISSING", "FOLLOW_ON_PIN_PRESENT"}),
               ("Element 78 Prompt Code (step type)", "100", {"DEBIT_COMPLETION_TOR"}),
               ("Element 58 Nonfuel Amount (test case value)", "100", TC_VALUE_RULES),
               ("Element 84 Segment Length (Segment 100)", "100", {"SEGMENT_LENGTH_IMPOSSIBLE"}),
               ("Any field: test data vs test case value", "100 / 105", {"TD_DIFFERS_FROM_TC"}),
               ("Element 113 Table 003 (AVS)", "111", {"AVS_DATA_NOT_SENT"}),
               ("Element 113 Table 005 (PAN entry mode)", "111", {"DEBIT_MANUAL_ENTRY"}),
               ("Element 113 Table 030 (POS Condition Code)", "111", {"TABLE_030_MISSING"}),
               ("Element 113 Table 031 (terminal capability)", "111", {"TERMINAL_CAPABILITY_CONFLICT"}),
               ("Element 113 Tables 032 / 049 / 056 (Credential on File)", "111",
                {"COF_INDICATORS_MISSING", "COF_INITIAL_INDICATOR_MISSING"}),
               ("Element 118 EMV Additional Information", "130", {"EMV_ADDL_INFO_CONTENT"}),
               ("Element 188 EMV Card Sequence Number", "130", {"EMV_CSN_WIDTH"}),
               ("Element 190 EMV Chip Data – Tag 9F34 CVM Results", "130", {"OFFLINE_PIN_NOT_IN_CVM"}),
               ("Element 190 EMV Chip Data – Tag 9F02 Amount, Authorized", "130", {"EMV_CHIP_AMOUNT_NOT_REMAINDER"}),
               ("Element 190 EMV Chip Data – Tags 9F02 / 9F03 / 9C (cash back)", "130", {"EMV_CASHBACK_AMOUNTS"}),
               ("Element 13 Card Label (Totals Request)", "105", {"TOTALS_CARD_LABEL_WIDTH"}),
               ("Element 84 Segment Length (Totals Request)", "105", {"TOTALS_SEGMENT_LENGTH"}),
               ("Elements 16 / 15 / 42 Card Type Total Count, Amount, Grand Total", "105", {"TOTALS_FLOW_MISMATCH"})]
# order of the field-by-field recommendations (Segment 100, 111, 130, 105)
FIELD_ORDER = ["D-15", "D-07", "D-03", "D-08", "D-13", "D-04", "D-16", "D-22", "D-23", "D-05", "D-14", "D-24", "D-06", "D-17",
               "D-09", "D-10", "D-12", "D-02", "D-01", "D-11", "D-18", "D-19", "D-20", "D-21"]
S100, S111, S130 = '`"Standard Message Data Segment"`', '`"Variable Information Data Segment"`', '`"EMV Request Data Segment"`'
S105 = '`"Totals Data Segment"`'
DEFECT_DOC = {
    "D-01": dict(field="Element 188 EMV Card Sequence Number", seg="130", where=f'{S130} → `"EMVCardSequenceNumber"`',
                 kind="Wrong field width", scope="EMV test data files (Segment 130 sent)", attr=("130", "EMV Card Sequence Number"),
                 fix="Element 188 = Tag 5F34 right-aligned and zero-padded to 3 digits (`01` → `001`); 3 spaces when Tag 5F34 is "
                     "absent. Recalculate the Segment 130 Segment Length afterwards.",
                 check="Element 188 has 3 characters and equals Tag 5F34 zero-padded"),
    "D-02": dict(field="Element 118 EMV Additional Information (with Elements 191/192)", seg="130",
                 where=f'{S130} → `"EMVAdditionalInformationIndicator"`, `"EMVAdditionalInformationLength"`, '
                       '`"EMVAdditionalInformation"`',
                 kind="Content not allowed for the indicator", scope="EMV test data files (Segment 130 sent)",
                 attr=("130", "EMV Additional Information"),
                 certainty="Certain that the content is invalid; the exact corrected Element 192 value depends on the framing "
                           "question in §6",
                 fix="Indicator `001`: send EMV Table Data `EMVYES` (device can receive EMV table data) or `EMVNOT` and set "
                     "Element 192 to its length. Apply the Appendix T values instead of filling the 984-character maximum, never "
                     "reuse a generated string across test cases, and recalculate the Segment 130 length.",
                 check="Element 118 is `EMVYES` or `EMVNOT` (or the confirmed framed form) and Element 192 = its length"),
    "D-03": dict(field="Element 12 Card Discretionary Block Data (expiry)", seg="100 / 130",
                 where=f'{S100} → `"CardDiscretionaryBlockData"`; EMV: `EMVChipData` Tags 5F24 and 95',
                 kind="Test objective not met", attr=("100", "Card Discretionary Block Data"),
                 certainty="Certain; the fix needs an expired test card from the client (listed in the test case)",
                 fix="Expired-card tests: use a card whose expiry is before the run date; for EMV also carry Tag 5F24 with that "
                     "date and set TVR byte 2 bit 7 (expired application). Mark the test case blocked until the card is supplied.",
                 check="expiry (YYMM) is earlier than the run month"),
    "D-04": dict(field="Element 33 Encrypted PIN Block Data and Tag 9F34 CVM Results", seg="100 / 130",
                 where=f'{S100} → `"EncryptedPINBlockData"`; {S130} → `"EMVChipData"` Tag 9F34',
                 kind="Test objective not met (needs client input)", attr=("100", "Encrypted PIN Block Data"),
                 certainty="Certain; the fix needs the client's online-PIN test card and DUKPT key",
                 fix="Online-PIN tests only: Element 33 = DUKPT KSN + PIN block and Tag 9F34 = online PIN (CVM code 02, "
                     "`42xxxx`/`02xxxx`) once the client supplies the card and key; until then mark the test case blocked. Do not "
                     "add a PIN to other credit tests (PIN is optional for credit).",
                 check="online-PIN test cases: Element 33 not empty and Tag 9F34 CVM code 02"),
    "D-05": dict(field="Element 58 Nonfuel Amount (test case value), Element 84 Segment Length, Tag 9F02", seg="100 / 130",
                 where=f'test case `steps[1].request` → `"Nonfuel Amount"`; step 2 {S100} → `"SegmentLength"`; EMV: step 2 '
                       '`EMVChipData` Tag 9F02',
                 kind="Impossible amount, wrong length", attr=("100", "Nonfuel Amount"),
                 fix="Split tender: step 2 amount = step 1 requested amount − step 1 Approved Amount (Element 6 of the step 1 "
                     "response), kept as a run-time value; build step 2 Tag 9F02 from the step 2 amount; any provisional value at "
                     "most 7 digits and smaller than the original; Segment Length calculated after substitution.",
                 check="1 ≤ step 2 amount < step 1 amount, at most 7 digits; Tag 9F02 = step 2 amount; Segment Length recalculated"),
    "D-06": dict(field="Element 113 Table 031 (terminal capability) vs Table 005", seg="111",
                 where=f'{S111} → `"VariableInformationSection"` (Table 005 and Table 031)',
                 kind="Contradictory terminal description",
                 fix="Describe one terminal consistently: Table 031 must agree with the terminal PIN capability in Table 005 "
                     "(digit 3). Use a separate RFID terminal profile, not the hybrid chip terminal of the other test cases.",
                 check="Table 005 digit 3 and Table 031 describe the same terminal"),
    "D-07": dict(field="Element 2 Account Number and Element 12 Card Discretionary Block Data (Track 1)", seg="100",
                 where=f'{S100} → `"AccountNumber"`, `"CardDiscretionaryBlockData"`',
                 kind="Test objective not met", attr=("100", "Card Discretionary Block Data"),
                 fix="Track 1 tests: send the unaltered Track 1 data – Account Number with format code `B`, Card Discretionary "
                     "Block Data = the data after the first `^` (name, `^`, expiry, service code, discretionary data), combined at "
                     "most 76 characters.",
                 check="Account Number starts with `B`, Card Discretionary Block Data contains `^`, combined length ≤ 76"),
    "D-08": dict(field="Element 12 Card Discretionary Block Data", seg="100",
                 where=f'{S100} → `"CardDiscretionaryBlockData"` (with Table 005 entry mode)',
                 kind="Card data does not match the entry mode",
                 scope="test data files with Table 005 entry mode 79 (follow-on steps excluded)",
                 attr=("100", "Card Discretionary Block Data"),
                 fix="Entry modes in which the card is not read (01 keyed, 79 chip fallback to manual): Element 12 = expiry MMYY "
                     "only, never track data. If the scenario really swipes the card, use entry mode 80 (fallback to swipe).",
                 check="entry mode 01/79 → Element 12 is 4 digits MMYY with month 01–12"),
    "D-09": dict(field="Element 113 Tables 032 / 049 / 056 (Credential on File indicators)", seg="111",
                 where=f'{S111} → `"VariableInformationSection"`', kind="Required data missing",
                 scope="Credential on File test data files (entry mode 10, and the storing step of the T7 tests)",
                 fix="Credential on File: add Table 032 sub-table 08 (`C`; `I` on the transaction that stores the credential), "
                     "Table 049 sub-table 02 (Transaction Initiation Indicator `C` or `M`) and, for MasterCard, Table 056 "
                     "sub-table 09 (MIT/CIT category) on every COF step, including reversals and completions.",
                 check="every COF step has Tables 032-08 and 049-02 (and 056-09 for MasterCard); the storing step has 032-08 = `I`"),
    "D-10": dict(field="Element 113 Table 003 (AVS)", seg="111", where=f'{S111} → `"VariableInformationSection"`',
                 kind="Test objective not met (data missing)",
                 certainty="Certain; the address data must come from the client's AVS test card",
                 fix="AVS tests: add Table 003 with the street address / ZIP Code the client supplies for the test card "
                     "(mismatching values for the AVS-mismatch tests).",
                 check="AVS test cases carry Table 003"),
    "D-11": dict(field="Element 190 EMV Chip Data – Tag 9F34 CVM Results", seg="130", where=f'{S130} → `"EMVChipData"` Tag 9F34',
                 kind="Test objective not met", attr=("130", "EMV Chip Data"),
                 fix="Offline-PIN tests: generate the chip data from an offline-PIN transaction – Tag 9F34 with CVM code 01 or 04 "
                     "(PIN verified by the card, e.g. `410302` or `440302`); Element 33 stays empty for credit.",
                 check="offline-PIN test cases: Tag 9F34 CVM code 01, 03, 04 or 05"),
    "D-12": dict(field="Element 113 Table 030 (POS Condition Code)", seg="111", where=f'{S111} → `"VariableInformationSection"`',
                 kind="Required data missing", scope="EMV and Credential on File test data files",
                 fix="Add Table 030 (POS Condition Code, 10 digits, values from Appendix Q for the certified terminal) to every "
                     "EMV request and every Credential on File request.",
                 check="Segment 130 present or entry mode 10 → Table 030 present"),
    "D-13": dict(field="Element 12 Card Discretionary Block Data", seg="100",
                 where=f'{S100} → `"CardDiscretionaryBlockData"` (follow-on step)',
                 kind="Card data not allowed on a follow-on message", attr=("100", "Card Discretionary Block Data"),
                 fix="Follow-on messages (time-out reversal, reversal, completion) carry Account Number + expiry YYMM only "
                     "(truncated Track 2); never copy or store the full track data of the original.",
                 check="follow-on steps → Element 12 is 4 digits YYMM"),
    "D-14": dict(field="Element 58 Nonfuel Amount (test case value), Element 84 Segment Length", seg="100",
                 where=f'test case `steps[i].request` → `"Nonfuel Amount"`; follow-on step {S100} → `"SegmentLength"`',
                 kind="Impossible amount, wrong length", attr=("100", "Nonfuel Amount"),
                 fix="Follow-on step after a partial approval: keep the amount a run-time value from the original response "
                     "(Approved Amount); any provisional value at most 7 digits and not more than the original amount; Segment "
                     "Length calculated after substitution.",
                 check="provisional amount ≤ 7 digits and ≤ original amount; Segment Length recalculated"),
    "D-15": dict(field="Element 2 Account Number and Element 12 Card Discretionary Block Data", seg="100",
                 where=f'{S100} → `"AccountNumber"`, `"CardDiscretionaryBlockData"`',
                 kind="Required data missing (needs client input)", scope="financial test data files (Segment 100 sent)",
                 attr=("100", "Account Number"),
                 certainty="Certain that BUYPASS cannot process the message; the values themselves need the client's card "
                           "profile (the test case marks the Account Number `AWAITING_CLIENT_VALUE`)",
                 fix="Fill in the Account Number and Element 12 from the client's test card: the data read from the card for "
                     "card-read entry modes, Account Number + expiry YYMM on follow-on messages, the expiry for stored credentials. "
                     "Until the card profile is supplied, write an explicit placeholder (for example `<CLIENT_CARD_PAN>`) and keep "
                     "the test case blocked – never an empty string.",
                 check="every financial request has an Account Number and Element 12 (or an explicit placeholder)"),
    "D-16": dict(field="Element 33 Encrypted PIN Block Data", seg="100", where=f'{S100} → `"EncryptedPINBlockData"`',
                 kind="Required data missing (needs client input)",
                 scope="debit requests that need a PIN (every step except the Purchase Reversal and other follow-on messages)",
                 attr=("100", "Encrypted PIN Block Data"),
                 certainty="Certain; the value needs the client's test PIN and DUKPT key",
                 fix="Every debit request except the Purchase Reversal and the follow-on messages: Element 33 = DUKPT Key Serial "
                     "Number + PIN block (Section 10.2.1.3). Until the client supplies the test PIN and DUKPT key, write an "
                     "explicit placeholder and keep the test case blocked.",
                 check="debit requests that are not follow-on messages or Purchase Reversals: Element 33 not empty"),
    "D-17": dict(field="Element 113 Table 005 (PAN entry mode)", seg="111",
                 where=f'{S111} → `"VariableInformationSection"` (Table 005)', kind="Entry mode not allowed for the card program",
                 scope="debit test data files with a keyed entry mode (01, 79), follow-on steps excluded",
                 certainty="Certain – Section 10.2.1.4; the catalog's own device-only test T11-DEBIT-NO-MANUAL expects the "
                           "device to end a debit transaction after three failed reads, without offering manual entry",
                 fix="Debit: no manual entry (Section 10.2.1.4). For the chip-fallback scenario use entry mode 80 (fallback to "
                     "swipe), or drop the keyed debit test cases.",
                 check="debit requests: Table 005 entry mode is not 01 or 79"),
    "D-18": dict(field="Element 190 EMV Chip Data – Tags 9F02, 9F03 and 9C", seg="130",
                 where=f'{S130} → `"EMVChipData"` (Tags 9F02, 9F03, 9C)', kind="Chip data does not match the request",
                 attr=("130", "EMV Chip Data"),
                 fix="Purchase with cash back: generate the chip data from a cash-back transaction – 9F02 = Nonfuel Amount + "
                     "Cash Amount, 9F03 = Cash Amount, 9C = `09` (EMV Book 3) – and recalculate Elements 189 and 84 of Segment 130.",
                 check="Cash Amount > 0 → 9F02 = total, 9F03 = Cash Amount, 9C = 09"),
    "D-19": dict(field="Element 13 Card Label (Totals Request)", seg="105", where=f'{S105} → `"CardLabel"`',
                 kind="Wrong field width", scope="Totals Request files", attr=("105", "Card Label"),
                 fix="Card Label is a fixed 4-character field, left-aligned and space-filled: `DB` → `DB  ` (two trailing "
                     "spaces). Recalculate the Segment Length afterwards (D-20).",
                 check="Card Label has exactly 4 characters"),
    "D-20": dict(field="Element 84 Segment Length (Totals Request)", seg="105", where=f'{S105} → `"SegmentLength"`',
                 kind="Wrong length", scope="Totals Request files", attr=("105", "Segment Length"),
                 fix="Segment 105 length = Fields 1–14 + 14 Field Separators + the Category Total Data Section, which has no "
                     "separators (Card Label 4 + Count 5 + Amount 8 = 17) (Section 12.6). Do not count a separator after Fields "
                     "15–17 and use the 4-character Card Label (D-19).",
                 check="declared Segment Length = Fields 1–14 + 14 + 17"),
    "D-21": dict(field="Elements 16, 15, 42 Card Type Total Count, Card Type Total Amount, Grand Total", seg="105",
                 where=f'{S105} → `"CardTypeTotalCount"`, `"CardTypeTotalAmount"`, `"GrandTotal"`',
                 kind="Totals do not match the flow", attr=("105", "Card Type Total Count"),
                 fix="Compute the device totals from the steps before the Totals Request (Section 9.2): approved Purchase/Capture "
                     "or Completion +1 / +amount; approved Reversal −1 / −amount; Merchandise Return +1 / −amount; Authorization "
                     "Only into the AO bucket, which the grand total leaves out. A purchase that never got a response and was "
                     "reversed is not in the totals. A card bucket whose net count is zero is not sent.",
                 check="count, amount and grand total = the sums over the approved steps before the Totals Request"),
    "D-22": dict(field="Element 33 Encrypted PIN Block Data (follow-on message)", seg="100",
                 where=f'{S100} → `"EncryptedPINBlockData"` (follow-on step)', kind="Data not allowed on a follow-on message",
                 attr=("100", "Encrypted PIN Block Data"),
                 fix="Debit follow-on messages (completion, reversal, time-out reversal): send Element 33 empty (Section 10.2.5.1).",
                 check="debit follow-on steps → Element 33 empty"),
    "D-23": dict(field="Element 78 Prompt Code – Time-out Reversal of a debit Completion", seg="100",
                 where=f'{S100} → `"PromptCode"` (time-out reversal step)', kind="Flow not supported for the card program",
                 fix="Do not reverse a debit Completion with a Time-out Reversal (Section 10.2.2): remove the step or replace "
                     "the test case, and ask BUYPASS how an unanswered debit Completion is to be handled.",
                 check="no Time-out Reversal whose original step is a debit Completion"),
    "D-24": dict(field="Any field – test data value vs test case request value", seg="100 / 105",
                 where="the field named in each row", kind="Generation fidelity",
                 fix="Write the test data file from the test case request it belongs to (same values); regenerate the files "
                     "after any change to the test case.",
                 check="every test data value = the test case request value (except run-time placeholders)"),
}


def fixed_samples():
    """The samples of the first report apply to the MasterCard Credit sample only."""
    return FIXED if PROGRAM == "MasterCard Credit" and not ALL else {}


def md(s):
    return re.sub(r"\s*[\r\n]+\s*", " ", str(s).replace("|", "\\|").replace("*", "\\*"))


def short(s, head=20, tail=8):
    s = str(s)
    return s if len(s) <= head + tail + 3 else f"{s[:head]}…{s[len(s) - tail:]}"


def join(items, last=" and "):
    items = [str(x) for x in items]
    return items[0] if len(items) == 1 else ", ".join(items[:-1]) + last + items[-1] if items else ""


# ---------------------------------------------------------------- test case conversion (jsonl -> validator format)
def fields(seg):
    return {k: (x.get("value") if isinstance(x, dict) else x) for k, x in (seg or {}).items()}


def not_sent(seg):
    return any("SEGMENT_NOT_SENT" in g for x in (seg or {}).values() if isinstance(x, dict) for g in x.get("flags") or [])


def vi_pairs(seg):
    v = fields(seg)
    for sfx in [""] + [f" [{i}]" for i in range(2, 10)]:
        if v.get("Variable Information Indicator" + sfx):
            yield sfx, v["Variable Information Indicator" + sfx], v.get("Variable Information" + sfx)


def tc_request(req):
    s100 = fields(req.get(SEG_NAMES["100"]))
    out = {name: s100.get(name) for _, _, _, name, _ in tdv.SEG100 if name not in ("Segment Type", "Segment Length")}
    out.update({f"VI {ind}": val for _, ind, val in vi_pairs(req.get(SEG_NAMES["111"]))})
    return out


def tc_emv(req):
    seg = req.get(SEG_NAMES["130"])
    if not seg or not_sent(seg):
        return {}
    v = fields(seg)
    return {name: v[name] for *_, name in tdv.S130 if name != "Segment Length" and v.get(name) is not None}


def spec_basis(rec):
    basis, seen = [], set()
    for b in rec.get("spec_basis") or []:
        if (b.get("page"), b.get("quote")) in seen:
            continue
        seen.add((b.get("page"), b.get("quote")))
        item = {k: b[k] for k in ("source_id", "page", "quote") if k in b}
        if tcv.squash(b.get("quote") or "") == tcv.squash(tdv.REFS["S10.1.6.1.3"][2]):
            item["context_note"] = P87_NOTE
        basis.append(item)
    return basis


def convert_device_only(rec):
    """A device-only record (T11): no host message, so only its ATL105 citations can be checked."""
    text = lambda x: "; ".join(map(str, x)) if isinstance(x, list) else str(x or "")  # noqa: E731
    tc = dict(id=rec["id"], display_id=rec["display_id"], template_id=rec["template_id"], status=rec["status"],
              name=rec["name"], dimensions=rec["dimensions"], polarity=rec.get("polarity"),
              flags=tdv.uniq(f.split(":")[0].strip() for f in rec.get("flags") or []),
              expected_response=rec.get("expected_response"), spec_basis=spec_basis(rec), steps=[],
              device_checkpoints=rec.get("device_checkpoints") or [], device_refs=[],
              device_only={"Objective": text(rec.get("test_objective")), "Procedure (on the device)": text(rec.get("procedure")),
                           "Expected behaviour": text(rec.get("expected_behaviour"))})
    return tc, dict(common_spec_basis=[], field_citations=[])


def convert(rec):
    """One test_cases.jsonl record -> (validator test case, per-test-case metadata)."""
    legs = {lg["step"]: lg for lg in rec.get("tx_legs") or []}
    files = [Path(p).name for p in rec.get("test_data_leg_files") or [rec["test_data_file"]]]
    raw = rec.get("steps") or [dict(step=1, role=legs.get(1, {}).get("role", "purchase"), request=rec["request"])]
    top = (rec.get("expected_response") or {}).get("response_code") or {}
    top_rc, top_any = top.get("value"), [str(c) for c in top.get("acceptable_values") or []]
    steps = []
    for i, s in enumerate(raw):
        n, lg, req = s["step"], legs.get(s["step"], {}), s["request"]
        totals = req.get(SEG_NAMES["105"])
        st = dict(step=n, role=s.get("role") or lg.get("role"), td=files[i], tc_request=tc_request(req),
                  code="" if totals is not None else
                  lg.get("code") or str(fields(req.get(SEG_NAMES["100"])).get("Prompt Code") or "?")[:1])
        if totals is not None:
            st["tc_totals"] = fields(totals)
        attrs = {k: x for seg in ("100", "105") for k, x in (req.get(SEG_NAMES[seg]) or {}).items() if isinstance(x, dict)}
        st["tc_flags"] = {k: x.get("flags") or [] for k, x in attrs.items()}
        st["tc_methods"] = {k: x.get("method") for k, x in attrs.items()}
        if lg.get("of"):
            st["of"] = lg["of"]
        if tc_emv(req):
            st["tc_emv"] = tc_emv(req)
        runtime = {r["element"]: r["rule"] for r in s.get("runtime_substitutions") or []}
        if runtime:
            st["runtime"] = runtime
        rc = ((((s.get("expected_response") or {}).get("response_code") or {}).get("value"))
              or (lg.get("expect") or {}).get("response_code") or (top_rc if len(raw) == 1 else None))
        if rc:
            st["expected_rc"] = rc
        elif top_any and i == len(raw) - 1:
            st["expected_any"] = top_any
        elif "none" not in (lg.get("expect") or {}):
            st["expected_open"] = True
        if st["code"] in P85_REASON and any(d.get("type") == "carry_response_field" and d.get("element_name") == "Approval Number"
                                            and d.get("source_page") == 85 for d in lg.get("link_defs") or []):
            reason, correct = P85_REASON[st["code"]]
            st["link_citation"] = dict(element="Approval Number", cited_page=85, cited_ref="S10.1.2.2", step_ref=f"APPG-{st['code']}",
                                       correct_ref=correct, reason=reason)
        steps.append(st)
    tc = dict(id=rec["id"], display_id=rec["display_id"], template_id=rec["template_id"], status=rec["status"],
              name=rec["name"], dimensions=rec["dimensions"], polarity=rec.get("polarity"),
              flags=tdv.uniq(f.split(":")[0].strip() for f in rec.get("flags") or []),
              expected_response=rec.get("expected_response"), spec_basis=spec_basis(rec), steps=steps,
              device_checkpoints=rec.get("device_checkpoints") or [], device_refs=DEVICE_REFS.get(rec["template_id"], []))
    execution = "; ".join(e for lg in rec.get("tx_legs") or [] for e in lg.get("execution") or [])
    if execution:
        tc["execution"] = execution
    meta = dict(common_spec_basis=[], field_citations=[])
    vseg = raw[0]["request"].get(SEG_NAMES["111"]) or {}
    page005 = next(((vseg.get("Variable Information" + sfx) or {}).get("source_page")
                    for sfx, ind, _ in vi_pairs(vseg) if ind == "005"), None)
    if page005:
        meta["field_citations"].append(dict(
            field="POS Entry Mode table (Table 005)", cited_page=page005, topic_ref="APPI-005",
            where="test case request -> Variable Information Data Segment -> Variable Information (source_page), every step"))
    return tc, meta


# ---------------------------------------------------------------- one validated test case
class Run:
    def __init__(self, mode, rec, line, tc, rep, ctx, td_dir):
        self.mode, self.rec, self.line, self.tc, self.rep, self.ctx, self.td_dir = mode, rec, line, tc, rep, ctx, td_dir
        self.id, self._td = tc["id"], {}
        self.rule_checks, self.link_checks = run_rule_checks(self), run_link_defs(self)

    def st(self, n):
        return next(s for s in self.tc["steps"] if s["step"] == n)

    def path(self, n):
        return self.td_dir / self.st(n)["td"]

    def msg(self, n):
        if n not in self._td:
            self._td[n] = json.loads(self.path(n).read_text(encoding="utf-8"))
        return next(iter(self._td[n].values()))

    def seg(self, n, code):
        return self.msg(n).get(SEG_NAMES[code]) or {}

    def line_of(self, n, key, nth=1):
        hits = [i + 1 for i, s in enumerate(self.path(n).read_text(encoding="utf-8").splitlines()) if f'"{key}":' in s]
        return hits[nth - 1] if len(hits) >= nth else "?"

    def fails(self, rules):
        return [f for f in self.rep.findings if f["severity"] == "FAIL" and f["rule"] in rules]

    def hits(self, d):
        """FAIL findings of defect d (restricted to the step roles of d, if any)."""
        roles = d.get("roles")
        return [f for f in self.fails(d["rules"]) if roles is None or (f["step"] and self.st(f["step"])["role"] in roles)]

    @property
    def defects(self):
        return [d["id"] for d in DEFECTS if self.hits(d)]

    @property
    def scenario(self):
        return f"{self.rec.get('tc_type') or self.tc['template_id'][:2]} – {self.rec.get('test_objective', '').replace('->', '→')}"

    def files(self):
        return [s["td"] for s in self.tc["steps"]]


def td_value(run, n, element):
    return run.ctx["flats"].get(n, {}).get(TD_KEY.get(element, ""), "")


def run_rule_checks(run):
    """Execute the test case's own rule_checks against its test data: (id, step, ok or None, basis)."""
    out = []
    for rc in run.rec.get("rule_checks") or []:
        n, op = rc.get("step", 1), rc.get("op")
        if n not in run.ctx["flats"]:
            continue
        if op == "segment_present":
            ok = SEG_NAMES.get(rc.get("segment"), "?") in next(iter(json.loads(run.path(n).read_text(encoding="utf-8")).values()))
        elif op == "value_slice_in":
            ok = td_value(run, n, rc.get("element"))[rc["start"] - 1:rc["end"]] in rc["allowed"]
        elif op == "table_slice_in":
            t = {"005": run.ctx["t005"], "031": run.ctx["t031"]}.get(rc.get("table_id"), {}).get(n) or ""
            ok = t[rc["start"] - 1:rc["end"]] in rc["allowed"]
        elif op == "field_absent":
            ok = td_value(run, n, rc.get("element")) == ""
        elif op == "value_differs_from_step":
            ok = td_value(run, n, rc.get("element")) != td_value(run, rc.get("other_step", 1), rc.get("element"))
        else:
            ok = None
        if ok is not None and rc.get("expect", "hold") != "hold":
            ok = not ok
        out.append((rc.get("id"), n, ok, rc.get("basis", "")))
    return out


def run_link_defs(run):
    """Execute the test case's link_defs: (step, type, element, ok or None for run-time links, detail)."""
    out = []
    for lg in run.rec.get("tx_legs") or []:
        n = lg["step"]
        for d in lg.get("link_defs") or []:
            src, el, typ = d.get("from_step", 1), d.get("element_name"), d.get("type")
            a, b = td_value(run, src, el), td_value(run, n, el)
            if typ == "carry_request_field" or (typ == "amount_relation" and d.get("relation") == "equal"):
                out.append((n, typ, el, a == b, f"step {n} `{b}` = step {src} `{a}`"))
            elif typ == "must_differ":
                out.append((n, typ, el, a != b, f"step {n} `{b}` differs from step {src} `{a}`"))
            else:
                out.append((n, typ, el, None, f"run-time value from the step {src} response"))
    return out


# ---------------------------------------------------------------- report writer
class Writer:
    def __init__(self, refs, td_dir, out, runs, cross, recs):
        self.refs, self.td_dir, self.out, self.runs, self.cross, self.recs = refs, td_dir, out, runs, cross, recs

    # spec citations
    def q(self, rid):
        self.refs.check(rid)
        return md(self.refs.catalog[rid][2])

    def pg(self, rid):
        self.refs.check(rid)
        p = self.refs.catalog[rid][1]
        return f"p.{p}"

    def cite(self, rid):
        self.refs.check(rid)
        sec, p, text = self.refs.catalog[rid]
        return f"{md(sec)}, p.{p} (printed {self.refs.spec.printed(p)}): *\"{md(text)}\"*"

    def shorts(self, rids):
        return md(self.refs.shorts(rids))

    def compact(self, rids):
        """'Section 12.1 p.223, Element 84 p.406' - one entry per section/element and page."""
        out = []
        for rid in rids:
            self.refs.check(rid)
            sec, p, _ = self.refs.catalog[rid]
            m = re.match(r"(Section [\d.]+|Element \d+|Appendix [A-Z]+)", sec)
            out.append(f"{m.group(1) if m else sec} p.{p}")
        return md(", ".join(tdv.uniq(out)))

    # ------------------------------------------------------------ per scenario (transaction category x entry mode)
    def write_mode(self, mode, runs):
        emv = any(r.seg(s["step"], "130") for r in runs for s in r.tc["steps"])
        folder = self.out / mode["path"]
        folder.mkdir(parents=True, exist_ok=True)
        for r in runs:
            tdv.write_tc_md(folder, self.refs, r.tc, r.rep, name=REPORT.format(r.id), td_prefix=f"{DATA_LABEL}/test_data/")
        present = [d for d in DEFECTS if any(r.hits(d) for r in runs)]
        host = [r for r in runs if r.tc["steps"]]
        common = [d for d in present if all(r.hits(d) for r in host)] if len(host) > 1 else []
        specific = [d for d in present if d not in common]
        others = [(r, f) for r in runs for f in r.rep.findings if f["severity"] == "FAIL" and f["rule"] not in DEFECT_RULES]
        verdicts = Counter(r.rep.verdict() for r in runs)
        nfiles = sum(len(r.tc["steps"]) for r in runs)
        flows = sum(1 for r in runs if len(r.tc["steps"]) > 1)
        codes = tdv.uniq((r.ctx["t005"].get(r.tc["steps"][0]["step"]) or "")[:2] for r in host)
        mode["entries"] = codes
        types = Counter(r.rec.get("tc_type", "?") for r in runs)
        types = ", ".join(f"{t} ×{k}" if k > 1 else t for t, k in sorted(types.items(), key=lambda x: int(x[0][1:] or 0)))
        device = len(runs) - len(host)

        rest = ", ".join(f"{verdicts[v]} {v}" for v in ("BLOCKED", "REVIEW", "PASS") if verdicts[v])
        parts = []
        if len(runs) == 1 and present:
            parts.append(f"defect{'s' if len(present) > 1 else ''} {', '.join(d['id'] for d in present)}")
        if common:
            parts.append(f"{len(common)} defect{'s' if len(common) > 1 else ''} common to every test case "
                         f"({', '.join(d['id'] for d in common)})")
        if specific and len(runs) > 1:
            parts.append(f"{'plus ' if common else ''}{len(specific)} scenario-specific defect{'s' if len(specific) > 1 else ''} "
                         f"({', '.join(d['id'] for d in specific)})")
        overall = (f"**{verdicts['FAIL']} / {len(runs)} test case{'s' if len(runs) > 1 else ''} FAIL**" + (f" ({rest})" if rest else "")
                   + " – " + (", ".join(parts) if parts else "no defects; review items only"))
        lines = sorted(r.line for r in runs)
        excluded = "; ".join(f"{v} {k}" for k, v in mode["excluded"].items())
        L = [f"# ATL105 Test Data Validation – Findings Summary – {mode['title']}", "",
             "| Item | Value |", "|---|---|",
             f"| Product / program | BUYPASS ATL105 – {PROGRAM} – {mode['cat_label']} |",
             f"| Scenario | {mode['key']} {mode['title']} (scenario status: {mode['status']}) |",
             f"| Entry mode | {mode['label']} (Table 005 PAN entry mode "
             + ", ".join(f"`{c}` = {md(tdv.PAN_ENTRY_DESC.get(c, '?'))}" for c in codes) + ") |",
             f"| Specification | {tdv.SPEC_TITLE} **{self.refs.spec.version}** ({self.refs.spec.date}) – `{PDF}` |",
             f"| Test data under review | `{DATA_LABEL}/test_data` |",
             f"| Test case catalog | `{DATA_LABEL}/test_cases/test_cases.jsonl` (lines {', '.join(map(str, lines))}) |",
             "| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text "
             "re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |",
             (f"| Scope | The only test case of this scenario ({runs[0].rec.get('tc_type', '?')})" if mode["total"] == 1 else
              f"| Scope | All {len(runs)} test cases of this scenario ({types})" if ALL else
              f"| Scope | {len(runs)} of the {mode['total']} test cases of this scenario "
              f"({', '.join(r.rec.get('tc_type', '?') for r in runs)})")
             + f", {nfiles} test data file{'s' if nfiles > 1 else ''}"
             + (f" ({flows} multi-step flow{'s' if flows > 1 else ''})" if flows else "")
             + (f"; {device} device-only test case{'s' if device > 1 else ''} without a host message (ATL105 citations "
                "checked only)" if device else "")
             + (f". Not sampled: {excluded}" if excluded else "") + " |",
             f"| Date | {TODAY.isoformat()} |",
             f"| Overall result | {overall} |", "",
             "Per-test-case evidence: the `TC-xxxxxx_ATL105_Field_Validation.md` reports in this folder (index in §8). All "
             "scenarios: [../../README.md](../../README.md).", "",
             f"Page numbers are PDF pages of the {self.refs.spec.version} edition (printed page in brackets where given). The model "
             "report used the older 2026-3 edition, so its page numbers differ (see the README).", "", "---", ""]
        L += self.section1(mode, runs, emv)
        L += self.section2(mode, runs)
        L += self.section3(mode, runs, common, emv)
        L += self.section4(mode, runs, specific, others, emv)
        L += self.section5(mode, runs, emv)
        qs = self.questions(mode, runs, emv)
        L += self.section6(qs)
        L += self.section7(mode, runs, present, qs, emv)
        L += self.section8(mode, runs)
        name = f"{mode['folder']}_Findings.md"
        (folder / name).write_text("\n".join(L) + "\n", encoding="utf-8")
        return dict(mode=mode, runs=runs, present=present, verdicts=verdicts, file=f"{mode['path']}/{name}", nfiles=nfiles,
                    total=mode["total"])

    def section1(self, mode, runs, emv):
        codes = set(mode["entries"])
        roles = {s["role"] for r in runs for s in r.tc["steps"]}
        card, crefs = [], []
        if codes - KEYED_CODES - {"10"}:
            card.append("track data: Track 2 (equivalent) '=' split, expiry YYMM, service code (2xx = chip card), Account Number "
                        "+ '=' + Element 12 at most 37 characters (Track 1: '^' split, at most 76)")
            crefs += ["EL12-TRACK2", "EL12-MAX"]
        if codes & {"80", "90"}:
            card.append("a chip card swiped at a chip terminal is sent as fallback 80")
            crefs += ["S10.14.4-80"]
        if codes & KEYED_CODES:
            card.append(f"keyed entry ({', '.join(sorted(codes & KEYED_CODES))}): expiry MMYY only, no track data")
            crefs += ["EL12", "S10.1.1.3"]
        if "10" in codes:
            card.append("stored credential (10): expiry only, no track data")
            crefs += ["APPJ-10"]
        if roles & set(tcv.SAME_SEQ_ROLES):
            card.append("follow-on steps send the truncated expiry YYMM only")
            crefs += ["S10.1.8.2", "S10.1.1.2-FOLLOW"]
        tables = ["Table 005 (PAN entry mode + terminal PIN capability)", "Table 031 (terminal capability)"]
        trefs = ["S12.10-FS", "APPI-005", "APPI-031"] + [f"APPJ-{c}" for c in sorted(codes) if f"APPJ-{c}" in self.refs.catalog]
        if emv or "10" in codes or any(r.tc["template_id"].startswith("T7-") for r in runs):
            tables.append("Table 030 (POS Condition Code) for EMV and Credential on File transactions")
            trefs.append("APPI-030")
        if "10" in codes or any(r.tc["template_id"].startswith("T7-") for r in runs):
            tables.append("Tables 032/049/056 (Credential on File indicators)")
            trefs += ["APPI-032-08", "APPI-049-02", "S10.13.2.4.2-MC"]
        if any("-AVS" in r.tc["template_id"] for r in runs):
            tables.append("Table 003 (AVS address) on AVS tests")
            trefs += ["EL113-AVS"]
        lrefs = ["S2.3.1-SEQ", "APPJ-PERSIST"] + (["EL86-REV"] if roles & {"reversal", "moto_reversal", "void_return"} else []) + (
            ["EL86-PREAUTH"] if roles & {"completion", "completion_resubmitted"} else []) + (
            ["S10.1.2.2-SEQ"] if "cancellation" in roles else []) + (["S2.3.1-TOR-SEQ"] if roles & tdv.TOR_ROLES else [])
        codes_rc = sorted({s.get("expected_rc") for r in runs for s in r.tc["steps"] if s.get("expected_rc")})
        objectives = [(label, rids) for key, label, rids in OBJECTIVE_REFS if any(key in r.tc["template_id"] for r in runs)]
        objectives = [(lbl, rids) for i, (lbl, rids) in enumerate(objectives) if lbl not in [x for x, _ in objectives[:i]]]
        txn = sorted({s["code"] for r in runs for s in r.tc["steps"] if s["code"]})
        prof = tdv.profile(runs[0].tc)
        totals = any(r.ctx["roots"].get(s["step"]) == tdv.TOTALS_ROOT for r in runs for s in r.tc["steps"])
        device = [r.id for r in runs if not r.tc["steps"]]
        rows = [("Message / Data Section 1", "Message type per step" + (
            " (EMV Financial Transaction Request when Segment 130 is sent; Financial Transaction Request on follow-on steps)" if emv
            else "") + ", Element 55 `ATL105`, Element 63 = segments present", ["S11.1-DS1"] + (["S11.8-EMV", "S11.8-130"] if emv else [])),
                ("Segment 100 (17 fields)", "Presence, character type, length and valid values of every field; Prompt Code "
                 f"Transaction Type(s) {', '.join(txn)} for the step roles; Segment Length recomputed from the fields plus Field "
                 "Separators; run-time placeholders",
                 ["S12.1-REQ", "S12.1-LEN", "EL5-REV", "EL33-FMT", "EL44", "EL58", "EL78", "EL84", "EL86-FMT", "EL102", "EL121",
                  prof["card_ref"]] + [f"APPG-{c}" for c in txn if f"APPG-{c}" in self.refs.catalog]),
                ("Card data (Elements 2 + 12)", "; ".join(card) + "; card-number check digit"
                 + (f" and {prof['bin_text']}" if prof["bin_text"] else ""), crefs + ["EL2"]),
                ("Segment 111", ", ".join(tables) + "; Element 112 = data length; Segment Length", trefs)]
        if prof["debit"]:
            rows.append(("Debit rules (Section 10.2)", f"Card type {prof['card_type']} in the Prompt Code; PIN block (Element 33, "
                         "DUKPT) on every request except the Purchase Reversal and follow-on messages; no PIN block on follow-on "
                         "messages; no manual (keyed) entry; no Time-out Reversal of a Completion",
                         ["S10.2.1.3-PIN", "S10.2.1.3-DUKPT", "S10.2.1.4", "S10.2.5.1-PIN", "S10.2.2-TOR", prof["card_ref"]]))
        if totals:
            rows.append(("Totals Request (Segment 105, 17 fields)", "Data Section 1 + Segment 105 only; presence, type, length "
                         "and value of every field (Prompt Code 990, Totals Date, versions, Sequence Number, Card Label 4 "
                         "characters); Segment Length (14 Field Separators, none in the Category Total Data Section); count, "
                         "amount and grand total against the approved steps of the flow (Section 9.2)",
                         ["S11.4-TOTALS", "S12.6-TYPE", "S12.6-PROMPT", "S12.6-FS", "S12.6-NOFS", "S12.6-CAT", "EL13", "EL16-VAL",
                          "EL15", "EL42", "EL105", "S9.2-GT", "S9.2-PUR", "S9.2-REV", "S9.2-RET"]))
        if emv:
            rows += [("Segment 130 (9 fields)", "Elements 187-192 and 118; EMV Chip Data TLV decode; EMV Additional Information "
                      "(Appendix T); Segment Length recomputed (chip data counted in binary bytes)",
                      ["S12.20-LEN", "EL187", "EL188", "EL189", "EL190-TLV", "EL191", "EL192", "EL118-EMV", "APPR-PKT", "APPT-1"]),
                     ("EMV cross-field", "9F02 vs amount (with the cash back when sent), 9F03 vs Cash Amount, 9C vs "
                      "transaction type, AID (9F06/84) present, 5F2A/9F1A, 5F34 vs Element 188, 9F27, 9F34, Tags 5A/57 absent",
                      ["EL190-AID", "EL190-NO5A57", "EL190-EXACT", "APPR-9F02"])]
        rows += [("Test objective", "The data can prove what the scenario claims: " + ", ".join(lbl for lbl, _ in objectives),
                  [rid for _, rids in objectives for rid in rids]),
                 ("Step-to-step links", "Sequence Number, Account Number, terminal and entry mode against the original step"
                  + ("; new Sequence Number and a different card on the split-tender step" if "split_tender" in roles else "")
                  + ("; new Sequence Number and the same card on the stored-credential step" if "subsequent" in roles else ""),
                  lrefs),
                 ("Expected response codes", "Expected Response Code of each step fits the step; open ('?') codes are listed",
                  [f"EL83-{c}" for c in codes_rc if f"EL83-{c}" in self.refs.catalog] or ["EL83-0"]),
                 ("TC-declared checks", "Every `rule_checks` and `link_defs` entry of the test case executed against the test data",
                  []),
                 ("Generation fidelity", "Every test data value compared with the test case request value", []),
                 ("Spec citations", f"Every `spec_basis` quote and cited page of the test case checked against the "
                                    f"{self.refs.spec.version} text", [])]
        if device:
            rows.append(("Device-only test cases", f"{join(device)}: no host message, so no test data; the ATL105 text each "
                         "quotes is checked on its page and its device procedure is listed in its report", []))
        L = ["## 1. What was validated", "", "Every field of every test data file was checked against the ATL105 layouts and "
             "element definitions:", "", "| Area | Checks | Spec reference |", "|---|---|---|"]
        L += [f"| {a} | {c} | {self.compact(rids) if rids else 'TC catalog'} |" for a, c, rids in rows]
        L += ["", "Verdicts: **PASS** conforms to ATL105 · **FAIL** violates ATL105 or the test case's own objective · **BLOCKED** "
              "holds instruction text that must be replaced by a run-time value before sending · **REVIEW** not decidable from the "
              "spec or the test data alone (placeholder, BUYPASS-assigned or run-time value, spec gap, citation) · **INFO** note "
              "for the message builder.", "", "---", ""]
        return L

    def section2(self, mode, runs):
        L = ["## 2. Results by test case", "",
             "| Scenario | Test case | Test data files | Result | FAIL | BLOCKED | REVIEW | Defects |", "|---|---|---|---|---|---|---|---|"]
        for r in runs:
            L.append(f"| {md(r.scenario)} | {r.id} ({r.tc['display_id']}) | {len(r.tc['steps'])} | **{r.rep.verdict()}** | "
                     f"{r.rep.count('FAIL')} | {r.rep.count('BLOCKED')} | {r.rep.count('REVIEW')} | {', '.join(r.defects) or '–'} |")
        L += ["", "Counts are findings in the per-test-case report (one finding can cover several fields). Defect IDs are the "
              "same in all scenario reports (catalog in the README).", "", "---", ""]
        return L

    def section3(self, mode, runs, common, emv):
        if emv:
            with130 = [(r, s["step"]) for r in runs for s in r.tc["steps"] if r.seg(s["step"], "130")]
            without = [(r, s["step"]) for r in runs for s in r.tc["steps"] if not r.seg(s["step"], "130")
                       and r.ctx["roots"].get(s["step"]) != tdv.TOTALS_ROOT]
            rev = [(r, n) for r, n in without if r.st(n)["role"] in REVERSAL_ROLES]
            other = [(r, n) for r, n in without if (r, n) not in rev]
            L = [f"## 3. Defects common to every test case ({len(with130)} EMV test data file{'s' if len(with130) > 1 else ''})", ""]
            if rev:
                L.append("Follow-on reversal files without Segment 130 (" + ", ".join(f"`{r.st(n)['td']}`" for r, n in rev)
                         + f") are allowed: {self.cite('S10.14.2.4-REV')}")
            if other:
                L.append(("" if not rev else "<br>") + "Other files without Segment 130: " + ", ".join(
                    f"`{r.st(n)['td']}` ({r.st(n)['role'].replace('_', ' ')})" for r, n in other) + " – see §6.")
            L.append("")
        else:
            L = ["## 3. Defects common to every test case", ""]
        if sum(1 for r in runs if r.tc["steps"]) == 1:
            L += ["Only one test case with test data in this scenario, so all its defects are listed in §4.", ""]
        elif not common:
            L += ["None – no defect occurs in every test case of this scenario.", ""]
        for d in common:
            L += self.render(d, [(r, f) for r in runs for f in r.hits(d)], mode, emv)
        return L + ["---", ""]

    def section4(self, mode, runs, specific, others, emv):
        L = ["## 4. Scenario-specific defects" if len(runs) > 1 else "## 4. Defects of the test case", ""]
        if not specific and not others:
            L += ["None.", ""]
        for d in specific:
            L += self.render(d, [(r, f) for r in runs for f in r.hits(d)], mode, emv)
        if others:
            L += ["### Other FAIL findings", "", "| TC | Step | Field | Value | Why | What it should be |", "|---|---|---|---|---|---|"]
            L += [f"| {r.id} | {f['step'] or '-'} | {md(f['label'])} | `{md(short(f['value'], 40))}` | {md(f['why'])} | {md(f['fix'])} |"
                  for r, f in others]
            L.append("")
        follow = [r for r in runs if any(s["role"] in tcv.SAME_SEQ_ROLES for s in r.tc["steps"])]
        if follow:
            L += ["### Follow-on steps checked against their original step", ""]
        for r in follow:
            L += self.render_reversal(r, emv)
        return L + ["---", ""]

    def render(self, d, hits, mode, emv):
        fn = getattr(self, "render_" + d["id"].replace("-", ""), None)
        if fn:
            return fn(d, hits, mode)
        key = RULE_KEY.get(hits[0][1]["rule"])
        key, nth = key if isinstance(key, tuple) else (key, 1)
        return self.render_generic(d, hits, key, nth=nth)

    @staticmethod
    def field_key(f):
        """JSON key at the end of a finding's field path ('file -> segment -> Key'), if it is one."""
        key = str(f.get("field", "")).split("->")[-1].strip()
        return key if re.fullmatch(r"[A-Za-z0-9]+", key) else None

    # ------------------------------------------------------------ defect renderers
    def render_D01(self, d, hits, mode):
        rows = []
        for r, f in hits:
            n = f["step"]
            s = r.seg(n, "130")
            tag = r.ctx["emv"][n].get("5F34")
            rows.append((r, n, r.line_of(n, "EMVCardSequenceNumber"), s.get("EMVCardSequenceNumber", ""), tag or "-",
                         tag.zfill(3) if tag else "(3 spaces)", s.get("SegmentLength", "")))
        width = len(rows[0][3])
        L = [f"### D-01 – {hits[0][1]['title']}", "", "| | |", "|---|---|",
             f"| Location | `\"EMV Request Data Segment\" → \"EMVCardSequenceNumber\"`, **line "
             f"{join(sorted({x[2] for x in rows}))}** of each EMV test data file |",
             f"| Generated value | {join(f'`{v}`' for v in sorted({x[3] for x in rows}))} |",
             f"| Expected | {join(f'`{v}`' for v in sorted({x[5] for x in rows}))} |",
             f"| Spec | {self.cite('EL188')}<br>{self.cite('EL188-VAL')} |",
             f"| Certainty | **Certain.** The chip data carries Tag 5F34 = {join(f'`{v}`' for v in sorted({x[4] for x in rows}))}, so "
             "the value is right but the width is wrong. |",
             f"| Side effect | The declared Segment 130 length ({join(f'`{v}`' for v in sorted({x[6] for x in rows}))}) was computed "
             f"with the {width}-byte value and changes by {3 - width:+d} when corrected. |", "",
             "#### D-01 evidence by file", "", "| Test case | Test data file | Line | Element 188 | Tag 5F34 | Expected |",
             "|---|---|---|---|---|---|"]
        L += [f"| {r.id} | {r.st(n)['td']} | {ln} | `{v}` | `{t}` | `{e}` |" for r, n, ln, v, t, e, _ in rows]
        return L + [""]

    def render_D02(self, d, hits, mode):
        rows = []
        for r, f in hits:
            n = f["step"]
            s = r.seg(n, "130")
            rows.append((r, n, f"{r.line_of(n, 'EMVAdditionalInformationIndicator')}–{r.line_of(n, 'EMVAdditionalInformation')}",
                         s.get("EMVAdditionalInformationIndicator", ""), s.get("EMVAdditionalInformationLength", ""),
                         s.get("EMVAdditionalInformation", "")))
        r0 = hits[0][0]
        src = (r0.rec.get("steps") or [dict(request=r0.rec["request"])])[0]["request"][SEG_NAMES["130"]]
        attr = src.get("EMV Additional Information") or {}
        flag = next((g for g in attr.get("flags") or []), "")
        by_value = {}
        for r, n, *_, info in rows:
            by_value.setdefault(info, []).append(r.id)
        reused = [(v, tdv.uniq(ids)) for v, ids in by_value.items() if len(tdv.uniq(ids)) > 1]
        info0 = rows[0][5]
        charset = "A–Z/0–9 " if all(re.fullmatch(r"[A-Z0-9]+", x[5]) for x in rows) else ""
        ln0 = rows[0][4]
        L = [f"### D-02 – {d['title']}", "", "| | |", "|---|---|",
             f"| Location | `\"EMV Request Data Segment\"`, **lines {join(sorted({x[2] for x in rows}))}** of each EMV test data file |",
             f"| Generated value | Indicator `{rows[0][3]}`, Length `{ln0}`, Information = {len(info0)} generated {charset}characters "
             f"(e.g. `{short(info0)}`) |",
             "| Expected | Indicator `001` with EMV Table Data value **`EMVYES`** or **`EMVNOT`** |",
             f"| Spec chain | 1. **E191** ({self.pg('EL191')}): *\"{self.q('EL191')}\"* → `001` PASS<br>2. **E192** "
             f"({self.pg('EL192')}): *\"{self.q('EL192')}\"* → `{ln0}` **PASS on its own**<br>3. **E118 EMV Additional "
             f"Information** ({self.pg('EL118-EMV')}): *\"{self.q('EL118-EMV')}\"*<br>4. **Appendix T-1** ({self.pg('APPT-1')}): "
             f"EMV Table Data – *\"{self.q('APPT-1-LEN')}\"*; request values *\"{self.q('APPT-1')}\"* / *\"{self.q('APPT-1-NOT')}\"* "
             "→ **FAIL** |",
             f"| Root cause | The test case marks this value `method: {md(attr.get('method', '?'))}`"
             + (f" with flag `{md(flag)}`" if flag else "") + f". The generator filled the generic {len(info0)}-character "
             "maximum instead of applying Appendix T."
             + (" The same generated string is reused across test cases (" + "; ".join(
                 f"{'/'.join(ids)} → `{v[:4]}…`" for v, ids in reused) + ")." if reused else "") + " |",
             "| Certainty | **Certain** that the content is invalid. **Not certain** what the exact corrected Element 192 value is "
             "(see §6, Q-01). |", "",
             f"**Clarification on Element 192:** Element 192 = `{ln0}` is a valid value in range and equals the actual content length, "
             "so the report marks it **PASS**. The defect is the **content** of Element 118. Element 192 is consistent with that "
             "content, but the content itself is not allowed for indicator `001`.", "",
             "#### D-02 evidence by file", "", "| Test case | Test data file | Lines | Indicator | Length | Value (start…end) |",
             "|---|---|---|---|---|---|"]
        L += [f"| {r.id} | {r.st(n)['td']} | {lines} | {ind} | {ln} | `{short(info)}` |" for r, n, lines, ind, ln, info in rows]
        return L + [""]

    def render_D03(self, d, hits, mode):
        L = [f"### D-03 – {d['title']} (T2)", "", "| TC | File : line | Element 12 value | Expiry (YYMM) | Expected |",
             "|---|---|---|---|---|"]
        notes = []
        for r, f in hits:
            n = f["step"]
            cdb, entry = r.ctx["flats"][n]["CardDiscretionaryBlockData"], r.ctx["entries"].get(n)
            yymm = cdb[2:] + cdb[:2] if entry == "01" else cdb[:4]
            L.append(f"| {r.id} | {r.st(n)['td']} : {r.line_of(n, 'CardDiscretionaryBlockData')} | `{cdb}` | `{yymm}` = "
                     f"{tdv.expiry(yymm[2:] + yymm[:2])} | earlier than `{TODAY:%y%m}` |")
            emv = r.ctx["emv"].get(n)
            if emv is not None:
                tvr = emv.get("95", "")
                if len(tvr) >= 4 and not int(tvr[2:4], 16) & 0x40:
                    notes.append(f"TVR (Tag 95 = `{tvr}`) byte 2 \"Expired application\" is not set")
                if "5F24" not in emv:
                    notes.append("Tag 5F24 (Application Expiration Date) is absent")
            notes += [f"The TC already lists *\"{md(o)}\"* as an open client input" for o in r.rec.get("open_items") or []]
        L += ["", f"Spec: {self.cite('S10.14.2.1')}; {self.cite('EL12-EXP')}. The test cannot exercise its objective with a "
              "valid card." + "".join(f" {x}." for x in tdv.uniq(notes)), ""]
        return L

    def render_D04(self, d, hits, mode):
        L = [f"### D-04 – {d['title']} (T3)", "", "| TC | Finding | Location | Value | Expected |", "|---|---|---|---|---|"]
        r0 = hits[0][0]
        for r, f in hits:
            n = f["step"]
            pin, cvm = r.ctx["flats"][n]["EncryptedPINBlockData"], r.ctx["emv"].get(n, {}).get("9F34", "")
            if not pin:
                L.append(f"| {r.id} | Encrypted PIN Block Data (Element 33) empty | {r.st(n)['td']} line "
                         f"{r.line_of(n, 'EncryptedPINBlockData')} | `\"\"` | DUKPT KSN + PIN block – *\"{self.q('EL33-FMT')}\"* "
                         f"({self.pg('EL33-FMT')}) |")
            if cvm:
                code = int(cvm[:2], 16) & 0x3F
                L.append(f"| {r.id} | CVM Results (Tag 9F34) = {tdv.CVM_NAMES.get(code, 'other')} | EMVChipData, Tag 9F34 | "
                         f"`{cvm}` (CVM code {code:02X}) | CVM code 02 / 42 (enciphered PIN verified online) – EMV Book 3 |")
        t1 = [r.id for r in self.mode_runs(mode) if r.tc["template_id"].startswith("T1-")]
        execution = "; ".join(e for lg in r0.rec.get("tx_legs") or [] for e in lg.get("execution") or [])
        opens = r0.rec.get("open_items") or []
        L += ["", "**Clarification – this is not \"credit always needs a PIN\".** For credit, Element 33 is optional "
              f"({self.cite('S10.14.3-OPT')}; {self.cite('EL33-REQ')}). So an empty Element 33 is **correct** for normal credit "
              f"transactions (e.g. {join(t1) or 'the T1 test case'}), and D-04 is not raised there.", "",
              "D-04 applies only to the **online-PIN test cases**, because:",
              f"- {self.cite('S10.14.3-ONLINE')}; and {self.cite('S10.14.3-MC')}",
              f"- The TC objective is *\"{md(r0.rec.get('test_objective', ''))}\"*, and its execution step is *\"{md(execution)}\"*.",
              "- The generated data has no PIN block and a signature CVM, so it is a signature credit transaction (same as T1), "
              "not an online-PIN test.", "",
              "**Classification:** test-objective defect, **blocked by client input**." + (
                  f" The TC itself lists *\"{md(opens[0])}\"* as an open item; without them a valid PIN block cannot be generated."
                  if opens else ""), "",
              "**Ask to the AI Dev team:** do **not** add a PIN to credit test data in general. For online-PIN test cases only, "
              "either populate Element 33 (DUKPT KSN + PIN block) and set Tag 9F34 to online PIN once the test card and key are "
              "supplied, or mark the TC **blocked – needs client input** instead of releasing signature data under an online-PIN "
              "objective.", ""]
        return L

    def render_D05(self, d, hits, mode):
        L = [f"### D-05 – {d['title']} (T4)", ""]
        for r in tdv.uniq(r for r, _ in hits):
            n = next(f["step"] for rr, f in hits if rr is r)
            st = r.st(n)
            of = st.get("of", 1)
            s100n, s100o = r.seg(n, "100"), r.seg(of, "100")
            orig, seglen, ph = s100o.get("NonfuelAmount", ""), s100n.get("SegmentLength", ""), s100n.get("NonfuelAmount", "")
            prov = str(st["tc_request"].get("Nonfuel Amount") or "")
            base = tdv.seg100_length(tdv.td_seg100_values(s100n), skip=("Nonfuel Amount",))
            implied = int(seglen) - base if seglen.isdigit() else None
            a9f02, o9f02 = r.ctx["emv"].get(n, {}).get("9F02"), r.ctx["emv"].get(of, {}).get("9F02")
            ex = int(orig) * 2 // 3
            left = int(orig) - ex
            ln_amt, ln_len = r.line_of(n, "NonfuelAmount"), r.line_of(n, "SegmentLength")
            idx = [s["step"] for s in r.tc["steps"]].index(n)
            L += ["| TC | Finding | Location | Value | Expected |", "|---|---|---|---|---|"]
            if a9f02 and r.fails({"EMV_CHIP_AMOUNT_NOT_REMAINDER"}):
                L.append(f"| {r.id} | Step {n} Tag 9F02 (Amount, Authorized) | `{st['td']}`, EMVChipData | `{a9f02}` (= step {of} "
                         f"full amount) | Remaining balance = requested – Approved Amount (Element 6) of the partial approval "
                         f"({self.pg('S10.1.2.1-REMAIN')}) |")
            if r.fails({"SEGMENT_LENGTH_IMPOSSIBLE"}):
                L.append(f"| {r.id} | Step {n} Segment 100 length vs amount | `{st['td']}`, SegmentLength `{seglen}` (line {ln_len}) | "
                         f"Declared length implies {'an' if str(implied).startswith('8') else 'a'} **{implied}-digit** Nonfuel Amount | "
                         "Element 58: at most **7 digits** for "
                         f"non-Amex cards ({self.pg('EL58')}) |")
            if r.fails({"PROVISIONAL_AMOUNT_TOO_LONG", "AMOUNT_NOT_POSSIBLE"}):
                L.append(f"| {r.id} | TC provisional step {n} amount | `test_cases.jsonl` line {r.line} → `steps[{idx}].request` → "
                         f"`Nonfuel Amount` | `{prov}` | Less than `{orig}` and at most 7 digits |")
            L += ["", f"The test data itself correctly carries a run-time expression for step {n} `NonfuelAmount` (`{md(ph)}`). That "
                  "is BLOCKED until it is filled in, not a defect. " + ("The chip amount and the declared Segment Length were "
                                                                      if a9f02 else "The declared Segment Length was ")
                  + "generated from the invalid provisional value.", "",
                  "#### D-05 explained with a simple example (for the AI Dev team)", "", "**The story the test is meant to tell**", "",
                  f"A customer buys something for **{tdv.amount(orig)}** and pays with two cards:", "",
                  f"1. **Card A** (`{s100o.get('AccountNumber')}`) is asked for {tdv.amount(orig)} but only has, say, "
                  f"**{tdv.amount(ex)}** available. The issuer approves only {tdv.amount(ex)} (partial approval, Response Code `F`).",
                  f"2. The device asks for the **remaining {tdv.amount(left)}**, and the customer pays it with **Card B** "
                  f"(`{s100n.get('AccountNumber')}`).", "",
                  f"Card B must be charged only **the leftover**: {tdv.amount(orig)} − {tdv.amount(ex)} = {tdv.amount(left)}. The "
                  f"leftover is always **less than {tdv.amount(orig)}**, because Card A paid something ({self.cite('S10.1.2.1-REMAIN')}).",
                  "", "**What the generated test data contains**", "", f"Step {of}, Card A (`{r.st(of)['td']}`):", "",
                  "| Field | Value | Meaning | OK? |", "|---|---|---|---|",
                  f"| NonfuelAmount (line {r.line_of(of, 'NonfuelAmount')}) | `{orig}` | {tdv.amount(orig)} | ✅ |"]
            if o9f02:
                L.append(f"| Chip amount, Tag 9F02 | `{o9f02}` | {tdv.amount(str(int(o9f02)))} | "
                         f"{'✅ matches' if int(o9f02) == int(orig) else '❌ differs'} |")
            L += ["", f"Step {n}, Card B (`{st['td']}`):", "", "| Field | Value | Meaning | OK? |", "|---|---|---|---|",
                  f"| NonfuelAmount (line {ln_amt}) | `{md(ph if len(ph) <= 28 else ph[:24] + '…>')}` | \"Fill in the leftover at run time\" (e.g. "
                  f"{tdv.amount(left)}) | ✅ correct approach |"]
            if a9f02:
                ok = int(a9f02) < int(orig)
                L.append(f"| Chip amount, Tag 9F02 | `{a9f02}` | **{tdv.amount(str(int(a9f02)))}** | {'✅' if ok else '❌ wrong'} |")
            if implied:
                L.append(f"| SegmentLength (line {ln_len}) | `{seglen}` | Calculated as if the amount had **{implied} digits** | "
                         f"{'❌ wrong' if r.fails({'SEGMENT_LENGTH_IMPOSSIBLE'}) else '✅'} |")
            k = 0
            L.append("")
            if a9f02 and r.fails({"EMV_CHIP_AMOUNT_NOT_REMAINDER"}):
                k += 1
                L += [f"**Problem {k}: Card B's chip says {tdv.amount(str(int(a9f02)))}.** The request asks Card B to pay the "
                      f"leftover (e.g. {tdv.amount(left)}), but Card B's chip data says it is paying the full "
                      f"{tdv.amount(str(int(a9f02)))}, copied from Card A. It is like a receipt for the second card showing "
                      f"\"{tdv.amount(str(int(a9f02)))}\" when only {tdv.amount(left)} was owed. Whatever Card A gets approved, the "
                      f"leftover is always less than {tdv.amount(orig)}, so `{int(a9f02)}` on Card B is always wrong.", ""]
            if implied and r.fails({"SEGMENT_LENGTH_IMPOSSIBLE", "PROVISIONAL_AMOUNT_TOO_LONG", "AMOUNT_NOT_POSSIBLE"}):
                k += 1
                bullets = []
                if prov.isdigit() and int(prov) >= int(orig):
                    bullets.append(f"- It is **larger than the {tdv.amount(orig)} bill**, and a leftover can never exceed the bill.")
                if len(prov) > tdv.NON_AMEX_AMOUNT_MAX:
                    bullets.append(f"- {self.cite('EL58')} It has {len(prov)} digits.")
                L += [f"**Problem {k}: the length was counted with an impossible amount.** `SegmentLength` = {int(seglen)} is the "
                      f"character count of Segment 100. All other fields plus separators add up to **{base}**, so {int(seglen)} − "
                      f"{base} = **{implied} characters** were reserved for the amount. The {implied}-digit value the generator "
                      f"used is in its own test case (`test_cases.jsonl` line {r.line} → {r.id} → `steps[{idx}].request` → "
                      f"`Nonfuel Amount`): **`{prov}` = {tdv.amount(prov)}**." + (" That is wrong twice:" if len(bullets) > 1
                                                                               else " That is wrong:"), ""] + bullets
                L += ["", f"A real leftover such as {tdv.amount(left)} (`{left}`, {len(str(left))} digits) gives a Segment Length "
                      f"of **{base + len(str(left)):03d}**, not {seglen}.", ""]
            L += [f"**In one line:** Card B should pay only the leftover (less than {tdv.amount(orig)}), but the generator "
                  + (f"gave Card B's chip **{tdv.amount(str(int(a9f02)))}** and " if a9f02 and r.fails({'EMV_CHIP_AMOUNT_NOT_REMAINDER'})
                     else "") + f"built the message length around an impossible amount of **{tdv.amount(prov)}**.", "",
                  "**What the generator must do next run**", "", "| # | Rule | Check before release |", "|---|---|---|",
                  f"| 1 | Step {n} amount = step {of} requested amount − step {of} Approved Amount (Element 6 from the step {of} "
                  "response). Keep it as a run-time value; never synthesize a random amount. | "
                  f"`1 ≤ step {n} amount < step {of} amount` |",
                  "| 2 | Any provisional/placeholder amount must still satisfy Element 58: at most 7 digits for non-Amex cards. | "
                  "Length check against the card network |"]
            i = 3
            if a9f02:
                L.append(f"| {i} | Step {n} chip Tag 9F02 must be built from the **step {n}** amount (Card B's own transaction), not "
                         f"copied from step {of}. | `9F02 == step {n} NonfuelAmount` (12 digits, zero-padded) |")
                i += 1
            L += [f"| {i} | Segment 100 `SegmentLength` must be computed **after** the run-time amount is substituted. | "
                  "Recompute: sum of fields + one separator per field |", ""]
        return L

    def render_D06(self, d, hits, mode):
        L = [f"### D-06 – {d['title']}", "", "| TC | File : lines | Table 005 | Table 031 | Expected |", "|---|---|---|---|---|"]
        for r, f in hits:
            n = f["step"]
            L.append(f"| {r.id} | {r.st(n)['td']} : {r.line_of(n, 'VariableInformation', 1)}, {r.line_of(n, 'VariableInformation', 2)} "
                     f"| `{r.ctx['t005'].get(n)}` | `{r.ctx['t031'].get(n)}` | {md(f['fix'])} |")
        f0 = hits[0][1]
        L += ["", md(f0["why"]), "", "Spec:"] + [f"- {self.cite(rid)}" for rid in f0["refs"]] + [""]
        return L

    def render_D07(self, d, hits, mode):
        L = [f"### D-07 – {d['title']} (T2)", "", "| TC | File : lines | Account Number | Card Discretionary Block Data | Expected |",
             "|---|---|---|---|---|"]
        for r, f in hits:
            n = f["step"]
            fl = r.ctx["flats"][n]
            L.append(f"| {r.id} | {r.st(n)['td']} : {r.line_of(n, 'AccountNumber')}, {r.line_of(n, 'CardDiscretionaryBlockData')} | "
                     f"`{fl['AccountNumber']}` | `{fl['CardDiscretionaryBlockData']}` | {md(f['fix'])} |")
        r0, f0 = hits[0]
        execution = "; ".join(e for lg in r0.rec.get("tx_legs") or [] for e in lg.get("execution") or [])
        L += ["", md(f0["why"]), "", f"The test case's own execution step says: *\"{md(execution)}\"*.", "", "Spec:"]
        L += [f"- {self.cite(rid)}" for rid in f0["refs"]] + [""]
        return L

    def render_generic(self, d, hits, key, note="", nth=1):
        """Defect table: one row per test data file, then the reason and the spec text."""
        L = [f"### {d['id']} – {d['title']}", "", "| TC | Step | File : line | Value | What it should be |", "|---|---|---|---|---|"]
        for r, f in hits:
            n = f["step"]
            k = key or self.field_key(f)
            L.append(f"| {r.id} | {n} ({r.st(n)['role'].replace('_', ' ')}) | {r.st(n)['td']} : {r.line_of(n, k, nth) if k else '-'} | "
                     f"`{md(short(f['value'], 48))}` | {md(f['fix'])} |")
        r0, f0 = hits[0]
        L += ["", f"**Why ({r0.id}):** {md(f0['why'])}", ""] + ([note, ""] if note else []) + ["Spec:"]
        L += [f"- {self.cite(rid)}" for rid in tdv.uniq(rid for _, f in hits for rid in f["refs"])] + [""]
        return L

    def render_D08(self, d, hits, mode):
        r0 = hits[0][0]
        execution = "; ".join(e for lg in r0.rec.get("tx_legs") or [] for e in lg.get("execution") or [])
        return self.render_generic(d, hits, "CardDiscretionaryBlockData",
                                   f"The test case's own execution step says: *\"{md(execution)}\"*." if execution else "")

    def render_D09(self, d, hits, mode):
        return self.render_generic(d, hits, "VariableInformationSection")

    def render_D10(self, d, hits, mode):
        return self.render_generic(d, hits, "VariableInformationSection")

    def render_D11(self, d, hits, mode):
        return self.render_generic(d, hits, "EMVChipData")

    def render_D12(self, d, hits, mode):
        note = ("This rule was added in this run; the first version of this report did not check it." if mode["key"] in fixed_samples()
                else "")
        return self.render_generic(d, hits, "VariableInformationSection", note)

    def render_D13(self, d, hits, mode):
        return self.render_generic(d, hits, "CardDiscretionaryBlockData")

    def render_D14(self, d, hits, mode):
        L = [f"### D-14 – {d['title']}", "", "| TC | Step | Finding | Location | Value | Expected |", "|---|---|---|---|---|---|"]
        for r, f in hits:
            n = f["step"]
            L.append(f"| {r.id} | {n} ({r.st(n)['role'].replace('_', ' ')}) | {md(f['title'])} | {md(f['field'])} | "
                     f"`{md(short(f['value'], 40))}` | {md(f['fix'])} |")
        for r in tdv.uniq(r for r, _ in hits):
            n = next(f["step"] for rr, f in hits if rr is r)
            st = r.st(n)
            of = st.get("of", 1)
            orig = r.ctx["flats"][of].get("NonfuelAmount", "")
            prov = str(st["tc_request"].get("Nonfuel Amount") or "")
            ph = r.ctx["flats"][n].get("NonfuelAmount", "")
            orc = r.st(of).get("expected_rc")
            role = st["role"].replace("_", " ")
            L += ["", f"**{r.id} in plain words:** step {of} asks for {tdv.amount(orig)}"
                  + (f" and expects Response Code `{orc}`" + (" (partial approval)" if orc == "F" else "") if orc else "")
                  + f". The {role} in step {n} must carry the amount from the step {of} response, so the test data correctly uses a "
                  f"run-time expression (`{md(short(ph, 40))}`). But the test case also holds a provisional value `{prov}`"
                  + (f" = {tdv.amount(prov)}" if prov.isdigit() else "")
                  + (f" ({len(prov)} digits; Element 58 allows at most {tdv.NON_AMEX_AMOUNT_MAX} for MasterCard)"
                     if prov.isdigit() and len(prov) > tdv.NON_AMEX_AMOUNT_MAX else "")
                  + (f", larger than the {tdv.amount(orig)} that was asked for" if prov.isdigit() and orig.isdigit()
                     and int(prov) > int(orig) else "")
                  + ", and the generator computed the declared Segment Length from it."]
        f0 = hits[0][1]
        L += ["", "Spec:"] + [f"- {self.cite(rid)}" for rid in tdv.uniq(rid for _, f in hits for rid in f["refs"])] + [""]
        L += ["**What the generator must do next run:** keep the follow-on amount a run-time value taken from the original step's "
              "response, make any provisional amount satisfy Element 58 (at most 7 digits, not more than the original amount), "
              "and compute the Segment Length after substitution.", ""]
        return L

    def render_reversal(self, r, emv):
        follow = [s for s in r.tc["steps"] if s["role"] in tcv.SAME_SEQ_ROLES]
        L = [f"#### {md(r.scenario)} ({r.id})", ""]
        for st in follow:
            n, of = st["step"], st.get("of", 1)
            role, orole = st["role"].replace("_", " "), r.st(of)["role"].replace("_", " ")
            before = tdv.uniq(dd["id"] for dd in DEFECTS for f in r.hits(dd) if f["step"] == of)
            here = tdv.uniq(dd["id"] for dd in DEFECTS for f in r.hits(dd) if f["step"] == n)
            rows = {(x["el"], x["seg"]): x for x in r.rep.rows if x["step"] == n}
            L += [f"Step {n} ({role}) against step {of} ({orole}): "
                  + (f"{join(before)} appl{'y' if len(before) > 1 else 'ies'} to step {of}; " if before else f"no defect on step {of}; ")
                  + (f"step {n} has {join(here)}." if here else f"step {n} has no defect."), "",
                  "| Check | Value | Result |", "|---|---|---|"]
            msg = rows.get(("-", "Message"))
            pc = rows.get(("78", "100"))
            if msg and pc:
                res = next((v for v in ("FAIL", "BLOCKED", "REVIEW") if v in (msg["result"], pc["result"])), "PASS")
                L.append(f"| Physical message `{msg['td']}`, Prompt Code `{pc['td']}` ({md(pc['rule'])}) | – | "
                         f"{res + ' (see §6)' if res == 'REVIEW' else res} |")
            if emv:
                has130 = bool(r.seg(n, "130"))
                if st["role"] in REVERSAL_ROLES:
                    L.append(f"| Segment 130 on a reversal is optional ({self.shorts(['S10.14.2.4-REV'])}) | "
                             f"{'present' if has130 else 'absent'} | PASS |")
                else:
                    open_ = any(f["rule"] == "EMV_COMPLETION_NO_CHIP_DATA" and f["step"] == n for f in r.rep.findings)
                    L.append(f"| Segment 130 on the {role} | {'present' if has130 else 'absent'} | "
                             f"{'REVIEW (see §6)' if open_ else 'PASS'} |")
            for x in r.rep.links:
                if x["step"] == n:
                    note = (f" – {self.cite('APPJ-FOOT1')}" if x["check"].startswith("Same POS entry mode")
                            and x["value"] != x["original"] else "")
                    L.append(f"| {md(x['check'])}{note} | `{x['value']}` (original `{x['original']}`) | {x['result']} |")
            a, b = r.ctx["flats"][of]["NonfuelAmount"], r.ctx["flats"][n]["NonfuelAmount"]
            if not tdv.PLACEHOLDER.match(b):
                L.append(f"| Amount = step {of} amount | `{b}` (step {of} `{a}`) | {'PASS' if a == b else 'REVIEW'} |")
            for el in ("12", "121"):
                x = rows.get((el, "100"))
                if x:
                    L.append(f"| {md(x['name'])}: {md(x['rule'])} | `{md(short(x['td'], 40))}` | {x['result']} |")
            x5, x84 = rows.get(("5", "100")), rows.get(("84", "100"))
            if x5:
                label = (f"is a run-time link to the step {of} response" if tdv.PLACEHOLDER.match(x5["td"])
                         else md(short(x5["rule"], 120)))
                L.append(f"| Approval Number (Element 5) {label} | `{md(short(x5['td'], 40))}` | {x5['result']} |")
            if x84:
                L.append(f"| Segment Length: {md(x84['rule'])} | `{x84['td']}` | {x84['result']} |")
            for f in r.rep.findings:
                if f["rule"] == "CITATION_WRONG_SECTION" and f["step"] == n:
                    L.append(f"| Test case cites {md(f['value'])} for the Approval Number rule | {md(f['fix'])} | REVIEW |")
            L.append("")
        return L

    # ------------------------------------------------------------ §5 - §8
    def section5(self, mode, runs, emv):
        areas = [("Message / Data Section 1", {"Message", "Data Section 1"}), ("Segment 100", {"100"}), ("Segment 111", {"111"})]
        if emv:
            areas.append(("Segment 130 and EMV chip data", {"130"}))
        if any(x["seg"] == "105" for r in runs for x in r.rep.rows):
            areas.append(("Segment 105 (Totals Request)", {"105"}))
        nfiles = sum(len(r.tc['steps']) for r in runs)
        L = ["## 5. What passes", "", (f"Aggregated over all {nfiles} test data files of this scenario." if nfiles > 1
                                      else "Based on the single test data file of this scenario."),
             "", "| Area | Passes in every test data file | Not in every file (see §3, §4, §6) |", "|---|---|---|"]
        for area, segs in areas:
            stats = {}
            for r in runs:
                for x in r.rep.rows:
                    if x["seg"] in segs:
                        s = stats.setdefault(x["name"], dict(values=[], bad=[]))
                        if x["td"] not in s["values"]:
                            s["values"].append(x["td"])
                        if x["result"] != "PASS":
                            s["bad"].append(f"{x['result']} in {r.id} step {x['step']}")
            good = [f"{md(k)} {'/'.join(f'`{md(short(v, 28, 8))}`' for v in s['values'][:3])}" for k, s in stats.items() if not s["bad"]]
            bad = [f"{md(k)} ({'; '.join(tdv.uniq(s['bad']))})" for k, s in stats.items() if s["bad"]]
            L.append(f"| {area} | {'; '.join(good) or '–'} | {'; '.join(bad) or '–'} |")
        links = [(r, x) for r in runs for x in r.rep.links]
        if links:
            L.append(f"| Step-to-step links | {sum(1 for _, x in links if x['result'] == 'PASS')} of {len(links)} checks PASS | "
                     + ("; ".join(f"{md(x['check'])} ({x['result']} in {r.id})" for r, x in links if x["result"] != "PASS") or "–")
                     + " |")
        resp = [(r, x) for r in runs for x in r.rep.responses]
        if not resp:
            L.append("| Expected response codes | – | No expected Response Code is stated (see §6) |")
        else:
            L.append(f"| Expected response codes | {sum(1 for _, x in resp if x['result'] == 'PASS')} of {len(resp)} PASS ("
                     + ", ".join(sorted({f'`{x[1]["code"]}` = {x[1]["meaning"]}' for x in resp})) + ") | "
                     + ("; ".join(f"{r.id} step {x['step']} `{x['code']}`" for r, x in resp if x["result"] != "PASS") or "–") + " |")
        checks = [(r, c) for r in runs for c in r.rule_checks]
        ldefs = [(r, c) for r in runs for c in r.link_checks]
        skipped = sum(1 for _, c in checks if c[2] is None)
        L.append(f"| TC-declared `rule_checks` | {sum(1 for _, c in checks if c[2])} of {len(checks) - skipped} hold"
                 + (f" ({skipped} with an unsupported operation not executed)" if skipped else "") + " | "
                 + ("; ".join(f"{c[0]} ({r.id} step {c[1]})" for r, c in checks if c[2] is False) or "–") + " |")
        runtime = sum(1 for _, c in ldefs if c[3] is None)
        if ldefs:
            L.append(f"| TC-declared `link_defs` | {sum(1 for _, c in ldefs if c[3])} of {len(ldefs) - runtime} hold; {runtime} "
                     "run-time link(s) (BLOCKED until filled in) | "
                     + ("; ".join(f"{c[2]} {c[1]} ({r.id} step {c[0]})" for r, c in ldefs if c[3] is False) or "–") + " |")
        fid = [(r, f) for r in runs for f in r.rep.findings if f["rule"].endswith("_FIDELITY") or f["rule"] == "TD_DIFFERS_FROM_TC"
               or "differs from the test case request value" in f["why"]]
        L.append("| Generation fidelity (test data value = test case request value) | "
                 + ("Every value, except run-time placeholders (BLOCKED)" if not fid else "–") + " | "
                 + ("; ".join(f"{r.id}: {md(f['label'])}" for r, f in fid) or "–") + " |")
        quotes = [(r, x) for r in runs for x in r.rep.quotes]
        L.append(f"| Spec quotes in the test cases | {sum(1 for _, x in quotes if x['result'] == 'PASS')} of {len(quotes)} found "
                 f"word-for-word on the cited page | "
                 + ("; ".join(tdv.uniq(f"p.{x['page']}: {md(x['status'])}" for _, x in quotes if x["result"] != "PASS")) or "–")
                 + " |")
        notes = tdv.uniq((f["title"], f["why"], f["fix"]) for r in runs for f in r.rep.findings if f["severity"] == "INFO")
        if notes:
            L += ["", "**Note for the team that builds the wire message (INFO):**", ""]
            L += [f"- **{md(t)}** – {md(w)} {md(x)}" for t, w, x in notes]
        return L + ["", "---", ""]

    def questions(self, mode, runs, emv):
        qs = []
        if emv:
            chip = next((r.seg(s["step"], "130") for r in runs for s in r.tc["steps"] if r.seg(s["step"], "130")), {})
            hexd = chip.get("EMVChipData", "")
            qs.append(dict(item="Exact corrected value of Element 192 / framing of Element 118 for indicator 001",
                           why=(f"Appendix T ({self.pg('APPT-1')}) lists *\"{self.q('APPT-1-ID')}\"* and *\"{self.q('APPT-1-TLEN')}\"*"
                                " but never states the Table Length width. The value may be `EMVYES` alone (length `006`) or "
                                "framed with Table ID + Table Length (longer)."),
                           tcs="all EMV requests", refs=["APPT-1-ID", "APPT-1-TLEN", "APPT-1-LEN"],
                           needed="BUYPASS / SME or a certified device trace", rule="EMV_Q_FRAMING"))
            qs.append(dict(item=f"Packed-BCD 2-byte prefix (`{hexd[:4]}`) inside Element 190 counted in Element 189 "
                                f"(`{chip.get('EMVChipDataLength', '')}`)",
                           why=(f"Matches the Appendix R example (*\"{self.q('APPR-PKT')}\"*, {self.pg('APPR-PKT')}) and Element 190 "
                                f"(*\"{self.q('EL190-BIN')}\"*), but the field boundary is not stated explicitly."),
                           tcs="all EMV requests", refs=["APPR-PKT", "EL190-BIN", "EL189"],
                           needed="Producer adapter declaration / SME", rule="EMV_Q_BCD"))
        for title, tcs, f in tdv.review_groups([(r.tc, r.rep) for r in runs]):
            qs.append(dict(item=title, why=md(f["why"]), tcs=", ".join(tcs), refs=f["refs"], needed=NEEDED.get(f["rule"], "Test case owner"),
                           rule=f["rule"], fix=f["fix"]))
        opens = {}
        partial_q = {t for x in qs if x["rule"] == "PARTIAL_APPROVAL_PRECONDITION" for t in x["tcs"].split(", ")}
        for r in runs:
            if {"D-03", "D-04"} & set(r.defects):
                continue  # the D-03/D-04 sections already quote the test case's open items
            for o in r.rec.get("open_items") or []:
                if r.id in partial_q and "partial" in str(o).lower():
                    continue
                opens.setdefault(md(o if isinstance(o, str) else json.dumps(o)), []).append(r.id)
        for o, tcs in opens.items():
            qs.append(dict(item=o, why="Listed by the test case itself as an open item (input needed before execution).",
                           tcs=", ".join(tdv.uniq(tcs)), refs=[], needed="Client / test case owner", rule="TC_OPEN_ITEM"))
        ids = {r.id for r in runs}
        pins = Counter((r.ctx["t005"].get(s["step"]) or "")[2:3] for r in self.runs for s in r.tc["steps"]
                       if r.ctx["roots"].get(s["step"]) != tdv.TOTALS_ROOT)
        majority = pins.most_common(1)[0][0] if pins else ""
        minority = any((r.ctx["t005"].get(s["step"]) or "")[2:3] != majority for r in runs for s in r.tc["steps"]
                       if r.ctx["roots"].get(s["step"]) != tdv.TOTALS_ROOT)
        for c in self.cross:
            if c["result"] == "REVIEW" and c.get("listed") and minority and any(i in c["detail"] for i in ids):
                qs.append(dict(item=c["check"], why=md(c["detail"]), tcs=", ".join(sorted(i for i in ids if i in c["detail"])),
                               refs=c["refs"], needed="Test design", rule="CROSS", fix=c.get("fix", "")))
        for i, x in enumerate(qs, 1):
            x["id"] = f"Q-{i:02d}"
        return qs

    def section6(self, qs):
        L = ["## 6. Open questions / review items (not defects)", "",
             "| ID | Item | Why it is open | Test cases | Where in ATL105 | Needed from |", "|---|---|---|---|---|---|"]
        L += [f"| {x['id']} | {md(x['item'])} | {x['why']} | {x['tcs']} | {self.shorts(x['refs']) if x['refs'] else '–'} | "
              f"{x['needed']} |" for x in qs]
        return L + ["", "---", ""]

    def section7(self, mode, runs, present, qs, emv):
        ids = {d["id"] for d in present}
        qid = {x["rule"]: x["id"] for x in qs}
        recs = []
        if "D-01" in ids:
            recs.append("**E188:** zero-pad Tag 5F34 to 3 bytes (`001`), or send 3 spaces when 5F34 is absent (D-01).")
        if "D-02" in ids:
            recs.append("**E191/E192/E118:** for indicator `001`, populate EMV Table Data with `EMVYES` / `EMVNOT` per Appendix T-1 "
                        f"({self.pg('APPT-1')}), and set E192 to the resulting length once {qid.get('EMV_Q_FRAMING', 'Q-01')} is "
                        "confirmed. Do not synthesize free text from the 984 maximum; apply the Appendix T allowed values instead of "
                        "skipping `valid_values_parsed` (D-02).")
        if ids & {"D-01", "D-02"}:
            r, f = next((r, f) for r in runs for f in r.fails({"EMV_ADDL_INFO_CONTENT", "EMV_CSN_WIDTH"}))
            s = r.seg(f["step"], "130")
            info, csn, sl = s.get("EMVAdditionalInformation", ""), s.get("EMVCardSequenceNumber", ""), s.get("SegmentLength", "")
            fixed = int(sl) - len(info) + 6 + (3 - len(csn) if "D-01" in ids else 0)
            recs.append(f"**Recompute the Segment 130 length** after these fixes. With `EMVYES` and no framing, `{sl}` becomes "
                        f"`{fixed:04d}` ({6 - len(info):+d} for E118" + (f", {3 - len(csn):+d} for E188" if "D-01" in ids else "")
                        + ").")
        scen = []
        if "D-03" in ids:
            scen.append(f"T2 expired card: use a card whose expiry is earlier than the run date (before `{TODAY:%y%m}` as YYMM)"
                        + (", and include Tag 5F24 and the TVR bit \"expired application\" in the chip data" if emv else "") + " (D-03).")
        if "D-04" in ids:
            scen.append("T3 online PIN: populate E33 (DUKPT KSN + PIN block) and set 9F34 to online PIN (`42xxxx` / `02xxxx`), or "
                        "mark the test case blocked until the client supplies the card and key (D-04).")
        if "D-05" in ids:
            scen.append("T4 split tender: derive the step 2 amount" + (" and Tag 9F02" if emv else "") + " from the step 1 Approved "
                        "Amount. Never exceed 7 digits (non-Amex cards), and compute the Segment Length after substitution (D-05).")
        if "D-06" in ids:
            f = next(f for r in runs for f in r.fails({"TERMINAL_CAPABILITY_CONFLICT"}))
            scen.append(f"RFID terminal test: {md(f['fix'])} (D-06).")
        if "D-07" in ids:
            scen.append("T2 Track 1: when Track 2 is unreadable, send the unaltered Track 1 data – Account Number with format code "
                        "`B`, Card Discretionary Block Data after the first `^` (name, `^`, expiry, service code, discretionary "
                        "data), combined at most 76 characters (D-07).")
        if "D-08" in ids:
            scen.append("Entry modes that do not read the card (01 keyed, 79 chip fallback to manual): send only the expiry MMYY in "
                        "Element 12; never generate track data for them (D-08).")
        if "D-09" in ids:
            scen.append("Credential on File (entry mode 10): add Table 032 sub-table 08 (`C`, or `I` on the transaction that stores "
                        "the credential) and Table 049 sub-table 02 (Transaction Initiation Indicator)"
                        + (" and Table 056 sub-table 09 (MasterCard MIT/CIT category)" if PROGRAM.startswith("MasterCard") else "")
                        + " (D-09).")
        if "D-10" in ids:
            scen.append("AVS tests: send Table 003 with the street address / ZIP Code the client supplies for the test card (D-10).")
        if "D-11" in ids:
            scen.append("Offline-PIN tests: generate the chip data from an offline-PIN transaction (Tag 9F34 CVM code 01 or 04) "
                        "(D-11).")
        if "D-13" in ids:
            scen.append("Follow-on messages (time-out reversal, reversal, completion): send Account Number + expiry YYMM only "
                        "(truncated Track 2); never copy the full track data of the original (D-13).")
        if "D-14" in ids:
            scen.append("Follow-on amount after a partial approval: keep it a run-time value from the original response; any "
                        "provisional amount must be at most 7 digits and not more than the original, and the Segment Length is "
                        "computed after substitution (D-14).")
        if scen:
            recs.append("**Scenario fidelity:**" + "".join(f"\n   - {x}" for x in scen))
        if "D-12" in ids:
            recs.append("**Table 030 (POS Condition Code):** add it to every EMV request and every Credential on File request "
                        f"(10 digits, values from Appendix Q, for the certified terminal) – {self.cite('APPI-030')} (D-12).")
        for did in ("D-15", "D-16", "D-22", "D-23", "D-24", "D-17", "D-18", "D-19", "D-20", "D-21"):
            if did in ids:
                recs.append(f"**{DEFECT_DOC[did]['field']}:** {DEFECT_DOC[did]['fix']} ({did}).")
        rules = {x["rule"] for x in qs}
        if "EXPECTED_RESPONSE_OPEN" in rules:
            recs.append(f"**Expected responses:** replace the open `?` Response Codes with the code each step must return "
                        f"({qid['EXPECTED_RESPONSE_OPEN']}).")
        if "EMV_CONTACTLESS_TEMPLATE" in rules:
            recs.append("**Contactless:** generate contactless-specific chip data rather than reusing the contact template "
                        f"({qid['EMV_CONTACTLESS_TEMPLATE']}).")
        if "D-02" in ids:
            infos = [r.seg(s["step"], "130").get("EMVAdditionalInformation") for r in runs for s in r.tc["steps"]
                     if r.seg(s["step"], "130")]
            if len(set(infos)) < len(infos):
                recs.append("**Avoid reusing identical generated values** across different test cases (D-02).")
        if rules & {"CITATION_OUTDATED_PAGE", "CITATION_WRONG_SECTION", "QUOTE_CONTEXT", "QUOTE_PAGE_DRIFT", "QUOTE_WRONG_PAGE",
                    "QUOTE_PARAPHRASED"}:
            ids_q = ", ".join(x["id"] for x in qs if x["rule"].startswith(("CITATION", "QUOTE")))
            recs.append(f"**Citations:** cite the {self.refs.spec.version} pages and the section that matches the transaction "
                        f"({ids_q}). The data values are not affected.")
        if "TERMINAL_PLACEHOLDER" in rules:
            recs.append(f"**Terminal:** replace the placeholder Terminal Identifier with the BUYPASS-assigned one before execution "
                        f"({qid['TERMINAL_PLACEHOLDER']}).")
        L = ["## 7. Recommendations for the AI test data generator", ""]
        L += [f"{i}. {x}" for i, x in enumerate(recs, 1)] or ["No generator change needed for this scenario."]
        return L + ["", "---", ""]

    def section8(self, mode, runs):
        L = ["## 8. Report index", "", "| Scenario | TC | Test | Result | Report |", "|---|---|---|---|---|"]
        L += [f"| {mode['key']} | {r.id} | {md(r.scenario)} | {r.rep.verdict()} | [{REPORT.format(r.id)}](./{REPORT.format(r.id)}) |"
              for r in runs]
        L += ["", "Re-run (from `regression-artifact-validator`):", "", "```powershell", "$env:PYTHONUTF8 = '1'",
              RERUN, "```"]
        return L

    def mode_runs(self, mode):
        return [r for r in self.runs if r.mode is mode]

    # ------------------------------------------------------------ consolidated defect report (all scenarios, field by field)
    @staticmethod
    def scen_short(mode):
        return f"{mode['key'].replace('BR1_', '')} {mode['title']}"

    def tc_ref(self, r):
        return f"[{r.id}](./{r.mode['path']}/{REPORT.format(r.id)})"

    def td_ref(self, r, n, key=None, nth=1):
        td = r.st(n)["td"]
        ln = r.line_of(n, key, nth) if key else "?"
        return f"[{td}]({TD_LINK}{td}#L{ln}) line {ln}" if isinstance(ln, int) else f"[{td}]({TD_LINK}{td})"

    def finding_where(self, r, f):
        if f["rule"] in TC_VALUE_RULES:
            idx = [s["step"] for s in r.tc["steps"]].index(f["step"])
            return (f"[test_cases.jsonl line {r.line}]({TC_LINK}#L{r.line}) → `steps[{idx}].request` → `Nonfuel Amount` "
                    f"(used for {r.st(f['step'])['td']})")
        key = RULE_KEY.get(f["rule"]) or self.field_key(f)
        key, nth = key if isinstance(key, tuple) else (key, 1)
        return self.td_ref(r, f["step"], key, nth) if f["step"] else md(f["field"])

    @staticmethod
    def raw_attr(r, n, seg, element):
        steps = r.rec.get("steps") or [dict(request=r.rec.get("request") or {})]
        idx = [s["step"] for s in r.tc["steps"]].index(n)
        req = (steps[idx].get("request") or {}) if idx < len(steps) else {}
        a = (req.get(SEG_NAMES[seg]) or {}).get(element)
        return a if isinstance(a, dict) else {}

    def trace(self, doc, hits):
        """How the generator produced the field: `method` counts and flag codes from the test case records."""
        if not doc.get("attr"):
            return ""
        methods, notes, seen = Counter(), [], set()
        for r, f in hits:
            if (r.id, f["step"]) in seen:
                continue
            seen.add((r.id, f["step"]))
            a = self.raw_attr(r, f["step"], *doc["attr"])
            methods[a.get("method") or "-"] += 1
            notes += [str(g).split(":")[0].strip() for g in a.get("flags") or []]
        return ("; ".join(f"`method: {m}` ({k} of {len(seen)} file{'s' if len(seen) != 1 else ''})" for m, k in methods.most_common())
                + (" – flags: " + ", ".join(f"`{x}`" for x in tdv.uniq(notes)[:4]) if notes else ""))

    @staticmethod
    def in_population(did, r, s):
        n, entry = s["step"], r.ctx["entries"].get(s["step"])
        emv, t7 = bool(r.seg(n, "130")), r.tc["template_id"].startswith("T7-")
        root = r.ctx["roots"].get(n)
        fin, tot, follow = root not in (None, tdv.TOTALS_ROOT), root == tdv.TOTALS_ROOT, s["role"] in tcv.SAME_SEQ_ROLES
        debit = tdv.profile(r.tc)["debit"]
        return {"D-01": emv, "D-02": emv, "D-08": entry == "79" and not follow,
                "D-09": entry == "10" or (t7 and n == r.tc["steps"][0]["step"]), "D-12": emv or entry == "10" or t7,
                "D-15": fin, "D-16": fin and debit and not follow and s.get("code") != "8",
                "D-17": fin and debit and entry in KEYED_CODES and not follow, "D-19": tot, "D-20": tot}.get(did, False)

    def manual(self, d, hits, r, f):
        """Numbered steps to confirm defect d by hand on one example finding."""
        did, n = d["id"], f["step"]
        fl, emv = r.ctx["flats"].get(n, {}), r.ctx["emv"].get(n) or {}
        entry, t005, tables = r.ctx["entries"].get(n) or "", r.ctx["t005"].get(n) or "", r.ctx["tables"].get(n) or {}
        cdb = fl.get("CardDiscretionaryBlockData", "")
        desc = f"`{entry}` = {tdv.PAN_ENTRY_DESC.get(entry, '?')}"
        read = "Read in ATL105: " + "; ".join(self.cite(x) for x in tdv.uniq(f["refs"]))
        cvm = emv.get("9F34", "")
        code = int(cvm[:2], 16) & 0x3F if len(cvm) >= 2 else None
        cvm_txt = (f"`{cvm}` → first byte `{cvm[:2]}` AND `3F` = CVM code `{code:02X}` = {tdv.CVM_NAMES.get(code, 'other')}"
                   if code is not None else "absent")
        if did == "D-01":
            s = r.seg(n, "130")
            csn, tag, sl = s.get("EMVCardSequenceNumber", ""), emv.get("5F34", ""), s.get("SegmentLength", "")
            return [f"Open {self.td_ref(r, n, 'EMVCardSequenceNumber')}: `EMVCardSequenceNumber` = `{csn}` ({len(csn)} characters).",
                    f"In `EMVChipData` ({self.td_ref(r, n, 'EMVChipData')}) search for `5F34`: the next byte is the length (`01`) "
                    f"and the byte after it is the card sequence number → `{tag or 'absent'}`.", read,
                    f"Expected = Tag 5F34 right-aligned and zero-padded to 3 bytes = `{tag.zfill(3) if tag else '   '}`; the file has "
                    f"`{csn}` → **FAIL**.",
                    f"Side check: Segment 130 `SegmentLength` = `{sl}` was counted with the {len(csn)}-byte value and grows by "
                    f"{3 - len(csn)} after the fix."]
        if did == "D-02":
            s = r.seg(n, "130")
            info, ln = s.get("EMVAdditionalInformation", ""), s.get("EMVAdditionalInformationLength", "")
            return [f"Open {self.td_ref(r, n, 'EMVAdditionalInformationIndicator')}: indicator "
                    f"`{s.get('EMVAdditionalInformationIndicator', '')}`, `EMVAdditionalInformationLength` = `{ln}`, "
                    f"`EMVAdditionalInformation` = {len(info)} characters (`{short(info)}`).",
                    f"Indicator meaning: {self.cite('EL191')}", f"Content for this indicator: {self.cite('EL118-EMV')}",
                    f"Allowed values: {self.cite('APPT-1')}; {self.cite('APPT-1-NOT')}; {self.cite('APPT-1-LEN')}",
                    f"The field holds {len(info)} generated characters instead of `EMVYES` / `EMVNOT` → **FAIL**. Element 192 = `{ln}` "
                    "equals that length, so it is consistent with the wrong content and must change with the fix (framing: §6)."]
        if did == "D-03":
            yymm = cdb[2:] + cdb[:2] if entry == "01" else cdb[:4]
            S = [f"Open {self.td_ref(r, n, 'CardDiscretionaryBlockData')}: `CardDiscretionaryBlockData` = `{cdb}`; the expiry is "
                 + ("the MMYY value" if entry == "01" else "the first 4 digits (YYMM)") + f" → `{yymm}` (YYMM).",
                 f"Compare with the run month {TODAY:%B %Y} (YYMM `{TODAY:%y%m}`): an expired card has an earlier expiry; `{yymm}` "
                 "is not earlier."]
            if r.seg(n, "130"):
                tvr = emv.get("95", "")
                bit = len(tvr) >= 4 and bool(int(tvr[2:4], 16) & 0x40)
                t5f24 = f"`{emv['5F24']}`" if "5F24" in emv else "absent"
                S.append(f"Chip data: Tag 5F24 (Application Expiration Date) is {t5f24}; TVR (Tag 95) = `{tvr or '-'}`, byte 2 bit 7 "
                         f"'Expired application' is {'set' if bit else 'not set'}.")
            return S + [read, "Conclusion: the expired-card test runs with a valid card → **FAIL**."]
        if did == "D-04":
            return [f"Open {self.td_ref(r, n, 'EncryptedPINBlockData')}: `EncryptedPINBlockData` = "
                    f"`\"{fl.get('EncryptedPINBlockData', '')}\"`.",
                    f"In `EMVChipData` find Tag `9F34` (CVM Results): {cvm_txt} (EMV Book 3; online PIN = CVM code 02).", read,
                    f"Note: {self.cite('S10.14.3-OPT')} – so an empty Element 33 is wrong only for the online-PIN test cases.",
                    "Conclusion: the online-PIN test sends neither a PIN block nor an online-PIN CVM → **FAIL** (needs the client's "
                    "online-PIN test card and DUKPT key)."]
        if did in ("D-05", "D-14"):
            st = r.st(n)
            of = st.get("of", 1)
            orig, orc = r.ctx["flats"].get(of, {}).get("NonfuelAmount", ""), r.st(of).get("expected_rc")
            S = [f"Open the step {of} file {self.td_ref(r, of, 'NonfuelAmount')}: `NonfuelAmount` = `{orig}` ({tdv.amount(orig)})"
                 + ((f"; the test case expects Response Code `{orc}`" + (" (partial approval)" if orc == "F" else "")) if orc else "")
                 + ".",
                 f"Open the step {n} file {self.td_ref(r, n, 'NonfuelAmount')}: `NonfuelAmount` = `{md(short(fl.get('NonfuelAmount', ''), 80))}` "
                 "– a run-time value, which is the correct approach."]
            mine = sorted((g for rr, g in hits if rr is r), key=lambda g: tdv.RULE_ORDER.get(g["rule"], 9))
            S += [f"{md(g['title'])}: {self.finding_where(r, g)} = `{md(short(g['value'], 40))}`. {md(g['why'])}" for g in mine]
            refs = tdv.uniq(x for g in mine for x in g["refs"])
            return S + ["Read in ATL105: " + "; ".join(self.cite(x) for x in refs),
                        "Conclusion: **FAIL** – expected: " + "; ".join(tdv.uniq(md(g["fix"]) for g in mine))]
        if did == "D-06":
            return [f"Open {self.td_ref(r, n, 'VariableInformation', 1)}: Table 005 = `{t005}` (entry mode {desc}; digit 3 = "
                    f"terminal PIN capability `{t005[2:3]}`).",
                    f"Table 031 ({self.td_ref(r, n, 'VariableInformation', 2)}) = `{r.ctx['t031'].get(n) or ''}`.", md(f["why"]), read,
                    f"Conclusion: **FAIL** – expected: {md(f['fix'])}"]
        if did == "D-07":
            return [f"Open {self.td_ref(r, n, 'AccountNumber')}: `AccountNumber` = `{fl.get('AccountNumber', '')}`, "
                    f"`CardDiscretionaryBlockData` = `{cdb}`.",
                    "Track 1 data starts with the format code `B` and uses `^` separators (card number `^` name `^` expiry, service "
                    "code, discretionary data); Track 2 data has neither.",
                    f"Here there is no `B` and no `^` – the data is Track 2, but the test objective is "
                    f"*\"{md(r.rec.get('test_objective', ''))}\"*.", read, "Conclusion: the Track 1 test sends Track 2 data → **FAIL**."]
        if did == "D-08":
            return [f"Open {self.td_ref(r, n, 'VariableInformation', 1)}: Table 005 = `{t005}` → entry mode {desc}.",
                    f"`CardDiscretionaryBlockData` ({self.td_ref(r, n, 'CardDiscretionaryBlockData')}) = `{cdb}` = expiry `{cdb[:4]}` "
                    f"(YYMM) + service code `{cdb[4:7]}` + discretionary data `{cdb[7:]}` – Track 2 data, which only exists when the "
                    "card is read.", read,
                    f"Expected for a card that is not read: the expiry only, as MMYY → `{cdb[2:4]}{cdb[:2]}`. Conclusion: **FAIL**."]
        if did in ("D-09", "D-10", "D-12"):
            kind = ("Segment 130 is sent (EMV request); " if r.seg(n, "130") else "") + f"Table 005 entry mode {desc}"
            return [f"Open {self.td_ref(r, n, 'VariableInformationSection')}: Segment 111 lists Tables "
                    f"{', '.join(f'`{t}`' for t in tables) or 'none'} (read each `VariableInformationIndicator`).",
                    kind + "." + (f" The test objective is *\"{md(r.rec.get('test_objective', ''))}\"*." if did == "D-10" else ""),
                    md(f["why"]), read, f"Conclusion: **FAIL** – expected: {md(f['fix'])}"]
        if did == "D-11":
            return [f"Open {self.td_ref(r, n, 'EMVChipData')} and find Tag `9F34` (CVM Results) in `EMVChipData`: {cvm_txt}.",
                    "An offline-PIN transaction shows CVM code 01 (plaintext PIN verified by the card) or 04 (enciphered PIN verified "
                    "by the card), e.g. `410302` or `440302` (EMV Book 3).", read,
                    "Conclusion: the offline-PIN test carries a signature CVM → **FAIL**."]
        if did == "D-13":
            pc = next((x["td"] for x in r.rep.rows if x["step"] == n and x["el"] == "78"), "")
            return [f"Open {self.td_ref(r, n, 'CardDiscretionaryBlockData')}: Prompt Code `{pc}` – step role "
                    f"{r.st(n)['role'].replace('_', ' ')}, a follow-on message built from card data the device stored.",
                    f"`CardDiscretionaryBlockData` = `{cdb}` = expiry `{cdb[:4]}` + service code `{cdb[4:7]}` + discretionary data – "
                    "full Track 2 data.", read, f"Expected: `{cdb[:4]}` (expiry YYMM only). Conclusion: **FAIL**."]
        return [f"Open {self.finding_where(r, f)}: value `{md(short(f['value'], 40))}`.", md(f["why"]), read,
                f"Expected: {md(f['fix'])}"]

    def defect_section(self, d, hits, allruns):
        did, doc = d["id"], DEFECT_DOC[d["id"]]
        tcs = tdv.uniq(r.id for r, _ in hits)
        files = set((r.id, f["step"]) for r, f in hits)
        scen = tdv.uniq(self.scen_short(r.mode) for r, _ in hits)
        values = tdv.uniq(md(short(f["value"], 60)) for _, f in hits)
        fixes = tdv.uniq(md(f["fix"]) for _, f in hits)
        multi = len({f["rule"] for _, f in hits}) > 1
        r0, f0 = hits[0]
        pl = lambda k, w: f"{k} {w}{'s' if k != 1 else ''}"  # noqa: E731
        L = [f'<a id="{did.lower()}"></a>', "", f"### {did} – {d['title']}", "", "| Item | Value |", "|---|---|",
             f"| Field | {doc['field']} (Segment {doc['seg']}) |", f"| Where in the test data | {doc['where']} |",
             f"| Type | {doc['kind']} |",
             f"| Affected | {pl(len(tcs), 'test case')}, {pl(len(files), 'test data file')} in {pl(len(scen), 'scenario')}: "
             f"{'; '.join(scen)} |"]
        if doc.get("scope"):
            pop = [(r, s) for r in allruns for s in r.tc["steps"] if self.in_population(did, r, s)]
            inside = sum(1 for r, s in pop if (r.id, s["step"]) in files)
            L.append(f"| Systemic | Yes – found in {inside} of the {len(pop)} {doc['scope']} of the {'catalog' if ALL else 'sample'}"
                     + (" (every one)" if inside == len(pop) else "") + ": it comes from a generator rule, not from one bad value |")
        L += ["| Actual (generated) | " + (f"`{values[0]}` in every affected file" if len(values) == 1 else
                                         f"{len(values)} different values – see the table below (e.g. `{values[0]}`)") + " |",
              "| Expected (ATL105) | " + (fixes[0] if len(fixes) == 1 else f"per row in the table below (e.g. {fixes[0]})") + " |",
              "| ATL105 reference | " + "<br>".join(self.cite(x) for x in tdv.uniq(x for _, f in hits for x in f["refs"])) + " |",
              f"| Why it is wrong | {md(f0['why'])} ({r0.id}) |"]
        trace = self.trace(doc, hits)
        if trace:
            L.append(f"| How the generator produced it | {trace} |")
        L += [f"| Certainty | {doc.get('certainty', 'Certain')} |", f"| Fix in the generator | {doc['fix']} |",
              f"| Check before release | {doc['check']} |", "", f"#### {did} – actual vs expected, field by field", ""]
        if len(values) == 1:
            L.append(f"Actual in every row: `{values[0]}`")
        if len(fixes) == 1:
            L.append(("<br>" if len(values) == 1 else "") + f"Expected in every row: {fixes[0]}")
        if len(values) == 1 or len(fixes) == 1:
            L.append("")
        cols = ["#", "Scenario", "Test case", "Step", "Test data file", "Field"] + (["Problem"] if multi else []) + (
            ["Actual"] if len(values) > 1 else []) + (["Expected"] if len(fixes) > 1 else [])
        L += ["| " + " | ".join(cols) + " |", "|" + "---|" * len(cols)]
        for i, (r, f) in enumerate(hits[:EVIDENCE_ROWS], 1):
            n = f["step"]
            row = [str(i), self.scen_short(r.mode), self.tc_ref(r), f"{n} ({r.st(n)['role'].replace('_', ' ')})",
                   self.finding_where(r, f), md(f["label"])]
            row += [md(f["title"])] if multi else []
            row += [f"`{md(short(f['value'], 60))}`"] if len(values) > 1 else []
            row += [md(f["fix"])] if len(fixes) > 1 else []
            L.append("| " + " | ".join(row) + " |")
        if len(hits) > EVIDENCE_ROWS:
            L += ["", f"… and {len(hits) - EVIDENCE_ROWS} more rows of the same kind. Every row is in "
                  f"[{DEFECT_SHEET}](./{DEFECT_SHEET}), sheet *Field findings* (filter *Defect* = {did})."]
        steps = self.manual(d, hits, r0, f0)
        if len(files) > 1:
            steps.append("Repeat for the other rows of the table above – only the file, the line and the values change.")
        L += ["", f"#### {did} – how to validate manually (example: {r0.id}, step {f0['step']})", ""]
        L += [f"{i}. {s}" for i, s in enumerate(steps, 1)]
        return L + ["", "Your conclusion: ☐ Agree ☐ Disagree – note: ____________", "", "---", ""]

    def write_defect_report(self, results):
        allruns = [r for x in results for r in x["runs"]]
        verdicts = Counter(r.rep.verdict() for r in allruns)
        present = [(d, [(r, f) for r in allruns for f in r.hits(d)]) for d in DEFECTS]
        order = {id(r): i for i, r in enumerate(allruns)}
        present = [(d, sorted(h, key=lambda rf: (order[id(rf[0])], rf[1]["step"] or 0, tdv.RULE_ORDER.get(rf[1]["rule"], 9))))
                   for d, h in present if h]
        systemic = [(d, h) for d, h in present if DEFECT_DOC[d["id"]].get("scope")]
        specific = [(d, h) for d, h in present if not DEFECT_DOC[d["id"]].get("scope")]
        link = lambda did: f"[{did}](#{did.lower()})"  # noqa: E731
        rest = ", ".join(f"{verdicts[v]} {v}" for v in ("BLOCKED", "REVIEW", "PASS") if verdicts[v])
        prof = tdv.profile(allruns[0].tc)
        device = sum(1 for r in allruns if not r.tc["steps"])
        ntotals = sum(1 for r in allruns for s in r.tc["steps"] if r.ctx["roots"].get(s["step"]) == tdv.TOTALS_ROOT)
        nfiles = sum(x["nfiles"] for x in results)
        scope = (f"All {len(allruns)} test cases of the catalog in {len(results)} scenarios: {len(allruns) - device} with test data "
                 f"({nfiles} test data files, {ntotals} of them Totals Requests) and {device} device-only test cases without a "
                 "host message (ATL105 citations checked)" if ALL else
                 f"{len(allruns)} test cases (up to {SAMPLE_SIZE} per scenario, all {len(results)} scenarios), {nfiles} test data "
                 "files")
        L = [f"# ATL105 Test Data Validation – Consolidated Defect Report – {PROGRAM}", "",
             "| Item | Value |", "|---|---|",
             f"| Product / program | BUYPASS ATL105 – {PROGRAM} – " + ", ".join(tdv.uniq(x["mode"]["cat_label"] for x in results))
             + " |",
             f"| Specification | {tdv.SPEC_TITLE} **{self.refs.spec.version}** ({self.refs.spec.date}) – `{PDF}` |",
             f"| Test data under review | `{DATA_LABEL}/test_data` |",
             f"| Test case catalog | `{DATA_LABEL}/test_cases/test_cases.jsonl` ({len(self.recs)} test cases in {len(results)} scenarios) |",
             "| Validator | `scripts/validate-td-against-atl105-spec.py` (field-by-field ATL105 rules; every quoted spec text "
             "re-verified on its page), run by `scripts/build-entry-mode-validation-report.py` |",
             f"| Scope | {scope} |",
             f"| Date | {TODAY.isoformat()} |",
             f"| Overall result | **{verdicts['FAIL']} / {len(allruns)} test cases FAIL** ({rest}) – {len(present)} defects: "
             f"{len(systemic)} systemic ({', '.join(link(d['id']) for d, _ in systemic)}), {len(specific)} scenario-specific "
             f"({', '.join(link(d['id']) for d, _ in specific)}) |",
             "| Purpose | Feedback for the AI test data generator team: for each defect the field, the ATL105 rule (verbatim), "
             "actual vs expected in every affected test data file, how to confirm it by hand and what to change |", "",
             "Per-scenario detail: the findings reports listed in §8. Per-test-case detail: the `TC-xxxxxx_ATL105_Field_Validation.md` "
             "reports linked in every table. Test data links open the file at the line shown. Every finding is also in "
             f"[{DEFECT_SHEET}](./{DEFECT_SHEET}) (one row per field finding, filterable).", "",
             f"Page numbers are PDF pages of the {self.refs.spec.version} edition (printed page in brackets). Defect IDs are the "
             "same as in the scenario findings reports and the README.", "", "---", "",
             "## 1. What was validated", "",
             "Every field of every test data file was checked against the ATL105 layouts and element definitions (details per "
             "scenario in §1 of each findings report):", "",
             "| Area | Checks |", "|---|---|",
             "| Message / Data Section 1 | Message type per step, Element 55 `ATL105`, Element 63 = segments present |",
             "| Segment 100 (17 fields) | Presence, character type, length and valid values; Prompt Code per step role; Segment Length "
             "recalculated from the fields plus Field Separators; run-time placeholders |",
             "| Card data (Elements 2 + 12) | Track data, keyed (MMYY), stored credential and follow-on (YYMM) formats per entry mode; "
             "card-number check digit" + (f" and {prof['bin_text']}" if prof["bin_text"] else "") + " |",
             "| Segment 111 | Tables 005 and 031, and the tables the transaction requires (030 for EMV and Credential on File, "
             "032/049/056 for Credential on File, 003 for AVS) |",
             "| Segment 130 | Elements 187–192 and 118, EMV chip data TLV, cross-field checks (9F02, 9F03, 9C, 5F34, 9F34, …) |"]
        if prof["debit"]:
            L.append(f"| Debit rules (Section 10.2) | Card type {prof['card_type']} in the Prompt Code; PIN block (Element 33) on every "
                     "request except the Purchase Reversal and follow-on messages; no PIN block on follow-on messages; no manual "
                     "(keyed) entry; no Time-out Reversal of a Completion |")
        if ntotals:
            L.append("| Totals Request (Segment 105) | Data Section 1 + Segment 105 only; every field (Prompt Code 990, Totals Date, "
                     "versions, Card Label 4 characters, counts and amounts); Segment Length with 14 Field Separators; count, "
                     "amount and grand total against the approved steps of the flow (Section 9.2) |")
        if device:
            L.append("| Device-only test cases | No host message: the ATL105 text each test case quotes is checked on its page |")
        L += ["| Test objective | The data can prove what the test claims (expired card, online/offline PIN, Track 1, AVS, split "
              "tender, completion, cancellation, time-out reversal) |",
              "| Step-to-step links | Sequence Number, Account Number, terminal, entry mode and amount against the original step |",
              "| Test case catalog | Expected Response Codes, the test case's own `rule_checks` and `link_defs`, generation fidelity, "
              "ATL105 citations |", "",
              "**How to read each defect section:** a summary table (field, where, type, how many files, actual, expected, ATL105 "
              "text, why, how the generator produced it, certainty, fix, check before release), then every affected field with "
              "actual vs expected, then numbered steps to confirm it by hand on one example.", "",
              "Verdicts: **FAIL** violates ATL105 or the test case's own objective · **BLOCKED** holds instruction text that must be "
              "replaced by a run-time value · **REVIEW** cannot be decided from the spec or the test data alone (§6).", "", "---", "",
              "## 2. Defects at a glance", "",
              "| Defect | Field | Segment | What is wrong | Type | Scenarios | Test cases | Test data files |",
              "|---|---|---|---|---|---|---|---|"]
        for d, h in present:
            doc = DEFECT_DOC[d["id"]]
            L.append(f"| {link(d['id'])} | {doc['field']} | {doc['seg']} | {md(d['title'])} | {doc['kind']} | "
                     f"{', '.join(tdv.uniq(r.mode['key'].replace('BR1_', '') for r, _ in h))} | {len(tdv.uniq(r.id for r, _ in h))} | "
                     f"{len(set((r.id, f['step']) for r, f in h))} |")
        L += ["", "### Field-wise view", "", "One row per ATL105 field: the defects found in it, an example of the generated value "
              "and what ATL105 expects.", "", "| Field | Segment | Defect | Test cases | Files | Actual (example) | Expected (example) |",
              "|---|---|---|---|---|---|---|"]
        for label, seg, rules in FIELD_RULES:
            fh = [(d, r, f) for d, h in present for r, f in h if f["rule"] in rules]
            for did in tdv.uniq(d["id"] for d, _, _ in fh):
                dh = [(r, f) for d, r, f in fh if d["id"] == did]
                r0, f0 = dh[0]
                L.append(f"| {label} | {seg} | {link(did)} | {len(tdv.uniq(r.id for r, _ in dh))} | "
                         f"{len(set((r.id, f['step']) for r, f in dh))} | `{md(short(f0['value'], 60))}` ({r0.id}) | {md(f0['fix'])} |")
        L += ["", "---", "", "## 3. Systemic defects (one generator rule, every test data file of a kind)", ""]
        for d, h in systemic:
            L += self.defect_section(d, h, allruns)
        L += ["## 4. Scenario-specific defects", ""]
        for d, h in specific:
            L += self.defect_section(d, h, allruns)
        others = [(r, f) for r in allruns for f in r.rep.findings if f["severity"] == "FAIL" and f["rule"] not in DEFECT_RULES]
        if others:
            L += ["### Other FAIL findings", "", "| Test case | Step | Field | Actual | Expected | Why |", "|---|---|---|---|---|---|"]
            L += [f"| {self.tc_ref(r)} | {f['step'] or '-'} | {md(f['label'])} | `{md(short(f['value'], 40))}` | {md(f['fix'])} | "
                  f"{md(f['why'])} |" for r, f in others[:EVIDENCE_ROWS]]
            if len(others) > EVIDENCE_ROWS:
                L += ["", f"… and {len(others) - EVIDENCE_ROWS} more – every row is in [{DEFECT_SHEET}](./{DEFECT_SHEET}), sheet "
                      "*Field findings* (Defect = `other`)."]
            L += ["", "---", ""]
        L += self.dr_passes(allruns) + self.dr_questions(allruns) + self.dr_recommendations(present) + self.dr_index(results)
        (self.out / DEFECT_REPORT).write_text("\n".join(L) + "\n", encoding="utf-8")

    def dr_passes(self, allruns):
        L = ["## 5. What passes", "", f"Aggregated over all {sum(len(r.tc['steps']) for r in allruns)} test data files.", "",
             "| Area | Correct in every test data file | Not correct in every file (files with FAIL / BLOCKED / REVIEW) |",
             "|---|---|---|"]
        areas = [("Message / Data Section 1", {"Message", "Data Section 1"}), ("Segment 100", {"100"}), ("Segment 111", {"111"}),
                 ("Segment 130 and EMV chip data", {"130"})]
        if any(x["seg"] == "105" for r in allruns for x in r.rep.rows):
            areas.append(("Segment 105 (Totals Request)", {"105"}))
        for area, segs in areas:
            stats = {}
            for r in allruns:
                for x in r.rep.rows:
                    if x["seg"] in segs:
                        stats.setdefault(x["name"], Counter())[x["result"]] += 1
            good = [md(k) for k, c in stats.items() if set(c) == {"PASS"}]
            bad = [f"{md(k)} ({', '.join(f'{v} {res}' for res, v in c.items() if res != 'PASS')} of {sum(c.values())})"
                   for k, c in stats.items() if set(c) != {"PASS"}]
            L.append(f"| {area} | {'; '.join(good) or '–'} | {'; '.join(bad) or '–'} |")
        links = [x for r in allruns for x in r.rep.links]
        resp = [x for r in allruns for x in r.rep.responses]
        checks = [c for r in allruns for c in r.rule_checks if c[2] is not None]
        quotes = [x for r in allruns for x in r.rep.quotes]
        L += [f"| Step-to-step links | {sum(1 for x in links if x['result'] == 'PASS')} of {len(links)} checks PASS | "
              f"{', '.join(f'{v} {k}' for k, v in Counter(x['result'] for x in links if x['result'] != 'PASS').items()) or '–'} |",
              f"| Expected response codes | {sum(1 for x in resp if x['result'] == 'PASS')} of {len(resp)} stated codes PASS | "
              "open (`?`) codes are listed in §6 |",
              f"| TC-declared `rule_checks` | {sum(1 for c in checks if c[2])} of {len(checks)} hold | "
              f"{sum(1 for c in checks if not c[2]) or '–'} |",
              f"| Spec quotes in the test cases | {sum(1 for x in quotes if x['result'] == 'PASS')} of {len(quotes)} found "
              "word-for-word on the cited page | citation items in §6 |", "",
              "The rows count the field-format checks. Objective defects (D-03, D-04, D-07, D-10, D-11) are judged against the "
              "purpose of the test, so they are listed in §3–§4 even where the field format itself passes.", "", "---", ""]
        return L

    def dr_questions(self, allruns):
        scen = {r.id: r.mode["key"].replace("BR1_", "") for r in allruns}
        every = len(tdv.uniq(scen.values()))
        where = lambda ids: ("all" if len(tdv.uniq(scen[i] for i in ids)) == every  # noqa: E731
                             else ", ".join(tdv.uniq(scen[i] for i in ids)))
        rows = []
        emv_ids = [r.id for r in allruns if any(r.seg(s["step"], "130") for s in r.tc["steps"])]
        if emv_ids:
            rows.append(("Exact corrected value of Element 192 / framing of Element 118 for indicator 001 (affects D-02)",
                         f"Appendix T ({self.pg('APPT-1')}) lists *\"{self.q('APPT-1-ID')}\"* and *\"{self.q('APPT-1-TLEN')}\"* but never "
                         "states the Table Length width: `EMVYES` alone (length `006`) or framed with Table ID + Table Length.",
                         emv_ids, ["APPT-1-ID", "APPT-1-TLEN", "APPT-1-LEN"], "BUYPASS / SME or a certified device trace"))
        for title, tcs, f in tdv.review_groups([(r.tc, r.rep) for r in allruns]):
            if f["rule"] == "SAME_REQUEST_AS_OTHER_TC":
                title = "Request identical to another test case except the Sequence Number"
            why = md(f["why"]) + (f" (example: {tcs[0]})" if len(tcs) > 1 else "")
            rows.append((title, why, tcs, f["refs"], NEEDED.get(f["rule"], "Test case owner")))
        opens = {}
        for r in allruns:
            for o in r.rec.get("open_items") or []:
                opens.setdefault(md(o if isinstance(o, str) else json.dumps(o)), []).append(r.id)
        rows += [(o, "Listed by the test case itself as an open item.", ids, [], "Client / test case owner") for o, ids in opens.items()]
        L = ["## 6. Open questions / review items (not defects)", "", "Consolidated from §6 of the findings reports. These do not "
             "make a test case FAIL, but each needs an answer or a correction before execution.", "",
             "| ID | Item | Why it is open | Test cases | Scenarios | Where in ATL105 | Needed from |", "|---|---|---|---|---|---|---|"]
        for i, (item, why, ids, refs, needed) in enumerate(rows, 1):
            ids = tdv.uniq(ids)
            L.append(f"| Q-{i:02d} | {md(item)} | {why} | {len(ids) if len(ids) > 3 else ', '.join(ids)} | {where(ids)} | "
                     f"{self.shorts(refs) if refs else '–'} | {needed} |")
        return L + ["", "---", ""]

    def dr_recommendations(self, present):
        ids = {d["id"]: h for d, h in present}
        L = ["## 7. Recommendations for the AI test data generator (field by field)", "",
             "| # | Field | What to change | Defect | Check before release |", "|---|---|---|---|---|"]
        k = 0
        for did in [x for x in FIELD_ORDER if x in ids]:
            k += 1
            doc = DEFECT_DOC[did]
            L.append(f"| {k} | {doc['field']} | {doc['fix']} | [{did}](#{did.lower()}) | {doc['check']} |")
        lens = [x for x in ("D-01", "D-02", "D-05", "D-14", "D-19", "D-20") if x in ids]
        L += [f"| {k + 1} | All Segment Length fields | Recalculate every Segment Length after the fixes above (Segment 100: "
              "17 fields + 17 Field Separators; Segment 130: as in Section 12.20"
              + ("; Segment 105: Fields 1–14 + 14 Field Separators + Card Label, Count and Amount without separators"
                 if {"D-19", "D-20"} & set(ids) else "") + f"). | {', '.join(lens) or '–'} | declared length = "
              "recalculated length |",
              f"| {k + 2} | ATL105 citations in the test cases | Cite the {self.refs.spec.version} pages and the section that matches "
              "the transaction (citation items in §6). The data values are not affected. | – | every quote found word-for-word on "
              "the cited page |",
              f"| {k + 3} | Element 102 Terminal Identifier | Replace the placeholder Terminal Identifier with the BUYPASS-assigned "
              "one before execution (§6). | – | Terminal Identifier from the client merchant list |", "", "---", ""]
        return L

    def dr_index(self, results):
        L = ["## 8. Report index", "", "| Scenario | Findings report | Test cases | Defects |", "|---|---|---|---|"]
        for x in results:
            m = x["mode"]
            defects = ", ".join(f"[{d['id']}](#{d['id'].lower()})" for d in x["present"]) or "–"
            L.append(f"| {m['key']} {m['title']} | [{m['folder']}_Findings.md](./{x['file']}) | "
                     f"{', '.join(self.tc_ref(r) for r in x['runs'])} | {defects} |")
        return L + ["", "Re-run (from `regression-artifact-validator`):", "", "```powershell", "$env:PYTHONUTF8 = '1'",
                    RERUN, "```"]

    # ------------------------------------------------------------ README
    def write_readme(self, results):
        allruns = [r for x in results for r in x["runs"]]
        verdicts = Counter(r.rep.verdict() for r in allruns)
        cats = tdv.uniq(x["mode"]["cat_label"] for x in results)
        keys = [x["mode"]["key"] for x in results]
        device = sum(1 for r in allruns if not r.tc["steps"])
        nfiles = sum(x["nfiles"] for x in results)
        ntotals = sum(1 for r in allruns for s in r.tc["steps"] if r.ctx["roots"].get(s["step"]) == tdv.TOTALS_ROOT)
        L = [f"# {PROGRAM} – Manual Validation Report (ATL105 test data, by transaction category and entry mode)", "",
             "| Item | Value |", "|---|---|",
             f"| Product / program | BUYPASS ATL105 – {PROGRAM} – {', '.join(cats)} |",
             f"| Specification | {tdv.SPEC_TITLE} **{self.refs.spec.version}** ({self.refs.spec.date}) – `{PDF}` |",
             f"| Test data under review | `{DATA_LABEL}/test_data` |",
             f"| Test case catalog | `{DATA_LABEL}/test_cases/test_cases.jsonl` ({len(self.recs)} test cases in {len(results)} "
             f"scenarios, {keys[0]} to {keys[-1]}) |",
             (f"| Scope | All {len(allruns)} test cases: {len(allruns) - device} with test data ({nfiles} test data files, "
              f"{ntotals} of them Totals Requests) and {device} device-only test cases (no host message; ATL105 citations "
              "checked) |" if ALL else
              f"| Scope | {len(allruns)} test cases (up to {SAMPLE_SIZE} per scenario) and {nfiles} test data files |"),
             f"| Date | {TODAY.isoformat()} |",
             "| Overall result | " + ", ".join(f"**{verdicts[v]} {v}**" for v in ("FAIL", "BLOCKED", "REVIEW", "PASS") if verdicts[v])
             + " |", "",
             "Each scenario folder (`<transaction category>/<entry mode>/`) holds a findings summary (`<entry mode>_Findings.md`, "
             "sections 1–8 as in the model report `EMVcontact_contactless_findings.md`) and one field-by-field report per test "
             "case.", "",
             f"**For the development team:** [{DEFECT_REPORT}](./{DEFECT_REPORT}) – every defect field by field across all "
             "scenarios (actual vs expected per test data file, ATL105 reference, how to confirm it by hand, what to fix) – and "
             f"[{DEFECT_SHEET}](./{DEFECT_SHEET}), the same findings as filterable sheets (defects, one row per field finding, "
             "test cases, review items).", "",
             "## Results by scenario", "",
             "| Scenario | Transaction category | Entry mode | Table 005 | Test cases | FAIL | BLOCKED | REVIEW | Defects | "
             "Findings report |", "|---|---|---|---|---|---|---|---|---|---|"]
        for x in results:
            m, v = x["mode"], x["verdicts"]
            L.append(f"| {m['key']} ({m['status']}) | {m['cat_label']} | {m['label']} | {', '.join(f'`{c}`' for c in m['entries'])} | "
                     + (f"{len(x['runs'])}" if ALL else ', '.join(r.id for r in x['runs']))
                     + f" | {v['FAIL']} | {v['BLOCKED']} | {v['REVIEW']} | "
                     f"{', '.join(d['id'] for d in x['present']) or '–'} | [{m['folder']}_Findings.md](./{x['file']}) |")
        L += ["", "## Defect catalog", "", "Defect IDs are the same in every findings report.", "",
              "| Defect | What is wrong | Scenarios | Test cases |", "|---|---|---|---|"]
        for d in DEFECTS:
            hit = [(x["mode"], [r.id for r in x["runs"] if r.hits(d)]) for x in results]
            hit = [(m, ids) for m, ids in hit if ids]
            ids = [i for _, ids in hit for i in ids]
            if ALL and not ids:
                continue
            L.append(f"| **{d['id']}** | {md(d['title'])} | {', '.join(m['key'].replace('BR1_', '') for m, _ in hit) or '–'} | "
                     + ((f"{len(ids)}" if ALL else f"{len(ids)}: {', '.join(ids)}") if ids else "–") + " |")
        first = [x for x in results if x["mode"]["key"] in fixed_samples()]
        d12 = [r.id for x in first for r in x["runs"] if "D-12" in r.defects]
        if first:
            L += ["", "## Changes since the first report (Purchase/Capture, 4 entry modes)", "",
                  "- The four entry-mode folders of the first report now sit under `Purchase_Capture/`, next to the other "
                  "Purchase/Capture entry modes. Their test cases are unchanged (" + "; ".join(
                      f"{x['mode']['label']}: {', '.join(r.id for r in x['runs'])}" for x in first) + ").",
                  "- Rules D-08 to D-14 were added for the new scenarios (keyed fallback, Credential on File, AVS, offline PIN, "
                  "follow-on messages)."]
        if d12:
            L.append(f"- One new rule also applies to the first report: **D-12 Table 030 missing** on every EMV request "
                     f"({', '.join(d12)}). {self.cite('APPI-030')}")
        if ALL:
            L += ["", "## Which test cases were validated", "",
                  "Every test case of the catalog. Test cases with a host message: every test data file of every step, field by "
                  "field, including the Totals Request of the T8 settlement test cases (Segment 105 and the totals of the flow). "
                  "T11 device-only test cases send no message to BUYPASS, so there is no test data; their ATL105 citations are "
                  "checked and their device procedure is listed in their report.", "",
                  "| Scenario | Test cases by type | Test data files | Device-only |", "|---|---|---|---|"]
            for x in results:
                m = x["mode"]
                types = Counter(r.rec.get("tc_type", "?") for r in x["runs"])
                L.append(f"| {m['key']} {m['title']} | " + ", ".join(f"{t} ×{k}" for t, k in sorted(
                    types.items(), key=lambda y: int(y[0][1:] or 0))) + f" | {x['nfiles']} | "
                    + (", ".join(r.id for r in x["runs"] if not r.tc["steps"]) or "–") + " |")
        else:
            L += ["", "## How the test cases were chosen", "",
                  "Only test cases that send a financial host message can be validated against the ATL105 message layouts. Not "
                  "sampled: T8 settlement tests (they send only a Totals Request), T11 device-only tests and blocked spec-gap test "
                  f"cases (no message is generated for them). From the remaining test cases of each scenario, up to {SAMPLE_SIZE} "
                  "were taken in this fixed order: the first T1 (base transaction), the first two T2/T3 (tests specific to the "
                  "entry mode), the first T4 and T5 (two-step flows: split tender, completion, cancellation, reversal), then T7 "
                  "(stored credential), T9 (AVS/FSA), T6 (time-out reversal) and T10 (edge cases) and, while still fewer than "
                  f"{SAMPLE_SIZE}, further test cases in catalog order."
                  + (" Purchase/Capture TS1, TS2, TS3 and TS5 keep the test cases of the first report." if first else ""), "",
                  "| Scenario | Selected test cases (catalog line) | Test cases in the scenario | Not sampled |", "|---|---|---|---|"]
            for x in results:
                m = x["mode"]
                L.append(f"| {m['key']} {m['title']} | " + "<br>".join(f"{r.id} (line {r.line}) – {md(r.scenario)}" for r in x["runs"])
                         + f" | {m['total']} | " + ("<br>".join(f"{v} {k}" for k, v in m["excluded"].items()) or "–") + " |")
        L += ["", "## Folder layout", "", "```text", "Manual Validation Report/", "  README.md",
              f"  {DEFECT_REPORT}     all defects, field by field (for the development team)",
              f"  {DEFECT_SHEET}    the same findings as filterable sheets"]
        for cat in tdv.uniq(x["mode"]["cat_folder"] for x in results):
            L.append(f"  {cat}/")
            for x in results:
                if x["mode"]["cat_folder"] == cat:
                    L += [f"    {x['mode']['folder']}/", f"      {x['mode']['folder']}_Findings.md"]
                    L += ([f"      TC-xxxxxx_ATL105_Field_Validation.md   ({len(x['runs'])} reports, one per test case)"] if ALL
                          else [f"      {REPORT.format(r.id)}" for r in x["runs"]])
        L += ["```", "", "## Page numbers: 2026-4 versus 2026-3", "",
              f"All pages here are PDF pages of the {self.refs.spec.version} edition. The model report used the 2026-3 edition, so "
              "the same text is on a different page. Both columns were found by searching the text of each edition:", "",
              "| Spec text | 2026-3 edition | 2026-4 edition (this report) |", "|---|---|---|"]
        prior = self.refs.prior
        for label, rid in (("Element 188 EMV Card Sequence Number", "EL188"), ("Element 191 EMV Additional Information Indicator", "EL191"),
                           ("Element 192 EMV Additional Information Length", "EL192"), ("Appendix T-1 EMV Table Data", "APPT-1"),
                           ("Element 58 Nonfuel Amount", "EL58"), ("Section 10.14.2.1 Expiration Date (EMV)", "S10.14.2.1"),
                           ("Section 12.20 EMV Request Data Segment", "S12.20-ALWAYS"),
                           ("Section 2.3 Time-out reversal", "S2.3-TOR"),
                           ("Section 10.1.1.2 Track Support (follow-up transactions)", "S10.1.1.2-FOLLOW"),
                           ("Section 10.1.2.2 Authorization Only Reversal", "S10.1.2.2"),
                           ("Element 86 Sequence Number (preauthorized completion)", "EL86-PREAUTH"),
                           ("Appendix I Table 030 POS Condition Code", "APPI-030"),
                           ("Appendix I Table 032 sub-table 08 Credential on File indicator", "APPI-032-08"),
                           ("Appendix J entry mode 10 Credential on file", "APPJ-10"),
                           ("Section 10.2.1.3 PIN Entry (debit)", "S10.2.1.3-PIN"), ("Section 10.2.1.4 Manual Entry (debit)", "S10.2.1.4"),
                           ("Section 12.6 Totals Data Segment", "S12.6-FS"), ("Element 13 Card Label", "EL13"),
                           ("Section 9.2 Local Totals", "S9.2-PUR")):
            if rid not in self.refs.catalog or (rid.startswith(("S10.2", "S12.6", "EL13", "S9.2")) and rid not in self.refs.used):
                continue
            new = self.refs.catalog[rid][1]
            old = prior.pages_with(self.refs.catalog[rid][2]) if prior else []
            L.append(f"| {label} | {f'p.{min(old, key=lambda p: abs(p - new))}' if old else '?'} | {self.pg(rid)} |")
        L += ["", "## Re-run", "", "From `regression-artifact-validator`:", "", "```powershell", "$env:PYTHONUTF8 = '1'",
              RERUN, "```", "",
              "The builder converts the test case records, runs `scripts/validate-td-against-atl105-spec.py` on their test "
              f"data and re-verifies every quoted ATL105 text against `specifications/ATL105/docs/specs/extracted_text_new.txt` "
              f"(the text of the {self.refs.spec.version} PDF).", ""]
        (self.out / "README.md").write_text("\n".join(L) + "\n", encoding="utf-8")

    # ------------------------------------------------------------ consolidated sheet (Excel)
    def write_defect_sheet(self, results):
        """Consolidated_Defect_Sheet.xlsx: summary, defects, one row per FAIL field finding, test cases, open items."""
        from openpyxl import Workbook
        from openpyxl.cell.cell import ILLEGAL_CHARACTERS_RE
        from openpyxl.styles import Alignment, Font, PatternFill
        from openpyxl.utils import get_column_letter

        allruns = [r for x in results for r in x["runs"]]
        tag = {id(f): d["id"] for r in allruns for d in DEFECTS for f in r.hits(d)}
        present = [d for d in DEFECTS if any(r.hits(d) for r in allruns)]
        plain = lambda s: str(s or "").replace("`", "").replace("\\|", "|")  # noqa: E731
        report = lambda r: f"{r.mode['path']}/{REPORT.format(r.id)}"  # noqa: E731
        wb = Workbook()

        def sheet(title, cols, rows, links=None):
            ws = wb.active if wb.active.max_row == 1 and wb.active.title == "Sheet" else wb.create_sheet()
            ws.title = title
            ws.append([c for c, _ in cols])
            for row in rows:
                ws.append([v if isinstance(v, (int, float)) else ILLEGAL_CHARACTERS_RE.sub("", str(v))[:32000] for v in row])
            for cell in ws[1]:
                cell.font, cell.fill = Font(bold=True, color="FFFFFF"), PatternFill("solid", fgColor="1F4E78")
                cell.alignment = Alignment(wrap_text=True, vertical="center")
            for i, (_, width) in enumerate(cols, 1):
                ws.column_dimensions[get_column_letter(i)].width = width
            for row in ws.iter_rows(min_row=2):
                for cell in row:
                    cell.alignment = Alignment(wrap_text=True, vertical="top")
                    if cell.data_type == "f":  # a value starting with '=' is data, never a formula
                        cell.data_type = "s"
                    if links and cell.column == links and cell.value:
                        cell.hyperlink = cell.value
                        cell.font = Font(color="0563C1", underline="single")
            ws.freeze_panes = "A2"
            if rows:
                ws.auto_filter.ref = ws.dimensions

        verdicts = Counter(r.rep.verdict() for r in allruns)
        device = sum(1 for r in allruns if not r.tc["steps"])
        sheet("Summary", [("Item", 28), ("Value", 120)], [
            ("Product / program", f"BUYPASS ATL105 – {PROGRAM} – " + ", ".join(tdv.uniq(x["mode"]["cat_label"] for x in results))),
            ("Specification", f"{tdv.SPEC_TITLE} {self.refs.spec.version} ({self.refs.spec.date})"),
            ("Test data under review", f"{DATA_LABEL}/test_data"),
            ("Test case catalog", f"{DATA_LABEL}/test_cases/test_cases.jsonl ({len(self.recs)} test cases, {len(results)} scenarios)"),
            ("Scope", f"{len(allruns)} test cases ({len(allruns) - device} with test data, {device} device-only), "
                      f"{sum(x['nfiles'] for x in results)} test data files"),
            ("Date", TODAY.isoformat()),
            ("Overall result", ", ".join(f"{verdicts[v]} {v}" for v in ("FAIL", "BLOCKED", "REVIEW", "PASS") if verdicts[v])),
            ("Defects", ", ".join(f"{d['id']} {d['title']}" for d in present)),
            ("Sheets", "Defects: one row per defect. Field findings: one row per FAIL finding (field, actual, expected, why, "
                       "ATL105 reference, test data file and line). Test cases: verdict per test case. Open items: REVIEW "
                       "(to confirm) and BLOCKED (run-time value) findings."),
            ("Detail", f"{DEFECT_REPORT} (field-by-field explanation and manual validation steps per defect), README.md")])
        rows = []
        for d in present:
            doc, h = DEFECT_DOC[d["id"]], [(r, f) for r in allruns for f in r.hits(d)]
            rows.append((d["id"], d["title"], plain(doc["field"]), doc["seg"], doc["kind"],
                         f"Yes – {doc['scope']}" if doc.get("scope") else "No",
                         ", ".join(tdv.uniq(r.mode["key"].replace("BR1_", "") for r, _ in h)), len(tdv.uniq(r.id for r, _ in h)),
                         len({(r.id, f["step"]) for r, f in h}), plain(doc["fix"]), plain(doc["check"]),
                         plain(doc.get("certainty", "Certain")), self.refs.shorts(tdv.uniq(x for _, f in h for x in f["refs"]))))
        sheet("Defects", [("Defect", 9), ("What is wrong", 40), ("Field", 34), ("Segment", 10), ("Type", 22), ("Systemic", 30),
                          ("Scenarios", 24), ("Test cases", 10), ("Test data files", 10), ("Fix in the generator", 70),
                          ("Check before release", 40), ("Certainty", 30), ("ATL105 reference", 40)], rows)
        rows = []
        for r in allruns:
            for f in r.rep.findings:
                if f["severity"] != "FAIL":
                    continue
                n = f["step"]
                key = RULE_KEY.get(f["rule"]) or self.field_key(f)
                key, nth = key if isinstance(key, tuple) else (key, 1)
                if f["rule"] in TC_VALUE_RULES:
                    file, line = "test_cases.jsonl", r.line
                else:
                    file, line = (r.st(n)["td"] if n else "(test case)"), (r.line_of(n, key, nth) if n and key else "")
                rows.append((len(rows) + 1, tag.get(id(f), "other"), f["rule"], self.scen_short(r.mode), r.mode["cat_label"],
                             r.mode["label"], r.id, r.tc["template_id"], n or "", r.st(n)["role"].replace("_", " ") if n else "",
                             file, line if isinstance(line, int) else "", f["label"], f["title"], f["value"], plain(f["fix"]),
                             f["why"], self.refs.shorts(f["refs"]) if f["refs"] else "", report(r)))
        sheet("Field findings", [("#", 6), ("Defect", 8), ("Rule", 26), ("Scenario", 30), ("Transaction category", 16),
                                 ("Entry mode", 18), ("Test case", 11), ("Template", 24), ("Step", 6), ("Step role", 14),
                                 ("Test data file", 36), ("Line", 6), ("Field", 26), ("Problem", 36), ("Actual (test data)", 30),
                                 ("Expected (ATL105)", 40), ("Why", 70), ("ATL105 reference", 34), ("Report", 40)], rows, links=19)
        rows = [(r.id, r.tc["display_id"], self.scen_short(r.mode), r.mode["cat_label"], r.mode["label"], r.tc["template_id"],
                 r.rec.get("tc_type", ""), r.tc["status"], len(r.tc["steps"]) or "device-only", r.rep.verdict(), r.rep.count("FAIL"),
                 r.rep.count("BLOCKED"), r.rep.count("REVIEW"), ", ".join(r.defects), report(r)) for r in allruns]
        sheet("Test cases", [("Test case", 11), ("Display ID", 16), ("Scenario", 30), ("Transaction category", 16),
                             ("Entry mode", 18), ("Template", 26), ("Type", 6), ("Status", 16), ("Test data files", 10),
                             ("Result", 9), ("FAIL", 6), ("BLOCKED", 8), ("REVIEW", 8), ("Defects", 24), ("Report", 40)], rows, links=15)
        rows = []
        for r in allruns:
            for f in r.rep.findings:
                if f["severity"] in ("REVIEW", "BLOCKED"):
                    rows.append((len(rows) + 1, f["severity"], f["rule"], f["title"], self.scen_short(r.mode), r.id, f["step"] or "",
                                 f["label"], f["value"], f["why"], plain(f["fix"]),
                                 NEEDED.get(f["rule"], "Test case owner") if f["severity"] == "REVIEW"
                                 else "Run time (from the earlier step's response)",
                                 self.refs.shorts(f["refs"]) if f["refs"] else ""))
        sheet("Open items", [("#", 6), ("Severity", 9), ("Rule", 26), ("Item", 40), ("Scenario", 30), ("Test case", 11), ("Step", 6),
                             ("Field", 24), ("Value", 30), ("Why it is open", 70), ("Action", 50), ("Needed from", 22),
                             ("ATL105 reference", 34)], rows)
        wb.save(self.out / DEFECT_SHEET)


def not_validatable(rec):
    """Reason why a test case has no financial host message to validate, else None."""
    legs = rec.get("tx_legs") or []
    if not legs:
        return ("blocked spec-gap (no message generated)" if rec.get("status") == "blocked_spec_gap"
                else "T11 device-only (no host message)")
    if any(lg.get("role") == "totals" for lg in legs):
        return "T8 settlement (Totals Request only)"
    return None


def select_tcs(ts, items):
    """Deterministic sample: first T1, first two T2/T3, first T4, first T5, then T7, T9, T6, T10, then catalog order."""
    if ALL:
        return [rec["id"] for _, rec in items], {}
    host = [rec for _, rec in items if not_validatable(rec) is None]
    if ts in fixed_samples():
        picked = list(FIXED[ts])
    else:
        def first(types, k=1):
            return [rec["id"] for rec in host if rec["tc_type"] in types][:k]
        picked = first({"T1"}) + first({"T2", "T3"}, 2) + first({"T4"}) + first({"T5"})
        for t in ("T7", "T9", "T6", "T10"):
            if len(picked) < SAMPLE_SIZE:
                picked += [i for i in first({t}) if i not in picked]
        picked += [rec["id"] for rec in host if rec["id"] not in picked][:max(0, SAMPLE_SIZE - len(picked))]
    order = {rec["id"]: i for i, (_, rec) in enumerate(items)}
    picked = sorted(picked[:SAMPLE_SIZE], key=order.get)
    excluded = Counter(not_validatable(rec) or f"further host-message test cases (sample limit {SAMPLE_SIZE})"
                       for _, rec in items if rec["id"] not in picked)
    return picked, dict(excluded)


def main():
    global PROGRAM, ALL, DATA_LABEL, TD_LINK, TC_LINK, RERUN
    sys.stdout.reconfigure(encoding="utf-8")
    ap = argparse.ArgumentParser(description=__doc__.splitlines()[0])
    ap.add_argument("--data", default=str(DATA), help="folder with test_cases/test_cases.jsonl and test_data/")
    ap.add_argument("--out", help="report folder (default: '<data>/Manual Validation Report'; for the default MasterCard data "
                                  "'MasterCard Credit/Manual Validation Report')")
    ap.add_argument("--all", action="store_true", help="validate every test case of the catalog instead of the sample")
    ap.add_argument("--spec-text", default=str(tcv.DEFAULT_SPEC))
    ap.add_argument("--prior-spec-text", default=str(tcv.PRIOR_SPEC))
    a = ap.parse_args()

    data = Path(a.data).resolve()
    out = Path(a.out).resolve() if a.out else OUT if data == DATA.resolve() else data / "Manual Validation Report"
    ALL = a.all
    DATA_LABEL = data.relative_to(ROOT).as_posix() if data.is_relative_to(ROOT) else data.as_posix()
    TD_LINK = Path(os.path.relpath(data / "test_data", out)).as_posix() + "/"
    TC_LINK = Path(os.path.relpath(data / "test_cases" / "test_cases.jsonl", out)).as_posix()
    RERUN = ("python scripts/build-entry-mode-validation-report.py" + (f' --data "{DATA_LABEL}"' if data != DATA.resolve() else "")
             + (" --all" if ALL else "") + (f' --out "{a.out}"' if a.out else ""))
    spec = tdv.Spec(Path(a.spec_text))
    prior = tdv.Spec(Path(a.prior_spec_text)) if Path(a.prior_spec_text).exists() else None
    states = spec.state_codes()
    refs = tdv.Refs(spec, prior, states)
    ref_json = json.loads(tcv.ELEMENT_REF.read_text(encoding="utf-8"))
    ref = {e["element"]: e for e in ref_json["elements"]}
    qv = tcv.Validator(spec.sq, {}, ref_json)

    recs = {}
    for i, line in enumerate((data / "test_cases" / "test_cases.jsonl").read_text(encoding="utf-8").splitlines(), 1):
        if line.strip():
            rec = json.loads(line)
            recs[rec["id"]] = (i, rec)
    rec0 = next(iter(recs.values()))[1]
    key = f"{rec0['dimensions'].get('network')}/{rec0['dimensions'].get('program')}".upper()
    labels = rec0.get("dimension_labels") or {}
    PROGRAM = (tdv.PROFILES[key]["name"] if key in tdv.PROFILES
               else " ".join(x for x in (labels.get("network"), labels.get("program")) if x) or "ATL105")
    if not PROGRAM.startswith("MasterCard"):
        NEEDED["EMV_CONTACTLESS_TEMPLATE"] = f"{labels.get('network', 'Network')} contactless kernel trace"
    runs = []
    by_ts = {}
    for line, rec in recs.values():
        by_ts.setdefault(rec["scenario_display_id"], []).append((line, rec))
    scenarios = []
    for ts in sorted(by_ts, key=lambda k: int(re.search(r"TS(\d+)$", k).group(1))):
        items = sorted(by_ts[ts], key=lambda x: x[0])
        rec0 = items[0][1]
        dims = rec0["dimensions"]
        cat_folder, cat_label = CATEGORIES[dims["txn_category"]]
        folder, label = ENTRY_MODES[dims["entry_mode"]]
        ids, excluded = select_tcs(ts, items)
        scenarios.append(dict(key=ts, cat=dims["txn_category"], cat_folder=cat_folder, cat_label=cat_label, folder=folder,
                              label=label, path=f"{cat_folder}/{folder}", title=f"{cat_label} – {label}", dim=dims["entry_mode"],
                              status=rec0.get("scenario_status", "?"), ids=ids, total=len(items), excluded=excluded, entries=[]))
    for mode in scenarios:
        names = [f"{mode['folder']}_Findings.md"] + [REPORT.format(i) for i in mode["ids"]]
        longest = max((out.resolve() / mode["path"] / n for n in names), key=lambda p: len(str(p)))
        if len(str(longest)) > MAX_PATH:
            sys.exit(f"Output path too long for Windows ({len(str(longest))} > {MAX_PATH} characters): {longest}\n"
                     "Use --out with a shorter folder.")
    for mode in scenarios:
        for tid in mode["ids"]:
            line, rec = recs[tid]
            tc, meta = convert(rec) if rec.get("tx_legs") else convert_device_only(rec)
            rep, ctx = tdv.validate_tc(tc, meta, data / "test_data", ref, refs, qv, states)
            runs.append(Run(mode, rec, line, tc, rep, ctx, data / "test_data"))
    cross = tdv.cross_checks([(r.tc, r.rep) for r in runs], {r.id: r.ctx for r in runs})

    w = Writer(refs, data / "test_data", out, runs, cross, recs)
    out.mkdir(parents=True, exist_ok=True)
    results = [w.write_mode(m, [r for r in runs if r.mode is m]) for m in scenarios if m["ids"]]
    w.write_readme(results)
    w.write_defect_report(results)
    w.write_defect_sheet(results)

    if not ALL:
        for r in runs:
            print(f"{r.mode['key']:9} {r.mode['path']:52} {r.id} {r.tc['template_id']:26} {r.rep.verdict():7} "
                  f"FAIL={r.rep.count('FAIL')} BLOCKED={r.rep.count('BLOCKED')} REVIEW={r.rep.count('REVIEW')} "
                  f"defects={','.join(r.defects) or '-'}")
    print(f"{PROGRAM}: {len(runs)} test cases, {sum(len(r.tc['steps']) for r in runs)} test data files; verdicts "
          + ", ".join(f"{k} {v}" for k, v in Counter(r.rep.verdict() for r in runs).most_common()))
    for d in DEFECTS:
        h = [(r, f) for r in runs for f in r.hits(d)]
        if h:
            print(f"  {d['id']} {len(tdv.uniq(r.id for r, _ in h)):4} TCs {len({(r.id, f['step']) for r, f in h}):4} files  {d['title']}")
    other = Counter(f["rule"] for r in runs for f in r.rep.findings if f["severity"] == "FAIL" and f["rule"] not in DEFECT_RULES)
    if other:
        print("  other FAIL rules:", dict(other.most_common()))
    unverified = {k: v for k, v in refs.status.items() if v != "verified"}
    print(f"ATL105 references used: {len(refs.used)}; not verified in spec text: {unverified or 'none'}")
    print(f"Written: {out}")


if __name__ == "__main__":
    main()
