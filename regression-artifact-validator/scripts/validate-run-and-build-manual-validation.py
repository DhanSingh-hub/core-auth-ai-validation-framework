#!/usr/bin/env python3
"""Validate every test case and test data file of a generated ATL105 run and write a 'Manual Validation' folder.

Runs the trained validator (validate-td-against-atl105-spec.py, via build-entry-mode-validation-report.py) on every test
case, adds checks for what the run uses beyond the trained rules (all other data segments, every Variable Information
table, the test cases' own rule_checks, test data vs test case fidelity, identical requests, negative-test intent,
time-out reversal flow), classifies every finding and writes into --out:
  README.md                                    how to use the folder, overall result
  ATL105_Run_Validation_Summary.md             detailed analysis of all test cases and test data files
  ATL105_Run_Validation_All_Test_Cases.xlsx    one row per test case, finding, defect and scenario
  TC-xxxxxx-Manual-Validation.md               step-by-step manual validation guide per selected test case
  Test cases/<scenario>/TC-xxxxxx.md           one validation document per test case, with an index
With --only and --package the whole run is still validated, but only the listed test cases are written, into --package:
  Result_MD_Folder/                            ATL105 result per test case (every segment and field) + summary
  Manual Validation/                           step-by-step guides + README
  ATL105_Validation_<n>_Test_Cases.md          overview: test cases, run files checked against MANIFEST.json, results
Every ATL105 text that is quoted is checked against the spec text on its PDF page before anything is written.
"""

import argparse
import csv
import hashlib
import importlib.util
import json
import os
import re
import sys
from collections import Counter, defaultdict
from datetime import datetime
from pathlib import Path
from urllib.parse import quote as urlquote

ROOT = Path(__file__).resolve().parents[1]


def _load(name, file):
    spec = importlib.util.spec_from_file_location(name, ROOT / "scripts" / file)
    mod = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(mod)
    return mod


bem = _load("bem", "build-entry-mode-validation-report.py")
mvg = _load("mvg", "build-manual-validation-guides.py")
tdv, tcv = bem.tdv, bem.tcv
L, H, R = mvg.L, mvg.H, mvg.R

DEFAULT_RUN = ROOT / "16_nov_2026_mastercard_credit_output" / "mastercard_credit_new - Copy (2)"
DEFAULT_OUT = ROOT / "16_nov_2026_mastercard_credit_output" / "Manual Validation"
DEFAULT_GUIDES = ["TC-020803", "TC-020804", "TC-020805", "TC-020806", "TC-022362"]
SUMMARY = "ATL105_Run_Validation_Summary.md"
SHEET = "ATL105_Run_Validation_All_Test_Cases.xlsx"
TC_DOCS = "Test cases"
RESULT_DIR, MANUAL_DIR, RESULT_SUMMARY = "Result_MD_Folder", "Manual Validation", "ATL105-TD-Validation-Summary.md"
MAX_PATH = 259

S2_3, S2_3_1 = "Section 2.3 Unsuccessful Financial Transactions", "Section 2.3.1 Time-out by Customer's Host/Controller/POS System"
S11_1 = "Section 11.1 Financial Transactions (Data Section No. 3)"
S12_38 = "Section 12.38 Network Token Data Request Segment"
S12_31 = "Section 12.31 Enhanced Fleet Request Segment"
# ATL105 text used by the extended checks: id -> (section, PDF page, exact text); verified on its page at run time
NEW_REFS = {
    "S2.3-NEVER": (S2_3, 39, "A TOR never initiates a new financial transaction request."),
    "S2.3.1-TIMEOUT": (S2_3_1, 40, "The customer’s 30-second response interval expires without a response from BUYPASS."),
    "S2.3.1-LATE": (S2_3_1, 40, "If the customer’s host/controller/POS system receives a late response to the original transaction "
                                "request from BUYPASS, the late response is ignored."),
    "S2.3.1-QUEUE": (S2_3_1, 40, "Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR"),
    "S2.3.1-ANYRESP": (S2_3_1, 40, "Any response from BUYPASS to a TOR, whether it is an approval or a decline, is considered a "
                                   "valid response"),
    "S2.3.1-UNMATCHED": (S2_3_1, 40, "BUYPASS declines a TOR request as an unmatched reversal when BUYPASS has not received the "
                                     "original financial transaction request related to the TOR; or when the original financial "
                                     "transaction has already been reversed."),
    "S10.1.2-LIST": ("Section 10.1.2 Supported Transaction Types", 83, "The following point-of-sale transactions are supported for "
                     "credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out "
                     "Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal"),
    "S11.1-DS3": (S11_1, 168, "All Financial Transaction Requests contain none, one, or more of the following data segments in "
                              "Field Nos. 4–9."),
    "S11.1-101": (S11_1, 168, "101 Fleet Data Segment 308 C Contains fleet-specific data. Sent only on transactions requiring "
                              "fleet data."),
    "S11.1-104": (S11_1, 168, "104 Purchase Card Data Segment 86 C Contains purchase card-specific data. Sent only on "
                              "transactions requiring purchase card data."),
    "S11.1-123": (S11_1, 168, "123 NFC Payment Tokenization Data Segment 186 C Contains data needed to process NFC tokenized "
                              "transactions."),
    "S11.1-145": (S11_1, 168, "145 Enhanced Fleet Request Segment 999 C Contains data that applies to enhanced fleet offerings."),
    "S11.1-153": (S11_1, 169, "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this "
                              "transaction."),
    "S12.1-ROW3": ("Section 12.1 Standard Message Data Segment", 223, "3 44 Information Byte 1 R"),
    "S12.1-ROW16": ("Section 12.1 Standard Message Data Segment", 225, "Identifies the local date and local time of the "
                                                                        "preauthorized transaction."),
    "S12.2-FS": ("Section 12.2 Fleet Data Segment", 226, "All fields are separated by Field Separators."),
    "S12.3-TOTAL": ("Section 12.3 Product Code Data Segment", 229, "The total of product amounts in the segment must equal the "
                                                                    "total of the amounts"),
    "S12.3-FS": ("Section 12.3 Product Code Data Segment", 229, "A Field Separator always follows Segment Type and Segment Length."),
    "S12.3-DELIM": ("Section 12.3 Product Code Data Segment", 229, "A Product Data Field Delimiter always follows Quantity and "
                                                                    "Unit Price."),
    "S12.3-LAST": ("Section 12.3 Product Code Data Segment", 229, "A Field Separator follows Product Amount when Product Amount is "
                                                                   "the very last element in the segment."),
    "S12.3-MAX": ("Section 12.3 Product Code Data Segment", 229, "It has a maximum length of 381 alphanumeric characters"),
    "S12.3-ROW3": ("Section 12.3 Product Code Data Segment", 230, "3 87 Service Level 1 R Identifies the type of sale."),
    "S12.5-FS": ("Section 12.5 Purchase Card Data Segment", 233, "All fields are separated by Field Separators."),
    "S12.5-MAX": ("Section 12.5 Purchase Card Data Segment", 233, "It has a maximum length of 86 alphanumeric characters"),
    "S12.7-FS": ("Section 12.7 Loyalty Card Data Segment", 238, "All fields are separated by Field Separators, with a Field "
                                                                "Separator following Field No. 13."),
    "S12.7-MAX": ("Section 12.7 Loyalty Card Data Segment", 238, "It has a maximum length of 142 alphanumeric characters"),
    "S12.7-ROW12": ("Section 12.7 Loyalty Card Data Segment", 239, "12 148 Payment Tender Type 2 R Used to determine whether the "
                                                                   "transaction is a multiple-card transaction involving a "
                                                                   "loyalty card."),
    "S12.19-FS": ("Section 12.19 NFC Payment Tokenization Data Segment", 267, "All fields are separated by Field Separator."),
    "S12.19-MAX": ("Section 12.19 NFC Payment Tokenization Data Segment", 267, "It has a maximum length of 186 alphanumeric "
                                                                               "characters"),
    "S12.19-REQ": ("Section 12.19 NFC Payment Tokenization Data Segment", 267, "It is required on all initial and recurring "
                   "transactions involving tokenized data and for transactions that include MasterCard Token or Digital Secure "
                   "Remote Payment (DSRP) or Visa TAVV data."),
    "EL87-VAL": ("Element 87 Service Level (Chapter 13)", 410, "F Full serve S Self-serve N Mini-serve X Maxi-serve H "
                 "High-Speed Dispense O Other/Fuel not present."),
    "APPI-048": ("Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048)", 586,
                 "This field is applicable to merchants sending e-Commerce transactions with 3D Secure version or EMV 3D Secure "
                 "for authentication."),
    "APPI-048-LEN": ("Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048)", 586,
                     "Merchant should send the table length as ‘1’ when sending only Program Protocol, and table length should "
                     "be ‘37’ when they support both Program Protocol and Directory Server Transaction ID."),
    "S12.30-102": ("Section 12.30 Tax by Product Data Segment", 285, "A Product Code Data Segment (Data Segment No. 102) must be "
                   "present in the transaction."),
    "S12.30-ORDER": ("Section 12.30 Tax by Product Data Segment", 285, "Each entry in the Product Code Data Segment (Data Segment "
                     "No. 102) should have an entry in this segment too and the entries should be in the same order in both the "
                     "segments."),
    "S12.30-CAN": ("Section 12.30 Tax by Product Data Segment", 285, "For Canadian transactions, the valid values for Tax Type "
                   "are GST (Goods and Services Tax), HST (Harmonized Sales Tax), and PST (Provincial State Tax)."),
    "S11.1-143": (S11_1, 168, "143 Tax by Product Data Segment 399 C Contains tax data for each product included in segment "
                              "102. This segment has limited applicability and should only be included if instructed by your "
                              "account manager."),
    "APPI-048-PP": ("Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048)", 587,
                    "1 = EMV 3-D Secure Version 2.1"),
    "S10.1.3-USE": ("Section 10.1.3 Address Verification Service", 86, "Address Verification Service (AVS) is a credit card "
                    "authorization tool used for transactions performed primarily in a non-face-to-face environment, such as "
                    "mail order, phone order, or customer-activated terminal (CAT)."),
    "S10.1.3-REQ": ("Section 10.1.3 Address Verification Service", 86, "The device sends a transaction request to BUYPASS "
                    "containing the AVS information in the Variable Information Data Segment"),
    "S10.1.3-RESP": ("Section 10.1.3 Address Verification Service", 86, "The authorizer returns a response containing both the "
                     "transaction authorization Response Code and the AVS information in the Additional Information Data "
                     "Segment"),
    "EL113-003": ("Element 113 Variable Information (Chapter 13)", 426, "When Element No. 111 (Variable Information Indicator) "
                  "has a value of 003 (AVS indicator), address information (street name, ZIP Code™/ZIP+4®) is included here."),
    "EL116-INIT": ("Element 116 Additional Information Indicator (Chapter 13)", 433, "A Financial Transaction Request "
                   "initiates transmittal of AVS information (003) and CVV information (004) in a Financial Transaction "
                   "Response."),
    "APPI-003-ZIP": ("Appendix I. Variable Information Data Layouts - AVS Information (Table 003)", 554, "ZIP Code™ (5 "
                     "characters), followed by 4 spaces, for a total of 9 characters; or"),
    "APPI-003-ADDR": ("Appendix I. Variable Information Data Layouts - AVS Information (Table 003)", 554, "The remaining "
                      "00–20 characters contain street address information."),
    "APPI-004": ("Appendix I. Variable Information Data Layouts - Card Verification Information (Table 004)", 554,
                 "MasterCard:  3 bytes"),
    "APPK-003": ("Appendix K. Additional Information Data Layouts - AVS or NVS or Both Information (Table 003)", 643,
                 "Identifies the AVS or NVS or both response from the authorizer."),
    "APPK-004": ("Appendix K. Additional Information Data Layouts - CVV Information (Table 004)", 648, "Identifies the CVV "
                 "response from the authorizer."),
    "S11.2.1-108": ("Section 11.2.1 Loyalty Card Transactions - Request", 175, "The Loyalty Card Transaction Request "
                    "contains Segment Nos. 108 and 114."),
    "S11.2.1-108R": ("Section 11.2.1 Loyalty Card Transactions - Request", 175, "Contains the loyalty card data required for "
                     "all loyalty card transactions."),
    "EL62": ("Element 62 Number of Products (Chapter 13)", 394, "Always precede single digits (1–9) with a zero (01–09)."),
    "EL76": ("Element 76 Product Amount (Chapter 13)", 399, "Representation: Variable length of up to 12 digits with two "
             "assumed decimal places"),
    "EL77": ("Element 77 Product Code (Chapter 13)", 400, "Representation: Fixed length of three digits"),
    "EL81": ("Element 81 Quantity (Chapter 13)", 402, "Processing Rules: The leading number indicates the number of assumed "
             "decimal places. There is a maximum number of three assumed decimal places."),
    "EL81-EX": ("Element 81 Quantity (Chapter 13)", 402, "Examples: 010 = 10, where 0 is the number of assumed decimal places "
                "and 10 represents the quantity of 10. 34170 = 4.170, where 3 is the number of assumed decimal places and 4170 "
                "represents the quantity of 4.170."),
    "EL106": ("Element 106 Unit of Measure (Chapter 13)", 420, "Valid Codes/Values: Value Description C Case/Carton G Gallon "
              "H Hours I Imperial Gallons K Kilogram L Liter"),
    "EL107": ("Element 107 Unit Price (Chapter 13)", 421, "Processing Rules: The leading number indicates the number of "
              "assumed decimal places. There is a maximum number of three assumed decimal places."),
    "S12.31-SCOPE": (S12_31, 289, "The Enhanced Fleet Request Segment contains fleet data that applies to the enhanced fleet "
                                  "offerings such as Wex OTR, Comdata, Voyager EMV, and Visa Fleet 2.0."),
    "S12.31-MC": (S12_31, 289, "Only the Prompt Table sub-segment is valid for MasterCard Enhanced Fleet EMV transactions."),
    "S12.31-LEN": (S12_31, 289, "2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment "
                                "Type length"),
    "S12.31-TLV": (S12_31, 289, "All or some of the Enhanced Fleet sub-segment data is specified below. Data is in "
                                "<tag><len><data> format."),
    "S12.38-UCOM": (S12_38, 310, "This segment is used to send Network Token transaction information from Ucom to BUYPASS."),
    "S12.38-FS": (S12_38, 310, "There is a Field Separator between Field Nos. 1 and 2."),
    "S12.38-NOFS": (S12_38, 310, "Field Separators are not there between the elements in Network Token Data Layouts."),
    "S12.38-ENTITLED": (S12_38, 310, "When a merchant is entitled to use both First Data provisioned Network Tokens and Auth "
                                     "Optimization service, the merchant is required to send the same and exact information "
                                     "from the original transaction request in all the follow-up transactions"),
    "S12.38-LEN": (S12_38, 310, "2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment "
                                "Type length"),
    "S12.38-TLV": (S12_38, 310, "All or some of the Network Token sub-segment data is specified below. Data is in "
                                "<tag><len><data> format."),
    "S12.38-T001": (S12_38, 311, "Table Data n Max. Len.: 19 Identifies the Network Token Data."),
    "S12.38-T002": (S12_38, 311, "Identifies the Network Token Expiration Date. Format: YYMM"),
    "S12.38-T004": (S12_38, 312, "Identifies that the Token or PAN is sent by the merchant Valid values are: Y – Network Token "
                                 "N – Clear PAN"),
    "S12.38-T006": (S12_38, 312, "This data is applicable only to subsequent transactions (Example: Reversal, Completion, "
                                 "Cancellation)."),
}

# ---------------------------------------------------------------- segments
SEG_NUMBER = {"Standard Message Data Segment": "100", "Fleet Data Segment": "101", "Product Code Data Segment": "102",
              "EBT Data Segment": "103", "Purchase Card Data Segment": "104", "Totals Data Segment": "105",
              "Loyalty Card Data Segment": "108", "Check Data Segment": "110", "Variable Information Data Segment": "111",
              "NFC Payment Tokenization Data Segment": "123", "EMV Request Data Segment": "130",
              "Enhanced Fleet Request": "145", "Network Token Data Request": "153"}
TRAINED_SEGMENTS = {"100", "105", "111", "130"}
# extra segments: (layout max length, length rule, references, what the client must supply when the content is synthesized)
EXTRA = {
    "101": (308, "all_fs", ["S12.2-FS", "S11.1-101"], "the fleet card and its fleet prompt data"),
    "102": (381, "product", ["S12.3-FS", "S12.3-DELIM", "S12.3-LAST", "S12.3-MAX"], "the merchant's product data"),
    "104": (86, "all_fs", ["S12.5-FS", "S12.5-MAX", "S11.1-104"], "a purchase (commercial) card and its purchase-card data"),
    "108": (142, "all_fs_13", ["S12.7-FS", "S12.7-MAX"], "the merchant's loyalty program data and loyalty card"),
    "123": (186, "all_fs", ["S12.19-FS", "S12.19-MAX", "S11.1-123"], "a tokenized card and its token/cryptogram data"),
    "145": (999, "tlv", ["S12.31-LEN", "S12.31-TLV", "S11.1-145"], "an enhanced fleet card and its fleet sub-segment data"),
    "153": (999, "tlv_153", ["S12.38-FS", "S12.38-NOFS", "S12.38-LEN", "S11.1-153"],
            "the merchant's network-token entitlement and a real provisioned network token"),
}
SEGMENT_SCOPE = {"145": ("S12.31-SCOPE", "fleet data for enhanced fleet offerings (WEX OTR, Comdata, Voyager EMV, Visa Fleet 2.0)"),
                 "101": ("S11.1-101", "fleet data, sent only on transactions requiring fleet data")}
NET_TOKEN_TABLES = {"001": ("Network Token", None, 19), "002": ("Network Token Expiration Date", 4, 4),
                    "003": ("Network Token Provisional Fee Indicator", 1, 1), "004": ("Network Token Input Indicator", 1, 1),
                    "005": ("Network Token Eligible Indicator", 1, 1), "006": ("Network Token PAN Indicator", 1, 1),
                    "007": ("Network Token TransArmor Billing Indicator", 1, 1)}
PETROLEUM = {"PETROLEUM-ATTENDED", "PETROLEUM-UNATTENDED"}
# fuel merchant category codes: 5541 service stations, 5542 automated fuel dispensers, 5983 fuel dealers
FUEL_MCC = {"MCC_5541", "MCC_5542", "MCC_5983"}
# request types the trained validator does not check field by field (no ATL105 rules trained for them)
NON_FIN_ROOTS = {"Totals with Proprietary Data Load Request", "Proprietary Data Load Request", "Electronic Mail Request",
                 "CA Public Key File Load Request", "TransArmor Key Load Request", "Moneris Key Load Request",
                 "Software Load Request", "Table Load Request", "Date and Time Load Request", "Phone Load Request",
                 "Communications Test Request"}


def is_petroleum(rec):
    return rec.get("acceptance_context") in PETROLEUM | FUEL_MCC or bool(rec.get("fuel_at_pump"))


ROLE_BY_TT = {"0": "purchase", "4": "purchase", "3": "auth_only", "5": "auth_only", "6": "moto_purchase",
              "B": "moto_auth_only", "7": "return", "E": "balance_inquiry", "A": "account_verification", "8": "reversal",
              "C": "moto_reversal", "S": "cancellation", "U": "void_return", "Z": "timeout_reversal"}
FOLLOW_ON = set(tcv.SAME_SEQ_ROLES)
TOR_ROLES = {"timeout_reversal", "tor_retry"}
AUTH_ROLES = {"auth_only", "moto_auth_only"}
READ_ENTRY_MODES = {"05", "07", "80", "90", "91"}
TXN_LABEL = {"PURCHASE": "Purchase/Capture", "AUTH_ONLY": "Authorization Only", "MOTO_PURCHASE": "Mail/Phone Purchase",
             "MOTO_AUTH_ONLY": "Mail/Phone Authorization Only", "RETURN": "Merchandise Return", "BALANCE_INQUIRY": "Balance Inquiry"}
OBJECTIVE_TT = {"Mail/Phone Authorization Only": {"B"}, "Mail/Phone Purchase": {"6"}, "Authorization Only": {"3", "5"},
                "Purchase/Capture": {"0", "4"}, "Merchandise Return": {"7"}, "Balance Inquiry": {"E"}}

# ---------------------------------------------------------------- categories and defect catalogue
CATS = {  # id -> (label, effect on the test case verdict)
    "TD": ("Test data defect", "FAIL"), "TC": ("Test case defect", "FAIL"), "TCR": ("Test case issue to correct", "REVIEW"),
    "CIT": ("Citation issue", "REVIEW"), "INPUT": ("Awaiting client input / run-time value", "BLOCKED"),
    "SME": ("To confirm with BUYPASS / SME", "REVIEW"), "INTENDED": ("Intended violation of a negative test (correct)", None),
    "AI_NA": ("AI validator rule not applicable - value checked and correct", None), "INFO": ("Information", None),
    "SCOPE": ("Not checked - request type outside the validator's trained ATL105 rules", None)}
CAT_ORDER = ["TD", "TC", "TCR", "CIT", "INPUT", "SME", "INTENDED", "AI_NA", "INFO", "SCOPE"]
DEFECTS = {
    # ---- test data
    "D-01": dict(cat="TD", title="Totals Request: card bucket count/amount does not match the transactions of the flow",
                 rules={"TOTALS_FLOW_MISMATCH"}, refs=["S9.2-GT", "S9.2-PUR", "S9.2-REV", "S12.6-CAT", "EL16", "EL15"],
                 check="Open the Totals Request file, read CardLabel / CardTypeTotalCount / CardTypeTotalAmount / GrandTotal and "
                       "recompute them from the earlier steps of the same test case (Section 9.2).",
                 fix="Compute the totals from the approved steps of the flow; an Authorization Only goes to the AO bucket and is "
                     "left out of the grand total; a bucket with net count 0 is not sent; count and amount must agree."),
    "D-02": dict(cat="TD", title="Optional Segment 100 fields filled with invalid values or with values whose conditions do not "
                                 "apply",
                 rules={"FIELD_5", "X_PIN_BLOCK_FORMAT", "X_OPTIONAL_FIELD_CONDITION"},
                 refs=["EL5-PRE", "EL33-FMT", "S12.1-ROW16"],
                 check="Open the test data file and read ApprovalNumber, EncryptedPINBlockData, LocalDateandLocalTime, "
                       "PumpLaneNumber, FuelPurchaseAmount, CashAmount; compare with the ATL105 rule of each field.",
                 fix="Send only the conditional fields whose condition holds: no Approval Number / Local Date and Time on a new "
                     "authorization, a real DUKPT KSN + PIN block (32-36 characters) or nothing in Element 33, no fuel or pump "
                     "data outside petroleum."),
    "D-03": dict(cat="TD", title="Segment 145 (Enhanced Fleet Request) is not valid: length indicator over 3 digits, segment "
                                 "over its maximum and data not in <tag><len><data> format",
                 rules={"X_SEG_LENGTH_FIELD", "X_SEG_MAX", "X_TLV_FORMAT"}, refs=["S12.31-LEN", "S12.31-TLV", "S11.1-145"],
                 check="Open the file, count the characters of SegmentLengthIndicator (must be 3) and try to split "
                       "EnhancedFleetData into 3-digit tag + 3-digit length + data.",
                 fix="Build Segment 145 from real fleet sub-segments (TLV), keep the segment within 999 characters, or drop the "
                     "segment for a MasterCard Credit (non-fleet) card."),
    "D-04": dict(cat="TD", title="A field of another data segment breaks its Chapter 13 format (character type or length)",
                 rules={"X_FIELD_FORMAT"}, refs=[],
                 check="Open the file and compare the value with the element's character type and maximum length in Chapter 13.",
                 fix="Generate the value with the element's character type and length."),
    "D-05": dict(cat="TD", title="Segment length of another data segment does not match its content",
                 rules={"X_SEGMENT_LENGTH"}, refs=["EL84"],
                 check="Recalculate the segment length with the Field Separator rule of the segment (section 12.x).",
                 fix="Recalculate the Segment Length after the content is final."),
    "D-06": dict(cat="TD", title="Test data differs from the test case it was generated from",
                 rules={"TD_DIFFERS_FROM_TC", "X_TD_DIFFERS_FROM_TC"}, refs=[],
                 check="Compare the value in the test data file with the value in test_cases.jsonl (request -> segment -> field).",
                 fix="Regenerate the test data file from the test case."),
    "D-07": dict(cat="TD", title="Test data does not meet a rule check written in its own test case",
                 rules={"X_RULE_CHECK"}, refs=[],
                 check="Read the rule_checks entry in test_cases.jsonl and apply it to the test data file.",
                 fix="Correct the test data (or the rule check) so that the test case's own assertion holds."),
    "D-08": dict(cat="TD", title="Variable Information table indicator is not an ATL105 table ID",
                 rules={"X_VI_TABLE_ID"}, refs=["EL111"],
                 check="Read VariableInformationIndicator: it must be a 3-character table ID listed for Element 111.",
                 fix="Use a valid table ID."),
    "D-09": dict(cat="TD", title="A negative test breaks more than the one rule it is meant to break",
                 rules={"X_UNINTENDED"}, refs=[],
                 check="Compare the failing field with the field the test case deliberately changes (flags "
                       "DELIBERATE_CONSTRAINT_VIOLATION / method deliberate_violation_*).",
                 fix="Keep every other field valid so the decline can only come from the rule under test."),
    "D-10": dict(cat="TD", title="Sequence Number used twice for new transactions on the same terminal",
                 rules={"X_SEQ_DUPLICATE"}, refs=["EL86-UNIQUE"],
                 check="Search the test data folder for the Sequence Number on the same Terminal Identifier.",
                 fix="Allocate a unique Sequence Number per new transaction and terminal."),
    "D-11": dict(cat="TD", title="Segment 100 / 111 / 130 / 105 value breaks an ATL105 rule (trained validator)",
                 rules=set(), refs=[],
                 check="Open the field named in the finding and apply the ATL105 rule quoted with it.",
                 fix="Correct the value as stated in the finding."),
    "D-12": dict(cat="TD", title="Another data segment is longer than its ATL105 maximum or breaks its TLV layout",
                 rules=set(), refs=[],
                 check="Add up the segment (section 12.x Field Separator rule) and compare with the maximum length in section "
                       "11.1 / 12.x.",
                 fix="Keep the segment within its maximum length and in its layout."),
    "D-13": dict(cat="TD", title="Card read from the chip / stripe, but Card Discretionary Block Data holds only an MMYY expiry "
                                 "instead of the Track 2 data after the '='",
                 rules={"FIELD_12"}, refs=["EL12-TRACK2", "EL12-MAX"],
                 check="Open the test data file: Table 005 starts with 05 / 07 / 90 / 91 (card read), but CardDiscretionaryBlockData "
                       "is 4 digits (MMYY). For a read card it must be the Track 2 data after the '=': expiry YYMM, 3-digit "
                       "service code, discretionary data.",
                 fix="Send the test card's Track 2 (equivalent) data after the '=' (YYMM + service code 2xx for a chip card + "
                     "discretionary data), or use a keyed entry mode if only the expiry is known."),
    "D-16": dict(cat="TD", title="Follow-on step sends the keyed card's MMYY expiry instead of YYMM",
                 rules=set(), refs=["S10.1.8.2", "S10.1.1.2-FOLLOW"],
                 check="Open the follow-on step file (reversal, time-out reversal, cancellation, void, completion) and read "
                       "CardDiscretionaryBlockData: it repeats the original's MMYY expiry (e.g. 0131 = January 2031). A "
                       "follow-on transaction is formatted from stored card data as Account Number = YYMM (3101).",
                 fix="Send the expiry as YYMM in every follow-on step: swap month and year of the original's MMYY."),
    "D-15": dict(cat="TD", title="Positive boundary test: the value is not at the documented limit or is not a valid value",
                 rules={"X_BOUNDARY_NOT_AT_LIMIT", "X_BOUNDARY_INVALID_VALUE"}, refs=["EL2", "EL87-VAL", "EL44"],
                 check="Read the objective (field and limit), then the value in the test data: it must have exactly that many "
                       "characters and be a valid value of the element.",
                 fix="Send a valid value of exactly the documented maximum length; if no such value can be approved (a "
                     "MasterCard card number is 16 digits, not 24), drop the positive boundary test."),
    # ---- test case
    "C-01": dict(cat="TC", title="Same request as another test case but a different expected result",
                 rules={"X_SAME_REQUEST_DIFFERENT_OUTCOME"}, refs=["S2.3.1-MATCH"],
                 check="Open both test data files side by side: apart from the Sequence Number they are identical, but one "
                       "test expects approval and the other a decline.",
                 fix="Give the decline test its own trigger (decline test card / amount / simulator rule) so its request differs."),
    "C-02": dict(cat="TC", title="Negative test premise contradicts ATL105 (the rule it claims to break does not exist)",
                 rules={"X_NEG_PREMISE"}, refs=["S10.1.2-LIST", "S10.14.3-ONLINE", "S12.1-ROW3", "S12.3-ROW3", "S12.7-ROW12"],
                 check="Read the test objective, then the cited ATL105 rule: the transaction/field is supported or required.",
                 fix="Remove the test or rewrite it around a rule ATL105 states."),
    "C-03": dict(cat="TCR", title="Test case marked 'ready' although its Terminal Identifier is a placeholder",
                 rules={"READY_WITH_PLACEHOLDER"}, refs=["EL102"],
                 check="TEST_CASES.md / test_cases.jsonl status = ready, merchant_provenance = placeholder.",
                 fix="Keep the test case out of 'ready' until the real Terminal Identifier is supplied."),
    "C-04": dict(cat="TCR", title="Expected Response Code not stated for a step, or several codes accepted",
                 rules={"EXPECTED_RESPONSE_OPEN"}, refs=["EL83-0", "EL83-2"],
                 check="Open tx_legs[i].expect / steps[i].expected_response: no response_code value; or expected_response."
                       "response_code.acceptable_values lists several approval codes (e.g. 0 and 2) for a non-totals step. "
                       "Approved-Totals codes 5/6/B/D/N/W for a Totals Request are accepted (Element 83, p.404-405).",
                 fix="State the expected Response Code (from BUYPASS or a reference run)."),
    "C-05": dict(cat="TCR", title="Test objective / name does not match the message sent or the expected result",
                 rules={"X_OBJECTIVE_MISMATCH", "X_NAME_OUTCOME_MISMATCH"}, refs=["EL78"],
                 check="Compare test_objective and the name with the Prompt Code Transaction Type in the test data and with "
                       "the expected Response Code.",
                 fix="Correct the objective / name text."),
    "C-06": dict(cat="SME", title="Time-out Reversal sent after a step that is expected to be answered",
                 rules={"X_TOR_AFTER_RESPONSE"}, refs=["S2.3-TOR", "S2.3.1-TIMEOUT", "S2.3.1-LATE", "S2.3.1-QUEUE"],
                 check="Read tx_legs: the original step expects a Response Code and has no 'no response' harness directive, "
                       "yet the next step is a Time-out Reversal.",
                 fix="Make the original step a fault-injected 'no response' (or a late response the harness ignores)."),
    "C-07": dict(cat="SME", title="'Different terminal' negative uses a generated terminal that is not set up at BUYPASS",
                 rules={"X_NEG_RANDOM_TERMINAL"}, refs=["EL102", "S2.3.1-DEVICE"],
                 check="Read the follow-on step's Terminal Identifier: method deliberate_violation_missing_carry, a generated "
                       "value.",
                 fix="Use a second real terminal of the same merchant so the decline proves the same-device rule."),
    "C-08": dict(cat="SME", title="Same request as another test case (difference only on the device / response side)",
                 rules={"X_SAME_REQUEST"}, refs=[],
                 check="Compare both test data files: identical apart from the Sequence Number.",
                 fix="Check the difference on the device or in the response; optionally use another card."),
    "C-09": dict(cat="SME", title="Segment sent although ATL105 scopes it to other card programs",
                 rules={"X_SEGMENT_SCOPE"}, refs=["S12.31-SCOPE", "S11.1-101"],
                 check="Read the segment description in section 11.1 / 12.x and compare with the card program (MasterCard "
                       "Credit, card type 020).",
                 fix="Confirm with BUYPASS or drop the segment from MasterCard Credit tests."),
    "C-10": dict(cat="TC", title="Positive test does not send the condition it claims (table, segment or field value)",
                 rules={"X_CONDITION_NOT_SENT"}, refs=["EL111"],
                 check="Read test_objective and the name, then open the test data: the claimed table, segment or value is not in "
                       "the request, and open_items / awaiting_inputs do not ask for it.",
                 fix="Send the claimed data with the segments it depends on, or keep the test out of 'ready' with the missing "
                     "input named, or re-scope / withdraw it when the condition does not apply."),
    "C-11": dict(cat="TC", title="Positive test expects response information that its request does not ask for",
                 rules={"X_RESPONSE_NOT_INITIATED"}, refs=["EL116-INIT"],
                 check="Read tx_legs[i].response_tables (the Segment 112 table the test expects back), then open the request: "
                       "the Segment 111 table that initiates it (003 AVS, 004 card verification) is not sent.",
                 fix="Send the initiating table with the client's test card data and assert the result, or drop the response "
                     "expectation / re-scope the test."),
    "C-12": dict(cat="SME", title="Table 048 (MasterCard 3-D Secure authentication data) sent on a POS transaction",
                 rules={"X_TABLE_SCOPE"}, refs=["APPI-048", "APPI-048-LEN", "APPI-048-PP"],
                 check="Open the test data: Segment 111 carries Table 048 (Program Protocol), while Prompt Code and Table 005 "
                       "show a POS card-present or keyed transaction, not an e-commerce (3-D Secure) transaction.",
                 fix="Confirm with BUYPASS; otherwise send Table 048 only in e-commerce (3-D Secure) tests, or re-scope the "
                     "test."),
    # ---- citations
    "Q-01": dict(cat="CIT", title="Quote not found in ATL105 or missing (generator note or other text cited as ATL105)",
                 rules={"QUOTE_NOT_IN_SPEC", "QUOTE_MISSING", "QUOTE_NO_PAGE"}, refs=[], check="Search the quoted text on the cited page.",
                 fix="Quote the ATL105 sentence word for word."),
    "Q-02": dict(cat="CIT", title="Quote is on a different page than cited", rules={"QUOTE_WRONG_PAGE", "QUOTE_PAGE_DRIFT",
                                                                                 "CITATION_OUTDATED_PAGE"},
                 refs=[], check="Find the quote: it is on another PDF page.", fix="Correct the page number."),
    "Q-03": dict(cat="CIT", title="Quote is not word-for-word", rules={"QUOTE_PARAPHRASED"}, refs=[],
                 check="Compare the quote with the page text.", fix="Quote word for word."),
    "Q-04": dict(cat="CIT", title="Citation or device checkpoint from an unrelated section or document",
                 rules={"QUOTE_CONTEXT", "X_CHECKPOINT_CONTEXT", "X_BASIS_CONTEXT", "X_BASIS_PAGE", "CITATION_WRONG_SECTION"}, refs=[],
                 check="Read the cited text: it covers another transaction, card or industry.", fix="Cite the rule that applies."),
    # ---- client input / run-time values
    "I-01": dict(cat="INPUT", title="Terminal Identifier is a generated placeholder", rules={"TERMINAL_PLACEHOLDER"},
                 refs=["EL102", "EL102-STATE"], check="merchant_provenance = placeholder; flags MERCHANT_PLACEHOLDER.",
                 fix="Client / BUYPASS to supply the real Terminal Identifier."),
    "I-02": dict(cat="INPUT", title="Value filled in at run time from the previous response (expected for follow-on steps)",
                 rules={"RUNTIME_PLACEHOLDER", "SEGMENT_LENGTH_RUNTIME"}, refs=["EL5-REV"],
                 check="The field holds text in angle brackets, e.g. <ApprovalNumber from step 1 response>.",
                 fix="The harness substitutes the value from the previous response and recalculates the Segment Length."),
    "I-03": dict(cat="INPUT", title="Variable Information table value awaited from the client (Segment 111 length to recalculate)",
                 rules={"X_AWAITING_TABLE"}, refs=["S12.10-LEN", "S12.10-FS"],
                 check="VariableInformationLength / VariableInformation hold <AWAITING:...>; awaiting_inputs lists the value.",
                 fix="Fill in the client value and recalculate Variable Information Length and Segment 111 Segment Length."),
    "I-04": dict(cat="INPUT", title="EMV chip data is generated, not from a card / simulator",
                 rules={"EMV_CA_CHECKSUM_SAMPLE", "EMV_PLACEHOLDER_CRYPTO", "EMV_TVR_CVM_MISMATCH", "EMV_CONTACTLESS_TEMPLATE"},
                 refs=["EL190-EXACT", "EL187-USE"], check="See the EMV finding text in the guide / sheet.",
                 fix="Use chip data captured from the client's test card or card simulator."),
    "I-05": dict(cat="INPUT", title="Totals Request device versions are generated placeholders", rules={"TOTALS_VERSION_PLACEHOLDER"},
                 refs=["EL43", "EL96", "EL39"], check="Hardware/Software/Firmware Version hold random strings.",
                 fix="Client to supply the versions of the certified device."),
    "I-06": dict(cat="INPUT", title="Optional segment content is synthesized - real client data needed before an approval can be "
                                    "expected", rules={"X_SYNTHESIZED_CONTENT"}, refs=[],
                 check="In test_cases.jsonl the segment fields have method format_synthesis / spec_tlv_composite.",
                 fix="Client to supply the data listed in the finding; keep the test case out of 'ready' until then."),
    "I-07": dict(cat="INPUT", title="Decline trigger needed: the request is identical to the approval test",
                 rules={"X_SAME_REQUEST_NEEDS_TRIGGER"}, refs=[],
                 check="open_items asks for a decline test card / simulator trigger.",
                 fix="Client to supply the decline trigger; until then the test sends the approval test's request."),
    "I-08": dict(cat="INPUT", title="No test data generated for a host test case", rules={"X_NO_TEST_DATA"}, refs=[],
                 check="test_data_file is empty in test_cases.jsonl; status blocked_no_test_data.",
                 fix="Generate the test data when the missing input is available."),
    "I-09": dict(cat="INPUT", title="Segment 100 value awaited from the client (<AWAITING:...>)", rules={"X_AWAITING_VALUE"},
                 refs=["EL2"], check="The field holds <AWAITING:...>; the test case field has method awaiting_client_value.",
                 fix="Client to supply the value (for Account Number: a MasterCard test card)."),
    "I-10": dict(cat="INPUT", title="Claimed table not in the test data yet - the test case lists it as client data to supply",
                 rules={"X_CONDITION_AWAITED"}, refs=["EL111"],
                 check="test_objective names the table; open_items names it as client data; the status is not 'ready'.",
                 fix="Client to supply the table data; then add the table to Segment 111 and recalculate its length."),
    # ---- to confirm
    "S-01": dict(cat="SME", title="Expiry format for stored credentials (entry mode 10) not stated", rules={"COF_EXPIRY_FORMAT"},
                 refs=["EL12", "S10.1.8.2"], check="See finding text.", fix="Confirm with BUYPASS."),
    "S-02": dict(cat="SME", title="Totals: Authorization Only (AO) bucket after the flow", rules={"TOTALS_AO_BUCKET"},
                 refs=["S9.2-GT"], check="See finding text.", fix="Confirm with BUYPASS."),
    "S-03": dict(cat="SME", title="Segment length: ATL105 Field Separator rule of the segment is unclear",
                 rules={"X_SEGMENT_LENGTH_UNCLEAR"}, refs=["S12.38-FS", "S12.38-NOFS", "S12.7-FS"],
                 check="Recalculate the length under both readings shown in the finding.", fix="Confirm with BUYPASS."),
    "S-04": dict(cat="SME", title="Network Token Input Indicator 'Y' while Element 2 carries a clear card number",
                 rules={"X_TOKEN_INDICATOR"}, refs=["S12.38-T004", "EL2"], check="Parse Network Token Data table 004.",
                 fix="Confirm with BUYPASS which field carries the token when the indicator is Y."),
    "S-05": dict(cat="SME", title="Other points to confirm", rules=set(), refs=[], check="See finding text.",
                 fix="Confirm with BUYPASS / SME."),
    "S-06": dict(cat="SME", title="Totals after a Merchandise Return: the net amount is negative, but ATL105 defines no sign "
                                 "for the totals amounts", rules={"TOTALS_NEGATIVE_NET"}, refs=["S9.2-RET", "EL15"],
                 check="Open the Totals Request file: count 00001 is right (a return increases the count), the amount nets "
                       "to minus the return amount, and the file sends 00000000.",
                 fix="Confirm with BUYPASS how a negative net amount is reported in the Totals Request."),
}
RULE_DEFECT = {r: d for d, x in DEFECTS.items() for r in x["rules"]}
INTENDED_ID, AI_NA_ID, INFO_ID = "N-01", "V-01", "INFO"

