#!/usr/bin/env python3
"""Validate wire-shaped test data (test_data/TD-TC-*.json) field by field against the BUYPASS ATL105 spec.

Reuses the trained spec loader / quote checker in validate-test-cases-against-atl105-spec.py and the
Chapter 13 element reference. Each finding is explained in plain language with the ATL105 section, PDF page,
printed page and exact spec text; every cited text is re-verified against the spec text on each run.
Writes a report per test case, a summary and a one-page key-findings report into --out.
"""

import argparse
import importlib.util
import json
import re
from collections import Counter
from datetime import date
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
_spec = importlib.util.spec_from_file_location("tcv", ROOT / "scripts" / "validate-test-cases-against-atl105-spec.py")
tcv = importlib.util.module_from_spec(_spec)
_spec.loader.exec_module(tcv)

SPEC_TITLE = "BUYPASS Platform ATL105 Message Format Specifications"
S2_3_1 = "Section 2.3.1 Time-out by Customer's Host/Controller/POS System"
S10_1_1_3 = "Section 10.1.1.3 Manual Entry"
S10_1_2_1 = "Section 10.1.2.1 Partial Approval Transaction"
S12_1 = "Section 12.1 Standard Message Data Segment"
S12_10 = "Section 12.10 Variable Information Data Segment"
APP_I_031 = "Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031)"
APP_J = "Appendix J. Valid Point-of-Service Entry Mode Codes"
S11_8_1 = "Section 11.8.1 EMV Financial Transaction Request"
S12_20 = "Section 12.20 EMV Request Data Segment"
S10_14_3 = "Section 10.14.3 EMV PIN (Personal Identification Number) Processing"
S10_14_4 = "Section 10.14.4 EMV Fallback Transaction Processing"
EL12_NAME = "Element 12 Card Discretionary Block Data (Chapter 13)"
EL190_NAME = "Element 190 EMV Chip Data (Chapter 13)"
APP_R = "Appendix R. EMV Chip Data Example"
APP_T = "Appendix T. EMV Additional Information Data Layouts - EMV Table Data"
APP_G = "Appendix G. Valid Transaction Type Codes"
APP_I_032 = "Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08"
APP_Q = "Appendix Q. Valid National Point-of-Service Condition Codes"
S2_3 = "Section 2.3 Unsuccessful Financial Transactions"

# id -> (section, PDF page, exact spec text); each text is re-verified on its page at run time
REFS = {
    "S2.3.1-SEQ": (S2_3_1, 40, "Each time any financial transaction request is forwarded to BUYPASS, the financial transaction "
                               "should be assigned a new Sequence Number"),
    "S2.3.1-MATCH": (S2_3_1, 40, "The Sequence Number, Account Number, and dollar amount—assigned by the customer's "
                                 "host/controller/POS system—are the elements used to match a request with its respective response."),
    "S2.3.1-DEVICE": (S2_3_1, 40, "A TOR must be sent from the same device as the original financial transaction request that "
                                  "resulted in the TOR."),
    "S10.1.1.3": (S10_1_1_3, 81, "If unable to read the magnetic strip after three attempts, the device should prompt for manual "
                                 "entry of the Account Number and Expiration Date (Format: MMYY)."),
    "S10.1.1.3-STAR": (S10_1_1_3, 81, "If manual entry is used, this fact is indicated on the receipt and journal by an asterisk "
                                      "(*) before the Account Number."),
    "S10.1.2.1-F": (S10_1_2_1, 84, "This information is returned when a Partial Approval Indicator value of 1 is sent in Partial "
                                   "Approval Indicator (Element No. 121)."),
    "S10.1.2.1-REMAIN": (S10_1_2_1, 84, "Approved Amount (Element No. 6) contains the approved partial amount, and the POS device "
                                        "must prompt for payment of the remaining balance due on the sale."),
    "S10.1.2.1-REV": (S10_1_2_1, 84, "The amount in a Reversal (Void) request must be the amount approved in the original "
                                     "transaction response when a transaction has been partially approved."),
    "S10.1.2.1-SEQ": (S10_1_2_1, 84, "The Sequence Number must be the Sequence Number contained in the original transaction request."),
    "S10.1.2.2": ("Section 10.1.2.2 Authorization Only Reversal Transaction", 85,
                  "Element No. 5 (Approval Number)—present in the original transaction—must be present and identical in the "
                  "Authorization Only Reversal transaction."),
    "S10.1.5": ("Section 10.1.5 Radio Frequency Identification Service", 86,
                "Even if the transaction is swiped, manually keyed, or uses a card type for which RFID is not applicable, "
                "Point-of-Sale Entry Mode must be populated correctly in the transaction request from the point-of-sale when an "
                "RFID receiver is present."),
    "S10.1.6.1.3": ("Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service)", 87,
                    "A Split Tender transaction results when a Partial Approval is returned to the POS in the transaction response."),
    "S10.1.8.2": ("Section 10.1.8.2 Track Support Requirements", 94,
                  "The host message should only contain the Account Number, followed by an account delimiter (=), followed by "
                  "the 4 digit Expiration Date (Storage format – YYMM)."),
    "S11.1-DS1": ("Section 11.1 Financial Transactions (Data Section No. 1)", 167,
                  "All Financial Transaction Requests contain Element Nos. 55 and 63."),
    "S12.1-REQ": (S12_1, 223, "It is the only required data segment on all Financial Transaction Request messages."),
    "S12.1-TYPE": (S12_1, 223, "Fixed value: 100"),
    "S12.1-LEN": (S12_1, 223, "Identifies the data segment's length, including Segment Type's length and Field Separators."),
    "S12.1-FS": (S12_1, 223, "When a field is not populated, still send the Field Separator."),
    "S12.10-TYPE": (S12_10, 246, "Fixed value: 111"),
    "S12.10-FS": (S12_10, 246, "A Field Separator follows the final Variable Information Section."),
    "S12.10-LEN": (S12_10, 246, "Identifies the length of Variable Information (Element No. 113)."),
    "EL2": ("Element 2 Account Number (Chapter 13)", 364, "a variable length (up to 24 alphanumeric bytes) Account Number"),
    "EL5-FMT": ("Element 5 Approval Number (Chapter 13)", 368, "Fixed length of six alphanumeric characters; space-filled"),
    "EL5-REV": ("Element 5 Approval Number (Chapter 13)", 368, "Required on reversals for matching information to the original purchase."),
    "EL5-PRE": ("Element 5 Approval Number (Chapter 13)", 368, "If it is included in a Purchase Request, then the Purchase Request is "
                                                               "considered preauthorized, and BUYPASS does not go out for authorization."),
    "EL6": ("Element 6 Approved Amount (Chapter 13)", 368,
            "Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned"),
    "EL12": ("Element 12 Card Discretionary Block Data (Chapter 13)", 372,
             "For a card whose number has been manually entered, the expiration date is in MMYY format"),
    "EL44": ("Element 44 Information Byte (Chapter 13)", 386, "0 Single-message transmission"),
    "EL55": ("Element 55 Message Format Version Identifier (Chapter 13)", 391, "Fixed length of six alphanumeric characters"),
    "EL58": ("Element 58 Nonfuel Amount (Chapter 13)", 393, "A maximum length of 7 bytes is allowed for all other card types."),
    "EL63": ("Element 63 Number of Segments (Chapter 13)", 395, "Variable length of up to two digits"),
    "EL78": ("Element 78 Prompt Code (Chapter 13)", 400, "this is a 4-character Prompt Code that identifies the transaction's "
                                                         "Transaction Type (one character) and Card Type (three characters)."),
    "EL83-0": ("Element 83 Response Code (Chapter 13)", 404, "0 Approved—Purchase/Capture"),
    "EL83-F": ("Element 83 Response Code (Chapter 13)", 404, "F Approved—Partial approval"),
    "EL84": ("Element 84 Segment Length (Chapter 13)", 406, "This figure is calculated using the lengths of all data elements in "
                                                            "the segment plus all Field Separators/Product Data Field Delimiters "
                                                            "related to those elements."),
    "EL86-FMT": ("Element 86 Sequence Number (Chapter 13)", 409, "Valid Codes/Values: 000001–999999"),
    "EL86-UNIQUE": ("Element 86 Sequence Number (Chapter 13)", 409, "Each transaction has a unique Sequence Number."),
    "EL86-REV": ("Element 86 Sequence Number (Chapter 13)", 409, "In the case of a purchase reversal/void or time-out reversal "
                                                                 "transaction, the Sequence Number must be the same as in the original "
                                                                 "transaction request."),
    "EL102": ("Element 102 Terminal Identifier (Chapter 13)", 417, "The Terminal Identifier must be assigned by and set up at BUYPASS."),
    "EL102-STATE": ("Element 102 Terminal Identifier (Chapter 13)", 417,
                    "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."),
    "EL111": ("Element 111 Variable Information Indicator (Chapter 13)", 422, "Fixed length of 3 alphanumeric characters"),
    "EL121": ("Element 121 Partial Approval Indicator (Chapter 13)", 440,
              "This value indicates that the Merchant supports credit/debit/fleet Partial Approval transactions."),
    "EL121-LESS": ("Element 121 Partial Approval Indicator (Chapter 13)", 440,
                   "the network can approve an amount that is less than the amount in the Authorization request"),
    "APPC-03": ("Appendix C. Valid Authorizer Codes", 511, "03 MasterCard® Credit"),
    "APPE-020": ("Appendix E. Valid Card Type Codes", 521, "020 Credit Credit"),
    "APPG-0": ("Appendix G. Valid Transaction Type Codes", 545,
               "0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion"),
    "APPG-8": ("Appendix G. Valid Transaction Type Codes", 546, "8 Purchase reversal Purchase reversal/void"),
    "APPI-005": ("Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005)", 555,
                 "Point-of-Service Entry Mode Information"),
    "APPI-031": (APP_I_031, 569, "Terminal Capability values must be populated for all transactions"),
    "APPI-031-STABLE": (APP_I_031, 569, "Once certified, the respective terminal capability values do not change regardless of "
                                        "the POS Entry Mode."),
    "APPI-031-07": (APP_I_031, 569, "07 = Contactless magnetic stripe read capability (no contact or contactless chip capability)"),
    "APPI-031-12": (APP_I_031, 569, "12 = Hybrid Integrated Circuit Card Reader (Contact Chip & Contactless Chip) Capabilities"),
    "APPJ-01": (APP_J, 638, "01 Manual/keyed"),
    "APPJ-PERSIST": (APP_J, 638, "These transactions will use the same PAN Entry Mode code as their corresponding original "
                                 "Purchase or Authorization transaction"),
    "APPJ-PIN1": (APP_J, 639, "1 PIN entry capability (including both EMV Contact and Contactless)"),
    "APPJ-PIN8": (APP_J, 639, "8 Contactless Magnetic Stripe Read Capability"),
    "APPJ-NOTE1": (APP_J, 639, "If a Contactless MSR is attached to the POS device and it has no chip capability, then all "
                               "transactions will use Terminal PIN Entry Capability Mode code 8"),
    "APPJ-EMV1": (APP_J, 639, "For EMV Contact and Contactless the valid PIN Entry Capability Mode code is 1 and not 8."),
    "APPJ-02": (APP_J, 638, "02 Magnetic stripe read with partial track data"),
    "APPJ-05": (APP_J, 638, "05 Integrated circuit card (ICC) read – CVV data reliable (used for EMV chip read)"),
    "APPJ-07": (APP_J, 638, "07 Contactless chip read (used for contactless chip)"),
    "APPJ-90": (APP_J, 638, "90 Magnetic stripe read with full track data"),
    "APPJ-FOOT1": (APP_J, 638, "They will use a PAN Entry Mode code 02 when referencing an original Purchase or Authorization "
                               "transaction in which a PAN Entry Mode code 90 was used."),
    "APPI-006": ("Appendix I. Variable Information Data Layouts - Currency Code Information (Table 006)", 555,
                 "Identifies the currency of the country in which the transaction originated. [Default value: 840 (United States)]"),
    "EL83-1": ("Element 83 Response Code (Chapter 13)", 404, "1 Approved—Communications Test Declined—All other transactions"),
    "EL12-TRACK2": (EL12_NAME, 371, "Account Number is considered the data before the = character, and Card Discretionary Block "
                                    "Data is considered the data after the = character."),
    "EL12-TRACK1": (EL12_NAME, 371, "Account Number is considered the data before the first ^ character, and Card Discretionary "
                                    "Block Data is considered the data after the first ^ character."),
    "EL12-MAX": (EL12_NAME, 371, "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 "
                                 "or 37 characters for Track 2."),
    "EL12-EXP": (EL12_NAME, 372, "Note: For all authorizations, merchants must send a value even for expired cards."),
    "S10.1.1.2": ("Section 10.1.1.2 Track Support", 81, "If the device cannot read Track 2, then the device should send unaltered "
                                                          "Track 1 before attempting manual entry."),
    "S10.14.2.1": ("Section 10.14.2.1 Expiration Date (EMV card requirements)", 160,
                   "The device does not perform expiration date validation in an EMV card transaction."),
    "S10.14.2.4-REV": ("Section 10.14.2.4 Supported Transaction Types (EMV)", 161, "EMV data is not required on Reversal transactions."),
    "S10.14.3-MC": (S10_14_3, 162, "MasterCard has mandated that Point-of-Sale devices that support EMV PIN must support both online "
                                   "PIN and offline PIN processing."),
    "S10.14.3-ONLINE": (S10_14_3, 162, "Online PIN: This is applicable for credit or debit transactions, in which the PIN is "
                                       "electronically sent to and validated by the card issuer."),
    "S10.14.3-OPT": (S10_14_3, 162, "In a credit/signature debit transaction, a PIN of all F’s is NOT required since PINs are an "
                                    "optional field for a credit/signature debit transaction."),
    "S10.14.4-80": (S10_14_4, 162, "The device is EMV enabled and • The card is chip and • The card is swiped Always send the "
                                   "Fallback Entry Mode of ‘80’or spec equivalent"),
    "S10.14.4-90": (S10_14_4, 162, "The card is NOT a chip card or • The device is not chip capable and • The card is swiped Send "
                                   "as a MSR Entry Mode of ‘90’or spec equivalent"),
    "EL33-FMT": ("Element 33 Encrypted PIN Block Data (Chapter 13)", 382,
                 "For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."),
    "EL33-REQ": ("Element 33 Encrypted PIN Block Data (Chapter 13)", 382, "Required for debit and EBT."),
    "S11.8-EMV": (S11_8_1, 205, "The following table illustrates the layout of an EMV Financial Transaction Request:"),
    "S11.8-130": (S11_8_1, 206, "This segment is the only segment required for all EMV financial transactions."),
    "S12.20-ALWAYS": (S12_20, 270, "It always appears in Data Section No. 3 of an EMV Financial Transaction Request."),
    "S12.20-MAX": (S12_20, 270, "It has a maximum length of 3043 alphanumeric characters"),
    "S12.20-TYPE": (S12_20, 270, "Fixed value: 130"),
    "S12.20-LEN": (S12_20, 270, "Identifies the data segment's length, including Segment Type's length and Field Separators."),
    "S12.20-FS": (S12_20, 270, "A Field Separator follows the final EMV Additional Information Section."),
    "S12.20-ADDL": (S12_20, 271, "This section is repeated – by EMV Additional Information Indicator – for a maximum length of "
                                 "2,000 bytes."),
    "EL187": ("Element 187 CA Public Key File Checksum (Chapter 13)", 476, "Fixed length of 25 bytes"),
    "EL187-REQ": ("Element 187 CA Public Key File Checksum (Chapter 13)", 476, "Required in the EMV Financial Transaction Request."),
    "EL187-USE": ("Element 187 CA Public Key File Checksum (Chapter 13)", 477,
                  "Once received, the checksum must be used for all subsequent EMV transaction requests from this device."),
    "EL188": ("Element 188 EMV Card Sequence Number (Chapter 13)", 477,
              "Fixed length of three bytes, right-aligned and padded with zeroes"),
    "EL188-VAL": ("Element 188 EMV Card Sequence Number (Chapter 13)", 477,
                  "Valid Codes/Values: 000—099 EMV Card Sequence Number is supplied by the EMV chip card"),
    "EL189": ("Element 189 EMV Chip Data Length (Chapter 13)", 477,
              "Identifies the length of the following field, EMV Chip Data, in an EMV transaction request and response."),
    "EL190-BIN": (EL190_NAME, 478, "All fields after the table length are in binary format."),
    "EL190-TLV": (EL190_NAME, 478, "EMV data elements include a series of TLV (Tag/Length/Value) combinations which are required "
                                   "for processing EMV transactions."),
    "EL190-EXACT": (EL190_NAME, 478, "All data elements must contain the exact values that were present on the card to terminal "
                                     "interface."),
    "EL190-AID": (EL190_NAME, 478, "the Application ID (AID) TLV (tag 9F06) or Dedicated File Name (tag 84) must be included in "
                                   "Data Element No. 190 (EMV Chip Data)."),
    "EL190-NO5A57": (EL190_NAME, 478, "Tag ‘5A’ (PAN) and Tag ‘57’ (Track 2 equivalent data) must not be included in Data "
                                      "Element 190 — EMV Chip Data."),
    "EL191": ("Element 191 EMV Additional Information Indicator (Chapter 13)", 479, "001 EMV table data"),
    "EL192": ("Element 192 EMV Additional Information Length (Chapter 13)", 480, "Valid Codes/Values: 001–985"),
    "EL118-EMV": ("Element 118 EMV Additional Information (Chapter 13)", 439,
                  "When EMV Additional Information Indicator has a value of 001, EMV table data is included here."),
    "APPR-PKT": (APP_R, 697, "01\\06 EMV/Chip Data length (BCD packed)"),
    "APPR-NOTE": (APP_R, 697, "The below table does not contain complete list of tags."),
    "APPR-9F02": (APP_R, 697, "9F\\02\\06\\00\\00\\00\\00\\09\\00\\ Amount, authorized (numeric)"),
    "APPR-9C": (APP_R, 697, "9C\\01\\00\\ Transaction type"),
    "APPT-1": (APP_T, 701, "EMVYES This code indicates that the device is able to receive EMV Table Data."),
    "APPT-1-NOT": (APP_T, 701, "EMVNOT This code indicates that the device is unable to receive EMV Table Data."),
    "APPT-1-LEN": (APP_T, 701, "Maximum Length n6 Len.: 6"),
    "APPT-1-ID": (APP_T, 701, "Table ID n3 Fixed value: 001"),
    "APPT-1-TLEN": (APP_T, 701, "Table Length n Fixed"),
    "APPG-3": (APP_G, 545, "3 POS Authorization Only Point-of-sale authorization only (no draft capture)"),
    "APPG-6": (APP_G, 545, "6 Mail/Phone Purchase Mail/Telephone order"),
    "APPG-7": (APP_G, 546, "7 Merchandise return Merchandise return/refund"),
    "APPG-A": (APP_G, 546, "A Account Verification Account verification"),
    "APPG-B": (APP_G, 546, "B Mail/Phone Authorization Only Mail/Telephone authorization only"),
    "APPG-C": (APP_G, 546, "C Mail/Phone Reversal Mail/Telephone reversal/void"),
    "APPG-E": (APP_G, 546, "E Balance Inquiry Balance Inquiry—Credit, EBT cash benefits and stored value card"),
    "APPG-S": (APP_G, 546, "S Cancellation Cancels a stored value card, credit Authorization Only transaction, and debit "
                           "Authorization Only transaction."),
    "APPG-U": (APP_G, 546, "U Void of a Merchandise Return Void of a merchandise return"),
    "APPG-Z": (APP_G, 546, "Z Time-out Reversal Time-out reversal"),
    "APPJ-10": (APP_J, 638, "10 Credential on file"),
    "APPJ-10-NOTE": (APP_J, 638, "The value is NOT applicable to the first transaction where credentials are being verified or stored."),
    "APPJ-79": (APP_J, 638, "79 Contact and Contactless chip fallback to manual"),
    "APPJ-80": (APP_J, 638, "80 Chip card capable – unaltered track data read (used for contact EMV fall back to swiped)"),
    "APPJ-82": (APP_J, 638, "82 Contactless mobile commerce device"),
    "APPJ-86": (APP_J, 638, "86 Dual interface contactless chip to contact chip"),
    "APPJ-91": (APP_J, 638, "91 Full contactless magnetic stripe read with track data"),
    "EL83-2": ("Element 83 Response Code (Chapter 13)", 404, "2 Approved—Authorization only"),
    "EL83-3": ("Element 83 Response Code (Chapter 13)", 404, "3 Approved—Authorization only with AVS"),
    "EL83-4": ("Element 83 Response Code (Chapter 13)", 404, "4 Approved—Purchase/Capture with AVS"),
    "EL83-5": ("Element 83 Response Code (Chapter 13)", 404, "5 Approved—Totals"),
    "EL83-6": ("Element 83 Response Code (Chapter 13)", 404, "6 Approved—Totals with electronic mail retrieval pending"),
    "EL83-B": ("Element 83 Response Code (Chapter 13)", 404, "B Approved—Totals with electronic mail retrieval pending and "
                                                           "proprietary data pending"),
    "EL83-D": ("Element 83 Response Code (Chapter 13)", 404, "D Approved—Totals with proprietary data pending"),
    "EL83-N": ("Element 83 Response Code (Chapter 13)", 404, "N Approved—Totals with Proprietary Host Discount data pending"),
    "EL83-W": ("Element 83 Response Code (Chapter 13)", 405, "W Approved—Totals with Proprietary Custom Receipt Text pending"),
    "S2.3-TOR": (S2_3, 39, "A time-out reversal (TOR) is created for an unsuccessful financial transaction."),
    "S2.3-TOR-TYPES": (S2_3, 39, "A TOR is generated when any of the following transactions is unsuccessful: POS Purchase/Capture "
                                 "transaction CAT Purchase/Capture transaction Mail/Phone Purchase transaction"),
    "S2.3.1-TOR-SEQ": (S2_3_1, 40, "The Sequence Number of a TOR must be the same as the Sequence Number of the financial "
                                  "transaction being reversed."),
    "S10.1.1.2-FOLLOW": ("Section 10.1.1.2 Track Support", 81,
                         "All follow-up transactions should use truncated Track 2 or truncated Track 2 facsimile."),
    "S10.1.8.1-STORE": ("Section 10.1.8.1 Data Storage Requirements", 94, "The merchant device must not store full magnetic stripe data."),
    "S10.1.2.2-SEQ": ("Section 10.1.2.2 Authorization Only Reversal Transaction", 85,
                      "Element No.86 (Sequence Number)—present in the original transaction—must be present and identical in the "
                      "Authorization Only Reversal transaction."),
    "EL86-PREAUTH": ("Element 86 Sequence Number (Chapter 13)", 409,
                     "In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture is the same Sequence "
                     "Number as in the transaction’s Authorization Only request."),
    "S10.1.3-AVS": ("Section 10.1.3 Address Verification Service", 86,
                    "The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information "
                    "Data Segment"),
    "EL113-AVS": ("Element 113 Variable Information (Chapter 13)", 426,
                  "When Element No. 111 (Variable Information Indicator) has a value of 003 (AVS indicator), address information "
                  "(street name, ZIP Code™/ZIP+4®) is included here."),
    "S2.3.4-RESUBMIT": ("Section 2.3.4 Summary of TOR Processing Rules", 47,
                        "When attempting to submit a TOR for a completion transaction, the device must finalize the TOR processing "
                        "prior to resubmitting the completion. If the completion is resent before the TOR processing has been "
                        "completed, the device risks voiding the resubmitted completion."),
    "S10.14.3-OFFLINE": (S10_14_3, 162, "Offline PIN: Instead of sending the PIN to the issuer, the PIN entered by the consumer is "
                                       "matched to that on an application housed on the EMV chip card."),
    "S10.13.2.4.1": ("Section 10.13.2.4.1 Stored Credentials", 143,
                     "A stored credential is information, including, but not limited to, an account number or a payment token."),
    "S10.13.2.4.2-MC": ("Section 10.13.2.4.2 Stored Credential Use Cases", 144,
                        "Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and "
                        "subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction "
                        "(CIT) and Merchant initiated transactions (MIT) for MasterCard card type."),
    "APPI-032-08": (APP_I_032, 575, "It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on "
                                   "File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, "
                                   "Reversal etc.)."),
    "APPI-032-08-10": (APP_I_032, 575, "POS Entry Mode 10 should be used for all subsequent COF transactions (when the COF "
                                      "indicator is ‘C’)."),
    "APPI-049-02": ("Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02", 589,
                    "This field is required for all subsequent Credential on File transactions."),
    "APPI-030": ("Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030)", 568,
                 "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), "
                 "and all Credential on File transactions."),
    "APPQ": (APP_Q, 690, "This appendix lists valid entries for Variable Information (Element No. 113), Table ID 030 The National "
                         "Point-of-Service Condition Codes data element contains the following elements: • Terminal Class "
                         "(Position Nos. 1–3) • Presentation Type (Position Nos. 4–7) • Security Condition (Position No. 8) "
                         "• Terminal Type (Position Nos. 9–10)"),
    "APPQ-CLASS": (APP_Q, 691, "Position No. 1 Value Description 0 Attended 1 Unattended 2–7 Reserved for national use 8–9 "
                               "Reserved for private use Position No. 2 Value Description 0 Customer-operated 1 Card "
                               "acceptor-operated 2 Administrative 3–7 Reserved for national use 8–9 Reserved for private use "
                               "Position No. 3 Value Description 0 On premise 1 Off premise"),
    "APPQ-TYPE": (APP_Q, 692, "Value Description 00 Administrative terminal 01 Point-of-service terminal 02 ATM 03 Home terminal "
                              "04 ECR 05 Dial terminal 06 Traveler's check machine 07 Fuel machine"),
    "APPQ-PRES": (APP_Q, 696, "Position Nos. 4–7 Presentation Type Description 0000 Customer and card present 0100 Customer "
                              "present; card not present 1000 Customer not present; card present 1100 Customer not present; card "
                              "not present 2100 Mail and/or telephone order 8800 Preauthorized purchase"),
    "APPQ-SEC": (APP_Q, 696, "Security Condition Value Description 0 No security concern 1 Suspected fraud 2 Identification "
                             "verified 3 Electronic commerce transaction with digital signature"),
    # ---- debit card processing (Section 10.2)
    "APPE-011": ("Appendix E. Valid Card Type Codes", 521, "011 Debit checking Debit"),
    "S10.2.1.3-PIN": ("Section 10.2.1.3 PIN Entry", 96,
                      "All transactions—except the Purchase Reversal transaction—require PIN entry."),
    "S10.2.1.3-DUKPT": ("Section 10.2.1.3 PIN Entry", 96,
                        "When processing a debit card transaction, the entry of the customer PIN is required on a DES-compliant "
                        "entry device, and it must be encrypted using the DUKPT PIN encryption method."),
    "S10.2.1.4": ("Section 10.2.1.4 Manual Entry", 96, "Manual entry is not allowed on a debit card transaction."),
    "S10.2.2-TOR": ("Section 10.2.2 Supported Transaction Types", 97,
                    "Note: Time-out Reversal for debit Completion transaction is not supported."),
    "S10.2.5.1-PIN": ("Section 10.2.5.1 Data Storage Requirements", 105,
                      "PIN block data is not included in follow-on transactions (Completions/Reversals)."),
    "S10.2.5.2-FOLLOW": ("Section 10.2.5.2 Track Support Requirements", 105,
                         "The host message should only contain the Account Number, followed by an account delimiter (=), "
                         "followed by the 4 digit Expiration Date (Storage format – YYMM)."),
    "EL12-READ": (EL12_NAME, 371, "If the Account Number is obtained from a Track 2 card reader, instead of being manually keyed, "
                                  "the Account Number and Card Discretionary Block Data should consist of all data read from the "
                                  "card between, but not including, the Start Sentinel and End Sentinel."),
    "EL12-ALL": (EL12_NAME, 372, "Note: For all authorizations, merchants must send a value even for expired cards."),
    "EL12-COF": (EL12_NAME, 372, "These credentials on file (COF) transactions include bill payment, recurring, and installment "
                                 "payment transactions."),
    "EL17": ("Element 17 Cash Amount (Chapter 13)", 375, "Identifies the cash back amount of a transaction."),
    "EL41": ("Element 41 Fuel Purchase Amount (Chapter 13)", 385, "Representation: Variable length of up to eight digits with two "
             "assumed decimal places"),
    "EL41-USE": ("Element 41 Fuel Purchase Amount (Chapter 13)", 385, "Included on any transactions with fuel sales."),
    "EL79": ("Element 79 Pump/Lane Number (Chapter 13)", 401, "Representation: Variable length of up to two digits Purpose: "
             "Identifies the pump or lane number at the point-of-sale location."),
    "EL79-USE": ("Element 79 Pump/Lane Number (Chapter 13)", 402, "Required on all fuel transactions."),
    "EL99": ("Element 99 Tax Amount (Chapter 13)", 415, "Identifies the transaction tax amount in Data Segment No. 100, "
             "Standard Message Data Segment."),
    "EL58-PURPOSE": ("Element 58 Nonfuel Amount (Chapter 13)", 393,
                     "Identifies the transaction’s dollar amount that is not a fuel, tax, or cash amount."),
    # ---- Totals Request (Section 11.4.1.1, Segment 105 p.235-237, Section 9.2-9.3)
    "S11.4-TOTALS": ("Section 11.4.1.1 Totals Request", 178,
                     "Note: Totals Request messages do not contain Data Section No. 2 [Standard Message Data Segment (Data "
                     "Segment No. 100)]."),
    "S12.6-FS": ("Section 12.6 Totals Data Segment", 235,
                 "Field Nos. 1–14 are separated by Field Separators, with a Field Separator following Field No. 14."),
    "S12.6-NOFS": ("Section 12.6 Totals Data Segment", 235, "Field Nos. 15–17 are not separated by Field Separators."),
    "S12.6-PROMPT": ("Section 12.6 Totals Data Segment", 235, "Identifies the format type for the requested totals. Fixed value: 990"),
    "S12.6-TYPE": ("Section 12.6 Totals Data Segment", 235, "Identifies the type of data segment being formatted. Fixed value: 105"),
    "S12.6-CAT": ("Section 12.6 Totals Data Segment", 237,
                  "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order "
                  "shown and only when data occurs for a card type."),
    "EL13": ("Element 13 Card Label (Chapter 13)", 372, "Fixed length of four alphanumeric characters; left-aligned and space-filled"),
    "EL13-DB": ("Element 13 Card Label (Chapter 13)", 373, "Debit DB"),
    "EL15": ("Element 15 Card Type Total Amount (Chapter 13)", 374, "Fixed length of eight digits with two assumed decimal places"),
    "EL16": ("Element 16 Card Type Total Count (Chapter 13)", 375, "Fixed length of five digits"),
    "EL16-VAL": ("Element 16 Card Type Total Count (Chapter 13)", 375, "Valid Codes/Values: 00001–99999"),
    "EL20": ("Element 20 Currency Code (Chapter 13)", 376, "Fixed length of three digits"),
    "EL39": ("Element 39 Firmware Version (Chapter 13)", 384, "Fixed length of eight alphanumeric characters"),
    "EL42": ("Element 42 Grand Total (Chapter 13)", 386, "Fixed length of eight digits with two assumed decimal places"),
    "EL43": ("Element 43 Hardware Version (Chapter 13)", 386, "Fixed length of four or eight alphanumeric characters"),
    "EL96": ("Element 96 Software Version (Chapter 13)", 414, "Fixed length of eight alphanumeric characters"),
    "EL105": ("Element 105 Totals Date (Chapter 13)", 419, "Fixed length of six digits"),
    "S9.2-GT": ("Section 9.2 Local Totals", 72, "The grand total includes all transactions except the non-financial transactions "
                                              "grouped in the following categories: • AO—Authorization only"),
    "S9.2-PUR": ("Section 9.2 Local Totals", 72, "A Purchase/Capture increases the count and dollar amount of the corresponding "
                                               "category as well as the grand total."),
    "S9.2-REV": ("Section 9.2 Local Totals", 72, "A Reversal decreases the count and dollar amount of the corresponding category "
                                               "as well as the grand total."),
    "S9.2-RET": ("Section 9.2 Local Totals", 72, "A Merchandise Return increases the count and decreases the dollar amount for the "
                                               "corresponding category as well as the grand total."),
}

