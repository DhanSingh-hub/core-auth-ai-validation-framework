# AI-to-Test BR Coverage and Improvement Report

**Audience:** Fiserv Business and Test Solution stakeholders  
**Assessment date:** 2026-10-14  
**AI delivery:** 2026-10-10 artifact set; requirement catalog SHA-256 `6b40963dfec26160013f87d47fe1defa3652b414adc4b29b60619cd6d71b6b4a`  
**Latest Test BR universe:** 605 catalogued rules

## Executive Summary

The current AI delivery provides source-anchor candidates for **348 of the latest 605 catalogued Test BRs (57.52%)**. The crosswalk provides **1,208 candidate pairs** as a concrete, searchable starting point for review. **308 of 605 (50.91%)** also have a direct AI BR-to-scenario-to-test-case route with physical test data and matching metadata present.

The specification update expanded the catalogued Test BR baseline from 601 to 605 rules. The latest view has 57.52% candidate reach; the prior 601 source-derived view has 344/601 (57.24%) candidate reach and 304/601 (50.58%) direct-chain reach. These are separate baseline views, not an improvement trend.

For comparison with the prior source-derived inventory, candidate reach is **344/601 (57.24%)**, with **304/601 (50.58%)** direct-chain reach.

**Progress already in place:** 21,227 of 21,254 test cases have payload and matching metadata present (**99.87%** under the structural check), providing a strong base for focused business review.

**Validation boundary:** candidate links do not prove that an AI-generated BR is correct. All pairs remain `NOT_ASSESSED` for semantic equivalence; SME/business approval, full test adequacy, and host/runtime execution are not established. Treat candidate reach as a review-prioritization measure, not a pass rate or certification.

## Progress at a Glance

| Measure | Latest 605-rule view | Prior 601-rule view | Meaning |
|---|---:|---:|---|
| Test BR denominator | 605 catalogued rules | 601 source-derived rules | Separate catalog views |
| AI BR catalog | 6,880 | 6,880 | Catalog status is not SME approval |
| Distinct Test BRs with candidates | **348 / 605 (57.52%)** | **344 / 601 (57.24%)** | Primary reach measure; candidate links only |
| Candidate pair rows | 1,208 | 1,180 | Pair count, not a unique-BR count |
| Distinct Test BRs with complete direct chain | **308 / 605 (50.91%)** | **304 / 601 (50.58%)** | Requires linked scenario/case plus payload and matching metadata |
| Test cases with complete payload and metadata | 21,227 / 21,254 | Same intake | Structural file/metadata check only |
| Semantic equivalence / SME-approved coverage | **NOT_ASSESSED** | **NOT_ASSESSED** | No semantic coverage credit is granted |

The producer traceability matrix's **72.15%** is internal AI-catalog linkage using a different numerator and denominator. It is not AI-to-independent-Test-BR coverage.

## Segment Achievements

The current candidate map reaches 348 catalogued Test BRs across 49 segments. These high-contribution segments show where source-anchor alignment is already working and provide a foundation for expanding validation into adjacent requirements.

| Segment | Catalogued Test BRs | Candidate-linked | Reach |
|---|---:|---:|---:|
| 100 | 36 | 30 | 83.33% |
| 119 | 40 | 24 | 60.00% |
| 118 | 29 | 21 | 72.41% |
| 102 | 26 | 19 | 73.08% |
| 108 | 24 | 18 | 75.00% |
| 105 | 21 | 17 | 80.95% |
| 109 | 22 | 15 | 68.18% |
| 110 | 20 | 15 | 75.00% |

These are source-anchor candidate rates, not semantic approval. The linked Test BR index and candidate-pair register provide a practical basis for business review and continued expansion.

## Next Milestone: 90%

At a fixed denominator of 605, 90% requires at least **545 distinct Test BRs**. Candidate reach therefore needs **197 additional distinct Test BRs** beyond the current 348. The complete-chain measure needs **237 additional Test BRs** beyond the current 308 to reach 545.

These targets are structural milestones. Build toward them with source-backed requirements and complete test evidence while keeping matching rules and the independent denominator stable. Continue to report candidate reach, complete-chain reach, semantic agreement, and executed coverage separately.

## Candidate Map and Enrichment Opportunities

These mutually exclusive dispositions partition the 6,880 AI BRs under the exact normalized segment-plus-numeric-element matcher. They identify candidate/review states, not semantic decisions.

| Disposition | AI BRs | Share | Opportunity |
|---|---:|---:|---|
| No numeric element anchor | 2,728 | 39.65% | Add a source-backed rule-type/business-scope anchor where an element does not apply; never invent an element |
| Segment unresolved | 3,332 | 48.43% | Resolve segment/message-family scope from source evidence and validated entity mappings |
| No exact Test BR candidate | 42 | 0.61% | Review for new scope, an anchor mismatch, or a possible Test Solution baseline gap |
| Ambiguous/composite candidates | 455 | 6.61% | Atomize independent obligations where appropriate; review pair-level mappings |
| Legacy entity-anchor candidates | 323 | 4.70% | Reconcile old entity references to current segment and element anchors |

The 6,060 AI BRs with no numeric element anchor or unresolved segment context are the largest opportunity to improve findability. For process, response, and lifecycle requirements, use suitable domain anchors rather than forcing an element number. Better traceability supports review; it does not by itself prove requirement correctness.

