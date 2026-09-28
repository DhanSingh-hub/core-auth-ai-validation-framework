# Segment 142 Business Requirements

**Segment:** 142 — Moneris Day End Batch Close (Response) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.29  
**Oracle:** [segment-142-rule-catalog.json](coverage/segment-142-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 142 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 142 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 2 |
| structure | 1 |
| **Total** | **3** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG142-001 | structure | Segment 142 consists of a reiteration of the first 6 fields in the Segment 141 request, followed by a text response from Moneris | The segment's structural position and composition match the rule. | `SEG142-R-001` §12.29 | SPEC_DERIVED |
| BR-SEG142-002 | field | Segment Type and Segment Length are Host-sourced (contrast Segment 140's Device-sourced Segment Type/Length, and Segment 141's fully-Device-sourced request) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG142-R-002` §12.29 | SPEC_DERIVED |
| BR-SEG142-003 | field | Terminal Identifier, Moneris Terminal Identifier, Moneris Merchant ID are Device-sourced echoes; SPDH Header, Batch Number, Response Display are Moneris-sourced; MAC (Element 210, 16 characters) is Moneris-sourced and validates the response at the terminal | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG142-R-003` §12.29 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG142-NEG-001 | `SEG142-R-001` | MUT-010 structural requirement | Validation error citing SEG142-R-001 |
| BR-SEG142-NEG-002 | `SEG142-R-002` | MUT-001 wrong fixed value | Validation error citing SEG142-R-002 |
| BR-SEG142-NEG-003 | `SEG142-R-003` | MUT-002/MUT-006 format or charset violation | Validation error citing SEG142-R-003 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (AI-artifacts, test-data): No dedicated Segment 142 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-142-rule-catalog.json](coverage/segment-142-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