# Segment 100 layout, p.223-225: (field no, element, TD key, spec name, R/C)
SEG100 = [
    (1, "85", "SegmentType", "Segment Type", "R"), (2, "84", "SegmentLength", "Segment Length", "R"),
    (3, "44", "InformationByte", "Information Byte", "R"), (4, "102", "TerminalIdentifier", "Terminal Identifier", "R"),
    (5, "78", "PromptCode", "Prompt Code", "R"), (6, "2", "AccountNumber", "Account Number", "R"),
    (7, "12", "CardDiscretionaryBlockData", "Card Discretionary Block Data", "C"),
    (8, "33", "EncryptedPINBlockData", "Encrypted PIN Block Data", "C"), (9, "79", "PumpLaneNumber", "Pump/Lane Number", "C"),
    (10, "41", "FuelPurchaseAmount", "Fuel Purchase Amount", "C"), (11, "58", "NonfuelAmount", "Nonfuel Amount", "C"),
    (12, "99", "TaxAmount", "Tax Amount", "C"), (13, "17", "CashAmount", "Cash Amount", "C"),
    (14, "86", "SequenceNumber", "Sequence Number", "R"), (15, "5", "ApprovalNumber", "Approval Number", "C"),
    (16, "49", "LocalDateandLocalTime", "Local Date and Local Time", "C"),
    (17, "121", "PartialApprovalIndicator", "Partial Approval Indicator", "R"),
]
NON_AMEX_AMOUNT_MAX = 7  # Elements 41/58/99 note: 8 bytes AMEX only, 7 bytes all other card types
# Chapter 13 text for Segment 100 fields that have no rule of their own
FIELD_REFS = {"41": ["EL41", "EL41-USE"], "79": ["EL79", "EL79-USE"], "99": ["EL99"], "17": ["EL17"]}
INFO_BYTE = {"?": "download request", "0": "single-message transmission", "1": "multimessage transmission"}
PAI = {"0": "partial approval not supported", "1": "partial approval supported", "5": "card balance only (Amex prepaid)"}
PAN_ENTRY_DESC = {"00": "unspecified", "01": "manual/keyed", "02": "magnetic stripe, partial track data", "05": "chip read",
                  "07": "contactless chip read", "10": "credential on file", "79": "chip fallback to manual",
                  "80": "chip fallback to swipe", "82": "contactless mobile commerce device",
                  "86": "contactless chip to contact chip", "90": "magnetic stripe, full track data",
                  "91": "contactless magnetic stripe"}
PIN_CAP_DESC = {"0": "unspecified", "1": "PIN entry capability", "2": "no PIN entry capability",
                "3": "mPOS software-based PIN entry", "6": "PIN pad inoperative", "8": "contactless magnetic stripe read capability"}
T031_DESC = {"00": "unknown or unspecified", "01": "terminal not used", "02": "magnetic stripe read (no chip)", "03": "bar code",
             "04": "OCR", "05": "contact chip (ICC) read", "06": "contactless chip read (no contact chip)",
             "07": "contactless magnetic stripe read (no contact or contactless chip)", "09": "terminal does not read card data",
             "12": "hybrid reader: contact chip and contactless chip"}
CHIP_CAPABLE_031 = {"05", "06", "12"}
# Appendix Q (p.691-696): Table 030 = Terminal Class (1-3) + Presentation Type (4-7) + Security Condition (8) + Terminal Type (9-10)
T030_POS = [("Terminal Class position 1", {"0": "attended", "1": "unattended"}),
            ("Terminal Class position 2", {"0": "customer-operated", "1": "card acceptor-operated", "2": "administrative"}),
            ("Terminal Class position 3", {"0": "on premise", "1": "off premise"}),
            ("Presentation Type position 4", {"0": "customer present", "1": "customer not present",
                                              "2": "mail and/or telephone order (customer not present)", "8": "preauthorized purchase"}),
            ("Presentation Type position 5", {"0": "card present", "1": "card not present", "8": "preauthorized purchase"}),
            ("Presentation Type position 6", {"0": "device does not have card retention capability",
                                              "1": "device has card retention capability"}),
            ("Presentation Type position 7", {"0": "original presentment", "1": "first re-presentment", "2": "second re-presentment",
                                              "3": "third re-presentment", "4": "previously authorized request", "5": "resubmit"}),
            ("Security Condition position 8", {"0": "no security concern", "1": "suspected fraud", "2": "identification verified",
                                               "3": "electronic commerce transaction with digital signature"})]
T030_TYPE = {"00": "administrative terminal", "01": "point-of-service terminal", "02": "ATM", "03": "home terminal", "04": "ECR",
             "05": "dial terminal", "06": "traveler's check machine", "07": "fuel machine", "08": "scrip machine",
             "09": "coupon machine", "10": "ticket machine", "11": "point-of-banking terminal", "19": "call center operator",
             "20": "voice response unit (VRU)", "21": "channel encryption; cardholder certificate is not used",
             "24": "digital secure remote payment with UCAF data", "25": "internet (nonrecurring, secure)",
             "26": "internet (secure; 3DES attempted)", "27": "internet (secure using SSL)", "28": "internet (nonsecure)",
             "29": "internet recurring payment after a mobile device-issued payment token (MasterCard only)"}
# Appendix Q p.693 examples of Terminal Class + Terminal Type combinations
for _cls, _typ, _desc in (("100", "01", "Unattended on premise customer-operated point-of-service terminal"),
                          ("001", "01", "Attended off premise customer-operated point-of-service terminal"),
                          ("000", "04", "Attended on premise customer-operated ECR"),
                          ("010", "04", "Attended on premise Card acceptor-operated ECR"),
                          ("001", "04", "Attended off premise customer-operated ECR"),
                          ("100", "07", "Unattended on premise customer-operated fuel machine"),
                          ("101", "07", "Unattended off premise customer-operated fuel machine")):
    REFS[f"APPQ-{_cls}-{_typ}"] = (APP_Q, 693, f"{_cls} {_typ} {_desc}")
RC_MEANING = {"0": ("Approved - Purchase/Capture", "EL83-0"), "F": ("Approved - Partial approval", "EL83-F"),
              "1": ("Declined - all other transactions", "EL83-1"), "2": ("Approved - Authorization only", "EL83-2"),
              "3": ("Approved - Authorization only with AVS", "EL83-3"), "4": ("Approved - Purchase/Capture with AVS", "EL83-4"),
              "5": ("Approved - Totals", "EL83-5"), "6": ("Approved - Totals with electronic mail retrieval pending", "EL83-6"),
              "B": ("Approved - Totals with electronic mail retrieval pending and proprietary data pending", "EL83-B"),
              "D": ("Approved - Totals with proprietary data pending", "EL83-D"),
              "N": ("Approved - Totals with Proprietary Host Discount data pending", "EL83-N"),
              "W": ("Approved - Totals with Proprietary Custom Receipt Text pending", "EL83-W")}
TOTALS_APPROVED_RC = {"5", "6", "B", "D", "N", "W"}
RUNTIME_REFS = {("split_tender", "Nonfuel Amount"): ["S10.1.2.1-REMAIN", "EL6"],
                ("reversal", "Nonfuel Amount"): ["S10.1.2.1-REV", "EL6"],
                ("reversal", "Approval Number"): ["EL5-REV", "EL5-FMT"],
                ("moto_reversal", "Approval Number"): ["EL5-REV", "EL5-FMT"],
                ("void_return", "Approval Number"): ["EL5-REV", "EL5-FMT"],
                ("completion", "Approval Number"): ["EL5-PRE", "APPG-0"],
                ("completion", "Nonfuel Amount"): ["S10.1.2.1-REMAIN", "EL6"],
                ("cancellation", "Approval Number"): ["S10.1.2.2", "EL5-FMT"],
                ("cancellation", "Nonfuel Amount"): ["S10.1.2.1-REV", "EL6"]}
# follow-on steps that must carry the original's Approval Number; a TOR follows a transaction that never got a response
APPROVAL_ROLES = {"reversal", "moto_reversal", "void_return", "cancellation", "completion", "completion_resubmitted"}
TOR_ROLES = {"timeout_reversal", "tor_retry"}
FOLLOW_ON_KIND = {"completion": "completion", "completion_resubmitted": "completion", "cancellation": "cancellation"}
PROMPT_TXN_BY_ROLE = {**tcv.PROMPT_TXN_BY_ROLE, "balance_inquiry": {"E"}, "resubmitted": {"0", "4"}}
# entry modes without a card read: expiry is sent as MMYY (keyed) - Element 12, Appendix J
KEYED_ENTRY_MODES = {"01", "79"}
# EMV Book 3 Transaction Type (Tag 9C) per ATL105 Transaction Type
EMV_9C = {"0": "00", "3": "00", "6": "00", "B": "00", "7": "20", "E": "31"}
# possible wire length (min, max) of a run-time value after a partial approval of the original amount
RUNTIME_LENGTHS = {"Nonfuel Amount": lambda orig: (1, len(str(int(orig) - 1))), "Approval Number": lambda orig: (6, 6)}
QUOTE_STATUS = {"QUOTE_PARAPHRASED": "Not word-for-word: all its words are on the cited page, but not in this form",
                "QUOTE_PAGE_DRIFT": "Found on a neighbouring page, not the cited one",
                "QUOTE_WRONG_PAGE": "Found on a different page, not the cited one",
                "QUOTE_ONLY_IN_2026_3": "Only in the older 2026-3 edition",
                "QUOTE_NOT_IN_SPEC": "Not found in ATL105"}
QUOTE_TITLE = {"QUOTE_PARAPHRASED": "is not word-for-word in ATL105", "QUOTE_PAGE_DRIFT": "is on the neighbouring page",
               "QUOTE_WRONG_PAGE": "is on a different page", "QUOTE_ONLY_IN_2026_3": "is only in the older 2026-3 edition",
               "QUOTE_NOT_IN_SPEC": "is not in ATL105"}
SEVERITY_ORDER = {"FAIL": 0, "BLOCKED": 1, "REVIEW": 2, "INFO": 3, "PASS": 4}
# reading order of related findings inside one issue (cause before consequence)
RULE_ORDER = {"PROVISIONAL_AMOUNT_TOO_LONG": 1, "AMOUNT_NOT_POSSIBLE": 2, "SEGMENT_LENGTH_IMPOSSIBLE": 3,
              "RUNTIME_PLACEHOLDER": 4, "SEGMENT_LENGTH_RUNTIME": 5}
PLACEHOLDER = re.compile(r"^<.*>$")
TODAY = date.today()
EMV_ROOT, PLAIN_ROOT = "EMV Financial Transaction Request", "Financial Transaction Request"
EMV_ENTRY_MODES = {"05", "07", "86"}
# card programs: Prompt Code card type (Appendix E p.521), totals bucket (Element 13) and debit rules (Section 10.2)
PROFILES = {
    "MASTERCARD/CREDIT": dict(name="MasterCard Credit", network="MasterCard", card_type="020", card_ref="APPE-020",
                              card_desc="Credit", bin_ok=tcv.is_mastercard_bin, bin_text="MasterCard range 51-55 / 2221-2720",
                              debit=False, label="CC"),
    "INTERLINK/DEBIT": dict(name="Interlink Debit", network="Interlink", card_type="011", card_ref="APPE-011",
                            card_desc="Debit checking", bin_ok=None, bin_text="", debit=True, label="DB"),
    "MAESTRO_DIRECT/DEBIT": dict(name="Maestro Direct Debit", network="Maestro", card_type="011", card_ref="APPE-011",
                                 card_desc="Debit checking", bin_ok=None, bin_text="", debit=True, label="DB"),
}
TOTALS_ROOT = "Totals Request"
# Segment 105 layout, p.235-237: (field no, element, TD key, spec name, R/O/C, character type, allowed lengths)
S105 = [(1, "85", "SegmentType", "Segment Type", "R", "N", (3,)), (2, "84", "SegmentLength", "Segment Length", "R", "N", (3,)),
        (3, "44", "InformationByte", "Information Byte", "R", "AN", (1,)),
        (4, "102", "TerminalIdentifier", "Terminal Identifier", "R", "AN", None),
        (5, "78", "PromptCode", "Prompt Code", "R", "N", (3,)), (6, "32", "EmployeeNumber", "Employee Number", "C", "N", (4,)),
        (7, "65", "Password", "Password", "C", "AN", (6,)), (8, "105", "TotalsDate", "Totals Date", "R", "N", (6,)),
        (9, "43", "HardwareVersion", "Hardware Version", "R", "AN", (4, 8)),
        (10, "96", "SoftwareVersion", "Software Version", "R", "AN", (8,)),
        (11, "39", "FirmwareVersion", "Firmware Version", "R", "AN", (8,)),
        (12, "86", "SequenceNumber", "Sequence Number", "R", "N", (6,)),
        (13, "20", "CurrencyCode", "Currency Code", "O", "N", (3,)), (14, "42", "GrandTotal", "Grand Total", "R", "N", (8,)),
        (15, "13", "CardLabel", "Card Label", "R", "AN", (4,)),
        (16, "16", "CardTypeTotalCount", "Card Type Total Count", "R", "N", (5,)),
        (17, "15", "CardTypeTotalAmount", "Card Type Total Amount", "R", "N", (8,))]
S105_REFS = {"85": ["S12.6-TYPE"], "84": ["S12.6-FS", "S12.6-NOFS", "EL84"], "44": ["EL44"], "102": ["EL102", "EL102-STATE"],
             "78": ["S12.6-PROMPT"], "105": ["EL105"], "43": ["EL43"], "96": ["EL96"], "39": ["EL39"], "86": ["EL86-FMT"],
             "20": ["EL20"], "42": ["EL42", "S9.2-GT"], "13": ["EL13", "S12.6-CAT"], "16": ["EL16", "EL16-VAL"], "15": ["EL15"]}
CARD_LABELS = {"CC", "TE", "DS", "AO", "DB", "FL", "CS", "PR", "CK", "EF", "EC", "SV1", "SV2", "SV3", "SV4", "ECA", "EWIC", "EK"}
TOTALS_DATES = {"111111": "totals since the last 999999 request", "222222": "current settlement date, read and cut",
                "333333": "most recent date with activity, read only", "444444": "second most recent date with activity",
                "555555": "third most recent date with activity", "999999": "clear all totals since the last 999999 request"}
TOTALS_ROLES = {"purchase", "next_transaction", "resubmitted", "completion", "completion_resubmitted", "reversal",
                "timeout_reversal", "tor_retry", "return", "void_return", "auth_only", "moto_auth_only", "cancellation"}


def profile(tc):
    d = tc.get("dimensions") or {}
    return PROFILES.get(f"{d.get('network') or 'MASTERCARD'}/{d.get('program') or 'CREDIT'}".upper(), PROFILES["MASTERCARD/CREDIT"])

# Segment 130 layout, p.270-271: (field no, element, TD key, spec name)
S130 = [(1, "85", "SegmentType", "Segment Type"), (2, "84", "SegmentLength", "Segment Length"),
        (3, "187", "CAPublicKeyFileChecksum", "CA Public Key File Checksum"),
        (4, "188", "EMVCardSequenceNumber", "EMV Card Sequence Number"),
        (5, "189", "EMVChipDataLength", "EMV Chip Data Length"), (6, "190", "EMVChipData", "EMV Chip Data"),
        (7, "191", "EMVAdditionalInformationIndicator", "EMV Additional Information Indicator"),
        (8, "192", "EMVAdditionalInformationLength", "EMV Additional Information Length"),
        (9, "118", "EMVAdditionalInformation", "EMV Additional Information")]
