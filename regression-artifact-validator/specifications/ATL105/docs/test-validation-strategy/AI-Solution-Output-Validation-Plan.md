# AI Solution Output Validation Plan

**Platform:** Core Auth Regression Test Solution  
**Current specification pack:** ATL105 2026-3  
**Release overlay:** ATL105 2026-4 Appendix AF is modeled separately as review-required; it does not imply full 2026-4 support.  
**Primary source inspected:** `core-auth-test-generation-platform/src/pipeline`  
**Purpose:** Define how Test Validation independently validates the AI Solution generation workflow and every outcome artifact

**Validation-control register:** Stable `AIV-*` controls, implementation/test evidence, and the distinction between validator-control coverage and ATL105 BR coverage are maintained in [AI Artifact Validation Controls](AI-ARTIFACT-VALIDATION-CONTROLS.md). This plan defines the workflow; the control register defines how we verify that the workflow itself is implemented and tested.

**Mandatory change synchronization:** Every change to AI-output validation code, schemas, adapters, canonical matching, coverage denominators/calculations, validation statuses, or report semantics must update this plan and the [Common LLM Segment Training Handbook](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) in the same change set. Update the affected `AIV-*` control's implementation/test evidence in [AI Artifact Validation Controls](AI-ARTIFACT-VALIDATION-CONTROLS.md), add or revise regression tests for repeatable behavior, and refresh any affected per-delivery result. A code-only or report-only change without its strategy/handbook/evidence updates is incomplete.

**Coverage invariant:** Keep the independent Test Solution rule denominator separate from the AI BR inventory. `confirmedRequirementCoveragePercent` is confirmed independent rules divided by in-scope independent rules; AI-BR inventory metrics use AI BR IDs as their own denominator. A missing crosswalk entry means `NOT_ASSESSED`, not `AI_ONLY`. If the independent denominator is structurally invalid or empty, coverage is `NOT_CALCULABLE` (JSON `null`), not `0%`. Segment AI BR inventories are not added into a run-wide denominator unless IDs have been deduplicated across segments.

An isolated request/response sample without a frozen delivery inventory and explicit BR -> TS -> TC -> TD identity is not a coverage population. Record only sample-scoped validator outcomes; do not create a BR-match numerator, classify unseen requirements as `AI_ONLY`, or infer lifecycle coverage from a single leg. For the sanitized 2026-10-16 Authorization sample, the declared segment count and EMV byte count are structurally consistent. The new `AppendixREmvChipDataOracle.assessWithPackedLengthPrefix` candidate path validates the apparent packed-BCD count against the trailing TLV body and parses that body, but returns `REVIEW_REQUIRED` until the producer adapter declares the field boundary. The strict bare-TLV path remains unchanged; no prefix is silently stripped. Element 188 fails its N3 format and does not match the decoded card-sequence tag; the producer track alias is unmapped. No BR coverage percentage is calculable from that sample.

**Execution-ready Test Model quality:** The canonical model carries a requirement `statement` and explicit `atomicity` disposition; a ready BR must be `ATOMIC` and include source evidence bound to a file hash, locator, and literal quote. Ready scenarios declare intent and preconditions. Ready test cases declare per-BR assertions with expected results and anchors shared with each linked requirement; every BR reached through the case's scenarios must be asserted. `CanonicalTraceabilityValidator.validateForExecution` enforces these deterministic contract checks and rejects source paths outside the trusted root, stale hashes, nonliteral quotes, incomplete assertions, and non-atomic dispositions. This is evidence/structure validation only: it does not decide that a rule is semantically atomic or approve the source interpretation. Draft/review packages remain loadable, and execution readiness remains blocked until authorized decisions and host evidence are supplied.

**Coverage presentation (2026-10-06):** The complete-handoff HTML/PDF Coverage view reports producer-internal BR/TS-to-TC linkage, physical-output presence, segment-attributed linkage, exact transaction-target labels, scenario-type inventory and expected response-code declarations. Every percentage names its numerator and denominator. Segment counts overlap; scenario groups inherit linked BR attribution and TC groups use direct BR references. Do not infer normalized transaction-code or code/family coverage from producer labels or code mentions. Catalog rule counts remain a separate inventory; semantic rule coverage is `NOT_CALCULABLE` and code/family validation is `NOT_ASSESSED` until evidence-qualified comparison is performed. Hash-bind the AI inputs, response matrix and catalogs used by the view.

