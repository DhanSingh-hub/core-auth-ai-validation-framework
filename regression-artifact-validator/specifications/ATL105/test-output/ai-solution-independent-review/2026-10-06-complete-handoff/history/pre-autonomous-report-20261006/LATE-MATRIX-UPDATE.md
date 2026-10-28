# Late Requirement Matrix Update

**Received and assessed:** October 6, 2026. **Disposition:** REVIEW_REQUIRED.

## Status Transition

The requirement matrix is now received, preserved unchanged in the dated Run1
supplement, and independently reconciled. The previous assessment is retained
in full under [history/2026-10-06-before-late-matrix](history/2026-10-06-before-late-matrix/AI-ARTIFACT-FILTER-VIEW.html).
All 24 prior report/evidence files were copied before updating either report,
and their snapshot hashes are recorded in
[late-traceability-matrix-assessment.json](late-traceability-matrix-assessment.json).

| Measure | Previous assessment | New matrix claim | Independent frozen-intake recount |
|---|---|---|---|
| BRs | 6,887 | 6,887 | 6,887 |
| Scenarios / TCs | 12,679 / 21,123 | 12,679 / 21,123 | Unchanged |
| BRs with TC links | 3,659 | 3,661 | 3,659, including scenario-inherited links |
| Scenarios without BR links | 155 | 92 | 155 |
| Scenarios without TCs | 3,732 | Not stated | Unchanged |
| Independent semantic coverage | NOT_CALCULABLE | Not established | NOT_CALCULABLE |
| Execution certification | Not established | Not established | Not established |

## Matrix Findings

- The supplied Markdown explicitly shows only **2,000 of 61,107 flat rows** and
  **200 of 6,887 requirement details**. The named complete JSON companion is not
  present in the supplied folder. Missing displayed rows are unavailable
  evidence, not automatically broken links.
- The full gap table lists 3,226 SCENARIO_ONLY BRs. Its complement implies 3,661
  traced BRs, but two of these have no TC links in the frozen intake:
  `REQ-SRC-ATL105-PDF-001:3226` and `REQ-SRC-ATL105-PDF-001:521`.
- All 1,654 unique TD paths in the displayed flat excerpt resolve in the frozen
  handoff. Of the 2,000 displayed leaves, 1,918 have verified graph links and
  physical TD presence; 75 leaves carry FULLY_TRACED despite having no TC or TD,
  and seven carry SCENARIO_ONLY. A requirement-level status cannot establish a
  complete chain for every individual leaf.
- The summary's 3,661 / 6,887 is **53.16%**, not its printed **53.13%**. The latter
  matches the independently counted 3,659 numerator. The declared **53.2%**
  full-chain figure is rounded to one decimal and is not independent readiness
  or business-coverage certification.
- The matrix still calls the specification "unknown specification" despite
  declaring source ID SRC-ATL105-PDF-001 and version 2026-3. Identity and source
  hashes must be reconciled before accepting new graph/coverage declarations.

## Complete Independent Reconciliation

The truncated export is now compared with a complete **Test Solution-owned
structural reconstruction**, without altering the received Markdown or claiming
to recover the missing AI-produced JSON.

| Measure | Independent result |
|---|---|
| BRs represented | 6,887 / 6,887 |
| BR-chain leaves | 61,044 |
| Producer-declared leaves | 61,107; 63 more than the frozen reconstruction |
| Structurally traced leaves with physical data | 56,617 |
| Scenario-only leaves | 4,400 |
| TC leaves with no physical data | 27 |
| Logical cases with all data files | 21,096 |
| Displayed BR detail statuses agreeing | 200 / 200 |
| Displayed leaf status disagreements | 75 |
| Unattributed scenarios / cases | 155 / 155, retained separately |

The 75 flat-row disagreements are now explained: **all displayed BR-level
statuses agree**, but FULLY_TRACED is propagated from a BR with at least one
complete chain to individual scenario-only leaves. Those leaves remain
SCENARIO_ONLY in the independent matrix. This is a status-granularity issue,
not 75 newly discovered missing-TD cases. The 27 missing TDs remain a separate
population; multiple leaves per scenario/BR must not be confused with unique
scenario or case counts.

All 155 cases without BR attribution have valid scenario parents. They are not
155 broken TC-to-scenario links: the producer's zero orphan TCs can agree when
"orphan TC" means a missing scenario parent. Keep the BR-attribution and
scenario-parent definitions separate when comparing these counters.

The 63 additional declared rows match the 63 fewer declared orphan scenarios
arithmetically. This is consistent with additional producer attribution, but
does not identify the actual missing edges. The disputed `:521` BR concerns
AVS-driven Purchase Reversal; `:3226` concerns estimated/initial authorization
when the final amount is unknown. Neither has a TC link in the frozen graph;
do not assign the orphan scenarios by keywords or this arithmetic coincidence.

Complete independent outputs are available as
[reconstructed-requirement-matrix.json](reconstructed-requirement-matrix.json)
and [reconstructed-requirement-matrix.csv](reconstructed-requirement-matrix.csv).
Their hashes and leaf/detail comparisons are recorded in the reconciliation
JSON. The earlier matrix-era assessment is also preserved unchanged as 27
hash-recorded files under
[history/pre-reconciliation-20261006](history/pre-reconciliation-20261006/AI-ARTIFACT-FILTER-VIEW.html).
The original 24-file pre-matrix snapshot remains intact.

The three frozen catalog inputs and all 21,210 present physical payload files
are independently hash-checked against the original source-verified intake
manifest before publishing the reconstruction. The complete JSON retains the
155 unlinked cases separately, with their actual scenario and data-file links;
they are not silently dropped or attributed to an invented BR.

## Remaining Follow-Up

1. Obtain the complete traceability_matrix.json and exact BR/TS/TC catalog hashes
   used to generate it; preserve those as another immutable late supplement.
2. Explain the two additional linked BRs and the 92-versus-155 orphan-scenario
   discrepancy. Do not silently substitute the later matrix's graph for the
   previously frozen delivery.
3. Clarify FULLY_TRACED propagation onto no-TC/no-TD leaves and publish
   requirement-level and leaf-level statuses separately.
4. Keep semantic/SME review, isolated full-control effectiveness, captured-wire
   validation and authoritative host outcomes unresolved until qualifying
   evidence is supplied. Structural matrix receipt does not resolve these gates.

## Evidence and History

- [Preserved matrix](../../../test-input/ai-solution/runs/2026-10-05/Run1/supplements/2026-10-06-requirement-matrix/traceability_matrix.md)
- [Reconciliation JSON and hashes](late-traceability-matrix-assessment.json)
- [Complete parsed flat-excerpt CSV](late-matrix-reconciled-leaves.csv)
- [Previous run report](history/2026-10-06-before-late-matrix/AI-ARTIFACT-FILTER-VIEW.html)
- [Previous PDF](history/2026-10-06-before-late-matrix/AI-ARTIFACT-FILTER-VIEW.pdf)
- [Previous semantic report](history/2026-10-06-before-late-matrix/SEMANTIC-FIRST-BATCH-REPORT.html)

The earlier structural and semantic assessments remain historical evidence,
not withdrawn findings. The current reports add the matrix receipt, deltas and
review queue without promoting producer declarations to independent acceptance.