# validator rules that the extended checks replace for this run
REPLACED = {"SAME_REQUEST_AS_OTHER_TC", "EXTRA_SEGMENTS"}


def norm_key(s):
    return re.sub(r"[^a-z0-9]", "", str(s).lower())


def is_ph(v):
    return bool(re.match(r"^<.*>$", str(v or "")))


def flat(v):
    if isinstance(v, dict):
        return "".join(str(x) for x in v.values())
    return "" if v is None else str(v)


def esc(s):
    return re.sub(r"\s*[\r\n]+\s*", " ", str(s)).replace("|", "\\|")


def cell(v, empty="(empty)"):
    if v is None:
        return "(not sent)"
    s = flat(v)
    if not s:
        return empty
    if len(s) > 60:
        s = s[:40] + "…" + s[-12:]
    return "`" + s.replace("|", "\\|").replace("`", "'") + "`"


def amount(v):
    return tdv.amount(v)


def plural(n, word, many=None):
    return f"{n:,} {word if n == 1 else many or word + 's'}"


# ---------------------------------------------------------------- test data access
def td_message(td):
    root = next(iter(td))
    return root, td[root]


def segs_by_no(msg):
    out = {}
    for k, x in msg.items():
        if isinstance(x, dict):
            out[SEG_NUMBER.get(k) or str(x.get("SegmentType") or k)] = (k, x)
    return out


def td_field(seg, name):
    nk = norm_key(re.sub(r"\s*\[\d+\]$", "", name))
    for k, v in seg.items():
        if norm_key(k) == nk:
            return k, v
    return None, None


def vi_sections(seg):
    return [(s.get("VariableInformationIndicator", ""), s.get("VariableInformationLength", ""), s.get("VariableInformation", ""))
            for s in (seg or {}).get("VariableInformationSection") or []]


def line_of(path, key, nth=1):
    hits = [i + 1 for i, s in enumerate(path.read_text(encoding="utf-8").splitlines()) if f'"{key}":' in s]
    return hits[nth - 1] if len(hits) >= nth else None


def parse_tlv3(data):
    """<tag n3><len n3><data> items; returns (items, problem)."""
    items, i = [], 0
    while i < len(data):
        tag, ln = data[i:i + 3], data[i + 3:i + 6]
        if not (re.fullmatch(r"\d{3}", tag or "") and re.fullmatch(r"\d{3}", ln or "")):
            return items, f"at position {i + 1} '{data[i:i + 6]}' is not a 3-digit tag followed by a 3-digit length"
        n = int(ln)
        if i + 6 + n > len(data):
            return items, f"tag {tag} declares {n} characters but only {len(data) - i - 6} remain"
        items.append((tag, n, data[i + 6:i + 6 + n]))
        i += 6 + n
    return items, None


def segment_length(no, seg):
    """(declared key, declared value, computed, alternative readings) for an extra segment."""
    key = "SegmentLength" if "SegmentLength" in seg else "SegmentLengthIndicator"
    declared = str(seg.get(key, ""))
    vals = [flat(v) for k, v in seg.items() if k != key]
    data = [flat(v) for k, v in seg.items() if k not in (key, "SegmentType")]
    if any(is_ph(v) for v in vals):
        return key, declared, None, {}
    all_fs = len(flat(seg.get("SegmentType"))) + 1 + len(declared) + 1 + sum(len(v) for v in data) + len(data)
    if no == "102":
        g = lambda k: flat(seg.get(k))  # noqa: E731
        products = 1
        comp = (len(g("SegmentType")) + 1 + len(declared) + 1 + len(g("ServiceLevel")) + len(g("NumberofProducts"))
                + products * 0 + len(g("ProductCode")) + len(g("UnitofMeasure")) + len(g("Quantity")) + 1
                + len(g("UnitPrice")) + 1 + len(g("ProductAmount")) + 1)
        return key, declared, comp, {}
    if no == "153":
        only12 = len(flat(seg.get("SegmentType"))) + 1 + len(declared) + sum(len(v) for v in data)
        return key, declared, all_fs, {"Field Separator only between Fields 1 and 2 (literal reading of p.310)": only12,
                                        "Field Separator after Fields 1 and 2, none after Field 3": only12 + 1}
    if no == "108":
        return key, declared, all_fs, {"Field Separators up to Field 13 only (literal reading of p.238)": all_fs - 2}
    return key, declared, all_fs, {}


# ---------------------------------------------------------------- one test case
class TC:
    def __init__(self, rec, line, run_dir):
        self.rec, self.line, self.id = rec, line, rec["id"]
        self.findings = []
        self.msgs, self.roots, self.files = {}, {}, {}
        self.rsteps = rec.get("steps") or ([dict(step=1, request=rec.get("request") or {},
                                                 data_section_1=rec.get("data_section_1"))] if rec.get("request") else [])
        legs = {lg["step"]: lg for lg in rec.get("tx_legs") or []}
        self.legs = legs
        files = [Path(p).name for p in rec.get("test_data_leg_files") or ([rec["test_data_file"]] if rec.get("test_data_file")
                                                                           else [])]
        for i, f in enumerate(files, 1):
            path = run_dir / "test_data" / f
            self.files[i] = f
            if path.exists():
                self.roots[i], self.msgs[i] = td_message(json.loads(path.read_text(encoding="utf-8")))
        self.synth_legs = []
        if not legs and 1 in self.msgs:
            tt = flat((self.msgs[1].get("Standard Message Data Segment") or {}).get("PromptCode"))[:1]
            rc = ((rec.get("expected_response") or {}).get("response_code") or {})
            self.synth_legs = [dict(step=1, role=ROLE_BY_TT.get(tt, "purchase"), code=tt or None, of=None, link_defs=[],
                                    expect={"response_code": rc.get("value"), "tier": rc.get("tier")})]
            self.legs = {1: self.synth_legs[0]}
        self.tc = self.meta = self.rep = self.ctx = None

    @property
    def negative(self):
        r = self.rec
        return r.get("polarity") == "Negative" or bool(r.get("is_negative"))

    def role(self, n):
        return (self.legs.get(n) or {}).get("role") or ""

    def of(self, n):
        return (self.legs.get(n) or {}).get("of")

    def effective_role(self, n):
        """'purchase' that follows an Authorization Only in the same test case is its completion."""
        role, of = self.role(n), self.of(n)
        if role == "purchase" and of and self.role(of) in AUTH_ROLES:
            return "completion"
        return role

    def add(self, step, sev, rule, title, field="", value="", why="", fix="", refs=(), source="Extended check", cat=None,
            defect=None, label="", seg=None):
        self.findings.append(dict(step=step, severity=sev, rule=rule, title=title, field=field, value=str(value), why=why,
                                  fix=fix, refs=list(refs), source=source, cat=cat, defect=defect, label=label, seg=seg))

    def deliberate(self):
        out = set()
        for s in self.rsteps:
            for seg, fl in (s.get("request") or {}).items():
                for k, x in fl.items():
                    if isinstance(x, dict) and (str(x.get("method") or "").startswith("deliberate")
                                                or any("DELIBERATE_CONSTRAINT_VIOLATION" in g for g in x.get("flags") or [])):
                        out.add(norm_key(re.sub(r"\s*\[\d+\]$", "", k)))
        for g in self.rec.get("flags") or []:
            m = re.match(r"DELIBERATE_CONSTRAINT_VIOLATION: '([^']+)'", g)
            if m:
                out.add(norm_key(m.group(1)))
        obj = (self.rec.get("test_objective") or "").lower()
        if re.search(r"entry mode \d{2}", obj):
            out.add("variableinformation")
        if "number of segments" in obj:
            out.add("numberofsegments")
        out |= {f"segment{no}" for no in re.findall(r"segment (\d{3})", obj)}
        for s in self.rsteps:
            for fl in (s.get("request") or {}).values():
                for k in fl:
                    base = re.sub(r"\s*\[\d+\]$", "", k)
                    if len(base) > 6 and base.lower() in obj:
                        out.add(norm_key(base))
        return out

    def deliberate_step(self, n):
        """Fields this step changes on purpose (negative tests)."""
        out = set()
        for s in self.rsteps:
            if s["step"] != n:
                continue
            for fl in (s.get("request") or {}).values():
                for k, x in fl.items():
                    if isinstance(x, dict) and (str(x.get("method") or "").startswith("deliberate")
                                                or any("DELIBERATE_CONSTRAINT_VIOLATION" in g for g in x.get("flags") or [])):
                        out.add(norm_key(re.sub(r"\s*\[\d+\]$", "", k)))
        return out

    def expected(self, n):
        """(code, tier, acceptable set, no_response) for step n."""
        lg = self.legs.get(n) or {}
        e = lg.get("expect") or {}
        if "none" in e:
            return None, None, set(), True
        steps = self.rec.get("steps") or []
        rc = ((steps[n - 1].get("expected_response") or {}).get("response_code") or {}) if len(steps) >= n else \
            ((self.rec.get("expected_response") or {}).get("response_code") or {})
        code = rc.get("value") or e.get("response_code")
        acc = set(rc.get("acceptable_values") or e.get("acceptable_codes") or ([code] if code else []))
        return code, rc.get("tier") or e.get("tier"), acc, False


# ---------------------------------------------------------------- extended checks
def eval_rule_check(t, rc):
    n = rc.get("step", 1)
    msg = t.msgs.get(n)
    if msg is None:
        return None, "the step has no test data"
    segs = segs_by_no(msg)

    def val(seg_no, element, m=msg):
        s = segs_by_no(m).get(str(seg_no))
        if not s:
            return None
        _, v = td_field(s[1], element)
        return None if v is None else flat(v)

    def table(tid):
        s = segs.get("111")
        for ind, _, data in vi_sections(s[1] if s else {}):
            if ind == tid:
                return data
        return None

    op = rc.get("op")
    if op == "segment_present":
        ok, detail = str(rc["segment"]) in segs, f"Segment {rc['segment']} {'is' if str(rc['segment']) in segs else 'is not'} sent"
    elif op == "segments_not_together":
        ok = not all(str(x) in segs for x in rc["segments"])
        detail = f"Segments {', '.join(rc['segments'])}: " + ", ".join(f"{x} {'sent' if str(x) in segs else 'not sent'}"
                                                                       for x in rc["segments"])
    elif op in ("value_slice_in", "value_length_max", "field_absent"):
        v = val(rc.get("segment", "100"), rc.get("element"))
        if v is None or is_ph(v):
            return None, f"{rc.get('element')} is {'a run-time value' if v else 'not in the test data'}"
        if op == "value_slice_in":
            part = v[rc["start"] - 1:rc["end"]]
            ok, detail = part in rc["allowed"], f"{rc['element']} positions {rc['start']}-{rc['end']} = '{part}', allowed " \
                                                f"{', '.join(rc['allowed'])}"
        elif op == "value_length_max":
            ok, detail = len(v) <= rc["max"], f"{rc['element']} '{v}' has {len(v)} characters, maximum {rc['max']}"
        else:
            ok, detail = v == "", f"{rc['element']} = '{v}'"
    elif op == "fields_absent":
        vals = {e: val(rc["segment"], e) for e in rc.get("elements") or []}
        ok = all(not v for v in vals.values())
        detail = ", ".join(f"{e} = '{v or ''}'" for e, v in vals.items())
    elif op == "table_slice_in":
        data = table(rc["table_id"])
        if data is None:
            ok, detail = False, f"Table {rc['table_id']} is not sent"
        elif is_ph(data):
            return None, f"Table {rc['table_id']} is awaiting a client value"
        else:
            part = data[rc["start"] - 1:rc["end"]]
            ok, detail = part in rc["allowed"], f"Table {rc['table_id']} positions {rc['start']}-{rc['end']} = '{part}', " \
                                                f"allowed {', '.join(rc['allowed'][:6])}" + ("…" if len(rc["allowed"]) > 6 else "")
    elif op == "value_differs_from_step":
        other = t.msgs.get(rc.get("other_step", 1))
        a, b = val("100", rc["element"]), val("100", rc["element"], other) if other else None
        ok, detail = a != b, f"{rc['element']} step {n} '{a}' vs step {rc.get('other_step', 1)} '{b}'"
    elif op == "product_total_equals_amounts":
        s102 = segs.get("102")
        if not s102:
            return None, "Segment 102 not sent"
        pa = flat(s102[1].get("ProductAmount"))
        amts = [val("100", e) or "" for e in ("Fuel Purchase Amount", "Nonfuel Amount", "Tax Amount", "Cash Amount")]
        if is_ph(pa) or any(is_ph(a) for a in amts) or not re.fullmatch(r"\d+", pa or ""):
            return None, "run-time or non-numeric amount"
        total = sum(int(a) for a in amts if a.isdigit())
        ok, detail = int(pa) == total, f"Product Amount {pa} vs Elements 41+58+99+17 = {total}"
    else:
        return None, f"operation '{op}' not evaluated"
    if rc.get("expect", "hold") != "hold":
        ok = not ok
    return ok, detail


def check_rule_checks(t):
    t.rule_results = []
    for rc in t.rec.get("rule_checks") or []:
        ok, detail = eval_rule_check(t, rc)
        t.rule_results.append((rc, ok, detail))
        if ok is False:
            t.add(rc.get("step", 1), "FAIL", "X_RULE_CHECK", f"Test data does not meet the test case's own rule check {rc.get('id')}",
                  field=f"test case rule_checks {rc.get('id')} (step {rc.get('step', 1)})", value=detail,
                  why=f"The test case asserts '{rc.get('basis', '')}' ({rc.get('op')}, expect {rc.get('expect', 'hold')}), but "
                      f"in the test data: {detail}.",
                  fix="Correct the test data (or the rule check) so that the assertion holds.",
                  label=rc.get("element") or (f"Table {rc['table_id']}" if rc.get("table_id") else f"Segment {rc.get('segment')}"))


def check_fidelity(t):
    for s in t.rsteps:
        n, msg = s["step"], t.msgs.get(s["step"])
        if msg is None:
            continue
        fname = t.files.get(n, "?")
        for seg_name, flds in (s.get("request") or {}).items():
            if seg_name in ("Totals Data Segment", "EMV Request Data Segment"):
                continue
            vals = {k: x for k, x in flds.items() if isinstance(x, dict)}
            not_sent = any("SEGMENT_NOT_SENT" in g for x in vals.values() for g in x.get("flags") or [])
            has_vals = any(x.get("value") not in (None, "") for x in vals.values())
            td_seg = msg.get(seg_name)
            if td_seg is None:
                if has_vals and not not_sent:
                    t.add(n, "FAIL", "X_TD_DIFFERS_FROM_TC", f"{seg_name} is in the test case but not in the test data",
                          field=f"{fname} -> {seg_name}", value="(absent)", label=seg_name,
                          why="The test case request carries this segment with values, but the test data file does not send it.",
                          fix="Regenerate the test data file from the test case.")
                continue
            if not_sent and not has_vals:
                t.add(n, "FAIL", "X_TD_DIFFERS_FROM_TC", f"{seg_name} is sent although the test case says it is not sent",
                      field=f"{fname} -> {seg_name}", value="(present)", label=seg_name,
                      why="The test case marks the segment SEGMENT_NOT_SENT, but the test data file contains it.",
                      fix="Regenerate the test data file from the test case.")
                continue
            if seg_name == "Variable Information Data Segment":
                secs = vi_sections(td_seg)
                for k, x in vals.items():
                    m = re.match(r"Variable Information(?: (Indicator|Length))?(?: \[(\d+)\])?$", k)
                    if not m or x.get("value") is None:
                        continue
                    i = int(m.group(2) or 1)
                    part = {"Indicator": 0, "Length": 1, None: 2}[m.group(1)]
                    if i > len(secs):
                        t.add(n, "FAIL", "X_TD_DIFFERS_FROM_TC", f"Variable Information table {i} is missing in the test data",
                              field=f"{fname} -> {seg_name} -> VariableInformationSection[{i - 1}]", value="(absent)",
                              label="Variable Information", why=f"The test case has '{x.get('value')}' for {k}.",
                              fix="Regenerate the test data file from the test case.")
                        continue
                    if secs[i - 1][0] in ("005", "031"):
                        continue  # compared by the trained validator
                    tdv_ = secs[i - 1][part]
                    if not is_ph(tdv_) and str(tdv_) != str(x.get("value")):
                        t.add(n, "FAIL", "X_TD_DIFFERS_FROM_TC", f"{k} in the test data differs from the test case",
                              field=f"{fname} -> {seg_name} -> VariableInformationSection[{i - 1}]", value=tdv_,
                              label="Variable Information", why=f"File '{tdv_}', test case '{x.get('value')}'.",
                              fix=f"'{x.get('value')}' (regenerate the file)")
                continue
            for k, x in vals.items():
                if k in ("Segment Length", "Segment Length Indicator") or str(x.get("method") or "").startswith("derived"):
                    continue
                key, tdval = td_field(td_seg, k)
                if key is None:
                    if x.get("value") not in (None, ""):
                        t.add(n, "FAIL", "X_TD_DIFFERS_FROM_TC", f"{k} is in the test case but not in the test data",
                              field=f"{fname} -> {seg_name}", value="(absent)", label=k,
                              why=f"The test case has '{x.get('value')}'.", fix="Regenerate the test data file.")
                    continue
                tdval, tcval = flat(tdval), x.get("value")
                if is_ph(tdval):
                    continue
                if tcval is None:
                    if tdval and seg_name != "Standard Message Data Segment":
                        t.add(n, "FAIL", "X_TD_DIFFERS_FROM_TC", f"{k} is sent although the test case omits it",
                              field=f"{fname} -> {seg_name} -> {key}", value=tdval, label=k,
                              why=f"The test case leaves {k} out (method {x.get('method')}), but the file sends '{tdval}'.",
                              fix="(empty) - regenerate the file")
                    elif tdval and seg_name == "Standard Message Data Segment":
                        t.add(n, "FAIL", "X_TD_DIFFERS_FROM_TC", f"{k} is sent although the test case omits it",
                              field=f"{fname} -> {seg_name} -> {key}", value=tdval, label=k,
                              why=f"The test case leaves {k} out (method {x.get('method')}), but the file sends '{tdval}'.",
                              fix="(empty) - regenerate the file")
                    continue
                if seg_name == "Standard Message Data Segment":
                    continue  # compared by the trained validator
                if str(tcval) != tdval:
                    t.add(n, "FAIL", "X_TD_DIFFERS_FROM_TC", f"{k} in the test data differs from the test case",
                          field=f"{fname} -> {seg_name} -> {key}", value=tdval, label=k,
                          why=f"File '{tdval}', test case '{tcval}' (method {x.get('method')}).",
                          fix=f"'{tcval}' (regenerate the file)")


def element_constraint(ref, el, name):
    e = ref.get(str(el))
    if not e:
        return None
    for d in e["definitions"]:
        if norm_key(d.get("name")) == norm_key(name):
            c = d["constraints"]
            return c.get("characterType"), c.get("maxLength"), c.get("lengthMode")
    return None


def check_extra_segments(t, ref):
    for n, msg in t.msgs.items():
        fname = t.files[n]
        for no, (seg_name, seg) in segs_by_no(msg).items():
            if no in TRAINED_SEGMENTS or no not in EXTRA:
                continue
            mx, rule, srefs, supply = EXTRA[no]
            path = f"{fname} -> {seg_name}"
            st = str(seg.get("SegmentType", ""))
            if st != no:
                t.add(n, "FAIL", "X_SEGMENT_TYPE", f"{seg_name}: Segment Type is not {no}", field=f"{path} -> SegmentType",
                      value=st, label="Segment Type", why=f"Segment {no} has the fixed Segment Type value {no}.", fix=no,
                      refs=srefs[:1])
            key, declared, comp, alts = segment_length(no, seg)
            if comp is None:
                t.add(n, "BLOCKED", "SEGMENT_LENGTH_RUNTIME", f"{seg_name}: length must be recalculated at run time",
                      field=f"{path} -> {key}", value=declared, label=key,
                      why="The segment holds run-time values in angle brackets; its length can only be calculated after they "
                          "are filled in.", fix="Recalculate after substitution.", refs=srefs)
            elif rule in ("tlv", "tlv_153") and len(declared) != 3:
                t.add(n, "FAIL", "X_SEG_LENGTH_FIELD", f"{seg_name}: Segment Length Indicator has {len(declared)} digits",
                      field=f"{path} -> {key}", value=declared, label=key, refs=srefs, seg=no,
                      why=f"Field 2 (Element 84) of Segment {no} is 3 characters long (Len. 3), so '{declared}' cannot be sent; "
                          f"the segment itself is {comp} characters long.",
                      fix="Keep the segment within 999 characters so its length fits 3 digits.")
            elif str(comp).zfill(3) != declared:
                if alts and declared in {str(v).zfill(3) for v in alts.values()}:
                    pass
                else:
                    t.add(n, "FAIL", "X_SEGMENT_LENGTH", f"{seg_name}: Segment Length does not match the content",
                          field=f"{path} -> {key}", value=declared, label=key, refs=srefs + ["EL84"],
                          why=f"'{declared}' is declared, but the segment adds up to {comp:03d} with the Field Separator rule "
                              f"of Segment {no}.", fix=f"{comp:03d}")
            elif alts:
                readings = "; ".join(f"{k}: {v:03d}" for k, v in alts.items())
                t.add(n, "REVIEW", "X_SEGMENT_LENGTH_UNCLEAR", f"{seg_name}: length depends on an unclear Field Separator rule",
                      field=f"{path} -> {key}", value=declared, label=key, refs=srefs,
                      why=f"'{declared}' assumes a Field Separator after every field ({comp:03d}). ATL105 reads differently "
                          f"for this segment - {readings}.",
                      fix="Confirm the Field Separator rule of this segment with BUYPASS.")
            if comp and comp > mx:
                t.add(n, "FAIL", "X_SEG_MAX", f"{seg_name} is longer than its maximum ({mx})", field=path, value=comp,
                      label=seg_name, refs=srefs, why=f"The segment is {comp} characters long; ATL105 allows at most {mx}.",
                      fix=f"Keep the segment within {mx} characters.", seg=no)
            # TLV content
            if rule in ("tlv", "tlv_153"):
                dkey = "EnhancedFleetData" if no == "145" else "NetworkTokenData"
                data = flat(seg.get(dkey))
                items, prob = parse_tlv3(data)
                if prob and not is_ph(data):
                    t.add(n, "FAIL", "X_TLV_FORMAT", f"{seg_name}: data is not in <tag><len><data> format",
                          field=f"{path} -> {dkey}", value=data[:60] + ("…" if len(data) > 60 else ""), label=dkey, seg=no,
                          refs=["S12.31-TLV" if no == "145" else "S12.38-TLV"],
                          why=f"ATL105 sends this field as sub-segments <3-digit tag><3-digit length><data>, but {prob}.",
                          fix="Build the field from the sub-segments defined in the segment's layout tables.")
                if no == "153" and not prob:
                    t.tlv153 = items
                    for tag, ln, val in items:
                        spec_ = NET_TOKEN_TABLES.get(tag)
                        if not spec_:
                            t.add(n, "FAIL", "X_FIELD_FORMAT", f"Network Token Data: unknown table {tag}", field=f"{path} -> {dkey}",
                                  value=tag, label="Network Token Data", refs=["S12.38-TLV"],
                                  why=f"Table {tag} is not defined for Segment 153 (tables 001-007).", fix="Remove it.")
                            continue
                        name, fixed, mxl = spec_
                        bad = (fixed and ln != fixed) or ln > mxl or (tag in ("001", "002") and not val.isdigit())
                        if tag == "002" and not bad and not 1 <= int(val[2:]) <= 12:
                            bad = True
                        if bad:
                            t.add(n, "FAIL", "X_FIELD_FORMAT", f"Network Token Data table {tag} ({name}) has a wrong format",
                                  field=f"{path} -> {dkey}", value=f"{tag}{ln:03d}{val}", label="Network Token Data",
                                  refs=["S12.38-T001" if tag == "001" else "S12.38-T002" if tag == "002" else "S12.38-T004"],
                                  why=f"Table {tag} value '{val}' (length {ln}) does not fit the layout.", fix="Correct the value.")
                    ind = next((v for g, _, v in items if g == "004"), None)
                    pan = flat(msg.get("Standard Message Data Segment", {}).get("AccountNumber"))
                    if ind == "Y" and pan.isdigit() and tcv.luhn_ok(pan):
                        t.add(n, "REVIEW", "X_TOKEN_INDICATOR", "Network Token Input Indicator 'Y' while Element 2 is a card "
                              "number", field=f"{path} -> {dkey} (table 004)", value="Y", label="Network Token Input Indicator",
                              refs=["S12.38-T004", "EL2"],
                              why=f"Table 004 'Y' says the merchant sent a network token, but Element 2 carries the card number "
                                  f"{pan[:6]}…{pan[-4:]} and the token is only in table 001. ATL105 does not say which field "
                                  "carries the token when the indicator is Y.",
                              fix="Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number.")
            # field formats from Chapter 13
            rs = next((s for s in t.rsteps if s["step"] == n), {})
            tcflds = (rs.get("request") or {}).get(seg_name) or {}
            for k, x in tcflds.items():
                if not isinstance(x, dict) or k in ("Segment Type", "Segment Length", "Segment Length Indicator"):
                    continue
                key, v = td_field(seg, k)
                v = flat(v)
                if key is None or v == "" or is_ph(v) or no in ("145", "153"):
                    continue
                c = element_constraint(ref, x.get("element_no"), k)
                if not c:
                    continue
                ctype, cmax, mode = c
                probs = tdv.char_problems(v, ctype, cmax, mode)
                if probs:
                    t.add(n, "FAIL", "X_FIELD_FORMAT", f"{seg_name}: {k} breaks its Chapter 13 format", field=f"{path} -> {key}",
                          value=v, label=k, refs=[],
                          why=f"Element {x.get('element_no')} {k} is {ctype}, {'fixed' if mode == 'FIXED' else 'max'} length "
                              f"{cmax} (Chapter 13); '{v}' " + "; ".join(probs) + ".",
                          fix=f"A value of type {ctype} with {'exactly' if mode == 'FIXED' else 'at most'} {cmax} characters.")
            # scope and synthesized content
            if no in SEGMENT_SCOPE and t.rec["dimensions"].get("program") == "CREDIT":
                rid, what = SEGMENT_SCOPE[no]
                t.add(n, "REVIEW", "X_SEGMENT_SCOPE", f"{seg_name} sent on a MasterCard Credit card", field=path,
                      value=f"Segment {no}", label=seg_name, refs=[rid] + (["S12.31-MC"] if no == "145" else []),
                      why=f"ATL105 describes Segment {no} as {what}. This test uses a MasterCard Credit card (card type 020), "
                          "which is not a fleet card.",
                      fix="Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it.")
            methods = {str(x.get("method")) for x in tcflds.values() if isinstance(x, dict) and x.get("value") not in (None, "")}
            synth = methods & {"format_synthesis", "spec_tlv_composite"}
            if synth and not t.negative and no != "102":
                t.add(n, "BLOCKED", "X_SYNTHESIZED_CONTENT", f"{seg_name}: content is synthesized, not client data", field=path,
                      value=", ".join(sorted(synth)), label=seg_name, refs=srefs[-1:],
                      why=f"The test case generated the Segment {no} values itself ({', '.join(sorted(synth))}); they are "
                          f"placeholders, so BUYPASS cannot be expected to approve them. Needed: {supply}.",
                      fix=f"Client to supply {supply}; keep the test case out of 'ready' until then.")


def check_vi_tables(t, valid_ids):
    for n, msg in t.msgs.items():
        s111 = msg.get("Variable Information Data Segment")
        if not s111:
            continue
        fname = t.files[n]
        awaiting = []
        for i, (ind, ln, data) in enumerate(vi_sections(s111)):
            path = f"{fname} -> Variable Information Data Segment -> VariableInformationSection[{i}]"
            if is_ph(ln) or is_ph(data):
                awaiting.append((i, ind, data))
                continue
            if ind not in valid_ids:
                t.add(n, "FAIL", "X_VI_TABLE_ID", f"Variable Information Indicator '{ind}' is not an ATL105 table ID",
                      field=path, value=ind or "(empty)", label="Variable Information Indicator", refs=["EL111"],
                      why=f"Element 111 is a fixed 3-character table ID from the Element 111 list / Appendix I; '{ind}' is not one "
                          "of them.", fix="A valid table ID.")
        if awaiting:
            need = [a for a in t.rec.get("awaiting_inputs") or [] if f"step {n}:" in a and "111" in a]
            avs = any(ind == "003" for _, ind, _ in awaiting)
            t.add(n, "BLOCKED", "X_AWAITING_TABLE", "Variable Information table value awaited from the client",
                  field=f"{fname} -> Variable Information Data Segment", label="Variable Information",
                  value=", ".join(f"Table {ind}" for _, ind, _ in awaiting),
                  refs=["S12.10-LEN", "S12.10-FS"] + (["S10.1.3-REQ", "APPI-003-ZIP", "APPI-003-ADDR"] if avs else []),
                  why=("Table(s) " + ", ".join(ind for _, ind, _ in awaiting) + " hold <AWAITING:...> text instead of a value. "
                       f"The Segment 111 Segment Length '{s111.get('SegmentLength')}' counts only the table indicator, so it "
                       "must be recalculated when the value is filled in." + (f" The test case lists: {need[0]}" if need else "")),
                  fix="Fill in the client value, then recalculate Variable Information Length and the Segment 111 length.")


def check_optional_fields(t):
    for n, msg in t.msgs.items():
        s = msg.get("Standard Message Data Segment")
        if not s:
            continue
        fname, role = t.files[n], t.effective_role(n)
        pin = flat(s.get("EncryptedPINBlockData"))
        if pin and not is_ph(pin) and not re.fullmatch(r"[0-9A-Fa-f]{32,36}", pin) and (
                not t.negative or "encryptedpinblockdata" not in t.deliberate()):
            t.add(n, "FAIL", "X_PIN_BLOCK_FORMAT", "Encrypted PIN Block Data is not a DUKPT KSN + PIN block",
                  field=f"{fname} -> Standard Message Data Segment -> EncryptedPINBlockData", value=pin,
                  label="Encrypted PIN Block Data", refs=["EL33-FMT"],
                  why=f"Element 33 carries a 16- to 20-byte Key Serial Number plus a 16-byte PIN block (32-36 characters); "
                      f"'{pin}' has {len(pin)} characters.", fix="A real DUKPT KSN + PIN block, or leave it empty for credit.")
        notes = []
        ldt = flat(s.get("LocalDateandLocalTime"))
        if ldt and role not in ("completion", "completion_resubmitted") and not flat(s.get("ApprovalNumber")):
            notes.append(f"Local Date and Local Time '{ldt}' (Element 49 identifies the date/time of a preauthorized "
                         "transaction; this step is not one)")
        if ldt and t.role(n) in AUTH_ROLES:
            notes.append(f"Local Date and Local Time '{ldt}' on a new Authorization Only")
        if not is_petroleum(t.rec):
            for k, label in (("PumpLaneNumber", "Pump/Lane Number"), ("FuelPurchaseAmount", "Fuel Purchase Amount")):
                if flat(s.get(k)):
                    notes.append(f"{label} '{flat(s.get(k))}' at a {t.rec.get('acceptance_context') or 'general'} "
                                 "(non-petroleum) merchant")
        if notes and not t.negative:
            t.add(n, "REVIEW", "X_OPTIONAL_FIELD_CONDITION", "Conditional fields sent although their condition does not apply",
                  field=f"{fname} -> Standard Message Data Segment", value="; ".join(notes)[:200], label="Segment 100",
                  refs=["S12.1-ROW16"], why="; ".join(notes) + ".",
                  fix="Send conditional fields only when their condition holds.")


def table_list_ref(env, nnn):
    """Ref to the Element 111 list line 'NNN <table name>' (printed 13-83 to 13-85), added to the catalog on first use."""
    rid = f"EL111-{nnn}"
    if rid not in env.refs.catalog:
        for p in sorted(env.spec.raw):
            m = re.search(rf"(?m)^\s*({nnn} [A-Za-z][^\n]*?)\s*$", env.spec.raw[p]) \
                if env.spec.printed(p) in ("13-83", "13-84", "13-85") else None
            if m:
                env.refs.catalog[rid] = ("Element 111 Variable Information Indicator (Chapter 13)", p, m.group(1))
                break
    return [rid] if rid in env.refs.catalog else []


def check_claimed_condition(t, env):
    """Positive test whose objective names a table, segment or field value that its own test data does not send."""
    rec, obj = t.rec, t.rec.get("test_objective") or ""
    if str(rec.get("polarity")).lower() != "positive" or not t.msgs:
        return
    n = min(t.msgs)
    planned = ""
    m_tab = re.search(r"\bsending Table (\d{3})\b\s*(.*)$", obj)
    m_seg = re.search(r"\bwith (?:optional )?segment (\d{3}) (?:sent|present)\b", obj)
    m_val = re.search(r"\bwith (.+?) = (\S+) \(the rule's condition holds\)", obj)
    if m_tab:
        nnn = m_tab.group(1)
        sent = sorted({a for m in t.msgs.values() for a, _, _ in vi_sections(m.get("Variable Information Data Segment"))})
        if nnn in sent:
            return
        what, hint = f"Table {nnn} {m_tab.group(2).split(' -> ')[0]}".strip(), f"Table {nnn}"
        field, value = f"{t.files[n]} -> Variable Information Data Segment", "Segment 111 tables sent: " + ", ".join(sent)
        refs = ["EL111"] + table_list_ref(env, nnn) + (["APPI-048", "APPI-048-LEN", "APPI-048-PP"] if nnn == "048" else [])
        fix = (f"Add Table {nnn} to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 "
               "length - or keep the test out of 'ready' with the missing data named as an open item.")
        if nnn == "048":
            fix += (" Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so "
                    + ("confirm this card-not-present test is such a transaction, or re-scope / withdraw it." if card_not_present(t)
                       else "a card-present test must be re-scoped to such a transaction or withdrawn."))
        i, lit = planned_table(t, nnn)
        if lit is not None:
            planned = (f" The test case even plans the table: tx_legs[{i}].generated_tables lists Table {nnn} with value "
                       f"'{lit}', but it was not written into the test data.")
    elif m_seg:
        no = m_seg.group(1)
        sent = sorted({k for m in t.msgs.values() for k in segs_by_no(m)})
        if no in sent:
            return
        what, hint, refs = f"Segment {no}", f"segment {no}", []
        field, value = f"{t.files[n]} -> {t.roots[n]}", "Segments sent: " + ", ".join(sent)
        fix = f"Send Segment {no} with the data it needs, or re-scope / withdraw the test."
    elif m_val:
        name, val = m_val.group(1), m_val.group(2)
        got = {flat(v) for m in t.msgs.values() for seg in m.values() if isinstance(seg, dict)
               for k, v in seg.items() if norm_key(k) == norm_key(name)}
        if val in got:
            return
        seg_name = next((s for s, fl in (rec.get("request") or {}).items()
                         if any(norm_key(k) == norm_key(name) for k in (fl or {}))), None)
        what, hint = f"{name} = {val}", name
        field = f"{t.files[n]} -> {t.roots[n]}"
        value = (f"{name}: " + (", ".join(sorted(got)) or "not in the test data")
                 + (f"; segments sent: {', '.join(sorted({k for m in t.msgs.values() for k in segs_by_no(m)}))}" if seg_name else ""))
        refs = (["S12.30-102", "S12.30-ORDER", "S12.30-CAN", "S11.1-143"] if seg_name == "Tax by Product Data Segment"
                else [])
        fix = (f"Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, "
               f"the inclusive/exclusive flag (I or E), {name} = {val} and the tax amount; recalculate the segment count and "
               "lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it "
               "(Section 11.1), so confirm the merchant context - or re-scope / withdraw the test."
               if seg_name == "Tax by Product Data Segment" else f"Send {name} = {val} in {seg_name or 'the request'}, or "
                                                                   "re-scope / withdraw the test.")
    else:
        return
    notes = " ".join(str(x) for x in (rec.get("open_items") or []) + (rec.get("awaiting_inputs") or []))
    if hint.lower() in notes.lower():
        t.add(n, "BLOCKED", "X_CONDITION_AWAITED", f"{what} is not in the test data yet - the test case waits for client data",
              field=field, value=value, label=what, refs=refs,
              why=f"The objective says the test sends {what}; the test data does not contain it yet and the test case lists it "
                  f"as client data to supply (\"{esc(notes)[:200]}\").",
              fix=f"Client to supply the {what} data; then add it to the request and recalculate the lengths.")
    else:
        t.add(n, "FAIL", "X_CONDITION_NOT_SENT", f"The test claims {what} but the request does not send it",
              field=field, value=value, label=what, refs=refs,
              why=f"The objective \"{obj}\" says the request carries {what}, but the test data does not contain it and the test "
                  f"case names no missing input (status `{rec.get('status')}`). An approval would only prove that the request "
                  f"without {what} is approved - not that {what} is handled." + planned,
              fix=fix)


def card_not_present(t):
    return str(t.rec.get("acceptance_context") or "").startswith("CARD_NOT_PRESENT")


def planned_table(t, nnn):
    """(leg index, literal value) of Table nnn in the test case's own tx_legs[].generated_tables, or (None, None)."""
    for i, leg in enumerate(t.rec.get("tx_legs") or []):
        gt = ((leg or {}).get("generated_tables") or {}).get(nnn)
        if gt:
            return i, (gt.get("value") or {}).get("literal")
    return None, None


# Segment 112 tables the request initiates (Element 116, p.433): response table -> (request table, label, references, fix)
RESPONSE_INITIATED = {
    "003": ("003", "AVS data (Table 003)", ["EL116-INIT", "S10.1.3-REQ", "S10.1.3-RESP", "S10.1.3-USE", "EL113-003",
                                            "APPI-003-ZIP", "APPI-003-ADDR", "APPK-003"],
            "Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address "
            "(up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS "
            "result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face "
            "transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS."),
    "004": ("004", "Card verification data (Table 004)", ["EL116-INIT", "APPI-004", "APPK-004"],
            "Send Table 004 in Segment 111 with the test card's CVC2 (3 digits for MasterCard), recalculate the Segment 111 "
            "length and assert the CVV result in Segment 112 Table 004 - or drop the CVV expectation."),
}


