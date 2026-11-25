#!/usr/bin/env python3
"""Write a manual validation guide per test case into 'Manual Validation/'.

Each guide is a step-by-step path for checking one test case and its test data by hand: how the test data was generated
(test_cases/test_cases.jsonl), what every field must contain and how to check it, worked Segment Length and check-digit
calculations, links between steps, citations, and every issue the AI validator (validate-td-against-atl105-spec.py)
reported, with steps to confirm it. The ATL105 text needed for the checks is copied into the guide; every copied line is
verified against the spec text on its PDF page before anything is written.
"""

import argparse
import importlib.util
import json
import re
from collections import Counter
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
_spec = importlib.util.spec_from_file_location("tdv", ROOT / "scripts" / "validate-td-against-atl105-spec.py")
tdv = importlib.util.module_from_spec(_spec)
_spec.loader.exec_module(tdv)
tcv = tdv.tcv
amount, esc, uniq = tdv.amount, tdv.esc, tdv.uniq


def L(page, text):
    return ("L", page, text, None)


def H(*cells):
    return ("H", None, cells, None)


def R(page, *cells, verify=None):
    return ("R", page, cells, verify)


SEG_COLS = ("Field No.", "Element No.", "Element Name", "Len.", "Entry (R/O/C)", "Description")
SEG100_LAYOUT = [
    (223, 1, "85", "Segment Type", "3", "R", "Identifies the type of data segment being formatted. Fixed value: 100 Source: Device"),
    (223, 2, "84", "Segment Length", "3", "R", "Identifies the data segment’s length, including Segment Type’s length and Field "
                                               "Separators. Source: Device"),
    (223, 3, "44", "Information Byte", "1", "R", "Indicates whether the request is a single-message request or a multimessage "
                                                 "request. Source: Device"),
    (223, 4, "102", "Terminal Identifier", "Var.", "R", "Identifies the device by Device Type, State Code, BUYPASS Merchant "
                                                        "Number, and Device Number. Source: Device"),
    (223, 5, "78", "Prompt Code", "4", "R", "Identifies the Transaction Type and Card Type. Source: Device"),
    (224, 6, "2", "Account Number", "Var.", "R", "Identifies a portion of the cardholder’s account number or identification "
                                                 "number. Source: Device"),
    (224, 7, "12", "Card Discretionary Block Data", "51", "C", "Identifies the card’s expiration date and discretionary data. "
                                                               "Source: Device"),
    (224, 8, "33", "Encrypted PIN Block Data", "36", "C", "For DUKPT PIN encryption, this is a 16- to 20-byte KSN plus a 16-byte "
                                                          "PIN block. Source: Cardholder"),
    (224, 9, "79", "Pump/Lane Number", "2", "C", "Identifies the pump or lane number at the point-of-service location. Source: Device"),
    (224, 10, "41", "Fuel Purchase Amount", "8", "C", "Identifies the net total dollar amount of all fuel purchase amounts less any "
                                                      "applicable discounts and/or coupons. Source: Device"),
    (224, 11, "58", "Nonfuel Amount", "8", "C", "Identifies the total net dollar amount of all nonfuel, noncash, and nontax amounts "
                                                "less any applicable discounts and/or coupons. Source: Device"),
    (224, 12, "99", "Tax Amount", "8", "C", "Identifies the total dollar amount of all tax amounts. Source: Device"),
    (224, 13, "17", "Cash Amount", "8", "C", "Identifies the total dollar amount of all cash amounts. Source: Device"),
    (224, 14, "86", "Sequence Number", "6", "R", "Identifies the unique transaction sequence number assigned to and associated with "
                                                 "a transaction throughout its life. Source: Device"),
    (224, 15, "5", "Approval Number", "6", "C", "Identifies the assigned Approval Number. Source: Device"),
    (225, 16, "49", "Local Date and Local Time", "10", "C", "Identifies the local date and local time of the preauthorized "
                                                            "transaction. Source: Device"),
    (225, 17, "121", "Partial Approval Indicator", "1", "R", "Indicates whether or not a merchant supports credit/debit Partial "
                                                             "Approvals. Source: Device"),
]

