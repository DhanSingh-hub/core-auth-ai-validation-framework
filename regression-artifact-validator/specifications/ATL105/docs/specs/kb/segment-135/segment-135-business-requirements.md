# Segment 135 Business Requirements

**Segment:** 135 — Moneris Data (Request) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.1, 12.24, AppendixV  
**Oracle:** [segment-135-rule-catalog.json](coverage/segment-135-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 135 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 135 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 3 |
| applicability | 1 |
| serialization | 1 |
| **Total** | **5** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG135-001 | applicability | Segment 135 is required only for transactions destined for the Moneris authorizer | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG135-R-001` §11.1.1,12.24 | SPEC_DERIVED |
| BR-SEG135-002 | field | Segment Type is fixed value 135, sourced at the Device | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG135-R-002` §12.24 | SPEC_DERIVED |
| BR-SEG135-003 | field | Segment Length Indicator is 3 digits, identifies the segment's length including Segment Type's length only (NOT explicitly including Field Separators, unlike most other segments' Segment Length description) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG135-R-003` §12.24 | REVIEW_REQUIRED |
| BR-SEG135-004 | serialization | Field Separators appear between fields 1-2 and 2-3; the segment as a whole should end with a trailing Field Separator; data fields contained WITHIN field 3 (Moneris Data) are NOT separated by Field Separators | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG135-R-004` §12.24 | SPEC_DERIVED |
| BR-SEG135-005 | field | Moneris Data (Element 213) is required, max 100 characters, in <tag><len><data> TLV-like format; full layout documented in Appendix V (Moneris Data layouts) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG135-R-005` §12.24,AppendixV | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG135-NEG-001 | `SEG135-R-001` | MUT-005 required field omitted | Validation error citing SEG135-R-001 |
| BR-SEG135-NEG-002 | `SEG135-R-002` | MUT-001 wrong fixed value | Validation error citing SEG135-R-002 |
| BR-SEG135-NEG-003 | `SEG135-R-003` | MUT-001 wrong fixed value | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG135-NEG-004 | `SEG135-R-004` | MUT-010 structural / separator violation | Validation error citing SEG135-R-004 |
| BR-SEG135-NEG-005 | `SEG135-R-005` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG135-003` (`SEG135-R-003`) — REVIEW_REQUIRED
- `BR-SEG135-005` (`SEG135-R-005`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG135-R-003): Does Segment 135's Segment Length Indicator include Field Separators in its count, or only Segment Type's length as literally stated?
- **P-03** (AI-artifacts, test-data): No dedicated Segment 135 Test Team package was located (though an AI Solution Team BR package exists: POC-AI-ATL105-Segment-135-Business-Requirements.json). Confirm whether to treat it as canonical for Item 2.

Appendix V is in scope and has been transcribed in the [Appendix V training module](../appendix-v/README.md). Its request Table 004/005 data-width conflicts remain review-gated.

## Implementation traceability

- Rule catalog: [coverage/segment-135-rule-catalog.json](coverage/segment-135-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
