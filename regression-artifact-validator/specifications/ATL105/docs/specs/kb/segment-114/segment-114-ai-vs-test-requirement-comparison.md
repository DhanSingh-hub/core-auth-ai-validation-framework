# Segment 114: AI-Generated vs Test-Generated Requirement Comparison

This document extracts every AI Solution Team requirement statement that references Segment 114 (SKU Data Segment) from the ingested AI artifact pipeline and matches it against the Test Team's independently-derived rule catalog ([`segment-114-rule-catalog.json`](coverage/segment-114-rule-catalog.json)). It follows the same producer-neutral comparison principle as the [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md) Item 2 ("AI Artifact Comparison"): the specification is the oracle, not the AI output.

## Evidence Sources

- `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`
- `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/candidates/requirement_candidates.json`
- `test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.json`
- `test-output/test-solution-independent-review/atomic-br-ai-crosswalk.csv` / `.json`
- `test-output/ai-solution-independent-review/run2-br-ts-tc-td-gap-details.csv`
- `test-output/ai-solution-independent-review/run2-br-ts-tc-td-gap-report-for-ai-team.md`

## 1. AI-Generated Requirement Statements Extracted

| AI Requirement ID | Statement | Source Rule ID | Source Page |
|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:1189 | "SKU Data Segment (No. 114) always appears in Field No. 5 in Data Section No. 3." | BR-248-1 | 248 |
| REQ-SRC-ATL105-PDF-001:1190 | "SKU Data Segment has a maximum length of 1010 alphanumeric characters (001-1010/a-z/A-Z)." | BR-248-2 | 248 |
| REQ-SRC-ATL105-PDF-001:1191 | "All fields in the SKU Data Segment are separated by Field Separators; a Field Separator follows Field No. 3. When a field is not populated, still send the Field Separator." | BR-248-3 | 248 |
| REQ-SRC-ATL105-PDF-001:1192 | "SKU Data Segment originates at the device." | BR-248-4 | 248 |
| REQ-SRC-ATL105-PDF-001:3842 | "The Financial Transaction Request transaction includes SKU Data Segment (Data Segment No. 114)." | REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST | — |
| REQ-SRC-ATL105-PDF-001:3843 | "The Loyalty Card Transaction transaction includes SKU Data Segment (Data Segment No. 114)." | REL-ENT-SEG-114-LOYALTY_CARD_TRANSACTION | — |
| REQ-SRC-ATL105-PDF-001:4163 | "The Loyalty Card Transaction Request transaction includes SKU Data Segment (Data Segment No. 114)." | ENT-SEG-114 | — |
| (candidates) | "In SKU Data Segment (Data Segment No. 114), field 1 (Segment Type) is required and must be present." | (field-level, condition_trigger: SKU Data Segment present) | — |
| (candidates) | "In SKU Data Segment (Data Segment No. 114), field 2 (Segment Length) is required and must be present." | (field-level, condition_trigger: SKU Data Segment present) | — |
| (candidates) | "In SKU Data Segment (Data Segment No. 114), field 3 (SKU Data) is required and must be present." | (field-level, condition_trigger: SKU Data Segment present) | — |
| (candidates) | "SKU Data Segment length is 0001-1010." | — | — |
| (candidates) | "SKU Data Segment is optional and contains bar code SKU data." | — | — |
| (candidates) | "SKU Data Segment: Data Segment No. 114, appears in Field No. 5 of Data Section No. 3, originates at device." | — | — |
| (candidates) | "Loyalty Card Transaction Request Data Section 3 may contain none, one, or more of Loyalty Card Data Segment or SKU Data Segment." | — | — |

## 2. Match Against the Test Team's Rule Catalog

