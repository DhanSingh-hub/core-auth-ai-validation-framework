# October 5 AI Requirement Coverage Assessment

**2026-10-06 supplement notice:** TC/TD artifacts are now present in an unchanged same-run supplement. The [detailed supplement review](2026-10-06-complete-handoff/DETAILED-REVIEW.md) recounts 21,123 TC candidates, 21,096 logical cases with physical outputs, and 21,210 payloads. This closes receipt of downstream artifacts, not their semantic acceptance or execution certification. The assessment below describes the original BR/TS-only subset; `NOT_CALCULABLE` remains applicable because this review has not established an eligible same-run independent crosswalk and validated full chains.

**Delivery:** One AI pipeline run, `SRC-ATL105-PDF-001`, generated 2026-10-01
**BR denominator:** 6,887 AI requirements
**Disposition:** `NOT_CALCULABLE - REVIEW_REQUIRED`
**Purpose:** Evidence-backed AI BR-to-Test Solution requirement coverage; not the AI producer's own scenario coverage statistic

The separate Test Solution validator-rule/method coverage for this same delivery is recorded in [AI Validation Control Coverage](ai-validation-control-coverage-2026-10-05.md). `AIV-*` controls are not ATL105 BRs and are not included in the BR denominator below.

## Result

| Classification | Count | Interpretation |
|---|---:|---|
| AI business requirements in delivery | 6,887 | Candidate BR records in the October 5 delivery |
| Confirmed AI-to-Test Solution BR matches recorded for this run | 0 | No October 5 crosswalk exists; this is not proof that zero semantic matches exist |
| BRs with no same-run independent crosswalk disposition | 6,887 | `NOT_ASSESSED`; do not report these as confirmed unmatched or AI-only |
| Confirmed AI-only BRs | Not calculable | Requires a completed comparison against an eligible independent Test Solution denominator |
| Independently invalid BRs | Not assessed | No independent semantic BR validation/crosswalk was run |
| AI's own BR verdicts marked `FAIL` | 4 | Producer-side traceback failures; review candidates, not independent Test Solution verdicts |
| AI's own BR verdicts marked `NEEDS_REVIEW` | 4 | Producer-side review signals |
| AI's own BR verdicts marked `PASS` | 142 | Producer-side verdicts only; not Test Solution acceptance |

The current evidence-backed confirmed-match rate is **not calculable**. Although zero confirmed matches are recorded for this run, dividing zero by 6,887 would misleadingly imply that all 6,887 BRs were independently compared and failed to match. They were not. The correct disposition for all 6,887 is `NOT_ASSESSED_NO_CURRENT_RUN_CROSSWALK`.

The independent Test Solution currently has **601 catalogued rules across 49 segments**, but [training-status.json](../../training-status.json) reports **0 segments `TRAINED_FOR_INTAKE` and all 49 `IN_PROGRESS`**. Those 601 rules are not an approved/certified denominator for this run. The old 235-rule crosswalk denominator is also not reusable: it belongs to the 2026-09-23 delivery.

The complete AI BR inventory and explicit self-verdicts are in [run1-ai-br-crosswalk-backlog-2026-10-05.csv](run1-ai-br-crosswalk-backlog-2026-10-05.csv). The [per-segment rollup](run1-ai-br-segment-rollup-2026-10-05.csv) shows AI BR counts, review/attribution signals, and Test Solution catalogued rules for all 49 segments; segment BR counts are non-additive where BRs have multiple segment assignments. Each BR remains unclassified until an independent, same-run disposition is created.

## Structural Findings

- 6,887 BR IDs are unique; 3,817 BRs carry at least one review flag (55.4%).
- 1,628 BRs have unresolved segment assignment (23.6%); 875 are assigned through `llm_proposed` attribution (12.7%). These groups can overlap other quality signals.
- 271 BRs have no `source_rule_id`; 150 are LLM-phrased. The BR package has no specification-version manifest value and no canonical `sourceAnchor` on its records.
- The TS catalog has 48,868 internal BR references spanning all 6,887 BR IDs. These resolve within the AI delivery. They are producer-internal links, not Test Solution matches. There are 155 TSs without a BR link.
- The delivery has no TCs or TDs. Consequently, none of the 6,887 BRs has a complete BR -> TS -> TC -> TD chain in this delivery.
- The BR confidence summary does not match the serialized records: declared HIGH/MEDIUM/LOW is 3,451/2,548/888; recalculated from records it is 3,531/2,492/864.

## Why Prior Matches Cannot Be Carried Forward

