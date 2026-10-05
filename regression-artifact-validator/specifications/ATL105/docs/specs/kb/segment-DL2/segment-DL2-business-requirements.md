# Segment DL2 Business Requirements

**Segment:** DL2 — Dial String Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.43, 11.7.1.2, 11.7.2, 11.7.2.2, 13.2  
**Oracle:** [segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json) (9 rules)  
**Benchmark:** [Segment 100 financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md)

## Scope and provenance

Each requirement restates one catalog rule as an independently testable statement. Requirements come only from the ATL105 specification; AI artifacts are not requirement evidence. Rules linked to an open provisional item stay `REVIEW_REQUIRED`.

## Rule composition

| Class | Rules |
|---|---:|
| structure | 1 |
| serialization | 1 |
| field | 3 |
| conditional | 1 |
| applicability | 1 |
| lifecycle | 2 |
| **Total** | **9** |

## Requirements

| ID | Class | Requirement | Valid representation | Invalid representation | Source | Status |
|---|---|---|---|---|---|---|
| BR-SEGDL2-001 | structure | DL2 shall begin with `!`, end with `~`, and not exceed 69 characters | `!1...F~` (29 chars) | Missing `~`; 70 characters | `SEGDL2-R-001` §12.43 | SPEC_DERIVED |
| BR-SEGDL2-002 | field | Dial String Type shall be `1` and the primary block shall end with `A` | `!1` … `A` | `!2`; primary without `A` | `SEGDL2-R-002` §12.43 | SPEC_DERIVED |
| BR-SEGDL2-003 | lifecycle | The secondary block shall mirror the primary and end with `F`; the secondary number is dialed only after primary attempts are exhausted | Secondary `2 5555550199 F` | Secondary ending `A`; secondary dialed first | `SEGDL2-R-003` §12.43, §13.2 | REVIEW_REQUIRED (P-01) |
| BR-SEGDL2-004 | serialization | DL2 fields shall be concatenated without Field Separators | `!13` … | FS between Redial Count and Phone Number | `SEGDL2-R-004` §12.43 | REVIEW_REQUIRED (P-04) |
| BR-SEGDL2-005 | conditional | Access Code and Pause Indicator shall be both present or both absent in each block | `39B5555550100A`; `35555550100A` | `395555550100A` (code, no `B`) | `SEGDL2-R-005` §13.2 | REVIEW_REQUIRED (P-04) |
| BR-SEGDL2-006 | field | Redial Count shall be 1, 2 or 3 in each block | `1`, `3` | `0`, `4`, `A` | `SEGDL2-R-006` §13.2 | SPEC_DERIVED |
| BR-SEGDL2-007 | field | Phone Number shall be 1-18 digits; terminators shall be `A` then `F` | 10-digit number | 19 digits; letters in number; `F` before `A` | `SEGDL2-R-007` §13.2 | REVIEW_REQUIRED (P-04) |
| BR-SEGDL2-008 | applicability | DL2 shall appear only in a Table Load Response (Block 2, optional) or a Phone Load Response (required) | Phone Load Response with DL2 | Software Load Response with DL2; Phone Load Response without DL2 | `SEGDL2-R-008` §11.7.2.2 | REVIEW_REQUIRED (P-03) |
| BR-SEGDL2-009 | lifecycle | A Phone Load Response with DL2 shall be returned only when the load flag is `PHON` | Flag `PHON` → DL2 | Flag not set → DL2 | `SEGDL2-R-009` §11.7.2 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Mutation | Expected result |
|---|---|---|---|---|
| BR-SEGDL2-NEG-001 | `SEGDL2-R-001` | MUT-001 Identity value | `!` → `#` | Fail citing SEGDL2-R-001 |
| BR-SEGDL2-NEG-002 | `SEGDL2-R-002` | MUT-008 Enumeration | Dial String Type `2` | Fail citing SEGDL2-R-002 |
| BR-SEGDL2-NEG-003 | `SEGDL2-R-003` | MUT-010 Structural | Secondary terminator `A` | Fail citing SEGDL2-R-003 |
| BR-SEGDL2-NEG-004 | `SEGDL2-R-004` | MUT-010 Structural | Insert FS | Fail citing SEGDL2-R-004 |
| BR-SEGDL2-NEG-005 | `SEGDL2-R-005` | MUT-009 Cross-field | Access Code without `B` | Fail citing SEGDL2-R-005 |
| BR-SEGDL2-NEG-006 | `SEGDL2-R-006` | MUT-008 Enumeration | Redial Count `4` | Fail citing SEGDL2-R-006 |
| BR-SEGDL2-NEG-007 | `SEGDL2-R-007` | MUT-003 Length | 19-digit Phone Number | Fail citing SEGDL2-R-007 |
| BR-SEGDL2-NEG-008 | `SEGDL2-R-008` | MUT-010 Structural | DL2 in Software Load Response | Fail citing SEGDL2-R-008 |
| BR-SEGDL2-NEG-009 | `SEGDL2-R-009` | MUT-009 Cross-field | Flag not `PHON`, DL2 returned | Fail citing SEGDL2-R-009 |

## Coverage denominator

- **In scope:** all 9 catalog rules.
- **Out of scope:** modem dialing behaviour (Hayes comma substitution), error-recovery timing from the Asynchronous Communications Protocol Specifications, tertiary numbers.
- **Required counts:** 9 BRs, ≥ 9 scenarios, ≥ 18 test cases, ≥ 18 request/response test-data records.

## Open SME items

`SEGDL2-SME-001` (fallback document), `SEGDL2-SME-002` (package), `SEGDL2-SME-003` (Phone Load framing), `SEGDL2-SME-004` (parse boundary). See the [input register](segment-DL2-sme-tba-input-register.md).

## Implementation traceability

- Rule catalog: [coverage/segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json)
- Validator: _not yet implemented_ (`SegmentDL2PayloadValidator`, blocked on `SEGDL2-SME-004`)
- Candidate coverage package: [coverage/README.md](coverage/README.md) · [BR/TS/TC/test-data JSON](../../../../test-output/test-json/segment-DL2-coverage-package.json)
