# Segment DL3 Business Requirements

**Segment:** DL3 — Date and Time Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.44  
**Oracle:** [segment-DL3-rule-catalog.json](coverage/segment-DL3-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment DL3 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment DL3 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 2 |
| structure | 1 |
| **Total** | **3** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEGDL3-001 | structure | Segment DL3 maximum length is 23 alphanumeric characters; originates at BUYPASS (Host); Data Type Indicator fixed ':' (field 1), End-of-Data Indicator fixed '~' (last field) | The segment's structural position and composition match the rule. | `SEGDL3-R-001` §12.44 | SPEC_DERIVED |
| BR-SEGDL3-002 | field | Day of the Week(25,1), Current Date(21,6), Current Time(22,4), Cut Time(23,4) are all required and Host-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEGDL3-R-002` §12.44 | SPEC_DERIVED |
| BR-SEGDL3-003 | field | Password (Element 65, 6 characters) is the only Device-sourced field in this segment, identifying the end-of-day function's password — every other field in Segment DL3 is Host-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEGDL3-R-003` §12.44 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEGDL3-NEG-001 | `SEGDL3-R-001` | MUT-003 length violation | Validation error citing SEGDL3-R-001 |
| BR-SEGDL3-NEG-002 | `SEGDL3-R-002` | MUT-005 required field omitted | Validation error citing SEGDL3-R-002 |
| BR-SEGDL3-NEG-003 | `SEGDL3-R-003` | MUT-002/MUT-006 format or charset violation | Validation error citing SEGDL3-R-003 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (AI-artifacts, test-data): No dedicated Segment DL3 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-DL3-rule-catalog.json](coverage/segment-DL3-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
