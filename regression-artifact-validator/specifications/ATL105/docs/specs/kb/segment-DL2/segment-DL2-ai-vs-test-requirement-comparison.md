# Segment DL2: AI-Generated vs Test-Generated Requirement Comparison

**AI evidence:** [supplied-ai-catalog-coverage.md](../../../../test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.md) (AI BRs tagged `DL2`). No dedicated AI BR → TS → TC → TD package exists for DL2 (`SEGDL2-SME-002`).

**Rule:** candidate mappings are review aids only. Every row stays `REVIEW_REQUIRED` until an SME decision is recorded in the SME decision register.

## AI requirements tagged DL2

| AI BR | AI source rule | AI requirement (abridged) | Candidate Test Solution rule | Disposition |
|---|---|---|---|---|
| REQ-SRC-ATL105-PDF-001:001 | ENT-ELEM-1 | Access Code AN, ≤ 12 bytes | SEGDL2-R-005 | REVIEW_REQUIRED — AI misses the "present only with Pause Indicator" rule and `B` pauses |
| REQ-SRC-ATL105-PDF-001:027 | ENT-ELEM-27 | Dial String Terminator AN, ≤ 1 byte | SEGDL2-R-007 | REVIEW_REQUIRED — AI misses `A`/`F` values |
| REQ-SRC-ATL105-PDF-001:028 | ENT-ELEM-28 | Dial String Type N, ≤ 1 byte | SEGDL2-R-002, R-007 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:069 | ENT-ELEM-66 | Pause Indicator AN, ≤ 1 byte | SEGDL2-R-005 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:073 | ENT-ELEM-75 | Phone Number N, ≤ 18 bytes | SEGDL2-R-007 | REVIEW_REQUIRED |
| REQ-SRC-ATL105-PDF-001:081 | ENT-ELEM-82 | Redial Count N, ≤ 1 byte | SEGDL2-R-006 | REVIEW_REQUIRED — AI misses the 1-3 range |
| REQ-SRC-ATL105-PDF-001:3284 to :3293 | ENT-FIELD-DL2-1 to -7 | Fields 1-7 presence; Dial String Type = 1; Pause = B; terminator = A; Access Code/Pause conditional | SEGDL2-R-001, R-002, R-005 | REVIEW_REQUIRED (`:3286` Dial String Type = 1 was scored `MATCHED_SEMANTICS_ONLY`; still not confirmed) |

## Test Solution rules with no AI requirement

| Test Solution rule | Gap in AI output |
|---|---|
| SEGDL2-R-003 | No requirement for the secondary block (fields 8-12), terminator `F`, or fallback order |
| SEGDL2-R-001 | No maximum length (69) or End-of-Data `~` requirement |
| SEGDL2-R-004 | No "no Field Separator" rule |
| SEGDL2-R-008, R-009 | No message placement or load-flag rule |

The AI "conditional" requirements for fields 4 and 5 say "present only when its documented condition holds" without stating the condition; they cannot be tested as written.
