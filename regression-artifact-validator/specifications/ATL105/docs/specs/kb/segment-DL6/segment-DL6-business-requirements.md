# Segment DL6 Business Requirements

**Segment:** DL6 — Store and Forward Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.47, 11.7.1.2, 13.2, Appendix E  
**Oracle:** [segment-DL6-rule-catalog.json](coverage/segment-DL6-rule-catalog.json) (7 rules)  
**Benchmark:** [Segment 100 financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md)

## Rule composition

| Class | Rules |
|---|---:|
| applicability | 1 |
| structure | 2 |
| serialization | 1 |
| field | 2 |
| lifecycle | 1 |
| **Total** | **7** |

## Requirements

| ID | Class | Requirement | Valid representation | Invalid representation | Source | Status |
|---|---|---|---|---|---|---|
| BR-SEGDL6-001 | applicability | DL6 shall be present in a Table Load Response if and only if DL1 carries Card Type `173` | `173` + DL6 | DL6 without `173`; `173` without DL6 | `SEGDL6-R-001` §12.47 | SPEC_DERIVED |
| BR-SEGDL6-002 | structure | DL6 shall begin with `\` and end with `~` within the maximum length | `\01000500~` | `$01000500~`; missing `~` | `SEGDL6-R-002` §12.47 | REVIEW_REQUIRED (P-03, P-04) |
| BR-SEGDL6-003 | field | Start Time and End Time shall both be Element 166 values | Two HHMM values | — | `SEGDL6-R-003` §12.47 | REVIEW_REQUIRED (P-01) |
| BR-SEGDL6-004 | serialization | DL6 fields shall be concatenated without Field Separators | `\01000500~` | FS between the times | `SEGDL6-R-004` §12.47 | SPEC_DERIVED |
| BR-SEGDL6-005 | field | Start and End Time shall be HHMM 0000-2359 | `0000`, `2359` | `2400`, `0160`, `1:00` | `SEGDL6-R-005` §13.2 | SPEC_DERIVED |
| BR-SEGDL6-006 | structure | DL6 shall be Data Block 4, after the `*` that closes block 3, followed by `*` | … DL3 `*` DL6 `*` | DL6 before DL3; no final `*` | `SEGDL6-R-006` §11.7.1.2 | REVIEW_REQUIRED (`SEGDL1-SME-003`) |
| BR-SEGDL6-007 | lifecycle | The device shall block store-and-forward processing daily between Start Time and End Time | Blocked at 03:00 for 0100-0500 | Allowed at 03:00 | `SEGDL6-R-007` Appendix E | REVIEW_REQUIRED (P-05) |

## Required negative coverage

| ID | Violates | Mutation class | Mutation | Expected result |
|---|---|---|---|---|
| BR-SEGDL6-NEG-001 | `SEGDL6-R-001` | MUT-009 Cross-field | Remove `173` from DL1, keep DL6 | Fail citing SEGDL6-R-001 |
| BR-SEGDL6-NEG-002 | `SEGDL6-R-002` | MUT-001 Identity value | `\` → `$` | Fail citing SEGDL6-R-002 |
| BR-SEGDL6-NEG-004 | `SEGDL6-R-004` | MUT-010 Structural | Insert FS | Fail citing SEGDL6-R-004 |
| BR-SEGDL6-NEG-005 | `SEGDL6-R-005` | MUT-008 Enumeration | Start Time `2400` | Fail citing SEGDL6-R-005 |
| BR-SEGDL6-NEG-006 | `SEGDL6-R-006` | MUT-010 Structural | DL6 placed before DL3 | Fail citing SEGDL6-R-006 |

## Coverage denominator

- **In scope:** all 7 catalog rules.
- **Out of scope:** store-and-forward queue behaviour itself; transaction forwarding after the window.
- **Required counts:** 7 BRs, ≥ 7 scenarios, ≥ 12 test cases, ≥ 12 Table Load Response records.

## Open SME items

`SEGDL6-SME-001` to `SEGDL6-SME-005`, plus `SEGDL1-SME-003`. See the [input register](segment-DL6-sme-tba-input-register.md).

## Implementation traceability

- Rule catalog: [coverage/segment-DL6-rule-catalog.json](coverage/segment-DL6-rule-catalog.json)
- Validator: _not yet implemented_ (`SegmentDL6PayloadValidator`, blocked on `SEGDL6-SME-004`)
