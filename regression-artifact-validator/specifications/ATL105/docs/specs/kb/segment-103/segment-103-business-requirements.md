# Segment 103 Business Requirements

**Segment:** 103 — EBT Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.5.2, 10.5.3, 10.5.4.8, 10.5.5, 10.5.5.1, 10.5.5.2, 10.5.6, 11.1, 12, 12.4, 13.2, Appendix M  
**Oracle:** [segment-103-rule-catalog.json](coverage/segment-103-rule-catalog.json) (24 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 103 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 103 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 13 |
| serialization | 4 |
| structure | 2 |
| applicability | 2 |
| metadata | 1 |
| lifecycle | 1 |
| compatibility | 1 |
| **Total** | **24** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG103-001 | structure | Segment 103 is a Data Section 3 companion segment usable in any Data Section 3 field slot | The segment's structural position and composition match the rule. | `SEG103-R-001` §11.1,12.4 | SPEC_DERIVED |
| BR-SEG103-002 | applicability | Segment 103 is required when the transaction flow needs EBT-specific data; the applicability matrix is fully enumerated in SEG103-R-024 and Segment103ApplicabilityValidator | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG103-R-002` §12.4,10.5.3,10.5.4.8,10.5.5,10.5.6 | SPEC_DERIVED |
| BR-SEG103-003 | field | Segment Type is 103 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-003` §12.4 | SPEC_DERIVED |
| BR-SEG103-004 | field | Segment Length is 3 or 4 digits; 4 digits is required for EBT-with-eWIC transactions | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-004` §12.4 | SPEC_DERIVED |
| BR-SEG103-005 | serialization | Segment 103 maximum length is 3,334 alphanumeric characters | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG103-R-005` §12.4 | SPEC_DERIVED |
| BR-SEG103-006 | serialization | Field order matches Section 12.4 (Segment Type, Segment Length, Clerk ID, Voucher ID, WIC Discount Amount, WIC Product Data, EBT Program Data) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG103-R-006` §12.4 | SPEC_DERIVED |
| BR-SEG103-007 | serialization | Request serialization: every Segment 103 field is separated by a Field Separator, including empty fields | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG103-R-007` §12.4 | SPEC_DERIVED |
| BR-SEG103-008 | serialization | Response serialization: there is no Field Separator between Segment 103 fields | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG103-R-008` §12.4 | SPEC_DERIVED |
| BR-SEG103-009 | field | Clerk ID, when populated, is numeric with maximum length 10 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-009` §12.4 | SPEC_DERIVED |
| BR-SEG103-010 | field | Voucher ID, when populated, is numeric with maximum length 10 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-010` §12.4 | SPEC_DERIVED |
| BR-SEG103-011 | field | WIC Discount Amount uses the positional format Account Type(97) + Amount Type(52) + Currency Code + signed Amount | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-011` §13.2 | SPEC_DERIVED |
| BR-SEG103-012 | field | WIC Discount Amount maximum length is 40 bytes, composed of one or more 20-byte positional blocks | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-012` §13.2 | SPEC_DERIVED |
| BR-SEG103-013 | field | WIC Product Data is bounded to 3,001 bytes and begins with a 4-digit Total Length subelement with maximum value 2997 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-013` §13.2 | SPEC_DERIVED |
| BR-SEG103-014 | field | EBT Program Data is bounded to 267 bytes and begins with a 3-digit Total Length subelement with maximum value 264 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-014` §13.2 | SPEC_DERIVED |
| BR-SEG103-015 | field | EBT Program Data contains 1 to 6 Program Data subelements, each bounded to 44 bytes | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-015` §13.2 | SPEC_DERIVED |
| BR-SEG103-016 | field | EBT Program Data subelement TAG must be one of the documented values (50, IT for requests; 51, 52 for responses) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-016` §13.2 | SPEC_DERIVED |
| BR-SEG103-017 | field | EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE fixed value 98 (not required when TAG is IT) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-017` §13.2 | SPEC_DERIVED |
| BR-SEG103-018 | structure | Segment 103 appears exactly once per message | The segment's structural position and composition match the rule. | `SEG103-R-018` §12 | SPEC_DERIVED |
| BR-SEG103-019 | metadata | Segment 103 originates at the device | Documented for traceability; not independently asserted by a validator. | `SEG103-R-019` §12.4 | SPEC_DERIVED |
| BR-SEG103-020 | lifecycle | eWIC does not support Return transactions (absolute prohibition, not a code lookup) | Paired messages are present and the correlated values agree. | `SEG103-R-020` §10.5.5.1 | SPEC_DERIVED |
| BR-SEG103-021 | compatibility | eWIC operations use a recognized prompt-code set (3086, S086, E086, 0086, 8086) carried in the companion Segment 100 Prompt Code | Only the permitted companion segments / message families carry this segment. | `SEG103-R-021` §10.5.5.2 | SPEC_DERIVED |
| BR-SEG103-022 | field | EBT Program Data subelement layout: fixed ACCOUNT TYPE/CURRENCY/DESCRIPTOR and 12-digit detail for TAG 50/51/52; 28-character address and up-to-9-digit ZIP for TAG IT. Request TAG 50 AMOUNT TYPE remains disputed (`SEG103-SME-009`). | Validate source-set positional fields; keep TAG 50 AMOUNT TYPE semantics `REVIEW_REQUIRED` until SME/TBA resolves Appendix M vs §13.2. | `SEG103-R-022` §13.2, Appendix M | SPEC_DERIVED_REVIEW_REQUIRED |
| BR-SEG103-023 | field | WIC Product Data subelement catalog: EF (Earliest WIC Benefit Expiration Date, 8 bytes), EA (WIC Prescription Balance Information, up to 14 bytes), PS (WIC UPC Exception/Denial or Purchase Information, up to 47 bytes) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG103-R-023` §13.2 | SPEC_DERIVED |
| BR-SEG103-024 | applicability | Segment 103 applicability matrix: REQUIRED for Food Stamp Electronic Voucher (Voucher ID) and eWIC Purchase Completion/Voucher Clear (WIC data); OPTIONAL for all other EBT/eWIC transaction types listed in Section 10.5.3; PROHIBITED for eWIC Return | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG103-R-024` §10.5.2,10.5.3,10.5.4.8,10.5.5,10.5.6 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG103-NEG-001 | `SEG103-R-001` | MUT-010 structural requirement | Validation error citing SEG103-R-001 |
| BR-SEG103-NEG-002 | `SEG103-R-002` | MUT-005 required field omitted | Validation error citing SEG103-R-002 |
| BR-SEG103-NEG-003 | `SEG103-R-003` | MUT-001 wrong fixed value | Validation error citing SEG103-R-003 |
| BR-SEG103-NEG-004 | `SEG103-R-004` | MUT-003 length violation | Validation error citing SEG103-R-004 |
| BR-SEG103-NEG-005 | `SEG103-R-005` | MUT-003 length violation | Validation error citing SEG103-R-005 |
| BR-SEG103-NEG-006 | `SEG103-R-006` | MUT-001 wrong fixed value | Validation error citing SEG103-R-006 |
| BR-SEG103-NEG-007 | `SEG103-R-007` | MUT-010 structural / separator violation | Validation error citing SEG103-R-007 |
| BR-SEG103-NEG-008 | `SEG103-R-008` | MUT-010 structural / separator violation | Validation error citing SEG103-R-008 |
| BR-SEG103-NEG-009 | `SEG103-R-009` | MUT-003 length violation | Validation error citing SEG103-R-009 |
| BR-SEG103-NEG-010 | `SEG103-R-010` | MUT-003 length violation | Validation error citing SEG103-R-010 |
| BR-SEG103-NEG-011 | `SEG103-R-011` | MUT-010 structural requirement | Validation error citing SEG103-R-011 |
| BR-SEG103-NEG-012 | `SEG103-R-012` | MUT-003 length violation | Validation error citing SEG103-R-012 |
| BR-SEG103-NEG-013 | `SEG103-R-013` | MUT-003 length violation | Validation error citing SEG103-R-013 |
| BR-SEG103-NEG-014 | `SEG103-R-014` | MUT-003 length violation | Validation error citing SEG103-R-014 |
| BR-SEG103-NEG-015 | `SEG103-R-015` | MUT-003 length violation | Validation error citing SEG103-R-015 |
| BR-SEG103-NEG-016 | `SEG103-R-016` | MUT-004/MUT-008 value outside allowed set | Validation error citing SEG103-R-016 |
| BR-SEG103-NEG-017 | `SEG103-R-017` | MUT-001 wrong fixed value | Validation error citing SEG103-R-017 |
| BR-SEG103-NEG-018 | `SEG103-R-018` | MUT-010 structural requirement | Validation error citing SEG103-R-018 |
| BR-SEG103-NEG-019 | `SEG103-R-020` | MUT-010 structural requirement | Validation error citing SEG103-R-020 |
| BR-SEG103-NEG-020 | `SEG103-R-021` | MUT-010 structural requirement | Validation error citing SEG103-R-021 |
| BR-SEG103-NEG-021 | `SEG103-R-022` | MUT-010 structural requirement | Validation error citing SEG103-R-022 |
| BR-SEG103-NEG-022 | `SEG103-R-023` | MUT-003 length violation | Validation error citing SEG103-R-023 |
| BR-SEG103-NEG-023 | `SEG103-R-024` | MUT-005 required field omitted | Validation error citing SEG103-R-024 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-07** (AI-artifacts): Location of AI-generated Segment 103 BR/TS/TC/TD packages (none supplied as of this training pass). Not resolvable from the specification text; requires the AI/producer team to deliver real artifacts.
- **P-08** (test-data): Availability of real Segment 103 sample JSONs, or continued approval to use synthesized .synthetic.json fixtures. Not resolvable from the specification text; requires production/test-data availability.

## Implementation traceability

- Rule catalog: [coverage/segment-103-rule-catalog.json](coverage/segment-103-rule-catalog.json)
- Validator: `Segment103PayloadValidator`
- Tests: `Segment103ApplicabilityValidatorTest`, `Segment103ArtifactComparisonTest`, `Segment103ConsolidatedReportTest`, `Segment103IndependenceValidatorTest`, `Segment103MutationTestRunnerTest`, `Segment103MutationTesterTest`, `Segment103PayloadValidatorTest`, `Segment103TraceabilityMatrixTest`, `Segment103WireFormatValidatorTest`
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
