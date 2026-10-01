# Segment 113 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 113 — ECA/TeleCheck® Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.8.5, 11.1.1, 11.3.1, 11.3.2, 12, 12.12, 13.2  
**Oracle:** [segment-113-rule-catalog.json](coverage/segment-113-rule-catalog.json) (18 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 113 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (9)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG113-R-003` | Segment Type is 113 | 12.12 | 85 | SPEC_DERIVED |
| `SEG113-R-004` | Segment Length is 3 digits representing the segment content length | 12.12 | 84 | SPEC_DERIVED |
| `SEG113-R-008` | ECA/TeleCheck® Clerk ID, required, is alphanumeric with maximum length 6 | 12.12,13.2 | 131 | SPEC_DERIVED |
| `SEG113-R-009` | ECA/TeleCheck® Product Code, when populated, is alphanumeric with maximum length 6 and has no documented enumeration (free-form, merchant/risk-control defined) | 12.12,13.2 | 132 | SPEC_DERIVED |
| `SEG113-R-010` | ECA/TeleCheck® Phone Number, when populated, is numeric with maximum length 10 | 12.12,13.2 | 133 | SPEC_DERIVED |
| `SEG113-R-011` | ECA/TeleCheck® Trace ID, when populated, is alphanumeric with maximum length 22; required on Void transaction requests | 12.12,13.2 | 134 | SPEC_DERIVED |
| `SEG113-R-012` | Merchant Trace ID, when populated, is alphanumeric with maximum length 25 | 12.12,13.2 | 135 | SPEC_DERIVED |
| `SEG113-R-013` | Denial Record Number, when populated, is alphanumeric with maximum length 7; used to reference a declined ECA/TeleCheck® transaction on Denial Record receipts | 10.8.5,12.12,13.2 | 136 | SPEC_DERIVED |
| `SEG113-R-014` | Extended MICR Data, when populated, is alphanumeric with maximum length 65; must supplement MICR Data (Element 122) in Segment 110 when raw MICR data exceeds 50 bytes | 12.12,13.2 | 137 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | See [Element 85 reference](#element-85-segment-type) |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits — see [Element 84 reference](#element-84-segment-length) | See [Element 84 reference](#element-84-segment-length) |
| 131 | ECA/ TeleCheck® Clerk ID | AN | 6 bytes | Variable length of up to 6 alphanumeric characters | 1–999999 |
| 132 | ECA/ TeleCheck® Product Code | AN | 6 bytes | Variable length of up to 6 alphanumeric characters |  |
| 133 | ECA/ TeleCheck® Phone Number | N | 10 bytes | Variable length of up to 10 digits | — |
| 134 | ECA/ TeleCheck® Trace ID | AN | 22 bytes | Variable length of up to 22 alphanumeric characters | — |
| 135 | Merchant Trace ID | AN | 25 bytes | Variable length of up to 25 alphanumeric characters |  |
| 136 | Denial Record Number | AN | 7 bytes | Variable length of up to 7 alphanumeric characters | — |
| 137 | Extended MICR Data | AN | 65 bytes | Variable length of up to 65 alphanumeric characters | — |

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

### Element 131: ECA/ TeleCheck® Clerk ID

- **Character type:** AN · **Maximum length:** 6 bytes
- **Representation:** Variable length of up to 6 alphanumeric characters
- **Purpose:** Identifies the clerk’s identification number.

**Valid Codes/Values**

1–999999

### Element 132: ECA/ TeleCheck® Product Code

- **Character type:** AN · **Maximum length:** 6 bytes
- **Representation:** Variable length of up to 6 alphanumeric characters
- **Purpose:** Identifies the ECA/ TeleCheck® product code for the merchandise type sold by the merchant and used by ECA/ TeleCheck® Service Risk Control.

### Element 133: ECA/ TeleCheck® Phone Number

- **Character type:** N · **Maximum length:** 10 bytes
- **Representation:** Variable length of up to 10 digits
- **Purpose:** Identifies the phone number of the consumer presenting the check.

### Element 134: ECA/ TeleCheck® Trace ID

- **Character type:** AN · **Maximum length:** 22 bytes
- **Representation:** Variable length of up to 22 alphanumeric characters
- **Purpose:** Identifies the unique trace identification number generated by the ECA/ TeleCheck® service host.

### Element 135: Merchant Trace ID

- **Character type:** AN · **Maximum length:** 25 bytes
- **Representation:** Variable length of up to 25 alphanumeric characters
- **Purpose:** Identifies an optional unique identifier assigned by the merchant, entered at the time of the transaction and later used by the merchant to identify the customer.

### Element 136: Denial Record Number

- **Character type:** AN · **Maximum length:** 7 bytes
- **Representation:** Variable length of up to 7 alphanumeric characters
- **Purpose:** Identifies the record number of a declined ECA/ TeleCheck® transaction.

### Element 137: Extended MICR Data

- **Character type:** AN · **Maximum length:** 65 bytes
- **Representation:** Variable length of up to 65 alphanumeric characters
- **Purpose:** Identifies the data encoded along the bottom of a check.
- **Processing rules:** This field must be populated in addition to MICR Data (Element No. 122) in Check Data Segment (Segment No. 110) when the raw MICR data exceeds the length.

## Catalog Notes

- `SEG113-R-009` — SME-confirmed 2026-09-22 (SEG113-SME-002): no external code table exists for this element; validate type/length only.
- `SEG113-R-011` — SME-confirmed 2026-09-22 (SEG113-SME-003): the Void-requires-Trace-ID business condition is cataloged only, not code-enforced by the JSON payload validator (same treatment as similar business-condition rules in Segments 103/108).
- `SEG113-R-014` — SME-confirmed 2026-09-22 (SEG113-SME-004): this cross-segment (110<->113) dependency is cataloged only, not code-enforced by the single-segment JSON payload validator.

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
  -> field position in Segment 113
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is 113
  -> source: ATL105 2026-3 §12.12 (SEG113-R-003)
  -> a violating payload shall fail validation citing SEG113-R-003
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

`Segment113PayloadValidator` exists in `src/main`; confirm each rule above has an assertion before marking it covered.

## Review Checklist

- Is every rule traced to its source anchor (`SEG113-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
