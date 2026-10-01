# Segment DL8 Business Requirements

**Segment:** DL8 — EMV Terminal Floor Limits Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.49, 13.2  
**Oracle:** [segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md)

## Rule composition

| Class | Rules |
|---|---:|
| lifecycle | 1 |
| structure | 3 |
| field | 1 |
| **Total** | **5** |

## Requirements

| ID | Class | Requirement | Valid representation | Invalid representation | Source | Status |
|---|---|---|---|---|---|---|
| BR-SEGDL8-001 | lifecycle | DL8 shall be included in a table load only for terminals with the Special, and a data change shall set their table download flag | Special terminal receives DL8 | Non-Special terminal receives DL8 | `SEGDL8-R-001` §12.49 | REVIEW_REQUIRED (P-02) |
| BR-SEGDL8-002 | structure | DL8 shall begin with `%`, carry a Segment Length excluding `%`, and have no `~` | `%052…` | `%…~`; `^` first | `SEGDL8-R-002` §12.49 | REVIEW_REQUIRED (P-02) |
| BR-SEGDL8-003 | structure | DL8 shall carry 1-24 Floor Limit Data groups, ≤ 624 bytes | 2 groups (52 bytes) | 0 or 25 groups | `SEGDL8-R-003` §12.49 | SPEC_DERIVED |
| BR-SEGDL8-004 | field | Each group: RID 10, Stand-in 1/2/3, Floor Limit 12 digits, Card Type 3 | `A0000000033000000005000020` | Stand-in `4`; Floor Limit `5000.00` | `SEGDL8-R-004` §13.2 | REVIEW_REQUIRED (P-03) |
| BR-SEGDL8-005 | structure | Segment Length shall be three digits and describe whole 26-byte groups | `052` for 2 groups | `51`; `053` for 2 groups | `SEGDL8-R-005` §13.2 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Mutation | Expected result |
|---|---|---|---|---|
| BR-SEGDL8-NEG-001 | `SEGDL8-R-001` | MUT-009 Cross-field | Terminal without Special + DL8 | Fail citing SEGDL8-R-001 |
| BR-SEGDL8-NEG-002 | `SEGDL8-R-002` | MUT-001 Identity value | `%` → `^` | Fail citing SEGDL8-R-002 |
| BR-SEGDL8-NEG-003 | `SEGDL8-R-003` | MUT-003 Length | 25 groups (650 bytes) | Fail citing SEGDL8-R-003 |
| BR-SEGDL8-NEG-004 | `SEGDL8-R-004` | MUT-008 Enumeration | Stand-in Indicator `4` | Fail citing SEGDL8-R-004 |
| BR-SEGDL8-NEG-005 | `SEGDL8-R-005` | MUT-009 Cross-field | Remove the last Card Type byte | Fail citing SEGDL8-R-005 |

## Coverage denominator

- **In scope:** all 5 catalog rules.
- **Out of scope:** EMV kernel behaviour at the terminal; Segment 130 chip data.
- **Required counts:** 5 BRs, ≥ 5 scenarios, ≥ 10 test cases, ≥ 10 test-data records.

## Open SME items

`SEGDL8-SME-001` to `SEGDL8-SME-003`, plus `SEGDL7-SME-005` (shared Segment Length counting). See the [input register](segment-DL8-sme-tba-input-register.md).

## Implementation traceability

- Rule catalog: [coverage/segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json)
- Validator: _not yet implemented_ (`SegmentDL8PayloadValidator`, blocked on `SEGDL8-SME-003`)
