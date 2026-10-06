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
These controls do not establish complete field extraction, wire validity,
semantic equivalence, approved fixtures, AI coverage, or execution certification.

| Control | Validation requirement | Method / implementation surface | Regression evidence | Current format boundary |
|---|---|---|---|---|
| `AIV-001` | Freeze the received delivery; retain source location, revision, received time, and file/package hashes. Never normalize the preserved source copy. | `AiArtifactIntakeService`, source-specific intake adapters, artifact storage policy | `AiArtifactIntakeServiceTest`; provenance/hash checks for each intake workflow | Intake adapters resolve local/shared/checked-out Git sources. Hash-manifest generation is not established by the location adapters themselves. |
| `AIV-002` | Identify the package/schema version and reject or explicitly adapt unknown shapes. Validate required fields, value types, encodings, and workbook-vs-JSON export semantics. | Versioned producer adapter -> canonical artifact model; producer-neutral schemas | `ProducerNeutralContractTest`; adapter-specific contract tests | The September nested Run2 traceability format has `Run2TraceabilityAdapter`. The October 5 flat BR/TS catalog plus XLSX exports has no equivalent production adapter. |
| `AIV-003` | Preserve producer-local IDs within a run, but detect reuse of an ID for changed content across runs. Never treat a reused local ID as cross-run identity. | Delivery manifest, per-run ID index, content/source fingerprint comparison | Add regression cases for changed content under a reused ID; canonical comparison must key on anchors/evidence | October 5 reuses September IDs for changed BR and scenario content; this is a current gap. |
| `AIV-004` | Establish specification/version/source-document identity and source hash before source claims can be confirmed. | Manifest validation and, where needed, hash-bound version resolution | `Run2TraceabilityAdapterTest` version-resolution and hash-change cases | October 5 BR manifest omits specification version; no source PDF hash is supplied in the catalog package. |
| `AIV-005` | Require resolvable canonical source anchors on BR/TS/TC/TD records before independent matching. | `SourceAnchor`, canonical anchor schema, `CanonicalTraceabilityValidator` | `ProducerNeutralContractTest`, `CanonicalTraceabilityValidatorTest` | October 5 records use page/entity fields but do not carry canonical source anchors. |
| `AIV-006` | Validate the producer-internal artifact graph and report missing, duplicate, orphaned, and unresolved links. Only `testData[].testCaseIds` forms the canonical TC -> TD edge; `coversBr` alone is a BR hint, not a chain link. | Canonical graph validation; aggregate generator quarantines unlinked TD candidates with provenance | `CanonicalTraceabilityValidatorTest`, `Run2TraceabilityAdapterTest`, `AggregateEvidenceMergeTest` | October 5 BR -> TS links can be structurally screened; 155 TS records are unlinked. There are no TC/TD artifacts to validate. |
| `AIV-007` | Compare AI BRs with a Test Solution-owned, source-anchored, in-scope independent denominator. Store every mapping disposition and evidence. | `RuleCatalogBaselineLoader`, `Run2CrosswalkLoader`, `AiCoverageAssessmentService` | `AiCoverageAssessmentServiceTest`, `Run2TraceabilityAdapterTest`, `ReviewedRun2CrosswalkMigrationServiceTest` | Historical crosswalks are delivery-bound. Reuse only after exact source/business identity is revalidated; never migrate by local ID alone. |
| `AIV-008` | Apply explicit match dispositions: `CONFIRMED`, `REVIEW_REQUIRED`, `MISSING`. A confirmed match requires anchor and business evidence; heuristic/alias-only candidates remain review-required. Keep unresolved/blocked BRs in an explicit blocked collection rather than the active canonical chain. | Canonical crosswalk, evidence-qualified matching, explicit blocked-review records | `ProducerNeutralContractTest`, `CanonicalTraceabilityValidatorTest`, `AiCoverageAssessmentServiceTest`, `Segment100PaymentNetworkOracleTest` | October 5 has no same-run independent crosswalk; all 6,887 BRs are `NOT_ASSESSED`, not automatically missing or AI-only. Section 5 context/receipt parent BRs remain blocked outside the 26 active matrix BRs. |
| `AIV-009` | Validate BR quality/provenance and TS intent independently; preserve AI flags/verdicts as producer evidence, not Test Solution verdicts. | Source-rule checks, atomicity/review policy, scenario-rule comparison | Stage-specific checks and source-review cases; no AI self-verdict may auto-approve | October 5 includes 3,198 scenario `FAIL` verdicts and 10,559 flagged scenarios; Test Solution must independently adjudicate them. |
| `AIV-010` | Validate the full BR -> TS -> TC -> TD chain, expected outcome, payload contract, explicit test-data readiness, and segment semantics before full-chain claims. Readiness is separate from expected payload outcome; placeholders and unlinked candidates never count as executable chains. | `CanonicalTestCaseQualityValidator`, `CanonicalTraceabilityValidator`, `CanonicalTestDataLinkPolicy`, aggregate generators, `Run2PayloadBatchValidator` | `AggregateEvidenceMergeTest`, `AiCoverageAssessmentServiceTest`, `CanonicalTraceabilityValidatorTest`, `Run2SegmentValidationTest`, `AppendixCoverageConsistencyTest` | October 5 AI delivery has no TCs or TDs; full-chain coverage is blocked, not zero-valued evidence. |
| `AIV-011` | Calculate separate metrics with explicit denominators: independent Test Solution rules, AI BR inventory, and complete chains. Never present an unavailable denominator as 0%. | `IndependentRequirementBaseline`, `AiCoverageAssessmentService`, `AiCoverageAssessmentJsonFields`, assessment/evidence/segment report writers | `AiCoverageAssessmentServiceTest`, `Run2SegmentReportWriterTest`, `Run2PreSmeEvidenceWriterTest`, `Run2ReviewArtifactsConsistencyTest` | Implemented/test evidence exists for structurally valid canonical baselines; the report marks structural validity separately from certification | October has no same-run crosswalk or trained-for-intake baseline; rule coverage is `NOT_CALCULABLE`; AI BRs are separately `NOT_ASSESSED` |
| `AIV-012` | Verify validator effectiveness using positive, negative, boundary, conflict, and mutation tests; detect wrong rules and wrong locations, not only malformed JSON. | Segment/domain validators and mutation testers | Segment mutation and validator tests (for example `Segment100MutationTesterTest` and segment payload-validator tests) | Validator regression evidence is separate from validation of a particular AI delivery. |
| `AIV-013` | Verify report provenance, summary arithmetic, and decision consistency against source rows and validation evidence. | JSON report writers plus deterministic report-consistency checks | `AiCoverageAssessmentServiceTest`, `Run2ReviewArtifactsConsistencyTest` | October 5 BR confidence summary differs from record-level bands; report as a defect and block acceptance. |
| `AIV-014` | For any scoped taxonomy such as the 16 Section 5 payment-network leaves, trace each leaf to AI BR/TS candidates using source evidence. Do not infer leaf coverage from related card types, field-value lists, or keyword hits. | Leaf-specific Test Solution baseline + canonical crosswalk and per-leaf coverage rows | `Segment100PaymentNetworkOracleTest` validates the Test Solution allowlist; add mapping/coverage tests when an AI adapter and crosswalk exist | October 5 has 0/16 explicit leaf BR IDs, source-rule keys, or `networkType` values. Related behavior is not a confirmed leaf mapping. |

## Validation-Control Coverage Method

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
