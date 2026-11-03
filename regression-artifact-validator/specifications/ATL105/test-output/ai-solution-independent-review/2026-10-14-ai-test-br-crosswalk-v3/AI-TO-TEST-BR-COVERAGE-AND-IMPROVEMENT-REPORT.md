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

The same candidate map also extends across single and multileg transaction flows. All listed cases have physical payloads with matching test-case and scenario metadata.

| Flow group | Cases | Cases linked to candidate BR | Distinct Test BR candidates | Payload and metadata present |
|---|---:|---:|---:|---:|
| Single Sale-labeled | 565 | 58 (10.27%) | 34 | 565 / 565 |
| Multileg Sale role | 60 | 1 (1.67%) | 1 | 60 / 60 |
| Multileg Completion role | 138 | 8 (5.80%) | 6 | 138 / 138 |

Case-link percentages count cases with at least one source-anchor candidate; they are not percentages of all Test BRs for a flow. The independent baseline has no flow-specific BR denominator. Flow groups overlap when a multileg case combines stages, such as Completion followed by Void. Candidate links remain subject to semantic review.

## Next Milestone: 90%

At a fixed denominator of 605, 90% requires at least **545 distinct Test BRs**. Candidate reach therefore needs **197 additional distinct Test BRs** beyond the current 348. The complete-chain measure needs **237 additional Test BRs** beyond the current 308 to reach 545.

These targets are structural milestones. Build toward them with source-backed requirements and complete test evidence while keeping matching rules and the independent denominator stable. Continue to report candidate reach, complete-chain reach, semantic agreement, and executed coverage separately.

## Transaction BR Coverage

This count groups BRs explicitly anchored to Prompt Code (Element 78) and BRs whose titles/source rules identify transaction lifecycle behavior. The combined row deduplicates BRs that appear in both groups.

| BR scope | AI BRs in scope | Test BRs in scope | Test BRs with AI candidates |
|---|---:|---:|---:|
| Prompt Code / Element 78 | 142 | 14 | 14 |
| Lifecycle-related BRs | 329 | 31 | 12 |
| **Combined, deduplicated** | **453** | **41** | **26** |

The two scopes overlap: four Test BRs and 18 AI BRs appear in both. All 41 Test BRs in this screened scope are `DRAFT_REVIEW_REQUIRED`; candidate links are not semantic approval. Per-code and per-flow BR coverage is not separately measurable from these catalogs because they lack consistent normalized transaction-code and flow identifiers.

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
- [Shared category summary](../2026-10-15-ai-test-br-category-groups-v1/category-coverage-summary.md)
- [Grouped AI and Test BR register](../2026-10-15-ai-test-br-category-groups-v1/categorized-business-requirements.csv)
- [Scenario card-brand/network proxy groups](../2026-10-15-ai-scenario-network-groups-v1/category-coverage-summary.md)


1. Review the segment queues in the priority order above, starting with source-backed, exact candidate pairs and then resolving ambiguous/composite and legacy-anchor pairs individually.
2. Record each pair decision as `MATCH`, `PARTIAL`, `NOT_MATCH`, or `NOT_APPLICABLE`, with reviewer authority, source quote/anchor, rationale, and affected BR/TS/TC/TD IDs. Keep undecided pairs `REVIEW_REQUIRED`.
3. Route the 40 candidate-reached Test BRs without a clean complete physical route through the separate chain-gap queue; candidate reach is not physical-chain completion.
4. Retain **57.52% (348 / 605)** as candidate reach for the latest catalogued view and **57.24% (344 / 601)** for the prior source-derived view. These are separate baselines, not a trend.
5. Publish semantic approval and executed validation only after their independent decision and execution evidence gates pass. Both remain `NOT_ASSESSED` today.