# Segment 145 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** 145 — Enhanced Fleet Request Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.31  
**Oracle:** [segment-145-rule-catalog.json](coverage/segment-145-rule-catalog.json) (8 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment 145 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (3)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG145-R-001` | Segment 145 carries fleet data for enhanced fleet offerings (Wex OTR, Comdata, Voyager EMV, Visa Fleet 2.0, MasterCard Enhanced Fleet EMV); it may hold multiple TLV-encoded sub-segments (sub-segment type, sub-segment length, sub-segment value) | 12.31 | — | SPEC_DERIVED |
| `SEG145-R-002` | Merchants should NOT send Segment 101 (Fleet Data Segment) and Segment 145 (Enhanced Fleet Request Segment) together in the same message | 12.31 | — | SPEC_DERIVED |
| `SEG145-R-003` | Only the Prompt Table sub-segment (Table ID 004) is valid for Voyager EMV, Visa Fleet 2.0, Comdata, and MasterCard Enhanced Fleet EMV transactions — other sub-segment tables (001, 002, 006, 007, 008) are restricted to different authorizer contexts not enumerated by this restriction | 12.31 | — | REVIEW_REQUIRED |

## Catalog Notes

- `SEG145-R-003` — PROVISIONAL: the specification does not explicitly state which authorizer(s) DO use Tables 001/002/006/007/008 (WEX OTR is implied by context but not explicitly cross-referenced here). Pending SME confirmation (SEG145-SME-001).

## SME Reasoning

Ask:

1. Which message families may carry Segment 145, and in which Data Section?
2. Is Segment 145 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment 145 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment 145 ever legitimate, and how should that be diagnosed?

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
Segment 145 carries fleet data for enhanced fleet offerings Wex OTR, Comdata, Voyager EMV, Visa Fleet 2.0, MasterCard Enhanced Fleet EMV it may hold multiple TLV-encoded sub-segments sub-segment type, sub-segment length, sub-segment value
  -> source: ATL105 2026-3 §12.31 (SEG145-R-001)
  -> a violating payload shall fail validation citing SEG145-R-001
```

## Open Provisional Items

- **P-01** (SEG145-R-003): Which authorizer(s) use Enhanced Fleet Data Tables 001, 002, 006, 007, 008 (as opposed to the Prompt-Table-only restriction for Voyager EMV/Visa Fleet 2.0/Comdata/MasterCard Enhanced Fleet EMV)?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment145PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG145-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
