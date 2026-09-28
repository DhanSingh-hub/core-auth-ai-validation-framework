# Segment 115 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 115 — Print Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.9, 11.1.2, 11.1.2-EMV, 11.2.2, 12.14, 13.2  
**Oracle:** [segment-115-rule-catalog.json](coverage/segment-115-rule-catalog.json) (13 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 115 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (3)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG115-R-003` | Segment Type is fixed value 115 | 12.14 | 85 | SPEC_DERIVED |
| `SEG115-R-004` | Segment Length is 4 digits, one of exactly seven segments across the specification requiring a 4-digit Segment Length (103, 114, 115, 118, 120, 130, 131) | 12.14 | 84 | SPEC_DERIVED |
| `SEG115-R-008` | Print Data (Element 152) is required and identifies the print data being transmitted to the device; per Section 12.14's note this field carries the terms & conditions text for Blackhawk phone activation and recharge rec… | 12.14,13.2 | 152 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 152 | Print Data | ANS | 900 bytes | Variable length of up to 900 alphanumeric and special characters |  |

## Catalog Notes

- `SEG115-R-003` — Unlike Segment 114, Section 12.14 explicitly prints 'Fixed value: 115' for this field — no enforceability ambiguity.
- `SEG115-R-008` — PROVISIONAL: Section 12.14's own field table states length 999, but the internal data-element reference table (13-data-elements.md) states 900 bytes max. Proposed resolution: 999 is authoritative (matches the primary dedicated section and the EMV response layout table). Pending SME confirmation (SEG115-SME-003).

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
  -> field position in Segment 115
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 115
  -> source: ATL105 2026-3 §12.14 (SEG115-R-003)
  -> a violating payload shall fail validation citing SEG115-R-003
```

## Open Provisional Items

- **P-02** (SEG115-R-008): Section 12.14's own field table states Print Data (Element 152) length 999, but the internal 13-data-elements.md reference states 900 bytes max. Which governs?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment115PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG115-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
