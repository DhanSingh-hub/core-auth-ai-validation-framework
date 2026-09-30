# Segment 155 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** 155 — Real Time Account Updater Response Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.39  
**Oracle:** [segment-155-rule-catalog.json](coverage/segment-155-rule-catalog.json) (6 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment 155 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG155-R-001` | Segment 155 can only be sent when the transaction qualifies for, and uses, the First Data Auth Optimizer service | 12.39 | — | SPEC_DERIVED |
| `SEG155-R-003` | Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard | 12.39 | — | SPEC_DERIVED |

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which message families may carry Segment 155, and in which Data Section?
2. Is Segment 155 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment 155 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment 155 ever legitimate, and how should that be diagnosed?

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
Segment 155 can only be sent when the transaction qualifies for, and uses, the First Data Auth Optimizer service
  -> source: ATL105 2026-3 §12.39 (SEG155-R-001)
  -> a violating payload shall fail validation citing SEG155-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment155PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG155-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
