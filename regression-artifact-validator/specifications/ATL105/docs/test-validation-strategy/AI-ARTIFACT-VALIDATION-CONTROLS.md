# AI Artifact Validation Controls

**Owner:** Test Validation Team
**Applies to:** AI Solution deliveries for ATL105 and future specification packs
**Control IDs:** `AIV-*` (validation controls; not ATL105 payment BRs)
**Strategy:** [AI Solution Output Validation Plan](AI-Solution-Output-Validation-Plan.md)
**Contract:** [Producer-Neutral Artifact Package](../../../../contract/artifact-package.schema.json)

## Purpose and Ownership

This catalog makes the validation method auditable and testable. It defines what the Test Solution must check, which independent method performs the check, and what regression evidence is expected.

These controls are **not ATL105 business requirements**. ATL105 BRs describe payment or message behavior. `AIV-*` controls describe the Test Solution's assurance obligations. Do not add AIV controls to a Segment 100/101/etc. rule denominator. Do not report AIV-control coverage as AI business-requirement coverage.

The source of truth for the stages and methods is the [AI Solution Output Validation Plan](AI-Solution-Output-Validation-Plan.md). This catalog supplies stable control IDs and maps each control to implementation/test evidence. Per-delivery results belong under `specifications/ATL105/test-output/ai-solution-independent-review/` and must name the exact delivery ID and source hashes.

## Control Catalogue

Appendix I evidence for `AIV-005`, `AIV-011`, and `AIV-012`:
`GenerateAppendixITrainingInventoryTest` verifies source boundaries/hashes and
actual selector identity; `AppendixITrainingKnowledgeTest` verifies draft source
evidence, unique IDs, and rule/test-design metadata; `AppendixILogicalDataValidatorTest`
checks selected Tables 001-009 representation predicates, 13 targeted format
mutations, unresolved scope/context, and sensitive-value non-disclosure.
`AppendixISegmentWireValidatorTest` adds six framing mutations, repeated-record
parsing, conflicted wire, unsupported encoding/scope, and zero-length checks.
`GenerateAppendixIFirstBatchCandidatesTest` checks candidate links and exact
wire/structured-fragment correspondence, preserving review/approval boundaries.
Knowledge reconciliation requires every discovered top-level selector exactly
once and every pending issue to reference a central Segment 111 SME query.
`AppendixILogicalCandidateKnowledgeTest` checks the remaining four example
batches' rule/evidence links, table reconciliation and review-only scope.
`GenerateAppendixIRepresentativeIntakeTest` verifies hash-bearing input identity,
the linked representative BR/TS/TC/TD IDs, independent physical length gaps and
non-disclosure. This is bounded AIV-001/AIV-010 evidence, not proof of complete
chain quality, semantic entailment, producer approval or whole-appendix AI coverage.
`AppendixIFullMessageSourceInspectionTest` pins the source hash and independently
measures the referenced Appendix B Segment 100 example (078 declared versus
82 ASCII bytes), retaining `SEG111-SME-121`. It is source-dependency evidence,
not full-message execution. New candidates use Section 12.1 calculated lengths;
offline validation never establishes host acceptance.
`AppendixAFIncrementalAuthorizationValidatorTest` adds the separately pinned
ATL105 2026-4 Appendix AF overlay: source-quote checks, release-specific
BR/TS/TC/TD links, 21 logical lifecycle cases, Table 82 network policy and
layout, IA_REVERSAL disambiguation, AF Transaction ID open-state uniqueness
separate from Table 82 Terminal Transaction Number continuity,
predecessor, approval-code and post-completion checks.
These remain review-only and do not change the 2026-3 denominator. A synthetic
110-byte Financial Transaction Request application-payload candidate checks the
2026-3 Section 11/12.1/12.10 envelope and measured Segment 100/111 lengths;
artifact: `test-output/test-solution-independent-review/appendix-af-physical-wire-candidate-v1.json`.
Its Table 82 N2-to-N3 indicator is a stated candidate assumption, not a confirmed
encoding; AF Transaction ID remains separate from Table 82 N4. TCP/IP/TPDU,
response wire, host outcome, independently observed open-state completeness, and
SME approval remain unverified. The candidate does not establish full physical
serialization or execution readiness.
`GenerateAppendixIScopedTrainingTest` adds AIV-005/AIV-006/AIV-011 evidence:
11 independently evaluated scoped logical examples, 22 physical review-only TD
records with canonical links, specific mutation-target detection, literal source
checks, input hashes and reproducible artifacts. Distinct scoped anchors do not
claim whole-rule equivalence. Supplied context and logical response relations
are not BIN/network/issuer evidence. The 302-rule ledger is a draft inventory,
not proof of semantic completeness or an approved AI coverage denominator.
These controls do not establish complete field extraction, wire validity,
semantic equivalence, approved fixtures, AI coverage, or execution certification.

