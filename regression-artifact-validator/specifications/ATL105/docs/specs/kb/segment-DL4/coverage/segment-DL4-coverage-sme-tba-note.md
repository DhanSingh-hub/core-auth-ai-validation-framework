# Segment DL4 Coverage Closure: SME and TBA Learning Note

## Boundary

### Included

- Applicability to BUYPASS-managed devices (`R-001`)
- DL4 identity, framing, length and serialization (`R-002`, `R-004`)
- Schedule fields and element formats (`R-003`, `R-005`)
- Software Load Response placement with DL5 and flag `SOFT` (`R-006`)
- Software update processing lifecycle and attempt limit (`R-007`, `R-008`)

### Excluded or separately governed

- The application image transfer from the device management system
- DL5-specific IP/URL rules (DL5 catalog)

## Coverage Denominator

| Item | Required |
|---|---:|
| Rules | 8 |
| BRs | 8 |
| Test scenarios | ≥ 8 |
| Test cases (positive + negative) | ≥ 15 |
| Test cases with request/response data | ≥ 15 |

## Current Status

| Rule | Status | Reason |
|---|---|---|
| R-001, R-002, R-003, R-008 | MISSING | No TS/TC/TD yet (`SEGDL4-SME-001`) |
| R-006, R-007 | REVIEW_REQUIRED | Placement conflict (`SEGDL4-SME-002`) |
| R-004, R-005 | REVIEW_REQUIRED | Phone Number padding (`SEGDL4-SME-003`) |

## Field-Alias Crosswalk Status

No DL4 AI test-data payload exists; no crosswalk is built. See the [comparison](../segment-DL4-ai-vs-test-requirement-comparison.md).
