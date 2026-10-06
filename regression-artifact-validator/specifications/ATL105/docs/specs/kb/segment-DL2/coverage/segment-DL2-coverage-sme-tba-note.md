# Segment DL2 Coverage Closure: SME and TBA Learning Note

## Boundary

### Included

- DL2 identity, framing, length and no-separator serialization (`R-001`, `R-004`)
- Dial String Type, primary and secondary blocks, terminators (`R-002`, `R-003`, `R-007`)
- Access Code / Pause Indicator pairing (`R-005`) and Redial Count range (`R-006`)
- Message placement and `PHON` load flag (`R-008`, `R-009`)

### Excluded or separately governed

- Modem dialing behaviour and Hayes comma substitution
- Error-recovery timing in the Asynchronous Communications Protocol Specifications (`SEGDL2-SME-001`)
- DL1/DL3 rules in the same Table Load Response

## Coverage Denominator

| Item | Required |
|---|---:|
| Rules | 9 |
| BRs | 9 |
| Test scenarios | ≥ 9 |
| Test cases (positive + negative) | ≥ 18 |
| Test cases with request/response data | ≥ 18 |

## Current Status

| Rule | Status | Reason |
|---|---|---|
| R-001, R-002, R-006, R-009 | MISSING | No TS/TC/TD yet (`SEGDL2-SME-002`) |
| R-003 | REVIEW_REQUIRED | External fallback document (`SEGDL2-SME-001`) |
| R-008 | REVIEW_REQUIRED | Phone Load framing (`SEGDL2-SME-003`) |
| R-004, R-005, R-007 | REVIEW_REQUIRED | Parse boundary (`SEGDL2-SME-004`) |

## Field-Alias Crosswalk Status

No DL2 AI test-data payload exists; no crosswalk is built. The supplied AI catalog has element- and field-level DL2 requirements for fields 1-7 only; see the [comparison](../segment-DL2-ai-vs-test-requirement-comparison.md).
