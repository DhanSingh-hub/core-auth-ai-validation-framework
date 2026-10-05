# Segment 100 Single-Step and Multi-Step BR Traceability

## Scope and Counting Rules

This inventory uses the independent Test Solution BR sources, not AI-generated requirements. A code-level BR establishes support/behavior for a transaction type; it is not automatically a lifecycle BR. Lifecycle flows reuse some code-level BRs and add flow-level correlation requirements.

- Single-step view: **42 BRs** = 8 standard financial code BRs + 34 special/nonfinancial draft BRs. The 34 special BRs remain `DRAFT_REVIEW_REQUIRED`; this is a classification/count, not execution readiness.
- Multi-step view: **12 transaction-code BR references** for participating types + **5 lifecycle BR entries** currently in the catalog. Seven of the 12 code BRs also appear in the single-step view, so these are not additive when counting distinct IDs.
- Across the two views: **52 distinct existing BR IDs** (47 transaction-code BRs + 5 lifecycle BR entries). One lifecycle BR, `BR-SEG100-LIFE-AUTH-VOID`, encodes an invalid transaction-code pairing and must not be treated as valid coverage. Authorization cancellation is a required flow but has no dedicated lifecycle BR; it is traceable to `BR-SEG100-TX-S` and the source rules below.
- The requirements document defines five source-supported flow families after removing its invalid authorization-to-code-8 flow. The combined matrix lists those five families. The lifecycle catalog instead contains the invalid authorization-void entry and omits cancellation.

## Single-Step BRs (42)

### Standard Financial (8)

| BR ID | Transaction type | Traceability |
|---|---|---|
| `BR-SEG100-TX-0` | POS Purchase/Capture; code 0 also supports preauthorized completion | ATL105 2026-3, Appendix G, Segment 100, Element 78, `transaction-type-0` |
| `BR-SEG100-TX-3` | POS Authorization Only | ATL105 2026-3, Appendix G, Segment 100, Element 78, `transaction-type-3` |
| `BR-SEG100-TX-4` | Customer-activated Purchase/Capture | ATL105 2026-3, Appendix G, Segment 100, Element 78, `transaction-type-4` |
| `BR-SEG100-TX-5` | Customer-activated Authorization Only | ATL105 2026-3, Appendix G, Segment 100, Element 78, `transaction-type-5` |
| `BR-SEG100-TX-6` | Mail/Phone Purchase | ATL105 2026-3, Appendix G, Segment 100, Element 78, `transaction-type-6` |
| `BR-SEG100-TX-7` | Merchandise Return/Refund | ATL105 2026-3, Appendix G, Segment 100, Element 78, `transaction-type-7` |
| `BR-SEG100-TX-A` | Account Verification | ATL105 2026-3, Appendix G, Segment 100, Element 78, `transaction-type-A` |
| `BR-SEG100-TX-B` | Mail/Phone Authorization Only | ATL105 2026-3, Appendix G, Segment 100, Element 78, `transaction-type-B` |

### Special/Nonfinancial (34; all draft review required)

