# Segment DL3 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL3 — Date and Time Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.44  
**Oracle:** [segment-DL3-rule-catalog.json](coverage/segment-DL3-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment DL3 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL3-R-002` | Day of the Week(25,1), Current Date(21,6), Current Time(22,4), Cut Time(23,4) are all required and Host-sourced | 12.44 | 25,21,22,23 | SPEC_DERIVED |
| `SEGDL3-R-003` | Password (Element 65, 6 characters) is the only Device-sourced field in this segment, identifying the end-of-day function's password — every other field in Segment DL3 is Host-sourced | 12.44 | 65 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 25 | Day of the Week | N | 1 byte | Fixed length of one digit | Code Description 0 Sunday 1 Monday 2 Tuesday 3 Wednesday 4 Thursday 5 Friday 6 Saturday |
| 21 | Current Date | N | 6 bytes | Fixed length of six digits (MMDDYY) | Code Description 01–12 Month of the year 01–31 Day of the month 00–99 Last two digits of the year |
| 22 | Current Time | N | 4 bytes | Fixed length of four digits (HHMM) | Code Description 01–24 Hour of the day 01–60 Minute of the hour |
| 23 | Cut Time | N | 4 bytes | Fixed length 4-digit number (HHMM) | Code Description 01–24 Hour of the day 01–60 Minute of the hour |
| 65 | Password | N | 6 bytes | Variable length of up to six digits | Default password: 123456. |

## Catalog Notes

- `SEGDL3-R-003` — Genuinely distinctive: a single Device-sourced field embedded within an otherwise entirely Host-sourced segment.

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
  -> field position in Segment DL3
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Day of the Week 25,1 , Current Date 21,6 , Current Time 22,4 , Cut Time 23,4 are all required and Host-sourced
  -> source: ATL105 2026-3 §12.44 (SEGDL3-R-002)
  -> a violating payload shall fail validation citing SEGDL3-R-002
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL3PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL3-R-002`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
