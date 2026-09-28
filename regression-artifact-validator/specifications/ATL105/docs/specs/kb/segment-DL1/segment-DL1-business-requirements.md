# Segment DL1 Business Requirements

**Segment:** DL1 — Merchant Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.42, 12.47  
**Oracle:** [segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment DL1 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment DL1 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| structure | 2 |
| serialization | 1 |
| field | 1 |
| lifecycle | 1 |
| **Total** | **5** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEGDL1-001 | serialization | Segment DL1 maximum length is 399 alphanumeric characters; originates at BUYPASS (Host) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEGDL1-R-001` §12.42 | SPEC_DERIVED |
| BR-SEGDL1-002 | structure | Segment DL1 uses NO Field Separators; instead it is self-delimited by a Data Type Indicator ('#', field 1) and an End-of-Data Indicator ('~', last field) — a distinct delimiting convention from the numbered segments (85/84 Segment Type/Length pair) | The segment's structural position and composition match the rule. | `SEGDL1-R-002` §12.42 | SPEC_DERIVED |
| BR-SEGDL1-003 | field | Merchant Name(53,24), Store Number(98,16), Address Line 1(3,24), Address Line 2(4,21), Merchant Phone Number(54,13) are all required, Host-sourced fixed-length fields | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEGDL1-R-003` §12.42 | SPEC_DERIVED |
| BR-SEGDL1-004 | structure | Number of Card Types (Element 59) identifies how many times field 8 (Card Type) repeats — Card Type may repeat 01-99 times | The segment's structural position and composition match the rule. | `SEGDL1-R-004` §12.42 | SPEC_DERIVED |
| BR-SEGDL1-005 | lifecycle | A Card Type value of '173' in this segment's repeating Card Type field (Element 14) conditionally triggers Segment DL6 (Store and Forward) in the same Table Load Response | Paired messages are present and the correlated values agree. | `SEGDL1-R-005` §12.42,12.47 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEGDL1-NEG-001 | `SEGDL1-R-001` | MUT-003 length violation | Validation error citing SEGDL1-R-001 |
| BR-SEGDL1-NEG-002 | `SEGDL1-R-002` | MUT-001 wrong fixed value | Validation error citing SEGDL1-R-002 |
| BR-SEGDL1-NEG-003 | `SEGDL1-R-003` | MUT-003 length violation | Validation error citing SEGDL1-R-003 |
| BR-SEGDL1-NEG-004 | `SEGDL1-R-004` | MUT-010 structural requirement | Validation error citing SEGDL1-R-004 |
| BR-SEGDL1-NEG-005 | `SEGDL1-R-005` | MUT-010 structural requirement | Validation error citing SEGDL1-R-005 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (AI-artifacts, test-data): No dedicated Segment DL1 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
