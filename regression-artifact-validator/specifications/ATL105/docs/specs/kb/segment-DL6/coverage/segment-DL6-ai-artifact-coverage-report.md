# DL6 AI Artifact Coverage and Validation

**Status:** INCOMPLETE_REVIEW_REQUIRED

**AI scope:** Supplied AI DL6 catalog filter plus one representative phase-one chain and payload.

**Open decisions:** P-01 through P-06 OPEN, including SEGDL1-SME-003 for block-boundary placement.

## Artifact inventory

| Evidence | Count |
|---|---:|
| Oracle rules | 7 |
| Candidate BR / TS / TC / TD | 7 / 7 / 14 / 14 |
| Supplied AI catalog DL6 requirements | 0 |
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
| SEGDL6-R-001 | 2 | 1 | 1 | CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED |
| SEGDL6-R-002 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL6-R-003 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL6-R-004 | 2 | 1 | 1 | CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED |
| SEGDL6-R-005 | 2 | 1 | 1 | CANDIDATE_CHAIN_COMPLETE_NOT_EXECUTED |
| SEGDL6-R-006 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |
| SEGDL6-R-007 | 2 | 1 | 1 | CANDIDATE_CHAIN_REVIEW_REQUIRED |

## Supplied AI catalog crosswalk

| AI requirement | Source rule | AI match status | Candidate oracle rule | Disposition |
|---|---|---|---|---|

All mappings remain unconfirmed candidate source-element links. The supplied catalog currently has 0 records where segment == DL6.

## Observed AI field-name crosswalk

| AI field | Element | Rule | Occurrences |
|---|---:|---|---:|
| DataTypeIndicator | 24 | SEGDL6-R-002 | 3 |
| StartTime | 166 | SEGDL6-R-005 | 3 |
| EndTime | 166 | SEGDL6-R-005 | 3 |
| EndofDataIndicator | 34 | SEGDL6-R-002 | 3 |

## Independent sample validation

| Rule | Result | Observation |
|---|---|---|
| SEGDL6-R-001 | NOT_ASSERTABLE | The supplied payload has DL1-shaped data but no Card Type 173 field or Table Load Response applicability context. |
| SEGDL6-R-002 | REVIEW_REQUIRED | DL6 marker fields are present=true, marker correctness=true, logical field length=10; the stated 9-versus-10 length issue remains open. |
| SEGDL6-R-003 | REVIEW_REQUIRED | Start and End Time slots are present=true; Element 166 reuse remains open under P-01. |
| SEGDL6-R-004 | NOT_ASSERTABLE | The AI artifact is structured JSON; Field Separator placement cannot be checked on serialized bytes. |
| SEGDL6-R-005 | PASS | Start Time and End Time satisfy HHMM 0000-2359 shape=true. |
| SEGDL6-R-006 | FAIL | Metadata message family is Software Load Phone Response=true; DL6 Data Block 4 Table Load placement is therefore not demonstrated. |
| SEGDL6-R-007 | NOT_ASSERTABLE | No device store-and-forward processing observation, local time basis, or across-midnight behavior is supplied. |

Totals: **1 PASS**, **1 FAIL**, **2 REVIEW_REQUIRED**, **3 NOT_ASSERTABLE**.

The AI sample is one representative chain, not an exhaustive package. Sensitive phone, password, IP/URL, and key values are excluded from this report. P-01 through P-06 remain open; no data is approved or executed.