# ATL105 text copied into the guides: id -> (title, items). Appendix D is added at run time for the State Codes in use.
EXTRACTS = {
    "S2.3.1": ("Section 2.3.1 Time-out by Customer's Host/Controller/POS System", [
        L(40, "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."),
        L(40, "The Sequence Number, Account Number, and dollar amount—assigned by the customer’s host/controller/POS system—are "
              "the elements used to match a request with its respective response."),
        L(40, "Each time any financial transaction request is forwarded to BUYPASS, the financial transaction should be assigned "
              "a new Sequence Number by the customer’s host/controller/POS system."),
    ]),
    "S10.1.1": ("Section 10.1.1 Card Requirements (10.1.1.1 Expiration Date, 10.1.1.3 Manual Entry)", [
        L(81, "10.1.1.1 Expiration Date"),
        L(81, "The device performs expiration date validation in a credit card transaction."),
        L(81, "Note: Dates up to and including December, 2049 (Format: 1249) are assumed to be in the twenty-first century."),
        L(81, "10.1.1.3 Manual Entry"),
        L(81, "Manual entry is allowed on a credit card transaction. If the device is unable to read the card's magnetic strip, it "
              "should prompt the user to swipe the card again—up to three times. If unable to read the magnetic strip after three "
              "attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format: MMYY)."),
        L(81, "Note: If manual entry is used, this fact is indicated on the receipt and journal by an asterisk (*) before the "
              "Account Number."),
    ]),
    "S10.1.2": ("Section 10.1.2 Supported Transaction Types", [
        L(83, "The following point-of-sale transactions are supported for credit card processing:\n• Purchase/Capture\n"
              "• Purchase Reversal\n• Authorization Only\n• Merchandise Return\n• Time-out Reversal\n• Mail/Phone Purchase\n"
              "• Mail/Phone Authorization Only\n• Mail/Phone Reversal\n• Void of Merchandise Return\n• Balance Inquiry\n"
              "• Partial Approval\n• Authorization Only Reversal"),
    ]),
    "S10.1.2.1": ("Section 10.1.2.1 Partial Approval Transaction", [
        L(84, "A Partial Approval transaction enables a merchant to receive approval for the amount available on the presented "
              "credit card when the account balance is more or less than the amount of the Transaction Request."),
        L(84, "Credit networks supporting the Partial Approval transaction return an approval containing the following "
              "information:\nNote: This information is returned when a Partial Approval Indicator value of 1 is sent in Partial "
              "Approval Indicator (Element No. 121).\n• A Response Code (Element No. 83) value of F (Indicates “approval, partial "
              "amount approved.”)\n• Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must "
              "prompt for payment of the remaining balance due on the sale.\n• The card balance—if returned by the issuer—is in "
              "the Additional Information Data Segment (Segment No. 112)."),
        L(84, "Partial Approval transactions are supported for:\n• POS Purchase\n• POS Authorization Only\n• CAT Authorization Only"),
        L(84, "The amount in a Reversal (Void) request must be the amount approved in the original transaction response when a "
              "transaction has been partially approved. BUYPASS supports reversal of a partially approved original authorization "
              "and NOT a partial reversal of an original transaction."),
        L(84, "The amount in a TOR request must be the amount requested in the original transaction response when a transaction "
              "has been partially approved."),
        L(84, "The Sequence Number must be the Sequence Number contained in the original transaction request."),
    ]),
    "S10.1.2.2": ("Section 10.1.2.2 Authorization Only Reversal Transaction", [
        L(85, "This transaction enables a merchant to reverse the following credit card transactions within required timeframes:\n"
              "• POS Authorization Only (Transaction Type code “3”)\n• CAT Authorization Only (Transaction Type code “5”)"),
        L(85, "Successful reversal matching requires the following:\n• Element No. 5 (Approval Number)—present in the original "
              "transaction—must be present and identical in the Authorization Only Reversal transaction.\n• Element No.86 "
              "(Sequence Number)—present in the original transaction—must be present and identical in the Authorization Only "
              "Reversal transaction.\n• Element No. 78 (Prompt Code) must contain a Transaction Type code “S” to indicate "
              "cancellation."),
    ]),
    "S10.1.5": ("Section 10.1.5 Radio Frequency Identification Service", [
        L(86, "Radio frequency identification (RFID) service is an optional service."),
        L(86, "RFID processing requires that the appropriate values be sent in Point-of-Sale Entry Mode (Contained in Element No. "
              "113, Variable Information.) on all transactions when an RFID receiver is connected to the point-of-sale device or "
              "controller. Even if the transaction is swiped, manually keyed, or uses a card type for which RFID is not "
              "applicable, Point-of-Sale Entry Mode must be populated correctly in the transaction request from the point-of-sale "
              "when an RFID receiver is present."),
    ]),
    "S10.1.6": ("Section 10.1.6 Healthcare Auto-Substantiation Service (contains 10.1.6.1.3 Split Tender Processing)", [
        L(87, "10.1.6 Healthcare Auto-Substantiation Service"),
        L(87, "This service identifies and substantiates medical purchases, including over-the-counter (OTC) purchases, for "
              "flexible spending accounts (FSA) and health reimbursement arrangement (HRA) accounts."),
        L(87, "10.1.6.1.3 Split Tender Processing"),
        L(87, "A Split Tender transaction results when a Partial Approval is returned to the POS in the transaction response. The "
              "POS device must then prompt for payment of the remaining balance due on the sale. The cardholder uses a different "
              "form of payment for nonqualified items, such as another payment card, cash, or a check."),
    ]),
    "S10.1.7": ("Section 10.1.7 Receipt Requirements", [
        L(91, "The Transaction Type prints on all receipts."),
        L(91, "The type of card prints on all credit card receipts. Card Type ID’s are:"),
        L(91, "MasterCard®: MC"),
        L(92, "The Account Number (Element No. 2) must be truncated—suppressed to its last four digits—on all transaction receipts."),
        L(92, "Note: If manual entry is used, this fact is indicated on the receipt and journal by an asterisk (*) before the "
              "Account Number."),
        L(92, "The Expiration Date must be suppressed on all transaction receipts."),
        L(92, "The date of the transaction prints on all credit card receipts."),
        L(92, "The time of the transaction prints on all credit card receipts."),
        L(92, "The transaction's Sequence Number appears on all credit card receipts."),
        L(92, "Balance information prints on a credit card receipt for a Partial Approval transaction and a Balance Return with "
              "Purchase transaction."),
        L(93, "The approval phrase, including the approval code, must be on a receipt for an \"approved\" transaction that "
              "involves a point-of-sale device."),
        L(93, "A signature line prints only on the merchant’s copy of the credit card transaction receipt."),
    ]),
    "S10.1.8.2": ("Section 10.1.8.2 Track Support Requirements", [
        L(94, "The host message should only contain the following card data information in the Account Number and Card "
              "Discretionary Block Data fields:\n• Account Number\n• Expiration Date (Field format: YYMM)"),
        L(94, "Note: These two items must be separated by an “=” sign, rather than a Field Separator."),
        L(94, "• The host message should only contain the Account Number, followed by an account delimiter (=), followed by the "
              "4 digit Expiration Date (Storage format – YYMM). No other"),
        L(95, "Card Discretionary Data field follows the 4-digit Expiration Date. This scenario applies only to the following "
              "credit transactions:\n• Credit Completions\n• Credit POS Purchase Reversals\n• Credit Time Out Reversals\n"
              "• Credit POS Cancellations\n• Credit Cancellations"),
    ]),
    "S11.1": ("Section 11.1 Financial Transactions - layout of a Financial Transaction Request", [
        L(167, "All Financial Transaction Requests contain Element Nos. 55 and 63."),
        L(167, "Note: A Field Separator separates Field Nos. 1 and 2; and a Field Separator follows Field No. 2."),
        H("Field No.", "Ele. No.", "Element Name", "Len.", "Entry (R/O/C)", "Description"),
        R(167, "1", "55", "Message Format Version Identifier", "6", "R", "Identifies the message format type and release version."),
        R(167, "2", "63", "Number of Segments", "2", "R", "Identifies the number of BUYPASS-defined segments included in the message."),
        L(167, "All Financial Transaction Requests contain Segment No. 100."),
        H("Field No.", "Seg. No.", "Segment Name", "Max. Len.", "Entry (R/O/C)", "Description"),
        R(167, "3", "100", "Standard Message Data Segment", "218", "R", "Contains the standard data required for all financial "
          "transactions. This segment is the only segment required for all financial transactions."),
        L(168, "All Financial Transaction Requests contain none, one, or more of the following data segments in Field Nos. 4–9."),
        H("Field No.", "Seg. No.", "Segment Name", "Max. Len.", "Entry (R/O/C)", "Description"),
        R(168, "4–9", "111", "Variable Information Data Segment", "999", "C", "Contains data elements unique to transactions "
          "requiring variable information.", verify="111 Variable Information Data Segment 999 C Contains data elements unique "
          "to transactions requiring variable information."),
    ]),
    "S12.1": ("Section 12.1 Standard Message Data Segment (Segment 100)", [
        L(223, "It is the only required data segment on all Financial Transaction Request messages."),
        L(223, "Any trailing fields within the data segment that are not needed should not be transmitted. For example, if a data "
               "segment has five fields defined, but the last two fields are optional, only three fields should be transmitted "
               "with the third field followed by a Field Separator."),
        L(223, "Note: All fields are separated by a Field Separator. When a field is not populated, still send the Field Separator."),
        H(*SEG_COLS),
    ] + [R(p, str(f), el, name, ln, rc, d) for p, f, el, name, ln, rc, d in SEG100_LAYOUT]),
    "S12.10": ("Section 12.10 Variable Information Data Segment (Segment 111)", [
        L(246, "This section describes Data Segment No. 111, Variable Information Data Segment."),
        L(246, "This data segment can appear in any of the fields in data Section No. 3."),
        L(246, "It has a maximum length of 999 alphanumeric characters (01–999/a-z/A–Z)."),
        L(246, "Note: There is a Field Separator between Field Nos. 1 and 2 and between Field No. 2 and Variable Information "
               "Section. Field Separators are not between the elements in Variable Information Section. Field Separators are not "
               "between repetitions of Variable Information Section. A Field Separator follows the final Variable Information "
               "Section."),
        H(*SEG_COLS),
        R(246, "1", "85", "Segment Type", "3", "R", "Identifies the type of data segment being formatted. Fixed value: 111 Source: Device"),
        R(246, "2", "84", "Segment Length", "3", "R", "Identifies the data segment’s length, including Segment Type’s length and "
          "Field Separators. Source: Device"),
        L(246, "Variable Information Section: This section is repeated—by Variable Information Indicator—for a total variable "
               "length of up to 991 bytes. The Field Separator that follows Field No. 5 is included with the last repetition."),
        H(*SEG_COLS),
        R(246, "3", "111", "Variable Information Indicator", "3", "R", "Identifies the type of variable information. Source: Device"),
        R(246, "4", "112", "Variable Information Length", "3", "R", "Identifies the length of Variable Information (Element No. "
          "113). Source: Device"),
        R(246, "5", "113", "Variable Information", "Var.", "R", "Identifies the variable information. Source: Device"),
    ]),
    "EL2": ("Element 2 Account Number (Chapter 13)", [
        L(363, "Number: 2 Name: Account Number"),
        L(363, "Character Type: ANS Maximum Length: Non-TransArmor (all requests): 24 bytes"),
        L(364, "Non-TransArmor—Initial Transaction—Request: Format: 123456789012nn…nn Where 123456789012nn…nn = a variable length "
               "(up to 24 alphanumeric bytes) Account Number."),
        L(364, "Purpose: Non-TransArmor: Identifies the card or account."),
        L(365, "Valid Codes/Values: Credit, debit, and EBT cards: Please refer to the most recent version of BUYPASS® Platform "
               "Issuer Identification Number (IIN) Reference Manual."),
    ]),
    "EL5": ("Element 5 Approval Number (Chapter 13)", [
        L(368, "Number: 5 Name: Approval Number"),
        L(368, "Character Type: AN Maximum Length: 6 bytes"),
        L(368, "Representation: Fixed length of six alphanumeric characters; space-filled"),
        L(368, "Purpose: Identifies the assigned Approval Number."),
        L(368, "Processing Rules: Required on reversals for matching information to the original purchase. If it is included in a "
               "Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for "
               "authorization."),
        L(368, "Valid Codes/Values: Any alphanumeric representation, including spaces"),
    ]),
    "EL6": ("Element 6 Approved Amount (Chapter 13)", [
        L(368, "Number: 6 Name: Approved Amount"),
        L(368, "Character Type: N Maximum Length: 9 bytes"),
        L(368, "Representation: Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned"),
        L(368, "Purpose: Identifies the amount approved by the issuer."),
        L(368, "Processing Rules: The Approved Amount may not be the same as the requested amount."),
        L(368, "If the transaction is a Partial Approval transaction, the amount approved is contained in this field, and the POS "
               "device must prompt for payment of the remaining balance due in the sale."),
        L(368, "Valid Codes/Values: 000000001–999999999"),
    ]),
    "EL12": ("Element 12 Card Discretionary Block Data (Chapter 13)", [
        L(371, "Number: 12 Name: Card Discretionary Block Data"),
        L(371, "Character Type: AN Maximum Length: 51 bytes"),
        L(371, "Representation: Variable length of up to 51 alphanumeric characters"),
        L(371, "Purpose: Identifies the card’s expiration date and/or discretionary data."),
        L(372, "Valid Codes/Values: For a card whose number has been manually entered, the expiration date is in MMYY format, and "
               "the other required data depends upon the application."),
        L(372, "Note: For all authorizations, merchants must send a value even for expired cards."),
    ]),
    "EL44": ("Element 44 Information Byte (Chapter 13)", [
        L(386, "Number: 44 Name: Information Byte"),
        L(386, "Character Type: AN Maximum Length: 1 byte"),
        L(386, "Representation: Fixed length of one alphanumeric character"),
        L(386, "Single-message Request signifies one transaction is performed, and then communication is discontinued."),
        L(386, "Valid Codes/Values: Character Description\n? Download Request\n0 Single-message transmission\n"
               "1 Multimessage transmission"),
    ]),
    "EL55": ("Element 55 Message Format Version Identifier (Chapter 13)", [
        L(391, "Number: 55 Name: Message Format Version Identifier"),
        L(391, "Character Type: AN Maximum Length: 6 bytes"),
        L(391, "Representation: Fixed length of six alphanumeric characters"),
        L(391, "Purpose: Identifies the type and release version of message format."),
        L(391, "For Financial Transaction Requests, the following data element and data segment always follow this data element:\n"
               "• Element No. 63, Number of Segments\n• Data Segment No. 100, Standard Message Data Segment"),
        L(392, "Valid Codes/Values: Value Description\nATL105 Device/Controller (default)"),
    ]),
    "EL58": ("Element 58 Nonfuel Amount (Chapter 13)", [
        L(393, "Number: 58 Name: Nonfuel Amount"),
        L(393, "Character Type: N Maximum Length: 8 bytes"),
        L(393, "Representation: Variable length of up to eight digits with two assumed decimal places"),
        L(393, "Note: A maximum length of 8 bytes is allowed for American Express cards only. A maximum length of 7 bytes is "
               "allowed for all other card types."),
        L(393, "Purpose: Identifies the transaction’s dollar amount that is not a fuel, tax, or cash amount."),
        L(393, "Valid Codes/Values: 1–99999999"),
    ]),
    "EL63": ("Element 63 Number of Segments (Chapter 13)", [
        L(395, "Number: 63 Name: Number of Segments"),
        L(395, "Character Type: N Maximum Length: 2 bytes"),
        L(395, "Representation: Variable length of up to two digits"),
        L(395, "Financial Transaction Requests: This data element is always followed by Data Section No. 2 which contains Data "
               "Segment No. 100, Standard Message Data Segment, plus Data Section No. 3 which contains none, one, or more of the "
               "of the input data segments listed in Chapter 12."),
        L(395, "Valid Codes/Values: The value of this field, call it N, indicates that the transaction will have the 100 segment "
               "+ N-1 of the input segments listed in Chapter 12."),
    ]),
    "EL78": ("Element 78 Prompt Code (Chapter 13)", [
        L(400, "Number: 78 Name: Prompt Code"),
        L(400, "Character Type: AN Maximum Length: 4 bytes"),
        L(400, "Representation: Variable length of three or four alphanumeric characters"),
        L(400, "Purpose: In the case of a financial transaction, this is a 4-character Prompt Code that identifies the "
               "transaction’s Transaction Type (one character) and Card Type (three characters)."),
        L(401, "Processing Rules: Must be a valid Transaction Type code and/or Card Type code."),
        L(401, "Valid Codes/Values: In the case of a financial transaction, position no. 1 is the Transaction Type (For a list of "
               "valid entries, please see Appendix G. Valid Transaction Type Codes.); and position nos. 2–4 are the Card Type "
               "(For a list of valid entries, please see Appendix E. Valid Card Type Codes.)."),
    ]),
    "EL83": ("Element 83 Response Code (Chapter 13)", [
        L(403, "Number: 83 Name: Response Code"),
        L(403, "Character Type: AN Maximum Length: 1 byte"),
        L(403, "Purpose: For financial transactions, it identifies an approval with purchase/capture, an approval without "
               "purchase/capture, or a decline."),
        L(404, "0 Approved—Purchase/Capture"),
        L(404, "F Approved—Partial approval"),
    ]),
    "EL84": ("Element 84 Segment Length (Chapter 13)", [
        L(406, "Number: 84 Name: Segment Length"),
        L(406, "Character Type: N Maximum Length: 4 bytes"),
        L(406, "Representation: Variable length of three or four digits"),
        L(406, "Purpose: Identifies the length of the segment in which it appears."),
        L(406, "Processing Rules: This figure is calculated using the lengths of all data elements in the segment plus all Field "
               "Separators/Product Data Field Delimiters related to those elements."),
        L(406, "Note: Segment Length can only have a length of 4 digits when transmitting the following Data Segments. It cannot "
               "have a length of 4 digits when sending any other segment."),
        L(407, "001–218 Standard Message Data Segment (No. 100)"),
        L(407, "001–999 Variable Information Data Segment (No. 111)"),
    ]),
    "EL85": ("Element 85 Segment Type (Chapter 13)", [
        L(408, "Number: 85 Name: Segment Type"),
        L(408, "Character Type: N Maximum Length: 3 bytes"),
        L(408, "Representation: Fixed length of three digits"),
        L(408, "100 Data Segment No. 100, Standard Message Data Segment"),
        L(408, "111 Data Segment No. 111, Variable Information Data Segment"),
    ]),
    "EL86": ("Element 86 Sequence Number (Chapter 13)", [
        L(409, "Number: 86 Name: Sequence Number"),
        L(409, "Character Type: N Maximum Length: 6 bytes"),
        L(409, "Representation: Fixed length of six digits"),
        L(409, "Purpose: Note: Identifies a transaction for the life of the transaction. Sequence Number must persist in all the "
               "subsequent transactions that are associated with the initial authorization request."),
        L(409, "Processing Rules: Each transaction has a unique Sequence Number. It is included on all transaction types, and "
               "originates at the device."),
        L(409, "In the case of a purchase reversal/void or time-out reversal transaction, the Sequence Number must be the same as "
               "in the original transaction request."),
        L(409, "Valid Codes/Values: 000001–999999"),
    ]),
    "EL102": ("Element 102 Terminal Identifier (Chapter 13)", [
        L(417, "Number: 102 Name: Terminal Identifier"),
        L(417, "Character Type: AN Maximum Length: 22 bytes"),
        L(417, "Representation: Variable length up to 22 alphanumeric characters"),
        L(417, "Purpose: Identifies the transaction device by Device Type, State Code (ANSI® Code), BUYPASS Merchant Number, and "
               "Device Number."),
        L(417, "Processing Rules: Required for all transactions and messages."),
        L(417, "The Terminal Identifier must be assigned by and set up at BUYPASS."),
        L(417, "Valid Codes/Values: Device Type (Pos. Nos. 1–2) Any valid 2-character Device Type assigned by BUYPASS personnel."),
        L(417, "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code). Please see Appendix D. Valid State "
               "Codes, for a list of valid ANSI® Code entries."),
        L(417, "BUYPASS Merchant Number (Starts immediately in Pos. No. 5.) Any valid 6- to 15-character BUYPASS Merchant Number "
               "assigned by BUYPASS personnel. The Device Number immediately follows the BUYPASS Merchant Number."),
        L(417, "Device Number (Starts immediately after the BUYPASS Merchant Number.)"),
        L(418, "Any valid 3-character Device Number assigned by BUYPASS personnel."),
    ]),
    "EL111": ("Element 111 Variable Information Indicator (Chapter 13)", [
        L(422, "Number: 111 Name: Variable Information Indicator"),
        L(422, "Character Type: AN Maximum Length: 3 bytes"),
        L(422, "Representation: Fixed length of 3 alphanumeric characters"),
        L(422, "Purpose: Identifies the type of variable information in a Variable Information Data Segment."),
    ]),
    "EL112": ("Element 112 Variable Information Length (Chapter 13)", [
        L(425, "Number: 112 Name: Variable Information Length"),
        L(425, "Character Type: N Maximum Length: 3 bytes"),
        L(425, "Representation: Fixed length of 3 bytes"),
        L(425, "Purpose: Identifies the length of Variable Information (Element No. 113) in the Variable Information Data Segment."),
        L(425, "Valid Codes/Values: 001–999"),
    ]),
    "EL113": ("Element 113 Variable Information (Chapter 13)", [
        L(426, "Number: 113 Name: Variable Information"),
        L(426, "Character Type: AN Maximum Length: 982 bytes"),
        L(426, "When Element No. 111 (Variable Information Indicator) has a value of 005 (point-of-service entry mode indicator), "
               "the actual point-of-service entry mode (swiped/manual/RFID) is included here."),
    ]),
    "EL121": ("Element 121 Partial Approval Indicator (Chapter 13)", [
        L(440, "Number: 121 Name: Partial Approval Indicator"),
        L(440, "Character Type: N Maximum Length: 1 byte"),
        L(440, "Representation: Fixed length of 1 digit"),
        L(440, "Processing Rules: Required on all transactions."),
        L(440, "0 Not specified (default)/Not supported"),
        L(440, "1 Supported This value indicates that the Merchant supports credit/debit/fleet Partial Approval transactions."),
        L(440, "If the Merchant transmits this value with a transaction—and the corresponding network supports credit/debit/fleet "
               "Partial Approval transactions—the network can approve an amount that is less than the amount in the "
               "Authorization request; and BUYPASS will send a Partial Approval response."),
        L(440, "5 This value indicates that the Merchant only supports the receipt of the card balance, not Partial Approval "
               "transaction processing."),
        L(440, "Note: This value is only supported for American Express prepaid cards."),
    ]),
    "APP-C": ("Appendix C. Valid Authorizer Codes", [
        L(511, "The following table contains a list of valid entries for Element No. 7, Authorizer Code:"),
        H("Authorizer Code", "Authorizer Name", "Authorizer Type"),
        R(511, "03", "MasterCard®", "Credit"),
    ]),
    "APP-E": ("Appendix E. Valid Card Type Codes", [
        L(521, "The following table contains Card Type codes sent by the device to the host as part of the 4-character Prompt "
               "Code used in financial transaction processing:"),
        L(521, "Note: These codes are preceded by a valid Transaction Type to create a valid Prompt Code. (See Appendix G. Valid "
               "Transaction Type Codes.)"),
        H("Code", "Card Type", "Card Type Description"),
        R(521, "020", "Credit", "Credit"),
    ]),
    "APP-G": ("Appendix G. Valid Transaction Type Codes", [
        L(545, "Prompt Codes are transmitted by a device at the time a transaction is initiated. A valid Prompt Code (Element No. "
               "78) is composed of a Transaction Type code and a Card Type code."),
        H("Code", "Transaction Type", "Transaction Description"),
        R(545, "0", "POS Purchase/Capture", "Point-of-sale purchase/preauthorized completion"),
        R(545, "3", "POS Authorization Only", "Point-of-sale authorization only (no draft capture)"),
        R(545, "5", "Customer-activated Authorization Only", "Customer-activated authorization only (no draft capture)"),
        R(546, "8", "Purchase reversal", "Purchase reversal/void"),
        R(546, "S", "Cancellation", "Cancels a stored value card, credit Authorization Only transaction, and debit Authorization "
          "Only transaction."),
        R(546, "Z", "Time-out Reversal", "Time-out reversal"),
    ]),
    "APP-H-548": ("What is on PDF p.548 - the page the test case cites for Table 005", [
        L(548, "The following table contains a list of valid Decline Codes that can be used to prompt a clerk/customer to "
               "reenter indicated data:"),
        L(548, "Decline Codes Prompting Data Reentry"),
    ]),
    "APP-I-005": ("Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005)", [
        L(555, "Point-of-Service Entry Mode Information"),
        H("Field", "Attributes", "Description"),
        R(555, "Table ID", "n", "Fixed value: 005"),
        R(555, "Table Length", "n", "3"),
        R(555, "Table Data", "n", "Fixed Len.: 3"),
        L(555, "Note: The POS Entry Mode field must be populated for all transactions, which include EMV, Magstripe, and Key "
               "Entered transactions from all POS devices for any given solution."),
        L(555, "Please refer to Appendix J. Valid Point-of-Service Entry Mode Codes for valid entry information."),
    ]),
    "APP-I-031": ("Appendix I. Variable Information Data Layouts - Point-of-Service Terminal Capability Codes (Table 031)", [
        L(569, "Point-of-Service Terminal Capability Codes"),
        H("Field", "Attributes", "Description"),
        R(569, "Table ID", "n3", "Fixed Value: 031"),
        R(569, "Table Length", "n3", "Fixed Len.: 2"),
        R(569, "Table Data", "n2", "Identifies the terminal capability code of the point-of-sale terminal. Fixed length of 2 digits."),
        L(569, "Terminal Capability values must be populated for all transactions, which include EMV, Magstripe, and Key Entered "
               "transactions from all POS devices for any given solution."),
        L(569, "Once certified, the respective terminal capability values do not change regardless of the POS Entry Mode."),
        L(569, "Valid codes/values:\n00 = Unknown or unspecified\n01 = Terminal not used (Example: eCommerce and In App)\n"
               "02 = Magnetic stripe read capability (no contact or contactless chip)\n03 = Bar code capability\n"
               "04 = Optical character recognition (OCR) read capability\n05 = Integrated circuit card (ICC) read capability\n"
               "06 = Contactless chip read capability (no contact chip)\n07 = Contactless magnetic stripe read capability (no "
               "contact or contactless chip capability)\n09 = Terminal does not read card data\n12 = Hybrid Integrated Circuit "
               "Card Reader (Contact Chip & Contactless Chip) Capabilities"),
    ]),
    "APP-J": ("Appendix J. Valid Point-of-Service Entry Mode Codes", [
        L(638, "This appendix lists valid entries for Variable Information (Element No. 113)."),
        L(638, "A valid 3-digit Point-of-Service Entry Mode code has the following components:\n• 2-digit PAN Entry Mode code in "
               "position nos. 1–2\n• 1-digit Terminal Capability Mode code in position no. 3"),
        L(638, "Note: The POS Entry Mode values listed below must persist throughout the lifecycle of the transactions, except as "
               "noted below the Valid PAN Entry Mode Codes table."),
        H("Pos. Nos. 1–2", "Description"),
        R(638, "01", "Manual/keyed"),
        L(638, "Due to credit authorizer (American Express, Diners Club®, Discover® Network, JCB®, MasterCard®, MasterCard® Fleet, "
               "Visa®, and Visa® Fleet) and debit authorizer regulatory requirements regarding storage of full track data, "
               "previously authorized credit and debit transactions (Voids and Completions) can only pass the account number and "
               "expiration date to BUYPASS. These transactions will use the same PAN Entry Mode code as their corresponding "
               "original Purchase or Authorization transaction, with one exception: They will use a PAN Entry Mode code 02 when "
               "referencing an original Purchase or Authorization transaction in which a PAN Entry Mode code 90 was used."),
        H("Position 3", "Description"),
        R(639, "0", "Unspecified"),
        R(639, "1", "PIN entry capability (including both EMV Contact and Contactless)"),
        R(639, "2", "No PIN entry capability (including both EMV Contact and Contactless)"),
        R(639, "8", "Contactless Magnetic Stripe Read Capability"),
        L(639, "If more than one valid Terminal Capability Mode code exists for a POS device, the following hierarchy logic should "
               "be followed:\nIf a Contactless MSR is attached to the POS device and it has no chip capability, then all "
               "transactions will use Terminal PIN Entry Capability Mode code 8; but\nFor EMV Contact and Contactless the valid "
               "PIN Entry Capability Mode code is 1 and not 8. The PIN Entry Capability Mode code 8 is reserved only for "
               "Contactless MSR; or\nIf no Contactless MSR is attached, and the POS device has the PIN debit feature enabled, then "
               "all transactions will use Terminal PIN Entry Capability Mode code 1; or\nIf no Contactless MSR is attached, and "
               "the POS device has the PIN debit feature disabled, then all transactions will use Terminal PIN Entry Capability "
               "Mode code 2."),
    ]),
}
X_ORDER = ["S2.3.1", "S10.1.1", "S10.1.2", "S10.1.2.1", "S10.1.2.2", "S10.1.5", "S10.1.6", "S10.1.7", "S10.1.8.2", "S11.1",
           "S12.1", "S12.10", "EL2", "EL5", "EL6", "EL12", "EL44", "EL55", "EL58", "EL63", "EL78", "EL83", "EL84", "EL85", "EL86",
           "EL102", "EL111", "EL112", "EL113", "EL121", "APP-C", "APP-D", "APP-E", "APP-G", "APP-H-548", "APP-I-005", "APP-I-031",
           "APP-J"]
