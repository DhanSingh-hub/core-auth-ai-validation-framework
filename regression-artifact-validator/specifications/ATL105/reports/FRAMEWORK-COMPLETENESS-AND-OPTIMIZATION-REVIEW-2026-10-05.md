# Test Solution Framework: Completeness and Optimization Review

## 1. Review identity and decision

- Review date: 2026-10-05.
- Reviewed baseline: `d16b7231f861ddbe81409b119ab2bf1d718c33ae`, the merged `Develop` snapshot containing DL1-DL8.
- Delivery branch: `review/framework-completeness-2026-10-05`.
- Scope: repository indexing, segment/element references, training handbook and operational guidance, AI-artifact validation controls, implementation/test evidence, reproducibility, and optimization opportunities.
- This is a correctness, completeness, and maintainability review, not a security assessment or SME certification.

**Decision: do not claim that no detail is missing, or that the entire framework is ready for certification.** Segment packages being implemented and focused tests passing do not establish a green full suite, complete source-rule extraction, semantic correctness, complete AI intake, or execution readiness.

The full Maven suite fails on the reviewed baseline. No validation rules, source decisions, immutable AI input, or approval state were changed for this review. Recommendations below are proposed work, not completed fixes.

## 2. Method and measured inventory

The review combined independent inspections of indexing, training guidance, and validation implementation with repository inventory and full-suite execution. Findings distinguish current defects, missing mechanical gates, external-input gaps, and SME decisions.

| Tracked scope at the baseline | Count | Interpretation |
|---|---:|---|
| Repository files | 2,623 | Tracked files, not all files on disk |
| Main Java source files | 284 | Production/generation/validation implementation |
| Test Java source files | 174 | Source-file count, not test-method count |
| Feature files | 2 | Cucumber resources |
| ATL105 documentation files | 1,358 | Includes segment guidance and historical documents |
| ATL105 test-output files | 481 | Published/generated evidence, not proof of approval |
| AI Solution input files | 242 | Run/input inventory, not complete per-segment intake |
| Contract/schema files | 23 | Module contract, ATL105 contract, and ATL105 schemas combined |

Inventory used `git ls-files` against these paths. Counts deliberately exclude untracked build output. The source-file counts do not establish line or branch coverage.

### 2.1 Full-suite baseline

From the [module directory](../../../):

```powershell
mvn test
```

Environment: Windows 11 amd64, Oracle JDK 27, Maven 3.9.9; the [POM](../../../pom.xml) targets Java 26.

| Maven result | Value |
|---|---:|
| Tests run | 1,274 |
| Failures | 4 |
| Errors | 0 |
| Skipped | 0 |
| Passed, calculated as tests minus failures/errors/skips | 1,270 |
| Elapsed Maven time | 2 min 11 sec |
| Build result / exit | BUILD FAILURE / 1 |

The earlier VS Code test invocation reported 1,462 passed and six failed nodes, including failing containers. That is a different discovery/reporting count; it must not be combined with Maven's method-level totals. Maven is the reproducible baseline recorded here.

### 2.2 Confirmed failing checks

| ID | Test | Observed failure | Required response |
|---|---|---|---|
| B-01 | [CommunicationRegisterTest](../../../src/test/java/com/coreauth/validator/CommunicationRegisterTest.java) | DL6 catalog P-06 points to `SEGDL1-SME-003`, but the register validator requires a query belonging to that segment and catalog item | Model local queries and cross-segment dependencies separately; preserve the unresolved SME decision |
| B-02 | [TestSolutionJsonFormatTest](../../../src/test/java/com/coreauth/validator/TestSolutionJsonFormatTest.java) | Appendix F and G chain packages contain scenario status `COVERED`, which cannot deserialize into `ArtifactStatus` | Separate coverage result from artifact lifecycle status; correct the generators and regenerate, rather than weakening the enum |
| B-03 | [Atl105DependencyReviewMatrixTest](../../../src/test/java/com/coreauth/validator/Atl105DependencyReviewMatrixTest.java) | Expected 235 review candidates; regenerated output contains 236 | Reconcile the actual rule change and regenerate the entire dependency/report chain |
| B-04 | [Atl105RemainingLimitationsStatusTest](../../../src/test/java/com/coreauth/validator/Atl105RemainingLimitationsStatusTest.java) | Expected 235 dependency candidates; actual is 236 | Update downstream evidence consistently; do not merely replace constants to make tests pass |

