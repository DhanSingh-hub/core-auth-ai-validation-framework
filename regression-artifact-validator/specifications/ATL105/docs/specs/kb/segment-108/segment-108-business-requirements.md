# Segment 108 Business Requirements

**Segment:** 108 — Loyalty Card Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.9.1.2, 11.2.1, 11.2.2, 12, 12.7, 13.2  
**Oracle:** [segment-108-rule-catalog.json](coverage/segment-108-rule-catalog.json) (24 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 108 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 108 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 15 |
| serialization | 3 |
| structure | 2 |
| applicability | 1 |
| metadata | 1 |
| compatibility | 1 |
| lifecycle | 1 |
| **Total** | **24** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG108-001 | structure | Segment 108 belongs exclusively to the Loyalty Card Transaction Request; it is not a Financial Transaction Request companion | The segment's structural position and composition match the rule. | `SEG108-R-001` §11.2.1,13.2 | SPEC_DERIVED |
| BR-SEG108-002 | applicability | Segment 108 is required in every Loyalty Card Transaction Request | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG108-R-002` §11.2.1 | SPEC_DERIVED |
| BR-SEG108-003 | field | Segment Type is 108 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-003` §12.7 | SPEC_DERIVED |
| BR-SEG108-004 | field | Segment Length is 3 digits representing the segment content length | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-004` §12.7 | SPEC_DERIVED |
| BR-SEG108-005 | serialization | Segment 108 maximum length is 142 alphanumeric characters | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG108-R-005` §12.7 | SPEC_DERIVED |
| BR-SEG108-006 | serialization | Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG108-R-006` §12.7 | SPEC_DERIVED |
| BR-SEG108-007 | serialization | All fields are separated by Field Separators, including a separator following the last field; empty fields still send the separator | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG108-R-007` §12.7 | SPEC_DERIVED |
| BR-SEG108-008 | field | Loyalty Program ID, required, is numeric with maximum length 6 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-008` §12.7 | SPEC_DERIVED |
| BR-SEG108-009 | field | Loyalty Account Number, when populated, is numeric with maximum length 24 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-009` §12.7 | SPEC_DERIVED |
| BR-SEG108-010 | field | Points to Redeem, when populated, is numeric with maximum length 6 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-010` §12.7 | SPEC_DERIVED |
| BR-SEG108-011 | field | Coupon ID, when populated, is numeric with maximum length 19 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-011` §12.7 | SPEC_DERIVED |
| BR-SEG108-012 | field | Coupon Amount, when populated, is numeric with maximum length 8 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-012` §12.7 | SPEC_DERIVED |
| BR-SEG108-013 | field | Update Code, when populated, is exactly one alphanumeric character from the documented set A, C, E, I, P, S, T, U | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-013` §13.2 | REVIEW_REQUIRED |
| BR-SEG108-014 | field | Street Address, when populated, is numeric with maximum length 5 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-014` §12.7 | SPEC_DERIVED |
| BR-SEG108-015 | field | Phone Number, Loyalty, when populated, is numeric with maximum length 10 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-015` §12.7 | SPEC_DERIVED |
| BR-SEG108-016 | field | Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-016` §10.9.1.2,13.2 | SPEC_DERIVED |
| BR-SEG108-017 | field | Payment Tender Type is required, exactly 2 alphanumeric characters from the documented set AX, CK, CS, DB, DN, DS, EB, EC, FL, GC, JC, MC, PC, PR, VS | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-017` §13.2 | SPEC_DERIVED |
| BR-SEG108-018 | field | Loyalty Track 2 Data, when populated, is alphanumeric with maximum length 38 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-018` §12.7 | SPEC_DERIVED |
| BR-SEG108-019 | field | Loyalty Information Version, when populated, is numeric length 1 with valid values 1 or 2; defaults to 1 when not sent | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-019` §13.2 | SPEC_DERIVED |
| BR-SEG108-020 | field | Unit of Work, when populated, is numeric with fixed length 19; required on loyalty reversals to match the original purchase | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG108-R-020` §13.2 | SPEC_DERIVED |
| BR-SEG108-021 | structure | Segment 108 appears exactly once per message | The segment's structural position and composition match the rule. | `SEG108-R-021` §12 | SPEC_DERIVED |
| BR-SEG108-022 | metadata | Segment 108 originates at the device | Documented for traceability; not independently asserted by a validator. | `SEG108-R-022` §12.7 | SPEC_DERIVED |
| BR-SEG108-023 | compatibility | Segment 108's sole optional Data Section 3 companion is Segment 114 (SKU Data Segment) | Only the permitted companion segments / message families carry this segment. | `SEG108-R-023` §11.2.1 | SPEC_DERIVED |
| BR-SEG108-024 | lifecycle | Segment 108 does not appear in the Loyalty Card Transaction Response, which mirrors the generic Financial Transaction Response layout | Paired messages are present and the correlated values agree. | `SEG108-R-024` §11.2.2 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG108-NEG-001 | `SEG108-R-001` | MUT-010 structural requirement | Validation error citing SEG108-R-001 |
| BR-SEG108-NEG-002 | `SEG108-R-002` | MUT-005 required field omitted | Validation error citing SEG108-R-002 |
| BR-SEG108-NEG-003 | `SEG108-R-003` | MUT-001 wrong fixed value | Validation error citing SEG108-R-003 |
| BR-SEG108-NEG-004 | `SEG108-R-004` | MUT-003 length violation | Validation error citing SEG108-R-004 |
| BR-SEG108-NEG-005 | `SEG108-R-005` | MUT-003 length violation | Validation error citing SEG108-R-005 |
| BR-SEG108-NEG-006 | `SEG108-R-006` | MUT-001 wrong fixed value | Validation error citing SEG108-R-006 |
| BR-SEG108-NEG-007 | `SEG108-R-007` | MUT-010 structural / separator violation | Validation error citing SEG108-R-007 |
| BR-SEG108-NEG-008 | `SEG108-R-008` | MUT-003 length violation | Validation error citing SEG108-R-008 |
| BR-SEG108-NEG-009 | `SEG108-R-009` | MUT-003 length violation | Validation error citing SEG108-R-009 |
| BR-SEG108-NEG-010 | `SEG108-R-010` | MUT-003 length violation | Validation error citing SEG108-R-010 |
| BR-SEG108-NEG-011 | `SEG108-R-011` | MUT-003 length violation | Validation error citing SEG108-R-011 |
| BR-SEG108-NEG-012 | `SEG108-R-012` | MUT-003 length violation | Validation error citing SEG108-R-012 |
| BR-SEG108-NEG-013 | `SEG108-R-013` | MUT-002/MUT-006 format or charset violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG108-NEG-014 | `SEG108-R-014` | MUT-003 length violation | Validation error citing SEG108-R-014 |
| BR-SEG108-NEG-015 | `SEG108-R-015` | MUT-003 length violation | Validation error citing SEG108-R-015 |
| BR-SEG108-NEG-016 | `SEG108-R-016` | MUT-003 length violation | Validation error citing SEG108-R-016 |
| BR-SEG108-NEG-017 | `SEG108-R-017` | MUT-005 required field omitted | Validation error citing SEG108-R-017 |
| BR-SEG108-NEG-018 | `SEG108-R-018` | MUT-003 length violation | Validation error citing SEG108-R-018 |
| BR-SEG108-NEG-019 | `SEG108-R-019` | MUT-003 length violation | Validation error citing SEG108-R-019 |
| BR-SEG108-NEG-020 | `SEG108-R-020` | MUT-003 length violation | Validation error citing SEG108-R-020 |
| BR-SEG108-NEG-021 | `SEG108-R-021` | MUT-010 structural requirement | Validation error citing SEG108-R-021 |
| BR-SEG108-NEG-022 | `SEG108-R-023` | MUT-010 structural requirement | Validation error citing SEG108-R-023 |
| BR-SEG108-NEG-023 | `SEG108-R-024` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG108-013` (`SEG108-R-013`) — REVIEW_REQUIRED
- `BR-SEG108-024` (`SEG108-R-024`) — REVIEW_REQUIRED

## Open SME items

- **P-02** (SEG108-R-013): Section 10.9.3 describes 9-10 distinct loyalty advice functions (including 'Reversal of coupon redeem' and 'Reversal of points redeemed') but Element 143 only documents 8 Update Code values (A,C,E,I,P,S,T,U). Which code(s) represent the two reversal functions?
- **P-03** (SEG108-R-024): User indicated Segment 108 (or loyalty data) DOES appear in some response scenario(s), contradicting the literal Section 11.2.2 text, but could not yet specify which scenario(s) or field layout. Needs a follow-up answer with a section/page reference before SEG108-R-024 can be enforced with confidence.
- **P-04** (receipt/print-data, not segment-108 wire format): Appendix K Table 008 (Loyalty Information - Version 1) and Table 010 (Loyalty Information - Version 2), referenced by Section 10.9.4 for loyalty receipts, are not deeply transcribed in this KB pass. Are these in scope for Segment 108 training, or a separate Appendix K workstream?
- **P-07** (AI-artifacts): Location of AI-generated Segment 108 BR/TS/TC/TD packages (none supplied as of this training pass).
- **P-08** (test-data): Availability of real Segment 108 sample JSONs, or approval to continue with synthesized .synthetic.json fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-108-rule-catalog.json](coverage/segment-108-rule-catalog.json)
- Validator: `Segment108PayloadValidator`
- Tests: `Segment108ArtifactComparisonTest`, `Segment108ConsolidatedReportTest`, `Segment108IndependenceValidatorTest`, `Segment108MutationTestRunnerTest`, `Segment108MutationTesterTest`, `Segment108PayloadValidatorTest`, `Segment108TraceabilityMatrixTest`
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