# validator reference id prefix -> extract id (Element refs map to EL<n>)
X_OF_REF = (("S2.3.1", "S2.3.1"), ("S10.1.1.3", "S10.1.1"), ("S10.1.2.1", "S10.1.2.1"), ("S10.1.2.2", "S10.1.2.2"),
            ("S10.1.5", "S10.1.5"), ("S10.1.6", "S10.1.6"), ("S10.1.8.2", "S10.1.8.2"), ("S11.1", "S11.1"), ("S12.10", "S12.10"),
            ("S12.1-", "S12.1"), ("APPC", "APP-C"), ("APPD", "APP-D"), ("APPE", "APP-E"), ("APPG", "APP-G"),
            ("APPI-005", "APP-I-005"), ("APPI-031", "APP-I-031"), ("APPJ", "APP-J"))
PAGE_X = {40: "S2.3.1", 81: "S10.1.1", 83: "S10.1.2", 84: "S10.1.2.1", 85: "S10.1.2.2", 86: "S10.1.5", 87: "S10.1.6",
          91: "S10.1.7", 92: "S10.1.7", 93: "S10.1.7", 94: "S10.1.8.2", 511: "APP-C", 545: "APP-G", 548: "APP-H-548",
          555: "APP-I-005", 569: "APP-I-031", 638: "APP-J", 639: "APP-J"}
FIELD_PAGE_X = {223: "S12.1", 224: "S12.1", 225: "S12.1", 246: "S12.10", 391: "EL55", 395: "EL63", 555: "APP-I-005",
                569: "APP-I-031"}
METHOD_TEXT = {
    "fixed_value": "Fixed value set by ATL105 for this field",
    "fixed_value_chapter11_table": "Fixed value from the Chapter 11 message layout",
    "derived_at_wire_encoding": "Not stored in the test case; calculated from the other fields when the file was built",
    "derived_segment_count": "Not stored in the test case; counted from the segments when the file was built",
    "derived_from_table_data": "Calculated from the length of the table data",
    "test_data_config_value": "Value configured once for all test data (client setting)",
    "realistic_terminal_id": "Generated Terminal Identifier for the merchant's terminal (placeholder merchant)",
    "scenario_parameter_literal": "Fixed by the test scenario",
    "realistic_pan": "Generated MasterCard test card number",
    "realistic_expiry_date": "Generated card expiry date",
    "qe_example_value": "Sequence Number allocated by the generator (unique per terminal across the run)",
    "axis_table_placement": "Built from the scenario: entry mode in positions 1-2, PIN entry capability in position 3",
    "realistic_terminal_capability": "Certified capability of the merchant's terminal (generator merchant settings)",
    "flow_fixed_transaction_code": "Fixed by the flow template for this step",
    "flow_response_runtime_value": "Comes from the step 1 response at run time; the test case only holds a provisional value",
    "flow_new_value_required": "Must be a new value, different from step 1",
    "flow_carried_value": "Copied from step 1",
}
EMPTY_FIELD = {8: "encrypted PIN block (DUKPT); this keyed credit transaction has no PIN",
               9: "pump or lane number; not used by this test case",
               10: "fuel purchase amount; this sale has no fuel",
               12: "tax amount; not sent by this test case",
               13: "cash amount (cash back); not used by this test case",
               16: "local date and time of a preauthorized transaction; this is not a preauthorized transaction"}
RC_TEXT = {"0": "Approved—Purchase/Capture", "F": "Approved—Partial approval"}
TABLE_NAME = {"005": "Point-of-Service Entry Mode Information", "031": "Point-of-Service Terminal Capability Codes"}
VI_KEYS = ("VariableInformationIndicator", "VariableInformationLength", "VariableInformation")
KIND = {"FAIL": "Wrong", "BLOCKED": "Missing - filled in at run time", "REVIEW": "To confirm", "INFO": "Note"}
MISSING_INPUT = {"TERMINAL_PLACEHOLDER": "Missing - client input (real Terminal Identifier)",
                 "PARTIAL_APPROVAL_PRECONDITION": "Missing - client input (partial-approval test card)"}


def anchor(xid_):
    return "x-" + re.sub(r"[^a-z0-9]+", "-", xid_.lower()).strip("-")


def xid(rid):
    m = re.match(r"EL(\d+)", rid)
    if m:
        return f"EL{m.group(1)}"
    return next(x for prefix, x in X_OF_REF if rid.startswith(prefix))


def cell(v, empty="(empty)"):
    if v is None:
        return "(none)"
    return f"`{v}`" if str(v) else empty


def md(text):
    """Table-safe text; quoted run-time placeholders are shown as code so they are not read as HTML."""
    return esc(re.sub(r"'(<[^'<>]*>)'", r"`\1`", str(text)))


def quoted(text):
    """Quote text at the end of a sentence without doubling its full stop."""
    text = str(text)
    return f'"{text}"' + ("" if text.endswith(".") else ".")


def app_d(states, codes):
    intro = {"Canada": (517, "The following table contains a list of valid State codes for the provinces of Canada:",
                        ("Province of Canada", "Alphabetical Code", "ANSI® Code")),
             "United States": (514, "The following table contains a list of valid State Codes for the states of the United "
                                    "States of America:", ("State", "Alphabetical Code", "ANSI® Code"))}
    items = [L(514, "The ANSI® State Codes are used in the following data element:\n• Terminal Identifier (No. 102)")]
    for code in codes:
        if code in states:
            name, abbr, page, region = states[code]
            ipage, text, head = intro.get(region, (None, None, ("Name", "Alphabetical Code", "ANSI® Code")))
            items += ([L(ipage, text)] if text else []) + [H(*head), R(page, name, abbr, code)]
    return ("Appendix D. Valid State Codes", items)


def verify(spec, extracts):
    bad = []
    for xid_, (_, items) in extracts.items():
        for kind, page, payload, check in items:
            if kind != "H":
                text = check or (payload if kind == "L" else " ".join(c for c in payload if c))
                if not spec.has(page, text):
                    bad.append(f"[{xid_}] p.{page}: {text[:90]}")
    return bad


def render_extract(spec, xid_, title, items):
    pages = sorted({p for _, p, _, _ in items if p})
    span = f"PDF p.{pages[0]}" + (f"-{pages[-1]}" if len(pages) > 1 else "")
    printed = " to ".join(uniq([spec.printed(pages[0]), spec.printed(pages[-1])]))
    out = [f'<a id="{anchor(xid_)}"></a>', "", f"### [{xid_}] {title}", "", f"*{span} (printed {printed})*", ""]
    cur, block = pages[0], None
    for kind, page, payload, _ in items:
        if kind == "H":
            out += ([""] if block else []) + ["| " + " | ".join(payload) + " | PDF p. |", "|" + "---|" * (len(payload) + 1)]
            block = "table"
        elif kind == "R":
            out.append("| " + " | ".join(esc(c) for c in payload) + f" | {page} |")
            cur = page
        else:
            if page != cur:
                out += ([""] if block else []) + [f"*PDF p.{page} (printed {spec.printed(page)}):*", ""]
                cur, block = page, None
            elif block == "table":
                out.append("")
            out += ([">"] if block == "quote" else []) + ["> " + payload.replace("*", "\\*").replace("\n", "<br>")]
            block = "quote"
    return out + ([""] if block else [])


def locate(tc_dir, ids):
    recs, loc = {}, {i: {} for i in ids}
    with open(tc_dir / "test_cases.jsonl", encoding="utf-8") as fh:
        for n, line in enumerate(fh, 1):
            m = re.match(r'\{"id": "(TC-\d+)"', line)
            if m and m.group(1) in loc:
                loc[m.group(1)]["jsonl"] = n
                recs[m.group(1)] = json.loads(line)
    for fname, key in (("TEST_CASES.md", "md"), ("test_cases.csv", "csv")):
        for n, line in enumerate((tc_dir / fname).read_text(encoding="utf-8").splitlines(), 1):
            for i in ids:
                if key not in loc[i] and re.search(rf"{i}\b", line):
                    loc[i][key] = n
    return recs, loc


