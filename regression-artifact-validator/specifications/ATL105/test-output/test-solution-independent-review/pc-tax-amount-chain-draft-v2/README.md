# PC Tax Amount: Automated Artifact Review v2

**Status:** Source-backed Element 74 format validation and value-redacted review of all three linked candidates are complete. Semantic review and Segment 104 applicability remain open; no coverage is credited.

Element 74 has numeric character type, a maximum of seven digits, and two assumed decimal places. The source lists no valid amount range. The shared `value-domains.json` now uses only the format predicate `[0-9]{1,7}`; it does not claim that zero or any other formatted value is a semantically valid transaction tax amount, and it does not decide when Segment 104 or Element 74 must be present.

The audit runner checks the source anchor, payload and sidecar parseability, case identity, exact Element 74 field observations, metadata agreement, and SHA-256 bindings. It omits raw payment values and does not use producer responses as an oracle. All three linked candidate payloads and sidecars parse and bind to their case IDs, but none contains an exact Element 74 field; none of the sidecars has an exact Element 74 row or explicitly declares the element unavailable. The sidecars also lack `sourceBatchSha256`. This corrects v1's claim of declared omission: the evidence establishes no exact field observation, not a producer-declared omission. Candidate files do not establish approved artifact status.

Run from `regression-artifact-validator`:

```powershell
python scripts/review-segment-104-pc-tax-amount.py --self-test
python scripts/review-segment-104-pc-tax-amount.py
mvn.cmd '-Dmaven.compiler.useIncrementalCompilation=false' '-Dtest=Chapter13DataElementValidatorTest#pcTaxAmountChecksOnlyNumericTypeAndSevenDigitMaximum' test
```

The complete Chapter 13 domain-boundary suite passed 469/469. The generated report and manifest bind the source text, domain catalog, runner, three payloads, and three sidecars. Test-owner decisions on Segment 104 applicability and Element 74 presence, an independently sourced populated amount, and Test readiness/execution remain required. Semantic approvals: 0. Coverage credit: 0. Execution certification: false. The official Test baseline and canonical Test package are unchanged.