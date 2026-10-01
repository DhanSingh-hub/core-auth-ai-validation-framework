# Segment DL3 Coverage Closure: SME and TBA Learning Note

## Boundary

### Included

- DL3 identity, framing, length and serialization (`R-001`, `R-004`)
- Day, date, time, cut time and password formats (`R-002`, `R-003`, `R-005`, `R-006`)
- Message placement and Date and Time Load lifecycle (`R-007`, `R-008`)
- Automatic cut-time behaviour (`R-009`, review severity)

### Excluded or separately governed

- Totals Request content (Section 11.4, Segments 105/119)
- Device clock hardware and DST calendars
- DL1 Card Type `164` rule (DL1 catalog)

## Coverage Denominator

| Item | Required |
|---|---:|
| Rules | 9 |
| BRs | 9 |
| Test scenarios | ≥ 9 |
| Test cases (positive + negative) | ≥ 16 |
| Test cases with request/response data | ≥ 16 |

## Current Status

| Rule | Status | Reason |
|---|---|---|
| R-002, R-004, R-008, R-009 | MISSING | No TS/TC/TD yet (`SEGDL3-SME-001`) |
| R-003, R-006 | REVIEW_REQUIRED | Password source (`SEGDL3-SME-002`) |
| R-001, R-007 | REVIEW_REQUIRED | `~` in Date and Time Load Response (`SEGDL3-SME-003`) |
| R-005 | REVIEW_REQUIRED | HHMM ranges (`SEGDL3-SME-004`) |

## Field-Alias Crosswalk Status

No DL3 AI test-data payload exists; no crosswalk is built. See the [comparison](../segment-DL3-ai-vs-test-requirement-comparison.md).
