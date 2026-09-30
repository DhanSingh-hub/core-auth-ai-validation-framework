# Segment DL7 Coverage Closure: SME and TBA Learning Note

## Boundary

### Included

- Hybrid framing, Segment Length counting and width (`R-001`, `R-002`, `R-006`)
- Download Data TLV structure and Appendix W tables (`R-003`, `R-004`, `R-005`)

### Excluded or separately governed

- Message placement and lifecycle (not defined — `SEGDL7-SME-004`)
- Device localisation behaviour

## Coverage Denominator

| Item | Required |
|---|---:|
| Rules | 6 |
| BRs | 6 |
| Test scenarios | ≥ 6 |
| Test cases (positive + negative) | ≥ 11 |
| Test cases with test data | ≥ 11 |

## Current Status

| Rule | Status | Reason |
|---|---|---|
| R-001, R-005 | REVIEW_REQUIRED | Placement / multi-segment (`SEGDL7-SME-004`) |
| R-002, R-006 | REVIEW_REQUIRED | Segment Length counting (`SEGDL7-SME-005`) |
| R-003 | REVIEW_REQUIRED | Appendix W scope (`SEGDL7-SME-001`) |
| R-004 | REVIEW_REQUIRED | `an1` vs Table Length (`SEGDL7-SME-003`) |

## Field-Alias Crosswalk Status

No DL7 AI evidence of any kind exists; no crosswalk is built.
