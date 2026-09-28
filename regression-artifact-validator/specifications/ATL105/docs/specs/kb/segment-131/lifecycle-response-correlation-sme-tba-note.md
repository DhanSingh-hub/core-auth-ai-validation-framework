# Segment 131 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** 131 — EMV Response Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.2-EMV, 12.11, 12.20, 12.21  
**Oracle:** [segment-131-rule-catalog.json](coverage/segment-131-rule-catalog.json) (12 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `sequence-lifecycle-*` (cross-message correlation).

## Core Idea

Some Segment 131 rules can only be proven across two or more related messages. A single message, or a test-control flag claiming correlation, is description rather than evidence.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG131-R-007` | CA Public Key File Checksum (Element 187) is required and is explicitly echoed from the Request (Segment 130's CA Public Key File Checksum) | 12.21 | 187 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

_No Chapter 13 element definition could be resolved for the elements referenced by these rules._

## Catalog Notes

- `SEG131-R-007` — CONFIRMS the request/response handshake pattern inferred for Segment 130 (SEG130-R-015) with an explicit citation: 'This is echoed from the Request.'

## SME Reasoning

Ask:

1. Which Segment 131 values must be echoed, preserved, or referenced in a related message?
2. Which message is the original and which is the follow-up or response?
3. Does the test data contain both messages, or only a claim that they correlate?
4. What happens when the follow-up or response is missing, late, or mismatched?
5. Is any correlation value environment-specific (test vs production)?

## TBA Dependency Chain

```text
original message (Segment 131)
  -> host processing
  -> response / follow-up message
  -> echoed or preserved values
  -> correlation assertion
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
CA Public Key File Checksum Element 187 is required and is explicitly echoed from the Request Segment 130's CA Public Key File Checksum
  -> source: ATL105 2026-3 §12.21 (SEG131-R-007)
  -> a violating payload shall fail validation citing SEG131-R-007
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment131PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG131-R-007`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
