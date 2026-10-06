# Segment 134 Business Requirements

**Segment:** 134 — Transaction Attributes Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.23  
**Oracle:** [segment-134-rule-catalog.json](coverage/segment-134-rule-catalog.json) (7 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 134 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 134 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 4 |
| serialization | 2 |
| metadata | 1 |
| **Total** | **7** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG134-001 | metadata | Segment 134 originates at BUYPASS (the Host) | Documented for traceability; not independently asserted by a validator. | `SEG134-R-001` §12.23 | SPEC_DERIVED |
| BR-SEG134-002 | serialization | Segment 134 uses NO Field Separators — fixed-length/positional, identical wire-format pattern to Segment 131 | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG134-R-002` §12.23 | SPEC_DERIVED |
| BR-SEG134-003 | serialization | Segment 134 maximum length is 19 alphanumeric characters (01-19/a-z/A-Z) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG134-R-003` §12.23 | SPEC_DERIVED |
| BR-SEG134-004 | field | Segment Type is fixed value 134, Segment Length is 4 digits | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG134-R-004` §12.23 | REVIEW_REQUIRED |
| BR-SEG134-005 | field | Settlement Type (Element 198) is required, 1 character, valid values D (Dual message), S (Single message), X (Non-traditional Signature Debit — availability must be confirmed with First Data Project/Relationship Manager) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG134-R-005` §12.23 | REVIEW_REQUIRED |
| BR-SEG134-006 | field | Signature Required (Element 199) is required, 1 character, valid values T (required), F (not required), Space (device software logic determines) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG134-R-006` §12.23 | SPEC_DERIVED |
| BR-SEG134-007 | field | Receipt Card Description (Element 200) is required, 10 characters, left-justified and space-filled (e.g., 'STAR ') | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG134-R-007` §12.23 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG134-NEG-001 | `SEG134-R-002` | MUT-003 length violation | Validation error citing SEG134-R-002 |
| BR-SEG134-NEG-002 | `SEG134-R-003` | MUT-003 length violation | Validation error citing SEG134-R-003 |
| BR-SEG134-NEG-003 | `SEG134-R-004` | MUT-001 wrong fixed value | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG134-NEG-004 | `SEG134-R-005` | MUT-005 required field omitted | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG134-NEG-005 | `SEG134-R-006` | MUT-005 required field omitted | Validation error citing SEG134-R-006 |
| BR-SEG134-NEG-006 | `SEG134-R-007` | MUT-005 required field omitted | Validation error citing SEG134-R-007 |

## Requirements that must not be certified yet

- `BR-SEG134-004` (`SEG134-R-004`) — REVIEW_REQUIRED
- `BR-SEG134-005` (`SEG134-R-005`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG134-R-004): Is Segment 134 genuinely an eighth 4-digit-Segment-Length segment (in addition to the previously-confirmed seven: 103,114,115,118,120,130,131), or is this a table transcription inconsistency?
- **P-02** (SEG134-R-005): Which merchant configurations have Non-traditional Signature Debit (Settlement Type X) enabled? Confirm with First Data Project/Relationship Manager per the specification's own note.
- **P-03** (AI-artifacts, test-data): No dedicated Segment 134 AI or Test Team package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-134-rule-catalog.json](coverage/segment-134-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
