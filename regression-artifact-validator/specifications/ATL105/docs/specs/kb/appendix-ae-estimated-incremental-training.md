# Appendix AE: Visa Estimated and Incremental Authorization

Source: ATL105 2026-3, Appendix AE-1, extracted PDF page 722. Training status: IN_PROGRESS / REVIEW_REQUIRED. No SME certification, wire validity, merchant eligibility approval or host-outcome certification is granted.

## Source Decisions

The existing four-BR coverage draft overstated MCC eligibility as an exclusive list and truncated the rollover requirement. Both are corrected. MCC 4121/5411 require card-not-present context; MCC 5552 requires EV context. Appendix AE also discusses hotel, cruise and rental merchants, so unlisted MCCs must not be automatically prohibited.

The source-derived atomic inventory is in `appendix-ae-rule-catalog.json`. Its 13 rules are separate training entries, not additions to the approved segment-rule denominator. The existing four-BR package remains review-required and is not counted as 13 approved BRs.

## Logical Flow Contract

`VisaEstimatedIncrementalAuthorizationValidator.validatePayload` consumes a logical observation with `specificationVersion: 2026-3`, `network: Visa`, textual four-digit `mcc`, `merchantPermissionEvidence`, `merchantCategory`, boolean `stayExceedsWindow`, textual `finalAmount`, and ordered `steps`.

Supported roles: `INITIAL`, `INCREMENTAL`, `COMPLETION`, `SECOND_INITIAL`. This is one initial authorization lifecycle, followed by at most one rollover initiation; it is not a complete multi-cycle engine. Every step uses an ISO date. Unknown/missing context creates review warnings.

Initial observations record whether the final amount is known and the estimated indicator. The example-grounded unknown-amount profile requires `estimatedAuthIndicator: 1`. Subsequent incremental observations omit that key entirely, supply `authorizationIdentificationResponse`, and preserve textual `sequenceNumber`, `transactionIdentifier`, and `terminalTransactionNumberTable63` from the original initial request. The validator does not guess the Transaction Identifier segment or assert merchant transmission of the host-created incremental indicator.

Completion compares its textual decimal `amount` with `finalAmount`. It does not infer incremental arithmetic, currencies, authorizer acceptance or chargebacks.

Expiry profiles: `CRUISE`, `LODGING`, `CAR_VEHICLE_RENTAL` use 14 days; `OTHER_RENTAL` uses seven. The original initial date is the anchor even after later incrementals. Unknown categories stay review-required. MCC-to-category mapping is not invented.

For a longer stay/rental, the first completion must precede the second initiation, and both must occur on or before the original deadline. Tests include exact day 7/14 and one-day-late mutations. These use LocalDate calendar-day semantics; actual expiry timestamps/timezones remain review-required.

## Evidence And Checks

Focused JUnit tests cover CNP/EV restrictions, initial indicator, missing approval reference, incremental without initial, all three continuity mutations, final amount mismatch, incremental-not-resetting-expiry, and rollover boundaries. Synthetic observations are not executable host messages. Tests verify that all catalog quotes occur in the actual Appendix AE source body.

Run from the Java module:

```powershell
mvn.cmd '-Dtest=VisaEstimatedIncrementalAuthorizationValidatorTest,AppendixCoverageConsistencyTest' test
```

## Remaining Gates

Confirm permitted-merchant scope, Transaction Identifier placement, Appendix I Table 63/Sub Table 09 wire integration, approval-response provenance, actual time cutoff, and independent request/response fixtures. Do not promote the existing AI estimated/incremental flow solely because metadata labels it that way. Each logical predicate can pass while the overall observation remains review-required.