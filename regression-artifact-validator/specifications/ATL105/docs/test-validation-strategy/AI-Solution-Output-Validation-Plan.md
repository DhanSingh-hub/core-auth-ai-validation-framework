# AI Solution Output Validation Plan

**Platform:** Core Auth Regression Test Solution  
**Current specification pack:** ATL105 2026-3  
**Primary source inspected:** `core-auth-test-generation-platform/src/pipeline`  
**Purpose:** Define how Test Validation independently validates the AI Solution generation workflow and every outcome artifact

**Validation-control register:** Stable `AIV-*` controls, implementation/test evidence, and the distinction between validator-control coverage and ATL105 BR coverage are maintained in [AI Artifact Validation Controls](AI-ARTIFACT-VALIDATION-CONTROLS.md). This plan defines the workflow; the control register defines how we verify that the workflow itself is implemented and tested.

**Mandatory change synchronization:** Every change to AI-output validation code, schemas, adapters, canonical matching, coverage denominators/calculations, validation statuses, or report semantics must update this plan and the [Common LLM Segment Training Handbook](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) in the same change set. Update the affected `AIV-*` control's implementation/test evidence in [AI Artifact Validation Controls](AI-ARTIFACT-VALIDATION-CONTROLS.md), add or revise regression tests for repeatable behavior, and refresh any affected per-delivery result. A code-only or report-only change without its strategy/handbook/evidence updates is incomplete.

**Coverage invariant:** Keep the independent Test Solution rule denominator separate from the AI BR inventory. `confirmedRequirementCoveragePercent` is confirmed independent rules divided by in-scope independent rules; AI-BR inventory metrics use AI BR IDs as their own denominator. A missing crosswalk entry means `NOT_ASSESSED`, not `AI_ONLY`. If the independent denominator is structurally invalid or empty, coverage is `NOT_CALCULABLE` (JSON `null`), not `0%`. Segment AI BR inventories are not added into a run-wide denominator unless IDs have been deduplicated across segments.

**Coverage presentation (2026-10-06):** The complete-handoff HTML/PDF Coverage view reports producer-internal BR/TS-to-TC linkage, physical-output presence, segment-attributed linkage, exact transaction-target labels, scenario-type inventory and expected response-code declarations. Every percentage names its numerator and denominator. Segment counts overlap; scenario groups inherit linked BR attribution and TC groups use direct BR references. Do not infer normalized transaction-code or code/family coverage from producer labels or code mentions. Catalog rule counts remain a separate inventory; semantic rule coverage is `NOT_CALCULABLE` and code/family validation is `NOT_ASSESSED` until evidence-qualified comparison is performed. Hash-bind the AI inputs, response matrix and catalogs used by the view.

**Standard:** This plan is implemented under the [Common LLM Segment Training Strategy](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md). The common strategy is mandatory for every Test Team contributor, agent, and automation. Improvements require evidence, Test Team review, documentation, and regression tests before they become standard.

**Proposed coverage policy — pending Fiserv Leadership approval:** Any coverage metric produced by the AI Solution is treated as a claim under test. The Test Validation solution proposes to calculate its own rule, traceability, scenario, test-case, test-data, semantic-execution, and parameter-combination coverage.

## 1. Understanding of the AI Architecture

