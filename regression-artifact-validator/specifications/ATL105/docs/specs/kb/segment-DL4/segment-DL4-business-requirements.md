# Segment DL4 Business Requirements

**Segment:** DL4 — Software Dial Load Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.45  
**Oracle:** [segment-DL4-rule-catalog.json](coverage/segment-DL4-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment DL4 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment DL4 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

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
| BR-SEGDL4-001 | applicability | Segment DL4 is used to download an application from a BUYPASS-supported device management system; vendors who support their own applications do NOT use this segment | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEGDL4-R-001` §12.45 | SPEC_DERIVED |
| BR-SEGDL4-002 | structure | Segment DL4 maximum length is 52 alphanumeric characters; originates at BUYPASS (Host); Data Type Indicator fixed '@' (field 1), End-of-Data Indicator fixed '~' (last field) | The segment's structural position and composition match the rule. | `SEGDL4-R-002` §12.45 | SPEC_DERIVED |
| BR-SEGDL4-003 | field | New Software Version(57,8), Software Terminal Record ID(95,13), Software Load Phone Number(91,18), Software Load Request Date(92,6), Software Load Request Time(93,4), Software Load Type(94,1) are all required, Host-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEGDL4-R-003` §12.45 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEGDL4-NEG-001 | `SEGDL4-R-001` | MUT-010 structural requirement | Validation error citing SEGDL4-R-001 |
| BR-SEGDL4-NEG-002 | `SEGDL4-R-002` | MUT-003 length violation | Validation error citing SEGDL4-R-002 |
| BR-SEGDL4-NEG-003 | `SEGDL4-R-003` | MUT-005 required field omitted | Validation error citing SEGDL4-R-003 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (AI-artifacts, test-data): No dedicated Segment DL4 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-DL4-rule-catalog.json](coverage/segment-DL4-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
