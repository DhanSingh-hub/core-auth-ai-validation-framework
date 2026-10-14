# Automation Guide

Use `../framework.ps1 -List` for common tasks. `framework-tasks.json` in the module root is the task index; script names and explicit review-directory inputs remain available for advanced runs.

## Purpose Groups

| Group | Tools | Ownership |
|---|---|---|
| Intake and provenance | `analyze-oct05-handoff.py`, `assess-late-traceability-matrix.py`, `detect-ai-run-duplicates.py`, `build-ai-run1-4-combined-crosswalk.py`, `audit-ai-br-chain-maximum-coverage.py`, `verify-ai-run-intake.py` | Preserve producer data; identify byte-identical content; and build provenance-preserving BR/scenario/TC indexes and per-BR chain-coverage audits. |
| AI BR validation groups | `group-ai-br-validation-results.py` | Attribute current Run4 bounded rule outcomes to every BR chain and group by chain completeness and Test BR candidate presence. |
| AI BR inventory | `export-ai-br-test-model-inventory.py`, `export-ai-test-br-crosswalk.py` | Export AI BR inventories and exact source-anchor candidate links to the independent Test BR baseline; candidates remain review-required. |
| Technical validation coverage | `summarize-ai-technical-validation-coverage.py` | Reconcile byte-bound field/structure runner outcomes with the independent 601-rule baseline and delivery-wide physical-chain ledgers; emit per-rule and candidate-observation ledgers plus a hash manifest. |
| Targeted cohort seed | `prepare-targeted-ai-br-generation-cohort.py` | Package ATL105 source excerpts and an evaluation-only map for uncovered Test BR anchors; does not generate requirements or grant coverage credit. |
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
python.exe .\scripts\export-ai-test-br-crosswalk.py --ai-requirements .\specifications\ATL105\test-input\ai-solution\runs\2026-10-10\Run1\step5_requirements\approved\requirement_catalog.json --test-solution-directory .\specifications\ATL105\test-output\test-solution-independent-review --output-directory .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-14-ai-test-br-crosswalk-v1
python.exe .\scripts\build-ai-run1-4-combined-crosswalk.py --runs-root .\specifications\ATL105\test-input\ai-solution\runs\2026-10-07 --analysis-dir .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-07-Run4\analysis-v2 --test-solution-dir .\specifications\ATL105\test-output\test-solution-independent-review --dedup-report .\specifications\ATL105\test-output\ai-solution-independent-review\ai-run-deduplication-2026-10-12.json --output-dir .\specifications\ATL105\test-output\ai-solution-independent-review\combined-runs-2026-10-12-crosswalk-v4
python.exe .\scripts\summarize-ai-technical-validation-coverage.py --field-structure-report .\specifications\ATL105\test-output\Run1-2026-10-14-full-delivery-field-structure-execution-v3.json --baseline .\specifications\ATL105\test-output\test-solution-independent-review\source-derived-requirement-inventory.json --delivery-summary .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-14-delivery-wide-assessment-v6\summary.json --output-dir .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-14-technical-validation-coverage-full-recursive-v2
python.exe .\scripts\summarize-ai-technical-validation-coverage.py --self-test
python.exe .\scripts\audit-ai-br-chain-maximum-coverage.py --combined-crosswalk .\specifications\ATL105\test-output\ai-solution-independent-review\combined-runs-2026-10-12-crosswalk-v4\combined-ai-br-test-solution-crosswalk.jsonl --run4-crosswalk .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-07-Run4\analysis-v2\run4-independent-br-crosswalk-v2\run4-ai-br-chain-crosswalk.jsonl --run1-requirements .\specifications\ATL105\test-input\ai-solution\runs\2026-10-07\Run1\pipeline_outputs_2026-10-07\sample_req_vision\requirements.json --run1-suite .\specifications\ATL105\test-input\ai-solution\runs\2026-10-07\Run1\pipeline_outputs_2026-10-07\src\pipeline\reporting\output\segment-100-suite-catalog.json --test-solution-dir .\specifications\ATL105\test-output\test-solution-independent-review --output-dir .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-12-ai-br-chain-maximum-coverage-v3
mvn.cmd -q exec:java "-Dexec.mainClass=com.coreauth.validator.canonical.Atl105FieldStructureRuleRunner" "-Dexec.args=specifications/ATL105 Run4-2026-10-07 specifications/ATL105/test-input/ai-solution/runs/2026-10-07/Run4/pipeline_outputs_2026-10-07/src/pipeline/test_generation/candidates/qe_shaped_test_data specifications/ATL105/test-output/Run4-field-structure-execution-v11.json"
$deliveryRoot = 'C:\path\to\immutable-delivery-root'
$execArgs = 'specifications/ATL105 Run1-2026-10-13-full-delivery "' + $deliveryRoot + '" specifications/ATL105/test-output/Run1-2026-10-13-field-structure-execution-v14.json --recursive'
mvn.cmd -q exec:java "-Dexec.mainClass=com.coreauth.validator.canonical.Atl105FieldStructureRuleRunner" "-Dexec.args=$execArgs"
python.exe .\scripts\group-ai-br-validation-results.py --chain-audit .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-12-ai-br-chain-maximum-coverage-v3\ai-br-chain-maximum-coverage-crosswalk.jsonl --field-structure-report .\specifications\ATL105\test-output\Run4-field-structure-execution-v11.json --module-root . --output-dir .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-12-ai-br-grouped-validation-v4
python.exe .\scripts\export-ai-br-test-model-inventory.py --chain-audit .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-12-ai-br-chain-maximum-coverage-v3\ai-br-chain-maximum-coverage-crosswalk.jsonl --output-dir .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-12-ai-br-test-model-inventory-v1
python.exe .\scripts\prepare-targeted-ai-br-generation-cohort.py --test-solution-dir .\specifications\ATL105\test-output\test-solution-independent-review --chain-audit .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-12-ai-br-chain-maximum-coverage-v3\ai-br-chain-maximum-coverage-crosswalk.jsonl --extracted-text .\specifications\ATL105\docs\specs\extracted_text.txt --output-dir .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-12-targeted-ai-br-generation-seed-v1
& .\scripts\powershell\export-atl105-element-reference-to-excel.ps1
```

The combined crosswalk deduplicates byte-identical Run2–Run4 catalogs, merges Run1 suite test cases only on exact stored fingerprint, and writes a content-hash index without copying payload contents. The chain audit emits one row per core AI BR plus separate supplemental suite BR rows, retains all official 601-rule and generated 725-BR package candidates, and records BR→TS→TC→TD route IDs. Coverage counts exact source-anchor candidates without requiring SME approval; reverse-only TD→TC references do not qualify as complete chains. Candidate reach does not assert semantic equivalence, test execution, or final test approval.

The grouped validation report derives from the hash-checked Run4 field-structure report. `PASS`/`FAIL` are bounded profile outcomes, negative-target detection candidates are separated from other failures, and a linked TC with no matching rule-evaluation row is not treated as a full-case failure or as proof it was never inspected.

The technical-coverage summarizer requires a stable, provenance-v1 field/structure runner report, the AI-excluded independent baseline, and the delivery-wide summary with its adjacent hash-verified ledgers. The current full-recursive report covers both QE-shaped candidate directories: 39,479 metadata messages paired with 39,479 payloads, plus one separately inventoried unpaired payload; it records 362,963 evaluation rows. The aggregate emits 601 per-rule rows and only exact candidate-rule observations on clean direct physical chains. It records 58/601 rules with limited/partial executor bindings, 41/601 with a decisive predicate outcome, 14,396/21,227 cases with any decisive result, and 208/6,880 AI BRs with a linked candidate-rule observation. These are separate measures: case-any-result is not whole-case pass, candidate observation is not semantic agreement, and semantic-confirmed/host-executed coverage remain null. `manifest.json` hashes the coverage JSON and all sibling artifacts; it excludes itself to avoid recursive hashing. Run `--self-test` after changing aggregation or publication behavior.

For a full candidate-delivery pass, invoke the field runner on the immutable delivery root with `--recursive`. It scans every `qe_shaped_test_data` directory, uses root-relative metadata paths as message IDs so duplicate basenames remain distinct, records payloads with no metadata sidecar as unpaired (never synthesizing metadata), and checks input inventory/hash stability. The technical summary reports paired messages and orphan counts; this is still bounded field/structure execution, not validation of archives/auxiliary formats, semantic BR correctness, or host execution.

The targeted cohort seed is intentionally separate from the blind AI coverage measurement. Its generation context contains ATL105 excerpts and source anchors but omits Test BR IDs/titles/statements; those appear only in the separate evaluation map. The chain file contains unfilled BR→TS→TC→TD templates. Source stubs remain review-required, and the seed pack itself is not generated test coverage.

Review-directory and jsdom paths are resolved from the caller's directory before the launcher changes working directory. Python defaults to `python.exe`; use `-PythonPath` when a particular interpreter is required. Failures propagate as task errors, and the caller's directory is restored. Task launch does not install packages or grant approval.

## Retention

`audit-ai-delivery-coverage.py` provides the delivery-wide ledger. Run with
`--run-root`, `--test-solution-directory`, `--source-text` and a new
`--output-directory`; run `--self-test` for synthetic regressions. Requires the
installed `ijson` package. The source text must correspond to the independent
baseline release; producer source identity is not silently relabeled.

Both approved and candidate catalog tiers are included. Every occurrence retains
its source file, row hash and producer state. Conflicting row content under one
ID, missing IDs and within-catalog duplicate IDs block structural credit. All
physical files are inventoried/hashed, and explicit TC declarations resolve
relative to their catalog with metadata TC/scenario identity checks. No guessed
filename or basename-only fallback supplies a complete route.

Proposed ELEMENT/CROSS_FIELD/PROCESS/LIFECYCLE/RESPONSE/TRANSPORT and
MESSAGE/SECURITY/AMOUNT/DEPENDENCY/ACCOUNT_CARD/CROSS_SEGMENT types are derived
only from explicit producer entity IDs, category and requirement-type metadata.
They remain unassessed/review-required; MESSAGE is not automatically RESPONSE,
CROSS_SEGMENT is not CROSS_FIELD, and quotes are checked only when supplied.
No fabricated element number supplies candidate coverage. Pair review
queues remain pending authorized decisions. Candidate reach, physical-chain
candidate reach, semantic confirmation and execution are separate measures;
the latter two stay null without qualified evidence. Auxiliary matrices, archives
and other formats receive explicit unassessed validation dispositions.

The shared candidate matcher is captured as `crosswalk-implementation.py` in
each output directory and that exact snapshot is executed/hash-verified; edits
to the working exporter cannot silently change the matching policy mid-run.
The complete file population and hashes are checked again before publication.
A changing delivery/analyzer aborts; preserve the partial directory and use a
new output directory for the corrected replay. Outputs include full-baseline
Test BR rows, anchor/pair queues, incomplete TC declarations and chain-gap rows.

Keep immutable intake under `specifications/ATL105/test-input`; place independent outputs under the pack's `test-output` and published views under `reports`. Preserve prior report snapshots and their recorded hashes. Python caches and Maven build outputs are ignored, not evidence.

The former module-root Segment 103 report is retained under `specifications/ATL105/reports/legacy/module-root`. Its SHA-256 at relocation was `D570CA9A925A432147BA8B26749B5B5EC90A3711011EEEAD237DED9C155577C7`; this is a historical snapshot, not the latest validation result. New Segment 103 reports use `Atl105Paths.consolidatedReport("103")`.