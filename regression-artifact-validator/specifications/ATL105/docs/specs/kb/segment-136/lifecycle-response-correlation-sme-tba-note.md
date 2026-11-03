# Segment 136 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** 136 — Moneris Data (Response) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.25, AppendixV  
**Oracle:** [segment-136-rule-catalog.json](coverage/segment-136-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `sequence-lifecycle-*` (cross-message correlation).

## Core Idea

Some Segment 136 rules can only be proven across two or more related messages. A single message, or a test-control flag claiming correlation, is description rather than evidence.

## Specification-Derived Rules (0)

_The Segment 136 rule catalog contains **no** `lifecycle/response/operational` rules. This is recorded as a gap, not an assumption that none exist — confirm with the SME before writing requirements in this area, and do not invent rules to fill it._

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which Segment 136 values must be echoed, preserved, or referenced in a related message?
2. Which message is the original and which is the follow-up or response?
3. Does the test data contain both messages, or only a claim that they correlate?
4. What happens when the follow-up or response is missing, late, or mismatched?
5. Is any correlation value environment-specific (test vs production)?

## TBA Dependency Chain

```text
original message (Segment 136)
  -> host processing
  -> response / follow-up message
  -> echoed or preserved values
  -> correlation assertion
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

## Open Provisional Items

- **P-01** (SEG136-R-002): Confirm whether Segment Type/Segment Length Source 'Device' is intentional for this Moneris response segment or a transcription error (mirrors the Segment 131 pattern).
- **P-02** (SEG136-R-004): Confirm whether Field Separators also appear between fields 1-2 and 2-3 in Segment 136, or only as a single trailing separator after the whole segment.
- **P-04** (AI-artifacts, test-data): No dedicated Segment 136 AI or Test Team package was located. Provide one, or approve synthesized fixtures.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment136PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Has the SME confirmed that this area genuinely has no rules?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
