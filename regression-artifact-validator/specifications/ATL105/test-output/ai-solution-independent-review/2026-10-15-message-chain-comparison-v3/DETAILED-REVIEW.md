# Detailed Message Category Cross-Matching

## Scope and Result

All 97 AI Message BRs and 243 Test Message BRs are retained. All 23,571 Message-to-Message combinations were scored for review retrieval. Each AI Message BR was also compared to all 605 Test BRs, and every Test Message BR to all 6,880 AI BRs, to avoid category-label blind spots. Category assignments remain proposals.

| Measure | Result |
|---|---:|
| Existing v3 anchor pairs touching Message on either side | 240 |
| Existing Message-to-Message pairs | 12 |
| Additional context review leads, including cross-category | 809 |
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

These reach counts are retrieval results, not semantic coverage. No rule was approved or added to either baseline. A lead can overlap only part of a composite BR or contain unreviewed applicability differences. Counts are distinct BRs, not sums of pairs. This is the widest review queue produced by the stated retrieval rules, not a proven maximum semantic match.

## Representative Additional Leads

AI IDs use prefix `REQ-SRC-ATL105-PDF-001:`. Test source-rule IDs resolve to package BR IDs with prefix `BR-RULE-`.

| AI BR | Test BR | BR comparison | AI chain and remaining gap |
|---|---|---|---|
| :1275 | SEG118-R-003 | Close wording: Segment 118 maximum 3,800 alphanumeric characters in a response. | SC-6649 -> TC-007517 -> hash-verified payload/metadata. The case uses Proprietary Data Load Request, not the response scope. Its assertion says maximum 3,800; independent boundary/response outcome agreement is not established. |
| :1000 | SEG135-R-001 | Close condition: Moneris Request Segment required only for transactions destined for the Moneris authorizer. | SC-6374 -> TC-006776 / TC-006777 -> clean physical routes. Sample assertion only says Segment 135 required; both Moneris and non-Moneris branches are not established. |
| :5966 | SEG148-R-001 | Shared WEX product/fleet information in Financial Transaction response; Test adds WEX-only applicability. | SC-11340 exists but has no credited clean case/data route. BR-level lead only. |
| :1253 | SEG112-R-004 | Shared Additional Information Segment at end of Financial Transaction response. AI adds BUYPASS origin; Test uses only-at-end wording. | SC-6627 exists but has no credited clean case/data route. Ordering and exclusivity need evidence. |
| :3688 | SEG108-R-002 | Close requirement that Loyalty Request includes required Segment 108. | SC-9062 exists but has no credited clean case/data route. Presence/omission cases and independent outcomes are needed. |

All Test counterparts above have review-placeholder chains, not executable independent fixtures. Original Test BR/scenario/case/data records are in `test-chain-evidence.jsonl`. AI source records, assertions, paths, hashes and observed structure scalars are in `ai-chain-evidence.jsonl.gz`. Both review registers retain every Message BR, including those without a lead.

## Existing Candidate That Overstates Coverage

AI :1907 -> SC-7281 -> clean cases TC-009116, TC-009117, TC-009118, TC-009119 -> payload/metadata. It supplies all 12 existing Message-to-Message pairs. Its broad obligation is four-digit Segment Length across seven segments; the generic case assertion says Segment Length must be four digits.

- Seven pairs have format-related Test obligations: SEG103-R-004, SEG114-R-004, SEG115-R-004, SEG118-R-007, SEG120-R-002, SEG130-R-003, SEG131-R-004. Applicability, encoded-content inclusion, zero-padding and EBT/eWIC conditions are not established by sharing element 84.
- Five pairs concern maximum capacity instead: SEG103-R-005, SEG114-R-005, SEG115-R-005, SEG130-R-004, SEG131-R-005. These are flagged as format-versus-capacity gaps, not equivalent obligations.
- No Segment Length scalar was observed in the four clean case payloads. Physical presence plus a textual assertion does not prove four-digit format or encoded-length calculations are exercised.
- TC-009120 appears in the live catalog but is not credited by the frozen v6 clean physical-chain set.

## Chain Gates and Evidence

1. BR: explicit context and statement overlap generate review leads, not source interpretation or authorized decisions.
2. Scenario: original links are retained; a generic restatement does not establish applicability, alternative branches or negative isolation.
3. Case: producer assertions and responses are retained; producer confidence or proxy approval is not an independent Test Solution oracle.
4. Data: 6,754 unique payload/metadata pairs across the expanded scope match frozen hashes; 126 declared pairs are missing/incomplete. Every credited clean case has at least one complete hash-verified route, totaling 5,719 BR-to-case occurrences. Incomplete alternate routes remain visible. Scalar observations are not wire-length validation.
5. Test-side substance: all 243 Message chains contain review placeholders, and none has executable payload data in the selected package.
6. Semantic approval, wire agreement and host execution remain unestablished. Zero certified pairs means this review has not certified equivalence, not that shared behavior is absent.

## Review Priority

Review close obligations with correct transaction/segment scope first, then derive independent Test fixtures and expected outcomes before comparing execution. Treat the 42 newly reached Test BRs as a review queue, not an automatic coverage gain. Keep the 97 unreached Test BRs and 52 unreached AI Message BRs visible. Retrieval scores are neither confidence nor approval.