def check_response_tables(t, env):
    """Positive test expecting a Segment 112 table that only its request can initiate, without sending that request table."""
    if str(t.rec.get("polarity")).lower() != "positive":
        return
    notes = " ".join(str(x) for x in (t.rec.get("open_items") or []) + (t.rec.get("awaiting_inputs") or []))
    for i, leg in enumerate(t.rec.get("tx_legs") or []):
        n = (leg or {}).get("step", 1)
        if n not in t.msgs:
            continue
        sent = sorted({a for a, _, _ in vi_sections(t.msgs[n].get("Variable Information Data Segment"))})
        for rt in leg.get("response_tables") or []:
            req = RESPONSE_INITIATED.get(rt.get("table_id")) if rt.get("segment") == "112" else None
            if not req or req[0] in sent:
                continue
            nnn, label, refs, fix = req
            back = f"Segment 112 Table {rt['table_id']} {rt.get('name')}"
            kw = {"field": f"{t.files[n]} -> Variable Information Data Segment", "label": label,
                  "value": f"Segment 111 tables sent: {', '.join(sent)}; expected back: {back}",
                  "refs": refs + table_list_ref(env, nnn)}
            if re.search(rf"\bTable {nnn}\b|\b(AVS|ZIP)\b" if nnn == "003" else rf"\bTable {nnn}\b|\bCV[VC]2?\b", notes):
                t.add(n, "BLOCKED", "X_CONDITION_AWAITED", f"{label} is not in the test data yet - the test case waits for "
                      "client data", why=f"tx_legs[{i}].response_tables expects {back}; the request does not send {label} "
                                         f"yet and the test case lists it as client data (\"{esc(notes)[:200]}\").",
                      fix=fix, **kw)
            else:
                t.add(n, "FAIL", "X_RESPONSE_NOT_INITIATED", f"The test expects {rt.get('name')} back, but the request "
                      f"does not send {label}",
                      why=f"tx_legs[{i}].response_tables expects {back} in the response (status `{t.rec.get('status')}`). "
                          f"ATL105 says the request initiates this information (Element 116), but the request sends only "
                          f"Segment 111 tables {', '.join(sent)} - no Table {nnn}. The host has nothing to verify and nothing "
                          "to return, so the expected result cannot occur and an approval proves nothing about it.",
                      fix=fix, **kw)


def check_table_scope(t, env):
    """Table 048 (MasterCard 3-D Secure authentication data) sent on a POS transaction that is not e-commerce."""
    for n, msg in t.msgs.items():
        secs = vi_sections(msg.get("Variable Information Data Segment"))
        t048 = next((d for ind, _, d in secs if ind == "048"), None)
        if t048 is None or (t.negative and "variableinformation" in t.deliberate()):
            continue
        entry = next((d[:2] for ind, _, d in secs if ind == "005"), "")
        pc = flat((msg.get("Standard Message Data Segment") or {}).get("PromptCode"))
        what = f"{tcv.TXN_TYPES.get(pc[:1], 'transaction')} (Prompt Code {pc}" + (
            f", Table 005 entry mode {entry} = {tdv.PAN_ENTRY_DESC.get(entry, '?')})" if entry else ")")
        t.add(n, "REVIEW", "X_TABLE_SCOPE", "Table 048 (3-D Secure authentication data) sent on a POS transaction",
              field=f"{t.files[n]} -> Variable Information Data Segment -> Table 048", value=t048, label="Table 048",
              refs=["APPI-048", "APPI-048-LEN", "APPI-048-PP"] + table_list_ref(env, "048"),
              why=(f"Table 048 = '{t048}' (Program Protocol {t048[:1]} = EMV 3-D Secure Version 2.{t048[:1]}). ATL105 applies "
                   "this field to merchants sending e-commerce transactions with 3-D Secure / EMV 3-D Secure authentication. "
                   f"This step is a {what} at a POS terminal, not an e-commerce transaction, so no 3-D Secure "
                   "authentication took place and an approval does not show that BUYPASS processes MasterCard "
                   "authentication data."),
              fix="Confirm with BUYPASS; otherwise send Table 048 only in e-commerce (3-D Secure) tests, or re-scope the "
                  "test.")


NEG_PREMISES = [
    (r"Mail/Phone Reversal sent on .*not supported", "TC", ["S10.1.2-LIST"],
     "ATL105 Section 10.1.2 lists Mail/Phone Reversal among the point-of-sale transactions supported for credit card processing."),
    (r"Encrypted PIN Block Data sent where", "TC", ["S10.14.3-ONLINE", "S10.14.3-OPT"],
     "ATL105 says online PIN is applicable for credit transactions and that a PIN is an optional field for credit - it does "
     "not forbid a PIN block on a credit Authorization Only."),
    (r"Information Byte sent where", "TC", ["S12.1-ROW3", "EL44"],
     "Information Byte (Element 44) is a Required field (R) of Segment 100, so it is sent on every financial request."),
    (r"Service Level sent where", "TC", ["S12.3-ROW3"],
     "Service Level (Element 87) is a Required field (R) of Segment 102 whenever the segment is sent."),
    (r"Payment Tender Type sent where", "TC", ["S12.7-ROW12"],
     "Payment Tender Type (Element 148) is a Required field (R) of Segment 108 whenever the segment is sent."),
    (r"Account Verification sent on .*not supported", "SME", ["S10.1.2-LIST", "APPG-A"],
     "Account Verification is not in the Section 10.1.2 list for credit, but Appendix G defines Transaction Type A; confirm."),
    (r"Cancellation sent where the spec forbids", "SME", ["APPG-S"],
     "Appendix G defines Cancellation (S) for credit Authorization Only transactions; the cited text does not forbid it here."),
]
# 'segment NNN omitted where p.N requires it': Section 11.1 condition of the segment, and pages that do state a condition
SEG_OMIT_ROW = {"101": "S11.1-101", "104": "S11.1-104", "123": "S11.1-123", "145": "S11.1-145", "153": "S11.1-153"}
SOURCE_DOCS = {"SRC-ATL105-PDF-001": "ATL105",
               "SRC-ATL105-TRANSARMOR-PDF-001": "from the ATL105 TransArmor specification, not the ATL105 Message Format "
                                                "Specifications",
               "SRC-FISERV-EMV-GUIDE-PDF-001": "from the Fiserv EMV guide, not ATL105"}
SEG_OMIT_PAGE = {
    ("123", 267): ("S12.19-REQ", "p.267 requires Segment 123 only on transactions involving tokenized data (MasterCard Token, "
                                "DSRP or Visa TAVV data). This test sends a card number without any token data, so leaving "
                                "the segment out may be correct and BUYPASS may approve."),
    ("153", 310): ("S12.38-ENTITLED", "p.310 requires the same Segment 153 data only in the follow-up transactions of a merchant "
                                     "entitled to both First Data Network Tokens and Auth Optimization. This test sends an "
                                     "original transaction without any network token data."),
}


def check_design(t, spec):
    rec = t.rec
    obj = rec.get("test_objective") or ""
    m = re.search(r"^(.*?) \(segment (\d{3})\) omitted where p\.(\d+) requires it", obj)
    if t.negative and m:
        name, seg, page = m.group(1), m.group(2), int(m.group(3))
        cond = SEG_OMIT_PAGE.get((seg, page))
        rrefs = ([cond[0]] if cond else []) + [SEG_OMIT_ROW.get(seg, "S11.1-DS3")]
        printed = spec.printed(page)
        loc = f"PDF p.{page} " + (f"(printed {printed})" if printed and printed != "?" else "(front matter, no printed page number)")
        entry = (t.ctx or {}).get("entries", {}).get(1)
        dim = (rec.get("dimensions") or {}).get("entry_mode") or ""
        if seg == "130" and (entry in ("05", "07") if entry else dim.startswith("EMV")):
            t.add(None, "REVIEW", "X_BASIS_PAGE", "The rule exists, but the cited page does not state it",
                  field="test case test_objective", value=obj[:160], label="test objective", refs=["S11.8-130"],
                  why=f"Segment 130 is required for all EMV financial transactions and step 1 is a chip read ("
                      + (f"Table 005 entry mode {entry}" if entry else f"test case entry mode {dim}")
                      + f"), so the negative test is valid - but {loc} does not say so.",
                  fix="Cite Section 11.8 (Segment 130 required for all EMV financial transactions) as the basis.", cat="CIT",
                  defect="Q-04")
        elif cond:
            t.add(None, "REVIEW", "X_NEG_PREMISE_CONFIRM", "Confirm the premise of this negative test",
                  field="test case test_objective", value=obj[:160], label="test objective", refs=rrefs, why=cond[1],
                  fix="Confirm with BUYPASS, or send a transaction for which the segment is required.", cat="SME",
                  defect="S-05")
        else:
            ptxt = re.sub(r"\s+", " ", spec.raw.get(page, ""))
            said = f"No. {seg}" in ptxt or name.lower() in ptxt.lower()
            basis = next((b for b in rec.get("spec_basis") or [] if isinstance(b, dict) and b.get("page") == page
                          and b.get("quote")), None)
            own = (f" The test case's own citation for p.{page} (spec_basis, "
                   f"{SOURCE_DOCS.get(basis.get('source_id'), 'source ' + str(basis.get('source_id')))}) reads "
                   f"\"{' '.join(basis['quote'].split())}\" - it says nothing about {name}." if basis else "")
            if seg == "108":
                rrefs = ["S11.2.1-108", "S11.2.1-108R", "S11.1-DS3"]
                premise = ("ATL105 makes Segment 108 required only in the Loyalty Card Transaction Request (Section 11.2.1: "
                           "it 'Contains the loyalty card data required for all loyalty card transactions'); the Financial "
                           "Transaction Request layout in Section 11.1 does not list it. This MasterCard Credit transaction "
                           "carries no loyalty card, so leaving Segment 108 out is correct and BUYPASS has no reason to "
                           "decline.")
            else:
                premise = (f"Section 11.1 lists Segment {seg} as conditional (C): it is sent only when the transaction needs "
                           "its data, and nothing in this MasterCard Credit test needs it - so leaving it out is correct and "
                           "BUYPASS has no reason to decline.")
            t.add(None, "FAIL", "X_NEG_PREMISE", "The rule this negative test claims to break is not in ATL105",
                  field="test case test_objective", value=obj[:160], label="test objective", refs=rrefs,
                  why=(f"{loc} "
                       + ("mentions the segment but does not make it required" if said else f"does not mention {name} at all")
                       + f".{own} {premise}"),
                  fix="Remove the test or rewrite it around a rule ATL105 states.", cat="TC", defect="C-02")
    mb = re.match(r"^(.*?) exactly at its documented maximum length \((\d+) bytes?\) is accepted", obj)
    if mb and 1 in t.msgs:
        name, limit, key = mb.group(1), int(mb.group(2)), norm_key(mb.group(1))
        hit = next(((sname, k, flat(v)) for sname, seg in t.msgs[1].items() if isinstance(seg, dict)
                    for k, v in seg.items() if norm_key(k) == key), None)
        code = t.expected(1)[0]
        if "→ declined" in rec["name"] and code and code != "1":
            t.add(None, "REVIEW", "X_NAME_OUTCOME_MISMATCH", "The test case name says 'declined', but the test expects an "
                  "approval", field="test case name", value=rec["name"][-70:], label="test case name",
                  refs=["EL83-0" if code == "0" else "EL83-2"],
                  why=f"The name ends with '→ declined', but the objective says the value 'is accepted' and the expected "
                      f"Response Code is {code} (approved).", fix="Correct the name to '→ approved'.", cat="TCR", defect="C-05")
        if hit and len(hit[2]) != limit:
            t.add(1, "FAIL", "X_BOUNDARY_NOT_AT_LIMIT", f"{name} is not at its documented maximum length",
                  field=f"{t.files[1]} -> {hit[0]} -> {hit[1]}", value=hit[2], label=name, refs=["EL2"] if key == "accountnumber"
                  else [], why=f"The test is meant to send {name} at exactly {limit} characters, but '{hit[2]}' has "
                               f"{len(hit[2])}, so the boundary is not tested.",
                  fix=f"A valid {name} of exactly {limit} characters, or drop the test if no such value can be approved.",
                  cat="TD", defect="D-15")
        if hit and key == "servicelevel" and not re.fullmatch(r"[FSNXHO0-9]", hit[2]):
            t.add(1, "FAIL", "X_BOUNDARY_INVALID_VALUE", f"{name} '{hit[2]}' is not a valid value",
                  field=f"{t.files[1]} -> {hit[0]} -> {hit[1]}", value=hit[2], label=name, refs=["EL87-VAL"],
                  why=f"Element 87 accepts F, S, N, X, H, O (0-9 reserved for private use; other values reserved for future "
                      f"use). '{hit[2]}' is not one of them, so an approval cannot be expected.",
                  fix="A valid Service Level, e.g. F (Full serve).", cat="TD", defect="D-15")
    if t.negative:
        for rx, cat, rrefs, why in NEG_PREMISES:
            if re.search(rx, obj):
                t.add(None, "FAIL" if cat == "TC" else "REVIEW", "X_NEG_PREMISE" if cat == "TC" else "X_NEG_PREMISE_CONFIRM",
                      "The rule this negative test claims to break is not in ATL105" if cat == "TC"
                      else "Confirm the premise of this negative test", field="test case test_objective", value=obj[:160],
                      label="test objective", refs=rrefs, why=why, fix="Remove or rewrite the test around a rule ATL105 states."
                      if cat == "TC" else "Confirm with BUYPASS.", cat="TC" if cat == "TC" else "SME",
                      defect="C-02" if cat == "TC" else "S-05")
    # objective vs transaction sent
    m = re.match(r"^[A-Za-z /-]+?: (Mail/Phone Authorization Only|Mail/Phone Purchase|Authorization Only|Purchase/Capture|"
                 r"Merchandise Return|Balance Inquiry)", obj)
    if m and 1 in t.msgs:
        s = t.msgs[1].get("Standard Message Data Segment") or {}
        tt = flat(s.get("PromptCode"))[:1]
        if tt and tt not in OBJECTIVE_TT[m.group(1)]:
            t.add(None, "REVIEW", "X_OBJECTIVE_MISMATCH", "The test objective names a different transaction than the one sent",
                  field="test case test_objective", value=obj[:120], label="test objective", refs=["EL78"],
                  why=f"The objective says '{m.group(1)}', but step 1 sends Prompt Code Transaction Type '{tt}' "
                      f"({tcv.TXN_TYPES.get(tt, '?')}) and the name says {TXN_LABEL.get(rec['dimensions']['txn_category'])}.",
                  fix=f"Correct the objective to {tcv.TXN_TYPES.get(tt, '?')}.")
    # TOR after an answered request
    for n in sorted(t.legs):
        if t.role(n) in TOR_ROLES and t.of(n):
            o = t.of(n)
            code, tier, acc, none = t.expected(o)
            if not none and (code or acc) and t.role(o) not in TOR_ROLES:
                t.add(n, "REVIEW", "X_TOR_AFTER_RESPONSE", f"Step {n} sends a Time-out Reversal although step {o} is expected "
                      "to be answered", field=f"test case tx_legs[{o - 1}].expect / tx_legs[{n - 1}]",
                      value=f"step {o} expects {code or '/'.join(sorted(acc))}", label="flow",
                      refs=["S2.3-TOR", "S2.3.1-TIMEOUT", "S2.3.1-LATE", "S2.3.1-QUEUE"],
                      why=f"ATL105 creates a TOR only when the original request times out (no response within 30 seconds, or a "
                          f"late response that is ignored). Step {o} has no 'no response' harness directive and expects Response "
                          f"Code {code or '/'.join(sorted(acc))}, so the device would not send a TOR.",
                      fix=f"Make step {o} a fault-injected 'no response' (or a late response the harness ignores).")
                break
    # negative 'different terminal' with a generated terminal
    for s in t.rsteps:
        x = ((s.get("request") or {}).get("Standard Message Data Segment") or {}).get("Terminal Identifier") or {}
        if str(x.get("method") or "").startswith("deliberate"):
            t.add(s["step"], "REVIEW", "X_NEG_RANDOM_TERMINAL", "The 'different terminal' is a generated value not set up at "
                  "BUYPASS", field=f"{t.files.get(s['step'])} -> Standard Message Data Segment -> TerminalIdentifier",
                  value=x.get("value"), label="Terminal Identifier", refs=["EL102", "S2.3.1-DEVICE"],
                  why=f"Step {s['step']} uses the generated Terminal Identifier '{x.get('value')}' (method {x.get('method')}). "
                      "A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an "
                      "unknown terminal is rejected, not the same-device rule.",
                  fix="Use a second real terminal of the same merchant.")
    # device checkpoints from petroleum documents outside petroleum
    if not is_petroleum(rec):
        cps = [c for c in rec.get("device_checkpoints") or [] if re.search(r"\bpump", c, re.I)]
        if cps:
            t.add(None, "REVIEW", "X_CHECKPOINT_CONTEXT", "Device checkpoints about fuel pumping in a non-petroleum test",
                  field="test case device_checkpoints", value=cps[0][:120], label="device checkpoints", refs=[],
                  why=f"{len(cps)} device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, "
                      f"capture after pumping), but this test runs at {rec['merchant']['name']} "
                      f"({rec.get('acceptance_context')}).", fix="Drop checkpoints that do not apply to this merchant.")
    # Table 006 rule cited for an original transaction
    for b in rec.get("spec_basis") or []:
        if b.get("page") == 312 and "subsequent transactions" in (b.get("quote") or "") and t.role(1) not in FOLLOW_ON:
            t.add(None, "REVIEW", "X_BASIS_CONTEXT", "Cited Segment 153 rule is for Table 006 on subsequent transactions",
                  field="test case spec_basis (p.312)", value=(b.get("quote") or "")[:100], label="quote p.312",
                  refs=["S12.38-T006"],
                  why="The quote is the description of Network Token PAN Indicator (Table 006), which applies only to subsequent "
                      "transactions. This test sends an original transaction and does not send Table 006.",
                  fix="Cite the Segment 153 layout (p.310) as the basis.")
            break


def cross_checks(tcs):
    # identical single-step requests (all segments, Sequence Number left out)
    groups = defaultdict(list)
    for t in tcs:
        if len(t.msgs) != 1 or 1 not in t.msgs or t.roots.get(1) == "Totals Request":
            continue
        if any(lg.get("harness") for lg in t.legs.values()):
            continue  # resend / held-offline tests: the harness, not the message, makes the difference
        m = json.loads(json.dumps(t.msgs[1]))
        m.get("Standard Message Data Segment", {}).pop("SequenceNumber", None)
        groups[(t.roots[1], json.dumps(m, sort_keys=True))].append(t)
    same = 0
    for lst in groups.values():
        if len(lst) < 2:
            continue
        first = lst[0]
        f_code, _, f_acc, f_none = first.expected(1)
        for t in lst[1:]:
            code, _, acc, none = t.expected(1)
            same += 1
            out_a = "no response" if f_none else (f_code or "/".join(sorted(f_acc)) or "?")
            out_b = "no response" if none else (code or "/".join(sorted(acc)) or "?")
            approve = lambda c, a: bool(({c} | a) & {"0", "2", "3", "4", "F"})  # noqa: E731
            differs = (f_none != none) or (approve(f_code, f_acc) != approve(code, acc))
            trigger = any(re.search(r"decline|trigger|test card", o, re.I) for o in t.rec.get("open_items") or [])
            if differs and trigger:
                rule, sev, cat, defect = "X_SAME_REQUEST_NEEDS_TRIGGER", "BLOCKED", "INPUT", "I-07"
            elif differs:
                rule, sev, cat, defect = "X_SAME_REQUEST_DIFFERENT_OUTCOME", "FAIL", "TC", "C-01"
            elif t.rec.get("tc_type") == "response_check":
                rule, sev, cat, defect = "X_SAME_REQUEST_RESPONSE", "INFO", "INFO", INFO_ID
            else:
                rule, sev, cat, defect = "X_SAME_REQUEST", "REVIEW", "SME", "C-08"
            t.add(1, sev, rule, f"Same request as {first.id} ({first.rec['display_id']})",
                  field=f"{t.files[1]} vs {first.files[1]}", value=f"expected {out_b} vs {out_a}", label="request message",
                  why=(f"Apart from the Sequence Number the message is identical to {first.id} (all segments compared). {first.id} "
                       f"expects {out_a}; this test expects {out_b}."
                       + (" The test case asks for a decline trigger in open_items." if trigger and differs else "")
                       + ("" if differs else " What makes the two tests different is not in the message sent to BUYPASS.")),
                  fix=("Supply the decline trigger so the request differs." if trigger and differs else
                       "Make the request of the decline test different (decline test card, amount or simulator rule)."
                       if differs else "Check the difference on the device / in the response; optionally use another card."),
                  cat=cat, defect=defect)
            t.same_as = first.id
    # Sequence Number uniqueness for new transactions on a terminal
    seqs = defaultdict(list)
    for t in tcs:
        for n, msg in t.msgs.items():
            s = msg.get("Standard Message Data Segment")
            if not s or t.effective_role(n) in FOLLOW_ON:
                continue
            seqs[(flat(s.get("TerminalIdentifier")), flat(s.get("SequenceNumber")))].append((t, n))
    dups = {k: v for k, v in seqs.items() if len(v) > 1}
    for (tid, seq), lst in dups.items():
        for t, n in lst[1:]:
            t.add(n, "FAIL", "X_SEQ_DUPLICATE", f"Sequence Number {seq} is also used by {lst[0][0].id} step {lst[0][1]}",
                  field=f"{t.files[n]} -> Standard Message Data Segment -> SequenceNumber", value=seq, label="Sequence Number",
                  refs=["EL86-UNIQUE"], why=f"Two new transactions on terminal {tid} carry Sequence Number {seq}.",
                  fix="A unique Sequence Number per new transaction.")
    return dict(same_pairs=same, seq_keys=len(seqs), seq_dups=len(dups))


# ---------------------------------------------------------------- classification of the trained validator's findings
def classify(t, f):
    rule, sev = f["rule"], f["severity"]
    if f.get("cat"):
        return f["cat"], f.get("defect") or RULE_DEFECT.get(rule, "S-05")
    n = f.get("step")
    delib = t.deliberate() if t.negative else set()
    label = norm_key(f.get("label") or "")
    rule_el = {"LINK_SEQUENCENUMBER": "sequencenumber", "LINK_TERMINALIDENTIFIER": "terminalidentifier",
               "LINK_ACCOUNTNUMBER": "accountnumber", "LINK_ENTRY_MODE": "variableinformation",
               "TABLE_005": "variableinformation", "SEG130_MISSING": "variableinformation"}.get(rule)
    if rule.startswith("FIELD_") and rule[6:].isdigit():
        rule_el = next((norm_key(name) for _, el, _, name, _ in tdv.SEG100 if el == rule[6:]), label)
    # segment-level rules of data element tests: the element they are about
    if re.fullmatch(r"SEG\d{3}_TYPE", rule):
        rule_el = "segmenttype"
    elif re.fullmatch(r"SEG\d{3}_LENGTH|TOTALS_SEGMENT_LENGTH", rule) and "segmentlength" in delib:
        rule_el = "segmentlength"
    elif rule == "NUMBER_OF_SEGMENTS":
        rule_el = "numberofsegments"
    elif rule in ("FIELD_189", "FIELD_189_FIDELITY"):
        rule_el = "emvchipdatalength"
    elif re.fullmatch(r"FIELD_105_\d+", rule):
        rule_el = norm_key(re.sub(r"\s*\(Totals\)", "", f.get("label") or ""))
    elif rule == "VARINFO_LENGTH" and "variableinformationlength" in delib:
        rule_el = "variableinformationlength"
    elif rule == "FIELD_192" and "emvadditionalinformation" in delib:
        rule_el = "emvadditionalinformation"
    elif rule == "MESSAGE_TYPE_EMV" and "segment130" in delib:
        rule_el = "segment130"
    if rule == "TOTALS_SEGMENT_LENGTH" and delib & {"cardlabel", "cardtypetotalcount", "cardtypetotalamount"}:
        # the validator counts the Card Label at its fixed width; the deliberately longer/shorter label changes the length
        rule_el = sorted(delib & {"cardlabel", "cardtypetotalcount", "cardtypetotalamount"})[0]
    el = rule_el or label
    # AI validator rules that do not fit this run's flows (each checked against ATL105)
    if rule in ("FIELD_78", "RESPONSE_CODE") and t.role(n) == "split_tender" and t.of(n) and t.role(t.of(n)) in AUTH_ROLES:
        tt = flat((t.msgs.get(n) or {}).get("Standard Message Data Segment", {}).get("PromptCode"))[:1]
        if tt in ("3", "5", "B"):
            f["na_reason"] = ("The remaining balance after a partially approved Authorization Only is a new Authorization Only on "
                              "the second card (Transaction Type 3, approval code 2). ATL105 supports partial approval for POS "
                              "Authorization Only (Section 10.1.2.1); the AI rule only knew split tender after a purchase.")
            return "AI_NA", AI_NA_ID
    if rule == "FIELD_12" and t.effective_role(n) == "completion":
        f["na_reason"] = ("This step completes the Authorization Only of step 1 (preauthorized Purchase/Capture). Credit "
                          "completions send Account Number = YYMM (Section 10.1.8.2), which is what the file has; the AI rule "
                          "treated the step as a new purchase.")
        return "AI_NA", AI_NA_ID
    if rule == "TABLE_005" and t.effective_role(n) == "completion" and t.ctx["entries"].get(n) == "02" \
            and t.ctx["entries"].get(t.of(n)) == "90":
        f["na_reason"] = ("A completion of an original read with full track data (entry mode 90) uses PAN Entry Mode 02 "
                          "(Appendix J, p.638); the AI rule treated the step as a new magnetic-stripe purchase.")
        return "AI_NA", AI_NA_ID
    if rule == "EMV_CASHBACK_AMOUNTS" and t.role(n) in AUTH_ROLES:
        f["na_reason"] = ("The chip data (9F02 = total including the cash back, 9F03 = the cash back, 9C = 09) describes the "
                          "cash-back amounts consistently; the AI rule expected 9C = 00 because the step is an Authorization "
                          "Only. The real problem of this test - optional fields filled without their condition - is D-02.")
        return "AI_NA", AI_NA_ID
    if rule in ("SEG111_LENGTH", "VARINFO_LENGTH") and any(is_ph(x) for _, ln, d in vi_sections(
            (t.msgs.get(n) or {}).get("Variable Information Data Segment")) for x in (ln, d)):
        return "INPUT", "I-03"
    if rule == "RUNTIME_PLACEHOLDER" and f["value"].startswith("<AWAITING:"):
        f["rule"] = "X_AWAITING_VALUE"
        f["why"] = (f"The field holds '{f['value']}' instead of a value: the generator found no valid value and waits for "
                    "a client value (method awaiting_client_value).")
        f["fix"] = "Client to supply the value (for Account Number: a MasterCard test card)."
        return "INPUT", "I-09"
    if rule == "FIELD_44" and t.rec.get("polarity") == "Boundary":
        return "TD", "D-15"
    bnd = re.match(r"^(.*?) exactly at its documented", t.rec.get("test_objective") or "") \
        if t.rec.get("polarity") == "Boundary" else None
    bfield = norm_key(bnd.group(1)) if bnd else ""
    if bfield and sev == "FAIL" and rule != "FIELD_5" and (rule.startswith("FIELD_") or rule in ("TABLE_005", "X_RULE_CHECK")) \
            and el and (bfield.startswith(el) or el.startswith(bfield) or bfield in norm_key(f["why"])):
        return "TD", "D-15"
    # negative tests: the deliberately broken rule and its direct consequences
    if t.negative and sev == "FAIL":
        if (el and el in delib) or (rule.startswith("SEG") and rule.endswith("_MISSING") and f"segment{rule[3:6]}" in delib):
            return "INTENDED", INTENDED_ID
        if rule == "FIELD_EMPTY" and label == "approvalnumber" and not t.of(n or 1):
            f["intended_reason"] = "There is no original transaction, so there is no Approval Number to copy."
            return "INTENDED", INTENDED_ID
        if rule in ("X_FIELD_FORMAT", "X_SEGMENT_LENGTH", "X_RULE_CHECK", "X_VI_TABLE_ID", "X_SEG_MAX") and (el in delib or not delib
                                                                                                  or rule == "X_SEG_MAX"):
            return "INTENDED", INTENDED_ID
        if rule.startswith("SEG") and rule.endswith("_LENGTH") and delib and f.get("fix") == f.get("value"):
            f["intended_reason"] = ("The declared Segment Length matches the content; the deliberately over-long field makes the "
                                    "segment longer than its 3-digit Segment Length can express.")
            return "INTENDED", INTENDED_ID
        if rule == "EMV_CSN_WIDTH" and "emvchipdata" in delib:
            f["intended_reason"] = ("The deliberately broken EMV Chip Data has no readable Tag 5F34, so Element 188 cannot be "
                                    "checked against it.")
            return "INTENDED", INTENDED_ID
        if rule.startswith("QUOTE_") or rule.startswith("X_NEG") or rule == "MESSAGE_TYPE" or moto_completion(t, f):
            pass
        else:
            f["unintended"] = True
            if rule == "FIELD_12" and "Follow-on transaction" in f["fix"]:
                return follow_on_expiry(t, f)
            if rule == "FIELD_12" and "as Track 2 data does" in f["why"]:
                return "TD", "D-13"
            if rule == "LINK_ACCOUNTNUMBER" and is_ph(f["value"]):
                need = next((a for a in t.rec.get("awaiting_inputs") or [] if a.startswith(f"step {n}:")), "")
                f["why"] = (f"Step {n} follows up step {t.of(n)} but waits for another card ('{f['value']}'"
                            + (f"; awaiting_inputs: \"{need[:160]}\"" if need else "") + f"), while step {t.of(n)} "
                            f"uses card '{f['fix']}'. A follow-on is matched to its original by Sequence Number, Account "
                            "Number and amount, so with another card it no longer matches the original.")
            return "TD", "D-13" if rule == "FIELD_12" and t.ctx["entries"].get(n) in READ_ENTRY_MODES else "D-09"
    if t.negative and rule == "X_RULE_CHECK":
        return "INTENDED", INTENDED_ID
    if rule in ("X_SEG_MAX", "X_TLV_FORMAT", "X_SEG_LENGTH_FIELD"):
        return "TD", "D-03" if f.get("seg") == "145" else "D-12"
    neg = re.search(r"\((-\d+)\)", f["why"]) if rule == "TOTALS_FLOW_MISMATCH" else None
    if neg and f["value"].lower() == f["fix"].lower():
        f["rule"] = "TOTALS_NEGATIVE_NET"
        f["why"] = (f"Section 9.2: a Merchandise Return increases the count and decreases the dollar amount, so the card "
                    f"bucket nets to count 00001 and amount -${abs(int(neg.group(1))) / 100:,.2f}. Card Type Total Amount "
                    f"(Element 15) is a fixed-length unsigned 8-digit amount and ATL105 does not say how a negative net "
                    f"amount is sent; the file sends 00000000.")
        f["fix"] = "Confirm with BUYPASS how a negative net amount is reported in the Totals Request."
        f["refs"] = ["S9.2-RET", "EL15"]
        return "SME", "S-06"
    if rule == "MESSAGE_TYPE" and f["value"] in NON_FIN_ROOTS:
        f["title"] = f"{f['value']}: not checked field by field"
        f["why"] = (f"The file is a {f['value']}. The validator's ATL105 rules cover Financial / EMV Financial Transaction "
                    "Requests and Totals Requests; this request type was checked only at test case level (status, expected "
                    "response, citations, identical requests).")
        f["fix"] = "Check the request against its Chapter 11 layout by hand."
        return "SCOPE", "SCOPE"
    if moto_completion(t, f):
        of = t.of(n)
        f["rule"] = "X_MOTO_COMPLETION_CODE"
        f["title"] = "Completion of a Mail/Phone Authorization Only: Transaction Type to confirm"
        f["why"] = (f"Step {n} completes the Mail/Phone Authorization Only (Transaction Type B) of step {of} with Prompt Code "
                    f"'{f['value']}' (Transaction Type 6 = Mail/Phone Purchase) and the Approval Number. Appendix G names a "
                    "completion code only for POS (0 = point-of-sale purchase/preauthorized completion) and customer-activated "
                    "(4) purchases; Element 5 says a Purchase Request carrying an Approval Number is preauthorized. ATL105 does "
                    "not say which Transaction Type completes a Mail/Phone Authorization Only.")
        f["fix"] = "Confirm with BUYPASS whether a Mail/Phone Authorization Only is completed with Transaction Type 6 or 0."
        f["refs"] = ["APPG-0", "EL5-PRE"]
        return "SME", "S-05"
    if rule == "FIELD_12" and "Follow-on transaction" in f["fix"]:
        return follow_on_expiry(t, f)
    if rule == "FIELD_5" and t.role(n) in AUTH_ROLES:
        f["why"] = (f"'{f['value']}' is an Approval Number on an original Authorization Only (Transaction Type 3), which asks "
                    "for a new approval. ATL105 uses Element 5 to match a follow-on to its original (reversals) and says that "
                    "in a Purchase Request it makes the purchase preauthorized; it does not say how BUYPASS treats an "
                    "Authorization Only that carries one, so the request contradicts the scenario it is meant to test.")
        f["fix"] = "(empty) - an original Authorization Only carries no Approval Number"
        f["title"] = "Approval Number sent on an original Authorization Only"
    defect = RULE_DEFECT.get(rule)
    if defect:
        return DEFECTS[defect]["cat"], defect
    if sev == "INFO":
        return "INFO", INFO_ID
    if sev == "BLOCKED":
        return "INPUT", "I-02"
    if sev == "REVIEW":
        return "SME", "S-05"
    return "TD", "D-09" if t.negative else "D-11"


def follow_on_expiry(t, f):
    """D-16: rewrite the FIELD_12 follow-on finding in plain words."""
    m = re.search(r"original MMYY (\d{4}) -> (\d{4})", f["fix"])
    if m:
        n, o, yy = f["step"], m.group(1), m.group(2)
        f["why"] = (f"Step {n} is a {t.role(n).replace('_', ' ')} of step {t.of(n)}, a keyed card whose expiry is MMYY "
                    f"'{o}' ({tdv.expiry(o)}). A follow-on transaction sends the stored card data as Account Number = YYMM, "
                    f"i.e. '{yy}'; the file repeats '{o}', which read as YYMM is month '{f['value'][2:]}' - not a valid month.")
    return "TD", "D-16"


def moto_completion(t, f):
    """Prompt Code finding on a Mail/Phone Purchase (6) step that completes a Mail/Phone Authorization Only (B)."""
    if f["rule"] != "FIELD_78" or not str(f["value"]).startswith("6") or "completion step" not in f["why"]:
        return False
    of = t.of(f["step"] or 1)
    return flat(((t.msgs.get(of) or {}).get("Standard Message Data Segment") or {}).get("PromptCode"))[:1] == "B"


def verdict(t):
    effects = {CATS[f["cat"]][1] for f in t.findings if f["cat"]}
    worst = next((v for v in ("FAIL", "BLOCKED", "REVIEW") if v in effects), "PASS")
    if not t.msgs and t.rec.get("tc_layer") == "device" and worst in ("PASS", "REVIEW"):
        return "DEVICE"
    if worst == "PASS" and any(f["cat"] == "SCOPE" for f in t.findings):
        return "NOT CHECKED"
    return worst


def can_run(t):
    v = t.verdict
    if v == "DEVICE":
        return "Device-only test: run it on the terminal (no message to BUYPASS)"
    if v == "FAIL":
        return "No - fix the defects first"
    if v == "BLOCKED":
        return "Only after the client / run-time values are filled in"
    if v == "REVIEW":
        return "Yes, after the points to confirm are answered"
    if v == "NOT CHECKED":
        return "Not checked field by field - review the request against its ATL105 layout first"
    return "Yes"


# ---------------------------------------------------------------- run everything
class Env:
    pass


def run_label(run):
    """'MasterCard Credit run of 16 Nov 2026 (plan v12)' from the run's build_manifest.json and README.md."""
    label = "MasterCard Credit run"
    try:
        built = json.loads((run / "build_manifest.json").read_text(encoding="utf-8")).get("built_at", "")
        label += f" of {datetime.fromisoformat(str(built)[:19]):%d %b %Y}"
    except (OSError, ValueError):
        pass
    readme = run / "README.md"
    m = re.search(r"\(plan (v\d+)\)", readme.read_text(encoding="utf-8")) if readme.exists() else None
    return label + (f" (plan {m.group(1)})" if m else "")


def validate_run(run_dir):
    env = Env()
    env.run = run_dir
    env.label = run_label(run_dir)
    env.spec = tdv.Spec(Path(tcv.DEFAULT_SPEC))
    prior = tdv.Spec(Path(tcv.PRIOR_SPEC)) if Path(tcv.PRIOR_SPEC).exists() else None
    env.states = env.spec.state_codes()
    env.refs = tdv.Refs(env.spec, prior, env.states)
    env.refs.catalog.update(NEW_REFS)
    bad = {r: env.refs.check(r) for r in NEW_REFS if env.refs.check(r) != "verified"}
    if bad:
        sys.exit(f"ATL105 text not found on its page: {bad}")
    ref_json = json.loads(tcv.ELEMENT_REF.read_text(encoding="utf-8"))
    env.ref = {e["element"]: e for e in ref_json["elements"]}
    qv = tcv.Validator(env.spec.sq, {}, ref_json)
    # valid table IDs: Element 111 list (p.423-425) + Appendix I layouts
    env.valid_ids = set()
    for p in sorted(env.spec.raw):
        lab = env.spec.printed(p)
        if lab in ("13-83", "13-84", "13-85"):
            env.valid_ids |= {m.group(1) for m in re.finditer(r"(?m)^\s*(\d{3})\s+\S", env.spec.raw[p])}
        if lab.startswith("Appendix I-"):
            env.valid_ids |= set(re.findall(r"Table ID\s+\w{1,3}\s+Fixed [Vv]alue:?\s*(\d{3})", re.sub(r"\s+", " ", env.spec.raw[p])))
    lines = (run_dir / "test_cases" / "test_cases.jsonl").read_text(encoding="utf-8").splitlines()
    env.tcs = []
    for i, line in enumerate(lines, 1):
        if not line.strip():
            continue
        rec = json.loads(line)
        t = TC(rec, i, run_dir)
        t.same_as, t.tlv153, t.rule_results = None, None, []
        if t.msgs and (rec.get("tx_legs") or t.synth_legs):
            t.tc, t.meta = bem.convert(rec if rec.get("tx_legs") else {**rec, "tx_legs": t.synth_legs})
        else:
            t.tc, t.meta = bem.convert_device_only(rec)
        t.rep, t.ctx = tdv.validate_tc(t.tc, t.meta, run_dir / "test_data", env.ref, env.refs, qv, env.states)
        for f in t.rep.findings:
            if f["rule"] in REPLACED:
                continue
            t.findings.append(dict(step=f["step"], severity=f["severity"], rule=f["rule"], title=f["title"], field=f["field"],
                                   value=str(f["value"]), why=f["why"], fix=str(f["fix"]), refs=list(f["refs"]),
                                   source="Trained AI validator", cat=None, defect=None, label=f.get("label", "")))
        if t.msgs:
            check_rule_checks(t)
            check_fidelity(t)
            check_extra_segments(t, env.ref)
            check_vi_tables(t, env.valid_ids)
            check_optional_fields(t)
            check_claimed_condition(t, env)
            check_response_tables(t, env)
            check_table_scope(t, env)
        elif rec.get("tc_layer") == "host":
            t.add(None, "BLOCKED", "X_NO_TEST_DATA", "No test data was generated for this host test case",
                  field="test case test_data_file", value=rec.get("status"), label="test data",
                  why=f"The test case sends a message to BUYPASS ({rec.get('message_chain')}) but has no test data file "
                      f"(status {rec.get('status')}).", fix="Generate the test data once the missing input is available.")
        check_design(t, env.spec)
        env.tcs.append(t)
    env.cross = cross_checks(env.tcs)
    for t in env.tcs:
        for f in t.findings:
            f["cat"], f["defect"] = classify(t, f)
        # the Segment 111 length finding is covered by the awaiting-input finding of the same step
        t.findings = [f for f in t.findings if not (f["rule"] in ("SEG111_LENGTH", "VARINFO_LENGTH") and f["cat"] == "INPUT")]
        t.verdict = verdict(t)
    env.qv = qv
    return env


