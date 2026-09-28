# Segment DL7 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL7 — Supplemental Terminal Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.48, AppendixW  
**Oracle:** [segment-DL7-rule-catalog.json](coverage/segment-DL7-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment DL7 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL7-R-002` | Segment Length Indicator (Element 84) for Segment DL7 is EXCLUSIVE of the Data Type Indicator's length — a distinct counting convention from most numbered segments, which INCLUDE the Segment Type's length in their Segme… | 12.48 | 84 | SPEC_DERIVED |
| `SEGDL7-R-003` | Download Data (Element 232, max 100 characters) is required, in <tag><len><data> format, with detailed layout defined in Appendix W (Download Data Layout) | 12.48,AppendixW | 232 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |

## Catalog Notes

- `SEGDL7-R-003` — PROVISIONAL: Appendix W has not been transcribed into this KB pass. Pending SME confirmation (SEGDL7-SME-001) on scope.

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
  -> field position in Segment DL7
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Length Indicator Element 84 for Segment DL7 is EXCLUSIVE of the Data Type Indicator's length — a distinct counting convention from most numbered segments, which INCLUDE the Segment Type's length in their Segment Length
  -> source: ATL105 2026-3 §12.48 (SEGDL7-R-002)
  -> a violating payload shall fail validation citing SEGDL7-R-002
```

## Open Provisional Items

- **P-01** (SEGDL7-R-003): Is Appendix W (Download Data Layout) in scope for this training pass?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL7PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL7-R-002`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