The test run also rewrote five tracked output paths: all-elements reference JSON/Markdown, dependency review matrix CSV/JSON, and Segment 100 coverage Markdown. Only three had semantic diffs; those totaled 30 added and 12 deleted lines. The changes included DL6 rule `SEGDL6-R-002` becoming a direct element anchor and an additional DL4 dependency candidate for Element 91. These are observable freshness and test-isolation concerns, not proof that those relationships are semantically approved.

Only files changed by the review's test execution were restored to the baseline after preserving the evidence. Regenerated snapshots were not published as approved fixes.

## 3. Findings and recommendations

Priorities mean: **P1** blocks trustworthy release/intake or a green validation baseline; **P2** materially weakens completeness/reproducibility; **P3** improves usability or efficiency. They are not vulnerability severities.

### F-01 (P1): The full validation baseline is not green

The four failures in Section 2.2 are reproducible implementation/data consistency gaps. They are not all SME blockers. In particular, invalid lifecycle values and stale derived counts are mechanical defects.

**Acceptance:** all four failing methods pass on regenerated artifacts; `mvn test` is green; unknown lifecycle statuses still fail; cross-segment SME dependencies remain unresolved until reviewed. Report both focused and full-suite outcomes.

### F-02 (P2): Tests mutate published evidence and expose mixed snapshots

Executing the suite modifies tracked generated reports. Consequently, test order and previous generation can influence the apparent evidence snapshot. A reviewer cannot infer freshness merely from an existing report's filename.

**Acceptance:** tests generate into temporary directories; explicit publication regenerates in dependency order; each output identifies its input hashes and generator revision; a clean checkout remains clean after `mvn test`; changing an upstream input without refreshing a dependent report fails a freshness gate.

### F-03 (P2): No tracked CI workflow enforces the overall gates

No tracked GitHub Actions workflow, Jenkinsfile, or Azure Pipelines definition was found. This does not establish whether external CI exists. Local reminders and focused tests are not a repository-enforced full validation gate.

The [workspace tasks](../../../../.vscode/tasks.json) also repeat Segment 101 commands and embed user-specific Maven/JDK paths. They are not a portable whole-framework entry point.

**Acceptance:** add or document the authoritative CI pipeline; pin a supported JDK/Maven combination; enforce contract, register, training consistency, index/link integrity, freshness, and full-suite checks on pull requests. Document how externally hosted CI provides equivalent enforcement if that is the intended setup.

### F-04 (P2): Training publication freshness is a documented but unenforced condition

The [ownership document](../docs/test-validation-strategy/TEST-SOLUTION-FRAMEWORK-OWNERSHIP.md) explains refresh order and explicitly warns that the limitations summary does not establish one common snapshot. The [storage policy](../docs/test-validation-strategy/ARTIFACT-STORAGE-POLICY.md) requires provenance. This is good guidance, but manual ordering does not reject stale publication.

**Acceptance:** a common snapshot manifest ties source revision, catalogs, training status, contracts, input package hashes, and all dependent reports together. Tests deliberately change one upstream input and demonstrate that stale output cannot be published or promoted.

### F-05 (P2): DL runbooks do not provide a complete reproducible command path

The DL coverage READMEs identify producer classes and test classes, but naming a producer is not a clean-checkout execution recipe. The handbook's generic numeric `Segment<NNN>ConsolidatedReport` guidance does not explain DL-specific generators.

**Acceptance:** publish one shared DL recipe with working directory, supported tooling, immutable input revision, build, generator invocation, focused/full tests, outputs, and freshness check. Exercise every documented command in the selected environment. Dependency-repository restrictions must fail explicitly or have a documented, verified local-cache invocation; do not assume an untested plugin is available.

### F-06 (P3): Clarify the handbook's authority hierarchy

The [common handbook](../docs/specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) describes itself as the single guide, yet requires a separate questionnaire and relies on companion intake, ownership, and storage controls.

**Acceptance:** define the handbook as workflow authority, identify the binding companion policies, and explicitly distinguish current normative guidance from archived snapshots. Preserve the existing source-first, alias-review, mutation, and SME-separation safeguards.

### F-07 (P2): The primary KB index does not represent the delivered modules

Evidence: [KB index](../docs/specs/kb/00-index.md), lines 67-96.

