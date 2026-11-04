# Test Team Action 2: Proposed Case Traceability and Applicability Review

**Status:** Initial candidate crosswalk; not an approved BR/TS/TC mapping  
**Input:** BA workbook `BR & TS Scope updates.xlsx` (SHA-256 recorded in [the workbook review](./REPORT.md))  
**Related review:** [Action 1 scenario/source review](./TEST-TEAM-ACTION-1-SCENARIO-SOURCE-REVIEW.md)  
**Scope boundary:** Workbook and existing BRs were not modified. Monika's labels and mappings are treated as hypotheses to validate, not authoritative requirements.

## 1. Executive outcome

Action 2 cannot be closed as a confirmed canonical crosswalk yet. The workbook
does not identify the specification release or map BR1–BR13 to canonical ATL105
BR IDs, and most detailed case references do not agree with the workbook's own
scope tab. Product/network applicability is absent from case-level fields on
BR1–BR4 and cannot be inferred from tab names or a product matrix alone.

The repository does contain potentially relevant source-anchored test-solution
BR/TS/TC candidates, but the aggregate and complete packages are explicitly
review-required/draft artifacts. Candidate IDs below are useful for review and
comparison only; they are not approvals, a completed canonical crosswalk, or
evidence that the BA scenarios are covered.

## 2. Workbook-internal traceability results

The workbook has 668 detailed case rows on BR1–BR5 and 86 scope-sheet TS
definitions. Row-level comparison produced:

| Case tab | Rows | Rows with same BR + TS as scope | Rows also matching scenario | Rows whose TS exists only under another scope BR | Rows with TS absent from scope |
|---|---:|---:|---:|---:|---:|
| BR1 Cases | 149 | 46 | 38 | 103 | 0 |
| BR2 Cases | 114 | 0 | 0 | 114 | 0 |
| BR3 Cases | 152 | 0 | 0 | 152 | 0 |
| BR4 Cases | 98 | 0 | 0 | 77 | 21 |
| BR5 Cases | 155 | 0 | 0 | 0 | 155 |
| **Total** | **668** | **46** | **38** | **446** | **176** |

Thus, **622 of 668 rows** do not have a same-parent-BR/TS pair in the scope
sheet. Only **38 rows** match the scope sheet's BR, TS, and scenario values
exactly. These are workbook-internal traceability checks; an incorrect workbook
TS label does not prove that the underlying scenario has no canonical
requirement.

At the distinct-identifier level, 105 TS IDs appear on the five case tabs. Only
7 of those TS IDs are paired with the same BR in the scope tab, and only 6
distinct full BR/TS/scenario tuples match. This is compatible with the row
counts above: the 7 valid pairs account for repeated case rows.

Examples of internal mismatch:

- BR2 case-tab **Authorization Decline** uses TS25, while scope BR4 TS25 is
  **Card Verification (CVV/AVS)**.
- BR3 **Full Merchandise Return** uses TS46, while scope BR7 TS46 is
  **Duplicate Settlement Exception**.
- BR4 **Balance Inquiry Decline** uses TS76, while scope BR12 TS76 is
  **Driver ID Prompt Validation**.
- BR5 case tabs use TS91–TS116, while the scope tab ends at TS86.

## 3. Candidate source-anchored crosswalk

The repository's [`complete-all-test-solution-br-ts-tc-td-package.json`](../complete-all-test-solution-br-ts-tc-td-package.json)
is an **independent Test Solution** package for ATL105 2026-3 with status
`COMPLETE_STRUCTURE_REVIEW_REQUIRED`; the aggregate package
[`all-test-solution-br-ts-tc-td-training-package.json`](../all-test-solution-br-ts-tc-td-training-package.json)
has status `EVIDENCE_AGGREGATE_REVIEW_REQUIRED`. These are useful source-backed
candidate catalogs, not an approved BR baseline. The separate
[approved atomic BR package](../approved-atomic-test-solution-business-requirements.json)
currently contains zero approved new rules and is not execution-ready.

