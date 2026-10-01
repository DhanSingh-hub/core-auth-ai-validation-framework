# Segment 134 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 134 — Transaction Attributes Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.23  
**Oracle:** [segment-134-rule-catalog.json](coverage/segment-134-rule-catalog.json) (7 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 134 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (4)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG134-R-004` | Segment Type is fixed value 134, Segment Length is 4 digits | 12.23 | 85,84 | REVIEW_REQUIRED |
| `SEG134-R-005` | Settlement Type (Element 198) is required, 1 character, valid values D (Dual message), S (Single message), X (Non-traditional Signature Debit — availability must be confirmed with First Data Project/Relationship Manager) | 12.23 | 198 | REVIEW_REQUIRED |
| `SEG134-R-006` | Signature Required (Element 199) is required, 1 character, valid values T (required), F (not required), Space (device software logic determines) | 12.23 | 199 | SPEC_DERIVED |
| `SEG134-R-007` | Receipt Card Description (Element 200) is required, 10 characters, left-justified and space-filled (e.g., 'STAR ') | 12.23 | 200 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | See [Element 85 reference](#element-85-segment-type) |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits — see [Element 84 reference](#element-84-segment-length) | See [Element 84 reference](#element-84-segment-length) |
| 199 | Signature Required | AN | 1 byte | Fixed length 1 alphanumeric character. | See [Element 199 reference](#element-199-signature-required) |
| 200 | Receipt Card Description | AN | 10 bytes | Fixed length 10 alphanumeric characters. | See [Element 200 reference](#element-200-receipt-card-description) |

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

### Element 199: Signature Required

- **Character type:** AN · **Maximum length:** 1 byte
- **Representation:** Fixed length 1 alphanumeric character.
- **Purpose:** Indicates if a signature is required for this transaction.
- **Processing rules:** Required in the Common AID EMV and non-traditional Signature Debit Financial Transaction Response. Note: Please check with your First Data Project Manager or Relationship Manager for availability of Signature Debit functionality.

**Valid Codes/Values**

Following are the values that are returned in the terminal: Value Description T Signature is required F Signature is not required Space Device software logic determines if signature is required

### Element 200: Receipt Card Description

- **Character type:** AN · **Maximum length:** 10 bytes
- **Representation:** Fixed length 10 alphanumeric characters.
- **Purpose:** Contains the text that can be used on the printed receipt to indicate the card type.
- **Processing rules:** Required in the Common AID EMV and non-traditional Signature Debit Financial Transaction Response. Note: Please check with your First Data Project Manager or Relationship Manager for availability of Signature Debit functionality.

**Valid Codes/Values**

A left-justified, space filled, fixed length text value that indicates the card type of the transaction. For example, a Star Northeast authorized transaction would contain the following text “STAR“. This can be used to print in the receipt to indicate the card type.

## Catalog Notes

- `SEG134-R-004` — PROVISIONAL: Segment 134 is not among the seven segments independently confirmed (via Segment 120's cross-reference) to require a 4-digit Segment Length, yet its own field table lists Segment Length as 4 characters. Flag this as an apparent addition to that family pending SME confirmation (SEG134-SME-001).
- `SEG134-R-005` — PROVISIONAL: value X's availability is explicitly conditional on a business relationship configuration, not a pure technical rule. Pending SME confirmation (SEG134-SME-002) on which merchants/configurations have Signature Debit enabled.

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
  -> field position in Segment 134
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 134, Segment Length is 4 digits
  -> source: ATL105 2026-3 §12.23 (SEG134-R-004)
  -> a violating payload shall fail validation citing SEG134-R-004
```

## Open Provisional Items

- **P-01** (SEG134-R-004): Is Segment 134 genuinely an eighth 4-digit-Segment-Length segment (in addition to the previously-confirmed seven: 103,114,115,118,120,130,131), or is this a table transcription inconsistency?
- **P-02** (SEG134-R-005): Which merchant configurations have Non-traditional Signature Debit (Settlement Type X) enabled? Confirm with First Data Project/Relationship Manager per the specification's own note.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment134PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG134-R-004`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
