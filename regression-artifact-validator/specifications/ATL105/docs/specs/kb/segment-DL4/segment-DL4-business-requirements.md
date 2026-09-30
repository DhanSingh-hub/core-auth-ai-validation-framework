# Segment DL4 Business Requirements

**Segment:** DL4 — Software Dial Load Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.45, 11.7.4, 11.7.4.2, 10.10, 13.2  
**Oracle:** [segment-DL4-rule-catalog.json](coverage/segment-DL4-rule-catalog.json) (8 rules)  
**Benchmark:** [Segment 100 financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md)

## Rule composition

| Class | Rules |
|---|---:|
| applicability | 2 |
| structure | 1 |
| serialization | 1 |
| field | 2 |
| lifecycle | 2 |
| **Total** | **8** |

## Requirements

| ID | Class | Requirement | Valid representation | Invalid representation | Source | Status |
|---|---|---|---|---|---|---|
| BR-SEGDL4-001 | applicability | DL4 shall be sent only to BUYPASS-managed devices | BUYPASS-managed device receives DL4 | Vendor-managed device receives DL4 | `SEGDL4-R-001` §12.45 | SPEC_DERIVED |
| BR-SEGDL4-002 | structure | DL4 shall begin with `@`, end with `~`, and not exceed 52 characters | 52-character example | `$` first; 53 characters | `SEGDL4-R-002` §12.45 | SPEC_DERIVED |
| BR-SEGDL4-003 | field | All six schedule fields shall be present | All present | Request Time missing | `SEGDL4-R-003` §12.45 | SPEC_DERIVED |
| BR-SEGDL4-004 | serialization | DL4 fields shall be concatenated without Field Separators | `@APP02.10STR...` | FS after Version | `SEGDL4-R-004` §12.45 | REVIEW_REQUIRED (P-03) |
| BR-SEGDL4-005 | field | Version AN 8, Record ID AN 13, Phone ≤ 18, Date MMDDYY, Time HHMM, Load Type `F`/`P` | `APP02.10`, `101526`, `0200`, `F` | `APP2.1`, `2026-10-15`, `2:00`, `X` | `SEGDL4-R-005` §13.2 | REVIEW_REQUIRED (P-03) |
| BR-SEGDL4-006 | applicability | DL4 shall be Data Block 1 of the Software Load Response with DL5, only when the load flag is `SOFT` | `)` DL4 DL5 | DL4 without DL5; DL4 in Phone Load Response; flag not set | `SEGDL4-R-006` §11.7.4.2 | REVIEW_REQUIRED (P-02) |
| BR-SEGDL4-007 | lifecycle | Software update processing shall follow profile bit → Download Indicator 1 → load request → DL4/DL5 → scheduled load → Table Load | Full sequence | DL4 without prior Download Indicator 1 | `SEGDL4-R-007` §10.10 | REVIEW_REQUIRED (P-02) |
| BR-SEGDL4-008 | lifecycle | The device shall make at most three automated load attempts and print the decline message on failure | 3 attempts then decline | 4th attempt | `SEGDL4-R-008` §10.10 | SPEC_DERIVED (review severity) |

## Required negative coverage

| ID | Violates | Mutation class | Mutation | Expected result |
|---|---|---|---|---|
| BR-SEGDL4-NEG-001 | `SEGDL4-R-001` | MUT-009 Cross-field | Vendor-managed context + DL4 | Fail citing SEGDL4-R-001 |
| BR-SEGDL4-NEG-002 | `SEGDL4-R-002` | MUT-001 Identity value | `@` → `$` | Fail citing SEGDL4-R-002 |
| BR-SEGDL4-NEG-003 | `SEGDL4-R-003` | MUT-005 Required field omitted | Remove Software Terminal Record ID | Fail citing SEGDL4-R-003 |
| BR-SEGDL4-NEG-004 | `SEGDL4-R-004` | MUT-010 Structural | Insert FS | Fail citing SEGDL4-R-004 |
| BR-SEGDL4-NEG-005 | `SEGDL4-R-005` | MUT-008 Enumeration | Software Load Type `X` | Fail citing SEGDL4-R-005 |
| BR-SEGDL4-NEG-006 | `SEGDL4-R-006` | MUT-009 Cross-field | Remove DL5 from the response | Fail or REVIEW citing SEGDL4-R-006 |
| BR-SEGDL4-NEG-007 | `SEGDL4-R-007` | MUT-009 Cross-field | No Download Indicator 1 before the load | REVIEW citing SEGDL4-R-007 |

## Coverage denominator

- **In scope:** all 8 catalog rules (`R-008` as a review item).
- **Out of scope:** the application image download itself and the device-management system protocol.
- **Required counts:** 8 BRs, ≥ 8 scenarios, ≥ 15 test cases, ≥ 15 request/response records.

## Open SME items

`SEGDL4-SME-001` to `SEGDL4-SME-003`. See the [input register](segment-DL4-sme-tba-input-register.md).

## Implementation traceability

- Rule catalog: [coverage/segment-DL4-rule-catalog.json](coverage/segment-DL4-rule-catalog.json)
- Validator: _not yet implemented_ (`SegmentDL4PayloadValidator`, blocked on `SEGDL4-SME-003`)
