# Segment 119 Business Requirements

**Segment:** 119 — Totals with Proprietary Data Load Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.4, 11.4.1.2, 12.17, 9  
**Oracle:** [segment-119-rule-catalog.json](coverage/segment-119-rule-catalog.json) (36 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 119 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 119 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 19 |
| serialization | 4 |
| structure | 3 |
| response | 2 |
| dependency | 2 |
| lifecycle | 2 |
| applicability | 1 |
| compatibility | 1 |
| operational | 1 |
| test-data | 1 |
| **Total** | **36** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG119-001 | structure | Totals-with-proprietary-load request contains Data Sections 1 and 3 but not Data Section 2 | The segment's structural position and composition match the rule. | `SEG119-R-001` §11.4.1.2 | SPEC_DERIVED |
| BR-SEG119-002 | applicability | Segment 119 is sent only when the transaction requires totals data | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG119-R-002` §11.4.1.2 | SPEC_DERIVED |
| BR-SEG119-003 | structure | Segment 119 appears in Field No. 3 of Data Section No. 3 | The segment's structural position and composition match the rule. | `SEG119-R-003` §12.17 | SPEC_DERIVED |
| BR-SEG119-004 | serialization | Segment 119 maximum length is 493 alphanumeric characters | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG119-R-004` §12.17 | SPEC_DERIVED |
| BR-SEG119-005 | serialization | Fields 1-17 use Field Separators, including after Field 17 | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG119-R-005` §12.17 | SPEC_DERIVED |
| BR-SEG119-006 | serialization | Empty fields in fields 1-17 retain their Field Separators | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG119-R-006` §12.17 | SPEC_DERIVED |
| BR-SEG119-007 | serialization | Fields 18-20 are concatenated without Field Separators and a separator follows field 20 | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG119-R-007` §12.17 | SPEC_DERIVED |
| BR-SEG119-008 | field | Segment Type is fixed value 119 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-008` §12.17 | SPEC_DERIVED |
| BR-SEG119-009 | field | Segment Length is required and includes Segment Type and Field Separators | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-009` §12.17 | SPEC_DERIVED |
| BR-SEG119-010 | field | Information Byte is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-010` §12.17 | SPEC_DERIVED |
| BR-SEG119-011 | field | Terminal Identifier is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-011` §12.17 | SPEC_DERIVED |
| BR-SEG119-012 | field | Prompt Code is fixed value 990 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-012` §12.17 | SPEC_DERIVED |
| BR-SEG119-013 | field | Employee Number is conditional | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-013` §12.17 | SPEC_DERIVED |
| BR-SEG119-014 | field | Password is conditional | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-014` §12.17 | SPEC_DERIVED |
| BR-SEG119-015 | field | Totals Date is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-015` §12.17 | SPEC_DERIVED |
| BR-SEG119-016 | field | Hardware Version is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-016` §12.17 | SPEC_DERIVED |
| BR-SEG119-017 | field | Software Version is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-017` §12.17 | SPEC_DERIVED |
| BR-SEG119-018 | field | Firmware Version is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-018` §12.17 | SPEC_DERIVED |
| BR-SEG119-019 | field | Sequence Number is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-019` §12.17 | SPEC_DERIVED |
| BR-SEG119-020 | field | Device Card Table Version is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-020` §12.17 | SPEC_DERIVED |
| BR-SEG119-021 | field | Host Discount Timestamp is required and uses CCYYMMDDHHMM | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-021` §12.17 | SPEC_DERIVED |
| BR-SEG119-022 | field | Currency Code is optional | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-022` §12.17 | SPEC_DERIVED |
| BR-SEG119-023 | field | Grand Total is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-023` §12.17 | SPEC_DERIVED |
| BR-SEG119-024 | field | Card Label is required for each card bucket | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-024` §12.17 | SPEC_DERIVED |
| BR-SEG119-025 | field | Card Type Total Count is required for each card bucket | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-025` §12.17 | SPEC_DERIVED |
| BR-SEG119-026 | field | Card Type Total Amount is required for each card bucket | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-026` §12.17 | SPEC_DERIVED |
| BR-SEG119-027 | structure | Card bucket fields 17-19 repeat up to 20 times in documented order | The segment's structural position and composition match the rule. | `SEG119-R-027` §12.17 | SPEC_DERIVED |
| BR-SEG119-028 | response | Approved responses include card types 1 through 15 | The response carries the stated values in the stated positions. | `SEG119-R-028` §12.17 | SPEC_DERIVED |
| BR-SEG119-029 | response | Card types 16 through 20 are included only when data occurs | The response carries the stated values in the stated positions. | `SEG119-R-029` §12.17 | SPEC_DERIVED |
| BR-SEG119-030 | dependency | Non-financial card categories marked with * are excluded from Grand Total dollar aggregation | The dependent value agrees with its controlling field or segment. | `SEG119-R-030` §12.17 | SPEC_DERIVED |
| BR-SEG119-031 | compatibility | Segment 119 selection requires an approved proprietary-data-load selection policy | Only the permitted companion segments / message families carry this segment. | `SEG119-R-031` §11.4.1.2 | SPEC_DERIVED |
| BR-SEG119-032 | lifecycle | Request and response correlation uses the approved totals lifecycle policy | Paired messages are present and the correlated values agree. | `SEG119-R-032` §11.4 | SPEC_DERIVED |
| BR-SEG119-033 | dependency | Totals aggregation and reconciliation policy is approved before certification | The dependent value agrees with its controlling field or segment. | `SEG119-R-033` §12.17 | SPEC_DERIVED |
| BR-SEG119-034 | lifecycle | Retry and duplicate Totals Request behavior is approved before certification | Paired messages are present and the correlated values agree. | `SEG119-R-034` §11.4 | SPEC_DERIVED |
| BR-SEG119-035 | operational | Merchant settlement cutoff and timezone behavior is approved before certification | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-035` §9,11.4 | SPEC_DERIVED |
| BR-SEG119-036 | test-data | Segment 119 synthetic fixtures are replaced or explicitly accepted for training | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG119-R-036` §11.4.1.2,12.17 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG119-NEG-001 | `SEG119-R-001` | MUT-010 structural requirement | Validation error citing SEG119-R-001 |
| BR-SEG119-NEG-002 | `SEG119-R-002` | MUT-010 structural requirement | Validation error citing SEG119-R-002 |
| BR-SEG119-NEG-003 | `SEG119-R-003` | MUT-010 structural requirement | Validation error citing SEG119-R-003 |
| BR-SEG119-NEG-004 | `SEG119-R-004` | MUT-003 length violation | Validation error citing SEG119-R-004 |
| BR-SEG119-NEG-005 | `SEG119-R-005` | MUT-010 structural / separator violation | Validation error citing SEG119-R-005 |
| BR-SEG119-NEG-006 | `SEG119-R-006` | MUT-010 structural / separator violation | Validation error citing SEG119-R-006 |
| BR-SEG119-NEG-007 | `SEG119-R-007` | MUT-010 structural / separator violation | Validation error citing SEG119-R-007 |
| BR-SEG119-NEG-008 | `SEG119-R-008` | MUT-001 wrong fixed value | Validation error citing SEG119-R-008 |
| BR-SEG119-NEG-009 | `SEG119-R-009` | MUT-001 wrong fixed value | Validation error citing SEG119-R-009 |
| BR-SEG119-NEG-010 | `SEG119-R-010` | MUT-005 required field omitted | Validation error citing SEG119-R-010 |
| BR-SEG119-NEG-011 | `SEG119-R-011` | MUT-005 required field omitted | Validation error citing SEG119-R-011 |
| BR-SEG119-NEG-012 | `SEG119-R-012` | MUT-001 wrong fixed value | Validation error citing SEG119-R-012 |
| BR-SEG119-NEG-013 | `SEG119-R-013` | MUT-010 structural requirement | Validation error citing SEG119-R-013 |
| BR-SEG119-NEG-014 | `SEG119-R-014` | MUT-010 structural requirement | Validation error citing SEG119-R-014 |
| BR-SEG119-NEG-015 | `SEG119-R-015` | MUT-005 required field omitted | Validation error citing SEG119-R-015 |
| BR-SEG119-NEG-016 | `SEG119-R-016` | MUT-005 required field omitted | Validation error citing SEG119-R-016 |
| BR-SEG119-NEG-017 | `SEG119-R-017` | MUT-005 required field omitted | Validation error citing SEG119-R-017 |
| BR-SEG119-NEG-018 | `SEG119-R-018` | MUT-005 required field omitted | Validation error citing SEG119-R-018 |
| BR-SEG119-NEG-019 | `SEG119-R-019` | MUT-005 required field omitted | Validation error citing SEG119-R-019 |
| BR-SEG119-NEG-020 | `SEG119-R-020` | MUT-005 required field omitted | Validation error citing SEG119-R-020 |
| BR-SEG119-NEG-021 | `SEG119-R-021` | MUT-005 required field omitted | Validation error citing SEG119-R-021 |
| BR-SEG119-NEG-022 | `SEG119-R-022` | MUT-010 structural requirement | Validation error citing SEG119-R-022 |
| BR-SEG119-NEG-023 | `SEG119-R-023` | MUT-005 required field omitted | Validation error citing SEG119-R-023 |
| BR-SEG119-NEG-024 | `SEG119-R-024` | MUT-005 required field omitted | Validation error citing SEG119-R-024 |
| BR-SEG119-NEG-025 | `SEG119-R-025` | MUT-005 required field omitted | Validation error citing SEG119-R-025 |
| BR-SEG119-NEG-026 | `SEG119-R-026` | MUT-005 required field omitted | Validation error citing SEG119-R-026 |
| BR-SEG119-NEG-027 | `SEG119-R-027` | MUT-010 structural requirement | Validation error citing SEG119-R-027 |
| BR-SEG119-NEG-028 | `SEG119-R-028` | MUT-010 structural requirement | Validation error citing SEG119-R-028 |
| BR-SEG119-NEG-029 | `SEG119-R-029` | MUT-010 structural requirement | Validation error citing SEG119-R-029 |
| BR-SEG119-NEG-030 | `SEG119-R-030` | MUT-010 structural requirement | Validation error citing SEG119-R-030 |
| BR-SEG119-NEG-031 | `SEG119-R-031` | MUT-010 structural requirement | Validation error citing SEG119-R-031 |
| BR-SEG119-NEG-032 | `SEG119-R-032` | MUT-010 structural requirement | Validation error citing SEG119-R-032 |
| BR-SEG119-NEG-033 | `SEG119-R-033` | MUT-010 structural requirement | Validation error citing SEG119-R-033 |
| BR-SEG119-NEG-034 | `SEG119-R-034` | MUT-010 structural requirement | Validation error citing SEG119-R-034 |
| BR-SEG119-NEG-035 | `SEG119-R-035` | MUT-010 structural requirement | Validation error citing SEG119-R-035 |
| BR-SEG119-NEG-036 | `SEG119-R-036` | MUT-010 structural requirement | Validation error citing SEG119-R-036 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

_No open provisional items are linked to these rules._

## Implementation traceability

- Rule catalog: [coverage/segment-119-rule-catalog.json](coverage/segment-119-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
