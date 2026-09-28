# Segment 156 Business Requirements

**Segment:** 156 — EV Charging Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.40  
**Oracle:** [segment-156-rule-catalog.json](coverage/segment-156-rule-catalog.json) (7 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 156 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 156 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 5 |
| applicability | 1 |
| serialization | 1 |
| **Total** | **7** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG156-001 | applicability | Segment 156 is sent only for an EV charging transaction (EV fuel code in Segment 102 Product Code Segment), and is applicable to the Visa card type only | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG156-R-001` §12.40 | SPEC_DERIVED |
| BR-SEG156-002 | serialization | Segment 156 uses NO Field Separators — fixed-length/positional | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG156-R-002` §12.40 | SPEC_DERIVED |
| BR-SEG156-003 | field | EV Transaction Indicator (Table 01) is mandatory for an EV transaction and must be 'Y'; if the EV segment is sent without this indicator set to 'Y', BUYPASS will decline the transaction | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG156-R-003` §12.40 | SPEC_DERIVED |
| BR-SEG156-004 | field | Time-based sub-tables (02 Total Time Plugged In, 03 Total Charging Time, 04 Start Time of Charge, 05 Finish Time of Charge) all use fixed 6-character hhmmss format, hh 00-99, mm/ss 00-59 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG156-R-004` §12.40 | SPEC_DERIVED |
| BR-SEG156-005 | field | Charging Reason Code (Table 07) uses a documented Visa-specific enumeration (010-023, 100, 200); other card networks' valid values are not documented here | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG156-R-005` §12.40 | SPEC_DERIVED |
| BR-SEG156-006 | field | Connector Type (Table 12) uses a documented Visa-defined enumeration (001-003, 100-103, 200) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG156-R-006` §12.40 | SPEC_DERIVED |
| BR-SEG156-007 | field | Additional numeric measurement sub-tables exist: 06 Charging Power Output Capacity (kW), 08 Estimated KM/Miles Added, 09 Carbon Footprint (CO2e grams), 10 Estimated Vehicle KM/Miles Available, 11 Maximum Power Dispensed — all variable-length numeric fields wi… | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG156-R-007` §12.40 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG156-NEG-001 | `SEG156-R-001` | MUT-010 structural requirement | Validation error citing SEG156-R-001 |
| BR-SEG156-NEG-002 | `SEG156-R-002` | MUT-003 length violation | Validation error citing SEG156-R-002 |
| BR-SEG156-NEG-003 | `SEG156-R-003` | MUT-005 required field omitted | Validation error citing SEG156-R-003 |
| BR-SEG156-NEG-004 | `SEG156-R-004` | MUT-002/MUT-006 format or charset violation | Validation error citing SEG156-R-004 |
| BR-SEG156-NEG-005 | `SEG156-R-005` | MUT-004/MUT-008 value outside allowed set | Validation error citing SEG156-R-005 |
| BR-SEG156-NEG-006 | `SEG156-R-006` | MUT-010 structural requirement | Validation error citing SEG156-R-006 |
| BR-SEG156-NEG-007 | `SEG156-R-007` | MUT-003 length violation | Validation error citing SEG156-R-007 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (AI-artifacts, test-data): No dedicated Segment 156 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-156-rule-catalog.json](coverage/segment-156-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
