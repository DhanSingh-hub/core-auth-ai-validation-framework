# AI Validation Control Coverage: October 5 Delivery

**Delivery:** `SRC-ATL105-PDF-001`, one combined AI run archived as `2026-10-05/Run1`
**Control catalogue:** [AI Artifact Validation Controls](../../docs/test-validation-strategy/AI-ARTIFACT-VALIDATION-CONTROLS.md)
**BR coverage result:** `NOT_CALCULABLE - REVIEW_REQUIRED`
**Scope:** Test Solution rule/method coverage and this delivery's control outcomes; this is not ATL105 payment BR coverage.

## How to Read This Report

Two different questions are tracked separately:

1. **Validator-control coverage:** does the Test Solution have an implemented rule/method and regression evidence for each applicable `AIV-*` control? A referenced test means a test exists; unless a test command is recorded as run, it is not a claim that the test passed in this review.
2. **Delivery result:** did this exact AI delivery pass, fail, or lack prerequisites for the control?

The `AIV-*` controls are not ATL105 BRs. Their coverage must never be added to, or used as the denominator for, the independent ATL105 rule/BR coverage metric.

## Control Coverage Matrix

| Control | Test Solution implementation and regression evidence | Control implementation coverage | October 5 delivery outcome |
|---|---|---|---|
| `AIV-001` Immutable intake and provenance | Intake adapters; `AiArtifactIntakeServiceTest`; storage policy | Partial: adapters resolve sources; run manifest/hash automation is not part of the resolver | `PASS_WITH_LIMITATION`: all six received files were preserved and hash-checked; source-document hash is absent from the AI manifest |
| `AIV-002` Schema/version detection and adapter normalization | Producer-neutral JSON schemas; `ProducerNeutralContractTest`; `Run2TraceabilityAdapterTest` | Partial: Run2 traceability format is adapted; October flat-catalog/XLSX format has no production adapter | `REVIEW_REQUIRED`: catalogs were independently parsed for this analysis, but the normal Java intake path does not normalize this format |
| `AIV-003` Stable IDs and cross-run change detection | Canonical IDs and crosswalk validation exist; no regression test currently proves changed content under a reused ID is detected across deliveries | Gap | `FAIL`: all 6,473 September BR IDs recur; only 10 retain the checked source/business identity fields. All 11,094 prior scenario IDs recur, but only 7 keep the same primary BR link |
| `AIV-004` Specification/version/source identity | `Run2SpecificationVersionResolution`; version/hash cases in `Run2TraceabilityAdapterTest` | Partial: hash-bound version correction is supported for the Run2 adapter | `FAIL`: October BR manifest omits specification version; no source-document hash is supplied; TS rows declare `2026-3` |
| `AIV-005` Canonical source anchors | `CanonicalTraceabilityValidator`; `ProducerNeutralContractTest`; `CanonicalTraceabilityValidatorTest` | Implemented/test evidence exists for canonical packages | `FAIL`: October BR and TS records do not carry canonical source anchors |
| `AIV-006` Producer-internal graph integrity | `CanonicalTraceabilityValidator`; `Run2TraceabilityAdapterTest` | Implemented/test evidence exists for canonical graph checks and review retention | `PARTIAL`: 48,868 TS-to-BR references resolve; 155 TSs have no BR link. There are no TCs or TDs |
| `AIV-007` Independent denominator and evidence-backed crosswalk | `RuleCatalogBaselineLoader`, `IndependentRequirementBaseline`, `Run2CrosswalkLoader`; `AiCoverageAssessmentServiceTest`, `ReviewedRun2CrosswalkMigrationServiceTest` | Implemented/test evidence exists for canonical Run2 comparisons | `BLOCKED`: no crosswalk exists for this exact run; training status has zero segments certified for AI intake. September mappings are not reusable by ID |
| `AIV-008` Match/review/missing disposition; prevent heuristic auto-confirmation | `vocabulary.json`, crosswalk model/loader, `AiCoverageAssessmentService`; `ProducerNeutralContractTest`, `Run2TraceabilityAdapterTest`, `AiCoverageAssessmentServiceTest` | Implemented/test evidence exists for supported canonical format | `NOT_ASSESSED`: no October crosswalk; zero same-run confirmed mappings recorded does not mean zero semantic matches |
| `AIV-009` Independent BR/TS semantic and approval review | Segment validators and AI review artifacts; review-state policy in strategy | Partial: technical validators exist by segment; no generic check makes producer approval evidence valid | `FAIL/REVIEW_REQUIRED`: both approval audits say `automated-batch-nonSME`; 3,817 BRs and 10,559 TSs are flagged; 3,198 TS verdicts say `FAIL` (AI-side evidence only) |
| `AIV-010` TC/TD chain and payload validation | `CanonicalTestCaseQualityValidator`, `CanonicalTraceabilityValidator`, `Run2PayloadBatchValidator`; coverage and segment tests | Implemented/test evidence exists for canonical packages and supported segments | `BLOCKED`: October delivery contains zero TCs and zero TDs |
| `AIV-011` Separate metrics and explicit denominators | `AiCoverageAssessmentService`, `AiCoverageAssessmentReportWriter`; `AiCoverageAssessmentServiceTest` | Implemented/test evidence exists for independent canonical baseline calculations | `NOT_CALCULABLE`: no eligible Test Solution denominator/crosswalk/full chain for October |
| `AIV-012` Validator effectiveness and mutation evidence | Segment mutation testers/runners and corresponding tests | Partial: mutation frameworks exist for implemented segment validators; this review did not run the suite | `NOT_RUN_FOR_DELIVERY`: not applicable as an AI artifact pass/fail; validator regression-suite execution was not part of this data review |
| `AIV-013` Report arithmetic and decision consistency | Assessment/report writers; `AiCoverageAssessmentServiceTest`, `Run2ReviewArtifactsConsistencyTest` | Partial: canonical assessment reporting has tests; producer catalog summary reconciliation is not enforced by the current intake engine | `FAIL`: BR confidence summary reports HIGH/MEDIUM/LOW `3451/2548/888`; record recount is `3531/2492/864` |
| `AIV-014` Leaf/taxonomy-specific coverage disposition | Section 5 matrix and `Segment100PaymentNetworkOracle`; `Segment100PaymentNetworkOracleTest` | Partial: Test Solution allowlist recognition is tested; mapping AI artifacts to each leaf is not automated | `NOT_MAPPED`: 0/16 canonical leaf BR IDs, source-rule keys, or `networkType` fields occur in the AI catalogs. Related mentions do not constitute leaf coverage |

