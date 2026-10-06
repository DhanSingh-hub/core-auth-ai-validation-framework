# DL4 AI Artifact Coverage and Validation

**Status:** INCOMPLETE_REVIEW_REQUIRED

**AI scope:** Supplied AI DL4 catalog crosswalk plus one representative phase-one chain and payload.

**Open decisions:** P-01 through P-03 OPEN

## Artifact inventory

| Evidence | Count |
|---|---:|
| Oracle rules | 8 |
| Candidate BR / TS / TC / TD | 8 / 8 / 18 / 18 |
| Supplied AI catalog DL4 requirements | 15 |
| Representative phase-one AI chains | 1 |

## Candidate coverage (not execution coverage)

| Measure | Result |
|---|---:|
| Rules with candidate BR → TS → TC → TD links | 8 / 8 (100.0%) |
| Rules with positive and negative candidates | 8 / 8 (100.0%) |
| Approved pairs / executed cases / certified rules | 0 / 0 / 0 |

### Per-rule traceability

| Rule | Cases | Positive | Negative | Status |
|---|---:|---:|---:|---|
| SEGDL4-R-001 | 2 | 1 | 1 | CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED |
| SEGDL4-R-002 | 3 | 1 | 2 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL4-R-003 | 2 | 1 | 1 | CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED |
| SEGDL4-R-004 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL4-R-005 | 3 | 2 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL4-R-006 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL4-R-007 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL4-R-008 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |

Positive/negative polarity is candidate test intent only. Mappings and candidate percentages are not SME approval, execution, or certification.

## Supplied AI catalog crosswalk

| AI requirement | Source rule | AI match status | Candidate oracle rule | Disposition |
|---|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:060 | ENT-ELEM-57 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:092 | ENT-ELEM-91 | UNMATCHED_IN_TEST_SOLUTION | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:093 | ENT-ELEM-92 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:094 | ENT-ELEM-93 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:095 | ENT-ELEM-94 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:096 | ENT-ELEM-94 | UNMATCHED_IN_TEST_SOLUTION | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:097 | ENT-ELEM-95 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3301 | ENT-FIELD-DL4-1 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-002 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3302 | ENT-FIELD-DL4-2 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3303 | ENT-FIELD-DL4-3 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3304 | ENT-FIELD-DL4-4 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3305 | ENT-FIELD-DL4-5 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3306 | ENT-FIELD-DL4-6 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3307 | ENT-FIELD-DL4-7 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-003, SEGDL4-R-005 | CANDIDATE_MAPPING_REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3308 | ENT-FIELD-DL4-8 | POTENTIAL_MATCH_REVIEW_REQUIRED | SEGDL4-R-002 | CANDIDATE_MAPPING_REVIEW_REQUIRED |

All mappings remain unconfirmed candidate source-element links.

## Observed AI field-name crosswalk

| AI field | Element | Rule | Occurrences |
|---|---:|---|---:|
| DataTypeIndicator | 24 | SEGDL4-R-002 | 2 |
| NewSoftwareVersion | 57 | SEGDL4-R-005 | 2 |
| SoftwareTerminalRecordID | 95 | SEGDL4-R-005 | 2 |
| SoftwareLoadPhoneNumber | 91 | SEGDL4-R-005 | 2 |
| SoftwareLoadRequestDate | 92 | SEGDL4-R-005 | 2 |
| SoftwareLoadRequestTime | 93 | SEGDL4-R-005 | 2 |
| SoftwareLoadType | 94 | SEGDL4-R-005 | 2 |
| EndofDataIndicator | 34 | SEGDL4-R-002 | 2 |

Crosswalk is comparison-only and must not influence the oracle.

## Independent sample validation

| Rule | Result | Observation |
|---|---|---|
| SEGDL4-R-001 | NOT_ASSERTABLE | The supplied payload has no BUYPASS-versus-vendor device-management context. |
| SEGDL4-R-002 | REVIEW_REQUIRED | Logical marker slots are present and correct=true; combined logical field length=52/52 (within limit=true); serialized bytes and P-03 phone-boundary parsing are not asserted. |
| SEGDL4-R-003 | PASS | All six required software schedule fields are present. |
| SEGDL4-R-004 | NOT_ASSERTABLE | The AI artifact is structured JSON; Field Separator placement cannot be checked on serialized bytes. |
| SEGDL4-R-005 | REVIEW_REQUIRED | Version width/type=true; terminal ID width/type=true; phone within AN/18=true; date MMDDYY shape=true; time HHMM shape=true; F/P load type=true; phone parse/padding remains open under P-03. |
| SEGDL4-R-006 | REVIEW_REQUIRED | Message family is 'Software Load Response'; DL5 co-presence=true; merchant SOFT/DLL context and exchange reconciliation remain open under P-02. |
| SEGDL4-R-007 | NOT_ASSERTABLE | No Download Indicator, profile DLL bit, initiating request, scheduled event, or follow-up Table Load context is supplied. |
| SEGDL4-R-008 | NOT_ASSERTABLE | No device retry log or decline-message observation is supplied. |

Totals: **1 PASS**, **0 FAIL**, **3 REVIEW_REQUIRED**, **4 NOT_ASSERTABLE**.

The AI sample is one representative chain, not an exhaustive package. Phone values are excluded from this report. P-01 through P-03 remain open; no data is approved or executed.