Appendix I training uses a source-only, hash-pinned table inventory and independent
field/rule batches for Elements 111/112/113. `AppendixILogicalDataValidator` checks
selected logical representations in Tables 001-009, not the complete segment wire
grammar, network authorization, field allocation, conditional presence, or AI
business equivalence. PASS means only the tested predicate passed; unknown scope
is NOT_ASSERTABLE, missing network context is REVIEW_REQUIRED, and Table 009
framing stays gated by SEG111-SME-002. No delivery coverage or execution-readiness
promotion follows from these checks. See the [Appendix I addendum](../specs/kb/appendix-i/README.md).
`AppendixISegmentWireValidator` separately checks common ASCII framing, contiguous
records, exact lengths and separators for candidate Segment 111 fragments.
Unknown encoding is NOT_ASSERTABLE and conflicted Table 009 framing remains
REVIEW_REQUIRED. The first candidate generator emits 18 logical fragments and 16
wire-segment fragments with explicit review gates; neither is a complete financial
request or a canonical execution-ready package. Full-message/response validation
and complete AI BR/TS/TC/TD reconstruction are still required.
Additional Appendix I batches provide 178 draft logical candidate records for
the remaining 69 tables, not executed or complete request fixtures. A bounded
immutable-delivery intake hashes and joins the existing TC-000016 chain and
reports missing segment length plus a single-record alias length discrepancy
(AIF-0017). Identity joins and local predicates are measured separately from
semantic equivalence, message-family validation and exhaustive AI coverage.
The user selected offline source-backed full-message validation first.
Appendix B's Segment 100 example declares 078 but represents 82 ASCII bytes;
`AppendixIFullMessageSourceInspectionTest` reproduces the discrepancy from the
hash-pinned source. `SEG111-SME-121` remains open. New synthetic candidates must
calculate lengths under Section 12.1, without treating this process decision as
approval of the example's other fields or companion applicability. Source
inspection is not a generated full-request candidate or host-acceptance result.

The AI Solution is a staged test-generation platform, not only a final JSON generator.

```text
Specification acquisition
  -> specification profiling
  -> document extraction
  -> message/segment extraction
  -> field attributes and valid values
  -> appendix/code-table extraction
  -> relationship extraction
  -> deep supplemental extraction
  -> knowledge model and knowledge graph
  -> knowledge approval
  -> requirement derivation
  -> scenario generation
  -> scenario approval
  -> test prioritization
  -> test-case generation
  -> test-case approval
  -> test-data/test-suite serialization
  -> traceability and coverage reporting
```

The inspected AI generation workflow includes stages and outputs for:

- `step0_acquisition`
- `spec_profiler`
- `step1b_messages`
- `step1_entities`
- `step2_attributes`
- `step2b_appendix_codes`
- `step3_relationships`
- `step4_deep_extraction`
- `step4_values`
- `assembled/output`
- `step5_requirements`
- `scenarios/candidates`
- `scenarios/approved`
- `prioritization`
- `test_generation/candidates`
- `test_generation/approved`
- `reporting`

The platform architecture states that the final product is an approved, traceable regression test suite, a traceability matrix, and a coverage report. Test execution, failure triage, and modification of an external automation framework are separate responsibilities unless separately integrated.

## 2. Validation Principle

The Test Validation solution must validate every stage against an independent contract. It must not validate only the final test cases.

```text
AI stage output
  -> structural contract
  -> provenance and source evidence
  -> semantic rule validation
  -> approval-state validation
  -> downstream traceability validation
  -> coverage impact
```

### Adapter and evaluator safeguards

- `AiCoverageAssessmentService` always uses `CanonicalTraceabilityValidator.validateForExecution` for readiness. Producer non-strict mode may not bypass execution metadata, BR/TS/TC `EXECUTION_READY` states, TD `EXECUTABLE` readiness, request/response envelopes, or availability dates. All six named strategies and validation results must pass against a positive, fully confirmed denominator. A linked chain with an unresolved scenario may retain structural coverage but must serialize a review-required execution verdict. Declared readiness is not independent SME approval or proof of source authenticity; those remain separate evidence gates.
- Treat content variation between runs as normal; treat schema/format changes as contract changes. Every delivery must declare its producer, schema/contract version, generator version, specification version, and run identity.
- Select only an explicit versioned adapter. Preserve the original files and record adapter version, hashes, row counts, unknown fields, and normalization dispositions. If no adapter supports the declared format, stop before matching and report `ADAPTER_UNSUPPORTED` / `NOT_ASSESSED`; do not guess field mappings or report zero coverage.
- Verify normalization losslessly for supported fields: source-to-canonical counts, unique IDs, status/flag distributions, and every declared BR/TS/TC/TD link must reconcile. Workbook exports from one run are provenance-linked views, not extra records.
- Keep adapter outcome, structural validity, semantic disposition, and full-chain/execution status separate. `NOT_ASSESSED` from parser/adapter failure is not `MISSING`, `AI_ONLY`, or proof of an AI defect.
- Guard against validator false passes with source-anchored expected behavior, positive/negative/boundary/context tests, mutation tests for missing or incorrect checks, and a risk-based independent review sample. A green test suite proves only tested behavior; it does not certify the source interpretation.
- If no SME/TBA reviewer is available, retain semantic decisions as `PENDING` / `REVIEW_REQUIRED`. Automation may prepare evidence and verify deterministic source facts, but may not create a reviewed event, confirm semantic equivalence, or certify coverage. Use a formally delegated approver or record explicit business risk acceptance; do not infer approval from silence.
- If an AI BR appears source-supported but is absent from the Test Solution baseline, record a baseline-omission candidate and re-derive the rule independently from ATL105. Do not copy the AI BR into the Test Solution or classify it as `AI_ONLY` until the eligible baseline is complete and comparison is performed.

