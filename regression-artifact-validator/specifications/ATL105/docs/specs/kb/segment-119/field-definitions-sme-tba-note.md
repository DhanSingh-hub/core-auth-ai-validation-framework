# Segment 119 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 119 — Totals with Proprietary Data Load Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.4, 11.4.1.2, 12.17, 9  
**Oracle:** [segment-119-rule-catalog.json](coverage/segment-119-rule-catalog.json) (36 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 119 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (20)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG119-R-008` | Segment Type is fixed value 119 | 12.17 | 85 | SPEC_DERIVED |
| `SEG119-R-009` | Segment Length is required and includes Segment Type and Field Separators | 12.17 | 84 | SPEC_DERIVED |
| `SEG119-R-010` | Information Byte is required | 12.17 | 44 | SPEC_DERIVED |
| `SEG119-R-011` | Terminal Identifier is required | 12.17 | 102 | SPEC_DERIVED |
| `SEG119-R-012` | Prompt Code is fixed value 990 | 12.17 | 78 | SPEC_DERIVED |
| `SEG119-R-013` | Employee Number is conditional | 12.17 | 32 | SPEC_DERIVED |
| `SEG119-R-014` | Password is conditional | 12.17 | 65 | SPEC_DERIVED |
| `SEG119-R-015` | Totals Date is required | 12.17 | 105 | SPEC_DERIVED |
| `SEG119-R-016` | Hardware Version is required | 12.17 | 43 | SPEC_DERIVED |
| `SEG119-R-017` | Software Version is required | 12.17 | 96 | SPEC_DERIVED |
| `SEG119-R-018` | Firmware Version is required | 12.17 | 39 | SPEC_DERIVED |
| `SEG119-R-019` | Sequence Number is required | 12.17 | 86 | SPEC_DERIVED |
| `SEG119-R-020` | Device Card Table Version is required | 12.17 | 176 | SPEC_DERIVED |
| `SEG119-R-021` | Host Discount Timestamp is required and uses CCYYMMDDHHMM | 12.17 | 179 | SPEC_DERIVED |
| `SEG119-R-022` | Currency Code is optional | 12.17 | 20 | SPEC_DERIVED |
| `SEG119-R-023` | Grand Total is required | 12.17 | 42 | SPEC_DERIVED |
| `SEG119-R-024` | Card Label is required for each card bucket | 12.17 | 13 | SPEC_DERIVED |
| `SEG119-R-025` | Card Type Total Count is required for each card bucket | 12.17 | 16 | SPEC_DERIVED |
| `SEG119-R-026` | Card Type Total Amount is required for each card bucket | 12.17 | 15 | SPEC_DERIVED |
| `SEG119-R-036` | Segment 119 synthetic fixtures are replaced or explicitly accepted for training | 11.4.1.2,12.17 | — | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | See [Element 85 reference](#element-85-segment-type) |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits — see [Element 84 reference](#element-84-segment-length) | See [Element 84 reference](#element-84-segment-length) |
| 44 | Information Byte | AN | 1 byte | Fixed length of one alphanumeric character | Character Description ? Download Request 0 Single-message transmission 1 Multimessage transmission |
| 102 | Terminal Identifier | AN | 22 bytes | Variable length up to 22 alphanumeric characters | See [Element 102 reference](#element-102-terminal-identifier) |
| 78 | Prompt Code | AN | 4 bytes | Variable length of three or four alphanumeric characters | See [Element 78 reference](#element-78-prompt-code) |
| 32 | Employee Number | N | 4 bytes | Fixed length of four digits | Valid employee numbers. Note: Use 1111 if no employee numbers are assigned. |
| 65 | Password | N | 6 bytes | Variable length of up to six digits | Default password: 123456. |
| 105 | Totals Date | N | 6 bytes | Fixed length of six digits | See [Element 105 reference](#element-105-totals-date) |
| 43 | Hardware Version | AN | 4/8 bytes | Fixed length of four or eight alphanumeric characters | Any valid hardware version number |
| 96 | Software Version | AN | 8 bytes | Fixed length of eight alphanumeric characters | — |
| 39 | Firmware Version | AN | 8 bytes | Fixed length of eight alphanumeric characters |  |
| 86 | Sequence Number | N | 6 bytes | Fixed length of six digits | See [Element 86 reference](#element-86-sequence-number) |
| 20 | Currency Code | N | 3 bytes | Fixed length of three digits | Please see Appendix L. Valid Currency Codes. |
| 42 | Grand Total | N | 8 bytes | Fixed length of eight digits with two assumed decimal places | 00000000–99999999 |
| 13 | Card Label | AN | 4 bytes | Fixed length of four alphanumeric characters; left-aligned and space-filled | See [Element 13 reference](#element-13-card-label) |
| 16 | Card Type Total Count | N | 5 bytes | Fixed length of five digits | 00001–99999 |
| 15 | Card Type Total Amount | N | 8 bytes | Fixed length of eight digits with two assumed decimal places | 00000000–99999999 |

## Chapter 13 Reference: Full Element Definitions

Full, untruncated Chapter 13.2 text for the elements listed above, transcribed from the ATL105 specification extract (`docs/specs/extracted_text.txt`). The table above links here instead of truncating long value lists.

### Element 85: Segment Type

- **Character type:** N · **Maximum length:** 3 bytes
- **Representation:** Fixed length of three digits
- **Purpose:** Identifies the type of segment being formatted in a Financial Transaction request, a Totals request, a Loyalty Card transaction request, Electronic Mail request, or a TransArmor PKI Encryption and Tokenization Load Request. For TransArmor PKI Encryption and Tokenization, this value indicates that the segment contains Key and Key ID information.
- **Processing rules:** Required for the TransArmor PKI Encryption and Tokenization Load Request.

**Valid Codes/Values**

| Code | Description |
|---|---|
| `100` | Data Segment No. 100, Standard Message Data Segment |
| `101` | Data Segment No. 101, Fleet Data Segment |
| `102` | Data Segment No. 102, Product Code Data Segment |
| `103` | Data Segment No. 103, EBT Data Segment |
| `104` | Data Segment No. 104, Purchase Card Data Segment |
| `105` | Data Segment No. 105, Totals Data Segment |
| `106` | Data Segment No. 106 is reserved for proprietary use. |
| `107` | Data Segment No. 107 is reserved for proprietary use. |
| `108` | Data Segment No. 108, Loyalty Card Data Segment |
| `109` | Data Segment No. 109, Electronic Mail Data Segment |
| `111` | Data Segment No. 111, Variable Information Data Segment |
| `112` | Data Segment No. 112, Additional Information Data Segment |
| `116` | Data Segment No. 116, TransArmor Load Data Segment |
| `118` | Data Segment No 118, Proprietary Data Load Segment |
| `119` | Data Segment No. 119, Totals with Proprietary Data Load Load Data Segment |
| `120` | Data Segment No. 120, Print Data 2 Segment |
| `123` | Data Segment No. 123, NFC Payment Tokenization Data Segment |
| `130` | Data Segment No. 130, EMV Request Data Segment |
| `131` | Data Segment No. 131, EMV Response Data Segment |
| `132` | Data Segment No. 132, CA Public Key File Segment |
| `134` | Data Segment No. 134, Transaction Attributes Data Segment |
| `157` | Data Segment No. 157, Adjusted Product Code Data Segment |
| `DL1` | Data Segment No. DL1, Merchant Data Segment. |
| `DL2` | Data Segment No. DL2, Dial String Data Segment |
| `DL3` | Data Segment No. DL3, Date and Time Data Segment |
| `DL4` | Data Segment No. DL4, Software Dial Load Data Segment |
| `DL5` | Data Segment No. DL5, Software IP Load Data Segment |
| `DL6` | Data Segment No.DL6, Store and Forward Data Segment |

### Element 84: Segment Length

- **Character type:** N · **Maximum length:** 4 bytes
- **Representation:** Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (No. 103) • SKU Data Segment (No. 114) • Print Data Segment (No. 115) • Proprietary Data Load Segment (No. 118) • Print Data 2 Segment (No. 120) • EMV Request Data Segment (No.130) • EMV Response Data Segment (No. 131)
- **Purpose:** Identifies the length of the segment in which it appears.
- **Processing rules:** This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements. (Please refer to chapter 12, “Data Segment Formats,” for additional information about specific data segments—including their composition, data element lengths, Field Separators, and Product Data Field Delimiters.) Note: Segment Length can only have a length of 4 digits when transmitting the following Data Segments. It cannot have a length of 4 digits when sending any other segment. • EBT Data Segment (No. 103) • SKU Data Segment (No. 114) • Print Data Segment (No. 115) • Proprietary Data Load Segment (No. 118) • Print Data 2 Segment (No. 120) • EMV Request Data Segment (No.130) • EMV Response Data Segment (No. 131)

**Valid Codes/Values**

_Source column headings: Data Length Data Segment_

| Code | Description |
|---|---|
| `001–218` | Standard Message Data Segment (No. 100) |
| `001–61` | Fleet Data Segment (No. 101) |
| `001–381` | Product Code Data Segment (No. 102) |
| `001–3334` | EBT Data Segment (No. 103) |
| `001–86` | Purchase Card Data Segment (No. 104) |
| `001–409` | Totals Data Segment (No. 105) |
| `001–142` | Loyalty Card Data Segment (No. 108) |
| `001–232` | Electronic Mail Data Segment (No. 109) |
| `001–168` | Check Data Segment (No. 110) |
| `001–999` | Variable Information Data Segment (No. 111) |
| `001–999` | Additional Information Data Segment (No. 112) |
| `001–156` | ECA/ TeleCheck® Data Segment (No. 113) |
| `0001–1010` | SKU Data Segment (No. 114) |
| `001-1009` | Print Data Segment (No. 115) |
| `01–50` | TransArmor Load Data Segment (No. 116) |
| `0001-3800` | Proprietary Data Load Segment (No. 118) |
| `001-493` | Totals with Proprietary Data Load Data Segment (No. 119) |
| `001-1009` | Print Data 2 Segment (No. 120) |
| `001-186` | NFC Payment Tokenization Data Segment (No. 123) |
| `001-3043` | EMV Request Data Segment (No.130) |
| `001-3834` | EMV Response Data Segment (No. 131) |
| `01-77` | CA Public Key File Data Segment (No. 132) |
| `01-19` | Transaction Attributes Data Segment (No. 134) |
| `001–381` | Adjusted Product Code Data Segment (No. 157) |

### Element 44: Information Byte

- **Character type:** AN · **Maximum length:** 1 byte
- **Representation:** Fixed length of one alphanumeric character
- **Purpose:** Identifies the type of request
- **Processing rules:** Download Request signifies type of special load. Single-message Request signifies one transaction is performed, and then communication is discontinued. Multimessage Request signifies that after a transaction has been processed, the communication line remains established for further transactions.

**Valid Codes/Values**

Character Description ? Download Request 0 Single-message transmission 1 Multimessage transmission

### Element 102: Terminal Identifier

- **Character type:** AN · **Maximum length:** 22 bytes
- **Representation:** Variable length up to 22 alphanumeric characters
- **Purpose:** Identifies the transaction device by Device Type, State Code (ANSI® Code), BUYPASS Merchant Number, and Device Number.
- **Processing rules:** Required for all transactions and messages. The Terminal Identifier must be assigned by and set up at BUYPASS. Note: In the case of a Table Load Request, Phone Load Request, Date and Time Load Request, or Software Load Request, the field length is 13 bytes; otherwise, the field length is a maximum of 22 bytes. For the TransArmor PKI Encryption and Tokenization Load Request, the first two characters (Device Type) of this element’s value must be “++” regardless of the actual device type. For the EMV Key Load Request, the first two characters (Device Type) of this element’s value must be “+*” regardless of the actual device type.

**Valid Codes/Values**

Device Type (Pos. Nos. 1–2) Any valid 2-character Device Type assigned by BUYPASS personnel. For the TransArmor PKI Encryption and Tokenization Load Request, this element’s value must be “++” regardless of the actual device type. For the EMV Key Load Request, this element’s value must be “+*” regardless of the actual device type. State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code). Please see Appendix D. Valid State Codes, for a list of valid ANSI® Code entries. BUYPASS Merchant Number (Starts immediately in Pos. No. 5.) Any valid 6- to 15-character BUYPASS Merchant Number assigned by BUYPASS personnel. The Device Number immediately follows the BUYPASS Merchant Number. Note: If using TransArmor, the first 6 digits of the Merchant Number are used in Error! Reference source not found.. P lease refer to the description of Element No. 2 (Account Number) for additional information. Device Number (Starts immediately after the BUYPASS Merchant Number.) Any valid 3-character Device Number assigned by BUYPASS personnel.

### Element 78: Prompt Code

- **Character type:** AN · **Maximum length:** 4 bytes
- **Representation:** Variable length of three or four alphanumeric characters
- **Purpose:** In the case of a financial transaction, this is a 4-character Prompt Code that identifies the transaction’s Transaction Type (one character) and Card Type (three characters). In the case of a special transaction, this is a 3-character Prompt Code that identifies requested special data (3-character Card Type).
- **Processing rules:** Must be a valid Transaction Type code and/or Card Type code. In the case of a financial transaction, a device transmits a 4- character Prompt Code when the transaction is initiated. In the case of a special transaction, a device transmits a 3- character Prompt Code (3-character Card Type) for a special transaction when the transaction is initiated. In the case of an Authorization Only Reversal, this element— present in the original transaction—must be present and identical in the Authorization Only Reversal transaction. (Note: The Authorization Only Reversal transaction enables a merchant to reverse the following credit card transactions within required timeframes: POS Authorization Only [Transaction Type code “3”]); POS Mail/Phone Authorization Only [Transaction Type code “B”]); and CAT Authorization Only [Transaction Type code “5”]). Signature Debit eligible transactions must be sent with a Credit Prompt Code, and NOT a Debit Prompt Code. In EMV transactions, if ‘D’ is returned in response, then subsequent transactions i.e. completion or void or reversal must be sent as credit. This will be reflected in the Prompt Code (Data Segment 100: Data Element 78) of the subsequent transaction. In EMV transactions, if ‘S’ is returned in response, then subsequent transactions i.e. completion or void or reversal must be sent as debit. This will be reflected in the Prompt Code (Data Segment 100: Data Element 78) of the subsequent transaction.

**Valid Codes/Values**

In the case of a financial transaction, position no. 1 is the Transaction Type (For a list of valid entries, please see Appendix G. Valid Transaction Type Codes.); and position nos. 2–4 are the Card Type (For a list of valid entries, please see Appendix E. Valid Card Type Codes.). In the case of a special transaction, position nos. 1–3 are the Card Type (For a list of valid entries, please see Appendix E. Valid Card Type Codes.) that identifies requested special data.

### Element 32: Employee Number

- **Character type:** N · **Maximum length:** 4 bytes
- **Representation:** Fixed length of four digits
- **Purpose:** Identifies the employee who performs a utility transaction. Note: Used for reporting purposes only.

**Valid Codes/Values**

Valid employee numbers. Note: Use 1111 if no employee numbers are assigned.

### Element 65: Password

- **Character type:** N · **Maximum length:** 6 bytes
- **Representation:** Variable length of up to six digits
- **Purpose:** Identifies the password entered during the end-of-day function.
- **Processing rules:** Password required for totals request or electronic mail request. This is matched against the password defined in the merchant’s profile at BUYPASS. If entry is less than six characters long, right-align the entry using spaces.

**Valid Codes/Values**

Default password: 123456.

### Element 105: Totals Date

- **Character type:** N · **Maximum length:** 6 bytes
- **Representation:** Fixed length of six digits
- **Purpose:** Identifies the date for which totals are being requested or the date for which totals and an end-of-day are being requested.
- **Processing rules:** The customer assigns this information. Totals cannot be requested for a date prior to the third most recent date with activity. Note: The value of this field in a Totals Response can be different than the value sent in the Totals Request.

**Valid Codes/Values**

Request Response Description MMDDYY YYMMDD Identifies a request for totals for a specified settlement date. 111111 111111 Identifies a request for totals for the period of time since the last 999999 code was used. 222222 YYMMDD Identifies a request for totals for the current settlement date; ends the current settlement date; and rolls the date to the next settlement date. 333333 YYMMDD Identifies a request for totals for the most recent date with activity. 444444 YYMMDD Identifies a request for totals for the second most recent date with activity. 555555 YYMMDD Identifies a request for totals for the third most recent date with activity. 999999 999999 Identifies a request to clear all totals since the last 999999 code was used. Used with the 111111 code.

### Element 43: Hardware Version

- **Character type:** AN · **Maximum length:** 4/8 bytes
- **Representation:** Fixed length of four or eight alphanumeric characters
- **Purpose:** Identifies the device’s hardware version.
- **Processing rules:** Required for Totals Requests, Table Load Request, and TransArmor PKI Encryption and Tokenization Load Request. For Total Requests, some devices send 8 bytes of length.

**Valid Codes/Values**

Any valid hardware version number

### Element 96: Software Version

- **Character type:** AN · **Maximum length:** 8 bytes
- **Representation:** Fixed length of eight alphanumeric characters
- **Purpose:** Identifies the device’s software version at the customer location.
- **Processing rules:** Required for Totals Request, Table Load Request, and TransArmor PKI Encryption and Tokenization Load Request.

### Element 39: Firmware Version

- **Character type:** AN · **Maximum length:** 8 bytes
- **Representation:** Fixed length of eight alphanumeric characters
- **Purpose:** Identifies the device’s firmware version at the customer location.
- **Processing rules:** Required for Totals Requests, Table Load Request, and TransArmor PKI Encryption and Tokenization Load Request.

### Element 86: Sequence Number

- **Character type:** N · **Maximum length:** 6 bytes
- **Representation:** Fixed length of six digits
- **Purpose:** Note: Identifies a transaction for the life of the transaction. Sequence Number must persist in all the subsequent transactions that are associated with the initial authorization request. In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture identifies the Sequence Number in the transaction’s Authorization Only request.
- **Processing rules:** Each transaction has a unique Sequence Number. It is included on all transaction types, and originates at the device. In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture is the same Sequence Number as in the transaction’s Authorization Only request. In the case of a purchase reversal/void or time-out reversal transaction, the Sequence Number must be the same as in the original transaction request. In the case of an Authorization Only Reversal, this element— present in the original transaction—must be present and identical in the Authorization Only Reversal transaction. (Note: The Authorization Only Reversal transaction enables a merchant to reverse the following credit card transactions within required timeframes: POS Authorization Only [Transaction Type code “3”]); POS Mail/Phone Authorization Only [Transaction Type code “B”]); and CAT Authorization Only [Transaction Type code “5”]). For TransArmor PKI Encryption and Tokenization, this element is included in a new data segment (No. 116 [TransArmor Load Data Segment]).

**Valid Codes/Values**

000001–999999 100000-199999 Note: This limited sequence number range (100000-199999) consists of the only allowed values when using the Multithreaded Dial Protocol Communications header for a CAPK File Load for the ATL105 message format

### Element 20: Currency Code

- **Character type:** N · **Maximum length:** 3 bytes
- **Representation:** Fixed length of three digits
- **Purpose:** Identifies the currency type according to country.
- **Processing rules:** If not sent in Transaction Request, the value defaults to 840 (United States currency). This field will be ignored during transaction processing. Due to totaling restrictions, all transactions for a given merchant will use the currency associated with that merchant.

**Valid Codes/Values**

Please see Appendix L. Valid Currency Codes.

### Element 42: Grand Total

- **Character type:** N · **Maximum length:** 8 bytes
- **Representation:** Fixed length of eight digits with two assumed decimal places
- **Purpose:** Identifies the final total amount in a Totals Response.

**Valid Codes/Values**

00000000–99999999

### Element 13: Card Label

- **Character type:** AN · **Maximum length:** 4 bytes
- **Representation:** Fixed length of four alphanumeric characters; left-aligned and space-filled
- **Purpose:** Identifies the card type in a Totals Response.

**Valid Codes/Values**

Card Type Code MasterCard®/ Visa® CC Amex® TE Discover® Network, Diners Club®, JCB® DS Authorization only AO Debit DB Fleet FL Cash CS Proprietary PR Check CK EBT food stamp EF EBT cash EC Stored value activation/deactivation SV1 Stored value purchase/capture /merchandise return SV2 Stored value card replacement SV3 Stored value recharge SV4 ECA/ TeleCheck® service ECA EBT version of WIC EWIC EBT child care EK

### Element 16: Card Type Total Count

- **Character type:** N · **Maximum length:** 5 bytes
- **Representation:** Fixed length of five digits
- **Purpose:** Identifies the transaction count for a particular card type in a Totals Response.

**Valid Codes/Values**

00001–99999

### Element 15: Card Type Total Amount

- **Character type:** N · **Maximum length:** 8 bytes
- **Representation:** Fixed length of eight digits with two assumed decimal places
- **Purpose:** Identifies the total amount for a particular card type.

**Valid Codes/Values**

00000000–99999999

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which element number does each field carry, and does the payload preserve it?
2. Is the value fixed-length or variable-length, and is the length measured in bytes or characters?
3. Is the field Required, Optional, or Conditional, and what triggers the condition?
4. Does the valid-value set come from Chapter 13 or from an appendix table?
5. Is any value cardholder- or key-sensitive and therefore synthetic-only in test data?

## TBA Dependency Chain

```text
element definition (Chapter 13)
  -> field position in Segment 119
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 119
  -> source: ATL105 2026-3 §12.17 (SEG119-R-008)
  -> a violating payload shall fail validation citing SEG119-R-008
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment119PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG119-R-008`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
