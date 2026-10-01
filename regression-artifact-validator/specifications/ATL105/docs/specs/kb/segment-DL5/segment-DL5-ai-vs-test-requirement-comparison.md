# Segment DL5: AI-Generated vs Test-Generated Requirement Comparison

**AI evidence:** [supplied-ai-catalog-coverage.md](../../../../test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.md) (AI BRs tagged `DL5`). No dedicated AI BR → TS → TC → TD package exists for DL5 (`SEGDL5-SME-001`).

**Rule:** candidate mappings are review aids. Every row stays `REVIEW_REQUIRED` until an SME decision is recorded.

## AI requirements tagged DL5

| AI BR | AI source rule | AI requirement (abridged) | Candidate Test Solution rule | Disposition |
|---|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:118 | ENT-ELEM-114 | Software Load IP/URL Address (length) | SEGDL5-R-005 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:119 | ENT-ELEM-114 | Software Load IP/URL Address (valid values) | SEGDL5-R-005 | REVIEW_REQUIRED — the source value list is itself disputed (`SEGDL5-SME-003`) |
| REQ-SRC-ATL105-PDF-001:3309 to :3316 | ENT-FIELD-DL5-1 to -8 | Fields 1-8 required and present | SEGDL5-R-002, R-003 | REVIEW_REQUIRED |

The AI catalog tags the shared Elements 57, 92, 93, 94 and 95 to DL4 only; for DL5 they appear only as field-presence requirements.

## Test Solution rules with no AI requirement

| Test Solution rule | Gap in AI output |
|---|---|
| SEGDL5-R-001 | No BUYPASS-managed vs vendor-managed applicability rule |
| SEGDL5-R-002 | No maximum length or fixed `$`/`~` values (and no awareness of the 64 vs 66 conflict) |
| SEGDL5-R-004 | No "no Field Separator" rule |
| SEGDL5-R-006 | No Software Load Response placement after DL4 |
| SEGDL5-R-007 | No scheduled-load lifecycle |
