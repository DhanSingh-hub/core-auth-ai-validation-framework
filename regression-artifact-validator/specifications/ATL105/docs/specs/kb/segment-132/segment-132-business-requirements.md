# Segment 132 Business Requirements

**Segment:** 132 — CA Public Key File Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.9.1, 12.22  
**Oracle:** [segment-132-rule-catalog.json](coverage/segment-132-rule-catalog.json) (12 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 132 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 132 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 8 |
| serialization | 2 |
| applicability | 1 |
| metadata | 1 |
| **Total** | **12** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG132-001 | applicability | Segment 132 belongs to the CA Public Key File Load Request's Data Section 3, alongside optional companions 101, 102, 104, 111 | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG132-R-001` §11.9.1 | REVIEW_REQUIRED |
| BR-SEG132-002 | field | Segment Type is fixed value 132, sourced at the Device | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG132-R-002` §12.22 | SPEC_DERIVED |
| BR-SEG132-003 | field | Segment Length is 3 digits (not 4 — Segment 132 is NOT one of the seven 4-digit-length segments) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG132-R-003` §12.22 | SPEC_DERIVED |
| BR-SEG132-004 | serialization | Segment 132 maximum length is 77 alphanumeric characters (01-77/a-z/A-Z) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG132-R-004` §12.22 | REVIEW_REQUIRED |
| BR-SEG132-005 | field | Sequence Number (Element 86) is required, 6 digits; a limited range (100000-199999) applies ONLY when using the Multithreaded Dial Protocol Communications header for a CAPK File Load | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG132-R-005` §12.22 | SPEC_DERIVED |
| BR-SEG132-006 | field | Terminal Identifier (Element 102) is required, 13 characters; for EMV, the first two characters (Device Type) must be '+*' regardless of the actual device type | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG132-R-006` §12.22 | SPEC_DERIVED |
| BR-SEG132-007 | field | Load Type (Element 48) is required, fixed value 'K' (indicates Public Key information is requested) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG132-R-007` §12.22 | SPEC_DERIVED |
| BR-SEG132-008 | field | Hardware Version (Element 43), Software Version (Element 96), and Firmware Version (Element 39) are all required device-version identifiers | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG132-R-008` §12.22 | SPEC_DERIVED |
| BR-SEG132-009 | field | CA Public Key File Checksum (Element 187) is required in Segment 132, identifying the checksum currently in use — a THIRD segment (after 130 request, 131 response) referencing this same element | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG132-R-009` §12.22 | SPEC_DERIVED |
| BR-SEG132-010 | field | Block Number (Element 11) is required, 3 digits, identifying the specific data block requested by a device or sent by the host — implying a multi-block CA key file transfer protocol not fully detailed in this section | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG132-R-010` §12.22 | REVIEW_REQUIRED |
| BR-SEG132-011 | serialization | Field Separator placement is only explicitly shown for fields 1-2 and 2-3 in the extracted table (via inline <FS> markers); no summary sentence documents separator behavior for fields 4-10 | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG132-R-011` §12.22 | REVIEW_REQUIRED |
| BR-SEG132-012 | metadata | Segment 132 originates at the Device | Documented for traceability; not independently asserted by a validator. | `SEG132-R-012` §12.22 | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG132-NEG-001 | `SEG132-R-001` | MUT-005 required field omitted | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG132-NEG-002 | `SEG132-R-002` | MUT-001 wrong fixed value | Validation error citing SEG132-R-002 |
| BR-SEG132-NEG-003 | `SEG132-R-003` | MUT-003 length violation | Validation error citing SEG132-R-003 |
| BR-SEG132-NEG-004 | `SEG132-R-004` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG132-NEG-005 | `SEG132-R-005` | MUT-003 length violation | Validation error citing SEG132-R-005 |
| BR-SEG132-NEG-006 | `SEG132-R-006` | MUT-005 required field omitted | Validation error citing SEG132-R-006 |
| BR-SEG132-NEG-007 | `SEG132-R-007` | MUT-001 wrong fixed value | Validation error citing SEG132-R-007 |
| BR-SEG132-NEG-008 | `SEG132-R-008` | MUT-005 required field omitted | Validation error citing SEG132-R-008 |
| BR-SEG132-NEG-009 | `SEG132-R-009` | MUT-005 required field omitted | Validation error citing SEG132-R-009 |
| BR-SEG132-NEG-010 | `SEG132-R-010` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG132-NEG-011 | `SEG132-R-011` | MUT-010 structural / separator violation | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG132-001` (`SEG132-R-001`) — REVIEW_REQUIRED
- `BR-SEG132-004` (`SEG132-R-004`) — REVIEW_REQUIRED
- `BR-SEG132-010` (`SEG132-R-010`) — REVIEW_REQUIRED
- `BR-SEG132-011` (`SEG132-R-011`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG132-R-001): What is Segment 132's exact R/O/C designation within the CA Public Key File Load Request's Data Section 3 table?
- **P-02** (SEG132-R-004): The documented field lengths sum to 74, not 77. Confirm the exact maximum-length reconciliation (e.g., are 3 separator bytes included in the 77 figure?).
- **P-03** (SEG132-R-010): Is the multi-block CA key file transfer protocol (Block Number, Element 11) in scope for this training pass, or a separate workstream?
- **P-04** (SEG132-R-011): Confirm the Field Separator behavior for fields 4 through 10 of Segment 132 — the specification does not include the usual summary sentence.
- **P-05** (AI-artifacts, test-data): No dedicated Segment 132 AI or Test Team package was located. Provide one, or approve synthesized `.synthetic.json` fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-132-rule-catalog.json](coverage/segment-132-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