| Control | Validation requirement | Method / implementation surface | Regression evidence | Current format boundary |
|---|---|---|---|---|
| `AIV-001` | Freeze the received delivery; retain source location, revision, received time, and file/package hashes. Never normalize the preserved source copy. | `AiArtifactIntakeService`, source-specific intake adapters, artifact storage policy | `AiArtifactIntakeServiceTest`; provenance/hash checks for each intake workflow | Intake adapters resolve local/shared/checked-out Git sources. Hash-manifest generation is not established by the location adapters themselves. |
| `AIV-002` | Identify the package/schema version and reject or explicitly adapt unknown shapes. Validate required fields, value types, encodings, and workbook-vs-JSON export semantics. | Versioned producer adapter -> canonical artifact model; preservation/loss-reconciliation checks; producer-neutral schemas | `ProducerNeutralContractTest`; adapter-specific golden, loss, and version-compatibility tests | The September nested Run2 traceability format has `Run2TraceabilityAdapter`. The October 5 flat BR/TS catalog plus XLSX exports has no equivalent production adapter; unsupported format must remain `NOT_ASSESSED`, not a semantic failure. |
| `AIV-003` | Preserve producer-local IDs within a run, but detect reuse of an ID for changed content across runs. Never treat a reused local ID as cross-run identity. | Delivery manifest, per-run ID index, content/source fingerprint comparison | Add regression cases for changed content under a reused ID; canonical comparison must key on anchors/evidence | October 5 reuses September IDs for changed BR and scenario content; this is a current gap. |
| `AIV-004` | Establish specification/version/source-document identity and source hash before source claims can be confirmed. | Manifest validation and, where needed, hash-bound version resolution | `Run2TraceabilityAdapterTest` version-resolution and hash-change cases | October 5 BR manifest omits specification version; no source PDF hash is supplied in the catalog package. |
| `AIV-005` | Require resolvable canonical source anchors on BR/TS/TC/TD records before independent matching. | `SourceAnchor`, canonical anchor schema, `CanonicalTraceabilityValidator` | `ProducerNeutralContractTest`, `CanonicalTraceabilityValidatorTest` | October 5 records use page/entity fields but do not carry canonical source anchors. |
| `AIV-006` | Validate the producer-internal artifact graph and report missing, duplicate, orphaned, and unresolved links. Only `testData[].testCaseIds` forms the canonical TC -> TD edge; `coversBr` alone is a BR hint, not a chain link. | Appendix O/R/S/T/Y aliases normalize to canonical anchors/links; 18 synthetic-capable TDs become review-only drafts; external/no-BR candidates remain provenance-only | `CanonicalTraceabilityValidatorTest`, `Run2TraceabilityAdapterTest`, `AggregateEvidenceMergeTest`, `AggregateReadinessGateTest`; `scripts/build-ai-run4-br-chain-crosswalk.py`; `scripts/validate-ai-pipeline-delivery-review.cjs` | Run4 includes all 5,153 AI BRs and 65,989 matrix leaves. Direct catalogs link 5,153 BRs to TS and 3,719 through TC to physical TD; 62 matrix BR-to-TS links do not match direct scenario requirements; 28 FULLY_TRACED leaves omit TD. |
| `AIV-007` | Compare AI BRs with a Test Solution-owned, source-anchored, in-scope independent denominator. Store every mapping disposition and evidence. | `RuleCatalogBaselineLoader`, `Run2CrosswalkLoader`, `AiCoverageAssessmentService`; Run4 source-anchor candidate exporter | `AiCoverageAssessmentServiceTest`, `Run2TraceabilityAdapterTest`, `ReviewedRun2CrosswalkMigrationServiceTest`, `scripts/validate-ai-pipeline-delivery-review.cjs` | Run4 compares all AI BRs regardless of approval against the 601-rule independent baseline: 562 AI BRs have candidates covering 270 Test BRs, all REVIEW_REQUIRED. No Run4 semantic matches are confirmed; all 601 Test BRs remain NOT_ASSESSED. Run2 decisions are not transferred. |
| `AIV-008` | Apply explicit match dispositions: `CONFIRMED`, `REVIEW_REQUIRED`, `MISSING`. A confirmed match requires anchor and business evidence; heuristic/alias-only candidates remain review-required. Keep unresolved/blocked BRs in an explicit blocked collection rather than the active canonical chain. | Canonical crosswalk, evidence-qualified matching, explicit blocked-review records | `ProducerNeutralContractTest`, `CanonicalTraceabilityValidatorTest`, `AiCoverageAssessmentServiceTest`, `Segment100PaymentNetworkOracleTest`, `scripts/build-ai-run4-br-chain-crosswalk.py` | Run4 exact segment/element candidates are explicitly `REVIEW_REQUIRED`; composite/ambiguous, missing-anchor, and no-candidate outcomes are separate. No source-anchor candidate is labeled confirmed or AI_ONLY. |
| `AIV-009` | Validate BR quality/provenance and TS intent independently; preserve AI flags/verdicts as producer evidence, not Test Solution verdicts. | Source-rule checks, atomicity/review policy, scenario-rule comparison | Stage-specific checks and source-review cases; no AI self-verdict may auto-approve | October 5 includes 3,198 scenario `FAIL` verdicts and 10,559 flagged scenarios; Test Solution must independently adjudicate them. |
| `AIV-010` | Validate the full BR -> TS -> TC -> TD chain, expected outcome, payload contract, explicit test-data readiness, and segment semantics before full-chain claims. Readiness is separate from expected payload outcome; placeholders and unlinked candidates never count as executable chains. | `CanonicalTestCaseQualityValidator`, `CanonicalTraceabilityValidator`, `CanonicalTestDataLinkPolicy`, aggregate generators, `Run2PayloadBatchValidator`; Run4 matrix/catalog/physical join | `AggregateEvidenceMergeTest`, `AiCoverageAssessmentServiceTest`, `CanonicalTraceabilityValidatorTest`, `Run2SegmentValidationTest`, `AppendixCoverageConsistencyTest`, `scripts/validate-ai-pipeline-delivery-review.cjs` | Run4 row-level export checks matrix BR-TS-TC-TD declarations against direct scenario/case catalogs and physical candidate TD/metadata files for every AI BR. Presence is structural evidence, not execution certification. |
| `AIV-011` | Calculate separate metrics with explicit denominators: independent Test Solution rules, AI BR inventory, and complete chains. Never present an unavailable denominator as 0%. | `IndependentRequirementBaseline`, `AiCoverageAssessmentService`, `AiCoverageAssessmentJsonFields`, assessment/evidence/segment report writers | `AiCoverageAssessmentServiceTest`, `Run2SegmentReportWriterTest`, `Run2PreSmeEvidenceWriterTest`, `Run2ReviewArtifactsConsistencyTest`; `scripts/validate-ai-pipeline-delivery-review.cjs` | Run4 shows `0/601` confirmed-match evidence with status `NOT_ASSESSED`; 562 AI BRs have source-anchor candidates to 270 Test BRs, none confirmed. The 72.17% statistic remains labeled only as AI-internal BR-to-TC linkage. |
| `AIV-012` | Verify validator effectiveness using positive, negative, boundary, conflict, context, and mutation tests; detect wrong rules and wrong locations, not only malformed JSON. Distinguish packed-BCD prefix bytes from the TLV value and keep unresolved producer field boundaries review-gated. | Source-anchored segment/domain validators, mutation testers, and risk-based independent review; `AppendixREmvChipDataOracle.assessWithPackedLengthPrefix` is an explicit candidate path and does not change bare-TLV parsing. | `AppendixREmvChipDataOracleTest` covers matching-prefix review disposition, prefix/count mismatch failure, and unchanged bare-TLV behavior; existing segment mutation and payload-validator tests remain applicable. | Validator regression evidence is separate from validation of a particular AI delivery. A green suite does not prove producer adapter provenance, semantic approval, or host evidence. A matching prefix candidate remains `REVIEW_REQUIRED` until the field boundary is declared. |
| `AIV-013` | Verify report provenance, summary arithmetic, and decision consistency against source rows and validation evidence. | JSON report writers plus deterministic report-consistency checks | `AiCoverageAssessmentServiceTest`, `Run2ReviewArtifactsConsistencyTest`, `scripts/validate-ai-pipeline-delivery-review.cjs` | Run4 regression checks assert 5,153 unique AI BR rows, approval-independent inclusion, source-match disposition totals, full matrix leaf/chain counts, 601-rule denominator, zero confirmed matches, `NOT_ASSESSED`, and separation from AI-internal linkage. |
| `AIV-014` | For any scoped taxonomy such as the 16 Section 5 payment-network leaves, trace each leaf to AI BR/TS candidates using source evidence. Do not infer leaf coverage from related card types, field-value lists, or keyword hits. | Leaf-specific Test Solution baseline + canonical crosswalk and per-leaf coverage rows | `Segment100PaymentNetworkOracleTest` validates the Test Solution allowlist; add mapping/coverage tests when an AI adapter and crosswalk exist | October 5 has 0/16 explicit leaf BR IDs, source-rule keys, or `networkType` values. Related behavior is not a confirmed leaf mapping. |
| `AIV-015` | Keep specification-release overlays source-hash-pinned, anchor-versioned, separately counted, and explicitly review-gated; never promote a partial new-release overlay into full-pack support. | Release-specific source catalog, canonical test model, training-status overlay and bounded logical validator | `AppendixAFIncrementalAuthorizationValidatorTest` checks the pinned PDF hash, exact source quotes, 2026-4 anchors, artifact links, replay outcomes, AF-ID/Table-82 distinction and review-only readiness | The 2026-4 AF overlay has nine draft rules and 21 logical cases. Complete open-state behavior is tested only with synthetic records. AF Transaction ID to Table 82 N4 mapping, complete serialized request/response fixtures, processor behavior, and SME approval remain review-required. |
| `AIV-016` | Reconcile every approved and candidate catalog occurrence and physical artifact for a complete delivery; detect duplicate/conflicting IDs, invalid/orphan links, unsafe/missing TD paths, mismatched metadata, and gaps against the full independent Test BR baseline. Preserve producer states without treating them as approval. | `scripts/audit-ai-delivery-coverage.py`; immutable file inventory, occurrence/conflict ledgers, physical-TD assessment, independent Test BR ledger and hash-bound report | `python.exe scripts/audit-ai-delivery-coverage.py --self-test`; delivery `VERIFICATION.md` independently recounts output hashes, catalog occurrences, conflicts, route counts, pair-review rows and anchor queues | Physical completeness is a declared-route/file-integrity result, not payload semantics. Candidate reach is structural only; semantic and executed coverage stay `NOT_ASSESSED`/null until authorized decisions and execution evidence exist. Auxiliary formats remain unassessed unless parsed. |
| `AIV-017` | Execute only explicitly bound, source-profile field/structure predicates against stable byte-hashed candidate inputs; reconcile every outcome to the independent Test BR baseline and delivery chain; keep executor binding, decisive outcomes, case-any-result, candidate observations, semantic decisions and host execution separate. | `Atl105FieldStructureRuleRunner`; `scripts/summarize-ai-technical-validation-coverage.py` | `Atl105FieldStructureRuleRunnerTest`; `python.exe scripts/summarize-ai-technical-validation-coverage.py --self-test`; independently verified full-recursive v3 runner report and full-recursive v2 coverage `manifest.json` | Run1 covers both QE-shaped candidate directories: 39,479 paired metadata/payload records and one separately inventoried unpaired payload; 326/601 rules are in runner scope, 58 have limited/partial bindings and 41 have decisive outcomes. Candidate predicate observations are not semantic confirmations; remaining rules and host execution are not assessed. |
| `AIV-018` | Block execution-ready BR-to-test chains unless BR text/atomicity and source evidence are explicit, scenarios declare intent/preconditions, and every linked BR has an anchored assertion with an expected result. Verify citation root containment, SHA-256 and literal quote without inferring semantic approval. | `CanonicalRequirement`, `CanonicalSourceEvidence`, `CanonicalScenario`, `CanonicalTestAssertion`; `CanonicalTraceabilityValidator`; `CanonicalTestCaseQualityValidator` | `CanonicalTraceabilityValidatorTest`; `AiCoverageAssessmentServiceTest` | Draft/review artifacts remain readable. `ATOMIC` is a declared disposition, not an automated semantic judgment; source quote/hash integrity is not reviewer authentication, SME approval, or host execution. |
| `AIV-019` | Ensure every independent Test BR has a scenario record; for missing scenarios, emit only a source-anchored review proposal and explicitly record unresolved intent/context/test-design gaps. Do not promote generated proposals to executable scenarios. | `GenerateCompleteCanonicalTestSolutionPackage.draftScenarioProposal` | `CanonicalRuleDenominatorTest.everyMissingScenarioIsAReviewOnlySourceAnchoredProposal` | Proposals derive only from the independent BR title and anchor. Preconditions, flow applicability, positive/negative boundaries, expected outcomes, semantic approval, and execution remain unresolved until independently authored/reviewed. |
| `AIV-020` | Require AI test-case presence for each configured card-brand/network axis value and each of the 36 financial Prompt Code card types. Measure these dimensions separately; do not invent a network-by-card-type Cartesian product. Exclude conflicted cases and bind physical payloads to the frozen delivery inventory. | `scripts/audit-ai-network-cardtype-coverage.py`; `Segment100CardTypeOracle` | Script `--self-test`; report manifest and separate network-brand/card-type coverage ledgers | Current delivery has cases for four declared brand-axis values but Prompt Code payload coverage is incomplete (1/36 card types). Brand-axis enumeration is not an independent global network denominator; Section 5 taxonomy is a separate mixed classification and provides no brand/card-code compatibility matrix. |
| `AIV-021` | Require a versioned, explicit producer-to-ATL105 mapping for account/track aliases. Declare entry method; split Track 2 into Elements 2/12 at `=` and Track 1 at the first `^`; preserve Segment 130 as a distinct EMV segment. Unknown aliases, delimiters, or modes remain `REVIEW_REQUIRED`; never infer or normalize them from the payload. | Proposed producer-specific adapter; `AccountNumberEntryValidator` currently evaluates only explicit normalized Element 2/12 fields and does not consume a producer `TrackData` alias. Discussion tracked as `AID-0005`. | `Segment100Elements2And12ExecutionTest` covers normalized synthetic fields and mutations, but not the producer alias adapter. Add adapter golden, round-trip, delimiter, missing-context, and EMV-coexistence tests when the contract is agreed. | Partial: the user-supplied sample has a producer `TrackData` alias, a non-source delimiter, and Segment 130; no converter mapping or entry-method contract was supplied. This is not yet a confirmed AI defect. Sample values are not persisted. No semantic, wire, or host outcome is inferred. |
| `AIV-022` | Validate multi-step lifecycle flows as linked transactions, not isolated request JSON: enforce source-defined transaction-code path and Sequence Number carry; bind completion Approval Number to the actual Authorization response; evaluate amount/partial-approval rules from host-approved amount evidence; validate every step's declared payload and matching metadata. Do not enforce same Terminal ID as a universal rule unless source-backed for that flow. | `Segment100MultiStepCoverageValidator.validateAuthorizationCompletionWithHostResponse`; `scripts/review-ai-authorization-completion-chains.py` | `Segment100MultiStepCoverageValidatorTest`; v6 auth-completion case register, BR/TS/TC/TD matrix, amount-host-evidence queue and hash manifest | Partial: runtime-binding API and lifecycle regressions exist; current Oct 10 cohort has 138 flows, 124 without direct AI BR links, 133 synthetic approval placeholders, 5 missing approvals, 35 amount-change cases needing host evidence. Physical files/metadata match, but semantic approval and host execution remain pending. |
| `AIV-023` | After BR coverage, inventory and validate every in-scope AI artifact and catalog occurrence, not only matched BRs or a sample: approved/candidate BR/TS/TC, every multi-step leg, payload, metadata, response oracle, traceability matrix, run output, and supported auxiliary format. Reconcile all rows/links, source hashes, serialization, categories, expected outcomes, and unresolved dispositions. A standalone sample without a frozen delivery inventory and BR -> TS -> TC -> TD identity cannot establish delivery coverage. | Post-BR Gate 11 in `AI-Solution-Output-Validation-Plan.md`; `audit-ai-delivery-coverage.py`, `summarize-ai-technical-validation-coverage.py`, and lifecycle-specific validators | `audit-ai-delivery-coverage.py --self-test`; technical coverage summarizer `--self-test`; per-run hash manifest and row-level artifact/chain ledgers | Partial: V6 inventories 79,040 files and validates declared graph/file integrity; technical execution covers a bounded field/structure slice. Auxiliary formats, unsupported semantics, pending BR/Test BR decisions, missing oracles, and host execution must remain explicitly unassessed until their own validator/evidence exists. |

