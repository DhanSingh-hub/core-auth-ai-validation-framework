# Regression Artifact Validator

Java validation framework and ATL105 specification pack for independently reviewing AI-generated business requirements, scenarios, test cases, test data, and traceability.

## Repository map

```text
regression-artifact-validator/
|-- framework.ps1                     Working-directory-safe task launcher
|-- framework-tasks.json              Named build, test and assessment tasks
|-- contract/                         Producer-neutral schemas and vocabulary
|-- scripts/                          Cohesive Python/Node assessment pipelines
|   `-- powershell/                   PowerShell export and legacy reporting tools
|-- src/main/java/                    Validation engine and adapters
|-- src/test/java/                    Unit and governance tests
|-- src/test/resources/               Test features and fixtures
|-- specifications/ATL105/
|   |-- contract/                     ATL105 semantic aliases
|   |-- docs/specs/kb/                Source-grounded segment rule catalogs and training report
|   |-- docs/test-validation-strategy/ Review policy and evidence gates
|   |-- reports/                      Published stakeholder reports
|   |   `-- legacy/module-root/       Preserved legacy module-root reports
|   |-- schemas/                      ATL105 validation schemas
|   |-- scripts/                      ATL105-specific pipelines
|   |-- test-input/                   Immutable AI runs and controlled fixtures
|   `-- test-output/                  Independent baselines and validation evidence
`-- target/                           Disposable Maven build output
```

See [ATL105 Artifact Storage Policy](specifications/ATL105/docs/test-validation-strategy/ARTIFACT-STORAGE-POLICY.md) for ownership and retention rules.
See [Test Solution Framework Ownership](specifications/ATL105/docs/test-validation-strategy/TEST-SOLUTION-FRAMEWORK-OWNERSHIP.md) for generation order, module boundaries and review gates.
See the [automation guide](scripts/README.md) for task selection, tool dependencies and script ownership. Existing module-level `test-output` presentation deliveries are retained for compatibility; new ATL105 evidence belongs under the specification pack. Do not delete historical reports or immutable AI inputs as build cleanup.

## Framework review

The [2026-10-05 completeness and optimization review](specifications/ATL105/reports/FRAMEWORK-COMPLETENESS-AND-OPTIMIZATION-REVIEW-2026-10-05.md) records the merged DL1-DL8 baseline, four full-suite failures, indexing and validation-control gaps, and prioritized improvements with acceptance criteria. It is a review snapshot, not approval or certification.

## Current validation state

The [segment training report](specifications/ATL105/docs/specs/kb/SEGMENT-TRAINING-EXECUTION-REPORT.md) reconciles rule catalogs with [training gate decisions](specifications/ATL105/training-status.json). At the current snapshot, 49 segments are in progress with 601 catalogued rules; none is certified for independent AI-artifact intake. The first business transaction context, Mastercard Credit Purchase, is `IN_PROGRESS` with 168 of 189 in-scope BRs trained through canonical BR -> TS -> TC -> TD chains; see its [BR coverage report](specifications/ATL105/test-output/test-solution-independent-review/mc-credit-purchase-br-coverage.md) and regenerate it with `python scripts/train-mastercard-credit-purchase.py`. Its source FR-matrix workbook is validated line by line by `python scripts/validate-mastercard-fr-matrix.py` (current verdict `REJECTED_NOT_INTAKE_READY`; see the [workbook validation](specifications/ATL105/test-output/test-solution-independent-review/mc-fr-matrix-validation-v1/README.md)). The [element chain report](specifications/ATL105/test-output/test-solution-independent-review/atl105-element-chain-coverage.md) counts 539 placeholder-generated structural links and 0 executable links; structural completion is not SME approval or executable BR-to-test-data coverage. See the [remaining limitations](specifications/ATL105/test-output/test-solution-independent-review/atl105-remaining-limitations-status.md) for the separate approval and execution measures.

The [source-backed rule gate](specifications/ATL105/test-output/test-solution-independent-review/atl105-source-backed-rule-gate.md) tracks all 601 rules with source-body navigation, and separately reports resolvable citations, curated assertions, draft cases and SME approval. Body locations are reference-only. Its Element 83 examples remain review-required and are not inserted into the execution package.

Run1 and Run2 remain evidence under independent review, not Test Solution training truth. See the [RAID Log](specifications/ATL105/docs/test-validation-strategy/RAID-Log.md) and [composite feedback](specifications/ATL105/docs/test-validation-strategy/AI-SOLUTION-FEEDBACK-RUN1-RUN2-COMPOSITE.md) for delivery history.

## Producer-neutral contract

- [Vocabulary](contract/vocabulary.json)
- [Canonical anchor schema](contract/canonical-anchor.schema.json)
- [Artifact package schema](contract/artifact-package.schema.json)
- [ATL105 semantic aliases](specifications/ATL105/contract/semantic-aliases.json)

AI and Test Solution artifacts keep producer-local IDs. Canonical source anchors and explicit, evidence-backed crosswalks provide shared identity. Heuristic or alias matches remain `REVIEW_REQUIRED`.

