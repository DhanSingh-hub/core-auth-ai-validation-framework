# Segment 139 Business Requirements

**Segment:** 139 — Moneris Day End Batch Balance (Request) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.26  
**Oracle:** [segment-139-rule-catalog.json](coverage/segment-139-rule-catalog.json) (4 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 139 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 139 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

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
| BR-SEG139-001 | applicability | Segment 139 is used to retrieve debit totals for the previous period, to be used in the day end batch close transaction (Segment 141) | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG139-R-001` §12.26 | SPEC_DERIVED |
| BR-SEG139-002 | field | Segment Type is fixed value 139, Segment Length is 3 digits, both sourced at the Device | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG139-R-002` §12.26 | SPEC_DERIVED |
| BR-SEG139-003 | field | Terminal Identifier (Element 102), SPDH Header (Element 206), Moneris Terminal Identifier (Element 207), Moneris Merchant ID (Element 208), Batch Number (Element 214), and Language Indicator (Element 215) are all required, Device-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG139-R-003` §12.26 | SPEC_DERIVED |
| BR-SEG139-004 | serialization | Field Separator behavior is not explicitly summarized in Section 12.26 (no 'Note:' sentence, unlike Segments 135/141) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG139-R-004` §12.26 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG139-NEG-001 | `SEG139-R-001` | MUT-010 structural requirement | Validation error citing SEG139-R-001 |
| BR-SEG139-NEG-002 | `SEG139-R-002` | MUT-001 wrong fixed value | Validation error citing SEG139-R-002 |
| BR-SEG139-NEG-003 | `SEG139-R-003` | MUT-005 required field omitted | Validation error citing SEG139-R-003 |
| BR-SEG139-NEG-004 | `SEG139-R-004` | MUT-010 structural / separator violation | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG139-004` (`SEG139-R-004`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG139-R-004): Confirm Field Separator placement for Segment 139 — no summary sentence documents it.
- **P-02** (AI-artifacts, test-data): No dedicated Segment 139 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-139-rule-catalog.json](coverage/segment-139-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
