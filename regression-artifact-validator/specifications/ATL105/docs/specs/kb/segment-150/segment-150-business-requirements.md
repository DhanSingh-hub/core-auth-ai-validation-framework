# Segment 150 Business Requirements

**Segment:** 150 — Fuel Price Update Response Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.35  
**Oracle:** [segment-150-rule-catalog.json](coverage/segment-150-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 150 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 150 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| applicability | 1 |
| serialization | 1 |
| field | 1 |
| **Total** | **3** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG150-001 | applicability | Segment 150 contains an indication of success or failure of the Segment 149 Fuel Price Update Request | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG150-R-001` §12.35 | SPEC_DERIVED |
| BR-SEG150-002 | serialization | A Field Separator follows the segment (single trailing separator, per-field placement for fields 1-4 not explicitly documented) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG150-R-002` §12.35 | REVIEW_REQUIRED |
| BR-SEG150-003 | field | Segment Type fixed 150, Segment Length 3 digits, Terminal Identifier (echo), Decline Code (Element 26, 2 characters, indicates success or failure); all Device-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG150-R-003` §12.35 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG150-NEG-001 | `SEG150-R-001` | MUT-010 structural requirement | Validation error citing SEG150-R-001 |
| BR-SEG150-NEG-002 | `SEG150-R-002` | MUT-010 structural / separator violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG150-NEG-003 | `SEG150-R-003` | MUT-001 wrong fixed value | Validation error citing SEG150-R-003 |

## Requirements that must not be certified yet

- `BR-SEG150-002` (`SEG150-R-002`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG150-R-002): Confirm whether per-field Field Separators (1-2, 2-3, 3-4) also apply, or only the single trailing separator.
- **P-02** (AI-artifacts, test-data): No dedicated Segment 150 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-150-rule-catalog.json](coverage/segment-150-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