## Validation

Preferred entry point, from this module directory:

```powershell
.\framework.ps1 -List
.\framework.ps1 -Task structure
.\framework.ps1 -Task test -Tests 'VisaEstimatedIncrementalAuthorizationValidatorTest'
.\framework.ps1 -Task training-ae
.\framework.ps1 -Task semantic-self-test -PythonPath 'C:\Program Files\Python314\python.exe'
```

From the workspace root, use `regression-artifact-validator/framework.ps1` with the same options. Tasks run inside the module and restore the caller's directory. `-DryRun` displays the command without running it. Maven, Python and Node must already be installed; the launcher does not install dependencies, rewrite producer inputs or approve evidence. Direct commands below remain supported.

### ATL105 Excel validation reference

Open the [ATL105 2026-3 validation workbook](specifications/ATL105/reports/ATL105-2026-3-Validation-Reference.xlsx)
and begin with **START HERE**. It includes element definitions/values, all 49 segment inventories
and source-derived field rows, the 31 message layouts, transaction/card/response codes,
source-backed rule candidates, source conflicts, and a BR/scenario/case/data review register.
Source pages, line references and input SHA256 hashes are retained for traceability.

**Segment Subsets** enumerates all conditional/optional subsets within each represented template,
with required segments retained and template ordering preserved. These are structural candidates,
not an approved list of valid transactions. Repetition counts, arbitrary reordering and a cross-product
of all field values are not enumerated. Transaction/card prompt combinations likewise require
contextual approval. Nested appendix layouts remain source evidence rather than fully normalized
executable constraints; external TransArmor details remain blocked. The workbook does not run
AI-artifact validation or certify coverage.

Regenerate and test using Node.js (built-in modules only; no Excel installation required):

```powershell
node .\scripts\export-atl105-validation-workbook.cjs
node --test .\scripts\export-atl105-validation-workbook.test.cjs
```

The exporter accepts optional ATL105 pack and output workbook paths as its first two arguments.
It creates a separate comprehensive workbook without overwriting the existing element-only export.

Run from this module directory:

```powershell
mvn test
```

Focused governance checks:

```powershell
mvn '-Dtest=ProducerNeutralContractTest,RepositoryStructureTest,Atl105PathsTest' test
```

## Unified AI validation

The unified runner emits one canonical envelope per test case, joining profile-driven intake evidence, source hashes, structural defect candidates and response-code-family checks. It supports the catalog arrays documented in the runner and records the selected catalog paths and input hashes. Add a versioned input profile when a new metadata/payload layout appears; unsupported layouts are not guessed.

Run the complete Run4 assessment from this module directory:

```powershell
$run = 'specifications/ATL105/test-input/ai-solution/runs/2026-10-07/Run4/pipeline_outputs_2026-10-07/src/pipeline/test_generation/candidates/qe_shaped_test_data'
$scenarios = 'specifications/ATL105/test-input/ai-solution/runs/2026-10-07/Run4/pipeline_outputs_2026-10-07/src/pipeline/scenarios/approved/approved_scenarios.json'
$cases = 'specifications/ATL105/test-input/ai-solution/runs/2026-10-07/Run4/pipeline_outputs_2026-10-07/src/pipeline/test_generation/candidates/test_case_candidates.json'
$supplementalFlowCatalog = 'specifications/ATL105/test-input/ai-solution/runs/2026-10-07/Run1/pipeline_outputs_2026-10-07/src/pipeline/reporting/output/segment-100-suite-catalog.json'
$report = 'specifications/ATL105/test-output/ai-unified-run4-compact-v4.json'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.canonical.AiUnifiedValidationRunner' "-Dexec.args=specifications/ATL105 $run $scenarios $cases $report --chapter13-semantics --supplemental-flow-catalog $supplementalFlowCatalog"
```

The compact JSON summary references `run4-unified-compact-v4-cases.jsonl.gz`, a gzip JSONL sidecar containing one case envelope per test case. Each envelope includes source hashes, exercised rule findings and declared-negative target disposition. The summary groups coverage by source rule, negative-target dispositions and stable `OG-…` oracle-gap IDs with bounded case samples. Negative-target detection is diagnostic only; it is not isolated mutation-effectiveness certification. Supplemental suite catalogs can be passed with `--supplemental-flow-catalog` to audit declared flow IDs separately.

`AI_DEFECT` reports automated structural/declaration defect candidates; `DISAGREE` means a response-family rejection or mismatch against an approved case outcome; `AGREE` is reserved for complete expected-outcome agreement and is not inferred from code-family permission; `ORACLE_GAP` means the required independent outcome oracle is unavailable or conflicting. None of these results is business or execution certification.

### Case-level outcome adjudication

Prepare a pending register from an existing summary and its hash-verified case-envelope sidecar (use a new output path on subsequent preparations):

