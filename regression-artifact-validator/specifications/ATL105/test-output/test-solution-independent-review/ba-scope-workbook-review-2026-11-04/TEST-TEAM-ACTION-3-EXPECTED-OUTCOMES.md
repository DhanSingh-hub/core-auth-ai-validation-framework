# Test Team Action 3: Define Testable Expected Outcomes

**Status:** Source-bounded outcome contract; scenario-specific host/network mapping remains open  
**Source baseline inspected:** ATL105 2026-3  
**Input:** BA workbook `BR & TS Scope updates.xlsx` (SHA-256 recorded in [the workbook review](./REPORT.md))  
**Related reviews:** [Action 1 scenario/source review](./TEST-TEAM-ACTION-1-SCENARIO-SOURCE-REVIEW.md), [Action 2 traceability/applicability review](./TEST-TEAM-ACTION-2-TRACEABILITY-APPLICABILITY.md)  
**Boundary:** No workbook or BRs were changed. Do not treat these candidate assertions as an approved test oracle until the case-to-BR mapping, release, product context, and processor/host behavior are confirmed.

## 1. Finding

The workbook uses outcome phrases such as “Purchase declined,” “Transaction
declined,” “Storage request declined,” and “Retry rejected.” These indicate
intended business outcomes but do not specify a response that a test can
unambiguously assert. Many case titles name a decline reason (for example
“Insufficient Funds” or “Lost Card”), but the workbook does not establish:

- which response family and transaction context apply;
- which one-character ATL105 Response Code (Element 83) is expected;
- whether a two-character Decline Code (Element 26) is expected and what
  source-defined behavior that code triggers;
- whether a two-character Authorizer Response Code (Element 8) is part of the
  assertion and which host/network mapping supplies it; or
- whether a decline should stop, permit re-entry, be resubmitted, be retried,
  or be reversed.

The source specifies some protocol-level response meanings. It does **not**
provide a complete Element 83 × transaction type × card type table or a
universal mapping from scenario titles such as “Insufficient Funds” to
Elements 8, 26, or 83. Those values must not be invented.

## 2. Keep the response fields distinct

| Field | ATL105 role | What to assert | What not to infer |
|---|---|---|---|
| **Element 83 – Response Code** | One alphanumeric character in the response; meaning depends on response-message family and sometimes transaction/load context. | Assert the source-defined code meaning for the identified response family and supported transaction context. | Do not use the global code roster without family context or assume a complete transaction/card cross-product. |
| **Element 26 – Decline Code** | Fixed two-character alphanumeric BUYPASS-defined reason for decline. Appendix H lists selected intervention/re-entry, timeout, Visa recurring, and TransArmor-token cases. | Assert its exact two-character value and prescribed handling only when the scenario and authoritative source/configuration provide that value. | Do not treat Appendix H as the exhaustive list of every valid decline code; do not substitute it for Element 83. |
| **Element 8 – Authorizer Response Code** | A distinct two-byte authorizer response field, not Element 83 or Element 26. | Assert it only when the test oracle identifies the processor/authorizer mapping and exact expected value. | Do not equate values from logs, a harness column, or external network codes to this ATL105 field without a field mapping. |

This separation is material: the existing response-code evidence records unresolved
two-byte “Response Code” fields in some execution documents and excludes them
from the Element 83 oracle until their identity is established.

## 3. Source-defined Element 83 outcome contract

For standard financial responses, the repository’s ATL105 2026-3 source-derived
family matrix lists these codes and meanings:

| Response context | Candidate Element 83 assertion | Source boundary |
|---|---|---|
| Approved purchase/capture | `0` — Approved—Purchase/Capture | Source meaning is direct. Compatibility with transaction types `0`, `4`, and `6` is the current Test Solution’s source-aligned classification; ATL105 does not publish an exhaustive response-code × transaction-type × card-type cross-table. |
| Approved authorization only | `2` — Approved—Authorization only; `3` — Approved—Authorization only with AVS | Apply `3` only when the relevant AVS context is established. |
| Approved purchase/capture with AVS | `4` — Approved—Purchase/Capture with AVS | Apply only when the AVS response case is intended and source/context supports it. |
| Approved partial approval | `F` — Approved—Partial approval | Also assert the approved amount and any source-supported capability/indicator context; `F` alone does not establish the remaining-balance behavior. |
| Financial decline | `1` — Declined for transactions other than Communications Test; `S` — Declined, retry the transaction | Both meanings are source-listed. The workbook does not state which is expected for any named decline reason. Select one only from an approved processor/host oracle and lifecycle context. |