- The tracked inventory has 49 segment READMEs and 49 catalogs: 41 numeric segments and DL1-DL8.
- Only 9/49 segment READMEs are directly linked from the primary index; 40 are unlinked, including all eight DL modules.
- The index calls 38 modules placeholders although their training-status entries are substantive. Segment 132 is both linked as developed and listed as a placeholder.
- The index says Elements 100-228 are not done; the linked dictionary contains 231 distinct IDs, including Element 243.

Unlinked numeric segments: 104, 109, 110, 111, 114, 115, 116, 118, 119, 120, 123, 130, 131, 134, 135, 136, 139, 140, 141, 142, 143, 145, 146, 148, 149, 150, 151, 152, 153, 155, 156, 157.

**Acceptance:** derive a navigation table from the tracked modules and training status; verify exactly 49 unique README/catalog pairs and all eight DL entries. Include the training report, communication register, appendix inventory/gaps, and contracts/schemas. Label implementation status separately from approval and execution.

### F-08 (P2): An assembled machine catalog omits Element 243

Evidence: [assembled templates](../docs/atl105_complete_templates.json), lines 7-19, and [data dictionary](../docs/specs/kb/13-data-elements.md), line 258.

The assembled `elements_reference` has 230 IDs and declares 230. The all-element inventory, known-reference catalog, and dictionary have 231 distinct IDs. Exact set subtraction identifies only Element 243, Extended Unit of Measure, as missing from the assembled catalog.

The [message-template catalog test](../../../src/test/java/com/coreauth/validator/Atl105MessageTemplateCatalogTest.java), lines 16-25, verifies transaction-template cardinality, not element-set equality.

**Acceptance:** reconcile element identity sets, not just declared counts. Either include Element 243 or document and enforce an intentional exclusion. This finding does not establish that Element 243 lacks rules or executable checks elsewhere.

### F-09 (P2): Appendix summary and physical records disagree

Evidence: [appendix inventory](../test-output/test-json/knowledge/segment-100-canonical-appendix-inventory.json), line 16.

- Summary: 134 BR records.
- Sum of 31 appendix entries: 191.
- Sum of embedded BRs in all 31 referenced packages: 191.
- Per-package count mismatches: zero.

The aggregate undercounts recorded BR objects by 57. [Appendix consistency tests](../../../src/test/java/com/coreauth/validator/AppendixCoverageConsistencyTest.java), lines 18-51, do not reconcile that aggregate.

**Acceptance:** generate the total from rows and independently check referenced packages. A count of 191 is a physical inventory measure, not approved or executable coverage.

### F-10 (P2): Eight checked local navigation destinations are missing

The indexing inspection checked 598 tracked Markdown files and 2,836 local destinations, excluding handbook and designated instructional paths. It found eight missing destinations and no existing-but-untracked destinations.

| Evidence | Broken destination |
|---|---|
| [Segment 103 README](../docs/specs/kb/segment-103/README.md), lines 42-43 | Crosswalk and Markdown report links omit the existing `coverage-reports/segment-103/` directory |
| Same README, line 44 | Ratio-report HTML does not exist |
| [Segment 104 README](../docs/specs/kb/segment-104/README.md), line 38 | Report link omits the existing `coverage-reports/segment-104/` directory |
| [Segment 112 flow](../docs/specs/kb/segment-112/additional-information-indicator-flow.md), lines 43-44 | Register and serialization links go up one directory incorrectly |
| [Segment 119 README](../docs/specs/kb/segment-119/README.md), line 28 | Ratio-report HTML does not exist |
| [Segment 120 README](../docs/specs/kb/segment-120/README.md), line 81 | Template destination needs one additional parent-directory traversal |

**Acceptance:** fix moved paths; replace nonexistent reports only after verifying equivalent evidence, otherwise label them unavailable. Add a tracked-file local-link gate. This check did not test fragments, external URLs, code-span references, or semantic equivalence.

### F-11 (P3): Dictionary and Appendix I/K counting claims are inaccurate

Evidence: [dictionary introduction](../docs/specs/kb/13-data-elements.md), lines 11-12; [Appendix I/K overview](../docs/specs/kb/appendix-I-K-overview.md), lines 22, 111, 166-167.