The prior crosswalks and `ai-only-requirements-analysis.json` are explicitly tied to `2026-09-23/Run1+Run2` and 6,473 BRs. All 6,473 old IDs appear in the October 5 catalog, but only 10 retain the same source/business identity fields (statement, source rule, page, segment, related entities, and requirement meaning). The other 6,463 IDs now identify different or materially changed requirement records. None of the 312 AI BR IDs previously marked confirmed retains the prior source/business identity fields.

Therefore, neither the previous 30 confirmed Test Solution rules nor the previous 5,939 `AI-only unreferenced` count can be applied to October 5. Matching by producer-local ID would produce false matches.

## AI-Declared BR Failures

These four records are marked `FAIL` by the AI delivery's own judge. They are invalidity candidates requiring independent source review; they are not counted as independently invalid by the Test Solution.

| AI BR ID | Page | Segment | AI judge finding |
|---|---:|---:|---|
| `REQ-SRC-ATL105-PDF-001:6739` | 356 | 100 | Page gives Account Number field lengths, not the assertion about retaining Account Number after card swipe. |
| `REQ-SRC-ATL105-PDF-001:6759` | 377 | Unassigned | Candidate omits the Table Load Request and TransArmor Load Request applicability stated in source. |
| `REQ-SRC-ATL105-PDF-001:6776` | 389 | Unassigned | Candidate omits the Electronic Mail Request condition for Password. |
| `REQ-SRC-ATL105-PDF-001:6860` | 476 | 123 | Candidate omits Interlink/Maestro tokenized transactions stated in source. |

## Correct Coverage Method

Report both denominators separately; do not collapse them into one percentage.

1. **Freeze and identify the exact run.** Hash the AI files and record the source specification version/hash, generator version, and delivery ID. Only crosswalk decisions bound to this exact content may be reused.
2. **Set the Test Solution denominator.** Select independently derived, source-anchored, in-scope Test Solution rules for segments whose intake gates are ready. Document exclusions and SME/provisional status. The AI's BR count is not the denominator for Test Solution rule coverage.
3. **Normalize AI BRs without replacing provenance.** Preserve AI IDs and source fields; create canonical anchors only from independently verified source evidence. Page number or similar wording alone is not a confirmed anchor.
4. **Create an evidence-backed crosswalk.** Compare each Test Solution rule to zero, one, or more AI BRs by canonical anchor and business meaning. Store evidence, status, rationale, reviewer, and source/run hashes. Use `CONFIRMED`, `REVIEW_REQUIRED`, or `MISSING`; a heuristic/text candidate remains `REVIEW_REQUIRED`.
5. **Classify AI-only requirements only after comparison.** An AI BR is confirmed AI-only only after it has been reviewed against the complete eligible Test Solution baseline and found to have no equivalent rule. Absence from a crosswalk is `NOT_ASSESSED`, not proof of AI-only behavior.
6. **Count unique rules and requirements.** For Test Solution rule coverage, count unique mandatory Test Solution rules with at least one confirmed, complete AI chain. For AI BR matching, count unique AI BR IDs with confirmed mappings. Keep one-to-many mappings and many-to-one consolidation explicit; do not count links as distinct requirements.
7. **Validate downstream traceability.** To claim full-chain coverage, require matching source identity and valid links through BR -> TS -> TC -> TD, then validate payload/expected behavior. This delivery has no TC or TD, so full-chain coverage is unavailable.
8. **Separate invalidity from review.** Only independent Test Solution validation or source adjudication can classify a BR as invalid. AI `FAIL`, flags, low confidence, missing attribution, and unresolved links are separate signals, not interchangeable invalid labels.
9. **Recompute and publish.** Recalculate summary counts from row-level dispositions and publish confirmed, review-required, missing, AI-only, invalid, and not-assessed counts with the source run, denominator, exclusions, and evidence references.

Conceptually:

```text
AI BR confirmed-match rate = unique AI BR IDs with CONFIRMED evidence / eligible AI BR IDs assessed
Test Solution rule coverage = unique mandatory Test Solution rules with complete confirmed AI chains / eligible mandatory Test Solution rules
```

Neither percentage is currently computable for October 5. The first lacks a same-run crosswalk; the second additionally lacks an intake-ready independent denominator and TC/TD chains. The documented 0 confirmed matches is a **recorded-evidence count**, not a semantic coverage result.

## Next Action to Produce the Requested Classification

Create and review an October 5-specific BR crosswalk against a declared, intake-eligible Test Solution rule baseline. Start with canonical source anchors and explicit source evidence; carry the 4 AI-declared BR failures and all review-flagged/segment-unassigned requirements into the review queue. After crosswalk disposition, calculate true confirmed matches, unmatched Test Solution rules, AI-only BRs, and independently invalid BRs. Until then, report the result as `NOT_CALCULABLE - REVIEW_REQUIRED`, not 0% coverage.