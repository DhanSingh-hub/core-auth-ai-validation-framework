# Segment 101 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 101 — Fleet Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.1, 12, 12.2  
**Oracle:** [segment-101-rule-catalog.json](coverage/segment-101-rule-catalog.json) (26 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 101 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (16)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG101-R-004` | Segment Type is 101 | 12.2 | 85 | SPEC_DERIVED |
| `SEG101-R-005` | Segment Length is 3 digits representing the segment content length | 12.2 | 84 | SPEC_DERIVED |
| `SEG101-R-009` | Odometer, when populated, is numeric with maximum length 8 | 12.2 | 64 | SPEC_DERIVED |
| `SEG101-R-010` | Vehicle Number, when populated, is alphanumeric with maximum length 10 | 12.2 | 108 | SPEC_DERIVED |
| `SEG101-R-011` | Job Number, when populated, is alphanumeric with maximum length 10 | 12.2 | 47 | SPEC_DERIVED |
| `SEG101-R-012` | Driver/Identification Number, when populated, is alphanumeric with maximum length 10 | 12.2 | 31 | SPEC_DERIVED |
| `SEG101-R-013` | Fleet Employee Number, when populated, is alphanumeric with maximum length 10 | 12.2 | 40 | SPEC_DERIVED |
| `SEG101-R-014` | License #, when populated, is alphanumeric with maximum length 10 | 12.2 | 158 | SPEC_DERIVED |
| `SEG101-R-015` | Job ID, when populated, is alphanumeric with maximum length 12 | 12.2 | 159 | SPEC_DERIVED |
| `SEG101-R-016` | Department #, when populated, is alphanumeric with maximum length 12 | 12.2 | 160 | SPEC_DERIVED |
| `SEG101-R-017` | Customer Data, when populated, is alphanumeric with maximum length 12 | 12.2 | 161 | SPEC_DERIVED |
| `SEG101-R-018` | User ID, when populated, is alphanumeric with maximum length 12 and non-zero | 12.2 | 162 | SPEC_DERIVED |
| `SEG101-R-019` | Vehicle ID#, when populated, is alphanumeric with maximum length 8 | 12.2 | 163 | SPEC_DERIVED |
| `SEG101-R-022` | Fleet Tag format is 3-byte code plus up to 31-byte data payload | 12.2 | — | SPEC_DERIVED |
| `SEG101-R-023` | Fleet Tag 3-byte code must be one of the 17 codes in the fleet-tag code table | 12.2 | — | SPEC_DERIVED |
| `SEG101-R-024` | Fleet Tag data payload conforms to the per-code format | 12.2 | — | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 64 | Odometer | N | 8 bytes | Variable length of one to eight digits | 1–99999999 |
| 108 | Vehicle Number | N | 10 bytes | Variable length of up to 10 digits | Any numeric representation |
| 47 | Job Number | N | 10 bytes | Variable length of up to 10 digits | Any numeric representation |
| 31 | Driver/Identification Number | N | 10 bytes | Variable length of up to 10 digits | Any numeric representation |
| 40 | Fleet Employee Number | N | 10 bytes | Variable length of up to 10 digits | Any valid Fleet Employee Number |
| 158 | License # | AN | 10 bytes | Variable maximum length of 10 alphanumeric characters | Any valid License #. |
| 159 | Job ID | AN | 12 bytes | Variable maximum length of 12 alphanumeric characters | Any valid Job ID. |
| 160 | Department # | AN | 12 bytes | Variable maximum length of 12 alphanumeric characters | Any valid Department #. |
| 161 | Customer Data | AN | 12 bytes | Variable maximum length of 12 alphanumeric characters | Any valid Customer Data. |
| 162 | User ID | AN | 12 bytes | Variable maximum length of 12 alphanumeric characters | Any valid User ID, except zero [0]. |
| 163 | Vehicle ID# | AN | 8 bytes | Variable maximum length of 8 alphanumeric characters | Any valid Vehicle ID#. |

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
  -> field position in Segment 101
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is 101
  -> source: ATL105 2026-3 §12.2 (SEG101-R-004)
  -> a violating payload shall fail validation citing SEG101-R-004
```

## Open Provisional Items

- **P-02** (SEG101-R-024): Confirm DLN carries 'Driver License name' (spec text 'nameation' appears to be a typo).

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

`Segment101PayloadValidator` exists in `src/main`; confirm each rule above has an assertion before marking it covered.

## Review Checklist

- Is every rule traced to its source anchor (`SEG101-R-004`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