| BR ID | Code | Behavior | Traceability |
|---|---:|---|---|
| `BR-TX-9-TRANSACTION-TYPE-9-SPECIAL-FLOW` | 9 | Route special transactions to specialized nonfinancial flows | Appendix G, Segment 100, Element 78, `transaction-type-9-special-flow` |
| `BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT` | 9 | Special Prompt Code format; no financial card type appended | Appendix E, Segment 100, Element 78, `special-prompt-code-three-character-format` |
| `BR-TX9-PROMPT-900` | 9 | Communications Status | Appendix E, Segment 100, Element 78, `special-prompt-900` |
| `BR-TX9-PROMPT-901` | 9 | Custom receipt text | Appendix E, Segment 100, Element 78, `special-prompt-901` |
| `BR-TX9-PROMPT-902` | 9 | Dynamic card table | Appendix E, Segment 100, Element 78, `special-prompt-902` |
| `BR-TX9-PROMPT-903` | 9 | Site configuration data | Appendix E, Segment 100, Element 78, `special-prompt-903` |
| `BR-TX9-PROMPT-904` | 9 | Host discount data | Appendix E, Segment 100, Element 78, `special-prompt-904` |
| `BR-TX9-PROMPT-905` | 9 | Fuel volume data | Appendix E, Segment 100, Element 78, `special-prompt-905` |
| `BR-TX9-PROMPT-981` | 9 | Nonproprietary electronic mail | Appendix E, Segment 100, Element 78, `special-prompt-981` |
| `BR-TX9-PROMPT-990` | 9 | Totals | Appendix E, Segment 100, Element 78, `special-prompt-990` |
| `BR-TX9-PROMPT-995` | 9 | Electronic mail submission | Appendix E, Segment 100, Element 78, `special-prompt-995` |
| `BR-TX9-PROMPT-996` | 9 | Proprietary electronic mail | Appendix E, Segment 100, Element 78, `special-prompt-996` |
| `BR-TX9-PROMPT-997` | 9 | Electronic mail status reset | Appendix E, Segment 100, Element 78, `special-prompt-997` |
| `BR-TX-D-TRANSACTION-TYPE-D-RBC-LOYALTY-LOOKUP` | D | RBC Loyalty Lookup | Appendix G, Segment 100, Element 78, `transaction-type-D-rbc-loyalty-lookup`; Appendix-G-only evidence |
| `BR-TX-E-TRANSACTION-TYPE-E-BALANCE-INQUIRY` | E | Balance inquiry for credit, EBT cash-benefit, and stored-value contexts | Appendix G, Segment 100, Element 78, `transaction-type-E-balance-inquiry` |
| `BR-TX-E-TRANSACTION-TYPE-E-SVC-BALANCE-INQUIRY-NO-TOTAL-CHANGE` | E | Stored-value inquiry reports balance without changing totals | Section 10.7.3.11, Segment 100, Element 78, `transaction-type-E-svc-balance-inquiry-no-total-change` |
| `BR-TX-K-TRANSACTION-TYPE-K-SVC-ACTIVATION` | K | Stored-value activation | Section 10.7.3.6, Segment 100, Element 78, `transaction-type-K-svc-activation` |
| `BR-TX-L-TRANSACTION-TYPE-L-STANDARD-GIFT-DEACTIVATION` | L | Standard Gift Card deactivation | Section 10.7.3.7, Segment 100, Element 78, `transaction-type-L-standard-gift-deactivation` |
| `BR-TX-L-TRANSACTION-TYPE-L-FIRST-DATA-CASHOUT` | L | First Data Premium Gift Card cash-out | Section 10.7.3.7, Segment 100, Element 78, `transaction-type-L-first-data-cashout` |
| `BR-TX-M-TRANSACTION-TYPE-M-BALANCE-MERGE-TWO-CARDS` | M | Merge up to two stored-value card balances | Section 10.7.3.9, Segment 100, Element 78, `transaction-type-M-balance-merge-two-cards` |
| `BR-TX-M-TRANSACTION-TYPE-M-BALANCE-MERGE-REPEAT` | M | Repeat merges when more than two cards are involved | Section 10.7.3.9, Segment 100, Element 78, `transaction-type-M-balance-merge-repeat` |
| `BR-TX-N-TRANSACTION-TYPE-N-REPLACE-CARD` | N | Transfer balance to replacement card; zero old balance | Section 10.7.3.10, Segment 100, Element 78, `transaction-type-N-replace-card` |
| `BR-TX-Q-TRANSACTION-TYPE-Q-RECHARGE-ORIGINAL-VALUE-CAP` | Q | Recharge up to original card value | Section 10.7.3.8, Segment 100, Element 78, `transaction-type-Q-recharge-original-value-cap` |
| `BR-TX-T-TRANSACTION-TYPE-T-TRANSARMOR-TOKEN-REGISTRATION` | T | Identify TransArmor token registration | Appendix G, Segment 100, Element 78, `transaction-type-T-transarmor-token-registration`; details delegated externally |
| `BR-TXV-ADD-ACCOUNT` | V | Add loyalty account | Section 10.9.3, Segment 100, Element 78, `transaction-type-V-loyalty-add-account` |
| `BR-TXV-COUPON-REDEEM` | V | Redeem coupon | Section 10.9.3, Segment 100, Element 78, `transaction-type-V-loyalty-coupon-redeem` |
| `BR-TXV-EXPIRATION-UPDATE` | V | Update expiration date | Section 10.9.3, Segment 100, Element 78, `transaction-type-V-loyalty-expiration-update` |
| `BR-TXV-ACCOUNT-INQUIRY` | V | Loyalty account inquiry | Section 10.9.3, Segment 100, Element 78, `transaction-type-V-loyalty-account-inquiry` |
| `BR-TXV-POINTS-REDEEM` | V | Redeem loyalty points | Section 10.9.3, Segment 100, Element 78, `transaction-type-V-loyalty-points-redeem` |
| `BR-TXV-COUPON-REVERSAL` | V | Reverse coupon redemption | Section 10.9.3, Segment 100, Element 78, `transaction-type-V-loyalty-coupon-reversal` |
| `BR-TXV-POINTS-REVERSAL` | V | Reverse redeemed points | Section 10.9.3, Segment 100, Element 78, `transaction-type-V-loyalty-points-reversal` |
| `BR-TXV-SALE-UPDATE` | V | Loyalty sale update | Section 10.9.3, Segment 100, Element 78, `transaction-type-V-loyalty-sale-update` |
| `BR-TXV-ACCOUNT-UPDATE` | V | Update loyalty account | Section 10.9.3, Segment 100, Element 78, `transaction-type-V-loyalty-account-update` |
| `BR-TXV-TOTALS-REPORT` | V | Loyalty totals report | Section 10.9.3, Segment 100, Element 78, `transaction-type-V-loyalty-totals-report` |

