# Segment DL1 Coverage Closure: SME and TBA Learning Note

## Boundary

### Included

- DL1 identity, framing and length (`R-001`, `R-002`, `R-006`)
- Merchant identity fields and their element formats (`R-003`, `R-010`, `R-011`)
- Card Type count and valid codes (`R-004`, `R-009`)
- Table Load Response placement, load-flag precondition and block order (`R-007`, `R-008`, `R-012`)
- Card Type `173` → DL6 cross-segment rule (`R-005`)

### Excluded or separately governed

- DL2, DL3 and DL6 field rules (their own catalogs)
- Device behaviour after the load (card acceptance, feature switching)
- Merchant-profile administration at BUYPASS

## Coverage Denominator

| Item | Required |
|---|---:|
| Rules | 12 |
| BRs | 12 |
| Test scenarios | ≥ 12 |
| Test cases (positive + negative) | ≥ 24 |
| Test cases with request/response data | ≥ 24 |

## Current Status

| Rule | Status | Reason |
|---|---|---|
| R-001, R-002, R-004, R-007, R-008, R-010, R-011 | CANDIDATE_COMPLETE_NOT_EXECUTED | BR/TS/TC/TD chains exist, but fixtures are synthetic and unapproved (`SEGDL1-SME-001`) |
| R-003, R-006 | REVIEW_REQUIRED | Padding and parse boundaries (`SEGDL1-SME-002`) |
| R-005, R-012 | REVIEW_REQUIRED | DL6 and End-of-Load framing (`SEGDL1-SME-003`) |
| R-009 | REVIEW_REQUIRED | Complete Card Type set (`SEGDL1-SME-004`) |

## Semantic Evidence Checks

1. Does the Card Type `173` test contain DL6 in the same response object?
2. Does the Number of Card Types test actually change the count, not only the value?
3. Does the load-flag test state the flag as a precondition?
4. Is the payload serialized without Field Separators?

## Field-Alias Crosswalk Status

The DL1 crosswalk is generated from observed phase-one AI test-data field names in `Merchant Data Segment` payloads. It is a comparison aid only; it does not define the denominator, validate the Test Solution, approve fixtures, execute cases, or certify coverage. The supplied AI catalog contains DL1 element- and field-level requirements; see the [comparison](../segment-DL1-ai-vs-test-requirement-comparison.md).
