# Segment 145 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** 145 — Enhanced Fleet Request Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.31  
**Oracle:** [segment-145-rule-catalog.json](coverage/segment-145-rule-catalog.json) (8 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `sequence-lifecycle-*` (cross-message correlation).

## Core Idea

Some Segment 145 rules can only be proven across two or more related messages. A single message, or a test-control flag claiming correlation, is description rather than evidence.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG145-R-004` | MasterCard Enhanced Fleet EMV functionality was, as of the specification's authoring, expected to be enabled in First Data production no earlier than June 2026; until then it is for development/certification preparation… | 12.31 | — | REVIEW_REQUIRED |

## Catalog Notes

- `SEG145-R-004` — PROVISIONAL: confirm current production-enablement status as of the training date — the specification's 'no earlier than June 2026' notice may now be superseded. Pending SME confirmation (SEG145-SME-002).

## SME Reasoning

Ask:

1. Which Segment 145 values must be echoed, preserved, or referenced in a related message?
2. Which message is the original and which is the follow-up or response?
3. Does the test data contain both messages, or only a claim that they correlate?
4. What happens when the follow-up or response is missing, late, or mismatched?
5. Is any correlation value environment-specific (test vs production)?

## TBA Dependency Chain

```text
original message (Segment 145)
  -> host processing
  -> response / follow-up message
  -> echoed or preserved values
  -> correlation assertion
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
MasterCard Enhanced Fleet EMV functionality was, as of the specification's authoring, expected to be enabled in First Data production no earlier than June 2026 until then it is for development/certification preparation only and must not be used in live production transactions
  -> source: ATL105 2026-3 §12.31 (SEG145-R-004)
  -> a violating payload shall fail validation citing SEG145-R-004
```

## Open Provisional Items

- **P-02** (SEG145-R-004): Confirm current production-enablement status of MasterCard Enhanced Fleet EMV as of the training date (2026-09-26) — the spec's 'no earlier than June 2026' notice may now be superseded.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment145PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG145-R-004`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