# EMV tag names (EMV Book 3); ATL105 Appendix R gives an example packet only
EMV_TAGS = {"5A": "Application PAN", "57": "Track 2 Equivalent Data", "5F24": "Application Expiration Date",
            "5F2A": "Transaction Currency Code", "5F34": "PAN Sequence Number", "82": "Application Interchange Profile",
            "84": "Dedicated File (DF) Name", "95": "Terminal Verification Results", "9A": "Transaction Date",
            "9C": "Transaction Type", "9F02": "Amount, Authorized", "9F03": "Amount, Other",
            "9F06": "Application Identifier (AID)", "9F10": "Issuer Application Data", "9F1A": "Terminal Country Code",
            "9F1E": "Interface Device Serial Number", "9F26": "Application Cryptogram", "9F27": "Cryptogram Information Data",
            "9F33": "Terminal Capabilities", "9F34": "CVM Results", "9F35": "Terminal Type",
            "9F36": "Application Transaction Counter", "9F37": "Unpredictable Number", "9F41": "Transaction Sequence Counter",
            "9F53": "Transaction Category Code", "9F6E": "Third Party Data"}
APPR_TAGS = {"9F06", "9F02", "9F26", "82", "9F36", "9F37", "95", "9C", "9F10", "84", "9F27", "9F34", "5F2A", "9F1A", "9A", "9F33"}
CVM_NAMES = {0x00: "CVM processing failed", 0x01: "plaintext PIN verified by the card", 0x02: "enciphered PIN verified online",
             0x03: "plaintext PIN by the card and signature", 0x04: "enciphered PIN verified by the card",
             0x05: "enciphered PIN by the card and signature", 0x1E: "signature", 0x1F: "no CVM required",
             0x3F: "no CVM performed"}
CID_NAMES = {"00": "AAC (declined offline)", "40": "TC (approved offline)", "80": "ARQC (go online)"}


class Spec:
    """Spec text split by '--- PAGE N ---' (PDF page) with the printed page label of each page."""
    LABEL = re.compile(r"Specification Release Version:\s*(\S+)\s+([A-Z][a-z]+ \d{1,2}, \d{4})\s+(Appendix [A-Z]+-\d+|\d+-\d+)")

    def __init__(self, path):
        text = path.read_text(encoding="utf-8", errors="replace")
        self.raw = {int(m.group(1)): m.group(2) for m in re.finditer(r"--- PAGE (\d+) ---\n(.*?)(?=--- PAGE \d+ ---|\Z)", text, re.S)}
        self.sq = {p: tcv.squash(s) for p, s in self.raw.items()}
        self.labels, self.version, self.date = {}, "?", "?"
        for p, s in self.raw.items():
            m = self.LABEL.search(re.sub(r"\s+", " ", s))
            if m:
                self.labels[p] = m.group(3)
                self.version, self.date = m.group(1), m.group(2)

    def printed(self, page):
        return self.labels.get(page, "?")

    def has(self, page, text):
        return tcv.squash(text) in self.sq.get(page, "")

    def pages_with(self, text):
        key = tcv.squash(text)
        return [p for p in sorted(self.sq) if key in self.sq[p]]

    def state_codes(self):
        """Appendix D rows: code -> (name, abbreviation, PDF page, region)."""
        out, region = {}, "United States"
        for p in sorted(self.raw):
            if not self.printed(p).startswith("Appendix D-"):
                continue
            for line in self.raw[p].splitlines():
                text = line.strip()
                low = text.lower()
                if "following table contains" in low:
                    region = ("Canada" if "canada" in low else "outside the US and Canada" if "outside" in low
                              else "United States territory" if "territories" in low else "US military" if "military" in low
                              else "United States")
                m = re.match(r"^(.+?)\s+([A-Z]{2})\s+(\d{2})$", text)
                if m:
                    out[m.group(3)] = (m.group(1).strip(), m.group(2), p, region)
        return out


class Refs:
    def __init__(self, spec, prior, states):
        self.spec, self.prior, self.status, self.used = spec, prior, {}, []
        self.catalog = dict(REFS)
        for code, (name, abbr, page, _) in states.items():
            self.catalog[f"APPD-{code}"] = ("Appendix D. Valid State Codes", page, f"{name} {abbr} {code}")

    def check(self, rid):
        if rid not in self.status:
            _, page, text = self.catalog[rid]
            found = self.spec.pages_with(text)
            self.status[rid] = ("verified" if page in found else f"not on p.{page}; found on p.{found}" if found
                                else "NOT FOUND in spec text")
        if rid not in self.used:
            self.used.append(rid)
        return self.status[rid]

    def where(self, rid):
        self.check(rid)
        sec, page, _ = self.catalog[rid]
        return f"{sec}, PDF p.{page} (printed {self.spec.printed(page)})"

    def short(self, rid):
        self.check(rid)
        sec, page, _ = self.catalog[rid]
        m = re.match(r"(Section [\d.]+|Element \d+|Appendix [A-Z]+)", sec)
        return f"{m.group(1) if m else sec}, PDF p.{page} (printed {self.spec.printed(page).replace('Appendix ', '')})"

    def shorts(self, rids):
        return "; ".join(uniq(self.short(r) for r in rids)) or "-"

    def quote(self, rid):
        return self.catalog[rid][2]

    def full(self, rid):
        status = self.check(rid)
        flag = "" if status == "verified" else f" [text check: {status}]"
        return f'{self.where(rid)}: "{self.quote(rid)}"{flag}'


class Report:
    def __init__(self, tc):
        self.tc = tc
        self.rows, self.links, self.responses, self.quotes, self.findings = [], [], [], [], []
        self.issue_titles, self.emv = {}, {}

    def row(self, step, seg, fno, el, name, td, tc_value, rule, refs, result):
        self.rows.append(dict(step=step, seg=seg, fno=fno, el=el, name=name, td=td, tc=tc_value, rule=rule,
                              refs=list(refs), result=result))

    def add(self, step, severity, rule, title, label="", field="", value="", why="", fix="", refs=(), issue=None, brief=None):
        self.findings.append(dict(step=step, severity=severity, rule=rule, title=title, label=label, field=field, value=value,
                                  why=why, fix=fix, refs=list(refs), issue=issue or rule, brief=brief or why))

    def count(self, severity):
        return sum(1 for f in self.findings if f["severity"] == severity)

    def verdict(self):
        seen = {f["severity"] for f in self.findings} | {r["result"] for r in self.rows + self.links + self.responses}
        return next((s for s in ("FAIL", "BLOCKED", "REVIEW") if s in seen), "PASS")

    def can_run(self):
        v = self.verdict()
        if v == "FAIL":
            return "No - fix the FAIL items first"
        if v == "BLOCKED":
            return "Only after the run-time values are filled in from the step 1 response"
        if "MERCHANT_PLACEHOLDER" in self.tc.get("flags", []):
            return "Yes, once the real Terminal Identifier is supplied"
        return "Yes"


def uniq(items):
    out = []
    for x in items:
        if x and x not in out:
            out.append(x)
    return out


def esc(s):
    return str(s).replace("|", "\\|").replace("\n", " ")


def amount(v):
    return f"${int(v) / 100:,.2f}" if str(v).isdigit() else str(v)


def expiry(mmyy):
    if not re.fullmatch(r"\d{4}", str(mmyy)):
        return str(mmyy)
    mm, yy = int(mmyy[:2]), int(mmyy[2:])
    return f"{date(2000 + yy, mm, 1):%B %Y}" if 1 <= mm <= 12 else mmyy


def char_problems(val, ctype, mx, mode):
    probs = []
    rx = tcv.CHAR_RX.get(ctype)
    if rx and not re.fullmatch(rx, val):
        probs.append({"N": "contains characters other than digits",
                      "AN": "contains characters other than letters, digits and spaces"}.get(ctype, f"is not {ctype}"))
    if mx and mode == "FIXED" and len(val) != mx:
        probs.append(f"is {len(val)} characters long; the field is fixed at {mx}")
    elif mx and mode != "FIXED" and len(val) > mx:
        probs.append(f"is {len(val)} characters long; the field allows at most {mx}")
    return probs


def ref_constraint(ref, el):
    e = ref.get(el)
    if not e:
        return None, None, None
    c = e["definitions"][0]["constraints"]
    return c.get("characterType"), c.get("maxLength"), c.get("lengthMode")


def td_seg100_values(seg):
    out = {}
    for _, _, key, _, _ in SEG100:
        v = seg.get(key)
        if key == "PromptCode" and isinstance(v, dict):
            v = f"{v.get('TransactionType', '')}{v.get('CardType', '')}"
        out[key] = "" if v is None else str(v)
    return out


def seg100_length(values, skip=()):
    """Segment 100 length: every field is followed by a Field Separator (p.223)."""
    return sum(len(values[k]) for _, _, k, name, _ in SEG100 if name not in skip) + len(SEG100)


def length_range(values, placeholders, orig):
    """(base, min, max) once run-time values are filled in; orig = sale amount after a partial approval, else None."""
    base = seg100_length(values, placeholders)
    lo = hi = base
    for name in placeholders:
        if name not in RUNTIME_LENGTHS or (name == "Nonfuel Amount" and not orig):
            return None
        a, b = RUNTIME_LENGTHS[name](orig)
        lo, hi = lo + a, hi + b
    return base, lo, hi


def parse_tlv(hexdata):
    """Element 190: 2-byte BCD length (Appendix R) + BER-TLV items -> (declared length, [(tag, length, value)], problems)."""
    if not hexdata or len(hexdata) % 2 or not re.fullmatch(r"[0-9A-Fa-f]+", hexdata):
        return None, [], ["is not an even-length hexadecimal string"]
    data = bytes.fromhex(hexdata)
    if not data[:2].hex().isdigit():
        return len(data) - 2, [], [f"does not start with a 2-byte BCD length (it starts with {data[:2].hex().upper()})"]
    declared, body, i, items, problems = int(data[:2].hex()), data[2:], 0, [], []
    if declared != len(body):
        problems.append(f"declares {declared} bytes of TLV data in its 2-byte BCD length, but {len(body)} bytes follow")
    while i < len(body):
        tag = body[i:i + 2] if body[i] & 0x1F == 0x1F else body[i:i + 1]
        i += len(tag)
        if i >= len(body):
            problems.append(f"tag {tag.hex().upper()} has no length")
            break
        ln, i = body[i], i + 1
        if ln & 0x80:
            ln, i = int.from_bytes(body[i:i + (ln & 0x7F)], "big"), i + (ln & 0x7F)
        if i + ln > len(body):
            problems.append(f"tag {tag.hex().upper()} declares {ln} bytes, but only {len(body) - i} remain")
            break
        items.append((tag.hex().upper(), ln, body[i:i + ln].hex().upper()))
        i += ln
    return declared, items, problems


def seg130_length(val, chip_bytes):
    """Segment 130 (p.270): a Field Separator after Fields 1-6 and after the final EMV Additional Information Section."""
    return (sum(len(val[k]) for _, _, k, _ in S130[:5]) + 5 + chip_bytes + 1
            + sum(len(val[k]) for _, _, k, _ in S130[6:]) + 1)


def approval_rule(role):
    """(rule text, refs) for the Approval Number of a follow-on step that must carry the original's Approval Number."""
    if FOLLOW_ON_KIND.get(role) == "completion":
        return "Required on a completion: the authorization's Approval Number makes the purchase preauthorized", ["EL5-PRE", "APPG-0"]
    if role == "cancellation":
        return "Required on a cancellation (Authorization Only Reversal): identical to the original", ["S10.1.2.2", "EL5-FMT"]
    return "Required on a reversal: the 6-character Approval Number of the original", ["EL5-REV", "EL5-FMT"]