**eWIC request-presence evidence for `AIV-010` / `AIV-012`:** `Segment100EwicValidatorTest` and `Segment100EwicArtifactIntakeValidatorTest` verify that source-optional eWIC request roles can omit Segment 103 with a matching Element 63 count, while Purchase Completion and Voucher Clear still require it and WIC data. Tests also cover additional included segment objects in the count. This is ATL105 request-shape/applicability validation; Commerce Hub mapping and response outcomes remain external and review-gated.

**Execution-ready Test Model evidence for `AIV-010` / `AIV-012` / `AIV-013`:** `CanonicalTraceabilityValidator.validateForExecution` requires a BR statement and `ATOMIC` disposition, verifies source citation containment/hash/literal quote, requires declared scenario intent/preconditions, and checks that every BR linked through a case's scenarios has an anchored assertion and expected result. `CanonicalTraceabilityValidatorTest` covers positive execution-ready structure plus missing/non-atomic requirements, missing assertions, wrong anchors, changed hashes, fabricated quotes, path traversal, and undeclared preconditions. The gate validates declared model evidence; it does not authenticate the author, approve semantic interpretation, or perform host execution.

## Validation-Control Coverage Method

Delivery-wide regression evidence for AIV-001/002/003/005/006/010/011/013:
`audit-ai-delivery-coverage.py --self-test` checks all-tier occurrence retention,
conflicting IDs, duplicate keys, orphan links, explicit declaration paths,
metadata identity mismatch, traversal refusal and null semantic coverage.
Real-delivery ledgers hash supplied files and baseline inputs and recheck
stability before publication. Typed-proposal tests keep MESSAGE separate from
RESPONSE and CROSS_SEGMENT separate from CROSS_FIELD; neither producer category
grants a semantic anchor. Pending pair-review queues do not grant SME equivalence.
Physical JSON/file checks are bounded; matrix,
archive and auxiliary format validation remains explicitly unassessed.

