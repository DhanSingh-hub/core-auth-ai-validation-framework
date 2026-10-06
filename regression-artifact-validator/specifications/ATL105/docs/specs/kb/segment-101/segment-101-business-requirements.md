# Segment 101 Business Requirements

**Segment:** 101 — Fleet Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.1, 12, 12.2  
**Oracle:** [segment-101-rule-catalog.json](coverage/segment-101-rule-catalog.json) (26 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 101 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 101 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 16 |
| serialization | 3 |
| structure | 2 |
| applicability | 2 |
| compatibility | 1 |
| lifecycle | 1 |
| metadata | 1 |
| **Total** | **26** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG101-001 | structure | Segment 101 is a Data Section 3 companion of Segment 100 | The segment's structural position and composition match the rule. | `SEG101-R-001` §11.1.1 | SPEC_DERIVED |
| BR-SEG101-002 | applicability | Segment 101 is required in every fleet-card financial transaction request | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG101-R-002` §12.2 | SPEC_DERIVED |
| BR-SEG101-003 | compatibility | Segment 101 must not coexist with Segment 145 (Enhanced Fleet) in the same message | Only the permitted companion segments / message families carry this segment. | `SEG101-R-003` §12.2 | SPEC_DERIVED |
| BR-SEG101-004 | field | Segment Type is 101 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-004` §12.2 | SPEC_DERIVED |
| BR-SEG101-005 | field | Segment Length is 3 digits representing the segment content length | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-005` §12.2 | SPEC_DERIVED |
| BR-SEG101-006 | serialization | Base Segment 101 maximum length is 61 alphanumeric characters | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG101-R-006` §12.2 | REVIEW_REQUIRED |
| BR-SEG101-007 | serialization | Field order matches Section 12.2 (base fields 1-13, Auth Completion tag fields 14-18) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG101-R-007` §12.2 | SPEC_DERIVED |
| BR-SEG101-008 | serialization | Empty non-trailing fields retain their Field Separators | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG101-R-008` §12.2 | SPEC_DERIVED |
| BR-SEG101-009 | field | Odometer, when populated, is numeric with maximum length 8 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-009` §12.2 | SPEC_DERIVED |
| BR-SEG101-010 | field | Vehicle Number, when populated, is alphanumeric with maximum length 10 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-010` §12.2 | SPEC_DERIVED |
| BR-SEG101-011 | field | Job Number, when populated, is alphanumeric with maximum length 10 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-011` §12.2 | SPEC_DERIVED |
| BR-SEG101-012 | field | Driver/Identification Number, when populated, is alphanumeric with maximum length 10 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-012` §12.2 | SPEC_DERIVED |
| BR-SEG101-013 | field | Fleet Employee Number, when populated, is alphanumeric with maximum length 10 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-013` §12.2 | SPEC_DERIVED |
| BR-SEG101-014 | field | License #, when populated, is alphanumeric with maximum length 10 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-014` §12.2 | SPEC_DERIVED |
| BR-SEG101-015 | field | Job ID, when populated, is alphanumeric with maximum length 12 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-015` §12.2 | SPEC_DERIVED |
| BR-SEG101-016 | field | Department #, when populated, is alphanumeric with maximum length 12 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-016` §12.2 | SPEC_DERIVED |
| BR-SEG101-017 | field | Customer Data, when populated, is alphanumeric with maximum length 12 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-017` §12.2 | SPEC_DERIVED |
| BR-SEG101-018 | field | User ID, when populated, is alphanumeric with maximum length 12 and non-zero | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-018` §12.2 | SPEC_DERIVED |
| BR-SEG101-019 | field | Vehicle ID#, when populated, is alphanumeric with maximum length 8 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-019` §12.2 | SPEC_DERIVED |
| BR-SEG101-020 | lifecycle | Fleet Tag fields 14-18 are only present in Auth Completion (0220) messages | Paired messages are present and the correlated values agree. | `SEG101-R-020` §12.2 | SPEC_DERIVED |
| BR-SEG101-021 | applicability | Fleet Tag fields 14-18 are only sent when Host Prompts are supported | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG101-R-021` §12.2 | SPEC_DERIVED |
| BR-SEG101-022 | field | Fleet Tag format is 3-byte code plus up to 31-byte data payload | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-022` §12.2 | SPEC_DERIVED |
| BR-SEG101-023 | field | Fleet Tag 3-byte code must be one of the 17 codes in the fleet-tag code table | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-023` §12.2 | SPEC_DERIVED |
| BR-SEG101-024 | field | Fleet Tag data payload conforms to the per-code format | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG101-R-024` §12.2 | SPEC_DERIVED |
| BR-SEG101-025 | metadata | Segment 101 originates at the device | Documented for traceability; not independently asserted by a validator. | `SEG101-R-025` §12.2 | SPEC_DERIVED |
| BR-SEG101-026 | structure | Segment 101 appears exactly once per message | The segment's structural position and composition match the rule. | `SEG101-R-026` §12 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG101-NEG-001 | `SEG101-R-001` | MUT-010 structural requirement | Validation error citing SEG101-R-001 |
| BR-SEG101-NEG-002 | `SEG101-R-002` | MUT-005 required field omitted | Validation error citing SEG101-R-002 |
| BR-SEG101-NEG-003 | `SEG101-R-003` | MUT-010 structural requirement | Validation error citing SEG101-R-003 |
| BR-SEG101-NEG-004 | `SEG101-R-004` | MUT-001 wrong fixed value | Validation error citing SEG101-R-004 |
| BR-SEG101-NEG-005 | `SEG101-R-005` | MUT-003 length violation | Validation error citing SEG101-R-005 |
| BR-SEG101-NEG-006 | `SEG101-R-006` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG101-NEG-007 | `SEG101-R-007` | MUT-009 interdependency violation | Validation error citing SEG101-R-007 |
| BR-SEG101-NEG-008 | `SEG101-R-008` | MUT-010 structural / separator violation | Validation error citing SEG101-R-008 |
| BR-SEG101-NEG-009 | `SEG101-R-009` | MUT-003 length violation | Validation error citing SEG101-R-009 |
| BR-SEG101-NEG-010 | `SEG101-R-010` | MUT-003 length violation | Validation error citing SEG101-R-010 |
| BR-SEG101-NEG-011 | `SEG101-R-011` | MUT-003 length violation | Validation error citing SEG101-R-011 |
| BR-SEG101-NEG-012 | `SEG101-R-012` | MUT-003 length violation | Validation error citing SEG101-R-012 |
| BR-SEG101-NEG-013 | `SEG101-R-013` | MUT-003 length violation | Validation error citing SEG101-R-013 |
| BR-SEG101-NEG-014 | `SEG101-R-014` | MUT-003 length violation | Validation error citing SEG101-R-014 |
| BR-SEG101-NEG-015 | `SEG101-R-015` | MUT-003 length violation | Validation error citing SEG101-R-015 |
| BR-SEG101-NEG-016 | `SEG101-R-016` | MUT-003 length violation | Validation error citing SEG101-R-016 |
| BR-SEG101-NEG-017 | `SEG101-R-017` | MUT-003 length violation | Validation error citing SEG101-R-017 |
| BR-SEG101-NEG-018 | `SEG101-R-018` | MUT-003 length violation | Validation error citing SEG101-R-018 |
| BR-SEG101-NEG-019 | `SEG101-R-019` | MUT-003 length violation | Validation error citing SEG101-R-019 |
| BR-SEG101-NEG-020 | `SEG101-R-020` | MUT-010 structural requirement | Validation error citing SEG101-R-020 |
| BR-SEG101-NEG-021 | `SEG101-R-021` | MUT-010 structural requirement | Validation error citing SEG101-R-021 |
| BR-SEG101-NEG-022 | `SEG101-R-022` | MUT-010 structural requirement | Validation error citing SEG101-R-022 |
| BR-SEG101-NEG-023 | `SEG101-R-023` | MUT-004/MUT-008 value outside allowed set | Validation error citing SEG101-R-023 |
| BR-SEG101-NEG-024 | `SEG101-R-024` | MUT-010 structural requirement | Validation error citing SEG101-R-024 |
| BR-SEG101-NEG-025 | `SEG101-R-026` | MUT-010 structural requirement | Validation error citing SEG101-R-026 |

## Requirements that must not be certified yet

- `BR-SEG101-006` (`SEG101-R-006`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG101-R-006): Reconcile base 001-061 max length with 5 x 34-byte Fleet Tags in Auth Completion messages.
- **P-02** (SEG101-R-024): Confirm DLN carries 'Driver License name' (spec text 'nameation' appears to be a typo).
- **P-03** (SEG101-R-002): Enumerate the fleet-eligible Appendix E card types.
- **P-04** (SEG101-R-002): Confirm the Appendix G transaction types applicable to fleet (working: 0,5,6,7,S,C,U).
- **P-05** (response-side): Whether Segment 101 (or variant) appears in Financial Response messages.
- **P-06** (SEG101-R-009..019): Per-field mandatory-presence triggers from fleet-program rules.
- **P-07** (separate-domain): Petroleum Industry Processing Specifications scope and version.
- **P-08** (SEG101-R-003): Segment 145 (Enhanced Fleet) scope for this training.
- **P-09** (AI-artifacts): Location of AI-generated Segment 101 BR/TS/TC/TD packages.
- **P-10** (test-data): Availability of Segment 101 sample JSONs, or approval to synthesize .synthetic.json fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-101-rule-catalog.json](coverage/segment-101-rule-catalog.json)
- Validator: `Segment101PayloadValidator`
- Tests: `Segment101ArtifactComparisonTest`, `Segment101ConsolidatedReportTest`, `Segment101IndependenceValidatorTest`, `Segment101MutationTestRunnerTest`, `Segment101MutationTesterTest`, `Segment101PayloadValidatorTest`, `Segment101TraceabilityMatrixTest`
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
