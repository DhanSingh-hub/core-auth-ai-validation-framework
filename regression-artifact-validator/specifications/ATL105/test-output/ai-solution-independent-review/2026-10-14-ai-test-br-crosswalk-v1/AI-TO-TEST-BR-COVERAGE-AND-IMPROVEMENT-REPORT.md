# AI-to-Test BR Coverage and Improvement Report

**Audience:** Fiserv Business and Test Solution stakeholders  
**Assessment date:** 2026-10-14  
**AI delivery:** 2026-10-10 artifact set, current source catalog SHA-256 `6b40963dfec26160013f87d47fe1defa3652b414adc4b29b60619cd6d71b6b4a`  
**Independent denominator:** 601 Test Solution business requirements (BRs)

## Executive Summary

The current AI delivery has **source-anchor candidate reach to 344 of 601 independent Test BRs (57.24%)**. The calculation uses exact normalized segment and numeric-element anchors to identify candidate pairs; it does not decide that the AI requirement and Test BR have the same business meaning.

Of the 344 candidate-reached Test BRs, **304 (50.58% of the 601-BR baseline)** are also reachable through an AI BR-to-scenario-to-test-case route with physical test data and matching metadata present. The remaining 40 candidate-reached Test BRs do not have that complete direct evidence route under this check.

**This report does not prove that AI-generated BRs are correct.** All source-anchor pairs remain `NOT_ASSESSED` for semantic equivalence. No SME/business approval or host/runtime execution is established by the crosswalk. The 57.24% figure is a useful screening and prioritization measure, not a pass rate or quality certification.

The previous Oct 10 report recorded **332/601 (55.24%)**. That figure is historical, not a comparable baseline: it recorded a different requirement-catalog SHA-256 (`3cb91370258169970be96ffb5fe4a956be6c5aa018247071efec274737d2fef8`), while the current OneDrive catalog and the file now present in the locally named `2026-10-10/Run1` intake both hash to `6b40963dfec26160013f87d47fe1defa3652b414adc4b29b60619cd6d71b6b4a`. The old source version is not available at that intake path for a controlled rerun. Therefore, do not present the 1.99 percentage-point difference as proven AI improvement; it may include source-version and calculation changes.

## Coverage at a Glance

| Measure | Current result | What it means |
|---|---:|---|
| Independent Test Solution BR denominator | 601 | Fixed comparison baseline from the independent source-derived inventory |
| AI BR catalog | 6,880 | Current approved-catalog records; catalog status is not SME approval |
| Candidate Test BR reach | **344 / 601 (57.24%)** | Primary requested metric: distinct Test BRs with one or more AI source-anchor candidates |
| Candidate pair rows | 1,180 | Pair count; not a unique-BR denominator |
| Candidate Test BRs with complete direct chain | **304 / 601 (50.58%)** | Candidate reach with a linked scenario, test case, and present payload/metadata |
| Candidate-reached Test BRs without complete direct chain | 40 | Follow-up traceability/data evidence gap; not necessarily a missing test |
| Test-case records with complete declared payload and metadata | 21,227 / 21,254 | Structural file/metadata check; does not establish test correctness |
| Test-case records with incomplete payload or metadata | 27 / 21,254 | Requires remediation or explicit disposition |
| Confirmed semantic matches / SME-approved coverage | **NOT_ASSESSED** | No semantic coverage credit is granted by this report |

The AI producer's traceability matrix separately reports 72.15% full-chain linkage within its own AI catalogs. That is a different numerator and denominator; it is **not** the AI-to-independent-Test-BR coverage reported here.

## Candidate Disposition of AI BRs

The following dispositions partition the 6,880 AI BRs under the exact source-anchor lookup. Categories are screening results, not adjudications.

| AI BR disposition | Count | Share of AI BR catalog | Interpretation / next action |
|---|---:|---:|---|
| No numeric element anchor | 2,728 | 39.65% | Add a truthful rule-type/business-scope anchor where applicable; do not invent an element number for process or lifecycle rules |
| Segment unresolved | 3,332 | 48.43% | Resolve segment/message-family context from cited source text and validated entity mappings |
| No exact Test BR source-anchor candidate | 66 | 0.96% | Review whether this is genuinely new scope, an anchor mismatch, or a Test Solution baseline gap |
| Ambiguous or composite candidate | 431 | 6.27% | Split bundled normative claims where appropriate; retain all candidate links for human review |
| Legacy entity-anchor candidate | 323 | 4.70% | Reconcile legacy identifiers to current segment/element anchors; review before accepting |

The no-anchor and unresolved-segment categories total 6,060 AI BRs (88.08%). They are the largest metadata and source-context improvement opportunity, but resolving anchors alone does not establish semantic correctness.

## Segment Achievements

The current candidate map reaches 344 independent Test BRs across the baseline. These segments contribute many of the existing links, with several showing broad reach across their catalogued rules. They provide a strong base for extending validation into adjacent requirements.

| Segment | Independent Test BRs | Candidate-linked | Reach |
|---|---:|---:|---:|
| 100 | 36 | 30 | 83.33% |
| 119 | 40 | 24 | 60.00% |
| 118 | 29 | 21 | 72.41% |
| 102 | 25 | 18 | 72.00% |
| 108 | 24 | 18 | 75.00% |
| 105 | 21 | 17 | 80.95% |
| 109 | 22 | 15 | 68.18% |
| 110 | 20 | 15 | 75.00% |

