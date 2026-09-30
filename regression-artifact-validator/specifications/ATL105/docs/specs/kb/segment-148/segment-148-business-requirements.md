# Segment 148 Business Requirements

**Segment:** 148 — WEX Available Product Fleet Information Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.33  
**Oracle:** [segment-148-rule-catalog.json](coverage/segment-148-rule-catalog.json) (4 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 148 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 148 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 2 |
| applicability | 1 |
| serialization | 1 |
| **Total** | **4** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG148-001 | applicability | Segment 148 sends available product and fleet information from WEX to the terminal; appears in the Financial Transaction response for WEX transactions only | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG148-R-001` §12.33 | SPEC_DERIVED |
| BR-SEG148-002 | field | Segment Type fixed 148, Segment Length includes Segment Type's length; both Host-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG148-R-002` §12.33 | SPEC_DERIVED |
| BR-SEG148-003 | serialization | Segment 148 maximum length is documented as 23 characters in the opening statement but the valid-values range given is 001-999 — a numeric inconsistency; Available Product Information (Element 240) itself is 17 characters | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG148-R-003` §12.33 | REVIEW_REQUIRED |
| BR-SEG148-004 | field | Available Product Information (Element 240) is a fixed-format 17-character sub-structure: Restriction Code (2-3 AN, fuel product group codes 00/01/06/07/08/10/11/12/13), '=' filler, Restriction Code Amount (1-5 AN), ',' filler, Restriction Code Quantity (1-5 AN), space filler, Restriction Code Unit of Measure (1 AN) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG148-R-004` §12.33 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG148-NEG-001 | `SEG148-R-001` | MUT-010 structural requirement | Validation error citing SEG148-R-001 |
| BR-SEG148-NEG-002 | `SEG148-R-002` | MUT-001 wrong fixed value | Validation error citing SEG148-R-002 |
| BR-SEG148-NEG-003 | `SEG148-R-003` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG148-NEG-004 | `SEG148-R-004` | MUT-002/MUT-006 format or charset violation | Validation error citing SEG148-R-004 |

## Requirements that must not be certified yet

- `BR-SEG148-003` (`SEG148-R-003`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG148-R-003): Reconcile the three conflicting length figures (23, 001-999, 17) for Segment 148 / Element 240.
- **P-02** (AI-artifacts, test-data): No dedicated Segment 148 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-148-rule-catalog.json](coverage/segment-148-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
