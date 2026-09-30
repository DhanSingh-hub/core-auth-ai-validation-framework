# Segment 151 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 151 — Incomm OTC Market Basket Data (Request) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.36  
**Oracle:** [segment-151-rule-catalog.json](coverage/segment-151-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 151 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG151-R-004` | Segment Type fixed 151, sourced at Host (despite the segment overall 'originating at the device' per the opening statement) — a sourcing inconsistency | 12.36 | 85 | REVIEW_REQUIRED |
| `SEG151-R-005` | Segment Length is 4 digits (not 3), max 2300 characters; Market Basket Data (unnumbered element) consists of one DV dataset followed by up to 10 PI datasets, with full format defined in a separate 'Buypass Incomm Market Basket Data format' document | 12.36 | — | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |

## Catalog Notes

- `SEG151-R-004` — PROVISIONAL: the opening narrative says 'It originates at the device' but the field table lists Source: Host for fields 1-2. Pending SME confirmation (SEG151-SME-002).
- `SEG151-R-005` — PROVISIONAL: the detailed DV/PI dataset format is defined in an external document not available in this KB pass. Pending SME confirmation (SEG151-SME-003) on scope and document location.

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
  -> field position in Segment 151
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type fixed 151, sourced at Host despite the segment overall 'originating at the device' per the opening statement — a sourcing inconsistency
  -> source: ATL105 2026-3 §12.36 (SEG151-R-004)
  -> a violating payload shall fail validation citing SEG151-R-004
```

## Open Provisional Items

- **P-02** (SEG151-R-004): Confirm whether Segment 151's origin is Device (per opening narrative) or Host (per field table Source entries).
- **P-03** (SEG151-R-005): Locate and confirm scope of the 'Buypass Incomm Market Basket Data format' document defining DV/PI dataset structures.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment151PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG151-R-004`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