def validate_step(rep, refs, tc, st, td, ref, ctx, states):
    n, role, tcr = st["step"], st["role"], st["tc_request"]
    fname, of = st["td"], st.get("of", st["step"])
    runtime = st.get("runtime", {})
    issue = f"AMOUNT_LINK_{n}"
    root = EMV_ROOT if EMV_ROOT in td else PLAIN_ROOT
    msg = td.get(root)
    if msg is None:
        rep.add(n, "FAIL", "MESSAGE_TYPE", "The file is not a Financial Transaction Request", label="message", field=fname,
                value=", ".join(td), why=f"The test data root must be '{PLAIN_ROOT}' or '{EMV_ROOT}'.",
                fix="Regenerate the test data file.", refs=["S11.1-DS1"])
        return
    segs = [k for k, x in msg.items() if isinstance(x, dict)]
    s100 = msg.get("Standard Message Data Segment")
    s111 = msg.get("Variable Information Data Segment")
    s130 = msg.get("EMV Request Data Segment")
    vi = [(x.get("VariableInformationIndicator", ""), x.get("VariableInformationLength", ""), x.get("VariableInformation", ""))
          for x in (s111 or {}).get("VariableInformationSection") or []]
    t005 = next((x[2] for x in vi if x[0] == "005"), None)
    t031 = next((x[2] for x in vi if x[0] == "031"), None)
    entry = t005[:2] if t005 else None
    ctx["roots"][n] = root
    prof = profile(tc)

    # ---- message type (p.205-206, p.270)
    if s130 is not None:
        ok = root == EMV_ROOT
        rep.row(n, "Message", "-", "-", "Message type", root, "-", "Segment 130 is sent only in an EMV Financial Transaction Request",
                ["S12.20-ALWAYS", "S11.8-EMV"], "PASS" if ok else "FAIL")
        if not ok:
            rep.add(n, "FAIL", "MESSAGE_TYPE_EMV", "EMV data is sent in a non-EMV message", label="message", field=fname, value=root,
                    why="Segment 130 always appears in an EMV Financial Transaction Request.", fix=EMV_ROOT,
                    refs=["S12.20-ALWAYS"])
    elif entry in EMV_ENTRY_MODES and role not in tcv.SAME_SEQ_ROLES:
        rep.row(n, "Message", "-", "-", "Message type", root, "-", "Chip read: EMV Financial Transaction Request with Segment 130",
                ["S11.8-130"], "FAIL")
        rep.add(n, "FAIL", "SEG130_MISSING", "The EMV Request Data Segment (130) is missing", label="Segment 130", field=fname,
                value="(absent)", why=(f"Table 005 entry mode '{entry}' is a chip read, and Segment 130 is required for all EMV "
                                      "financial transactions."), fix="Add Segment 130 with the EMV chip data.", refs=["S11.8-130"])
    elif of != n and ctx["roots"].get(of) == EMV_ROOT and FOLLOW_ON_KIND.get(role) == "completion":
        rep.row(n, "Message", "-", "-", "Message type", root, "-", "Completion of an EMV authorization, sent without Segment 130",
                ["S10.14.2.4-REV", "EL5-PRE"], "REVIEW")
        rep.add(n, "REVIEW", "EMV_COMPLETION_NO_CHIP_DATA", "The completion of an EMV authorization carries no chip data",
                label="Segment 130", field=fname, value="(absent)",
                why=("ATL105 says EMV data is not required on reversal transactions, but it says nothing about completions. The "
                     "completion is a preauthorized Purchase/Capture (it carries the authorization's Approval Number)."),
                fix="Confirm with BUYPASS whether a completion of an EMV authorization is sent without Segment 130.",
                refs=["S10.14.2.4-REV", "EL5-PRE"])
    elif of != n and ctx["roots"].get(of) == EMV_ROOT:
        kind = {"timeout_reversal": "Time-out Reversal", "tor_retry": "Time-out Reversal", "cancellation": "Cancellation",
                "void_return": "Void of a return"}.get(role, "Reversal")
        rep.row(n, "Message", "-", "-", "Message type", root, "-", f"{kind} of an EMV transaction: no Segment 130 needed",
                ["S10.14.2.4-REV"], "PASS")
    else:
        rep.row(n, "Message", "-", "-", "Message type", root, "-", "Financial Transaction Request (no chip data)", ["S11.1-DS1"],
                "PASS")

    # ---- Data Section 1 (p.167)
    mfv = msg.get("MessageFormatVersionIdentifier", "")
    ok = mfv == "ATL105"
    rep.row(n, "Data Section 1", 1, "55", "Message Format Version Identifier", mfv, "ATL105", "Fixed 6 characters: ATL105",
            ["S11.1-DS1", "EL55"], "PASS" if ok else "FAIL")
    if not ok:
        rep.add(n, "FAIL", "MFV", "Wrong Message Format Version Identifier", label="Message Format Version Identifier",
                field=f"{fname} -> MessageFormatVersionIdentifier", value=mfv, why=f"'{mfv}' is not 'ATL105'.",
                fix="ATL105", refs=["EL55"])
    nos = msg.get("NumberofSegments", "")
    ok = nos.isdigit() and len(nos) <= 2 and int(nos) == len(segs)
    rep.row(n, "Data Section 1", 2, "63", "Number of Segments", nos, "-",
            f"Up to 2 digits; equals the number of segments sent ({len(segs)})", ["S11.1-DS1", "EL63"], "PASS" if ok else "FAIL")
    if not ok:
        rep.add(n, "FAIL", "NUMBER_OF_SEGMENTS", "Number of Segments does not match the segments sent", label="Number of Segments",
                field=f"{fname} -> NumberofSegments", value=nos, why=f"'{nos}' is declared, but {len(segs)} segment(s) are present.",
                fix=f"{len(segs):02d}", refs=["EL63"])

    if s100 is None:
        rep.add(n, "FAIL", "SEG100_MISSING", "Segment 100 is missing", label="Segment 100", field=fname, value="(absent)",
                why="Segment 100 is the only required segment on every Financial Transaction Request.", fix="Add Segment 100.",
                refs=["S12.1-REQ"])
        return
    if s111 is None:
        rep.add(n, "FAIL", "SEG111_MISSING", "Segment 111 (Table 031) is missing", label="Segment 111", field=fname,
                value="(absent)", why="Table 031 (terminal capability) must be sent on every transaction; it travels in Segment 111.",
                fix="Add Segment 111 with Table 031.", refs=["APPI-031"])
    extra = [s for s in segs if s not in ("Standard Message Data Segment", "Variable Information Data Segment",
                                          "EMV Request Data Segment")]
    if extra:
        rep.add(n, "REVIEW", "EXTRA_SEGMENTS", "Segments other than 100, 111 and 130 are present", label="segments", field=fname,
                value=", ".join(extra), why="These segments were not checked by this review.", fix="Confirm they are needed.")

    v = td_seg100_values(s100)
    orig = ctx["flats"].get(of, {}).get("NonfuelAmount") if of != n else None
    partial = ctx["expected"].get(of) == "F"
    placeholders = [name for _, _, key, name, _ in SEG100 if PLACEHOLDER.match(v[key])]
    # the test case's provisional values reproduce the declared Segment Length
    prov = {key: (str(tcr.get(name) or "") if name in placeholders else v[key]) for _, _, key, name, _ in SEG100}
    if placeholders:
        rep.issue_titles[issue] = {"split_tender": "Remaining-balance amount and Segment Length are wrong",
                                   "reversal": "Reversal amount, Approval Number and Segment Length are wrong",
                                   "completion": "Completion amount, Approval Number and Segment Length are wrong",
                                   "cancellation": "Cancellation amount, Approval Number and Segment Length are wrong"
                                   }.get(role, "Run-time values")

    # ---- Segment 100 (p.223-225)
    for fno, el, key, name, rc in SEG100:
        val, tcval = v[key], tcr.get(name)
        tcdisp = "" if tcval is None else str(tcval)
        path = f"{fname} -> Standard Message Data Segment -> {key}"

        if name in placeholders:
            rrefs = RUNTIME_REFS.get((role, name), [])
            todo = runtime.get(name, f"value from the step {of} response")
            reversal = role in tcv.SAME_SEQ_ROLES
            kind = FOLLOW_ON_KIND.get(role, "reversal")
            amount_fix = None
            if name == "Nonfuel Amount" and orig and partial and orig.isdigit():
                ex = int(orig) * 2 // 3
                amount_fix = (f"{kind.capitalize()} amount = the step {of} response Approved Amount without the leading zeros "
                              f"(for example {ex:09d} -> {ex})." if reversal else
                              f"Remaining balance = {orig} minus the step {of} response Approved Amount, leading zeros removed "
                              f"(for example Approved Amount {ex:09d} -> remainder {int(orig) - ex}).")
            rep.row(n, "100", fno, el, name, val, tcdisp or "-", f"Filled in at run time: {todo}", rrefs, "BLOCKED")
            why = (f"The field holds the instruction text '{val}' instead of a real {name}. BUYPASS cannot process this text, "
                   f"so the message cannot be sent until the real value from the step {of} response is filled in.")
            if name == "Nonfuel Amount" and "approved amount" not in val.lower():
                why += (f" Note: the step {of} response has no Nonfuel Amount field; the value comes from response Element 6 "
                        "Approved Amount (9 digits, zero-filled), with the leading zeros removed.")
            rep.add(n, "BLOCKED", "RUNTIME_PLACEHOLDER", f"{name} holds instruction text, not a value", label=name, field=path,
                    value=val, why=why, fix=amount_fix or f"Fill in at run time: {todo.rstrip('.')}.", refs=rrefs, issue=issue,
                    brief=f"{name} holds instruction text, not a value; it must be filled in from the step {of} response.")
            if name == "Nonfuel Amount" and tcdisp:
                probs = char_problems(tcdisp, "N", NON_AMEX_AMOUNT_MAX, "VARIABLE")
                if probs:
                    why = (f"'{tcdisp}' ({amount(tcdisp)}) has {len(tcdisp)} digits." if tcdisp.isdigit()
                           else f"'{tcdisp}' " + "; ".join(probs) + ".")
                    rep.add(n, "FAIL", "PROVISIONAL_AMOUNT_TOO_LONG", f"The test case value for {name} is too long",
                            label=f"{name} (test case value)", field=f"test case step {n} request -> {name} (used to build {fname})",
                            value=tcdisp,
                            why=(why + f" {prof['network']} amounts allow at most {NON_AMEX_AMOUNT_MAX} digits; 8 digits are allowed "
                                       "for American Express cards only."),
                            fix=amount_fix or f"Use the real amount from the step {of} response (at most {NON_AMEX_AMOUNT_MAX} digits).",
                            refs=["EL58"], issue=issue,
                            brief=f"'{tcdisp}' has {len(tcdisp)} digits; {prof['network']} allows at most {NON_AMEX_AMOUNT_MAX}.")
                if amount_fix and tcdisp.isdigit() and int(tcdisp) >= int(orig):
                    if reversal:
                        title = (f"The {kind} amount cannot be larger than the original "
                                 f"{'sale' if kind == 'reversal' else 'authorization'}")
                        why = (f"The {kind} must carry exactly the amount approved in step {of}. A partial approval is always "
                               f"less than the {amount(orig)} requested, so '{tcdisp}' ({amount(tcdisp)}) is impossible.")
                        arefs = ["S10.1.2.1-REV" if kind != "completion" else "S10.1.2.1-REMAIN", "EL121-LESS", "EL6"]
                    else:
                        title = "The remaining balance cannot be larger than the original sale"
                        why = (f"The original sale is {amount(orig)} ('{orig}'). After a partial approval the remaining balance is "
                               f"the sale minus the approved amount, so it is always less than {amount(orig)}; "
                               f"'{tcdisp}' ({amount(tcdisp)}) is impossible.")
                        arefs = ["S10.1.2.1-REMAIN", "EL121-LESS", "EL6"]
                    rep.add(n, "FAIL", "AMOUNT_NOT_POSSIBLE", title, label=f"{name} (test case value)",
                            field=f"test case step {n} request -> {name}", value=tcdisp, why=why, fix=amount_fix, refs=arefs,
                            issue=issue,
                            brief=f"{amount(tcdisp)} is larger than the {amount(orig)} sale, which is impossible after a partial approval.")
            continue

        if val == "":
            probs = []
            rule, rrefs = "Optional - not sent (the Field Separator is still sent)", ["S12.1-FS"]
            if el == "2" or (el == "12" and v["AccountNumber"]):
                if role in tcv.SAME_SEQ_ROLES:
                    need = "the Account Number and the expiry as YYMM (follow-on transaction)"
                    crefs = ["EL2", "S10.2.5.2-FOLLOW" if prof["debit"] else "S10.1.8.2"]
                elif entry in KEYED_ENTRY_MODES or entry == "10":
                    need = "the Account Number and the expiry as MMYY"
                    crefs = ["EL2", "EL12", "EL12-ALL"]
                else:
                    need = f"the data read from the card (entry mode {entry} = {PAN_ENTRY_DESC.get(entry, 'see Appendix J')})"
                    crefs = ["EL2", "EL12-READ", "EL12-TRACK2"]
                rep.row(n, "100", fno, el, name, "(empty)", tcdisp or "(not sent)", f"Required: {need}", crefs, "FAIL")
                missing = [x for x, gone in (("Account Number", not v["AccountNumber"]),
                                             ("Card Discretionary Block Data", not v["CardDiscretionaryBlockData"])) if gone]
                flags = (st.get("tc_flags") or {})
                awaiting = any("AWAITING_CLIENT_VALUE" in str(g) for g in flags.get("Account Number") or [])
                omitted = (st.get("tc_methods") or {}).get("Card Discretionary Block Data") == "omitted"
                rep.add(n, "FAIL", "CARD_DATA_MISSING", f"The card data is empty ({' and '.join(missing)})", label=" / ".join(missing),
                        field=f"{fname} -> Standard Message Data Segment -> " + ", ".join(x.replace(' ', '') for x in missing),
                        value="(empty)", refs=crefs,
                        why=(f"This {role.replace('_', ' ')} must carry {need}, but {' and '.join(missing)} "
                             + ("are" if len(missing) > 1 else "is") + " empty, so BUYPASS cannot identify the card."
                             + (f" The test case marks the Account Number AWAITING_CLIENT_VALUE: the {prof['name']} card profile was "
                                "never supplied, and the generator wrote an empty field instead of a value or an explicit placeholder."
                                if awaiting else "")
                             + (" The Card Discretionary Block Data is marked as an omitted conditional field, although it is "
                                "needed here." if omitted and "Card Discretionary Block Data" in missing else "")),
                        fix=(f"Fill in {need} from the client's {prof['name']} test card. Until the card profile is supplied, keep "
                             "the test case blocked and write an explicit placeholder instead of an empty field."),
                        brief=f"{' and '.join(missing)} empty – no card data, so the message cannot be processed.")
                continue
            if el == "12":
                rep.row(n, "100", fno, el, name, "(empty)", tcdisp or "(not sent)",
                        "Required with the Account Number (see the card data finding)", ["EL12-READ"], "FAIL")
                continue
            if el == "33" and prof["debit"] and role not in tcv.SAME_SEQ_ROLES and st.get("code") != "8":
                tags = {t: x for t, _, x in parse_tlv(s130.get("EMVChipData", ""))[1]} if s130 else {}
                cvm, prefs = tags.get("9F34", ""), ["S10.2.1.3-PIN", "S10.2.1.3-DUKPT", "EL33-REQ", "EL33-FMT"]
                rep.row(n, "100", fno, el, name, "(empty)", tcdisp or "(not sent)",
                        "Required for debit: DUKPT Key Serial Number + PIN block", prefs, "FAIL")
                rep.add(n, "FAIL", "DEBIT_PIN_MISSING", "The debit PIN block (Element 33) is empty", label=name, field=path,
                        value="(empty)", refs=prefs,
                        why=("ATL105 requires PIN entry, DUKPT-encrypted, on every debit transaction except the Purchase Reversal, "
                             f"and Element 33 is required for debit. This {role.replace('_', ' ')} (Transaction Type "
                             f"{st.get('code')}) sends no PIN block"
                             + (f", although its chip data reports an online PIN (Tag 9F34 = {cvm})" if cvm[:2] in ("02", "42") else "")
                             + "."),
                        fix=("Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until "
                             "they are supplied, mark the test case blocked (needs client input)."),
                        brief="No PIN block on a debit transaction that needs one.")
                continue
            if el == "5":
                if role in APPROVAL_ROLES:
                    rule, rrefs = approval_rule(role)
                    probs.append(f"the Approval Number is required on a {role.replace('_', ' ')}")
                elif role in TOR_ROLES:
                    rule, rrefs = ("Not sent: a TOR follows a transaction whose response never arrived; it is matched by "
                                   "Sequence Number, Account Number and amount", ["S2.3-TOR", "S2.3.1-MATCH"])
                elif role in ("purchase", "split_tender", "next_transaction", "subsequent", "resubmitted", "moto_purchase"):
                    rule, rrefs = "Not sent on a purchase (it would make the purchase preauthorized)", ["EL5-PRE"]
                else:
                    rule, rrefs = "Not sent on a new transaction", ["EL5-PRE"]
            if rc == "R":
                rule = "Required"
                probs.append(f"{name} is required")
            if tcval not in (None, ""):
                probs.append(f"the test case has '{tcdisp}'")
            rep.row(n, "100", fno, el, name, "(empty)", tcdisp or "(not sent)", rule, rrefs, "FAIL" if probs else "PASS")
            if probs:
                rep.add(n, "FAIL", "FIELD_EMPTY", f"{name} is empty", label=name, field=path, value="(empty)",
                        why="The field is empty, but " + "; ".join(probs) + ".", fix=tcdisp or f"Populate {name}.", refs=rrefs)
            continue

        ctype, mx, mode = ref_constraint(ref, el)
        if el == "2":
            ctype, mx, mode = "ANS", 24, "VARIABLE"
        elif el == "12" and "^" in val:
            ctype = "ANS"  # Track 1 data keeps its '^' separator and the cardholder name (E12 p.371)
        elif el in ("41", "58", "99"):
            ctype, mx, mode = "N", NON_AMEX_AMOUNT_MAX, "VARIABLE"
        elif el == "84":
            ctype, mx, mode = "N", 3, "FIXED"
        probs = char_problems(val, ctype, mx, mode)
        rule, rrefs, fix, result = f"{ctype}, {(mode or '').lower()} length {mx}", list(FIELD_REFS.get(el, [])), None, None
        if el in ("41", "99", "17") and val.isdigit():
            rule += f" with two assumed decimals: '{val}' = {amount(val)}"
        if el == "85":
            rule, rrefs = "Fixed value 100", ["S12.1-TYPE"]
            if val != "100":
                probs.append("must be 100")
                fix = "100"
        elif el == "84":
            calc = seg100_length(prov)
            rule = f"Length of all 17 fields + 17 Field Separators = {calc:03d}"
            rrefs = ["S12.1-LEN", "EL84"]
            if placeholders:
                rule += " (using the test case value for " + ", ".join(placeholders) + ")"
            if val.isdigit() and int(val) != calc:
                probs.append(f"is declared, but the fields add up to {calc:03d}")
                fix = f"{calc:03d}"
            elif placeholders:
                rng = length_range(prov, placeholders, orig if partial else None)
                if rng and not rng[1] <= int(val) <= rng[2]:
                    base, lo, hi = rng
                    with_approval = "Approval Number" in placeholders
                    used = ", ".join(f"{nm} '{prov[k]}'" for _, _, k, nm, _ in SEG100 if nm in placeholders)
                    real = ([f"amount 1 to {RUNTIME_LENGTHS['Nonfuel Amount'](orig)[1]} digits"]
                            if "Nonfuel Amount" in placeholders else []) + (
                        ["Approval Number always 6 characters"] if with_approval else [])
                    if "Nonfuel Amount" in placeholders:
                        ex = int(orig) * 2 // 3
                        ex_amt = ex if role in tcv.SAME_SEQ_ROLES else int(orig) - ex
                        ex_len = base + len(str(ex_amt)) + (6 if with_approval else 0)
                        example = (f"Approved Amount {ex:09d} -> amount {ex_amt}"
                                   + (" plus a 6-character Approval Number" if with_approval else "") + f" -> Segment Length {ex_len:03d}")
                    else:
                        example = f"Segment Length {lo:03d}"
                    rep.add(n, "FAIL", "SEGMENT_LENGTH_IMPOSSIBLE", "Segment Length was calculated from the invalid test case value",
                            label="Segment Length", field=path, value=val,
                            why=(f"'{val}' is the length you get with the test case value(s) {used}. With real values "
                                 f"({'; '.join(real)}) the Segment Length can only be {lo:03d} to {hi:03d}, so '{val}' can never be correct."),
                            fix=f"Recalculate the Segment Length after filling in the real values (for example {example}).",
                            refs=["S12.1-LEN", "EL84"], issue=issue,
                            brief=f"'{val}' was calculated from that value; with real values it can only be {lo:03d} to {hi:03d}.")
                    result = "FAIL"
                elif rng:
                    rule += (f"; with the real run-time value(s) it is {rng[1]:03d}" + (f" to {rng[2]:03d}" if rng[2] != rng[1] else "")
                             + " - the declared value fits")
                else:
                    rep.add(n, "BLOCKED", "SEGMENT_LENGTH_RUNTIME", "Segment Length must be recalculated at run time",
                            label="Segment Length", field=path, value=val,
                            why=f"'{val}' depends on the run-time value(s) {', '.join(placeholders)}.",
                            fix="Recalculate the Segment Length after filling in the real values.", refs=["S12.1-LEN", "EL84"],
                            issue=issue)
                    result = "BLOCKED"
        elif el == "44":
            rule, rrefs = f"1 character: '{val}' = {INFO_BYTE.get(val, 'not a valid code')}", ["EL44"]
            if val not in INFO_BYTE:
                probs.append("is not a valid Information Byte (?, 0 or 1)")
        elif el == "102":
            dev, state, rest = val[:2], val[2:4], val[4:]
            info = states.get(state)
            rule = (f"Device Type '{dev}' + State Code '{state}'"
                    + (f" = {info[0]} ({info[1]}), {info[3]}" if info else " (not in Appendix D)")
                    + f" + Merchant Number and Device Number '{rest}'")
            rrefs = ["EL102", "EL102-STATE"] + ([f"APPD-{state}"] if info else [])
            if not info:
                probs.append(f"has State Code '{state}' (positions 3-4), which is not in Appendix D")
            if len(rest) < 7:
                probs.append("is too short for a 6-15 character Merchant Number plus a Device Number")
        elif el == "78":
            tt, ct = val[:1], val[1:]
            rule = (f"Transaction Type '{tt}' = {tcv.TXN_TYPES.get(tt, 'not valid')}; Card Type '{ct}' = "
                    + (prof["card_desc"] if ct == prof["card_type"] else "see Appendix E"))
            rrefs = ["EL78"] + [r for r in (f"APPG-{tt}",) if r in refs.catalog] + [prof["card_ref"]]
            exp = PROMPT_TXN_BY_ROLE.get(role, set())
            if tt not in tcv.TXN_TYPES:
                probs.append(f"has Transaction Type '{tt}', which is not in Appendix G")
            elif exp and tt not in exp:
                probs.append(f"has Transaction Type '{tt}', which does not fit a {role.replace('_', ' ')} step "
                             f"(expected {' or '.join(sorted(exp))})")
            if ct != prof["card_type"]:
                probs.append(f"has Card Type '{ct}', not {prof['card_type']} ({prof['card_desc']})")
            if tt != st.get("code"):
                probs.append(f"does not match the test case step's Transaction Type '{st.get('code')}'")
        elif el == "2":
            pan = val[1:] if val[:1] == "B" and val[1:].isdigit() else val
            rule = "Up to 24 characters; valid check digit" + (f" and {prof['bin_text']} (card-industry rule)" if prof["bin_ok"] else "")
            if pan != val:
                rule = "Track 1: format code 'B' + card number. " + rule
            rrefs = ["EL2"]
            if not pan.isdigit() or not tcv.luhn_ok(pan):
                probs.append("fails the card-number check digit (Luhn)")
            elif prof["bin_ok"] and not prof["bin_ok"](pan):
                probs.append(f"has BIN {pan[:6]}, which is not in the {prof['network']} range")
        elif el == "12":
            expired_test = "EXPIRED" in tc.get("template_id", "")
            if role in tcv.SAME_SEQ_ROLES:
                o = ctx["flats"].get(of, {}).get("CardDiscretionaryBlockData", "")
                # a follow-on of a follow-on (e.g. TOR of a completion): the original already holds YYMM
                o_follow = next((s["role"] for s in tc["steps"] if s["step"] == of), "") in tcv.SAME_SEQ_ROLES
                keyed = bool(re.fullmatch(r"\d{4}", o)) and not o_follow
                yymm = (o[2:] + o[:2] if keyed else o[:4]) if re.fullmatch(r"\d{4,}", o) else None
                rule = ("Follow-on transaction: expiry as YYMM only"
                        + (f" (original {'MMYY' if keyed else 'YYMM' if o_follow else 'track data'} {o} -> {yymm})" if yymm else ""))
                rrefs = ["S10.1.8.2", "S10.1.1.2-FOLLOW"]
                if re.fullmatch(r"\d{4}\d{3}\S+", val):
                    result = "FAIL"
                    rep.add(n, "FAIL", "FOLLOW_ON_FULL_TRACK", "A follow-on message carries full track data", label=name, field=path,
                            value=val, refs=["S10.1.1.2-FOLLOW", "S10.1.8.1-STORE", "S10.1.8.2"],
                            why=(f"This {role.replace('_', ' ')} is formatted from stored card data after the original transaction, "
                                 f"but '{val}' is full Track 2 data (expiry, service code and discretionary data). Follow-up "
                                 "transactions use truncated Track 2 (Account Number = YYMM), and full magnetic stripe data must not be "
                                 "stored for later messages."),
                            fix=f"'{val[:4]}' (the expiry YYMM only)", brief=f"'{val}' is full track data; send only YYMM '{val[:4]}'.")
                elif not re.fullmatch(r"\d{4}", val):
                    probs.append("is not a 4-digit YYMM expiry date")
                elif not 1 <= int(val[2:]) <= 12:
                    probs.append(f"has month '{val[2:]}', which is not 01-12")
                elif yymm and val != yymm:
                    probs.append(f"does not match the original expiry {o} (YYMM should be {yymm})")
                    fix = yymm
                rep.add(n, "INFO", "FOLLOW_ON_EQUALS_SEPARATOR", "Join Account Number and expiry with '=' in the wire message",
                        label="Account Number / Card Discretionary Block Data",
                        field=f"{fname} -> AccountNumber, CardDiscretionaryBlockData", value=f"{v['AccountNumber']} / {val}",
                        why=("The JSON keeps Account Number and the expiry as separate fields. In the real message for this "
                             "follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field "
                             "Separator. The Segment Length is the same either way."),
                        fix=f"The message builder sends {v['AccountNumber']}={val}.", refs=["S10.1.8.2"])
            elif entry in KEYED_ENTRY_MODES or entry == "10":
                stored = entry == "10"
                rrefs = ["EL12", "S10.1.1.3"] + ([f"APPJ-{entry}"] if entry != "01" else [])
                if re.fullmatch(r"\d{4}\d{3}\S+", val):
                    result = "FAIL"
                    rule = f"Entry mode {entry} ({PAN_ENTRY_DESC.get(entry)}): no card read, so expiry MMYY only"
                    rep.add(n, "FAIL", "KEYED_ENTRY_TRACK_DATA", "Track data is sent with an entry mode that does not read the card",
                            label=name, field=path, value=val, refs=["EL12", "S10.1.1.3", f"APPJ-{entry}"],
                            why=(f"Table 005 entry mode '{entry}' = {PAN_ENTRY_DESC.get(entry)}: the card number is "
                                 f"{'taken from stored credentials' if stored else 'keyed in'}, so no magnetic stripe or chip data "
                                 f"is read. '{val}' is Track 2 data (expiry {val[:4]}, service code {val[4:7]}, discretionary data). "
                                 "For a keyed card ATL105 sends only the expiration date, as MMYY."),
                            fix=f"'{val[2:4]}{val[:2]}' (expiry MMYY), or a swipe entry mode if the card was really read",
                            brief=f"Entry mode {entry} means no card read, but '{val}' is track data.")
                elif not re.fullmatch(r"\d{4}", val):
                    probs.append("is not a 4-digit MMYY expiry date")
                elif not 1 <= int(val[:2]) <= 12:
                    probs.append(f"has month '{val[:2]}', which is not 01-12")
                else:
                    rule = f"{'Stored credential' if stored else 'Keyed card'}: expiry MMYY = {expiry(val)}"
                    if (2000 + int(val[2:]), int(val[:2])) < (TODAY.year, TODAY.month) and not expired_test:
                        rep.add(n, "REVIEW", "CARD_EXPIRED", "The card is expired", label=name, field=path, value=val,
                                why=f"{expiry(val)} is before {TODAY:%B %Y}.",
                                fix="Use a card that is not expired, unless an expired-card test is intended.", refs=["EL12"])
                    if stored and not any(f["rule"] == "COF_EXPIRY_FORMAT" for f in rep.findings):
                        rep.add(None, "REVIEW", "COF_EXPIRY_FORMAT", "Expiry format for stored credentials is not stated",
                                label=name, field=f"{name} (entry mode 10 steps)", value=val, refs=["EL12", "S10.1.8.2"],
                                why=("The stored-credential steps send the expiry as MMYY, the format for manually entered cards. "
                                     "ATL105 also says that card data the merchant stored is sent as Account Number = YYMM. "
                                     "Entry mode 10 uses stored credentials, and the spec does not say which of the two applies."),
                                fix="Confirm the expiry format for entry mode 10 with BUYPASS.")
            else:
                track1 = "^" in val
                total, limit = len(v["AccountNumber"]) + 1 + len(val), 76 if track1 else 37
                m = re.match(r"(\d{2})(\d{2})(\d{3})", val.split("^", 1)[1] if track1 else val)
                rrefs = ["EL12-TRACK1" if track1 else "EL12-TRACK2", "EL12-MAX"]
                if not m or not 1 <= int(m.group(2)) <= 12:
                    probs.append(f"does not start with an expiry (YYMM) and a 3-digit service code, as {'Track 1' if track1 else 'Track 2'} "
                                 "data does")
                else:
                    yy, mm, svc = m.groups()
                    chip_card = svc[0] in "26"
                    rule = (f"{'Track 1' if track1 else 'Track 2'} data: expiry {yy}{mm} = {expiry(mm + yy)}; service code {svc} "
                            f"({'chip card' if chip_card else 'no chip'}, ISO/IEC 7813); Account Number + '{'^' if track1 else '='}' "
                            f"+ this field = {total} characters (at most {limit})")
                    if (2000 + int(yy), int(mm)) < (TODAY.year, TODAY.month) and not expired_test:
                        rep.add(n, "REVIEW", "CARD_EXPIRED", "The card is expired", label=name, field=path, value=val,
                                why=f"The track data expiry {yy}{mm} ({expiry(mm + yy)}) is before {TODAY:%B %Y}.",
                                fix="Use a card that is not expired, unless an expired-card test is intended.", refs=["EL12-EXP"])
                    if entry in EMV_ENTRY_MODES and not chip_card:
                        rep.add(n, "REVIEW", "SERVICE_CODE_NO_CHIP", "Chip read, but the card data says the card has no chip",
                                label=name, field=path, value=val, refs=["APPJ-05", "APPJ-07"],
                                why=f"Table 005 entry mode '{entry}' is a chip read, but service code {svc} starts with {svc[0]} (no chip).",
                                fix="Use the Track 2 equivalent data of a chip card (service code 2xx).")
                    if entry == "90" and chip_card and t031 in CHIP_CAPABLE_031:
                        rep.add(n, "REVIEW", "CHIP_CARD_SWIPED", "A chip card is swiped at a chip-capable terminal", label=name,
                                field=path, value=val, refs=["S10.14.4-80", "S10.14.4-90"],
                                why=(f"Service code {svc} marks a chip card and Table 031 '{t031}' a chip-capable terminal; ATL105 "
                                     "sends such a swipe as fallback (80), not as a regular swipe (90)."),
                                fix="Use a magnetic-stripe-only card (service code 1xx) or send entry mode 80.")
                if total > limit:
                    probs.append(f"together with the Account Number is {total} characters; {'Track 1' if track1 else 'Track 2'} "
                                 f"allows at most {limit}")
        elif el == "33" and prof["debit"] and role in tcv.SAME_SEQ_ROLES:
            rule, rrefs, result = "Not sent: PIN block data is not included in debit follow-on transactions", ["S10.2.5.1-PIN"], "FAIL"
            rep.add(n, "FAIL", "FOLLOW_ON_PIN_PRESENT", "A debit follow-on message carries a PIN block", label=name, field=path,
                    value=val, refs=["S10.2.5.1-PIN", "S10.2.1.3-PIN"],
                    why=(f"This {role.replace('_', ' ')} is a follow-on transaction. ATL105 does not include PIN block data in debit "
                         "follow-on transactions (completions, reversals)."),
                    fix="Send Element 33 empty on this step.", brief="Debit follow-on transactions carry no PIN block.")
        elif el == "17":
            rule, rrefs = f"Cash back amount, not part of the Nonfuel Amount: '{val}' = {amount(val)}", ["EL17", "EL58-PURPOSE"]
        elif el == "58":
            rule = f"Digits only, at most {NON_AMEX_AMOUNT_MAX} for {prof['network']} (8 only for Amex): '{val}' = {amount(val)}"
            rrefs = ["EL58"]
        elif el == "86":
            rule, rrefs = "6 digits, 000001-999999", ["EL86-FMT"]
            if val.isdigit() and int(val) == 0:
                probs.append("is 000000, which is not allowed")
        elif el == "5":
            if role in APPROVAL_ROLES:
                rule, rrefs = approval_rule(role)
            elif role in TOR_ROLES:
                rule, rrefs = "Not sent on a Time-out Reversal", ["S2.3-TOR", "S2.3.1-MATCH"]
                probs.append("is sent on a Time-out Reversal, but the original never received a response, so there is no Approval Number")
            else:
                rule, rrefs = "Not sent on a purchase", ["EL5-PRE"]
                probs.append("is sent on a purchase, which makes it preauthorized, so BUYPASS does not go out for authorization")
        elif el == "121":
            rule, rrefs = f"'{val}' = {PAI.get(val, 'not valid')}", ["EL121"]
            if val not in PAI:
                probs.append("is not 0, 1 or 5")
            elif val == "5":
                probs.append("is 5, which is only for American Express prepaid cards")
        differs = tcval is not None and str(tcval) != val
        if differs and probs:
            probs.append(f"differs from the test case request value '{tcdisp}'")
            fix = fix or tcdisp
        if probs:
            result = "FAIL"
            rep.add(n, "FAIL", f"FIELD_{el}", f"{name} is not valid", label=name, field=path, value=val,
                    why=f"'{val}' " + "; ".join(probs) + ".", fix=fix or f"A value that meets: {rule}.", refs=rrefs)
        elif differs:
            result = "FAIL"
            dup = next((s for s in tc["steps"] if el == "86" and s["step"] != n
                        and (ctx["flats"].get(s["step"]) or {}).get("SequenceNumber") == val), None)
            rep.add(n, "FAIL", "TD_DIFFERS_FROM_TC", f"{name} in the test data differs from the test case", label=name, field=path,
                    value=val, refs=rrefs + (["EL86-UNIQUE"] if dup else []),
                    why=(f"The test data file has '{val}', but the test case request (the source the file is generated from) has "
                         f"'{tcdisp}'. The two are out of step, so the file does not send what the test case describes."
                         + (f" '{val}' is already the Sequence Number of step {dup['step']} ({dup['role'].replace('_', ' ')}) on "
                            "the same terminal, so the terminal would send one Sequence Number twice." if dup else "")),
                    fix=f"'{tcdisp}' (regenerate the file from the test case)", brief=f"File '{val}', test case '{tcdisp}'.")
        rep.row(n, "100", fno, el, name, val, tcdisp or "-", rule, rrefs, result or "PASS")

    # ---- Segment 111 (p.246)
    if s111:
        typ = s111.get("SegmentType", "")
        rep.row(n, "111", 1, "85", "Segment Type", typ, "111", "Fixed value 111", ["S12.10-TYPE"], "PASS" if typ == "111" else "FAIL")
        if typ != "111":
            rep.add(n, "FAIL", "SEG111_TYPE", "Segment 111 has the wrong Segment Type", label="Segment Type (111)",
                    field=f"{fname} -> Variable Information Data Segment -> SegmentType", value=typ, why=f"'{typ}' is not 111.",
                    fix="111", refs=["S12.10-TYPE"])
        tables = sum(len(a) + len(b) + len(c) for a, b, c in vi)
        calc = 3 + 3 + tables + 3
        sl = s111.get("SegmentLength", "")
        ok = sl.isdigit() and len(sl) == 3 and int(sl) == calc
        rep.row(n, "111", 2, "84", "Segment Length", sl, "-",
                f"3 (type) + 3 (length) + {tables} (tables) + 3 Field Separators = {calc:03d}", ["S12.10-FS", "EL84"],
                "PASS" if ok else "FAIL")
        if not ok:
            rep.add(n, "FAIL", "SEG111_LENGTH", "Segment 111 length is wrong", label="Segment Length (111)",
                    field=f"{fname} -> Variable Information Data Segment -> SegmentLength", value=sl,
                    why=f"'{sl}' is declared, but the segment adds up to {calc:03d}.", fix=f"{calc:03d}", refs=["S12.10-FS", "EL84"])
        for i, (ind, ln, val) in enumerate(vi, 1):
            vpath = f"{fname} -> Variable Information Data Segment -> VariableInformationSection[{i - 1}]"
            rep.row(n, "111", f"3 [{i}]", "111", "Variable Information Indicator", ind, "-", f"3-character table ID (Table {ind})",
                    ["EL111"], "PASS" if re.fullmatch(r"[A-Za-z0-9]{3}", ind) else "FAIL")
            ok = ln.isdigit() and len(ln) == 3 and int(ln) == len(val)
            rep.row(n, "111", f"4 [{i}]", "112", "Variable Information Length", ln, "-",
                    f"3 digits = length of the table data ({len(val):03d})", ["S12.10-LEN"], "PASS" if ok else "FAIL")
            if not ok:
                rep.add(n, "FAIL", "VARINFO_LENGTH", f"Table {ind} length is wrong", label=f"Table {ind} length", field=vpath,
                        value=ln, why=f"'{ln}' is declared, but the table data '{val}' is {len(val)} characters long.",
                        fix=f"{len(val):03d}", refs=["S12.10-LEN"])
            probs, rule, rrefs, dfail = [], "", [], False
            if ind == "005":
                pe, pin = val[:2], val[2:3]
                rule = (f"'{pe}' = {PAN_ENTRY_DESC.get(pe, 'see Appendix J')}; terminal capability '{pin}' = "
                        f"{PIN_CAP_DESC.get(pin, 'see Appendix J')}")
                rrefs = ["APPI-005"] + [r for r in (f"APPJ-{pe}", f"APPJ-PIN{pin}") if r in refs.catalog]
                if len(val) != 3:
                    probs.append("must be 3 digits")
                if pe not in tcv.PAN_ENTRY_MODES:
                    probs.append(f"has PAN entry mode '{pe}', which is not in Appendix J")
                if pin not in tcv.PIN_CAPABILITY:
                    probs.append(f"has terminal capability '{pin}', which is not in Appendix J")
                exp = tcv.EXPECTED_ENTRY_MODE.get(tc["dimensions"].get("entry_mode"))
                if exp and pe not in exp and role not in tcv.SAME_SEQ_ROLES:
                    probs.append(f"has entry mode '{pe}', which does not fit the {tc['dimensions'].get('entry_mode')} scenario "
                                 f"(expected {'/'.join(sorted(exp))})")
                if prof["debit"] and pe in KEYED_ENTRY_MODES and role not in tcv.SAME_SEQ_ROLES:
                    rep.add(n, "FAIL", "DEBIT_MANUAL_ENTRY", "Manual (keyed) entry is used for a debit card", label="Table 005 entry mode",
                            field=vpath, value=val, refs=["S10.2.1.4"] + [r for r in (f"APPJ-{pe}",) if r in refs.catalog],
                            why=(f"Table 005 entry mode '{pe}' = {PAN_ENTRY_DESC.get(pe)}: the card number is keyed in. ATL105 does "
                                 "not allow manual entry on a debit card transaction; when the card cannot be read after three "
                                 "attempts, the transaction is ended" + (", so a chip fallback for debit can only swipe the card "
                                                                         "(entry mode 80)" if pe == "79" else "") + "."),
                            fix=("Use a card-read entry mode – 80 (chip fallback to swipe) for the fallback scenario – or drop "
                                 "the keyed debit test case."),
                            brief=f"Entry mode {pe} is keyed entry, which ATL105 does not allow for debit.")
                    dfail = True
            elif ind == "031":
                rule = f"'{val}' = {T031_DESC.get(val, 'not a valid code')}; required on every transaction"
                rrefs = ["APPI-031"] + [r for r in (f"APPI-031-{val}",) if r in refs.catalog]
                if val not in T031_DESC:
                    probs.append("is not a valid Table 031 code")
            elif ind == "030":
                parts = [f"'{val[i:i + 1]}' = {codes.get(val[i:i + 1], 'not a valid code')}" for i, (_, codes) in enumerate(T030_POS)]
                rule = (f"10 digits (Appendix Q): Terminal Class {', '.join(parts[:3])}; Presentation Type "
                        f"{', '.join(parts[3:7])}; Security Condition {parts[7]}; Terminal Type '{val[8:10]}' = "
                        f"{T030_TYPE.get(val[8:10], 'not a valid code')}")
                rrefs = ["APPI-030", "APPQ", "APPQ-CLASS", "APPQ-PRES", "APPQ-SEC", "APPQ-TYPE"] + [
                    r for r in (f"APPQ-{val[:3]}-{val[8:10]}",) if r in refs.catalog]
                if not re.fullmatch(r"\d{10}", val):
                    probs.append("must be 10 digits")
                else:
                    probs += [f"has {name} '{val[i]}', which is not a valid Appendix Q value"
                              for i, (name, codes) in enumerate(T030_POS) if val[i] not in codes]
                    if val[8:10] not in T030_TYPE:
                        probs.append(f"has Terminal Type '{val[8:10]}', which is not a valid Appendix Q value")
            expected = tcr.get(f"VI {ind}")
            if expected and expected != val:
                probs.append(f"differs from the test case value '{expected}'")
            rep.row(n, "111", f"5 [{i}]", "113", f"Variable Information (Table {ind})", val, expected or "-", rule, rrefs,
                    "FAIL" if probs or dfail else "PASS")
            if probs:
                rep.add(n, "FAIL", f"TABLE_{ind}", f"Table {ind} value is not valid", label=f"Table {ind}", field=vpath, value=val,
                        why=f"'{val}' " + "; ".join(probs) + ".", fix=expected or "A valid code.", refs=rrefs)
        if t031 is None:
            rep.add(n, "FAIL", "TABLE_031_MISSING", "Table 031 (terminal capability) is missing", label="Table 031",
                    field=f"{fname} -> Variable Information Data Segment", value="(absent)",
                    why="Table 031 must be sent on every transaction.",
                    fix="Add Table 031 with the terminal's certified capability.", refs=["APPI-031"])
        if t005 and t031:
            pin = t005[2:3]
            no_chip = "no chip" in (tc.get("execution") or "").lower()
            if t031 in CHIP_CAPABLE_031 and (pin == "8" or no_chip):
                why = [f"Table 005 '{t005}' says: {PAN_ENTRY_DESC.get(t005[:2], t005[:2])} entry on a terminal with code {pin} "
                       f"({PIN_CAP_DESC.get(pin, pin)})"
                       + (", which Appendix J note 1 reserves for a terminal with a contactless magstripe reader and no chip."
                          if pin == "8" else "."),
                       f"Table 031 '{t031}' says: {T031_DESC[t031]}, so the terminal has a chip reader.",
                       "Both cannot be true for the same terminal: a chip-capable terminal must send 1 (not 8), and a terminal "
                       f"without a chip cannot be {t031}."]
                if no_chip:
                    why.append(f"The test case itself says: \"{tc['execution']}\".")
                good = "07" if pin == "8" else "02"  # p.569 no-chip codes: 07 contactless magstripe, 02 magstripe
                brief = (f"Table 005 = {t005} says the terminal has a contactless magstripe reader and no chip, but Table 031 = {t031} "
                         f"says {T031_DESC[t031]}" if pin == "8" else f"Table 031 = {t031} says {T031_DESC[t031]}")
                brief += "; the test case says the terminal has no chip reader." if no_chip else "."
                rep.add(n, "FAIL", "TERMINAL_CAPABILITY_CONFLICT", "The terminal is described two different ways in the same message",
                        label="Table 031 (terminal capability)",
                        field=f"{fname} -> Variable Information Data Segment -> Table 005 and Table 031",
                        value=f"Table 005 = {t005}, Table 031 = {t031}", why=" ".join(why),
                        fix=(f"Keep Table 005 = {t005} and change Table 031 to {good}, which means {T031_DESC[good]}. Use a "
                             "separate RFID terminal, not the hybrid chip terminal used by the other test cases."),
                        refs=[r for r in (f"APPI-031-{t031}", "APPI-031-07") if r in refs.catalog]
                        + (["APPJ-NOTE1", "APPJ-PIN8", "APPJ-EMV1"] if pin == "8" else []) + ["APPI-031-STABLE"],
                        issue="TERMINAL_CAPABILITY", brief=brief)
                rep.issue_titles["TERMINAL_CAPABILITY"] = "The terminal is described two different ways in the same message"

    # ---- tables required by the kind of transaction (Appendix I, Section 10.13.2.4)
    tables = {x[0]: x[2] for x in vi}
    ctx["tables"][n] = tables
    vpath = f"{fname} -> Variable Information Data Segment"
    cof = entry == "10" or tc.get("template_id", "").startswith("T7-")
    kinds = [k for k, on in (("an EMV transaction (Segment 130 is sent)", s130 is not None),
                             ("a Credential on File transaction", cof)) if on]
    if kinds and "030" not in tables:
        rep.add(n, "FAIL", "TABLE_030_MISSING", "Table 030 (POS Condition Code) is missing", label="Table 030", field=vpath,
                value=f"tables sent: {', '.join(tables) or 'none'}", refs=["APPI-030"],
                why=(f"This step is {' and '.join(kinds)}. Table 030 identifies the device class, presentation data, security "
                     "condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions."),
                fix="Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.",
                brief=f"Required for {' and '.join(kinds)}, but only Tables {', '.join(tables)} are sent.")
    if entry == "10":
        mc = prof["network"] == "MasterCard"
        checks = [("Table 032 sub-table 08 = 'C' (Credential on File)", "08001C" in tables.get("032", "")),
                  ("Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M')", "02" in tables.get("049", "")[:2])]
        if mc:
            checks.append(("Table 056 sub-table 09 (MasterCard MIT/CIT category)", "09" in tables.get("056", "")))
        missing = [t for t, ok in checks if not ok]
        if missing:
            rep.add(n, "FAIL", "COF_INDICATORS_MISSING", "The Credential on File indicators are missing", label="Credential on File tables",
                    field=vpath, value=f"tables sent: {', '.join(tables)}",
                    refs=["APPI-032-08", "APPI-049-02"] + (["S10.13.2.4.2-MC"] if mc else []) + ["APPJ-10"],
                    why=("Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the "
                         "Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all "
                         "subsequent COF transactions" + (" and, for MasterCard, the MIT/CIT category" if mc else "")
                         + ". Missing: " + "; ".join(missing) + "."),
                    fix="Add " + "; ".join(missing) + ".", brief="Missing: " + "; ".join(missing) + ".")
    elif cof and n == tc["steps"][0]["step"] and "08001I" not in tables.get("032", ""):
        rep.add(n, "FAIL", "COF_INITIAL_INDICATOR_MISSING", "The first stored-credential transaction does not say 'Initial'",
                label="Table 032 sub-table 08", field=vpath, value=f"tables sent: {', '.join(tables)}", refs=["APPI-032-08", "APPJ-10-NOTE"],
                why=("This test stores the card for later use (its later steps use entry mode 10). ATL105 requires the Initial/Credential "
                     "on File indicator 'I' on the transaction that establishes the stored credential; Table 032 is not sent."),
                fix="Add Table 032 sub-table 08 with value 'I' (Initial).",
                brief="The credential-storing transaction has no Table 032 sub-table 08 = 'I'.")

    if s130 is not None:
        validate_seg130(rep, tc, st, s130, v, ctx, entry, orig, partial)
    ctx["flats"][n], ctx["entries"][n], ctx["t031"][n], ctx["t005"][n] = v, entry, t031, t005


