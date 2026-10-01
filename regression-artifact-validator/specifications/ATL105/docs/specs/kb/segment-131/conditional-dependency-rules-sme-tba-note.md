# Segment 131 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** 131 — EMV Response Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.2-EMV, 12.11, 12.20, 12.21  
**Oracle:** [segment-131-rule-catalog.json](coverage/segment-131-rule-catalog.json) (12 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `partial-approval-*` (a conditional feature with cross-field consequences).

## Core Idea

Conditional rules are where Segment 131 validation most often fails silently: a field that is correct in isolation can be wrong because of the value of another field or another segment.

## Specification-Derived Rules (0)

_The Segment 131 rule catalog contains **no** `dependency/interdependency/conditional` rules. This is recorded as a gap, not an assumption that none exist — confirm with the SME before writing requirements in this area, and do not invent rules to fill it._

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which fields are Conditional, and what exact condition makes each one required?
2. Which values must agree with another field in Segment 131?
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

- **P-01** (SEG131-R-001): Section 12.21 states Segment 131 appears in 'Field No. 4 in Data Section No. 3', but the EMV Financial Transaction Response layout table (also corroborated by the AI Solution Team's own statement) places it at Field No. 17/18/19/20 of Data Section No. 2. Which governs, or do these describe two different message contexts?
- **P-02** (SEG131-R-005): Confirm 3,834 is the sole authoritative maximum length for Segment 131 — an earlier, OCR-uncertain reading of a response layout table suggested a possible shorter figure that could not be confidently transcribed.
- **P-03** (SEG131-R-008): Confirm whether EMV Chip Data Length/EMV Chip Data's 'Source: Device' notation in Segment 131's field table is a transcription artifact (values echoed from the device's request) or intentional, given Segment 131 as a whole originates at BUYPASS.
- **P-04** (AI-artifacts, test-data): No dedicated segment-131 AI Solution BR/TS/TC/TD package or Test Team core-structure package exists (unlike Segment 130). Provide the location of one, or approve continued use of synthesized `.synthetic.json` fixtures.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment131PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Has the SME confirmed that this area genuinely has no rules?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
