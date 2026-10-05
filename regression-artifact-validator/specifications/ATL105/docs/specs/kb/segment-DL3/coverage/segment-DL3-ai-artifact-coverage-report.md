# DL3 AI Artifact Coverage and Validation

**Status:** INCOMPLETE_REVIEW_REQUIRED

**AI scope:** Supplied AI DL3 catalog crosswalk plus one representative phase-one chain and payload.

**Open decisions:** P-01 through P-04 OPEN

## Artifact inventory

| Evidence | Count |
|---|---:|
| Oracle rules | 9 |
| Candidate BR / TS / TC / TD | 9 / 9 / 18 / 18 |
| Supplied AI catalog DL3 requirements | 12 |
| Representative phase-one AI chains | 1 |

## Candidate coverage (not execution coverage)

| Measure | Result |
|---|---:|
| Rules with candidate BR → TS → TC → TD links | 9 / 9 (100.0%) |
| Rules with positive and negative candidates | 6 / 9 (66.7%) |
| Approved pairs / executed cases / certified rules | 0 / 0 / 0 |

### Per-rule candidate traceability

| Rule | Cases | Positive | Negative | Status |
|---|---:|---:|---:|---|
| SEGDL3-R-001 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL3-R-002 | 2 | 1 | 1 | CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED |
| SEGDL3-R-003 | 1 | 0 | 0 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL3-R-004 | 2 | 1 | 1 | CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED |
| SEGDL3-R-005 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL3-R-006 | 2 | 0 | 0 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL3-R-007 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL3-R-008 | 2 | 1 | 1 | CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED |
| SEGDL3-R-009 | 3 | 0 | 0 | CANDIDATE_CHAIN_REVIEW_REQUIRED |

No candidate mappings or counts constitute SME approval, accepted AI equivalence, execution, or certification.

## Supplied AI catalog crosswalk

| AI requirement | Source rule | AI match status | Candidate Test Solution rule(s) | Disposition |
|---|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:020 | ENT-ELEM-21 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL3-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:021 | ENT-ELEM-22 | UNMATCHED_IN_TEST_SOLUTION | SEGDL3-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:022 | ENT-ELEM-23 | UNMATCHED_IN_TEST_SOLUTION | SEGDL3-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:025 | ENT-ELEM-25 | UNMATCHED_IN_TEST_SOLUTION | SEGDL3-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:034 | ENT-ELEM-34 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL3-R-001 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3294 | ENT-FIELD-DL3-1 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL3-R-001 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3295 | ENT-FIELD-DL3-2 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL3-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3296 | ENT-FIELD-DL3-3 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL3-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3297 | ENT-FIELD-DL3-4 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL3-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3298 | ENT-FIELD-DL3-5 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL3-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3299 | ENT-FIELD-DL3-6 | MATCHED_ELEMENT_AND_SEMANTICS | SEGDL3-R-003, SEGDL3-R-006 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3300 | ENT-FIELD-DL3-7 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL3-R-001 | CANDIDATE_MAPPING_REVIEW_REQUIRED |

AI source-element links are candidate comparisons only; all mappings remain unconfirmed.

## AI field-name crosswalk

| AI field name | ATL105 element | Rule | Observed occurrences |
|---|---:|---|---:|
| DataTypeIndicator | 24 | SEGDL3-R-001 | 5 |
| DayoftheWeek | 25 | SEGDL3-R-005 | 5 |
| CurrentDate | 21 | SEGDL3-R-005 | 5 |
| CurrentTime | 22 | SEGDL3-R-005 | 5 |
| CutTime | 23 | SEGDL3-R-005 | 5 |
| Password | 65 | SEGDL3-R-006 | 5 |
| EndofDataIndicator | 34 | SEGDL3-R-001 | 5 |

The field-name crosswalk is comparison-only and does not influence oracle rules or validators.

## Independent validation of the representative AI sample

| Rule | Result | Observation |
|---|---|---|
| SEGDL3-R-001 | REVIEW_REQUIRED | Logical marker/width shape valid=true; serialized length and Date and Time Load Response end framing are not independently assertable. |
| SEGDL3-R-002 | PASS | All required logical field slots are present. |
| SEGDL3-R-003 | REVIEW_REQUIRED | Password source conflicts between Sections 12.44 and 11.7.3.2; field value is intentionally not included in this report. |
| SEGDL3-R-004 | NOT_ASSERTABLE | The supplied artifact is structured JSON, not a serialized DL3 byte string. |
| SEGDL3-R-005 | FAIL | Day valid=false; Current Date has six-digit MMDDYY shape=true; Current Time/Cut Time have four-digit HHMM shape=true; HHMM boundary interpretation remains open under P-04. |
| SEGDL3-R-006 | REVIEW_REQUIRED | Password source/ownership remains open under P-02; no Password value is emitted or used for comparison. |
| SEGDL3-R-007 | FAIL | DL3 sample message family is 'Software Load Phone Response'; Date and Time Load Response framing remains open under P-03. |
| SEGDL3-R-008 | NOT_ASSERTABLE | The AI sample contains no request object, Load Type, or merchant-load-flag context. |
| SEGDL3-R-009 | NOT_ASSERTABLE | No device settlement observation or automatic-cut-time lifecycle context is supplied. |

Totals: **1 PASS**, **2 FAIL**, **3 REVIEW_REQUIRED**, **3 NOT_ASSERTABLE**.

The representative sample is one chain, not an exhaustive DL3 AI package. Password values are deliberately excluded from this report. P-01 through P-04 remain open; no data is approved or executed.