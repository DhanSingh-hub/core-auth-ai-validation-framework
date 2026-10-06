# Segment DL5 Coverage Closure: SME and TBA Learning Note

## Boundary

### Included

- Applicability to BUYPASS-managed devices (`R-001`)
- DL5 identity, framing, length and serialization (`R-002`, `R-004`)
- Field order relative to DL4 and element formats (`R-003`, `R-005`)
- Software Load Response placement after DL4 (`R-006`)
- Scheduled IP load attempts (`R-007`, review severity)

### Excluded or separately governed

- DL4 rules and the shared placement conflict (`SEGDL4-SME-002`)
- IP transport and the application image

## Coverage Denominator

| Item | Required |
|---|---:|
| Rules | 7 |
| BRs | 7 |
| Test scenarios | ≥ 7 |
| Test cases (positive + negative) | ≥ 13 |
| Test cases with request/response data | ≥ 13 |

## Current Status

| Rule | Status | Reason |
|---|---|---|
| R-001, R-003, R-004, R-007 | MISSING | No TS/TC/TD yet (`SEGDL5-SME-001`) |
| R-002 | REVIEW_REQUIRED | 64 vs 66 (`SEGDL5-SME-002`) |
| R-005 | REVIEW_REQUIRED | Element 114 content (`SEGDL5-SME-003`) |
| R-006 | REVIEW_REQUIRED | Placement (`SEGDL4-SME-002`) |

## Field-Alias Crosswalk Status

No DL5 AI test-data payload exists; no crosswalk is built. See the [comparison](../segment-DL5-ai-vs-test-requirement-comparison.md).