def validate_seg130(rep, tc, st, seg, v, ctx, entry, orig, partial):
    """EMV Request Data Segment (p.270-271), Elements 187-192 and 118 (p.439, p.476-480), Appendix R and T."""
    n, role, fname = st["step"], st["role"], st["td"]
    of, tce = st.get("of", n), st.get("tc_emv", {})
    path = f"{fname} -> EMV Request Data Segment"
    val = {k: str(seg.get(k, "")) for _, _, k, _ in S130}
    chip = val["EMVChipData"]
    declared, tags, tlv_probs = parse_tlv(chip)
    chip_bytes = len(chip) // 2 if declared is not None else 0
    tagmap = {t: x for t, _, x in tags}
    rep.emv[n], ctx["emv"][n] = tags, tagmap

    def row(fno, el, name, value, rule, rrefs, ok):
        tcval = tce.get(name)
        shown_tc = "-" if tcval is None else (tcval if len(str(tcval)) <= 32 else f"{str(tcval)[:16]}... ({len(str(tcval))})")
        rep.row(n, "130", fno, el, name, value, shown_tc, rule, rrefs, ok if isinstance(ok, str) else ("PASS" if ok else "FAIL"))

    def fail(rule, title, key, value, why, fix, rrefs, **kw):
        rep.add(n, "FAIL", rule, title, label=title.split(" (")[0], field=f"{path} -> {key}", value=value, why=why, fix=fix,
                refs=rrefs, **kw)

    def review_once(rule, title, key, value, why, fix, rrefs):
        if not any(f["rule"] == rule for f in rep.findings):
            rep.add(None, "REVIEW", rule, title, label=title, field=f"EMV Request Data Segment -> {key} (every EMV request)",
                    value=value, why=why, fix=fix, refs=rrefs)

    for _, el, key, name in S130:
        tcval = tce.get(name)
        if tcval is not None and key not in ("SegmentLength",) and str(tcval) != val[key]:
            fail(f"FIELD_{el}_FIDELITY", f"{name} differs from the test case", key, val[key],
                 f"The test data value differs from the test case request value '{tcval}'.", str(tcval), [])

    typ = val["SegmentType"]
    row(1, "85", "Segment Type", typ, "Fixed value 130", ["S12.20-TYPE"], typ == "130")
    if typ != "130":
        fail("SEG130_TYPE", "Segment 130 has the wrong Segment Type", "SegmentType", typ, f"'{typ}' is not 130.", "130",
             ["S12.20-TYPE"])
    calc, sl = seg130_length(val, chip_bytes), val["SegmentLength"]
    ok = bool(re.fullmatch(r"\d{4}", sl)) and int(sl) == calc
    row(2, "84", "Segment Length", sl, f"4 digits: Fields 1-6 + 6 Field Separators + EMV Additional Information Section + 1 Field "
        f"Separator = {calc:04d} (EMV Chip Data counted as {chip_bytes} binary bytes)", ["S12.20-LEN", "S12.20-FS", "EL190-BIN"], ok)
    if not ok:
        fail("SEG130_LENGTH", "Segment 130 length is wrong", "SegmentLength", sl,
             f"'{sl}' is declared, but the segment adds up to {calc:04d}.", f"{calc:04d}", ["S12.20-LEN", "S12.20-FS"])

    ck = val["CAPublicKeyFileChecksum"]
    ok = bool(re.fullmatch(r"\d{25}", ck))
    row(3, "187", "CA Public Key File Checksum", ck, "Numeric, fixed length 25; the checksum from the last CA Public Key File Load",
        ["EL187", "EL187-REQ", "EL187-USE"], ok)
    if not ok:
        fail("FIELD_187", "CA Public Key File Checksum (Element 187) is not 25 digits", "CAPublicKeyFileChecksum", ck,
             f"'{ck}' is {len(ck)} characters; Element 187 is numeric, fixed length 25.", "The 25-digit checksum from the host",
             ["EL187"])
    else:
        review_once("EMV_CA_CHECKSUM_SAMPLE", "The CA Public Key File Checksum is a sample value", "CAPublicKeyFileChecksum", ck,
                    "The format is right (25 digits), but the value must be the checksum received in the device's last CA Public "
                    "Key File Load; the test case marks it as a configured test-data value.",
                    "Confirm the checksum of the host's current CA Public Key file before execution.", ["EL187-USE"])

    csn, t5f34 = val["EMVCardSequenceNumber"], tagmap.get("5F34")
    exp_csn = t5f34.zfill(3) if t5f34 else "   "
    row(4, "188", "EMV Card Sequence Number", csn, f"Fixed 3 bytes, right-aligned, zero-filled; Tag 5F34 = "
        f"{t5f34 or 'absent'} -> '{exp_csn}'", ["EL188", "EL188-VAL"], csn == exp_csn)
    if csn != exp_csn:
        fail("EMV_CSN_WIDTH", (f"EMV Card Sequence Number (Element 188) is {len(csn)} bytes instead of 3" if len(csn) != 3 else
                               "EMV Card Sequence Number (Element 188) does not match Tag 5F34 in the chip data"),
             "EMVCardSequenceNumber",
             csn, (f"Element 188 is fixed at 3 bytes, right-aligned and zero-filled. The chip data carries Tag 5F34 = '{t5f34}', so the "
                   f"card sequence number itself is right, but '{csn}' is {len(csn)} bytes; it must be '{exp_csn}'. The declared Segment "
                   f"Length ({sl}) was calculated with the {len(csn)}-byte value and changes by {3 - len(csn):+d} when corrected."
                   if t5f34 and len(csn) != 3 else
                   f"The chip data carries Tag 5F34 = '{t5f34}', so Element 188 must be '{exp_csn}', not '{csn}'." if t5f34
                   else f"Tag 5F34 is not in the chip data, so Element 188 must be three spaces, not '{csn}'."),
             f"'{exp_csn}' (then recalculate the Segment 130 length)", ["EL188", "EL188-VAL"],
             brief=f"'{csn}' must be '{exp_csn}' (fixed 3 bytes, zero-filled; Tag 5F34 = {t5f34}).")

    cdl = val["EMVChipDataLength"]
    row(5, "189", "EMV Chip Data Length", cdl, f"3 digits = bytes of EMV Chip Data: {chip_bytes} (2-byte BCD length + {declared} "
        "bytes of TLV data)", ["EL189", "APPR-PKT"], cdl == f"{chip_bytes:03d}")
    if cdl != f"{chip_bytes:03d}":
        fail("FIELD_189", "EMV Chip Data Length (Element 189) does not match the chip data", "EMVChipDataLength", cdl,
             f"'{cdl}' is declared, but EMV Chip Data is {chip_bytes} bytes.", f"{chip_bytes:03d}", ["EL189"])

    probs = list(tlv_probs)
    if not {"9F06", "84"} & tagmap.keys():
        probs.append("has neither Tag 9F06 (AID) nor Tag 84 (DF Name)")
    probs += [f"contains Tag {t} ({EMV_TAGS[t]}), which must not be sent" for t in ("5A", "57") if t in tagmap]
    dups = sorted(t for t, c in Counter(t for t, _, _ in tags).items() if c > 1)
    if dups:
        probs.append("repeats tag(s) " + ", ".join(dups))
    row(6, "190", "EMV Chip Data", f"{len(chip)} hex characters = {chip_bytes} bytes, {len(tags)} tags",
        "2-byte BCD length + TLV data; Tag 9F06 or 84 present; no Tag 5A or 57",
        ["EL190-TLV", "EL190-AID", "EL190-NO5A57", "APPR-PKT"], not probs)
    if probs:
        fail("EMV_CHIP_DATA_FORMAT", "EMV Chip Data (Element 190) is not valid", "EMVChipData", f"{chip[:24]}...",
             "The chip data " + "; ".join(probs) + ".", "Regenerate the chip data as TLV items after a 2-byte BCD length.",
             ["EL190-TLV", "EL190-AID", "EL190-NO5A57"])

    amounts = {"NonfuelAmount": "Nonfuel Amount", "CashAmount": "Cash Amount", "FuelPurchaseAmount": "Fuel Purchase Amount",
               "TaxAmount": "Tax Amount"}
    parts = [(label, v[k]) for k, label in amounts.items() if v[k].isdigit()]
    total = sum(int(x) for _, x in parts)
    cash = v["CashAmount"] if v["CashAmount"].isdigit() and int(v["CashAmount"]) else ""
    cashback = []
    nf, a9f02 = v["NonfuelAmount"], tagmap.get("9F02")
    if a9f02:
        if PLACEHOLDER.match(nf):
            bad = role in tcv.NEW_SEQ_ROLES and orig and partial and int(a9f02) >= int(orig)
            row("6 / 9F02", "190", "Tag 9F02 Amount, Authorized", a9f02, f"The step {n} amount (filled in at run time), 12 digits",
                ["APPR-9F02", "EL190-EXACT"], "FAIL" if bad else "BLOCKED")
            if bad:
                fail("EMV_CHIP_AMOUNT_NOT_REMAINDER", "Card B's chip amount (Tag 9F02) is the full original amount",
                     "EMVChipData -> Tag 9F02", a9f02,
                     (f"Tag 9F02 = {a9f02} = {amount(str(int(a9f02)))}, the full step {of} amount. Card B pays only the remaining "
                      f"balance, which after a partial approval is always less than {amount(orig)}, so 9F02 can never be {a9f02} here."),
                     (f"Build Tag 9F02 at run time from the step {n} amount (12 digits, zero-filled; for example remainder 500 -> "
                      "000000000500)."), ["S10.1.2.1-REMAIN", "APPR-9F02", "EL190-EXACT"], issue=f"AMOUNT_LINK_{n}",
                     brief=f"Card B's chip data says {amount(str(int(a9f02)))} (copied from step {of}); it must be the remaining balance.")
        else:
            ok = int(a9f02) == total
            row("6 / 9F02", "190", "Tag 9F02 Amount, Authorized", a9f02,
                f"= transaction total ({' + '.join(f'{k} {x}' for k, x in parts)}) as 12 digits" if len(parts) > 1 else
                (f"= Nonfuel Amount {nf} as 12 digits" if nf else "No Nonfuel Amount is sent, so the chip amount is zero"),
                ["APPR-9F02", "EL190-EXACT"] + (["EL17"] if cash else []), ok)
            if not ok and cash:
                cashback.append(f"Tag 9F02 (Amount, Authorized) = {a9f02} = {amount(str(int(a9f02)))}, but the transaction total "
                                f"including the cash back is {amount(str(total))} ({total:012d})")
            elif not ok:
                fail("EMV_CHIP_AMOUNT_MISMATCH", "Chip amount (Tag 9F02) differs from the Nonfuel Amount", "EMVChipData -> Tag 9F02",
                     a9f02, f"Tag 9F02 = {a9f02}, but the Nonfuel Amount is '{nf}'.", f"{int(nf or 0):012d}" if nf.isdigit() or not nf
                     else nf, ["APPR-9F02", "EL190-EXACT"])
    if "9F03" in tagmap and tagmap["9F03"].isdigit():
        exp03 = f"{int(cash or 0):012d}"
        row("6 / 9F03", "190", "Tag 9F03 Amount, Other", tagmap["9F03"],
            f"= Cash Amount (cash back) {cash} as 12 digits (EMV Book 3)" if cash else "No cash back, so zero (EMV Book 3)",
            ["EL17", "EL190-EXACT"], tagmap["9F03"] == exp03)
        if cash and tagmap["9F03"] != exp03:
            cashback.append(f"Tag 9F03 (Amount, Other – the cash back) = {tagmap['9F03']}, but the Cash Amount is {cash} ({exp03})")
    tt = v["PromptCode"][:1]
    exp9c = "09" if tt == "0" and cash else EMV_9C.get(tt)
    if "9C" in tagmap and exp9c:
        names = {"00": "purchase of goods/services", "09": "purchase with cash back", "20": "refund", "31": "balance inquiry"}
        row("6 / 9C", "190", "Tag 9C Transaction Type", tagmap["9C"],
            f"{exp9c} = {names[exp9c]} (Transaction Type {tt}; EMV Book 3)", ["APPR-9C", "EL190-EXACT"],
            tagmap["9C"] == exp9c)
        if cash and tagmap["9C"] != exp9c:
            cashback.append(f"Tag 9C (Transaction Type) = {tagmap['9C']} (purchase), but a purchase with cash back is 09")
    if cashback:
        fail("EMV_CASHBACK_AMOUNTS", "The chip data does not include the cash back", "EMVChipData -> Tags 9F02, 9F03, 9C",
             f"9F02={tagmap.get('9F02', '-')}, 9F03={tagmap.get('9F03', '-')}, 9C={tagmap.get('9C', '-')}",
             (f"This purchase sends a Cash Amount of {cash} ({amount(cash)}) next to the Nonfuel Amount, which excludes cash. The "
              "chip data must hold the exact card-to-terminal values of a cash-back purchase (EMV Book 3: 9F02 = total including "
              "the cash back, 9F03 = the cash back, 9C = 09). Here: " + "; ".join(cashback) + "."),
             (f"Generate the chip data from a purchase with cash back: 9F02 = {total:012d}, 9F03 = {int(cash):012d}, 9C = 09."),
             ["EL17", "EL58-PURPOSE", "APPR-9F02", "EL190-EXACT"],
             brief="The chip data describes a purchase without cash back.")
    for t in ("5F2A", "9F1A"):
        if t in tagmap:
            row(f"6 / {t}", "190", f"Tag {t} {EMV_TAGS[t]}", tagmap[t], "0840 = United States (Table 006 default 840)", ["APPI-006"],
                tagmap[t] == "0840")
    if "9F27" in tagmap:
        row("6 / 9F27", "190", "Tag 9F27 Cryptogram Information Data", tagmap["9F27"],
            f"{CID_NAMES.get(tagmap['9F27'], 'see EMV Book 3')} (EMV Book 3)", ["EL190-EXACT"], "PASS")
    if "9F34" in tagmap:
        code = int(tagmap["9F34"][:2], 16) & 0x3F
        row("6 / 9F34", "190", "Tag 9F34 CVM Results", tagmap["9F34"],
            f"CVM code {code:02X} = {CVM_NAMES.get(code, 'other')} (EMV Book 3)", ["EL190-EXACT"], "PASS")
        tvr = tagmap.get("95", "")
        if len(tvr) >= 6 and int(tvr[4:6], 16) & 0x04 and code != 0x02 and not v["EncryptedPINBlockData"]:
            review_once("EMV_TVR_CVM_MISMATCH", "TVR says 'online PIN entered' on a transaction without online PIN",
                        "EMVChipData -> Tag 95", f"95={tvr}, 9F34={tagmap['9F34']}",
                        f"TVR byte 3 (`{tvr[4:6]}`) sets 'Online PIN entered' (EMV Book 3), but the CVM Results (Tag 9F34 = "
                        f"{tagmap['9F34']}) say {CVM_NAMES.get(code, 'other')} and Encrypted PIN Block Data is empty. The TVR is a "
                        "generated placeholder; it must match the CVM once the chip data comes from a card simulator.",
                        "Produce the TVR and the CVM Results together with a card simulator.", ["EL190-EXACT"])
    review_once("EMV_PLACEHOLDER_CRYPTO", "Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders",
                "EMVChipData", ", ".join(f"{t}={tagmap[t]}" for t in ("9F26", "9F36", "9F37", "95") if t in tagmap),
                "ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated "
                "by the issuer.", "Produce the chip data with a card simulator / HSM before execution.", ["EL190-EXACT"])
    if entry == "07" and not {"9F6E", "9F6D", "9F7C", "9F66"} & tagmap.keys():
        review_once("EMV_CONTACTLESS_TEMPLATE", "Contactless chip data reuses the contact tag set", "EMVChipData",
                    f"{len(tags)} tags, CVM 9F34 = {tagmap.get('9F34', '-')}",
                    "The contactless request carries the same tags as the contact requests and no contactless-specific tag "
                    "(9F6E, 9F6D, 9F7C, 9F66). ATL105 Appendix R lists an example only, so this cannot be decided from the spec.",
                    "Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace.", ["APPR-NOTE"])

    ind, ln, info = (val["EMVAdditionalInformationIndicator"], val["EMVAdditionalInformationLength"],
                     val["EMVAdditionalInformation"])
    row(7, "191", "EMV Additional Information Indicator", ind, "001 = EMV table data", ["EL191"], ind == "001")
    if ind != "001":
        fail("FIELD_191", "EMV Additional Information Indicator (Element 191) is not valid",
             "EMVAdditionalInformationIndicator", ind, f"'{ind}' is not a listed code (001 = EMV table data).", "001", ["EL191"])
    ok = bool(re.fullmatch(r"\d{3}", ln)) and 1 <= int(ln) <= 985 and int(ln) == len(info)
    row(8, "192", "EMV Additional Information Length", ln, f"3 digits, 001-985, = length of Element 118 ({len(info):03d})",
        ["EL192"], ok)
    if not ok:
        fail("FIELD_192", "EMV Additional Information Length (Element 192) is not valid", "EMVAdditionalInformationLength", ln,
             f"'{ln}' must be 001-985 and equal the length of Element 118 ({len(info)}).", f"{len(info):03d}", ["EL192"])
    shown = info if len(info) <= 24 else f"{info[:20]}...{info[-8:]} ({len(info)} characters)"
    if ind == "001":
        ok = info in ("EMVYES", "EMVNOT")
        row(9, "118", "EMV Additional Information", shown, "Indicator 001 = EMV Table Data: EMVYES or EMVNOT (6 characters)",
            ["EL118-EMV", "APPT-1", "APPT-1-NOT", "APPT-1-LEN"], ok)
        if not ok:
            fixed = calc - len(info) + 6 + (3 - len(csn) if t5f34 and len(csn) < 3 else 0)
            fail("EMV_ADDL_INFO_CONTENT", "EMV Additional Information (Element 118) does not contain EMV Table Data",
                 "EMVAdditionalInformation", shown,
                 (f"Indicator 001 means EMV Table Data, and Appendix T allows only EMVYES or EMVNOT (6 characters). The field holds "
                  f"{len(info)} generated characters. Element 192 = '{ln}' matches that length, so it is consistent with the content, "
                  "but the content itself is not allowed for indicator 001."),
                 (f"EMVYES (device can receive EMV Table Data) or EMVNOT, with Element 192 = 006 (see the open question on framing); "
                  f"the Segment 130 length then becomes {fixed:04d}" + (" together with the Element 188 fix" if fixed != calc - len(info) + 6
                                                                      else "") + "."),
                 ["EL118-EMV", "APPT-1", "APPT-1-NOT", "APPT-1-LEN"],
                 brief=f"{len(info)} generated characters instead of EMVYES or EMVNOT.")
    if len(ind) + len(ln) + len(info) > 2000:
        fail("SEG130_ADDL_TOO_LONG", "EMV Additional Information Section is longer than 2,000 bytes", "EMVAdditionalInformation",
             shown, "The section allows at most 2,000 bytes.", "Shorten the section.", ["S12.20-ADDL"])
    if calc > 3043:
        fail("SEG130_TOO_LONG", "Segment 130 is longer than 3,043 characters", "SegmentLength", sl,
             f"The segment adds up to {calc} characters.", "Shorten the segment.", ["S12.20-MAX"])


