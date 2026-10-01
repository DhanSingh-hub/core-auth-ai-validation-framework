# Segment 151 Business Requirements

**Segment:** 151 — Incomm OTC Market Basket Data (Request) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.36  
**Oracle:** [segment-151-rule-catalog.json](coverage/segment-151-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 151 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 151 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| serialization | 2 |
| field | 2 |
| structure | 1 |
| **Total** | **5** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG151-001 | structure | Segment 151 can appear in ANY field slot of Data Section No. 3 (not a fixed position), originates at the device | The segment's structural position and composition match the rule. | `SEG151-R-001` §12.36 | SPEC_DERIVED |
| BR-SEG151-002 | serialization | Segment 151 maximum length is documented as 2,309 alphanumeric characters in the opening statement, but the valid-values range given is 01-3,334 — a numeric inconsistency | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG151-R-002` §12.36 | REVIEW_REQUIRED |
| BR-SEG151-003 | serialization | A Field Separator appears between each of the 3 fields | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG151-R-003` §12.36 | SPEC_DERIVED |
| BR-SEG151-004 | field | Segment Type fixed 151, sourced at Host (despite the segment overall 'originating at the device' per the opening statement) — a sourcing inconsistency | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG151-R-004` §12.36 | REVIEW_REQUIRED |
| BR-SEG151-005 | field | Segment Length is 4 digits (not 3), max 2300 characters; Market Basket Data (unnumbered element) consists of one DV dataset followed by up to 10 PI datasets, with full format defined in a separate 'Buypass Incomm Market Basket Data format' document | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG151-R-005` §12.36 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG151-NEG-001 | `SEG151-R-001` | MUT-010 structural requirement | Validation error citing SEG151-R-001 |
| BR-SEG151-NEG-002 | `SEG151-R-002` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG151-NEG-003 | `SEG151-R-003` | MUT-010 structural / separator violation | Validation error citing SEG151-R-003 |
| BR-SEG151-NEG-004 | `SEG151-R-004` | MUT-001 wrong fixed value | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG151-NEG-005 | `SEG151-R-005` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG151-002` (`SEG151-R-002`) — REVIEW_REQUIRED
- `BR-SEG151-004` (`SEG151-R-004`) — REVIEW_REQUIRED
- `BR-SEG151-005` (`SEG151-R-005`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG151-R-002): Reconcile the 2,309 vs 3,334 maximum-length discrepancy.
- **P-02** (SEG151-R-004): Confirm whether Segment 151's origin is Device (per opening narrative) or Host (per field table Source entries).
- **P-03** (SEG151-R-005): Locate and confirm scope of the 'Buypass Incomm Market Basket Data format' document defining DV/PI dataset structures.
- **P-04** (AI-artifacts, test-data): No dedicated Segment 151 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-151-rule-catalog.json](coverage/segment-151-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
