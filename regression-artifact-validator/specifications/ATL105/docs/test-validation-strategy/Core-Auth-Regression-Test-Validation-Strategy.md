# Core Auth Regression Test Validation Strategy

**Template basis:** TFF Test Strategy Document - June 19_comments.docx  
**Audience:** AI Solution Team, Test Validation Team, Fiserv Business/Development Team  
**Current foundation specification:** BUYPASS Platform ATL105 Message Format Specification 2026-3  
**Current foundation implementation:** ATL105 validation platform, first proven with Segment 100  
**Current ATL105 delivery target:** All ATL105 segments, message categories, transaction flows, and appendices  
**End goal:** A reusable Core Auth Test Solution that generates and validates test artifacts for any specification

## Table of Contents

1. Version History and Approvals
2. Introduction
3. Test Objective
4. Project Testing Approach
5. Test Solution Training Procedure and Memory
6. Test Phases
7. Test Data
8. Test Tools
9. Test Schedule
10. Sign-off Criteria
11. Test Governance Process
12. Defect Management Process
13. Testing Deliverables
14. Test Environments
15. Roles and Responsibilities/RACI
16. Risks and Issues
17. Appendix A: ATL105 Demonstration
18. Appendix B: All-Specification End State

## Version History and Approvals

| Version | Date | Author/Owner | Change | Status |
| --- | --- | --- | --- | --- |
| 0.2 | 2026-09-16 | Core Auth Test Validation Team | Added Test Team solution design, AI artifact validation value, and risk-based manual testing | Working review |
<<<<<<< HEAD
| 0.3 | 2026-10-17 | Core Auth Test Validation Team | Trained QE-Fiserv SME feedback as 33 enforced rules (AIV-025) and applied them to the full 10 October AI delivery | Working review |
=======
| 0.3 | 2026-10-26 | Core Auth Test Validation Team | Added business transaction-context validation and coverage (Mastercard Credit Purchase), the training gate, merchant test-data evidence rule, deliverable TD09 and risk R008 | Working review |
>>>>>>> origin/Dhan_Singh_HTML
| 0.4 | 2026-10-27 | Core Auth Test Validation Team | Ingested the missing ATL105 external reference Conexxus Payment System Product Codes v4.1 (Appendix K-27 Table 034), with an Element 77 crosswalk, PSPC BR/Test Coverage and Validation Strategy | Working review |

| Role | Reviewer/Approver | Status |
| --- | --- | --- |
| Test Validation Lead | To be nominated | Pending |
| AI Solution Team | To be nominated | Pending |
| SME/TBA | To be nominated | Pending |
| Fiserv Business/Development Team | To be nominated | Pending |

## Introduction

### Project Overview

Core Auth Regression Test Validation is an independent quality gate and artifact-generation platform for specification-driven testing. ATL105 is the current foundation specification. The platform is designed to support all future specifications through versioned specification packs.

The platform supports two paths:

```text
AI output artifacts
  -> format adapter
  -> canonical model
  -> independent validation

Specification pack
  -> canonical BR generation
  -> scenario generation
  -> test-case generation
  -> JSON test-data generation
  -> validation and coverage
```

The Core Auth platform generates an approved, traceable regression test suite. Test execution, production automation execution, failure triage, and changes to an existing external automation framework remain outside the generation platform unless separately integrated and approved.

### AI Solution Outcomes and Test Validation Focus

The strategy focuses on the artifacts produced by the AI Solution and how the Test Solution validates them:

| AI outcome | Validation focus |
| --- | --- |
| Knowledge model | Source provenance, confidence, flags, relationships, and approval state |
| Business Requirements | Source mapping, completeness, atomicity, and testability |
| Test Scenarios | Relevance, classification, combinations, and missing scenarios |
| Test Cases | Traceability, expected-result consistency, priority, and approval state |
| Test Data JSON | Schema, field semantics, serialization, lifecycle, and executability |
| Traceability/coverage reports | Independent recalculation and discrepancy detection |

The Test Validation Team validates these outcomes. It does not need to reproduce the AI generation process to determine whether the outputs are complete, traceable, and correct.

### What the Test Team Is Building

The Test Team is building an independent validation solution around the AI Solution. The solution is reusable across specifications and uses a specification pack to supply specification-specific rules.

| Test Team capability | What the team builds | How it helps test the AI Solution |
| --- | --- | --- |
| Artifact intake | Versioned adapters, manifests, hashes, and original-file preservation | Proves which AI package was tested and prevents silent format changes |
| Canonical model | Common representations for requirements, scenarios, test cases, and request-only Test Data JSON | Allows different AI output formats to be compared consistently |
| Independent rule catalog | Atomic rules, source anchors, conditions, examples, and expected behavior derived from the specification | Provides an oracle independent of AI-generated requirements and coverage claims |
| Traceability graph | Rule -> BR -> Scenario -> Test Case -> Test Data links | Finds orphan, duplicate, unsupported, and broken artifacts |
| Semantic validators | Field, value, dependency, lifecycle, compatibility, and serialization checks | Detects JSON that is structurally valid but business- or specification-invalid |
| Coverage engine | Independent rule, requirement, scenario, case, data, traceability, and parameter coverage | Exposes missing coverage and verifies AI-reported metrics |
| Manual review queue | Risk-based sample selection, findings, decisions, and evidence | Resolves ambiguity that automation cannot responsibly approve |
| Evidence and reporting | Reproducible JSON/Markdown reports with status, defects, and review items | Gives stakeholders an auditable pass, fail, or held-for-review decision |

The Test Team solution is therefore both a validator and an independent evidence producer. It can generate canonical Test Team artifacts from approved rules, compare them with AI artifacts, and show exactly why an artifact is accepted, rejected, or held for review.

### How the Test Solution Helps Testing

The Test Solution reduces the principal risks of AI-generated testing assets:

- **False confidence:** valid JSON is separated from correct business meaning.
- **Untraceable content:** every accepted artifact must resolve to an independent source rule.
- **Hidden omissions:** coverage is measured against the independent rule catalog, not only AI-produced counts.
- **Inconsistent formats:** adapters normalize different AI packages into one canonical contract.
- **Unsupported approval:** confidence scores and AI approval flags cannot replace Test Team or business approval.
- **Ambiguous interpretation:** uncertain items are explicitly held for manual review instead of being silently accepted.
- **Regression risk:** the validator and its rule catalog are versioned and executed repeatedly when AI schemas, specifications, or validators change.

### Architecture Alignment

The strategy follows the Core Auth Regression Automation Tool architecture:

```text
Specification profiling
  -> document extraction
  -> knowledge extraction
  -> knowledge approval
  -> requirement derivation
  -> scenario management
  -> scenario approval
  -> test prioritization
  -> test generation
  -> test case approval
  -> traceability and coverage reports
```

The AI output is treated as an untrusted sample input to the validation solution. It is never used as the source of truth for validating itself. Three review gates are defined:

- **Knowledge Review:** the Test Validation Team builds an independent source-derived rule catalog from the specification. SME/TBA review is preferred but is not assumed to be available.
- **Scenario Review:** the Test Validation Team derives scenarios from the independent rule catalog. AI scenarios are compared with this catalog and are not the coverage denominator.
- **Test Case Review:** the Test Validation Team validates generated cases and expected results against the independent rules and executable checks.

