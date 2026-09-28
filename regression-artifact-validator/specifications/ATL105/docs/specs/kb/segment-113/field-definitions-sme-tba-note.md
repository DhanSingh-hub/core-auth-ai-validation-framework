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
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 131 | ECA/ TeleCheck® Clerk ID | AN | 6 bytes | Variable length of up to 6 alphanumeric characters | 1–999999 |
| 132 | ECA/ TeleCheck® Product Code | AN | 6 bytes | Variable length of up to 6 alphanumeric characters |  |
| 133 | ECA/ TeleCheck® Phone Number | N | 10 bytes | Variable length of up to 10 digits | — |
| 134 | ECA/ TeleCheck® Trace ID | AN | 22 bytes | Variable length of up to 22 alphanumeric characters | — |
| 135 | Merchant Trace ID | AN | 25 bytes | Variable length of up to 25 alphanumeric characters |  |
| 136 | Denial Record Number | AN | 7 bytes | Variable length of up to 7 alphanumeric characters | — |
| 137 | Extended MICR Data | AN | 65 bytes | Variable length of up to 65 alphanumeric characters | — |

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
