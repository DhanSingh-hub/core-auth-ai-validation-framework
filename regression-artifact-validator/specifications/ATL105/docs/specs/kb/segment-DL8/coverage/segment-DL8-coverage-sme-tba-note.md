# Segment DL8 Coverage Closure: SME and TBA Learning Note

## Boundary

### Included

- Special-gated inclusion and download-flag lifecycle (`R-001`)
- Hybrid framing and Segment Length (`R-002`, `R-005`)
- 1-24 group structure and element formats (`R-003`, `R-004`)

### Excluded or separately governed

- EMV kernel stand-in decisions at the terminal
- Segment 130 EMV request data
- Position in the Table Load Response (open — `SEGDL8-SME-002`)

## Coverage Denominator

| Item | Required |
|---|---:|
| Rules | 5 |
| BRs | 5 |
| Test scenarios | ≥ 5 |
| Test cases (positive + negative) | ≥ 10 |
| Test cases with test data | ≥ 10 |

## Current Status

| Rule | Status | Reason |
|---|---|---|
| R-003, R-005 | MISSING | No TS/TC/TD yet (`SEGDL8-SME-001`) |
| R-001, R-002 | REVIEW_REQUIRED | Position, Special, Segment Length source (`SEGDL8-SME-002`) |
| R-004 | REVIEW_REQUIRED | RID and Card Type content (`SEGDL8-SME-003`) |

## Field-Alias Crosswalk Status

No DL8 AI evidence of any kind exists; no crosswalk is built.