class Guide:
    def __init__(self, env, tc, rep, ctx, rec, loc):
        self.env, self.tc, self.rep, self.ctx, self.rec, self.loc = env, tc, rep, ctx, rec, loc
        self.spec, self.refs, self.states = env["spec"], env["refs"], env["states"]
        self.multi = bool(rec.get("steps"))
        self.rsteps = rec["steps"] if self.multi else [dict(step=1, request=rec["request"], data_section_1=rec["data_section_1"],
                                                            runtime_substitutions=rec.get("runtime_substitutions") or [])]
        self.msgs = {s["step"]: json.loads((env["td_dir"] / s["td"]).read_text(encoding="utf-8"))["Financial Transaction Request"]
                     for s in tc["steps"]}
        self.jl = f"[test_cases.jsonl line {loc['jsonl']}](../test_cases/test_cases.jsonl#L{loc['jsonl']})"
        self.used, self.out, self.calc = [], [], {}

    # ---- helpers
    def x(self, *ids):
        for i in ids:
            if i not in self.used:
                self.used.append(i)
        return ", ".join(f"[{i}](#{anchor(i)})" for i in ids)

    def xr(self, rids):
        return self.x(*uniq(xid(r) for r in rids))

    def pg(self, page):
        return f"PDF p.{page} (printed {self.spec.printed(page)})"

    def prefix(self, n):
        return f"steps[{n - 1}]." if self.multi else ""

    def step(self, n):
        return next(s for s in self.tc["steps"] if s["step"] == n)

    def td(self, n):
        return self.step(n)["td"]

    def leg(self, n):
        legs = self.rec.get("tx_legs") or []
        return legs[n - 1] if len(legs) >= n else {}

    def ai(self, step, seg, fno):
        return next((r["result"] for r in self.rep.rows if r["step"] == step and r["seg"] == seg and str(r["fno"]) == str(fno)), "-")

    def worst(self, n, name):
        sev = [f["severity"] for f in self.rep.findings if f["step"] == n and f["label"].startswith(name)]
        return min(sev, key=tdv.SEVERITY_ORDER.get) if sev else "PASS"

    def info(self, st):
        n, of = st["step"], st.get("of", st["step"])
        v = tdv.td_seg100_values(self.msgs[n]["Standard Message Data Segment"])
        ph = [name for _, _, key, name, _ in tdv.SEG100 if tdv.PLACEHOLDER.match(v[key])]
        prov = {key: (str(st["tc_request"].get(name) or "") if name in ph else v[key]) for _, _, key, name, _ in tdv.SEG100}
        first = self.ctx["flats"].get(of, {}) if of != n else {}
        return n, of, v, ph, prov, first, first.get("NonfuelAmount")

    def expected_rc(self, n):
        if self.multi:
            rc = (self.rec["steps"][n - 1].get("expected_response") or {}).get("response_code") or {}
            exp = self.leg(n).get("expect") or {}
            return (rc.get("value") or exp.get("response_code"), rc.get("tier") or exp.get("tier"),
                    rc.get("source_page") or exp.get("page"))
        rc = (self.rec.get("expected_response") or {}).get("response_code") or {}
        return rc.get("value"), rc.get("tier"), rc.get("source_page")

    def runtime_rule(self, n, name):
        """The test case's own rule for a run-time value, quoted."""
        for r in self.rsteps[n - 1].get("runtime_substitutions") or []:
            if r.get("element") == name:
                return quoted(r.get("rule"))
        for d in self.leg(n).get("link_defs") or []:
            if d.get("element_name") == name and d.get("type") == "carry_response_field":
                return quoted(d.get("rule"))
        return "the value from the step 1 response."

    def build(self):
        for part in (self.header, self.what, self.path, self.generation, self.fields, self.calculations, self.links,
                     self.responses, self.citations, self.issues, self.across, self.atl105, self.signoff):
            part()
        return "\n".join(self.out) + "\n"

    # ---- sections
    def header(self):
        tc, rec, rep, loc, spec = self.tc, self.rec, self.rep, self.loc, self.spec
        files = ", ".join(f"[{s['td']}](../test_data/{s['td']})" for s in tc["steps"])
        self.out += [
            f"# {tc['id']} ({tc['display_id']}) - Manual Validation Guide", "", f"**{rec['name']}**", "",
            "This guide is a step-by-step path for checking this test case and its test data by hand against ATL105, so you can "
            "confirm or reject what the AI validator reported. The ATL105 text needed for every check is copied into "
            "[section 11](#s11); each copied line was checked against the ATL105 text on the stated page, so you do not need to "
            "open the specification.", "",
            "| Item | Value |", "|---|---|",
            f"| AI validator result | **{rep.verdict()}** - can it run as-is? {rep.can_run()} "
            f"([AI report](../Result_MD_Folder/{tc['id']}.md)) |",
            f"| Test case record | {self.jl} (the whole test case is on this one line) |",
            f"| Test case summary | [TEST_CASES.md line {loc['md']}](../test_cases/TEST_CASES.md#L{loc['md']}), "
            f"[test_cases.csv line {loc['csv']}](../test_cases/test_cases.csv#L{loc['csv']}) |",
            f"| Test data | {files} |",
            f"| Template / type | `{rec['template_id']}` / {rec.get('tc_type')} - {rec.get('tc_type_name')} |",
            f"| Status in the test case | `{rec.get('status')}` |",
            f"| Specification | {tdv.SPEC_TITLE}, release {spec.version} ({spec.date}) |", "",
            "## How to read this guide", "",
            "- **PDF p.** is the page number shown by the PDF viewer; **printed** is the page number in the ATL105 page footer.",
            "- Links such as **[EL58]** jump to the ATL105 text copied in [section 11](#s11).",
            "- A **Field Separator** is the separator character that follows a field in the real (wire) message; it counts as one "
            "character.",
            "- **AI result** is what the AI validator decided. **Your check** is for you: write ☑ when you agree, or note what you "
            "found.",
        ] + tdv.legend(spec)[2:]

    def what(self):
        rec, tc = self.rec, self.tc
        lab = rec.get("dimension_labels") or {}
        rows = [("Objective", rec.get("test_objective"), "test_objective"),
                ("Validation focus", rec.get("validation_focus"), "validation_focus"),
                ("Scenario", f"{rec.get('scenario_id')} ({rec.get('scenario_display_id')})", "scenario_id"),
                ("Card", f"{lab.get('network')} {lab.get('program')}", "dimension_labels"),
                ("Transaction / entry mode", f"{lab.get('txn_category')} / {lab.get('entry_mode')}", "dimension_labels"),
                ("Merchant", f"{rec['merchant']['name']} (id {rec['merchant']['id']}), merchant_provenance = "
                             f"{rec.get('merchant_provenance')}", "merchant, merchant_provenance"),
                ("Transaction Type per step", rec.get("message_chain"), "message_chain")]
        for leg in rec.get("tx_legs") or []:
            rows += [(f"How to run step {leg['step']}", e, f"tx_legs[{leg['step'] - 1}].execution") for e in leg.get("execution") or []]
        rows += [("Open item (client input needed)", o, "open_items") for o in rec.get("open_items") or []]
        self.out += ['<a id="s1"></a>', "", "## 1. What this test case does", "",
                     "| Item | Value | Field in the test case record |", "|---|---|---|"]
        self.out += [f"| {a} | {md(b)} | `{c}` |" for a, b, c in rows]
        self.out += ["", "| Step | Role | Prompt Code | Expected Response Code | Test data file |", "|---|---|---|---|---|"]
        for st in tc["steps"]:
            n = st["step"]
            pc = tdv.td_seg100_values(self.msgs[n]["Standard Message Data Segment"])["PromptCode"]
            code, tier, _ = self.expected_rc(n)
            self.out.append(f"| {n} | {st['role'].replace('_', ' ')} | `{pc}` = {tcv.TXN_TYPES.get(pc[:1], '?')}, card type "
                            f"{pc[1:]} | `{code}` = {RC_TEXT.get(code, 'see Element 83')} ({tier}) | "
                            f"[{st['td']}](../test_data/{st['td']}) |")
        cps = rec.get("device_checkpoints") or []
        if cps:
            self.out += ["", "Checks on the device (`device_checkpoints`) - not visible in the message sent to BUYPASS:", ""]
            for c in cps:
                xs = uniq(PAGE_X[int(p)] for p in re.findall(r"p\.(\d+)", c) if int(p) in PAGE_X)
                self.out.append(f"- {md(c)}" + (f" - ATL105: {self.x(*xs)}" if xs else ""))
        self.out.append("")

    def path(self):
        tc, loc = self.tc, self.loc
        files = ", ".join(f"[{s['td']}](../test_data/{s['td']})" for s in tc["steps"])
        multi = len(tc["steps"]) > 1
        items = [
            f"**Open the test case.** Open {self.jl}. The record is one long line: in VS Code press Ctrl+G, enter {loc['jsonl']}, "
            "copy the line into a new file, set the language to JSON and press Shift+Alt+F to format it. Confirm the id "
            f"`{tc['id']}`, display id `{tc['display_id']}`, template, status and steps shown in [section 1](#s1). The same test "
            f"case is summarised in [TEST_CASES.md line {loc['md']}](../test_cases/TEST_CASES.md#L{loc['md']}).",
            f"**Open the test data file{'s' if multi else ''}** next to this guide: {files}.",
            f"**Check the message structure** ({self.x('S11.1', 'S12.1', 'S12.10')}): the root is `Financial Transaction Request` "
            "with `MessageFormatVersionIdentifier` and `NumberofSegments` (Data Section 1), `Standard Message Data Segment` "
            "(Segment 100, 17 fields in ATL105 order) and `Variable Information Data Segment` (Segment 111 with Tables 005 and 031).",
            "**See how each value was produced** ([section 3](#s3)): compare every value in the test data file with the value and "
            "generation method recorded in the test case.",
            "**Check every field** ([section 4](#s4)): read the value in the file, apply the ATL105 rule shown, compare with the "
            "expected value and mark the Your check column.",
            "**Recalculate** the segment lengths, the card number check digit and the expiry dates ([section 5](#s5)).",
            ("**Check the links between the steps** ([section 6](#s6)): Sequence Number, card, terminal and amounts against step 1."
             if multi else "**Links between steps** ([section 6](#s6)): not applicable - this test case has one step."),
            "**Check the expected Response Codes** ([section 7](#s7)).",
            "**Check the ATL105 citations written in the test case** ([section 8](#s8)).",
            "**Confirm each issue the AI reported** ([section 9](#s9)) with its 'How to confirm by hand' steps.",
            "**Review the checks across the five test cases** ([section 10](#s10)).",
            "**Record your conclusion** ([section 12](#s12)).",
        ]
        self.out += ['<a id="s2"></a>', "", "## 2. Manual validation path (do these steps in order)", ""]
        self.out += [f"{i}. {t}" for i, t in enumerate(items, 1)] + [""]

    def gen_rows(self, idx, st):
        n = st["step"]
        rs, msg = self.rsteps[idx], self.msgs[n]
        smds = rs["request"]["Standard Message Data Segment"]
        vids = rs["request"]["Variable Information Data Segment"]
        ds1 = rs["data_section_1"]["elements"]
        v = tdv.td_seg100_values(msg["Standard Message Data Segment"])
        rows = [("Message Format Version Identifier (Element 55)", msg.get("MessageFormatVersionIdentifier", ""), ds1.get("55")),
                ("Number of Segments (Element 63)", msg.get("NumberofSegments", ""), ds1.get("63"))]
        rows += [(f"100 / {fno}. {name} (Element {el})", v[key], smds.get(name)) for fno, el, key, name, _ in tdv.SEG100]
        s111 = msg.get("Variable Information Data Segment") or {}
        rows += [("111 / 1. Segment Type (Element 85)", s111.get("SegmentType", ""), vids.get("Segment Type")),
                 ("111 / 2. Segment Length (Element 84)", s111.get("SegmentLength", ""), vids.get("Segment Length"))]
        for i, sec in enumerate(s111.get("VariableInformationSection") or [], 1):
            sfx, table = ("" if i == 1 else f" [{i}]"), sec.get("VariableInformationIndicator", "")
            for no, name, key, el in ((3, "Variable Information Indicator", VI_KEYS[0], 111),
                                      (4, "Variable Information Length", VI_KEYS[1], 112),
                                      (5, "Variable Information", VI_KEYS[2], 113)):
                rows.append((f"111 / {no}. {name} - Table {table} (Element {el})", sec.get(key, ""), vids.get(name + sfx)))
        return rows

    @staticmethod
    def same(td_val, tv, method):
        if tdv.PLACEHOLDER.match(td_val or ""):
            return "No - the file holds run-time instruction text; the test case value is provisional"
        if tv is None:
            if (method or "").startswith("derived"):
                return "Calculated when the file was built"
            return "Yes (empty)" if td_val in ("", None) else "No"
        return "Yes" if str(tv) == str(td_val) else "No"

    def page_note(self, page, field):
        if not page:
            return "-"
        t005 = self.refs.catalog["APPI-005"][1]
        note = f" - outdated: Table 005 is on p.{t005} (see section 9)" if "Table 005" in field and page != t005 else ""
        return f"p.{page} (printed {self.spec.printed(page)}){note}"

    def generation(self):
        self.out += ['<a id="s3"></a>', "", "## 3. How the test data was generated", "",
                     "The test data file is the wire-ready copy of the request stored in the test case. For each field the test "
                     "case records the value, the method the generator used (`method`), the ATL105 page it relied on "
                     "(`source_page`) and its notes (`flags`). Fields without a value in the test case are sent empty - their "
                     "Field Separator is still sent. Segment Length and Number of Segments are not stored in the test case; they "
                     "are calculated when the file is built. Values that only exist after step 1 has run are written as "
                     "instruction text in angle brackets.", ""]
        notes = []
        for idx, st in enumerate(self.tc["steps"]):
            n, pfx = st["step"], self.prefix(st["step"])
            self.out += [f"### 3.{n} Step {n} - [{st['td']}](../test_data/{st['td']})", "",
                         f"Where to look in {self.jl}: `{pfx}request[\"<segment name>\"][\"<field name>\"]` and "
                         f"`{pfx}data_section_1.elements[\"55\"]` / `[\"63\"]`.", "",
                         "| Field | Value in the test data file | Value in the test case | Same? | How it was produced (`method`) | "
                         "ATL105 page used by the generator | Generator notes (`flags`) |",
                         "|---|---|---|---|---|---|---|"]
            for field, td_val, f in self.gen_rows(idx, st):
                f = f or {}
                tv, method, page, flags = f.get("value"), f.get("method"), f.get("source_page"), f.get("flags") or []
                notes += [fl for fl in flags if fl not in notes]
                how = METHOD_TEXT.get(method) or ("Not used in this transaction - sent empty" if tv is None else "-")
                codes = ", ".join(uniq(fl.split(":")[0] for fl in flags)) or "-"
                self.out.append(f"| {field} | {cell(td_val)} | {cell(tv)} | {self.same(td_val, tv, method)} | {how}"
                                + (f" (`{method}`)" if method else "") + f" | {self.page_note(page, field)} | {codes} |")
            self.out.append("")
        if notes:
            self.out += ["Generator notes used above (full text from `flags`):", ""] + [f"- {md(fl)}" for fl in notes] + [""]

    def field_rows(self, st):
        n, of, v, ph, prov, first, orig = self.info(st)
        role, tcr, msg = st["role"], st["tc_request"], self.msgs[n]
        reversal, split = role in tcv.SAME_SEQ_ROLES, role in tcv.NEW_SEQ_ROLES
        partial = any(s.get("expected_rc") == "F" for s in self.tc["steps"])
        rows = []

        def add(no, field, key, value, rule, xs, how, expected, ai):
            rows.append(dict(no=no, field=field, key=key, value=value, rule=rule, xs=xs, how=how, expected=expected, ai=ai))

        segs = [k for k, x in msg.items() if isinstance(x, dict)]
        add("DS1-1", "Message Format Version Identifier (55)", "MessageFormatVersionIdentifier",
            msg.get("MessageFormatVersionIdentifier", ""), "Fixed length of six alphanumeric characters; valid value ATL105",
            ["S11.1", "EL55"], "Read the value: it must be exactly ATL105.", "ATL105", self.ai(n, "Data Section 1", 1))
        add("DS1-2", "Number of Segments (63)", "NumberofSegments", msg.get("NumberofSegments", ""),
            "Up to two digits; N means Segment 100 plus N-1 other segments", ["S11.1", "EL63"],
            f"Count the segment objects in the file: {' and '.join(segs)} = {len(segs)}.", f"{len(segs):02d}",
            self.ai(n, "Data Section 1", 2))
        for fno, el, key, name, _ in tdv.SEG100:
            val, ai, no, label = v[key], self.ai(n, "100", fno), f"100-{fno}", f"{name} ({el})"
            if fno == 1:
                add(no, label, key, val, "Fixed value: 100", ["S12.1", "EL85"], "Read the value.", "100", ai)
            elif fno == 2:
                calc = tdv.seg100_length(prov)
                if ph and orig:
                    _, lo, hi = tdv.length_range(prov, ph, orig)
                    exp = f"{lo:03d} to {hi:03d} once the real run-time values are filled in (the test case values give {calc:03d})"
                else:
                    exp = f"{calc:03d}"
                add(no, label, key, val, "Length of all fields of the segment, including the Segment Type, plus all Field "
                    "Separators", ["S12.1", "EL84"], f"Add up the 17 field lengths and 17 Field Separators - see section 5.1, "
                    f"step {n}.", exp, ai)
            elif fno == 3:
                add(no, label, key, val, "One character: ? Download Request, 0 Single-message transmission, 1 Multimessage "
                    "transmission", ["EL44"], f"Find `{val}` in the Element 44 list: {tdv.INFO_BYTE.get(val, 'not in the list')}.",
                    "0 or 1 (this test data uses 0 = single-message)", ai)
            elif fno == 4:
                code, rest = val[2:4], val[4:]
                where = self.states.get(code)
                where = f"{where[0]} ({where[1]}), {where[3]}" if where else "not in Appendix D"
                how = (f"Count the characters: {len(val)} (at most 22). Split it: Device Type `{val[:2]}` (positions 1-2); State "
                       f"Code `{code}` (positions 3-4) = {where} in Appendix D; then `{rest}` ({len(rest)} characters) = BUYPASS "
                       "Merchant Number (6 to 15 characters) followed by the 3-character Device Number, so 9 to 18 characters. "
                       "Only BUYPASS can confirm the Device Type, Merchant Number and Device Number.")
                if n > 1:
                    how += f" It must be the same as step {of} (same device)."
                add(no, label, key, val, "Up to 22 characters: Device Type + ANSI State Code + BUYPASS Merchant Number + Device "
                    "Number; assigned by BUYPASS", ["EL102", "APP-D"], how,
                    "Valid layout; the real value must come from BUYPASS (this one is a placeholder)", ai)
            elif fno == 5:
                tt, ct = val[:1], val[1:]
                add(no, label, "PromptCode.TransactionType + PromptCode.CardType", val, "4 characters: position 1 Transaction Type "
                    "(Appendix G), positions 2-4 Card Type (Appendix E)", ["EL78", "APP-G", "APP-E"],
                    f"The JSON splits the Prompt Code into TransactionType `{tt}` and CardType `{ct}`; on the wire they are joined "
                    f"as `{val}`. Find `{tt}` in Appendix G ({tcv.TXN_TYPES.get(tt, 'not in the list')}) and `{ct}` in Appendix E "
                    f"({'Credit' if ct == '020' else 'see Appendix E'}).",
                    f"{st['code']}020 ({tcv.TXN_TYPES.get(st['code'], '?')}, Credit)", ai)
            elif fno == 6:
                how = (f"Count the digits: {len(val)} (at most 24). Card-industry checks (ATL105 refers to the BUYPASS IIN "
                       "Reference Manual): a MasterCard number starts with 51-55 or 2221-2720 (this one starts with "
                       f"{val[:2]}) and its last digit is a check digit - see section 5.3.")
                exp = "MasterCard number with a valid check digit"
                if reversal and first:
                    how += f" A reversal uses the same card as step {of}: `{first['AccountNumber']}`."
                    exp += f"; same as step {of}"
                elif split and first:
                    how += f" The remaining balance is paid with a different card (card B) than step {of} (`{first['AccountNumber']}`)."
                    exp += f"; different from step {of}"
                add(no, label, key, val, "Up to 24 characters (non-TransArmor request)", ["EL2"], how, exp, ai)
            elif fno == 7:
                if reversal and first:
                    o = first["CardDiscretionaryBlockData"]
                    add(no, label, key, val, "Follow-on credit reversal: Account Number, '=', Expiration Date as YYMM; no other "
                        "discretionary data", ["S10.1.8.2", "EL12"],
                        f"Step {of} sent the expiry as MMYY `{o}`. Rewrite it as YYMM: `{o[2:] + o[:2]}`. Compare with `{val}`.",
                        o[2:] + o[:2], ai)
                else:
                    add(no, label, key, val, "Keyed card: expiration date in MMYY format", ["EL12", "S10.1.1"],
                        f"`{val}` = month {val[:2]}, year 20{val[2:]} = {tdv.expiry(val)}. The month must be 01-12 and, for an "
                        f"approval test, the card must not be expired on the test date ({tdv.TODAY:%B %Y}).",
                        f"MMYY with a valid month, not expired ({tdv.expiry(val)})", ai)
            elif fno in EMPTY_FIELD:
                add(no, label, key, val, f"Conditional (C): {EMPTY_FIELD[fno]}", ["S12.1"],
                    "Must be empty; the Field Separator is still sent." if val == "" else f"The file has `{val}`; check why it is sent.",
                    "(empty)", ai)
            elif fno == 11:
                tcval = str(tcr.get(name) or "")
                if name in ph:
                    k = len(str(int(orig) - 1)) if orig else 7
                    how = (f"The file holds instruction text `{val}` instead of an amount, so the step cannot be sent yet. The "
                           f"test case rule for the run-time value: {self.runtime_rule(n, name)} The test case's provisional value "
                           f"is `{tcval}` ({amount(tcval)}, {len(tcval)} digits): a real value must be digits only, at most 7 "
                           f"digits for MasterCard, and less than the step {of} amount ({amount(orig)}).")
                    exp = (f"The Approved Amount of the step {of} response, leading zeros removed (1 to {k} digits)" if reversal
                           else f"{orig} minus the step {of} response Approved Amount (1 to {k} digits)")
                    add(no, label, key, val, "Up to 8 digits with two assumed decimals; at most 7 digits for non-American Express "
                        "cards", ["EL58", "S10.1.2.1", "EL6"], how, exp, ai)
                else:
                    add(no, label, key, val, "Up to 8 digits with two assumed decimals; at most 7 digits for non-American Express "
                        "cards", ["EL58"], f"`{val}` = {amount(val)} (the last two digits are cents): digits only, {len(val)} "
                        "digits (at most 7 for MasterCard; 8 only for American Express).", f"{tcval} ({amount(tcval)})", ai)
            elif fno == 14:
                how, xs = f"`{val}` has {len(val)} digits and is not 000000.", ["EL86"]
                if reversal and first:
                    how += f" A purchase reversal keeps the Sequence Number of the original: step {of} has `{first['SequenceNumber']}`."
                    exp, xs = first["SequenceNumber"], xs + ["S10.1.2.1"]
                elif split and first:
                    how += (f" A new transaction gets a new Sequence Number: step {of} has `{first['SequenceNumber']}`, so this one "
                            "must be different.")
                    exp, xs = f"6 digits, different from {first['SequenceNumber']}", xs + ["S2.3.1"]
                else:
                    how += " No other new transaction on this terminal may use the same number (see section 10)."
                    exp = f"6 digits, unique ({val})"
                add(no, label, key, val, "Fixed length of six digits, 000001-999999; unique per transaction", xs, how, exp, ai)
            elif fno == 15:
                if name in ph:
                    how = (f"The file holds instruction text `{val}`. At run time it must be the Approval Number returned in the "
                           f"step {of} response (6 characters). The test case's provisional value is `{tcr.get(name)}`.")
                    exp = f"The Approval Number from the step {of} response (6 characters)"
                elif val == "":
                    how = ("Must be empty on a purchase: an Approval Number in a Purchase Request makes it preauthorized, and "
                           "BUYPASS does not go out for authorization.")
                    exp = "(empty)"
                else:
                    how, exp = f"The file has `{val}`.", "(empty) on a purchase; 6 characters on a reversal"
                add(no, label, key, val, "Conditional: required on reversals; not sent on a purchase", ["EL5"], how, exp, ai)
            elif fno == 17:
                how, xs = f"`{val}` = {tdv.PAI.get(val, 'not a valid value')}. Required on all transactions.", ["EL121"]
                if partial:
                    how += " This test case needs a partial approval, which is only returned when the indicator is 1."
                    xs.append("S10.1.2.1")
                add(no, label, key, val, "One digit: 0 not supported, 1 supported, 5 card balance only (American Express prepaid)",
                    xs, how, "1", ai)
        s111 = msg.get("Variable Information Data Segment") or {}
        vi = [tuple(sec.get(k, "") for k in VI_KEYS) for sec in s111.get("VariableInformationSection") or []]
        calc111 = 3 + 3 + sum(len(a + b + c) for a, b, c in vi) + 3
        add("111-1", "Segment Type (85)", "SegmentType", s111.get("SegmentType", ""), "Fixed value: 111", ["S12.10", "EL85"],
            "Read the value.", "111", self.ai(n, "111", 1))
        add("111-2", "Segment Length (84)", "SegmentLength", s111.get("SegmentLength", ""), "Length of the segment including the "
            "Segment Type and all Field Separators", ["S12.10", "EL84"], f"Add it up as in section 5.2, step {n}.",
            f"{calc111:03d}", self.ai(n, "111", 2))
        rfid = "RFID" in self.rec.get("template_id", "")
        for i, (ind, ln, data) in enumerate(vi, 1):
            base, tx = f"VariableInformationSection[{i - 1}]", {"005": "APP-I-005", "031": "APP-I-031"}.get(ind)
            add(f"111-3.{i}", "Variable Information Indicator (111)", f"{base}.VariableInformationIndicator", ind,
                "3 characters: the table ID", ["EL111"] + ([tx] if tx else []),
                f"`{ind}` = Table {ind} ({TABLE_NAME.get(ind, 'see Appendix I')}).", ind, self.ai(n, "111", f"3 [{i}]"))
            fixed = {"005": 3, "031": 2}.get(ind)
            add(f"111-4.{i}", "Variable Information Length (112)", f"{base}.VariableInformationLength", ln,
                "3 digits: number of characters in Variable Information" + (f"; Table {ind} data is {fixed} characters" if fixed else ""),
                ["EL112", "S12.10"] + ([tx] if tx else []), f"Count the characters of `{data}`: {len(data)}, written as "
                f"`{len(data):03d}`.", f"{len(data):03d}", self.ai(n, "111", f"4 [{i}]"))
            if ind == "005":
                pe, pin = data[:2], data[2:3]
                how = (f"Positions 1-2 `{pe}` = {tdv.PAN_ENTRY_DESC.get(pe, '?')} (Appendix J PAN Entry Mode). Position 3 `{pin}` "
                       f"= {tdv.PIN_CAP_DESC.get(pin, '?')} (Appendix J Terminal PIN Entry Capability). The scenario is Manual / "
                       "Keyed, so positions 1-2 must be 01.")
                xs = ["EL113", "APP-I-005", "APP-J"]
                if rfid:
                    how += " An RFID receiver is attached, so the entry mode must still be populated correctly for a keyed card."
                    xs.append("S10.1.5")
                if reversal and first:
                    how += f" A reversal keeps the PAN Entry Mode of the original (step {of}: `{self.ctx['entries'].get(of)}`)."
                add(f"111-5.{i}", "Variable Information (113) - Table 005", f"{base}.VariableInformation", data, "3 digits: PAN "
                    "Entry Mode (positions 1-2) + Terminal PIN Entry Capability (position 3)", xs, how,
                    "01 + the terminal's PIN entry capability code (see row 111-C)", self.ai(n, "111", f"5 [{i}]"))
            elif ind == "031":
                add(f"111-5.{i}", "Variable Information (113) - Table 031", f"{base}.VariableInformation", data, "2 digits: the "
                    "terminal's certified capability; required on every transaction", ["APP-I-031"],
                    f"`{data}` = {tdv.T031_DESC.get(data, 'not a valid code')} (Table 031). It must describe the terminal's "
                    "certified capability, which does not change with the entry mode.",
                    "The terminal's certified capability code (2 digits)", self.ai(n, "111", f"5 [{i}]"))
        t005 = next((d for a, _, d in vi if a == "005"), None)
        t031 = next((d for a, _, d in vi if a == "031"), None)
        if t005 and t031:
            pin = t005[2:3]
            conflict = any(f["rule"] == "TERMINAL_CAPABILITY_CONFLICT" and f["step"] == n for f in self.rep.findings)
            how = (f"Table 005 position 3 is `{pin}` ({tdv.PIN_CAP_DESC.get(pin, '?')}); Table 031 is `{t031}` "
                   f"({tdv.T031_DESC.get(t031, '?')}). Appendix J note 1: a device with a contactless MSR and no chip uses 8; a "
                   "chip device (EMV contact or contactless) uses 1, not 8. Both tables must describe the same terminal.")
            execs = [e for leg in self.rec.get("tx_legs") or [] for e in leg.get("execution") or [] if "chip" in e.lower()]
            if execs:
                how += f" The test case says: \"{execs[0]}\"."
            exp = ("Table 031 = 07 (contactless magnetic stripe, no chip)" if pin == "8" else
                   "A chip terminal (Table 031 05, 06 or 12) with position 3 = 1 or 2")
            add("111-C", "Consistency: Table 005 position 3 vs Table 031", "both tables", f"{pin} / {t031}",
                "Both tables describe the same terminal", ["APP-J", "APP-I-031"], how, exp, "FAIL" if conflict else "PASS")
        return rows

    def fields(self):
        self.out += ['<a id="s4"></a>', "", "## 4. Field-by-field check", "",
                     "For each row: find the JSON key in the test data file (Segment 100 keys are under `Standard Message Data "
                     "Segment`, Segment 111 keys under `Variable Information Data Segment`), read the value, apply the ATL105 "
                     "rule (its text is linked) and compare with the expected value.", ""]
        for st in self.tc["steps"]:
            n = st["step"]
            self.out += [f"### 4.{n} Step {n} - [{st['td']}](../test_data/{st['td']})", "",
                         "| # | Field (Element) | JSON key | Value in the file | ATL105 rule | How to check | Expected | AI result | "
                         "Your check |", "|---|---|---|---|---|---|---|---|---|"]
            for r in self.field_rows(st):
                self.out.append(f"| {r['no']} | {r['field']} | `{r['key']}` | {cell(r['value'])} | {esc(r['rule'])} "
                                f"({self.x(*r['xs'])}) | {md(r['how'])} | {md(r['expected'])} | {r['ai']} | ☐ |")
            self.out.append("")

    def calculations(self):
        x = self.x
        self.out += ['<a id="s5"></a>', "", "## 5. Calculations", "", '<a id="s5-1"></a>', "", "### 5.1 Segment 100 length", "",
                     f"Rule ({x('S12.1', 'EL84')}): the Segment Length counts every field of the segment - including the Segment "
                     "Type and the Segment Length field itself - plus all Field Separators. Each of the 17 fields is followed by a "
                     "Field Separator, even when the field is empty.", ""]
        for st in self.tc["steps"]:
            n, of, v, ph, prov, _, orig = self.info(st)
            self.out += [f"**Step {n} - {st['td']}**", "",
                         "| No. | Field | Value counted | Characters | Field Separator | Running total |", "|---|---|---|---|---|---|"]
            total = 0
            for fno, _, key, name, _ in tdv.SEG100:
                total += len(prov[key]) + 1
                shown = cell(prov[key]) + (f" = test case value (the file holds {cell(v[key])})" if name in ph else "")
                self.out.append(f"| {fno} | {name} | {shown} | {len(prov[key])} | 1 | {total} |")
            declared = v["SegmentLength"]
            if not (ph and orig):
                ok = declared.isdigit() and int(declared) == total
                self.out += ["", f"Total: {total - len(tdv.SEG100)} characters of data + {len(tdv.SEG100)} Field Separators = "
                                 f"**{total:03d}**. The file declares `{declared}`: **{'match' if ok else 'MISMATCH'}**.", ""]
                continue
            base, lo, hi = tdv.length_range(prov, ph, orig)
            real = [f"Nonfuel Amount 1 to {len(str(int(orig) - 1))} digits (a partial approval of the {amount(orig)} sale is at "
                    f"most {amount(str(int(orig) - 1))})"] + (["Approval Number exactly 6 characters"] if "Approval Number" in ph else [])
            ex = int(orig) * 2 // 3
            ex_amt = ex if st["role"] in tcv.SAME_SEQ_ROLES else int(orig) - ex
            ex_len = base + len(str(ex_amt)) + (6 if "Approval Number" in ph else 0)
            self.calc[n] = dict(total=total, lo=lo, hi=hi, real="; ".join(real))
            self.out += ["", f"With the test case value(s) the total is **{total:03d}** - this is where the declared `{declared}` "
                             "comes from.", "",
                         f"The real value(s) only exist after step {of} has run: {'; '.join(real)}. Without the run-time value(s) "
                         f"(but with all 17 Field Separators) the segment is {base} characters, so the real Segment Length can only "
                         f"be **{lo:03d} to {hi:03d}**. Example: Approved Amount `{ex:09d}` gives amount `{ex_amt}`"
                         + (" plus a 6-character Approval Number" if "Approval Number" in ph else "")
                         + f", so the Segment Length is {ex_len:03d}.", "",
                         f"`{declared}` is outside {lo:03d}-{hi:03d}: it can never be correct and must be recalculated after the "
                         "real values are filled in.", ""]

        self.out += ['<a id="s5-2"></a>', "", "### 5.2 Segment 111 length", "",
                     f"Rule ({x('S12.10', 'EL84')}): a Field Separator follows Field 1 (Segment Type) and Field 2 (Segment "
                     "Length). Inside the Variable Information Section there is no Field Separator between the indicator, length "
                     "and data, nor between tables; one Field Separator follows the final table.", ""]
        for st in self.tc["steps"]:
            n = st["step"]
            s111 = self.msgs[n].get("Variable Information Data Segment") or {}
            vi = [tuple(sec.get(k, "") for k in VI_KEYS) for sec in s111.get("VariableInformationSection") or []]
            parts = [("1. Segment Type", s111.get("SegmentType", ""), 1), ("2. Segment Length", s111.get("SegmentLength", ""), 1)]
            parts += [(f"Table {a}: indicator + length + data", f"{a}{b}{c}", 1 if i == len(vi) else 0)
                      for i, (a, b, c) in enumerate(vi, 1)]
            self.out += [f"**Step {n} - {st['td']}**", "",
                         "| Part | Value | Characters | Field Separator after it | Running total |", "|---|---|---|---|---|"]
            total = 0
            for label, val, fs in parts:
                total += len(val) + fs
                self.out.append(f"| {label} | `{val}` | {len(val)} | {fs} | {total} |")
            declared = s111.get("SegmentLength", "")
            ok = declared.isdigit() and int(declared) == total
            self.out += ["", f"Total **{total:03d}**. The file declares `{declared}`: **{'match' if ok else 'MISMATCH'}**.", ""]

        self.out += ['<a id="s5-3"></a>', "", "### 5.3 Card number check digit (Luhn)", "",
                     f"ATL105 does not define card numbers itself: Element 2 refers to the BUYPASS Issuer Identification Number "
                     f"(IIN) Reference Manual ({x('EL2')}). The two checks below are card-industry rules: a MasterCard number starts "
                     "with 51-55 or 2221-2720, and its last digit is a Luhn check digit. Luhn: starting from the right, double every "
                     "second digit (subtract 9 when the result is above 9) and add all the digits; the total must end in 0.", ""]
        pans = uniq(self.ctx["flats"][s["step"]]["AccountNumber"] for s in self.tc["steps"])
        for pan in pans:
            in_steps = [str(s["step"]) for s in self.tc["steps"] if self.ctx["flats"][s["step"]]["AccountNumber"] == pan]
            digits = [int(c) for c in pan]
            dbl = [(len(pan) - i) % 2 == 0 for i in range(len(pan))]
            used = [(d * 2 - 9 if d * 2 > 9 else d * 2) if f else d for d, f in zip(digits, dbl)]
            total = sum(used)
            self.out += [f"**`{pan}`** (step {', '.join(in_steps)})", "",
                         "| Position | " + " | ".join(str(i) for i in range(1, len(pan) + 1)) + " |",
                         "|---|" + "---|" * len(pan),
                         "| Digit | " + " | ".join(str(d) for d in digits) + " |",
                         "| Doubled? | " + " | ".join("×2" if f else "" for f in dbl) + " |",
                         "| Value added | " + " | ".join(str(u) for u in used) + " |", "",
                         f"Sum = {total}, which ends in {total % 10}: check digit **{'valid' if total % 10 == 0 else 'NOT valid'}**. "
                         f"Starts with `{pan[:2]}`: **{'in' if tcv.is_mastercard_bin(pan) else 'NOT in'}** the MasterCard range.", ""]

        reversal = any(s["role"] in tcv.SAME_SEQ_ROLES for s in self.tc["steps"])
        self.out += ['<a id="s5-4"></a>', "", "### 5.4 Expiry dates", "",
                     f"Keyed cards send the expiry as MMYY ({x('EL12', 'S10.1.1')}); dates up to December 2049 are 21st-century "
                     "dates." + (f" Follow-on credit reversals send it as YYMM after an '=' ({x('S10.1.8.2')})." if reversal else ""),
                     "", "| Step | Value in the file | Format | Meaning | Check |", "|---|---|---|---|---|"]
        for st in self.tc["steps"]:
            n, of, v, _, _, first, _ = self.info(st)
            cdb = v["CardDiscretionaryBlockData"]
            if st["role"] in tcv.SAME_SEQ_ROLES and first:
                o = first["CardDiscretionaryBlockData"]
                yymm = o[2:] + o[:2]
                self.out.append(f"| {n} | `{cdb}` | YYMM | {tdv.expiry(cdb[2:] + cdb[:2])} | Step {of} had MMYY `{o}`, as YYMM "
                                f"`{yymm}`: **{'same' if yymm == cdb else 'DIFFERENT'}** |")
            else:
                expired = (2000 + int(cdb[2:]), int(cdb[:2])) < (tdv.TODAY.year, tdv.TODAY.month)
                self.out.append(f"| {n} | `{cdb}` | MMYY | {tdv.expiry(cdb)} | Month {cdb[:2]} is valid; "
                                f"**{'expired' if expired else 'not expired'}** on {tdv.TODAY:%B %Y} |")
        self.out.append("")

    def links(self):
        self.out += ['<a id="s6"></a>', "", "## 6. Links between the steps", ""]
        if len(self.tc["steps"]) == 1:
            self.out += ["Not applicable - this test case has one step.", ""]
            return
        for idx, st in enumerate(self.tc["steps"][1:], 1):
            n, of, *_ = self.info(st)
            a, b = self.ctx["flats"][of], self.ctx["flats"][n]
            ai = {lk["check"]: lk["result"] for lk in self.rep.links if lk["step"] == n}
            if st["role"] in tcv.SAME_SEQ_ROLES:
                o = a["CardDiscretionaryBlockData"]
                rows = [("Sequence Number", a["SequenceNumber"], b["SequenceNumber"], f"Same as step {of}", ["EL86", "S10.1.2.1"],
                         ai.get("Same Sequence Number as the original", "-")),
                        ("Account Number", a["AccountNumber"], b["AccountNumber"], f"Same card as step {of}", ["S2.3.1"],
                         ai.get("Same Account Number as the original", "-")),
                        ("Terminal Identifier", a["TerminalIdentifier"], b["TerminalIdentifier"], f"Same device as step {of}",
                         ["S2.3.1"], ai.get("Same terminal as the original", "-")),
                        ("Prompt Code", a["PromptCode"], b["PromptCode"], "Transaction Type 8 (Purchase reversal), Card Type 020",
                         ["APP-G"], self.ai(n, "100", 5)),
                        ("Expiry", o, b["CardDiscretionaryBlockData"], f"YYMM of step {of}: `{o[2:] + o[:2]}`", ["S10.1.8.2"],
                         self.ai(n, "100", 7)),
                        ("Nonfuel Amount", a["NonfuelAmount"], b["NonfuelAmount"], f"The Approved Amount (Element 6) of the step "
                         f"{of} response", ["S10.1.2.1", "EL6"], self.worst(n, "Nonfuel Amount")),
                        ("Approval Number", a["ApprovalNumber"], b["ApprovalNumber"], f"The Approval Number of the step {of} "
                         "response", ["EL5"], self.worst(n, "Approval Number")),
                        ("POS entry mode (Table 005 positions 1-2)", self.ctx["entries"].get(of), self.ctx["entries"].get(n),
                         f"Same PAN Entry Mode as step {of}", ["APP-J"], ai.get("Same POS entry mode as the original", "-"))]
            else:
                rows = [("Terminal Identifier", a["TerminalIdentifier"], b["TerminalIdentifier"], f"Same device as step {of} "
                         "(the test case carries it)", ["S2.3.1"], "not checked by the AI"),
                        ("Sequence Number", a["SequenceNumber"], b["SequenceNumber"], f"A new number, different from step {of}",
                         ["S2.3.1", "EL86"], ai.get("New Sequence Number", "-")),
                        ("Account Number", a["AccountNumber"], b["AccountNumber"], "A different card (card B) pays the remaining "
                         "balance", ["S10.1.6", "S10.1.2.1"], ai.get("A different card pays the remaining balance", "-")),
                        ("Prompt Code", a["PromptCode"], b["PromptCode"], "Purchase (0), Credit (020)", ["APP-G"],
                         self.ai(n, "100", 5)),
                        ("Nonfuel Amount", a["NonfuelAmount"], b["NonfuelAmount"], f"Remaining balance = {a['NonfuelAmount']} "
                         f"minus the step {of} Approved Amount", ["S10.1.2.1", "EL6"], self.worst(n, "Nonfuel Amount"))]
            self.out += [f"### 6.{idx} Step {n} ({st['role'].replace('_', ' ')}) compared with step {of}", "",
                         f"| Check | Step {of} value | Step {n} value | Same? | Expected | ATL105 | AI result | Your check |",
                         "|---|---|---|---|---|---|---|---|"]
            for label, va, vb, exp, xs, res in rows:
                self.out.append(f"| {label} | {cell(va)} | {cell(vb)} | {'same' if va == vb else 'different'} | {md(exp)} | "
                                f"{self.x(*xs)} | {res} | ☐ |")
            defs = self.leg(n).get("link_defs") or []
            if defs:
                self.out += ["", f"Link rules written in the test case (`tx_legs[{n - 1}].link_defs` in {self.jl}):", "",
                             "| Type | Field | Page cited | Rule |", "|---|---|---|---|"]
                for d in defs:
                    p = d.get("source_page")
                    self.out.append(f"| `{d.get('type')}` | {d.get('element_name')} | p.{p} (printed {self.spec.printed(p)}) | "
                                    f"{md(d.get('rule'))} |")
            self.out.append("")

    def responses(self):
        self.out += ['<a id="s7"></a>', "", "## 7. Expected responses", "",
                     f"Response Code values are listed in {self.x('EL83')}. The tier shows how sure the generator is: ASSERT = taken "
                     "from ATL105; FLAG = not fully grounded, to be confirmed.", "",
                     "| Step | Expected Response Code | Meaning (Element 83) | Tier | Page cited by the test case | AI result | "
                     "Your check |", "|---|---|---|---|---|---|---|"]
        notes = []
        for st in self.tc["steps"]:
            n = st["step"]
            code, tier, page = self.expected_rc(n)
            ai = next((r["result"] for r in self.rep.responses if r["step"] == n), "-")
            pg = f"p.{page} (printed {self.spec.printed(page)})" if page else "-"
            self.out.append(f"| {n} | `{code}` | {RC_TEXT.get(code, 'see Element 83')} | {tier} | {pg} | {ai} | ☐ |")
            if tier == "FLAG":
                notes.append(f"Step {n}: the test case marks the expected `{code}` as FLAG (not fully grounded) - confirm the "
                             f"Response Code of an approved {st['role'].replace('_', ' ')} with BUYPASS.")
        rc = (self.rec.get("expected_response") or {}).get("response_code") or {}
        notes += [f"The test case also accepts `{alt.get('value')}` ({alt.get('meaning')}) when {alt.get('condition')}."
                  for alt in rc.get("acceptable_alternatives") or [] if not self.multi]
        self.out += [""] + [f"- {md(t)}" for t in notes] + ([""] if notes else [])

    def citations(self):
        self.out += ['<a id="s8"></a>', "", "## 8. ATL105 citations written in the test case", "",
                     f"The test case lists its ATL105 sources in `spec_basis` ({self.jl}). Find each quote in the ATL105 text linked "
                     "in the last column.", "",
                     "| # | Page cited | Quote in the test case | AI finding | Compare with | Your check |", "|---|---|---|---|---|---|"]
        for i, q in enumerate(self.rep.quotes, 1):
            xs = PAGE_X.get(q["page"])
            self.out.append(f"| {i} | p.{q['page']} (printed {self.spec.printed(q['page'])}) | {md(q['quote'])} | {q['result']} - "
                            f"{q['status']} | {self.x(xs) if xs else '-'} | ☐ |")
        pages = {}
        for idx, st in enumerate(self.tc["steps"]):
            for field, _, f in self.gen_rows(idx, st):
                p = (f or {}).get("source_page")
                label = re.sub(r" \(Element \d+\)$", "", field)
                if p and label not in pages.setdefault(p, []):
                    pages[p].append(label)
        t005 = self.refs.catalog["APPI-005"][1]
        self.out += ["", "The generator also records an ATL105 page for each field (`source_page`, section 3). Each page should "
                         "hold the rule for that field:", "",
                     "| Page used by the generator | Fields | Holds the rule? | Compare with |", "|---|---|---|---|"]
        for p in sorted(pages):
            xs = FIELD_PAGE_X.get(p) or PAGE_X.get(p)
            ok = (f"No - outdated: Table 005 is on p.{t005} (see section 9)" if p != t005 and any("Table 005" in f for f in pages[p])
                  else "Yes" if xs else "Check")
            self.out.append(f"| p.{p} (printed {self.spec.printed(p)}) | {', '.join(pages[p])} | {ok} | "
                            f"{self.x(xs) if xs else '-'} |")
        self.out.append("")

    def howto(self, f):
        rule, n = f["rule"], f["step"]
        x, jl = self.x, self.jl
        pfx = self.prefix(n) if n else ""
        if rule == "TERMINAL_CAPABILITY_CONFLICT":
            t005, t031 = self.ctx["t005"][n], self.ctx["t031"][n]
            pin = t005[2:3]
            ex = [e for leg in self.rec.get("tx_legs") or [] for e in leg.get("execution") or []]
            return [f"Open `{self.td(n)}`, `Variable Information Data Segment` → `VariableInformationSection[0]` (Table 005): "
                    f"`VariableInformation` = `{t005}`, so position 3 = `{pin}`.",
                    f"Find position 3 in {x('APP-J')} (Terminal PIN Entry Capability Mode codes): `{pin}` = "
                    f"{tdv.PIN_CAP_DESC.get(pin, '?')}. Note 1 under that table: code 8 is used when a contactless MSR is attached "
                    "and the device has no chip capability; for EMV contact and contactless the code is 1, not 8.",
                    f"Open `VariableInformationSection[1]` (Table 031): `VariableInformation` = `{t031}`. Find it in "
                    f"{x('APP-I-031')}: `{t031}` = {tdv.T031_DESC.get(t031, '?')}."]\
                + ([f"Read how the test case describes the terminal: {jl}, `tx_legs[0].execution` = \"{ex[0]}\"."] if ex else [])\
                + [f"Conclusion: Table 005 (code {pin}) describes a terminal without a chip reader, while Table 031 (`{t031}`) "
                   "describes a terminal with a chip reader. One terminal cannot be both. For a contactless-magstripe terminal "
                   f"without a chip, Table 031 must be `07` ({x('APP-I-031')})."]
        if rule == "PROVISIONAL_AMOUNT_TOO_LONG":
            val, pan = f["value"], self.ctx["flats"][n]["AccountNumber"]
            return [f"Open {jl}, `{pfx}request[\"Standard Message Data Segment\"][\"Nonfuel Amount\"]`: `value` = `{val}`, "
                    "`method` = `flow_response_runtime_value` (its notes say the value is provisional).",
                    f"Count the digits: `{val}` has {len(val)} digits ({amount(val)}).",
                    f"Read {x('EL58')}: \"A maximum length of 8 bytes is allowed for American Express cards only. A maximum length "
                    "of 7 bytes is allowed for all other card types.\" This card is a MasterCard (Prompt Code card type 020, card "
                    f"number starting with {pan[:2]}).",
                    f"Conclusion: {len(val)} digits is more than 7, so the value is too long for a MasterCard amount."]
        if rule == "AMOUNT_NOT_POSSIBLE":
            st = self.step(n)
            of = st.get("of", n)
            orig, val = self.ctx["flats"][of]["NonfuelAmount"], f["value"]
            if st["role"] in tcv.SAME_SEQ_ROLES:
                why = (f"Read {x('S10.1.2.1')}: \"The amount in a Reversal (Void) request must be the amount approved in the "
                       "original transaction response when a transaction has been partially approved.\" A partial approval is an "
                       f"amount less than the amount requested ({x('EL121')}), so the reversal amount must be less than "
                       f"{amount(orig)}.")
            else:
                why = (f"Read {x('S10.1.2.1')}: the Approved Amount (Element 6) contains the approved partial amount, and the "
                       "POS must prompt for the remaining balance due on the sale. Remaining balance = "
                       f"{amount(orig)} minus the Approved Amount, so it is always less than {amount(orig)} ({x('EL6', 'EL121')}).")
            return [f"Step {of} amount: `{self.td(of)}`, `NonfuelAmount` = `{orig}` = {amount(orig)} (two assumed decimal "
                    f"places, {x('EL58')}).", f"Test case value for step {n}: `{val}` = {amount(val)}.", why,
                    f"Conclusion: {amount(val)} is larger than {amount(orig)}, which cannot happen after a partial approval."]
        if rule == "SEGMENT_LENGTH_IMPOSSIBLE":
            c = self.calc[n]
            return [f"Do the Segment 100 calculation for step {n} in [section 5.1](#s5-1). With the test case value(s) the total "
                    f"is {c['total']:03d}, the same as the declared `{f['value']}`: the generator calculated the length from the "
                    "provisional value(s).",
                    f"Replace them with real values: {c['real']}. The total can then only be {c['lo']:03d} to {c['hi']:03d}.",
                    f"Read {x('S12.1', 'EL84')}: the Segment Length is calculated from the lengths of all fields plus all Field "
                    "Separators, so it must be recalculated after the real values are filled in.",
                    f"Conclusion: `{f['value']}` is outside {c['lo']:03d}-{c['hi']:03d}, so it can never be correct."]
        if rule == "RUNTIME_PLACEHOLDER":
            key, name = f["field"].split("-> ")[-1], f["label"]
            fmt = {"Nonfuel Amount": f"digits only, at most 7 for MasterCard ({x('EL58')})",
                   "Approval Number": f"six alphanumeric characters ({x('EL5')})"}.get(name, "a real value")
            return [f"Open `{self.td(n)}`, `Standard Message Data Segment` → `{key}`: it contains `{f['value']}` - text in angle "
                    "brackets, not a value.",
                    f"ATL105 defines {name} as {fmt}; instruction text cannot be sent to BUYPASS.",
                    f"Where the real value comes from (test case rule): {self.runtime_rule(n, name)}",
                    f"Conclusion: step {n} cannot be sent until `{key}` holds the real value from the step "
                    f"{self.step(n).get('of', 1)} response."]
        if rule == "CITATION_OUTDATED_PAGE":
            t005, cited = self.refs.catalog["APPI-005"][1], int(f["value"].replace("p.", ""))
            where = "every step: `steps[i]." if self.multi else "`"
            return [f"Open {jl}. In {where}request[\"Variable Information Data Segment\"][\"Variable Information Indicator\"]` "
                    f"(and `Variable Information Length`, `Variable Information`) `source_page` = {cited}.",
                    f"Read {x(PAGE_X.get(cited, 'APP-H-548'))}: in release {self.spec.version}, {self.pg(cited)} is the "
                    "decline-code table of Appendix H, not Table 005.",
                    f"Read {x('APP-I-005')}: Table 005 (Point-of-Service Entry Mode Information) is on {self.pg(t005)}.",
                    f"Conclusion: the page number is out of date (Table 005 was on p.{cited} in the older 2026-3 edition). The "
                    "data values are not affected."]
        if rule == "QUOTE_CONTEXT":
            page = int(re.search(r"p\.(\d+)", f["label"]).group(1))
            return [f"Open {jl}, `spec_basis`: the entry with `page` {page} quotes {quoted(f['value'])}",
                    f"Read {x(PAGE_X[page])}: the quote is word-for-word, but it is in 10.1.6 Healthcare Auto-Substantiation "
                    "Service, which is about FSA/HRA healthcare benefit cards.",
                    f"Read {x('S10.1.2.1')}: the general credit card rule - after a partial approval the POS must prompt for the "
                    "remaining balance due on the sale.",
                    "Conclusion: the quote is real but comes from the healthcare section; cite Section 10.1.2.1 (PDF p.84) as "
                    "the main basis for a normal MasterCard credit card."]
        if rule.startswith("QUOTE_"):
            page = int(re.search(r"p\.(\d+)", f["label"]).group(1))
            xs = PAGE_X.get(page)
            return [f"Open {jl}, `spec_basis`: the entry with `page` {page} quotes {quoted(f['value'])}",
                    f"Compare it with the ATL105 text in {x(xs) if xs else 'the cited page'}"
                    + (f": the row reads {quoted(self.refs.quote(f['refs'][0]))}" if f["refs"] else "."),
                    f"Conclusion: {tdv.QUOTE_STATUS.get(rule, rule)}."
                    + (" The meaning is the same; only the wording of the quote should be corrected."
                       if rule == "QUOTE_PARAPHRASED" else "")]
        if rule == "TERMINAL_PLACEHOLDER":
            tid = f["value"]
            info = self.states.get(tid[2:4])
            flag = next((fl for fl in self.rec.get("flags") or [] if fl.startswith("MERCHANT_PLACEHOLDER")), "MERCHANT_PLACEHOLDER")
            return [f"Open {jl}: `merchant.name` = \"{self.rec['merchant']['name']}\", `merchant_provenance` = "
                    f"\"{self.rec.get('merchant_provenance')}\", and `flags` contains \"{flag}\".",
                    f"Read {x('EL102')}: \"The Terminal Identifier must be assigned by and set up at BUYPASS.\" A generated value "
                    "is not assigned by BUYPASS.",
                    f"Positions 3-4 of `{tid}` are `{tid[2:4]}`. Find `{tid[2:4]}` in {x('APP-D')}: "
                    + (f"{info[0]} ({info[1]}), {info[3]}." if info else "not listed."),
                    "Conclusion: ask the client/BUYPASS for the real Terminal Identifier of this merchant before execution and "
                    "confirm its location."]
        if rule == "READY_WITH_PLACEHOLDER":
            line = self.loc["md"]
            return [f"Open [TEST_CASES.md line {line}](../test_cases/TEST_CASES.md#L{line}): the Status column is "
                    f"`{self.rec.get('status')}` (the same as `status` in the test case record).",
                    "The previous issue shows that the Terminal Identifier is a placeholder, so BUYPASS cannot accept the message "
                    "as-is.",
                    "Conclusion: the status should not be 'ready' until the real Terminal Identifier is supplied."]
        if rule == "SAME_REQUEST_AS_OTHER_TC":
            other = re.search(r"TC-\d+", f["title"]).group(0)
            other_td = next(tc["steps"][0]["td"] for tc, _ in self.env["results"] if tc["id"] == other)
            mine = self.tc["steps"][0]["td"]
            a, b = self.env["ctxs"][other]["flats"][1], self.ctx["flats"][1]
            diff = [k for k in a if a[k] != b[k]]
            return [f"Open [{other_td}](../test_data/{other_td}) and [{mine}](../test_data/{mine}) side by side (VS Code: "
                    "right-click the first file, Select for Compare, then right-click the second, Compare with Selected).",
                    "Only " + ", ".join(f"`{k}` (`{a[k]}` and `{b[k]}`)" for k in diff) + " differs; every other field, "
                    "including both Variable Information tables, is identical.",
                    f"Read {x('S10.1.1')}: the three failed swipes and the manual-entry prompt happen on the device; nothing about "
                    "them is sent to BUYPASS.",
                    "Conclusion: the message is valid, but what makes this test different must be checked on the device (see "
                    "the device checks in section 1)."]
        if rule == "PARTIAL_APPROVAL_PRECONDITION":
            ex = (self.leg(n).get("execution") or [""])[0]
            fl = self.ctx["flats"][n]
            oi = "; ".join(self.rec.get("open_items") or [])
            return [f"Open {jl}, `tx_legs[{n - 1}]`: `expect.response_code` = `F`; `execution` = {quoted(ex)}",
                    f"Read {x('S10.1.2.1')} and {x('EL121')}: F is returned only when Partial Approval Indicator 1 is sent and "
                    "the network approves an amount less than the amount requested.",
                    f"In `{self.td(n)}`: `PartialApprovalIndicator` = `{fl['PartialApprovalIndicator']}` and `NonfuelAmount` = "
                    f"`{fl['NonfuelAmount']}` ({amount(fl['NonfuelAmount'])}).",
                    f"Conclusion: the test needs a card or simulator setting that approves less than "
                    f"{amount(fl['NonfuelAmount'])}." + (f" The test case lists this under `open_items`: {quoted(oi)}" if oi else "")]
        if rule == "CITATION_WRONG_SECTION":
            el = f["label"]
            d = next((d for d in self.leg(n).get("link_defs") or [] if d.get("element_name") == el), {})
            return [f"Open {jl}, `tx_legs[{n - 1}].link_defs`: the rule for `{el}` cites `source_page` {d.get('source_page')}: "
                    f"{quoted(d.get('rule'))}",
                    f"Read {x('S10.1.2.2')}: {self.pg(85)} is the Authorization Only Reversal, which reverses Transaction Types 3 "
                    "and 5 and uses Transaction Type S.",
                    f"In `{self.td(n)}`: `PromptCode.TransactionType` = `{self.ctx['flats'][n]['PromptCode'][:1]}`; in "
                    f"{x('APP-G')} 8 = Purchase reversal.",
                    f"Read {x('EL5')}: \"Required on reversals for matching information to the original purchase.\" This is the "
                    "rule for a Purchase Reversal.",
                    "Conclusion: the data is right; only the citation should change to Element 5, PDF p.368."]
        if rule == "FOLLOW_ON_EQUALS_SEPARATOR":
            fl = self.ctx["flats"][n]
            return [f"In `{self.td(n)}`: `AccountNumber` = `{fl['AccountNumber']}` and `CardDiscretionaryBlockData` = "
                    f"`{fl['CardDiscretionaryBlockData']}`.",
                    f"Read {x('S10.1.8.2')}: for credit POS purchase reversals the two items \"must be separated by an “=” sign, "
                    "rather than a Field Separator\".",
                    f"Conclusion: the wire message must carry `{fl['AccountNumber']}={fl['CardDiscretionaryBlockData']}`. The '=' "
                    "takes the place of the Field Separator, so the Segment Length does not change."]
        return [f"Open {md(f['field'])} and confirm the value `{f['value']}`.",
                f"Read {self.xr(f['refs'])}." if f["refs"] else "Read the related ATL105 text.",
                f"Compare: {md(f['why'])}"]

    def issues(self):
        fs = sorted(self.rep.findings, key=lambda f: (tdv.SEVERITY_ORDER[f["severity"]], tdv.RULE_ORDER.get(f["rule"], 0)))
        self.out += ['<a id="s9"></a>', "", "## 9. What is wrong or missing - and how to confirm each issue by hand", "",
                     f"These are the issues the AI validator reported for {self.tc['id']}, numbered as in the "
                     f"[AI report](../Result_MD_Folder/{self.tc['id']}.md).", ""]
        if not fs:
            self.out += ["The AI validator reported no issues.", ""]
            return
        self.out += ["| # | Type | Issue | Step | AI result |", "|---|---|---|---|---|"]
        for i, f in enumerate(fs, 1):
            kind = MISSING_INPUT.get(f["rule"]) or KIND[f["severity"]]
            self.out.append(f"| [{i}](#i{i}) | {kind} | {md(f['title'])} | {f['step'] or 'all'} | {f['severity']} |")
        self.out.append("")
        for i, f in enumerate(fs, 1):
            atl = "<br>".join(f"{self.x(xid(r))} {self.refs.short(r)}: \"{esc(self.refs.quote(r))}\"" for r in f["refs"]) or "-"
            self.out += [f'<a id="i{i}"></a>', "", f"### 9.{i} {md(f['title'])} - {f['severity']}", "", "| Item | Value |",
                         "|---|---|"]
            if f["step"]:
                self.out.append(f"| Step | {f['step']} |")
            self.out += [f"| Where | {md(f['field'])} |", f"| Value | `{esc(f['value'])}` |", f"| ATL105 | {atl} |",
                         f"| Why (AI) | {md(f['why'])} |", f"| What it should be (AI) | {md(f['fix'])} |", "",
                         "**How to confirm by hand**", ""]
            self.out += [f"{k}. {t}" for k, t in enumerate(self.howto(f), 1)]
            self.out += ["", "Your conclusion: ☐ Agree ☐ Disagree - note: ____________", ""]

    def across(self):
        self.out += ['<a id="s10"></a>', "", "## 10. Checks across the five test cases", "",
                     "All five test cases use the same terminal, so some rules can only be checked by comparing them.", "",
                     "| Check | AI result | Detail | ATL105 | Your check |", "|---|---|---|---|---|"]
        for c in self.env["cross"]:
            self.out.append(f"| {md(c['check'])} | {c['result']} | {md(c['detail'])} | "
                            f"{self.xr(c['refs']) if c.get('refs') else '-'} | ☐ |")
        self.out += ["", "Sequence Numbers sent on the terminal (a reversal reuses the number of its original on purpose):", "",
                     "| Test case | Step | Role | Sequence Number |", "|---|---|---|---|"]
        self.out += [f"| {tc['id']} | {s['step']} | {s['role'].replace('_', ' ')} | "
                     f"`{self.env['ctxs'][tc['id']]['flats'][s['step']]['SequenceNumber']}` |"
                     for tc, _ in self.env["results"] for s in tc["steps"]] + [""]

    def atl105(self):
        missing = [u for u in self.used if u not in self.env["extracts"]]
        if missing:
            raise SystemExit(f"{self.tc['id']}: no ATL105 text defined for {missing}")
        self.out += ['<a id="s11"></a>', "", "## 11. ATL105 text used in this guide", "",
                     f"Copied from the {tdv.SPEC_TITLE}, release {self.spec.version} ({self.spec.date}). Each line below was "
                     "found on the stated PDF page when this guide was generated. Table rows keep the ATL105 column order.", ""]
        for xid_ in X_ORDER:
            if xid_ in self.used:
                title, items = self.env["extracts"][xid_]
                self.out += render_extract(self.spec, xid_, title, items)

    def signoff(self):
        rep = self.rep

        def cnt(rows):
            return ", ".join(f"{v} {k}" for k, v in sorted(Counter(r["result"] for r in rows).items()))

        issues = ", ".join(f"{rep.count(s)} {s}" for s in ("FAIL", "BLOCKED", "REVIEW", "INFO") if rep.count(s)) or "none"
        self.out += ['<a id="s12"></a>', "", "## 12. Sign-off", "",
                     "| Area | AI result | Your result | Comment |", "|---|---|---|---|",
                     f"| Field checks (section 4) | {cnt(rep.rows)} | ☐ Agree ☐ Disagree | |",
                     f"| Links between steps (section 6) | {cnt(rep.links) or 'not applicable'} | ☐ Agree ☐ Disagree | |",
                     f"| Expected responses (section 7) | {cnt(rep.responses)} | ☐ Agree ☐ Disagree | |",
                     f"| Citations (section 8) | {cnt(rep.quotes)} | ☐ Agree ☐ Disagree | |",
                     f"| Issues (section 9) | {issues} | ☐ Agree ☐ Disagree | |",
                     f"| **Overall** | **{rep.verdict()}** - {rep.can_run()} | ☐ Agree ☐ Disagree | |", "",
                     "Reviewed by: ____________________    Date: ____________", ""]