- Dictionary says IDs 32, 59, and 166 are absent; it defines them at lines 48, 75, and 183. The stated 228 total is stale.
- Actual dictionary inventory is 231 distinct IDs and 232 definition occurrences; Element 118 has two definitions.
- Appendix I claims 81 distinct/indexed IDs but has 78 rows; K claims 47 but has 44. Combined rows: 122, not 128.
- I has no rows for 023, 061, 074; K has no rows for 014, 015, 033. This is not proof these sequential IDs are source-defined missing entries.
- The overview says K excludes reserved 002 but includes it at line 116.

**Acceptance:** distinguish maximum allocated ID, source-defined IDs, reserved IDs, and indexed rows. Reconcile source headings before adding entries; derive gap/count statements from identity sets.

### F-12 (P3): Three appendix families claim TD records that are absent

Evidence: [appendix gap register](../docs/specs/kb/segment-100/appendix-family-gap-closure-register.md), lines 39, 40, 43.

AA, AB, and AE each claim 4/3/3/1 BR/TS/TC/TD, but their packages have 4/3/3/0. The current wording describes a TD object without payload; the actual state is no embedded TD object. Other inspected physical count rows agree.

**Acceptance:** derive physical counts and distinguish absent data records from present but nonexecutable records. Do not invent payloads or silently raise execution coverage.

### 3.1 Inventory agreements that were verified

| Inventory/gate | Verified agreement | What it does not establish |
|---|---|---|
| Segment catalogs/status | 49 modules, 601 rules, zero per-segment rule-count mismatches | Complete source extraction or approved rules |
| Training report | 49 IN_PROGRESS; 48 substantive; one source-stub; 44 with explicit blockers; zero TRAINED_FOR_INTAKE | Independent intake readiness |
| Section 11 | 31 indexed layouts, 31 unique template keys, 31 assembled templates; no dangling indexed keys | Approval; templates remain NOT_APPROVED |
| Element identities | Dictionary/known references/all-element inventory agree on 231 distinct IDs | Assembled catalog agreement; see F-08 |
| Appendices | 31 inventory records and 31 referenced packages | Correct aggregate summary; see F-09 |
| Communication register | 259 unique IDs: 224 SME queries, 15 Test Team, 4 AI discussions, 16 AI feedback; 49 segment index rows agree with status counts | Correct DL6 P-06 query ownership; see B-01 |
| Transaction ledger | 23 indexed tasks and 23 tracked task files | Execution or SME approval |
| Schema inventory | Three `.schema.json` files; ATL105 contract has 18 JSON files | A schema for every segment or enforcement at every intake path |

The 23 contract/schema files in Section 2 include the module contract JSON files as well; they are not 23 schemas.

### F-13 (P1): Canonical assessment can label a review-required chain EXECUTION_READY

Evidence:

- [Assessment service test](../../../src/test/java/com/coreauth/validator/AiCoverageAssessmentServiceTest.java), lines 34, 243, 257: the ready fixture contains a REVIEW_REQUIRED scenario, non-strict execution mode, and request-only TD, yet expects `executionReady()` to be true.
- [Canonical validator](../../../src/main/java/com/coreauth/validator/canonical/CanonicalTraceabilityValidator.java), lines 27 and 165: execution-readiness/response checks depend on strict mode.
- [Assessment report](../../../src/main/java/com/coreauth/validator/coverage/AiCoverageAssessmentReport.java), line 55: readiness derives from structural validity, strategy flags, and full-chain counts.
- [Report writer](../../../src/main/java/com/coreauth/validator/coverage/AiCoverageAssessmentReportWriter.java), line 18: the resulting boolean is serialized as EXECUTION_READY.

**Impact:** a passing existing test encodes a readiness claim that is stronger than its evidence. This defect is distinct from the four failing full-suite tests.

**Acceptance:** execution readiness requires the execution contract, eligible BR/TS/TC lifecycle states, explicitly executable TD, validated request/response envelopes, and applicable payload gates. Add negative tests for REVIEW_REQUIRED, BLOCKED, non-strict mode, missing TD readiness, and missing response. Keep structural coverage as a separate measure.

### F-14 (P2): The producer-neutral schema is not the runtime package shape

Evidence: [package schema](../../../contract/artifact-package.schema.json), line 7; [runtime model](../../../src/main/java/com/coreauth/validator/canonical/CanonicalArtifactPackage.java), line 7; [loader](../../../src/main/java/com/coreauth/validator/canonical/CanonicalPackageLoader.java), line 13; [contract test](../../../src/test/java/com/coreauth/validator/ProducerNeutralContractTest.java), line 47.