```powershell
$pack = 'specifications/ATL105'
$register = "$pack/test-output/run4-case-outcome-oracles-2026-10-12-v1.json"
mvn -q exec:java '-Dexec.mainClass=com.coreauth.validator.canonical.AiCaseOutcomeOracleRegister' "-Dexec.args=--prepare $pack $report $register"
```

Preparation includes every `ORACLE_GAP` case with its scenario and physical input hashes. Expected outcomes remain null, all entries remain `PENDING`, and no AI-declared value is copied into an independent oracle. Draft Section 10.2 device-state vectors are not Run4 host outcome approvals.

An authorized reviewer supplies a decision JSON containing `testCaseId`, `previousRegisterSha256`, `status` (`APPROVED`, `REJECTED`, or `PENDING`), `reviewer`, `reviewedAt` (ISO-8601 instant), and `rationale`. Approval also requires:

- `completeness: "COMPLETE_CASE_OUTCOME"` and `independenceAttestation: "I_REVIEWED_SOURCE_INDEPENDENTLY_OF_AI_OUTPUT"`.
- `expectedResponse`: the independently authored complete response assertion object, with a one-character `response_code`. Use the case's canonical field names; field `{ "value": ... }` wrappers are compared by value, not producer confidence/flags. Every asserted field must match, with no extra fields.
- `evidence`: non-empty rows with `type` (`ATL105_SPECIFICATION`, `INDEPENDENT_EXECUTION`, or `AUTHORIZED_REVIEW_RECORD`), `sourcePath`, `sha256`, and `locator`. Relative paths resolve under the ATL105 pack. Evidence files must exist, hash-match, and be outside the current producer input and both catalogs. Keep runtime evidence masked; a source quote alone does not establish a host outcome without case-state/lifecycle context.

Record a review into a **new** register version, retaining the previous version and each case's review history:

```powershell
$reviewed = "$pack/test-output/run4-case-outcome-oracles-2026-10-12-v2.json"
$decision = "$pack/test-output/reviewer-case-outcome-decision.json"
mvn -q exec:java '-Dexec.mainClass=com.coreauth.validator.canonical.AiCaseOutcomeOracleRegister' "-Dexec.args=--record $pack $run $scenarios $cases $register $decision --new-register $reviewed"
```

The decision's prior-register hash prevents applying a review to a different snapshot. Reviewer identity/authorization and completeness are explicit attestations, not automatically authenticated or inferred by the tool. Only a reviewer can adjudicate applicability and host preconditions; agents must not invent approvals.

Pass `--case-outcome-oracle-register <reviewed-register.json>` to the unified runner, using a new report path. Input-catalog hashes, scenario ID, payload/metadata bindings and independent evidence are checked. Approved outcomes produce `MATCH`/`MISMATCH`; pending, rejected, stale bindings and family-oracle conflicts never grant agreement. Structural/declaration defects retain precedence. Missing AI expected assertions remain mismatches/defects, not silently filled from the independent oracle. An expected-response match does not certify field presence, wire conversion, lifecycle execution, host results, or semantic BR coverage.

Full Run4 verification completed with [the case-oracle v5 report](specifications/ATL105/test-output/ai-unified-run4-case-oracles-v5.json) and its hash-verified gzip case sidecar: 21,531 unique cases, unchanged verdicts (1,022 `AI_DEFECT`, 1,346 `DISAGREE`, 19,163 `ORACLE_GAP`). The [v1 oracle register](specifications/ATL105/test-output/run4-case-outcome-oracles-2026-10-12-v1.json) contains 19,163 pending entries, all reported as `REVIEW_REQUIRED`; the other 2,368 cases have `NOT_PROVIDED`. Zero case outcomes are approved. The 34 focused runner/comparator/intake/defect tests pass; independent sidecar/count/register-hash checks pass. Authorized outcome review remains open.

After changing a segment catalog or training decision, regenerate and check the training report:

```powershell
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105TrainingReport'
mvn '-Dtest=TrainingStatusConsistencyTest' test
```

Read-only Run2 intake summary:

```powershell
mvn -q org.codehaus.mojo:exec-maven-plugin:3.5.0:java '-Dexec.mainClass=com.coreauth.validator.coverage.Run2IntakeSummary' '-Dexec.args=specifications/ATL105/test-input/ai-solution/runs/2026-09-23/Run2/traceability_matrix_full.json specifications/ATL105/test-input/ai-solution/runs/2026-09-23/Run2'
```

## Confluence source retrieval

Fetch the BUYPASS Development JP page as a read-only, versioned snapshot. Set `CONFLUENCE_PAT` in the process environment; optionally override `CONFLUENCE_BASE_URL` or `CONFLUENCE_PAGE_ID`. The fetcher never writes credentials to disk, and it refuses to overwrite a snapshot for the same page version.

```powershell
$env:CONFLUENCE_PAT = '<personal-access-token>'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.confluence.FetchConfluencePage' '-Dexec.args=specifications/ATL105/test-input/confluence'
```

Treat the resulting snapshot as source evidence for review. It does not automatically alter validation rules or pass/fail results.