def write_readme(out, env, recs):
    spec = env["spec"]
    lines = ["# Manual Validation - TC-001867 to TC-001871", "",
             "Step-by-step guides for checking the five ATL105 test cases and their test data by hand, and for confirming or "
             "rejecting what the AI validator reported. Each guide contains the ATL105 text needed for its checks, so the "
             "specification does not have to be opened.", "",
             "| Test case | Guide | Test data | AI result | Can it run as-is? |", "|---|---|---|---|---|"]
    for tc, rep in env["results"]:
        files = ", ".join(f"[{s['td']}](../test_data/{s['td']})" for s in tc["steps"])
        lines.append(f"| {tc['id']} ({tc['display_id']}) - {md(recs[tc['id']].get('test_objective'))} | "
                     f"[{tc['id']}-Manual-Validation.md]({tc['id']}-Manual-Validation.md) | {files} | **{rep.verdict()}** | "
                     f"{rep.can_run()} |")
    lines += ["", "## How to use a guide", "",
              "1. Open the guide in preview (right-click the file, Open Preview) next to its test data file.",
              "2. Follow section 2 of the guide: it lists the checks in order and links to each section.",
              "3. In each table, compare the value in the file with the expected value and fill in the Your check column.",
              "4. For each issue in section 9, follow 'How to confirm by hand' and record Agree or Disagree.",
              "5. Complete the sign-off in section 12.", "",
              "## Sources", "",
              f"- ATL105: {tdv.SPEC_TITLE}, release {spec.version} ({spec.date}), text from "
              "`specifications/ATL105/docs/specs/extracted_text_new.txt`. 'PDF p.' is the page in the PDF viewer; 'printed' is "
              "the page number in the page footer.",
              "- Test cases: [test_cases/test_cases.jsonl](../test_cases/test_cases.jsonl) (full records) and "
              "[test_cases/TEST_CASES.md](../test_cases/TEST_CASES.md) (summary).",
              "- Test data: [test_data](../test_data).",
              "- AI validator reports: [Result_MD_Folder](../Result_MD_Folder) - start with "
              "[ATL105-TD-Validation-Key-Findings.md](../Result_MD_Folder/ATL105-TD-Validation-Key-Findings.md).",
              "- Regenerate these guides with `python scripts/build-manual-validation-guides.py` after the test cases or test "
              "data change.", ""]
    (out / "README.md").write_text("\n".join(lines) + "\n", encoding="utf-8")


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--metadata", default=str(ROOT / "Result_MD_Folder" / "inputs" / "tc-001867-001871.metadata.json"))
    ap.add_argument("--test-cases", default=str(ROOT / "test_cases"))
    ap.add_argument("--test-data", default=str(ROOT / "test_data"))
    ap.add_argument("--spec-text", default=str(tcv.DEFAULT_SPEC))
    ap.add_argument("--prior-spec-text", default=str(tcv.PRIOR_SPEC))
    ap.add_argument("--out", default=str(ROOT / "Manual Validation"))
    a = ap.parse_args()

    meta = json.loads(Path(a.metadata).read_text(encoding="utf-8"))
    ref_json = json.loads(tcv.ELEMENT_REF.read_text(encoding="utf-8"))
    ref = {e["element"]: e for e in ref_json["elements"]}
    spec = tdv.Spec(Path(a.spec_text))
    prior = tdv.Spec(Path(a.prior_spec_text)) if Path(a.prior_spec_text).exists() else None
    states = spec.state_codes()
    refs = tdv.Refs(spec, prior, states)
    qv = tcv.Validator(spec.sq, {}, ref_json)
    td_dir = Path(a.test_data)

    results, ctxs = [], {}
    for tc in meta["test_cases"]:
        rep, ctx = tdv.validate_tc(tc, meta, td_dir, ref, refs, qv, states)
        results.append((tc, rep))
        ctxs[tc["id"]] = ctx
    cross = tdv.cross_checks(results, ctxs)

    extracts = dict(EXTRACTS)
    extracts["APP-D"] = app_d(states, sorted({f["TerminalIdentifier"][2:4] for c in ctxs.values() for f in c["flats"].values()}))
    bad = verify(spec, extracts)
    if bad:
        raise SystemExit("ATL105 text not found on the stated page:\n" + "\n".join(bad))

    recs, locs = locate(Path(a.test_cases), [tc["id"] for tc in meta["test_cases"]])
    out = Path(a.out)
    out.mkdir(parents=True, exist_ok=True)
    env = dict(spec=spec, refs=refs, states=states, extracts=extracts, cross=cross, results=results, ctxs=ctxs, td_dir=td_dir)
    for tc, rep in results:
        g = Guide(env, tc, rep, ctxs[tc["id"]], recs[tc["id"]], locs[tc["id"]])
        (out / f"{tc['id']}-Manual-Validation.md").write_text(g.build(), encoding="utf-8")
        print(f"{tc['id']}: AI result {rep.verdict():7} ATL105 extracts {len(g.used)}")
    write_readme(out, env, recs)
    lines = sum(1 for _, items in extracts.values() for item in items if item[0] != "H")
    print(f"ATL105 lines/rows copied: {lines}; all found on their stated pages")


if __name__ == "__main__":
    main()
