# POC AI Segment 118 BR Coverage Report

**Full crosswalk:** [docs/specs/kb/segment-118/coverage/segment-118-supplied-pipeline-ai-coverage-report.md](../../../docs/specs/kb/segment-118/coverage/segment-118-supplied-pipeline-ai-coverage-report.md)
**Machine-readable data:** [POC-AI-Segment-118-BR-Coverage-Crosswalk.json](POC-AI-Segment-118-BR-Coverage-Crosswalk.json)
**AI requirement source:** `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`, filtered to `segment_number == "118"` OR `related_entity_ids` containing `ENT-SEG-118` (107 requirements)
**Independent Test Solution oracle:** `docs/specs/kb/segment-118/coverage/segment-118-rule-catalog.json` (30 rules, `SEG118-R-001` to `SEG118-R-030`)
**Report date:** 2026-09-26

## Summary

| Measure | Result |
|---|---:|
| Canonical Segment 118 rules | 30 |
| Rules with at least some AI evidence | 26 / 30 (86.7%) |
| Directly covered (no open policy question) | 21 / 30 (70.0%) |
| Partially covered | 5 / 30 (16.7%) |
| Missing (no AI evidence) | 4 / 30 (13.3%) |
| Release decision | `NOT_READY_FOR_SEGMENT_118_CERTIFICATION` |

This is the strongest AI coverage ratio of any segment trained in this workspace so far.

## Notable Independent Findings

1. **`99999` sentinel independently confirmed by both sides**: the Test Solution found "a value beginning with 99999 indicates no card table used at the location" in Element 176's Chapter 13 definition; the AI catalog independently derived the same fact (`ENT-ELEM-176` value dependency, `BR-465-1`).
2. **Segment 116/119 conflict recurs**: AI requirement `BR-176-6` repeats the same "Totals with Proprietary Data Load Request = Segment 116" mislabeling found during Segment 116 training, again at low confidence (41%) — excluded from this segment's coverage counting.
3. **Type correction**: Device Card Table Version and Card Table Load Version were corrected from alphanumeric to numeric (35 digits) after independent verification against the Chapter 13 element catalog.

See the full crosswalk for the rule-by-rule table, out-of-scope AI requirements, and required remediation items.
