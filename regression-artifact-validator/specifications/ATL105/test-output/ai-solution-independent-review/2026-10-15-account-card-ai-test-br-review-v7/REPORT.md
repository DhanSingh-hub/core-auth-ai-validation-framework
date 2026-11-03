# Account/Card AI-to-Test BR Crossmatch and Chain Review

This review covers all 447 AI Account/Card BRs, all 26 Test Account/Card BRs, the 283 complete-physical-chain AI BRs with no v3 Test candidate, and the seven Test BRs with no v3 AI candidate. Existing source-anchor pairs and ranked retrieval leads are separate; ranked leads are not matches.

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
| rankedLeadsFromAiGapPopulationToSevenTestGaps | 171 |
| aiGapBrsWithRankedLeadToSevenTestGaps | 138 |
| sevenTestGapsReachedByRankedLead | 7 |
| aiGapBrsWithCompletePhysicalChain | 283 |
| testGapRulesWithPlaceholderPrimaryChains | 7 |
| semanticFinalDecisions | 0 |
| testFixtureExecutions | 0 |
| hostExecutions | 0 |

## Seven Test BR Gap Review

### SEG105-R-015

Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount are required

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:438, REQ-SRC-ATL105-PDF-001:1050, REQ-SRC-ATL105-PDF-001:1227, REQ-SRC-ATL105-PDF-001:1327, REQ-SRC-ATL105-PDF-001:1712. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:438: PARTIAL_AGGREGATE_RULE_COVERAGE - Top AI leads describe totals reporting, card labels or count/amount behavior separately; no single AI BR states the entire four-field required set.
- REQ-SRC-ATL105-PDF-001:1050: PARTIAL_AGGREGATE_RULE_COVERAGE - Top AI leads describe totals reporting, card labels or count/amount behavior separately; no single AI BR states the entire four-field required set.
- REQ-SRC-ATL105-PDF-001:1227: PARTIAL_AGGREGATE_RULE_COVERAGE - Top AI leads describe totals reporting, card labels or count/amount behavior separately; no single AI BR states the entire four-field required set.

### SEG108-R-006

Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:2610, REQ-SRC-ATL105-PDF-001:3060, REQ-SRC-ATL105-PDF-001:480, REQ-SRC-ATL105-PDF-001:489, REQ-SRC-ATL105-PDF-001:490. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:2610: NO_DIRECT_AI_FIELD_ORDER_RULE_FOUND - Ranked AI leads share Segment 108 and loyalty terminology but describe transaction/data-field behavior, not the full 15-field serialized order.
- REQ-SRC-ATL105-PDF-001:3060: NO_DIRECT_AI_FIELD_ORDER_RULE_FOUND - Ranked AI leads share Segment 108 and loyalty terminology but describe transaction/data-field behavior, not the full 15-field serialized order.
- REQ-SRC-ATL105-PDF-001:480: NO_DIRECT_AI_FIELD_ORDER_RULE_FOUND - Ranked AI leads share Segment 108 and loyalty terminology but describe transaction/data-field behavior, not the full 15-field serialized order.

### SEG119-R-028

Approved responses include card types 1 through 15

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:1325, REQ-SRC-ATL105-PDF-001:1326, REQ-SRC-ATL105-PDF-001:1324, REQ-SRC-ATL105-PDF-001:481, REQ-SRC-ATL105-PDF-001:1638. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:1325: STRONG_STATEMENT_OVERLAP_REVIEW_REQUIRED - AI and Test statements cover the same value-set or enumeration; independent condition/source review and Test fixture execution remain required.
- REQ-SRC-ATL105-PDF-001:1326: STRONG_STATEMENT_OVERLAP_REVIEW_REQUIRED - AI :1325 directly states card types 1-15 appear in approved responses; verify source context and response completeness.
- REQ-SRC-ATL105-PDF-001:1324: STRONG_STATEMENT_OVERLAP_REVIEW_REQUIRED - AI :1325 directly states card types 1-15 appear in approved responses; verify source context and response completeness.

### SEG119-R-029

Card types 16 through 20 are included only when data occurs

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:1326, REQ-SRC-ATL105-PDF-001:1325, REQ-SRC-ATL105-PDF-001:1503, REQ-SRC-ATL105-PDF-001:2066, REQ-SRC-ATL105-PDF-001:1495. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:1326: STRONG_STATEMENT_OVERLAP_REVIEW_REQUIRED - AI and Test statements cover the same value-set or enumeration; independent condition/source review and Test fixture execution remain required.
- REQ-SRC-ATL105-PDF-001:1325: STRONG_STATEMENT_OVERLAP_REVIEW_REQUIRED - AI :1326 directly states card types 16-20 are included only when data occurs; verify the condition and response completeness.
- REQ-SRC-ATL105-PDF-001:1503: STRONG_STATEMENT_OVERLAP_REVIEW_REQUIRED - AI :1326 directly states card types 16-20 are included only when data occurs; verify the condition and response completeness.