An AI output can be syntactically valid and still be invalid because:

- The source page/rule is wrong.
- A confidence/review flag was ignored.
- A requirement was derived from an unapproved knowledge item.
- A scenario is not linked to a requirement.
- A test case has no approved scenario.
- Test data does not prove the claimed behavior.
- Coverage was calculated against generated scenarios instead of the SME-approved scenario catalog.

## 3. Stage-by-Stage Validation Gates

### Gate 0: Source and acquisition

**AI outputs:** source identity, extracted document, source hash, acquisition metadata.

**Validate:**

- Source document exists and is versioned.
- Source hash is present and reproducible.
- Specification/version is correct.
- Extracted page references are valid.
- No source pages were silently skipped.
- Re-running the same source produces a comparable baseline.

**Failure outcomes:** `SOURCE_MISSING`, `SOURCE_HASH_MISMATCH`, `SOURCE_VERSION_UNDECLARED`.

### Gate 1: Specification profiling

**AI outputs:** specification structure/profile/configuration.

**Validate:**

- Message categories are identified.
- Segment and field containers are mapped.
- Appendix/code-table locations are mapped.
- Profile decisions have source evidence.
- Unknown document structures are flagged for SME review.

**Key invariant:** a new specification should be onboardable through a profile/specification pack without changing the Core Auth engine.

### Gate 2: Knowledge extraction

**AI outputs:** message structures, attributes, valid values, appendices, relationships, knowledge graph.

**Validate:**

- Every extracted field has a source page/rule.
- Element/segment numbers are valid and unique in context.
- Field lengths and character types are internally consistent.
- Valid values are represented as structured values where possible.
- Relationship edges point to existing nodes.
- Unresolved references are reported.
- Flags such as `UNTRUSTED_VALID_VALUES`, `AMBIGUOUS_LENGTH`, and conditional-field flags are preserved.
- Confidence scores and approval audit are present.

**Important:** an item flagged as untrusted or ambiguous cannot silently become an approved hard validation rule.

### Gate 3: Knowledge approval

**AI outputs:** approved knowledge model, review decisions, confidence summary.

**Validate:**

- Every approved item has an approval decision.
- Rejected items do not feed downstream generation.
- Review-required items remain visible.
- Unresolved or blocked requirements remain in an explicit blocked/review collection with a reason; do not insert unlinked blocked records into the active canonical chain or count them as covered.
- Approval actor, timestamp, source version, and decision are recorded.
- Knowledge counts reconcile across extracted, approved, rejected, and flagged items.

### Gate 4: Requirement derivation

**AI outputs:** requirement catalog, derivation method, source rule IDs, coverage statistics, pending phrasing, approval audit.

**Validate:**

- Requirement IDs are unique.
- Each requirement maps to an approved knowledge/source rule.
- Requirement statement is atomic and testable.
- `source_rule_id` exists in the independent catalog.
- Derivation method is recorded.
- Pending phrasing is not treated as approved.
- Requirements flagged for review are excluded or explicitly marked.
- Counts and `knowledge_base_coverage` reconcile.

### Gate 5: Scenario generation and approval

**AI outputs:** candidate scenarios, approved scenarios, scenario type, requirement ID, duplicate/skipped records.

**Validate:**

