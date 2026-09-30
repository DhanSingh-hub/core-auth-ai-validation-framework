# Segment 155 Business Requirements

**Segment:** 155 — Real Time Account Updater Response Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.39  
**Oracle:** [segment-155-rule-catalog.json](coverage/segment-155-rule-catalog.json) (6 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 155 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 155 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| applicability | 2 |
| field | 2 |
| serialization | 1 |
| metadata | 1 |
| **Total** | **6** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG155-001 | applicability | Segment 155 can only be sent when the transaction qualifies for, and uses, the First Data Auth Optimizer service | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG155-R-001` §12.39 | SPEC_DERIVED |
| BR-SEG155-002 | serialization | Segment 155 uses NO Field Separators — fixed-length/positional, same pattern as Segments 131/134 | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG155-R-002` §12.39 | SPEC_DERIVED |
| BR-SEG155-003 | applicability | Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG155-R-003` §12.39 | SPEC_DERIVED |
| BR-SEG155-004 | field | Card Status (Table 004) valid values: A (New Acct# & Exp Date), E (New Exp Date), Q (Contact Cardholder), C (Closed), U (Unknown Card) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG155-R-004` §12.39 | SPEC_DERIVED |
| BR-SEG155-005 | field | Account Updater Result Code (Table 006) is a fixed 6-character code from a documented enumeration (VAU001 through VAU016, non-contiguous — VAU015 absent), applicable only to Visa | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG155-R-005` §12.39 | SPEC_DERIVED |
| BR-SEG155-006 | metadata | Segment Type fixed 155; the field table for fields 1-2 does not list a Source (unlike every other segment trained), an editorial omission | Documented for traceability; not independently asserted by a validator. | `SEG155-R-006` §12.39 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG155-NEG-001 | `SEG155-R-001` | MUT-010 structural requirement | Validation error citing SEG155-R-001 |
| BR-SEG155-NEG-002 | `SEG155-R-002` | MUT-003 length violation | Validation error citing SEG155-R-002 |
| BR-SEG155-NEG-003 | `SEG155-R-003` | MUT-010 structural requirement | Validation error citing SEG155-R-003 |
| BR-SEG155-NEG-004 | `SEG155-R-004` | MUT-004/MUT-008 value outside allowed set | Validation error citing SEG155-R-004 |
| BR-SEG155-NEG-005 | `SEG155-R-005` | MUT-002/MUT-006 format or charset violation | Validation error citing SEG155-R-005 |

## Requirements that must not be certified yet

_None — every rule is directly specification-derived._

## Open SME items

- **P-01** (SEG155-R-005): Confirm VAU015 is intentionally absent from the Account Updater Result Code enumeration, not a transcription gap.
- **P-02** (AI-artifacts, test-data): No dedicated Segment 155 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-155-rule-catalog.json](coverage/segment-155-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
