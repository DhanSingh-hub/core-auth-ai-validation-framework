# Segment DL8 Business Requirements

**Segment:** DL8 — EMV Terminal Floor Limits Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.49  
**Oracle:** [segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment DL8 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment DL8 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| structure | 2 |
| lifecycle | 1 |
| **Total** | **3** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEGDL8-001 | lifecycle | Segment DL8 contains EMV floor limits (as RID) for the terminal; inclusion in a table load depends on a 'Special' flag set at the terminal level; the data is maintained at the BUYPASS Host, and changes to it set the table download flag for all terminals with… | Paired messages are present and the correlated values agree. | `SEGDL8-R-001` §12.49 | SPEC_DERIVED |
| BR-SEGDL8-002 | structure | Data Type Indicator is fixed value '%' (Element 24); Segment Length Indicator (Element 84, 3 digits) is EXCLUSIVE of the Data Type Indicator's length, same hybrid framing convention as Segment DL7 (no End-of-Data Indicator) | The segment's structural position and composition match the rule. | `SEGDL8-R-002` §12.49 | SPEC_DERIVED |
| BR-SEGDL8-003 | structure | Floor Limit Data repeats per RID, for a maximum of 24 RIDs, total variable length up to 624 bytes; each repetition contains RID (233,10, Host-sourced), Stand-in Indicator (234,1, Host-sourced), Floor Limit (235,12, Host-sourced, maximum allowable stand-in val… | The segment's structural position and composition match the rule. | `SEGDL8-R-003` §12.49 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEGDL8-NEG-001 | `SEGDL8-R-001` | MUT-010 structural requirement | Validation error citing SEGDL8-R-001 |
| BR-SEGDL8-NEG-002 | `SEGDL8-R-002` | MUT-001 wrong fixed value | Validation error citing SEGDL8-R-002 |
| BR-SEGDL8-NEG-003 | `SEGDL8-R-003` | MUT-003 length violation | Validation error citing SEGDL8-R-003 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (AI-artifacts, test-data): No dedicated Segment DL8 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
