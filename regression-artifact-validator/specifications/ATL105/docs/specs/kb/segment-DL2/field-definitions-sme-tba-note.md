# Segment DL2 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL2 — Dial String Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.43  
**Oracle:** [segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment DL2 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL2-R-002` | Dial String Type is fixed value 1; Primary Phone Number block (fields 3-7: Redial Count, Access Code [conditional], Pause Indicator [conditional, fixed 'B'], Phone Number, Dial String Terminator fixed 'A') is required | 12.43 | 28,82,1,66,75,27 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 28 | Dial String Type | N | 1 byte | Fixed length of one digit | Value Description 1 Transaction dial strings |
| 82 | Redial Count | N | 1 byte | Fixed length of one digit | 1–3 |
| 1 | Access Code | AN | 12 bytes | Variable length of up to 12 alphanumeric characters | Any number B |
| 66 | Pause Indicator | AN | 1 byte | Fixed length of one alphanumeric character | B |
| 75 | Phone Number | N | 18 bytes | Variable length of up to 18 digits | — |
| 27 | Dial String Terminator | AN | 1 byte | Fixed length of one alphanumeric character | Value Description A Indicates the end of the first dial string in the Dial String Data Segment (Data Segment No. DL2). F Indicates the end of the sec… |

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
  -> field position in Segment DL2
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Dial String Type is fixed value 1 Primary Phone Number block fields 3-7: Redial Count, Access Code conditional , Pause Indicator conditional, fixed 'B' , Phone Number, Dial String Terminator fixed 'A' is required
  -> source: ATL105 2026-3 §12.43 (SEGDL2-R-002)
  -> a violating payload shall fail validation citing SEGDL2-R-002
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL2PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL2-R-002`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
