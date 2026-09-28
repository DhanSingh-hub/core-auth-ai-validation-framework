# Segment DL2 Business Requirements

**Segment:** DL2 — Dial String Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.43  
**Oracle:** [segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment DL2 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment DL2 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| structure | 1 |
| field | 1 |
| lifecycle | 1 |
| **Total** | **3** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEGDL2-001 | structure | Segment DL2 maximum length is 69 alphanumeric characters; originates at BUYPASS (Host); Data Type Indicator fixed '!' (field 1), End-of-Data Indicator fixed '~' (last field) | The segment's structural position and composition match the rule. | `SEGDL2-R-001` §12.43 | SPEC_DERIVED |
| BR-SEGDL2-002 | field | Dial String Type is fixed value 1; Primary Phone Number block (fields 3-7: Redial Count, Access Code [conditional], Pause Indicator [conditional, fixed 'B'], Phone Number, Dial String Terminator fixed 'A') is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEGDL2-R-002` §12.43 | SPEC_DERIVED |
| BR-SEGDL2-003 | lifecycle | Secondary Phone Number block (fields 8-11) mirrors the Primary block's structure, terminated by a Dial String Terminator fixed 'F' (field 12); the secondary number is used only after all primary-number retry attempts are exhausted per the Asynchronous Communi… | Paired messages are present and the correlated values agree. | `SEGDL2-R-003` §12.43 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEGDL2-NEG-001 | `SEGDL2-R-001` | MUT-003 length violation | Validation error citing SEGDL2-R-001 |
| BR-SEGDL2-NEG-002 | `SEGDL2-R-002` | MUT-001 wrong fixed value | Validation error citing SEGDL2-R-002 |
| BR-SEGDL2-NEG-003 | `SEGDL2-R-003` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEGDL2-003` (`SEGDL2-R-003`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEGDL2-R-003): Is the Asynchronous Communications Protocol Specifications document (defining primary-to-secondary phone fallback logic) in scope for this training pass?
- **P-02** (AI-artifacts, test-data): No dedicated Segment DL2 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
