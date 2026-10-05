# Segment DL1 Business Requirements

**Segment:** DL1 — Merchant Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.42, 11.7.1, 11.7.1.2, 13.2, Appendix E  
**Oracle:** [segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json) (12 rules)  
**Benchmark:** [Segment 100 financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md)

## Scope and provenance

Each requirement restates one catalog rule as an independently testable statement with a positive and a negative representation. Requirements are derived only from the ATL105 specification; AI artifacts are not requirement evidence. A rule linked to an open provisional item stays `REVIEW_REQUIRED` and must not be certified.

## Rule composition

| Class | Rules |
|---|---:|
| structure | 3 |
| serialization | 2 |
| field | 4 |
| applicability | 1 |
| lifecycle | 2 |
| **Total** | **12** |

## Requirements

| ID | Class | Requirement | Valid representation | Invalid representation | Source | Status |
|---|---|---|---|---|---|---|
| BR-SEGDL1-001 | serialization | DL1 shall not exceed 399 characters and is host-originated | 111 characters with 3 Card Types | 402 characters (100 Card Types; also violates the 01-99 Card Type count in `SEGDL1-R-004`) | `SEGDL1-R-001` §12.42 | SPEC_DERIVED |
| BR-SEGDL1-002 | structure | DL1 shall begin with `#` and end with `~` and carry no Segment Type/Length | `#...~` | Starts `100`, or missing `~` | `SEGDL1-R-002` §12.42 | SPEC_DERIVED |
| BR-SEGDL1-003 | field | Merchant Name, Store Number, Address Line 1, Address Line 2 and Merchant Phone Number shall all be present; transmitted widths and padding remain subject to P-02 | All five present; exact widths/padding pending P-02 | Merchant Name missing | `SEGDL1-R-003` §12.42 | REVIEW_REQUIRED (P-02) |
| BR-SEGDL1-004 | structure | The number of Card Type occurrences shall equal Number of Card Types (01-99) | `03` + 3 codes | `03` + 2 codes; `00`; 100 codes | `SEGDL1-R-004` §12.42 | SPEC_DERIVED |
| BR-SEGDL1-005 | lifecycle | A Card Type `173` shall be accompanied by DL6 in the same Table Load Response, and DL6 shall not appear without it | `173` + DL6 | `173` without DL6; DL6 without `173` | `SEGDL1-R-005` §12.42, §12.47 | REVIEW_REQUIRED (P-03) |
| BR-SEGDL1-006 | serialization | DL1 fields shall be concatenated without Field Separators | `#NAME...` | FS (`0x1C`) between fields | `SEGDL1-R-006` §12.42 | REVIEW_REQUIRED (P-02) |
| BR-SEGDL1-007 | applicability | DL1 shall be Data Block 1 of the Table Load Response, after `)`, and in no other message | `)` + DL1 in Table Load Response | DL1 in a Phone Load Response | `SEGDL1-R-007` §11.7.1.2 | SPEC_DERIVED |
| BR-SEGDL1-008 | lifecycle | DL1 shall be returned only when the merchant load flag is `TABL` | Flag `TABL` → DL1 present | Flag not set → DL1 present | `SEGDL1-R-008` §11.7.1 | SPEC_DERIVED |
| BR-SEGDL1-009 | field | Every Card Type shall be an Appendix E Table Load code | `020`, `164`, `173` | `999`, `02A` | `SEGDL1-R-009` Appendix E | REVIEW_REQUIRED (P-04) |
| BR-SEGDL1-010 | field | Address Line 2 shall follow City(1-12) space State(14-15) space ZIP(17-21) | `SPRINGFIELD  IL 62701` | `SPRINGFIELD,IL,62701` | `SEGDL1-R-010` §13.2 | SPEC_DERIVED |
| BR-SEGDL1-011 | field | Merchant Phone Number shall be `(nnn)nnn-nnnn`; Store Number shall be numeric and non-zero | `(555)555-0100`; `0000000000001234` | `5555550100`; `000000000000000A`; `0000000000000000` | `SEGDL1-R-011` §13.2 | SPEC_DERIVED |
| BR-SEGDL1-012 | structure | The Table Load Response shall order `)` DL1, DL2, DL3, `*`, then DL6 and `*` when triggered | `)` DL1 DL2 DL3 `*` | DL3 before DL1; DL6 before `*` | `SEGDL1-R-012` §11.7.1.2 | REVIEW_REQUIRED (P-03) |

