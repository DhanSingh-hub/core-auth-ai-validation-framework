# Segment 102 Business Requirements

**Segment:** 102 — Segment 102  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.3, 12.30, Appendix F  
**Oracle:** [segment-102-rule-catalog.json](coverage/segment-102-rule-catalog.json) (25 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 102 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 102 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 8 |
| dependency | 8 |
| serialization | 4 |
| compatibility | 3 |
| structure | 2 |
| **Total** | **25** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG102-001 | field | Segment Type is 102 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG102-R-001` §12.3 | SPEC_DERIVED |
| BR-SEG102-002 | serialization | Segment Length represents encoded Segment 102 content | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG102-R-002` §12.3 | SPEC_DERIVED |
| BR-SEG102-003 | field | Service Level uses an allowed value | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG102-R-003` §12.3 | SPEC_DERIVED |
| BR-SEG102-004 | field | Number of Products is 01-10 and zero-padded | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG102-R-004` §12.3 | SPEC_DERIVED |
| BR-SEG102-005 | dependency | Number of Products matches the actual serialized product entry count | The dependent value agrees with its controlling field or segment. | `SEG102-R-005` §12.3 | SPEC_DERIVED |
| BR-SEG102-006 | structure | A maximum of ten products is allowed in the segment | The segment's structural position and composition match the rule. | `SEG102-R-006` §12.3 | SPEC_DERIVED |
| BR-SEG102-007 | dependency | Fuel products are always the first products in the segment | The dependent value agrees with its controlling field or segment. | `SEG102-R-007` §12.3 | REVIEW_REQUIRED |
| BR-SEG102-008 | dependency | For EV charging transactions the EV product code is first | The dependent value agrees with its controlling field or segment. | `SEG102-R-008` §12.3 | REVIEW_REQUIRED |
| BR-SEG102-009 | dependency | A unique Product Code is sent for each type of fuel purchase | The dependent value agrees with its controlling field or segment. | `SEG102-R-009` §12.3 | SPEC_DERIVED |
| BR-SEG102-010 | field | Product Code is a valid value per Appendix F | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG102-R-010` §Appendix F | REVIEW_REQUIRED |
| BR-SEG102-011 | field | Unit of Measure uses an allowed value | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG102-R-011` §12.3 | SPEC_DERIVED |
| BR-SEG102-012 | field | Quantity preserves the assumed-decimal-place leading digit | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG102-R-012` §12.3 | SPEC_DERIVED |
| BR-SEG102-013 | field | Unit Price preserves the assumed-decimal-place leading digit | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG102-R-013` §12.3 | SPEC_DERIVED |
| BR-SEG102-014 | field | Product Amount is present for every product entry | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG102-R-014` §12.3 | SPEC_DERIVED |
| BR-SEG102-015 | dependency | Sum of Product Amounts reconciles with Segment 100 fuel, nonfuel, tax, and cash amounts | The dependent value agrees with its controlling field or segment. | `SEG102-R-015` §12.3 | SPEC_DERIVED |
| BR-SEG102-016 | dependency | Tax-coded product totals are reflected in Segment 100 Tax Amount | The dependent value agrees with its controlling field or segment. | `SEG102-R-016` §12.3 | SPEC_DERIVED |
| BR-SEG102-017 | dependency | Fuel merchants send both fuel and nonfuel product data | The dependent value agrees with its controlling field or segment. | `SEG102-R-017` §12.3 | REVIEW_REQUIRED |
| BR-SEG102-018 | compatibility | Segment 102 and Segment 157 are mutually exclusive | Only the permitted companion segments / message families carry this segment. | `SEG102-R-018` §12.3 | SPEC_DERIVED |
| BR-SEG102-019 | compatibility | Segment 102 is not used for Comdata card transactions | Only the permitted companion segments / message families carry this segment. | `SEG102-R-019` §12.3 | SPEC_DERIVED |
| BR-SEG102-020 | serialization | Product Data Field Delimiter follows Quantity and Unit Price | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG102-R-020` §12.3 | SPEC_DERIVED |
| BR-SEG102-021 | serialization | Product Amount separator depends on its position in the segment | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG102-R-021` §12.3 | SPEC_DERIVED |
| BR-SEG102-022 | serialization | Field Separator follows Segment Type and Segment Length | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG102-R-022` §12.3 | SPEC_DERIVED |
| BR-SEG102-023 | structure | Segment 102 total length does not exceed 381 characters | The segment's structural position and composition match the rule. | `SEG102-R-023` §12.3 | SPEC_DERIVED |
| BR-SEG102-024 | dependency | Multi-fuel OTR transactions list the primary fuel first | The dependent value agrees with its controlling field or segment. | `SEG102-R-024` §12.3 | REVIEW_REQUIRED |
| BR-SEG102-025 | compatibility | Segment 143 product order and count match Segment 102 when present | Only the permitted companion segments / message families carry this segment. | `SEG102-R-025` §12.30 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG102-NEG-001 | `SEG102-R-001` | MUT-001 wrong fixed value | Validation error citing SEG102-R-001 |
| BR-SEG102-NEG-002 | `SEG102-R-002` | MUT-003 length violation | Validation error citing SEG102-R-002 |
| BR-SEG102-NEG-003 | `SEG102-R-003` | MUT-004/MUT-008 value outside allowed set | Validation error citing SEG102-R-003 |
| BR-SEG102-NEG-004 | `SEG102-R-004` | MUT-010 structural requirement | Validation error citing SEG102-R-004 |
| BR-SEG102-NEG-005 | `SEG102-R-005` | MUT-009 interdependency violation | Validation error citing SEG102-R-005 |
| BR-SEG102-NEG-006 | `SEG102-R-006` | MUT-003 length violation | Validation error citing SEG102-R-006 |
| BR-SEG102-NEG-007 | `SEG102-R-007` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG102-NEG-008 | `SEG102-R-008` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG102-NEG-009 | `SEG102-R-009` | MUT-010 structural requirement | Validation error citing SEG102-R-009 |
| BR-SEG102-NEG-010 | `SEG102-R-010` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG102-NEG-011 | `SEG102-R-011` | MUT-004/MUT-008 value outside allowed set | Validation error citing SEG102-R-011 |
| BR-SEG102-NEG-012 | `SEG102-R-012` | MUT-010 structural requirement | Validation error citing SEG102-R-012 |
| BR-SEG102-NEG-013 | `SEG102-R-013` | MUT-010 structural requirement | Validation error citing SEG102-R-013 |
| BR-SEG102-NEG-014 | `SEG102-R-014` | MUT-010 structural requirement | Validation error citing SEG102-R-014 |
| BR-SEG102-NEG-015 | `SEG102-R-015` | MUT-010 structural requirement | Validation error citing SEG102-R-015 |
| BR-SEG102-NEG-016 | `SEG102-R-016` | MUT-010 structural requirement | Validation error citing SEG102-R-016 |
| BR-SEG102-NEG-017 | `SEG102-R-017` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG102-NEG-018 | `SEG102-R-018` | MUT-010 structural requirement | Validation error citing SEG102-R-018 |
| BR-SEG102-NEG-019 | `SEG102-R-019` | MUT-010 structural requirement | Validation error citing SEG102-R-019 |
| BR-SEG102-NEG-020 | `SEG102-R-020` | MUT-010 structural / separator violation | Validation error citing SEG102-R-020 |
| BR-SEG102-NEG-021 | `SEG102-R-021` | MUT-010 structural / separator violation | Validation error citing SEG102-R-021 |
| BR-SEG102-NEG-022 | `SEG102-R-022` | MUT-001 wrong fixed value | Validation error citing SEG102-R-022 |
| BR-SEG102-NEG-023 | `SEG102-R-023` | MUT-003 length violation | Validation error citing SEG102-R-023 |
| BR-SEG102-NEG-024 | `SEG102-R-024` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG102-NEG-025 | `SEG102-R-025` | MUT-009 interdependency violation | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG102-007` (`SEG102-R-007`) — REVIEW_REQUIRED
- `BR-SEG102-008` (`SEG102-R-008`) — REVIEW_REQUIRED
- `BR-SEG102-010` (`SEG102-R-010`) — REVIEW_REQUIRED
- `BR-SEG102-017` (`SEG102-R-017`) — REVIEW_REQUIRED
- `BR-SEG102-024` (`SEG102-R-024`) — REVIEW_REQUIRED
- `BR-SEG102-025` (`SEG102-R-025`) — REVIEW_REQUIRED

## Open SME items

_No open provisional items are linked to these rules._

## Implementation traceability

- Rule catalog: [coverage/segment-102-rule-catalog.json](coverage/segment-102-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
