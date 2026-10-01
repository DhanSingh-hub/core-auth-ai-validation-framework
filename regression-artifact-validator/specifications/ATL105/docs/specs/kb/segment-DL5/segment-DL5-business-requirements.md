# Segment DL5 Business Requirements

**Segment:** DL5 — Software IP Load Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.46, 12.45, 11.7.4, 11.7.4.2, 10.10, 13.2  
**Oracle:** [segment-DL5-rule-catalog.json](coverage/segment-DL5-rule-catalog.json) (7 rules)  
**Benchmark:** [Segment 100 financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md)

## Rule composition

| Class | Rules |
|---|---:|
| applicability | 2 |
| structure | 1 |
| serialization | 1 |
| field | 2 |
| lifecycle | 1 |
| **Total** | **7** |

## Requirements

| ID | Class | Requirement | Valid representation | Invalid representation | Source | Status |
|---|---|---|---|---|---|---|
| BR-SEGDL5-001 | applicability | DL5 shall be sent only to BUYPASS-managed devices | BUYPASS-managed device | Vendor-managed device receives DL5 | `SEGDL5-R-001` §12.46 | SPEC_DERIVED |
| BR-SEGDL5-002 | structure | DL5 shall begin with `$`, end with `~`, and not exceed the maximum length | 64 characters | `@` first; 65 characters | `SEGDL5-R-002` §12.46 | REVIEW_REQUIRED (P-02) |
| BR-SEGDL5-003 | field | DL5 shall have DL4's field order with Element 114 in field 4 | `$` Ver RecID IP/URL Date Time Type `~` | Phone number in field 4 | `SEGDL5-R-003` §12.45-12.46 | SPEC_DERIVED |
| BR-SEGDL5-004 | serialization | DL5 fields shall be concatenated without Field Separators | Positional | FS after Record ID | `SEGDL5-R-004` §12.46 | SPEC_DERIVED |
| BR-SEGDL5-005 | field | IP/URL Address shall be exactly 30 characters; other fields as in DL4 | 30 characters | 29 or 31 characters; Load Type `X` | `SEGDL5-R-005` §13.2 | REVIEW_REQUIRED (P-03) |
| BR-SEGDL5-006 | applicability | DL5 shall be field 3 of the Software Load Response, after DL4, only with flag `SOFT` | `)` DL4 DL5 | DL5 alone; DL5 before DL4 | `SEGDL5-R-006` §11.7.4.2 | REVIEW_REQUIRED (`SEGDL4-SME-002`) |
| BR-SEGDL5-007 | lifecycle | The device shall make at most three scheduled IP load attempts and print the decline message on failure | 3 attempts | 4th attempt | `SEGDL5-R-007` §10.10 | SPEC_DERIVED (review severity) |

## Required negative coverage

| ID | Violates | Mutation class | Mutation | Expected result |
|---|---|---|---|---|
| BR-SEGDL5-NEG-001 | `SEGDL5-R-001` | MUT-009 Cross-field | Vendor-managed context + DL5 | Fail citing SEGDL5-R-001 |
| BR-SEGDL5-NEG-002 | `SEGDL5-R-002` | MUT-001 Identity value | `$` → `@` | Fail citing SEGDL5-R-002 |
| BR-SEGDL5-NEG-003 | `SEGDL5-R-003` | MUT-010 Structural | Swap fields 4 and 5 | Fail citing SEGDL5-R-003 |
| BR-SEGDL5-NEG-004 | `SEGDL5-R-004` | MUT-010 Structural | Insert FS | Fail citing SEGDL5-R-004 |
| BR-SEGDL5-NEG-005 | `SEGDL5-R-005` | MUT-003 Length | IP/URL 29 characters | Fail citing SEGDL5-R-005 |
| BR-SEGDL5-NEG-006 | `SEGDL5-R-006` | MUT-009 Cross-field | DL4 removed | Fail or REVIEW citing SEGDL5-R-006 |

## Coverage denominator

- **In scope:** all 7 catalog rules.
- **Out of scope:** IP transport to the device management system; the application image.
- **Required counts:** 7 BRs, ≥ 7 scenarios, ≥ 13 test cases, ≥ 13 request/response records.

## Open SME items

`SEGDL5-SME-001` to `SEGDL5-SME-003`, plus `SEGDL4-SME-002` (placement). See the [input register](segment-DL5-sme-tba-input-register.md).

## Implementation traceability

- Rule catalog: [coverage/segment-DL5-rule-catalog.json](coverage/segment-DL5-rule-catalog.json)
- Validator: _not yet implemented_ (`SegmentDL5PayloadValidator`, blocked on `SEGDL5-SME-003`)
