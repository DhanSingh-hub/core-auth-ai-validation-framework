# Segment 110 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 110 — Check Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.3.1, 12.9, Appendix D, Appendix I-17, Chapter 13  
**Oracle:** [segment-110-rule-catalog.json](coverage/segment-110-rule-catalog.json) (20 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 110 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (12)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG110-R-004` | Segment Type (Element 85) is fixed value 110 | 12.9 | 85 | SPEC_DERIVED |
| `SEG110-R-005` | Segment Length (Element 84) is required and identifies the segment's serialized length including Segment Type and Field Separators | 12.9 | 84 | SPEC_DERIVED |
| `SEG110-R-008` | MICR Data (Element 122) is required, alphanumeric, maximum 50 bytes, and required on all check transactions; when raw MICR data exceeds 50 bytes, only the first 50 bytes (left to right) are placed here and the remainder is expected in Extended MICR Data (Element 137), whose hosting data segment is not confirmed by the extracted Segment 110 table | 12.9 | 122 | REVIEW_REQUIRED |
| `SEG110-R-009` | Driver's License (Element 123) is conditional, alphanumeric, maximum 40 bytes, and required on all manually entered check transactions; the machine-testable trigger that distinguishes a manually entered check transaction from a MICR-read one is not defined in the extracted Segment 110 table | 12.9 | 123 | REVIEW_REQUIRED |
| `SEG110-R-011` | Date of Birth (Element 125) is conditional, numeric, fixed 8 digits (MMDDYYYY); the extracted Segment 110 table marks it conditional but states no explicit trigger separate from the Driver's License/State Code identification bundle | 12.9 | 125 | REVIEW_REQUIRED |
| `SEG110-R-012` | Check Type (Element 126) is required, alphanumeric, fixed 1 character, and must be one of the documented codes: P (Personal) or C (Company) | 12.9 | 126 | SPEC_DERIVED |
| `SEG110-R-013` | Check Number (Element 127) is conditional, alphanumeric, maximum 8 bytes, and required for manually keyed check data; it shares the same undefined manually-entered trigger as Driver's License | 12.9 | 127 | REVIEW_REQUIRED |
| `SEG110-R-014` | Customer Phone Number (Element 128) is optional, numeric, maximum 10 digits | 12.9 | 128 | SPEC_DERIVED |
| `SEG110-R-015` | Customer Last Name (Element 129) is optional, alphanumeric, maximum 24 bytes | 12.9 | 129 | SPEC_DERIVED |
| `SEG110-R-016` | Check Issue Date (Element 130) is optional, numeric, fixed 8 digits (MMDDYYYY) | 12.9 | 130 | SPEC_DERIVED |
| `SEG110-R-017` | Alternate MICR IND, documented in Section 12.9 as Segment 110 field 12 using element number 239, is optional, fixed 1 byte, and its only documented value is 'Y' (Alternate/RAW TOAD MICR format is being sent); this element number is separately defined in the Chapter 13 master element catalog as 'Enhanced Fleet Data' (999 bytes, used by Segment 145). Per SME decision (2026-09-26), both usages are modeled as distinct, segment-scoped entities (element 239 @ Segment 110 vs. element 239 @ Segment 145); this rule intentionally remains REVIEW_REQUIRED by design, not due to a gap | 12.9 | 239 | REVIEW_REQUIRED |
| `SEG110-R-018` | Two MICR encodings are documented in narrative text: Full MICR Line TAC format (example T999999999A999999999999999999C999999) and Full MICR Line RAW TOAD format (symbol substitution using T, O, A, D); Alternate MICR IND = 'Y' signals RAW TOAD/ALB1 format, but no machine-checkable grammar beyond the example and the 50-byte length limit is given | 12.9 | 122,239 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | See [Element 85 reference](#element-85-segment-type) |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits — see [Element 84 reference](#element-84-segment-length) | See [Element 84 reference](#element-84-segment-length) |
| 122 | MICR Data | AN | 50 bytes | Variable length of up to 50 alphanumeric characters | — |
| 123 | Driver’s License | AN | 40 bytes | Variable length of up to 40 alphanumeric characters |  |
| 125 | Date of Birth | N | 8 bytes | Fixed length, 8-digit number (MMDDYYYY) | — |
| 126 | Check Type | AN | 1 byte | Fixed length of 1 alphanumeric character | Code Description P Personal C Company |
| 127 | Check Number | AN | 8 bytes | Variable length of up to 8 alphanumeric characters | — |
| 128 | Customer Phone Number | N | 10 bytes | Variable length of up to 10 digits | — |
| 129 | Customer Last Name | AN | 24 bytes | Variable length of up to 24 alphanumeric characters |  |
| 130 | Check Issue Date | N | 8 bytes | Fixed length, 8-digit number (MMDDYYYY) | — |
| 239 | Enhanced Fleet Data | AN | 999 bytes | Variable length of up to 999 alphanumeric characters | See [Element 239 reference](#element-239-enhanced-fleet-data) |

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

### Element 122: MICR Data

- **Character type:** AN · **Maximum length:** 50 bytes
- **Representation:** Variable length of up to 50 alphanumeric characters
- **Purpose:** Identifies the data encoded along the bottom of a check.
- **Processing rules:** Required on all check transactions. If MICR data is greater than 50 bytes, include the first 50 bytes (from left to right) in this field. Note: MICR Data is also included in Account Number (Element No. 2) of the Standard Message Data Segment (Segment No. 100). If MICR data > 50 bytes and the prompt code is ECA/ TeleCheck® check service then refer ECA/ TeleCheck® Data Segment (Segment No. 113).

### Element 123: Driver’s License

- **Character type:** AN · **Maximum length:** 40 bytes
- **Representation:** Variable length of up to 40 alphanumeric characters
- **Purpose:** Identifies the driver’s license number used as identification in a check transaction.
- **Processing rules:** Required on all manually entered check transactions.

### Element 125: Date of Birth

- **Character type:** N · **Maximum length:** 8 bytes
- **Representation:** Fixed length, 8-digit number (MMDDYYYY)
- **Purpose:** Identifies the consumer’s date of birth in a check transaction.

### Element 126: Check Type

- **Character type:** AN · **Maximum length:** 1 byte
- **Representation:** Fixed length of 1 alphanumeric character
- **Purpose:** Identifies the type of check used in a check transaction.
- **Processing rules:** Required on all manually entered check transactions.

**Valid Codes/Values**

| Code | Description |
|---|---|
| `P` | Personal |
| `C` | Company |

### Element 127: Check Number

- **Character type:** AN · **Maximum length:** 8 bytes
- **Representation:** Variable length of up to 8 alphanumeric characters
- **Purpose:** Identifies the manually entered check number.
- **Processing rules:** Required on all manually entered check transactions.

### Element 128: Customer Phone Number

- **Character type:** N · **Maximum length:** 10 bytes
- **Representation:** Variable length of up to 10 digits
- **Purpose:** Identifies the phone number of the consumer presenting the check.

### Element 129: Customer Last Name

- **Character type:** AN · **Maximum length:** 24 bytes
- **Representation:** Variable length of up to 24 alphanumeric characters
- **Purpose:** Identifies the last name of the consumer presenting the check.

### Element 130: Check Issue Date

- **Character type:** N · **Maximum length:** 8 bytes
- **Representation:** Fixed length, 8-digit number (MMDDYYYY)
- **Purpose:** Identifies the issue date of the check.

### Element 239: Enhanced Fleet Data

- **Character type:** AN · **Maximum length:** 999 bytes
- **Representation:** Variable length of up to 999 alphanumeric characters
- **Purpose:** Contains fleet data that applies to enhanced fleet offerings such as Wex OTR, Visa Fleet 2.0, MasterCard Enhanced Fleet EMV, and Voyager EMV
- **Processing rules:** Used in the Enhanced Fleet Data Segment (No 145). When Element No. 239 (Enhanced Fleet Data) has a value of ‘004’ (Prompt Data Request), the list of enhanced prompts and the associated information is included here.

**Valid Codes/Values**

Refer to Data Segment 145— Enhanced Fleet Request Format Note: Visa Fleet 2.0 is a new Fleet EMV Standard that harnesses the additional capabilities of EMV at the fuel pump in a solution that is straightforward for fuel merchants to implement. In addition to more driver prompt options, this functionality enables commercial clients to restrict card use at fuel Merchants to certain fuel and non-fuel product categories. For example, cards that can be used to purchase diesel fuel or used for EV charging. Availability Notice: This functionality for MasterCard Enhanced transactions is expected to be enabled in First Data production no earlier than June 2026. Until then, this field is intended for development and certification preparation only and should not be used for live production transactions.

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
  -> field position in Segment 110
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type Element 85 is fixed value 110
  -> source: ATL105 2026-3 §12.9 (SEG110-R-004)
  -> a violating payload shall fail validation citing SEG110-R-004
```

## Open Provisional Items

- **P-02** (SEG110-R-008): Confirm the data segment that carries Extended MICR Data (Element 137) when raw MICR data exceeds 50 bytes, and whether Segment 110 test packages must always be paired with that segment's fixture when the overflow condition applies.
- **P-03** (SEG110-R-009, SEG110-R-013): Define the machine-testable condition (a POS entry-mode flag, prompt code, or other field) that marks a check transaction as 'manually entered' or 'manually keyed', which triggers the Driver's License, State Code, and Check Number requirements.
- **P-04** (SEG110-R-011): Confirm whether Date of Birth is required whenever Driver's License/State Code are present (as BR-130-3/BR-365-4 suggest for Certegy transactions) or is governed by a separate, narrower trigger.
- **P-06** (SEG110-R-018, SEG110-R-020): Section 12.9 gives one TAC/RAW TOAD example, and Appendix I-17 (Segment 111, Variable Information Indicator 024) independently confirms four named MICR Type format codes ('T$','18','09','19') with one example each. Provide a machine-checkable grammar per code, or an approved sample set, sufficient to validate MICR Data content rather than only its length.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

`Segment110PayloadValidator` exists in `src/main`; confirm each rule above has an assertion before marking it covered.

## Review Checklist

- Is every rule traced to its source anchor (`SEG110-R-004`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
