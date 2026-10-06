# Segment DL3: AI-Generated vs Test-Generated Requirement Comparison

**AI evidence:** [supplied-ai-catalog-coverage.md](../../../../test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.md) (AI BRs tagged `DL3`). No dedicated AI BR → TS → TC → TD package exists for DL3 (`SEGDL3-SME-001`).

**Rule:** candidate mappings are review aids. Every row stays `REVIEW_REQUIRED` until an SME decision is recorded.

## AI requirements tagged DL3

| AI BR | AI source rule | AI requirement (abridged) | Candidate Test Solution rule | Disposition |
|---|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:020 | ENT-ELEM-21 | Current Date N, ≤ 6 bytes | SEGDL3-R-005 | REVIEW_REQUIRED — AI misses MMDDYY |
| REQ-SRC-ATL105-PDF-001:021 | ENT-ELEM-22 | Current Time N, ≤ 4 bytes | SEGDL3-R-005 | REVIEW_REQUIRED — AI misses HHMM and time-zone adjustment |
| REQ-SRC-ATL105-PDF-001:022 | ENT-ELEM-23 | Cut Time N, ≤ 4 bytes | SEGDL3-R-005, R-009 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:025 | ENT-ELEM-25 | Day of the Week N, ≤ 1 byte | SEGDL3-R-005 | REVIEW_REQUIRED — AI misses 0-6 |
| REQ-SRC-ATL105-PDF-001:034 | ENT-ELEM-34 | End-of-Data Indicator A, ≤ 1 byte | SEGDL3-R-001 | REVIEW_REQUIRED — AI misses fixed `~` |
| REQ-SRC-ATL105-PDF-001:3294 to :3300 | ENT-FIELD-DL3-1 to -7 | Fields 1-7 required and present | SEGDL3-R-001, R-002, R-003 | REVIEW_REQUIRED (`:3299` Password was scored `MATCHED_ELEMENT_AND_SEMANTICS`, but the Password source is itself disputed — `SEGDL3-SME-002` — so the match cannot be confirmed) |

## Test Solution rules with no AI requirement

| Test Solution rule | Gap in AI output |
|---|---|
| SEGDL3-R-004 | No "no Field Separator" rule |
| SEGDL3-R-006 | No Password format (right-aligned, space-filled) |
| SEGDL3-R-007, R-008 | No message placement or Load Type `D` rule |
| SEGDL3-R-009 | No automatic cut-time behaviour |