OBJECTIVE_RULES = {"EXPIRED_TEST_CARD_NOT_EXPIRED", "ONLINE_PIN_NOT_SENT", "TRACK1_NOT_SENT", "OFFLINE_PIN_NOT_IN_CVM",
                   "AVS_DATA_NOT_SENT"}
# EMV Book 3 CVM codes verified by the card (offline PIN)
OFFLINE_PIN_CVM = {0x01, 0x03, 0x04, 0x05}


def check_objective(rep, tc, ctx):
    """Can the data prove what the test case template claims (expired card, online PIN, Track 1)?"""
    if not tc["steps"]:
        return
    tpl, st = tc.get("template_id", ""), tc["steps"][0]
    n, f = st["step"], ctx["flats"].get(st["step"])
    if not f:
        return
    cdb, entry, emv = f.get("CardDiscretionaryBlockData") or "", ctx["entries"].get(n), ctx["emv"].get(n)
    path = f"{st['td']} -> Standard Message Data Segment"
    if "EXPIRED" in tpl:
        yymm = cdb[2:] + cdb[:2] if entry == "01" else cdb[:4]
        if (re.fullmatch(r"\d{4}", yymm) and 1 <= int(yymm[2:]) <= 12
                and (2000 + int(yymm[:2]), int(yymm[2:])) >= (TODAY.year, TODAY.month)):
            extra = []
            if emv is not None and "5F24" not in emv:
                extra.append("Tag 5F24 (Application Expiration Date) is not in the chip data.")
            tvr = (emv or {}).get("95", "")
            if len(tvr) >= 4 and not int(tvr[2:4], 16) & 0x40:
                extra.append(f"The TVR (Tag 95 = {tvr}) does not set 'Expired application' (byte 2, bit 7).")
            when = expiry(yymm[2:] + yymm[:2])
            rep.add(n, "FAIL", "EXPIRED_TEST_CARD_NOT_EXPIRED", "The expired-card test uses a card that is not expired",
                    label="Card Discretionary Block Data (expiry)", field=f"{path} -> CardDiscretionaryBlockData", value=cdb,
                    why=(f"The test is about an expired EMV card that must still be sent online, but the expiry in the card data is "
                         f"{yymm} ({when}), which is not before {TODAY:%B %Y}. " + " ".join(extra)
                         + " The test cannot exercise its objective with a valid card."),
                    fix=(f"Use an expired test card (expiry before {TODAY:%y%m} as YYMM) with matching chip data; the test case already "
                         "lists an expired EMV test card as an open client input."),
                    refs=["S10.14.2.1", "EL12-EXP"], brief=f"Expiry {yymm} ({when}) is not expired.")
    if "ONLINE-PIN" in tpl:
        pin_block, cvm = f.get("EncryptedPINBlockData") or "", (emv or {}).get("9F34", "")
        code = int(cvm[:2], 16) & 0x3F if len(cvm) >= 2 else None
        if not pin_block or code != 0x02:
            parts = (["Encrypted PIN Block Data (Element 33) is empty"] if not pin_block else []) + (
                [f"CVM Results (Tag 9F34) = {cvm}: CVM code {code:02X} = {CVM_NAMES.get(code, 'other')} (EMV Book 3), not 02 = "
                 "enciphered PIN verified online"] if code is not None and code != 0x02 else [])
            rep.add(n, "FAIL", "ONLINE_PIN_NOT_SENT", "The online-PIN test carries no PIN block and a signature CVM",
                    label="Encrypted PIN Block Data / CVM Results", field=f"{path} -> EncryptedPINBlockData; EMVChipData Tag 9F34",
                    value=f"'{pin_block}' / 9F34 = {cvm}",
                    why=("The test case objective is EMV online PIN, and its execution step sends the PIN block online. "
                         + "; ".join(parts) + ". The data is therefore an ordinary signature purchase, not an online-PIN test. "
                         "For credit a PIN is optional in general, so an empty Element 33 is correct for normal purchases - this "
                         "finding applies only to online-PIN test cases."),
                    fix=("When the client supplies the online-PIN test card and DUKPT key, send the DUKPT KSN + PIN block in Element 33 "
                         "and the online-PIN CVM in Tag 9F34 (code 02/42); until then mark the test case blocked (needs client input)."),
                    refs=["S10.14.3-ONLINE", "S10.14.3-MC", "EL33-FMT", "S10.14.3-OPT"],
                    brief="No PIN block (Element 33 empty) and CVM = signature, so this is not an online-PIN transaction.")
    if "TRACK1" in tpl and "^" not in cdb and not (f.get("AccountNumber") or "").startswith("B"):
        an = f.get("AccountNumber") or ""
        rep.add(n, "FAIL", "TRACK1_NOT_SENT", "The Track 1 test sends Track 2 data", label="Account Number / Card Discretionary Block Data",
                field=f"{path} -> AccountNumber, CardDiscretionaryBlockData", value=f"{an} / {cdb}",
                why=("The test case objective is 'Track 2 unreadable -> Track 1 sent', but the card data is in Track 2 form: the "
                     "Account Number has no Track 1 format code 'B', and the Card Discretionary Block Data has no '^' separator or "
                     "cardholder name - it starts with the expiry and service code, as Track 2 data does. In Track 1 data the Account "
                     "Number is the data before the first '^' and the Card Discretionary Block Data the data after it."),
                fix=(f"Send the unaltered Track 1 data, for example Account Number 'B{an}' and Card Discretionary Block Data "
                     f"'CARDHOLDER/NAME^{cdb[:7]}...' (combined at most 76 characters)."),
                refs=["S10.1.1.2", "EL12-TRACK1", "EL12-MAX"], brief="Track 2 data is sent; the message shows no Track 1 data at all.")
    if "OFFLINE-PIN" in tpl:
        cvm = (emv or {}).get("9F34", "")
        code = int(cvm[:2], 16) & 0x3F if len(cvm) >= 2 else None
        if code not in OFFLINE_PIN_CVM:
            rep.add(n, "FAIL", "OFFLINE_PIN_NOT_IN_CVM", "The offline-PIN test shows a signature, not an offline PIN",
                    label="CVM Results (Tag 9F34)", field=f"{st['td']} -> EMV Request Data Segment -> EMVChipData Tag 9F34",
                    value=cvm or "(absent)",
                    why=("The test case objective is EMV offline PIN: the PIN is checked by the chip, and the chip data reports that "
                         "result in the CVM Results. " + (f"Tag 9F34 = {cvm} has CVM code {code:02X} = "
                                                         f"{CVM_NAMES.get(code, 'other')} (EMV Book 3)" if code is not None
                                                         else "Tag 9F34 is absent")
                         + ", so the data is an ordinary signature transaction, not an offline-PIN test."),
                    fix=("Generate the chip data from an offline-PIN transaction: Tag 9F34 with CVM code 01/04 (PIN verified by the "
                         "card, for example 410302 or 440302); Element 33 stays empty for credit."),
                    refs=["S10.14.3-OFFLINE", "S10.14.3-MC", "EL190-EXACT"],
                    brief=f"CVM Results {cvm} = {CVM_NAMES.get(code, 'other')}, not an offline PIN.")
    if "-AVS" in tpl:
        sent = [x["td"] for x in tc["steps"] if "003" in ctx["tables"].get(x["step"], {})]
        if not sent:
            rep.add(n, "FAIL", "AVS_DATA_NOT_SENT", "The AVS test sends no address data", label="Table 003 (AVS)",
                    field=f"{st['td']} -> Variable Information Data Segment",
                    value=f"tables sent: {', '.join(ctx['tables'].get(n, {})) or 'none'}",
                    why=("The test case objective is an Address Verification (AVS) mismatch, but no request carries AVS data: "
                         "Table 003 (street address, ZIP Code) is not in the Variable Information Data Segment. Without it the "
                         "issuer cannot verify an address, so the test cannot produce an AVS result."),
                    fix="Add Table 003 with the mismatching street address/ZIP Code that the client supplies for the test card.",
                    refs=["S10.1.3-AVS", "EL113-AVS"], brief="No Table 003, so there is no address to verify.")


def expected_totals(tc, ctx, n):
    """Device totals for the steps before step n (Section 9.2, p.72): (count, amount, AO count, story) or None if unknown."""
    cnt = amt = ao = 0
    counted, story = set(), []
    for s in tc["steps"]:
        if s["step"] >= n or ctx["roots"].get(s["step"]) == TOTALS_ROOT:
            continue
        role, rc, f = s["role"], s.get("expected_rc"), ctx["flats"].get(s["step"]) or {}
        a = sum(int(f[k]) for k in ("NonfuelAmount", "CashAmount") if str(f.get(k, "")).isdigit())
        if role not in TOTALS_ROLES or rc == "F" or PLACEHOLDER.match(str(f.get("NonfuelAmount", ""))):
            return None
        approved = rc not in ("1",) and not (rc is None and not (s.get("expected_open") or s.get("expected_any")))
        what = f"step {s['step']} {role.replace('_', ' ')} {amount(str(a))}"
        if not approved:
            story.append(f"{what}: " + ("declined" if rc == "1" else "no response (timed out)") + ", so not in the totals")
        elif role in ("purchase", "next_transaction", "resubmitted", "completion", "completion_resubmitted"):
            cnt, amt = cnt + 1, amt + a
            counted.add(s["step"])
            story.append(f"{what}: count +1, amount +{amount(str(a))}")
        elif role in ("reversal", "timeout_reversal", "tor_retry"):
            if s.get("of", 1) in counted:
                cnt, amt = cnt - 1, amt - a
                story.append(f"{what}: count -1, amount -{amount(str(a))}")
            else:
                story.append(f"{what}: its original was never approved, so nothing to take out")
        elif role == "return":
            cnt, amt = cnt + 1, amt - a
            story.append(f"{what}: count +1, amount -{amount(str(a))}")
        elif role == "void_return":
            cnt, amt = cnt - 1, amt + a
            story.append(f"{what}: count -1, amount +{amount(str(a))}")
        elif role in ("auth_only", "moto_auth_only"):
            ao += 1
            story.append(f"{what}: Authorization Only - AO bucket, not in the grand total")
        elif role == "cancellation":
            ao -= 1
            story.append(f"{what}: cancellation - subtracts from the AO bucket")
    return cnt, amt, ao, story


