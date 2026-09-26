# POC AI Segment 116 BR Coverage Report

**Full crosswalk:** [docs/specs/kb/segment-116/coverage/segment-116-supplied-pipeline-ai-coverage-report.md](../../../docs/specs/kb/segment-116/coverage/segment-116-supplied-pipeline-ai-coverage-report.md)
**Machine-readable data:** [POC-AI-Segment-116-BR-Coverage-Crosswalk.json](POC-AI-Segment-116-BR-Coverage-Crosswalk.json)
**AI requirement source:** `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`, filtered to `segment_number == "116"` OR `related_entity_ids` containing `ENT-SEG-116` (15 requirements)
**Independent Test Solution oracle:** `docs/specs/kb/segment-116/coverage/segment-116-rule-catalog.json` (9 rules, `SEG116-R-001` to `SEG116-R-009`)
**Report date:** 2026-09-26

## Summary

| Measure | Result |
|---|---:|
| Canonical Segment 116 rules | 9 |
| Rules with at least some AI evidence | 8 / 9 (88.9%) |
| Directly covered (no open policy question) | 4 / 9 (44.4%) |
| Partially covered | 2 / 9 (22.2%) |
| Requires SME/TBA review | 2 / 9 (22.2%) |
| Missing (no AI evidence) | 1 / 9 (11.1%) |
| Release decision | `NOT_READY_FOR_SEGMENT_116_CERTIFICATION` |

## Why This Segment's Coverage Is Structurally Limited

Segment 116's own ATL105 sections (12.15, 11.7.5) are single-sentence stubs redirecting to an external "BUYPASS Platform ATL105 Specification Updates for TransArmor Processing" document that is not present in this workspace. Both the independent Test Solution rule catalog and the AI-generated requirement catalog appear to have been derived from the same incomplete base document — neither has the segment's full field layout. This is a **data availability gap**, not a quality gap in either the Test Solution or the AI pipeline.

## Notable Independent Findings

1. **Source conflict corroborated by AI's own low confidence** (`SEG116-R-009`): the AI catalog assigns only 34% confidence to linking "Totals with Proprietary Data Load Request" to Segment 116 — the lowest confidence of any Segment 116 entry — matching the independently found conflict between Section 11.4.1.2 and the authoritative Element 85 valid-codes table / Section 12.17 (the correct segment is 119).
2. **Unconfirmed Sequence Number lead** (`SEG116-R-006`): AI requirements suggest Element 86 (Sequence Number) may belong to Segment 116, but this is generalized from a generic "included on all transaction types" statement, not a segment-specific table. Flagged as a lead for future confirmation, not certified.

See the full crosswalk for the rule-by-rule table, the out-of-scope AI requirement, and the required remediation items — the first and most important of which is obtaining the external TransArmor document.
