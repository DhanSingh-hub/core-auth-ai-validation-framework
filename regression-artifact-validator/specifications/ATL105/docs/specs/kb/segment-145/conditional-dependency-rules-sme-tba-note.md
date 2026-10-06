# Segment 145 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** 145 — Enhanced Fleet Request Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.31  
**Oracle:** [segment-145-rule-catalog.json](coverage/segment-145-rule-catalog.json) (8 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `partial-approval-*` (a conditional feature with cross-field consequences).

## Core Idea

Conditional rules are where Segment 145 validation most often fails silently: a field that is correct in isolation can be wrong because of the value of another field or another segment.

## Specification-Derived Rules (0)

_The Segment 145 rule catalog contains **no** `dependency/interdependency/conditional` rules. This is recorded as a gap, not an assumption that none exist — confirm with the SME before writing requirements in this area, and do not invent rules to fill it._

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which fields are Conditional, and what exact condition makes each one required?
2. Which values must agree with another field in Segment 145?
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

- **P-01** (SEG145-R-003): Which authorizer(s) use Enhanced Fleet Data Tables 001, 002, 006, 007, 008 (as opposed to the Prompt-Table-only restriction for Voyager EMV/Visa Fleet 2.0/Comdata/MasterCard Enhanced Fleet EMV)?
- **P-02** (SEG145-R-004): Confirm current production-enablement status of MasterCard Enhanced Fleet EMV as of the training date (2026-09-26) — the spec's 'no earlier than June 2026' notice may now be superseded.
- **P-03** (SEG145-R-006): Confirm whether Table IDs 003/005 being request-absent but response-present (Segment 146) is intentional asymmetry.
- **P-04** (SEG145-R-008): Is full per-token validation for all 5 authorizer-specific prompt-token catalogs required for Item 1 sign-off, or is format-only (existence + max length) validation sufficient for this training pass?
- **P-05** (AI-artifacts, test-data): No dedicated Segment 145 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment145PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Has the SME confirmed that this area genuinely has no rules?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
