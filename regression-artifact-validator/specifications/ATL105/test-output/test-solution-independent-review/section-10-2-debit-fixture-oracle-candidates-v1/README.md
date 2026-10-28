# ATL105 Section 10.2 Debit Fixture and Oracle Candidates

Status: DRAFT_REVIEW_REQUIRED. These are independently source-quoted candidates; they do not promote the v2 BR chains or baseline.

## Fixture scope

`candidate-test-solution-package.json` contains one canonical Segment 100 request fragment for Partial Approval Indicator 1. Existing Segment 100 core-field and indicator validators can assess that fragment. It is deliberately marked REVIEW_REQUIRED and `executionAllowed: false`; it is not a full request, has no debit BIN/network evidence, PIN block, complete Segment 1 fields, complete Segment 100 field set, or captured wire.

`source-backed-outcome-oracle-candidates.json` contains 11 source-quoted state vectors across 10 of the 29 Section 10.2 draft rules. The other 19 rules are listed with explicit missing-evidence blockers. Candidate vectors confer no coverage credit. Quotes are bound to the extracted ATL105 source SHA-256 and exact line ranges.

These state vectors are not converter-ready wire fixtures, actual host observations, or device executions. Numeric values are illustrative state-vector units only where explicitly labeled.

## Technical evidence

The build checks the package schema, physical-fixture/package equality, exact source quote ranges and links to the v2 draft chains. The focused Java test checks only Segment 100 core-field shape, Partial Approval Indicator 1, traceability, and that execution validation remains blocked.

Java validator result: PASS (2 tests, 0 failures, 0 errors).

## Blocked

- No debit-capable POS/device implementation is present in this repository.
- No approved debit BIN, issuer/network routing profile, or host simulator is supplied.
- No captured authorized host response is supplied; response vectors are injected candidates only.
- No DUKPT test keys, KSN policy, or DES-compliant PIN-entry device evidence is supplied.
- No full converter-ready debit request/response wire capture is supplied.
- The code-only source oracle cannot establish debit-network support, issuer response authority, or host state.
- Formal SME/TBA interpretation and execution certification remain blocked; technical evidence never sets `executionCertified` true.

All candidate BR/TS/TC/TD records remain REVIEW_REQUIRED. No baseline rows, coverage credit, or approvals are changed.
