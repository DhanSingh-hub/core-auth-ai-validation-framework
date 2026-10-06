# Segment DL7: AI-Generated vs Test-Generated Requirement Comparison

**AI evidence:** [supplied-ai-catalog-coverage.md](../../../../test-output/ai-artifacts/coverage-reports/supplied-ai-catalog/supplied-ai-catalog-coverage.md). The supplied AI catalog contains **no** requirement tagged `DL7`. Its Element 24 requirement (`REQ-SRC-ATL105-PDF-001:024`, tagged DL1) lists "(No. DL7)" among malformed enumeration values, which is not a DL7 requirement. No dedicated AI BR → TS → TC → TD package exists (`SEGDL7-SME-002`).

## Result

| Test Solution rule | AI requirement | Disposition |
|---|---|---|
| SEGDL7-R-001 (framing, no `~`) | None | TEST_ONLY |
| SEGDL7-R-002 (Segment Length excludes `^`) | None | TEST_ONLY |
| SEGDL7-R-003 (TLV per Appendix W) | None | TEST_ONLY |
| SEGDL7-R-004 (Appendix W tables) | None | TEST_ONLY |
| SEGDL7-R-005 (Download Data ≤ 100) | None | TEST_ONLY |
| SEGDL7-R-006 (3-digit Segment Length) | None | TEST_ONLY |

The AI output does not cover DL7 or Appendix W.