## Required negative coverage

| ID | Violates | Mutation class | Mutation | Expected result |
|---|---|---|---|---|
| BR-SEGDL1-NEG-001 | `SEGDL1-R-001` | MUT-003 Length | 100 Card Types (length 402) | Fail citing SEGDL1-R-001 / R-004 |
| BR-SEGDL1-NEG-002 | `SEGDL1-R-002` | MUT-001 Identity value | `#` → `!` | Fail citing SEGDL1-R-002 |
| BR-SEGDL1-NEG-003 | `SEGDL1-R-003` | MUT-005 Required field omitted | Remove Merchant Name | Fail citing SEGDL1-R-003 |
| BR-SEGDL1-NEG-004 | `SEGDL1-R-004` | MUT-009 Cross-field dependency | Number of Card Types `03`, two codes | Fail citing SEGDL1-R-004 |
| BR-SEGDL1-NEG-005 | `SEGDL1-R-005` | MUT-009 Cross-field dependency | `173` present, DL6 removed | Fail citing SEGDL1-R-005 |
| BR-SEGDL1-NEG-006 | `SEGDL1-R-006` | MUT-010 Structural requirement | Insert FS after Merchant Name | Fail citing SEGDL1-R-006 |
| BR-SEGDL1-NEG-007 | `SEGDL1-R-007` | MUT-010 Structural requirement | Place DL1 in a Phone Load Response | Fail citing SEGDL1-R-007 |
| BR-SEGDL1-NEG-008 | `SEGDL1-R-008` | MUT-009 Cross-field dependency | Flag not `TABL`, DL1 returned | Fail citing SEGDL1-R-008 |
| BR-SEGDL1-NEG-009 | `SEGDL1-R-009` | MUT-008 Enumeration out of bounds | Card Type `999` | Fail or REVIEW citing SEGDL1-R-009 |
| BR-SEGDL1-NEG-010 | `SEGDL1-R-010` | MUT-006 Pattern | State Code at positions 13-14 | Fail citing SEGDL1-R-010 |
| BR-SEGDL1-NEG-011 | `SEGDL1-R-011` | MUT-002 Format | Phone `5555550100   ` | Fail citing SEGDL1-R-011 |
| BR-SEGDL1-NEG-012 | `SEGDL1-R-012` | MUT-010 Structural requirement | DL6 before End-of-Load of block 3 | Fail or REVIEW citing SEGDL1-R-012 |
| BR-SEGDL1-NEG-013 | `SEGDL1-R-011` | MUT-008 Enumeration out of bounds | Store Number `0000000000000000` | Fail citing SEGDL1-R-011 |

## Coverage denominator

- **In scope:** all 12 catalog rules.
- **Out of scope:** device card-acceptance behaviour after the load, merchant-profile administration at BUYPASS, and DL2/DL3/DL6 field rules (owned by their segments).
- **Required counts for completion:** 12 BRs, at least 12 scenarios, 24 test cases (one positive and one negative per rule), and 24 request/response test-data records.

## Open SME items

`SEGDL1-SME-001` (package), `SEGDL1-SME-002` (padding), `SEGDL1-SME-003` (End-of-Load count), `SEGDL1-SME-004` (Card Type set). See the [input register](segment-DL1-sme-tba-input-register.md).

## Implementation traceability

- Rule catalog: [coverage/segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json)
- Validator: _not yet implemented_ (`SegmentDL1PayloadValidator`, blocked on `SEGDL1-SME-002`)
- BR IDs are positional against the catalog; if the catalog changes, update this file in the same commit.
