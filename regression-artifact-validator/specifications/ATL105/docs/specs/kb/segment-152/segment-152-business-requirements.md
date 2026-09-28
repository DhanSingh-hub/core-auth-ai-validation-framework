# Segment 152 Business Requirements

**Segment:** 152 — Incomm OTC Market Basket Data (Response) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.37  
**Oracle:** [segment-152-rule-catalog.json](coverage/segment-152-rule-catalog.json) (3 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 152 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 152 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 2 |
| serialization | 1 |
| **Total** | **3** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG152-001 | serialization | A Field Separator follows the segment (trailing separator, mirrors Segment 150's response pattern) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG152-R-001` §12.37 | SPEC_DERIVED |
| BR-SEG152-002 | field | Segment Type fixed 152, Segment Length 4 digits, both Host-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG152-R-002` §12.37 | SPEC_DERIVED |
| BR-SEG152-003 | field | Market Basket Data consists of one DV dataset, up to 10 PI datasets, AND up to 10 PU datasets — one more dataset type than the request (Segment 151, which has no PU datasets) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG152-R-003` §12.37 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG152-NEG-001 | `SEG152-R-001` | MUT-010 structural / separator violation | Validation error citing SEG152-R-001 |
| BR-SEG152-NEG-002 | `SEG152-R-002` | MUT-001 wrong fixed value | Validation error citing SEG152-R-002 |
| BR-SEG152-NEG-003 | `SEG152-R-003` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG152-003` (`SEG152-R-003`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG152-R-003): Locate and confirm scope of the external Incomm Market Basket Data format document (shared question with Segment 151).
- **P-02** (AI-artifacts, test-data): No dedicated Segment 152 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-152-rule-catalog.json](coverage/segment-152-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