# ---------------------------------------------------------------- links
def run_link(out, run_dir):
    rel = os.path.relpath(run_dir, out).replace("\\", "/")
    return "/".join(urlquote(p) for p in rel.split("/"))


class Links:
    def __init__(self, out, env):
        self.base = run_link(out, env.run)
        self.run = env.run

    def td(self, t, n, key=None, nth=1, text=None):
        f = t.files.get(n)
        if not f:
            return "-"
        ln = line_of(self.run / "test_data" / f, key, nth) if key else None
        return f"[{text or f}]({self.base}/test_data/{urlquote(f)}" + (f"#L{ln})" if ln else ")")

    def jsonl(self, t, text=None):
        return f"[{text or 'test_cases.jsonl line ' + str(t.line)}]({self.base}/test_cases/test_cases.jsonl#L{t.line})"

    def file(self, rel, text):
        return f"[{text}]({self.base}/{'/'.join(urlquote(p) for p in rel.split('/'))})"


# ---------------------------------------------------------------- summary
def ref_line(refs, rid):
    status = refs.check(rid)
    sec, page, text = refs.catalog[rid]
    flag = "" if status == "verified" else f" [text check: {status}]"
    return f"{sec}, PDF p.{page} (printed {refs.spec.printed(page)}): \"{text}\"{flag}"


def write_summary(out, env, lk, guides, file_name=SUMMARY, docs=None, guide_dir="", sheet=True):
    """docs: relative folder of the flat per-test-case documents of a package (None = the 'Test cases' index)."""
    tcs, refs = env.tcs, env.refs
    package = getattr(env, "total", None)
    files = sum(len(t.files) for t in tcs)
    v = Counter(t.verdict for t in tcs)
    by_def = defaultdict(list)
    for t in tcs:
        for f in t.findings:
            by_def[f["defect"]].append((t, f))
    host = [t for t in tcs if t.msgs]
    seg_use = Counter(no for t in host for m in t.msgs.values() for no in segs_by_no(m))
    docs_line = (f"Manual validation document for every test case: [{TC_DOCS}/README.md]({urlquote(TC_DOCS)}/README.md). "
                 if docs is None else "ATL105 result of each test case, every segment and field: "
                 + ", ".join(f"[{t.id}]({docs}{t.id}.md)" for t in tcs) + ". ")
    lines = [(f"# ATL105 validation of {len(tcs)} test cases of the run and their {files:,} test data files" if package else
              f"# ATL105 validation of the run - all {len(tcs):,} test cases and {files:,} test data files"), "",
             f"**Run:** {lk.file('', env.run.name)} ({esc(env.label)}), build "
             f"`{tcs[0].rec.get('build_id')}`  ",
             f"**Specification:** {tdv.SPEC_TITLE}, release {env.spec.version} ({env.spec.date}). The attached PDF "
             f"(`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf`) is byte-identical (SHA-256) to the "
             "2026-4 PDF whose text the validator reads, so every page number below can be opened in that PDF.  ",
             "**PDF p.** = page number in the PDF viewer; **printed** = page number in the ATL105 page footer.", "",
             docs_line + f"Detailed step-by-step guides for {len(guides)} test cases: "
             + ", ".join(f"[{g}]({guide_dir}{g}-Manual-Validation.md)" for g in guides) + "."
             + (" Every finding of every test case is in the Excel sheet [" + SHEET + "](" + urlquote(SHEET) + ")." if sheet else ""),
             "",
             "## 1. Result at a glance", "",
             "| Result | Test cases | Meaning |", "|---|---:|---|",
             f"| **FAIL** | {v['FAIL']:,} | At least one test data or test case defect that must be fixed |",
             f"| **BLOCKED** | {v['BLOCKED']:,} | No defect, but a client value or a run-time value is still missing |",
             f"| **REVIEW** | {v['REVIEW']:,} | No defect; a citation must be corrected or a point confirmed with BUYPASS |",
             f"| **PASS** | {v['PASS']:,} | Nothing found |",
             f"| **DEVICE** | {v['DEVICE']:,} | Device-only test: no message to BUYPASS; only its ATL105 citations could be checked |",
             f"| Total | {len(tcs):,} | {len(host):,} send a message to BUYPASS ({files:,} test data files); "
             f"{len(tcs) - len(host):,} are device-only or have no test data |", "",
             "Findings by kind (a test case can have several):", "",
             "| Kind | Findings | Test cases | Effect on the result |", "|---|---:|---:|---|"]
    for c in CAT_ORDER:
        fs = [(t, f) for t in tcs for f in t.findings if f["cat"] == c]
        if fs:
            lines.append(f"| {CATS[c][0]} | {len(fs):,} | {len({t.id for t, _ in fs}):,} | {CATS[c][1] or 'none'} |")
    lines += ["", "Data segments in the test data: " + ", ".join(f"{no} ({seg_use[no]:,} files)" for no in sorted(seg_use)) + ".",
              "", "## 2. How every test case and test data file was checked", "",
              "1. **Trained AI validator** (`scripts/validate-td-against-atl105-spec.py`, the rules built in the earlier "
              "MasterCard, Interlink and Maestro reviews): Data Section 1, all 17 fields of Segment 100, Segment 111 with Tables "
              "005/031/030 and the Credential on File tables, Segment 130 (EMV) with its TLV chip data, Segment 105 (Totals), "
              "links between steps (Sequence Number, card, terminal, entry mode), expected Response Codes, and every ATL105 "
              "quote in the test case (`spec_basis`) against the page it cites.",
              "2. **Extended checks for this run** (`scripts/validate-run-and-build-manual-validation.py`): the other data "
              "segments (101, 102, 104, 108, 123, 145, 153 - Segment Type, Segment Length with each segment's Field Separator "
              "rule, Chapter 13 format of every field, TLV structure), every Variable Information table ID, values awaited "
              "from the client, each test case's own `rule_checks`, test data vs test case value fidelity for all segments, "
              "identical requests across all test cases, Sequence Number uniqueness per terminal, Time-out Reversal flows, "
              "negative-test premises and conditional fields.",
              "3. **Classification**: every finding was sorted into the kinds in section 1. Findings of negative tests that are "
              "the rule the test deliberately breaks are listed as *intended* (correct). Where the trained rule did not fit a "
              "new flow of this run, the value was checked against ATL105 and the finding listed as *AI rule not applicable* "
              "(section 9) with the reason.",
              "4. Every ATL105 text quoted in this report (listed in section 11) was found word for word on the stated page "
              "when this report was generated.", ""]

    def defect_block(did, num):
        d = DEFECTS[did]
        hits = by_def.get(did, [])
        if not hits:
            return []
        tc_ids = {t.id for t, _ in hits}
        nfiles = len({(t.id, f["step"]) for t, f in hits if f["step"]})
        out_ = [f"### {num} {did} - {d['title']}", "",
                f"**{plural(len(tc_ids), 'test case')}, {plural(len(hits), 'finding')}"
                + (f", {plural(nfiles, 'test data file')}" if nfiles else "") + "** - kind: " + CATS[d['cat']][0], ""]
        rules = Counter(f["rule"] for _, f in hits)
        out_.append("Rules: " + ", ".join(f"`{r}` ({c})" for r, c in rules.most_common()) + ".")
        firsts = {}
        for t_, f in hits:
            firsts.setdefault((f["rule"], re.sub(r"\d+", "#", re.sub(r"'[^']*'", "''", f["why"]))), (t_, f))
        firsts = list(firsts.values())[:12]
        rids = tdv.uniq(list(d["refs"]) + [r for _, f in firsts for r in f["refs"]])[:10]
        if rids:
            out_ += ["", "ATL105:", ""] + [f"- {ref_line(refs, r)}" for r in rids if r in refs.catalog]
        ex = firsts + [h for h in hits[:24] if h not in firsts]
        ex = ex[:max(12, len(firsts))]
        out_ += ["", "What is wrong (one example per kind of problem):", ""]
        out_ += [f"- `{f['rule']}` ({t_.id}) - {esc(f['why'])}" for t_, f in firsts]
        out_ += ["", "| Test case | Step | Rule | Where (test data file / field) | Value | Expected / fix |", "|---|---|---|---|---|---|"]
        for t, f in ex:
            key = f["field"].split("-> ")[-1].split(" ")[0] if "->" in f["field"] else None
            where = lk.td(t, f["step"], key) if f["step"] and key and re.fullmatch(r"[A-Za-z]+", key or "") else esc(f["field"])
            out_.append(f"| {lk.jsonl(t, t.id)} ({t.rec['display_id']}) | {f['step'] or '-'} | `{f['rule']}` | {where} | "
                        f"{cell(f['value'])} | {esc(f['fix'])[:200]} |")
        if len(hits) > len(ex):
            out_.append(f"| … | | {len(hits) - len(ex):,} more in the sheet (Findings, filter on {did}) | | | |")
        scen = Counter(t.rec["scenario_display_id"] for t, _ in hits)
        out_ += ["", "Scenarios: " + ", ".join(f"{s} ({c})" for s, c in sorted(scen.items(), key=lambda x: -x[1])[:15])
                 + (" …" if len(scen) > 15 else ""), "", f"**How to check by hand:** {d['check']}", "",
                 f"**Fix:** {d['fix']}", ""]
        return out_

    sections = [("3", "Defects in the test data (must be fixed)", [k for k in DEFECTS if DEFECTS[k]["cat"] == "TD"]),
                ("4", "Defects and issues in the test cases (design, status, expected results)",
                 [k for k in DEFECTS if DEFECTS[k]["cat"] in ("TC", "TCR")]),
                ("5", "Citation issues (the ATL105 text quoted in the test cases)", [k for k in DEFECTS if DEFECTS[k]["cat"] == "CIT"]),
                ("6", "Values awaiting client input or run-time values (BLOCKED, not defects)",
                 [k for k in DEFECTS if DEFECTS[k]["cat"] == "INPUT"]),
                ("7", "Points to confirm with BUYPASS / SME", [k for k in DEFECTS if DEFECTS[k]["cat"] == "SME"])]
    for sno, title, ids in sections:
        lines += [f"## {sno}. {title}", ""]
        idx = 0
        for did in ids:
            blk = defect_block(did, f"{sno}.{idx + 1}")
            if blk:
                idx += 1
                lines += blk
        if not idx:
            lines += ["None found.", ""]
    # intended
    intended = by_def.get(INTENDED_ID, [])
    lines += ["## 8. Negative tests - intended violations (correct)", "",
              f"{plural(len({t.id for t, _ in intended}), 'negative test case')} break their rule as designed "
              f"({plural(len(intended), 'finding')}). They are not defects; check that each test breaks only that rule.", "",
              "| Rule | Findings | Example |", "|---|---:|---|"]
    for r, c in Counter(f["rule"] for _, f in intended).most_common():
        t, f = next((t, f) for t, f in intended if f["rule"] == r)
        lines.append(f"| `{r}` | {c} | {t.id} step {f['step'] or '-'}: {esc(f['title'])} - {esc(f.get('intended_reason') or t.rec.get('test_objective'))[:160]} |")
    na = by_def.get(AI_NA_ID, [])
    lines += ["", "## 9. AI validator rules that did not apply to this run (value checked - correct)", "",
              "| Rule | Findings | Test cases | Why the value is correct |", "|---|---:|---:|---|"]
    for r, c in Counter(f["rule"] for _, f in na).most_common():
        fs = [(t, f) for t, f in na if f["rule"] == r]
        lines.append(f"| `{r}` | {c} | {len({t.id for t, _ in fs})} | {esc(fs[0][1].get('na_reason'))} |")
    lines += ["", f"Replaced by the extended checks for this run: `SAME_REQUEST_AS_OTHER_TC` (compared only Segment 100 and "
              "Tables 005/031, so test cases with extra segments looked identical) and `EXTRA_SEGMENTS` (the trained validator "
              "did not read segments 101-153; section 2 point 2 now checks them).", "",
              f"Run-wide checks" + (f" (all {package:,} test cases of the run were validated so that these checks see every "
                                    "test case)" if package else "")
              + f": {env.cross['same_pairs']:,} single-step test cases send the same request as an earlier test "
              f"case (all segments compared); {env.cross['seq_keys']:,} new transactions checked for a unique Sequence Number per "
              f"terminal, {env.cross['seq_dups']} duplicate(s).", "",
              "## 10. Result per scenario", "",
              "| Scenario | Name | Context | Test cases | FAIL | BLOCKED | REVIEW | PASS | DEVICE | Main defects |",
              "|---|---|---|---:|---:|---:|---:|---:|---:|---|"]
    by_ts = defaultdict(list)
    for t in tcs:
        by_ts[t.rec["scenario_display_id"]].append(t)
    for ts in sorted(by_ts, key=lambda k: int(re.search(r"(\d+)$", k).group(1))):
        lst = by_ts[ts]
        c = Counter(t.verdict for t in lst)
        defs = Counter(f["defect"] for t in lst for f in t.findings if f["cat"] in ("TD", "TC"))
        name = re.sub(r"^BUYPASS ATL105 MasterCard Credit ", "", lst[0].rec["name"])
        name = TXN_LABEL.get(lst[0].rec["dimensions"]["txn_category"], "") + " – " + \
            (lst[0].rec.get("dimension_labels") or {}).get("entry_mode", "")
        lines.append(f"| {ts} | {esc(name)} | {lst[0].rec.get('acceptance_context')} | {len(lst)} | {c['FAIL']} | {c['BLOCKED']} | "
                     f"{c['REVIEW']} | {c['PASS']} | {c['DEVICE']} | {', '.join(f'{d} ({n})' for d, n in defs.most_common(3)) or '-'} |")
    lines += ["", "## 11. ATL105 references used", "", "| Reference | Section | PDF p. | Printed | Text (verified on the page) |",
              "|---|---|---:|---|---|"]
    used = set(refs.used)
    if package:
        used = {r for t in tcs for x in (*t.rep.rows, *t.rep.links, *t.rep.responses, *t.findings) for r in x["refs"]}
        used = {r for r in used | {r for d in by_def if d in DEFECTS for r in DEFECTS[d]["refs"]} if r in refs.catalog}
    for rid in sorted(used):
        sec, page, text = refs.catalog[rid]
        status = refs.check(rid)
        lines.append(f"| {rid} | {esc(sec)} | {page} | {refs.spec.printed(page)} | {esc(text)}"
                     + ("" if status == "verified" else f" [text check: {status}]") + " |")
    lines += ["", f"Regenerate: `{getattr(env, 'command', 'python scripts/validate-run-and-build-manual-validation.py')}`.", ""]
    (out / file_name).write_text("\n".join(lines) + "\n", encoding="utf-8")


# ---------------------------------------------------------------- sheet
def write_sheet(out, env):
    from openpyxl import Workbook
    from openpyxl.styles import Alignment, Font, PatternFill
    from openpyxl.utils import get_column_letter
    wb = Workbook()
    fills = {"FAIL": "F8CBAD", "BLOCKED": "FFE699", "REVIEW": "BDD7EE", "PASS": "C6EFCE", "DEVICE": "E7E6E6"}

    def sheet(ws, head, rows, widths):
        ws.append(head)
        for c in ws[1]:
            c.font = Font(bold=True)
            c.fill = PatternFill("solid", fgColor="D9D9D9")
        for r in rows:
            ws.append([str(x) if isinstance(x, (list, dict)) else x for x in r])
        for i, w in enumerate(widths, 1):
            ws.column_dimensions[get_column_letter(i)].width = w
        ws.freeze_panes = "A2"
        ws.auto_filter.ref = ws.dimensions
        for row in ws.iter_rows(min_row=2):
            for c in row:
                c.alignment = Alignment(vertical="top", wrap_text=len(str(c.value or "")) > 40)

    tcs = env.tcs
    ws = wb.active
    ws.title = "Summary"
    v = Counter(t.verdict for t in tcs)
    rows = [["Run", str(env.run.name)], ["Specification", f"{tdv.SPEC_TITLE} {env.spec.version} ({env.spec.date})"],
            ["Test cases", len(tcs)], ["Test data files", sum(len(t.files) for t in tcs)]]
    rows += [[f"Result {k}", v[k]] for k in ("FAIL", "BLOCKED", "REVIEW", "PASS", "DEVICE")]
    for c in CAT_ORDER:
        fs = [f for t in tcs for f in t.findings if f["cat"] == c]
        rows.append([CATS[c][0], f"{len(fs)} findings in {len({t.id for t in tcs if any(f['cat'] == c for f in t.findings)})} test cases"])
    sheet(ws, ["Item", "Value"], rows, [40, 90])
    ws = wb.create_sheet("Test cases")
    rows = []
    for t in tcs:
        r = t.rec
        defs = Counter(f["defect"] for f in t.findings if f["cat"] in ("TD", "TC", "TCR"))
        rows.append([t.id, r["display_id"], r["scenario_display_id"], r.get("acceptance_context"),
                     TXN_LABEL.get(r["dimensions"]["txn_category"]), (r.get("dimension_labels") or {}).get("entry_mode"),
                     r.get("tc_type"), r.get("polarity"), r.get("status"), r.get("message_chain"), t.verdict, can_run(t),
                     ", ".join(f"{d} x{n}" for d, n in defs.items()),
                     sum(f["cat"] in ("TD", "TC") for f in t.findings), sum(f["cat"] == "INPUT" for f in t.findings),
                     sum(f["cat"] in ("SME", "CIT", "TCR") for f in t.findings), sum(f["cat"] == "INTENDED" for f in t.findings),
                     sum(f["cat"] == "AI_NA" for f in t.findings), "; ".join(t.files.values()), t.line, r["name"]])
    sheet(ws, ["Test case", "Display id", "Scenario", "Context", "Transaction", "Entry mode", "Type", "Polarity", "Status",
               "Message chain", "Result", "Can it run as-is?", "Defects", "Defect findings", "Awaiting input", "To review",
               "Intended (negative)", "AI rule n/a", "Test data files", "jsonl line", "Name"],
          rows, [11, 14, 10, 20, 20, 22, 18, 10, 18, 14, 9, 30, 30, 8, 8, 8, 8, 8, 60, 8, 90])
    for row in ws.iter_rows(min_row=2):
        row[10].fill = PatternFill("solid", fgColor=fills.get(row[10].value, "FFFFFF"))
    ws = wb.create_sheet("Findings")
    rows = []
    for t in tcs:
        for f in t.findings:
            rows.append([t.id, t.rec["display_id"], f["step"] or "", f["defect"], CATS[f["cat"]][0], f["severity"], f["rule"],
                         f["title"], f["field"], f["value"][:300], f["why"][:1000], f["fix"][:400],
                         "; ".join(f"{env.refs.catalog[r][0]} p.{env.refs.catalog[r][1]}" for r in f["refs"] if r in env.refs.catalog),
                         f.get("na_reason") or f.get("intended_reason") or "", f["source"]])
    sheet(ws, ["Test case", "Display id", "Step", "Defect id", "Kind", "Severity", "Rule", "Title", "Where", "Value", "Why",
               "Fix", "ATL105", "Note", "Found by"], rows, [11, 14, 5, 8, 26, 9, 28, 50, 50, 30, 80, 50, 50, 50, 18])
    ws = wb.create_sheet("Defects")
    rows = []
    for did, d in DEFECTS.items():
        hits = [(t, f) for t in tcs for f in t.findings if f["defect"] == did]
        if hits:
            rows.append([did, CATS[d["cat"]][0], d["title"], len({t.id for t, _ in hits}), len(hits),
                         "; ".join(ref_line(env.refs, r) for r in d["refs"] if r in env.refs.catalog), d["check"], d["fix"]])
    for did, label in ((INTENDED_ID, "Intended violations of negative tests"), (AI_NA_ID, "AI rule not applicable")):
        hits = [(t, f) for t in tcs for f in t.findings if f["defect"] == did]
        rows.append([did, label, label, len({t.id for t, _ in hits}), len(hits), "", "", ""])
    sheet(ws, ["Id", "Kind", "Title", "Test cases", "Findings", "ATL105", "How to check by hand", "Fix"], rows,
          [8, 28, 70, 10, 10, 90, 70, 70])
    ws = wb.create_sheet("Scenarios")
    by_ts = defaultdict(list)
    for t in tcs:
        by_ts[t.rec["scenario_display_id"]].append(t)
    rows = []
    for ts in sorted(by_ts, key=lambda k: int(re.search(r"(\d+)$", k).group(1))):
        lst = by_ts[ts]
        c = Counter(t.verdict for t in lst)
        rows.append([ts, TXN_LABEL.get(lst[0].rec["dimensions"]["txn_category"]),
                     (lst[0].rec.get("dimension_labels") or {}).get("entry_mode"), lst[0].rec.get("acceptance_context"),
                     len(lst), c["FAIL"], c["BLOCKED"], c["REVIEW"], c["PASS"], c["DEVICE"]])
    sheet(ws, ["Scenario", "Transaction", "Entry mode", "Context", "Test cases", "FAIL", "BLOCKED", "REVIEW", "PASS", "DEVICE"],
          rows, [10, 26, 30, 22, 10, 8, 8, 8, 8, 8])
    wb.save(out / SHEET)


# ---------------------------------------------------------------- manual validation guides
SEG100_RULES = {  # field no -> (rule text, extract ids)
    1: ("Fixed value: 100", ["S12.1", "EL85"]),
    2: ("Length of all 17 fields + all Field Separators", ["S12.1", "EL84"]),
    3: ("One character: ? download, 0 single-message, 1 multimessage", ["EL44"]),
    4: ("Up to 22 characters: Device Type + ANSI State Code + BUYPASS Merchant Number + Device Number; assigned by BUYPASS",
        ["EL102", "APP-D"]),
    5: ("4 characters: Transaction Type (Appendix G) + Card Type (Appendix E)", ["EL78", "APP-G", "APP-E"]),
    6: ("Up to 24 characters", ["EL2"]),
    7: ("Keyed card: expiry MMYY; follow-on credit message: expiry YYMM after '='", ["EL12", "S10.1.1", "S10.1.8.2"]),
    11: ("Up to 8 digits, two assumed decimals; at most 7 digits for non-American Express cards", ["EL58"]),
    14: ("6 digits 000001-999999, unique per transaction; follow-ons carry the original's number", ["EL86"]),
    15: ("Conditional: required on reversals; not sent on a purchase or a Time-out Reversal", ["EL5"]),
    17: ("One digit: 0 not supported, 1 supported, 5 card balance only (American Express prepaid)", ["EL121"]),
}
EMPTY_FIELD = {8: "encrypted PIN block (DUKPT); this keyed credit transaction has no PIN",
               9: "pump or lane number; not a petroleum transaction", 10: "fuel purchase amount; no fuel",
               12: "tax amount; not sent", 13: "cash amount (cash back); not used",
               16: "local date and time of a preauthorized transaction; not preauthorized"}
NEW_EXTRACTS = {
    "S2.3": ("Section 2.3 Unsuccessful Financial Transactions (time-out reversal)", [
        L(39, "A time-out reversal (TOR) is created for an unsuccessful financial transaction. A TOR never initiates a new "
              "financial transaction request."),
        L(39, "Notes: A TOR is generated when any of the following transactions is unsuccessful:\n• POS Purchase/Capture "
              "transaction\n• CAT Purchase/Capture transaction\n• Mail/Phone Purchase transaction"),
    ]),
    "S2.3.1": ("Section 2.3.1 Time-out by Customer's Host/Controller/POS System", [
        L(40, "A time-out occurs at the customer’s host/controller/POS system when either of the following situations occurs:\n"
              "• The customer’s 30-second response interval expires without a response from BUYPASS.\n• A response from BUYPASS "
              "is received after the customer’s 30-second response interval expires."),
        L(40, "If the customer’s host/controller/POS system receives a late response to the original transaction request from "
              "BUYPASS, the late response is ignored."),
        L(40, "Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR, which is queued in "
              "the customer’s host/controller/POS system until the next financial transaction is initiated for that device. A "
              "TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."),
        L(40, "The Sequence Number, Account Number, and dollar amount—assigned by the customer’s host/controller/POS system—are "
              "the elements used to match a request with its respective response. The Sequence Number of a TOR must be the same "
              "as the Sequence Number of the financial transaction being reversed. Each time any financial transaction request "
              "is forwarded to BUYPASS, the financial transaction should be assigned a new Sequence Number by the customer’s "
              "host/controller/POS system."),
        L(40, "Any response from BUYPASS to a TOR, whether it is an approval or a decline, is considered a valid response and "
              "should clear the TOR from the customer’s host/controller/POS queue."),
        L(40, "BUYPASS declines a TOR request as an unmatched reversal when BUYPASS has not received the original financial "
              "transaction request related to the TOR; or when the original financial transaction has already been reversed."),
    ]),
    "S11.1-DS3": ("Section 11.1 Financial Transactions - Data Section No. 3 (Segment 153 row)", [
        L(168, "All Financial Transaction Requests contain none, one, or more of the following data segments in Field Nos. 4–9."),
        H("Field No.", "Seg. No.", "Segment Name", "Max. Len.", "Entry (R/O/C)", "Description"),
        R(169, "4–9", "153", "Network Token Data Request Segment", "999", "C", "Contains Network Token Data associated with this "
          "transaction.", verify="153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this "
                                 "transaction."),
    ]),
    "S12.38": ("Section 12.38 Network Token Data Request Segment (Segment 153)", [
        L(310, "This segment is used to send Network Token transaction information from Ucom to BUYPASS."),
        L(310, "This section describes Data Segment No. 153, Network Token Data Request Segment."),
        L(310, "The sub-segments will be in the standard TLV format (sub-segment type, sub-segment length, sub-segment value). "
               "There is a Field Separator between Field Nos. 1 and 2. Field Separators are not there between the elements in "
               "Network Token Data Layouts."),
        L(310, "When a merchant is entitled to use both First Data provisioned Network Tokens and Auth Optimization service, the "
               "merchant is required to send the same and exact information from the original transaction request in all the "
               "follow-up transactions (Reversals, Completions, Cancellations, Void of Merchandise Return, Timeout Reversals)."),
        H("Field No.", "Element No.", "Element Name", "Len.", "Entry (R/O/C)", "Description"),
        R(310, "1", "85", "Segment Type", "3", "R", "Indicates the type of data segment being formatted Fixed Value 153"),
        R(310, "2", "84", "Segment Length Indicator", "3", "R", "Indicates the data segment length including the Segment Type "
          "length"),
        R(310, "3", "239", "Network Token Data", "999", "R", "All or some of the Network Token sub-segment data is specified "
          "below. Data is in <tag><len><data> format."),
        H("Table", "Table ID", "Table Length", "Table Data"),
        R(311, "Network Token", "n3 Fixed value: 001", "n3 Variable", "n Max. Len.: 19 Identifies the Network Token Data.",
          verify="Table Length n3 Variable Table Data n Max. Len.: 19 Identifies the Network Token Data."),
        R(311, "Network Token Expiration Date", "n3 Fixed value: 002", "n3 Fixed Value: 004",
          "n Fixed Len.: 4 Identifies the Network Token Expiration Date. Format: YYMM",
          verify="Table ID n3 Fixed value: 002 Table Length n3 Fixed Value: 004 Table Data n Fixed Len.: 4 Identifies the "
                 "Network Token Expiration Date. Format: YYMM"),
        R(311, "Network Token Input Indicator", "n3 Fixed value: 004", "n3 Fixed Value: 001", "an Network Token Input Indicator",
          verify="Table ID n3 Fixed value: 004 Table Length n3 Fixed Value: 001 Table Data an Network Token Input Indicator"),
        L(312, "Identifies that the Token or PAN is sent by the merchant\nValid values are:\nY – Network Token\nN – Clear PAN"),
        L(312, "Network Token PAN Indicator\nField Attributes Description\nTable ID n3 Fixed value: 006"),
        L(312, "Identifies that the original transaction is authorized by either PAN or Token. This data is applicable only to "
               "subsequent transactions (Example: Reversal, Completion, Cancellation)."),
    ]),
    "EL83X": ("Element 83 Response Code (Chapter 13)", [
        L(403, "Number: 83 Name: Response Code"),
        L(403, "Purpose: For financial transactions, it identifies an approval with purchase/capture, an approval without "
               "purchase/capture, or a decline."),
        L(404, "0 Approved—Purchase/Capture"),
        L(404, "1 Approved—Communications Test Declined—All other transactions"),
        L(404, "2 Approved—Authorization only"),
    ]),
    "APP-Q": ("Appendix Q. Valid National Point-of-Service Condition Codes (Table 030)", [
        L(568, "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), "
               "and all Credential on File transactions."),
        L(690, "This appendix lists valid entries for Variable Information (Element No. 113), Table ID 030\nThe National "
               "Point-of-Service Condition Codes data element contains the following elements:\n• Terminal Class (Position Nos. "
               "1–3)\n• Presentation Type (Position Nos. 4–7)\n• Security Condition (Position No. 8)\n• Terminal Type "
               "(Position Nos. 9–10)"),
        L(691, "Position No. 1\nValue Description\n0 Attended\n1 Unattended\n2–7 Reserved for national use\n8–9 Reserved for "
               "private use\nPosition No. 2\nValue Description\n0 Customer-operated\n1 Card acceptor-operated\n2 Administrative\n"
               "3–7 Reserved for national use\n8–9 Reserved for private use\nPosition No. 3\nValue Description\n0 On premise\n"
               "1 Off premise"),
        L(692, "Value Description\n00 Administrative terminal\n01 Point-of-service terminal\n02 ATM\n03 Home terminal\n04 ECR\n"
               "05 Dial terminal\n06 Traveler's check machine\n07 Fuel machine"),
        L(693, "010 04 Attended on premise Card acceptor-operated ECR"),
        L(693, "100 01 Unattended on premise customer-operated point-of-service terminal"),
        L(693, "100 07 Unattended on premise customer-operated fuel machine"),
        L(696, "Position Nos. 4–7 Presentation Type Description\n0000 Customer and card present\n0100 Customer present; card not "
               "present\n1000 Customer not present; card present\n1100 Customer not present; card not present\n2100 Mail and/or "
               "telephone order\n8800 Preauthorized purchase"),
        L(696, "Security Condition\nValue Description\n0 No security concern\n1 Suspected fraud\n2 Identification verified\n"
               "3 Electronic commerce transaction with digital signature"),
    ]),
}
GUIDE_ORDER = ["S2.3", "S2.3.1", "S10.1.1", "S10.1.2", "S10.1.2.1", "S10.1.8.2", "S11.1", "S11.1-DS3", "S12.1", "S12.10",
               "S12.38", "EL2", "EL5", "EL12", "EL44", "EL55", "EL58", "EL63", "EL78", "EL83X", "EL84", "EL85", "EL86", "EL102",
               "EL111", "EL112", "EL113", "EL121", "APP-C", "APP-D", "APP-E", "APP-G", "APP-I-005", "APP-I-031", "APP-J", "APP-Q"]
REF_X = (("S2.3.1", "S2.3.1"), ("S2.3-", "S2.3"), ("S10.1.1", "S10.1.1"), ("S10.1.2-LIST", "S10.1.2"), ("S10.1.2.1", "S10.1.2.1"),
         ("S10.1.8.2", "S10.1.8.2"), ("S11.1-153", "S11.1-DS3"), ("S11.1-DS3", "S11.1-DS3"), ("S11.1", "S11.1"),
         ("S12.10", "S12.10"), ("S12.1-", "S12.1"), ("S12.38", "S12.38"), ("APPC", "APP-C"), ("APPD", "APP-D"),
         ("APPE", "APP-E"), ("APPG", "APP-G"), ("APPI-005", "APP-I-005"), ("APPI-031", "APP-I-031"), ("APPJ", "APP-J"),
         ("APPI-030", "APP-Q"), ("APPQ", "APP-Q"), ("EL83", "EL83X"))


def xid_of(rid):
    for prefix, x in REF_X:
        if rid.startswith(prefix):
            return x
    m = re.match(r"EL(\d+)", rid)
    return f"EL{m.group(1)}" if m else None


