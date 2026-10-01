# Segment 113 Business Requirements

**Segment:** 113 — ECA/TeleCheck® Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.8.5, 11.1.1, 11.3.1, 11.3.2, 12, 12.12, 13.2  
**Oracle:** [segment-113-rule-catalog.json](coverage/segment-113-rule-catalog.json) (18 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 113 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 113 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 9 |
| serialization | 3 |
| structure | 2 |
| applicability | 1 |
| metadata | 1 |
| compatibility | 1 |
| lifecycle | 1 |
| **Total** | **18** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG113-001 | structure | Segment 113 belongs exclusively to the ECA/TeleCheck® Service Transaction Request; it is not a Financial Transaction Request companion | The segment's structural position and composition match the rule. | `SEG113-R-001` §11.1.1,11.3.1 | SPEC_DERIVED |
| BR-SEG113-002 | applicability | Segment 113 is a conditional member of the ECA/TeleCheck® Service Transaction Request's Data Section 3 (present when check-service risk-control data is being sent) | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG113-R-002` §11.3.1 | SPEC_DERIVED |
| BR-SEG113-003 | field | Segment Type is 113 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG113-R-003` §12.12 | SPEC_DERIVED |
| BR-SEG113-004 | field | Segment Length is 3 digits representing the segment content length | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG113-R-004` §12.12 | SPEC_DERIVED |
| BR-SEG113-005 | serialization | Segment 113 maximum length is 156 alphanumeric characters | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG113-R-005` §12.12 | SPEC_DERIVED |
| BR-SEG113-006 | serialization | Field order matches Section 12.12 ascending element order (85, 84, 131, 132, 133, 134, 135, 136, 137) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG113-R-006` §12.12 | SPEC_DERIVED |
| BR-SEG113-007 | serialization | All fields are separated by Field Separators; empty fields still send the separator | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG113-R-007` §12.12 | SPEC_DERIVED |
| BR-SEG113-008 | field | ECA/TeleCheck® Clerk ID, required, is alphanumeric with maximum length 6 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG113-R-008` §12.12,13.2 | SPEC_DERIVED |
| BR-SEG113-009 | field | ECA/TeleCheck® Product Code, when populated, is alphanumeric with maximum length 6 and has no documented enumeration (free-form, merchant/risk-control defined) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG113-R-009` §12.12,13.2 | SPEC_DERIVED |
| BR-SEG113-010 | field | ECA/TeleCheck® Phone Number, when populated, is numeric with maximum length 10 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG113-R-010` §12.12,13.2 | SPEC_DERIVED |
| BR-SEG113-011 | field | ECA/TeleCheck® Trace ID, when populated, is alphanumeric with maximum length 22; required on Void transaction requests | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG113-R-011` §12.12,13.2 | SPEC_DERIVED |
| BR-SEG113-012 | field | Merchant Trace ID, when populated, is alphanumeric with maximum length 25 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG113-R-012` §12.12,13.2 | SPEC_DERIVED |
| BR-SEG113-013 | field | Denial Record Number, when populated, is alphanumeric with maximum length 7; used to reference a declined ECA/TeleCheck® transaction on Denial Record receipts | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG113-R-013` §10.8.5,12.12,13.2 | SPEC_DERIVED |
| BR-SEG113-014 | field | Extended MICR Data, when populated, is alphanumeric with maximum length 65; must supplement MICR Data (Element 122) in Segment 110 when raw MICR data exceeds 50 bytes | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG113-R-014` §12.12,13.2 | SPEC_DERIVED |
| BR-SEG113-015 | structure | Segment 113 appears at most once per message | The segment's structural position and composition match the rule. | `SEG113-R-015` §12 | SPEC_DERIVED |
| BR-SEG113-016 | metadata | Segment 113 originates at the device | Documented for traceability; not independently asserted by a validator. | `SEG113-R-016` §12.12 | SPEC_DERIVED |
| BR-SEG113-017 | compatibility | Segment 113's ECA/TeleCheck® Service Transaction Request Data Section 3 siblings are Segment 110 (Check Data Segment, required) and Segment 111 (Variable Information Data Segment, optional) | Only the permitted companion segments / message families carry this segment. | `SEG113-R-017` §11.3.1 | SPEC_DERIVED |
| BR-SEG113-018 | lifecycle | Segment 113 does not appear in the ECA/TeleCheck® Service Transaction Response, which mirrors the generic Financial Transaction Response layout | Paired messages are present and the correlated values agree. | `SEG113-R-018` §11.3.2 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG113-NEG-001 | `SEG113-R-001` | MUT-010 structural requirement | Validation error citing SEG113-R-001 |
| BR-SEG113-NEG-002 | `SEG113-R-002` | MUT-005 required field omitted | Validation error citing SEG113-R-002 |
| BR-SEG113-NEG-003 | `SEG113-R-003` | MUT-001 wrong fixed value | Validation error citing SEG113-R-003 |
| BR-SEG113-NEG-004 | `SEG113-R-004` | MUT-003 length violation | Validation error citing SEG113-R-004 |
| BR-SEG113-NEG-005 | `SEG113-R-005` | MUT-003 length violation | Validation error citing SEG113-R-005 |
| BR-SEG113-NEG-006 | `SEG113-R-006` | MUT-009 interdependency violation | Validation error citing SEG113-R-006 |
| BR-SEG113-NEG-007 | `SEG113-R-007` | MUT-010 structural / separator violation | Validation error citing SEG113-R-007 |
| BR-SEG113-NEG-008 | `SEG113-R-008` | MUT-003 length violation | Validation error citing SEG113-R-008 |
| BR-SEG113-NEG-009 | `SEG113-R-009` | MUT-003 length violation | Validation error citing SEG113-R-009 |
| BR-SEG113-NEG-010 | `SEG113-R-010` | MUT-003 length violation | Validation error citing SEG113-R-010 |
| BR-SEG113-NEG-011 | `SEG113-R-011` | MUT-003 length violation | Validation error citing SEG113-R-011 |
| BR-SEG113-NEG-012 | `SEG113-R-012` | MUT-003 length violation | Validation error citing SEG113-R-012 |
| BR-SEG113-NEG-013 | `SEG113-R-013` | MUT-003 length violation | Validation error citing SEG113-R-013 |
| BR-SEG113-NEG-014 | `SEG113-R-014` | MUT-003 length violation | Validation error citing SEG113-R-014 |
| BR-SEG113-NEG-015 | `SEG113-R-015` | MUT-010 structural requirement | Validation error citing SEG113-R-015 |
| BR-SEG113-NEG-016 | `SEG113-R-017` | MUT-005 required field omitted | Validation error citing SEG113-R-017 |
| BR-SEG113-NEG-017 | `SEG113-R-018` | MUT-010 structural requirement | Validation error citing SEG113-R-018 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-05** (AI-artifacts): Location of AI-generated Segment 113 BR/TS/TC/TD packages (none supplied as of this training pass).

## Implementation traceability

- Rule catalog: [coverage/segment-113-rule-catalog.json](coverage/segment-113-rule-catalog.json)
- Validator: `Segment113PayloadValidator`
- Tests: `Segment113ArtifactComparisonTest`, `Segment113ConsolidatedReportTest`, `Segment113IndependenceValidatorTest`, `Segment113MutationTestRunnerTest`, `Segment113MutationTesterTest`, `Segment113PayloadValidatorTest`, `Segment113TraceabilityMatrixTest`
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
