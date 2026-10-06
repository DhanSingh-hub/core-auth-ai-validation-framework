# Segment 115 Business Requirements

**Segment:** 115 — Print Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.9, 11.1.2, 11.1.2-EMV, 11.2.2, 12.14, 13.2  
**Oracle:** [segment-115-rule-catalog.json](coverage/segment-115-rule-catalog.json) (13 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 115 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 115 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 3 |
| serialization | 3 |
| compatibility | 2 |
| lifecycle | 2 |
| structure | 1 |
| applicability | 1 |
| metadata | 1 |
| **Total** | **13** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG115-001 | structure | Segment 115 is response-only: it appears only in the Financial Transaction Response and EMV Financial Transaction Response, never in any Request message | The segment's structural position and composition match the rule. | `SEG115-R-001` §12.14,11.1.2 | SPEC_DERIVED |
| BR-SEG115-002 | applicability | Segment 115 is conditionally included at Field No. 17/18/19 of the Financial Transaction Response's Data Section 2, only when Element 115 (Additional Information Data Segment Flag) indicates it follows AND the request's Loyalty Information Version (Element 150) equals 2 | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG115-R-002` §11.1.2 | REVIEW_REQUIRED |
| BR-SEG115-003 | field | Segment Type is fixed value 115 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG115-R-003` §12.14 | SPEC_DERIVED |
| BR-SEG115-004 | field | Segment Length is 4 digits, one of exactly seven segments across the specification requiring a 4-digit Segment Length (103, 114, 115, 118, 120, 130, 131) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG115-R-004` §12.14 | SPEC_DERIVED |
| BR-SEG115-005 | serialization | Segment 115 maximum length is 1,009 alphanumeric characters (01-1,009/a-z/A-Z) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG115-R-005` §12.14 | REVIEW_REQUIRED |
| BR-SEG115-006 | serialization | Field order is Segment Type, Segment Length, Print Data (only 3 fields; same shape as Segment 114) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG115-R-006` §12.14 | SPEC_DERIVED |
| BR-SEG115-007 | serialization | Only two Field Separators exist in Segment 115: one between Field Nos. 1 and 2, and one between Field Nos. 2 and 3. There is NO trailing Field Separator after Field No. 3 (Print Data) — unlike every other segment documented so far (108, 114), which explicitly send a trailing separator | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG115-R-007` §12.14 | SPEC_DERIVED |
| BR-SEG115-008 | field | Print Data (Element 152) is required and identifies the print data being transmitted to the device; per Section 12.14's note this field carries the terms & conditions text for Blackhawk phone activation and recharge receipts | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG115-R-008` §12.14,13.2 | REVIEW_REQUIRED |
| BR-SEG115-009 | metadata | Segment 115 originates at the Host (every field's source is Host), unlike device-originated segments such as Segment 114 | Documented for traceability; not independently asserted by a validator. | `SEG115-R-009` §12.14 | SPEC_DERIVED |
| BR-SEG115-010 | compatibility | Whether 'no other data segments are contained in the Financial Transaction Response' (Section 12.14) means Segment 115 excludes Segment 112, or only means nothing besides 112 and 115 can appear, is unresolved — the Section 11.1.2 layout table shows Segment 112 (Field 16/17/18) and Segment 115 (Field 17/18/19) as two independently-conditional segments in the SAME response, which appears to contradict a strict reading of 'no other data segments' | Only the permitted companion segments / message families carry this segment. | `SEG115-R-010` §12.14,11.1.2 | REVIEW_REQUIRED |
| BR-SEG115-011 | compatibility | In the EMV Financial Transaction Response specifically, Segment 115 may co-occur with Segment 120 (Print Data 2 Segment) at Field 17/18/19, immediately followed by Segment 131 (EMV Response Data Segment) at Field 17/18/19/20 | Only the permitted companion segments / message families carry this segment. | `SEG115-R-011` §11.1.2-EMV | SPEC_DERIVED |
| BR-SEG115-012 | lifecycle | Segment 115 is plausibly the wire-format vehicle for 'Loyalty Print Data' returned during a Segment 108 Account Inquiry (Update Code I) or Totals Report (Update Code T) flow, inferred from the shared Loyalty Information Version = 2 trigger condition | Paired messages are present and the correlated values agree. | `SEG115-R-012` §10.9,11.1.2 | REVIEW_REQUIRED |
| BR-SEG115-013 | lifecycle | Whether Segment 115 can appear in the Loyalty Card Transaction Response (Section 11.2.2, which states it mirrors the generic Financial Transaction Response) is inferred but not explicitly restated for Segment 115 | Paired messages are present and the correlated values agree. | `SEG115-R-013` §11.2.2 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG115-NEG-001 | `SEG115-R-001` | MUT-010 structural requirement | Validation error citing SEG115-R-001 |
| BR-SEG115-NEG-002 | `SEG115-R-002` | MUT-009 interdependency violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG115-NEG-003 | `SEG115-R-003` | MUT-001 wrong fixed value | Validation error citing SEG115-R-003 |
| BR-SEG115-NEG-004 | `SEG115-R-004` | MUT-003 length violation | Validation error citing SEG115-R-004 |
| BR-SEG115-NEG-005 | `SEG115-R-005` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG115-NEG-006 | `SEG115-R-006` | MUT-001 wrong fixed value | Validation error citing SEG115-R-006 |
| BR-SEG115-NEG-007 | `SEG115-R-007` | MUT-010 structural / separator violation | Validation error citing SEG115-R-007 |
| BR-SEG115-NEG-008 | `SEG115-R-008` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG115-NEG-009 | `SEG115-R-010` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG115-NEG-010 | `SEG115-R-011` | MUT-010 structural requirement | Validation error citing SEG115-R-011 |
| BR-SEG115-NEG-011 | `SEG115-R-012` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG115-NEG-012 | `SEG115-R-013` | MUT-009 interdependency violation | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG115-002` (`SEG115-R-002`) — REVIEW_REQUIRED
- `BR-SEG115-005` (`SEG115-R-005`) — REVIEW_REQUIRED
- `BR-SEG115-008` (`SEG115-R-008`) — REVIEW_REQUIRED
- `BR-SEG115-010` (`SEG115-R-010`) — REVIEW_REQUIRED
- `BR-SEG115-012` (`SEG115-R-012`) — REVIEW_REQUIRED
- `BR-SEG115-013` (`SEG115-R-013`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG115-R-005): Section 12.14 and the AI Solution Team's BR-249-3 both state max length 1,009, but the Financial Transaction Response layout table (Section 11.1.2) states 910 for Field No. 17/18/19. Which governs?
- **P-02** (SEG115-R-008): Section 12.14's own field table states Print Data (Element 152) length 999, but the internal 13-data-elements.md reference states 900 bytes max. Which governs?
- **P-03** (SEG115-R-010): Does 'no other data segments are contained in the Financial Transaction Response' (Section 12.14) mean Segment 115 excludes Segment 112, or only that nothing besides 112 and 115 can appear? The Section 11.1.2 layout table depicts both as independently-conditional in the same response.
- **P-04** (SEG115-R-012, SEG115-R-013): Is Segment 115 confirmed as the wire-format vehicle for Segment 108's 'Loyalty Print Data' (Account Inquiry / Totals Report responses), and can Segment 115 appear in the Loyalty Card Transaction Response? Mirrors Segment 108's still-open SEG108-SME-003.
- **P-05** (Element 115 semantics): What are the complete valid values of Element 115 (Additional Information Data Segment Flag)? The internal reference table lists only 0 (none follows) and 1 (follows), but the narrative describes two independently conditional segments (112 and 115) governed by the same flag plus a second condition (Loyalty Information Version = 2). Please confirm the exact decision logic.
- **P-06** (AI-artifacts, test-data): No dedicated segment-115 AI Solution BR/TS/TC/TD package or real Financial-Transaction-Response sample JSON has been located as of this training pass (only cross-references inside other segments' packages, all mismatched to the wrong segment). Provide the location of a dedicated Segment 115 AI artifact package and real response sample data, or approve continued use of synthesized `.synthetic.json…

## Implementation traceability

- Rule catalog: [coverage/segment-115-rule-catalog.json](coverage/segment-115-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