**Independent BR coverage correction (2026-10-08):** The Test Team's BR coverage numerator is the number of distinct, in-scope independent Test Solution BRs with a delivery-bound, evidence-supported `CONFIRMED` match in the AI BR inventory; the denominator is the independent Test Solution BR baseline. Do not use AI BR-to-TC linkage as the BR coverage numerator, and do not transfer historical crosswalk decisions by reused AI ID. For October 7 Run4, the independent baseline has 601 rules and no confirmed semantic crosswalk: report `0/601` confirmed-match evidence (0.00% currently confirmed) with assessment status `NOT_ASSESSED` and all 601 rules unassessed. This is not evidence that no equivalent AI BRs exist. The former 72.17% is only AI-internal BR-to-candidate-TC structural linkage; matrix proposals remain separate. Preserve full-chain semantic coverage as `NOT_CALCULABLE`.

**Complete Run4 BR exercise (2026-10-08):** Include every AI BR regardless of producer/SME approval when building its delivery-bound crosswalk. Reconstruct BR -> TS from scenario `requirement_ids`, TS -> TC from case `scenario_id`, TC -> TD from declared file paths, and separately join declarations to physical payload/metadata evidence. Retain matrix-only/proposed edges as discrepancies. For Run4, all 5,153 BRs were exported across 65,989 matrix leaves; direct catalogs link all 5,153 to a TS, 3,719 through a TC to physical TD, with 62 matrix BR-to-TS rows not matching direct scenario requirement links and 28 matrix leaves marked FULLY_TRACED without a TD declaration. Exact normalized segment + numeric `ENT-ELEM-N` creates only a source-anchor candidate: 562 AI BRs mapped to 270 distinct Test Solution rules (285 exact-single-atomic candidates; 277 ambiguous/composite candidates); none are semantic confirmations. Keep all remaining candidate/unmatchable dispositions explicit and never promote them to `AI_ONLY` without complete baseline review. The row-level CSV/JSONL and hash-bound summary are published with the Run4 report.

**Bounded semantic assessment (2026-10-06):** Assess interpretation, BR objective, negative mutation, expected outcome and broader payload checks in that order. `scripts/assess-ai-semantic-batch.py` records an initial 100-case deterministic batch, explicit metadata-path mappings and two literal source predicates (Segment 100 fixed identity and Sequence Number six-digit format). Distinguish intentional invalid inputs from unexpected failures; preserved mutations and shared BR IDs do not certify negative effectiveness or business equivalence. Composite/absence mappings, valid control fixtures, response-family oracles and full-message validation remain explicit gates. Retain source/physical-file hashes and unassessed cases; never extrapolate a non-random batch to run-wide semantic coverage.

**Bounded follow-up:** Exact account/colon/block agreement may resolve composite preservation, but is not a normative wire delimiter. Required-field absence checks must join one declared omission, one unavailable-field mapping and one explicit physical segment; source requiredness is gated against Section 12.1 rows. Paired controls prove predicates only, not valid full messages. Flow sequence agreement and exact response-heading candidates remain context evidence, not BR mappings or host outcomes. Source/matrix code-family disagreements must remain review-required when the matrix is not adjudicated; unsupported aliases, missing TC oracles, compatibility contracts and wire artifacts cannot yield PASS.

**eWIC Segment 103 applicability crosscheck:** The eWIC intake validator follows the source-derived `Segment103ApplicabilityValidator` matrix. Authorization, Authorization Cancellation, Balance Inquiry, and Purchase Reversal/Void may omit Segment 103 on the request; Purchase Completion and Voucher Clear require it and the applicable WIC data. Element 63 is checked against BUYPASS segment objects actually included. Operation-catalog completeness in AI intake is separate from per-message Segment 103 presence. These checks do not establish Commerce Hub field mapping, support, or host outcome.

**Standard:** This plan is implemented under the [Common LLM Segment Training Strategy](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md). The common strategy is mandatory for every Test Team contributor, agent, and automation. Improvements require evidence, Test Team review, documentation, and regression tests before they become standard.

**2026-4 Appendix AF overlay:** `AppendixAFIncrementalAuthorizationValidator` and the AF test model cover source-quoted logical predicates for IA network applicability, four transaction roles, the Appendix I fixed Table 82 layout, terminal-number series guidance, Original Auth predecessor, approval-code carry-forward, and completion closure. `IA_REVERSAL` is distinct from the legacy partial-approval reversal rule. Per-terminal open-purchase uniqueness uses an explicit Appendix AF `transactionId` observation and a complete synthetic state list; it does not substitute Table 82's Terminal Transaction Number. A source-hash-bound application-payload candidate is available at `appendix-af-physical-wire-candidate-v1.json`: it contains a 110-byte synthetic Financial Transaction Request (Segment 100/111 lengths 081/019) and a 101-byte synthetic Financial Transaction Response layout. Chapter 13.2 source confirms Element 111 value `082` is Incremental Auth Data. The separate AF Transaction ID-to-Table 82 N4 mapping remains `REVIEW_REQUIRED`. The response is layout-only, not a captured host response or expected-outcome oracle; TCP/IP/TPDU framing is excluded. The 21 logical cases and payload pair do not establish host behavior, independent real open-state evidence, or certification of the full 2026-4 release. Discover missing Table 82 is a source-defined failure; missing Table 82 for Visa/Mastercard/American Express remains recommendation-only.

