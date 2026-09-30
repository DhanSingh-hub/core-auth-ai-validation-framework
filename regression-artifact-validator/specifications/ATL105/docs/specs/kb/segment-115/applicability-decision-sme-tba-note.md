# Segment 115 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** 115 — Print Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.9, 11.1.2, 11.1.2-EMV, 11.2.2, 12.14, 13.2  
**Oracle:** [segment-115-rule-catalog.json](coverage/segment-115-rule-catalog.json) (13 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment 115 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (4)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG115-R-001` | Segment 115 is response-only: it appears only in the Financial Transaction Response and EMV Financial Transaction Response, never in any Request message | 12.14,11.1.2 | — | SPEC_DERIVED |
| `SEG115-R-002` | Segment 115 is conditionally included at Field No. 17/18/19 of the Financial Transaction Response's Data Section 2, only when Element 115 (Additional Information Data Segment Flag) indicates it follows AND the request's Loyalty Information Version (Element 150) equals 2 | 11.1.2 | 115,150 | REVIEW_REQUIRED |
| `SEG115-R-010` | Whether 'no other data segments are contained in the Financial Transaction Response' (Section 12.14) means Segment 115 excludes Segment 112, or only means nothing besides 112 and 115 can appear, is unresolved — the Section 11.1.2 layout table shows Segment 112 (Field 16/17/18) and Segment 115 (Field 17/18/19) as two independently-conditional segments in the SAME response, which appears to contradict a strict reading of 'no other data segments' | 12.14,11.1.2 | — | REVIEW_REQUIRED |
| `SEG115-R-011` | In the EMV Financial Transaction Response specifically, Segment 115 may co-occur with Segment 120 (Print Data 2 Segment) at Field 17/18/19, immediately followed by Segment 131 (EMV Response Data Segment) at Field 17/18/19/20 | 11.1.2-EMV | — | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 115 | Additional Information Data Segment Flag | N | 1 byte | Fixed length of one digit | Value Description 0 No Additional Information Data Segment follows. 1 An Additional Information Data Segment follows. |
| 150 | Loyalty Information Version | N | 1 byte | Fixed length of 1 byte | Value Description 1 Loyalty information, Table ID 008 2 Loyalty Information, Table ID 010 |

## Catalog Notes

- `SEG115-R-002` — PROVISIONAL: Element 115's own valid-value table (0=none follows, 1=follows) does not explain how the response distinguishes 'Segment 112 follows' from 'Segment 115 follows' when both are conditionally listed in the same Data Section 2 table. Mirrors Segment 112's still-open SEG112-SME-003. Pending SME confirmation (SEG115-SME-002).
- `SEG115-R-010` — PROVISIONAL: independently corroborated by the AI Solution Team's literal BR-249-4 statement, which read the sentence the same restrictive way. Pending SME confirmation (SEG115-SME-004).

## SME Reasoning

Ask:

1. Which message families may carry Segment 115, and in which Data Section?
2. Is Segment 115 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment 115 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment 115 ever legitimate, and how should that be diagnosed?

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
Segment 115 is response-only: it appears only in the Financial Transaction Response and EMV Financial Transaction Response, never in any Request message
  -> source: ATL105 2026-3 §12.14,11.1.2 (SEG115-R-001)
  -> a violating payload shall fail validation citing SEG115-R-001
```

## Open Provisional Items

- **P-03** (SEG115-R-010): Does 'no other data segments are contained in the Financial Transaction Response' (Section 12.14) mean Segment 115 excludes Segment 112, or only that nothing besides 112 and 115 can appear? The Section 11.1.2 layout table depicts both as independently-conditional in the same response.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment115PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG115-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