For `AIV-009`, `AIV-010`, `AIV-012` and `AIV-013`, Java
`SemanticEvidenceValidator` adds complete declared structured-claim comparison,
isolated control/mutant predicates, independently profiled companion checks,
captured segment/full-message framing, request-bound outcome comparison and a
separate full negative-effectiveness gate. `SemanticEvidenceBatch` runs those
gates without modifying the intake or prior assessment. The versioned evidence
contract is documented in [Canonical Artifact Contract](../canonical-artifact-contract.md).
`SemanticEvidenceValidatorTest` tests positive/contradictory/missing/stale source
evidence, unintended mutations, invalid controls, actual wire lengths, unsupported
formats, exact oracle bindings and batch identity/version handling. Regression
success demonstrates the methods, not that the October delivery supplied the
structured claims, complete control profiles, wire captures or authoritative
host oracles. Unprovided evidence remains `NOT_ASSESSED`; SME approval and
execution certification are never inferred from a technical gate pass.

The bounded follow-up for `AIV-009`, `AIV-010` and `AIV-012` adds paired omission/presence, exact composite/changed packing, sequence agreement/difference, ambiguous-label refusal and review-matrix disagreement tests to `scripts/assess-ai-semantic-batch.py --self-test`. `semantic-bounded-control-pairs.json` is source-hash-bound, predicate-scoped evidence. The actual-data run records 20 preserved values, four preserved omissions and one external-oracle blocker. The code-1 queue retains all 2,005 rows, physical hashes where available and matrix/source concerns; no reviewed decision or execution status is inferred. The HTML regression checks the 880/1,098/27 context partition and 165 review concerns.

