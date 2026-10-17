# All Segments BR Coverage Ratio: AI Solution vs Test Solution

- Test Solution BRs: **339**
- AI BRs: **1877**
- Confirmed Test Solution baseline coverage: **12.4% (42/339)**
- Review queue: **1805**

All candidates remain reviewable until independently validated.

## QE-Fiserv Feedback Training (October 2026)

Source: [QE-Fiserv.xlsx](../../../../../../QE-Fiserv.xlsx) (`1.Feedback`, `2. Feedback-30-sep-26`, `Status-SepDef`, `3.Oct 2026_Feedback`, `Oct-10 AutoRun-FB`, `Action Items`). Every feedback item is trained and enforced as a Test Solution rule regardless of the workbook status column (rule catalog `contract/qe-feedback-rule-catalog.json`, rules `QEF-001`-`QEF-033`). Feedback rules that differ from ATL105 2026-3 text keep a recorded source conflict for SME confirmation. The `Oct-10 AutoRun-FB` decision columns are empty, so its candidate rows are not approved matches.

### BR, scenario and test-case chain on the 10 October AI delivery

Delivery preserved and SHA-256 verified at `test-input/ai-solution/runs/2026-10-17/Run1/`. The engine validated **6,880 AI BRs**, **12,254 scenarios**, **21,254 test cases** (387 flows) and **17,769/17,769 payload legs**. These counts use the AI BR population as denominator; they do not change the Test Solution baseline coverage above.

- BRs without a scenario: **0**; scenarios without a test case: **3,171**; BRs without a test case: **2,806** (silent coverage made visible).

| Rule | Feedback requirement | Applicable | Fail | Fail % |
| --- | --- | ---: | ---: | ---: |
| QEF-001 | BR linked to a segment (Def_SepRun01/04) | 6,880 | 1,599 | 23.2% |
| QEF-002 | BR has a scenario (Def_SepRun02/05) | 6,880 | 0 | 0.0% |
| QEF-003 | Scenario has a test case (Def_SepRun02/05/21) | 12,254 | 3,171 | 25.9% |
| QEF-004 | Test case has physical test data (Def_SepRun21) | 21,254 | 3,970 | 18.7% |
| QEF-005 | BR segment serialized in test data (Def_SepRun15/20) | 16,325 | 3,105 | 19.0% |
| QEF-006 | Scenario is not a copy of its BR (Def_Sep30Run04) | 12,254 | 6,654 | 54.3% |
| QEF-009 | Concrete expected response code (Def_Sep30Run03) | 21,254 | 5,443 | 25.6% |
| QEF-010 | BR has negative coverage (Def_Sep30Run05) | 4,074 | 3,763 | 92.4% |
| QEF-012 | Canonical source anchor on BR/TS/TC (Def_Sep30Run07) | 40,388 | 32,644 | 80.8% |
| QEF-013 | BR keeps value constraint (Def_Sep30Run08) | 6,880 | 421 | 6.1% |

QEF-001 also holds **893** BRs whose segment was only LLM-proposed for review. Test-data rules QEF-011 to QEF-031 (October findings D001-D020 and the Action Items) are reported in the [QE feedback validation report](../../../ai-solution-independent-review/2026-10-17-qe-feedback-training-v2/QE-FEEDBACK-VALIDATION-REPORT.md).

Readable outputs requested by the feedback: [per-segment traceability, 8-column manual tables and Test Case Cards](../../../ai-solution-independent-review/2026-10-17-qe-feedback-training-v2/readable/README.md). Semantic coverage remains `NOT_ASSESSED`; host execution `NOT_EXECUTED`. The workbook's September 3,072/6,291 (48.8%) figure belongs to an earlier delivery and is not comparable.