def validate_totals(rep, refs, tc, st, td, ctx, states):
    """Totals Request (Section 11.4.1.1): Data Section 1 + Totals Data Segment 105 (p.235-237), totals per Section 9.2."""
    n, fname, prof = st["step"], st["td"], profile(tc)
    msg = td[TOTALS_ROOT]
    ctx["roots"][n] = TOTALS_ROOT
    segs = [k for k, x in msg.items() if isinstance(x, dict)]
    s105 = msg.get("Totals Data Segment")
    ok = s105 is not None and segs == ["Totals Data Segment"]
    rep.row(n, "Message", "-", "-", "Message type", TOTALS_ROOT, "-",
            "Totals Request: Data Section 1 + Totals Data Segment (105); no Segment 100", ["S11.4-TOTALS"], "PASS" if ok else "FAIL")
    if not ok:
        rep.add(n, "FAIL", "TOTALS_SEGMENTS", "The Totals Request carries the wrong segments", label="segments", field=fname,
                value=", ".join(segs) or "(none)", refs=["S11.4-TOTALS"],
                why="A Totals Request contains Data Section 1 and the Totals Data Segment (105) only.",
                fix="Send only the Totals Data Segment.")
        if s105 is None:
            return
    mfv, nos = msg.get("MessageFormatVersionIdentifier", ""), msg.get("NumberofSegments", "")
    rep.row(n, "Data Section 1", 1, "55", "Message Format Version Identifier", mfv, "ATL105", "Fixed 6 characters: ATL105",
            ["S11.1-DS1", "EL55"], "PASS" if mfv == "ATL105" else "FAIL")
    if mfv != "ATL105":
        rep.add(n, "FAIL", "MFV", "Wrong Message Format Version Identifier", label="Message Format Version Identifier",
                field=f"{fname} -> MessageFormatVersionIdentifier", value=mfv, why=f"'{mfv}' is not 'ATL105'.", fix="ATL105",
                refs=["EL55"])
    ok = nos.isdigit() and int(nos) == len(segs)
    rep.row(n, "Data Section 1", 2, "63", "Number of Segments", nos, "-", f"Equals the number of segments sent ({len(segs)})",
            ["EL63"], "PASS" if ok else "FAIL")
    if not ok:
        rep.add(n, "FAIL", "NUMBER_OF_SEGMENTS", "Number of Segments does not match the segments sent", label="Number of Segments",
                field=f"{fname} -> NumberofSegments", value=nos, why=f"'{nos}' is declared, but {len(segs)} segment(s) are present.",
                fix=f"{len(segs):02d}", refs=["EL63"])

    v = {key: str(s105.get(key, "")) for _, _, key, *_ in S105}
    ctx["flats"][n] = v
    tcr = st.get("tc_totals") or {}
    head = sum(len(v[key]) for _, _, key, *_ in S105[:14])
    label = v["CardLabel"]
    no_cat = not (label or v["CardTypeTotalCount"] or v["CardTypeTotalAmount"])
    cat = (4 + len(v["CardTypeTotalCount"]) + len(v["CardTypeTotalAmount"])) if label else 0
    calc, as_sent = head + 14 + cat, head + 17 + len(label) + len(v["CardTypeTotalCount"]) + len(v["CardTypeTotalAmount"])
    first = next((ctx["flats"][s["step"]] for s in tc["steps"] if s["step"] != n and s["step"] in ctx["flats"]), {})
    for fno, el, key, name, req, ctype, lens in S105:
        val, tcval = v[key], tcr.get(name)
        tcdisp = "" if tcval is None else str(tcval)
        path = f"{fname} -> Totals Data Segment -> {key}"
        rrefs, probs, rule, result = list(S105_REFS.get(el, [])), [], "", None
        if val == "":
            rule = {"R": "Required", "C": "Conditional - not sent (the Field Separator is still sent)",
                    "O": "Optional - not sent (the Field Separator is still sent)"}[req]
            if no_cat and el in ("13", "16", "15"):
                rule, rrefs = "Not sent: Fields 15-17 are sent only when data occurs for a card type", ["S12.6-CAT"]
            elif req == "R":
                probs.append(f"{name} is required")
        elif el == "84":
            rule = (f"Fields 1-14 ({head}) + 14 Field Separators + Category Total Data Section without separators (Card Label 4 + "
                    f"Count {len(v['CardTypeTotalCount'])} + Amount {len(v['CardTypeTotalAmount'])}) = {calc:03d}")
            if not val.isdigit() or int(val) != calc:
                result = "FAIL"
                how = (f" '{val}' is what you get with a Field Separator after every one of the 17 fields and the {len(label)}-character "
                       "Card Label, as in Segment 100." if val.isdigit() and int(val) == as_sent else "")
                rep.add(n, "FAIL", "TOTALS_SEGMENT_LENGTH", "Segment 105 length is counted with the wrong separators and label width",
                        label=name, field=path, value=val, refs=["S12.6-FS", "S12.6-NOFS", "EL13", "EL84"],
                        why=(f"Fields 1-14 add up to {head} characters, plus 14 Field Separators = {head + 14}. Fields 15-17 (the "
                             f"Category Total Data Section) are not separated: Card Label 4 (fixed width) + Count "
                             f"{len(v['CardTypeTotalCount'])} + Amount {len(v['CardTypeTotalAmount'])} = {cat}. The Segment Length "
                             f"is therefore {calc:03d}, not '{val}'." + how),
                        fix=f"{calc:03d}", brief=f"'{val}' declared; the spec layout adds up to {calc:03d}.")
        else:
            if lens and len(val) not in lens and el != "13":
                probs.append(f"is {len(val)} characters; the field is fixed at {' or '.join(map(str, lens))}")
            if ctype == "N" and not val.isdigit():
                probs.append("must be digits only")
            if el == "85":
                rule = "Fixed value 105"
                if val != "105":
                    probs.append("must be 105")
            elif el == "44":
                rule = f"'{val}' = {INFO_BYTE.get(val, 'not valid')}"
                if val not in INFO_BYTE:
                    probs.append("is not a valid Information Byte (?, 0 or 1)")
            elif el == "102":
                info = states.get(val[2:4])
                rule = f"Same device as the financial steps; State Code '{val[2:4]}'" + (f" = {info[0]} ({info[1]})" if info else "")
                if first and val != first.get("TerminalIdentifier"):
                    probs.append(f"differs from the terminal of the financial steps ('{first.get('TerminalIdentifier')}')")
            elif el == "78":
                rule = "Fixed value 990 (Totals)"
                if val != "990":
                    probs.append("must be 990")
            elif el == "105":
                if val in TOTALS_DATES:
                    rule = f"'{val}' = {TOTALS_DATES[val]}"
                elif re.fullmatch(r"(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d\d", val):
                    rule = f"MMDDYY settlement date {val}"
                else:
                    probs.append("is neither a valid MMDDYY date nor a Totals Date code (111111-555555, 999999)")
            elif el in ("43", "96", "39"):
                rule = f"Device {name.split()[0].lower()} version, {' or '.join(map(str, lens))} alphanumeric characters"
                if not val.isalnum():
                    probs.append("must be alphanumeric")
            elif el == "86":
                rule = "6 digits, not 000000; a new Sequence Number for the totals request"
                if val.isdigit() and int(val) == 0:
                    probs.append("is 000000, which is not allowed")
            elif el == "20":
                rule = f"3 digits; '{val}'" + (" = US dollar (Appendix L, default 840)" if val == "840" else "")
            elif el == "42":
                rule = f"8 digits with two assumed decimals: {amount(str(int(val)))}" if val.isdigit() else "8 digits"
            elif el == "13":
                rule = f"Fixed 4 characters, left-aligned and space-filled; {prof['label']} = {prof['name'].split()[-1]} bucket"
                if label.strip() not in CARD_LABELS:
                    probs.append("is not a Card Label from Element 13")
                elif label.strip() != prof["label"]:
                    probs.append(f"is not the {prof['label']} bucket of a {prof['name']} card")
                if len(label) != 4:
                    result = "FAIL"
                    padded = label.strip().ljust(4)
                    rep.add(n, "FAIL", "TOTALS_CARD_LABEL_WIDTH", "Card Label (Element 13) is not 4 characters", label=name,
                            field=path, value=f"'{label}'", refs=["EL13", "EL13-DB" if prof["label"] == "DB" else "EL13"],
                            why=(f"Element 13 is a fixed field of four characters, left-aligned and space-filled. '{label}' has "
                                 f"{len(label)}, so the fields after it shift by {4 - len(label)} on the wire."),
                            fix=f"'{padded}' ({label.strip()} followed by {4 - len(label.strip())} spaces)",
                            brief=f"'{label}' must be '{padded}' (fixed 4 characters).")
            elif el == "16":
                rule = "5 digits, 00001-99999"
                if val == "00000":
                    rep.add(n, "REVIEW", "TOTALS_ZERO_BUCKET", "The totals bucket is sent with a count of 00000", label=name,
                            field=path, value=val, refs=["EL16-VAL", "S12.6-CAT"],
                            why=("Element 16 allows 00001-99999, and the category section is sent only when data occurs for a "
                                 "card type. A net count of 00000 suggests the bucket should not be sent at all."),
                            fix="Confirm with BUYPASS whether a bucket with a net count of zero is omitted or sent.")
                    result = "REVIEW"
            elif el == "15":
                rule = f"8 digits with two assumed decimals: {amount(str(int(val)))}" if val.isdigit() else "8 digits"
        differs = tcval is not None and tcdisp != val and el != "84"
        if probs:
            result = "FAIL"
            rep.add(n, "FAIL", f"FIELD_105_{el}", f"{name} (Totals) is not valid", label=name, field=path, value=val, refs=rrefs,
                    why=f"'{val}' " + "; ".join(probs) + ".", fix=(tcdisp if differs else "") or f"A value that meets: {rule}.")
        elif differs:
            result = "FAIL"
            rep.add(n, "FAIL", "TD_DIFFERS_FROM_TC", f"{name} in the test data differs from the test case", label=name, field=path,
                    value=val, refs=rrefs,
                    why=(f"The test data file has '{val}', but the test case request (the source the file is generated from) has "
                         f"'{tcdisp}'."), fix=f"'{tcdisp}' (regenerate the file from the test case)")
        rep.row(n, "105", fno, el, name, val if val else "(empty)", tcdisp or "-", rule, rrefs, result or "PASS")

    methods = st.get("tc_methods") or {}
    synth = [x for x in ("Hardware Version", "Software Version", "Firmware Version") if methods.get(x) == "format_synthesis"]
    if synth and not any(f["rule"] == "TOTALS_VERSION_PLACEHOLDER" for f in rep.findings):
        rep.add(n, "REVIEW", "TOTALS_VERSION_PLACEHOLDER", "The device version fields of the Totals Request are generated values",
                label=", ".join(synth), field=f"{fname} -> Totals Data Segment", value=", ".join(v[k] for _, _, k, nm, *_ in S105 if nm in synth),
                why=("ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case "
                     "generated random values, so they do not identify a certified device."),
                fix="Use the versions of the certified device that runs the test.", refs=["EL43", "EL96", "EL39"])

    exp = expected_totals(tc, ctx, n)
    if exp:
        cnt, amt, ao, story = exp
        got_cnt = int(v["CardTypeTotalCount"]) if v["CardTypeTotalCount"].isdigit() else None
        got_amt = int(v["CardTypeTotalAmount"]) if v["CardTypeTotalAmount"].isdigit() else None
        got_gt = int(v["GrandTotal"]) if v["GrandTotal"].isdigit() else None
        bucket = label.strip() or prof["label"]
        wrong = []
        if (cnt, amt) == (0, 0):
            if got_cnt not in (0, None) or got_amt not in (0, None):
                wrong.append(f"the {bucket} bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count "
                             f"{v['CardTypeTotalCount']} and amount {v['CardTypeTotalAmount']}")
        elif no_cat:
            wrong.append(f"the {bucket} bucket (count {cnt:05d}, amount {max(amt, 0):08d}) is not sent")
        else:
            if got_cnt != cnt:
                wrong.append(f"Card Type Total Count should be {cnt:05d}, the file has {v['CardTypeTotalCount']}")
            if got_amt != amt:
                wrong.append(f"Card Type Total Amount should be {max(amt, 0):08d} ({amount(str(amt))}), the file has "
                             f"{v['CardTypeTotalAmount']}")
        if got_gt is not None and got_gt != max(amt, 0):
            wrong.append(f"Grand Total should be {max(amt, 0):08d}, the file has {v['GrandTotal']}")
        rep.links.append(dict(step=n, check="Totals match the transactions of the flow (Section 9.2)",
                              value=f"count {v['CardTypeTotalCount']}, amount {v['CardTypeTotalAmount']}, grand total {v['GrandTotal']}",
                              original=f"count {cnt}, amount {amt}", refs=["S9.2-PUR", "S9.2-REV", "S9.2-RET"],
                              result="FAIL" if wrong else "PASS"))
        if wrong:
            said = "; ".join(wrong)
            rep.add(n, "FAIL", "TOTALS_FLOW_MISMATCH", "The totals do not match the transactions of the flow",
                    label="Card Type Total Count / Amount, Grand Total", field=f"{fname} -> Totals Data Segment",
                    value=f"count {v['CardTypeTotalCount']}, amount {v['CardTypeTotalAmount']}, grand total {v['GrandTotal']}",
                    refs=["S9.2-PUR", "S9.2-REV", "S9.2-RET", "S9.2-GT", "S12.6-CAT"],
                    why=("Section 9.2 counts each approved transaction into its card bucket: " + "; ".join(story) + ". "
                         + said[0].upper() + said[1:] + "."),
                    fix=(f"Count {max(cnt, 0):05d}, amount {max(amt, 0):08d}, grand total {max(amt, 0):08d}" if (cnt, amt) != (0, 0)
                         else f"Leave out the {bucket} bucket and send Grand Total 00000000"),
                    brief="; ".join(wrong) + ".")
        if ao > 0 and label.strip() != "AO":
            rep.add(n, "REVIEW", "TOTALS_AO_BUCKET", "The Authorization Only step has no AO bucket in the totals", label="Card Label",
                    field=f"{fname} -> Totals Data Segment", value=label, refs=["S12.6-CAT", "S9.2-GT"],
                    why=("Section 12.6 lists Preauthorization (AO) as bucket 4, and the grand total leaves the AO bucket out. This "
                         "flow has an approved Authorization Only, but "
                         + (f"only the {label.strip()} bucket is sent" if label.strip() else "no card-type bucket is sent")
                         + ". ATL105 says cancellations subtract from the AO bucket; it does not say whether a completed "
                         "authorization stays in it."),
                    fix="Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed.")


def check_response(rep, st):
    rc = st.get("expected_rc")
    if not rc and st.get("expected_any"):
        codes = st["expected_any"]
        totals_ok = st["role"] == "totals" and set(codes) <= TOTALS_APPROVED_RC
        rep.responses.append(dict(step=st["step"], code=" / ".join(codes), refs=[RC_MEANING[c][1] for c in codes if c in RC_MEANING],
                                  meaning="any of: " + "; ".join(f"{c} = {RC_MEANING.get(c, ('see Element 83',))[0]}" for c in codes),
                                  result="PASS" if totals_ok else "REVIEW"))
        if not totals_ok:
            rep.add(None, "REVIEW", "EXPECTED_RESPONSE_OPEN", "The test case accepts several Response Codes instead of one",
                    label="expected Response Code", field=f"test case expected_response.response_code (step {st['step']})",
                    value="any of " + ", ".join(codes),
                    why=("The test case accepts any of " + "; ".join(f"{c} = {RC_MEANING.get(c, ('see Element 83',))[0]}"
                                                                 for c in codes)
                         + f" for the {st['role'].replace('_', ' ')} step. Element 83 has a separate approval code for each "
                         "kind of transaction, so the test should state the one code BUYPASS returns."),
                    fix="State the one expected Response Code for this transaction type (from BUYPASS or a reference run).",
                    refs=[RC_MEANING[c][1] for c in codes if c in RC_MEANING])
        return
    if not rc:
        if st.get("expected_open") and not any(f["rule"] == "EXPECTED_RESPONSE_OPEN" for f in rep.findings):
            rep.add(None, "REVIEW", "EXPECTED_RESPONSE_OPEN", "The test case does not state the expected Response Code",
                    label="expected Response Code", field=f"test case step {st['step']} expected_response", value="? (FLAG)",
                    why=("The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 "
                         "has a separate approval code for each kind of transaction."),
                    fix="State the expected Response Code for this transaction type (from BUYPASS or a reference run).",
                    refs=["EL83-0", "EL83-2"])
        return
    meaning, rid = RC_MEANING.get(rc, ("see Element 83", None))
    allowed = tcv.APPROVED_CODES_BY_ROLE.get(st["role"])
    bad = rc not in tcv.RESPONSE_CODES or bool(allowed and rc not in allowed and rc != "1")
    rep.responses.append(dict(step=st["step"], code=rc, meaning=meaning, refs=[rid] if rid else [], result="FAIL" if bad else "PASS"))
    if bad:
        rep.add(st["step"], "FAIL", "RESPONSE_CODE", "Expected Response Code does not fit the step", label="expected Response Code",
                field=f"test case step {st['step']} expected_response", value=rc,
                why=f"'{rc}' is not an approval code for a {st['role'].replace('_', ' ')} step.",
                fix=" or ".join(sorted(allowed or [])), refs=[rid] if rid else [])


def check_links(rep, tc, ctx):
    debit = profile(tc)["debit"]
    for st in tc["steps"][1:]:
        n, role, of = st["step"], st["role"], st.get("of", 1)
        a, b = ctx["flats"].get(of), ctx["flats"].get(n)
        orole = next((s["role"] for s in tc["steps"] if s["step"] == of), "")
        if debit and role in TOR_ROLES and orole in ("completion", "completion_resubmitted"):
            rep.add(n, "FAIL", "DEBIT_COMPLETION_TOR", "A Time-out Reversal is sent for a debit Completion", label="Prompt Code",
                    field=f"{st['td']} -> Standard Message Data Segment -> PromptCode", value="Z (Time-out Reversal)",
                    refs=["S10.2.2-TOR"],
                    why=(f"Step {n} reverses the debit Completion of step {of} with a Time-out Reversal, but ATL105 does not "
                         "support a Time-out Reversal for a debit Completion transaction."),
                    fix=("Remove the Time-out Reversal of the debit Completion (or replace the test case); ask BUYPASS how an "
                         "unanswered debit Completion is to be handled."))
        if role == "totals" or ctx["roots"].get(n) == TOTALS_ROOT:
            continue
        if not a or not b:
            continue
        checks = []
        if role in tcv.SAME_SEQ_ROLES:
            seq_refs = (["EL86-PREAUTH"] if FOLLOW_ON_KIND.get(role) == "completion" else ["S10.1.2.2-SEQ"] if role == "cancellation"
                        else ["S2.3.1-TOR-SEQ", "EL86-REV"] if role in TOR_ROLES else ["EL86-REV", "S10.1.2.1-SEQ"])
            checks = [("Same Sequence Number as the original", "SequenceNumber", True, seq_refs),
                      ("Same Account Number as the original", "AccountNumber", True, ["S2.3.1-MATCH"]),
                      ("Same terminal as the original", "TerminalIdentifier", True, ["S2.3.1-DEVICE"])]
        elif role == "split_tender":
            checks = [("New Sequence Number", "SequenceNumber", False, ["S2.3.1-SEQ"]),
                      ("A different card pays the remaining balance", "AccountNumber", False, ["S10.1.2.1-REMAIN"])]
        elif role == "subsequent":
            checks = [("New Sequence Number", "SequenceNumber", False, ["S2.3.1-SEQ"]),
                      ("Same card as the stored credential", "AccountNumber", True, ["S10.13.2.4.1"])]
        elif role in tcv.NEW_SEQ_ROLES or role == "resubmitted":
            checks = [("New Sequence Number", "SequenceNumber", False, ["S2.3.1-SEQ"])]
        for label, key, same, crefs in checks:
            ok = (a[key] == b[key]) == same
            if not ok and role == "completion_resubmitted" and key == "SequenceNumber":
                rrefs = ["EL86-PREAUTH", "S2.3.4-RESUBMIT", "S2.3.1-MATCH", "S2.3.1-SEQ"]
                rep.links.append(dict(step=n, check=label, value=b[key], original=a[key], refs=rrefs, result="REVIEW"))
                rep.add(n, "REVIEW", "RESUBMIT_SEQUENCE_CONFLICT", "Sequence Number of the resubmitted completion: ATL105 rules conflict",
                        label=key, field=f"{st['td']} -> Standard Message Data Segment -> {key}", value=b[key],
                        why=(f"The test case gives the resubmitted completion a new Sequence Number ('{b[key]}'), citing the general "
                             "rule that every forwarded financial request gets a new Sequence Number. Element 86 says the "
                             "Purchase/Capture of a preauthorized transaction carries the Authorization Only's Sequence Number "
                             f"('{a[key]}'), and Section 2.3.4 warns that a completion resent before its TOR is finished risks being "
                             "voided by that TOR - which only happens if both carry the same matching data (Sequence Number, "
                             "Account Number, amount). ATL105 does not say which rule wins for a resubmitted completion."),
                        fix=f"Confirm with BUYPASS. If Element 86 applies, use '{a[key]}' (the Authorization Only's Sequence Number).",
                        refs=rrefs)
                continue
            rep.links.append(dict(step=n, check=label, value=b[key], original=a[key], refs=crefs, result="PASS" if ok else "FAIL"))
            if not ok:
                rep.add(n, "FAIL", f"LINK_{key.upper()}", f"Step {n}: {label.lower()} - check failed", label=key,
                        field=f"{st['td']} -> Standard Message Data Segment -> {key}", value=b[key],
                        why=f"Step {n} has '{b[key]}' and step {of} has '{a[key]}'.",
                        fix=a[key] if same else f"a value different from '{a[key]}'", refs=crefs)
        if role in tcv.SAME_SEQ_ROLES:
            ea, eb = ctx["entries"].get(of), ctx["entries"].get(n)
            ok = ea == eb or (ea == "90" and eb == "02")
            rep.links.append(dict(step=n, check="Same POS entry mode as the original", value=eb, original=ea, refs=["APPJ-PERSIST"],
                                  result="PASS" if ok else "FAIL"))
            if not ok:
                rep.add(n, "FAIL", "LINK_ENTRY_MODE", "The POS entry mode changed on the follow-on step", label="Table 005",
                        field=f"{st['td']} -> Table 005", value=eb, why=f"Step {n} uses entry mode '{eb}'; the original used '{ea}'.",
                        fix=ea, refs=["APPJ-PERSIST"])


def closest_ref(refs, page, quote):
    """Catalog text on the same page sharing at least half of the quote's words (None if nothing related)."""
    words = set(re.findall(r"[a-z0-9]{3,}", tcv.norm(quote).lower()))
    best, score = None, 0.0
    for rid, (_, p, text) in refs.catalog.items():
        if p == page and not rid.startswith("APPD-"):
            s = len(words & set(re.findall(r"[a-z0-9]{3,}", tcv.norm(text).lower()))) / max(1, len(words))
            if s > score:
                best, score = rid, s
    return best if score >= 0.5 else None


def check_citations(rep, tc, meta, refs, qv):
    for fc in meta.get("field_citations", []):
        _, page, text = refs.catalog[fc["topic_ref"]]
        cited = fc["cited_page"]
        if cited == page or refs.spec.has(cited, text):
            continue
        why = (f"The test case cites p.{cited} for the {fc['field']}. In ATL105 {refs.spec.version}, PDF p.{cited} (printed "
               f"{refs.spec.printed(cited)}) does not contain it; it is on PDF p.{page} (printed {refs.spec.printed(page)}).")
        if refs.prior and refs.prior.has(cited, text):
            why += f" p.{cited} is where it was in the older {refs.prior.version} edition."
        rep.add(None, "REVIEW", "CITATION_OUTDATED_PAGE", f"Outdated page cited for the {fc['field']}", label=fc["field"],
                field=fc["where"], value=f"p.{cited}", why=why,
                fix=f"Change the cited page from {cited} to {page}. The data values are not affected.", refs=[fc["topic_ref"]])
    for st in tc["steps"]:
        lc = st.get("link_citation")
        if lc:
            rep.add(st["step"], "REVIEW", "CITATION_WRONG_SECTION", f"The {lc['element']} rule cites the wrong section",
                    label=lc["element"], field=f"test case step {st['step']} -> link rule for {lc['element']}",
                    value=f"p.{lc['cited_page']}",
                    why=f"p.{lc['cited_page']} is {refs.catalog[lc['cited_ref']][0]}: {lc['reason']}.",
                    fix=f"Cite {refs.where(lc['correct_ref'])} instead. The data value itself is correct.",
                    refs=[lc["cited_ref"], lc["step_ref"], lc["correct_ref"]])
    for b in tc["spec_basis"] + meta.get("common_spec_basis", []):
        if (b.get("source_id") or "SRC-ATL105-PDF-001") != "SRC-ATL105-PDF-001" or b.get("source"):
            continue
        if not b.get("quote"):
            rep.add(None, "REVIEW", "QUOTE_MISSING", f"The citation of p.{b.get('page')} has no quoted text",
                    label=f"quote p.{b.get('page')}", field="test case spec_basis",
                    value=f"{b.get('rule_id', '')} p.{b.get('page')}".strip(),
                    why="The spec_basis entry gives a rule id and a page but no ATL105 text, so the basis cannot be checked "
                        "word for word.", fix="Add the ATL105 sentence the rule is based on.", refs=[])
            continue
        if b.get("page") is None:
            what = b.get("element") or "the rule"
            rep.add(None, "REVIEW", "QUOTE_NO_PAGE", f"The citation of {what} gives no ATL105 page",
                    label=f"quote ({what})", field="test case spec_basis", value=b["quote"],
                    why=(f"The spec_basis entry quotes \"{b['quote']}\" with page = null, so it cannot be traced to ATL105. "
                         "The text is a summary of the element's attributes, not an ATL105 sentence."),
                    fix="Cite the Chapter 13 page of the element and quote its ATL105 text word for word.", refs=[])
            continue
        qv.findings = []
        qv.check_quotes({"id": tc["id"], "display_id": tc["display_id"], "template_id": tc["template_id"],
                         "dimensions": tc["dimensions"], "spec_basis": [b]})
        f = qv.findings[0] if qv.findings else None
        page = b["page"]
        status = QUOTE_STATUS.get(f["rule"], f["rule"]) if f else "Found word-for-word on the cited page"
        found = re.findall(r"\d+", f["detail"].rsplit("found on", 1)[1]) if f and "found on" in f["detail"] else []
        if found:
            status += " (" + ", ".join(f"p.{p}" for p in found) + ")"
        rep.quotes.append(dict(page=page, quote=b["quote"], status=status, result=f["severity"] if f else "PASS"))
        if f:
            same_page = [r for r in (closest_ref(refs, page, b["quote"]),) if r]
            fix = (f"Cite p.{found[0]} instead of p.{page}." if found else
                   f"Quote the text exactly as it appears: \"{refs.quote(same_page[0])}\"." if same_page
                   else "Quote the text exactly as it appears on the page.")
            rep.add(None, f["severity"], f["rule"], f"The quote cited for p.{page} {QUOTE_TITLE.get(f['rule'], 'is not in ATL105')}",
                    label=f"quote p.{page}", field="test case spec_basis", value=b["quote"],
                    why=f"The test case quotes \"{b['quote']}\" for p.{page}. {status}.", fix=fix, refs=same_page)
        cn = b.get("context_note")
        if cn:
            rep.add(None, "REVIEW", "QUOTE_CONTEXT", f"The quote cited for p.{page} comes from an unrelated section",
                    label=f"quote p.{page}", field="test case spec_basis", value=b["quote"],
                    why=f"The quote is word-for-word, but it comes from {refs.where(cn['cited_ref'])}; {cn['reason']}.",
                    fix=f"Cite {refs.where(cn['primary_ref'])} as the main basis for a normal MasterCard credit card.",
                    refs=[cn["cited_ref"], cn["primary_ref"]])


