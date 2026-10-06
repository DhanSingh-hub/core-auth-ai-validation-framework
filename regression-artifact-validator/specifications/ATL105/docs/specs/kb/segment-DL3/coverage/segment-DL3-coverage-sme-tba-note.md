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
| R-002, R-004, R-008 | CANDIDATE_DEFINED | Independent TS/TC/symbolic TD candidates exist; not SME-approved or executed. |
| R-009 | REVIEW_REQUIRED | Candidate lifecycle cases exist; device-level timing evidence is not executed and rule has review severity. |
| R-003, R-006 | REVIEW_REQUIRED | Password source (`SEGDL3-SME-002`) |
| R-001, R-007 | REVIEW_REQUIRED | `~` in Date and Time Load Response (`SEGDL3-SME-003`) |
| R-005 | REVIEW_REQUIRED | HHMM ranges (`SEGDL3-SME-004`) |

## Field-Alias Crosswalk Status

The phase-one AI delivery contains one representative DL3 payload, not a dedicated AI test package. The [coverage report](segment-DL3-ai-artifact-coverage-report.md) validates this sample separately from the supplied AI catalog; the [field-alias crosswalk](../../../../../contract/segment-DL3-field-alias-crosswalk.json) records observed AI field names and occurrence counts without changing the oracle. See the [AI-vs-Test comparison](../segment-DL3-ai-vs-test-requirement-comparison.md).