| Workbook proposal / test intent | Candidate canonical Test Solution anchor | What can be confirmed now | What remains unconfirmed |
|---|---|---|---|
| BR1 purchase approval/decline | `BR-SEG100-RC-APPROVED-PURCHASE`, `BR-SEG100-RC-DECLINED`; ATL105 2026-3 §13.2, Element 83 | A source-anchored distinction exists between approved purchase/capture response and declined response. | Workbook's BR1 mapping, raw network response/reason, issuer-vs-validation outcome, product/network applicability, and case TDs. |
| BR1 / BR2 partial approval | `BR-SEG100-RC-PARTIAL`; `BR-RULE-SEG100-R-020`, `BR-RULE-SEG100-R-039`, `BR-RULE-SEG100-R-065`; §12.1 Element 121 and §13.2 | Partial approval response and indicator/context are distinct assertions. Value 5 has an Amex prepaid balance-receipt condition. | Whether each proposed test is in that context; workbook says general Credit/Debit or Amex Credit in its product matrix and does not enumerate Amex prepaid. |
| BR1 purchase reversal, BR2 authorization completion/cancellation, BR3 return void | `BR-SEG100-LIFE-SALE-VOID`, `BR-SEG100-LIFE-AUTH-COMPLETE`, `BR-SEG100-LIFE-AUTH-VOID`, `BR-SEG100-LIFE-REFUND-VOID`; §10 lifecycle correlation | These candidate rules distinguish sale void, authorization completion/void, and return void; each includes a scenario and positive/mismatch cases in the aggregate model. | Exact mapping of workbook BR/TS to these source rules; applicable references and operation-specific exception logic; execution TDs. |
| BR1/BR2 timeout and recovery | `BR-SEG100-LIFE-TIMEOUT-TOR`; §10 Element 86 lifecycle correlation | A source-anchored TOR scenario exists and specifies reuse of the timed-out request's Sequence Number. | Whether the workbook case is a purchase timeout or authorization-only timeout. The latter is not interchangeable with TOR; see the lifecycle source flow in Action 1. |
| BR2 authorization approved/declined | `BR-SEG100-RC-APPROVED-AUTH`, `BR-SEG100-RC-DECLINED`; §13.2 Element 83 | Authorization response outcome has a candidate source chain distinct from purchase response. | The BR2 label/TS IDs and exact response-code, processor, network, and decline-reason mapping. |
| BR3 full/partial/multiple return and duplicate return | No single general canonical return BR was confirmed by this pass; the lifecycle package has a specific return-void correlation candidate. | Return/void are distinguishable transaction flows; source overview lists Merchandise Return/Refund and Void of Return separately. | Amount-limit rules, repeated-return policy, duplicate detector outcome, settlement semantics, and canonical BR mapping require source/product confirmation. |
| BR4 balance inquiry | `BR-TX-E-TRANSACTION-TYPE-E-BALANCE-INQUIRY`; Appendix G Element 78 | A draft source-anchored balance-inquiry transaction-type rule exists for credit, EBT cash-benefit, and stored-value contexts. | Workbook BR4 scope calls out debit/prepaid/healthcare/supported credit; those contexts do not map automatically to this draft anchor. Confirm each context against authoritative sources. |
| BR5 credential storage, CIT/MIT, recurring/installment/bill/UCOF | No exact common canonical BR-to-case mapping confirmed. Appendix J entry-mode rules and network-specific source knowledge provide partial constraints. | Stored-credential entry mode is context-specific; initial storage/verification differs from subsequent usage. | Consent, transaction indicators, network-specific policies, all workbook BR/TS IDs, and expected host outcomes. |
| BR6 EMV/entry mode | `BR-RULE-SEG100-R-002` / `BR-SEG100-APPR-EMV-COMPANION` candidate for Segment 130; Appendix J for entry-mode values/context | EMV request data and entry-mode context have source-specific rules. | Contact/contactless, PIN, fallback, mobile cases require device/card/transaction conditions and release-specific crosswalk; BR6 has no detailed case tab. |
| BR11–BR13 healthcare/fleet/industry | Segment 101/103 and product-specific source catalogs may be relevant; eWIC and fleet examples are separately source-pinned in the package. | Some specialized requirements have specific source rules; they should not be generalized across all products. | No detailed cases are supplied for BR6–BR13; no canonical mapping, product/program permission, or complete scenario data is established. |

The candidate package's legacy status `COVERED`, where present, is not a
statement that a workbook case is approved, mapped, executable, or passing.
The complete package is review-required. The approved atomic package reports
zero approved new rules. It is therefore important not to promote candidate
relationships into “confirmed” mappings.

## 4. Case-level scenario intent and applicability assessment

### Scenario intent

Some scenario titles indicate a useful test intent, but titles do not always
state the behavior to assert:

