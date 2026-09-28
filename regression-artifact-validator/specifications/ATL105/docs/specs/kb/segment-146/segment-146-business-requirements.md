# Segment 146 Business Requirements

**Segment:** 146 — Enhanced Fleet Response Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.32, AppendixF  
**Oracle:** [segment-146-rule-catalog.json](coverage/segment-146-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 146 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 146 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 4 |
| applicability | 1 |
| **Total** | **5** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG146-001 | field | Segment Type fixed 146, Segment Length includes Segment Type's length; both Device-sourced (despite this being a response segment, mirroring the sourcing pattern already flagged for Segments 131/136) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG146-R-001` §12.32 | REVIEW_REQUIRED |
| BR-SEG146-002 | field | Enhanced Fleet Data (Element 239) is required, max 999 characters, cataloged as: Table 001 (Response Flags, incl. Settlement Indicator: CP/DB/DF/RC/RF/FN), Table 002 (Non-Fuel Product Limits, NOT present in Comdata responses), Table 003 (Fuel Product Limits,… | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG146-R-002` §12.32 | SPEC_DERIVED |
| BR-SEG146-003 | applicability | Table 002 (Non-Fuel Product Limits) is explicitly documented as NOT present in Comdata response messages | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG146-R-003` §12.32 | SPEC_DERIVED |
| BR-SEG146-004 | field | Table 004 (Prompt Formats) uses a documented edit-mask grammar: '?' (re-prompt), 'A'/'B' (alphanumeric, treated identically since 2015-01-02), 'N' (numeric only), 'I' (free format), 'O' (optional response), 'Z' (capture but don't resend), 'T' (data type N=Num… | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG146-R-004` §12.32 | SPEC_DERIVED |
| BR-SEG146-005 | field | Table 003 (Fuel Product Limits) uses standard product codes from ATL105 Appendix F (Valid Payment Systems Product Codes) — unlike Table 002's non-fuel category codes | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG146-R-005` §12.32,AppendixF | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG146-NEG-001 | `SEG146-R-001` | MUT-001 wrong fixed value | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG146-NEG-002 | `SEG146-R-002` | MUT-003 length violation | Validation error citing SEG146-R-002 |
| BR-SEG146-NEG-003 | `SEG146-R-003` | MUT-010 structural requirement | Validation error citing SEG146-R-003 |
| BR-SEG146-NEG-004 | `SEG146-R-004` | MUT-002/MUT-006 format or charset violation | Validation error citing SEG146-R-004 |
| BR-SEG146-NEG-005 | `SEG146-R-005` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG146-001` (`SEG146-R-001`) — REVIEW_REQUIRED
- `BR-SEG146-005` (`SEG146-R-005`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG146-R-001): Confirm whether Segment Type/Length 'Source: Device' is intentional for this response segment or a transcription error (mirrors Segments 131/136).
- **P-02** (SEG146-R-004): Is full edit-mask grammar parsing (M/X/V/P patterns) required for Item 1 sign-off, or is format-only validation sufficient?
- **P-03** (SEG146-R-005): Is Appendix F (Valid Payment Systems Product Codes) in scope for this training pass?
- **P-04** (AI-artifacts, test-data): No dedicated Segment 146 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-146-rule-catalog.json](coverage/segment-146-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