class Guide:
    def __init__(self, env, t, lk, extracts):
        self.env, self.t, self.lk, self.ex = env, t, lk, extracts
        self.spec, self.refs = env.spec, env.refs
        self.used, self.out = [], []
        self.rec = t.rec

    def x(self, *ids):
        ids = [i for i in ids if i]
        for i in ids:
            if i not in self.ex:
                raise SystemExit(f"{self.t.id}: no ATL105 extract '{i}'")
            if i not in self.used:
                self.used.append(i)
        return ", ".join(f"[{i}](#{mvg.anchor(i)})" for i in ids)

    def xr(self, rids):
        return self.x(*tdv.uniq(xid_of(r) for r in rids if xid_of(r) in self.ex))

    def pg(self, page):
        return f"PDF p.{page} (printed {self.spec.printed(page)})"

    def quote(self, rid):
        sec, page, text = self.refs.catalog[rid]
        self.refs.check(rid)
        return f"{sec}, {self.pg(page)}: \"{text}\""

    def ai_row(self, n, seg, fno):
        r = next((r for r in self.t.rep.rows if r["step"] == n and str(r["seg"]) == seg and str(r["fno"]) == str(fno)), None)
        return r["result"] if r else "-"

    def tc_field(self, n, seg, name):
        s = next((s for s in self.t.rsteps if s["step"] == n), {})
        return ((s.get("request") or {}).get(seg) or {}).get(name) or {}

    def build(self):
        for part in (self.header, self.what, self.path, self.generation, self.fields, self.calculations, self.links,
                     self.responses, self.citations, self.issues, self.across, self.atl105, self.signoff):
            part()
        return "\n".join(self.out) + "\n"

    # ---- sections
    def header(self):
        t, rec, lk = self.t, self.rec, self.lk
        files = ", ".join(lk.td(t, n) for n in sorted(t.files))
        kinds = Counter(CATS[f["cat"]][0] for f in t.findings)
        self.out += [f"# {t.id} ({rec['display_id']}) - Manual Validation Guide", "", f"**{esc(rec['name'])}**", "",
                     "A step-by-step path to check this test case and its test data by hand against ATL105 and to confirm or "
                     "reject what the AI validation reported. The ATL105 text needed for every check is copied into "
                     "[section 11](#s11) (each line was found on the stated page), so the specification does not have to be "
                     "opened. The same release is the attached `BUYPASS_Platform_ATL105_Message_Format_Specifications_"
                     "2026-4 (1).pdf` (byte-identical to the validator's copy).", "",
                     "| Item | Value |", "|---|---|",
                     f"| AI validation result | **{t.verdict}** - can it run as-is? {can_run(t)} |",
                     f"| Findings | " + (", ".join(f"{v} {k}" for k, v in kinds.items()) or "none") + " (section 9) |",
                     f"| Test case record | {lk.jsonl(t)} (the whole test case is on this one line) |",
                     f"| Test data | {files} |",
                     f"| Scenario | {rec['scenario_display_id']} - {TXN_LABEL.get(rec['dimensions']['txn_category'])} – "
                     f"{(rec.get('dimension_labels') or {}).get('entry_mode')} ({rec.get('acceptance_context')}) |",
                     f"| Type / polarity | `{rec.get('tc_type')}` {rec.get('tc_type_name')} / {rec.get('polarity')} |",
                     f"| Template | `{rec.get('template_id')}` |",
                     f"| Status in the test case | `{rec.get('status')}` |",
                     f"| Specification | {tdv.SPEC_TITLE}, release {self.spec.version} ({self.spec.date}) |", "",
                     "**How to read this guide**", "",
                     "- **PDF p.** is the page number shown in the PDF viewer; **printed** is the page number in the ATL105 footer.",
                     "- Links such as **[EL58]** jump to the ATL105 text copied in [section 11](#s11).",
                     "- A **Field Separator** is the separator character that follows a field in the real (wire) message; it "
                     "counts as one character. The JSON test data does not show it.",
                     "- **AI result** is what the AI validation decided; **Your check** is for you (☑ agree, or note what you found).",
                     "- **Intended** marks the rule a negative test breaks on purpose - that is correct for the test.", ""]

    def what(self):
        t, rec = self.t, self.rec
        rows = [("Objective", rec.get("test_objective"), "test_objective"), ("Validation focus", rec.get("validation_focus"),
                                                                             "validation_focus"),
                ("Merchant", f"{rec['merchant']['name']} (id {rec['merchant']['id']}, {rec['merchant'].get('terminal_type')}), "
                             f"merchant_provenance = {rec.get('merchant_provenance')}", "merchant"),
                ("Card", "MasterCard Credit - Prompt Code card type 020", "dimensions"),
                ("Message chain (Transaction Types)", rec.get("message_chain"), "message_chain")]
        rows += [("Open item (client input)", o, "open_items") for o in rec.get("open_items") or []]
        rows += [("Awaiting input", o, "awaiting_inputs") for o in rec.get("awaiting_inputs") or []]
        self.out += ['<a id="s1"></a>', "", "## 1. What this test case does", "", "| Item | Value | Field in the test case record |",
                     "|---|---|---|"]
        self.out += [f"| {a} | {esc(b)} | `{c}` |" for a, b, c in rows]
        self.out += ["", "| Step | Role | Prompt Code in the file | Expected response | Test data file |", "|---|---|---|---|---|"]
        for n in sorted(t.files):
            s = (t.msgs.get(n) or {}).get("Standard Message Data Segment") or {}
            pc = flat(s.get("PromptCode"))
            code, tier, acc, none = t.expected(n)
            exp = "no usable response (fault injected)" if none else (
                f"`{code}` ({tier})" if code else f"any of {', '.join(sorted(acc))} ({tier})" if acc else "not stated")
            self.out.append(f"| {n} | {t.role(n).replace('_', ' ')} | `{pc}` = {tcv.TXN_TYPES.get(pc[:1], '?')}, card type "
                            f"{pc[1:]} | {exp} | {self.lk.td(t, n)} |")
        cps = rec.get("device_checkpoints") or []
        if cps:
            self.out += ["", "Device checkpoints written in the test case (checked on the device, not in the message):", ""]
            for c in cps:
                note = " - *petroleum (PIPS) checkpoint; does not apply to this general merchant*" if (
                    re.search(r"\bpump", c, re.I) and not is_petroleum(rec)) else ""
                self.out.append(f"- {esc(c)}{note}")
        self.out.append("")

    def path(self):
        t = self.t
        multi = len(t.files) > 1
        extra = [no for m in t.msgs.values() for no in segs_by_no(m) if no not in TRAINED_SEGMENTS]
        tables = sorted({a for m in t.msgs.values() for a, _, _ in vi_sections(m.get("Variable Information Data Segment") or {})})
        items = [f"**Open the test case.** Open {self.lk.jsonl(t)}. The record is one long line: copy it into a new file, set "
                 "the language to JSON and format it (Shift+Alt+F). Confirm the id, display id, status and steps in "
                 "[section 1](#s1).",
                 f"**Open the test data file{'s' if multi else ''}** next to this guide: "
                 + ", ".join(self.lk.td(t, n) for n in sorted(t.files)) + ".",
                 f"**Check the message structure** ({self.x('S11.1', 'S12.1', 'S12.10')}"
                 + (f", {self.x('S11.1-DS3', 'S12.38')}" if "153" in extra else "") + "): root `Financial Transaction Request`, "
                 "Data Section 1 (`MessageFormatVersionIdentifier`, `NumberofSegments`), Segment 100 with its 17 fields, "
                 f"Segment 111 with Tables {', '.join(tables[:-1]) + ' and ' + tables[-1] if len(tables) > 1 else ''.join(tables)}"
                 + (", Segment 153 (Network Token Data Request)" if "153" in extra else "") + ".",
                 "**See how each value was produced** ([section 3](#s3)): compare every value in the file with the value and "
                 "method recorded in the test case.",
                 "**Check every field** ([section 4](#s4)) and mark the Your check column.",
                 "**Recalculate** the segment lengths, the card check digit and the expiry ([section 5](#s5)).",
                 ("**Check the links between the steps** ([section 6](#s6))." if multi else
                  "**Links between steps** ([section 6](#s6)): not applicable - one step."),
                 "**Check the expected responses** ([section 7](#s7)) and the **ATL105 citations** ([section 8](#s8)).",
                 "**Confirm each finding** ([section 9](#s9)) with its 'How to confirm by hand' steps.",
                 "**Check across test cases** ([section 10](#s10)) and **sign off** ([section 12](#s12))."]
        self.out += ['<a id="s2"></a>', "", "## 2. Manual validation path (do these steps in order)", ""]
        self.out += [f"{i}. {s}" for i, s in enumerate(items, 1)] + [""]

    def generation(self):
        t = self.t
        self.out += ['<a id="s3"></a>', "", "## 3. How the test data was generated", "",
                     "The test data file is the wire-ready copy of the request stored in the test case. For every field the test "
                     "case records the value, the generation `method`, the ATL105 page it relied on (`source_page`) and notes "
                     "(`flags`). Segment Length / Number of Segments are calculated when the file is built. Values that exist "
                     "only after an earlier step has run are written as instruction text in angle brackets.", ""]
        notes = []
        for n in sorted(t.files):
            rs = next((s for s in t.rsteps if s["step"] == n), {})
            msg = t.msgs.get(n) or {}
            pfx = f"steps[{n - 1}]." if t.rec.get("steps") else ""
            self.out += [f"### 3.{n} Step {n} - {self.lk.td(t, n)}", "",
                         f"In {self.lk.jsonl(t)}: `{pfx}request[\"<segment>\"][\"<field>\"]`.", "",
                         "| Segment / field | In the test data file | In the test case | Same? | How it was produced (`method`) | "
                         "Page used | Notes (`flags`) |", "|---|---|---|---|---|---|---|"]
            ds1 = ((rs.get("data_section_1") or t.rec.get("data_section_1") or {}).get("elements") or {})
            for el, key, label in (("55", "MessageFormatVersionIdentifier", "Message Format Version Identifier (55)"),
                                   ("63", "NumberofSegments", "Number of Segments (63)")):
                f = ds1.get(el) or {}
                self.out.append(self.gen_row(f"DS1 / {label}", msg.get(key), f))
            for seg_name, flds in (rs.get("request") or {}).items():
                td_seg = msg.get(seg_name)
                if td_seg is None:
                    continue
                vi = vi_sections(td_seg) if seg_name == "Variable Information Data Segment" else None
                for k, f in flds.items():
                    if not isinstance(f, dict):
                        continue
                    if vi is not None and k.startswith("Variable Information"):
                        mm = re.match(r"Variable Information(?: (Indicator|Length))?(?: \[(\d+)\])?$", k)
                        i = int(mm.group(2) or 1) if mm else 1
                        part = {"Indicator": 0, "Length": 1, None: 2}[mm.group(1) if mm else None]
                        tdval = vi[i - 1][part] if i <= len(vi) else None
                    else:
                        _, tdval = td_field(td_seg, k)
                    self.out.append(self.gen_row(f"{SEG_NUMBER.get(seg_name, '?')} / {k} (El. {f.get('element_no')})",
                                                 tdval, f))
                    notes += [g for g in f.get("flags") or [] if g not in notes]
            self.out.append("")
        if notes:
            self.out += ["Generator notes used above (full text of `flags`):", ""] + [f"- {esc(g)}" for g in notes[:40]] + [""]

    def gen_row(self, label, tdval, f):
        tv, method, page = f.get("value"), f.get("method"), f.get("source_page")
        tds = flat(tdval)
        if is_ph(tds):
            same = "Run-time value (provisional in the test case)"
        elif tv is None:
            same = "Calculated" if str(method or "").startswith("derived") else ("Yes (not sent)" if not tds else "No")
        else:
            same = "Yes" if str(tv) == tds else "No"
        how = mvg.METHOD_TEXT.get(method) or {
            "omitted": "Not used in this transaction - sent empty", "deliberate_violation_missing_carry":
            "Deliberately NOT copied from step 1 (the rule this negative test breaks)", "spec_tlv_composite":
            "Composed from the Segment 153 layout tables with synthesized (placeholder) data", "fixed_value": "Fixed value"}.get(
            method, method or "-")
        codes = ", ".join(tdv.uniq(g.split(":")[0] for g in f.get("flags") or [])) or "-"
        pg = f"p.{page} (printed {self.spec.printed(page)})" if page else "-"
        return f"| {label} | {cell(tdval)} | {cell(tv) if tv is not None else '(none)'} | {same} | {esc(how)} (`{method}`) | {pg} | {codes} |"

    def field_rows(self, n):
        t = self.t
        msg = t.msgs[n]
        s = msg.get("Standard Message Data Segment") or {}
        v = tdv.td_seg100_values(s)
        role, of = t.role(n), t.of(n)
        first = tdv.td_seg100_values((t.msgs.get(of) or {}).get("Standard Message Data Segment") or {}) if of else {}
        delib = t.deliberate_step(n) if t.negative else set()
        rows = []

        def add(no, field, key, value, rule, xs, how, expected, ai):
            rows.append((no, field, key, value, rule, xs, how, expected, ai))

        segs = [k for k, x in msg.items() if isinstance(x, dict)]
        add("DS1-1", "Message Format Version Identifier (55)", "MessageFormatVersionIdentifier", msg.get("MessageFormatVersionIdentifier"),
            "Fixed 6 characters: ATL105", ["S11.1", "EL55"], "Read the value.", "ATL105", self.ai_row(n, "Data Section 1", 1))
        add("DS1-2", "Number of Segments (63)", "NumberofSegments", msg.get("NumberofSegments"),
            "Up to two digits: Segment 100 plus N-1 other segments", ["S11.1", "EL63"],
            f"Count the segment objects: {', '.join(segs)} = {len(segs)}.", f"{len(segs):02d}", self.ai_row(n, "Data Section 1", 2))
        for fno, el, key, name, _ in tdv.SEG100:
            val, ai = v[key], self.ai_row(n, "100", fno)
            intended = " **(intended violation)**" if norm_key(name) in delib else ""
            rule, xs = SEG100_RULES.get(fno, (f"Conditional (C): {EMPTY_FIELD.get(fno, '')}", ["S12.1"]))
            if fno == 1:
                add(f"100-{fno}", f"{name} ({el})", key, val, rule, xs, "Read the value.", "100", ai)
            elif fno == 2:
                phs = [nm for _, _, k, nm, _ in tdv.SEG100 if is_ph(v[k])]
                base = tdv.seg100_length({k: ("" if is_ph(x) else x) for k, x in v.items()})
                exp = (f"{base + 6:03d} (the run-time Approval Number is always 6 characters)" if phs == ["Approval Number"]
                       else f"{base:03d} + the run-time value(s) of {', '.join(phs)}" if phs else f"{base:03d}")
                add(f"100-{fno}", f"{name} ({el})", key, val, rule, xs, f"Add up the fields - section 5.1, step {n}.", exp, ai)
            elif fno == 3:
                add(f"100-{fno}", f"{name} ({el})", key, val, rule, xs, f"`{val}` = {tdv.INFO_BYTE.get(val, 'not in the list')}.",
                    "0 (single-message)", ai)
            elif fno == 4:
                code = val[2:4]
                st = self.env.states.get(code)
                where = f"{st[0]} ({st[1]}), {st[3]}" if st else "not in Appendix D"
                how = (f"{len(val)} characters (at most 22). Device Type `{val[:2]}`, State Code `{code}` = {where} (Appendix D), "
                       f"then `{val[4:]}` = BUYPASS Merchant Number (6-15) + Device Number (3). Only BUYPASS can confirm it.")
                exp = "Valid layout; the real value comes from BUYPASS (placeholder here)"
                if of:
                    same = val == first.get("TerminalIdentifier")
                    how += f" Follow-on of step {of}: step {of} used `{first.get('TerminalIdentifier')}` - " + (
                        "same device." if same else "**a different terminal**.")
                    exp += f"; same as step {of}" if not intended else f"; deliberately different from step {of}"
                add(f"100-{fno}", f"{name} ({el})", key, val, rule, xs, how, exp + intended, ai)
            elif fno == 5:
                tt, ct = val[:1], val[1:]
                add(f"100-{fno}", f"{name} ({el})", "PromptCode.TransactionType + PromptCode.CardType", val, rule, xs,
                    f"JSON splits it into TransactionType `{tt}` and CardType `{ct}`; on the wire `{val}`. Appendix G: `{tt}` = "
                    f"{tcv.TXN_TYPES.get(tt, '?')}; Appendix E: `{ct}` = {'Credit' if ct == '020' else '?'}.",
                    f"{(t.legs.get(n) or {}).get('code')}020", ai)
            elif fno == 6:
                how = (f"{len(val)} digits (at most 24). MasterCard range 51-55 / 2221-2720 (starts {val[:2]}); last digit is a "
                       "Luhn check digit - section 5.3.")
                exp = "MasterCard number, valid check digit"
                if of:
                    how += f" Same card as step {of}: `{first.get('AccountNumber')}`."
                    exp += f"; same as step {of}"
                add(f"100-{fno}", f"{name} ({el})", key, val, rule, xs, how, exp, ai)
            elif fno == 7:
                if role in FOLLOW_ON and of:
                    o = first.get("CardDiscretionaryBlockData", "")
                    add(f"100-{fno}", f"{name} ({el})", key, val, "Follow-on credit message: expiry YYMM only, joined to the "
                        "Account Number by '='", ["S10.1.8.2", "EL12"], f"Step {of} sent MMYY `{o}`; as YYMM `{o[2:] + o[:2]}`.",
                        o[2:] + o[:2], ai)
                else:
                    ok = bool(re.fullmatch(r"\d{4}", val)) and 1 <= int(val[:2]) <= 12
                    add(f"100-{fno}", f"{name} ({el})", key, val, "Keyed card: expiry MMYY", ["EL12", "S10.1.1"],
                        (f"`{val}` = month {val[:2]}, year 20{val[2:]} = {tdv.expiry(val)}; not expired on {tdv.TODAY:%B %Y}."
                         if ok else f"`{val}` is not a 4-digit MMYY expiry with a month 01-12."),
                        "MMYY, valid month, not expired", ai)
            elif fno == 11:
                exp = f"{first.get('NonfuelAmount')} (same as step {of})" if of else "1500 ($15.00, scenario amount)"
                add(f"100-{fno}", f"{name} ({el})", key, val, rule, xs, f"`{val}` = {amount(val)}; digits only, {len(val)} digits.",
                    exp, ai)
            elif fno == 14:
                how = f"`{val}`: 6 digits, not 000000."
                if of:
                    same = val == first.get("SequenceNumber")
                    how += f" A {role.replace('_', ' ')} keeps the original's number: step {of} has `{first.get('SequenceNumber')}`" \
                           + (" - same." if same else " - **different**.")
                    exp = first.get("SequenceNumber") if not intended else f"deliberately different from {first.get('SequenceNumber')}"
                    xs = xs + (["S2.3.1"] if role in TOR_ROLES else ["S10.1.2.1"])
                else:
                    how += " Unique for this terminal (section 10)."
                    exp = "6 digits, unique"
                add(f"100-{fno}", f"{name} ({el})", key, val, rule, xs, how, exp + intended, ai)
            elif fno == 15:
                if is_ph(val):
                    how, exp = (f"The file holds `{val}`: at run time the Approval Number of the step {of} response (6 "
                                "characters).", f"Approval Number of the step {of} response")
                elif role in TOR_ROLES:
                    how, exp = "Not sent on a TOR: the original never got a response, so there is no Approval Number.", "(empty)"
                elif val == "":
                    how, exp = "Not sent on a purchase (it would make the purchase preauthorized).", "(empty)"
                else:
                    how, exp = f"The file has `{val}`.", "(empty) on a purchase; 6 characters on a reversal"
                add(f"100-{fno}", f"{name} ({el})", key, val, rule, xs, how, exp, ai)
            elif fno == 17:
                add(f"100-{fno}", f"{name} ({el})", key, val, rule, xs, f"`{val}` = {tdv.PAI.get(val, 'not valid')}; required on "
                    "all transactions.", "0 or 1 (merchant setting; 1 here)", ai)
            else:
                add(f"100-{fno}", f"{name} ({el})", key, val, rule, xs,
                    "Must be empty; the Field Separator is still sent." if val == "" else f"The file has `{val}`; check why.",
                    "(empty)", ai)
        s111 = msg.get("Variable Information Data Segment") or {}
        vi = vi_sections(s111)
        calc = 3 + 3 + sum(len(a) + len(b) + len(c) for a, b, c in vi) + 3
        add("111-1", "Segment Type (85)", "SegmentType", s111.get("SegmentType"), "Fixed value: 111", ["S12.10", "EL85"],
            "Read the value.", "111", self.ai_row(n, "111", 1))
        add("111-2", "Segment Length (84)", "SegmentLength", s111.get("SegmentLength"), "Length incl. Segment Type and Field "
            "Separators", ["S12.10", "EL84"], f"Section 5.2, step {n}.", f"{calc:03d}", self.ai_row(n, "111", 2))
        for i, (ind, ln, data) in enumerate(vi, 1):
            tx = {"005": "APP-I-005", "031": "APP-I-031", "030": "APP-Q"}.get(ind)
            add(f"111-3.{i}", "Variable Information Indicator (111)", f"VariableInformationSection[{i - 1}].VariableInformationIndicator",
                ind, "3 characters: the table ID", ["EL111"] + ([tx] if tx else []), f"`{ind}` = Table {ind}.", ind,
                self.ai_row(n, "111", f"3 [{i}]"))
            add(f"111-4.{i}", "Variable Information Length (112)", f"VariableInformationSection[{i - 1}].VariableInformationLength",
                ln, "3 digits = length of the table data", ["EL112", "S12.10"], f"`{data}` has {len(data)} characters.",
                f"{len(data):03d}", self.ai_row(n, "111", f"4 [{i}]"))
            if ind == "005":
                how = (f"Positions 1-2 `{data[:2]}` = {tdv.PAN_ENTRY_DESC.get(data[:2], '?')}; position 3 `{data[2:3]}` = "
                       f"{tdv.PIN_CAP_DESC.get(data[2:3], '?')} (Appendix J). Manual / Keyed scenario: 01.")
                if of:
                    how += f" A follow-on keeps the original's PAN Entry Mode (step {of}: `{t.ctx['entries'].get(of)}`)."
                add(f"111-5.{i}", "Variable Information (113) - Table 005", f"VariableInformationSection[{i - 1}].VariableInformation",
                    data, "PAN Entry Mode (1-2) + Terminal PIN Entry Capability (3)", ["EL113", "APP-I-005", "APP-J"], how,
                    "011 (01 keyed + 1 PIN capability of a chip terminal)", self.ai_row(n, "111", f"5 [{i}]"))
            elif ind == "031":
                add(f"111-5.{i}", "Variable Information (113) - Table 031", f"VariableInformationSection[{i - 1}].VariableInformation",
                    data, "Terminal capability, required on every transaction", ["APP-I-031"],
                    f"`{data}` = {tdv.T031_DESC.get(data, '?')}. With Table 005 position 3 = 1 (chip terminal, Appendix J note) "
                    "the two tables describe the same terminal.", "12 (hybrid chip terminal of this merchant)",
                    self.ai_row(n, "111", f"5 [{i}]"))
            elif ind == "030":
                pos = [codes.get(data[k:k + 1], "?") for k, (_, codes) in enumerate(tdv.T030_POS)]
                add(f"111-5.{i}", "Variable Information (113) - Table 030", f"VariableInformationSection[{i - 1}].VariableInformation",
                    data, "POS Condition Code, 10 digits (Appendix Q)", ["APP-Q"],
                    f"Terminal Class `{data[:3]}` = {', '.join(pos[:3])}; Presentation Type `{data[3:7]}` = {', '.join(pos[3:7])}; "
                    f"Security Condition `{data[7:8]}` = {pos[7]}; Terminal Type `{data[8:10]}` = "
                    f"{tdv.T030_TYPE.get(data[8:10], '?')}.", "0100000004 (attended, card acceptor-operated, on premise ECR; "
                    "customer and card present) for an attended store", self.ai_row(n, "111", f"5 [{i}]"))
        s153 = msg.get("Network Token Data Request")
        if s153:
            key, declared, comp, alts = segment_length("153", s153)
            add("153-1", "Segment Type (85)", "SegmentType", s153.get("SegmentType"), "Fixed Value 153", ["S12.38"],
                "Read the value.", "153", "PASS" if s153.get("SegmentType") == "153" else "FAIL")
            add("153-2", "Segment Length Indicator (84)", key, declared, "3 digits; length including the Segment Type",
                ["S12.38", "EL84"], "Section 5.4.", f"{comp:03d} (Field Separator after every field) - see 5.4 for the other "
                "readings", "REVIEW")
            items, _ = parse_tlv3(flat(s153.get("NetworkTokenData")))
            for tag, ln, val in items:
                name = NET_TOKEN_TABLES.get(tag, ("?",))[0]
                how = {"001": f"Tag `001`, length `{ln:03d}`, value `{val}` ({len(val)} digits, at most 19).",
                       "002": f"Tag `002`, length `{ln:03d}` (fixed 004), value `{val}` = YYMM {val[2:]}/{val[:2]}.",
                       "004": f"Tag `004`, length `{ln:03d}` (fixed 001), value `{val}` = "
                              f"{'Network Token' if val == 'Y' else 'Clear PAN' if val == 'N' else '?'}."}.get(tag, f"`{val}`")
                add(f"153-3.{tag}", f"Network Token Data (239) - table {tag} {name}", "NetworkTokenData", f"{tag}{ln:03d}{val}",
                    "<tag n3><len n3><data>", ["S12.38"], how, "Format OK; real token data from the client", "BLOCKED")
        return rows

    def fields(self):
        t = self.t
        self.out += ['<a id="s4"></a>', "", "## 4. Field-by-field check", "",
                     "For each row: find the JSON key in the test data file, read the value, apply the ATL105 rule (linked) and "
                     "compare with the expected value.", ""]
        for n in sorted(t.msgs):
            self.out += [f"### 4.{n} Step {n} - {self.lk.td(t, n)}", "",
                         "| # | Field (Element) | JSON key | Value in the file | ATL105 rule | How to check | Expected | AI result | "
                         "Your check |", "|---|---|---|---|---|---|---|---|---|"]
            for no, field, key, value, rule, xs, how, expected, ai in self.field_rows(n):
                self.out.append(f"| {no} | {field} | `{key}` | {cell(value)} | {esc(rule)} ({self.x(*xs)}) | {esc(how)} | "
                                f"{esc(expected)} | {ai} | ☐ |")
            self.out.append("")

    def calculations(self):
        t, x = self.t, self.x
        self.out += ['<a id="s5"></a>', "", "## 5. Calculations", "", '<a id="s5-1"></a>', "### 5.1 Segment 100 length", "",
                     f"Rule ({x('S12.1', 'EL84')}): count every field of the segment - including Segment Type and Segment Length - "
                     "plus one Field Separator after each of the 17 fields, even when the field is empty.", ""]
        for n in sorted(t.msgs):
            v = tdv.td_seg100_values(t.msgs[n].get("Standard Message Data Segment") or {})
            prov = {}
            for _, _, key, name, _ in tdv.SEG100:
                tcv_ = self.tc_field(n, "Standard Message Data Segment", name).get("value")
                prov[key] = str(tcv_ or "") if is_ph(v[key]) else v[key]
            self.out += [f"**Step {n} - {t.files[n]}**", "", "| No. | Field | Value counted | Characters | Field Separator | "
                         "Running total |", "|---|---|---|---|---|---|"]
            total = 0
            for fno, _, key, name, _ in tdv.SEG100:
                total += len(prov[key]) + 1
                shown = cell(prov[key]) + (f" (test case value; the file holds `{v[key]}`)" if is_ph(v[key]) else "")
                self.out.append(f"| {fno} | {name} | {shown} | {len(prov[key])} | 1 | {total} |")
            ok = v["SegmentLength"].isdigit() and int(v["SegmentLength"]) == total
            self.out += ["", f"Total **{total:03d}**; the file declares `{v['SegmentLength']}`: **{'match' if ok else 'MISMATCH'}**."
                         + (" A real Approval Number is always 6 characters, so the length stays the same after substitution."
                            if is_ph(v["ApprovalNumber"]) else ""), ""]
        self.out += ['<a id="s5-2"></a>', "### 5.2 Segment 111 length", "",
                     f"Rule ({x('S12.10', 'EL84')}): Field Separator after Field 1 and Field 2; none inside or between the tables; "
                     "one after the final table.", ""]
        for n in sorted(t.msgs):
            s111 = t.msgs[n].get("Variable Information Data Segment") or {}
            vi = vi_sections(s111)
            parts = [("Segment Type", s111.get("SegmentType", ""), 1), ("Segment Length", s111.get("SegmentLength", ""), 1)]
            parts += [(f"Table {a}: indicator + length + data", f"{a}{b}{c}", 1 if i == len(vi) else 0) for i, (a, b, c) in enumerate(vi, 1)]
            self.out += [f"**Step {n}**", "", "| Part | Value | Characters | Field Separator | Running total |", "|---|---|---|---|---|"]
            total = 0
            for label, val, fs in parts:
                total += len(val) + fs
                self.out.append(f"| {label} | `{val}` | {len(val)} | {fs} | {total} |")
            ok = str(s111.get("SegmentLength")) == f"{total:03d}"
            self.out += ["", f"Total **{total:03d}**; the file declares `{s111.get('SegmentLength')}`: **{'match' if ok else 'MISMATCH'}**.", ""]
        self.out += ['<a id="s5-3"></a>', "### 5.3 Card number check digit (Luhn) and expiry", "",
                     f"ATL105 refers card numbers to the BUYPASS IIN Reference Manual ({x('EL2')}); MasterCard numbers start with "
                     "51-55 or 2221-2720 and end with a Luhn check digit: from the right, double every second digit (subtract 9 "
                     "above 9) and add everything; the sum must end in 0.", ""]
        pans = tdv.uniq(flat((m.get("Standard Message Data Segment") or {}).get("AccountNumber")) for m in t.msgs.values())
        for pan in pans:
            if not pan.isdigit():
                self.out += [f"**`{pan or '(empty)'}`** - not a card number, so there is no check digit to compute.", ""]
                continue
            digits = [int(c) for c in pan]
            dbl = [(len(pan) - i) % 2 == 0 for i in range(len(pan))]
            used = [(d * 2 - 9 if d * 2 > 9 else d * 2) if f else d for d, f in zip(digits, dbl)]
            self.out += [f"**`{pan}`**", "", "| Position | " + " | ".join(str(i) for i in range(1, len(pan) + 1)) + " |",
                         "|---|" + "---|" * len(pan), "| Digit | " + " | ".join(map(str, digits)) + " |",
                         "| Doubled? | " + " | ".join("×2" if f else "" for f in dbl) + " |",
                         "| Value added | " + " | ".join(map(str, used)) + " |", "",
                         f"Sum = {sum(used)} → check digit **{'valid' if sum(used) % 10 == 0 else 'NOT valid'}**; starts with "
                         f"`{pan[:2]}` → **{'in' if tcv.is_mastercard_bin(pan) else 'NOT in'}** the MasterCard range.", ""]
        self.out += ["| Step | Card Discretionary Block Data | Format | Meaning | Check |", "|---|---|---|---|---|"]
        for n in sorted(t.msgs):
            cdb = flat((t.msgs[n].get("Standard Message Data Segment") or {}).get("CardDiscretionaryBlockData"))
            if t.role(n) in FOLLOW_ON and t.of(n):
                o = flat((t.msgs[t.of(n)].get("Standard Message Data Segment") or {}).get("CardDiscretionaryBlockData"))
                self.out.append(f"| {n} | `{cdb}` | YYMM ({x('S10.1.8.2')}) | {tdv.expiry(cdb[2:] + cdb[:2])} | step {t.of(n)} MMYY "
                                f"`{o}` → YYMM `{o[2:] + o[:2]}`: **{'same' if o[2:] + o[:2] == cdb else 'DIFFERENT'}** |")
            elif not (re.fullmatch(r"\d{4}", cdb) and 1 <= int(cdb[:2]) <= 12):
                self.out.append(f"| {n} | `{cdb}` | - | - | not a 4-digit MMYY expiry (track data, or broken on purpose by a "
                                "negative test) |")
            else:
                exp = (2000 + int(cdb[2:]), int(cdb[:2])) < (tdv.TODAY.year, tdv.TODAY.month)
                self.out.append(f"| {n} | `{cdb}` | MMYY ({x('EL12')}) | {tdv.expiry(cdb)} | month valid; **{'expired' if exp else 'not expired'}** "
                                f"on {tdv.TODAY:%B %Y} |")
        self.out.append("")
        s153 = next((m.get("Network Token Data Request") for m in t.msgs.values() if m.get("Network Token Data Request")), None)
        if s153:
            key, declared, comp, alts = segment_length("153", s153)
            data = flat(s153.get("NetworkTokenData"))
            self.out += ['<a id="s5-4"></a>', "### 5.4 Segment 153 length - three possible readings", "",
                         f"{x('S12.38')}: \"There is a Field Separator between Field Nos. 1 and 2. Field Separators are not there "
                         "between the elements in Network Token Data Layouts.\" Field 2 \"Indicates the data segment length "
                         "including the Segment Type length\". ATL105 says nothing about a separator after Field 2 or after Field 3.", "",
                         "| Part | Characters |", "|---|---|", f"| Segment Type `153` | 3 |", f"| Segment Length Indicator `{declared}` | 3 |",
                         f"| Network Token Data (`{data}`) | {len(data)} |", "",
                         "| Reading | Length |", "|---|---|",
                         f"| Field Separator after every field (pattern of Segments 100/111; what the generator did) | **{comp:03d}** |"]
            self.out += [f"| {k} | {val:03d} |" for k, val in alts.items()]
            self.out += ["", f"The file declares `{declared}`, which matches only the first reading. **Confirm with BUYPASS** "
                         "(finding in section 9).", ""]

    def links(self):
        t = self.t
        self.out += ['<a id="s6"></a>', "", "## 6. Links between the steps", ""]
        if len(t.msgs) < 2:
            self.out += ["Not applicable - this test case has one step.", ""]
            return
        for n in sorted(t.msgs):
            of = t.of(n)
            if not of:
                continue
            delib = t.deliberate_step(n) if t.negative else set()
            a = tdv.td_seg100_values(t.msgs[of]["Standard Message Data Segment"])
            b = tdv.td_seg100_values(t.msgs[n]["Standard Message Data Segment"])
            tor = t.role(n) in TOR_ROLES
            rows = [("Sequence Number", "SequenceNumber", "same", ["EL86", "S2.3.1" if tor else "S10.1.2.1"]),
                    ("Account Number", "AccountNumber", "same", ["S2.3.1"]),
                    ("Terminal Identifier (same device)", "TerminalIdentifier", "same", ["S2.3.1", "EL102"]),
                    ("Nonfuel Amount", "NonfuelAmount", "same", ["S2.3.1", "S10.1.2.1"]),
                    ("Expiry (MMYY → YYMM)", "CardDiscretionaryBlockData", "yymm", ["S10.1.8.2"]),
                    ("Approval Number", "ApprovalNumber", "runtime" if not tor else "empty", ["EL5"]),
                    ("Prompt Code", "PromptCode", "type", ["APP-G"])]
            self.out += [f"### 6.{n} Step {n} ({t.role(n).replace('_', ' ')}) compared with step {of}", "",
                         f"| Check | Step {of} | Step {n} | Result | Expected | ATL105 | Your check |", "|---|---|---|---|---|---|---|"]
            for label, key, kind, xs in rows:
                va, vb = a[key], b[key]
                if kind == "same":
                    ok = va == vb
                    res = "same" if ok else ("**different - intended**" if norm_key(key) in delib else "**DIFFERENT**")
                    exp = "same as the original"
                elif kind == "yymm":
                    ok = va[2:] + va[:2] == vb
                    res, exp = ("same" if ok else "**DIFFERENT**"), f"`{va[2:] + va[:2]}`"
                elif kind == "runtime":
                    res, exp = ("run-time placeholder" if is_ph(vb) else f"`{vb}`"), f"Approval Number of the step {of} response"
                elif kind == "empty":
                    res, exp = ("empty" if not vb else f"`{vb}`"), "(empty) - a TOR has no Approval Number"
                else:
                    res, exp = f"{tcv.TXN_TYPES.get(vb[:1], '?')}", f"{(t.legs.get(n) or {}).get('code')}020"
                self.out.append(f"| {label} | {cell(va)} | {cell(vb)} | {res} | {esc(exp)} | {self.x(*xs)} | ☐ |")
            ea, eb = t.ctx["entries"].get(of), t.ctx["entries"].get(n)
            self.out.append(f"| PAN Entry Mode (Table 005 pos. 1-2) | `{ea}` | `{eb}` | {'same' if ea == eb else '**DIFFERENT**'} | "
                            f"same as the original | {self.x('APP-J')} | ☐ |")
            defs = (t.legs.get(n) or {}).get("link_defs") or []
            if defs:
                self.out += ["", f"Link rules written in the test case (`tx_legs[{n - 1}].link_defs`):", "",
                             "| Type | Field | Page cited | Rule text in the test case |", "|---|---|---|---|"]
                for d in defs:
                    p = d.get("source_page")
                    self.out.append(f"| `{d.get('type')}` | {d.get('element_name')} | p.{p} (printed {self.spec.printed(p)}) | "
                                    f"{esc(d.get('rule'))} |")
            if t.negative:
                self.out += ["", f"**Clean-up after this negative test:** step {of} is approved and the broken follow-on is "
                             f"expected to be declined, so step {of} stays approved on the host. Send a correct "
                             f"{'Time-out Reversal' if tor else 'Purchase Reversal'} (same terminal, Sequence Number "
                             f"`{a['SequenceNumber']}`) afterwards, or include the purchase in the totals check."]
            self.out.append("")

    def responses(self):
        t = self.t
        self.out += ['<a id="s7"></a>', "", "## 7. Expected responses", "",
                     f"Response Codes are listed in {self.x('EL83X')}. Tier: ASSERT = taken from ATL105; FLAG / INFER = not fully "
                     "grounded, to be confirmed.", "",
                     "| Step | Expected | Tier | Meaning | ATL105 basis | AI result | Your check |", "|---|---|---|---|---|---|---|"]
        for n in sorted(t.files):
            code, tier, acc, none = t.expected(n)
            role = t.role(n)
            meaning = {"0": "Approved—Purchase/Capture", "1": "Declined—All other transactions", "2": "Approved—Authorization only"}
            basis = self.x("EL83X")
            if role in TOR_ROLES:
                basis += ", " + self.x("S2.3.1") + " (any response to a TOR is valid; an unmatched TOR is declined)"
            ai = next((r["result"] for r in t.rep.responses if r["step"] == n), "-")
            shown = "no usable response" if none else (f"`{code}`" if code else ", ".join(f"`{c}`" for c in sorted(acc)) or "not stated")
            self.out.append(f"| {n} | {shown} | {tier or '-'} | {meaning.get(code, '; '.join(meaning.get(c, c) for c in sorted(acc)))} | "
                            f"{basis} | {ai} | ☐ |")
        self.out.append("")

    def citations(self):
        t = self.t
        self.out += ['<a id="s8"></a>', "", "## 8. Citations written in the test case (`spec_basis`)", "",
                     "ATL105 quotes were checked word for word on the cited page by the AI validation; quotes from other "
                     "documents (Petroleum Industry Processing Specification, EMV guide) are listed but not checked here.", "",
                     "| # | Source | Page cited | Quote | AI check | Your check |", "|---|---|---|---|---|---|"]
        aiq = {(q["page"], q["quote"]): q for q in t.rep.quotes}
        seen = set()
        i = 0
        for b in t.rec.get("spec_basis") or []:
            k = (b.get("page"), b.get("quote"))
            if k in seen:
                continue
            seen.add(k)
            i += 1
            src = b.get("source_id") or "SRC-ATL105-PDF-001"
            q = aiq.get(k)
            atl = src == "SRC-ATL105-PDF-001"
            res = (f"{q['result']} - {q['status']}" if q else "-") if atl else "other document - not checked"
            pg = f"p.{b.get('page')} (printed {self.spec.printed(b.get('page'))})" if atl else f"p.{b.get('page')}"
            self.out.append(f"| {i} | {src.replace('SRC-', '').replace('-PDF-001', '')} | {pg} | {esc(b.get('quote'))[:300]} | {res} | ☐ |")
        self.out.append("")

    def howto(self, f):
        t, x, lk, rule, n = self.t, self.x, self.lk, f["rule"], f["step"]
        if rule == "TERMINAL_PLACEHOLDER":
            tid = f["value"]
            st = self.env.states.get(tid[2:4])
            return [f"Open {lk.jsonl(t)}: `merchant.name` = \"{t.rec['merchant']['name']}\", `merchant_provenance` = "
                    f"\"{t.rec.get('merchant_provenance')}\".",
                    f"Read {x('EL102')}: \"The Terminal Identifier must be assigned by and set up at BUYPASS.\"",
                    f"Positions 3-4 of `{tid}` are `{tid[2:4]}` = " + (f"{st[0]} ({st[1]}), {st[3]}" if st else "not listed")
                    + f" ({x('APP-D')}).", "Conclusion: ask the client / BUYPASS for the real Terminal Identifier before execution."]
        if rule == "READY_WITH_PLACEHOLDER":
            return [f"Open {lk.jsonl(t)}: `status` = `{t.rec.get('status')}`.",
                    "The previous finding shows the Terminal Identifier is a placeholder, so BUYPASS cannot accept the message.",
                    "Conclusion: the status should not be 'ready' until the real Terminal Identifier is supplied."]
        if rule == "RUNTIME_PLACEHOLDER":
            key = f["field"].split("-> ")[-1]
            return [f"Open {lk.td(t, n, key)}: `{key}` = `{f['value']}` - instruction text in angle brackets, not a value.",
                    f"Read {x('EL5')}: \"Required on reversals for matching information to the original purchase\"; six "
                    "alphanumeric characters.",
                    f"Conclusion: expected for a follow-on step - the harness fills in the Approval Number of the step "
                    f"{t.of(n)} response before sending. Not a defect."]
        if rule in ("LINK_SEQUENCENUMBER", "LINK_TERMINALIDENTIFIER"):
            key = "SequenceNumber" if rule == "LINK_SEQUENCENUMBER" else "TerminalIdentifier"
            of = t.of(n)
            a = flat(t.msgs[of]["Standard Message Data Segment"].get(key))
            b = flat(t.msgs[n]["Standard Message Data Segment"].get(key))
            xs = (["EL86", "S10.1.2.1"] if key == "SequenceNumber" else ["S2.3.1"])
            return [f"Open {lk.td(t, of, key)} and {lk.td(t, n, key)}: step {of} `{a}`, step {n} `{b}`.",
                    f"Read {x(*xs)}: a {t.role(n).replace('_', ' ')} must carry the original's "
                    + ("Sequence Number." if key == "SequenceNumber" else "device (\"A TOR must be sent from the same device as the "
                                                                          "original financial transaction request\")."),
                    f"Open {lk.jsonl(t)}: `steps[{n - 1}].request[\"Standard Message Data Segment\"][\""
                    + ("Sequence Number" if key == "SequenceNumber" else "Terminal Identifier")
                    + "\"]` has `method` = `deliberate_violation_missing_carry` and the flag DELIBERATE_CONSTRAINT_VIOLATION.",
                    "Conclusion: the difference is the rule this negative test breaks on purpose - **intended, correct**."]
        if rule == "X_NEG_RANDOM_TERMINAL":
            tid = f["value"]
            st = self.env.states.get(str(tid)[2:4])
            return [f"Open {lk.td(t, n, 'TerminalIdentifier')}: `{tid}` ({len(str(tid))} characters, State Code `{str(tid)[2:4]}` = "
                    + (f"{st[0]}" if st else "not in Appendix D") + ").",
                    f"In {lk.jsonl(t)} its `method` is `deliberate_violation_missing_carry`: the generator made the value up.",
                    f"Read {x('EL102')}: the Terminal Identifier must be assigned by and set up at BUYPASS.",
                    "Conclusion: BUYPASS will reject the step because the terminal is unknown - the test does not show that the "
                    "*same-device* rule is enforced. Agree if you would use a second real terminal of the same merchant."]
        if rule == "X_TOR_AFTER_RESPONSE":
            of = t.of(n)
            code, tier, acc, _ = t.expected(of)
            return [f"Open {lk.jsonl(t)}, `tx_legs[{of - 1}]`: `expect.response_code` = `{code}` ({tier}), `harness` = "
                    f"`{(t.legs[of].get('harness'))}` (no fault injection).",
                    f"Read {x('S2.3', 'S2.3.1')}: a TOR is created only for an unsuccessful transaction - no response within 30 "
                    "seconds, or a late response that is ignored.",
                    f"Conclusion: as written the device receives the step {of} approval and has no reason to send a TOR. The "
                    "harness must treat the step 1 response as timed out (or the step must be a 'no response' fault test, like "
                    "the fault_recovery test cases)."]
        if rule == "X_SAME_REQUEST":
            other = next(o for o in self.env.tcs if o.id == t.same_as)
            return [f"Open {lk.td(other, 1)} and {lk.td(t, 1)} side by side (VS Code: Select for Compare / Compare with Selected).",
                    "Only `SequenceNumber` differs" + (" - the other test has no Segment 153." if False else "") + ".",
                    f"Conclusion: BUYPASS receives the same message as for {other.id}; what this test checks is outside the request "
                    "(device / response)."]
        if rule in ("X_SEGMENT_LENGTH_UNCLEAR",):
            return [f"Open {lk.td(t, n, 'SegmentLengthIndicator')}: `SegmentLengthIndicator` = `{f['value']}`.",
                    f"Do the calculation in [section 5.4](#s5-4) with the ATL105 text of {x('S12.38')}.",
                    "Conclusion: the declared value is right only if a Field Separator follows every field; ATL105 states the "
                    "separator only between Fields 1 and 2. Confirm with BUYPASS."]
        if rule == "X_SYNTHESIZED_CONTENT":
            return [f"Open {lk.jsonl(t)}: `request[\"Network Token Data Request\"][\"Network Token Data\"]` has `method` = "
                    "`spec_tlv_composite` and the flag SPEC_TLV_COMPOSITE \"... provisional placeholder data\".",
                    f"Read {x('S12.38')}: the segment carries Network Token information sent from Ucom; a merchant entitled to "
                    "First Data provisioned Network Tokens sends it.",
                    "Conclusion: the token `001` and its expiry `002` are made up, and whether MERCHANT 01 is entitled to network "
                    "tokens is not known (other test cases of the same generator mark Segment 153 PENDING_CLIENT_CAPABILITY). An "
                    "approval cannot be expected until the client supplies a real token. Status should not be 'ready'."]
        if rule == "X_TOKEN_INDICATOR":
            pan = flat(t.msgs[n]["Standard Message Data Segment"].get("AccountNumber"))
            return [f"Split `NetworkTokenData` in {lk.td(t, n, 'NetworkTokenData')} into tag/length/data (section 4): table 004 = `Y`.",
                    f"Read {x('S12.38')}: table 004 \"Identifies that the Token or PAN is sent by the merchant\" - Y = Network "
                    "Token, N = Clear PAN.",
                    f"Element 2 `AccountNumber` = `{pan}`: a MasterCard card number with a valid check digit (section 5.3).",
                    "Conclusion: the message says a token was sent but Element 2 carries a card number; ATL105 does not say which "
                    "field carries the token. Confirm with BUYPASS."]
        if rule == "X_OBJECTIVE_MISMATCH":
            return [f"Open {lk.jsonl(t)}: `test_objective` = \"{esc(t.rec.get('test_objective'))}\".",
                    f"`name` says Purchase/Capture and {lk.td(t, 1, 'TransactionType')} sends Transaction Type "
                    f"`{flat(t.msgs[1]['Standard Message Data Segment'].get('PromptCode'))[:1]}` ({x('APP-G')}).",
                    "Conclusion: the objective text is wrong; the test data matches the name."]
        if rule == "X_BASIS_CONTEXT":
            return [f"Open {lk.jsonl(t)}, `spec_basis[0]`: p.312 \"{esc(f['value'])}…\".",
                    f"Read {x('S12.38')}: that sentence describes Network Token PAN Indicator (table 006), applicable only to "
                    "subsequent transactions (reversal, completion, cancellation).",
                    "This test is an original purchase and sends tables 001, 002 and 004 - not 006.",
                    "Conclusion: the cited rule is not the basis of this test; cite the Segment 153 layout (p.310)."]
        if rule == "X_CHECKPOINT_CONTEXT":
            return [f"Read `device_checkpoints` in {lk.jsonl(t)} (listed in section 1): checkpoints p.127 / p.138 talk about "
                    "pumping fuel; their source is the Petroleum Industry Processing Specification (PIPS).",
                    f"`merchant` = {t.rec['merchant']['name']}, `acceptance_context` = {t.rec.get('acceptance_context')}.",
                    "Conclusion: these checkpoints do not apply to this merchant; ignore them when executing."]
        if rule.startswith("QUOTE_"):
            page = re.search(r"p\.(\d+)", f["title"])
            found = re.search(r"\(((?:p\.\d+(?:, )?)+)\)\.?$", f["why"])
            pages = [int(p) for p in re.findall(r"\d+", found.group(1))] if found else []
            where = ""
            if pages:
                xs = mvg.PAGE_X.get(pages[0])
                where = (f" It is on {', '.join(self.pg(p) for p in pages)}"
                         + (f" - see {x(xs)}" if xs and xs in self.ex else "") + ".")
            return [f"Open {lk.jsonl(t)}, `spec_basis`: the entry with page {page.group(1) if page else '?'} quotes "
                    f"\"{esc(f['value'])}\".",
                    "Search that text in the PDF (Ctrl+F) on the cited page." + where
                    + (f" ATL105 has: {self.quote(f['refs'][0])}" if f["refs"] else ""),
                    f"Conclusion: {tdv.QUOTE_STATUS.get(rule, rule)}. The test data is not affected."]
        if rule == "FOLLOW_ON_EQUALS_SEPARATOR":
            s = t.msgs[n]["Standard Message Data Segment"]
            return [f"In {lk.td(t, n, 'AccountNumber')}: `AccountNumber` = `{s.get('AccountNumber')}`, `CardDiscretionaryBlockData` "
                    f"= `{s.get('CardDiscretionaryBlockData')}`.",
                    f"Read {x('S10.1.8.2')}: the two items \"must be separated by an “=” sign, rather than a Field Separator\".",
                    f"Conclusion: on the wire `{s.get('AccountNumber')}={s.get('CardDiscretionaryBlockData')}`; the Segment Length "
                    "does not change. Information only."]
        steps = [f"Where: {esc(f['field'])} - value `{esc(f['value'])}`."]
        if f["refs"]:
            steps.append("ATL105: " + "; ".join(self.quote(r) for r in f["refs"] if r in self.refs.catalog) + ".")
        steps.append(f"Compare: {esc(f['why'])}")
        return steps

    def issues(self):
        t = self.t
        fs = sorted(t.findings, key=lambda f: (CAT_ORDER.index(f["cat"]), f["step"] or 0))
        self.out += ['<a id="s9"></a>', "", "## 9. What is wrong, missing or to confirm - and how to check each finding by hand", ""]
        if not fs:
            self.out += ["No findings.", ""]
            return
        self.out += ["| # | Kind | Finding | Step | Severity | Found by |", "|---|---|---|---|---|---|"]
        for i, f in enumerate(fs, 1):
            self.out.append(f"| [{i}](#i{i}) | {CATS[f['cat']][0]} | {esc(f['title'])} | {f['step'] or 'all'} | {f['severity']} | "
                            f"{f['source']} |")
        self.out.append("")
        for i, f in enumerate(fs, 1):
            atl = "<br>".join(f"{self.xr([r])} {esc(self.quote(r))}" for r in f["refs"] if r in self.refs.catalog) or "-"
            self.out += [f'<a id="i{i}"></a>', "", f"### 9.{i} {esc(f['title'])} - {CATS[f['cat']][0]}", "", "| Item | Value |",
                         "|---|---|", f"| Where | {esc(f['field'])} |", f"| Value | {cell(f['value'])} |", f"| ATL105 | {atl} |",
                         f"| Why | {esc(f.get('na_reason') or f.get('intended_reason') or f['why'])} |",
                         f"| What it should be | {re.sub(r'[.]{2,}$', '.', esc(f['fix']))} |", f"| Defect id (summary / sheet) | {f['defect']} |", "",
                         "**How to confirm by hand**", ""]
            self.out += [f"{k}. {s}" for k, s in enumerate(self.howto(f), 1)]
            self.out += ["", "Your conclusion: ☐ Agree ☐ Disagree - note: ____________", ""]

    def across(self):
        t = self.t
        self.out += ['<a id="s10"></a>', "", "## 10. Checks across test cases", "",
                     "| Check | Result | Detail |", "|---|---|---|"]
        same = [o.id for o in self.env.tcs if getattr(o, "same_as", None) == t.id]
        if t.same_as:
            self.out.append(f"| Same request as another test case | REVIEW | identical to {t.same_as} apart from the Sequence Number |")
        if same:
            self.out.append(f"| Other test cases sending this request | info | {len(same)}: {', '.join(same[:12])}"
                            + (" …" if len(same) > 12 else "") + " |")
        dup = [f for f in t.findings if f["rule"] == "X_SEQ_DUPLICATE"]
        for n in sorted(t.msgs):
            s = t.msgs[n].get("Standard Message Data Segment") or {}
            new = t.effective_role(n) not in FOLLOW_ON
            self.out.append(f"| Sequence Number step {n} `{s.get('SequenceNumber')}` | "
                            f"{'FAIL' if any(d['step'] == n for d in dup) else 'PASS'} | "
                            + ("new transaction: unique on terminal " + flat(s.get('TerminalIdentifier')) + " across all "
                               f"{len(self.env.tcs):,} test cases" if new else f"follow-on: carries / is meant to carry the step "
                                                                            f"{t.of(n)} number") + " |")
        self.out.append("")

    def atl105(self):
        self.out += ['<a id="s11"></a>', "", "## 11. ATL105 text used in this guide", "",
                     f"Copied from the {tdv.SPEC_TITLE}, release {self.spec.version} ({self.spec.date}). Each line below was found on "
                     "the stated PDF page when this guide was generated.", ""]
        for xid_ in GUIDE_ORDER:
            if xid_ in self.used:
                title, items = self.ex[xid_]
                self.out += mvg.render_extract(self.spec, xid_, title, items)

    def signoff(self):
        t = self.t
        cnt = Counter(CATS[f["cat"]][0] for f in t.findings)
        self.out += ['<a id="s12"></a>', "", "## 12. Sign-off", "", "| Area | AI result | Your result | Comment |", "|---|---|---|---|",
                     f"| Field checks (section 4) | {', '.join(f'{v} {k}' for k, v in Counter(r['result'] for r in t.rep.rows).items())} | ☐ Agree ☐ Disagree | |",
                     f"| Links (section 6) | {', '.join(f'{v} {k}' for k, v in Counter(r['result'] for r in t.rep.links).items()) or 'n/a'} | ☐ Agree ☐ Disagree | |",
                     f"| Responses (section 7) | {', '.join(f'{v} {k}' for k, v in Counter(r['result'] for r in t.rep.responses).items()) or 'n/a'} | ☐ Agree ☐ Disagree | |",
                     f"| Citations (section 8) | {', '.join(f'{v} {k}' for k, v in Counter(q['result'] for q in t.rep.quotes).items()) or 'n/a'} | ☐ Agree ☐ Disagree | |",
                     f"| Findings (section 9) | {', '.join(f'{v} {k}' for k, v in cnt.items()) or 'none'} | ☐ Agree ☐ Disagree | |",
                     f"| **Overall** | **{t.verdict}** - {can_run(t)} | ☐ Agree ☐ Disagree | |", "",
                     "Reviewed by: ____________________    Date: ____________", ""]


