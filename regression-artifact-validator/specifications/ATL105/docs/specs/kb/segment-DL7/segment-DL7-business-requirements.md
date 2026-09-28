# Segment DL7 Business Requirements

**Segment:** DL7 — Supplemental Terminal Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.48, AppendixW  
**Oracle:** [segment-DL7-rule-catalog.json](coverage/segment-DL7-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment DL7 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment DL7 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 2 |
| structure | 1 |
| **Total** | **3** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEGDL7-001 | structure | Segment DL7 is identified by the special character '^' (Data Type Indicator, Element 24) but, UNLIKE Segments DL1-DL6, does NOT use an End-of-Data Indicator; instead it uses a Segment Length Indicator (Element 84) to frame the segment, matching the pattern us… | The segment's structural position and composition match the rule. | `SEGDL7-R-001` §12.48 | SPEC_DERIVED |
| BR-SEGDL7-002 | field | Segment Length Indicator (Element 84) for Segment DL7 is EXCLUSIVE of the Data Type Indicator's length — a distinct counting convention from most numbered segments, which INCLUDE the Segment Type's length in their Segment Length | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEGDL7-R-002` §12.48 | SPEC_DERIVED |
| BR-SEGDL7-003 | field | Download Data (Element 232, max 100 characters) is required, in <tag><len><data> format, with detailed layout defined in Appendix W (Download Data Layout) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEGDL7-R-003` §12.48,AppendixW | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEGDL7-NEG-001 | `SEGDL7-R-001` | MUT-003 length violation | Validation error citing SEGDL7-R-001 |
| BR-SEGDL7-NEG-002 | `SEGDL7-R-002` | MUT-001 wrong fixed value | Validation error citing SEGDL7-R-002 |
| BR-SEGDL7-NEG-003 | `SEGDL7-R-003` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEGDL7-003` (`SEGDL7-R-003`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEGDL7-R-003): Is Appendix W (Download Data Layout) in scope for this training pass?
- **P-02** (AI-artifacts, test-data): No dedicated Segment DL7 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-DL7-rule-catalog.json](coverage/segment-DL7-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
