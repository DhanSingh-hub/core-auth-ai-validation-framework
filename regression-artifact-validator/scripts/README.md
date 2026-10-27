# Automation Guide

Use `../framework.ps1 -List` for common tasks. `framework-tasks.json` in the module root is the task index; script names and explicit review-directory inputs remain available for advanced runs.

## Shared Large Evidence

Oversized evidence is stored losslessly as gzip-compressed parts (48 MiB maximum each) under `shared-artifacts/2026-10-16-validation-evidence` at the repository root. The manifest records original paths, sizes, Git blob IDs, SHA-256 hashes, and ordered part hashes. Parts are storage containers, not standalone JSON files. Original report hashes remain unchanged after restoration.

From anywhere inside the repository, using PowerShell 7:

```powershell
pwsh -File regression-artifact-validator/scripts/powershell/share-large-artifacts.ps1 -Mode Verify
pwsh -File regression-artifact-validator/scripts/powershell/share-large-artifacts.ps1 -Mode Restore
```

Restoration refuses to overwrite differing local files. Use `-DestinationRoot C:\path\to\separate-folder` to restore a separate copy. `-Mode Pack -Bundle shared-artifacts/new-bundle` packages oversized staged blobs into a new directory and verifies byte-exact reconstruction. Original files must remain unstaged when committing their parts; no source evidence or review approval is changed by packaging.

## Purpose Groups

### Petroleum supplemental source ingestion

From the module root, run `.\framework.ps1 -Task petroleum-ingest-build`,
`.\framework.ps1 -Task petroleum-ingest-check` and
`.\framework.ps1 -Task petroleum-ingest-test`. Python with `pypdf` is required
(the manifest records the installed extractor version). `--check` re-extracts the
registered PDF and compares every output without writing; source, lesson-input,
generator, page-text and output SHA-256 hashes expose changed or stale evidence.
Regenerate after an intentional extractor change and review the new bytes.