## Multi-Step BR Traceability

### Required source-supported flow families (5)

| Flow | Original -> follow-up | Dedicated lifecycle BR | Source traceability / status |
|---|---|---|---|
| Authorization -> completion | `3`, `5`, or `B` -> `0` | `BR-SEG100-LIFE-AUTH-COMPLETE` | ATL105 2026-3, Section 10, Segment 100, Element 86, `lifecycle-correlation`; catalogued |
| Authorization -> cancellation | `3`, `5`, or `B` -> `S` | No dedicated lifecycle BR | `BR-SEG100-TX-S`; Appendix G, Segment 100, Element 78, `transaction-type-S`; authorization reversal/correlation rules in Sections 10.1.2.2/10.2.2.2 and Element 86. Original Approval Number and Sequence Number must be present and identical; add negative tests for missing/mismatched Approval Number. |
| Purchase/sale -> void or reversal | `0` or `4` -> `8`; `6` -> `C` | `BR-SEG100-LIFE-SALE-VOID` | ATL105 2026-3, Section 10, Segment 100, Element 86, `lifecycle-correlation`, plus Appendix G transaction-type descriptions. Do not accept the cross-paired combinations. |
| Refund/return -> void of return | `7` -> `U` | `BR-SEG100-LIFE-REFUND-VOID` | ATL105 2026-3, Section 10, Segment 100, Element 86, `lifecycle-correlation`; catalogued |
| Timeout -> TOR -> next financial request | Eligible Purchase/Capture `0`, CAT Purchase/Capture `4`, or Mail/Phone Purchase `6` -> `Z` -> next financial request with new sequence | `BR-SEG100-LIFE-TIMEOUT-TOR` | ATL105 2026-3, Sections 2.3/2.3.1 and Element 86; distinguish code `0` Purchase/Capture from preauthorized completion, and apply card/product restrictions. Debit POS/CAT capture TORs are unsupported. |

The composed three-leg flow Authorization -> Completion -> Void is **not** a direct Authorization Only -> code `8` reversal. The existing [SME/TBA input register](../segment-100-sme-tba-input-register.md) marks its void target and debit eligibility `REVIEW_REQUIRED`; do not count it as validated coverage until that question is resolved.

