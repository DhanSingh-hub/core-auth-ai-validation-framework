# Test Team Action 4: Reproducible Test Data and Setup

**Status:** Test-data design guidance; no executable data approved  
**Source baseline inspected:** ATL105 2026-3  
**Input:** BA workbook `BR & TS Scope updates.xlsx` (SHA-256 recorded in [the workbook review](./REPORT.md))  
**Related reviews:** [Action 1 scenario/source review](./TEST-TEAM-ACTION-1-SCENARIO-SOURCE-REVIEW.md), [Action 2 traceability/applicability review](./TEST-TEAM-ACTION-2-TRACEABILITY-APPLICABILITY.md), [Action 3 expected-outcome review](./TEST-TEAM-ACTION-3-EXPECTED-OUTCOMES.md)  
**Boundary:** No workbook or BRs were changed. This document proposes what TDs/setup must capture; it does not create live credentials, processor configurations, host scripts, or approved response oracles.

## 1. Findings

The workbook does not have a dedicated Test Data (TD) sheet or stable TD
references. Its setup detail varies by tab:

- BR1 has `Preconditions`, `Parent Transaction`, and `Expected Result`, but each
  of these columns is blank on 70 of 149 rows.
- BR2–BR4 have a `Dependency` column but no structured preconditions,
  parameterized input, or expected-result fields.
- BR5 has preconditions, dependency, network, card-program, and expected-result
  fields, but the dependencies are mostly prose labels such as “Credential
  Storage” or “Authorization”; they do not identify a specific prior test-data
  record or transaction instance.
- No workbook tab defines synthetic account/card fixtures, issuer/host
  configuration, response injection, timeout timing, transaction state,
  sequence/reference values, cleanup, or run isolation.

The repository has prior design examples for lifecycle and structural TDs.
However, the sequence-number chain explicitly marks all 12 data designs
`REVIEW_REQUIRED`, including its multi-step authorization/reversal fixtures as
design-only and not executable. Treat those examples as shape guidance, not
validated fixtures or a shortcut to approval.

## 2. Minimum TD record contract

Each proposed case should link to an immutable or versioned TD record containing
the following, as applicable:

| TD field | Purpose |
|---|---|
| TD ID and version | Stable identity; not a reused TC number or free-text dependency. |
| Linked canonical BR, approved TS, TC | Preserves the trace chain; unresolved workbook BR1–BR13 labels remain candidate references. |
| Specification and test-environment profile | Pins ATL105 release, simulator/host profile, network/program profile, and configuration version. |
| Fixture kind and sensitivity class | Identifies logical request, multi-step lifecycle, injected response, network simulator setup, or stateful ledger fixture; marks synthetic/masked test data. |
| Preconditions / seeded state | Explicit card/account capability, merchant/POS setting, prior transaction state, balances/limits if applicable, batch/settlement state, and feature flags. |
| Request input | Required transaction type, amount/currency, entry mode, relevant Segment 100/companion fields, and source-relevant identifiers. Store only fabricated test values or approved masked data. |
| Interaction controls | Simulator response profile, timeout point, communication fault, duplicate delivery/retry controls, and any deterministic clock/random seed needed to reproduce the case. |
| Expected oracle | Link to source-derived behavior or approved host/network configuration; separate Element 83, Element 26, Element 8, device/application behavior, and ledger side effects. |
| Cleanup / isolation | How seeded transaction, card, merchant, and batch state is removed or namespaced so tests do not contaminate one another. |
| Readiness and provenance | `DESIGN_ONLY`, `REVIEW_REQUIRED`, or approved execution-ready state; owner, source anchor, approval reference, and fixture hash/version. |

Do not put real PAN, PIN, CVV, track data, cryptograms, or other sensitive
production credentials in the TD catalog. Use an approved processor test-card
set or synthetic/masked identifiers that the controlled test environment
explicitly recognizes.

## 3. Reproducible setup patterns by case family

The values below define the *conditions to control*, not specific network
response codes or authorization outcomes. Use clearly synthetic identifiers
(for example `SEQ-CASE-<run-id>` in the TD design) and replace them with
format-valid fixture values only in the approved test harness. Do not copy
production data or reuse a sequence/reference value across independent runs
where it could collide with existing transactions.