The source-derived family matrix marks codes outside the financial family as
invalid for that family; overlapping/context-sensitive values must be resolved
with the family identified first. An unknown family is review-required, not a
pass or global failure. The matrix remains source-derived review evidence, not
business approval.

## 4. Appendix H Element 26 outcomes are conditional

ATL105 §13.2 describes Element 26 as fixed length, two alphanumeric characters,
BUYPASS-defined, and meaning “reason for the decline.” It points to Appendix H
for decline codes that permit clerk/customer intervention. Appendix H is
organized into specific behavior groups, including:

- data re-entry codes (such as invalid PIN, voucher/authorization data, employee,
  driver, or vehicle data);
- timeout codes `4I` and `6C`, which identify timed-out transactions to be
  resubmitted;
- Visa recurring decline codes `K=` and `K#`, with distinct merchant
  instructions (stop recurring payments versus do not resubmit the same
  transaction); and
- TransArmor token retrieval code `4M`.

These are **context-specific source rules**, not a universal mapping for the
workbook’s issuer-style decline labels. Appendix H itself does not declare its
listed groups to be the exhaustive valid Element 26 value set. In particular:

- Do not map “Do Not Honor,” “Insufficient Funds,” “Lost Card,” “Stolen Card,”
  “Restricted Card,” “Invalid Account,” “Security Violation,” or “Transaction
  Not Permitted” to an Element 26 value from the title alone.
- Do not assert `4I`/`6C` for every timeout; confirm the authorizer/context.
- Do not translate the workbook’s generic “soft decline” and “hard decline”
  into `K=`/`K#` unless the applicable Visa recurring context and returned
  Decline Code are confirmed.
- For a timed-out/retry case, distinguish a Decline Code instruction to
  resubmit from a financial Response Code (`1` or `S`); the fields have
  different meanings.

## 5. Workbook case outcome disposition

The workbook has 50 case titles containing “decline” or “reject” across BR1–BR5
(13 + 14 + 4 + 9 + 10 respectively). Of these:

- BR1 has an Expected Result column, but its eight named purchase-reason cases
  all say only “Purchase declined”; reversal/TOR declines say only
  “Reversal declined” or “TOR declined”; three other decline cases have blank
  results.
- BR2–BR4 have no Expected Result column, so their 27 decline/reject cases
  have no explicit expected response.
- BR5 has generic outcomes for credential storage, subsequent COF/CIT/MIT,
  recurring, installment, bill-payment, and UCOF declines. It has separate
  “soft decline”/“hard decline” retry titles, but the labels do not identify
  the authoritative response field/value or establish the Visa Appendix H
  context.

The scenario title is not itself an outcome oracle. The following table defines
what can safely be asserted now and what must remain open:

| Workbook case family | Safe outcome assertion to specify now | Must remain open until confirmed |
|---|---|---|
| BR1 purchase approval | Response family is financial; for a confirmed purchase/capture context, assert the source-defined purchase outcome (Element 83 `0`, or `4` only for the supported AVS variant). | Exact network/host response, Element 8, product-specific code mapping, and companion fields. |
| BR1/BR2 issuer declines by named reason | Assert that the case is a decline only if the independent test oracle configures that result; record Element 83 as the host-oracle-selected financial decline code (`1` or `S`) and assert the normalized decline outcome. | Which Element 83 code; exact Element 8 authorizer value; Element 26 value; reason mapping and retry/re-entry behavior. |
| BR1 partial approval | Assert Element 83 `F` when the test oracle specifies partial approval; assert approved amount is less than request and separately verify remainder/split-tender behavior if in scope. | Product/network eligibility, indicator value/context, subsequent payment behavior, and whether a reversal/cancellation follows. |
| BR1 purchase reversal / TOR | Assert the correct lifecycle request/response outcome for the specific original transaction and verify source-required original transaction correlation. | Exact decline code, retry behavior, late response/reconciliation, and settlement effect. The workbook’s “declined” labels do not define these. |
| BR2 authorization/cancellation/completion | For approval, distinguish authorization-only Element 83 `2`/`3` from purchase/capture; for decline, use the context-approved decline oracle. | Whether the case is auth-only, preauthorization completion, cancellation, or another flow; exact host code and retry/cancel behavior. |
| BR3 return/refund/void | Identify the original transaction and assert the explicitly selected return, void-of-return, or reversal outcome; verify the returned/voided amount and settlement effect only under the applicable source rule. | Exact Element 83 transaction-code pairing, Element 8/26 values, return amount policy, duplicate and post-settlement behavior. |
| BR4 balance inquiry / account verification | Identify the message/transaction family and assert the operation-specific success or decline plus any source-defined balance/result data. | Whether these cases use the financial response family/code meanings and the precise product/program support. A response-code-by-transaction cross-product is not supplied. |
| BR5 credential-on-file recurring / retry | Identify initial storage versus CIT/MIT/recurring/installment/UCOF; assert the selected host outcome and whether the transaction is accepted, stopped, or not resubmitted. | The workbook's “soft”/“hard” labels do not determine `K=`/`K#`; confirm scheme, transaction type, and returned Element 26 before asserting Appendix H behavior. |
| BR6–BR13 scope-only scenarios | No case-level expected result can yet be adjudicated because detailed cases are absent. | Outcome, response family/code, product/program, TD, and source anchor. |

