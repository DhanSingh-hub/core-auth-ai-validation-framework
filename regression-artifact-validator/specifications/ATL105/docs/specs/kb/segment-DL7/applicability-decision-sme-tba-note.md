# Segment DL7 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL7 — Supplemental Terminal Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.48, AppendixW  
**Oracle:** [segment-DL7-rule-catalog.json](coverage/segment-DL7-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment DL7 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (1)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL7-R-001` | Segment DL7 is identified by the special character '^' (Data Type Indicator, Element 24) but, UNLIKE Segments DL1-DL6, does NOT use an End-of-Data Indicator; instead it uses a Segment Length Indicator (Element 84) to fr… | 12.48 | 24,84 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 24 | Data Type Indicator | AN | 1 byte | Fixed length of one alphanumeric character | Code Description # Identifies the Merchant Data Segment (No. DL1). ! Identifies the Dial String Data Segment (No. DL2). : Identifies the Date and Tim… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |

## Catalog Notes

- `SEGDL7-R-001` — A genuinely distinctive hybrid: DL7/DL8 combine the DL-family's Data Type Indicator with the numbered-segment family's Segment Length Indicator, and drop the End-of-Data Indicator entirely.

## SME Reasoning

Ask:

1. Which message families may carry Segment DL7, and in which Data Section?
2. Is Segment DL7 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment DL7 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment DL7 ever legitimate, and how should that be diagnosed?

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
Segment DL7 is identified by the special character '^' Data Type Indicator, Element 24 but, UNLIKE Segments DL1-DL6, does NOT use an End-of-Data Indicator instead it uses a Segment Length Indicator Element 84 to frame the segment, matching the pattern used by numbered segments 100-157 rather than the DL1-DL6 marker convention
  -> source: ATL105 2026-3 §12.48 (SEGDL7-R-001)
  -> a violating payload shall fail validation citing SEGDL7-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL7PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL7-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
