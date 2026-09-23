# Standard Segment Cleanup Process

This process normalizes raw AI-generated business requirements before coverage comparison. Raw AI output is preserved unchanged under the dated run folder.

## Stages

1. **Intake**: record source run date, run name, source catalog path, SHA-256, and ATL105 version.
2. **Scope filter**: retain only requirements whose source rule is directly related to `ENT-SEG-{segment}`. Shared elements and cross-segment relationships are candidates, not approved segment requirements.
3. **Candidate accounting**: count excluded related/cross-segment requirements instead of silently deleting them.
4. **Duplicate handling**: group by `source_rule_id`; retain distinct statements only when the source rule legitimately has separate behavior, otherwise mark duplicates for review.
5. **Confidence handling**: preserve `requires_review`, `below_confidence_gate`, confidence band, and flags. Never promote review items automatically.
6. **Version normalization**: require specification version `2026-3` for the current ATL105 pack.
7. **Artifact parity**: generate JSON and Markdown from the same normalized requirement collection.
8. **Coverage gate**: compare only after a matching Test Solution baseline package exists for the segment.

## Semantic Matching Policy

Semantic/source-anchor promotions must be explicit and segment-scoped. A promotion is allowed only when an AI source rule and statement clearly express the same structural or field rule as an existing Test Solution baseline rule. Text similarity alone is not sufficient.

For Segment 105, the approved explicit mappings currently cover:

- `BR-62-9` -> `BR-SEG105-008` (Totals Date request code)
- `BR-175-3` -> `BR-SEG105-001` (Segment 105 identity)
- `BR-232-2`, `BR-232-3`, `BR-232-4` -> `BR-SEG105-002` (field-separator/length behavior)

Broader settlement policy, response behavior, or eWIC operational statements remain `POTENTIAL_MATCH_REVIEW_REQUIRED` or `UNMATCHED_IN_TEST_SOLUTION` until an equivalent baseline rule and source anchor exist.

## Required Manifest Fields

Every normalized package must include:

- `specification`
- `specificationVersion`
- `segment`
- `segmentName`
- `generatedAt`
- `sourceCatalog`
- `filterMethod`
- `totalRequirements`
- `directSegmentRequirements`
- `excludedRelatedRequirements`
- `reviewRequiredRequirements`
- `belowConfidenceGateRequirements`
- `duplicateSourceRuleGroups`

## Acceptance Checks

A cleanup package is ready for coverage comparison only when:

- every retained requirement has a direct segment source relationship;
- no retained requirement is silently assigned from another segment;
- JSON and Markdown counts agree;
- source version is `ATL105 2026-3`;
- review-required and below-gate items remain visible;
- duplicate source-rule groups are explicitly reported; and
- the matching Test Solution baseline package exists.
