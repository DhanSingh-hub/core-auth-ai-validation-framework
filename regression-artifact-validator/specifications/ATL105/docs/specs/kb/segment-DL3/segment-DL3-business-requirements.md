# Segment DL3 Business Requirements

**Segment:** DL3 — Date and Time Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.44, 11.7.1.2, 11.7.3, 11.7.3.2, 13.2  
**Oracle:** [segment-DL3-rule-catalog.json](coverage/segment-DL3-rule-catalog.json) (9 rules)  
**Benchmark:** [Segment 100 financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md)

## Scope and provenance

Each requirement restates one catalog rule. Requirements come only from the ATL105 specification. Rules linked to an open provisional item stay `REVIEW_REQUIRED`.

## Rule composition

| Class | Rules |
|---|---:|
| structure | 1 |
| serialization | 1 |
| field | 4 |
| applicability | 1 |
| lifecycle | 2 |
| **Total** | **9** |

## Requirements

| ID | Class | Requirement | Valid representation | Invalid representation | Source | Status |
|---|---|---|---|---|---|---|
| BR-SEGDL3-001 | structure | DL3 shall begin with `:`, end with `~`, and be 23 characters | `:309302614052300  4821~` (23 chars) | 22 chars without `~` in a Table Load | `SEGDL3-R-001` §12.44 | REVIEW_REQUIRED (P-03) |
| BR-SEGDL3-002 | field | Day of the Week, Current Date, Current Time and Cut Time shall all be present | All four present | Cut Time missing | `SEGDL3-R-002` §12.44 | SPEC_DERIVED |
| BR-SEGDL3-003 | field | Password shall be treated per its source (Device per 12.44) | — | — | `SEGDL3-R-003` §12.44 | REVIEW_REQUIRED (P-02) |
| BR-SEGDL3-004 | serialization | DL3 fields shall be concatenated without Field Separators | `:3093026...` | FS between Date and Time | `SEGDL3-R-004` §12.44 | SPEC_DERIVED |
| BR-SEGDL3-005 | field | Day 0-6; Date MMDDYY; Time and Cut Time HHMM | `3`, `093026`, `1405`, `2300` | `7`, `2026-09-30`, `14:05`, `2460` | `SEGDL3-R-005` §13.2 | REVIEW_REQUIRED (P-04) |
| BR-SEGDL3-006 | field | Password shall be up to six digits, right-aligned and space-filled | `  4821`, `482193` | `4821  `, `48A193` | `SEGDL3-R-006` §13.2 | REVIEW_REQUIRED (P-02) |
| BR-SEGDL3-007 | applicability | DL3 shall appear only in a Table Load Response (Block 3, optional) or a Date and Time Load Response | Date and Time Load Response with DL3 fields | DL3 in a Phone Load Response | `SEGDL3-R-007` §11.7.3.2 | REVIEW_REQUIRED (P-03) |
| BR-SEGDL3-008 | lifecycle | A Date and Time Load Request with Load Type `D` shall receive DL3 without any load flag | Request `?` + TID + `D` → DL3 | Load Type `P` treated as Date and Time Load | `SEGDL3-R-008` §11.7.3 | SPEC_DERIVED |
| BR-SEGDL3-009 | lifecycle | A device with automatic cut time shall start settlement 30 minutes before Cut Time if not already settled | Cut Time 2300 → settlement at 2230 | Settlement at 2300 | `SEGDL3-R-009` §13.2 | SPEC_DERIVED (review severity) |

## Required negative coverage

| ID | Violates | Mutation class | Mutation | Expected result |
|---|---|---|---|---|
| BR-SEGDL3-NEG-001 | `SEGDL3-R-001` | MUT-001 Identity value | `:` → `#` | Fail citing SEGDL3-R-001 |
| BR-SEGDL3-NEG-002 | `SEGDL3-R-002` | MUT-005 Required field omitted | Remove Cut Time (length 19) | Fail citing SEGDL3-R-002 |
| BR-SEGDL3-NEG-004 | `SEGDL3-R-004` | MUT-010 Structural | Insert FS | Fail citing SEGDL3-R-004 |
| BR-SEGDL3-NEG-005 | `SEGDL3-R-005` | MUT-008 Enumeration | Day of the Week `7`; month `13` | Fail citing SEGDL3-R-005 |
| BR-SEGDL3-NEG-006 | `SEGDL3-R-006` | MUT-006 Pattern | Password left-aligned `4821  ` | Fail citing SEGDL3-R-006 |
| BR-SEGDL3-NEG-007 | `SEGDL3-R-007` | MUT-010 Structural | DL3 in a Software Load Response | Fail citing SEGDL3-R-007 |
| BR-SEGDL3-NEG-008 | `SEGDL3-R-008` | MUT-008 Enumeration | Load Type `X` on Date and Time Load Request | Fail citing SEGDL3-R-008 |

`SEGDL3-R-003` has no negative case until `SEGDL3-SME-002` decides the Password source. `SEGDL3-R-009` is device behaviour and is tested through Totals Request timing, not DL3 mutation.

## Coverage denominator

- **In scope:** all 9 catalog rules (`R-003` and `R-009` as review items).
- **Out of scope:** device clock hardware, settlement content (owned by Totals, Section 11.4).
- **Required counts:** 9 BRs, ≥ 9 scenarios, ≥ 16 test cases, ≥ 16 request/response records.

## Open SME items

`SEGDL3-SME-001` to `SEGDL3-SME-004`. See the [input register](segment-DL3-sme-tba-input-register.md).

## Implementation traceability

- Rule catalog: [coverage/segment-DL3-rule-catalog.json](coverage/segment-DL3-rule-catalog.json)
- Independent coverage/report validator: `GenerateSegmentDl3AiArtifactCoverageReport` (validates candidate package traceability and the representative AI sample; does not certify unresolved rules or execute serialized/device behavior).
- Production payload validator: _not yet implemented_; byte-level Date and Time Load Response validation remains blocked on `SEGDL3-SME-003`.
