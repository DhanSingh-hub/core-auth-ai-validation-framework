# Segment 115 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** 115 — Print Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.9, 11.1.2, 11.1.2-EMV, 11.2.2, 12.14, 13.2  
**Oracle:** [segment-115-rule-catalog.json](coverage/segment-115-rule-catalog.json) (13 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `sequence-lifecycle-*` (cross-message correlation).

## Core Idea

Some Segment 115 rules can only be proven across two or more related messages. A single message, or a test-control flag claiming correlation, is description rather than evidence.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG115-R-012` | Segment 115 is plausibly the wire-format vehicle for 'Loyalty Print Data' returned during a Segment 108 Account Inquiry (Update Code I) or Totals Report (Update Code T) flow, inferred from the shared Loyalty Information Version = 2 trigger condition | 10.9,11.1.2 | — | REVIEW_REQUIRED |
| `SEG115-R-013` | Whether Segment 115 can appear in the Loyalty Card Transaction Response (Section 11.2.2, which states it mirrors the generic Financial Transaction Response) is inferred but not explicitly restated for Segment 115 | 11.2.2 | — | REVIEW_REQUIRED |

## Catalog Notes

- `SEG115-R-012` — PROVISIONAL: this is an inference connecting Section 10.9's narrative ('the host returns the Loyalty Print Data') to Segment 115's Loyalty-Version-2 trigger condition; neither section explicitly cross-references the other. Also relevant to Segment 108's still-open SEG108-SME-003 (whether loyalty data appears in a response). Pending SME confirmation (SEG115-SME-005).
- `SEG115-R-013` — PROVISIONAL: consistent with SEG115-R-012; pending SME confirmation (SEG115-SME-005).

## SME Reasoning

Ask:

1. Which Segment 115 values must be echoed, preserved, or referenced in a related message?
2. Which message is the original and which is the follow-up or response?
3. Does the test data contain both messages, or only a claim that they correlate?
4. What happens when the follow-up or response is missing, late, or mismatched?
5. Is any correlation value environment-specific (test vs production)?

## TBA Dependency Chain

```text
original message (Segment 115)
  -> host processing
  -> response / follow-up message
  -> echoed or preserved values
  -> correlation assertion
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment 115 is plausibly the wire-format vehicle for 'Loyalty Print Data' returned during a Segment 108 Account Inquiry Update Code I or Totals Report Update Code T flow, inferred from the shared Loyalty Information Version = 2 trigger condition
  -> source: ATL105 2026-3 §10.9,11.1.2 (SEG115-R-012)
  -> a violating payload shall fail validation citing SEG115-R-012
```

## Open Provisional Items

- **P-04** (SEG115-R-012, SEG115-R-013): Is Segment 115 confirmed as the wire-format vehicle for Segment 108's 'Loyalty Print Data' (Account Inquiry / Totals Report responses), and can Segment 115 appear in the Loyalty Card Transaction Response? Mirrors Segment 108's still-open SEG108-SME-003.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment115PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG115-R-012`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
