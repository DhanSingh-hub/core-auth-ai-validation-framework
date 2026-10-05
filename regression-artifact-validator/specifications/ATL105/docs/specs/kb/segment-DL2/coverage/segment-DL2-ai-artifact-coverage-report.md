# DL2 AI Artifact Coverage and Validation

**Status:** INCOMPLETE_REVIEW_REQUIRED

**AI input scope:** Supplied AI DL2 catalog crosswalk plus one representative phase-one DL2 chain and payload.

## Artifact inventory

| Artifact | Phase-one run | DL2 |
|---|---:|---:|
| Requirements | 47 | 1 |
| Scenarios | 47 | 1 |
| Test cases | 37 | 1 |
| Test data / metadata files | — | 1 / 1 |
| Supplied AI catalog DL2 requirements | — | 16 |

## Coverage measures

| Measure | Result |
|---|---:|
| Complete candidate rule chains | 9 / 9 (100.0%) |
| Rules with positive and negative candidates | 9 / 9 (100.0%) |
| Candidate test cases / request-response pairs | 29 / 29 |
| Executed cases / certified rules | 0 / 0 |
| Oracle rules with candidate AI mappings pending review | 5 / 9 |
| Oracle rules without a candidate supplied-catalog mapping | 4 / 9 |

Candidate mappings are not confirmed semantic equivalence or execution evidence. Phase-one chain data remains one representative DL2 sample; the supplied AI catalog is separately crosswalked below.

## Supplied AI catalog crosswalk

| AI requirement | Source rule | Heuristic match | Candidate oracle rules | Review status |
|---|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:001 | ENT-ELEM-1 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL2-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:027 | ENT-ELEM-27 | UNMATCHED_IN_TEST_SOLUTION | SEGDL2-R-007 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:028 | ENT-ELEM-28 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL2-R-002 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:069 | ENT-ELEM-66 | UNMATCHED_IN_TEST_SOLUTION | SEGDL2-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:073 | ENT-ELEM-75 | UNMATCHED_IN_TEST_SOLUTION | SEGDL2-R-007 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:081 | ENT-ELEM-82 | UNMATCHED_IN_TEST_SOLUTION | SEGDL2-R-006 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3284 | ENT-FIELD-DL2-1 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL2-R-001 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3285 | ENT-FIELD-DL2-2 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL2-R-002 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3286 | ENT-FIELD-DL2-2 | MATCHED_SEMANTICS_ONLY | SEGDL2-R-002 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3287 | ENT-FIELD-DL2-3 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL2-R-006 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3288 | ENT-FIELD-DL2-4 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL2-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3289 | ENT-FIELD-DL2-5 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL2-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3290 | ENT-FIELD-DL2-5 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL2-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3291 | ENT-FIELD-DL2-6 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL2-R-007 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3292 | ENT-FIELD-DL2-7 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL2-R-007 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3293 | ENT-FIELD-DL2-7 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL2-R-007 | CANDIDATE_MAPPING_REVIEW_REQUIRED |

AI catalog links are source-element candidate mappings only. The AI-vs-Test comparison and SME decisions remain authoritative for disposition; heuristic matches are not requirement coverage.

## Candidate rule coverage

| Rule | Cases | Positive | Negative | Status |
|---|---:|---:|---:|---|
| SEGDL2-R-001 | 4 | 1 | 3 | COMPLETE_NOT_EXECUTED |
| SEGDL2-R-002 | 3 | 1 | 2 | COMPLETE_NOT_EXECUTED |
| SEGDL2-R-003 | 3 | 1 | 2 | COMPLETE_NOT_EXECUTED |
| SEGDL2-R-004 | 2 | 1 | 1 | COMPLETE_NOT_EXECUTED |
| SEGDL2-R-005 | 4 | 2 | 2 | COMPLETE_NOT_EXECUTED |
| SEGDL2-R-006 | 4 | 1 | 3 | COMPLETE_NOT_EXECUTED |
| SEGDL2-R-007 | 3 | 1 | 2 | COMPLETE_NOT_EXECUTED |
| SEGDL2-R-008 | 4 | 2 | 2 | COMPLETE_NOT_EXECUTED |
| SEGDL2-R-009 | 2 | 1 | 1 | COMPLETE_NOT_EXECUTED |

## Supplied AI test data validation

| Rule | Status | Observation |
|---|---|---|
| SEGDL2-R-001 | FAIL | Required '!' marker is present; End-of-Data '~' is missing or incorrect; total serialized length remains not assertable from structured fields. |
| SEGDL2-R-002 | PASS | Dial String Type, required primary fields, and primary A terminator are present. |
| SEGDL2-R-003 | FAIL | Secondary phone block and F terminator are absent. |
| SEGDL2-R-004 | NOT_ASSERTABLE | The AI artifact is structured JSON, not serialized bytes; Field Separator placement cannot be verified. |
| SEGDL2-R-005 | FAIL | Primary Access Code/Pause pairing valid=false; secondary pairing valid=true. |
| SEGDL2-R-006 | FAIL | Primary Redial Count valid=true; secondary Redial Count valid=false. |
| SEGDL2-R-007 | FAIL | Primary number numeric/1-18 digits=true; A terminator valid=true; secondary number numeric/1-18 digits=false; F terminator valid=false. |
| SEGDL2-R-008 | FAIL | AI response family is 'Date & Time Load Response'; DL2 is allowed only in Phone Load or Table Load Response. |
| SEGDL2-R-009 | NOT_ASSERTABLE | No merchant-profile PHON load-flag context is present in the supplied AI data. |

Validation totals: **1 PASS**, **6 FAIL**, **0 REVIEW_REQUIRED**, **2 NOT_ASSERTABLE**.

## Corrected candidate and unresolved gates

The separate [corrected AI sample candidate](../../../../../test-output/test-json/segment-DL2-ai-corrected-candidate.json) fixes the known response-family, primary/secondary block, terminator, access/pause pairing, and end-marker defects. It remains synthetic, unapproved, unexecuted, and logical-only. Do not infer Phone Load framing (P-03), fallback timing (P-01), or serialized field boundaries (P-04).