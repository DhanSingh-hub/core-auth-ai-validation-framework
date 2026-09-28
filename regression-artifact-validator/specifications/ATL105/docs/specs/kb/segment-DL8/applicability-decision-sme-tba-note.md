# Segment DL8 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL8 — EMV Terminal Floor Limits Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.49  
**Oracle:** [segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment DL8 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL8-R-002` | Data Type Indicator is fixed value '%' (Element 24); Segment Length Indicator (Element 84, 3 digits) is EXCLUSIVE of the Data Type Indicator's length, same hybrid framing convention as Segment DL7 (no End-of-Data Indica… | 12.49 | 24,84 | SPEC_DERIVED |
| `SEGDL8-R-003` | Floor Limit Data repeats per RID, for a maximum of 24 RIDs, total variable length up to 624 bytes; each repetition contains RID (233,10, Host-sourced), Stand-in Indicator (234,1, Host-sourced), Floor Limit (235,12, Host… | 12.49 | 233,234,235,236 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 24 | Data Type Indicator | AN | 1 byte | Fixed length of one alphanumeric character | Code Description # Identifies the Merchant Data Segment (No. DL1). ! Identifies the Dial String Data Segment (No. DL2). : Identifies the Date and Tim… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 234 | Stand-in Indicator | N | 1 byte | Fixed length of 1 byte. | Value Description 1 No Stand-in 2 Domestic only Stand-in 3 Domestic & Foreign Stand-in |
| 235 | Floor Limit | N | 12 bytes | Fixed length of 12 bytes. | 000000000000-999999999999 |
| 236 | BUYPASS RID Card Type | AN | 3 bytes | Fixed length of 3 bytes. | — |

## Catalog Notes

_No catalog notes are recorded against these rules._

## SME Reasoning

Ask:

1. Which message families may carry Segment DL8, and in which Data Section?
2. Is Segment DL8 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment DL8 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment DL8 ever legitimate, and how should that be diagnosed?

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
Data Type Indicator is fixed value '%' Element 24 Segment Length Indicator Element 84, 3 digits is EXCLUSIVE of the Data Type Indicator's length, same hybrid framing convention as Segment DL7 no End-of-Data Indicator
  -> source: ATL105 2026-3 §12.49 (SEGDL8-R-002)
  -> a violating payload shall fail validation citing SEGDL8-R-002
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL8PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL8-R-002`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