def write_guides(out, env, lk, ids):
    extracts = dict(mvg.EXTRACTS)
    extracts.update(NEW_EXTRACTS)
    tids = {f["TerminalIdentifier"][2:4] for t in env.tcs if t.id in ids for f in t.ctx["flats"].values()}
    extracts["APP-D"] = mvg.app_d(env.states, sorted(tids))
    bad = mvg.verify(env.spec, extracts)
    if bad:
        sys.exit("ATL105 text not found on the stated page:\n" + "\n".join(bad))
    by_id = {t.id: t for t in env.tcs}
    written = []
    for tid in ids:
        t = by_id[tid]
        g = Guide(env, t, lk, extracts)
        (out / f"{tid}-Manual-Validation.md").write_text(g.build(), encoding="utf-8")
        written.append(t)
    return written


# ---------------------------------------------------------------- one document per test case
def anchor_id(rid):
    return "r-" + re.sub(r"[^a-z0-9]+", "-", rid.lower()).strip("-")


def scenario_label(rec):
    return rec.get("scenario_name") or (f"{TXN_LABEL.get((rec.get('dimensions') or {}).get('txn_category'), '')} – "
                                         f"{(rec.get('dimension_labels') or {}).get('entry_mode', '')}")


NOISE_FLAGS = ("UNTRUSTED_VALID_VALUES", "SEGMENT_SCENARIO_FIELD_INCLUDED", "LIBRARY_KEY_NORMALISED", "QE_EXAMPLE_VALUES_REJECTED")
# Chapter 13 text for fields of the other data segments, by element number
ELEMENT_TEXT = {"62": ["EL62"], "76": ["EL76"], "77": ["EL77"], "81": ["EL81", "EL81-EX"], "87": ["EL87-VAL"],
                "106": ["EL106"], "107": ["EL107"]}


def lead_decimals(v):
    """Elements 81 / 107: the first digit is the number of assumed decimal places (0-3)."""
    if not (isinstance(v, str) and v.isdigit()) or int(v[0]) > 3:
        return None
    d = int(v[0])
    return int(v[1:] or "0") / 10 ** d, d


def product_rule(el, v, seg):
    """Decoded value of a Segment 102 product field, for the manual check."""
    if el in ("81", "107"):
        x = lead_decimals(v)
        what = "quantity" if el == "81" else "unit price"
        return (f"; first digit = number of assumed decimals (0-3): '{v}' = {what} {x[0]:.{x[1]}f}" if x else
                f"; '{v}': the first digit must be the number of assumed decimals (0-3)")
    if el == "76" and isinstance(v, str) and v.isdigit():
        out = f"; two assumed decimals: '{v}' = ${int(v) / 100:,.2f}"
        q, u = lead_decimals(td_field(seg, "Quantity")[1]), lead_decimals(td_field(seg, "Unit Price")[1])
        if q and u:
            calc = q[0] * u[0]
            out += (f". For your check: Quantity x Unit Price = {q[0]:.{q[1]}f} x {u[0]:.{u[1]}f} = ${calc:,.2f}"
                    + (" (matches)" if round(calc * 100) == int(v) else " (does not match)"))
        return out
    return ""


class TCDoc:
    """Manual validation document of one test case, built from the validator's own field checks and classified findings."""

    def __init__(self, env, t, lk, guide_rel, title="Manual validation"):
        self.env, self.t, self.lk, self.refs = env, t, lk, env.refs
        self.rec, self.rep, self.guide_rel, self.title = t.rec, t.rep, guide_rel, title
        self.used, self.out = [], []

    def R(self, rids):
        links = []
        for r in tdv.uniq(list(rids or [])):
            if r in self.refs.catalog:
                if r not in self.used:
                    self.used.append(r)
                links.append(f"[{r}](#{anchor_id(r)})")
        return ", ".join(links) or "-"

    def build(self):
        t, rec, lk = self.t, self.rec, self.lk
        files = ", ".join(lk.td(t, n) for n in sorted(t.files)) or "none - no message to BUYPASS"
        kinds = Counter(CATS[f["cat"]][0] for f in t.findings)
        self.out += [f"# {t.id} ({rec['display_id']}) - {self.title}", "", f"**{esc(rec['name'])}**", "",
                     "| Item | Value |", "|---|---|",
                     f"| AI validation result | **{t.verdict}** - can it run as-is? {can_run(t)} |",
                     "| Findings | " + (", ".join(f"{v} {k}" for k, v in kinds.items()) or "none") + " ([section 5](#s5)) |",
                     f"| Scenario | {rec.get('scenario_display_id')} - {esc(scenario_label(rec))} |",
                     f"| Context | {esc(rec.get('context_label') or rec.get('acceptance_context') or '-')} |",
                     f"| Test type | {esc(rec.get('tc_type_name') or rec.get('tc_type'))} - {rec.get('polarity')} - "
                     f"{rec.get('tc_layer')} |",
                     f"| Status in the test case | `{rec.get('status')}` |",
                     f"| Test case record | {lk.jsonl(t)} (the whole test case is on this line) |",
                     f"| Test data | {files} |"]
        if self.guide_rel:
            self.out.append(f"| Detailed step-by-step guide | [{t.id}-Manual-Validation.md]({self.guide_rel}) |")
        self.out += ["", f"ATL105 = {tdv.SPEC_TITLE}, release {self.env.spec.version} ({self.env.spec.date}), the attached "
                     "`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf`. Every ATL105 text used below is "
                     "copied into [section 8](#s8) with its PDF page and printed page.", ""]
        for part in (self.howto, self.what, self.fields, self.links, self.findings, self.generation, self.quotes,
                     self.atl105, self.signoff):
            part()
        return "\n".join(self.out) + "\n"

    def howto(self):
        t, O = self.t, self.out
        O += ['<a id="s1"></a>', "## 1. How to check this test case by hand", ""]
        if not t.msgs:
            what = "no test data was generated" if self.rec.get("tc_layer") == "host" else "it is checked on the device"
            O += [f"This test case sends no message to BUYPASS ({what}).", "",
                  f"1. Open the test case record ({self.lk.jsonl(t)}) and read the objective, procedure, expected behaviour and "
                  "device checkpoints ([section 2](#s2)).",
                  "2. For each procedure line in section 2, open the ATL105 PDF at the cited page and compare the text (the "
                  "*Text check* column says whether the text was found there); then tick it when checked on the terminal.",
                  "3. Check every ATL105 text the test case quotes ([section 7](#s7)) against [section 8](#s8).",
                  "4. Confirm or reject each finding ([section 5](#s5)) and sign off ([section 9](#s9)).", ""]
            return
        O += [f"1. Open the test case record ({self.lk.jsonl(t)}): `test_objective` says what the test proves and `tx_legs` "
              "lists the steps with the expected Response Code of each ([section 2](#s2)).",
              "2. Open the test data file(s) next to this document: " + ", ".join(self.lk.td(t, n) for n in sorted(t.files)) + ".",
              "3. [Section 3](#s3): for every field compare the value in the file with the ATL105 rule. The rule's ATL105 text "
              "is in [section 8](#s8) (follow the link in the row). Tick *Your check*.",
              "4. [Section 4](#s4): a follow-on step must carry the values of the step it refers to; the expected Response "
              "Codes must fit the transaction.",
              "5. [Section 5](#s5): for each finding open the file at *Where*, compare the value with the ATL105 text and with "
              "*What it should be*, and note Agree / Disagree.",
              "6. [Section 6](#s6): how every value was generated and what is still missing before the test can run.",
              "7. [Section 7](#s7): check the ATL105 text the test case quotes.",
              "8. Sign off ([section 9](#s9)).", ""]

    def what(self):
        t, rec, O = self.t, self.rec, self.out
        O += ['<a id="s2"></a>', "## 2. What the test case does", ""]
        if rec.get("test_objective"):
            O += [f"**Objective:** {esc(rec['test_objective'])}", ""]
        if t.msgs:
            O += ["| Step | Transaction | Prompt Code in the file | Expected Response Code | Test data |", "|---|---|---|---|---|"]
            for n in sorted(t.files):
                msg = t.msgs.get(n) or {}
                if "Totals Data Segment" in msg:
                    pc = flat(msg["Totals Data Segment"].get("PromptCode"))
                    what = "Totals Request"
                else:
                    pc = flat((msg.get("Standard Message Data Segment") or {}).get("PromptCode"))
                    what = tcv.TXN_TYPES.get(pc[:1], "?")
                code, tier, acc, none = t.expected(n)
                exp = ("no response expected" if none else f"`{code}` ({tier})" if code else
                       f"any of {', '.join(sorted(acc))} ({tier})" if acc else "not stated")
                O.append(f"| {n} | {t.role(n).replace('_', ' ') or '-'} | `{pc}` = {what} | {exp} | {self.lk.td(t, n)} |")
            O.append("")
        proc = rec.get("procedure")
        if not t.msgs and isinstance(proc, list) and proc and all(isinstance(p, str) for p in proc):
            m = re.search(r" - ([A-Z][A-Z0-9-]+)\s*$", rec.get("test_objective") or "")
            src = m.group(1) if m else "ATL105"
            O += [f"**Procedure** - {len(proc)} check(s) on the device. Source document named in the objective: "
                  f"**{src}**" + ("" if src == "ATL105" else " (not the attached ATL105 PDF, so the text cannot be checked "
                                                           "against it)") + ".", "",
                  "| # | Verify on the device | Cited page | Text check against ATL105 | Your check |", "|---|---|---|---|---|"]
            for i, p in enumerate(proc, 1):
                m = re.search(r"\(p\.(\d+)\)\s*$", p)
                page = int(m.group(1)) if m else None
                text = re.sub(r"^Verify on the device:\s*", "", p[:m.start()] if m else p).strip()
                if src != "ATL105":
                    chk = f"Not checked - the text is from {src}"
                elif page is None:
                    chk = "No page cited"
                else:
                    found = self.env.spec.pages_with(text)
                    chk = ("Found word-for-word on the cited page" if page in found else
                           f"Found on p.{', p.'.join(map(str, found[:3]))}, not on the cited page" if found else
                           "Not found word-for-word (a table row may be read in a different order) - compare by hand")
                where = (f"PDF p.{page} (printed {self.env.spec.printed(page)})" if page and src == "ATL105" else
                         f"p.{page}" if page else "-")
                O.append(f"| {i} | {esc(text)} | {where} | {chk} | ☐ |")
            O.append("")
            eb = rec.get("expected_behaviour")
            if eb:
                items = eb if isinstance(eb, list) else [x.strip() for x in re.split(r"(?=\b(?:SHOULD|MUST|MAY|SHALL)(?: NOT)?:)",
                                                                                  str(eb)) if x.strip()]
                O += ["**Expected behaviour:**", ""] + [f"- {esc(str(x))}" for x in items] + [""]
        else:
            for key, label in (("procedure", "Procedure"), ("expected_behaviour", "Expected behaviour")):
                v = rec.get(key)
                if v:
                    O += [f"**{label}:** " + esc(" ".join(map(str, v)) if isinstance(v, list) else v), ""]
        cps = rec.get("device_checkpoints") or []
        if cps:
            O += ["Device checkpoints (checked on the device, not in the message):", ""] + [f"- {esc(c)}" for c in cps] + [""]

    def fields(self):
        t, rep, O = self.t, self.rep, self.out
        O += ['<a id="s3"></a>', "## 3. Field-by-field check", ""]
        if not t.msgs:
            O += ["No test data - nothing to check field by field.", ""]
            return
        flagged = defaultdict(list)
        for f in t.findings:
            m = re.match(r".+? -> (.+?)(?: -> ([A-Za-z]+))?$", f.get("field") or "")
            if m and f["step"]:
                flagged[(f["step"], m.group(1), m.group(2))].append(f)
        head = ["| Segment | Field No. | Element | Name | Value in the test data | Value in the test case | ATL105 rule | "
                "ATL105 text | AI result | Your check |", "|---|---|---|---|---|---|---|---|---|---|"]
        for n in sorted(t.files):
            msg = t.msgs.get(n) or {}
            O += [f"### Step {n} - {t.role(n).replace('_', ' ') or 'request'} - {self.lk.td(t, n)}", ""] + head
            for r in (r for r in rep.rows if r["step"] == n):
                tcv_ = r["tc"] if r["tc"] not in (None, "-") else None
                O.append(f"| {r['seg']} | {r['fno']} | {r['el']} | {esc(r['name'])} | {cell(r['td'])} | "
                         f"{cell(tcv_) if tcv_ is not None else '-'} | {esc(r['rule'])} | {self.R(r['refs'])} | {r['result']} | ☐ |")
            rs = next((s for s in t.rsteps if s.get("step", 1) == n), {})
            for no, (seg_name, seg) in sorted(segs_by_no(msg).items()):
                if no not in EXTRA:
                    continue
                mx, rule, srefs, _ = EXTRA[no]
                key, declared, comp, _ = segment_length(no, seg)
                seg_res = "/".join(sorted({f["severity"] for k, fs in flagged.items() if k[0] == n and k[1] == seg_name
                                           and k[2] in (None, key) for f in fs})) or "PASS"
                O.append(f"| {no} | 1 | 85 | Segment Type | {cell(seg.get('SegmentType'))} | - | Fixed value {no} | "
                         f"{self.R(srefs[:1])} | {'PASS' if str(seg.get('SegmentType')) == no else 'FAIL'} | ☐ |")
                O.append(f"| {no} | 2 | 84 | Segment Length | {cell(declared)} | - | Adds up to "
                         f"{f'{comp:03d}' if isinstance(comp, int) else 'the run-time content'}; at most {mx} characters | "
                         f"{self.R(srefs + ['EL84'])} | {seg_res} | ☐ |")
                tcflds = (rs.get("request") or {}).get(seg_name) or {}
                for k, x in tcflds.items():
                    if not isinstance(x, dict) or k in ("Segment Type", "Segment Length", "Segment Length Indicator"):
                        continue
                    tkey, v = td_field(seg, k)
                    if tkey is None:
                        continue
                    c = element_constraint(self.env.ref, x.get("element_no"), k)
                    rule_ = (f"{c[0]}, {'fixed' if c[2] == 'FIXED' else 'max'} length {c[1]} (Chapter 13)" if c
                             else "Layout of the segment")
                    el_no = str(x.get("element_no") or "")
                    if no == "102":
                        rule_ += product_rule(el_no, v, seg)
                    res = "/".join(sorted({f["severity"] for f in flagged.get((n, seg_name, tkey), [])})) or "PASS"
                    O.append(f"| {no} | - | {esc(x.get('element_no') or '-')} | {esc(k)} | {cell(v)} | {cell(x.get('value'))} | "
                             f"{esc(rule_)} | {self.R(srefs[-1:] + ELEMENT_TEXT.get(el_no, []))} | {res} | ☐ |")
                    if no == "153" and norm_key(k) == "networktokendata":
                        O += self.token_rows(n, seg_name, v)
            O.append("")
            if rep.emv.get(n):
                O += [f"EMV Chip Data (Element 190) of step {n}, decoded from the hexadecimal value (2-byte BCD length first, "
                      "Appendix R):", "", "| Tag | Name | Length (bytes) | Value |", "|---|---|---|---|"]
                O += [f"| {g} | {tdv.EMV_TAGS.get(g, '-')} | {ln} | {cell(x)} |" for g, ln, x in rep.emv[n]]
                O.append("")

    def token_rows(self, n, seg_name, data):
        """Segment 153 Network Token Data split into its <tag n3><length n3><data> tables (p.310-312)."""
        items, _ = parse_tlv3(flat(data))
        fs = [f for f in self.t.findings if f["step"] == n and seg_name in (f.get("field") or "")]
        synth = any(f["rule"] == "X_SYNTHESIZED_CONTENT" for f in fs)
        rows = []
        for tag, ln, val in items:
            name, fixed, mxl = NET_TOKEN_TABLES.get(tag, ("not a Segment 153 table", None, 0))
            size = f"fixed length {fixed:03d}" if fixed else f"length up to {mxl:03d}"
            what = {"002": f"YYMM '{val}' = month {val[2:]} of 20{val[:2]}",
                    "004": f"'{val}' = " + {"Y": "Network Token", "N": "Clear PAN"}.get(val, "not a valid value")}.get(
                tag, f"'{val}' ({len(val)} characters)")
            ref = {"001": "S12.38-T001", "002": "S12.38-T002", "004": "S12.38-T004", "006": "S12.38-T006"}.get(tag, "S12.38-TLV")
            bad = any(f["rule"] == "X_FIELD_FORMAT" and str(f["value"]).startswith(f"{tag}{ln:03d}") for f in fs)
            res = ("FAIL" if bad else "REVIEW" if tag == "004" and any(f["rule"] == "X_TOKEN_INDICATOR" for f in fs)
                   else "BLOCKED" if synth and tag in ("001", "002") else "PASS")
            rows.append(f"| 153 | - | 239 / table {tag} | {esc(name)} | {cell(f'{tag}{ln:03d}{val}')} | - | Table {tag}, {size}: "
                        f"{esc(what)}" + ("; format right, value generated - the real token comes from the client"
                                          if res == "BLOCKED" else "") + f" | {self.R(['S12.38-TLV', ref])} | {res} | ☐ |")
        return rows

    def links(self):
        rep, O = self.rep, self.out
        O += ['<a id="s4"></a>', "## 4. Links between steps and expected responses", ""]
        if not rep.links and not rep.responses:
            O += ["Single-step test case - no link to another step.", ""]
            return
        if rep.links:
            O += ["| Step | Check | This step | Original step | ATL105 text | AI result | Your check |", "|---|---|---|---|---|---|---|"]
            O += [f"| {x['step']} | {esc(x['check'])} | {cell(x['value'])} | {cell(x['original'])} | {self.R(x['refs'])} | "
                  f"{x['result']} | ☐ |" for x in rep.links]
            O.append("")
        if rep.responses:
            O += ["| Step | Expected Response Code | Meaning | ATL105 text | AI result |", "|---|---|---|---|---|"]
            O += [f"| {x['step']} | `{x['code']}` | {esc(x['meaning'])} | {self.R(x['refs'])} | {x['result']} |"
                  for x in rep.responses]
            O.append("")

    def findings(self):
        t, O = self.t, self.out
        O += ['<a id="s5"></a>', "## 5. Findings", ""]
        fs = sorted(t.findings, key=lambda f: (CAT_ORDER.index(f["cat"]) if f["cat"] in CAT_ORDER else 99, f["step"] or 0))
        if not fs:
            O += ["None - the AI validation found nothing to report.", ""]
            return
        O += ["| # | Kind | Id | Step | Finding | Where | Value | Why | What it should be | ATL105 text | Agree? |",
              "|---|---|---|---|---|---|---|---|---|---|---|"]
        for i, f in enumerate(fs, 1):
            note = f.get("na_reason") or f.get("intended_reason") or ""
            O.append(f"| {i} | {CATS[f['cat']][0]} | {f['defect']} | {f['step'] or '-'} | {esc(f['title'])} | {esc(f['field'])} | "
                     f"{cell(f['value'])} | {esc(f['why'])}{(' ' + esc(note)) if note else ''} | {esc(f['fix'])} | "
                     f"{self.R(f['refs'])} | ☐ |")
        O += ["", "To confirm a finding: open the file named under *Where*, find the field, compare the value with the ATL105 "
              "text ([section 8](#s8)) and with *What it should be*. The defect id (column *Id*) is explained in the "
              "summary report.", ""]

    def generation(self):
        t, rec, O = self.t, self.rec, self.out
        O += ['<a id="s6"></a>', "## 6. How the test data was generated and what is missing", ""]
        unused = []
        for s in t.rsteps:
            rows, sent = [], t.msgs.get(s.get("step", 1)) or {}
            for seg_name, fl in (s.get("request") or {}).items():
                for k, x in (fl or {}).items():
                    if not isinstance(x, dict):
                        continue
                    v, method = x.get("value"), str(x.get("method") or "")
                    if v in (None, "") and method == "awaiting_client_value" and seg_name not in sent:
                        unused.append(f"{k} ({seg_name})")
                        continue
                    if v in (None, "") and not method.startswith("deliberate") and method != "awaiting_client_value":
                        continue
                    note = next((g for g in x.get("flags") or [] if not g.startswith(NOISE_FLAGS)), "")
                    if len(note) > 200:
                        note = note[:200].rsplit(" ", 1)[0] + " …"
                    rows.append(f"| {SEG_NUMBER.get(seg_name, esc(seg_name))} | {esc(k)} | {cell(v)} | `{method or '-'}` | "
                                f"{esc(note)} |")
            if rows:
                O += [f"Step {s.get('step', 1)} - values in the test case and how the generator produced them:", "",
                      "| Segment | Field | Value | How it was generated (`method`) | Generator note (first flag) |",
                      "|---|---|---|---|---|"] + rows + [""]
        missing = [f"Test case `awaiting_inputs`: {esc(a)}" for a in rec.get("awaiting_inputs") or []]
        missing += [f"Test case `open_items`: {esc(o if isinstance(o, str) else json.dumps(o, ensure_ascii=False))[:300]}"
                    for o in rec.get("open_items") or []]
        for n, msg in sorted(t.msgs.items()):
            for seg_name, seg in msg.items():
                if isinstance(seg, dict):
                    for k, v in seg.items():
                        if is_ph(flat(v)):
                            missing.append(f"Step {n}, {seg_name} -> {k} holds `{flat(v)}`")
        missing += [f"{CATS[f['cat']][0]} ({f['defect']}): {esc(f['title'])}" for f in t.findings if f["cat"] == "INPUT"]
        O += ["**What is missing before the test can run:**", ""]
        O += [f"- {m}" for m in tdv.uniq(missing)] or ["- Nothing - every value is in the test data."]
        O.append("")
        if unused:
            O += ["Generator library entries without a client value, for segments this test case does not send (not needed "
                  "to run it): " + ", ".join(f"`{esc(u)}`" for u in tdv.uniq(unused)) + ".", ""]

    def quotes(self):
        rep, O = self.rep, self.out
        O += ['<a id="s7"></a>', "## 7. ATL105 text quoted by the test case", ""]
        if not rep.quotes:
            O += ["The test case quotes no ATL105 text.", ""]
            return
        O += ["| Cited page | Quote in the test case | Check against ATL105 | AI result | Your check |", "|---|---|---|---|---|"]
        O += [f"| PDF p.{q['page']} (printed {self.env.spec.printed(q['page'])}) | {esc(str(q['quote'])[:400])} | "
              f"{esc(q['status'])} | {q['result']} | ☐ |" for q in rep.quotes]
        O.append("")

    def atl105(self):
        O = self.out
        O += ['<a id="s8"></a>', "## 8. ATL105 text used in this document", "",
              "Copied from the ATL105 text (release 2026-4); each line was searched on the stated page when this document was "
              "written.", ""]
        if not self.used:
            O += ["None.", ""]
            return
        for r in self.used:
            sec, page, text = self.refs.catalog[r]
            status = self.refs.check(r)
            flag = "" if status == "verified" else f" *(text check: {status})*"
            O += [f'<a id="{anchor_id(r)}"></a>', f"**{r}** - {esc(sec)}, PDF p.{page} (printed {self.env.spec.printed(page)})"
                  f"{flag}", "", f"> {esc(text)}", ""]

    def signoff(self):
        self.out += ['<a id="s9"></a>', "## 9. Sign-off", "", "| Item | Value |", "|---|---|",
                     f"| AI result | {self.t.verdict} |", "| Your result | ☐ Agree ☐ Disagree |", "| Checked by | |",
                     "| Date | |", "| Notes | |", ""]


def write_tc_docs(out, env, guide_ids, root=None, flat=False, title="Manual validation"):
    """<root>/<scenario>/<test case>.md for every test case plus an index (root default <out>/Test cases); flat: <root>/<TC>.md."""
    root = root or out / TC_DOCS
    by_scen = defaultdict(list)
    for t in env.tcs:
        by_scen[t.rec.get("scenario_display_id") or "other"].append(t)

    def ts_key(s):
        m = re.search(r"TS(\d+)$", s)
        return (int(m.group(1)) if m else 10 ** 6, s)
    for scen, tcs in by_scen.items():
        d = root if flat else root / scen
        d.mkdir(parents=True, exist_ok=True)
        lk = Links(d, env)
        for t in tcs:
            rel = run_link(d, out / f"{t.id}-Manual-Validation.md") if t.id in guide_ids else None
            (d / f"{t.id}.md").write_text(TCDoc(env, t, lk, rel, title).build(), encoding="utf-8")
    if flat:
        return len(env.tcs)
    v = Counter(t.verdict for t in env.tcs)
    lines = [f"# Manual validation documents - one per test case ({env.label})", "",
             f"{len(env.tcs):,} test cases in {len(by_scen)} scenarios: FAIL {v['FAIL']:,} · BLOCKED {v['BLOCKED']:,} · REVIEW "
             f"{v['REVIEW']:,} · PASS {v['PASS']:,} · DEVICE {v['DEVICE']:,}.", "",
             "Every document has the same sections: 1 how to check by hand, 2 what the test case does, 3 field-by-field check "
             "of the test data against ATL105, 4 links between steps and expected responses, 5 findings, 6 how the test data "
             "was generated and what is missing, 7 the ATL105 text the test case quotes, 8 the ATL105 text used (copied with "
             "PDF page and printed page), 9 sign-off.", "",
             "| Scenario | Name | Test cases | FAIL | BLOCKED | REVIEW | PASS | DEVICE |", "|---|---|---:|---:|---:|---:|---:|---:|"]
    for scen in sorted(by_scen, key=ts_key):
        tcs = by_scen[scen]
        c = Counter(t.verdict for t in tcs)
        lines.append(f"| [{scen}](#{scen.lower()}) | {esc(scenario_label(tcs[0].rec))} | {len(tcs)} | {c['FAIL']} | {c['BLOCKED']} | "
                     f"{c['REVIEW']} | {c['PASS']} | {c['DEVICE']} |")
    for scen in sorted(by_scen, key=ts_key):
        tcs = by_scen[scen]
        lines += ["", f'<a id="{scen.lower()}"></a>', f"## {scen} - {esc(scenario_label(tcs[0].rec))}", "",
                  "| Test case | Display id | Objective | Result | Document |", "|---|---|---|---|---|"]
        for t in sorted(tcs, key=lambda t: [int(x) if x.isdigit() else x for x in re.split(r"(\d+)", t.rec["display_id"])]):
            lines.append(f"| {t.id} | {t.rec['display_id']} | {esc(t.rec.get('test_objective') or '')} | **{t.verdict}** | "
                         f"[{t.id}.md]({urlquote(scen)}/{t.id}.md) |")
    (root / "README.md").write_text("\n".join(lines) + "\n", encoding="utf-8")
    return len(env.tcs)


def write_readme(out, env, lk, guides):
    tcs = env.tcs
    v = Counter(t.verdict for t in tcs)
    lines = [f"# Manual Validation - {env.label}", "",
             f"Validation of all {len(tcs):,} test cases and {sum(len(t.files) for t in tcs):,} test data files of the run "
             f"{lk.file('', env.run.name)} against {tdv.SPEC_TITLE}, release {env.spec.version} ({env.spec.date}) - the "
             "attached PDF (`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf`, byte-identical to the "
             f"validator's copy) - with a manual validation document for every test case and detailed step-by-step guides "
             f"for {len(guides)} test cases.", "",
             f"**Overall:** FAIL {v['FAIL']:,} · BLOCKED {v['BLOCKED']:,} · REVIEW {v['REVIEW']:,} · PASS {v['PASS']:,} · "
             f"DEVICE (device-only) {v['DEVICE']:,}.", "",
             "| File | What it is |", "|---|---|",
             f"| [{SUMMARY}]({urlquote(SUMMARY)}) | Detailed analysis of every test case and test data file: each defect with the "
             "ATL105 section, PDF page, printed page and exact text, affected test cases and files, how to check by hand and the fix |",
             f"| [{SHEET}]({urlquote(SHEET)}) | The same results as filterable sheets: Test cases (1 row per test case), Findings "
             "(every finding), Defects, Scenarios |",
             f"| [{TC_DOCS}/README.md]({urlquote(TC_DOCS)}/README.md) | One manual validation document per test case "
             "(field-by-field check against ATL105, findings, how the data was generated, what is missing, the ATL105 text), "
             "grouped by scenario |"]
    for t in guides:
        lines.append(f"| [{t.id}-Manual-Validation.md]({t.id}-Manual-Validation.md) | {t.rec['display_id']} - "
                     f"{esc(t.rec.get('test_objective'))} - **{t.verdict}** |")
    lines += ["", f"## The {len(guides)} test cases with a detailed step-by-step guide", "",
              "These guides go further than the per-test-case documents: they walk through every field with the ATL105 "
              "extract next to it, recalculate lengths and check digits, and explain each finding.", "",
              "| Test case | Steps | Test data | AI result | Can it run as-is? |", "|---|---|---|---|---|"]
    for t in guides:
        lines.append(f"| [{t.id}]({t.id}-Manual-Validation.md) ({t.rec['display_id']}) | {t.rec.get('message_chain')} | "
                     + ", ".join(lk.td(t, n) for n in sorted(t.files)) + f" | **{t.verdict}** | {can_run(t)} |")
    lines += ["", "## How to check a test case by hand", "",
              f"1. Open [{TC_DOCS}/README.md]({urlquote(TC_DOCS)}/README.md), find the test case and open its document in "
              "preview (right-click → Open Preview), next to its test data file(s).",
              "2. Follow section 1 of the document: it lists the checks in order.",
              "3. In each table compare the value in the file with the ATL105 rule and the copied ATL105 text, and tick "
              "**Your check**.",
              "4. For each finding note Agree / Disagree and complete the sign-off.", "",
              "## How the results were produced", "",
              "- `scripts/validate-run-and-build-manual-validation.py` runs the trained ATL105 validator "
              "(`scripts/validate-td-against-atl105-spec.py`) on every test case, adds the checks this run needs beyond the "
              "trained rules, classifies every finding and writes this folder. Re-run it after the test cases or test data change.",
              "- ATL105 text: `specifications/ATL105/docs/specs/extracted_text_new.txt` (release 2026-4). Every quoted text is "
              "checked against its PDF page on every run; the script stops if one is not found.", ""]
    (out / "README.md").write_text("\n".join(lines) + "\n", encoding="utf-8")