AI review, confidence scoring, scripted approval, and producer claims may prioritize attention, but they do not establish correctness.

## Test Objective

The objective is to generate or validate complete, traceable, specification-aligned, semantically correct, and executable Business Requirements, Test Scenarios, Test Cases, and JSON Test Data.

The solution shall:

- Independently validate AI output.
- Generate canonical Test Team artifacts from approved rules.
- Use canonical source anchors rather than matching producer-local IDs.
- Detect schema, traceability, semantic, coverage, and serialization defects.
- Hold unknown or ambiguous interpretations for manual review.
- Produce reproducible JSON and Markdown evidence.

### Independent Validation Principle

The AI Team artifacts are the system under test. They are sample inputs used to exercise intake, normalization, semantic validation, traceability, coverage, and reporting. The Test Validation Team must independently derive its rule catalog and expected outcomes from the ATL105 specification and approved source evidence without copying AI requirements, scenarios, cases, or data.

The independent catalog is the validation oracle for technical and documentary checks. It must preserve source anchors and distinguish direct source statements from derived interpretations. Without an SME, the platform may establish source evidence, structural correctness, consistency, reproducibility, and executable behavior, but it must not claim business approval. Ambiguous or interpretive rules are marked `PROVISIONAL` and remain release blockers for business certification.

### AI Artifact Validation Strategy

For every AI artifact package, execute these checks in order:

1. **Intake and schema:** validate manifest, versions, required fields, identifiers, JSON schemas, encoding, and package integrity.
2. **Canonical normalization:** map producer-local objects to canonical requirements, scenarios, test cases, and test-data models without trusting producer-local IDs.
3. **Source traceability:** verify every AI requirement has a resolvable source anchor, source statement, and scope. Unsupported claims are defects.
4. **Independent comparison:** compare AI requirements and downstream artifacts with the independent Test Team catalog. Classify matches, missing rules, extra rules, duplicates, contradictions, and granularity differences.
5. **Artifact chain:** verify `requirement -> scenario -> test case -> test data -> expected result` links. A requirement without a scenario or test case is uncovered.
6. **Semantic and executable checks:** run positive, negative, boundary, conditional, lifecycle, serialization, and compatibility cases against deterministic validators.
7. **Mutation checks:** alter valid AI data deliberately and confirm the independent validator detects the intended violation and identifies the correct rule.
8. **Decision reporting:** publish precision, recall, traceability, coverage, ambiguity, defect, and review-queue metrics against exact package and specification versions.
9. **Human-review outputs:** publish readable, segment-filterable Markdown views and a traceability matrix showing BR -> scenario -> test case -> test data, including missing links, derivation evidence, and source-page references. Keep canonical JSON authoritative; readable views must be reproducible from it.

The AI package may contain more or fewer requirements than the independent catalog. Count equality is not an acceptance criterion; every difference must be explained and dispositioned.

The stable validation controls for this workflow are catalogued separately as `AIV-*` in [AI Artifact Validation Controls](AI-ARTIFACT-VALIDATION-CONTROLS.md). `AIV-*` control coverage measures whether the Test Solution's validator rules have implementation and regression evidence; it is not ATL105 business-rule coverage. Per-delivery BR/TS/TC/TD dispositions and coverage results are recorded separately under `test-output/ai-solution-independent-review/`.

### QE-Fiserv SME Feedback Intake (October 2026)

The [QE-Fiserv feedback workbook](../../../../QE-Fiserv.xlsx) is enforced as Test Solution validation rules, not as an amendment to the versioned ATL105 specification. By Test Team instruction every item is enforced regardless of the workbook status column; a rule is closed only by a passing rerun on a new frozen delivery. Where a feedback rule differs from ATL105 2026-3 text, it still runs and the difference is recorded as a source conflict for SME confirmation. The workbook's `Oct-10 AutoRun-FB` tab has no populated AI-team decision, corrected-anchor, or review-rationale fields, so those candidate rows do not constitute SME decisions.

| Feedback area | Validation requirement |
| --- | --- |
| Segment-aware BRs and traceability (`Def_SepRun01`-`05`, `Def_SepRun14`-`21`) | Show BRs by attributed segment and validate every BR -> scenario -> test case -> test data edge. Keep unresolved segment attribution visible; never assign a segment merely to close a gap. Report BRs without scenarios, scenarios without cases, and cases without data as separate gaps. |
| Readable review evidence (`Def_SepRun03`, `06`-`10`, `Status-SepDef`) | Retain JSON as the canonical input and provide the requested Markdown matrix and risk-focused test-case views with source links, derivation path, expected outcome/oracle tier, and explicit `Not Executed` status where applicable. Matrix generation must be tested on repeated runs before it is called automatic. |
| Artifact and data fidelity (`Def_Sep30Run01`-`10`, `Oct10Run_D001`-`020`) | Compare each test case to its serialized request data, including segment identity/type, field names, length and count values, field duplication, transaction context, and the complete declared segment population. Empty or incomplete data cannot pass merely because producer metadata says `COMPLETE` or `PASS`. |
| Test design and expected outcomes (`Def_Sep30Run03`-`05`, `Def_SepRun16`-`18`) | Keep scenarios at business-flow level and cases specific enough to verify behavior; detect duplicate cases and include meaningful negative/boundary coverage. A missing or unsupported response oracle remains `NOT_ASSESSED`/`REVIEW_REQUIRED`, not a passing test. |
| Source/version and open rule claims (`Oct10Run_D018`-`020`, `Action Items`) | Pin the exact specification/addendum version and source evidence before changing ingestion or expected behavior. Prompt Code shape, Track Data composition/length, token expiration handling, and the proposed 17-segment maximum require source/context confirmation before becoming validator rules. |

The workbook's September counts (including 3,072/6,291 and the reported scenario/test-case gaps) describe a separate historical delivery. Do not combine them with the independently calculated denominator in the current BR coverage report.

