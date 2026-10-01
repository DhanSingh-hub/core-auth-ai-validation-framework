# Segment 104 Business Requirements

**Segment:** 104 — Purchase Card Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.1, 12, 12.5  
**Oracle:** [segment-104-rule-catalog.json](coverage/segment-104-rule-catalog.json) (14 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 104 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 104 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| unclassified | 14 |
| **Total** | **14** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG104-001 | unclassified | Purchase Card Data Segment is a Data Section 3 companion of Segment 100. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-001` §11.1.1 | SPEC_DERIVED |
| BR-SEG104-002 | unclassified | Purchase Card Data Segment object is required in the document being validated. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-002` §12.5 | SPEC_DERIVED |
| BR-SEG104-003 | unclassified | Segment Type is 104. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-004` §12.5 | SPEC_DERIVED |
| BR-SEG104-004 | unclassified | Segment Length is 3 digits representing the content length. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-005` §12.5 | SPEC_DERIVED |
| BR-SEG104-005 | unclassified | Purchase Card Data Segment max length is 86 alphanumeric characters. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-006` §12.5 | SPEC_DERIVED |
| BR-SEG104-006 | unclassified | Purchase Code is alphanumeric, max 16 characters, when populated. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-007` §12.5 | SPEC_DERIVED |
| BR-SEG104-007 | unclassified | PC Tax Amount is numeric, up to 7 digits with 2 assumed decimal places, when populated. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-008` §12.5 | SPEC_DERIVED |
| BR-SEG104-008 | unclassified | PC Freight Amount is numeric, up to 7 digits with 2 assumed decimal places, when populated. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-009` §12.5 | SPEC_DERIVED |
| BR-SEG104-009 | unclassified | PC Duty Amount is numeric, up to 7 digits with 2 assumed decimal places, when populated. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-010` §12.5 | SPEC_DERIVED |
| BR-SEG104-010 | unclassified | Ship-to Country Code is a fixed length of 3 digits, when populated. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-011` §12.5 | SPEC_DERIVED |
| BR-SEG104-011 | unclassified | Ship-to Postal Code format is PROVISIONAL (P-01): strict US ZIP+4 or general alphanumeric up to 10 characters; non-conforming values are flagged for SME review rather than hard-rejected. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-012` §12.5 | SPEC_DERIVED |
| BR-SEG104-012 | unclassified | Ship-from Postal Code format is PROVISIONAL (P-01): strict US ZIP+4 or general alphanumeric up to 10 characters; non-conforming values are flagged for SME review rather than hard-rejected. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-013` §12.5 | SPEC_DERIVED |
| BR-SEG104-013 | unclassified | Direct Marketing Invoice Number is alphanumeric, max 10 characters, when populated. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-014` §12.5 | SPEC_DERIVED |
| BR-SEG104-014 | unclassified | At most one Segment 104 per message. PROVISIONAL (P-02): not explicitly stated in the spec text the way Segment 101's cardinality is; enforced by analogy to the Data Section 3 slot structure pending SME confirmation. | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG104-R-020` §12 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG104-NEG-001 | `SEG104-R-001` | MUT-010 structural requirement | Validation error citing SEG104-R-001 |
| BR-SEG104-NEG-002 | `SEG104-R-002` | MUT-005 required field omitted | Validation error citing SEG104-R-002 |
| BR-SEG104-NEG-003 | `SEG104-R-004` | MUT-001 wrong fixed value | Validation error citing SEG104-R-004 |
| BR-SEG104-NEG-004 | `SEG104-R-005` | MUT-003 length violation | Validation error citing SEG104-R-005 |
| BR-SEG104-NEG-005 | `SEG104-R-006` | MUT-003 length violation | Validation error citing SEG104-R-006 |
| BR-SEG104-NEG-006 | `SEG104-R-007` | MUT-003 length violation | Validation error citing SEG104-R-007 |
| BR-SEG104-NEG-007 | `SEG104-R-008` | MUT-003 length violation | Validation error citing SEG104-R-008 |
| BR-SEG104-NEG-008 | `SEG104-R-009` | MUT-003 length violation | Validation error citing SEG104-R-009 |
| BR-SEG104-NEG-009 | `SEG104-R-010` | MUT-003 length violation | Validation error citing SEG104-R-010 |
| BR-SEG104-NEG-010 | `SEG104-R-011` | MUT-003 length violation | Validation error citing SEG104-R-011 |
| BR-SEG104-NEG-011 | `SEG104-R-012` | MUT-002/MUT-006 format or charset violation | Validation error citing SEG104-R-012 |
| BR-SEG104-NEG-012 | `SEG104-R-013` | MUT-002/MUT-006 format or charset violation | Validation error citing SEG104-R-013 |
| BR-SEG104-NEG-013 | `SEG104-R-014` | MUT-003 length violation | Validation error citing SEG104-R-014 |
| BR-SEG104-NEG-014 | `SEG104-R-020` | MUT-010 structural requirement | Validation error citing SEG104-R-020 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

_No open provisional items are linked to these rules._

## Implementation traceability

- Rule catalog: [coverage/segment-104-rule-catalog.json](coverage/segment-104-rule-catalog.json)
- Validator: `Segment104PayloadValidator`
- Tests: `Segment104ArtifactComparisonTest`, `Segment104ConsolidatedReportTest`, `Segment104IndependenceValidatorTest`, `Segment104MutationTestRunnerTest`, `Segment104MutationTesterTest`, `Segment104PayloadValidatorTest`, `Segment104TraceabilityMatrixTest`
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
