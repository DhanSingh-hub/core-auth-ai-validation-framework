# Segment DL6 Coverage Closure: SME and TBA Learning Note

## Boundary

### Included

- DL1 `173` ↔ DL6 presence (`R-001`)
- Framing, length and serialization (`R-002`, `R-004`)
- Element 166 times (`R-003`, `R-005`)
- Position in the Table Load Response (`R-006`)
- Daily blocking window (`R-007`)

### Excluded or separately governed

- Store-and-forward queue handling and later forwarding
- DL1 field rules (DL1 catalog)

## Coverage Denominator

| Item | Required |
|---|---:|
| Rules | 7 |
| BRs | 7 |
| Test scenarios | ≥ 7 |
| Test cases (positive + negative) | ≥ 12 |
| Test cases with Table Load Response data | ≥ 12 |

## Current Status

| Rule | Status | Reason |
|---|---|---|
| R-001, R-004, R-005 | MISSING | No TS/TC/TD yet (`SEGDL6-SME-002`) |
| R-002 | REVIEW_REQUIRED | Length and text defects (`SEGDL6-SME-003`, `-004`) |
| R-003 | REVIEW_REQUIRED | Element 166 reuse (`SEGDL6-SME-001`) |
| R-006 | REVIEW_REQUIRED | End-of-Load count (`SEGDL1-SME-003`) |
| R-007 | REVIEW_REQUIRED | Window semantics (`SEGDL6-SME-005`) |

## Field-Alias Crosswalk Status

No DL6 AI evidence of any kind exists; no crosswalk is built.
