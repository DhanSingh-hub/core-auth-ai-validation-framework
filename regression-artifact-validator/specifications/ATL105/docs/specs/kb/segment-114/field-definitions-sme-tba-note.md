# Segment 114 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 114 — SKU Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.1, 11.2.1, 11.2.2, 11.3.1, 11.9.1, 12.13, 13.2  
**Oracle:** [segment-114-rule-catalog.json](coverage/segment-114-rule-catalog.json) (13 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 114 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (3)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG114-R-003` | Segment Type is fixed value 114 | 12.13 | 85 | SPEC_DERIVED |
| `SEG114-R-004` | Segment Length is 4 digits, valid values 0001-1010, representing the segment content length including Segment Type's length and Field Separators | 12.13 | 84 | SPEC_DERIVED |
| `SEG114-R-008` | SKU Data (Element 149) is required, alphanumeric, with a maximum length of 1000 characters, and identifies the bar code SKU data | 12.13,13.2 | 149 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 149 | SKU Data | AN | 1000 bytes | Variable length of up to 1000 alphanumeric characters |  |

## Catalog Notes

- `SEG114-R-003` — RESOLVED 2026-09-26 (SEG114-SME-004): user confirmed SegmentType == '114' is enforced as a hard rule despite Section 12.13 not printing an explicit 'Fixed value: 114' phrase (contrast Section 12.14 for Segment 115, which does).
- `SEG114-R-004` — Segment 114 is one of exactly seven segments requiring a 4-digit Segment Length (103, 114, 115, 118, 120, 130, 131) per Section 12's cross-reference table; confirmed independently in segment-120's rule catalog.

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
  -> field position in Segment 114
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 114
  -> source: ATL105 2026-3 §12.13 (SEG114-R-003)
  -> a violating payload shall fail validation citing SEG114-R-003
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment114PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG114-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