The schema expects top-level contractVersion, producer, packageId, specificationVersion, and artifacts. The loader binds a nested-manifest model with separate BR/TS/TC/TD lists. Inspecting schema fields in a test does not prove a production adapter exists. The [controls catalog](../docs/test-validation-strategy/AI-ARTIFACT-VALIDATION-CONTROLS.md), line 22, also identifies the October 5 producer-format adapter as unestablished.

**Acceptance:** provide named/versioned schema-to-runtime adapters for both producers; reject unknown versions, missing fields, wrong types, and unsupported shapes before assessment. Preserve legacy formats only through explicit adapters and end-to-end fixtures.

### F-15 (P2): Canonical anchors are not bound to the manifest's specification identity

Evidence: [canonical validator](../../../src/main/java/com/coreauth/validator/canonical/CanonicalTraceabilityValidator.java), lines 57, 90, 208; [independent baseline](../../../src/main/java/com/coreauth/validator/coverage/IndependentRequirementBaseline.java), line 57; [assessment service](../../../src/main/java/com/coreauth/validator/coverage/AiCoverageAssessmentService.java), line 166.

The manifest requires ATL105, while anchor checks require a nonblank specification and matching version without equal specification identity. If the baseline contains the same foreign identity, matching anchors can participate in a confirmed result.

**Acceptance:** reject foreign-specification anchors and mixed versions; validate independent baseline anchors against the intended specification/revision. Add invalid-baseline and foreign-specification tests, not just version mismatch tests.

### F-16 (P2): Confirmed crosswalks lack verifiable evidence binding

Evidence: [assessment service](../../../src/main/java/com/coreauth/validator/coverage/AiCoverageAssessmentService.java), lines 145-153; [crosswalk model](../../../src/main/java/com/coreauth/validator/canonical/RequirementCrosswalkEntry.java), line 6; [schema](../../../contract/artifact-package.schema.json), line 39.

A confirmed runtime entry needs a nonblank reason, matching anchor, and version, but lacks the schema's evidence and matchMethod fields. The pipeline trusts the confirmation rather than resolving independent business evidence.

**Acceptance:** require a resolvable Test Solution-owned evidence reference or SME decision, including source/delivery identity. Alias-only and heuristic matches stay REVIEW_REQUIRED. Test contradictory business statements under an identical anchor and reject confirmation based only on a reason string.

### F-17 (P2): Intake provenance does not freeze the received content

Evidence: [intake result](../../../src/main/java/com/coreauth/validator/canonical/AiArtifactIntakeResult.java), lines 6-12; [local adapter](../../../src/main/java/com/coreauth/validator/canonical/LocalAiArtifactIntakeAdapter.java), line 14; [source model](../../../src/main/java/com/coreauth/validator/canonical/AiArtifactSource.java), line 12.

Location/revision provenance exists, but received time, package/file hashes, and immutable snapshots are not established. A movable GitHub revision such as a branch name can be accepted as nonblank.

**Acceptance:** resolve a commit SHA, hash deterministic file/package content, record receipt time, and validate from a preserved snapshot. Test post-intake content changes, reused IDs with changed content, and reproducible re-validation.

### F-18 (P3): Repeated graph edges and baseline-orphan crosswalks are incompletely checked

Evidence: [canonical validator](../../../src/main/java/com/coreauth/validator/canonical/CanonicalTraceabilityValidator.java), lines 118, 329, 350; [assessment service](../../../src/main/java/com/coreauth/validator/coverage/AiCoverageAssessmentService.java), line 202.

Duplicate artifact IDs and missing targets are checked. Repeated values inside relationship lists or an AI-ID list are not comprehensively rejected. Extra crosswalk keys absent from the independent baseline can be ignored during assessment.

**Acceptance:** reject duplicate edges at every graph stage and crosswalk keys outside the assessed baseline. Test explicit MISSING disposition, duplicates, and orphan mappings.

### F-19 (P3): DL1 listing failures can look like valid empty input

Evidence: [DL1 generator](../../../src/main/java/com/coreauth/validator/coverage/GenerateSegmentDl1AiArtifactCoverageReport.java), lines 499-504.

`countTestDataFiles` catches IOException and returns zero. That conflates an inventory error with a legitimate empty data directory.

