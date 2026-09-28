# Segment DL6 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL6 — Store and Forward Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.47  
**Oracle:** [segment-DL6-rule-catalog.json](coverage/segment-DL6-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment DL6 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (2)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEGDL6-R-001` | Segment DL6 is only sent in a Table Load Response when Segment DL1 (Merchant Data Segment) includes a Card Type value of 173 in Data Element No. 14 (Card Type) | 12.47 | 14 | SPEC_DERIVED |
| `SEGDL6-R-002` | Segment DL6 maximum length is 9 alphanumeric characters; originates at BUYPASS (Host); Data Type Indicator fixed '\' (field 1), End-of-Data Indicator fixed '~' (last field) | 12.47 | — | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 14 | Card Type | AN | 3 bytes | Fixed length of three alphanumeric characters | For a list of valid entries, please see Appendix E. Valid Card Type Codes. |

## Catalog Notes

- `SEGDL6-R-001` — Confirms SEGDL1-R-005.

## SME Reasoning

Ask:

1. Which message families may carry Segment DL6, and in which Data Section?
2. Is Segment DL6 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment DL6 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment DL6 ever legitimate, and how should that be diagnosed?

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
Segment DL6 is only sent in a Table Load Response when Segment DL1 Merchant Data Segment includes a Card Type value of 173 in Data Element No. 14 Card Type
  -> source: ATL105 2026-3 §12.47 (SEGDL6-R-001)
  -> a violating payload shall fail validation citing SEGDL6-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `SegmentDL6PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEGDL6-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