### SEG155-R-004

Card Status (Table 004) valid values: A (New Acct# & Exp Date), E (New Exp Date), Q (Contact Cardholder), C (Closed), U (Unknown Card)

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:1505, REQ-SRC-ATL105-PDF-001:1504, REQ-SRC-ATL105-PDF-001:1700, REQ-SRC-ATL105-PDF-001:1660. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:1505: STRONG_STATEMENT_OVERLAP_REVIEW_REQUIRED - AI and Test statements cover the same value-set or enumeration; independent condition/source review and Test fixture execution remain required.
- REQ-SRC-ATL105-PDF-001:1504: STRONG_ENUMERATION_OVERLAP_REVIEW_REQUIRED - AI :1505 enumerates the same five card-status values; verify authoritative source and full value semantics.
- REQ-SRC-ATL105-PDF-001:1700: STRONG_ENUMERATION_OVERLAP_REVIEW_REQUIRED - AI :1505 enumerates the same five card-status values; verify authoritative source and full value semantics.

### SEG156-R-001

Segment 156 is sent only for an EV charging transaction (EV fuel code in Segment 102 Product Code Segment), and is applicable to the Visa card type only

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:2723, REQ-SRC-ATL105-PDF-001:2740, REQ-SRC-ATL105-PDF-001:2630, REQ-SRC-ATL105-PDF-001:1504, REQ-SRC-ATL105-PDF-001:1501. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:2723: COMPOSITE_APPLICABILITY_OVERLAP_REVIEW_REQUIRED - AI :1529 and :1530 separately cover Visa applicability and EV transaction code; verify combined condition.
- REQ-SRC-ATL105-PDF-001:2740: COMPOSITE_APPLICABILITY_OVERLAP_REVIEW_REQUIRED - AI :1529 and :1530 separately cover Visa applicability and EV transaction code; verify combined condition.
- REQ-SRC-ATL105-PDF-001:2630: COMPOSITE_APPLICABILITY_OVERLAP_REVIEW_REQUIRED - AI :1529 and :1530 separately cover Visa applicability and EV transaction code; verify combined condition.

### SEGDL1-R-012

Table Load Response block order is ')' + DL1, then DL2 (Conditional), then DL3 (Conditional), then End-of-Load Indicator '*' (Element 35); when DL1 carries Card Type 173, DL6 (Data Block No. 4) follows and the load ends with '*'. For TCP/IP the Start-of-Data Block Indicator appears only before Data Block No. 1

Best ranked lead(s): REQ-SRC-ATL105-PDF-001:2387, REQ-SRC-ATL105-PDF-001:2356, REQ-SRC-ATL105-PDF-001:1653. Test chain status: DRAFT_REVIEW_REQUIRED; primary placeholders=3; executable Test payload data not established.

- REQ-SRC-ATL105-PDF-001:2387: PARTIAL_BLOCK_ORDER_AND_MARKER_LEADS - AI leads separately describe block parsing, Start-of-Data markers and Card Type 173/DL6 conditions; the full ordered response sequence is not found in one AI BR.
- REQ-SRC-ATL105-PDF-001:2356: PARTIAL_BLOCK_ORDER_AND_MARKER_LEADS - AI leads separately describe block parsing, Start-of-Data markers and Card Type 173/DL6 conditions; the full ordered response sequence is not found in one AI BR.
- REQ-SRC-ATL105-PDF-001:1653: PARTIAL_BLOCK_ORDER_AND_MARKER_LEADS - AI leads separately describe block parsing, Start-of-Data markers and Card Type 173/DL6 conditions; the full ordered response sequence is not found in one AI BR.

## Strong Provisional Leads

- `SEG119-R-028` is closely stated by AI `:1325` (card types 1-15 included in approved responses); AI chain has two physically complete cases.
- `SEG119-R-029` is closely stated by AI `:1326` (card types 16-20 only when data occurs); AI chain has two physically complete cases.
- `SEG155-R-004` is closely stated by AI `:1505` (the same five Card Status values); AI chain has six physically complete cases.
- `SEG156-R-001` is a composite of AI `:1529` (Visa-only applicability) and `:1530` (EV fuel code in Segment 102); both have physical cases, but the conjunction and negative branches need review.

`SEG105-R-015` has only partial/fragmentary AI leads; `SEG108-R-006` has no direct AI field-order rule identified; `SEGDL1-R-012` has partial block-order/indicator leads, not the full sequence.

No lead is an authorized semantic decision. Test category labels remain review proposals; Test BRs and package chains remain `DRAFT_REVIEW_REQUIRED`/`REVIEW_REQUIRED`. Offline predicates run on AI payloads only; no Test fixture or host executions are claimed.