# ---------------------------------------------------------------- package for selected test cases
def manifest_check(env):
    """SHA-256 of the files the selected test cases use, against the run's own MANIFEST.json."""
    path = env.run / "MANIFEST.json"
    if not path.exists():
        return None
    listed = json.loads(path.read_text(encoding="utf-8")).get("files") or {}
    present = {p.relative_to(env.run).as_posix() for p in env.run.rglob("*") if p.is_file()}
    rows = []
    for p in ["test_cases/test_cases.jsonl"] + [f"test_data/{f}" for t in env.tcs for _, f in sorted(t.files.items())]:
        f = env.run / p
        rows.append((p, hashlib.sha256(f.read_bytes()).hexdigest() if f.exists() else None, listed.get(p)))
    return dict(listed=len(listed), present=len(set(listed) & present),
                missing=Counter(p.split("/")[0] for p in set(listed) - present), rows=rows)


def old_ids(run):
    """New test case id -> its id in the previous build (id_migration.csv of the run)."""
    path = run / "id_migration.csv"
    if not path.exists():
        return {}
    with open(path, encoding="utf-8-sig", newline="") as fh:
        return {r["new_test_case_id"]: r["old_test_case_id"] for r in csv.DictReader(fh) if r.get("old_test_case_id")}


def tc_table_row(t, lk, olds, res, man):
    return (f"| {t.id} | {olds.get(t.id, '-')} | {t.rec['display_id']} | {esc(t.rec.get('test_objective'))} | "
            + ", ".join(lk.td(t, n) for n in sorted(t.files)) + f" | **{t.verdict}** | {can_run(t)} | "
            f"[{t.id}-Manual-Validation.md]({man}{t.id}-Manual-Validation.md) | [{t.id}.md]({res}{t.id}.md) |")


def write_package_readme(man, env, lk, guides):
    olds, v = old_ids(env.run), Counter(t.verdict for t in env.tcs)
    res = f"../{urlquote(RESULT_DIR)}/"
    lines = [f"# Manual Validation - {len(guides)} test cases of the {env.label}", "",
             f"Step-by-step guides to check by hand each test case and its test data in the run {lk.file('', env.run.name)} "
             f"against {tdv.SPEC_TITLE}, release {env.spec.version} ({env.spec.date}) - the attached "
             "`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` - and to confirm or reject what the AI "
             "validation reported. The ATL105 text needed for every check is copied into section 11 of each guide with its "
             "PDF page and printed page, so the specification does not have to be opened.", "",
             "**Result:** " + " · ".join(f"{k} {v[k]}" for k in ("FAIL", "BLOCKED", "REVIEW", "PASS", "DEVICE") if v[k]) + ".", "",
             "| Test case | Previous id | Display id | What it tests | Test data | AI result | Can it run as-is? | Guide | "
             "ATL105 result (every field) |", "|---|---|---|---|---|---|---|---|---|"]
    lines += [tc_table_row(t, lk, olds, res, "") for t in guides]
    lines += ["", "## How to validate a test case by hand", "",
              "1. Open the guide in preview (right-click → Open Preview) and its test data file(s) next to it.",
              "2. **Section 1** - what the test case does: objective, steps, Prompt Codes and expected Response Codes.",
              "3. **Section 2** - the manual validation path: the checks in order.",
              "4. **Section 3** - how every value was generated (the `method` and generator note recorded in the test case) "
              "and what is still missing before the test can run.",
              "5. **Section 4** - every field of every segment: compare the value in the file with the ATL105 rule and tick "
              "**Your check**. The ATL105 text behind each rule is in **section 11**.",
              "6. **Section 5** - recalculate the segment lengths, the card check digit and the expiry.",
              "7. **Sections 6-8** - links between steps, expected Response Codes and the ATL105 text the test case quotes.",
              "8. **Section 9** - confirm or reject each finding with its *How to confirm by hand* steps; **section 10** - checks "
              "across all test cases of the run.",
              "9. **Section 12** - sign off.", "",
              f"The same results in compact form (every segment and field with the ATL105 rule, the AI result and the ATL105 "
              f"text): [{RESULT_DIR}]({res}{urlquote(RESULT_SUMMARY)}). Defect sheets of this package (each defect with the "
              "actual and expected test data): " + ", ".join(f"[../{p.name}](../{urlquote(p.name)})"
                                                            for p in sorted(man.parent.glob('*.md'))) + ".", "",
              "## How the results were produced", "",
              f"- `{env.command}`",
              f"- The trained ATL105 validator (`scripts/validate-td-against-atl105-spec.py`) and the extended checks ran on all "
              f"{env.total:,} test cases of the run, so the checks across test cases see every test case; the documents are "
              "written for the test cases above.",
              "- Every ATL105 text in the guides was found on its PDF page in `specifications/ATL105/docs/specs/"
              "extracted_text_new.txt` (release 2026-4) when the guides were written.", ""]
    (man / "README.md").write_text("\n".join(lines) + "\n", encoding="utf-8")


def write_overview(path, env, lk, guides, title=None, scope=None, intro=None, outro=None):
    """BA review sheet of a package: defects with TC/TD references, actual evidence, expected behaviour, ATL105 text and
    acceptance criteria, then the actual and expected test data of every test case. intro / outro: markdown inserted
    before the defect table / before the appendix."""
    tcs, base = env.tcs, path.parent
    groups = sheet_groups(tcs)
    run_tcs = getattr(env, "all_tcs", None) or tcs
    in_run = {group_title(g, label): fs for g, label, fs in sheet_groups(run_tcs)} if run_tcs is not tcs else {}
    gids = {t.id for t in guides}
    res, man = f"{urlquote(RESULT_DIR)}/", f"{urlquote(MANUAL_DIR)}/"
    v, status = Counter(t.verdict for t in tcs), Counter(t.rec.get("status") for t in tcs)
    touched = tdv.uniq(t.id for _, _, fs in groups for t, _ in fs)
    title = title or f"MasterCard Credit: defect explanation for BA review - {len(tcs)} test cases"
    L = [f"# {esc(title)}", "", "## Purpose and scope", "",
         scope or (f"This sheet explains the defects found in {len(tcs)} test cases for the BA review. Every finding was "
                   "produced by the ATL105 validator from the test case definitions and their test data files, and every "
                   "ATL105 text quoted here was found word for word on its PDF page."), "",
         "| Item | Value |", "|---|---|",
         f"| Run | {lk.file('', env.run.name)} - {esc(env.label)}, build `{tcs[0].rec.get('build_id')}` (only this run was "
         "used; its files were checked against the run's own MANIFEST.json - see the appendix) |",
         f"| Specification | {tdv.SPEC_TITLE}, release {env.spec.version} ({env.spec.date}) - the attached "
         "`BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` |",
         "| Test cases | " + ", ".join(f"[{t.id}](#{t.id.lower()})" for t in tcs) + " |",
         "| AI result | " + " · ".join(f"{k} {v[k]}" for k in ("FAIL", "BLOCKED", "REVIEW", "PASS", "DEVICE", "NOT CHECKED")
                                        if v[k]) + " |",
         "| Readiness | " + ", ".join(f"{n} `{s}`" for s, n in status.most_common()) + " |", "",
         "- **TC** = test case: what the test says it will exercise (`test_cases.jsonl`, one test case per line).",
         "- **TD** = test data: the request prepared for transmission to the host (`test_data/TD-TC-....json`).",
         "- **DS** = data segment (Segment 100, 111, ...); **on the wire** = actually included in the transmitted request.",
         "- **Element** = ATL105 data element (Chapter 13); **Table** = Variable Information table in Segment 111 (Appendix I).",
         "- **FAIL** = must be fixed; **BLOCKED** = cannot run until a client or run-time value is supplied; **REVIEW** = to "
         "correct or to confirm with BUYPASS.", "",
         "These are composition defects in the delivered test cases and test data, found by reading them against ATL105 - not "
         "observed production failures. No host transactions were executed for this review.", ""]
    if intro:
        L += [intro.rstrip(), ""]
    L += (["## Defects in this sheet", ""] if intro else []) + (
        [f"**In the run** = test cases of the whole run ({len(run_tcs):,}) with the defect; **shown** = test cases of this "
         "sheet. Each defect section lists every affected test case of the run.", "",
         "| Defect | In the run | Shown | Kind | Defect id | Priority |", "|---|---:|---:|---|---|---|"] if in_run else
        ["| Defect | Affected test cases | Kind | Defect id | Priority |", "|---|---:|---|---|---|"])
    for k, (g, label, fs) in enumerate(groups, 1):
        run_n = (f"{len(tdv.uniq(t.id for t, _ in in_run.get(group_title(g, label), fs))):,} | " if in_run else "")
        L.append(f"| [{k}. {esc(group_title(g, label))}](#d{k}) | {run_n}{len(tdv.uniq(t.id for t, _ in fs))} | "
                 + ", ".join(tdv.uniq(CATS[f['cat']][0] for _, f in fs)) + " | " + ", ".join(tdv.uniq(f["defect"] for _, f in fs))
                 + (f" ({g['register']})" if g.get("register") else "") + f" | {g['priority']} |")
    L.append("")
    for k, (g, label, fs) in enumerate(groups, 1):
        L += group_section(k, g, label, fs, env, lk, base, in_run.get(group_title(g, label)) if in_run else None)
    nd = defaultdict(list)
    for t in tcs:
        for f in t.findings:
            if f["cat"] in ("INTENDED", "AI_NA", "INFO"):
                nd[(f["cat"], f["rule"])].append((t, f))
    if nd:
        L += ['<a id="not-defects"></a>', "", "## Not defects (shown so they are not raised again)", "",
              "One row per kind of finding with its first example; each test case section below lists its own.", "",
              "| Kind | Finding (first example) | Test cases | Actual | Where | Why it is correct |", "|---|---|---|---|---|---|"]
        for (cat, _), xs in nd.items():
            t, f = xs[0]
            L.append(f"| {CATS[cat][0]} | {esc(f['title'])} | "
                     + ", ".join(f"[{i}](#{i.lower()})" for i in tdv.uniq(x.id for x, _ in xs))
                     + f" | {finding_actual(t, f)} | {finding_where(t, f, lk)} | {finding_expected(t, f)} |")
        L.append("")
    L += ['<a id="test-data"></a>', "", "## Test data - actual and expected, per test case", "",
          "For each test case: its findings (actual value, where it is, what it should be), the test data as delivered "
          "(click to expand) and the expected test data - the same file with every change; each changed line ends with a `//` "
          "comment naming the defect id. Values in `<...>` must come from the client, BUYPASS or the previous response.", ""]
    for t in tcs:
        L += tc_section(t, env, lk, gids, res, man)
    L += ["## How to demonstrate the defects during the BA discussion", ""]
    for k, (g, label, fs) in enumerate(groups, 1):
        t, f = fs[0]
        L.append(f"{k}. **{esc(group_title(g, label))}** - open {lk.jsonl(t, t.id)} and read the test name and objective, then "
                 f"open {finding_where(t, f, lk)}: actual {finding_actual(t, f)}; expected: {finding_expected(t, f)}")
    high = [(g, label, fs) for g, label, fs in groups if g["priority"] == "High"]
    L += ["", "## Summary for BA", "",
          f"**{len(groups)} defects in {len(touched)} of the {len(tcs)} test cases; {len(high)} of them are high priority.**", ""]

    def bullet(g, label, fs):
        return (f"- {esc(group_title(g, label))} - {len(tdv.uniq(t.id for t, _ in fs))} test case(s)"
                + (f" ({g['register']})." if g.get("register") else "."))
    logged = [x for x in groups if re.search(r"BA review|register N\d", x[0].get("register") or "")]
    if logged:
        L += ["Raised in the BA defect log:", ""] + [bullet(*x) for x in logged] + [""]
    found = [x for x in high if x not in logged]
    if found:
        L += ["Also found by the validator (high priority):" if logged else "High priority:", ""] + [bullet(*x) for x in found]
    rest = [(g, label, fs) for g, label, fs in groups if g["priority"] != "High"]
    if rest:
        L += ["", "Medium and low priority: " + "; ".join(f"{esc(group_title(g, label))} ({len(tdv.uniq(t.id for t, _ in fs))})"
                                                     for g, label, fs in rest) + "."]
    L += ["", "The risk is misleading test results and coverage, not a demonstrated host defect: the requests must match the "
          "scenario each test claims, and nothing can run before the real client values are supplied.", ""]
    if outro:
        L += [outro.rstrip(), ""]
    L += sheet_appendix(env, lk, guides, res, man)
    path.write_text("\n".join(L) + "\n", encoding="utf-8")


# BA-facing groups of the defect sheet: defect ids, priority, plain-language explanation and acceptance criteria
SHEET_GROUPS = [
    dict(ids={"C-10"}, split=True, priority="High", register="AI-team register N17-01",
         title="{label} is claimed by the test but not sent in the request",
         ba="The test says it sends {label}, but the request does not contain it. A pass cannot be counted as coverage of "
            "{label}.",
         accept=["The request carries the claimed data with the segments it depends on, and the test asserts it - or the test "
                 "is re-scoped or withdrawn with the reason written down.",
                 "Missing client data is named as an open item and the test is not marked 'ready'.",
                 "A regression check fails a positive test whose claimed table, segment or value is not in its request."]),
    dict(ids={"D-02"}, priority="High", register="AI-team register N17-03",
         title="Original Authorization Only requests carry an Approval Number and other fields that do not belong to them",
         ba="We are asking for a new approval while already sending an approval number - plus PIN, fuel, pump and date/time "
            "values that do not belong to this transaction or merchant. The test must show a clean new authorization.",
         accept=["No Approval Number on an original Authorization Only: the field stays empty (its Field Separator is still "
                 "sent).",
                 "Reversals and completions keep the Approval Number of their original - do not remove those.",
                 "Local Date and Local Time, Pump/Lane Number, Fuel Purchase Amount and Encrypted PIN Block Data are sent only "
                 "when their condition holds, and the Segment 100 length is recalculated.",
                 "A regression check rejects a populated Approval Number on an original Authorization Only."]),
    dict(ids={"C-11"}, label_has="AVS", priority="High", register="BA review 19-11-2026",
         title="AVS information expected in the response, but the request sends no AVS data (Table 003)",
         ba="The test expects the host to send back the AVS (address verification) result, but the request carries no address "
            "data. ATL105 says the request initiates the AVS information in the response, so there is nothing to verify and "
            "nothing to return - an approval alone proves nothing about AVS.",
         accept=["The request sends Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or "
                 "ZIP+4) and up to 20 characters of street address, from the client's test card data; the Segment 111 "
                 "length is recalculated.",
                 "The test asserts the AVS result in Segment 112 Table 003 (AVS+ or AVS- with the issuer's code) - not only "
                 "Response Code 2.",
                 "AVS is used primarily for mail/phone order and CAT transactions: confirm the card-present variants with "
                 "BUYPASS, or re-scope them.",
                 "A regression check fails a positive test whose expected response table is initiated by a request table it "
                 "does not send."]),
    dict(ids={"C-11"}, label_has="Card verification", priority="High",
         title="CVV information expected in the response, but the request sends no card verification data (Table 004)",
         ba="The test expects the host to send back the CVV result, but the request carries no CVC2. ATL105 says the request "
            "initiates the CVV information in the response, so an approval alone proves nothing about it.",
         accept=["The request sends Table 004 with the test card's CVC2 (3 digits); the Segment 111 length is recalculated.",
                 "The test asserts the CVV result in Segment 112 Table 004 (M, N or P for MasterCard)."]),
    dict(ids={"C-02"}, priority="High", register="BA review 19-11-2026",
         title="Negative test leaves out something ATL105 does not require here - the expected decline is not justified",
         ba="Omitting a segment is a negative test only when the scenario makes that segment mandatory. If it is optional or "
            "not applicable, its absence alone does not justify expecting a decline - and the page the test cites does not "
            "say the segment is required.",
         accept=["Cite the ATL105 rule that makes the segment mandatory for this exact scenario, word for word on the cited "
                 "page - or remove the test.",
                 "To test a missing mandatory segment, use a scenario where ATL105 requires it (for Segment 108: a Loyalty "
                 "Card Transaction Request) and leave it out there.",
                 "A regression check fails 'segment omitted' negative tests whose cited page does not make the segment "
                 "required."]),
    dict(ids={"I-01", "C-03"}, priority="High",
         title="The Terminal Identifier is a generated placeholder, yet the test cases are marked ready",
         ba="Every request uses a made-up terminal. BUYPASS only accepts terminals it has set up, so these tests cannot run "
            "until the real Terminal ID is supplied - and they should not be 'ready' until then.",
         accept=["Client / BUYPASS supply the real Terminal Identifier of the merchant's terminal; every step uses it and the "
                 "Segment 100 length is recalculated.",
                 "A test case with a placeholder terminal has a status other than 'ready' (for example requires_review).",
                 "A regression check flags 'ready' test cases whose merchant is a placeholder."]),
    dict(ids={"C-06"}, priority="High",
         title="Time-out Reversal sent although the original request is expected to be answered",
         ba="A time-out reversal is only sent when the original gets no answer within 30 seconds. This test expects the "
            "original to be approved, so a real device would never send the reversal.",
         accept=["The original step is a fault-injected 'no response' (or a late response the harness ignores) and expects no "
                 "response.",
                 "The Time-out Reversal then follows on the same device with the original's Sequence Number, card and amount - "
                 "only the rule under test differs."]),
    dict(ids={"C-07"}, priority="Medium", title="'Different terminal' negative tests use a terminal BUYPASS does not know",
         ba="To prove that a reversal from another device is declined, the other device must be a real terminal of the same "
            "merchant. With a made-up terminal the decline only proves that unknown terminals are rejected.",
         accept=["The follow-on step uses a second real Terminal Identifier of the same merchant, assigned by BUYPASS.",
                 "The expected decline for the same-device rule is confirmed with BUYPASS."]),
    dict(ids={"C-05"}, priority="Medium", title="The test objective names a different transaction than the one sent",
         ba="The objective says one transaction, but the request (and the test name) is another. Coverage reports grouped by "
            "objective count this test in the wrong place.",
         accept=["Objective, name and Prompt Code describe the same transaction."]),
    dict(ids={"I-06", "S-03", "S-04"}, priority="Medium",
         title="Network token data (Segment 153) is synthesized, and its indicator and length need confirmation",
         ba="The network token was made up by the generator, the token flag says 'token' while the card number is sent in "
            "Element 2, and the segment length depends on a Field Separator rule ATL105 does not state clearly. An approval "
            "cannot be expected until real token data is supplied.",
         accept=["Client supplies the merchant's network-token entitlement and a real provisioned token (table 001) with its "
                 "expiry (table 002).",
                 "BUYPASS confirms which field carries the token when table 004 = Y, and the Segment 153 length rule.",
                 "The test stays out of 'ready' until then."]),
    dict(ids={"I-02"}, priority="Medium", title="Follow-on values are instruction text to be filled in from the previous response",
         ba="The reversal must carry the approval number the host returns for the purchase. The file holds an instruction "
            "text; the test harness must substitute the real value at run time.",
         accept=["The harness replaces the instruction text with the value from the previous response before sending.",
                 "The Segment 100 length is recalculated after the substitution."]),
    dict(ids={"I-04"}, priority="Medium", title="EMV chip data is generated, not captured from a card or simulator",
         ba="Cryptogram, counters and terminal results in the chip data are generated values. The issuer cannot validate them, "
            "so the test needs chip data from the client's test card or simulator.",
         accept=["Element 190 comes from the client's test card or simulator; TVR and CVM results agree; the CA Public Key "
                 "checksum comes from the current key load."]),
    dict(ids={"Q-01", "Q-02", "Q-03", "Q-04"}, priority="Low",
         title="ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context",
         ba="Some quotes the generator wrote into the test cases cite the wrong page or are not ATL105 text. They do not change "
            "the request, but a reviewer following the citation will not find the rule.",
         accept=["Every spec_basis quote is ATL105 text, word for word, on the cited page and about this transaction."]),
]
# groups of the 20-11 run set: registers say what happened to the defects logged for the 16-11 run
REG_NEW = "new in the 20-11 run"
SHEET_GROUPS_2011 = [
    dict(ids={"C-10"}, split=True, priority="High",
         register_by_label={"Tax Type 1 = GST": "BA review 19-11-2026 (GST) - still open, now on Purchase/Capture"},
         register=REG_NEW,
         title="{label} is claimed by the test but not sent in the request",
         ba="The test says it sends {label}, but the request does not contain it. A pass cannot be counted as coverage of "
            "{label}.",
         accept=["The request carries the claimed data with the segments it depends on, and the test asserts it - or the test "
                 "is re-scoped or withdrawn with the reason written down.",
                 "Missing client data is named as an open item and the test is not marked 'ready'.",
                 "A regression check fails a positive test whose claimed table, segment or value is not in its request."]),
    dict(ids={"D-16"}, priority="High", register=REG_NEW + " (0 test cases in 16-11)",
         title="Follow-on steps send the keyed card's expiry as MMYY instead of YYMM",
         ba="A reversal, time-out reversal, cancellation or void is built from the card data stored with the original "
            "transaction, and ATL105 sends that card data as Account Number = YYMM. These follow-on steps copy the keyed "
            "MMYY expiry of the original (for example 0131 = January 2031), which read as YYMM is month 31. The follow-on "
            "can be rejected or not matched for a reason the test does not intend.",
         accept=["Every follow-on step of a keyed original (Table 005 entry mode 01) sends the expiry as YYMM: month and year "
                 "of the original's MMYY swapped (0131 -> 3101).",
                 "The Segment 100 length does not change (4 characters either way).",
                 "A regression check compares the follow-on expiry with the original's MMYY."]),
    dict(ids={"D-09"}, rules={"LINK_ACCOUNTNUMBER"}, priority="High", register=REG_NEW,
         title="Issuer-decline tests of a follow-on transaction wait for a different card in the follow-on step only",
         ba="To get an issuer decline on the reversal (or cancellation, void, completion), the generator asks the client for "
            "a decline test card and puts it into the follow-on step only. A follow-on must carry the same card as its "
            "original; with another card it no longer matches the original, so BUYPASS would reject it as unmatched - not "
            "because the issuer declined it.",
         accept=["The follow-on step repeats the original's Account Number (and the expiry as YYMM).",
                 "How a follow-on of an approved original can be declined by the issuer is confirmed with BUYPASS (for example "
                 "a simulator rule); otherwise the test is withdrawn.",
                 "A regression check rejects a follow-on step whose Account Number differs from its original's."]),
    dict(ids={"D-09"}, rules={"FIELD_102"}, priority="High", register=REG_NEW,
         title="'No original' negative tests use a made-up, invalid Terminal Identifier",
         ba="These tests send a reversal, cancellation or void that has no original, to prove BUYPASS declines it. But the "
            "request also uses a random Terminal Identifier whose State Code is not valid, so a decline would only show "
            "that an unknown terminal is rejected - not the missing-original rule.",
         accept=["The step uses the merchant's Terminal Identifier (the same as the other tests of the merchant); only the "
                 "missing original differs.",
                 "A regression check validates the Terminal Identifier (Device Type + Appendix D State Code) of negative tests "
                 "whose rule is not about the terminal."]),
    dict(ids={"D-09"}, priority="High",
         title="Other negative tests that break a second rule besides the one under test",
         ba="A negative test must break exactly one rule, so that the decline can only come from that rule. These requests "
            "also break another ATL105 rule (missing Table 030 / Credential on File indicators, wrong segment length, entry "
            "mode that does not fit the scenario), so the cause of a decline cannot be told.",
         accept=["Every field except the one under test is valid for the scenario; the other rule named in each finding is "
                 "fixed.", "A regression check validates the whole request of each negative test except its deliberate field."]),
    dict(ids={"D-13"}, priority="High", register="also in 16-11 (37 test cases)",
         title="Card read from the chip or stripe, but the card data is only an MMYY expiry",
         ba="Table 005 says the card was read (chip or magnetic stripe), so ATL105 expects the track data after the '=' "
            "(expiry YYMM, service code, discretionary data). The request sends only a 4-digit MMYY expiry, as for a keyed "
            "card - the message contradicts itself.",
         accept=["A read card sends its Track 2 (equivalent) data after the '=' in Card Discretionary Block Data, or the test "
                 "uses a keyed entry mode when only the expiry is known."]),
    dict(ids={"D-01"}, priority="High", register="also in 16-11 (28 test cases)",
         title="Totals Request does not match the transactions of the flow",
         ba="The totals at the end of the flow must add up the approved transactions of the same test (Section 9.2). The "
            "files send counts and amounts that do not match the steps, so a totals test cannot pass for the right reason.",
         accept=["Card bucket count/amount and Grand Total are computed from the approved steps of the flow; an Authorization "
                 "Only goes to the AO bucket; a bucket with net count 0 is not sent."]),
    dict(ids={"C-02"}, priority="High", register="BA review 19-11-2026 (Segment 108) - premise defect still in other tests",
         title="Negative tests whose premise is not in ATL105 - the expected decline is not justified",
         ba="A negative test must break a rule ATL105 states. These tests send a field ATL105 makes Required whenever its "
            "segment is sent (Payment Tender Type in Segment 108, Service Level in Segment 102), call a supported "
            "transaction 'not supported' (Mail/Phone Reversal), or leave out a conditional segment that the cited page does "
            "not require - and the pages they cite are about something else.",
         accept=["Cite the ATL105 rule the test breaks, word for word on the cited page - or remove the test.",
                 "A 'field sent where the spec forbids it' test names the rule that forbids the field; a 'segment omitted' "
                 "test uses a scenario where ATL105 requires the segment.",
                 "A regression check fails negative tests whose premise is contradicted by Section 11.1 / 12.x."]),
    dict(ids={"I-01", "C-03"}, priority="High",
         title="The Terminal Identifier is a generated placeholder, yet the test cases are marked ready",
         ba="Every request uses a made-up terminal. BUYPASS only accepts terminals it has set up, so these tests cannot run "
            "until the real Terminal ID is supplied - and they should not be 'ready' until then.",
         accept=["Client / BUYPASS supply the real Terminal Identifier of the merchant's terminal; every step uses it and the "
                 "Segment 100 length is recalculated.",
                 "A test case with a placeholder terminal has a status other than 'ready'.",
                 "A regression check flags 'ready' test cases whose merchant is a placeholder."]),
    dict(ids={"C-06"}, priority="High", register="also in 16-11 (62 test cases)",
         title="Time-out Reversal sent although the original request is expected to be answered",
         ba="A time-out reversal is only sent when the original gets no answer within 30 seconds. This test expects the "
            "original to be approved, so a real device would never send the reversal.",
         accept=["The original step is a fault-injected 'no response' (or a late response the harness ignores) and expects no "
                 "response.",
                 "The Time-out Reversal then follows on the same device with the original's Sequence Number, card and amount."]),
    dict(ids={"C-12"}, priority="Medium",
         register="BA review 16/19-11-2026 (Table 048 not sent) - fixed: now sent; scope to confirm",
         title="Table 048 (3-D Secure authentication data) is now sent, but on POS purchases",
         ba="The logged defect is fixed: the tests now send Table 048 (Program Protocol 1 = EMV 3-D Secure 2.1). But ATL105 "
            "applies Table 048 to e-commerce transactions authenticated with 3-D Secure, and these are POS chip, "
            "contactless, swiped and keyed purchases. BUYPASS may ignore or reject the table; either way the test does not "
            "show 3-D Secure handling.",
         accept=["BUYPASS confirms whether Table 048 is accepted on POS transactions; if not, the tests are re-scoped to "
                 "e-commerce (3-D Secure) or withdrawn.",
                 "If kept, the test asserts how BUYPASS treats the table, not only the approval."]),
    dict(ids={"I-03"}, value_has="Table 003", priority="Medium",
         register="BA review 19-11-2026 (AVS Table 003 missing) - fixed: Table 003 now waits for client data",
         title="AVS tests now send Table 003 and wait for the client's AVS card data",
         ba="The logged defect is fixed: the AVS tests now carry Table 003 in Segment 111 and expect the AVS response codes "
            "(3 = Authorization only with AVS, 4 = Purchase/Capture with AVS). The street address / ZIP Code is a "
            "placeholder for the client's AVS test card, and the tests are correctly not 'ready'.",
         accept=["Client supplies the AVS test card's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address; the "
                 "table length and the Segment 111 length are recalculated.",
                 "The test asserts the AVS result in Segment 112 Table 003, not only the Response Code."]),
    dict(ids={"C-07"}, priority="Medium", title="'Different terminal' negative tests use a terminal BUYPASS does not know",
         ba="To prove that a follow-on from another device is declined, the other device must be a real terminal of the same "
            "merchant. With a made-up terminal the decline only proves that unknown terminals are rejected.",
         accept=["The follow-on step uses a second real Terminal Identifier of the same merchant, assigned by BUYPASS."]),
    dict(ids={"S-05"}, rules={"X_MOTO_COMPLETION_CODE"}, priority="Medium",
         title="Mail/Phone Authorization Only completed with Transaction Type 6 - the code is to be confirmed",
         ba="The completion of a Mail/Phone Authorization Only is sent as a Mail/Phone Purchase (6) with the Approval "
            "Number. ATL105 names completion codes only for POS (0) and customer-activated (4) purchases, so BUYPASS must "
            "confirm the code before these tests can be trusted.",
         accept=["BUYPASS confirms the Transaction Type that completes a Mail/Phone Authorization Only, and the tests use it."]),
    dict(ids={"C-05"}, priority="Medium", title="The test objective names a different transaction than the one sent",
         ba="The objective says one transaction, but the request (and the test name) is another. Coverage reports grouped by "
            "objective count this test in the wrong place.",
         accept=["Objective, name and Prompt Code describe the same transaction."]),
    dict(ids={"I-06", "S-03", "S-04"}, priority="Medium",
         title="Network token data (Segment 153) is synthesized, and its indicator and length need confirmation",
         ba="The network token was made up by the generator, the token flag says 'token' while the card number is sent in "
            "Element 2, and the segment length depends on a Field Separator rule ATL105 does not state clearly.",
         accept=["Client supplies a real provisioned token (table 001) with its expiry (table 002).",
                 "BUYPASS confirms which field carries the token when table 004 = Y, and the Segment 153 length rule."]),
    dict(ids={"I-02"}, priority="Medium", title="Follow-on values are instruction text to be filled in from the previous response",
         ba="The follow-on must carry the approval number the host returns for the original. The file holds an instruction "
            "text; the test harness must substitute the real value at run time.",
         accept=["The harness replaces the instruction text with the value from the previous response before sending.",
                 "The Segment 100 length is recalculated after the substitution."]),
    dict(ids={"I-04"}, priority="Medium", title="EMV chip data is generated, not captured from a card or simulator",
         ba="Cryptogram, counters and terminal results in the chip data are generated values. The issuer cannot validate them, "
            "so the test needs chip data from the client's test card or simulator.",
         accept=["Element 190 comes from the client's test card or simulator; the CA Public Key checksum comes from the current "
                 "key load."]),
    dict(ids={"Q-01", "Q-02", "Q-03", "Q-04"}, priority="Low",
         title="ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context",
         ba="Some quotes the generator wrote into the test cases cite the wrong page, give no page, or are not ATL105 text. "
            "They do not change the request, but a reviewer following the citation will not find the rule.",
         accept=["Every spec_basis quote is ATL105 text, word for word, on the cited page and about this transaction."]),
]
GROUP_SETS = {"16-11": SHEET_GROUPS, "20-11": SHEET_GROUPS_2011,
              # the data element / operations parts of the 20-11 run set: same texts, no register of the functional run
              "20-11-parts": [dict(g, register=None, register_by_label={},
                                   title=g["title"].replace("Other negative tests", "Negative tests"))
                              for g in SHEET_GROUPS_2011]}
ACTIVE_GROUPS = ["16-11"]
RUN_CSV = "Defects_in_the_run.csv"
PRIORITY = ["High", "Medium", "Low"]


def sheet_groups(tcs):
    """[(group, label, [(t, f)])]: the active group set (C-10 split per claimed condition), then any other defect id, by
    priority. Group filters: ids, label_has, rules (rule names), value_has."""
    hits = [(t, f) for t in tcs for f in t.findings if f["cat"] not in ("INTENDED", "AI_NA", "INFO", "SCOPE")]
    out, taken = [], set()
    for g in GROUP_SETS[ACTIVE_GROUPS[0]]:
        fs = [(t, f) for t, f in hits if f["defect"] in g["ids"] and g.get("label_has", "") in f["label"]
              and (not g.get("rules") or f["rule"] in g["rules"]) and g.get("value_has", "") in f["value"]
              and id(f) not in taken]
        taken |= {id(f) for _, f in fs}
        if g.get("split"):
            out += [(dict(g, register=g.get("register_by_label", {}).get(lab, g.get("register"))), lab,
                     [x for x in fs if x[1]["label"] == lab]) for lab in tdv.uniq(f["label"] for _, f in fs)]
        elif fs:
            out.append((g, None, fs))
    rest = defaultdict(list)
    for t, f in hits:
        if id(f) not in taken:
            rest[f["defect"]].append((t, f))
    for d, fs in rest.items():
        x = DEFECTS.get(d, {})
        cat = x.get("cat", fs[0][1]["cat"])
        out.append((dict(ids={d}, priority="High" if cat in ("TD", "TC") else "Low" if cat == "CIT" else "Medium",
                         title=x.get("title", fs[0][1]["title"]), ba=None, accept=[x.get("fix", fs[0][1]["fix"])]), None, fs))
    return sorted(out, key=lambda x: PRIORITY.index(x[0]["priority"]))


def group_title(g, label):
    return g["title"].format(label=label) if label else g["title"]


MAX_ROWS = 12


def more_row(tcs, cols, what=""):
    """Table row naming the test cases left out of a long table (each links to its section below)."""
    if not tcs:
        return []
    names = ", ".join(f"[{t.id}](#{t.id.lower()})" for t in tcs)
    return [f"| + {len(tcs)} more{' with ' + what if what else ''}: {names} |" + " |" * (cols - 1)]


def short_name(t):
    return re.sub(r"^BUYPASS ATL105 (MasterCard Credit|Data Element Validation|Terminal & Host Operations) "
                  r"(MERCHANT \d+ (\([^)]*\) |POS (– )?)?)?", "", t.rec.get("name") or "")


def one_per_kind(texts):
    """First text of each kind (quoted values and numbers ignored), so 8 test cases with one problem give one line."""
    seen = {}
    for x in texts:
        seen.setdefault(re.sub(r"\d+(?:, \d+)*", "#", re.sub(r"'[^']*'|`[^`]*`|\"[^\"]*\"", "''", x)), x)
    return list(seen.values())


def spec_line(env, page, text):
    """Line of the ATL105 text file where the quoted text starts on its PDF page."""
    if not hasattr(env, "page_line"):
        env.spec_lines = Path(tcv.DEFAULT_SPEC).read_text(encoding="utf-8").splitlines()
        env.page_line = {int(m.group(1)): i for i, s in enumerate(env.spec_lines) for m in [re.match(r"--- PAGE (\d+) ---", s)] if m}
    start = env.page_line.get(page)
    if start is None:
        return None
    end = min([i for i in env.page_line.values() if i > start], default=len(env.spec_lines))
    head = tcv.squash(" ".join(text.split()[:6]))[:24]
    return next((i + 1 for i in range(start, end) if head and head in tcv.squash(" ".join(env.spec_lines[i:i + 3]))), None)


def spec_ref(env, rid, base):
    sec, page, text = env.refs.catalog[rid]
    ln = spec_line(env, page, text)
    link = "/".join(urlquote(p) for p in os.path.relpath(Path(tcv.DEFAULT_SPEC).resolve(), base).replace("\\", "/").split("/"))
    return esc(ref_line(env.refs, rid)) + (f" ([ATL105 text line {ln}]({link}#L{ln}))" if ln else "")


def group_section(k, g, label, fs, env, lk, base, run_fs=None):
    title, ids = group_title(g, label), {f["defect"] for _, f in fs}
    tcs = tdv.uniq([t for t, _ in fs])
    kinds = tdv.uniq(CATS[f["cat"]][0] for _, f in fs)
    defect = any(f["cat"] in ("TD", "TC") for _, f in fs)
    L = [f'<a id="d{k}"></a>', "", f"## Defect {k}: {esc(title)}", "",
         f"**Priority:** {g['priority']} · **Kind:** {', '.join(kinds)} · **Test cases:** {len(tcs)}"
         + (f" shown ({len(tdv.uniq(t.id for t, _ in run_fs)):,} in the run)" if run_fs else "") + " · **Defect id:** "
         + ", ".join(sorted(ids)) + (f" ({g['register']})" if g.get("register") else ""), ""]
    if run_fs:
        shown = {t.id for t in tcs}
        allt = tdv.uniq([t for t, _ in run_fs])
        cap = 250
        L += [f"<details><summary>All {len(allt):,} test cases of the run with this defect (click to expand)</summary>", "",
              ", ".join(f"[{t.id}](#{t.id.lower()})" if t.id in shown else lk.jsonl(t, t.id) for t in allt[:cap])
              + (f" and {len(allt) - cap:,} more - every one is in [{RUN_CSV}]({urlquote(RUN_CSV)})"
                 if len(allt) > cap else ""), "", "</details>", ""]
    L += ["### TC and TD references", "",
          "| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |", "|---|---|---|---|---|"]
    L += [f"| [{t.id}](#{t.id.lower()}) ({lk.jsonl(t, 'definition')}) | `{t.rec.get('status')}` | {esc(short_name(t))} | "
          f"{esc(t.rec.get('test_objective'))} | " + ", ".join(lk.td(t, n) for n in sorted(t.files)) + " |" for t in tcs[:MAX_ROWS]]
    L += more_row(tcs[MAX_ROWS:], 5)
    L += ["", "### Actual evidence", "", "| Test case | Step | Where | Actual | What it means |", "|---|---|---|---|---|"]
    L += [f"| {t.id} | {f['step'] or '-'} | {finding_where(t, f, lk)} | {finding_actual(t, f)} | {esc(f['title'])} |"
          for t, f in fs[:MAX_ROWS * 2]]
    L += more_row(tdv.uniq([t for t, _ in fs[MAX_ROWS * 2:]]), 5, "the same kind of finding")
    L += ["", "### Expected behavior", ""] + [f"- {x}" for x in one_per_kind(finding_expected(t, f) for t, f in fs)]
    L += ["", "### Why this is a defect" if defect else "### Why it matters", ""]
    L += [f"- {esc(x)}" for x in one_per_kind(f["why"] for _, f in fs)]
    if g.get("ba"):
        L += ["", f"**BA explanation:** \"{esc(g['ba'].format(label=label))}\""]
    L += ["", "### Acceptance criteria", ""] + [f"- {esc(x)}" for x in g["accept"]]
    atl = tdv.uniq(a for _, f in fs for a in finding_atl(env, f, base))
    if atl:
        L += ["", "**Specification reference (ATL105, word for word):**", ""] + [f"- {a}" for a in atl]
    L += [""] + group_excerpt(fs, env, ids)
    return L


