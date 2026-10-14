# AI-to-Test BR Coverage and Improvement Report

**Audience:** Fiserv Business and Test Solution stakeholders  
**Assessment date:** 2026-10-14  
**AI delivery:** 2026-10-10 artifact set; requirement catalog SHA-256 `6b40963dfec26160013f87d47fe1defa3652b414adc4b29b60619cd6d71b6b4a`  
**Latest Test BR universe:** 605 catalogued rules; package status `COMPLETE_STRUCTURE_REVIEW_REQUIRED`

## Executive Summary

The current AI delivery provides source-anchor candidates for **348 of the latest 605 catalogued Test BRs (57.52%)**. The crosswalk provides **1,208 candidate pairs** as a concrete, searchable starting point for review. **308 of 605 (50.91%)** also have a direct AI BR-to-scenario-to-test-case route with physical test data and matching metadata present.

The latest universe consists of the prior **601 source-derived rules plus four newly catalogued rules**: `SEG102-R-026`, `SEG123-R-012`, `SEG156-R-008`, and `SEG156-R-009`. All four are `DRAFT_REVIEW_REQUIRED`; they are not approved rules. Each receives at least one source-anchor candidate from the AI catalog. Therefore, the 605 denominator is useful as a current catalog-wide view, but it is **not a 605-approved-BR baseline**.

For like-for-like comparison with the prior source-derived inventory, the current result is **344/601 (57.24%)**, with **304/601 (50.58%)** direct-chain reach. All four added Test BRs have candidates, raising the catalog-wide view to 57.52%; because they remain drafts, this is not proof of improved AI generation.

**Progress already in place:** all four newly catalogued Test BRs have at least one AI source-anchor candidate, and 21,227 of 21,254 test cases have payload and matching metadata present (**99.87%** under the structural check). These signals provide a strong base for focused business review.

**Validation boundary:** candidate links do not prove that an AI-generated BR is correct. All pairs remain `NOT_ASSESSED` for semantic equivalence; SME/business approval, full test adequacy, and host/runtime execution are not established. Treat candidate reach as a review-prioritization measure, not a pass rate or certification.

## Progress at a Glance

| Measure | Latest 605-rule view | Prior 601-rule view | Meaning |
|---|---:|---:|---|
| Test BR denominator | 605 catalogued rules | 601 source-derived rules | Latest package adds four draft rules |
| AI BR catalog | 6,880 | 6,880 | Catalog status is not SME approval |
| New catalogued Test BRs with AI candidates | 4 / 4 | Not applicable | All four additions are still `DRAFT_REVIEW_REQUIRED` |
| Distinct Test BRs with candidates | **348 / 605 (57.52%)** | **344 / 601 (57.24%)** | Primary reach measure; candidate links only |
| Candidate pair rows | 1,208 | 1,180 | Pair count, not a unique-BR count |
| Distinct Test BRs with complete direct chain | **308 / 605 (50.91%)** | **304 / 601 (50.58%)** | Requires linked scenario/case plus payload and matching metadata |
| Test cases with complete payload and metadata | 21,227 / 21,254 | Same intake | Structural file/metadata check only |
| Test cases with incomplete payload or metadata | 27 / 21,254 | Same intake | Remediate or explicitly disposition |
| Semantic equivalence / SME-approved coverage | **NOT_ASSESSED** | **NOT_ASSESSED** | No semantic coverage credit is granted |

The producer traceability matrix's **72.15%** is internal AI-catalog linkage using a different numerator and denominator. It is not AI-to-independent-Test-BR coverage.

## Baseline Change and Governance

The current independent Test Solution package has `independentRuleDenominator: 605`, matching `training-status.json`'s `totalCataloguedRules: 605`. Its status is `COMPLETE_STRUCTURE_REVIEW_REQUIRED`, and execution readiness is false. The 1,330 BR rows in that package are not the denominator: they include reviewed material, draft additions, and placeholder/aggregate content. The denominator is the 605 distinct `BR-RULE-*` IDs.