These are source-anchor candidate rates, not semantic approval. The full Test BR index and candidate-pair register support follow-up review and expansion.

## What 90% Would Require

At a fixed denominator of 601, 90% candidate reach requires at least **541 distinct Test BRs**. From the current 344, that means **197 additional distinct Test BRs** must receive source-anchor candidates. This is a structural target only.

For the stronger direct-chain measure, 90% also requires 541 Test BRs to have a candidate plus a complete direct BR-to-scenario-to-test-case-to-data route. From the current 304, that requires **237 additional complete-chain Test BRs**. Neither target should be described as 90% validated coverage until semantic review and the applicable execution evidence are complete.

Do not reach the target by loosening anchor matching, broadening many-to-many links without evidence, or changing the independent denominator. Keep candidate reach, complete-chain reach, semantic agreement, and execution results as separate metrics.

## Recommended Improvement Plan

### 1. Repair source context and anchors

- For the 3,332 segment-unresolved BRs, require a source-backed segment or message-family classification. Preserve the source page/section and quote that justifies the classification; route genuine ambiguity to review instead of guessing.
- For the 2,728 BRs without numeric element anchors, introduce explicit anchor types for element-level rules, transaction/process rules, response rules, cross-field rules, lifecycle rules, and host/transport rules. A non-element rule should have a valid domain anchor, not a fabricated element number.
- Enforce anchor completeness and validity at generation time: source/version, page/section, segment or rule scope, element when applicable, and the condition that makes the rule apply.

### 2. Make each requirement reviewable

- Express one independently testable normative obligation per BR. Split multi-condition statements when each condition can pass or fail independently; preserve parent/child provenance when splitting.
- Keep the rule's condition, required behavior, exception/negative behavior, and expected result explicit. Distinguish normative text from examples, inferred values, recommendations, and process descriptions.
- Preserve confidence and extraction flags in review queues. The intake analysis found 3,849/6,880 BR records and 9,255/12,254 scenario records with at least one flag; flags should trigger targeted review, not disappear during approval.

### 3. Close the 66 exact-anchor misses and the ambiguous candidates

- Review the 66 AI BRs that have usable anchors but no exact candidate in the 601-rule baseline. For each, record whether it is out of scope, genuinely new to the Test Solution, anchored to the wrong segment/element, or evidence of a baseline omission.
- Triage all 431 ambiguous/composite and 323 legacy-anchor AI BR candidates. Confirm the intended Test BR relationship at the pair level; one AI BR may legitimately map to multiple atomic Test BRs, but each link needs a reason.
- Add or change an independent Test BR only through the existing independent-source and approval process. Do not modify the denominator just to improve the percentage.

### 4. Strengthen the complete test chain

- Require every in-scope AI BR to link to one or more scenarios that explicitly name the BR ID and exercise its complete condition, not just shared fields or a generated title.
- Require each linked test case to contain an explicit assertion and expected outcome tied to the BR. Mark values inferred by the model as inferred; do not treat them as authoritative oracles.
- Require test-data declarations to resolve to payload and metadata whose test-case and scenario identities match. The current check found 27 of 21,254 records incomplete; remediate or disposition each.
- Keep source-anchor matching, artifact integrity, semantic adjudication, and runtime/host execution as separate validation gates.

### 5. Use a staged business review

1. Business SMEs build from established candidate reach across Segments 100, 105, 108, 118, and 119, then review adjacent requirements and ambiguous or flagged candidate pairs.
2. Test Solution owners record pair decisions as `MATCH`, `PARTIAL`, `NOT_MATCH`, or `NOT_APPLICABLE`, with rationale and source evidence. Keep unresolved items `REVIEW_REQUIRED`.
3. Test owners verify scenario intent, assertions, expected outcomes, and linked payloads for accepted pairs.
4. Recompute the same 601-BR metrics from a hash-frozen catalog and publish candidate, complete-chain, semantic-approved, and executed coverage side by side.

## Evidence and Limitations

- Current source folder: `C:\Users\F5H46GZ\OneDrive - Fiserv Corp\Documents\nitin verma\AI Solutions Output\10_oct_artifacts 3\10_oct_artifacts`.
- Current AI BR catalog SHA-256: `6b40963dfec26160013f87d47fe1defa3652b414adc4b29b60619cd6d71b6b4a`.
- The current source file and the local `2026-10-10/Run1` catalog have the same SHA-256 and 6,880 records. The earlier report's catalog hash differs; the historical 55.24% result is therefore retained as context, not a like-for-like trend point.
- Independent denominator: `source-derived-requirement-inventory.json`, 601 rules; AI artifacts are excluded from this baseline.
- Matching policy: exact normalized segment plus numeric element anchor; all pairs remain review-required. See [crosswalk summary](crosswalk-summary.json), [AI-to-Test BR candidate pairs](ai-test-br-candidate-pairs.csv), and [Test BR candidate index](test-br-ai-br-index.csv).
- The exported pair files establish candidate links, not correctness of requirement wording, complete behavioral coverage, test pass status, SME approval, or production/host behavior.

## Decision Requested

Approve a targeted business review of the 11 priority segment cohorts and the candidate-pair register, and nominate authorized SMEs for the flagged/ambiguous BR queue. Treat **57.24% as current candidate reach**, not certified correctness. Rebaseline the historical comparison after the prior `3CB913…` source version is recovered or formally mark the 55.24% report as a non-comparable earlier scan.