# Segment 111 Business Requirements

**Segment:** 111 — Segment 111  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.10, 11.3.1  
**Oracle:** [segment-111-rule-catalog.json](coverage/segment-111-rule-catalog.json) (8 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 111 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 111 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| unclassified | 8 |
| **Total** | **8** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG111-001 | unclassified | Segment Type fixed 111 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG111-R-001` §12.10 | SPEC_DERIVED |
| BR-SEG111-002 | unclassified | Segment Length format and computation | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG111-R-002` §12.10 | SPEC_DERIVED |
| BR-SEG111-003 | unclassified | Indicator length | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG111-R-003` §12.10 | SPEC_DERIVED |
| BR-SEG111-004 | unclassified | Variable information length | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG111-R-004` §12.10 | SPEC_DERIVED |
| BR-SEG111-005 | unclassified | Repeated section maximum | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG111-R-005` §12.10 | SPEC_DERIVED |
| BR-SEG111-006 | unclassified | Total maximum | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG111-R-006` §12.10 | SPEC_DERIVED |
| BR-SEG111-007 | unclassified | Separator serialization | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG111-R-007` §12.10 | SPEC_DERIVED |
| BR-SEG111-008 | unclassified | In an ECA/TeleCheck Service Transaction Request, Segment 111 occupies Data Section 3 Field No. 5 with a maximum length of 20 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG111-R-008` §11.3.1 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG111-NEG-001 | `SEG111-R-001` | MUT-001 wrong fixed value | Validation error citing SEG111-R-001 |
| BR-SEG111-NEG-002 | `SEG111-R-002` | MUT-003 length violation | Validation error citing SEG111-R-002 |
| BR-SEG111-NEG-003 | `SEG111-R-003` | MUT-003 length violation | Validation error citing SEG111-R-003 |
| BR-SEG111-NEG-004 | `SEG111-R-004` | MUT-003 length violation | Validation error citing SEG111-R-004 |
| BR-SEG111-NEG-005 | `SEG111-R-005` | MUT-003 length violation | Validation error citing SEG111-R-005 |
| BR-SEG111-NEG-006 | `SEG111-R-006` | MUT-003 length violation | Validation error citing SEG111-R-006 |
| BR-SEG111-NEG-007 | `SEG111-R-007` | MUT-010 structural / separator violation | Validation error citing SEG111-R-007 |
| BR-SEG111-NEG-008 | `SEG111-R-008` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG111-008` (`SEG111-R-008`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG111-R-008): The Section 11.3.1 layout gives Segment 111 a Max. Len. of 20 in the ECA/TeleCheck request, while Section 12.10 and the Section 11.1.1 Financial Transaction Request layout give 999. Confirm whether 20 is a deliberate ECA/TeleCheck-only cap or a documentation error.

## Implementation traceability

- Rule catalog: [coverage/segment-111-rule-catalog.json](coverage/segment-111-rule-catalog.json)
- Validator: `Segment111PayloadValidator`
- Tests: `Segment111ArtifactComparisonTest`, `Segment111ConsolidatedReportTest`, `Segment111IndependenceValidatorTest`, `Segment111MutationTestRunnerTest`, `Segment111MutationTesterTest`, `Segment111PayloadValidatorTest`, `Segment111SerializationValidatorTest`, `Segment111TraceabilityMatrixTest`
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