def group_excerpt(fs, env, ids):
    """The changed lines of the first affected test data file: actual line, then the expected line(s)."""
    for t, _ in fs:
        for n in sorted(t.files):
            sel = [c for c in expected_td(t, n, env.run)[2] if c[3] & ids]
            if not sel:
                continue
            L = [f"**Test data excerpt - [{t.id}](#{t.id.lower()}), step {n}, `{t.files[n]}` (selected fields, actual then "
                 "expected):**", "", "```jsonc"]
            for line_no, old, new, _ in sel:
                pad = min(len(x) - len(x.lstrip()) for x in new)
                L += ([f"// actual - line {line_no}", old.strip()] if old is not None else ["// actual - not in the request"])
                L += ["// expected"] + [x[pad:] for x in new]
            return L + ["```", ""]
    return []


def finding_actual(t, f):
    if f["rule"] == "X_NEG_PREMISE" and t.files:
        code = t.expected(min(t.files))[0]
        page = re.search(r"p\.(\d+)", f["value"])
        basis = [(i, b) for i, b in enumerate(t.rec.get("spec_basis") or []) if isinstance(b, dict) and page
                 and b.get("page") == int(page.group(1))][:1]
        return (f"`test_objective` = `{esc(f['value'])}`" + (f"; expected Response Code `{code}`" if code else "")
                + "".join(f"; `spec_basis[{i}]` = p.{b['page']}: \"{esc(' '.join(str(b.get('quote')).split()))}\""
                          for i, b in basis))
    if f["field"].startswith("test case"):
        vals = [(p, str(record_value(t, p))) for p in record_path(t, f)]
        return "; ".join(f"`{p}` = " + (f"`{x}`" if len(x) < 80 and "`" not in x else esc(x)[:400]) for p, x in vals)
    if f["rule"] == "X_SYNTHESIZED_CONTENT" and "Network Token" in f["field"]:
        ntd = flat(((t.msgs.get(f["step"]) or {}).get("Network Token Data Request") or {}).get("NetworkTokenData"))
        return (f"`NetworkTokenData` = `{ntd}`: " + "; ".join(f"table {g} {NET_TOKEN_TABLES.get(g, ('?',))[0]} `{x}`"
                                                              for g, _, x in parse_tlv3(ntd)[0])
                + f" - all generated by the test generator (method `{f['value']}`)")
    if f["rule"] == "X_OPTIONAL_FIELD_CONDITION":
        return esc(f["why"].rstrip("."))
    return f"`{esc(f['value'])}`"


def finding_expected(t, f):
    rule, key = f["rule"], re.sub(r"\s*\(.*\)$", "", (f.get("field") or "").split(" -> ")[-1]).strip()
    said = re.search(r"objective says '([^']+)'.* the name says (.+?)\.$", f["why"])
    cite = re.search(r"Cite p\.(\d+) instead of p\.(\d+)", f["fix"])
    if f["cat"] == "INTENDED":
        return (f"Keep it - this is the one rule this negative test breaks on purpose (a valid message would have "
                f"`{esc(f['fix'])}`).")
    if f["cat"] == "INFO":
        return f"{esc(f['fix'])} No change in the test data."
    if f["cat"] == "AI_NA":
        return esc(f.get("na_reason") or f["fix"])
    if rule == "READY_WITH_PLACEHOLDER":
        return "`status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied."
    if rule == "X_OBJECTIVE_MISMATCH" and said:
        return (f"`test_objective` = `{esc(t.rec.get('test_objective', '').replace(said.group(1), said.group(2)))}` - "
                f"{esc(f['fix'])}")
    if rule == "QUOTE_WRONG_PAGE" and cite:
        return (", ".join(f"`{p}.page` = `{cite.group(1)}`" for p in record_path(t, f))
                + f" instead of `{cite.group(2)}` - the quoted text itself is right.")
    if rule == "X_TOR_AFTER_RESPONSE":
        return (f"{esc(f['fix'])} Step {t.of(f['step'])} then expects no response (instead of Response Code "
                f"{esc(str(f['value']).split()[-1])}), and the device sends the Time-out Reversal after the 30-second "
                "response interval.")
    if rule == "RUNTIME_PLACEHOLDER":
        ex = tc_value(t, f["step"], key)
        return (f"At run time the {f.get('label') or key} returned in the step {t.of(f['step'])} response replaces this text"
                + (f" (the test case's own example value is `{ex}`)" if ex else "") + ".")
    if rule == "TERMINAL_PLACEHOLDER":
        return (f"{esc(f['fix'])} Then recalculate the Segment 100 Segment Length of each step: the length minus "
                f"{len(str(f['value']))} (this placeholder) plus the length of the real Terminal Identifier.")
    yymm = re.search(r"MMYY (\d{4}) -> (\d{4})\)", f["fix"]) if rule == "FIELD_12" else None
    if f["defect"] == "D-16" and yymm:
        return (f"`CardDiscretionaryBlockData` = `{yymm.group(2)}` - the expiry as YYMM (the original's MMYY "
                f"`{yymm.group(1)}` with month and year swapped), as every follow-on transaction sends it.")
    if f["defect"] == "D-13" and rule == "FIELD_12":
        return ("`CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + "
                "discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known.")
    if rule == "LINK_ACCOUNTNUMBER" and f["cat"] == "TD":
        return (f"`AccountNumber` = `{esc(f['fix'])}` (the card of step {t.of(f['step'])}) and its expiry as YYMM - the "
                "issuer decline comes from a simulator rule or decline card agreed with BUYPASS, not from another card in "
                "the follow-on only.")
    if rule == "FIELD_102" and f["cat"] == "TD":
        return ("`TerminalIdentifier` = the merchant's own terminal (the other tests of the merchant use the placeholder "
                "`JX41894821001` until BUYPASS assigns the real one) - only the missing original differs.")
    return esc(f["fix"])


def tc_section(t, env, lk, gids, res, man):
    steps = []
    for n in sorted(t.files):
        pc = flat(((t.msgs.get(n) or {}).get("Standard Message Data Segment") or {}).get("PromptCode"))
        code, _, acc, none = t.expected(n)
        exp = "no response" if none else f"`{code}`" if code else "/".join(sorted(acc)) or "not stated"
        steps.append(f"step {n} sends `{pc}` ({tcv.TXN_TYPES.get(pc[:1], '?')}), expected Response Code {exp}")
    L = [f'<a id="{t.id.lower()}"></a>', "", f"### {t.id} ({t.rec['display_id']}) - {esc(t.rec.get('test_objective'))}", "",
         f"**Test name:** {esc(short_name(t))}. **What the test does:** " + "; ".join(steps) + ".", "",
         f"**Status:** `{t.rec.get('status')}` · **AI result:** {t.verdict} - {can_run(t)} · **Test case record:** "
         f"{lk.jsonl(t)} · **Every field against ATL105:** [{RESULT_DIR}/{t.id}.md]({res}{t.id}.md)"
         + (f" · **Step-by-step guide:** [{t.id}-Manual-Validation.md]({man}{t.id}-Manual-Validation.md)" if t.id in gids else ""),
         "", "| Id | Finding | Kind | Actual | Where | Expected |", "|---|---|---|---|---|---|"]
    L += [f"| {f['defect']} | {esc(f['title'])} | {CATS[f['cat']][0]} | {finding_actual(t, f)} | {finding_where(t, f, lk)} | "
          f"{finding_expected(t, f)} |" for f in sorted(t.findings, key=lambda f: CAT_ORDER.index(f["cat"]))]
    for n in sorted(t.files):
        actual, expected, _ = expected_td(t, n, env.run)
        L += ["", f"<details><summary>Test data - actual, step {n}: {t.files[n]} (click to expand)</summary>", "", "```json",
              *actual, "```", "", "</details>", "",
              f"<details open><summary>Test data - expected, step {n}: {t.files[n]} (changed lines end with //)</summary>", "",
              "```jsonc", *expected, "```", "", "</details>"]
    return L + [""]


def sheet_appendix(env, lk, guides, res, man):
    tcs, chk = env.tcs, manifest_check(env)
    L = ['<a id="appendix"></a>', "", "## Appendix - run files checked, documents and how this sheet was produced", ""]
    if chk:
        miss = ", ".join(f"{k} {n:,}" for k, n in chk["missing"].most_common())
        L += [f"The run's `MANIFEST.json` lists {chk['listed']:,} files with their SHA-256; {chk['present']:,} of them are in the "
              "folder" + (f" (not in the folder: {miss} - not needed for this validation)" if miss else "") + ". Files these "
              "test cases use:", "", "| File | SHA-256 | Same as MANIFEST.json |", "|---|---|---|"]
        L += [f"| {lk.file(p, p)} | `{d[:16]}…` | {'yes' if d == m else 'NO'} |" if d else f"| {p} | missing | NO |"
              for p, d, m in chk["rows"]]
        L += ["", f"The other {sum(len(t.files) for t in env.all_tcs) - sum(len(t.files) for t in tcs):,} test data files of the "
              "run were read as well, for the checks across test cases (Sequence Number per terminal, identical requests).", ""]
    L += ["| Document | What it is |", "|---|---|",
          f"| [{RESULT_DIR}/{RESULT_SUMMARY}]({res}{urlquote(RESULT_SUMMARY)}) | Summary: every finding with the ATL105 section, "
          "PDF page, printed page and exact text, how to check it by hand and the fix |"]
    L += [f"| [{RESULT_DIR}/{t.id}.md]({res}{t.id}.md) | {t.rec['display_id']}: every segment and field of the test data "
          "against ATL105, how the data was generated, what is missing, the ATL105 text |" for t in tcs]
    L += [f"| [{MANUAL_DIR}/{t.id}-Manual-Validation.md]({man}{t.id}-Manual-Validation.md) | {t.rec['display_id']}: "
          "step-by-step manual validation guide with the ATL105 extracts |" for t in guides]
    L += ["", f"Produced with (from `core-auth-ai-validation-framework/regression-artifact-validator`): `{env.command}`. The "
          f"validator ran on all {env.total:,} test cases of the run; this sheet covers the test cases above.", ""]
    return L


def record_path(t, f):
    """Field of the test case record a 'test case ...' finding points to."""
    fld, rec = f["field"][len("test case "):], t.rec
    if fld.startswith("spec_basis"):
        head = str(f["value"]).split("…")[0].strip()
        idx = [i for i, b in enumerate(rec.get("spec_basis") or []) if str(b.get("quote") or "").startswith(head)]
        return [f"spec_basis[{i}]" for i in idx] or ["spec_basis"]
    if fld == "device_checkpoints":
        pat = r"\bpump" if f["rule"] == "X_CHECKPOINT_CONTEXT" else re.escape(str(f["value"]).split("…")[0])
        idx = [i for i, c in enumerate(rec.get("device_checkpoints") or []) if re.search(pat, str(c), re.I)]
        return [f"device_checkpoints[{i}]" for i in idx] or [fld]
    m = re.match(r"(tx_legs\[\d+\]\.expect)", fld)
    return [m.group(1)] if m else [fld]


def record_value(t, path):
    m = re.match(r"(spec_basis|device_checkpoints)\[(\d+)\]$", path)
    if m:
        x = t.rec[m.group(1)][int(m.group(2))]
        return f"p.{x.get('page')}: \"{x.get('quote')}\"" if isinstance(x, dict) else str(x)
    m = re.match(r"tx_legs\[(\d+)\]\.expect", path)
    if m:
        leg = t.rec["tx_legs"][int(m.group(1))] or {}
        return (json.dumps(leg.get("expect"), ensure_ascii=False) + f" and `harness` = `{json.dumps(leg.get('harness'))}` "
                "(no fault injection)")
    v = t.rec.get(path)
    return v if isinstance(v, str) else json.dumps(v, ensure_ascii=False)


def tc_value(t, n, key):
    """Value the test case record holds for a test data key in step n (None when it is a placeholder or absent)."""
    rs = next((s for s in t.rsteps if s.get("step", 1) == n), {})
    v = next((x.get("value") for seg in (rs.get("request") or {}).values() if isinstance(seg, dict)
              for k, x in seg.items() if isinstance(x, dict) and norm_key(k) == norm_key(key)), None)
    return None if v in (None, "") or is_ph(v) else str(v)


def finding_where(t, f, lk):
    """Clickable place of a finding: test data file and line with the JSON path, or the test case record field."""
    fld = f.get("field") or ""
    if fld.startswith("test case"):
        return f"test case record {lk.jsonl(t)} → " + ", ".join(f"`{p}`" for p in record_path(t, f))
    m = re.match(r"(TD-\S+?\.json) -> (.+)$", fld)
    every = re.match(r"(.+?) -> (\w+)(?: -> ([^(]+?))? \(every [^)]*\)$", fld)
    if m:
        steps, path = [k for k, v in t.files.items() if v == m.group(1)], m.group(2)
        if f["rule"] == "X_OPTIONAL_FIELD_CONDITION":
            path += " -> " + ", ".join(k for label, k, _ in OPT_FIELDS if f"{label} '" in f["why"])
    elif every:
        seg, key = every.group(1), every.group(2)
        steps = [k for k in sorted(t.files) if flat(((t.msgs.get(k) or {}).get(seg) or {}).get(key)) == str(f["value"])] \
            or [k for k in sorted(t.files) if key in ((t.msgs.get(k) or {}).get(seg) or {})]
        path = f"{seg} -> {key}" + (f" -> {every.group(3)}" if every.group(3) else "")
    else:
        return esc(fld)
    parts = [p.strip() for p in path.split(" -> ")]
    links = []
    for n in steps:
        p = lk.run / "test_data" / t.files[n]
        keys = next((ks for part in reversed(parts) for ks in [[re.sub(r"\s*\(.*\)$", "", k).strip() for k in part.split(",")]]
                     if all(line_of(p, k) for k in ks)), [])
        links += [lk.td(t, n, k, text=f"{t.files[n]} line {line_of(p, k)}") for k in keys] or [lk.td(t, n, text=t.files[n])]
    return ", ".join(links) + " → `" + " → ".join(parts) + "`"


OPT_FIELDS = (("Local Date and Local Time", "LocalDateandLocalTime", "Element 49 is for a preauthorized transaction; this "
               "request is not one"),
              ("Pump/Lane Number", "PumpLaneNumber", "fuel field at a non-petroleum merchant"),
              ("Fuel Purchase Amount", "FuelPurchaseAmount", "fuel field at a non-petroleum merchant"))


def json_block(pairs, ind, head, tail="", note=None):
    """Lines of a JSON object: the head ('{' or '"Name": {') with an optional // note, the "key": "value" pairs (each with an
    optional // note) and the closing brace plus tail."""
    L = [ind + head + (f"  // {note}" if note else "")]
    L += [f"{ind}  {json.dumps(k)}: {json.dumps(v)}" + ("," if j < len(pairs) - 1 else "") + (f"  // {c}" if c else "")
          for j, (k, v, c) in enumerate(pairs)]
    return L + [ind + "}" + tail]


def vi_entry(t, nnn, d):
    """(key/value/comment pairs, exact Segment 111 length increase or None, note) of a new Segment 111 entry for Table nnn."""
    i, planned = planned_table(t, nnn)
    origin = f"the value the test case itself planned (tx_legs[{i}].generated_tables)"
    if nnn == "048":
        pp = str(planned) if planned is not None and re.fullmatch(r"[1-9]", str(planned)) else None
        scope = ("Table 048 is for e-commerce with (EMV) 3-D Secure - confirm this card-not-present transaction is one, or "
                 "re-scope / withdraw it" if card_not_present(t) else
                 "Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it")
        return ([("VariableInformationIndicator", "048", ""),
                 ("VariableInformationLength", "001", "Program Protocol only (037 with the Directory Server Transaction ID)"),
                 ("VariableInformation", pp or "<Program Protocol 1-9>",
                  f"{origin}: {pp} = EMV 3-D Secure Version 2.{pp}" if pp else "1-9 = EMV 3-D Secure Version 2.1-2.9")],
                7, f"{d}: Table 048 MasterCard Authentication Data, as the test claims; {scope}")
    if nnn == "003":
        return ([("VariableInformationIndicator", "003", ""),
                 ("VariableInformationLength", "<length of the AVS data, up to 029>", ""),
                 ("VariableInformation", "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>",
                  "the cardholder's billing address - client test card data (Appendix I)")],
                None, f"{d}: Table 003 AVS Information - the request initiates the AVS result the test expects back")
    if nnn == "004":
        return ([("VariableInformationIndicator", "004", ""), ("VariableInformationLength", "003", "MasterCard CVC2: 3 digits"),
                 ("VariableInformation", "<CVC2 of the test card>", "client test card data")],
                9, f"{d}: Table 004 Card Verification - the request initiates the CVV result the test expects back")
    if planned is not None:
        return ([("VariableInformationIndicator", nnn, ""), ("VariableInformationLength", f"{len(str(planned)):03d}", ""),
                 ("VariableInformation", str(planned), origin)], 6 + len(str(planned)),
                f"{d}: Table {nnn} - the table this test claims to send")
    return ([("VariableInformationIndicator", nnn, ""), ("VariableInformationLength", "<length of the data>", ""),
             ("VariableInformation", f"<Table {nnn} data - Appendix I layout>", "")], None,
            f"{d}: Table {nnn} - the table this test claims to send")


def claim_insert(t, f, lines, msg):
    """What a C-10 / C-11 / I-10 finding adds to the test data: (index to insert after, new lines, [(key, nth, value,
    comment)])."""
    d, label = f["defect"], f["label"]
    s100 = msg.get("Standard Message Data Segment") or {}
    tab = re.search(r"Table (\d{3})", label) or re.search(r"Variable Information Indicator = (\d{3})", label)
    vi = next((i for i, s in enumerate(lines) if '"VariableInformationSection": [' in s), None)
    if tab and vi is not None:
        nnn, ind0 = tab.group(1), lines[vi][:len(lines[vi]) - len(lines[vi].lstrip())]
        last = next(i for i in range(vi + 1, len(lines)) if lines[i].startswith(ind0 + "]")) - 1
        ind = lines[last][:len(lines[last]) - len(lines[last].lstrip())]
        pairs, add, note = vi_entry(t, nnn, d)
        sl = flat((msg.get("Variable Information Data Segment") or {}).get("SegmentLength"))
        nth = sum('"SegmentLength"' in s for s in lines[:vi])
        length = ((f"{int(sl) + add:03d}", f"{d}: {sl} + {add} for the Table {nnn} entry (indicator 3 + length 3 + "
                                           f"{add - 6} character{'s' if add > 7 else ''})")
                  if add and sl.isdigit() else (None, f"{d}: recalculate: {sl} + 6 + length of the Table {nnn} data"))
        return last, json_block(pairs, ind, "{", note=note), [("SegmentLength", nth, *length)]
    if "S12.30-102" in f["refs"]:
        amount = flat(s100.get("NonfuelAmount"))
        gst = f"{round(int(amount) * 5 / 105)}" if amount.isdigit() else "<tax amount>"
        seg102 = [("SegmentType", "102", ""),
                  ("SegmentLength", f"{20 + len(amount) + 1 + len(amount):03d}" if amount.isdigit() else "<recalculate>",
                   "whole segment: type, length, 3 Field Separators and the data with its 2 '\\' separators"),
                  ("ServiceLevel", "O", "O = Other/Fuel not present (Element 87)"), ("NumberofProducts", "01", ""),
                  ("ProductCode", "400", "400 = General merchandise (Appendix F) - client to confirm"),
                  ("UnitofMeasure", "U", "U = Unit (Element 106)"), ("Quantity", "01", "1 (leading digit = decimal places)"),
                  ("UnitPrice", "2" + amount if amount.isdigit() else "<unit price>", "leading 2 = two decimal places"),
                  ("ProductAmount", amount or "<amount>", "= Nonfuel Amount")]
        seg143 = [("SegmentType", "143", ""),
                  ("SegmentLength", f"{18 + len(gst):03d}" if gst.isdigit() else "<recalculate>",
                   "counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with "
                   "BUYPASS"),
                  ("NumberofProducts", "01", "same products, same order as Segment 102"), ("ProductCode", "400", ""),
                  ("InclusiveExclusiveforTax1", "I", "I = tax included in the amount"), ("TaxType1", "GST", "the claimed value"),
                  ("TaxAmount1", gst, "example: 5% GST included in the amount - client to supply")]
        ind = "    "
        block = (json_block(seg102, ind, '"Product Code Data Segment": {', ",", f"{d}: Segment 143 needs Segment 102")
                 + json_block(seg143, ind, '"Tax by Product Data Segment": {', "", f"{d}: Tax Type 1 = GST, as the test claims"))
        segs = flat(msg.get("NumberofSegments"))
        return len(lines) - 3, block, [("NumberofSegments", 1, f"{int(segs) + 2:02d}" if segs.isdigit() else None,
                                        f"{d}: + Segments 102 and 143")]
    return None, [], [("NumberofSegments", 1, None, f"{d}: add {label} - the test claims it")]


def expected_td(t, n, run):
    """(actual lines, expected lines, changes) of step n's test data. Every changed or added line carries a // comment;
    changes = [(actual line number, actual line or None if added, expected lines, defect ids)]."""
    path = run / "test_data" / t.files[n]
    lines = path.read_text(encoding="utf-8").splitlines()
    msg = t.msgs.get(n) or {}
    edits, added = defaultdict(lambda: [None, []]), defaultdict(list)

    def mark(key, comment, value=None, nth=1):
        ln = line_of(path, key, nth)
        if ln:
            edits[ln - 1][1].append(comment)
            if value is not None:
                edits[ln - 1][0] = value
    s100 = msg.get("Standard Message Data Segment") or {}
    s100_new, len_ids, tid_ph = {}, [], False
    holes = {}  # Segment 100 keys whose expected value is a placeholder: key -> what the real value is
    link_acct = any(f["rule"] == "LINK_ACCOUNTNUMBER" and f["step"] == n and f["cat"] == "TD" for f in t.findings)
    for f in t.findings:
        rule, d = f["rule"], f["defect"]
        key = re.sub(r"\s*\(.*\)$", "", (f.get("field") or "").split(" -> ")[-1]).strip()
        if rule == "TERMINAL_PLACEHOLDER" and flat(s100.get("TerminalIdentifier")) == str(f["value"]):
            mark("TerminalIdentifier", f"{d}: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal",
                 "<Terminal Identifier assigned by BUYPASS>")
            tid_ph = True
            len_ids.append(d)
        if rule == "X_NEG_PREMISE" and n == min(t.files) and t.roots.get(n):
            mark(t.roots[n], f"{d}: no change to the request - what must change is the test (its premise and the expected "
                             "decline); see the finding")
        if f["step"] != n:
            continue
        yymm = re.search(r"-> (\d{4})\)", f["fix"]) if rule == "FIELD_12" else None
        if d == "D-16" and yymm:
            o = re.search(r"MMYY (\d{4})", f["fix"])
            mark("CardDiscretionaryBlockData", f"{d}: expiry as YYMM - the original's MMYY {o.group(1) if o else ''} with month "
                 "and year swapped (follow-on transaction, Section 10.1.8.2)", yymm.group(1))
            s100_new["CardDiscretionaryBlockData"] = yymm.group(1)
        elif d == "D-13" and rule == "FIELD_12":
            v = flat(s100.get("CardDiscretionaryBlockData"))
            yy = (v[2:] + v[:2]) if re.fullmatch(r"\d{4}", v) else "YYMM"
            mark("CardDiscretionaryBlockData", f"{d}: the card was read (Table 005) - send the track data after the '=': expiry "
                 f"{yy} (YYMM), service code, discretionary data - from the client's test card",
                 f"<Track 2 data after '=': {yy} + service code + discretionary data>")
            holes["CardDiscretionaryBlockData"] = "the track data"
            len_ids.append(d)
        elif rule == "LINK_ACCOUNTNUMBER" and f["cat"] == "TD":
            mark("AccountNumber", f"{d}: the card of step {t.of(n)} - a follow-on carries the card of its original", f["fix"])
            s100_new["AccountNumber"] = f["fix"]
            o = flat(((t.msgs.get(t.of(n)) or {}).get("Standard Message Data Segment") or {}).get("CardDiscretionaryBlockData"))
            if re.fullmatch(r"\d{4,}", o) and is_ph(s100.get("CardDiscretionaryBlockData")):
                yy = o[2:] + o[:2] if len(o) == 4 else o[:4]
                mark("CardDiscretionaryBlockData", f"{d}: the expiry of step {t.of(n)} as YYMM", yy)
                s100_new["CardDiscretionaryBlockData"] = yy
            len_ids.append(d)
        elif rule == "FIELD_102" and f["cat"] == "TD":
            mark("TerminalIdentifier", f"{d}: the merchant's own terminal, as in its other tests (placeholder JX41894821001 "
                 "until BUYPASS assigns it) - only the missing original may be wrong",
                 "<Terminal Identifier assigned by BUYPASS>")
            tid_ph = True
            len_ids.append(d)
        elif rule == "X_TABLE_SCOPE":
            i048 = next((i for i, s in enumerate(lines) if '"VariableInformationIndicator": "048"' in s), None)
            if i048 is not None:
                edits[i048][1].append(f"{d}: Table 048 is for e-commerce 3-D Secure transactions - confirm with BUYPASS that "
                                      "it is accepted on this POS transaction, or re-scope / withdraw the test")
        elif rule == "X_AWAITING_VALUE" and link_acct and key in ("AccountNumber", "CardDiscretionaryBlockData"):
            pass
        elif rule == "X_NEG_RANDOM_TERMINAL":
            mark("TerminalIdentifier", f"{d}: a second real terminal of the same merchant (still different from step "
                 f"{t.of(n)})", "<second Terminal Identifier of the same merchant, assigned by BUYPASS>")
            tid_ph = True
            len_ids.append(d)
        elif f["cat"] == "INTENDED":
            mark(key, f"{d}: different from step {t.of(n)} ('{f['fix']}') on purpose - the rule this negative test breaks; keep")
        elif rule == "RUNTIME_PLACEHOLDER":
            ex = tc_value(t, n, key)
            mark(key, f"{d}: filled in at run time from the step {t.of(n)} response"
                 + (f" - '{ex}' is the test case's example" if ex else ""), ex)
            if ex and key in s100:
                s100_new[key] = ex
        elif rule == "FIELD_5" and str(f["fix"]).startswith("(empty)"):
            mark("ApprovalNumber", f"{d}: empty - an original Authorization Only carries no Approval Number", "")
            s100_new["ApprovalNumber"] = ""
            len_ids.append(d)
        elif rule == "X_PIN_BLOCK_FORMAT":
            mark("EncryptedPINBlockData", f"{d}: empty - '{f['value']}' is not a DUKPT KSN + PIN block; no online PIN on this "
                                          "credit request", "")
            s100_new["EncryptedPINBlockData"] = ""
            len_ids.append(d)
        elif rule == "X_OPTIONAL_FIELD_CONDITION":
            for label, k, why in OPT_FIELDS:
                if f"{label} '" in f["why"] and flat(s100.get(k)):
                    mark(k, f"{d}: empty - {why}", "")
                    s100_new[k] = ""
            len_ids.append(d)
        elif rule in ("X_CONDITION_NOT_SENT", "X_CONDITION_AWAITED", "X_RESPONSE_NOT_INITIATED"):
            after, block, marks = claim_insert(t, f, lines, msg)
            if block:
                added[after].append((block, {d}))
            for k, nth, value, comment in marks:
                mark(k, comment, value, nth)
        elif rule == "FOLLOW_ON_EQUALS_SEPARATOR":
            mark("CardDiscretionaryBlockData", f"{d}: the wire message sends AccountNumber '=' this value: "
                 f"{flat(s100.get('AccountNumber'))}={flat(s100.get('CardDiscretionaryBlockData'))}")
        elif rule == "X_SYNTHESIZED_CONTENT" and "Network Token" in f["field"]:
            mark("NetworkTokenData", f"{d}: real provisioned network token from the client (nnn = its length, up to 019)",
                 "001nnn<network token>002004<token expiry YYMM>004001<Y or N>")
        elif rule == "X_TOKEN_INDICATOR":
            mark("NetworkTokenData", f"{d}: table 004 'N' (Clear PAN) if Element 2 keeps the card number - confirm with BUYPASS")
        elif rule == "X_SEGMENT_LENGTH_UNCLEAR":
            alts = ", ".join(re.findall(r": (\d{3})", f["why"]))
            mark(key, f"{d}: {f['value']} = a Field Separator after every field; other readings of ATL105 give {alts} - "
                      "confirm with BUYPASS")
        elif f["cat"] in ("TD", "TC", "INPUT", "SME") and key:
            mark(key, f"{d}: {esc(f['fix'])[:140]}")
    if s100 and len_ids:
        values = tdv.td_seg100_values(s100)
        values.update(s100_new)
        ids = "/".join(tdv.uniq(len_ids))
        if tid_ph:
            holes["TerminalIdentifier"] = "the real Terminal Identifier"
        if holes:
            base = tdv.seg100_length(values) - sum(len(values.get(k, "")) for k in holes)
            mark("SegmentLength", f"{ids}: recalculate: {base:03d} + length of " + " + length of ".join(holes.values()))
        else:
            mark("SegmentLength", f"{ids}: recalculated after the changes above", f"{tdv.seg100_length(values):03d}")
    out, changes = [], []
    for i, s in enumerate(lines):
        old = s
        if i in edits:
            value, notes = edits[i]
            if value is not None:
                s = re.sub(r'(:\s*)"(?:[^"\\]|\\.)*"', lambda m: m.group(1) + json.dumps(value), s, count=1)
        if added.get(i) and not s.rstrip().endswith(","):
            s += ","
        if i in edits:
            s += "  // " + "; ".join(tdv.uniq(edits[i][1]))
            changes.append((i + 1, old, [s], {d for x in edits[i][1] for d in x.split(":")[0].split("/")}))
        out.append(s)
        for j, (block, ids) in enumerate(added.get(i, [])):
            block = block[:-1] + [block[-1] + ("," if j < len(added[i]) - 1 and not block[-1].endswith(",") else "")]
            out += block
            changes.append((i + 1, None, block, ids))
    return lines, out, changes


def finding_atl(env, f, base):
    """ATL105 lines behind a finding (section, PDF page, printed page, exact text, link to the text line)."""
    refs = env.refs
    rids = tdv.uniq(list(f["refs"]) + (["S12.38-TLV"] if f["rule"] == "X_BASIS_CONTEXT" else [])
                    + [r for r, (_, _, txt) in refs.catalog.items() if len(txt) > 40 and txt in (f["fix"] or "")])
    atl = [spec_ref(env, r, base) for r in rids if r in refs.catalog]
    m = re.search(r"\(((?:p\.\d+(?:, )?)+)\)\.?$", f["why"]) if f["rule"] == "QUOTE_WRONG_PAGE" else None
    if m:
        q = re.search(r'quotes "(.+?)" for p\.', f["why"])
        atl.append(esc((f'"{q.group(1)}" is on ' if q else "The quoted text is on ")
                       + ", ".join(f"PDF p.{p} (printed {env.spec.printed(int(p))})" for p in re.findall(r"\d+", m.group(1)))))
    if f["rule"] == "X_CHECKPOINT_CONTEXT":
        atl.append("Not ATL105 - these checkpoints quote the BUYPASS Petroleum Industry Processing Specification (PIPS).")
    return atl


def write_run_csv(path, env):
    """Every defect finding of the whole run (one row per finding), grouped as in the defect sheet."""
    import csv
    with open(path, "w", newline="", encoding="utf-8-sig") as fh:
        w = csv.writer(fh)
        w.writerow(["Defect group", "Defect id", "Kind", "Test case", "Display id", "Status", "AI result", "Step", "Rule",
                    "Finding", "Where", "Actual", "Expected / fix"])
        for g, label, fs in sheet_groups(env.tcs):
            for t, f in fs:
                w.writerow([group_title(g, label), f["defect"], CATS[f["cat"]][0], t.id, t.rec.get("display_id"),
                            t.rec.get("status"), t.verdict, f["step"] or "", f["rule"], f["title"], f["field"],
                            f["value"][:300], f["fix"][:300]])


def write_package(pkg, env, ids, command, guide_ids=None, sheet=None, title=None, scope=None, intro=None, outro=None):
    """Result_MD_Folder (ATL105 result per test case + summary), 'Manual Validation' (guides) and a defect sheet, in <pkg>."""
    res, man = pkg / RESULT_DIR, pkg / MANUAL_DIR
    guide_ids = [i for i in (guide_ids or ids) if i in ids]
    overview = pkg / (sheet or f"ATL105_Validation_{len(ids)}_Test_Cases.md")
    paths = ([overview, res / RESULT_SUMMARY, man / "README.md"] + [res / f"{i}.md" for i in ids]
             + [man / f"{i}-Manual-Validation.md" for i in guide_ids])
    too_long = [str(p) for p in paths if len(str(p)) > MAX_PATH]
    if too_long:
        sys.exit(f"Output path too long for Windows: {too_long}")
    by_id = {t.id: t for t in env.tcs}
    view = Env()
    view.__dict__.update(env.__dict__)
    view.tcs, view.all_tcs, view.total, view.command = [by_id[i] for i in ids], env.tcs, len(env.tcs), command
    man.mkdir(parents=True, exist_ok=True)
    res.mkdir(parents=True, exist_ok=True)
    lk = Links(man, env)
    guides = write_guides(man, env, lk, guide_ids)
    write_tc_docs(man, view, set(guide_ids), root=res, flat=True, title="ATL105 validation of the test case and its test data")
    write_summary(res, view, Links(res, env), guide_ids, file_name=RESULT_SUMMARY, docs="",
                  guide_dir=f"../{urlquote(MANUAL_DIR)}/", sheet=False)
    write_overview(overview, view, Links(pkg, env), guides, title, scope, intro, outro)
    write_run_csv(pkg / RUN_CSV, env)
    write_package_readme(man, view, lk, guides)
    return view, guides, overview


def main():
    sys.stdout.reconfigure(encoding="utf-8")
    ap = argparse.ArgumentParser(description=__doc__.splitlines()[0])
    ap.add_argument("--run", default=str(DEFAULT_RUN), help="run folder with test_cases/test_cases.jsonl and test_data/")
    ap.add_argument("--out", default=str(DEFAULT_OUT))
    ap.add_argument("--guides", help="comma-separated test case ids for manual guides (default: the 5 TS5 test cases; with "
                                     "--package: all --only test cases)")
    ap.add_argument("--no-tc-docs", action="store_true", help="skip the one-document-per-test-case folder")
    ap.add_argument("--only", help="comma-separated test case ids to write a --package for (the whole run is still validated)")
    ap.add_argument("--package", help="folder for the --only test cases: Result_MD_Folder, 'Manual Validation' and a defect sheet")
    ap.add_argument("--sheet", help="file name of the package's defect sheet (default ATL105_Validation_<n>_Test_Cases.md)")
    ap.add_argument("--title", help="title of the defect sheet")
    ap.add_argument("--scope", help="purpose-and-scope paragraph of the defect sheet")
    ap.add_argument("--groups", choices=sorted(GROUP_SETS), default="16-11",
                    help="BA group texts of the defect sheet (registers of the run the sheet is for)")
    ap.add_argument("--intro", help="markdown file inserted in the defect sheet before the defect table")
    ap.add_argument("--outro", help="markdown file inserted in the defect sheet before the appendix")
    a = ap.parse_args()
    ACTIVE_GROUPS[0] = a.groups
    intro = Path(a.intro).read_text(encoding="utf-8") if a.intro else None
    outro = Path(a.outro).read_text(encoding="utf-8") if a.outro else None
    run_dir, out = Path(a.run).resolve(), Path(a.out).resolve()
    gids = [i.strip() for i in a.guides.split(",") if i.strip()] if a.guides else None
    if a.package:
        only = [i.strip() for i in (a.only or "").split(",") if i.strip()]
        if not only:
            sys.exit("--package needs --only")
        env = validate_run(run_dir)
        unknown = [i for i in only + (gids or []) if i not in {t.id for t in env.tcs}]
        if unknown:
            sys.exit(f"Not in the run: {unknown}")
        command = " ".join(f'"{x}"' if " " in x else x for x in ["python", "scripts/" + Path(sys.argv[0]).name] + sys.argv[1:])
        view, guides, sheet = write_package(Path(a.package).resolve(), env, only, command, gids, a.sheet, a.title, a.scope,
                                            intro, outro)
        print(f"Run: {len(env.tcs)} test cases validated; package for {len(view.tcs)} ({len(guides)} guides):")
        for t in view.tcs:
            print(f"  {t.id} {t.rec['display_id']:12} {t.verdict:8} " + ", ".join(f"{f['rule']}({f['cat']})" for f in t.findings))
        unverified = {k: s for k, s in env.refs.status.items() if s != "verified"}
        print(f"ATL105 references used: {len(env.refs.used)}; not verified: {unverified or 'none'}")
        print(f"Written: {sheet}")
        return
    ids = gids or DEFAULT_GUIDES
    for name in [SUMMARY, SHEET, "README.md"] + [f"{i}-Manual-Validation.md" for i in ids]:
        if len(str(out / name)) > MAX_PATH:
            sys.exit(f"Output path too long for Windows: {out / name}")
    env = validate_run(run_dir)
    out.mkdir(parents=True, exist_ok=True)
    lk = Links(out, env)
    guides = write_guides(out, env, lk, ids)
    docs = 0 if a.no_tc_docs else write_tc_docs(out, env, set(ids))
    write_summary(out, env, lk, ids)
    write_sheet(out, env)
    write_readme(out, env, lk, guides)
    v = Counter(t.verdict for t in env.tcs)
    print(f"{len(env.tcs)} test cases, {sum(len(t.files) for t in env.tcs)} test data files: "
          + ", ".join(f"{k} {v[k]}" for k in ("FAIL", "BLOCKED", "REVIEW", "PASS", "DEVICE", "NOT CHECKED")))
    for c in CAT_ORDER:
        fs = [(t, f) for t in env.tcs for f in t.findings if f["cat"] == c]
        if fs:
            print(f"  {CATS[c][0]:52} {len(fs):6} findings {len({t.id for t, _ in fs}):5} TCs  "
                  + ", ".join(f"{r}:{n}" for r, n in Counter(f["rule"] for _, f in fs).most_common(8)))
    for t in guides:
        print(f"  guide {t.id} {t.rec['display_id']:12} {t.verdict:8} " + ", ".join(f"{f['rule']}({f['cat']})" for f in t.findings))
    unverified = {k: s for k, s in env.refs.status.items() if s != "verified"}
    print(f"Per-test-case documents: {docs:,} in {out / TC_DOCS}")
    print(f"ATL105 references used: {len(env.refs.used)}; not verified: {unverified or 'none'}")
    print(f"Written: {out}")


if __name__ == "__main__":
    main()
