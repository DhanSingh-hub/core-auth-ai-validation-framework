# Segment 149 Business Requirements

**Segment:** 149 — Fuel Price Update Request Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.34  
**Oracle:** [segment-149-rule-catalog.json](coverage/segment-149-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 149 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 149 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

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
| BR-SEG149-001 | applicability | Segment 149 contains data required by the Comdata authorizer for fuel price updates | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG149-R-001` §12.34 | SPEC_DERIVED |
| BR-SEG149-002 | serialization | Field Separators appear between fields 1-2, 2-3, and 3-4; the segment should end with a trailing Field Separator; data fields within field 4 (Price Data) are NOT separated by Field Separators | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG149-R-002` §12.34 | SPEC_DERIVED |
| BR-SEG149-003 | field | Segment Type fixed 149, Segment Length 3 digits, Terminal Identifier (var.), Price Data (var., pipe-delimited tag:value pairs e.g. 'CASS:03.00') all Device-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG149-R-003` §12.34 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG149-NEG-001 | `SEG149-R-001` | MUT-005 required field omitted | Validation error citing SEG149-R-001 |
| BR-SEG149-NEG-002 | `SEG149-R-002` | MUT-010 structural / separator violation | Validation error citing SEG149-R-002 |
| BR-SEG149-NEG-003 | `SEG149-R-003` | MUT-001 wrong fixed value | Validation error citing SEG149-R-003 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (AI-artifacts, test-data): No dedicated Segment 149 AI or Test package was located. Provide one, or approve synthesized fixtures using the worked example already in Section 12.34.

## Implementation traceability

- Rule catalog: [coverage/segment-149-rule-catalog.json](coverage/segment-149-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
