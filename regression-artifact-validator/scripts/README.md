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

### Release-aware new-format validation and readiness

Run `.\framework.ps1 -Task new-format-readiness-build`,
`new-format-readiness-check` and `new-format-readiness-test` from the module root.
The [V2 contract and readiness guide](../specifications/ATL105/docs/new-format-release-validation.md)
defines multi-chain input, independent 2026-3/2026-4 profiles, separate structured
semantic/predicate/wire/lifecycle results, synthetic chains, release crosswalks,
draft lessons and explicit complete-validation blockers.

```powershell
python.exe .\scripts\new-format-validation.py --input 'C:\review\ai-v2.json' --output 'C:\review\new-assessment'
```

The output directory must not exist. Input bytes, producer-local IDs, unknown
observations, metadata and approval claims are retained without promotion.
The existing `new-format-sample.py --input ...` entry point also routes the
explicit V2 format; its V1 sample contract remains unchanged.
Ten target-release profiles and three baseline profiles are a scoped
implementation, not whole-spec readiness. The readiness report stays BLOCKED
until the remaining independent evidence/approval/coverage/execution gates close.
No Java execution-ready canonical or host certification is inferred.

### New-format BR/TS/TC/TD sample and bounded intake

Run `.\framework.ps1 -Task new-format-sample-build`, `new-format-sample-check`
and `new-format-sample-test` from the module root.
The [sample and validation process](../specifications/ATL105/docs/new-format-sample-and-validation-process.md)
describes the BA hierarchy and a concrete 1 BR / 2 TS / 6 TC / 6 TD example.
`new-format-sample.py --input <JSON> --output <new-directory>` preserves the input,
checks draft structure/traceability/context and runs an independent Element 86
six-ASCII-digit/range predicate against logical TD observations. The Test source,
not AI expected results, supplies the bounded oracle. Wrong implemented
expectations fail; unsupported claims/contexts remain NOT_ASSESSED.
This is not a general semantic validator, full-message serializer or host runner.
The normalized review envelope must not be submitted as an execution-ready
canonical package. Network eligibility, prose meaning, full wire and host outcomes
remain gated; no canonical counts or coverage are promoted.

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

Segment-free requirements: ATL105 places some fields outside any data segment
(Data Section 1 of requests/responses and positional totals, mail, comm-test,
download and key-load layouts) and defines every element in the segment-agnostic
Chapter 13 dictionary. `build-location-scoped-requirement-inventory.py` derives
`location-scoped-requirement-inventory.json` from the pinned 2026-3 source text:
131 `MESSAGE_LOCATION` rules from 28 Chapter 11 layouts and 232
`ELEMENT_DEFINITION` rules (Element 118's two definitions are kept and flagged).
It supplements, and never modifies, the frozen 601-rule baseline; run `--check`
to detect drift. When that file is present in `--test-solution-directory`, the
auditor also matches AI BRs that have element anchors but no lookup-eligible
segment by element number against baseline pseudo-location rules
(`DATA-SECTION-1`, `FINANCIAL-RESPONSE`) and the supplement, and links every
element-anchored BR to its dictionary rule. Each BR gets a
`locationAwareAssessment` resolution: `NON_SEGMENT_LOCATION_CANDIDATE`,
`SEGMENT_SCOPED_ELEMENT_SEGMENT_OMITTED` (an AI defect candidate listed in
`segment-omission-findings.jsonl`), `ELEMENT_DEFINITION_ONLY_LOCATION_UNKNOWN` or
`ELEMENT_NOT_IN_TEST_INVENTORY`. Original measures are unchanged; the
`locationAware*` (601 denominator), `supplement*` and `extended*` (601 +
supplement) measures are reported separately and are structural candidates only.
`location-aware-review-queue.jsonl` and `supplement-test-br-ledger.jsonl` carry
the pending pairs. TransArmor key-load layouts (11.7.5) live in a separate
document and remain out of scope.

POS Entry Mode supplement: `build-pos-entry-mode-supplement.py` derives a
source-pinned package at
`test-output/test-solution-independent-review/pos-entry-mode-supplement-v1`.
It adds atomic BRs for Segment 111 Table 005 format, Appendix J PAN/capability
code values, lifecycle persistence/exception handling, COF/Discover constraints,
capability hierarchy, RFID/gift/fallback behavior and token-entry-mode review
boundaries. It also emits network-scoped scenarios for seven networks plus
generic Debit and EBT, and includes positive/negative/review TC+TD payloads.
Every BR quote is page-checked against the pinned 2026-3 source and every test
payload is self-validated by a reference oracle (build fails on mismatch). The
package includes a supersession overlay that:
- decomposes composite `BR-SEG100-ANN-J` into atomic `BR-POSEM-*` requirements,
- narrows `BR-SEG100-APPO-ENTRY-MODE` to review-only semantics (`BR-POSEM-045`)
  until token code mapping is source-approved, and
- supersedes the defective `TD-SEG100-APPO-ENTRY-MODE` fixture.
Frozen canonical packages are not edited by this builder.

The shared candidate matcher is captured as `crosswalk-implementation.py` in
each output directory and that exact snapshot is executed/hash-verified; edits
to the working exporter cannot silently change the matching policy mid-run.
The complete file population and hashes are checked again before publication.
A changing delivery/analyzer aborts; preserve the partial directory and use a
new output directory for the corrected replay. Outputs include full-baseline
Test BR rows, anchor/pair queues, incomplete TC declarations and chain-gap rows.

Keep immutable intake under `specifications/ATL105/test-input`; place independent outputs under the pack's `test-output` and published views under `reports`. Preserve prior report snapshots and their recorded hashes. Python caches and Maven build outputs are ignored, not evidence.

The former module-root Segment 103 report is retained under `specifications/ATL105/reports/legacy/module-root`. Its SHA-256 at relocation was `D570CA9A925A432147BA8B26749B5B5EC90A3711011EEEAD237DED9C155577C7`; this is a historical snapshot, not the latest validation result. New Segment 103 reports use `Atl105Paths.consolidatedReport("103")`.