**Proposed coverage policy — pending Fiserv Leadership approval:** Any coverage metric produced by the AI Solution is treated as a claim under test. The Test Validation solution proposes to calculate its own rule, traceability, scenario, test-case, test-data, semantic-execution, and parameter-combination coverage.

## 1. Understanding of the AI Architecture

**Delivery-wide assessment (2026-10-14, AIV-016):** `audit-ai-delivery-coverage.py`
includes approved and candidate catalog occurrences, retaining row hashes and
producer status while blocking conflicting IDs from structural counts. It hashes
all supplied files and validates explicit catalog-relative TD paths and metadata
identities. Proposed element/cross-field/process/lifecycle/response/transport
and message/security/amount/dependency/account-card/cross-segment types derive
from explicit producer metadata. MESSAGE is not assumed to mean RESPONSE, nor
CROSS_SEGMENT to mean CROSS_FIELD. Literal quote matches do not adjudicate
business meaning. All Test
BRs appear in the baseline ledger; all pair decisions remain pending authorized
source review. Candidate reach, physical-chain candidate reach, semantic-confirmed
coverage and executed validation are separate. Auxiliary format validation is
unassessed. Input populations/hashes must remain stable through publication; the
hash-bound summary and row-level conflict, chain-gap, candidate-pair and anchor
queues are the review evidence, not a certification by themselves.

**Bounded technical validation coverage (2026-10-14, AIV-017):**
`scripts/summarize-ai-technical-validation-coverage.py` joins the stable,
byte-bound field/structure runner report to the AI-excluded 601-rule baseline
and the hash-verified delivery-wide chain ledgers. It publishes one row per
independent Test BR, one row per observed AI-BR/candidate-rule/payload relation,
explicit metric denominators and a sidecar SHA-256 manifest. The current Run1
report `Run1-2026-10-14-full-delivery-debug-v3` recursively processes both
QE-shaped candidate directories: 39,479 metadata messages and paired payloads,
plus one unpaired payload recorded but not evaluated. It contains 362,963
evaluation rows across 21,227 distinct case IDs; 58/601 rules have a limited or
partial executor binding, 41/601 have at least one decisive PASS or FAIL
predicate result, 14,396/21,227 cases have any decisive result, and 208/6,880
AI BRs have a candidate rule observed on a clean direct physical chain. The
runner covers a 326-rule field/structure slice; 275 rules are outside its scope.
These counts do not certify complete BR validators or complete-case passes.
Values are metadata-derived with bounded physical envelope/structure checks;
intended negative-target failures remain possible. Source interpretation,
authorized semantic decisions, remaining rule execution and host execution are
distinct gates; semantic-confirmed and host-executed percentages remain null,
`NOT_ASSESSED` and `NOT_EXECUTED`.

**Execution-ready model quality (2026-10-15, AIV-018):** The canonical Test
Solution model now carries explicit BR statement/atomicity/source-evidence,
scenario intent/preconditions, and per-BR TC assertions/expected results.
Execution gating verifies a supplied trusted-root path, source SHA-256 and
literal quote; assertion coverage across all BRs linked through the case's
scenarios; and shared BR/assertion source anchors. This completes the
deterministic model-integrity improvements from the review plan. It does not
decide atomicity or business equivalence, authenticate the evidence author,
substitute for SME adjudication, or produce host execution evidence. Existing
pending crosswalk decisions and all approval/execution measures stay unchanged.

**Missing-scenario handling (2026-10-15, AIV-019):** The complete Test Solution
package now emits one source-anchored draft scenario proposal for every
independent BR without a linked scenario. Each proposal uses only the BR title
and source anchor, names its unresolved applicability/preconditions, transaction
context, test partitions, and expected-outcome gaps, and remains
`REVIEW_REQUIRED`. This closes the scenario-inventory gap without fabricating
test behavior; authoring complete scenario conditions and outcomes remains a
source-review/SME task. Draft proposals do not change business coverage or
execution readiness.

