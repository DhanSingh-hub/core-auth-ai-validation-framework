# Segment 115: AI-Generated vs Test-Generated Requirement Comparison

This document extracts every AI Solution Team requirement statement from source page 249 (Section 12.14, Print Data Segment) and matches it against the Test Team's independently-derived rule catalog ([`segment-115-rule-catalog.json`](coverage/segment-115-rule-catalog.json)), following the same producer-neutral principle used for Segments 108 and 114.

## Evidence Sources

- `test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.json`
- `test-output/ai-artifacts/business-requirements/POC-AI-ATL105-Segment-*-Business-Requirements.json` (BR-249-* cross-references embedded in unrelated segments' files)
- `test-output/test-solution-independent-review/atomic-br-ai-crosswalk.csv` / `.json`
- `test-output/ai-solution-independent-review/run1-run2-composite-delivery/confirmed-matches.json`

## 1. AI-Generated Requirement Statements Extracted (Source Page 249)

| AI Requirement ID | Statement | Source Rule ID |
|---|---|---|
| REQ-SRC-ATL105-PDF-001:1193 | "The Print Data Segment (115) is only sent on Financial Transaction Response messages requiring large amounts of print data." | BR-249-1 |
| REQ-SRC-ATL105-PDF-001:1194 | "The Print Data Segment always appears at the end of a Financial Transaction Response." | BR-249-2 |
| REQ-SRC-ATL105-PDF-001:1195 | "The Print Data Segment has a maximum length of 1,009 alphanumeric characters." | BR-249-3 |
| REQ-SRC-ATL105-PDF-001:1196 | "When Print Data Segment (115) is present in a Financial Transaction Response, no other data segments are contained in that response." | BR-249-4 |
| REQ-SRC-ATL105-PDF-001:1197 | "A Field Separator exists between Field Nos. 1 and 2 and between Field Nos. 2 and 3 of Segment 115." | BR-249-5 |
| REQ-SRC-ATL105-PDF-001:1198 | "Segment Type field of Segment 115 has fixed value 115." | BR-249-6 |
| REQ-SRC-ATL105-PDF-001:1199 | "The Print Data field (152) contains the terms & conditions for Blackhawk phone activation and recharge receipts." | BR-249-7 |

## 2. Match Against the Test Team's Rule Catalog

| AI Statement | AI Solution Team's Own Crosswalk Result | Correct Test Rule (this training pass) | Match Verdict |
|---|---|---|---|
| BR-249-1: "only sent on ... requiring large amounts of print data" | `POTENTIAL_MATCH_REVIEW_REQUIRED` against `BR-SEG103-SEPARATOR-BEHAVIOR` — **wrong segment**, semantic score 0.286 | `SEG115-R-001` | **CONFIRMED equivalent** once correctly attributed to Segment 115. |
| BR-249-2: "always appears at the end of a Financial Transaction Response" | `POTENTIAL_MATCH_REVIEW_REQUIRED` against `BR-SEG101-CORE-002` — **wrong segment**, semantic score 0.375; separately appears as a `CONFIRMED` 80%-HIGH match against `SEG100-R-015` ("Segment Type is 100") in `confirmed-match-pairs.csv`, which is also an unrelated rule | `SEG115-R-002` | **CONFIRMED equivalent** once correctly attributed to Segment 115; flag the `SEG100-R-015` "confirmed" pairing as a false positive — the two statements share no real semantic content. |
| BR-249-3: "maximum length of 1,009 alphanumeric characters" | `MATCHED_SEMANTICS_ONLY` against `BR-SEG101-MAX-LENGTH` ("Fleet Data Segment ... 61 alphanumeric characters") — **wrong segment**, semantic score 0.75 | `SEG115-R-005` | **PARTIALLY CONFIRMED**: 1,009 matches Section 12.14's own text, but conflicts with the 910 (Section 11.1.2 table) and 999 (EMV table) figures — flagged `[PROVISIONAL SEG115-SME-001]`. |
| BR-249-4: "no other data segments are contained in that response" | `POTENTIAL_MATCH_REVIEW_REQUIRED` against an unrelated Segment 100 Appendix C rule — **wrong segment**, semantic score 0.333 | `SEG115-R-010` | **DISPUTED**: this is a literal, faithful extraction of Section 12.14's sentence, but it appears to contradict the Section 11.1.2 layout table showing Segment 112 and Segment 115 as independently conditional in the same response. Flagged `[PROVISIONAL SEG115-SME-004]` — this is a genuine specification ambiguity, not an AI extraction error. |
| BR-249-5: "Field Separator ... between 1-2 and 2-3" | `UNMATCHED_IN_TEST_SOLUTION` against `BR-SEG111-REPETITION-SEPARATOR` — **wrong segment**, semantic score 0.188 | `SEG115-R-007` | **CONFIRMED equivalent** once correctly attributed to Segment 115. Important: this same ID (`BR-249-5`) is reused elsewhere in the AI catalog for the *different* Segment-Type statement — see `AI-DEFECT-115-001` in the rule catalog. |
| BR-249-6: "Segment Type field ... fixed value 115" | `MATCHED_SEMANTICS_ONLY` against `BR-SEG103-TYPE` ("Segment Type field in the EBT Data Segment has fixed value 103") — **wrong segment**, semantic score 0.625 | `SEG115-R-003` | **CONFIRMED equivalent** once correctly attributed to Segment 115. |
| BR-249-7: "Print Data field (152) contains ... Blackhawk phone activation and recharge receipts" | `UNMATCHED_IN_TEST_SOLUTION` against `BR-SEG104-LENGTH` — **wrong segment**, semantic score 0.167 | `SEG115-R-008` | **CONFIRMED equivalent** once correctly attributed to Segment 115. A valuable, specific business fact the AI correctly extracted verbatim. |

## 3. Headline Findings

1. **No confirmed AI-to-Test match existed for Segment 115 before this training pass.** All seven `BR-249-*` statements were auto-matched by the AI Solution Team's tooling against the wrong segment (101, 103, 104, 111, plus one false-positive "confirmed" match against Segment 100), because no Segment 115-specific Test rule existed for the matcher to find.
2. **One AI-generated ID collision was discovered**: `BR-249-5` is used for two different statements across different per-segment BR files (see `AI-DEFECT-115-001`). The canonical `supplied-ai-catalog-coverage.json` resolves this correctly; per-segment BR files do not.
3. **One AI statement (`BR-249-4`) surfaces a genuine, unresolved specification ambiguity** rather than an AI extraction error — the literal "no other data segments" sentence appears to conflict with the Section 11.1.2 layout table. This is exactly the kind of finding that must be routed to SME/TBA, not silently resolved either way.
4. **The core field-level facts the AI extracted are accurate** (response-only, end-of-response placement, 1,009 max length, no-trailing-separator wire format, Segment Type 115, Blackhawk print-data content) and now have a corresponding, source-anchored Test rule (`SEG115-R-001`, `SEG115-R-003`, `SEG115-R-007`, `SEG115-R-008`).
5. **Two additional facts (conditional-inclusion mechanism and the Loyalty Print Data connection) are not covered by any AI statement at all** — they were discovered only through cross-referencing Sections 10.9, 11.1.2, and Segment 108's own KB, and remain open questions (`SEG115-SME-002`, `SEG115-SME-005`).

## 4. Next Steps

- Route `SEG115-SME-001` through `SEG115-SME-006` (see [SME/TBA Input Register](segment-115-sme-tba-input-register.md)) for resolution.
- Report the `BR-249-5` ID-collision defect back to the AI Solution Team.
- Report the `BR-249-4` / Section 11.1.2 apparent contradiction to the specification owner for clarification, not just the AI Solution Team.
- Once resolved, re-run the AI-to-Test crosswalk generator against the now-published `segment-115-rule-catalog.json`.
