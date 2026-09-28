# Segment 143 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 143 — Tax by Product Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.30  
**Oracle:** [segment-143-rule-catalog.json](coverage/segment-143-rule-catalog.json) (9 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 143 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (5)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG143-R-003` | Segment Type is fixed value 143, Segment Length identifies the segment's length including Segment Type's length and Field Separators, both Device-sourced, each followed by a Field Separator | 12.30 | 85,84 | SPEC_DERIVED |
| `SEG143-R-004` | Number of Products (Element 62) is required, 2 digits, and should match the number of products from the Product Code Data Segment | 12.30 | 62 | SPEC_DERIVED |
| `SEG143-R-006` | Inclusive/Exclusive flag values: 'I' (inclusive to the product amount), 'E' (exclusive), 'N' (this tax not applicable — tax type and amount fields are OMITTED entirely, not merely blank) | 12.30 | 223,226,229 | SPEC_DERIVED |
| `SEG143-R-007` | Tax Type is one of GST/HST/PST for Canadian transactions; a product can have between 0 and 3 of these taxes specified | 12.30 | 224,227,230 | REVIEW_REQUIRED |
| `SEG143-R-009` | A merchant may end a product's tax entry early (before 3 taxes) by using a Field Separator after the last reported tax entry instead of the '\' delimiter | 12.30 | — | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 62 | Number of Products | N | 2 bytes | Fixed length of two digits | 01–10 |
| 223 | Inclusive/Exclusive for Tax 1 | AN | 1 byte | Fixed length of 1 byte. | Value Description I Tax is inclusive E Tax is exclusive N Tax amount not applicable to product |
| 224 | Tax Type 1 | AN | 3 bytes | Fixed length of 3 bytes. | Valid values are country dependent. For Canada values are: Value Description GST Goods and Services Tax HST Harmonized Sales Tax PST Provincial Sales… |
| 227 | Tax Type 2 | AN | 3 bytes | Fixed length of 3 bytes. | Valid values are country dependent. For Canada values are: Value Description GST Goods and Services Tax HST Harmonized Sales Tax PST Provincial Sales… |
| 230 | Tax Type 3 | AN | 3 bytes | Fixed length of 3 bytes. | Valid values are country dependent. For Canada values are: Value Description GST Goods and Services Tax HST Harmonized Sales Tax PST Provincial Sales… |

## Catalog Notes

- `SEG143-R-006` — Genuinely distinctive rule: 'N' doesn't just set a flag — it structurally removes 2 downstream fields from that tax sub-entry, changing the byte-level layout of the rest of the product's entry.
- `SEG143-R-007` — PROVISIONAL: valid Tax Type values are documented only for Canadian transactions; other jurisdictions' valid values are not specified here. Pending SME confirmation (SEG143-SME-001).

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
  -> field position in Segment 143
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 143, Segment Length identifies the segment's length including Segment Type's length and Field Separators, both Device-sourced, each followed by a Field Separator
  -> source: ATL105 2026-3 §12.30 (SEG143-R-003)
  -> a violating payload shall fail validation citing SEG143-R-003
```

## Open Provisional Items

- **P-01** (SEG143-R-007): Are GST/HST/PST the only valid Tax Type values, or do non-Canadian jurisdictions use additional documented values not captured in this section?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment143PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG143-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
