# DL5 AI Artifact Coverage and Validation

**Status:** INCOMPLETE_REVIEW_REQUIRED

**AI scope:** Supplied AI DL5 catalog crosswalk plus one representative phase-one chain and payload.

**Open decisions:** P-01 through P-03 OPEN

## Artifact inventory

| Evidence | Count |
|---|---:|
| Oracle rules | 7 |
| Candidate BR / TS / TC / TD | 7 / 7 / 14 / 14 |
| Supplied AI catalog DL5 requirements | 10 |
| Representative phase-one AI chains | 1 |

## Candidate coverage (not execution coverage)

| Measure | Result |
|---|---:|
| Rules with candidate BR → TS → TC → TD links | 7 / 7 (100.0%) |
| Rules with positive and negative candidates | 7 / 7 (100.0%) |
| Approved pairs / executed cases / certified rules | 0 / 0 / 0 |

### Per-rule traceability

| Rule | Cases | Positive | Negative | Status |
|---|---:|---:|---:|---|
| SEGDL5-R-001 | 2 | 1 | 1 | CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED |
| SEGDL5-R-002 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL5-R-003 | 2 | 1 | 1 | CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED |
| SEGDL5-R-004 | 2 | 1 | 1 | CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED |
| SEGDL5-R-005 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL5-R-006 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL5-R-007 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |

Positive/negative polarity is candidate test intent only. Mappings and candidate percentages are not SME approval, execution, or certification.

## Supplied AI catalog crosswalk

| AI requirement | Source rule | AI match status | Candidate oracle rule | Disposition |
|---|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:118 | ENT-ELEM-114 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL5-R-003, SEGDL5-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:119 | ENT-ELEM-114 | UNMATCHED_IN_TEST_SOLUTION | SEGDL5-R-003, SEGDL5-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3309 | ENT-FIELD-DL5-1 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL5-R-002 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3310 | ENT-FIELD-DL5-2 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL5-R-003, SEGDL5-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3311 | ENT-FIELD-DL5-3 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL5-R-003, SEGDL5-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3312 | ENT-FIELD-DL5-4 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL5-R-003, SEGDL5-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3313 | ENT-FIELD-DL5-5 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL5-R-003, SEGDL5-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3314 | ENT-FIELD-DL5-6 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL5-R-003, SEGDL5-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3315 | ENT-FIELD-DL5-7 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL5-R-003, SEGDL5-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3316 | ENT-FIELD-DL5-8 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL5-R-002 | CANDIDATE_MAPPING_REVIEW_REQUIRED |

All mappings remain unconfirmed candidate source-element links.

## Observed AI field-name crosswalk

| AI field | Element | Rule | Occurrences |
|---|---:|---|---:|
| DataTypeIndicator | 24 | SEGDL5-R-002 | 2 |
| NewSoftwareVersion | 57 | SEGDL5-R-005 | 2 |
| SoftwareTerminalRecordID | 95 | SEGDL5-R-005 | 2 |
| SoftwareLoadIPURLAddress | 114 | SEGDL5-R-005 | 2 |
| SoftwareLoadRequestDate | 92 | SEGDL5-R-005 | 2 |
| SoftwareLoadRequestTime | 93 | SEGDL5-R-005 | 2 |
| SoftwareLoadType | 94 | SEGDL5-R-005 | 2 |
| EndofDataIndicator | 34 | SEGDL5-R-002 | 2 |

Crosswalk is comparison-only and must not influence the oracle.

## Independent sample validation

| Rule | Result | Observation |
|---|---|---|
| SEGDL5-R-001 | NOT_ASSERTABLE | The supplied payload has no BUYPASS-versus-vendor device-management context. |
| SEGDL5-R-002 | REVIEW_REQUIRED | Logical marker slots are present and correct=true; combined logical field length=36/64 (within limit=true); 64-vs-66 boundary remains open under P-02. |
| SEGDL5-R-003 | PASS | DL5 contains the DL4-style schedule fields with Element 114 in field 4: present. |
| SEGDL5-R-004 | NOT_ASSERTABLE | The AI artifact is structured JSON; Field Separator placement cannot be checked on serialized bytes. |
| SEGDL5-R-005 | FAIL | Version width/type=true; terminal ID width/type=true; IP/URL AN/30 candidate=false; date MMDDYY shape=true; time HHMM shape=true; F/P load type=true; Element 114 content and padding remain open under P-03. |
| SEGDL5-R-006 | REVIEW_REQUIRED | Message family is 'Software Load Response'; DL4 co-presence=true; merchant SOFT/DLL context and exchange reconciliation remain open under SEGDL4-SME-002. |
| SEGDL5-R-007 | NOT_ASSERTABLE | No scheduled device IP-load attempt log, retry count, decline message, or follow-up Table Load context is supplied. |

Totals: **1 PASS**, **1 FAIL**, **2 REVIEW_REQUIRED**, **3 NOT_ASSERTABLE**.

The AI sample is one representative chain, not an exhaustive package. Sensitive payload values are excluded from this report. P-01 through P-03 remain open; no data is approved or executed.