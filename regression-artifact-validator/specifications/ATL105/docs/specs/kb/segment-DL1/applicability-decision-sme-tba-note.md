# Segment DL1 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL1 — Merchant Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.42, 12.47  
**Oracle:** [segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment DL1 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL1-R-002` | Segment DL1 uses NO Field Separators; instead it is self-delimited by a Data Type Indicator ('#', field 1) and an End-of-Data Indicator ('~', last field) — a distinct delimiting convention from the numbered segments (85… | 12.42 | 24,34 | SPEC_DERIVED |
| `SEGDL1-R-004` | Number of Card Types (Element 59) identifies how many times field 8 (Card Type) repeats — Card Type may repeat 01-99 times | 12.42 | 59,14 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 24 | Data Type Indicator | AN | 1 byte | Fixed length of one alphanumeric character | Code Description # Identifies the Merchant Data Segment (No. DL1). ! Identifies the Dial String Data Segment (No. DL2). : Identifies the Date and Tim… |
| 34 | End-of-Data Indicator | A | 1 byte | Fixed length of one alpha character | ~ |
| 59 | Number of Card Types | N | 2 bytes | Fixed length of up to 2 digits | 01–99 |
| 14 | Card Type | AN | 3 bytes | Fixed length of three alphanumeric characters | For a list of valid entries, please see Appendix E. Valid Card Type Codes. |

## Catalog Notes

- `SEGDL1-R-002` — Segments DL1-DL6 use this Data-Type/End-of-Data marker convention instead of the Segment Type(85)/Segment Length(84) pair used by numbered segments 100-157.

## SME Reasoning

Ask:

1. Which message families may carry Segment DL1, and in which Data Section?
2. Is Segment DL1 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment DL1 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment DL1 ever legitimate, and how should that be diagnosed?

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
Segment DL1 uses NO Field Separators instead it is self-delimited by a Data Type Indicator ' ', field 1 and an End-of-Data Indicator '~', last field — a distinct delimiting convention from the numbered segments 85/84 Segment Type/Length pair
  -> source: ATL105 2026-3 §12.42 (SEGDL1-R-002)
  -> a violating payload shall fail validation citing SEGDL1-R-002
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL1PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL1-R-002`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
