# Segment 143 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** 143 — Tax by Product Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.30  
**Oracle:** [segment-143-rule-catalog.json](coverage/segment-143-rule-catalog.json) (9 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment 143 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG143-R-001` | When a Financial Transaction request includes Segment 143, a Product Code Data Segment (Segment 102) MUST also be present, and each entry in Segment 102 should have a corresponding entry in Segment 143, in the same order | 12.30 | — | SPEC_DERIVED |
| `SEG143-R-005` | Tax by Product Data repeats per product for a maximum of 10 products, total variable length up to 360 bytes; each product entry contains Product Code (77) plus up to 3 tax sub-entries (Inclusive/Exclusive flag, Tax Type, Tax Amount) | 12.30 | 77,223,224,225,226,227,228,229,230,231 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 77 | Product Code | N | 3 bytes | Fixed length of three digits | Please refer to Appendix F. Valid Payment Systems Product Codes, for lists of valid codes. |
| 223 | Inclusive/Exclusive for Tax 1 | AN | 1 byte | Fixed length of 1 byte. | Value Description I Tax is inclusive E Tax is exclusive N Tax amount not applicable to product |
| 224 | Tax Type 1 | AN | 3 bytes | Fixed length of 3 bytes. | See [Element 224 reference](#element-224-tax-type-1) |
| 227 | Tax Type 2 | AN | 3 bytes | Fixed length of 3 bytes. | See [Element 227 reference](#element-227-tax-type-2) |
| 230 | Tax Type 3 | AN | 3 bytes | Fixed length of 3 bytes. | See [Element 230 reference](#element-230-tax-type-3) |

## Chapter 13 Reference: Full Element Definitions

Full, untruncated Chapter 13.2 text for the elements listed above, transcribed from the ATL105 specification extract (`docs/specs/extracted_text.txt`). The table above links here instead of truncating long value lists.

### Element 77: Product Code

- **Character type:** N · **Maximum length:** 3 bytes
- **Representation:** Fixed length of three digits
- **Purpose:** Identifies the type of product in Data Segment No. 102, Product Code Data Segment or Data Segment No. 157, Adjusted Product Code Data Segment. In the case of a Proprietary Load Response (Prompt Code 904), the first instance identifies the type of product which is eligible for discount, and the second instance identifies the corresponding discount product code to send in Data Segment No. 102, Product Code Data Segment.
- **Processing rules:** This data element, along with the following data elements, is repeated for up to a maximum of 10 products in Data Segment No. 102 or Data Segment No. 157: • Unit of Measure (No. 106) • Quantity (No. 81) • Unit Price (No. 107) • Product Amount (No. 76) Note: A unique Product Code must be sent for each type of fuel purchase. When a device uses a Dynamic Card Table, only the product codes defined in the table are valid for transaction processing. In the case of a Proprietary Load Response (Host Discount Data, Prompt Code 904), the value in the second or third positions of the Product Code may contain a wild card character of *. If that occurs, any digit is allowed in that position. For example, if the value is 01*, product codes 010, 011, 012, 013, 014, 015, 016, 017, 018 and 019 are all available for discount.

**Valid Codes/Values**

Please refer to Appendix F. Valid Payment Systems Product Codes, for lists of valid codes.

### Element 223: Inclusive/Exclusive for Tax 1

- **Character type:** AN · **Maximum length:** 1 byte
- **Representation:** Fixed length of 1 byte.
- **Purpose:** Identifies whether this tax is inclusive or exclusive or not applicable to the product.
- **Processing rules:** If this tax amount value is “N”, both tax type and amount should be skipped.

**Valid Codes/Values**

| Value | Description |
|---|---|
| `I` | Tax is inclusive |
| `E` | Tax is exclusive |
| `N` | Tax amount not applicable to product |

### Element 224: Tax Type 1

- **Character type:** AN · **Maximum length:** 3 bytes
- **Representation:** Fixed length of 3 bytes.
- **Purpose:** Identifies the tax type for this tax item.
- **Processing rules:** If the Inclusive/Exclusive flag is “N”, this field is omitted.

**Valid Codes/Values**

Valid values are country dependent. For Canada values are: Value Description GST Goods and Services Tax HST Harmonized Sales Tax PST Provincial Sales Tax QST Quebec Province Sales Tax

### Element 227: Tax Type 2

- **Character type:** AN · **Maximum length:** 3 bytes
- **Representation:** Fixed length of 3 bytes.
- **Purpose:** Identifies the tax type for this tax item.
- **Processing rules:** If the Inclusive/Exclusive flag is “N”, this field is omitted.

**Valid Codes/Values**

Valid values are country dependent. For Canada values are: Value Description GST Goods and Services Tax HST Harmonized Sales Tax PST Provincial Sales Tax QST Quebec Province Sales Tax

### Element 230: Tax Type 3

- **Character type:** AN · **Maximum length:** 3 bytes
- **Representation:** Fixed length of 3 bytes.
- **Purpose:** Identifies the tax type for this tax item.
- **Processing rules:** If the Inclusive/Exclusive flag is “N”, this field is omitted.

**Valid Codes/Values**

Valid values are country dependent. For Canada values are: Value Description GST Goods and Services Tax HST Harmonized Sales Tax PST Provincial Sales Tax QST Quebec Province Sales Tax

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which message families may carry Segment 143, and in which Data Section?
2. Is Segment 143 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment 143 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment 143 ever legitimate, and how should that be diagnosed?

## TBA Dependency Chain

```text
transaction / message family
  -> Data Section placement
  -> inclusion condition
  -> companion-segment set
  -> Element 63 (Number of Segments) count where applicable
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
When a Financial Transaction request includes Segment 143, a Product Code Data Segment Segment 102 MUST also be present, and each entry in Segment 102 should have a corresponding entry in Segment 143, in the same order
  -> source: ATL105 2026-3 §12.30 (SEG143-R-001)
  -> a violating payload shall fail validation citing SEG143-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment143PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG143-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
