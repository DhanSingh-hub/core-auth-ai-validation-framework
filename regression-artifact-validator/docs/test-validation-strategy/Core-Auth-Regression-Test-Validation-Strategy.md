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
5. Test Phases
6. Test Data
7. Test Tools
8. Test Schedule
9. Sign-off Criteria
10. Test Governance Process
11. Defect Management Process
12. Testing Deliverables
13. Test Environments
14. Roles and Responsibilities/RACI
15. Risks and Issues
16. Appendix A: ATL105 Demonstration

## Version History and Approvals

| Version | Date | Author/Owner | Change | Status |
| --- | --- | --- | --- | --- |
| 0.1 | 2026-09-14 | Core Auth Test Validation Team | Template-aligned strategy baseline | Working review |

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

### Document Purpose

This document defines the test objective, scope, approach, phases, data, tools, schedule, governance, responsibilities, deliverables, risks, and acceptance criteria for the Core Auth Regression Test Solution.

### Document Scope

The current implementation foundation is ATL105, with Segment 100 as the first completed module. Current ATL105 delivery expands to all segments, message categories, transaction flows, responses, appendices, dependencies, lifecycle rules, and serialization rules.

The end goal is specification-independent operation: a new specification supplies a specification pack, rule catalog, source anchors, schemas, dependencies, lifecycle model, serialization rules, and coverage profile while the reusable Core Auth engine performs generation, normalization, validation, reporting, and governance.

### Abbreviations

| Term | Meaning |
| --- | --- |
| BR | Business Requirement |
| TBA | Technical Business Analyst |
| SME | Subject Matter Expert |
| BVT | Build Verification Test |
| UAT | User Acceptance Testing |
| RACI | Responsible, Accountable, Consulted, Informed |
| SIT | System Integration Testing |
| AI | Artificial Intelligence |

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

The AI package may contain more or fewer requirements than the independent catalog. Count equality is not an acceptance criterion; every difference must be explained and dispositioned.

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

Runs the reusable core suite and all impacted specification-pack tests whenever the engine, adapter, rule catalog, AI package, or schema changes.

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
| R001 | AI JSON format changes without versioned schema | Intake and traceability failure | Require manifest, schema version, and approved adapters | AI Solution/Test |
| R002 | Specification rules are incomplete or ambiguous | False coverage confidence | Independent rule catalog and manual-review gate | TBA/SME |
| R003 | Sensitive or production-like data enters repository | Security/compliance exposure | Synthetic/masked-data gate | AI Solution/Test |
| R004 | ATL105 segment rules remain unmapped | Incomplete ATL105 certification | Segment-by-segment specification-pack roadmap | Test/SME |
| R005 | Human approval decisions are not recorded | Un-auditable release | Versioned review and sign-off report | Fiserv Business/Development |

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
