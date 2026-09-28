# Segment 141 Business Requirements

**Segment:** 141 — Moneris Day End Batch Close (Request) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.28  
**Oracle:** [segment-141-rule-catalog.json](coverage/segment-141-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 141 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 141 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| applicability | 1 |
| serialization | 1 |
| field | 1 |
| **Total** | **3** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG141-001 | applicability | Segment 141 is used to close out each pay point to Moneris; it should be sent once for each pay point at day end | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG141-R-001` §12.28 | SPEC_DERIVED |
| BR-SEG141-002 | serialization | All fields are separated by Field Separator characters | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG141-R-002` §12.28 | SPEC_DERIVED |
| BR-SEG141-003 | field | Segment Type fixed 141, Segment Length 3 digits, both Device-sourced; all 14 fields (Terminal Identifier, SPDH Header, Moneris Terminal/Merchant ID, Batch Number, Language Indicator, Number/Dollar totals for debits/credits/corrections) are Device-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG141-R-003` §12.28 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG141-NEG-001 | `SEG141-R-001` | MUT-010 structural requirement | Validation error citing SEG141-R-001 |
| BR-SEG141-NEG-002 | `SEG141-R-002` | MUT-002/MUT-006 format or charset violation | Validation error citing SEG141-R-002 |
| BR-SEG141-NEG-003 | `SEG141-R-003` | MUT-001 wrong fixed value | Validation error citing SEG141-R-003 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (AI-artifacts, test-data): No dedicated Segment 141 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-141-rule-catalog.json](coverage/segment-141-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