**Acceptance:** propagate a generation error or emit an explicit invalid state that cannot become a successful zero-count report. Test unreadable/listing-failure inputs.

### 3.2 AI-validation control-to-implementation reconciliation

Control IDs refer to the current [AI validation controls catalog](../docs/test-validation-strategy/AI-ARTIFACT-VALIDATION-CONTROLS.md).

| Control | Evidence and remaining gap |
|---|---|
| AIV-001 | Intake service/adapters and intake tests cover location/provenance; immutable hash-bound receipt is missing (F-17) |
| AIV-002 | Producer-neutral schema exists and is inspected; runtime schema/adapter integration remains incomplete (F-14) |
| AIV-003 | Changed content under reused producer IDs needs delivery fingerprints and regression tests |
| AIV-004 | Run2 adapter tests cover hash-bound resolution/version mismatch; declared version can still be accepted without resolution |
| AIV-005 | Anchor presence/key matching exists; general source-document resolution and specification binding are incomplete (F-15) |
| AIV-006 | Missing links, duplicate IDs, orphan BR/TS/TC checks exist; duplicate edges and baseline-orphan mappings remain (F-18) |
| AIV-007 | Independent baseline drives denominator; tests distinguish it from AI BR inventory; confirmation provenance remains incomplete |
| AIV-008 | CONFIRMED/REVIEW_REQUIRED/MISSING logic exists; direct MISSING outcome and evidence binding need tests |
| AIV-009 | DL samples are evaluated independently and review states retained; October 5 producer adapter is not established |
| AIV-010 | Canonical execution gate permits false readiness (F-13) |
| AIV-011 | Null percentages for invalid/empty baselines and separate denominators are tested; this is a stronger implemented area |
| AIV-012 | Positive/negative cases exist; DL4-DL8 test depth and readiness-transition coverage are uneven |
| AIV-013 | Key report counts/decisions tested; end-to-end source-hash provenance for every report not established |
| AIV-014 | Explicit 16-leaf AI delivery mapping not established; an independent allowlist is not per-leaf AI coverage |

The read-only validation review ran 93 focused tests successfully across canonical validation, intake, producer-neutral contract, Run2 adaptation, and DL1-DL8 generators. These passes do not override the full-suite failures or F-13's incorrect positive expectation.

DL2-DL8 tests check non-disclosure of selected sensitive sample values. DL1 lacks the same explicit adversarial disclosure assertion in its focused report test. Add adversarial redaction/error-path tests across all segments; do not describe existing sample tests as a universal privacy guarantee.

## 4. External and SME boundaries

All eight DL entries in [training status](../training-status.json) retain mechanical AI-package and SME-certification blockers. Representative samples and available catalog rows do not amount to exhaustive BR/TS/TC/TD intake.

- Complete immutable AI packages must be supplied or generated by the authorized producer, then independently validated. Do not modify historical AI input or create synthetic records disguised as received AI artifacts.
- DL6-DL8 have no segment-specific records in the supplied AI catalog used by their coverage reports. This does not imply no sample payload evidence exists; DL6 can still have field-level sample PASS/FAIL outcomes.
- SME decisions remain separate from implementation defects. Do not mark a candidate approved, executed, or certified merely because a generator or unit test passes.
- Source-body navigation and complete structural links are useful evidence, but are not a substitute for complete, reviewed source-rule extraction and assertion coverage.

## 5. Proposed optimization plan

| Workstream | Improvement | Verification |
|---|---|---|
| Common report context | Parse immutable source/catalog/schema/input files once per run and share a read-only context | Record parse/file-read counts; compare equivalent outputs before/after |
| DL generation | Extract common anchor, link, polarity, coverage, and redaction logic; retain segment-specific semantic strategies | Parameterized conformance tests for DL1-DL8; golden-output comparison; mutation tests |
| Regeneration | Declare generator dependencies and publish atomically from one snapshot | Invalid/missing/stale dependencies fail before published output changes |
| Index maintenance | Derive indexes from the same source inventory used by gates | Every required segment/rule/artifact indexed exactly once; missing and duplicate mutations rejected |
| Tests | Move generated files to temporary directories; run targeted checks locally and the full suite in CI | Clean working tree after tests; no status changes caused by test order |
| Build/run UX | Use portable tasks and one documented framework command | Reproduce on a clean supported Windows environment without user-specific paths |
| Performance | Establish cold/warm time, peak memory, files scanned, parse counts, and output parity before optimization | Optimize only measured hotspots; retain semantic results and fail-closed behavior |