The four additions relative to the 601-rule source-derived inventory are:

| Test BR | Source scope | Current status | AI candidate present |
|---|---|---|---|
| `SEG102-R-026` | Segment 102, Element 76 | `DRAFT_REVIEW_REQUIRED` | Yes |
| `SEG123-R-012` | Segment 123, Elements 196/204/238 | `DRAFT_REVIEW_REQUIRED` | Yes |
| `SEG156-R-008` | Segment 156, Elements 85/84 | `DRAFT_REVIEW_REQUIRED` | Yes |
| `SEG156-R-009` | Segment 156, Element 242 | `DRAFT_REVIEW_REQUIRED` | Yes |

These four candidate-linked additions are a positive starting point for independent review. Keep their `DRAFT_REVIEW_REQUIRED` status visible and retain the 601-rule view for continuity until approval is recorded.

The earlier 55.24% report recorded 332/601 but a different AI catalog hash (`3cb913...d2fe8`). The current AI source and local intake copy both hash to `6b4096...b6b4a`; the older source version is unavailable at the intake path for a controlled rerun. Present 55.24% as historical context, not a like-for-like trend point.

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
- Review the four new Test BRs through the independent approval process before calling them approved or using them as a certified denominator.

### 4. Strengthen the BR-to-test evidence chain

- Require scenarios to name linked BR IDs and exercise the complete condition, not only shared fields or generated titles.
- Require test cases to include explicit assertions and expected outcomes tied to each BR. Keep inferred values separate from authoritative oracles.
- Resolve or disposition the 27 test cases with incomplete payload/metadata; require testcase and scenario identity agreement in each metadata join.
- Keep source extraction, artifact integrity, semantic review, and host/runtime execution as separate validation gates.

### 5. Establish a repeatable business review

1. Business SMEs review the segment-priority list and flagged/ambiguous candidate pairs.
2. Test Solution owners record `MATCH`, `PARTIAL`, `NOT_MATCH`, or `NOT_APPLICABLE` with rationale and source evidence; unresolved pairs remain `REVIEW_REQUIRED`.
3. Test owners verify scenario intent, assertions, expected outcomes, and linked payloads for accepted pairs.
4. Recompute the 601 source-derived and 605 catalogued views from hash-frozen inputs; add a separate semantic-approved and executed measure only when their evidence gates pass.

## Evidence and Transparency

- AI BR source: `C:\Users\F5H46GZ\OneDrive - Fiserv Corp\Documents\nitin verma\AI Solutions Output\10_oct_artifacts 3\10_oct_artifacts`.
- AI requirement catalog SHA-256: `6b40963dfec26160013f87d47fe1defa3652b414adc4b29b60619cd6d71b6b4a`.
- Training catalog SHA-256: `640f053f61d0e575468a0cb80381feb0787b3f7bbdbc429333e2c960ed1f12df`.
- Latest canonical package SHA-256: `4c487a68e3223b06ef509016abbc443f0ab6615fd437d147d2a6941132f04dad`.
- Latest package status: `COMPLETE_STRUCTURE_REVIEW_REQUIRED`; four additions are `DRAFT_REVIEW_REQUIRED`; `executionReady` is false.
- Matching method: exact normalized segment plus numeric element anchor. All pairs remain review-required; no semantic match is inferred.
- Supporting files: [crosswalk summary](crosswalk-summary.json), [AI-to-Test candidate pairs](ai-test-br-candidate-pairs.csv), [Test BR candidate index](test-br-ai-br-index.csv), and [AI BRs without candidates](ai-brs-without-test-br-candidates.csv).

## Decision Requested

Build on the 348 candidate-reached rules by prioritizing segments 119, 101, 103, 130, 114, and 118; nominate authorized SMEs to review ambiguous pairs; and advance the four new Test BR drafts through independent review. Present **57.52% as candidate reach across the latest 605 catalogued rules** and retain **57.24% for the prior 601-rule set**. Semantic approval and execution remain separate next milestones.