- “Declined” does not distinguish an issuer response from malformed input,
  unsupported product/context, local validation, or a retry/re-entry outcome.
- “Partial approval” needs the requested amount, approved amount, capability
  indicator/context, and remaining-balance disposition to identify what the
  case actually verifies.
- “Reversal”, “cancellation”, “void”, “refund”, and “timeout reversal” are not
  synonyms. The original transaction and follow-up type must be stated.
- “Duplicate” needs the duplicate key/condition and expected behavior for the
  applicable host/network. A repeated amount alone does not establish a
  duplicate.
- “Settlement successful” or “settlement adjusted” should identify the
  settlement/batch state and expected record/totals effect.

### Product and network applicability

- The scope tab uses broad labels such as `All`, `Credit`, `Debit`, `Fleet`,
  `Healthcare`, `Prepaid`, and industry labels; the product matrix instead
  assigns BR ranges to 12 product names. It does not give a product/network
  mapping per case or a source for those assignments.
- BR1–BR4 detailed case sheets have no network/product/program fields. Their
  case-level product/network applicability is therefore **not specified**,
  even where the scope tab has a broad category.
- BR5 has `Network` and `Card_Programs` columns on all 155 case rows, but those
  cases use TS91–TS116, which are absent from the scope tab. Their applicability
  cannot be traced to a scope TS or confirmed against the product matrix.
- The product matrix lists Amex Credit, but not Amex prepaid. Do not silently
  use its general “Credit” labels to claim coverage of the source-specific
  Amex prepaid partial-approval indicator context.
- The balance-inquiry scope names debit, prepaid, healthcare and supported
  credit products. The product matrix has Mastercard/Visa/Interlink debit,
  Healthcare Card, and credit entries, but no explicit prepaid product row.
  Treat prepaid applicability as an open mapping, not as confirmed or excluded.
- The product matrix does not explicitly name EBT/eWIC. Existing source-backed
  candidate rules for eWIC authorization, cancellation, balance inquiry,
  completion, reversal/void, and no-return do exist in the independent
  Test Solution catalog. If eWIC is intended to be within the workbook's test
  scope, its omission needs a deliberate disposition; its absence alone does
  not prove it is required in the client's scope.
- The product matrix labels fleet cards, purchasing cards, corporate cards,
  and healthcare cards, but does not state network-program combinations or
  operation-level permissions. These labels should not be expanded into
  network or product authorization assumptions.

## 5. Action 2 disposition by required field

| Field | Current disposition |
|---|---|
| Canonical BR | **Unconfirmed** for workbook BR1–BR13. Repository source-anchored candidate IDs exist for certain protocol/lifecycle rules, but the workbook has no crosswalk and the independent package is review-required. |
| Applicable TS | **Workbook-internal mismatches confirmed.** 46 case rows match their own scope parent BR/TS; 38 also match scenario. Most detailed references require correction/adjudication. |
| Scenario intent | **Partially interpretable from titles.** Decline, partial-approval, lifecycle, duplicate, and settlement titles need explicit behavior/original transaction/expected effect. |
| Product/network applicability | **Mostly unconfirmed.** Not present at case level for BR1–BR4; BR5 has values but no matching scope TS. The product matrix is not a per-case source-backed applicability oracle. |

No action was taken to revise the spreadsheet, canonical BRs, test model, or
product matrix.

## 6. Decisions/evidence needed to close this action

1. Establish canonical BR candidates by source section/rule and have a reviewer
   adjudicate them; do not assume Monika's BR labels or earlier AI mappings are
   correct.
2. Resolve the 622 case rows without a same-parent-BR/TS scope link, beginning
   with BR2–BR5 and BR1's remaining misassigned rows.
3. Confirm scenario intent against the governing source release, including
   transaction class, original/follow-up relationship, and precise assertion.
4. Define product/network/program applicability per case from authoritative
   source and approved client/product scope. Explicitly disposition omitted
   EBT/eWIC and prepaid contexts rather than silently including or excluding
   them.
5. Link each adjudicated test case to a stable, globally unique TC ID and TD
   evidence. The repository's candidate package contains no test-data IDs
   linked from the reviewed lifecycle TC records; this mapping does not certify
   executable data.

Until these inputs are reviewed, label mappings `CANDIDATE` or `REVIEW_REQUIRED`;
do not use them to claim approved test coverage.
