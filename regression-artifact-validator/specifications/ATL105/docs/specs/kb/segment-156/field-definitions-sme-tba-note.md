# Segment 156 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 156 — EV Charging Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.40  
**Oracle:** [segment-156-rule-catalog.json](coverage/segment-156-rule-catalog.json) (7 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 156 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (5)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG156-R-003` | EV Transaction Indicator (Table 01) is mandatory for an EV transaction and must be 'Y'; if the EV segment is sent without this indicator set to 'Y', BUYPASS will decline the transaction | 12.40 | — | SPEC_DERIVED |
| `SEG156-R-004` | Time-based sub-tables (02 Total Time Plugged In, 03 Total Charging Time, 04 Start Time of Charge, 05 Finish Time of Charge) all use fixed 6-character hhmmss format, hh 00-99, mm/ss 00-59 | 12.40 | — | SPEC_DERIVED |
| `SEG156-R-005` | Charging Reason Code (Table 07) uses a documented Visa-specific enumeration (010-023, 100, 200); other card networks' valid values are not documented here | 12.40 | — | SPEC_DERIVED |
| `SEG156-R-006` | Connector Type (Table 12) uses a documented Visa-defined enumeration (001-003, 100-103, 200) | 12.40 | — | SPEC_DERIVED |
| `SEG156-R-007` | Additional numeric measurement sub-tables exist: 06 Charging Power Output Capacity (kW), 08 Estimated KM/Miles Added, 09 Carbon Footprint (CO2e grams), 10 Estimated Vehicle KM/Miles Available, 11 Maximum Power Dispensed… | 12.40 | — | SPEC_DERIVED |

## Catalog Notes

- `SEG156-R-004` — hh range 00-99 (not 00-23) — accommodates elapsed-time durations exceeding 24 hours, not just clock times.

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
  -> field position in Segment 156
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
EV Transaction Indicator Table 01 is mandatory for an EV transaction and must be 'Y' if the EV segment is sent without this indicator set to 'Y', BUYPASS will decline the transaction
  -> source: ATL105 2026-3 §12.40 (SEG156-R-003)
  -> a violating payload shall fail validation citing SEG156-R-003
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment156PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG156-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
