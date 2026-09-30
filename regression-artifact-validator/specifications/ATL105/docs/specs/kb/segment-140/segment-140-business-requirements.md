# Segment 140 Business Requirements

**Segment:** 140 — Moneris Day End Batch Balance (Response) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.27  
**Oracle:** [segment-140-rule-catalog.json](coverage/segment-140-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 140 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 140 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 2 |
| structure | 1 |
| **Total** | **3** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG140-001 | structure | Segment 140 consists of a reiteration of the first 6 fields in the Segment 139 request, followed by a text response from Moneris, followed by the total of all debit transactions for this batch | The segment's structural position and composition match the rule. | `SEG140-R-001` §12.27 | SPEC_DERIVED |
| BR-SEG140-002 | field | Segment Type fixed 140, Segment Length 3 digits, both Device-sourced; fields 3 (Terminal Identifier), 5 (Moneris Terminal Identifier), 6 (Moneris Merchant ID) are Device-sourced echoes, while fields 4 (SPDH Header), 7 (Batch Number), 8-14 (Response Display, Number/Dollar totals) are Moneris-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG140-R-002` §12.27 | SPEC_DERIVED |
| BR-SEG140-003 | field | Debit Dollar Value (218), Credit Dollar Value (220), and Corrections Dollar Value (222) use format +/-9(16)v99 (signed, up to 16 integer digits, 2 implied decimal digits) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG140-R-003` §12.27 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG140-NEG-001 | `SEG140-R-001` | MUT-010 structural requirement | Validation error citing SEG140-R-001 |
| BR-SEG140-NEG-002 | `SEG140-R-002` | MUT-001 wrong fixed value | Validation error citing SEG140-R-002 |
| BR-SEG140-NEG-003 | `SEG140-R-003` | MUT-003 length violation | Validation error citing SEG140-R-003 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (AI-artifacts, test-data): No dedicated Segment 140 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-140-rule-catalog.json](coverage/segment-140-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
