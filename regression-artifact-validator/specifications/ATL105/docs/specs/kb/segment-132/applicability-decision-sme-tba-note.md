# Segment 132 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** 132 — CA Public Key File Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.9.1, 12.22  
**Oracle:** [segment-132-rule-catalog.json](coverage/segment-132-rule-catalog.json) (12 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment 132 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG132-R-001` | Segment 132 belongs to the CA Public Key File Load Request's Data Section 3, alongside optional companions 101, 102, 104, 111 | 11.9.1 | — | REVIEW_REQUIRED |

## Catalog Notes

- `SEG132-R-001` — PROVISIONAL: the exact R/O/C designation for Segment 132 within the CA Public Key File Load Request's own Data Section 3 table was not captured with full certainty during extraction (the segment name strongly implies Required, mirroring Segment 100/108/130's 'anchor segment' pattern, but this should be confirmed rather than assumed). Pending SME confirmation (SEG132-SME-001).

## SME Reasoning

Ask:

1. Which message families may carry Segment 132, and in which Data Section?
2. Is Segment 132 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment 132 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment 132 ever legitimate, and how should that be diagnosed?

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
Segment 132 belongs to the CA Public Key File Load Request's Data Section 3, alongside optional companions 101, 102, 104, 111
  -> source: ATL105 2026-3 §11.9.1 (SEG132-R-001)
  -> a violating payload shall fail validation citing SEG132-R-001
```

## Open Provisional Items

- **P-01** (SEG132-R-001): What is Segment 132's exact R/O/C designation within the CA Public Key File Load Request's Data Section 3 table?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment132PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG132-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
