# Regression Artifact Validator

Java validation framework and ATL105 specification pack for independently reviewing AI-generated business requirements, scenarios, test cases, test data, and traceability.

## Repository map

```text
regression-artifact-validator/
|-- contract/                         Producer-neutral schemas and vocabulary
|-- scripts/                          Reusable repository-level automation
|-- src/main/java/                    Validation engine and adapters
|-- src/test/java/                    Unit and governance tests
|-- src/test/resources/               Test features and fixtures
|-- specifications/ATL105/
|   |-- contract/                     ATL105 semantic aliases
|   |-- docs/                         Knowledge base and validation strategy
|   |-- reports/                      Published stakeholder reports
|   |-- schemas/                      ATL105 validation schemas
|   |-- scripts/                      ATL105-specific pipelines
|   |-- test-input/                   Immutable AI runs and controlled fixtures
|   `-- test-output/                  Independent baselines and validation evidence
`-- target/                           Disposable Maven build output
```

See [ATL105 Artifact Storage Policy](specifications/ATL105/docs/test-validation-strategy/ARTIFACT-STORAGE-POLICY.md) for ownership and retention rules.

## Current validation state

As of 2026-09-24, Run1 and Run2 are being reviewed as a single composite AI delivery. The Test Solution has generated independent crosswalks, segment validation reports, SME review queues, specification-version resolution evidence, and executive reporting under `specifications/ATL105/test-output/ai-solution-independent-review/`.

The current state is **review required**, not execution-ready: the composite requirement identity is verified, but source-version correction, SME decisions, payload validation, and coverage reconciliation remain tracked evidence gates. See [RAID Log](specifications/ATL105/docs/test-validation-strategy/RAID-Log.md) and [composite Run1/Run2 feedback](specifications/ATL105/docs/test-validation-strategy/AI-SOLUTION-FEEDBACK-RUN1-RUN2-COMPOSITE.md) for the latest history.

## Producer-neutral contract

- [Vocabulary](contract/vocabulary.json)
- [Canonical anchor schema](contract/canonical-anchor.schema.json)
- [Artifact package schema](contract/artifact-package.schema.json)
- [ATL105 semantic aliases](specifications/ATL105/contract/semantic-aliases.json)

AI and Test Solution artifacts keep producer-local IDs. Canonical source anchors and explicit, evidence-backed crosswalks provide shared identity. Heuristic or alias matches remain `REVIEW_REQUIRED`.

## Validation

Run from this module directory:

```powershell
mvn test
```

Focused governance checks:

```powershell
mvn '-Dtest=ProducerNeutralContractTest,RepositoryStructureTest,Atl105PathsTest' test
```

Read-only Run2 intake summary:

```powershell
mvn -q org.codehaus.mojo:exec-maven-plugin:3.5.0:java '-Dexec.mainClass=com.coreauth.validator.coverage.Run2IntakeSummary' '-Dexec.args=specifications/ATL105/test-input/ai-solution/runs/2026-09-23/Run2/traceability_matrix_full.json specifications/ATL105/test-input/ai-solution/runs/2026-09-23/Run2'
```
