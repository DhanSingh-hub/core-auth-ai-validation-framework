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
|   |-- docs/specs/kb/                Source-grounded segment rule catalogs and training report
|   |-- docs/test-validation-strategy/ Review policy and evidence gates
|   |-- reports/                      Published stakeholder reports
|   |-- schemas/                      ATL105 validation schemas
|   |-- scripts/                      ATL105-specific pipelines
|   |-- test-input/                   Immutable AI runs and controlled fixtures
|   `-- test-output/                  Independent baselines and validation evidence
`-- target/                           Disposable Maven build output
```

See [ATL105 Artifact Storage Policy](specifications/ATL105/docs/test-validation-strategy/ARTIFACT-STORAGE-POLICY.md) for ownership and retention rules.
See [Test Solution Framework Ownership](specifications/ATL105/docs/test-validation-strategy/TEST-SOLUTION-FRAMEWORK-OWNERSHIP.md) for generation order, module boundaries and review gates.

## Current validation state

The [segment training report](specifications/ATL105/docs/specs/kb/SEGMENT-TRAINING-EXECUTION-REPORT.md) reconciles rule catalogs with [training gate decisions](specifications/ATL105/training-status.json). At the current snapshot, 49 segments are in progress with 601 catalogued rules; none is certified for independent AI-artifact intake. The [element chain report](specifications/ATL105/test-output/test-solution-independent-review/atl105-element-chain-coverage.md) counts 539 placeholder-generated structural links and 0 executable links; structural completion is not SME approval or executable BR-to-test-data coverage. See the [remaining limitations](specifications/ATL105/test-output/test-solution-independent-review/atl105-remaining-limitations-status.md) for the separate approval and execution measures.

The [source-backed rule gate](specifications/ATL105/test-output/test-solution-independent-review/atl105-source-backed-rule-gate.md) tracks all 601 rules with source-body navigation, and separately reports resolvable citations, curated assertions, draft cases and SME approval. Body locations are reference-only. Its Element 83 examples remain review-required and are not inserted into the execution package.

Run1 and Run2 remain evidence under independent review, not Test Solution training truth. See the [RAID Log](specifications/ATL105/docs/test-validation-strategy/RAID-Log.md) and [composite feedback](specifications/ATL105/docs/test-validation-strategy/AI-SOLUTION-FEEDBACK-RUN1-RUN2-COMPOSITE.md) for delivery history.

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

After changing a segment catalog or training decision, regenerate and check the training report:

```powershell
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105TrainingReport'
mvn '-Dtest=TrainingStatusConsistencyTest' test
```

Read-only Run2 intake summary:

```powershell
mvn -q org.codehaus.mojo:exec-maven-plugin:3.5.0:java '-Dexec.mainClass=com.coreauth.validator.coverage.Run2IntakeSummary' '-Dexec.args=specifications/ATL105/test-input/ai-solution/runs/2026-09-23/Run2/traceability_matrix_full.json specifications/ATL105/test-input/ai-solution/runs/2026-09-23/Run2'
```