For `AIV-009`, `AIV-010` and `AIV-012`, `scripts/assess-ai-semantic-batch.py` provides a bounded first-batch assessment and controlled `--self-test` evidence for explicit metadata interpretation, source-fixed identity/six-digit predicates, mutation preservation/change and unsupported roots. The run register distinguishes intended negative predicate failures from AI defects, and preserves composite/absence/oracle blockers. All expected-code-1 queue rows remain independently unassessed. These checks do not certify complete BR objectives, isolated negative effectiveness, source-family response behavior or execution coverage.

For `AIV-011` and `AIV-013`, the complete-handoff Coverage presentation is implemented by `scripts/generate-ai-artifact-filter-view.py`. Its streaming BR/TS/TC calculations assert agreement with the independently recounted handoff totals. `scripts/validate-ai-artifact-filter-view.cjs` checks overall percentages, segment N/A/unassigned rows, transaction-group reconciliation, response declarations, scenario types and unavailable semantic status. The JSON evidence binds AI input, response-matrix and catalog hashes. These presentation checks do not certify semantic coverage; the HTML/PDF retain `NOT_CALCULABLE` / `NOT_ASSESSED` and non-additive segment warnings.

For `AIV-010` and `AIV-013`, assessment readiness uses `CanonicalTraceabilityValidator.validateForExecution`, not producer-selectable structural mode. The regression evidence in `AiCoverageAssessmentServiceTest` covers non-strict review-required scenarios, absent/draft/blocked/review states at BR/TS/TC levels, non-executable TDs, missing request/response envelopes, future dates and failed/absent payload validators. `Run2PreSmeEvidenceWriterTest` rejects abbreviated strategy maps. Structural coverage and readiness remain separate; these checks do not verify an approval signature or grant SME certification.

