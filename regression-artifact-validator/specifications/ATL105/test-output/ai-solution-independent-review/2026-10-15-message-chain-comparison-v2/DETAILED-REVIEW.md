# Detailed Message Category Cross-Matching

## Scope and Result

The comparison includes all 97 AI Message BRs and 243 Test Message BRs. All 23,571 Message-to-Message combinations were scored for review retrieval. To avoid category-label blind spots, each AI Message BR was also compared to all 605 Test BRs, and every Test Message BR to all 6,880 AI BRs. Category assignments remain proposals.

| Measure | Result |
|---|---:|
| Existing v3 candidate pairs touching Message on either side | 240 |
| Existing Message-to-Message pairs | 12 |
| Additional context-based review leads, including cross-category | 871 |
| Additional Message-to-Message leads | 32 |
| Test Message BRs with existing candidates | 104 / 243 |
| Test Message BRs with any retained lead | 146 / 243 |
| Newly reached Test Message BRs | 42 |
| Test Message BRs with a lead from an AI BR having a clean physical chain | 127 / 243 |
| Newly reached Test Message BRs with that AI-side chain support | 31 |
| Test Message BRs still without a retained lead | 97 |
| AI Message BRs with any retained lead | 45 / 97 |
| AI Message BRs still without a retained lead | 52 |
| AI Message BRs with clean physical chains, regardless of match leads | 62 / 97 |
| Test Message BRs with structural chains | 243 / 243 |
| Test Message BRs with review-placeholder chains | 243 / 243 |
| Test Message BRs with executable payload data in this package | 0 / 243 |
| Approved semantic pairs / certified complete-chain matches | 0 / 0 |

These reach counts are retrieval results, not semantic coverage. No rule was changed, approved, or added to either baseline. A lead can overlap only part of a composite BR or contain unreviewed applicability differences. Counts are distinct BRs, not sums of pairs.

## Representative Additional Leads

AI IDs below use prefix `REQ-SRC-ATL105-PDF-001:`. Test source-rule IDs resolve to package BR IDs with prefix `BR-RULE-`.

| AI BR | Test BR | BR-level comparison | AI chain evidence and remaining gap |
|---|---|---|---|
| :1275 | SEG118-R-003 | Close wording: Segment 118 maximum 3,800 alphanumeric characters in a response. | SC-6649 -> TC-007517 -> hash-verified payload/metadata. The case transaction is Proprietary Data Load Request, not the response scope in the BR. Its assertion says maximum 3,800; no independent boundary/response outcome agreement is established. |
| :1000 | SEG135-R-001 | Close condition: Moneris Request Segment required only for transactions destined for the Moneris authorizer. | SC-6374 -> TC-006776 / TC-006777 -> clean physical routes. Sample assertion only says Segment 135 required; this does not establish both Moneris and non-Moneris applicability branches. |
| :5966 | SEG148-R-001 | Shared WEX product/fleet information in Financial Transaction response. Test title adds WEX-only applicability. | SC-11340 exists, but no clean linked test case/data route is credited. BR-level lead only. |
| :1253 | SEG112-R-004 | Shared Additional Information Segment at end of Financial Transaction response. AI adds BUYPASS origin; Test uses only-at-end wording. | SC-6627 exists, but no clean linked test case/data route is credited. Ordering and exclusivity need evidence. |
| :3688 | SEG108-R-002 | Close requirement that Loyalty Request includes required Segment 108. | SC-9062 exists, but no clean linked test case/data route is credited. Presence/omission cases and independent expected results are needed. |

All Test counterparts above have structural review-placeholder chains, not executable independent fixtures. Full original BR/scenario/case/data records and statuses are in `test-chain-evidence.jsonl`. AI catalog occurrences, assertions, physical paths, hashes and observed structure scalars are in `ai-chain-evidence.jsonl.gz`.

## Existing Candidate That Overstates Coverage

AI :1907 -> SC-7281 -> clean cases TC-009116, TC-009117, TC-009118, TC-009119 -> payload/metadata. It is the source of all 12 existing Message-to-Message pairs. The AI BR describes four-digit Segment Length across seven segments. Its generic assertion says Segment Length must be four digits.

- Seven pairs have a format-related Test obligation: SEG103-R-004, SEG114-R-004, SEG115-R-004, SEG118-R-007, SEG120-R-002, SEG130-R-003, SEG131-R-004. Applicability, encoded-content inclusion, zero-padding and conditional EBT/eWIC details are not established merely by sharing element 84.
- Five pairs instead concern maximum capacity: SEG103-R-005, SEG114-R-005, SEG115-R-005, SEG130-R-004, SEG131-R-005. They are flagged as format-versus-capacity scope gaps, not counted as equivalent obligations.
- No Segment Length scalar was observed in the four clean case payloads. Thus payload presence and a textual assertion are not proof that four-digit length or encoded-length calculations are exercised.
- TC-009120 appears in the live case catalog but is not credited by the v6 clean physical-chain set. It is not silently added as another verified case.

## Complete-Chain Comparison Gates

1. BR: source segment/entity context and statement overlap produce a proposed pairing; source interpretation and authorized decisions remain open.
2. Scenario: original scenario records and links are retained; generic restatements do not establish applicability, alternate branches or negative isolation.
3. Test case: assertions and producer expected responses are retained for inspection; producer confidence/proxy approval is not an independent Test Solution oracle.
4. Test data: 6,754 distinct candidate payload/metadata pairs across the expanded evidence scope hash-match the frozen inventory; 126 declared pairs are missing/incomplete and remain gaps. Every credited clean case has at least one complete hash-verified route (5,719 BR-to-case occurrences); incomplete alternative routes are not treated as full failures of otherwise complete IDs.
5. Test-side substance: structural placeholder IDs are not fixtures or executable checks. None of the 243 Message Test BR chains has executable payload data in the selected complete package.
6. Semantic approval, wire agreement and host execution: not established. Zero certified matches means this review has not certified equivalence, not that the two systems share no behavior.

## Review Priority

Review close obligations with correct segment and transaction scope first, then derive independent Test fixtures and expected outcomes before comparing execution. Use the 42 newly reached Test BRs as a review queue, not an automatic coverage gain. Keep the 97 unreached Test BRs and 52 unreached AI Message BRs visible. A larger heuristic score is never an approval or a guarantee that no other semantic match exists.