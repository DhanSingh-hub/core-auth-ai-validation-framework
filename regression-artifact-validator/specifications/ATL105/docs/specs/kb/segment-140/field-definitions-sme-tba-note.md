# Segment 140 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 140 — Moneris Day End Batch Balance (Response) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.27  
**Oracle:** [segment-140-rule-catalog.json](coverage/segment-140-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 140 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG140-R-002` | Segment Type fixed 140, Segment Length 3 digits, both Device-sourced; fields 3 (Terminal Identifier), 5 (Moneris Terminal Identifier), 6 (Moneris Merchant ID) are Device-sourced echoes, while fields 4 (SPDH Header), 7 (Batch Number), 8-14 (Response Display, Number/Dollar totals) are Moneris-sourced | 12.27 | 85,84,102,206,207,208,214,216,217,218,219,220,221,222 | SPEC_DERIVED |
| `SEG140-R-003` | Debit Dollar Value (218), Credit Dollar Value (220), and Corrections Dollar Value (222) use format +/-9(16)v99 (signed, up to 16 integer digits, 2 implied decimal digits) | 12.27 | 218,220,222 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | See [Element 85 reference](#element-85-segment-type) |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits — see [Element 84 reference](#element-84-segment-length) | See [Element 84 reference](#element-84-segment-length) |
| 102 | Terminal Identifier | AN | 22 bytes | Variable length up to 22 alphanumeric characters | See [Element 102 reference](#element-102-terminal-identifier) |
| 206 | SPDH Header | AN | 48 bytes | Fixed length of 48 bytes. | Refer to Appendix V (Moneris Data layouts) for further details. |
| 207 | Moneris Terminal Identifier | AN | 8 bytes | Fixed length of 8 bytes. | — |
| 208 | Moneris Merchant ID | AN | 13 bytes | Fixed length of 13 bytes. | — |
| 214 | Batch Number | AN | 3 bytes | Fixed length of 3 bytes. | Number from 000 to 999 |
| 216 | Response Display | AN | 16 bytes | Fixed length of 16 bytes. | Any alpha-numeric characters |
| 217 | Number of Debits | N | 4 bytes | Fixed length of 4 bytes. | 0001-9999 |
| 218 | Debit Dollar Value | AN | 19 bytes | Fixed length of 19 bytes with leading +/- and two implied decimal places. | (+/-)000000000000000000-(+/-)999999999999999999 |
| 219 | Number of Credits | N | 4 bytes | Fixed length of 4 bytes. | 0001-9999 |
| 220 | Credit Dollar Value | AN | 19 bytes | Fixed length of 19 bytes with leading +/- and two implied decimal places. | (+/-)000000000000000000-(+/-)999999999999999999 |
| 221 | Number of Corrections | N | 4 bytes | Fixed length of 4 bytes. | 0001-9999 |
| 222 | Corrections Dollar Value | AN | 19 bytes | Fixed length of 19 bytes with leading +/- and two implied decimal places. | (+/-)000000000000000000-(+/-)999999999999999999 |

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

### Element 102: Terminal Identifier

- **Character type:** AN · **Maximum length:** 22 bytes
- **Representation:** Variable length up to 22 alphanumeric characters
- **Purpose:** Identifies the transaction device by Device Type, State Code (ANSI® Code), BUYPASS Merchant Number, and Device Number.
- **Processing rules:** Required for all transactions and messages. The Terminal Identifier must be assigned by and set up at BUYPASS. Note: In the case of a Table Load Request, Phone Load Request, Date and Time Load Request, or Software Load Request, the field length is 13 bytes; otherwise, the field length is a maximum of 22 bytes. For the TransArmor PKI Encryption and Tokenization Load Request, the first two characters (Device Type) of this element’s value must be “++” regardless of the actual device type. For the EMV Key Load Request, the first two characters (Device Type) of this element’s value must be “+*” regardless of the actual device type.

**Valid Codes/Values**

Device Type (Pos. Nos. 1–2) Any valid 2-character Device Type assigned by BUYPASS personnel. For the TransArmor PKI Encryption and Tokenization Load Request, this element’s value must be “++” regardless of the actual device type. For the EMV Key Load Request, this element’s value must be “+*” regardless of the actual device type. State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code). Please see Appendix D. Valid State Codes, for a list of valid ANSI® Code entries. BUYPASS Merchant Number (Starts immediately in Pos. No. 5.) Any valid 6- to 15-character BUYPASS Merchant Number assigned by BUYPASS personnel. The Device Number immediately follows the BUYPASS Merchant Number. Note: If using TransArmor, the first 6 digits of the Merchant Number are used in Error! Reference source not found.. P lease refer to the description of Element No. 2 (Account Number) for additional information. Device Number (Starts immediately after the BUYPASS Merchant Number.) Any valid 3-character Device Number assigned by BUYPASS personnel.

### Element 206: SPDH Header

- **Character type:** AN · **Maximum length:** 48 bytes
- **Representation:** Fixed length of 48 bytes.
- **Purpose:** Contains merchant, terminal, and transaction data required for MAC Encryption.
- **Processing rules:** Appears in both Moneris Key Load request and response.

**Valid Codes/Values**

Refer to Appendix V (Moneris Data layouts) for further details.

### Element 207: Moneris Terminal Identifier

- **Character type:** AN · **Maximum length:** 8 bytes
- **Representation:** Fixed length of 8 bytes.
- **Purpose:** Identifies Moneris terminal in request and response messages.
- **Processing rules:** Obtained from Moneris during terminal initialization.

### Element 208: Moneris Merchant ID

- **Character type:** AN · **Maximum length:** 13 bytes
- **Representation:** Fixed length of 13 bytes.
- **Purpose:** Identifies Moneris merchant in request and response messages.
- **Processing rules:** Obtained from Moneris during terminal initialization.

### Element 214: Batch Number

- **Character type:** AN · **Maximum length:** 3 bytes
- **Representation:** Fixed length of 3 bytes.
- **Purpose:** Identifies the batch number of a Moneris batch.
- **Processing rules:** Supplied by the terminal to identify the batch to Moneris.

**Valid Codes/Values**

Number from 000 to 999

### Element 216: Response Display

- **Character type:** AN · **Maximum length:** 16 bytes
- **Representation:** Fixed length of 16 bytes.
- **Purpose:** Indicates text of response to batch close request from Moneris.
- **Processing rules:** Supplied by Moneris in batch close response.

**Valid Codes/Values**

Any alpha-numeric characters

### Element 217: Number of Debits

- **Character type:** N · **Maximum length:** 4 bytes
- **Representation:** Fixed length of 4 bytes.
- **Purpose:** Indicates number of debit transactions in a batch.

**Valid Codes/Values**

0001-9999

### Element 218: Debit Dollar Value

- **Character type:** AN · **Maximum length:** 19 bytes
- **Representation:** Fixed length of 19 bytes with leading +/- and two implied decimal places.
- **Purpose:** Indicates dollar value of all debit transactions in a batch.

**Valid Codes/Values**

(+/-)000000000000000000-(+/-)999999999999999999

### Element 219: Number of Credits

- **Character type:** N · **Maximum length:** 4 bytes
- **Representation:** Fixed length of 4 bytes.
- **Purpose:** Indicates number of credit transactions in a batch.

**Valid Codes/Values**

0001-9999

### Element 220: Credit Dollar Value

- **Character type:** AN · **Maximum length:** 19 bytes
- **Representation:** Fixed length of 19 bytes with leading +/- and two implied decimal places.
- **Purpose:** Indicates dollar value of all credit transactions in a batch.

**Valid Codes/Values**

(+/-)000000000000000000-(+/-)999999999999999999

### Element 221: Number of Corrections

- **Character type:** N · **Maximum length:** 4 bytes
- **Representation:** Fixed length of 4 bytes.
- **Purpose:** Indicates number of correction transactions in a batch.

**Valid Codes/Values**

0001-9999

### Element 222: Corrections Dollar Value

- **Character type:** AN · **Maximum length:** 19 bytes
- **Representation:** Fixed length of 19 bytes with leading +/- and two implied decimal places.
- **Purpose:** Indicates dollar value of all correction transactions in a batch.

**Valid Codes/Values**

(+/-)000000000000000000-(+/-)999999999999999999

## Catalog Notes

- `SEG140-R-002` — 14 fields total — the richest field count of any segment trained in this batch. Field-level sourcing genuinely alternates between Device (echo) and Moneris (new data); do not assume uniform sourcing.

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
  -> field position in Segment 140
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type fixed 140, Segment Length 3 digits, both Device-sourced fields 3 Terminal Identifier , 5 Moneris Terminal Identifier , 6 Moneris Merchant ID are Device-sourced echoes, while fields 4 SPDH Header , 7 Batch Number , 8-14 Response Display, Number/Dollar totals are Moneris-sourced
  -> source: ATL105 2026-3 §12.27 (SEG140-R-002)
  -> a violating payload shall fail validation citing SEG140-R-002
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment140PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG140-R-002`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
