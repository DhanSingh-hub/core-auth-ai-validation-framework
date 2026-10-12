# Automation Guide

Use `../framework.ps1 -List` for common tasks. `framework-tasks.json` in the module root is the task index; script names and explicit review-directory inputs remain available for advanced runs.

## Purpose Groups

| Group | Tools | Ownership |
|---|---|---|
| Intake and provenance | `analyze-oct05-handoff.py`, `assess-late-traceability-matrix.py`, `detect-ai-run-duplicates.py`, `build-ai-run1-4-combined-crosswalk.py`, `verify-ai-run-intake.py` | Preserve producer data; create independent review evidence, identify byte-identical content, and build provenance-preserving multi-run BR/scenario/TC indexes. |
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
python.exe .\scripts\detect-ai-run-duplicates.py .\specifications\ATL105\test-input\ai-solution\runs\2026-10-07 .\specifications\ATL105\test-output\ai-run-deduplication-2026-10-07-v2.json
python.exe .\scripts\detect-ai-run-duplicates.py --self-test
python.exe .\scripts\build-ai-run1-4-combined-crosswalk.py --runs-root .\specifications\ATL105\test-input\ai-solution\runs\2026-10-07 --analysis-dir .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-07-Run4\analysis-v2 --test-solution-dir .\specifications\ATL105\test-output\test-solution-independent-review --dedup-report .\specifications\ATL105\test-output\ai-solution-independent-review\ai-run-deduplication-2026-10-12.json --output-dir .\specifications\ATL105\test-output\ai-solution-independent-review\combined-runs-2026-10-12-crosswalk-v3
& .\scripts\powershell\export-atl105-element-reference-to-excel.ps1
```

The combined crosswalk deduplicates byte-identical Run2–Run4 catalogs, merges Run1 suite test cases only on exact stored fingerprint, and writes a content-hash index without copying payload contents. Run1's distinct legacy BR catalog and its supplemental Segment 100 suite remain separate provenance cohorts. Anchor candidates are review-required; no scenario/BR semantic match or execution certification is inferred.

Review-directory and jsdom paths are resolved from the caller's directory before the launcher changes working directory. Python defaults to `python.exe`; use `-PythonPath` when a particular interpreter is required. Failures propagate as task errors, and the caller's directory is restored. Task launch does not install packages or grant approval.

## Retention

Keep immutable intake under `specifications/ATL105/test-input`; place independent outputs under the pack's `test-output` and published views under `reports`. Preserve prior report snapshots and their recorded hashes. Python caches and Maven build outputs are ignored, not evidence.

The former module-root Segment 103 report is retained under `specifications/ATL105/reports/legacy/module-root`. Its SHA-256 at relocation was `D570CA9A925A432147BA8B26749B5B5EC90A3711011EEEAD237DED9C155577C7`; this is a historical snapshot, not the latest validation result. New Segment 103 reports use `Atl105Paths.consolidatedReport("103")`.