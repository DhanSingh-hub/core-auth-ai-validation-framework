# Detailed Message BR and Complete-Chain Review

This extends the v3 review by including alternative authored Test BR chains with identical full source anchors, not only the primary BR-RULE chains. All 97 AI Message BRs and 243 Test Message BRs are retained. The 23,571 Message-to-Message combinations are supplemented by comparisons of every Test Message BR to all 6,880 AI BRs, and every AI Message BR to all 605 Test BRs, so category differences do not hide possible overlap.

| Measure | Result |
|---|---:|
| Existing candidate pairs touching Message | 240 |
| Existing Message-to-Message pairs | 12 |
| Additional context review leads, including cross-category | 809 |
| Additional Message-to-Message leads | 32 |
| Test Message BRs with existing candidates | 104 / 243 |
| Test Message BRs with any retained lead | 146 / 243 |
| Newly reached Test Message BRs | 42 |
| Test Message BRs with a lead from an AI BR with a clean physical chain | 127 / 243 |
| Newly reached Test Message BRs with that AI-side support | 31 |
| Test Message BRs still without a retained lead | 97 |
| AI Message BRs with any retained lead | 45 / 97 |
| AI Message BRs still without a retained lead | 52 |
| AI Message BRs with clean physical chains | 62 / 97 |
| Test Message BRs with structural, review-placeholder primary chains | 243 / 243 |
| Test Message BRs with exact-full-anchor authored alternatives | 41 / 243 |
| Test Data records in those alternatives | 58, all REVIEW_REQUIRED |
| Test Message rules with executable payload data in primary or exact-anchor alternative chains | 0 / 243 |
| Approved semantic pairs / certified complete-chain matches | 0 / 0 |

Reach means proposed BR pairs, not semantic coverage or executed tests. This is the widest queue found under the documented retrieval constraints, not a proven maximum semantic match. Additional leads require shared message subject and explicit segment context; obvious fixed Segment Type contradictions and format-versus-capacity gaps are excluded from new leads. Original candidates are preserved with risk flags. Category assignments remain review proposals.

## Strong New BR Leads and Their Chain Limits

AI IDs use prefix REQ-SRC-ATL105-PDF-001:. Test rule IDs resolve to BR-RULE- package IDs. All records and alternative chains are included in the evidence ledgers.

| AI BR | Test BR | Proposed overlap | Complete-chain assessment |
|---|---|---|---|
| :1275 | SEG118-R-003 | Both describe Segment 118 maximum 3,800 alphanumeric characters in a response. | AI SC-6649 -> TC-007517 -> hash-verified payload/metadata. The case uses Proprietary Data Load Request, not the stated response scope. Assertion says maximum 3,800; independent boundary/response agreement remains unestablished. |
| :1000 | SEG135-R-001 | Moneris Request Segment required only for Moneris-authorizer transactions. | AI SC-6374 -> TC-006776 / TC-006777 -> clean physical routes. Sample assertion only says Segment 135 required; both Moneris and non-Moneris applicability branches are not established. |
| :5966 | SEG148-R-001 | WEX product/fleet information in Financial Transaction response; Test adds WEX-only applicability. | AI SC-11340 exists but has no credited clean case/data route. BR-level lead, not complete-chain agreement. |
| :1253 | SEG112-R-004 | Additional Information Segment at end of Financial Transaction response. AI adds BUYPASS origin; Test uses only-at-end wording. | AI SC-6627 exists but has no credited clean case/data route. Ordering and exclusivity need evidence. |
| :3688 | SEG108-R-002 | Loyalty Request includes required Segment 108. | AI SC-9062 exists but has no credited clean case/data route. Presence/omission cases and independent outcomes are needed. |

## Broad Existing Candidate: Segment Length

AI :1907 -> SC-7281 -> clean cases TC-009116, TC-009117, TC-009118, TC-009119 -> payload/metadata. This one AI BR supplies all 12 existing Message-to-Message pairs.

- Seven Test obligations concern format: SEG103-R-004, SEG114-R-004, SEG115-R-004, SEG118-R-007, SEG120-R-002, SEG130-R-003, SEG131-R-004. Applicability, encoded-content inclusion, zero-padding and EBT/eWIC conditions are not established by shared element 84.
- Five Test obligations concern maximum capacity instead: SEG103-R-005, SEG114-R-005, SEG115-R-005, SEG130-R-004, SEG131-R-005. They are flagged as format-versus-capacity gaps, not equivalent obligations.
- No Segment Length scalar was observed in the four clean payloads. A generic assertion and physical presence do not prove that four-digit format or encoded-length calculations are exercised.
- TC-009120 appears in the live catalog but is not credited by the frozen v6 clean physical-chain set.

## Chain Comparison Gates

BR source context and statement overlap generate leads only. Original scenarios and cases are retained; restatements, inferred assertions, producer response templates and confidence/proxy approval do not substitute for independent expected outcomes. The review does not adjudicate source meanings or grant pair decisions.

Across the expanded evidence scope, 6,754 unique payload/metadata pairs verify against the frozen inventory; 126 declared pairs are missing/incomplete. Every credited clean case has at least one complete hash-verified route, totaling 5,719 BR-to-case occurrences. Incomplete alternative declarations remain visible. Observed scalars are not wire/encoded-length validation.

Primary Test chains contain placeholders. Exact-anchor alternatives are retained only on equality of specification, version, section, segment, element and rule; equal anchors establish context/provenance, not semantic equivalence or complete implementation of a composite obligation. Their 58 Test Data records remain REVIEW_REQUIRED. Neither chain form supplies executable payload data under the package contract.

Semantic approval, wire agreement and host execution are not established. Zero certified pairs means this analysis has not certified equivalence, not that shared behavior is absent. The 42 newly reached Test BRs are a review queue, not an automatic coverage improvement.

## Deliverables

- message-candidate-pairs.csv: 1,049 existing/additional review pairs, statements, source-context comparisons, chain limits and exact-anchor alternatives.
- message-to-message-all-pairs.csv: all 23,571 combinations, including unreached/rejected leads.
- ai-message-br-review-register.csv and test-message-br-review-register.csv: every Message BR, including rows without leads.
- ai-chain-evidence.jsonl.gz and test-chain-evidence.jsonl: original linked records, statuses, fixtures, assertions, paths and hashes for both sides.
- summary.json and manifest.json: source/output hashes and reconciled populations.

Review close obligations with correct transaction and segment scope first, then independently complete Test fixtures and expected outcomes before comparing execution. Keep the 97 unreached Test BRs and 52 unreached AI Message BRs visible.