No speedup percentage or memory saving is claimed: profiling and controlled before/after measurements were not performed.

## 6. Implementation order and release criteria

1. Repair the four baseline failures and the canonical false-ready gate (F-13), without changing SME answers or weakening validation.
2. Make generation hermetic and add shared snapshot/freshness enforcement.
3. Reconcile indexing and rule-to-artifact coverage; provision complete authorized AI intake where missing.
4. Publish the reproducible numeric/DL training path and enforce it in CI.
5. Consolidate repeated generation/validation logic behind conformance tests.
6. Review and approve source/SME questions in a separate lane; promote segments individually only when both lanes pass.

Completion requires a green full suite, coherent immutable provenance, exhaustive machine inventory checks, source-backed expected assertions, valid bidirectional BR/TS/TC/TD links, tested negative cases, and independently reviewed AI intake. Approval and execution remain separately evidenced states.

## 7. Review limits

This review does not certify the source specification as exhaustively extracted, approve fixtures, perform production execution, decide unresolved SME questions, verify an external CI service, or benchmark performance. Existing archived figures are not silently upgraded to current evidence. Any assertion of total completeness needs automated reconciliation plus source-owner review, not this report alone.

## 8. Prioritized work packages and definition of done

| Package | Findings | Deliverable / owner role | Definition of done |
|---|---|---|---|
| W-01: correctness baseline | F-01, F-13 | Validator maintainer: lifecycle/ready-gate fixes and regenerated evidence | Full suite green; review-only chains cannot become execution ready; invalid statuses still rejected |
| W-02: complete navigation | F-07-F-12 | Catalog/documentation maintainer: generated inventories and local-link gate | 49 segment pairs indexed; 231 assembled IDs reconciled; 191 physical appendix BRs reconciled; zero broken checked destinations |
| W-03: contracts and identity | F-14-F-18 | Intake/contract maintainer: versioned adapters, source identity checks, immutable provenance, graph checks | Both producer fixtures accepted through explicit adapters; foreign/malformed/orphan/duplicate/changed-content mutations rejected |
| W-04: reproducible publication | F-02-F-05, F-19 | Build/report maintainer: temporary-output tests, snapshot DAG, portable invocation and CI | Clean checkout stays clean after tests; stale dependency and listing failure block publication; documented commands exercised |
| W-05: training clarity | F-06 and Section 4 | Training owner: authority hierarchy, package intake checklist and independent promotion gate | Normative companion controls clear; external package gaps explicit; no approval inferred from structural counts |
| W-06: measured optimization | Section 5 | Framework maintainer: immutable shared index, adjacency graph, common DL core | Golden semantic outputs unchanged; mutation coverage maintained; measured timing/read/parse/memory evidence reported |

No named individual is assigned by this report. Agree ownership and dates through the existing communication register, rather than creating unsupported SME decisions.

### 8.1 Minimum mutation matrix

| Area | Required negative/edge checks |
|---|---|
| Source identity | Missing anchor; wrong specification; wrong version; mixed revisions; unresolvable citation |
| Contract | Unknown contract version; unsupported producer shape; missing field; wrong type; invalid lifecycle status |
| Traceability | Missing BR/TS/TC/TD; duplicate IDs; repeated edges; orphan crosswalk; inconsistent bidirectional links |
| Coverage | Empty/invalid independent denominator; extra AI BRs; no AI records; unmatched rule; explicit MISSING; sample PASS without rule certification |
| Confirmation | Alias-only match; same anchor with contradictory statement; missing/unresolvable evidence; movable revision |
| Execution | REVIEW_REQUIRED/BLOCKED artifacts; non-strict package; absent executable TD flag; absent/invalid response |
| Publication | Upstream input changed; mixed snapshot; unreadable directory; missing dependency; failed generation; clean-tree assertion |
| Non-disclosure | Sensitive values in payloads and parse/validation failures; no value echo in Markdown, JSON, logs, or exception surfaces |

For each package, record tests run, baseline/after results, source and delivery revisions, generated output identities, and remaining external/SME blockers. Do not use a single blended percentage for structural linkage, source coverage, AI coverage, approval, and execution.