The original [Petroleum PDF](../specifications/ATL105/docs/specs/BUYPASS_Platform_Petroleum_Industry_Processing_Specifications__2025-1.pdf)
remains beside ATL105 2026-3 and 2026-4. The
[knowledge index](../specifications/ATL105/docs/specs/kb/00-index.md#supplemental-petroleum-source-2025-1)
links the complete page corpus, bookmarks and 13 explicitly curated draft lessons.
Only these quoted, scoped lessons enter the draft teaching pack; raw pages are
retrieval evidence, not generated business requirements. Review questions and
cross-version/card/flow conflicts remain open. No model weights, canonical counts,
SME decisions or execution gates change.

For read-only all-term lexical retrieval, run:

```powershell
python .\scripts\ingest-petroleum-spec.py --query "MasterCard expiration"
```

Each hit retains physical PDF page, printed label and extracted-text hash. Empty
results are explicit; table/diagram semantics still require visual source review.

ATL105 sections 10.6 and 13.2 refer fleet processing to Petroleum 2025-1. Build,
check and test the versioned cross-reference map with
`petroleum-atl105-crossref-build`, `petroleum-atl105-crossref-check` and
`petroleum-atl105-crossref-test`. It records the 13 direct locations found in
each ATL105 release, including Section 10.6 and Elements 2, 31, 40, 47, 64, 108
and 158-163. These references cue fleet-only, issuer-specific rule lookup; they
do not override ATL105 field/format rules or establish current-release authority,
SME approval, execution readiness or model-weight training.

### Petroleum context training

From the module root, run `.\framework.ps1 -Task petroleum-train-build`,
`.\framework.ps1 -Task petroleum-train-check` and
`.\framework.ps1 -Task petroleum-train-test` for the MasterCard Fleet pilot, and
`petroleum-visa-train-build`, `-check` and `-test` for Visa Fleet. Card-program
differences (BINs, Prompt Code card type, prompt selector, track positions and
mutations) live in a plug-in such as `scripts/petroleum_visa_fleet.py`, selected
by `program` in `context-input.json`. Voyager chapter 9 uses
`petroleum-voyager-train-build`, `-check` and `-test`; its source catalog is
implemented in `scripts/petroleum_voyager.py`. Other contexts use
`python .\scripts\train-petroleum-context.py --context <folder>` once their
`context-input.json` exists. Ingestion outputs must be current first.

Each build checks the
[training plan](../specifications/ATL105/docs/specs/kb/petroleum-2025-1/training-plan.json)
(phases 0 to 10, steps, gate mapping and chapter-aligned rollout) and a context's
curated input. Every Petroleum quote must resolve on its stated page inside the
scope, every scoped bookmark and rule item must be covered, ATL105 evidence must
resolve on the stated page of both 2026-3 and 2026-4, and `PETROLEUM_ONLY` claims
must be absent from both ATL105 texts. Track layout rows, BIN ranges repeated across
pages, enumerations and printed examples (BIN, Mod 10, field lengths) are checked
against the source. Example anomalies stay `REVIEW_REQUIRED`. Phase status only
reaches an SME decision through a complete approval record; counts of BR/TS/TC/TD
and executed cases stay zero until later phases supply independent evidence.

When a context has `chain-input.json`, the same build runs phases 4 to 8 through
`petroleum_chain.py`: transactions must match the scoped list and quote ATL105
Appendix G in both releases; Product Type prompts must agree with the source
enumeration; every rule item needs a draft BR; every BR, supported matrix cell and
flow transition needs a scenario; each generated TD must give exactly the expected
rule-validator outcome; and every mutation operator must be caught. Results go to
`chain-package.json` and are reported as draft counts, never as independent
Test Solution counts or executed cases. Run `.\framework.ps1 -Task petroleum-chain-test`.

A context may add `scope.appendixPages` for document-local Petroleum appendices; each
range must start at its own appendix bookmark and stay inside it. `catalog.productCodes`
re-extracts every Appendix B row, checks the categories span 000 to 999 without gaps,
and compares each row with ATL105 Appendix F in both releases; the build fails unless
the differences equal `expectedDifferences`. Scenarios whose expectation depends on a
recorded source conflict carry `oracleConflict` and are reported as conflicting oracles.

### All-network requirements and lifecycle review

From the module root, run `.\framework.ps1 -Task all-network-review-build`,
`.\framework.ps1 -Task all-network-review-check`, and
`.\framework.ps1 -Task all-network-review-test`.
The [curated input](../specifications/ATL105/docs/specs/kb/segment-100/all-network-requirements-review.json)
defines seven named network groups (AMEX, Visa, Mastercard, Discover, Diners Club,
JCB and STAR Signature Debit) plus separate generic Debit and EBT groups.
Credit is a program, not a network. The original 145 mixed-dimension requirements
remain a shared library; Section 5 categories and financial/receipt/transaction codes
are secondary dimensions, never substitute network groups.
The [published supplement](../specifications/ATL105/test-output/test-solution-independent-review/all-network-requirements-review-v1/README.md)
includes a network index, 176 contextual draft BR/TS records under 12 review profiles,
44 explicitly selected conditional flow contexts, ten BA/SME questions and byte
provenance. Every shared ID is linked or explicitly unmapped (64 linked, 81 unmapped).
The BA business tree is platform -> spec/version -> card program -> network/generic
group -> transaction category -> presence/entry/channel -> flow/leg -> BR/TS/TC/TD.
Commercial usage is orthogonal to funding program; presence, entry mode and channel
are separate. Unresolved values are review gaps, not wildcard eligibility.
The generator reuses the Mastercard source-resolution, hashing and CSV-safety helpers;
both generator and helper hashes are recorded.

Only curated classifications and conditional source rules enter the draft lessons.
The unapproved contextual bindings, unanswered questions, external contracts and 2026-4
Appendix AF release overlay do not become executable policy or 2026-3 teaching truth.
The selected flow contexts inventory questions, not supported combinations. It never silently
infers identity from a receipt label or card code. Source locations/hashes do not
prove semantics, source completeness, host outcomes or approval.
Review format 2 replaces the former 25 mixed primary contexts and 850-cell cross-product.
The primary `requirements-review.csv` now contains contextual records; the original
145-row format is preserved in `shared-requirements-library.csv`. The JSON
`requirements` array remains the shared library; `businessNavigation` holds the
business tree, nine groups, profiles, contextual `businessRequirements`,
`testScenarios` and empty `testCases`/`testData` arrays. `chain-gaps.csv` exposes
missing concrete cases, synthetic payloads and independent oracles. No artificial
TC/TD IDs or complete chains are counted. `shared-library-mapping.csv` retains every
unassigned shared requirement for source-backed review, including closed-loop SVC
rules that must not be assigned automatically to open-loop prepaid.
No canonical BR/TC/TD counts, model weights, execution gates or coverage are promoted.
Rebuilding replaces generated files; return BA edits separately and persist approved
decisions through the existing SME workflow before updating curated input.

### Mastercard BA reference review

From the module root, run `.\framework.ps1 -Task mastercard-review-build`,
`.\framework.ps1 -Task mastercard-review-check`, and
`.\framework.ps1 -Task mastercard-review-test`.
The builder reads the curated independent
[review input](../specifications/ATL105/docs/specs/kb/segment-100/mastercard-ba-reference-review.json),
resolves unique source locations and fingerprints, and publishes the corrected
37-scenario CSV, nine-question BA/SME questionnaire, 13-lesson draft teaching pack,
email draft and provenance under
[mastercard-ba-reference-review-v1](../specifications/ATL105/test-output/test-solution-independent-review/mastercard-ba-reference-review-v1).
`--check` is read-only and fails for missing/stale outputs or changed inputs/sources.
The curated lesson statements are independently authored review drafts, not semantic
facts proven by string matching. Source lookup only verifies location and hashes.
The screenshot and unanswered questions are never training truth, and no model
weights, canonical BR counts, SME decisions or execution gates are promoted.
BA edits belong in a separate returned copy of the CSV; rebuilding replaces generated
outputs. Persist authorized outcomes through the existing SME decision workflow
before revising the curated input.

| Group | Tools | Ownership |
|---|---|---|
| Intake and provenance | `analyze-oct05-handoff.py`, `assess-late-traceability-matrix.py`, `detect-ai-run-duplicates.py`, `build-ai-run1-4-combined-crosswalk.py`, `audit-ai-br-chain-maximum-coverage.py`, `verify-ai-run-intake.py` | Preserve producer data; identify byte-identical content; and build provenance-preserving BR/scenario/TC indexes and per-BR chain-coverage audits. |
| AI BR validation groups | `group-ai-br-validation-results.py` | Attribute current Run4 bounded rule outcomes to every BR chain and group by chain completeness and Test BR candidate presence. |
| AI BR inventory | `export-ai-br-test-model-inventory.py`, `export-ai-test-br-crosswalk.py` | Export AI BR inventories and exact source-anchor candidate links to the independent Test BR baseline; candidates remain review-required. |
| AI/Test message categories | `build-ai-test-message-category-inventory.py` | Inventory every AI and Test BR/TS/TC/TD with message family, direction, explicit POS/card codes, domain type, segment scope and evidence basis; undeclared categories remain explicit and are never inferred from segment names. |
| AI authorization-completion lifecycle | `review-ai-authorization-completion-chains.py` | Screen explicit AI Authorization→Purchase/Capture flows, verify per-step physical payload/metadata against the frozen V6 inventory, and emit case, BR→TS→TC→TD, and candidate Test BR mapping matrices. Findings remain structural; no host or semantic approval is inferred. |
| Test BR network grouping | `group-test-brs-by-network-type.py` | Group every BR in the complete independent package by explicit Section 5 `networkType`; supplement the blocked Section 5 cross-context BR and retain all unassigned BRs without inference. |
| Scenario network grouping | `categorize-ai-test-scenarios-by-network.py` | Group approved scenarios and linked candidate test cases using only explicit `flow_axis_values.card_brand`; preserve unknown and undeclared values. |
| AI network/card-type coverage gate | `audit-ai-network-cardtype-coverage.py` | Audit AI candidate cases against the declared card-brand axis and all 36 financial Prompt Code card codes; bind physical data to the delivery inventory and report missing codes separately. |
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
$deliveryRoot = 'C:\path\to\immutable-delivery-root'
python.exe .\scripts\detect-ai-run-duplicates.py .\specifications\ATL105\test-input\ai-solution\runs\2026-10-07 .\specifications\ATL105\test-output\ai-run-deduplication-2026-10-07-v2.json
python.exe .\scripts\detect-ai-run-duplicates.py --self-test
python.exe .\scripts\export-ai-test-br-crosswalk.py --ai-requirements .\specifications\ATL105\test-input\ai-solution\runs\2026-10-10\Run1\step5_requirements\approved\requirement_catalog.json --test-solution-directory .\specifications\ATL105\test-output\test-solution-independent-review --output-directory .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-14-ai-test-br-crosswalk-v1
python.exe .\scripts\build-ai-test-message-category-inventory.py --ai-root "$deliveryRoot" --test-package .\specifications\ATL105\test-output\test-solution-independent-review\complete-all-test-solution-br-ts-tc-td-package.json --output-directory .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-16-message-category-inventory-v1
python.exe .\scripts\build-ai-test-message-category-inventory.py --self-test
python.exe .\scripts\review-ai-authorization-completion-chains.py --delivery-root "$deliveryRoot" --test-package .\specifications\ATL105\test-output\test-solution-independent-review\complete-all-test-solution-br-ts-tc-td-package.json --full-review-directory .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-16-all-ai-br-chain-test-br-review-v2 --output-directory .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-16-ai-auth-completion-chain-review-v6
python.exe .\scripts\categorize-ai-test-scenarios-by-network.py --approved-scenarios "$deliveryRoot\scenarios\approved\approved_scenarios.json" --candidate-scenarios "$deliveryRoot\scenarios\candidates\candidate_scenarios.json" --test-cases "$deliveryRoot\test_generation\test_generation\candidates\test_case_candidates.json" --output-directory .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-15-ai-scenario-network-groups-v1
python.exe .\scripts\group-test-brs-by-network-type.py --test-package .\specifications\ATL105\test-output\test-solution-independent-review\complete-all-test-solution-br-ts-tc-td-package.json --network-type-matrix .\specifications\ATL105\test-output\test-json\section-5-payment-network-segment-100-matrix.json --output-directory .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-15-test-brs-by-network-type-v2
python.exe .\scripts\audit-ai-network-cardtype-coverage.py --delivery-summary .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-14-delivery-wide-assessment-v6\summary.json --card-type-oracle-source .\src\main\java\com\coreauth\validator\canonical\Segment100CardTypeOracle.java --output-directory .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-16-ai-network-cardtype-coverage-v4
python.exe .\scripts\build-ai-run1-4-combined-crosswalk.py --runs-root .\specifications\ATL105\test-input\ai-solution\runs\2026-10-07 --analysis-dir .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-07-Run4\analysis-v2 --test-solution-dir .\specifications\ATL105\test-output\test-solution-independent-review --dedup-report .\specifications\ATL105\test-output\ai-solution-independent-review\ai-run-deduplication-2026-10-12.json --output-dir .\specifications\ATL105\test-output\ai-solution-independent-review\combined-runs-2026-10-12-crosswalk-v4
python.exe .\scripts\summarize-ai-technical-validation-coverage.py --field-structure-report .\specifications\ATL105\test-output\Run1-2026-10-14-full-delivery-field-structure-execution-v3.json --baseline .\specifications\ATL105\test-output\test-solution-independent-review\source-derived-requirement-inventory.json --delivery-summary .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-14-delivery-wide-assessment-v6\summary.json --output-dir .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-14-technical-validation-coverage-full-recursive-v2
python.exe .\scripts\summarize-ai-technical-validation-coverage.py --self-test
python.exe .\scripts\audit-ai-br-chain-maximum-coverage.py --combined-crosswalk .\specifications\ATL105\test-output\ai-solution-independent-review\combined-runs-2026-10-12-crosswalk-v4\combined-ai-br-test-solution-crosswalk.jsonl --run4-crosswalk .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-07-Run4\analysis-v2\run4-independent-br-crosswalk-v2\run4-ai-br-chain-crosswalk.jsonl --run1-requirements .\specifications\ATL105\test-input\ai-solution\runs\2026-10-07\Run1\pipeline_outputs_2026-10-07\sample_req_vision\requirements.json --run1-suite .\specifications\ATL105\test-input\ai-solution\runs\2026-10-07\Run1\pipeline_outputs_2026-10-07\src\pipeline\reporting\output\segment-100-suite-catalog.json --test-solution-dir .\specifications\ATL105\test-output\test-solution-independent-review --output-dir .\specifications\ATL105\test-output\ai-solution-independent-review\2026-10-12-ai-br-chain-maximum-coverage-v3
mvn.cmd -q exec:java "-Dexec.mainClass=com.coreauth.validator.canonical.Atl105FieldStructureRuleRunner" "-Dexec.args=specifications/ATL105 Run4-2026-10-07 specifications/ATL105/test-input/ai-solution/runs/2026-10-07/Run4/pipeline_outputs_2026-10-07/src/pipeline/test_generation/candidates/qe_shaped_test_data specifications/ATL105/test-output/Run4-field-structure-execution-v11.json"
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

Scenario network groups use only the producer's explicit `flow_axis_values.card_brand`. The report retains the raw code and labels the normalized network as a card-brand proxy, not a direct network-type assertion. It does not infer a network from titles, transaction types, card descriptions, or payload fields. Unmapped, multi-brand, and undeclared scenarios remain distinct; all source catalogs and the script are hash-bound in the report manifest.

The AI dimension audit checks network/card-brand axis presence separately from the 36 financial Prompt Code card-type codes. It excludes conflicting Test Case IDs and validates physical inputs against the frozen delivery inventory. It reports observed network/card-code combinations for review only; no Cartesian combination denominator or semantic compatibility is inferred. A delivery passes only when each configured brand value and each card code has at least one eligible test case.

The Test BR network grouping uses only explicit Section 5 `networkType` properties from the independent Test Solution package. It adds the separate Section 5 cross-context rule from the taxonomy matrix when it is absent from the active package. Other BRs remain in `NO_NETWORK_TYPE_DECLARED`; Visa/Mastercard words, card types, and crosswalk matches are not network assignments. The CSV is exhaustive over the active package plus the supplemental matrix BR and the manifest binds both inputs.

For a full candidate-delivery pass, invoke the field runner on the immutable delivery root with `--recursive`. It scans every `qe_shaped_test_data` directory, uses root-relative metadata paths as message IDs so duplicate basenames remain distinct, records payloads with no metadata sidecar as unpaired (never synthesizing metadata), and checks input inventory/hash stability. The technical summary reports paired messages and orphan counts; this is still bounded field/structure execution, not validation of archives/auxiliary formats, semantic BR correctness, or host execution.

The targeted cohort seed is intentionally separate from the blind AI coverage measurement. Its generation context contains ATL105 excerpts and source anchors but omits Test BR IDs/titles/statements; those appear only in the separate evaluation map. The chain file contains unfilled BR→TS→TC→TD templates. Source stubs remain review-required, and the seed pack itself is not generated test coverage.

Review-directory and jsdom paths are resolved from the caller's directory before the launcher changes working directory. Python defaults to `python.exe`; use `-PythonPath` when a particular interpreter is required. Failures propagate as task errors, and the caller's directory is restored. Task launch does not install packages or grant approval.

## Retention

`validate-ai-delivery-evidence.py` consumes a **completed** delivery audit, not
an incomplete inventory. It independently compares both `requirements` and
`flat_rows` in `traceability_matrix_full.json` to direct catalog identities and
BR/TS/TC/TD edges. Every physical payload, including unclaimed payloads, receives
one row in `test-data-validation.jsonl`. Catalogs are streamed with `ijson`.

```powershell
python.exe .\scripts\validate-ai-delivery-evidence.py --self-test
python.exe .\scripts\validate-ai-delivery-evidence.py `
  --audit-directory .\specifications\ATL105\test-output\ai-solution-independent-review\av1021-v2 `
  --output-directory .\specifications\ATL105\test-output\ai-solution-independent-review\eg1021-v1
```

Run from the module directory; use a new, short output directory on Windows.
The gate verifies audit ledger hashes and current delivery hashes, binds source
dependencies, and rechecks delivery population before publishing its summary.
It reuses `apply-qe-feedback-rules.py`'s **payload-leg checks only**, not its
full BR/TS/TC feedback workflow. Case/sidecar/declared-path identity must resolve;
conflicting/duplicate case IDs and ambiguous legs do not receive feedback credit.
Each TD lists rule observations and unevaluated payload rules. Findings omit
payload values; QE/source conflicts are retained, not adjudicated.

Optional `--element-report` requires an existing
`ATL105-AI-RUN-ELEMENT-VALIDATION` report with `chapter13Semantics=true`,
matching root-relative paths, file hashes, metadata and TC identity. Its bounded
predicate verdicts never establish full TD semantic correctness. A deliberately
invalid negative fixture is not automatically an AI defect. Neither a feedback
PASS nor a matrix match proves BR meaning, all conditional/allowed-value rules,
cross-field conflicts, negative objectives, lifecycle correctness or host results.
These remain explicit unassessed dimensions; the delivery status remains
`BLOCKED_UNPROVEN_SEMANTIC_CORRECTNESS`. Matrix set reconciliation does not
validate repeated row counts, producer coverage/status claims, the Markdown
matrix, physical `traceability.json`, archives or other auxiliary formats.

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