**Implementation (2026-10-17):** the feedback is trained as 33 rules `QEF-001`-`QEF-033` in [`contract/qe-feedback-rule-catalog.json`](../../contract/qe-feedback-rule-catalog.json), executed by `scripts/apply-qe-feedback-rules.py` (control `AIV-025`). The 10 October AI delivery was preserved unchanged at `test-input/ai-solution/runs/2026-10-17/Run1/` and verified file by file (35,564 files, all SHA-256 matches). The engine validated 6,880 BRs, 12,254 scenarios, 21,254 test cases and all 17,769 payload legs and published the [QE feedback validation report](../../test-output/ai-solution-independent-review/2026-10-17-qe-feedback-training-v2/QE-FEEDBACK-VALIDATION-REPORT.md) with readable per-segment traceability, 8-column manual tables and Test Case Cards. Headline results: 29 of 33 rules found defects (QEF-002, 008 and 011 passed; QEF-032 had no applicable transactions); every payload lacks Segment Length, uses `NumberofSegments` and a `TC-` data ID; every financial request serializes 19 segments; 9,979 payloads carry a wrong Segment Type; 3,171 scenarios and 2,806 BRs have no test case; 3,970 test cases lack physical test data; 5,443 test cases have no concrete expected response code. Rule-level counts are in the [training handbook](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md#training-run-result--10-october-ai-delivery-2026-10-17). These are feedback-conformance results; semantic coverage stays `NOT_ASSESSED` and host execution `NOT_EXECUTED`.

### Proposed Coverage and Reporting Model (Pending Fiserv Leadership Approval)

The following coverage and reporting model is a proposal for review. It is not an approved Fiserv governance policy until Fiserv Leadership accepts it. The current implementation can produce evidence using this model, but the acceptance denominator, mandatory gates, and final status rules remain subject to approval.

The Test Team catalog is atomic enough to test and is generated independently from the source. Each rule contains a stable Test Rule ID, source page/section, segment and field, expected behavior, conditions, positive example, negative example, boundary example where applicable, and validation status.

Use these statuses when no SME is available:

- `SOURCE_VERIFIED`: directly supported by an explicit source statement, table, value, or structural definition.
- `TEST_VALIDATED`: source-verified behavior demonstrated by deterministic positive and negative execution.
- `PROVISIONAL`: interpretation, ambiguity, missing source evidence, or unresolved cross-rule dependency.

The 16 existing high-level Test Team requirements are planning categories only. They are not the atomic comparison oracle. Segment 100 coverage must be measured against the independent atomic rule catalog, not against the AI catalog and not against a producer-reported count.

Required comparison metrics are:

```text
Requirement precision = correct AI requirements / AI requirements assessed
Requirement recall    = correctly matched AI requirements / independent rules
Traceability          = AI requirements with valid source anchors / AI requirements
Scenario coverage     = independent rules with at least one accepted scenario / independent rules
Case coverage         = independent rules with at least one accepted test case / independent rules
Data coverage         = accepted cases with executable test data / accepted cases
```

## Project Testing Approach

### Application Functional Scope - In Scope

- Specification-pack and independent rule-catalog ingestion.
- AI format detection, adapters, normalization, and schema validation.
- Canonical BR, Scenario, Test Case, and Test Data generation.
- Independent validation of AI-generated equivalents.
- Source-anchor traceability and coverage.
- Field, dependency, lifecycle, serialization, and compatibility rules.
- Request and response validation where the specification pack defines both.
- Manual-review workflow for unknown or ambiguous interpretations.
- JSON and Markdown decision reporting.

### Application Functional Scope - Current ATL105 Demonstration

The first demonstration uses ATL105:

```text
Segment 100: Standard Message Data Segment
Segment 111: Variable Information Data Segment
Segment 130: EMV Request Data Segment
```

It demonstrates Sale and Void messages, raw AI JSON assessment, canonical normalization, traceability, compatibility checks, lifecycle correlation, and coverage reporting.

### Application Functional Scope - Business Transaction Contexts

A business transaction context validates a business flow across segments, starting from the approved FR-matrix template rather than from one segment. The first context is **Mastercard Credit Purchase**:

```text
BR-01 Mastercard credit financial purchase transaction processing
  -> FR-001 Card Validation ... FR-008 Post Authorization
  -> business scenarios TS-001..TS-037 (template) and TS-038 onward (Test Solution)
  -> Test Solution BR -> TS -> TC -> TD chains (Segments 100, 111, 123, 130, 131 and the financial response)
```

The business BR stays at business level; segment-level Test Solution BRs are traceability evidence under each scenario. The context's trained package, review queue and gates are defined in the [training handbook](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md#transaction-context-training-mastercard-credit-purchase) and tracked under `transactionContextTraining` in `training-status.json`. AI artifacts for this context are validated against that package once an AI package is provisioned.

### Application Functional Scope - External Reference Documents

Some ATL105 rules delegate their value lists to documents outside the specification. Each external document is treated as a source. It is line-anchored, carries its own oracle, generator, chain package and tests, and has its own BR coverage, test coverage and validation strategy:

| External reference | ATL105 2026-4 dependency | Authority | Training package |
| --- | --- | --- | --- |
| Conexxus Payment System Product Codes (PSPC) v4.1, 2022-05-12 | Appendix K-27: Table ID 034 Host-based Purchase Restriction values; gated by Appendix I-46 Table 049 Sub Table 18 | Normative for Table 034 values. Informative only for Element 77, which stays governed by Appendix F. | [README](pspc-payment-system-product-codes/README.md), [BR Coverage](pspc-payment-system-product-codes/BR%20Coverage.md), [Test Coverage](pspc-payment-system-product-codes/Test%20Coverage.md), [Validation Strategy](pspc-payment-system-product-codes/Validation%20Strategy.md) |

The PSPC package contains:

- 420 BRs and 2,032 executable TS/TC/TD chains.
- Classification of all 1,000 codes.
- A 1,000-code Element 77 crosswalk with 142 divergent codes flagged `REVIEW_REQUIRED`.
- 21 Table 034 gating, structure and value cases.

The open SME items are the PSPC version binding and Table 034 packing (SEG111-SME-012, `ANSWERED`), and Visa applicability (SEG111-SME-003).

AI artifacts that cite PSPC are validated under rules VR-PSPC-01 to VR-PSPC-14.

### Out of Scope

- Auto-approval based only on AI claims.
- Production or live payment data.
- Client approval without recorded evidence.
- Host certification without an approved integration contract.
- Security, penetration, regulatory, localization, accessibility, and mobile certification unless separately commissioned.

### Test Case Design Process

```text
Approved specification rule
  -> atomic BR
  -> business scenario
  -> focused test case
  -> concrete JSON test data
  -> deterministic validation
  -> evidence and report
```

Required design types are positive, negative, boundary, conditional, lifecycle, serialization, compatibility, and review-required cases.

Candidate scenarios are generated from valid combinations of transaction type, card type, entry mode, feature settings, and specification constraints. The Test Validation Team independently derives and baselines the required rule/scenario set from the specification pack. AI-reported scenario counts and coverage percentages are evidence under test, not the certification denominator.

#### Independent Test Coverage Model

Coverage is reported as a vector of independently calculated measures:

| Metric | Denominator | Numerator |
| --- | --- | --- |
| Rule coverage | Approved mandatory specification rules | Rules with valid BR, scenario, case, data, and executable evidence |
| Requirement coverage | Independent mandatory BR baseline | BRs linked to at least one approved scenario |
| Scenario coverage | Independent required scenario baseline | Required scenarios with an approved test case |
| Test-case coverage | Approved required scenarios | Scenarios with at least one approved test case |
| Test-data coverage | Approved test cases | Cases with linked, valid, semantically exercising data |
| Traceability coverage | Artifact links required by the graph | Links that resolve and share canonical anchors |
| Semantic execution coverage | Test data requiring executable validation | Data items independently checked by applicable validators |
| Parameter coverage | Valid specification combinations | Combinations represented and independently validated |
| Business-context BR coverage | In-scope Test Solution BRs for the context | BRs with at least one BR -> TS -> TC -> TD chain that passes canonical traceability |
| Business-scenario coverage | Template business scenarios for the context | Scenarios whose Test Solution BRs are all trained (`MAPPED`/`PARTIAL` record template intent separately) |

The proposed primary certification metric is **Rule Coverage**. A single aggregate percentage cannot hide a missing mandatory rule, broken traceability, or test data that does not exercise its case.

```text
Rule Coverage = mandatory rules with complete executable evidence
                / total mandatory rules in the independent rule catalog
                x 100
```

Subject to Fiserv Leadership approval, the Test Solution would report each dimension separately and apply these proposed hard gates:

- Any missing mandatory rule blocks approval.
- Any broken mandatory traceability link blocks approval.
- Any semantic mismatch blocks approval.
- `REVIEW_REQUIRED` is held for human review and is not counted as certified coverage.
- AI-produced coverage metrics are compared with independently calculated metrics and are never authoritative.

#### Business Transaction-Context Validation

For a business transaction context, the Test Solution validates in this order:

1. **Template conformance:** every template scenario is classified `MAPPED`, `PARTIAL` or `NOT_IN_TEST_SOLUTION` against Test Solution BRs, with the gap stated; Test Solution BRs with no template scenario are `TEST_SOLUTION_ONLY`.
2. **Scope:** in-scope BRs are selected by an explicit, versioned rule (template mapping, brand naming, or segment anchor without another product or brand). Keyword-derived FR placement is a grouping aid and stays `REVIEW_REQUIRED`.
3. **Canonical chain:** each in-scope BR is trained only when a BR -> TS -> TC -> TD chain passes `CanonicalTraceabilityValidator`. Failing BRs go to an explicit review queue with the reason and required evidence; they are never counted as covered.
4. **Independence and freshness:** trained records must equal the Test Solution source apart from logged link, status and anchor-field normalizations, and must match the source package hash. AI artifacts are not inputs.
5. **Status:** the context is `IN_PROGRESS` until the review queue is closed, SME/TBA certification is recorded, and AI intake and execution readiness gates have evidence.

The Mastercard Credit Purchase result is published in the [context BR coverage report](../../test-output/test-solution-independent-review/mc-credit-purchase-br-coverage.md). On 2026-10-26 (retrained on Develop): 168 of 189 in-scope BRs (88.9%) trained, 21 in review, and 9 of 37 template scenarios have no Test Solution evidence (card status, routing, timeout timer/detection, TOR ordering, clearing, settlement and audit logging).

### SME Availability Dependency

SME/TBA availability is a required dependency for business approval. If no SME is available:

- Source-verified technical checks may continue.
- Structural, traceability, reproducibility, and executable checks may continue.
- Ambiguous or interpretive rules remain `PROVISIONAL`.
- Knowledge, scenario, and test-case approval cannot be claimed.
- Affected items are `HELD_FOR_MANUAL_REVIEW` or `Blocked` in the RAID log.
- Business certification and final release approval remain blocked until an approved SME/TBA delegate is available.

### Responsibility

- AI Solution Team: candidate artifacts, schemas, metadata, format documentation, assumptions, limitations, and corrections.
- Test Validation Team: independent rule catalog, adapters, validators, coverage, reports, and technical verdicts.
- SME/TBA Team: business interpretation, dependency confirmation, and manual-review decisions.
- Fiserv Business/Development Team: business approval and release acceptance.

### Exit Criteria

- Mandatory automated checks pass.
- Required rule coverage is complete.
- No unresolved traceability or semantic blockers remain.
- Manual-review decisions are recorded.
- Reports are published against exact package and specification-pack versions.

## Test Solution Training Procedure and Memory

This section is the single record of **how** the Test Solution is trained and **where** its trained memory lives. Anyone can use it to resume, reproduce, audit or extend training without relying on chat history.

The [ATL105 Segment Training Handbook](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) is the mandatory detailed standard. This section summarizes it, and it records the complete worked record for each external reference ingested.

### Training Principles (apply to every step)

1. **Source-only truth.** Every rule comes from an authoritative specification or reference document. It is cited by version, section/page and line. AI Solution artifacts are inputs under test and are never copied into training truth.
2. **Fact, interpretation and open question are kept apart.** An instruction or derived interpretation is not silently converted into an approved rule.
3. **No self-certification.** Agent- or automation-derived rules stay `REVIEW_REQUIRED` (or `DRAFT_REVIEW_REQUIRED`) until an SME/TBA signs off. `smeApproved` stays `false` until then.
4. **Conflicts are preserved, not resolved by guessing.** Both claims are kept, the verdict is `REVIEW_REQUIRED`, and the question goes to the communication register.
5. **Independent expected labels.** Expected results come from a generator that parses the source transcription. Verdicts come from a separately implemented validator. Tests fail if they disagree.
6. **Classification is not eligibility.** A code or value being listed in a table never implies that a transaction is accepted, that a merchant is configured for it, or what the host will return.
7. **Automatic update.** Every training-relevant decision updates the handbook, this strategy, the knowledge base, `training-status.json` and the register in the same change.

### Standard Training Steps

| Step | Activity | Gate in `training-status.json` | Output (memory) |
| --- | --- | --- | --- |
| 0 | Start from up-to-date `Develop`, run the existing test suite, and record pre-existing failures | — | Baseline failure list in the change notes |
| 1 | **Source inventory.** Extract the PDF to page-marked text (`--- PAGE N ---`). Record version, section, page, line, applicability and ambiguity. Create canonical source anchors. | `SOURCE_INVENTORY` | `docs/specs/extracted_text*.txt`, anchors |
| 2 | **Knowledge model.** Fields, order, type, length, valid values, message family, request/response role, external dependencies. | `SEGMENT_KNOWLEDGE_MODEL` | `docs/specs/kb/<area>/README.md`, rule catalogs |
| 3 | **Context matrix.** Transaction type, card/network, entry mode, lifecycle role, companion segments, appendix and external-reference dependencies. | `CONTEXT_MATRIX` | Context envelopes in the KB |
| 4 | **Independent BR derivation.** Condition, behavior, valid and invalid representation, expected result, boundary, anchor and review status. | `INDEPENDENT_BR_DERIVATION` | BR catalog / package `businessRequirements[]` |
| 5 | **TS/TC/TD chain.** Given/When/Then, a concrete JSON TD per TC, and positive, negative, boundary, conditional and review cases. | `TS_TC_TEST_DATA_CHAIN` | `test-output/test-json/*-package.json` |
| 6 | **Lifecycle and specialized flows.** | `LIFECYCLE_AND_SPECIALIZED_FLOWS` | Flow notes, lifecycle chains |
| 7 | **Serialization and mutation.** Standard mutations MUT-001..010; at least 85% detection, and 100% for source-critical mutations. | `SERIALIZATION_AND_MUTATION` | Mutation results |
| 8 | **Independent AI intake.** Normalize, match on canonical anchors, crosswalk only with evidence. | `INDEPENDENT_AI_ARTIFACT_INTAKE` | `test-output/ai-solution-independent-review/` |
| 9 | **Converter and execution readiness.** | `CONVERTER_AND_EXECUTION_READINESS` | Execution evidence |
| 10 | **Java proof (8-item framework).** Validator, comparison, independence, traceability, mutation definition and execution, enhancement, consolidated report. | — | `src/main/java/com/coreauth/validator/**`, `src/test/java/**` |
| 11 | **Register and status.** Record open questions and answers in the communication register, regenerate the views, and update `training-status.json`. | — | Register JSON + generated views |
| 12 | **Completion gate and sign-off checklist**, then commit to a branch and merge to `Develop`. | — | Commit |

For every new or changed rule, the trainer records the handbook's **Required Training Record**:

- source, version, page and section
- segment, element and applicability
- canonical anchor
- condition, behavior and representations
- the BR → TS → TC → TD links
- positive, negative and boundary evidence
- the validation result, reviewer, date and open items

### Where the Trained Memory Lives

| Memory store | Location | What it holds |
| --- | --- | --- |
| Source transcriptions | `specifications/ATL105/docs/specs/extracted_text.txt` (2026-3), `extracted_text_new.txt` (2026-4), `extracted_text_pspc_v4.1.txt` (Conexxus PSPC) | Line-anchored text that every rule cites |
| Knowledge base | `specifications/ATL105/docs/specs/kb/` ([index](../specs/kb/00-index.md)) | Segment, element and appendix training notes, rule catalogs, SME/TBA notes |
| Training handbook | [COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) | Mandatory process, gates, rules and lessons learned |
| Strategy and coverage documents | `specifications/ATL105/docs/test-validation-strategy/` (this folder) | Strategy, validation plan, controls, and per-reference BR/Test Coverage and Validation Strategy |
| Executable oracles | `src/main/resources/atl105/*.json` | Machine-readable source oracles (for example the Appendix F and PSPC product-code oracles) |
| Validators and generators | `src/main/java/com/coreauth/validator/canonical/`, `.../coverage/` | Independent verdict logic and expected-label generators |
| Tests | `src/test/java/com/coreauth/validator/` | Replay of every TD; source-fidelity checks |
| Chain packages | `specifications/ATL105/test-output/test-json/` | BR/TS/TC/TD packages and crosswalks |
| Contract extensions | `specifications/ATL105/contract/` | Aliases and crosswalks. These are never authoritative coverage. |
| Communication register | [atl105-communication-register.json](../../registers/atl105-communication-register.json) and the generated `registers/views/` and `kb/segment-*/…-sme-tba-input-register.md` | SME questions, answers, test-team items and AI feedback |
| Training status | [training-status.json](../../training-status.json) | Gate status per segment, appendix, transaction context and external reference |

### External Reference Document Training Procedure

Use this procedure whenever ATL105 delegates values or rules to a document outside the specification. Examples are "refer to the … reference document" and "see the … specification".

1. **Locate every dependency.** Search both ATL105 versions for the reference name and its synonyms. Record each anchor: section, page, line, and the field or table.
2. **Confirm whether ATL105 already carries a copy.** For example, Appendix F is a copy of the product-code list. Decide which source is authoritative per field, and record that authority hierarchy.
3. **Check version drift.** Diff the relevant ATL105 sections between the baseline (2026-3) and the current release (2026-4). Reuse existing oracles only when they are identical.
4. **Store the source.** Copy the supplied file into `docs/specs/` under a versioned name, record its SHA-256, and extract it to page-marked `extracted_text_<ref>_v<version>.txt`.
5. **Build the source oracle.** Generate the oracle with a parser that:
   - preserves every row, verbatim description, range, page and line;
   - asserts complete, non-overlapping coverage of the value space;
   - throws on any unclassified source phrasing rather than guessing.
6. **Implement an independent validator** with explicit modes for each ATL105 use of the reference.
7. **Generate the chain package.** Create one BR per source row plus one BR per ATL105 usage rule. Give every BR at least one TS → TC → TD with concrete payloads and explicit expected outcomes, including FAIL and REVIEW_REQUIRED cases.
8. **Crosswalk against any ATL105 copy.** Classify every value by relation. Divergent values are REVIEW_REQUIRED; never substitute one source for another.
9. **Write the tests:**
   - source fidelity (each row appears on its anchored lines);
   - ATL105 anchors re-read from the current extract;
   - handpicked boundaries and exceptions;
   - a full replay of every TD;
   - committed outputs equal to regenerated outputs.
10. **Write the coverage and strategy documents.** In `docs/test-validation-strategy/<reference>/`, write a training README (memory), `BR Coverage.md`, `Test Coverage.md` and `Validation Strategy.md`.
11. **Link the knowledge base and record status.**
    - Link from the KB index and every affected KB note.
    - Answer or raise register items, then regenerate the register views.
    - Add a `referenceTraining.<name>` entry to `training-status.json`.
12. **Run the related regression tests.** Record any failure that predates the change.

### Worked Training Record: Conexxus Payment System Product Codes (PSPC) v4.1

**Trigger.** ATL105 was trained before this external reference was available. ATL105 2026-4 Appendix K-27 says: "For a full list of values supported in this field, please refer to the Conexxus PSPC reference document." The gap was open as `SEG111-SME-012` (training issue `APPI-T049-Q010`). The user supplied `private___resources_PaymentSystemProductCodes.pdf` on 2026-10-27 and asked for it to be ingested and trained.

**Steps executed (2026-10-27):**

| # | Step | Result / evidence |
| --- | --- | --- |
| 1 | Identified the document | Conexxus Payment System Product Codes **v4.1**, May 12 2022, 18 pages. Conexxus is the Registration Authority for the ASC X9.104 Part 2 product codes. SHA-256 `D2FB4F47D2C3AD3D77FBAA2FAF19FB15148799BDEC3B968E05D57ADDA15C1C19`. |
| 2 | Searched ATL105 2026-3 and 2026-4 for `PSPC`, `Conexxus`, `PCATS`, `Payment System Product`, `Purchase Restriction` | Six dependencies found and anchored (next table). |
| 3 | Diffed Appendix F 2026-3 vs 2026-4 (521 normalized body lines) | Identical, so the existing 2026-3 Appendix F oracle stays valid for 2026-4. Table 034 and Sub Table 18 text are also unchanged; only the line numbers moved. |
| 4 | Stored the source | `docs/specs/Conexxus_Payment_System_Product_Codes_Version_4.1_2022-05-12.pdf`; extracted with `pypdf` to `docs/specs/extracted_text_pspc_v4.1.txt` (728 lines, page-marked). |
| 5 | Prototyped the parser (Python), then implemented it in Java | 413 source rows, every code `000`-`999` exactly once. Handled: the `000` row layout quirk, wrapped rows `333`/`338`, the `∗` footnote on `032`/`033`, and category header ranges that are not rows. |
| 6 | Classified assignment states | `ASSIGNED`, `DEPRECATED` (`<Deprecated>` marker), `RESERVED_CONEXXUS`, `RESERVED_PROPRIETARY`, `RESERVED_PREAUTHORIZATION` (`071`-`075`), `UNUSED` (`000`). |
| 7 | Fixed two classifier defects found by fail-fast guards | "POS/Loyalty Reserved Discount 1-5" (`920`-`924`) and "Proprietary Prepaid Card Activate/Reload" (`568`/`569`) are **assigned** product names, not reservations. The guard now matches only "Undefined", "Reserved for…", "Proprietary Use" and "Proprietary Future". |
| 8 | Captured the v4.1 release notes | Added codes `052`, `053`, `054`, `061`, `333`-`338` (flag `addedInV41`); these reuse codes deprecated in v3.7. |
| 9 | Built `PaymentSystemProductCodeOracle` | Three modes: `CLASSIFICATION`, `ELEMENT_77_CROSSWALK`, `HOST_PURCHASE_RESTRICTION`. `transactionEligibility` is always `NOT_EVALUATED`. |
| 10 | Generated the crosswalk of all 1,000 codes against Appendix F | 221 match, 621 neither assigned, 17 BUYPASS proprietary assignments, 1 both unused. **142 REVIEW_REQUIRED**: 83 description differs, 38 PSPC-only assigned, 13 Appendix F-only assigned, 6 PSPC deprecated. |
| 11 | Generated the chain package | 420 BRs, 2,032 TS/TC/TD: 1,000 classification, 11 format, 1,000 crosswalk and 21 Table 034 TDs. |
| 12 | Wrote `PaymentSystemProductCodeOracleTest` (6 tests) | Source fidelity, 2026-4 anchors, classification examples, crosswalk, Table 034 gating, full replay of 2,032 TDs. All pass. |
| 13 | Updated the register | `SEG111-SME-012` → `ANSWERED` with resolution and evidence; regenerated the 54 register views. `SEG111-SME-003` is unchanged. |
| 14 | Updated status and the knowledge base | Added `training-status.json` → `referenceTraining.conexxusPaymentSystemProductCodes`. Linked from the KB index, Appendix I/K overview, Appendix I README, Appendix F training note, appendix gap-closure register, and Segment 112 BR `AI-112-001-034`. |
| 15 | Ran the regression tests | Green: PSPC (6), Appendix F (5), Appendix K catalog (3), communication register (8), repository structure (4), training-status consistency (1), canonical denominator (9). `AppendixCoverageConsistencyTest` already failed before this change on unrelated Appendix R/AE counts. |

**ATL105 2026-4 dependencies on PSPC (memory):**

| Anchor | Lines | Rule learned | Authority |
| --- | --- | --- | --- |
| Appendix K-27, Table 034 note | 36323-36324 | Table 034 value list = Conexxus PSPC | PSPC (normative) |
| Appendix K-26, Table 034 layout | 36291-36310 | Table ID n3 `034`; Table Data `ans32`, 1-32 bytes; received only when the request sent Purchase Restriction Method `2` or `3` and the network returned a value; Visa Fleet EMV 2.0 and MC Enhanced Fleet EMV only | ATL105 |
| Appendix I-46, Table 049 Sub Table 18 | 31849-31882 | `0` none, `1` chip only, `2` host only, `3` both. Mandatory for MC Enhanced Fleet (MCF/MGF) auth, pre-auth and completion. If absent, BUYPASS defaults to no restrictions. | ATL105 |
| Appendix I-47 notice | 31895-31898 | MC Enhanced not in First Data production before June 2026 | ATL105 |
| Section 13.2, Element 118 | 24752-24754 | Element 116 = `034` carries Host-based Purchase Restriction | ATL105 |
| Appendix F-1 | 28593-28612 | Appendix F codes are the valid Element 77 entries | ATL105 Appendix F (PSPC informative only) |

**Decisions recorded (memory):**

- **Element 77 (Segments 102/157)** is validated against Appendix F only. PSPC v4.1 divergence is flagged, never substituted.
- **Material meaning collisions** for Element 77. An AI artifact that applies the PSPC name to one of these codes goes to review:
  - `006`-`010`, `016`-`018`, `025`, `033`, `035`, `037`-`040`
  - `052`-`054`, `060`, `061`: Appendix F Ultra Low Sulfur products vs PSPC B1, Additized and Renewable Diesel
  - `055`-`059`, `151`-`153`, `303`
- **Table 034 values:**
  - Assigned PSPC code → PASS.
  - `000` → FAIL.
  - Deprecated or any reserved code → REVIEW_REQUIRED.
  - No explicit code list → REVIEW_REQUIRED. ATL105 does not define how codes are packed in `ans32`.
- **Visa Fleet EMV 2.0 Table 034** → REVIEW_REQUIRED (`SEG111-SME-003`). MC Enhanced Fleet → evaluated. Any other program → FAIL.
- **Range `800`-`899`** is PSPC "Reserved for Proprietary Use". BUYPASS uses `800`-`809` (debit surcharge) and `890`-`896` (FSA/HRA). The Appendix F `895`/`896` overview-versus-detail conflict stays REVIEW_REQUIRED.
- **Assigned codes whose names contain "Reserved" or "Proprietary"** (`568`, `569`, `920`-`924`) are assigned products, not reservations.
- **Validation rules VR-PSPC-01 to VR-PSPC-14** govern AI artifacts that cite PSPC. See [PSPC Validation Strategy](pspc-payment-system-product-codes/Validation%20Strategy.md).

**Memory artifacts:**

| Artifact | Path |
| --- | --- |
| Training memory | [pspc-payment-system-product-codes/README.md](pspc-payment-system-product-codes/README.md) |
| BR coverage | [pspc-payment-system-product-codes/BR Coverage.md](pspc-payment-system-product-codes/BR%20Coverage.md) |
| Test coverage | [pspc-payment-system-product-codes/Test Coverage.md](pspc-payment-system-product-codes/Test%20Coverage.md) |
| Validation strategy | [pspc-payment-system-product-codes/Validation Strategy.md](pspc-payment-system-product-codes/Validation%20Strategy.md) |
| Source PDF / extract | [PDF](../specs/Conexxus_Payment_System_Product_Codes_Version_4.1_2022-05-12.pdf), [extract](../specs/extracted_text_pspc_v4.1.txt) |
| Source oracle | [pspc-product-code-oracle.json](../../../../src/main/resources/atl105/pspc-product-code-oracle.json) |
| Validator | [PaymentSystemProductCodeOracle.java](../../../../src/main/java/com/coreauth/validator/canonical/PaymentSystemProductCodeOracle.java) |
| Generator | [GeneratePaymentSystemProductCodeCoverage.java](../../../../src/main/java/com/coreauth/validator/coverage/GeneratePaymentSystemProductCodeCoverage.java) |
| Test | [PaymentSystemProductCodeOracleTest.java](../../../../src/test/java/com/coreauth/validator/PaymentSystemProductCodeOracleTest.java) |
| Chain package | [pspc-product-code-chain-package.json](../../test-output/test-json/pspc-product-code-chain-package.json) |
| Crosswalk | [pspc-appendix-f-crosswalk.csv](../../test-output/test-json/pspc-appendix-f-crosswalk.csv) |
| Status entry | `training-status.json` → `referenceTraining.conexxusPaymentSystemProductCodes` |
| Register | `SEG111-SME-012` (ANSWERED), `SEG111-SME-003` (OPEN) |

**Open items:**

- SME confirmation that PSPC v4.1 is the version the networks apply.
- The Table 034 `ans32` packing format.
- Visa Fleet scope.
- Element 77 rulings if PSPC names are ever accepted.
- Merchant Dynamic Card Table eligibility.
- Comparison of AI Solution artifacts once they are delivered.

**Reproduce or retrain** (from `regression-artifact-validator`):

```powershell
mvn -q -DskipTests compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GeneratePaymentSystemProductCodeCoverage'
mvn -q -DskipTests compile exec:java '-Dexec.mainClass=com.coreauth.validator.register.GenerateCommunicationRegisterViews'
mvn '-Dtest=PaymentSystemProductCodeOracleTest,AppendixFProductCodeOracleTest,AppendixKTableCatalogTest,CommunicationRegisterTest' test
```

For a new PSPC version, follow steps 4-12 of the External Reference Document Training Procedure. Pay particular attention to the crosswalk diff for codes Conexxus has reused.

## Test Phases

### Knowledge Extraction and Knowledge Approval

Document extraction records source pages and rules. Knowledge extraction builds message structures, field rules and values, appendix tables, relationships, and specification-pack metadata. The Test Validation Team independently reviews and baselines the source-derived catalog before requirement or scenario comparison. SME/TBA review is preferred; when unavailable, unresolved interpretations remain `PROVISIONAL`.

### Requirement Derivation

Each approved source rule becomes an explicit, testable BR with a canonical source anchor and exact source reference.

### Smoke/Sanity and BVT

Checks JSON parsing, manifest/schema loading, adapter initialization, specification-pack loading, and a minimal canonical artifact chain.

**Entry:** package and manifest received.  
**Exit:** ingestion and baseline validation pass.  
**Owner:** Test Validation Team with AI Solution support.

### Manual Testing

Checks business meaning, source interpretation, lifecycle intent, unknown combinations, and review findings.

**Entry:** normalized artifacts and independent rule catalog available.  
**Exit:** SME/TBA decisions recorded.  
**Suspend:** missing evidence, unavailable SMEs, or unusable data.  
**Resume:** evidence or review decision supplied.

### Scenario Review and Coverage Baseline

The Test Validation Team derives the independent scenario baseline from the independent rule catalog, removes unsupported combinations, and records missing or ambiguous combinations for review. AI scenarios are measured against this independent baseline; the producer's approved scenario list is evidence under test, not the coverage denominator. SME/TBA review may change the disposition but is not silently simulated by scripted approval.

### Test Prioritization

Approved scenarios are ranked by risk, business priority, specification change impact, and available evidence before test cases are composed.

### Test Case Review

Generated test cases include objectives, preconditions, payloads or steps, expected results, source anchors, and test-data links. The Test Validation Team checks them against the independent rule catalog and executable validators. Cases with unresolved interpretation, unsupported oracle values, or missing data remain `PROVISIONAL` and are excluded from business-certification claims until reviewed.

### Integration/API Testing

Checks adapters, package ingestion, specification-pack loading, report generation, and approved API/host contracts.

**Entry:** interface contract and environment available.  
**Exit:** integration paths and error handling pass.  
**Suspend/resume:** environment or dependency outage/resolution.

### Regression Testing

Runs the reusable core suite and all impacted specification-pack tests whenever the engine, adapter, rule catalog, AI package, or schema changes. Trained transaction contexts also run when the Test Solution source package changes: their training test compares the source hash and fails until the context is retrained.

### Performance Testing

Measures ingestion, normalization, semantic validation, coverage, and reporting for agreed package volumes. Performance results do not override semantic correctness.

### UAT Support

The Test Solution supplies traceability and evidence. Fiserv Business/Development Team owns business acceptance.

### Parallel Testing

Compares legacy/reference results, AI output, and Test Solution output using common canonical inputs. Differences are classified as rule, adapter, data, or implementation variance.

### Manual Sample Validation Gate

Before broad acceptance of an AI package, the Test Team shall manually validate only a small, risk-based handful of high-risk AI artifacts. The sample is not intended to manually review every generated artifact.

The high-risk sample should be selected from artifacts with business, safety, financial, lifecycle, dependency, ambiguity, or high-impact serialization risk. Where available, select a handful across BRs, scenarios, test cases, and JSON data. For each selected item, the Test Team compares:

```text
AI-generated artifact
  <> canonical Test Solution artifact
  <> independent specification rule
  <> expected business behavior
```

The initial ATL105 sample should normally contain five to eight items, adjusted when risk or package size requires it:

| Sample item | Manual checks | Required evidence |
| --- | --- | --- |
| One high-impact Business Requirement | Atomic wording, source anchor, condition, and expected behavior | Annotated source rule and comparison result |
| One positive and one negative Scenario | Valid combination, boundary/negative intent, duplicate handling, and requirement link | Scenario comparison and disposition |
| One high-priority Test Case | Preconditions, steps, expected result, priority rationale, and scenario link | Case review record and approval decision |
| One lifecycle Test Data JSON request | Request-only payload, original/follow-up relationship, sequence reuse, and lifecycle values | Canonical mapping and lifecycle check |
| One serialization or dependency-risk request | Segment order, lengths, separators, required fields, and companion-segment conditions | Validator output and wire-format comparison |

Manual reviewers record the artifact version, source rule, reviewer, date, observed differences, defect or assumption, expected disposition, and approval status. A sample passes only when the AI artifact, canonical Test Team artifact, independent rule, and expected business behavior agree. A sample finding does not automatically reject the entire package; it is classified by severity and used to determine whether additional sampling or broader correction is required.

The comparison records format/schema agreement, source-anchor agreement, complete traceability, expected-result agreement, payload semantics, differences, defects, assumptions, and manual-review findings.

This gate confirms that the AI Solution and Test Solution produce compatible expected results. It does not replace automated validation or final SME/Fiserv Business/Development approval.

Recommended outcomes:

```text
SAMPLE_VALIDATION_PASS
SAMPLE_VALIDATION_PASS_WITH_FINDINGS
SAMPLE_VALIDATION_REJECTED
SAMPLE_VALIDATION_BLOCKED
```

`SAMPLE_VALIDATION_BLOCKED` applies when required source evidence or SME approval is unavailable.

## Test Data

AI candidate artifacts belong under:

```text
test-input/ai-solution/
```

Generated Test Team artifacts and reports belong under:

```text
test-output/
```

Data shall be synthetic or approved masked data, versioned, traceable, reproducible, and explicit about logical versus wire-ready representation.

Business identifiers in test data are reported only where Test Solution evidence carries them. For Mastercard Credit Purchase, the Merchant ID is the BUYPASS Merchant Number component of Segment 100 Element 102 (`123456` in `TD-SEG100-E102-004` and `TD-SEG100-E102-005`). No evidence carries a Merchant Name, so it is reported as `NO_EVIDENCE` and is not invented.

The AI team must provide JSON schemas, metadata, source-reference conventions, expected-outcome semantics, lifecycle representation, serialization rules, and known limitations.

## Test Phases - Out of Scope

Unless separately commissioned: production testing, penetration testing, regulatory certification, full host authorization certification, and client UAT execution.

## Test Tools

| Tool | Purpose |
| --- | --- |
| Java | Deterministic validation engine |
| Maven | Build and dependency management |
| JUnit 5 | Automated validation and regression suite |
| Jackson | JSON parsing and canonical models |
| JSON Schema Validator | Structural contract validation |
| JSON/Markdown | Audit and approval reporting |
| CI workflow | Repeatable validation gates |

## Test Schedule

```text
Contract and schema agreement
  -> specification-pack readiness
  -> AI artifact intake
  -> BVT/smoke validation
  -> semantic and traceability validation
  -> coverage closure
  -> SME/TBA review
  -> Fiserv Business/Development Team approval
```

Each specification pack has its own release version, baseline, schedule, and coverage target.

## Sign-off Criteria

### Customer Testing Sign-off Criteria

- Mandatory specification rules are covered.
- No unresolved blocking semantic or traceability defects.
- Manual-review findings are dispositioned.
- Reports are reproducible for the approved package.
- Fiserv Business/Development Team accepts the evidence.

### Testing Sign-off Criteria

- Planned automated checks execute successfully.
- No unresolved blockers remain.
- Test data is safe and reproducible.
- Coverage and decision reports are published.

### Hyper Care/Post-Deployment Sign-off Criteria

Post-release monitoring tracks validator defects, adapter failures, specification-pack corrections, false positives, false negatives, new AI formats, and regression results.

## Test Governance Process

Governance controls package intake, schema/version changes, rule approvals, manual-review decisions, defects, reports, and release status.

### Team Meetings

| Meeting | Frequency | Participants |
| --- | --- | --- |
| Core validation review | Weekly | Test Validation, AI Solution, TBA/SME |
| Defect triage | Weekly/as required | Test, Development, AI Solution, SME |
| Specification-pack review | Per change | Test, SME/TBA, Fiserv Business/Development |
| Release/sign-off review | Per package | Accountable stakeholders |

### Test Report

Reports contain package version, specification-pack version, artifact counts, errors, warnings, coverage, review queue, execution timestamp, and final status.

### Test Metrics

- Artifacts received, generated, normalized, and validated.
- Traceability completion.
- Rules covered, partial, missing, and review-required.
- Schema, format, semantic, and traceability failures.
- Adapter normalization rate.
- Defect aging and retest rate.
- Execution duration and throughput.

## Defect Management Process

Defects are recorded against package version, specification-pack version, artifact ID, canonical rule, validation stage, evidence, severity, priority, owner, and retest result.

### Defect Priority and Severity

| Priority | Meaning |
| --- | --- |
| P1 | Validation failure blocks testing or creates unsafe approval risk |
| P2 | Major rule, traceability, or report failure blocks significant scope |
| P3 | Limited issue with documented workaround |
| P4 | Minor documentation, message, or reporting issue |

### Defect Triage Discussion

Test Validation, AI Solution, Development, TBA/SME, and Fiserv Business/Development representatives review validity, root cause, impact, disposition, target fix, and retest evidence.

### Responsibility

The Test Validation Lead owns technical triage. The AI Solution Team owns candidate-artifact corrections. Development owns framework defects. TBA/SME owns business interpretation. Fiserv Business/Development owns business acceptance decisions.

## Testing Deliverables

| ID | Deliverable | Description |
| --- | --- | --- |
| TD01 | Core Auth Regression Test Validation Strategy | Approved testing approach and governance |
| TD02 | Specification Pack | Rule catalog, source anchors, schemas, dependencies, and coverage profile |
| TD03 | Canonical Artifact Package | Generated or normalized BRs, scenarios, cases, and JSON data |
| TD04 | AI Artifact Intake Package | Original AI files, manifest, metadata, schemas, and traceability |
| TD05 | Automated Validation Suite | Deterministic validators and regression tests |
| TD06 | Coverage Report | Rule-level JSON and Markdown evidence |
| TD07 | Defect/Review Report | Open defects, manual-review decisions, and dispositions |
| TD08 | Test Summary Report | Phase outcome, metrics, risks, and sign-off status |
| TD09 | Transaction-Context Training Package | Business BR/FR/scenario matrix, canonical trained chains, review queue, context BR coverage report and training gate (first: Mastercard Credit Purchase) |

## Test Environments

| Environment | Purpose |
| --- | --- |
| Local developer environment | Rule and validator development |
| CI validation environment | Repeatable gates and regression |
| AI intake repository | Versioned candidate AI artifacts |
| Test-output repository | Generated reports and evidence |
| Integration environment | Approved API/host contract testing |
| Performance environment | Package-volume and throughput testing |

## Roles and Responsibilities / RACI Matrix

| Activity | Test Validation | AI Solution | TBA/SME | Fiserv Business/Development |
| --- | --- | --- | --- | --- |
| Rule catalog | R/A | I | C | A/C |
| AI artifact generation | C | R/A | C | I |
| Canonical artifact generation | R/A | C | C | I |
| Schema/format definition | R | R/A | C | I |
| Semantic validation | R/A | C | C | I |
| Business interpretation | C | C | R/A | A |
| Defect triage | R/A | R | C | C |
| Manual-review decision | C | C | R | A |
| Final release approval | C | I | C | R/A |

## Risks and Issues

| ID | Risk/Issue | Impact | Mitigation | Owner |
| --- | --- | --- | --- | --- |
| R001 | AI JSON/workbook format changes without a declared contract version, or a supported adapter drops/misreads valid content | Valid AI artifacts are rejected or become false negatives before semantic comparison; traceability and coverage are understated | Require a run manifest and versioned schema; preserve raw input; select only an explicit adapter; reconcile input/output counts and links; unknown formats become `ADAPTER_UNSUPPORTED` / `NOT_ASSESSED` with no coverage credit | AI Solution/Test |
| R002 | Specification rules are incomplete or ambiguous | False coverage confidence | Independent rule catalog and manual-review gate | TBA/SME |
| R003 | Sensitive or production-like data enters repository | Security/compliance exposure | Synthetic/masked-data gate | AI Solution/Test |
| R004 | ATL105 segment rules remain unmapped | Incomplete ATL105 certification | Segment-by-segment specification-pack roadmap | Test/SME |
| R005 | SME/TBA reviewer is unavailable or decisions are not recorded | Semantic defects or ambiguous interpretations may be mistaken for approved behavior; release decision is unauditable | Keep decisions `PENDING` / `REVIEW_REQUIRED`; automate evidence preparation and deterministic checks only; use a formally delegated reviewer or record explicit business risk acceptance; do not certify on silence | Test/Fiserv Business/Development |
| R006 | Test Solution baseline omits a source-supported requirement found in AI output | A valid AI BR may be mislabeled AI-only, or coverage may use an incomplete denominator | Record a Test Solution omission candidate; independently re-derive from ATL105 without copying AI wording; keep coverage `NOT_CALCULABLE` for an incomplete eligible baseline | Test/TBA/SME |
| R007 | Validator logic or rule catalog has a false-pass defect | Invalid AI behavior may pass the checks and appear covered | Bind validators to source-anchored rules; test positive, negative, boundary, and applicability cases; mutation-test wrong values, omitted checks, and context changes; report validator effectiveness separately from AI outcome; require independent review for unresolved semantics | Test/TBA/SME |
| R008 | A business transaction context's scope or FR placement is heuristic, or its scenarios are shared by BRs with different anchors | Business coverage is over- or understated; shared scenarios silently fail canonical traceability | Versioned scope rule; keyword FR placement kept `REVIEW_REQUIRED`; only canonically valid chains counted; failing BRs listed in the review queue with reasons; split shared scenarios per BR | Test/TBA/SME |

## Appendix A: ATL105 Demonstration

The current demonstration uses ATL105 Segment 100 with related Segments 111 and 130, including Sale and Void examples. See [ATL105-Segment100-Demo-Runbook.md](ATL105-Segment100-Demo-Runbook.md).

```text
AI JSON
  -> format/metadata assessment
  -> canonical normalization
  -> BR/Scenario/Test Case/Test Data traceability
  -> Segment 100 and related-segment validation
  -> lifecycle and semantic checks
  -> coverage and manual-review report
```

## Appendix B: All-Specification End State

```text
Any specification
  -> source analysis and specification pack
  -> independent rule catalog
  -> canonical BR generation
  -> canonical scenario generation
  -> canonical test-case generation
  -> canonical JSON test-data generation
  -> AI artifact normalization and validation
  -> coverage and governance
  -> approval-ready package
```

A new specification shall be integrated through a specification pack without changing the reusable Core Auth validation engine.