| AI Statement | AI Solution Team's Own Crosswalk Result | Correct Test Rule (this training pass) | Match Verdict |
|---|---|---|---|
| BR-248-1: "always appears in Field No. 5..." | `POTENTIAL_MATCH_REVIEW_REQUIRED` against `BR-SEG103-CORE-001` ("EBT Data Segment is a Data Section 3 companion segment usable in any field slot") — **wrong segment**, semantic score 0.375 | `SEG114-R-002` | **CONFIRMED equivalent** once correctly attributed to Segment 114; the AI Solution Team's automated segment-matcher paired it with Segment 103 instead. |
| BR-248-2: "maximum length of 1010..." | `MATCHED_SEMANTICS_ONLY` against `BR-SEG104-MAX-LENGTH` ("Purchase Card Data Segment has a maximum length of 86 alphanumeric characters") — **wrong segment**, semantic score 0.667 | `SEG114-R-005` | **PARTIALLY CONFIRMED**: the 1010 figure matches Section 12.13's own text, but conflicts with the 1009 figure in the Loyalty Card Transaction Request layout table (Section 11.2.1) — flagged `[PROVISIONAL SEG114-SME-001]`. |
| BR-248-3: "fields ... separated by Field Separators..." | `POTENTIAL_MATCH_REVIEW_REQUIRED` against `BR-SEG111-REPETITION-SEPARATOR` — **wrong segment**, semantic score 0.312 | `SEG114-R-007` | **CONFIRMED equivalent** once correctly attributed to Segment 114. |
| BR-248-4: "originates at the device." | `POTENTIAL_MATCH_REVIEW_REQUIRED` against `BR-SEG101-CORE-001` ("Fleet Data Segment is a Data Section 3 companion of Segment 100") — **wrong segment**, semantic score 0.2 | `SEG114-R-009` | **CONFIRMED equivalent** once correctly attributed to Segment 114. |
| Field 1 (Segment Type) required | Not present in the supplied crosswalk (field-level candidate only) | `SEG114-R-003` | **CONFIRMED**, with the caveat that the fixed value "114" is not printed as an explicit "Fixed value" statement (`[PROVISIONAL SEG114-SME-004]`). |
| Field 2 (Segment Length) required | Not present in the supplied crosswalk (field-level candidate only) | `SEG114-R-004` | **CONFIRMED** (4-digit length; independently cross-checked against Segment 120's rule catalog). |
| Field 3 (SKU Data) required | Not present in the supplied crosswalk (field-level candidate only) | `SEG114-R-008` | **CONFIRMED**. |
| "SKU Data Segment length is 0001-1010." | Not present in the supplied crosswalk | `SEG114-R-004` | **CONFIRMED**. |
| "SKU Data Segment is optional..." | Not present in the supplied crosswalk | `SEG114-R-002` | **CONFIRMED**. |
| REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST | Not present in the supplied crosswalk as a standalone comparison | **No Test rule supports this** | **REJECTED (confirmed 2026-09-26)** — no specification citation in Sections 11.1.1, 11.3.1, or 11.9.1 lists Segment 114 among Financial Transaction Request companions. SME/TBA intake confirmed this is an AI Solution Team error. Logged as `SEG114-R-012`, classification `UNSUPPORTED_BY_SPECIFICATION` / `REJECTED`; report back to the AI Solution Team as a defect. |
| REL-ENT-SEG-114-LOYALTY_CARD_TRANSACTION | Not present in the supplied crosswalk as a standalone comparison | `SEG114-R-001`, `SEG114-R-011` | **CONFIRMED** — consistent with Section 11.2.1. |
| ENT-SEG-114 (Loyalty Card Transaction Request includes SKU Data Segment) | Not present in the supplied crosswalk as a standalone comparison | `SEG114-R-001`, `SEG114-R-002` | **CONFIRMED**. |
| "Data Section 3 may contain none, one, or more of Loyalty Card Data Segment or SKU Data Segment." | Not present in the supplied crosswalk | Partially matches `SEG114-R-002` | **PARTIALLY ACCURATE** — this phrasing is reused boilerplate from the generic Financial Transaction Request Data Section 3 description ("contains none, one, or more"). It is imprecise for the Loyalty Card Transaction Request, whose table actually shows Segment 108 as **required** (not "none, one, or more") and Segment 114 as the only truly optional slot. |

## 3. Test-Generated Traceability Gap Baseline (Before This Training Pass)

Independent Test Solution review already flagged Segment 114 requirement IDs as incomplete through the BR→TS→TC→TD chain (`run2-br-ts-tc-td-gap-details.csv`, `run2-br-ts-tc-td-gap-report-for-ai-team.md`):

| AI Requirement ID | Entity | Gap Status | Meaning |
|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:1230 | ENT-SEG-114 | `REQUIREMENT_ONLY` | No test scenario has been generated from this requirement yet. |
| REQ-SRC-ATL105-PDF-001:169 | ENT-SEG-114 | `TEST_CASE_NO_DATA` | Test cases exist but no test data file resolves on disk. |
| REQ-SRC-ATL105-PDF-001:4163 | ENT-SEG-114 | `SCENARIO_ONLY` | A scenario exists but no test case has been generated yet. |

Aggregate: **1 requirement-only, 1 scenario-only, 1 test-case-without-data** gap recorded for `ENT-SEG-114` prior to this training pass (`run2-br-ts-tc-td-gap-report-for-ai-team.md` row: `ENT-SEG-114 | 1 | 1 | 1 | 0 | 3`). This is consistent with the KB module's prior `PLACEHOLDER` status — no Segment 114-specific coverage work had been done.

## 4. Headline Findings

1. **No confirmed AI-to-Test match existed for Segment 114 before this training pass.** All four `BR-248-*` statements were auto-matched by the AI Solution Team's tooling against the wrong segment's business requirements (103, 104, 111, 101), each below or only marginally above the confirmation threshold. This is expected: no Segment 114-specific Test rule existed for the matcher to find.
2. **One AI-generated relationship was confirmed unsupported by the specification and rejected** (`REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST`) — SME/TBA intake on 2026-09-26 confirmed this should be reported back to the AI Solution Team as a defect.
3. **The core field-level facts the AI extracted are accurate** (Field No. 5, 1010 max length — confirmed authoritative over the layout table's 1009 — Field Separator rule, device origin, 3-field layout, required-when-present field rules) and now have a corresponding, source-anchored, SME-confirmed Test rule (`SEG114-R-002` through `SEG114-R-009`).
4. **Two AI statements reuse imprecise boilerplate** ("none, one, or more") that does not reflect Segment 108's actual required status within the Loyalty Card Transaction Request — flagged for correction rather than direct adoption.
5. **One AI-adjacent question exceeded what any AI statement claimed**: whether Segment 114 can repeat per message. SME/TBA intake on 2026-09-26 confirmed it can, once per scanned SKU — a fact not present in any of the extracted AI statements above, and now captured only in the Test Team's catalog (`SEG114-R-010`).

## 5. Next Steps

- All six SME/TBA input items (`SEG114-SME-001` through `SEG114-SME-006`) were resolved on 2026-09-26 — see the [SME/TBA Input Register](segment-114-sme-tba-input-register.md).
- Re-run the AI-to-Test crosswalk generator against the now-published `segment-114-rule-catalog.json` so future crosswalk runs produce `CONFIRMED` matches instead of cross-segment `POTENTIAL_MATCH_REVIEW_REQUIRED` results.
- Report the `REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST` statement back to the AI Solution Team as a confirmed defect.
- Proceed to Item 2 (AI Artifact Comparison) and Item 3 (Independence) using synthesized `.synthetic.json` fixtures, per the approved `SEG114-SME-005` resolution, pending delivery of real bar-code SKU sample data.