## Delivery Coverage Summary

- **AI BRs:** 6,887. The row-level register is [run1-ai-br-crosswalk-backlog-2026-10-05.csv](run1-ai-br-crosswalk-backlog-2026-10-05.csv); all remain `NOT_ASSESSED_NO_CURRENT_RUN_CROSSWALK`.
- **Internal AI BR-to-TS links:** 48,868 references resolve; 155 scenarios are unlinked. This is producer-internal traceability, not independent coverage.
- **Independent BR matches:** no October 5 crosswalk. Report true matched/unmatched/AI-only/independently-invalid counts as unavailable until a same-run, source-anchored crosswalk is reviewed.
- **Test Solution baseline:** 601 catalogued rules across 49 segments; zero segments are `TRAINED_FOR_INTAKE` in the current training status.
- **TC/TD/full-chain:** absent; full-chain coverage cannot be calculated.
- **Section 5 payment-network taxonomy:** no explicit AI leaf mapping for any of the 16 leaves; related operational BRs/TSs remain candidates until individually linked and evidence-reviewed.

## Required Next Controls

1. Implement an October-format adapter that reads the single-run manifest, candidate/approved JSON, and workbook exports while preserving original files and typed values.
2. Add regression tests that fail when an ID is reused with changed source/business identity and verify change-manifest behavior across runs.
3. Require source-document identity/hash, specification version, and canonical anchors before crosswalk confirmation.
4. Keep producer approval and LLM verdicts as untrusted claims until evidence-backed review is recorded.
5. Add a per-eligible-rule mapping matrix with AI BR/TS IDs, disposition, evidence, owner, and separate status for TC/TD-chain completeness.
6. Recompute all summaries from final serialized records; fail the pipeline on summary/count drift.
7. Add per-leaf Section 5 coverage rows only where the scope requires those training metadata classifications; do not infer them from related card-type, EBT, fleet, stored-value, or loyalty mentions.

**Evidence limitation:** this report maps existing implementation and test evidence by source inspection. No full Maven test suite was run as part of this October 5 artifact assessment.
