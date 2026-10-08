# Segment 136 Business Requirements

**Segment:** 136 — Moneris Data (Response) Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.25, AppendixV  
**Oracle:** [segment-136-rule-catalog.json](coverage/segment-136-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 136 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 136 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

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
| BR-SEG136-001 | applicability | Segment 136 is used for both financial transactions and Key Load transactions destined for/from the Moneris authorizer | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG136-R-001` §12.25 | SPEC_DERIVED |
| BR-SEG136-002 | field | Segment Type is fixed value 136, sourced at the Device (as documented — despite this being a Response segment, the field table lists Source: Device for both Segment Type and Segment Length) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG136-R-002` §12.25 | REVIEW_REQUIRED |
| BR-SEG136-003 | field | Segment Length Indicator is 3 digits, identifies the segment's length including Segment Type's length | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG136-R-003` §12.25 | SPEC_DERIVED |
| BR-SEG136-004 | serialization | A Field Separator follows the segment (i.e., a single trailing separator after the whole Segment 136, unlike Segment 135's per-field pattern) | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG136-R-004` §12.25 | REVIEW_REQUIRED |
| BR-SEG136-005 | field | Moneris Data (Element 213) is required, max 100 characters, in <tag><len><data> format; full layout documented in Appendix V (Moneris Data layouts) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG136-R-005` §12.25,AppendixV | SPEC_DERIVED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG136-NEG-001 | `SEG136-R-001` | MUT-010 structural requirement | Validation error citing SEG136-R-001 |
| BR-SEG136-NEG-002 | `SEG136-R-002` | MUT-001 wrong fixed value | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG136-NEG-003 | `SEG136-R-003` | MUT-001 wrong fixed value | Validation error citing SEG136-R-003 |
| BR-SEG136-NEG-004 | `SEG136-R-004` | MUT-010 structural / separator violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG136-NEG-005 | `SEG136-R-005` | MUT-003 length violation | Validation error citing SEG136-R-005 |

## Requirements that must not be certified yet

- `BR-SEG136-002` (`SEG136-R-002`) — REVIEW_REQUIRED
- `BR-SEG136-004` (`SEG136-R-004`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG136-R-002): Confirm whether Segment Type/Segment Length Source 'Device' is intentional for this Moneris response segment or a transcription error (mirrors the Segment 131 pattern).
- **P-02** (SEG136-R-004): Confirm whether Field Separators also appear between fields 1-2 and 2-3 in Segment 136, or only as a single trailing separator after the whole segment.
- **P-04** (AI-artifacts, test-data): No dedicated Segment 136 AI or Test Team package was located. Provide one, or approve synthesized fixtures.

Appendix V is in scope and has been transcribed in the [Appendix V training module](../appendix-v/README.md). The response key encoding and cross-message semantics remain review-gated.

## Implementation traceability

- Rule catalog: [coverage/segment-136-rule-catalog.json](coverage/segment-136-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