def check_test_case_level(rep, tc, ctx, states):
    tid = next((f.get("TerminalIdentifier") or "" for f in ctx["flats"].values()), "")
    if tid and "MERCHANT_PLACEHOLDER" in tc.get("flags", []):
        info = states.get(tid[2:4])
        where = f"{info[0]} ({info[1]}), {info[3]}" if info else "not a valid State Code"
        rep.add(None, "REVIEW", "TERMINAL_PLACEHOLDER", "The Terminal Identifier is a placeholder", label="Terminal Identifier",
                field="Standard Message Data Segment -> TerminalIdentifier (every step)", value=tid,
                why=(f"The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so '{tid}' was generated, not "
                     f"assigned by BUYPASS. Its State Code (positions 3-4) '{tid[2:4]}' = {where}."),
                fix="Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location.",
                refs=["EL102", "EL102-STATE"] + ([f"APPD-{tid[2:4]}"] if info else []))
        if tc.get("status") == "ready":
            rep.add(None, "REVIEW", "READY_WITH_PLACEHOLDER", "Marked 'ready' although the terminal is a placeholder",
                    label="test case status", field="test case status", value="ready",
                    why="The test case status is 'ready', but its Terminal Identifier is still a placeholder.",
                    fix="Keep the test case out of 'ready' until the real Terminal Identifier is supplied.", refs=["EL102"])
    f_steps = [s for s in tc["steps"] if s.get("expected_rc") == "F"]
    if profile(tc)["debit"] and any(ctx["entries"].get(s["step"]) == "10" for s in tc["steps"]):
        rep.add(None, "REVIEW", "DEBIT_COF_PIN", "Credential on File with a PIN debit card is not described for this network",
                label="Table 005 entry mode 10", field="Variable Information Data Segment -> Table 005 (every step)", value="10",
                why=("ATL105 requires PIN entry on every debit transaction except the Purchase Reversal, but a stored-credential "
                     "transaction has no cardholder at the terminal to enter a PIN. ATL105 describes debit Credential on File "
                     "transactions (bill payment, recurring, installment) only for Accel and STAR (Element 12)."),
                fix=f"Confirm with BUYPASS whether {profile(tc)['name']} Credential on File transactions are supported and how the "
                    "PIN requirement applies.", refs=["S10.2.1.3-PIN", "EL12-COF"])
    if f_steps:
        n = f_steps[0]["step"]
        o = ctx["flats"].get(n, {}).get("NonfuelAmount", "")
        rep.add(n, "REVIEW", "PARTIAL_APPROVAL_PRECONDITION", f"Step {n} needs a partial-approval test card", label="test card",
                field=f"test case step {n} expected Response Code", value="F",
                why=(f"Step {n} expects Response Code F (partial approval). BUYPASS returns F only when Partial Approval Indicator 1 "
                     f"is sent and the network approves less than the {amount(o)} requested, so the test needs a card whose "
                     f"available balance is below {amount(o)}."),
                fix="Client to supply a partial-approval test card or simulator trigger (the test case is already 'needs_client_input').",
                refs=["S10.1.2.1-F", "EL83-F", "EL121-LESS"])


def validate_tc(tc, meta, td_dir, ref, refs, qv, states):
    rep = Report(tc)
    ctx = {"flats": {}, "entries": {}, "t031": {}, "t005": {}, "expected": {}, "roots": {}, "emv": {}, "tables": {}}
    for st in tc["steps"]:
        n = st["step"]
        ctx["expected"][n] = st.get("expected_rc")
        path = td_dir / st["td"]
        if not path.exists():
            rep.add(n, "FAIL", "TD_FILE_MISSING", "Test data file not found", label="test data file", field=str(path),
                    value="(missing)", why="The test data file named by the test case does not exist.",
                    fix="Generate the test data file.")
            continue
        td = json.loads(path.read_text(encoding="utf-8"))
        if TOTALS_ROOT in td:
            validate_totals(rep, refs, tc, st, td, ctx, states)
        else:
            validate_step(rep, refs, tc, st, td, ref, ctx, states)
        check_response(rep, st)
    check_links(rep, tc, ctx)
    check_objective(rep, tc, ctx)
    check_citations(rep, tc, meta, refs, qv)
    check_test_case_level(rep, tc, ctx, states)
    return rep, ctx


def cross_checks(results, ctxs):
    cross = []
    seqs = {}
    for tc, _ in results:
        for st in tc["steps"]:
            f = ctxs[tc["id"]]["flats"].get(st["step"])
            if f and st["role"] not in tcv.SAME_SEQ_ROLES:
                seqs.setdefault((f["TerminalIdentifier"], f["SequenceNumber"]), []).append(f"{tc['id']} step {st['step']}")
    dup = {k[1]: v for k, v in seqs.items() if len(v) > 1}
    cross.append(dict(result="FAIL" if dup else "PASS", check="Every new transaction on the terminal has its own Sequence Number",
                      detail=f"Duplicates: {dup}" if dup else f"{len(seqs)} new transactions; all Sequence Numbers are different",
                      refs=["EL86-UNIQUE"]))
    by_terminal = {}
    for tc, _ in results:
        c = ctxs[tc["id"]]
        for s, f in c["flats"].items():
            if c["roots"].get(s) == TOTALS_ROOT:
                continue
            by_terminal.setdefault(f["TerminalIdentifier"], []).append((tc["id"], c["t031"].get(s), (c["t005"].get(s) or "")[2:3]))
    for tid, items in by_terminal.items():
        caps = sorted({x[1] for x in items if x[1]})
        cross.append(dict(result="PASS" if len(caps) <= 1 else "FAIL", check=f"Terminal {tid} keeps one Table 031 value",
                          detail=(f"Table 031 = {', '.join(caps)} in all {len(items)} messages" if len(caps) <= 1
                                  else f"Table 031 values {caps}"), refs=["APPI-031-STABLE"]))
        pins = {}
        for who, _, pin in items:
            if who not in pins.setdefault(pin, []):
                pins[pin].append(who)
        if len(pins) > 1:
            parts = "; ".join(f"code {p} in {', '.join(ws)}" for p, ws in sorted(pins.items()))
            cross.append(dict(result="REVIEW", check=f"Terminal {tid} uses more than one terminal capability code (Table 005, position 3)",
                              detail=f"{parts}. Appendix J note 1 says all transactions from a device use one code.",
                              fix="Use a separate terminal for the RFID test.", refs=["APPJ-NOTE1"], listed=True))
    singles = [(tc, rep, ctxs[tc["id"]]) for tc, rep in results if len(tc["steps"]) == 1]
    for i, (tb, rb, cb) in enumerate(singles):
        for ta, _, ca in singles[:i]:
            fa, fb = ca["flats"].get(1), cb["flats"].get(1)
            if not (fa and fb) or any(fa[k] != fb[k] for k in fa if k != "SequenceNumber"):
                continue
            if (ca["t005"].get(1), ca["t031"].get(1)) != (cb["t005"].get(1), cb["t031"].get(1)):
                continue
            objective = next((f for f in rb.findings if f["rule"] in OBJECTIVE_RULES), None)
            if objective:
                objective["why"] += (f" Apart from the Sequence Number, the request is identical to {ta['id']} "
                                     f"({ta['template_id']}), so BUYPASS cannot tell the two tests apart.")
                break
            checks = tb.get("device_checkpoints") or []
            why = (f"The request is identical to {ta['id']} ({ta['template_id']}) except the Sequence Number: same card "
                   f"{fb['AccountNumber'][:6]}...{fb['AccountNumber'][-4:]}, expiry {fb['CardDiscretionaryBlockData']}, amount "
                   f"{amount(fb['NonfuelAmount'])} and entry mode {cb['t005'].get(1)}. ")
            why += (f"What makes this test different ({'; '.join(checks)}) happens on the device and is not visible in the "
                    "message sent to BUYPASS." if checks else "BUYPASS cannot tell the two tests apart.")
            fix = (("Verify on the device: " + "; ".join(checks) + ". ") if checks else "") + \
                "Optionally use a different card so the logs can tell the two tests apart."
            rb.add(None, "REVIEW", "SAME_REQUEST_AS_OTHER_TC", f"Same request as {ta['id']}", label="request message",
                   field=f"test_data/{tb['steps'][0]['td']}", value=f"identical to {ta['steps'][0]['td']} except SequenceNumber",
                   why=why, fix=fix, refs=tb.get("device_refs", []))
            cross.append(dict(result="REVIEW", check=f"{tb['id']} sends the same request as {ta['id']}", detail=why, fix=fix,
                              refs=tb.get("device_refs", []), listed=False))
            break
    return cross


def legend(spec):
    return [f"All page references are to the {SPEC_TITLE}, release {spec.version} ({spec.date}). \"PDF p.\" is the page number "
            "shown in the PDF viewer; \"printed\" is the page number in the document footer.", "",
            "- **FAIL** - a value in the test data breaks an ATL105 rule. It must be fixed.",
            "- **BLOCKED** - the field holds instruction text instead of a value. It must be filled in from the step 1 response "
            "before the message can be sent.",
            "- **REVIEW** - the data values are acceptable, but something in the test case must be confirmed or corrected.",
            "- **INFO** - a note for the team that builds the wire message.",
            "- **PASS** - matches ATL105.", ""]


def finding_block(refs, f, num):
    lines = [f"### {num}. {f['title']} - {f['severity']}", ""]
    if f["step"]:
        lines.append(f"- **Step:** {f['step']}")
    lines += [f"- **Where:** {f['field']}", f"- **Value:** `{f['value']}`", "- **What ATL105 says:**"]
    lines += [f"  - {refs.full(r)}" for r in f["refs"]] or ["  - (no specific ATL105 page)"]
    lines += [f"- **Why:** {f['why']}", f"- **What it should be / action:** {f['fix']}", ""]
    return lines


def write_tc_md(out, refs, tc, rep, name=None, td_prefix="test_data/"):
    L = [f"# {tc['id']} ({tc['display_id']}) - ATL105 Test Data Validation", "", f"**{tc['name']}**", "",
         "| Item | Value |", "|---|---|",
         f"| Result | **{rep.verdict()}** |",
         f"| Can it run as-is? | {rep.can_run()} |",
         "| Test data | " + (", ".join(f"`{td_prefix}{s['td']}`" for s in tc["steps"])
                             or "none – device-only test case: no message is sent to BUYPASS") + " |",
         f"| Template / status | `{tc['template_id']}` / `{tc['status']}` |",
         f"| Specification | {SPEC_TITLE}, release {refs.spec.version} ({refs.spec.date}) |", ""]
    L += legend(refs.spec)
    if tc.get("device_only"):
        L += ["## Device-only test case", "", "No message is sent to BUYPASS, so there is no test data to check against the ATL105 "
              "message layouts. The check covers the ATL105 text the test case quotes (end of this report).", ""]
        L += [f"- **{k}:** {esc(v)}" for k, v in tc["device_only"].items() if v] + [""]
    num = 0
    for sev, head in (("FAIL", "What must be fixed"), ("BLOCKED", "Values to fill in at run time"),
                      ("REVIEW", "Items to confirm"), ("INFO", "Notes for the message builder")):
        fs = sorted((f for f in rep.findings if f["severity"] == sev), key=lambda f: RULE_ORDER.get(f["rule"], 0))
        if sev == "FAIL" and not fs:
            L += ["## What must be fixed", "", "Nothing - no value in the test data breaks an ATL105 rule.", ""]
        if fs:
            L += [f"## {head}", ""]
            for f in fs:
                num += 1
                L += finding_block(refs, f, num)
    for st in tc["steps"]:
        n = st["step"]
        L += [f"## Field-by-field check - step {n}, {st['role'].replace('_', ' ')} (`{st['td']}`)", "",
              "| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |",
              "|---|---|---|---|---|---|---|---|---|"]
        L += [f"| {r['seg']} | {r['fno']} | {r['el']} | {esc(r['name'])} | `{esc(r['td'])}` | `{esc(r['tc'])}` | {esc(r['rule'])} | "
              f"{esc(refs.shorts(r['refs']))} | {r['result']} |" for r in rep.rows if r["step"] == n]
        L.append("")
        if rep.emv.get(n):
            L += [f"### EMV Chip Data (Element 190) decoded - step {n}", "",
                  "Decoded from the hexadecimal test data (2-byte BCD length first, Appendix R). Tag names follow EMV Book 3; "
                  "Appendix R shows an example tag set only.", "",
                  "| Tag | Name | Length (bytes) | Value | In the Appendix R example |", "|---|---|---|---|---|"]
            L += [f"| {t} | {EMV_TAGS.get(t, '-')} | {ln} | `{x}` | {'yes' if t in APPR_TAGS else 'no'} |" for t, ln, x in rep.emv[n]]
            L.append("")
    if rep.links:
        L += ["## Step-to-step checks", "", "| Step | Check | This step | Original step | Where in ATL105 | Result |",
              "|---|---|---|---|---|---|"]
        L += [f"| {x['step']} | {x['check']} | `{x['value']}` | `{x['original']}` | {esc(refs.shorts(x['refs']))} | {x['result']} |"
              for x in rep.links]
        L.append("")
    if rep.responses:
        L += ["## Expected response codes", "", "| Step | Expected Response Code | Meaning | Where in ATL105 | Result |",
              "|---|---|---|---|---|"]
        L += [f"| {x['step']} | `{x['code']}` | {x['meaning']} | {esc(refs.shorts(x['refs']))} | {x['result']} |" for x in rep.responses]
        L.append("")
    if rep.quotes:
        L += ["## Spec text quoted by the test case", "", "| Cited page | Quote in the test case | Check | Result |", "|---|---|---|---|"]
        L += [f"| PDF p.{q['page']} (printed {refs.spec.printed(q['page'])}) | {esc(q['quote'])} | {q['status']} | {q['result']} |"
              for q in rep.quotes]
        L.append("")
    (out / (name or f"{tc['id']}.md")).write_text("\n".join(L) + "\n", encoding="utf-8")


def fail_groups(results):
    """FAIL/BLOCKED findings grouped per test case, step and issue; only groups containing a FAIL."""
    groups = []
    for tc, rep in results:
        by = {}
        for f in rep.findings:
            if f["severity"] in ("FAIL", "BLOCKED"):
                by.setdefault((f["step"], f["issue"]), []).append(f)
        groups += [(tc, rep, step, issue, sorted(fs, key=lambda f: (SEVERITY_ORDER[f["severity"]], RULE_ORDER.get(f["rule"], 0))))
                   for (step, issue), fs in by.items() if any(f["severity"] == "FAIL" for f in fs)]
    return groups


def primary_refs(fs, minimum=3):
    """First reference of each finding, topped up from the remaining ones to at least `minimum`."""
    picked = uniq(f["refs"][0] for f in fs if f["refs"])
    for r in (r for f in fs for r in f["refs"]):
        if len(picked) >= minimum:
            break
        if r not in picked:
            picked.append(r)
    return picked


def group_title(rep, issue, fs):
    return rep.issue_titles.get(issue) or next(f["title"] for f in fs if f["severity"] == "FAIL")


def review_groups(results):
    groups = {}
    for tc, rep in results:
        for f in rep.findings:
            if f["severity"] == "REVIEW":
                g = groups.setdefault(f["rule"], (f["title"], [], f))
                if tc["id"] not in g[1]:
                    g[1].append(tc["id"])
    return list(groups.values())


def td_file(tc, step):
    return next((s["td"] for s in tc["steps"] if s["step"] == step), "")


def write_key_findings(out, refs, results, cross):
    ids = [tc["id"] for tc, _ in results]
    groups = fail_groups(results)
    K = [f"# ATL105 Test Data Review - Key Findings ({ids[0]} to {ids[-1]})", "",
         f"All page references are to the {SPEC_TITLE}, release {refs.spec.version} ({refs.spec.date}). \"PDF p.\" is the page "
         "number in the PDF viewer; \"printed\" is the page number in the document footer.", "",
         "\"Test data is wrong\" means a value in the request message (the JSON test data file) breaks a rule written in ATL105, "
         "so BUYPASS may reject the message or the test would not prove what it claims.", "",
         "## Result per test case", "", "| Test case | Test data | Result | Can it run as-is? |", "|---|---|---|---|"]
    for tc, rep in results:
        K.append(f"| {tc['id']} | " + ", ".join(f"`{s['td']}`" for s in tc["steps"]) + f" | **{rep.verdict()}** | {rep.can_run()} |")
    K += ["", f"## The {len(groups)} errors that must be fixed", "",
          "| Test case and field | Value in test data | What ATL105 says (where) | Why it's wrong | What it should be |",
          "|---|---|---|---|---|"]
    for tc, rep, step, issue, fs in groups:
        fields = uniq(f["label"].replace(" (test case value)", "") for f in fs)
        values = [f"`{fs[0]['value']}`"] if len(fs) == 1 else uniq(f"{f['label']}: `{f['value']}`" for f in fs)
        says = [f"{refs.short(r)}: \"{refs.quote(r)}\"" for r in primary_refs(fs)]
        K.append(f"| **{tc['id']} step {step}** - {esc(', '.join(fields))} | " + "<br>".join(esc(x) for x in values) + " | "
                 + "<br>".join(esc(x) for x in says) + " | " + "<br>".join(esc("- " + x) for x in uniq(f["brief"] for f in fs))
                 + " | " + "<br>".join(esc(x) for x in uniq(f["fix"] for f in fs)) + " |")
    K += ["", "## Things to confirm (the data values are acceptable)", ""]
    for title, tcs, f in review_groups(results):
        K.append(f"- **{title}** ({', '.join(tcs)}): {f['brief']} ATL105: {refs.shorts(f['refs'])}. Action: {f['fix']}")
    for c in cross:
        if c["result"] == "REVIEW" and c.get("listed"):
            K.append(f"- **{c['check']}**: {c['detail']} ATL105: {refs.shorts(c['refs'])}. Action: {c['fix']}")
    notes = uniq((f["title"], f["why"], tuple(f["refs"]), f["fix"]) for _, rep in results for f in rep.findings if f["severity"] == "INFO")
    if notes:
        K += ["", "## Note for the team that builds the wire message", ""]
        K += [f"- **{t}**: {w} ATL105: {refs.shorts(r)}. {x}" for t, w, r, x in notes]
    K += ["", "All ATL105 texts quoted above were checked against the spec text on the stated page. Every reference for each "
          "issue is listed in the summary.", "",
          "Full details: [ATL105-TD-Validation-Summary.md](ATL105-TD-Validation-Summary.md) and the per-test-case reports "
          + ", ".join(f"[{i}.md]({i}.md)" for i in ids) + ".", ""]
    (out / "ATL105-TD-Validation-Key-Findings.md").write_text("\n".join(K) + "\n", encoding="utf-8")


def write_summary(out, refs, results, cross):
    ids = [tc["id"] for tc, _ in results]
    S = [f"# ATL105 Test Data Validation - Summary ({ids[0]} to {ids[-1]})", "",
         f"Generated by `scripts/validate-td-against-atl105-spec.py` on {TODAY.isoformat()} from the test data in "
         "`regression-artifact-validator/test_data/` (the workspace-root `test_data/` folder holds only TD-TC-001722 to 001741).", ""]
    S += legend(refs.spec)
    S += ["## Results", "",
          "| Test case | Template | Result | Can it run as-is? | Must fix | Fill in at run time | To confirm | Fields checked | Report |",
          "|---|---|---|---|---|---|---|---|---|"]
    for tc, rep in results:
        S.append(f"| {tc['id']} ({tc['display_id']}) | {tc['template_id']} | **{rep.verdict()}** | {rep.can_run()} | "
                 f"{rep.count('FAIL')} | {rep.count('BLOCKED')} | {rep.count('REVIEW')} | {len(rep.rows)} | [{tc['id']}.md]({tc['id']}.md) |")
    S += ["", "One-page version for sharing: [ATL105-TD-Validation-Key-Findings.md](ATL105-TD-Validation-Key-Findings.md).", "",
          "## What must be fixed", ""]
    for i, (tc, rep, step, issue, fs) in enumerate(fail_groups(results), 1):
        S += [f"### {i}. {tc['id']} step {step} - {group_title(rep, issue, fs)}", "",
              f"- **Test data file:** `test_data/{td_file(tc, step)}`",
              "- **Fields and values:** " + "; ".join(uniq(f"{f['label']} = `{f['value']}`" for f in fs)),
              "- **What ATL105 says:**"]
        S += [f"  - {refs.full(r)}" for r in uniq(r for f in fs for r in f["refs"])]
        S += ["- **Why it is wrong:**"] + [f"  - {w}" for w in uniq(f["why"] for f in fs)]
        S += ["- **What it should be:**"] + [f"  - {x}" for x in uniq(f["fix"] for f in fs)] + [""]
    S += ["## Items to confirm", "", "| What was found | Test cases | Where in ATL105 | What to do |", "|---|---|---|---|"]
    for title, tcs, f in review_groups(results):
        S.append(f"| **{esc(title)}**: {esc(f['why'])} | {', '.join(tcs)} | {esc(refs.shorts(f['refs']))} | {esc(f['fix'])} |")
    for c in cross:
        if c["result"] == "REVIEW" and c.get("listed"):
            S.append(f"| **{esc(c['check'])}**: {esc(c['detail'])} | all | {esc(refs.shorts(c['refs']))} | {esc(c['fix'])} |")
    S += ["", "## Checks across test cases", "", "| Result | Check | Detail | Where in ATL105 |", "|---|---|---|---|"]
    for c in sorted(cross, key=lambda c: SEVERITY_ORDER.get(c["result"], 9)):
        S.append(f"| {c['result']} | {esc(c['check'])} | {esc(c['detail'])} | {esc(refs.shorts(c['refs']))} |")
    S += ["", "## ATL105 references used in this review", "",
          "Every text below was searched in the spec text on its page during this run (\"verified\" = found there).", "",
          "| Where | Exact text | Text check |", "|---|---|---|"]
    for rid in sorted(refs.used, key=lambda r: (refs.catalog[r][1], r)):
        S.append(f"| {esc(refs.where(rid))} | {esc(refs.quote(rid))} | {refs.status[rid]} |")
    S += ["", "## Scope and limits", "",
          "- Checked: every Data Section 1 element, all 17 Segment 100 fields and every Segment 111 element of every test data file; "
          "segment lengths recalculated from the field data plus Field Separators; character type and length; code tables "
          "(Appendix D, E, G, J, Table 031); card-number check digit and MasterCard range; expiry format; test data versus test case "
          "request values; step-to-step links; expected response codes; and every spec quote and cited page in the test cases.",
          "- Not certified: the actual host responses (Response Code F or 0) need execution; the merchant and terminal are "
          "placeholders; run-time values must be filled in from the step 1 responses.", ""]
    narrative = out / "ATL105-TD-Validation-Findings-Explained.md"
    if narrative.exists():
        S += [f"A hand-written narrative of the same findings: [{narrative.name}]({narrative.name}).", ""]
    (out / "ATL105-TD-Validation-Summary.md").write_text("\n".join(S) + "\n", encoding="utf-8")


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--metadata", default=str(ROOT / "Manual_Keyed Transaction" / "inputs" / "tc-001867-001871.metadata.json"))
    ap.add_argument("--test-data", default=str(ROOT / "test_data"))
    ap.add_argument("--spec-text", default=str(tcv.DEFAULT_SPEC))
    ap.add_argument("--prior-spec-text", default=str(tcv.PRIOR_SPEC))
    ap.add_argument("--out", default=str(ROOT / "Manual_Keyed Transaction"))
    a = ap.parse_args()

    meta = json.loads(Path(a.metadata).read_text(encoding="utf-8"))
    ref_json = json.loads(tcv.ELEMENT_REF.read_text(encoding="utf-8"))
    ref = {e["element"]: e for e in ref_json["elements"]}
    spec = Spec(Path(a.spec_text))
    prior = Spec(Path(a.prior_spec_text)) if Path(a.prior_spec_text).exists() else None
    states = spec.state_codes()
    refs = Refs(spec, prior, states)
    qv = tcv.Validator(spec.sq, {}, ref_json)
    out = Path(a.out)
    out.mkdir(parents=True, exist_ok=True)

    results, ctxs = [], {}
    for tc in meta["test_cases"]:
        rep, ctx = validate_tc(tc, meta, Path(a.test_data), ref, refs, qv, states)
        results.append((tc, rep))
        ctxs[tc["id"]] = ctx
    cross = cross_checks(results, ctxs)
    for tc, rep in results:
        write_tc_md(out, refs, tc, rep)
    write_key_findings(out, refs, results, cross)
    write_summary(out, refs, results, cross)

    for tc, rep in results:
        print(f"{tc['id']} {rep.verdict():7} FAIL={rep.count('FAIL')} BLOCKED={rep.count('BLOCKED')} "
              f"REVIEW={rep.count('REVIEW')} fields={len(rep.rows)}")
    unverified = {r: s for r, s in refs.status.items() if s != "verified"}
    print(f"ATL105 references used: {len(refs.used)}; not verified in spec text: {unverified or 'none'}")


if __name__ == "__main__":
    main()
