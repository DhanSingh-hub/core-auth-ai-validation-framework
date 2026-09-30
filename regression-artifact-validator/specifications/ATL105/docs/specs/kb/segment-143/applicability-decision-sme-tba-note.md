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
| 224 | Tax Type 1 | AN | 3 bytes | Fixed length of 3 bytes. | Valid values are country dependent. For Canada values are: Value Description GST Goods and Services Tax HST Harmonized Sales Tax PST Provincial Sales… |
| 227 | Tax Type 2 | AN | 3 bytes | Fixed length of 3 bytes. | Valid values are country dependent. For Canada values are: Value Description GST Goods and Services Tax HST Harmonized Sales Tax PST Provincial Sales… |
| 230 | Tax Type 3 | AN | 3 bytes | Fixed length of 3 bytes. | Valid values are country dependent. For Canada values are: Value Description GST Goods and Services Tax HST Harmonized Sales Tax PST Provincial Sales… |

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