- Every scenario links to an approved requirement.
- Scenario type is valid: positive, negative, boundary, conditional, lifecycle, or equivalent approved type.
- Valid transaction/card/entry-mode/feature combinations are covered.
- Duplicate scenarios are explainable and deduplicated deterministically.
- Expert-added scenarios are marked as such.
- Approved Scenario Catalog is versioned and has approval audit.
- Candidate scenarios rejected by SME do not count toward approved coverage.

The AI Solution's Approved Scenario Catalog is evidence under test, not automatically the Test Validation denominator. The Test Validation Team independently derives the mandatory rule and scenario baseline from the specification pack, then compares the AI catalog against it.

### Gate 6: Test prioritization

**AI outputs:** priority/risk ranking and rationale.

**Validate:**

- Every prioritized item exists in the approved scenario catalog.
- Priority values are from the approved vocabulary.
- Risk rationale is present.
- No high-risk scenario is silently omitted.
- Prioritization does not remove mandatory coverage.

### Gate 7: Test-case generation and approval

**AI outputs:** test-case catalog, steps/preconditions, expected results, requirement/scenario links, approval state.

**Validate:**

- Every test case links to an approved scenario.
- Every approved scenario requiring a test has a test case.
- One test case has one clear primary objective unless explicitly marked composite.
- Expected result is consistent with positive/negative intent.
- Preconditions and steps are complete.
- Test case approval is recorded.
- AI-generated expected results are treated as claims, not truth.

### Gate 8: Test-data generation

**AI outputs:** structured JSON test data, message templates, calculated fields, lifecycle groups.

**Validate:**

- Every canonical data item links to a test case via `testData[].testCaseIds`. `coversBr` alone is not a TC link; preserve BR-only data as an unlinked review candidate until a real TC is authored and linked.
- Appendix O/R/S/T/Y source packages may be normalized from `sourceAnchor`, `covers`, and `scenarioId` into canonical anchors and links. Preserve producer status as metadata; normalized artifacts remain `REVIEW_REQUIRED` until independently validated.
- A BR-only TD candidate may record a deterministic BR -> TS -> TC path in provenance; this is not a canonical TD link and does not remove the payload/fixture requirement.
- For Appendix O/R/S/T/Y, generate synthetic payload drafts only for source records claiming synthetic fixtures are sufficient. Drafts use `expectedValidation=REVIEW` and `readiness=REVIEW_REQUIRED`; they are structural inputs, not validated outcomes or approvals. External-fixture and no-BR records remain provenance-only.
- Every canonical test-data item declares readiness as `EXECUTABLE`, `EXTERNAL_FIXTURE_REQUIRED`, or `REVIEW_REQUIRED`; missing or unknown readiness is normalized to `REVIEW_REQUIRED`, never promoted. Generated chain placeholders are always review-required. Readiness describes fixture usability, while `expectedValidation` describes whether the test input should pass or fail; do not conflate them.
- Payload matches the expected schema and message category.
- Required fields and segments are present.
- Calculated values are marked and independently recalculated.
- Field separators, lengths, counts, and wire representation are consistent.
- Lifecycle messages contain original/follow-up relationships.
- Sensitive values are synthetic or approved masked values.
- Test data proves the test case objective.

### Gate 9: Final serialization

**AI outputs:** approved test suite JSON, serialized messages, expected responses where supported.

**Validate:**

- Suite contains only approved test cases.
- Traceability chain is complete.
- Test-data IDs and case IDs resolve.
- Request/response formats conform to the specification pack.
- Segment/message lengths are independently calculated.
- Final output is reproducible from package and specification versions.

### Gate 10: Reporting

**AI outputs:** traceability matrix, coverage report, run report.

**Validate:**

- Counts reconcile with source artifacts.
- Coverage uses the Approved Scenario Catalog denominator.
- Missing scenarios and cases are listed individually.
- Review-required items are visible.
- Report status agrees with rule-level findings.
- Source pages and artifact IDs are clickable or resolvable.

## 4. What the Current ATL105 Output Already Provides

The inspected pipeline already provides useful evidence fields such as:

- stage identifier such as `pipeline_step`
- `architecture_alignment`
- `source_id`
- `generated_at`/`derived_at`
- `state`
- `scope`
- `approval_audit`
- `confidence_summary`
- `flagged_for_review`
- `source_page`
- `element_no`
- derivation `method`
- item `flags`
- `source_rule_id`
- approved scenario catalogs
- approved test-case catalogs
- traceability and coverage reporting modules