| Workbook case family | Setup/data needed for repeatability | Required oracle or decision gate |
|---|---|---|
| Named purchase/authorization decline | Controlled synthetic account/card fixture; product/network and transaction type; merchant/POS capabilities; an explicitly configured simulator/host response profile; request amount/currency and entry mode. | Approved mapping from scenario to Element 83, Element 26, and/or Element 8 and application handling. Do not derive a decline code from “Lost Card” or similar title. |
| Invalid PAN/CVV/expiry/track/EMV input | One invalid dimension per case; hold all other input constant and valid; identify whether rejection occurs in local validation, device/processor, or BUYPASS. Use only environment-approved test vectors, never real authentication data. | Expected rejection layer and observable result. A malformed request is not automatically an issuer decline. |
| Partial approval / split tender | Seed a simulator result with requested and approved amounts, a supported partial-approval capability/context, currency, and remainder interaction; record each leg and final disposition. | Applicable product/indicator rules, partial response and amount, remainder payment/cancel behavior, and any follow-up/reversal. Verify source/processor context before expecting a specific indicator. |
| Completion/cancellation/reversal/void | Store the original request and response as a linked transaction fixture: original transaction class, Prompt Code, Segment 100 Sequence Number, Approval Number when applicable, amounts, response state, and settlement state. Follow-up references the original TD ID, not only a prose dependency. | Source-defined relationship for that flow. Do not apply authorization-only cancellation constraints to purchase reversal, refund void, or other lifecycle messages. |
| Timeout, late response, retry, recovery | Deterministically choose the timeout boundary (before request sent, after send/before response, or after late response); record network state, retry count, elapsed time/clock control, original transaction ID, and any recovery message. | Approved distinction between purchase TOR and authorization-only cancellation; code/retry oracle, duplicate handling, late-response policy, and terminal state. Appendix H timeout decline codes apply only to their authoritative contexts. |
| Duplicate purchase/completion/return/inquiry/reversal | Seed exactly one original transaction; specify the duplicate identity rule being tested (same request ID, Sequence Number, STAN/reference, or other configured key), changed/unchanged fields, host duplicate-detection window, and prior response/settlement state. Vary one factor at a time. | Processor/network duplicate policy, accepted/rejected result, and whether a second financial side effect is prohibited. Equal amount alone is not proof of duplicate identity. |
| Return/refund amount boundaries | Seed an eligible original purchase and record original/approved amount, prior partial returns, currency, settlement status, and requested return amount. Include separate valid boundary, over-limit, zero, and cumulative-return cases only where source/processor rules define them. | Approved return amount rule, full/partial return behavior, original reference, and ledger/settlement effect. Do not invent a universal return cap. |
| Settlement/batch cases | Seed a named batch with explicit included/excluded transactions, reversals/voids, totals, close/open status, and deterministic prior state. | Expected record inclusion and totals from an approved settlement oracle; clarify behavior after settlement/close. |
| Stored credential / recurring / MIT retry | Synthetic merchant/credential profile and consent/standing-instruction state; distinguish initial storage from subsequent CIT/MIT/recurring/installment/UCOF; record network, program, transaction indicator, and prior CIT/credential relationship. | Network-specific stored-credential and retry policy; distinguish soft/hard labels from actual Element 26 `K=`/`K#` applicability. Do not resubmit a real payment. |
| EMV/fallback/contactless | Approved EMV test-card and terminal profile, transaction state, entry mode, required Segment 130 data generation source, and separate fallback conditions. Cryptograms must come from an approved simulator/test tool. | Applicability of Segment 130, entry-mode conditions, cryptogram oracle, and whether a reversal/fallback is allowed. No hand-crafted or copied production cryptogram. |

## 4. Stateful test sequencing requirements

For every multi-message case:

1. Assign an isolated run/correlation ID and create or select a controlled
   synthetic fixture.
2. Record the original request and relevant response, including transaction
   class, Sequence Number, and any approval/reference information required by
   that flow.
3. Make the state transition explicit: approved, declined, timed out with
   outcome unknown, partially approved, reversed, returned, voided, or settled.
4. Bind the follow-up case to the original TD ID and state what fields are
   intentionally reused, changed, or omitted, citing the applicable source.
5. Capture both message-level validation and business side effects; do not
   treat a transport/harness `PASS` as proof of the financial outcome.
6. Clean up or namespace the fixture and record retry/replay policy so a rerun
   does not accidentally become a duplicate-production-like transaction.

