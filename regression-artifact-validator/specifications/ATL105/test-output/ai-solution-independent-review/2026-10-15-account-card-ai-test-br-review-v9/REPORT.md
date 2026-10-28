# Account/Card AI-to-Test BR Crossmatch and Chain Review

This review covers all 447 AI Account/Card BRs, all 26 Test Account/Card BRs, the 283 complete-physical-chain AI BRs with no v3 Test candidate, and the seven Test BRs with no v3 AI candidate. It includes 1,371 top-K ranked leads from the full AI catalog to those seven Test BRs, separately identifying the 171 leads sourced from the 283 complete-chain AI gap set. Existing source-anchor pairs and ranked retrieval leads are separate; ranked leads are not matches.

| Measure | Count |
|---|---:|
| aiAccountCardBrs | 447 |
| testAccountCardBrs | 26 |
| completeChainAiBrsWithoutV3Candidate | 283 |
| unmatchedTestBrsWithoutAiCandidate | 7 |
| allV3PairsTouchingAccountCard | 120 |
| sameCategoryV3PairRows | 19 |
| sameCategoryV3AiBrs | 8 |
| sameCategoryV3TestBrs | 12 |
| rankedLeadsFromFullAiCatalogToSevenTestGaps | 1371 |
| distinctFullCatalogAiBrsWithRankedLeads | 1272 |
| rankedLeadsFromCompleteChainAiGapPopulationToSevenTestGaps | 171 |
| completeChainAiGapBrsWithRankedLeads | 138 |
| sevenTestGapsReachedByFullCatalogRankedLeads | 7 |
| aiGapBrsWithCompletePhysicalChain | 283 |
| testGapRulesWithPlaceholderPrimaryChains | 7 |
| semanticFinalDecisions | 0 |
| testFixtureExecutions | 0 |
| hostExecutions | 0 |

## Seven Test BR Gap Review

### SEG105-R-015

Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount are required

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:2488, REQ-SRC-ATL105-PDF-001:438, REQ-SRC-ATL105-PDF-001:448, REQ-SRC-ATL105-PDF-001:5852, REQ-SRC-ATL105-PDF-001:5854. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:2488: PARTIAL_AGGREGATE_RULE_COVERAGE - Top AI leads describe totals reporting, card labels or count/amount behavior separately; no single AI BR states the entire four-field required set.
- REQ-SRC-ATL105-PDF-001:438: PARTIAL_AGGREGATE_RULE_COVERAGE - Top AI leads describe totals reporting, card labels or count/amount behavior separately; no single AI BR states the entire four-field required set.
- REQ-SRC-ATL105-PDF-001:448: PARTIAL_AGGREGATE_RULE_COVERAGE - Top AI leads describe totals reporting, card labels or count/amount behavior separately; no single AI BR states the entire four-field required set.

### SEG108-R-006

Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:2487, REQ-SRC-ATL105-PDF-001:2610, REQ-SRC-ATL105-PDF-001:2920, REQ-SRC-ATL105-PDF-001:2921, REQ-SRC-ATL105-PDF-001:2925. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:2487: RETRIEVAL_LEAD_NOT_ORDER_EVIDENCE - Shared loyalty/account terminology does not establish any of the 15 serialized positions.
- REQ-SRC-ATL105-PDF-001:2610: RETRIEVAL_LEAD_NOT_ORDER_EVIDENCE - Shared loyalty/account terminology does not establish any of the 15 serialized positions.
- REQ-SRC-ATL105-PDF-001:2920: RETRIEVAL_LEAD_NOT_ORDER_EVIDENCE - Shared loyalty/account terminology does not establish any of the 15 serialized positions.

### SEG119-R-028

Approved responses include card types 1 through 15

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:1325, REQ-SRC-ATL105-PDF-001:2614, REQ-SRC-ATL105-PDF-001:2615, REQ-SRC-ATL105-PDF-001:2695, REQ-SRC-ATL105-PDF-001:2696. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:1325: STRONG_ENUMERATION_OVERLAP_REVIEW_REQUIRED - AI and Test both state card types 1-15 in approved responses; source and response completeness still require review.
- REQ-SRC-ATL105-PDF-001:2614: RELATED_DIFFERENT_CARD_TYPE_SCOPE - This AI lead does not establish the Test BR's 1-15 card-type set.
- REQ-SRC-ATL105-PDF-001:2615: RELATED_DIFFERENT_CARD_TYPE_SCOPE - This AI lead does not establish the Test BR's 1-15 card-type set.

### SEG119-R-029

Card types 16 through 20 are included only when data occurs

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:1326, REQ-SRC-ATL105-PDF-001:2954, REQ-SRC-ATL105-PDF-001:397, REQ-SRC-ATL105-PDF-001:1325, REQ-SRC-ATL105-PDF-001:2614. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:1326: STRONG_CONDITIONAL_OVERLAP_REVIEW_REQUIRED - AI and Test both state card types 16-20 only when data occurs; verify scope and response completeness.
- REQ-SRC-ATL105-PDF-001:2954: RELATED_DIFFERENT_CARD_TYPE_SCOPE - This AI lead does not establish the Test BR's 16-20 conditional set.
- REQ-SRC-ATL105-PDF-001:397: RELATED_DIFFERENT_CARD_TYPE_SCOPE - This AI lead does not establish the Test BR's 16-20 conditional set.

