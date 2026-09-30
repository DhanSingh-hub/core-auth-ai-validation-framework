# Segment 131 Business Requirements

**Segment:** 131 — EMV Response Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.2-EMV, 12.11, 12.20, 12.21  
**Oracle:** [segment-131-rule-catalog.json](coverage/segment-131-rule-catalog.json) (12 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 131 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 131 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 3 |
| structure | 2 |
| serialization | 2 |
| metadata | 2 |
| applicability | 1 |
| lifecycle | 1 |
| compatibility | 1 |
| **Total** | **12** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG131-001 | structure | Section 12.21 states Segment 131 'always appears in Field No. 4 in Data Section No. 3', but the EMV Financial Transaction Response's own layout table places it at Field No. 17/18/19/20 of Data Section No. 2 (following Segments 112, 115, and 120) — these two p… | The segment's structural position and composition match the rule. | `SEG131-R-001` §12.21,11.1.2-EMV | REVIEW_REQUIRED |
| BR-SEG131-002 | applicability | Segment 131 is response-only and conditional: it follows in the EMV Financial Transaction Response only when EMV data is required, alongside Segments 112 and 115/120 | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG131-R-002` §11.1.2-EMV | SPEC_DERIVED |
| BR-SEG131-003 | field | Segment Type is fixed value 131, sourced at the Host (contrast Segment 130's device-sourced Segment Type) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG131-R-003` §12.21 | SPEC_DERIVED |
| BR-SEG131-004 | field | Segment Length is 4 digits, one of exactly seven segments across the specification requiring a 4-digit Segment Length (103, 114, 115, 118, 120, 130, 131) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG131-R-004` §12.21 | SPEC_DERIVED |
| BR-SEG131-005 | serialization | Segment 131 maximum length is 3,834 alphanumeric characters (001-3834/a-z/A-Z) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG131-R-005` §12.21 | REVIEW_REQUIRED |
| BR-SEG131-006 | serialization | Segment 131 uses NO Field Separators at all — it is fixed-length/positional. When a field is not populated, the next field immediately follows with no delimiter | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG131-R-006` §12.21 | SPEC_DERIVED |
| BR-SEG131-007 | lifecycle | CA Public Key File Checksum (Element 187) is required and is explicitly echoed from the Request (Segment 130's CA Public Key File Checksum) | Paired messages are present and the correlated values agree. | `SEG131-R-007` §12.21 | SPEC_DERIVED |
| BR-SEG131-008 | field | EMV Chip Data Length (Element 189) and EMV Chip Data (Element 190) are required, mirroring Segment 130's fields, but the field table lists their Source as 'Device' even though Segment 131 as a whole originates at BUYPASS | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG131-R-008` §12.21 | REVIEW_REQUIRED |
| BR-SEG131-009 | structure | The EMV Additional Information Section (fields 6-8: Indicator, Length, Information) repeats per EMV Additional Information Indicator for a maximum total length of 2,800 bytes — a DIFFERENT cap than Segment 130's 2,000-byte limit for the structurally identical… | The segment's structural position and composition match the rule. | `SEG131-R-009` §12.21 | SPEC_DERIVED |
| BR-SEG131-010 | metadata | Element 118 is reused across three segments now documented in this KB: Segment 112 ('Additional Information', single occurrence), Segment 130 ('EMV Additional Information', repeating, request-side), and Segment 131 ('EMV Additional Information', repeating, re… | Documented for traceability; not independently asserted by a validator. | `SEG131-R-010` §12.11,12.20,12.21 | SPEC_DERIVED |
| BR-SEG131-011 | metadata | Segment 131 originates at BUYPASS (the Host) | Documented for traceability; not independently asserted by a validator. | `SEG131-R-011` §12.21 | SPEC_DERIVED |
| BR-SEG131-012 | compatibility | In the EMV Financial Transaction Response, Segment 131 always follows Segment 130's response counterpart context — i.e., it appears after Segments 112 (Field 16/17/18) and 115/120 (Field 17/18/19) when those are also present | Only the permitted companion segments / message families carry this segment. | `SEG131-R-012` §11.1.2-EMV | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG131-NEG-001 | `SEG131-R-001` | MUT-005 required field omitted | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG131-NEG-002 | `SEG131-R-002` | MUT-005 required field omitted | Validation error citing SEG131-R-002 |
| BR-SEG131-NEG-003 | `SEG131-R-003` | MUT-001 wrong fixed value | Validation error citing SEG131-R-003 |
| BR-SEG131-NEG-004 | `SEG131-R-004` | MUT-003 length violation | Validation error citing SEG131-R-004 |
| BR-SEG131-NEG-005 | `SEG131-R-005` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG131-NEG-006 | `SEG131-R-006` | MUT-003 length violation | Validation error citing SEG131-R-006 |
| BR-SEG131-NEG-007 | `SEG131-R-007` | MUT-005 required field omitted | Validation error citing SEG131-R-007 |
| BR-SEG131-NEG-008 | `SEG131-R-008` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG131-NEG-009 | `SEG131-R-009` | MUT-003 length violation | Validation error citing SEG131-R-009 |
| BR-SEG131-NEG-010 | `SEG131-R-012` | MUT-010 structural requirement | Validation error citing SEG131-R-012 |

## Requirements that must not be certified yet

- `BR-SEG131-001` (`SEG131-R-001`) — REVIEW_REQUIRED
- `BR-SEG131-005` (`SEG131-R-005`) — REVIEW_REQUIRED
- `BR-SEG131-008` (`SEG131-R-008`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG131-R-001): Section 12.21 states Segment 131 appears in 'Field No. 4 in Data Section No. 3', but the EMV Financial Transaction Response layout table (also corroborated by the AI Solution Team's own statement) places it at Field No. 17/18/19/20 of Data Section No. 2. Which governs, or do these describe two different message contexts?
- **P-02** (SEG131-R-005): Confirm 3,834 is the sole authoritative maximum length for Segment 131 — an earlier, OCR-uncertain reading of a response layout table suggested a possible shorter figure that could not be confidently transcribed.
- **P-03** (SEG131-R-008): Confirm whether EMV Chip Data Length/EMV Chip Data's 'Source: Device' notation in Segment 131's field table is a transcription artifact (values echoed from the device's request) or intentional, given Segment 131 as a whole originates at BUYPASS.
- **P-04** (AI-artifacts, test-data): No dedicated segment-131 AI Solution BR/TS/TC/TD package or Test Team core-structure package exists (unlike Segment 130). Provide the location of one, or approve continued use of synthesized `.synthetic.json` fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-131-rule-catalog.json](coverage/segment-131-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
