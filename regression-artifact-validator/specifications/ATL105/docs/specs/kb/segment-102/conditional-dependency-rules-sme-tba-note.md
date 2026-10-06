# Segment 102 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** 102 — Segment 102  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.3, 12.30, Appendix F  
**Oracle:** [segment-102-rule-catalog.json](coverage/segment-102-rule-catalog.json) (25 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `partial-approval-*` (a conditional feature with cross-field consequences).

## Core Idea

Conditional rules are where Segment 102 validation most often fails silently: a field that is correct in isolation can be wrong because of the value of another field or another segment.

## Specification-Derived Rules (8)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG102-R-005` | Number of Products matches the actual serialized product entry count | 12.3 | 62 | SPEC_DERIVED |
| `SEG102-R-007` | Fuel products are always the first products in the segment | 12.3 | 77 | REVIEW_REQUIRED |
| `SEG102-R-008` | For EV charging transactions the EV product code is first | 12.3 | 77 | REVIEW_REQUIRED |
| `SEG102-R-009` | A unique Product Code is sent for each type of fuel purchase | 12.3 | 77 | SPEC_DERIVED |
| `SEG102-R-015` | Sum of Product Amounts reconciles with Segment 100 fuel, nonfuel, tax, and cash amounts | 12.3 | 76 | SPEC_DERIVED |
| `SEG102-R-016` | Tax-coded product totals are reflected in Segment 100 Tax Amount | 12.3 | 99 | SPEC_DERIVED |
| `SEG102-R-017` | Fuel merchants send both fuel and nonfuel product data | 12.3 | — | REVIEW_REQUIRED |
| `SEG102-R-024` | Multi-fuel OTR transactions list the primary fuel first | 12.3 | 77 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 62 | Number of Products | N | 2 bytes | Fixed length of two digits | 01–10 |
| 77 | Product Code | N | 3 bytes | Fixed length of three digits | Please refer to Appendix F. Valid Payment Systems Product Codes, for lists of valid codes. |
| 76 | Product Amount | N | 12 bytes | Variable length of up to 12 digits with two assumed decimal places | 1–999999999999 |
| 99 | Tax Amount | N | 8 bytes | Variable length of up to eight digits with two assumed decimal places — see [Element 99 reference](#element-99-tax-amount) | 1–9999999 |

## Chapter 13 Reference: Full Element Definitions

Full, untruncated Chapter 13.2 text for the elements listed above, transcribed from the ATL105 specification extract (`docs/specs/extracted_text.txt`). The table above links here instead of truncating long value lists.

### Element 62: Number of Products

- **Character type:** N · **Maximum length:** 2 bytes
- **Representation:** Fixed length of two digits
- **Purpose:** Identifies the count of products reported for a transaction.
- **Processing rules:** Always precede single digits (1–9) with a zero (01–09).

**Valid Codes/Values**

01–10

### Element 77: Product Code

- **Character type:** N · **Maximum length:** 3 bytes
- **Representation:** Fixed length of three digits
- **Purpose:** Identifies the type of product in Data Segment No. 102, Product Code Data Segment or Data Segment No. 157, Adjusted Product Code Data Segment. In the case of a Proprietary Load Response (Prompt Code 904), the first instance identifies the type of product which is eligible for discount, and the second instance identifies the corresponding discount product code to send in Data Segment No. 102, Product Code Data Segment.
- **Processing rules:** This data element, along with the following data elements, is repeated for up to a maximum of 10 products in Data Segment No. 102 or Data Segment No. 157: • Unit of Measure (No. 106) • Quantity (No. 81) • Unit Price (No. 107) • Product Amount (No. 76) Note: A unique Product Code must be sent for each type of fuel purchase. When a device uses a Dynamic Card Table, only the product codes defined in the table are valid for transaction processing. In the case of a Proprietary Load Response (Host Discount Data, Prompt Code 904), the value in the second or third positions of the Product Code may contain a wild card character of *. If that occurs, any digit is allowed in that position. For example, if the value is 01*, product codes 010, 011, 012, 013, 014, 015, 016, 017, 018 and 019 are all available for discount.

**Valid Codes/Values**

Please refer to Appendix F. Valid Payment Systems Product Codes, for lists of valid codes.

### Element 76: Product Amount

- **Character type:** N · **Maximum length:** 12 bytes
- **Representation:** Variable length of up to 12 digits with two assumed decimal places
- **Purpose:** Identifies the monetary value of product purchased in Data Segment No. 102, Product Code Data Segment or Data Segment No. 157, Adjusted Product Code Data Segment.
- **Processing rules:** The decimal point is implied by the optional Currency Code. The default value has two assumed decimal places. This data element, along with the following data elements, is repeated for up to a maximum of 10 products in Data Segment No. 102 or Data Segment No. 157: • Product Code (No. 77) • Unit of Measure (No. 106) • Quantity (No. 81) • Unit Price (No. 107)

**Valid Codes/Values**

1–999999999999

### Element 99: Tax Amount

- **Character type:** N · **Maximum length:** 8 bytes
- **Representation:** Variable length of up to eight digits with two assumed decimal places Note: A maximum length of 8 bytes is allowed for American Express cards only. A maximum length of 7 bytes is allowed for all other card types.
- **Purpose:** Identifies the transaction tax amount in Data Segment No. 100, Standard Message Data Segment.
- **Processing rules:** Note the difference between this data element and Element No. 74, PC Tax Amount. Note: Sales tax is applied to EBT Cash Benefits only. Sales tax is not applicable to EBT Food Stamps.

**Valid Codes/Values**

1–9999999

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which fields are Conditional, and what exact condition makes each one required?
2. Which values must agree with another field in Segment 102?
3. Which values must agree with Segment 100 or another companion segment?
4. Is the dependency stated in the specification, or inferred and therefore provisional?
5. What is the expected outcome when the dependency is violated — reject, decline, or ignore?

## TBA Dependency Chain

```text
triggering field / segment value
  -> conditional field requirement
  -> cross-field agreement
  -> cross-segment agreement
  -> validator outcome
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Number of Products matches the actual serialized product entry count
  -> source: ATL105 2026-3 §12.3 (SEG102-R-005)
  -> a violating payload shall fail validation citing SEG102-R-005
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment102PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG102-R-005`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