## 6. Required expected-result fields

Before a decline or exception case is approved for execution, its expected
result should identify, where applicable:

1. **Request identity:** canonical BR, approved TS, unique TC, specification
   release/source anchor, product/network/program, and transaction family/type.
2. **Response family and message:** expected response message family and which
   response elements are in the test oracle.
3. **Element 83:** exact one-character value and source-defined meaning, or a
   named external host/processor oracle if ATL105 does not specify the
   transaction cross-product.
4. **Element 26:** exact two-character value and source-specific handling only
   when defined for this context; otherwise mark not asserted/TBD, not guess.
5. **Element 8:** exact two-byte value only when a controlled mapping is
   supplied; do not conflate it with Element 83 or Element 26.
6. **Application behavior:** approve/decline/partial result; retry, stop,
   re-entry, cancellation, reversal, or recovery behavior; and user/device
   outcome if required by the test objective.
7. **State and side effects:** transaction status, amounts, balance/settlement
   changes, references, duplicate state, and whether a follow-up is expected.
8. **Oracle authority:** whether the expected value comes from ATL105 directly,
   an approved network/processor rule, or a controlled simulator setup.

If a value is not source-defined and no approved external oracle is supplied,
the expected-result field should explicitly say `REVIEW_REQUIRED`/`TBD` and
identify the decision owner. A plausible value is not a valid test oracle.

## 7. Decisions required to close action 3

1. Confirm the governing ATL105 release for the workbook scenarios.
2. Map each decline title to the intended source of the decision (ATL105
   protocol validation, BUYPASS decline handling, network/issuer decline, or
   test-simulator response).
3. Provide the authoritative host/network mapping for Element 8 and any
   product-specific decline reason; establish whether the case should assert
   Element 26 and which exact value.
4. Choose financial Element 83 values (`1` or `S`) per expected processing
   context; do not map them from scenario wording alone.
5. Confirm the applicability of Appendix H timeout, recurring, re-entry, and
   TransArmor behaviors to the relevant workbook cases.
6. Confirm the response family and operation-specific oracle for returns,
   balance inquiry, account verification, credential storage, and specialized
   product cases.

No expected code has been assigned to “Do Not Honor,” “Insufficient Funds,”
“Lost Card,” or similar workbook labels in this review. That mapping remains
explicitly open pending an authoritative oracle.

## Source references inspected

- ATL105 2026-3, Chapter 13, Element 83: source extract around lines 21186–21305.
- ATL105 2026-3, Chapter 13, Element 26: source extract around lines
  20111–20127.
- ATL105 2026-3, Appendix H: source extract around lines 27348–27427.
- Test Solution response-family matrix:
  [`atl105-response-code-family-matrix.json`](../../../test-json/atl105-response-code-family-matrix.json).
- Element 83 source-derived family validity matrix:
  [`element-83-response-code-br-validation-matrix.json`](../../../docs/specs/kb/element-83-response-code/coverage/element-83-response-code-br-validation-matrix.json).
- Source/training caveats:
  [`response-code-training.md`](../../../docs/specs/kb/segment-100/response-code-training.md).
- Element 26 code-list limitation review:
  [`decline-code-format-values-review.json`](../2026-10-19-full-corpus-review-batch-007/decline-code-format-values-review.json).
