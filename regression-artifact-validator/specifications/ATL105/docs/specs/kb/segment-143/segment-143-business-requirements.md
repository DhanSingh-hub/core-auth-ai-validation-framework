# Segment 143 Business Requirements

**Segment:** 143 — Tax by Product Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.30  
**Oracle:** [segment-143-rule-catalog.json](coverage/segment-143-rule-catalog.json) (9 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 143 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 143 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 5 |
| applicability | 1 |
| metadata | 1 |
| structure | 1 |
| serialization | 1 |
| **Total** | **9** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG143-001 | applicability | When a Financial Transaction request includes Segment 143, a Product Code Data Segment (Segment 102) MUST also be present, and each entry in Segment 102 should have a corresponding entry in Segment 143, in the same order | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG143-R-001` §12.30 | SPEC_DERIVED |
| BR-SEG143-002 | metadata | Only format validation is performed for this segment; no judgement is made of the values contained | Documented for traceability; not independently asserted by a validator. | `SEG143-R-002` §12.30 | SPEC_DERIVED |
| BR-SEG143-003 | field | Segment Type is fixed value 143, Segment Length identifies the segment's length including Segment Type's length and Field Separators, both Device-sourced, each followed by a Field Separator | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG143-R-003` §12.30 | SPEC_DERIVED |
| BR-SEG143-004 | field | Number of Products (Element 62) is required, 2 digits, and should match the number of products from the Product Code Data Segment | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG143-R-004` §12.30 | SPEC_DERIVED |
| BR-SEG143-005 | structure | Tax by Product Data repeats per product for a maximum of 10 products, total variable length up to 360 bytes; each product entry contains Product Code (77) plus up to 3 tax sub-entries (Inclusive/Exclusive flag, Tax Type, Tax Amount) | The segment's structural position and composition match the rule. | `SEG143-R-005` §12.30 | SPEC_DERIVED |
| BR-SEG143-006 | field | Inclusive/Exclusive flag values: 'I' (inclusive to the product amount), 'E' (exclusive), 'N' (this tax not applicable — tax type and amount fields are OMITTED entirely, not merely blank) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG143-R-006` §12.30 | SPEC_DERIVED |
| BR-SEG143-007 | field | Tax Type is one of GST/HST/PST for Canadian transactions; a product can have between 0 and 3 of these taxes specified | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG143-R-007` §12.30 | REVIEW_REQUIRED |
| BR-SEG143-008 | serialization | A dual-delimiter scheme is used: a Field Separator ('▲') follows the LAST tax amount for each product and signals moving to the next product (or end of segment for the last product); a Tax by Product Field Delimiter ('\') follows a tax amount when there is AN… | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG143-R-008` §12.30 | SPEC_DERIVED |
| BR-SEG143-009 | field | A merchant may end a product's tax entry early (before 3 taxes) by using a Field Separator after the last reported tax entry instead of the '\' delimiter | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG143-R-009` §12.30 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG143-NEG-001 | `SEG143-R-001` | MUT-010 structural requirement | Validation error citing SEG143-R-001 |
| BR-SEG143-NEG-002 | `SEG143-R-003` | MUT-001 wrong fixed value | Validation error citing SEG143-R-003 |
| BR-SEG143-NEG-003 | `SEG143-R-004` | MUT-003 length violation | Validation error citing SEG143-R-004 |
| BR-SEG143-NEG-004 | `SEG143-R-005` | MUT-003 length violation | Validation error citing SEG143-R-005 |
| BR-SEG143-NEG-005 | `SEG143-R-006` | MUT-010 structural requirement | Validation error citing SEG143-R-006 |
| BR-SEG143-NEG-006 | `SEG143-R-007` | MUT-004/MUT-008 value outside allowed set | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG143-NEG-007 | `SEG143-R-008` | MUT-002/MUT-006 format or charset violation | Validation error citing SEG143-R-008 |
| BR-SEG143-NEG-008 | `SEG143-R-009` | MUT-010 structural / separator violation | Validation error citing SEG143-R-009 |

## Requirements that must not be certified yet

- `BR-SEG143-007` (`SEG143-R-007`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG143-R-007): Are GST/HST/PST the only valid Tax Type values, or do non-Canadian jurisdictions use additional documented values not captured in this section?
- **P-02** (AI-artifacts, test-data): No dedicated Segment 143 AI or Test package was located. Provide one, or approve synthesized fixtures using the worked examples already in Section 12.30 (5 examples provided).

## Implementation traceability

- Rule catalog: [coverage/segment-143-rule-catalog.json](coverage/segment-143-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
