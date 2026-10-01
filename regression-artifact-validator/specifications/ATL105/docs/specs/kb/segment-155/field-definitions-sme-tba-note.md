# Segment 155 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 155 — Real Time Account Updater Response Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.39  
**Oracle:** [segment-155-rule-catalog.json](coverage/segment-155-rule-catalog.json) (6 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 155 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG155-R-004` | Card Status (Table 004) valid values: A (New Acct# & Exp Date), E (New Exp Date), Q (Contact Cardholder), C (Closed), U (Unknown Card) | 12.39 | — | SPEC_DERIVED |
| `SEG155-R-005` | Account Updater Result Code (Table 006) is a fixed 6-character code from a documented enumeration (VAU001 through VAU016, non-contiguous — VAU015 absent), applicable only to Visa | 12.39 | — | SPEC_DERIVED |

## Catalog Notes

- `SEG155-R-005` — The specification's own list skips VAU015 — confirm this is intentional (not a transcription gap) (SEG155-SME-001).

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
  -> field position in Segment 155
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Card Status Table 004 valid values: A New Acct & Exp Date , E New Exp Date , Q Contact Cardholder , C Closed , U Unknown Card
  -> source: ATL105 2026-3 §12.39 (SEG155-R-004)
  -> a violating payload shall fail validation citing SEG155-R-004
```

## Open Provisional Items

- **P-01** (SEG155-R-005): Confirm VAU015 is intentionally absent from the Account Updater Result Code enumeration, not a transcription gap.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment155PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG155-R-004`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
