# Automation Guide

Use `../framework.ps1 -List` for common tasks. `framework-tasks.json` in the module root is the task index; script names and explicit review-directory inputs remain available for advanced runs.

## Purpose Groups

| Group | Tools | Ownership |
|---|---|---|
| Intake and provenance | `analyze-oct05-handoff.py`, `assess-late-traceability-matrix.py` | Preserve producer data; create independent review evidence. |
| Semantic assessment | `assess-ai-semantic-batch.py`, `assess-full-semantic-chain.py`, `audit-ai-test-data-and-br-feedback.py` | Read source and frozen inputs; report supported checks and missing evidence. |
| Mitigation and finalization | `implement-semantic-mitigation.py`, `finalize-semantic-chain-assessment.py` | Create derived outputs; preserve original inputs and historical states. |
| Presentation | `generate-ai-artifact-filter-view.py`, `generate-semantic-batch-view.py`, `publish-final-semantic-report.py`, `prepare-business-presentation.py` | Publish from structured assessment data; never create semantic approval. |
| Cohort preparation | `prepare-autonomous-cohort.py` | Prepare bounded review inputs; not independent acceptance. |
| Presentation regressions | `validate-ai-artifact-filter-view.cjs`, `validate-semantic-batch-view.cjs`, `validate-full-semantic-chain-report.cjs` | Validate existing reports using an explicitly available jsdom installation. |
| Excel export | `powershell/export-atl105-element-reference-to-excel.ps1` | Export the source-derived field reference, preserving review states. |
| Legacy ratio view | `powershell/report-all-segments-br-ratio.ps1` | Legacy heuristic report; its labels are not authoritative semantic confirmation or execution readiness. |

The Python and Node tools intentionally remain together: several Python presentation tools load neighboring scripts using `__file__` and `runpy`. Moving each into a separate folder would break those live dependencies without reducing domain complexity. PowerShell utilities are grouped separately and resolve their defaults relative to their own location.

## Common Commands

From the module root:

```powershell
.\framework.ps1 -Task test
.\framework.ps1 -Task test -Tests 'RepositoryStructureTest,Atl105PathsTest' -DryRun
.\framework.ps1 -Task semantic-self-test -PythonPath 'C:\Program Files\Python314\python.exe'
.\framework.ps1 -Task semantic-view-check -ReviewDirectory 'specifications/ATL105/test-output/ai-solution-independent-review/2026-10-06-complete-handoff' -JsdomModulePath 'C:\path\to\node_modules\jsdom'
& .\scripts\powershell\export-atl105-element-reference-to-excel.ps1
```

Review-directory and jsdom paths are resolved from the caller's directory before the launcher changes working directory. Python defaults to `python.exe`; use `-PythonPath` when a particular interpreter is required. Failures propagate as task errors, and the caller's directory is restored. Task launch does not install packages or grant approval.

## Retention

Keep immutable intake under `specifications/ATL105/test-input`; place independent outputs under the pack's `test-output` and published views under `reports`. Preserve prior report snapshots and their recorded hashes. Python caches and Maven build outputs are ignored, not evidence.

The former module-root Segment 103 report is retained under `specifications/ATL105/reports/legacy/module-root`. Its SHA-256 at relocation was `D570CA9A925A432147BA8B26749B5B5EC90A3711011EEEAD237DED9C155577C7`; this is a historical snapshot, not the latest validation result. New Segment 103 reports use `Atl105Paths.consolidatedReport("103")`.