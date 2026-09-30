# Segment 157 Business Requirements

**Segment:** 157 — Adjusted Product Code Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.41  
**Oracle:** [segment-157-rule-catalog.json](coverage/segment-157-rule-catalog.json) (9 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 157 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 157 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 5 |
| applicability | 1 |
| compatibility | 1 |
| structure | 1 |
| serialization | 1 |
| **Total** | **9** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG157-001 | applicability | Segment 157 is used exclusively and allowed for Comdata cards only; any transaction containing this segment that is not for a Comdata card will be declined | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG157-R-001` §12.41 | SPEC_DERIVED |
| BR-SEG157-002 | compatibility | Segment 157 is mutually exclusive with Segment 102 (Product Code Data); merchants should send one or the other but never both — if both are sent, the transaction will be declined | Only the permitted companion segments / message families carry this segment. | `SEG157-R-002` §12.41 | SPEC_DERIVED |
| BR-SEG157-003 | field | Segment 157 does not allow any product codes above '899' except for '955' (Cash Back); if any other code above 899 is present, the transaction will be declined | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG157-R-003` §12.41 | SPEC_DERIVED |
| BR-SEG157-004 | structure | Fuel products must always be the first products in the segment; a maximum of ten products is allowed | The segment's structural position and composition match the rule. | `SEG157-R-004` §12.41 | SPEC_DERIVED |
| BR-SEG157-005 | field | The total of Adjusted Product Amounts in the segment must equal the total of Element 41 (Fuel Purchase Amount) + Element 58 (Nonfuel Amount) + Element 99 (Tax Amount) + Element 17 (Cash Amount) in Segment 100 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG157-R-005` §12.41 | SPEC_DERIVED |
| BR-SEG157-006 | field | Tax, discount, and coupon amounts are already accounted for in the individual Adjusted Product Amounts; separate tax/discount/coupon product codes must NOT be included in the segment | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG157-R-006` §12.41 | SPEC_DERIVED |
| BR-SEG157-007 | field | Multi-fuel support: BUYPASS can accept transactions with multiple Fuel Type Codes in one transaction (e.g., Diesel + DEF + Reefer); the primary fuel must be the very first product code in the segment | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG157-R-007` §12.41 | SPEC_DERIVED |
| BR-SEG157-008 | serialization | A dual-delimiter scheme is used: Field Separator ('▲') follows Segment Type/Length and follows Adjusted Product Amount only when it is the LAST element in the segment; a Product Data Field Delimiter ('\') always follows Quantity and Unit Price, and follows Ad… | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG157-R-008` §12.41 | SPEC_DERIVED |
| BR-SEG157-009 | field | Segment Type fixed 157, Segment Length 3 digits, Service Level (Element 87), Number of Products (Element 62, 2 digits), then per-product: Product Code (77, max 3 including 899-cap/955-exception), Unit of Measure (106), Quantity (81, 9 digits, must encode assu… | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG157-R-009` §12.41 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG157-NEG-001 | `SEG157-R-001` | MUT-004/MUT-008 value outside allowed set | Validation error citing SEG157-R-001 |
| BR-SEG157-NEG-002 | `SEG157-R-002` | MUT-010 structural requirement | Validation error citing SEG157-R-002 |
| BR-SEG157-NEG-003 | `SEG157-R-003` | MUT-010 structural requirement | Validation error citing SEG157-R-003 |
| BR-SEG157-NEG-004 | `SEG157-R-004` | MUT-003 length violation | Validation error citing SEG157-R-004 |
| BR-SEG157-NEG-005 | `SEG157-R-005` | MUT-009 interdependency violation | Validation error citing SEG157-R-005 |
| BR-SEG157-NEG-006 | `SEG157-R-006` | MUT-010 structural requirement | Validation error citing SEG157-R-006 |
| BR-SEG157-NEG-007 | `SEG157-R-007` | MUT-010 structural requirement | Validation error citing SEG157-R-007 |
| BR-SEG157-NEG-008 | `SEG157-R-008` | MUT-001 wrong fixed value | Validation error citing SEG157-R-008 |
| BR-SEG157-NEG-009 | `SEG157-R-009` | MUT-001 wrong fixed value | Validation error citing SEG157-R-009 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (AI-artifacts, test-data): No dedicated Segment 157 AI or Test package was located. Provide one, or approve synthesized fixtures using the worked example already in Section 12.41.

## Implementation traceability

- Rule catalog: [coverage/segment-157-rule-catalog.json](coverage/segment-157-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
