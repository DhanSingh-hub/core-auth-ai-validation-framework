# Segment 136 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** 136 — Moneris Data (Response) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.25, AppendixV  
**Oracle:** [segment-136-rule-catalog.json](coverage/segment-136-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `partial-approval-*` (a conditional feature with cross-field consequences).

## Core Idea

Conditional rules are where Segment 136 validation most often fails silently: a field that is correct in isolation can be wrong because of the value of another field or another segment.

## Specification-Derived Rules (0)

_The Segment 136 rule catalog contains **no** `dependency/interdependency/conditional` rules. This is recorded as a gap, not an assumption that none exist — confirm with the SME before writing requirements in this area, and do not invent rules to fill it._

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which fields are Conditional, and what exact condition makes each one required?
2. Which values must agree with another field in Segment 136?
3. Which values must agree with Segment 100 or another companion segment?
4. Is the dependency stated in the specification, or inferred and therefore provisional?
5. What is the expected outcome when the dependency is violated — reject, decline, or ignore?

## TBA Dependency Chain

```text
triggering field / segment value
  -> conditional field requirement
  -> cross-field agreement
  -> cross-segment agreement
  -> validator outcome
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

## Open Provisional Items

- **P-01** (SEG136-R-002): Confirm whether Segment Type/Segment Length Source 'Device' is intentional for this Moneris response segment or a transcription error (mirrors the Segment 131 pattern).
- **P-02** (SEG136-R-004): Confirm whether Field Separators also appear between fields 1-2 and 2-3 in Segment 136, or only as a single trailing separator after the whole segment.
- **P-03** (SEG136-R-005): Is Appendix V (Moneris Data layouts) in scope for this training pass?
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
