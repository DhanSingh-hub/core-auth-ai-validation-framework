# Segment DL7 Business Requirements

**Segment:** DL7 — Supplemental Terminal Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.48, 13.2, Appendix W  
**Oracle:** [segment-DL7-rule-catalog.json](coverage/segment-DL7-rule-catalog.json) (6 rules)  
**Benchmark:** [Segment 100 financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md)

## Rule composition

| Class | Rules |
|---|---:|
| structure | 2 |
| field | 4 |
| **Total** | **6** |

## Requirements

| ID | Class | Requirement | Valid representation | Invalid representation | Source | Status |
|---|---|---|---|---|---|---|
| BR-SEGDL7-001 | structure | DL7 shall begin with `^`, carry a Segment Length, and have no End-of-Data Indicator | `^028001003eng…` | `^…~`; `#` first | `SEGDL7-R-001` §12.48 | REVIEW_REQUIRED (P-04) |
| BR-SEGDL7-002 | field | Segment Length shall exclude the `^` | Value = content after `^` | Value counts `^` | `SEGDL7-R-002` §12.48 | REVIEW_REQUIRED (P-05) |
| BR-SEGDL7-003 | field | Download Data shall be `<tag><len><data>` per Appendix W | TLV entries | Free text | `SEGDL7-R-003` §12.48, Appendix W | REVIEW_REQUIRED (P-01) |
| BR-SEGDL7-004 | field | Table 001 shall carry a 3-character ISO 639-2 code; Table 002 a 13-character postal code; data length shall equal Table Length | `001003eng`, `002013A1B 2C3      ` | `001004eng`, `002013A1B2C3` | `SEGDL7-R-004` Appendix W | REVIEW_REQUIRED (P-03) |
| BR-SEGDL7-005 | field | Download Data shall be alphanumeric and at most 100 bytes | 28 bytes | 101 bytes | `SEGDL7-R-005` §13.2 | REVIEW_REQUIRED (P-04) |
| BR-SEGDL7-006 | structure | Segment Length shall be three digits and equal the content length | `028` for 28 bytes of data | `28`, `0028`, `030` for 28 bytes | `SEGDL7-R-006` §13.2 | REVIEW_REQUIRED (P-05) |

## Required negative coverage

| ID | Violates | Mutation class | Mutation | Expected result |
|---|---|---|---|---|
| BR-SEGDL7-NEG-001 | `SEGDL7-R-001` | MUT-010 Structural | Append `~` | Fail citing SEGDL7-R-001 |
| BR-SEGDL7-NEG-002 | `SEGDL7-R-002` | MUT-009 Cross-field | Segment Length + 1 (counts `^`) | Fail citing SEGDL7-R-002 |
| BR-SEGDL7-NEG-004 | `SEGDL7-R-004` | MUT-003 Length | Table Length `004` with 3 data characters | Fail citing SEGDL7-R-004 |
| BR-SEGDL7-NEG-005 | `SEGDL7-R-005` | MUT-003 Length | 101-byte Download Data | Fail citing SEGDL7-R-005 |
| BR-SEGDL7-NEG-006 | `SEGDL7-R-006` | MUT-002 Format | Segment Length `28` (two digits) | Fail citing SEGDL7-R-006 |

## Coverage denominator

- **In scope:** all 6 catalog rules.
- **Out of scope:** device localisation behaviour; postal-code validity per country.
- **Required counts:** 6 BRs, ≥ 6 scenarios, ≥ 11 test cases, ≥ 11 test-data records.

## Open SME items

`SEGDL7-SME-001` to `SEGDL7-SME-005`. See the [input register](segment-DL7-sme-tba-input-register.md).

## Implementation traceability

- Rule catalog: [coverage/segment-DL7-rule-catalog.json](coverage/segment-DL7-rule-catalog.json)
- Validator: _not yet implemented_ (`SegmentDL7PayloadValidator`, blocked on `SEGDL7-SME-005`)