## Recommended Improvement Plan

### 1. Improve source-context capture

- Resolve segment or message-family context for the 3,332 unresolved AI BRs using source page/section and quote evidence. Send genuine ambiguity to review rather than guessing.
- For the 2,728 BRs without numeric element anchors, support explicit rule types such as transaction/process, response, cross-field, lifecycle, and host/transport. Use domain anchors for non-element rules.
- Validate source version, page/section, quote, segment/rule scope, element when applicable, and applicability condition at generation time.

### 2. Make BRs testable and atomic

- Express one independently testable normative obligation per BR. Split conditions that can independently pass or fail, preserving parent/child provenance.
- State preconditions, required behavior, exceptions, and expected outcome. Distinguish normative text from examples, inferred values, recommendations, and process descriptions.
- Keep extraction confidence and review flags visible; producer catalog status alone is not SME approval.

### 3. Adjudicate candidate gaps

- Review the 42 exact-anchor misses for scope, incorrect anchors, new requirements, or independent-baseline omissions.
- Triage the 455 ambiguous/composite and 323 legacy-anchor candidates at the pair level. Record a rationale for every accepted one-to-many mapping.

### 4. Strengthen the BR-to-test evidence chain

- Require scenarios to name linked BR IDs and exercise the complete condition, not only shared fields or generated titles.
- Require test cases to include explicit assertions and expected outcomes tied to each BR. Keep inferred values separate from authoritative oracles.
- Maintain payload and matching test-case/scenario metadata as new cases are added; verify each scenario exercises its linked BRs.
- Keep source extraction, artifact integrity, semantic review, and host/runtime execution as separate validation gates.

### 5. Establish a repeatable business review

1. Business SMEs review the segment-priority list and flagged/ambiguous candidate pairs.
2. Test Solution owners record `MATCH`, `PARTIAL`, `NOT_MATCH`, or `NOT_APPLICABLE` with rationale and source evidence; unresolved pairs remain `REVIEW_REQUIRED`.
3. Test owners verify scenario intent, assertions, expected outcomes, and linked payloads for accepted pairs.
4. Recompute the 601 source-derived and 605 catalogued views from hash-frozen inputs; add a separate semantic-approved and executed measure only when their evidence gates pass.

## Supporting Files

- [Crosswalk summary](crosswalk-summary.json)
- [AI-to-Test BR candidate pairs](ai-test-br-candidate-pairs.csv)
- [Test BR candidate index](test-br-ai-br-index.csv)
- [AI BRs without candidates](ai-brs-without-test-br-candidates.csv)

## Business next steps

Use the priority queue below to organize authorized pair review. Segment counts are source-anchor attributions; pair rows and segment denominators can overlap when a Test BR has cross-segment anchors. They must not be summed into a new overall coverage denominator.

| Priority | Segment | Test BRs with candidates | Candidate pairs (ambiguous/composite; legacy anchor) | Existing open SME queries | SME role to nominate |
|---:|---:|---:|---:|---|---|
| 1 | 119 Totals with Proprietary Data Load | 24 / 40 (60.00%) | 29 (9; 20) | `SEG119-SME-001`–`009` | Totals, settlement, and reconciliation SME |
| 2 | 101 Fleet | 14 / 30 (46.67%) | 52 (6; 46) | `SEG101-SME-001`–`010` | Fleet/fuel-card operations SME |
| 3 | 103 EBT | 14 / 26 (53.85%) | 67 (61; 6) | `SEG103-SME-007`–`009` | EBT/WIC business and processor SME |
| 4 | 130 EMV Request Data | 13 / 24 (54.17%) | 45 (40; 5) | `SEG130-SME-002`–`005`, `007`–`014` | EMV/issuer/acquirer security SME |
| 5 | 114 Loyalty Card Data | 4 / 14 (28.57%) | 12 (11; 1) | `SEG114-SME-002`, `006` | Loyalty/card-program SME |
| 6 | 118 Proprietary Data Load | 21 / 29 (72.41%) | 82 (71; 11) | `SEG118-SME-001`–`005` | Host/proprietary-data-load SME |

The register currently lists these queries under `SME/TBA`; no individual nominees are recorded. The business owner should assign a named, authorized reviewer for each role before pair decisions are accepted.

1. Review the segment queues in the priority order above, starting with source-backed, exact candidate pairs and then resolving ambiguous/composite and legacy-anchor pairs individually.
2. Record each pair decision as `MATCH`, `PARTIAL`, `NOT_MATCH`, or `NOT_APPLICABLE`, with reviewer authority, source quote/anchor, rationale, and affected BR/TS/TC/TD IDs. Keep undecided pairs `REVIEW_REQUIRED`.
3. Route the 40 candidate-reached Test BRs without a clean complete physical route through the separate chain-gap queue; candidate reach is not physical-chain completion.
4. Retain **57.52% (348 / 605)** as candidate reach for the latest catalogued view and **57.24% (344 / 601)** for the prior source-derived view. These are separate baselines, not a trend.
5. Publish semantic approval and executed validation only after their independent decision and execution evidence gates pass. Both remain `NOT_ASSESSED` today.