### Supporting transaction-code BRs (12)

These existing BRs define the participating original/follow-up transaction types. The seven marked `*` are already counted in the single-step view; do not add them again to the distinct-ID total.

- `BR-SEG100-TX-0`* — POS Purchase/Capture; also preauthorized completion.
- `BR-SEG100-TX-3`* — POS Authorization Only.
- `BR-SEG100-TX-4`* — Customer-activated Purchase/Capture.
- `BR-SEG100-TX-5`* — Customer-activated Authorization Only.
- `BR-SEG100-TX-6`* — Mail/Phone Purchase.
- `BR-SEG100-TX-7`* — Merchandise Return/Refund.
- `BR-SEG100-TX-8` — Purchase Reversal/Void.
- `BR-SEG100-TX-B`* — Mail/Phone Authorization Only.
- `BR-SEG100-TX-C` — Mail/Phone Reversal/Void.
- `BR-SEG100-TX-S` — Cancellation of authorization-only transaction.
- `BR-SEG100-TX-U` — Void of Merchandise Return.
- `BR-SEG100-TX-Z` — Time-out Reversal.

All code-level anchors are ATL105 2026-3, Appendix G, Segment 100, Element 78, `transaction-type-{code}`.

## Cross-Check of New Requirements

| Requirement document clause | Finding | Source-backed learning |
|---|---|---|
| 2.1 Authorization -> completion, including `3`, `5`, `B` -> `0` | Supported for the general preauthorization relationship; preserve the original Element 86. The final completion amount may differ from the authorization amount. | Element 86 says subsequent transactions associated with the initial authorization retain its Sequence Number; a preauthorized Purchase/Capture uses the authorization's Sequence Number. |
| 2.2 Authorization -> cancellation, `3`, `5`, `B` -> `S` | Supported. The previous note incorrectly treated `B` as unsupported; Element 86 explicitly includes POS Mail/Phone Authorization Only (`B`) among Authorization Only Reversal transactions. Add the required matching Approval Number assertion. | Sections 10.1.2.2/10.2.2.2 require matching original Approval Number and Sequence Number and Prompt Code `S`; Element 86 also names `3`, `B`, and `5`. |
| 2.3 Authorization Only -> void, `3`, `5`, `B` -> `8` | **Invalid.** Authorization Only Reversal uses `S`, not purchase reversal code `8`. Remove this as a separate valid flow; use source-defined cancellation with `S`. `BR-SEG100-LIFE-AUTH-VOID` is not valid coverage for this pairing. | Sections 10.1.2.2/10.2.2.2 specify Prompt Code `S`; Appendix G defines `8` as Purchase Reversal/Void. The separate Authorization -> Completion -> Void question is still `REVIEW_REQUIRED` under SEG100-SME-002. |
| 2.3/2.4 same TerminalID for all reversal pairs | **Unsupported as a universal requirement.** ATL105 explicitly requires the same device for TOR. Authorization-reversal rules require matching Approval Number and Sequence Number, not matching TerminalID. | Restrict same-device/TerminalID assertions to flows whose source says so; do not make this a general lifecycle invariant. |
| 2.4 purchase/sale reversal permits `8` or `C` for any of `0`, `4`, `6` | **Under-specified; cross-pairing is invalid.** Use `0`/`4` -> `8` and Mail/Phone purchase `6` -> `C`; validate the transaction context, not a Cartesian product of codes. | Appendix G defines `8` as Purchase Reversal/Void and `C` as Mail/Phone Reversal/Void. |
| 2.5 Refund -> void of return, `7` -> `U` | Supported as a transaction-type flow; retain the original lifecycle Sequence Number. Account, amount, and terminal equality should remain conditional on an applicable rule. | Appendix G identifies `7` as Merchandise Return/Refund and `U` as Void of Merchandise Return; Element 86 defines transaction-lifetime correlation. |
| 3.1 TOR originals `0`, `4`, `6` | Supported with card/product restrictions, but code `0` also represents preauthorized completion; do not infer that completion itself is TOR-eligible from the code alone. Debit POS/CAT capture is explicitly unsupported. | Sections 2.3/2.3.1; Section 10.2 and relevant card/product rules. |
| 3.1 “wait until the documented 30-second timeout has expired” | **Incomplete timing rule.** The response interval is 30 seconds; the source separately recommends forwarding the TOR at least 30 seconds after the original transaction has timed out. Preserve these as separate intervals. | Sections 2.3.1/2.3.2 and the timeout flow diagram. |
| 3.1/4 preserve original account and dollar amount | **Over-broad without partial-approval rules.** Account/amount are matching keys, but required reversal/TOR amount can be the approved or requested amount depending on card and partial-approval case. | Sections 10.1.2.1, 10.2.1.1, 10.1.6.4.2/.4, and 10.2.3.4.2/.4 define amount-specific exceptions. |
| 4 maximum three TOR attempts total | **Invalid if interpreted as a global cap.** ATL105 says clear after three unsuccessful attempts on each available connection route. | Section 2.3.1. Retries must keep the same TOR identity; apply the attempt limit per available route and source health rules. |
| 5 sample as minimum converter-ready JSON | **Invalid as a fixture/example.** It contains placeholder values (`...`) and unconditionally shows Segment 111 and Segment 130 while saying companion segments are conditional. Do not submit it as converter-ready Test Data. | Make the example explicitly schematic or replace it with a validated transaction-specific JSON fixture with accurate segment count and lengths. |
| 6 same generic Section 10/Element 86 anchor on every artifact | **Insufficient traceability.** A shared lifecycle anchor can supplement, but cannot replace the exact source anchors for each transaction code and each specific rule. | BR/TS/TC/TD records should carry the applicable source anchor(s), including Appendix G/Element 78 where transaction-code semantics are asserted. |

