# Segment DL4: AI-Generated vs Test-Generated Requirement Comparison

**AI evidence:** [supplied-ai-catalog-coverage.md](../../../../test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.md) (AI BRs tagged `DL4`). No dedicated AI BR → TS → TC → TD package exists for DL4 (`SEGDL4-SME-001`).

**Rule:** candidate mappings are review aids. Every row stays `REVIEW_REQUIRED` until an SME decision is recorded.

## AI requirements tagged DL4

| AI BR | AI source rule | AI requirement (abridged) | Candidate Test Solution rule | Disposition |
|---|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:060 | ENT-ELEM-57 | New Software Version AN, ≤ 8 bytes | SEGDL4-R-005 | REVIEW_REQUIRED — Element 57 is *fixed* length 8 |
| REQ-SRC-ATL105-PDF-001:092 | ENT-ELEM-91 | Software Load Phone Number | SEGDL4-R-005 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:093 | ENT-ELEM-92 | Software Load Request Date | SEGDL4-R-005 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:094 | ENT-ELEM-93 | Software Load Request Time | SEGDL4-R-005 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:095 | ENT-ELEM-94 | Software Load Type A, ≤ 1 byte | SEGDL4-R-005 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:096 | ENT-ELEM-94 | Software Load Type one of F, P | SEGDL4-R-005 | REVIEW_REQUIRED — closest AI match to a Test Solution enumeration |
| REQ-SRC-ATL105-PDF-001:097 | ENT-ELEM-95 | Software Terminal Record ID | SEGDL4-R-005 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:3301 to :3308 | ENT-FIELD-DL4-1 to -8 | Fields 1-8 required and present | SEGDL4-R-002, R-003 | REVIEW_REQUIRED |

Elements 57, 92-95 are shared with DL5; the AI catalog tags them to DL4 only.

## Test Solution rules with no AI requirement

| Test Solution rule | Gap in AI output |
|---|---|
| SEGDL4-R-001 | No BUYPASS-managed vs vendor-managed applicability rule |
| SEGDL4-R-002 | No maximum length (52) or fixed `@`/`~` values |
| SEGDL4-R-004 | No "no Field Separator" rule |
| SEGDL4-R-006 | No Software Load Response placement or DL4 + DL5 co-presence |
| SEGDL4-R-007, R-008 | No software update processing lifecycle |