**AI network/card-type test-case completeness (2026-10-16, AIV-020):** The new
`scripts/audit-ai-network-cardtype-coverage.py` verifies presence of cases for
the declared `flow_axis_values.card_brand` values and independently for every
financial card code in `Segment100CardTypeOracle`. It hashes the catalogs and
checks payload/metadata bytes against the frozen delivery inventory, excluding
conflicted Test Case IDs. It reports combinations observed but does not require
unsupported combinations or promote them to semantic matches. Current delivery
coverage is 4/4 declared brand-axis values and 1/36 card types; status is
`INCOMPLETE_REQUIRED_DIMENSION_COVERAGE` with 35 missing card codes. The AI
generator source is outside this repository; the audit is the independent
acceptance gate and supplies the upstream generation gap list.

The 2026-10-16 audit's 35 missing financial card codes are routed to the AI
Solution Team as `AIF-0020`. Recheck a corrected, versioned delivery against the
same 36-code oracle and frozen-payload identity rules. Require the Prompt Code's
card type in the physical request payload to agree with its scenario/test-case
metadata. Keep the four observed brand-axis values separate: this feedback does
not define a global network denominator or a network-by-card-type combination
matrix, and case-presence checks do not validate semantics or host outcomes.

**Account/track alias and EMV mapping (2026-10-16, AIV-021 / AID-0005):** A
producer-level property such as `TrackData` is not itself an ATL105 Segment 100
element. The adapter must declare the POS entry method and a versioned mapping
from producer fields to Elements 2 and 12. Source-defined Track 2 splitting uses
`=`; Track 1 splitting uses the first `^`. Segment 130 EMV data remains a separate
segment and must not be reconstructed from a track alias. The supplied
Authorization sample establishes only that this mapping question exists: absent
the producer/converter contract, its alias and delimiter are neither normalized
nor declared invalid. `AccountNumberEntryValidator` exercises normalized fields
only; AIV-021 remains partial until an adapter has round-trip, mode, delimiter,
missing-context, and EMV-coexistence regressions. Preserve the input unchanged,
use synthetic fixtures, and keep semantic approval, wire validation, and host
execution separate.

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
The continuation's `GenerateAppendixIScopedTraining` evaluates 11 selected
logical examples for Tables 010-015 and 017, emits 22 linked review-only TC/TD
records and physical TD files, and validates the strict canonical graph.
Scoped source-anchor identities and assertion boundaries preserve partial-rule
status. Its measured mutation catches apply only to those supplied mutations;
the appendix-wide threshold remains unestablished. Input hashes and a
302-draft-rule ledger preserve reproducibility without certifying completeness.
AI comparison is deferred by the user; no new producer artifacts are assessed.
Appendix I remains IN_PROGRESS in the shared training status and the next
merge to Develop requires explicit user approval.

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
- Producer-specific composite properties that represent multiple ATL105 elements require a versioned adapter mapping, explicit source/entry context, and round-trip tests. Unknown aliases or delimiters remain `REVIEW_REQUIRED`; do not infer a wire separator from an example or merge separate EMV segment content into track fields.

**Every-TD evidence gate:** `scripts/validate-ai-delivery-evidence.py` consumes a completed,
hash-bound delivery audit and emits one `test-data-validation.jsonl` row per physical
payload, including orphans. It checks sidecar/TC/scenario identity and reuses the existing
QE payload-leg executor with explicit per-rule observations, source conflicts and
unevaluated rules; ambiguous cases/legs and unsupported shapes remain unassessed.
Payload values are not exported. These feedback checks are not the full 33-rule
BR/TS/TC workflow and are not source-semantic approval. Optional hash/identity-bound
Chapter 13 intake evidence supplies bounded predicates only. Every TD still reports
unproven complete value semantics, conditional applicability, cross-field/segment
dependencies, negative-objective isolation and lifecycle context. Expected negative
violations must not be relabeled as AI defects without an independent objective/oracle.

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

### Gate 11: Post-BR whole-delivery artifact validation

BR coverage is a milestone, not the final acceptance gate. After the BR crosswalk/coverage pass, enumerate and validate the complete AI-produced artifact population for the frozen delivery, including every approved and candidate BR/TS/TC catalog, every flow step, every declared payload and metadata file, response expectations, traceability matrices, run summaries, and auxiliary outputs that the report claims to cover.

**Required checks:**