### SEG155-R-004

Card Status (Table 004) valid values: A (New Acct# & Exp Date), E (New Exp Date), Q (Contact Cardholder), C (Closed), U (Unknown Card)

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:1505, REQ-SRC-ATL105-PDF-001:4974, REQ-SRC-ATL105-PDF-001:4975, REQ-SRC-ATL105-PDF-001:4972, REQ-SRC-ATL105-PDF-001:4973. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:1505: STRONG_ENUMERATION_OVERLAP_REVIEW_REQUIRED - AI and Test enumerate the same five Card Status values; source authority still requires review.
- REQ-SRC-ATL105-PDF-001:4974: RELATED_DIFFERENT_CARD_STATUS_OBLIGATION - This AI lead does not establish the five-value Card Status enumeration.
- REQ-SRC-ATL105-PDF-001:4975: RELATED_DIFFERENT_CARD_STATUS_OBLIGATION - This AI lead does not establish the five-value Card Status enumeration.

### SEG156-R-001

Segment 156 is sent only for an EV charging transaction (EV fuel code in Segment 102 Product Code Segment), and is applicable to the Visa card type only

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:1128, REQ-SRC-ATL105-PDF-001:1548, REQ-SRC-ATL105-PDF-001:2569, REQ-SRC-ATL105-PDF-001:2570, REQ-SRC-ATL105-PDF-001:2619. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:1128: COMPOSITE_APPLICABILITY_OVERLAP_REVIEW_REQUIRED - AI :1529 and :1530 separately cover Visa applicability and EV transaction code; verify combined condition.
- REQ-SRC-ATL105-PDF-001:1548: COMPOSITE_APPLICABILITY_OVERLAP_REVIEW_REQUIRED - AI :1529 and :1530 separately cover Visa applicability and EV transaction code; verify combined condition.
- REQ-SRC-ATL105-PDF-001:2569: COMPOSITE_APPLICABILITY_OVERLAP_REVIEW_REQUIRED - AI :1529 and :1530 separately cover Visa applicability and EV transaction code; verify combined condition.

### SEGDL1-R-012

Table Load Response block order is ')' + DL1, then DL2 (Conditional), then DL3 (Conditional), then End-of-Load Indicator '*' (Element 35); when DL1 carries Card Type 173, DL6 (Data Block No. 4) follows and the load ends with '*'. For TCP/IP the Start-of-Data Block Indicator appears only before Data Block No. 1

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:1074, REQ-SRC-ATL105-PDF-001:1075, REQ-SRC-ATL105-PDF-001:1081, REQ-SRC-ATL105-PDF-001:1100, REQ-SRC-ATL105-PDF-001:3108. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:1074: PARTIAL_TABLE_LOAD_BLOCK_MARKER_RULE - AI covers a block parse/order marker clause, not the full ordered Table Load Response rule.
- REQ-SRC-ATL105-PDF-001:1075: PARTIAL_TABLE_LOAD_BLOCK_MARKER_RULE - AI covers a block parse/order marker clause, not the full ordered Table Load Response rule.
- REQ-SRC-ATL105-PDF-001:1081: PARTIAL_TABLE_LOAD_BLOCK_MARKER_RULE - AI covers a block parse/order marker clause, not the full ordered Table Load Response rule.

## Strong Provisional Leads

- `SEG119-R-028` is closely stated by AI `:1325` (card types 1-15 included in approved responses); AI chain has two physically complete cases.
- `SEG119-R-029` is closely stated by AI `:1326` (card types 16-20 only when data occurs); AI chain has two physically complete cases.
- `SEG155-R-004` is closely stated by AI `:1505` (the same five Card Status values); AI chain has six physically complete cases.
- `SEG156-R-001` has a close AI statement `:5992` (EV charging segment, Visa-only) plus AI `:1529` (Visa-only) and `:1530` (EV fuel code in Segment 102); together they suggest coverage, but the condition conjunction and negative branches need review.

`SEG105-R-015` has only partial/fragmentary AI leads; `SEG108-R-006` has no direct AI field-order rule identified; `SEGDL1-R-012` has partial block-order/indicator leads, not the full sequence.

The v3 crosswalk requires an exact normalized segment plus numeric-element anchor; a Test BR absent from its index means no candidate under that policy, not proof that no AI requirement exists and not an authorized `NOT_MATCH`. No lead is an authorized semantic decision. Test category labels remain review proposals; Test BRs and package chains remain `DRAFT_REVIEW_REQUIRED`/`REVIEW_REQUIRED`. Offline predicates run on AI payloads only; no Test fixture or host executions are claimed.
