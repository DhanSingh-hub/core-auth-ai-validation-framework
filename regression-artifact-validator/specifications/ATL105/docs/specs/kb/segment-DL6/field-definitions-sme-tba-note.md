# Segment DL6 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** DL6 — Store and Forward Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.47  
**Oracle:** [segment-DL6-rule-catalog.json](coverage/segment-DL6-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment DL6 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL6-R-003` | Start Time and End Time both use Element 166 and format HHMM, defining the window during which store-and-forward processing is blocked | 12.47 | 166 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

_No Chapter 13 element definition could be resolved for the elements referenced by these rules._

## Catalog Notes

- `SEGDL6-R-003` — PROVISIONAL: fields 2 and 3 both cite Element 166 — confirm this is a genuine shared-element-number reuse (like Element 118 elsewhere) rather than a transcription error where field 3 should cite a distinct element number. Pending SME confirmation (SEGDL6-SME-001).

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
  -> field position in Segment DL6
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Start Time and End Time both use Element 166 and format HHMM, defining the window during which store-and-forward processing is blocked
  -> source: ATL105 2026-3 §12.47 (SEGDL6-R-003)
  -> a violating payload shall fail validation citing SEGDL6-R-003
```

## Open Provisional Items

- **P-01** (SEGDL6-R-003): Confirm whether End Time's citation of Element 166 (same as Start Time) is intentional element-number reuse or a transcription error.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL6PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL6-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
