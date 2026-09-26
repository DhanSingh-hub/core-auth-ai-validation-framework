# POC AI Segment 109 BR Coverage Report

**Full crosswalk:** [docs/specs/kb/segment-109/coverage/segment-109-supplied-pipeline-ai-coverage-report.md](../../../docs/specs/kb/segment-109/coverage/segment-109-supplied-pipeline-ai-coverage-report.md)
**Machine-readable data:** [POC-AI-Segment-109-BR-Coverage-Crosswalk.json](POC-AI-Segment-109-BR-Coverage-Crosswalk.json)
**AI requirement source:** `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`
**Independent Test Solution oracle:** `docs/specs/kb/segment-109/coverage/segment-109-rule-catalog.json` (22 rules, `SEG109-R-001` to `SEG109-R-022`)
**Report date:** 2026-09-26 (report re-published to `test-output` for file-organization parity with Segment 100; content unchanged from the KB coverage report dated 2026-09-22)

## Summary

| Measure | Result |
|---|---:|
| Canonical Segment 109 rules | 22 |
| Rules with at least some AI evidence | 20 / 22 (90.9%) |
| Directly covered (no open policy question) | 5 / 22 (22.7%) |
| Partially covered | 8 / 22 (36.4%) |
| Requires SME/TBA review | 7 / 22 (31.8%) |
| Missing (no AI evidence) | 2 / 22 (9.1%): `SEG109-R-007`, `SEG109-R-022` |
| Release decision | `NOT_READY_FOR_SEGMENT_109_CERTIFICATION` |

## Business Requirement Count Reconciliation

The KB coverage report (dated 2026-09-22) cites **38** Segment 109 business requirements sourced from an external pipeline segment-filter file (`step5_requirements/approved/readable_by_segment/markdown/SEG-109.md`) that is not present in this workspace. The [POC-AI-ATL105-Segment-109-Business-Requirements.md](../../business-requirements/POC-AI-ATL105-Segment-109-Business-Requirements.md) artifact added alongside this report instead applies the declared-linkage filter (`segment_number`/`related_entity_ids`) used consistently for every other segment in this workspace, which yields **61** requirements. Both counts should be reconciled once the external segment-filter file is supplied (`SEG109-SME-011`); until then, treat 61 as the broader, declared-linkage superset and 38 as the narrower, externally-curated subset.

See the full crosswalk in the KB coverage report for the rule-by-rule table and required remediation items.
