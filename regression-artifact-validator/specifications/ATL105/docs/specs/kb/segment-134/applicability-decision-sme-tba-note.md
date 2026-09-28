# Segment 134 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** 134 — Transaction Attributes Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.23  
**Oracle:** [segment-134-rule-catalog.json](coverage/segment-134-rule-catalog.json) (7 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment 134 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (0)

_The Segment 134 rule catalog contains **no** `applicability/compatibility/structure` rules. This is recorded as a gap, not an assumption that none exist — confirm with the SME before writing requirements in this area, and do not invent rules to fill it._

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which message families may carry Segment 134, and in which Data Section?
2. Is Segment 134 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment 134 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment 134 ever legitimate, and how should that be diagnosed?

## TBA Dependency Chain

```text
transaction / message family
  -> Data Section placement
  -> inclusion condition
  -> companion-segment set
  -> Element 63 (Number of Segments) count where applicable
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

## Open Provisional Items

- **P-01** (SEG134-R-004): Is Segment 134 genuinely an eighth 4-digit-Segment-Length segment (in addition to the previously-confirmed seven: 103,114,115,118,120,130,131), or is this a table transcription inconsistency?
- **P-02** (SEG134-R-005): Which merchant configurations have Non-traditional Signature Debit (Settlement Type X) enabled? Confirm with First Data Project/Relationship Manager per the specification's own note.
- **P-03** (AI-artifacts, test-data): No dedicated Segment 134 AI or Test Team package was located. Provide one, or approve synthesized fixtures.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment134PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Has the SME confirmed that this area genuinely has no rules?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