Control coverage answers: **does the Test Solution have an implemented method and regression evidence for each applicable AIV control?** It does not measure how many ATL105 rules the AI covered.

For each control, report:

```text
controlId
applicableToDelivery
implementationStatus: IMPLEMENTED | PARTIAL | NOT_IMPLEMENTED
regressionEvidence: test IDs or NOT_PRESENT
runDisposition: PASS | FAIL | REVIEW_REQUIRED | BLOCKED | NOT_ASSESSED
findingIds
owner
```

Do not compute an aggregate percentage while applicable controls have unknown test evidence or while a required control is blocked. A test file's existence is evidence that a test is defined; report a passing test run only when the test command was actually executed successfully.

## Business-Requirement and Artifact Coverage Method

ATL105 BR coverage remains governed by the independent rule catalog and the [canonical artifact contract](../canonical-artifact-contract.md). Assessment JSON reports `coverageDenominatorType=INDEPENDENT_TEST_SOLUTION_RULES` and a separate `aiBusinessRequirementDenominator`:

- The denominator is the eligible Test Solution-owned mandatory rule set, not the AI BR count.
- `confirmedRequirementCoveragePercent` is confirmed Test Solution rules over that independent rule denominator. It is not an AI-BR match percentage.
- `confirmedAiBusinessRequirementInventoryPercent` uses the AI BR inventory as its denominator. Separate counts expose AI BRs with a crosswalk disposition, confirmed, review-required, missing-mapped, and unmapped BRs.
- Segment reports retain both denominators separately. Executive reports may weight Test Solution rule counts, but must not sum AI BR inventories across segments unless a run-wide deduplicated AI BR inventory is supplied.
- If the independent baseline is structurally invalid or empty, rule-coverage percentages are serialized as `null` and rendered `NOT_CALCULABLE`, not `0%`. Structural validity does not establish SME or segment certification.
- Match each AI BR to zero, one, or more Test Solution rules using canonical source anchors and business equivalence.
- Record `CONFIRMED`, `REVIEW_REQUIRED`, or `MISSING` with reason, evidence, owner, delivery ID, and source hashes.
- Label an AI BR `AI_ONLY` only after it has been assessed against the complete eligible independent baseline and no equivalent rule is found. Runtime `unmappedAiBusinessRequirements` means no crosswalk disposition and is not an AI-only verdict.
- Label an artifact `INVALID` only after an independent validator/source review establishes the defect. AI `FAIL`, flags, low confidence, or missing links are evidence/signals, not by themselves independent invalidity decisions.
- Full-chain coverage requires linked BR -> TS -> TC -> TD with consistent anchors and valid expected behavior/data.
- For taxonomies, publish one row per independent leaf/rule. Keyword mentions or a related segment/case do not count without an evidence-backed mapping.

Per-delivery results are published under `specifications/ATL105/test-output/ai-solution-independent-review/` with a dated delivery identifier. The October 5 assessment is [run1-requirement-coverage-calculation-2026-10-05.md](../../test-output/ai-solution-independent-review/run1-requirement-coverage-calculation-2026-10-05.md); its detailed and segment-level registers are linked from that report.
