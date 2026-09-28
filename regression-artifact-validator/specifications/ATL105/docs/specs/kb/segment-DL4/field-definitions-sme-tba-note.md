# Segment DL4 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL4 — Software Dial Load Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.45  
**Oracle:** [segment-DL4-rule-catalog.json](coverage/segment-DL4-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment DL4 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL4-R-003` | New Software Version(57,8), Software Terminal Record ID(95,13), Software Load Phone Number(91,18), Software Load Request Date(92,6), Software Load Request Time(93,4), Software Load Type(94,1) are all required, Host-sour… | 12.45 | 57,95,91,92,93,94 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 57 | New Software Version | AN | 8 bytes | Fixed length of eight alphanumeric characters |  |
| 95 | Software Terminal Record ID | AN | 13 bytes | Fixed length of 13 alphanumeric characters | — |
| 91 | Software Load Phone Number | AN | 18 bytes | Variable length of up to eighteen alphanumeric characters | — |
| 92 | Software Load Request Date | N | 6 bytes | Fixed length of six digits (MMDDYY) |  |
| 93 | Software Load Request Time | N | 4 bytes | Fixed length of four digits (HHMM) | — |
| 94 | Software Load Type | A | 1 byte | One alphanumeric character | Codes Description F Full application load P Partial application load |

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
  -> field position in Segment DL4
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
New Software Version 57,8 , Software Terminal Record ID 95,13 , Software Load Phone Number 91,18 , Software Load Request Date 92,6 , Software Load Request Time 93,4 , Software Load Type 94,1 are all required, Host-sourced
  -> source: ATL105 2026-3 §12.45 (SEGDL4-R-003)
  -> a violating payload shall fail validation citing SEGDL4-R-003
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL4PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL4-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