### Existing Implementation/Artifact Drift

- `Segment100MultiStepCoverageValidator` accepts the invalid Authorization Only `-> 8` flow, accepts cross-paired sale reversal codes, allows TOR originals `3`, `5`, and `B`, imposes a global three-attempt cap, and universally compares TerminalID. Its acceptance rules should not be treated as the source of truth until corrected.
- `segment-100-multistep-flow-catalog.json` contains `BR-SEG100-LIFE-AUTH-VOID` and lacks a dedicated authorization-cancellation BR. Replace the invalid code-8 BR with a source-anchored cancellation BR before calling the lifecycle catalog complete.
- `combined-23-transaction-type-br-ts-tc-td-matrix.md` lists `3`, `5`, and `B` as timeout originals and groups `8`/`C` across originals `0`, `4`, and `6`. Narrow TORs to eligible purchases with card/product rules, and constrain reversal pairs to `0`/`4` -> `8` and `6` -> `C`.
- The shared flow-specific anchor `ATL105|2026-3|10|100|86|lifecycle-correlation` is generic. Add the precise Section 2.3/2.3.1, Section 10.1.2.2/10.2.2.2, and Appendix G anchors where they establish the rule.
- The special BR count includes draft candidates only. Codes `D` and `T` remain source blockers; no special BR is execution-ready based on this inventory.

## Source Artifacts

- [Standard financial code-flow BR package](../../../../../test-output/test-json/segment-100-code-flow-br-ts-tc-testdata.json)
- [Special transaction-type BR draft baseline](../../../../../test-output/test-json/special-transaction-type-br-baseline.json)
- [Multi-step lifecycle catalog](../../../../../test-output/test-json/segment-100-multistep-flow-catalog.json)
- [Multi-step requirements](../../../../SEGMENT-100-MULTI-STEP-TRANSACTION-REQUIREMENTS-FOR-AI-TEAM.md)
- [Sequence lifecycle learning note](../sequence-lifecycle-sme-tba-note.md)
- [Combined 23-code matrix](../../../../../test-output/test-json/combined-23-transaction-type-br-ts-tc-td-matrix.md)
