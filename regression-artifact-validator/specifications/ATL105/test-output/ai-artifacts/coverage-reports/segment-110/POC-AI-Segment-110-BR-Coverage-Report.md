# POC AI Segment 110 BR Coverage Report

**Full crosswalk:** [docs/specs/kb/segment-110/coverage/segment-110-supplied-pipeline-ai-coverage-report.md](../../../docs/specs/kb/segment-110/coverage/segment-110-supplied-pipeline-ai-coverage-report.md)
**Machine-readable data:** [POC-AI-Segment-110-BR-Coverage-Crosswalk.json](POC-AI-Segment-110-BR-Coverage-Crosswalk.json)
**AI requirement source:** `test-input/ai-solution/runs/2026-09-23/Run1/step5_requirements/approved/requirement_catalog.json`, filtered to `segment_number == "110"` OR `related_entity_ids` containing `ENT-SEG-110` (94 requirements)
**Independent Test Solution oracle:** `docs/specs/kb/segment-110/coverage/segment-110-rule-catalog.json` (20 rules, `SEG110-R-001` to `SEG110-R-020`)
**Report date:** 2026-09-26

## Summary

| Measure | Result |
|---|---:|
| Canonical Segment 110 rules | 20 |
| Rules with at least some AI evidence | 19 / 20 (95.0%) |
| Directly covered (no open policy question) | 9 / 20 (45.0%) |
| Partially covered | 4 / 20 (20.0%) |
| Requires SME/TBA review | 6 / 20 (30.0%) |
| Missing (no AI evidence) | 1 / 20 (5.0%) |
| Release decision | `NOT_READY_FOR_SEGMENT_110_CERTIFICATION` |

## Notable Independent Findings

1. **Element 239 conflict corroborated by the AI evidence itself** (`SEG110-R-017`): some AI requirements describe element 239 as "Enhanced Fleet Data" (999 bytes, Segment 145) while others describe it as "Alternate MICR IND" (1 byte, Segment 110, value `Y`). This matches the source-text conflict found independently in Section 12.9 vs. the Chapter 13 element catalog. It must remain `REVIEW_REQUIRED` (`SEG110-SME-005`), not be merged.
2. **AI mis-scoping corrected** (`SEG110-R-020`): AI requirements `BR-559-4` through `BR-559-7` correctly captured four MICR Type format codes (`T$`, `18`, `09`, `19`) but attributed them generically to Segment 110. Independent verification against Appendix I-17 confirmed they belong to Segment 111's Variable Information Indicator `024` sub-table ("Manual Check MICR Type"), not Segment 110 itself.
3. **Missing rule** (`SEG110-R-006`): no AI requirement enumerates the ordered 12-field Segment 110 serialization sequence; this must be added or explicitly confirmed as out of the AI extraction's scope.

See the full crosswalk for the rule-by-rule table, the out-of-scope AI requirement list, and the required remediation items.