These fields should become required validation inputs, not optional documentation.

## 5. Validation Outputs

The Test Validation solution should produce one report per stage plus a final package report:

```text
validation-output/
  00-source-validation.json
  01-knowledge-validation.json
  02-requirement-validation.json
  03-scenario-validation.json
  04-test-case-validation.json
  05-test-data-validation.json
  06-traceability-validation.json
  07-coverage-validation.json
  final-validation-report.json
  final-validation-report.md
```

Recommended statuses:

```text
PASS
PASS_WITH_REVIEW_ITEMS
REJECTED_SCHEMA
REJECTED_PROVENANCE
REJECTED_TRACEABILITY
REJECTED_SEMANTIC_MISMATCH
REJECTED_MISSING_COVERAGE
HELD_FOR_MANUAL_REVIEW
```

## 6. Manual Sample Validation Gate

Before broad acceptance of an AI package, the Test Team shall manually validate only a small, risk-based handful of high-risk AI artifacts. This is targeted sampling, not manual review of every generated artifact.

Select high-risk items based on business impact, financial impact, lifecycle complexity, dependency complexity, ambiguity flags, sensitive data, and serialization risk. Where available, select a handful across BRs, scenarios, test cases, and JSON data.

The Test Team compares:

```text
AI artifact
  <> canonical Test Solution artifact
  <> independent specification rule
  <> expected business behavior
```

The sample review records format agreement, source-anchor agreement, traceability, expected-result agreement, payload semantics, differences, defects, assumptions, and review findings.

This confirms that the AI Solution and Test Solution are producing compatible expected results. It does not replace automated validation or final SME/Fiserv Business/Development approval.

Outcomes:

```text
SAMPLE_VALIDATION_PASS
SAMPLE_VALIDATION_PASS_WITH_FINDINGS
SAMPLE_VALIDATION_REJECTED
SAMPLE_VALIDATION_BLOCKED
```

## 7. Current ATL105 Demonstration

Use the recent ATL105 outputs as a demo:

```text
segment-100-requirement_catalog.json
approved_scenarios.json
test_case_catalog.json
Sale.json
Void.json
```

Demonstrate:

1. Requirement catalog state, counts, source rules, confidence, flags, and approval audit.
2. Approved Scenario Catalog and its differences from the independent Test Validation baseline.
3. Test-case links to requirements/scenarios.
4. Raw Sale/Void JSON format gap.
5. Canonical normalization requirement.
6. Segment 100 + Segment 111 + Segment 130 compatibility.
7. Void lifecycle correlation verification.
8. Coverage and manual-review findings.

The demo must show that valid JSON is not sufficient: the package must also be approved, traceable, semantically correct, and complete.

## 7. Immediate Implementation Plan

### Phase 1: Ingestion and adapters

- Add an adapter for the current AI generation JSON contracts.
- Preserve original files and calculate package hashes.
- Normalize requirement, scenario, test-case, and test-data records.
- Preserve AI flags, confidence, method, source page, approval state, and review state.

### Phase 2: Artifact gates

- Validate requirement catalogs.
- Validate approved scenario catalogs.
- Validate test-case catalogs.
- Validate test data and raw message samples.
- Validate all cross-file IDs and source rules.

### Phase 3: ATL105 semantic gates

- Connect the current Segment 100 validators.
- Add all ATL105 segment-specific validators progressively.
- Validate the relationships and review flags from the knowledge model.

### Phase 4: Coverage and approval

- Use the independent rule catalog as the primary certification denominator.
- Report rule, requirement, scenario, test-case, test-data, traceability, semantic-execution, parameter, and segment coverage separately.
- Compare AI-reported coverage with independently calculated coverage.
- Produce stage and final reports.
- Hold unresolved items for SME/Test Team review.

### Phase 5: Any-specification platform

- Replace ATL105-specific assumptions with specification-pack interfaces.
- Add a second specification pack as a proof of reuse.
- Keep Core Auth graph, coverage, reporting, and governance logic unchanged.
