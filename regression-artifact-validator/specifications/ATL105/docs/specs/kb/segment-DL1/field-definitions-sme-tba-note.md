# Segment DL1 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL1 — Merchant Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.42, 12.47  
**Oracle:** [segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment DL1 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL1-R-003` | Merchant Name(53,24), Store Number(98,16), Address Line 1(3,24), Address Line 2(4,21), Merchant Phone Number(54,13) are all required, Host-sourced fixed-length fields | 12.42 | 53,98,3,4,54 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 53 | Merchant Name | AN | 24 bytes | Fixed length of up to 24 alphanumeric characters | — |
| 98 | Store Number | N | 16 bytes | Fixed length of up to 16 digits | 0000000000000001-9999999999999999 |
| 3 | Address Line 1 | AN | 24 bytes | Fixed length of up to 24 alphanumeric characters | — |
| 4 | Address Line 2 | AN | 21 bytes | Fixed length of 21 alphanumeric characters | Pos. Nos. Valid Entry 1–12 Any valid city name assigned by BUYPASS personnel. 13 Space 14–15 Any valid alphabetical State Code. Please refer to Appen… |
| 54 | Merchant Phone Number | AN | 13 bytes | Fixed length of 13 alphanumeric characters | Any valid telephone number |

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
  -> field position in Segment DL1
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Merchant Name 53,24 , Store Number 98,16 , Address Line 1 3,24 , Address Line 2 4,21 , Merchant Phone Number 54,13 are all required, Host-sourced fixed-length fields
  -> source: ATL105 2026-3 §12.42 (SEGDL1-R-003)
  -> a violating payload shall fail validation citing SEGDL1-R-003
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL1PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL1-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
