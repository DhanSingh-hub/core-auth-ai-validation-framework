# DL1 AI Artifact Coverage and Validation

**Status:** INCOMPLETE_REVIEW_REQUIRED

**AI input scope:** One representative DL1 chain from the 2026-09-29 phase_1_single_leg run; not the complete AI DL1 catalog.

## AI artifact inventory

| Artifact | Phase-one run | DL1 |
|---|---:|---:|
| Requirements | 47 | 1 |
| Scenarios | 47 | 1 |
| Test cases | 37 | 1 |
| Test data / metadata files | — | 1 / 1 |

## Coverage measures

| Measure | Result |
|---|---:|
| Test Solution rules with complete candidate chains | 12 / 12 (100.0%) |
| Test Solution rules with positive and negative candidates | 12 / 12 (100.0%) |
| AI requirements with positive/negative Test Solution candidates | 1 / 1 (100.0%) |
| AI requirements executed by the Test Solution | 0 / 1 (0.0%) |
| Oracle rules with direct/full AI requirement evidence | 0 / 12 (0.0%) |
| Oracle rules with partial AI evidence | 1 / 12 (8.3%) |
| Oracle rules with no AI evidence in this sample | 11 / 12 (91.7%) |

Candidate definitions are not executed coverage. AI input is one representative DL1 requirement, not the complete AI catalog.

## Oracle rule crosswalk

| Rule | AI BR evidence | AI evidence | Test Solution candidates |
|---|---|---|---|
| SEGDL1-R-001 | — | MISSING | COMPLETE_NOT_EXECUTED (2 TCs / 2 data pairs) |
| SEGDL1-R-002 | REQ-SRC-ATL105-PDF-001:0804 | PARTIAL | COMPLETE_NOT_EXECUTED (3 TCs / 3 data pairs) |
| SEGDL1-R-003 | — | MISSING | COMPLETE_NOT_EXECUTED (2 TCs / 2 data pairs) |
| SEGDL1-R-004 | — | MISSING | COMPLETE_NOT_EXECUTED (2 TCs / 2 data pairs) |
| SEGDL1-R-005 | — | MISSING | COMPLETE_NOT_EXECUTED (2 TCs / 2 data pairs) |
| SEGDL1-R-006 | — | MISSING | COMPLETE_NOT_EXECUTED (2 TCs / 2 data pairs) |
| SEGDL1-R-007 | — | MISSING | COMPLETE_NOT_EXECUTED (2 TCs / 2 data pairs) |
| SEGDL1-R-008 | — | MISSING | COMPLETE_NOT_EXECUTED (2 TCs / 2 data pairs) |
| SEGDL1-R-009 | — | MISSING | COMPLETE_NOT_EXECUTED (2 TCs / 2 data pairs) |
| SEGDL1-R-010 | — | MISSING | COMPLETE_NOT_EXECUTED (2 TCs / 2 data pairs) |
| SEGDL1-R-011 | — | MISSING | COMPLETE_NOT_EXECUTED (4 TCs / 4 data pairs) |
| SEGDL1-R-012 | — | MISSING | COMPLETE_NOT_EXECUTED (2 TCs / 2 data pairs) |

## Validation of supplied AI test data

| Rule | Status | Observation |
|---|---|---|
| SEGDL1-R-001 | NOT_ASSERTABLE | The AI artifact is a structured object, not serialized DL1 bytes; a character-length assertion cannot be made. |
| SEGDL1-R-002 | FAIL | # marker is present; End-of-Data '~' is absent |
| SEGDL1-R-003 | REVIEW_REQUIRED | Logical required-field presence is checked; exact widths/padding remain unresolved under P-02. |
| SEGDL1-R-004 | FAIL | Declared Card Type count is 01; repeated Card Type values are absent |
| SEGDL1-R-005 | FAIL | Card Type 173 present=false; DL6 present=true |
| SEGDL1-R-006 | NOT_ASSERTABLE | The structured AI data has no serialized DL1 bytes, so field-separator placement cannot be verified. |
| SEGDL1-R-007 | FAIL | AI data response type is 'Software Load Phone Response'; DL1 is specified for Table Load Response only. |
| SEGDL1-R-008 | NOT_ASSERTABLE | The AI test data does not include a merchant-profile load-flag context. |
| SEGDL1-R-009 | NOT_ASSERTABLE | No repeated Card Type value is present to validate; valid-set confirmation is pending P-04. |
| SEGDL1-R-010 | FAIL | Address Line 2 value '1' does not match the specified positional city/state/ZIP layout. |
| SEGDL1-R-011 | FAIL | Store Number format/range valid=true; Merchant Phone format valid=false |
| SEGDL1-R-012 | REVIEW_REQUIRED | The structured response contains DL1/DL2/DL3/DL6 blocks, but serialized End-of-Load framing is absent and the DL6 end-marker count remains open under P-03. |

Validation totals: **0 PASS**, **6 FAIL**, **2 REVIEW_REQUIRED**, **4 NOT_ASSERTABLE**. The AI sample is not valid for execution against the DL1 oracle.
