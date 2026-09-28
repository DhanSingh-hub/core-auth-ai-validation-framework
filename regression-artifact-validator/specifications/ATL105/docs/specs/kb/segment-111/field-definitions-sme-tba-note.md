# Segment 111 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 111 — Segment 111  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.10  
**Oracle:** [segment-111-rule-catalog.json](coverage/segment-111-rule-catalog.json) (7 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 111 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (7)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG111-R-001` | Segment Type fixed 111 | 12.10 | 85 | SPEC_DERIVED |
| `SEG111-R-002` | Segment Length format and computation | 12.10 | 84 | SPEC_DERIVED |
| `SEG111-R-003` | Indicator length | 12.10 | 111 | SPEC_DERIVED |
| `SEG111-R-004` | Variable information length | 12.10 | 112 | SPEC_DERIVED |
| `SEG111-R-005` | Repeated section maximum | 12.10 | — | SPEC_DERIVED |
| `SEG111-R-006` | Total maximum | 12.10 | — | SPEC_DERIVED |
| `SEG111-R-007` | Separator serialization | 12.10 | — | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 111 | Variable Information Indicator | AN | 3 bytes | Fixed length of 3 alphanumeric characters | Value Description 001 Universal Product Code (UPC) indicator 002 Program Identifier indicator 003 Address verification service (AVS) indicator 004 Ca… |
| 112 | Variable Information Length | N | 3 bytes | Fixed length of 3 bytes | 001–999 |

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
  -> field position in Segment 111
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type fixed 111
  -> source: ATL105 2026-3 §12.10 (SEG111-R-001)
  -> a violating payload shall fail validation citing SEG111-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

`Segment111PayloadValidator` exists in `src/main`; confirm each rule above has an assertion before marking it covered.

## Review Checklist

- Is every rule traced to its source anchor (`SEG111-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