ATL105 Element 86 is a six-digit sequence identifier for the transaction
lifecycle; the source-backed draft notes reuse for related follow-ups and new
sequence allocation for separately forwarded transactions. A sequence value
alone is not a full lifecycle fixture: the original request class and
transaction state must be recorded. Approval Number requirements and amount
relationships are flow-specific; do not make all lifecycle TDs assert the
same fields.

## 5. Deterministic negative and exception design

Negative cases should change one controlled condition at a time. Examples:

- For invalid format tests, keep a valid baseline and mutate just one field
  (missing, short, long, malformed, or out of-range) while preserving all
  unrelated required fields.
- For a sequence/reference mismatch, create a valid original/follow-up pair,
  then change only the field under test. Mark the expected response oracle
  `REVIEW_REQUIRED` if ATL105 does not prescribe it.
- For timeout/late-response tests, inject the fault at a named point and control
  the clock; record whether the host accepted the original before simulating a
  retry or TOR.
- For duplicate tests, define the exact duplicate-key combination and window;
  compare the duplicate's business effect with the single-original baseline.
- For partial approval, seed requested and approved amounts explicitly and
  record the remaining amount; do not infer split-tender completion merely from
  the partial approval response.
- For declines, configure the simulator result by named oracle profile rather
  than mapping a human-readable reason to an undocumented code.

This makes the input reproducible without claiming an unsupported response
code, decline reason, timeout threshold, retry limit, duplicate key, or amount
rule.

## 6. Workbook-specific TD readiness gaps

- **BR1:** precondition and parent-transaction details are blank in 70 rows
  each; 70 expected-result cells are blank. Each of these fields should be
  reviewed independently rather than assuming the same rows are blank.
- **BR2–BR4:** the `Dependency` values (for example `Authorization`,
  `Original Purchase`, or `Return`) are not stable fixture references and do
  not define seeded field values or pre-transaction state.
- **BR5:** network/program and precondition text exists, but it does not define
  a credential fixture, token lifecycle state, consent record, host response
  profile, or exact linked prior transaction. Values such as
  “Declined or updater invoked” are alternative outcomes, not deterministic
  test expectations.
- **All detailed tabs:** there is no shared TD ID, fixture schema, source
  release, sensitivity classification, cleanup plan, or named host/simulator
  oracle.
- **BR6–BR13:** there are no detailed cases or setup/data designs in the
  workbook, so their TD readiness cannot be assessed from it.

## 7. Recommended TD authoring and approval sequence

1. Resolve the candidate canonical BR/TS and product/network applicability
   first; keep unresolved references visibly `REVIEW_REQUIRED`.
2. For each case, write the precondition and state-transition contract before
   choosing data values.
3. Select synthetic or processor-approved test fixtures and use a named
   simulator/oracle profile for host-dependent outcomes.
4. Separate request fixtures from expected response and state assertions; cite
   the source or approved external mapping for every expected value.
5. Add unique TD IDs, lifecycle linkage, environment/version, setup, cleanup,
   sensitivity handling, and fixture hashes or version IDs.
6. Mark fixtures executable only after the controlled environment, oracle,
   data security, and rerun/isolation behavior have been validated by the Test
   owner.

## 8. Source and prior-art limits

- [Sequence-number chain draft v2](../sequence-number-chain-draft-v1/sequence-number-test-chain-package-v2.json)
  supplies source-pinned, design-only TD examples. Its manifest explicitly says
  they are not converter-ready, not execution-certified, and have zero coverage
  credit.
- [Sequence-number lifecycle learning note](../../../docs/specs/kb/segment-100/sequence-lifecycle-sme-tba-note.md)
  describes original-transaction identity, Sequence Number reuse, and
  flow-specific Approval Number/amount conditions; several broader behaviors
  remain open.
- [Partial-approval flow](../../../docs/specs/kb/segment-100/partial-approval-flow.md)
  separates capability indication from the approval result and identifies a
  specific Amex prepaid balance-receipt context.
- [Element 83 response-code training](../../../docs/specs/kb/element-83-response-code/README.md)
  and [Action 3 expected-outcome review](./TEST-TEAM-ACTION-3-EXPECTED-OUTCOMES.md)
  document that response values and declines must use a family/context-aware
  oracle.

## Action 4 disposition

This action is **complete as a test-data/setup review and design proposal**.
The workbook does not contain enough information to create approved executable
TDs. The next prerequisite is authoritative BR/TS/release/applicability and
host/simulator-oracle confirmation. Until then, keep data designs synthetic,
non-executable, and explicitly review-required; no approved denominator or
coverage percentage follows from this work.
