# Segment 114 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** 114 — SKU Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.1, 11.2.1, 11.2.2, 11.3.1, 11.9.1, 12.13, 13.2  
**Oracle:** [segment-114-rule-catalog.json](coverage/segment-114-rule-catalog.json) (13 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `sequence-lifecycle-*` (cross-message correlation).

## Core Idea

Some Segment 114 rules can only be proven across two or more related messages. A single message, or a test-control flag claiming correlation, is description rather than evidence.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG114-R-013` | Segment 114 does not appear in the Loyalty Card Transaction Response, which mirrors the generic Financial Transaction Response layout | 11.2.2 | — | SPEC_DERIVED |

## Catalog Notes

- `SEG114-R-013` — RESOLVED 2026-09-26 (SEG114-SME-003): user confirmed Segment 114 is request-only and never appears in the Loyalty Card Transaction Response.

## SME Reasoning

Ask:

1. Which Segment 114 values must be echoed, preserved, or referenced in a related message?
2. Which message is the original and which is the follow-up or response?
3. Does the test data contain both messages, or only a claim that they correlate?
4. What happens when the follow-up or response is missing, late, or mismatched?
5. Is any correlation value environment-specific (test vs production)?

## TBA Dependency Chain

```text
original message (Segment 114)
  -> host processing
  -> response / follow-up message
  -> echoed or preserved values
  -> correlation assertion
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment 114 does not appear in the Loyalty Card Transaction Response, which mirrors the generic Financial Transaction Response layout
  -> source: ATL105 2026-3 §11.2.2 (SEG114-R-013)
  -> a violating payload shall fail validation citing SEG114-R-013
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

- Is every rule traced to its source anchor (`SEG114-R-013`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
