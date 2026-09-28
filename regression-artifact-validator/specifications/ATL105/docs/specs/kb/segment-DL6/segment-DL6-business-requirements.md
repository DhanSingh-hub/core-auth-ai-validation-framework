# Segment DL6 Business Requirements

**Segment:** DL6 — Store and Forward Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.47  
**Oracle:** [segment-DL6-rule-catalog.json](coverage/segment-DL6-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment DL6 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment DL6 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| applicability | 1 |
| structure | 1 |
| field | 1 |
| **Total** | **3** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEGDL6-001 | applicability | Segment DL6 is only sent in a Table Load Response when Segment DL1 (Merchant Data Segment) includes a Card Type value of 173 in Data Element No. 14 (Card Type) | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEGDL6-R-001` §12.47 | SPEC_DERIVED |
| BR-SEGDL6-002 | structure | Segment DL6 maximum length is 9 alphanumeric characters; originates at BUYPASS (Host); Data Type Indicator fixed '\' (field 1), End-of-Data Indicator fixed '~' (last field) | The segment's structural position and composition match the rule. | `SEGDL6-R-002` §12.47 | SPEC_DERIVED |
| BR-SEGDL6-003 | field | Start Time and End Time both use Element 166 and format HHMM, defining the window during which store-and-forward processing is blocked | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEGDL6-R-003` §12.47 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEGDL6-NEG-001 | `SEGDL6-R-001` | MUT-010 structural requirement | Validation error citing SEGDL6-R-001 |
| BR-SEGDL6-NEG-002 | `SEGDL6-R-002` | MUT-003 length violation | Validation error citing SEGDL6-R-002 |
| BR-SEGDL6-NEG-003 | `SEGDL6-R-003` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEGDL6-003` (`SEGDL6-R-003`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEGDL6-R-003): Confirm whether End Time's citation of Element 166 (same as Start Time) is intentional element-number reuse or a transcription error.
- **P-02** (AI-artifacts, test-data): No dedicated Segment DL6 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-DL6-rule-catalog.json](coverage/segment-DL6-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
