# Segment 153 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 153 — Network Token Data Request Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.38  
**Oracle:** [segment-153-rule-catalog.json](coverage/segment-153-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 153 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG153-R-002` | Segment Type fixed 153, Segment Length 3 digits (includes Segment Type's length), both Device-sourced | 12.38 | 85,84 | SPEC_DERIVED |
| `SEG153-R-003` | Network Token Data (Element 239) is required, max 999 characters, cataloged as 6 fixed sub-tables: 001 (Network Token, 013-018 bytes), 002 (Expiration Date, fixed 004), 003 (Provisional Fee Indicator, fixed 001), 004 (I… | 12.38 | 239 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 239 | Enhanced Fleet Data | AN | 999 bytes | Variable length of up to 999 alphanumeric characters | Refer to Data Segment 145— Enhanced Fleet Request Format Note: Visa Fleet 2.0 is a new Fleet EMV Standard that harnesses the additional capabilities… |

## Catalog Notes

- `SEG153-R-003` — Note element number 239 is shared with Segments 145/146's Enhanced Fleet Data — same cross-segment element reuse pattern already documented for Element 118.

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
  -> field position in Segment 153
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type fixed 153, Segment Length 3 digits includes Segment Type's length , both Device-sourced
  -> source: ATL105 2026-3 §12.38 (SEG153-R-002)
  -> a violating payload shall fail validation citing SEG153-R-002
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment153PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG153-R-002`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
