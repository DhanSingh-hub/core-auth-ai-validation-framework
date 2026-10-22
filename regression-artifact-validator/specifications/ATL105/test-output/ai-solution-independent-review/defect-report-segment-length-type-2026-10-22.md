# AI-DEV Defect Report: Serialized Segment Length and Type

**Report ID:** ATL105-AIDEV-2026-10-22-SEGMENT-INTEGRITY  
**Assigned to:** AI-Dev (triage and serializer/composer owners)  
**Source delivery:** `181026 Run\181026 Run`  
**Specification baseline:** ATL105 2026-3  
**Independent assessment:** `eg1021-v2`  
**Disposition:** Investigate; do not bulk-correct until intended negative-test cases are separated from unintended serialization defects.

## Summary

The delivery-wide evidence pass found two distinct feedback-rule populations:

| Rule | Finding | Affected TDs / TCs | Segment-level observations | Rule severity |
|---|---|---:|---:|---|
| QEF-018 | Segment Length missing or failing numeric/width checks | 281 | 499 | Major |
| QEF-021 | Segment Type does not equal the identified segment number | 95 | 95 | Major |

The populations overlap on 8 TDs, so **368 distinct TDs/TCs** are listed in the annex. These are rule failures requiring review, not 368 confirmed unintended product defects. A negative test may intentionally corrupt one of these fields. Conversely, the TC may target a different rule, in which case a malformed length/type could be an unintended confounder.

The serialized-field checks also do not prove whether the test case's objective is correct. QEF-018 checks presence, numeric-string shape, width and non-zero value; it does not recalculate encoded wire length. QEF-021 compares Segment Type to the segment identifier resolved from the segment name and metadata.

## Observed detail

### QEF-018 — Segment Length

The contract requires a positive numeric string: three digits by default, with four digits for segments 103, 114, 115, 118, 120, 130 and 131.

| Observed condition | Segment occurrences | Affected TDs |
|---|---:|---:|
| Missing Segment Length | 467 | 249 |
| Wrong digit width | 16 | 16 |
| Not a numeric string | 16 | 16 |
| **Total** | **499** | **281** |

Missing-length occurrences are concentrated in segments 135 (232), 145 (231) and 153 (4). Some TDs have more than one affected segment. The source width contract is a width assertion only; exact serialized length remains outside this check.

### QEF-021 — Segment Type

All 95 observations are mismatches; the rule found no missing Segment Type among this population. The affected segments are 100 (24), 105 (6), 108 (6), 109 (6), 111 (6), 118 (6), 119 (6), 102 (4), 110 (4), 113 (4), 135 (4), 143 (4), 145 (4), 156 (4), 114 (3), 123 (2), and 130 (2).

Observed declarations include blank values, placeholder-like declarations and declarations resolving to a different segment number. The annex classifies these shapes without copying payload field values.

## Triage evidence and caveat

Producer TC metadata reports `PASS` for all 95 QEF-021 TDs. For QEF-018, 67 are marked `PASS` and 214 `REJECTED_FINAL`. These producer statuses are evidence to review, not independent semantic verdicts.

The affected TCs carry declared negative classes and/or violated-element names inconsistently. In the QEF-018 group, 228 TCs do not declare `negative_class` and 231 do not declare `violated_element_name`. In the QEF-021 group, all 95 declare `violated_element_name=Segment Type`, which may indicate intentional negative tests; the case objective and expected rejection must be checked before classifying them as AI-Dev defects.

For each annex row, AI-Dev/QA should:

1. Confirm whether the test objective deliberately mutates Segment Length or Segment Type.
2. If intentional, verify the expected response/assertion tests that precise mutation and that unrelated fields remain valid.
3. If not intentional, correct the source-backed serializer/composer and regenerate the TD; do not manually patch generated output only.
4. Add a regression test for the generation path and verify both valid controls and the intended negative mutation.
5. Re-run the affected checks over the full delivery and reconcile the resulting hashes and counts.

## Acceptance criteria

- Every annex row is dispositioned as `INTENTIONAL_NEGATIVE_CONFIRMED`, `UNINTENDED_DEFECT_CONFIRMED`, or `NEEDS_SOURCE_REVIEW`, with reviewer and evidence.
- No intended negative case is silently normalized into a positive request.
- Unintended defects are fixed at the producing stage and covered by regression tests.
- A source-backed valid control passes the relevant length/type shape checks.
- Each intentional negative case fails only its intended predicate, unless additional failures are explicitly part of the objective.
- The regenerated delivery and report are hash-bound; the full delivery rerun has no unexplained findings.
- Exact wire-length validation is claimed only after encoding and an independent oracle are available.

## Evidence files

- [Affected TD/segment annex](./defect-report-segment-length-type-2026-10-22.csv) — one row per failed segment-level observation, with TC/TS/TD identity, hashes, non-sensitive failure category and triage metadata.
- [Evidence-gate summary](./eg1021-v2/summary.json) — full delivery remains `BLOCKED_UNPROVEN_SEMANTIC_CORRECTNESS`; bounded findings are not full semantic certification.

Annex: 594 rows (499 QEF-018 segment observations; 95 QEF-021 segment observations).  
SHA-256: `8daf2119d644cdb187e3470cdd7307a338034946eb2b0330b9642c3f4508dff4`
