# Segment 153 Business Requirements

**Segment:** 153 — Network Token Data Request Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.38  
**Oracle:** [segment-153-rule-catalog.json](coverage/segment-153-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 153 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 153 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 2 |
| structure | 1 |
| **Total** | **3** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG153-001 | structure | Segment 153 contains Network Token Data for the transaction; may hold multiple TLV-encoded sub-segments (standard sub-segment type, length, value) | The segment's structural position and composition match the rule. | `SEG153-R-001` §12.38 | SPEC_DERIVED |
| BR-SEG153-002 | field | Segment Type fixed 153, Segment Length 3 digits (includes Segment Type's length), both Device-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG153-R-002` §12.38 | SPEC_DERIVED |
| BR-SEG153-003 | field | Network Token Data (Element 239) is required, max 999 characters, cataloged as 6 fixed sub-tables: 001 (Network Token, 013-018 bytes), 002 (Expiration Date, fixed 004), 003 (Provisional Fee Indicator, fixed 001), 004 (Input Indicator, fixed 001), 005 (Eligibl… | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG153-R-003` §12.38 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG153-NEG-001 | `SEG153-R-001` | MUT-001 wrong fixed value | Validation error citing SEG153-R-001 |
| BR-SEG153-NEG-002 | `SEG153-R-002` | MUT-001 wrong fixed value | Validation error citing SEG153-R-002 |
| BR-SEG153-NEG-003 | `SEG153-R-003` | MUT-003 length violation | Validation error citing SEG153-R-003 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (AI-artifacts, test-data): No dedicated Segment 153 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-153-rule-catalog.json](coverage/segment-153-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
