# Segment 120 Business Requirements

**Segment:** 120 — Print Data 2 Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.18, 13-66  
**Oracle:** [segment-120-rule-catalog.json](coverage/segment-120-rule-catalog.json) (8 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 120 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 120 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 3 |
| serialization | 2 |
| applicability | 1 |
| structure | 1 |
| content | 1 |
| **Total** | **8** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG120-001 | field | Segment Type is 120 | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG120-R-001` §12.18 | SPEC_DERIVED |
| BR-SEG120-002 | field | Segment Length is present, exactly 4 digits (zero-padded), and equals the segment's actual encoded length including the Segment Type field and Field Separators | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG120-R-002` §13-66 | SPEC_DERIVED |
| BR-SEG120-003 | field | Print Data is required and must be present whenever Segment 120 is included | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG120-R-003` §12.18 | SPEC_DERIVED |
| BR-SEG120-004 | serialization | Print Data must not exceed 999 characters AND total serialized Segment 120 length (Segment Type + Segment Length + Print Data + separators) must not exceed 1,009 alphanumeric characters -- both caps enforced independently | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG120-R-004` §12.18 | REVIEW_REQUIRED |
| BR-SEG120-005 | serialization | Exactly one Field Separator appears between Field 1 and Field 2, and exactly one between Field 2 and Field 3; there is no trailing separator after Print Data | The wire-format output reproduces the stated separator / length / ordering behaviour exactly. | `SEG120-R-005` §12.18 | SPEC_DERIVED |
| BR-SEG120-006 | applicability | Segment 120 is present only in Financial Transaction Response and EMV Financial Transaction Response messages; it must not appear on a request message | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG120-R-006` §12.18 | SPEC_DERIVED |
| BR-SEG120-007 | structure | Segment 120 is the final segment of the Data Section 3 companion sequence in a Financial Transaction Response / EMV Financial Transaction Response | The segment's structural position and composition match the rule. | `SEG120-R-007` §12.18 | SPEC_DERIVED |
| BR-SEG120-008 | content | Print Data may contain '\' as a line delimiter between lines of receipt text for Blackhawk phone activation/recharge | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG120-R-008` §12.18 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG120-NEG-001 | `SEG120-R-001` | MUT-001 wrong fixed value | Validation error citing SEG120-R-001 |
| BR-SEG120-NEG-002 | `SEG120-R-002` | MUT-001 wrong fixed value | Validation error citing SEG120-R-002 |
| BR-SEG120-NEG-003 | `SEG120-R-003` | MUT-005 required field omitted | Validation error citing SEG120-R-003 |
| BR-SEG120-NEG-004 | `SEG120-R-004` | MUT-001 wrong fixed value | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG120-NEG-005 | `SEG120-R-005` | MUT-010 structural / separator violation | Validation error citing SEG120-R-005 |
| BR-SEG120-NEG-006 | `SEG120-R-006` | MUT-010 structural requirement | Validation error citing SEG120-R-006 |
| BR-SEG120-NEG-007 | `SEG120-R-007` | MUT-010 structural requirement | Validation error citing SEG120-R-007 |
| BR-SEG120-NEG-008 | `SEG120-R-008` | MUT-010 structural / separator violation | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG120-004` (`SEG120-R-004`) — REVIEW_REQUIRED
- `BR-SEG120-008` (`SEG120-R-008`) — REVIEW_REQUIRED

## Open SME items

- **P-02-RESIDUAL** (SEG120-R-004): The field table lists Print Data's own max length as 999, but 1,009 (total) minus 3 (Segment Type) minus 4 (Segment Length, now confirmed fixed-width) minus 2 (separators) = 1,000, not 999 -- a 1-character arithmetic gap between the per-field cap and the total-segment cap. Is 999 a typo for 1,000, is the total cap actually 1,010 (matching the SKU Data Segment's analogous 4-digit-length pattern, '…
- **P-03** (SEG120-R-008): BR-263-5 (excluded from the AI Solution's own SEG-120 bucket, tagged UNASSIGNED) describes the '\' line-delimiter convention used only for Blackhawk phone activation/recharge receipt text. Should the envelope validator check for well-formed '\' delimiters inside Print Data, or is that content-specific business logic that belongs to a separate Blackhawk/loyalty module, analogous to how Segment 111…
- **P-04** (SEG120-R-006): Neither the extracted spec text nor the AI-generated requirements state whether Segment 120 may appear more than once per response message. Should cardinality be constrained to exactly one occurrence (by analogy to the confirmed Segment 101 rule SEG101-R-026), or left unconstrained until confirmed?

## Implementation traceability

- Rule catalog: [coverage/segment-120-rule-catalog.json](coverage/segment-120-rule-catalog.json)
- Validator: `Segment120PayloadValidator`
- Tests: `Segment120ArtifactComparisonTest`, `Segment120ConsolidatedReportTest`, `Segment120IndependenceValidatorTest`, `Segment120MutationTestRunnerTest`, `Segment120MutationTesterTest`, `Segment120PayloadValidatorTest`, `Segment120TraceabilityMatrixTest`
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
