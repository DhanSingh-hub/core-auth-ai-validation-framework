# Segment 105 Business Requirements

**Segment:** 105 — Segment 105  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** Totals Request, 11.4.1.1, 12.6, Chapter 13  
**Oracle:** [segment-105-rule-catalog.json](coverage/segment-105-rule-catalog.json) (22 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 105 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 105 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 9 |
| structure | 5 |
| conditional | 3 |
| serialization | 2 |
| dependency | 1 |
| lifecycle | 1 |
| compatibility | 1 |
| **Total** | **22** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG105-001 | field | Segment Type is 105 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG105-R-001` §Totals Request | SPEC_DERIVED |
| BR-SEG105-002 | serialization | Segment Length includes Segment Type and field separators | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG105-R-002` §Totals Request | SPEC_DERIVED |
| BR-SEG105-003 | field | Information Byte is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG105-R-003` §Totals Request | SPEC_DERIVED |
| BR-SEG105-004 | field | Terminal Identifier is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG105-R-004` §Totals Request | SPEC_DERIVED |
| BR-SEG105-005 | field | Prompt Code is 990 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG105-R-005` §Totals Request | SPEC_DERIVED |
| BR-SEG105-006 | conditional | Employee Number follows approved authorization policy when present | The conditional field is present exactly when its condition holds. | `SEG105-R-006` §Totals Request | REVIEW_REQUIRED |
| BR-SEG105-007 | conditional | Password follows approved authorization policy when present | The conditional field is present exactly when its condition holds. | `SEG105-R-007` §Totals Request | REVIEW_REQUIRED |
| BR-SEG105-008 | field | Totals Date uses a permitted source-defined request code | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG105-R-008` §Totals Request | SPEC_DERIVED |
| BR-SEG105-009 | dependency | Totals cannot be requested before the third most recent active date | The dependent value agrees with its controlling field or segment. | `SEG105-R-009` §Totals Request | SPEC_DERIVED |
| BR-SEG105-010 | field | Hardware Version is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG105-R-010` §Totals Request | SPEC_DERIVED |
| BR-SEG105-011 | field | Software Version is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG105-R-011` §Totals Request | SPEC_DERIVED |
| BR-SEG105-012 | field | Firmware Version is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG105-R-012` §Totals Request | SPEC_DERIVED |
| BR-SEG105-013 | field | Sequence Number is required | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG105-R-013` §Totals Request | SPEC_DERIVED |
| BR-SEG105-014 | conditional | Currency Code is optional when allowed by the message context | The conditional field is present exactly when its condition holds. | `SEG105-R-014` §Totals Request | SPEC_DERIVED |
| BR-SEG105-015 | structure | Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount are required | The segment's structural position and composition match the rule. | `SEG105-R-015` §Totals Request | SPEC_DERIVED |
| BR-SEG105-016 | lifecycle | Request and response correlation follows approved totals lifecycle policy | Paired messages are present and the correlated values agree. | `SEG105-R-016` §Totals Request | REVIEW_REQUIRED |
| BR-SEG105-017 | compatibility | Segment 119 use requires an approved proprietary-load selection rule | Only the permitted companion segments / message families carry this segment. | `SEG105-R-017` §Totals Request | REVIEW_REQUIRED |
| BR-SEG105-018 | structure | A Totals Request contains Data Sections 1 and 3 only; it has no Data Section 2 and no Segment 100 | The segment's structural position and composition match the rule. | `SEG105-R-018` §11.4.1.1 | SPEC_DERIVED |
| BR-SEG105-019 | structure | In a Totals Request, Segment 105 is the only data segment and occupies Data Section 3 Field No. 3 | The segment's structural position and composition match the rule. | `SEG105-R-019` §11.4.1.1,12.6 | SPEC_DERIVED |
| BR-SEG105-020 | structure | In a Totals Request, Element 63 (Number of Segments) is 01; the Chapter 13 variable length allows 1 or 01 | The segment's structural position and composition match the rule. | `SEG105-R-020` §11.4.1.1,13 | SPEC_DERIVED |
| BR-SEG105-021 | serialization | Segment 105 maximum length is 409 characters | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG105-R-021` §11.4.1.1,12.6 | SPEC_DERIVED |
| BR-SEG105-022 | structure | Segment 105 is present in every Totals Request | The segment's structural position and composition match the rule. | `SEG105-R-022` §11.4.1.1 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG105-NEG-001 | `SEG105-R-001` | MUT-001 wrong fixed value | Validation error citing SEG105-R-001 |
| BR-SEG105-NEG-002 | `SEG105-R-002` | MUT-001 wrong fixed value | Validation error citing SEG105-R-002 |
| BR-SEG105-NEG-003 | `SEG105-R-003` | MUT-005 required field omitted | Validation error citing SEG105-R-003 |
| BR-SEG105-NEG-004 | `SEG105-R-004` | MUT-005 required field omitted | Validation error citing SEG105-R-004 |
| BR-SEG105-NEG-005 | `SEG105-R-005` | MUT-010 structural requirement | Validation error citing SEG105-R-005 |
| BR-SEG105-NEG-006 | `SEG105-R-006` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG105-NEG-007 | `SEG105-R-007` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG105-NEG-008 | `SEG105-R-008` | MUT-010 structural requirement | Validation error citing SEG105-R-008 |
| BR-SEG105-NEG-009 | `SEG105-R-009` | MUT-010 structural requirement | Validation error citing SEG105-R-009 |
| BR-SEG105-NEG-010 | `SEG105-R-010` | MUT-005 required field omitted | Validation error citing SEG105-R-010 |
| BR-SEG105-NEG-011 | `SEG105-R-011` | MUT-005 required field omitted | Validation error citing SEG105-R-011 |
| BR-SEG105-NEG-012 | `SEG105-R-012` | MUT-005 required field omitted | Validation error citing SEG105-R-012 |
| BR-SEG105-NEG-013 | `SEG105-R-013` | MUT-005 required field omitted | Validation error citing SEG105-R-013 |
| BR-SEG105-NEG-014 | `SEG105-R-014` | MUT-004/MUT-008 value outside allowed set | Validation error citing SEG105-R-014 |
| BR-SEG105-NEG-015 | `SEG105-R-015` | MUT-005 required field omitted | Validation error citing SEG105-R-015 |
| BR-SEG105-NEG-016 | `SEG105-R-016` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG105-NEG-017 | `SEG105-R-017` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG105-NEG-018 | `SEG105-R-018` | MUT-010 structural requirement | Validation error citing SEG105-R-018 |
| BR-SEG105-NEG-019 | `SEG105-R-019` | MUT-010 structural requirement | Validation error citing SEG105-R-019 |
| BR-SEG105-NEG-020 | `SEG105-R-020` | MUT-001 wrong fixed value | Validation error citing SEG105-R-020 |
| BR-SEG105-NEG-021 | `SEG105-R-021` | MUT-003 length violation | Validation error citing SEG105-R-021 |
| BR-SEG105-NEG-022 | `SEG105-R-022` | MUT-005 required field omitted | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG105-006` (`SEG105-R-006`) — REVIEW_REQUIRED
- `BR-SEG105-007` (`SEG105-R-007`) — REVIEW_REQUIRED
- `BR-SEG105-016` (`SEG105-R-016`) — REVIEW_REQUIRED
- `BR-SEG105-017` (`SEG105-R-017`) — REVIEW_REQUIRED
- `BR-SEG105-022` (`SEG105-R-022`) — REVIEW_REQUIRED

## Open SME items

- **SEG105-SME-010** (SEG105-R-022): Section 11.4.1.1 marks Segment 105 Conditional ("Sent only on transactions requiring totals data"), but Element 63 is fixed at 01 and the message has no other segment. Confirm whether Segment 105 is required in every Totals Request.

## Implementation traceability

- Rule catalog: [coverage/segment-105-rule-catalog.json](coverage/segment-105-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