- Freeze the exact delivery population and hashes; reconcile artifact counts and duplicate/content-conflict IDs across candidate and approved tiers without treating producer state as approval.
- Validate each BR -> TS -> TC -> TD edge from explicit identifiers; report dangling, missing, orphaned, duplicate, and conflicting links at row level. Every artifact must be accounted for as validated, review-required, unsupported/unassessed, or explicitly out of scope.
- For multi-step flows, validate each step's transaction family/code, original/follow-up correlation fields, runtime host-response dependencies, declared TD path, step-specific metadata identity, and expected outcome. Never substitute a synthetic response value for an actual host response.
- Validate physical payload structure and value bindings with the applicable source-profile validator; compare metadata to physical payload and wire serialization where supported. File presence/hash equality alone is not semantic or wire validity.
- Validate expected-response oracle tiers, positive/negative/boundary coverage, test determinism, and source-backed assertions. Separate ASSERT, INFER, FLAG, and observed host outcomes.
- Reconcile validation outputs and manifests to their input hashes. Publish candidate coverage, complete-chain structural coverage, semantic decisions, and executed/host coverage as separate measures.

**Exit condition:** a whole-delivery result is complete only when every in-scope artifact is represented in the inventory and has an explicit disposition. Any unsupported format, missing source binding, unvalidated payload family, pending BR mapping, missing oracle, or unexecuted host outcome remains visible as `REVIEW_REQUIRED`, `NOT_ASSESSED`, or `NOT_EXECUTED`; do not promote the package to fully validated based on BR coverage alone.

**Current boundary:** V6 inventories 79,040 Oct 10 files and checks catalog links and declared physical routes; V6 technical coverage executes bounded predicates on 326/601 Test BR rules. The Authorization-to-Completion review adds step-level checks for 138 flows but still has 124 cases without direct AI BR links, 133 runtime approval placeholders, 5 missing completion approvals, and 35 amount-change cases awaiting host-approved amount evidence. These are not whole-delivery semantic or host certification.

**Additional matrix/every-TD gate:** `scripts/validate-ai-delivery-evidence.py` reconciles
direct catalog identities and BR-TS, TS-TC, BR-TC and TC-TD edge sets independently
against both nested and flat representations of `traceability_matrix_full.json`.
It preserves declared paths rather than guessing basename matches, checks input/output
hash dependencies and delivery-population stability, and reconciles the TD ledger
count to the completed audit. Repeated matrix-row counts, coverage/status assertions,
the Markdown matrix, physical `traceability.json`, archives and auxiliary formats
are not validated by this set comparison. BR/TS/TC meaning and complete TD semantics
remain unassessed. The result is deliberately
`BLOCKED_UNPROVEN_SEMANTIC_CORRECTNESS`, with `fullDeliveryValidated=false`,
even when all structural comparisons and bounded feedback predicates pass.

**Per-leg lifecycle evidence screen (2026-10-16, AIV-024):** `scripts/build-ai-flow-semantic-review.py` creates a separate, hash-bound ledger for every AI BR and each linked multileg flow step. It records transaction code, step identity, sequence/approval field shape, observed sequence equality, response-object presence, frozen payload copies and BR/Test-BR candidate links without exporting field values. Expected-response declarations are not actual responses; response-shaped producer payloads are not host evidence without independent provenance. Authorization approval binding remains blocked until the actual Authorization response is supplied and provenance reviewed; approval rules for purchase/return/timeout reversal families remain flow-specific `REVIEW_REQUIRED`. Current output `test-output/ai-solution-independent-review/2026-10-16-ai-flow-semantic-review-v3/` carries all 6,880 BRs, 28 flow cases, 67 logical steps and 134 hash-verified payload copies; 26 sequence comparisons match, zero response objects are present, 15 Authorization approval bindings are blocked, 13 reversal approval bindings remain flow-specific review, and semantic review remains 0/6,880. These are evidence observations and review queues, not semantic decisions or execution certification.

**QE-Fiserv feedback rules (2026-10-17, AIV-025):** the QE/SME feedback workbook is trained as 33 enforced rules in `contract/qe-feedback-rule-catalog.json` and executed by `scripts/apply-qe-feedback-rules.py` over every BR, scenario, test case and payload leg of a preserved, per-file hash-verified delivery (`scripts/verify-ai-delivery-intake.py`). Rules are enforced regardless of workbook status; differences from ATL105 2026-3 are kept as source conflicts for SME confirmation. The first run on the 10 October delivery (`test-output/ai-solution-independent-review/2026-10-17-qe-feedback-training-v2/`) validated 17,769/17,769 payloads and found defects for 29 of 33 rules, including missing Segment Length on every payload, 19-segment financial requests, 9,979 wrong Segment Types, 3,171 scenarios and 2,806 BRs without test cases, and 3,970 test cases without physical data. It also publishes readable per-segment traceability, manual test tables and Test Case Cards. Results are feedback conformance, not semantic or host certification.

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
