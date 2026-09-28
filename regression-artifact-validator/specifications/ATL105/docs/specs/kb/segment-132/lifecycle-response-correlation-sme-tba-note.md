# Segment 132 Lifecycle, Response and Message Correlation: SME/TBA Learning Note

**Segment:** 132 — CA Public Key File Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.9.1, 12.22  
**Oracle:** [segment-132-rule-catalog.json](coverage/segment-132-rule-catalog.json) (12 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `sequence-lifecycle-*` (cross-message correlation).

## Core Idea

Some Segment 132 rules can only be proven across two or more related messages. A single message, or a test-control flag claiming correlation, is description rather than evidence.

## Specification-Derived Rules (0)

_The Segment 132 rule catalog contains **no** `lifecycle/response/operational` rules. This is recorded as a gap, not an assumption that none exist — confirm with the SME before writing requirements in this area, and do not invent rules to fill it._

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which Segment 132 values must be echoed, preserved, or referenced in a related message?
2. Which message is the original and which is the follow-up or response?
3. Does the test data contain both messages, or only a claim that they correlate?
4. What happens when the follow-up or response is missing, late, or mismatched?
5. Is any correlation value environment-specific (test vs production)?

## TBA Dependency Chain

```text
original message (Segment 132)
  -> host processing
  -> response / follow-up message
  -> echoed or preserved values
  -> correlation assertion
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

## Open Provisional Items

- **P-01** (SEG132-R-001): What is Segment 132's exact R/O/C designation within the CA Public Key File Load Request's Data Section 3 table?
- **P-02** (SEG132-R-004): The documented field lengths sum to 74, not 77. Confirm the exact maximum-length reconciliation (e.g., are 3 separator bytes included in the 77 figure?).
- **P-03** (SEG132-R-010): Is the multi-block CA key file transfer protocol (Block Number, Element 11) in scope for this training pass, or a separate workstream?
- **P-04** (SEG132-R-011): Confirm the Field Separator behavior for fields 4 through 10 of Segment 132 — the specification does not include the usual summary sentence.
- **P-05** (AI-artifacts, test-data): No dedicated Segment 132 AI or Test Team package was located. Provide one, or approve synthesized `.synthetic.json` fixtures.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment132PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Has the SME confirmed that this area genuinely has no rules?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
