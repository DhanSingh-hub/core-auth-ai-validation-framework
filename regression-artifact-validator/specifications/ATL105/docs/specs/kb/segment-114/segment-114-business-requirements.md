# Segment 114 Business Requirements

**Segment:** 114 — SKU Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.1, 11.2.1, 11.2.2, 11.3.1, 11.9.1, 12.13, 13.2  
**Oracle:** [segment-114-rule-catalog.json](coverage/segment-114-rule-catalog.json) (13 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 114 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 114 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 3 |
| serialization | 3 |
| structure | 2 |
| compatibility | 2 |
| applicability | 1 |
| metadata | 1 |
| lifecycle | 1 |
| **Total** | **13** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG114-001 | structure | Segment 114 is exclusive to the Loyalty Card Transaction Request's Data Section 3; it is not documented as a companion in the Financial Transaction Request (11.1.1), ECA/TeleCheck Service Transaction Request (11.3.1), or CA Public Key File Load Request (11.9.… | The segment's structural position and composition match the rule. | `SEG114-R-001` §11.1.1,11.2.1,11.3.1,11.… | SPEC_DERIVED |
| BR-SEG114-002 | applicability | Segment 114 is optional in every Loyalty Card Transaction Request, always in Field No. 5 of Data Section No. 3, alongside the required Segment 108 in Field No. 4 | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG114-R-002` §11.2.1 | SPEC_DERIVED |
| BR-SEG114-003 | field | Segment Type is fixed value 114 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG114-R-003` §12.13 | SPEC_DERIVED |
| BR-SEG114-004 | field | Segment Length is 4 digits, valid values 0001-1010, representing the segment content length including Segment Type's length and Field Separators | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG114-R-004` §12.13 | SPEC_DERIVED |
| BR-SEG114-005 | serialization | Segment 114 maximum length is 1010 alphanumeric characters (001-1010/a-z/A-Z) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG114-R-005` §12.13 | SPEC_DERIVED |
| BR-SEG114-006 | serialization | Field order is Segment Type, Segment Length, SKU Data (only 3 fields; no field-ordering irregularity exists for this segment, unlike Segment 108) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG114-R-006` §12.13 | SPEC_DERIVED |
| BR-SEG114-007 | serialization | All fields are separated by Field Separators; a Field Separator follows Field No. 3 (the last field); when a field is not populated, still send the Field Separator | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG114-R-007` §12.13 | SPEC_DERIVED |
| BR-SEG114-008 | field | SKU Data (Element 149) is required, alphanumeric, with a maximum length of 1000 characters, and identifies the bar code SKU data | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG114-R-008` §12.13,13.2 | SPEC_DERIVED |
| BR-SEG114-009 | metadata | Segment 114 originates at the device | Documented for traceability; not independently asserted by a validator. | `SEG114-R-009` §12.13 | SPEC_DERIVED |
| BR-SEG114-010 | structure | Segment 114 may repeat within a single message, once per scanned bar code SKU; it is not limited to zero-or-one occurrence | The segment's structural position and composition match the rule. | `SEG114-R-010` §11.2.1 | SPEC_DERIVED |
| BR-SEG114-011 | compatibility | Segment 114 never appears without Segment 108: its only documented context is Data Section 3 of the Loyalty Card Transaction Request, where Segment 108 is required | Only the permitted companion segments / message families carry this segment. | `SEG114-R-011` §11.2.1 | SPEC_DERIVED |
| BR-SEG114-012 | compatibility | The AI-generated relationship 'The Financial Transaction Request transaction includes SKU Data Segment (Data Segment No. 114)' (source_rule_id REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST) is REJECTED as unsupported by Section 11.1.1's Financial Transaction… | Only the permitted companion segments / message families carry this segment. | `SEG114-R-012` §11.1.1 | SPEC_DERIVED |
| BR-SEG114-013 | lifecycle | Segment 114 does not appear in the Loyalty Card Transaction Response, which mirrors the generic Financial Transaction Response layout | Paired messages are present and the correlated values agree. | `SEG114-R-013` §11.2.2 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG114-NEG-001 | `SEG114-R-001` | MUT-010 structural requirement | Validation error citing SEG114-R-001 |
| BR-SEG114-NEG-002 | `SEG114-R-002` | MUT-005 required field omitted | Validation error citing SEG114-R-002 |
| BR-SEG114-NEG-003 | `SEG114-R-003` | MUT-001 wrong fixed value | Validation error citing SEG114-R-003 |
| BR-SEG114-NEG-004 | `SEG114-R-004` | MUT-001 wrong fixed value | Validation error citing SEG114-R-004 |
| BR-SEG114-NEG-005 | `SEG114-R-005` | MUT-003 length violation | Validation error citing SEG114-R-005 |
| BR-SEG114-NEG-006 | `SEG114-R-006` | MUT-001 wrong fixed value | Validation error citing SEG114-R-006 |
| BR-SEG114-NEG-007 | `SEG114-R-007` | MUT-010 structural / separator violation | Validation error citing SEG114-R-007 |
| BR-SEG114-NEG-008 | `SEG114-R-008` | MUT-003 length violation | Validation error citing SEG114-R-008 |
| BR-SEG114-NEG-009 | `SEG114-R-010` | MUT-010 structural requirement | Validation error citing SEG114-R-010 |
| BR-SEG114-NEG-010 | `SEG114-R-011` | MUT-005 required field omitted | Validation error citing SEG114-R-011 |
| BR-SEG114-NEG-011 | `SEG114-R-012` | MUT-010 structural requirement | Validation error citing SEG114-R-012 |
| BR-SEG114-NEG-012 | `SEG114-R-013` | MUT-010 structural requirement | Validation error citing SEG114-R-013 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

_No open provisional items are linked to these rules._

## Implementation traceability

- Rule catalog: [coverage/segment-114-rule-catalog.json](coverage/segment-114-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
