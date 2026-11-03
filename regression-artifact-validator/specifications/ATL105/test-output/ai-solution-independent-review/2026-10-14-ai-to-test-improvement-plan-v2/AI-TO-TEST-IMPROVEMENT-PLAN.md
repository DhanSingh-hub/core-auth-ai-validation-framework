# AI-to-Test Business Review Plan v2

**Assessment date:** 2026-10-14  
**AI delivery:** 2026-10-10 artifact set  
**Crosswalk:** `2026-10-14-ai-test-br-crosswalk-v3`  
**Status:** Priority review plan; no semantic approvals are implied

## Baseline

The latest catalogued view has **348 of 605 Test BRs with source-anchor candidates (57.52%)**. Retain **344 of 601 (57.24%)** for the prior source-derived view. These are different denominator views, not a trend. The 605 view includes four draft rules; candidate reach is not semantic approval.

The crosswalk has 1,208 candidate pairs. All remain `NOT_ASSESSED` for semantic equivalence. Execution and host validation are separate gates. Reviewers must record pair decisions with source evidence; candidate linkage alone earns no coverage credit.

## Priority SME Queue

| Priority | Segment | Test BRs with candidates | Candidate pairs | Ambiguous/composite | Legacy anchor | Open SME queries | Recommended SME role | Named nominee |
|---:|---|---:|---:|---:|---:|---|---|---|
| 1 | 119 Totals with Proprietary Data Load | 24 / 40 (60.00%) | 29 | 9 | 20 | `SEG119-SME-001`–`009` | Totals, settlement, and reconciliation SME | Pending business-owner assignment |
| 2 | 101 Fleet | 14 / 30 (46.67%) | 52 | 6 | 46 | `SEG101-SME-001`–`010` | Fleet and fuel-card operations SME | Pending business-owner assignment |
| 3 | 103 EBT | 14 / 26 (53.85%) | 67 | 61 | 6 | `SEG103-SME-007`–`009` | EBT/WIC business and processor SME | Pending business-owner assignment |
| 4 | 130 EMV Request Data | 13 / 24 (54.17%) | 45 | 40 | 5 | `SEG130-SME-002`–`005`, `SEG130-SME-007`–`014` | EMV/chip, issuer, or network SME | Pending business-owner assignment |
| 5 | 114 SKU Data (Loyalty request context) | 4 / 14 (28.57%) | 12 | 11 | 1 | `SEG114-SME-002`, `SEG114-SME-006` | Loyalty/SKU product-data SME | Pending business-owner assignment |
| 6 | 118 Proprietary Data Load | 21 / 29 (72.41%) | 82 | 71 | 11 | `SEG118-SME-001`–`005` | Host/processor proprietary-data-load SME | Pending business-owner assignment |

The segment rows use source-anchor attribution. Cross-segment Test BR anchors can appear in more than one row; segment denominators and pair rows are not additive. The communication register currently assigns these questions to `SME/TBA`, not named people. A business owner must confirm an authorized named reviewer for each role before decisions are accepted.

## Business next steps

1. Assign a named, authorized SME for each role above; retain `SME/TBA` until assignment is confirmed.
2. Begin in priority order. Review `candidate-review-queue` pairs at the pair level, with exact source quotes and the relevant segment context. Use `MATCH`, `PARTIAL`, `NOT_MATCH`, or `NOT_APPLICABLE`; unresolved pairs remain `REVIEW_REQUIRED`.
3. Review legacy-anchor pairs separately from ambiguous/composite pairs. Do not normalize a legacy entity ID or split a composite BR without source evidence and a rationale.
4. Route physical-chain gaps separately from semantic pair decisions. A complete declared payload/metadata route is structural evidence, not a valid payload, a correct expected result, or execution.
5. Append authorized decisions to the Test Solution decision register, rerun the crosswalk against hash-frozen inputs, and publish the 605-rule and 601-rule views side by side.
6. Keep semantic-approved coverage and executed-validation coverage `NOT_ASSESSED` until authorized review and execution evidence independently satisfy their gates.

## Evidence

- `../2026-10-14-ai-test-br-crosswalk-v3/crosswalk-summary.json`
- `../2026-10-14-ai-test-br-crosswalk-v3/ai-test-br-candidate-pairs.csv`
- `../2026-10-14-delivery-wide-assessment-v6/test-br-ledger.jsonl`
- `../../../../registers/atl105-communication-register.json`
