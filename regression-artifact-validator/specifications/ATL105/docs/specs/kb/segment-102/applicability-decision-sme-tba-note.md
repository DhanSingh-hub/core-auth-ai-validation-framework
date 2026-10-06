# Segment 102 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** 102 — Segment 102  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.3, 12.30, Appendix F  
**Oracle:** [segment-102-rule-catalog.json](coverage/segment-102-rule-catalog.json) (25 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment 102 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (5)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG102-R-006` | A maximum of ten products is allowed in the segment | 12.3 | — | SPEC_DERIVED |
| `SEG102-R-018` | Segment 102 and Segment 157 are mutually exclusive | 12.3 | — | SPEC_DERIVED |
| `SEG102-R-019` | Segment 102 is not used for Comdata card transactions | 12.3 | — | SPEC_DERIVED |
| `SEG102-R-023` | Segment 102 total length does not exceed 381 characters | 12.3 | — | SPEC_DERIVED |
| `SEG102-R-025` | Segment 143 product order and count match Segment 102 when present | 12.30 | — | REVIEW_REQUIRED |

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which message families may carry Segment 102, and in which Data Section?
2. Is Segment 102 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment 102 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment 102 ever legitimate, and how should that be diagnosed?

## TBA Dependency Chain

```text
transaction / message family
  -> Data Section placement
  -> inclusion condition
  -> companion-segment set
  -> Element 63 (Number of Segments) count where applicable
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
A maximum of ten products is allowed in the segment
  -> source: ATL105 2026-3 §12.3 (SEG102-R-006)
  -> a violating payload shall fail validation citing SEG102-R-006
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment102PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG102-R-006`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
