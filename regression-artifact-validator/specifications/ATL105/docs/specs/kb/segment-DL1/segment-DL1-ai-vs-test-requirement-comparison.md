# Segment DL1: AI-Generated vs Test-Generated Requirement Comparison

**AI evidence:** [supplied-ai-catalog-coverage.md](../../../../test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.md) (AI BRs tagged `DL1`). No dedicated AI BR → TS → TC → TD package for DL1 exists yet (`SEGDL1-SME-001`), so this is a requirement-level comparison only.

**Rule:** the candidate Test Solution rule below is a review aid. Nothing here is `CONFIRMED`; every row stays `REVIEW_REQUIRED` until an SME decision is recorded in the SME decision register.

## AI requirements tagged DL1

| AI BR | AI source rule | AI requirement (abridged) | Candidate Test Solution rule | Disposition |
|---|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:003 | ENT-ELEM-3 | Address Line 1 AN, ≤ 24 bytes | SEGDL1-R-003 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:004 | ENT-ELEM-4 | Address Line 2 AN, ≤ 21 bytes | SEGDL1-R-003, R-010 | REVIEW_REQUIRED — AI misses the positional City/State/ZIP layout |
| REQ-SRC-ATL105-PDF-001:023 | ENT-ELEM-24 | Data Type Indicator AN, ≤ 1 byte | SEGDL1-R-002 | REVIEW_REQUIRED — AI misses fixed value `#` |
| REQ-SRC-ATL105-PDF-001:024 | ENT-ELEM-24 | Data Type Indicator "one of: DL3)., (No. DL4)., ..." | — | REVIEW_REQUIRED — malformed enumeration extracted from Element 24 prose; not a DL1 rule |
| REQ-SRC-ATL105-PDF-001:056 | ENT-ELEM-53 | Merchant Name AN, ≤ 24 bytes | SEGDL1-R-003 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:057 | ENT-ELEM-54 | Merchant Phone Number | SEGDL1-R-011 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:062 | ENT-ELEM-59 | Number of Card Types N, ≤ 2 bytes | SEGDL1-R-004 | REVIEW_REQUIRED — AI misses the 01-99 range and the count-equals-occurrences rule |
| REQ-SRC-ATL105-PDF-001:100 | ENT-ELEM-98 | Store Number N, ≤ 16 bytes | SEGDL1-R-003, R-011 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3277 to :3283 | ENT-FIELD-DL1-1 to -7 | Fields 1-7 are required and must be present | SEGDL1-R-002, R-003, R-004 | REVIEW_REQUIRED |

## Test Solution rules with no AI requirement

| Test Solution rule | Gap in AI output |
|---|---|
| SEGDL1-R-001 | No maximum-length (399) requirement |
| SEGDL1-R-004 (field 8) | No requirement for field 8 (Card Type) or its 01-99 repetition |
| SEGDL1-R-002 (field 9) | No requirement for field 9 (End-of-Data `~`) |
| SEGDL1-R-005 | No Card Type `173` → DL6 dependency |
| SEGDL1-R-006 | No "no Field Separator / next field immediately follows" rule |
| SEGDL1-R-007, R-008, R-012 | No message placement, load-flag or block-order rule |
| SEGDL1-R-009 | No Appendix E Table Load Card Type validation |

## Next step

Record SME decisions for the rows above in the SME decision register and raise AI feedback for the missing field 8/9 and cross-segment requirements once a DL1 AI package is delivered.
