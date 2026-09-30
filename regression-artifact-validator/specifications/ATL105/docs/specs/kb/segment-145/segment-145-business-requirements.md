# Segment 145 Business Requirements

**Segment:** 145 — Enhanced Fleet Request Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.31  
**Oracle:** [segment-145-rule-catalog.json](coverage/segment-145-rule-catalog.json) (8 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Scope and provenance

This catalog restates each Segment 145 specification rule as an independently testable business requirement with acceptance criteria and a required negative case. It mirrors the structure of the Segment 100 [financial card-type business requirements](../segment-100/financial-card-type-business-requirements.md).

Requirements are derived **only** from the Segment 145 rule catalog and the ATL105 specification. AI-generated artifacts and JSON test fixtures are not used as requirement evidence.

## Rule composition

| Class | Rules |
|---|---:|
| field | 4 |
| structure | 1 |
| compatibility | 1 |
| applicability | 1 |
| lifecycle | 1 |
| **Total** | **8** |

## Requirements

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG145-001 | structure | Segment 145 carries fleet data for enhanced fleet offerings (Wex OTR, Comdata, Voyager EMV, Visa Fleet 2.0, MasterCard Enhanced Fleet EMV); it may hold multiple TLV-encoded sub-segments (sub-segment type, sub-segment length, sub-segment value) | The segment's structural position and composition match the rule. | `SEG145-R-001` §12.31 | SPEC_DERIVED |
| BR-SEG145-002 | compatibility | Merchants should NOT send Segment 101 (Fleet Data Segment) and Segment 145 (Enhanced Fleet Request Segment) together in the same message | Only the permitted companion segments / message families carry this segment. | `SEG145-R-002` §12.31 | SPEC_DERIVED |
| BR-SEG145-003 | applicability | Only the Prompt Table sub-segment (Table ID 004) is valid for Voyager EMV, Visa Fleet 2.0, Comdata, and MasterCard Enhanced Fleet EMV transactions — other sub-segment tables (001, 002, 006, 007, 008) are restricted to different authorizer contexts not enumerated by this restriction | Segment presence or absence matches the stated condition for the message family; a violation fails citing the rule ID. | `SEG145-R-003` §12.31 | REVIEW_REQUIRED |
| BR-SEG145-004 | lifecycle | MasterCard Enhanced Fleet EMV functionality was, as of the specification's authoring, expected to be enabled in First Data production no earlier than June 2026; until then it is for development/certification preparation only and must not be used in live production transactions | Paired messages are present and the correlated values agree. | `SEG145-R-004` §12.31 | REVIEW_REQUIRED |
| BR-SEG145-005 | field | Segment Type is fixed value 145, Segment Length includes Segment Type's length; both Device-sourced | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG145-R-005` §12.31 | SPEC_DERIVED |
| BR-SEG145-006 | field | Enhanced Fleet Data (Element 239) is required, max 999 characters, containing one or more sub-segments in <tag><len><data> format, cataloged as: Table 001 (Request Flags, incl. Commercial/Retail Flag), Table 002 (Non-Fuel Product Data, product-category-coded, '|'-delimited repeating), Table 004 (Prompt Data, authorizer-specific prompt tokens, '|'-delimited repeating), Table 006 (Money Code Payee Name), Table 007 (Money Code Check Number, required for all Money Code transactions), Table 008 (Cash Advance Limit) | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG145-R-006` §12.31 | REVIEW_REQUIRED |
| BR-SEG145-007 | field | Table 002 (Non-Fuel Product Data) product categories are authorizer-specific; a documented list applies to WEX OTR transactions (e.g., ADD, ANFR, BRAK, ... WWFL); other authorizers' category lists are not enumerated in this section | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG145-R-007` §12.31 | SPEC_DERIVED |
| BR-SEG145-008 | field | Table 004 (Prompt Data) prompt tokens are authorizer-specific and independently cataloged for Voyager EMV (DF-tag-based), Visa Fleet 2.0, Comdata, WEX OTR, and Conexxus (numeric prompt codes, used by MasterCard Enhanced Fleet EMV); full enumeration of all prompt-token tables is out of scope for this rule catalog pass | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG145-R-008` §12.31 | REVIEW_REQUIRED |

## Required negative coverage

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG145-NEG-001 | `SEG145-R-001` | MUT-001 wrong fixed value | Validation error citing SEG145-R-001 |
| BR-SEG145-NEG-002 | `SEG145-R-002` | MUT-010 structural requirement | Validation error citing SEG145-R-002 |
| BR-SEG145-NEG-003 | `SEG145-R-003` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG145-NEG-004 | `SEG145-R-004` | MUT-010 structural requirement | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG145-NEG-005 | `SEG145-R-005` | MUT-001 wrong fixed value | Validation error citing SEG145-R-005 |
| BR-SEG145-NEG-006 | `SEG145-R-006` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |
| BR-SEG145-NEG-007 | `SEG145-R-007` | MUT-010 structural requirement | Validation error citing SEG145-R-007 |
| BR-SEG145-NEG-008 | `SEG145-R-008` | MUT-003 length violation | Held at REVIEW_REQUIRED — do not assert until resolved |

## Requirements that must not be certified yet

- `BR-SEG145-003` (`SEG145-R-003`) — REVIEW_REQUIRED
- `BR-SEG145-004` (`SEG145-R-004`) — REVIEW_REQUIRED
- `BR-SEG145-006` (`SEG145-R-006`) — REVIEW_REQUIRED
- `BR-SEG145-008` (`SEG145-R-008`) — REVIEW_REQUIRED

## Open SME items

- **P-01** (SEG145-R-003): Which authorizer(s) use Enhanced Fleet Data Tables 001, 002, 006, 007, 008 (as opposed to the Prompt-Table-only restriction for Voyager EMV/Visa Fleet 2.0/Comdata/MasterCard Enhanced Fleet EMV)?
- **P-02** (SEG145-R-004): Confirm current production-enablement status of MasterCard Enhanced Fleet EMV as of the training date (2026-09-26) — the spec's 'no earlier than June 2026' notice may now be superseded.
- **P-03** (SEG145-R-006): Confirm whether Table IDs 003/005 being request-absent but response-present (Segment 146) is intentional asymmetry.
- **P-04** (SEG145-R-008): Is full per-token validation for all 5 authorizer-specific prompt-token catalogs required for Item 1 sign-off, or is format-only (existence + max length) validation sufficient for this training pass?
- **P-05** (AI-artifacts, test-data): No dedicated Segment 145 AI or Test package was located. Provide one, or approve synthesized fixtures.

## Implementation traceability

- Rule catalog: [coverage/segment-145-rule-catalog.json](coverage/segment-145-rule-catalog.json)
- Validator: _not yet implemented_
- Tests: _none yet_
- BR IDs in this file are positional against the catalog; if the catalog changes, regenerate this file.
