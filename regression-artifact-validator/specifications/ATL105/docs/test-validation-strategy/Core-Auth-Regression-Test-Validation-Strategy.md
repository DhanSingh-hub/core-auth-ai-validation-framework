# Core Auth Regression Test Validation Strategy

**Template basis:** TFF Test Strategy Document - June 19_comments.docx  
**Audience:** AI Solution Team, Test Validation Team, Fiserv Business/Development Team  
**Current foundation specification:** BUYPASS Platform ATL105 Message Format Specification 2026-3  
**Current input release:** BUYPASS Platform ATL105 Message Format Specifications 2026-4 (October 09, 2026), ingested as hash-pinned overlays; the core segment rule catalogs remain pinned to 2026-3  
**Companion EMV reference:** Fiserv EMV and Chip Implementation Guide v2025.RG20, which ATL105 §10.14 requires to be used with ATL105; ingested 2026-10-27 (see [EMV Companion Reference Training Knowledge](ATL105-EMV-Implementation-Guide-Training-Knowledge.md))  
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
17. Appendix B: All-Specification End State
18. Appendix C: EMV Companion Reference Summary

## Version History and Approvals

| Version | Date | Author/Owner | Change | Status |
| --- | --- | --- | --- | --- |
| 0.2 | 2026-09-16 | Core Auth Test Validation Team | Added Test Team solution design, AI artifact validation value, and risk-based manual testing | Working review |
| 0.3 | 2026-10-17 | Core Auth Test Validation Team | Trained QE-Fiserv SME feedback as 33 enforced rules (AIV-025) and applied them to the full 10 October AI delivery | Working review |
| 0.4 | 2026-10-26 | Core Auth Test Validation Team | Added business transaction-context validation and coverage (Mastercard Credit Purchase), the training gate, merchant test-data evidence rule, deliverable TD09 and risk R008 | Working review |
| 0.5 | 2026-10-27 | Core Auth Test Validation Team | Ingested the EMV and Chip Implementation Guide v2025.RG20, the companion document that ATL105 2026-4 §10.14 refers to and that was missing in earlier training. Added the EMV companion intake section and precedence rules; guide-derived tag, cross-field, lifecycle, AID, fallback, response and CAPK rules; the Appendix S exponent correction; EMV scope, data, tools and environment notes; deliverable TD10; risks R009 and R010; and Appendix C. | Working review |

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

### EMV Companion Reference Intake (October 2026)

ATL105 2026-4 §10.14 (EMV Processing Requirements, p.10-80; the same note is at 2026-3 p.10-79) says:

> "Refer to First Data EMV Implementation Guide ... The EMV Implementation Guide must be used in conjunction with the ATL105 Message Format Specifications."

Appendix R also defers the complete EMV tag list to "your BUYPASS representative". The document was not available in earlier training. The affected chip-data rules were held as `REVIEW_REQUIRED` or "no formula stated".

On 2026-10-27 the Test Team ingested the document under its current name:

- **Document:** Fiserv *EMV and Chip Implementation Guide*, Generic Edition, v2025.RG20 (12 August 2025, 212 pages), file `Fiserv_Generic_EMV_Implementation_Guide_v2025_RG20_081225.pdf`. The PDF is a local copy and is **not committed**, because this repository is public. Get it from the Fiserv Direct Platform Specifications portal.
- **SHA-256:** `A79B133C950CA2A0734E8D38D07AAE140A254E0069A4FC3F61D653CCC1489EF4`
- **Ingested as:** a separately hash-pinned **companion overlay**

The complete trained knowledge is the persistent memory for this topic: [ATL105 EMV Companion Reference Training Knowledge](ATL105-EMV-Implementation-Guide-Training-Knowledge.md). It contains:

- gap register and tag catalog
- rules: 14 tag, 11 cross-field, 17 lifecycle, 10 AID, 7 fallback, 6 response and 8 CAPK
- RQ traceability index and 53 scenarios
- conflicts, SME queries and implementation backlog

**Precedence and evidence rules (mandatory):**

| Topic | Rule |
| --- | --- |
| Precedence | ATL105 governs. The guide itself states that the message specification overrides it and that it "is not a specification". Guide content fills only what ATL105 delegates to it. Conflicts are logged and ATL105 is enforced (`SPEC_PRECEDENCE`). |
| Provenance | Guide rules carry a companion anchor (`FISERV-EMV-IG` / `2025.RG20` / section / RQ / page), never an ATL105 `sourceAnchor`. AI-to-Test matching stays keyed on ATL105 anchors. |
| Status | The existing statuses do not change. Guide-derived rules are `PROVISIONAL`. They run as findings: **FAIL-candidate** for message-observable `RQ` "must" rules not contradicted by ATL105; **REVIEW** for best practices, illustrative tables and brand-dependent tags; **INFO** for device, kernel, receipt and certification rules. They never count as certified Rule Coverage until SME/TBA confirms BuyPass applicability. ATL105-stated rules that the guide corroborates stay `SOURCE_VERIFIED`. |
| Scope | The guide is Fiserv-generic across five front ends (Nashville, Cardnet, North, BuyPass, Omaha). Tag meanings vary by brand. Table 25 is an expectation, not a mandatory list: v2019.RG1 removed "mandatory tags". |

**What the guide fills for the Test Solution:**

| ATL105 gap | Guide content applied | Result |
| --- | --- | --- |
| Appendix R "complete list of EMV tags" | Table 25 (tags expected in authorization, reversal, settlement/completion and response) and Table 26 (formats and lengths) | Tag catalog for Element 190 (Segment 130 request, Segment 131 response) |
| `BR-SEG100-APPR-CROSS-FIELD` / `SEG130-SME-003` (no chip-to-Segment 100 formula) | RQ3400/3500 (9F02 includes cash back; 9F03 = cash back), RQ4200 (5F2A), Table 28 (9C values), RQ1400 (5F24), §4.8 (9A) | Candidate formulas: 9F02 = Elements 41+58+99+17; 9F03 = Element 17; 5F2A = Element 20; 9C from the Appendix G Prompt Code; and others. Run as `REVIEW_REQUIRED` until SME confirmation. |
| §10.14.2.4 "EMV data is not required on Reversal transactions" | Table 10 and RQ4600 (1st or 2nd Generate AC data by reversal reason; same tag list as the original) | Absence stays valid. When EMV data is present, its content is validated. |
| Lifecycle | §5.10-5.20: completion reuses the original chip data and entry mode; partial approval (8A `10`, reversal on cardholder decline, settle at the partial amount); incremental authorization keyed without EMV data; SAF uses 1st Generate AC data; refunds; balance inquiry is never reversed | Lifecycle rules EMVIG-LC-001 to LC-017 |
| `SEG130-SME-008/012` (no card-type codes for the §10.14.2.3 brands) | Tables 4, 12, 13 and 63 (AIDs, US Common Debit AIDs, message type by AID type) | Proposed AID-based EMV eligibility; product-to-AID bridge; PIN-bypass and cash-back rules by AID |
| §10.14.4 fallback "80/90 or spec equivalent" | RQ4300-RQ4500, §5.8.1 (fallback not allowed), Discover empty-candidate-list rule | Appendix J `80`/`79`/`90` confirmed; fallback-eligibility checks |
| Appendix S CA Public Key file | Tables 14 and 15, §6 | **Correction:** the 6-character exponent is hex `010001`, not `065537`. RID is exactly 10 characters; modulus is at most 496 hex characters; at most 6 active keys per RID. Element 30 = `9` and "RQST/CURRENT HASH MATCH" map to the guide's update / no-update status. |
| Segment 131 response chip data | Table 7 (8A values), §4.12.2 (never both 71 and 72; scripts up to 128 bytes), RQ5500 (routing disclosure) | Response rules; candidate mapping from Element 83 to 8A |

**Still open after ingestion:**

- `SEG130-SME-010` (Element 187 echo)
- Appendix T CARC request legality (`SEG130-SME-007/011`)
- Segment 131/132 wire questions (`SEG131-SME-001/002/004`, `SEG132-SME-001`-`006`)
- Cryptogram and CAPK authenticity (external by design; brand L3 certification on ICCSimTMat)
- The new SME queries `Q-EMV-01` to `Q-EMV-11`
- Three further external references named by the guide and not supplied: BuyPass Petroleum Industry Specifications, Petroleum Implementation Guide (WEX DF30/DF31), and USPF AFD Chip Fallback Best Practices

These are listed in the knowledge file and must not be invented.

**Implementation status:** the knowledge, rules and scenarios are documented. No validator code was changed in this intake. The highest-priority code follow-ups are:

- B-01: Appendix S exponent value domain and checksum fixtures (a false-pass risk; see R010)
- B-02: AID RID agreement across 84, 9F06 and 4F. The Appendix R example itself pairs a Visa Common Debit 9F06 with a Mastercard 84.
- B-03: Element 188 equals 5F34, an ATL105-sourced deterministic check
- B-10: communication-register updates

Each code change must follow the handbook's Automatic Update Rule.

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
- Companion-reference rules from the EMV and Chip Implementation Guide (required by ATL105 §10.14) for EMV chip data, lifecycle and CA public key files, applied under ATL105 precedence.
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

Segment 130 EMV chip data (Element 190) is also checked against the EMV companion tag catalog and the candidate chip-to-Segment 100 cross-field rules. Those checks remain `REVIEW_REQUIRED` until SME confirmation (see [EMV Companion Reference Intake](#emv-companion-reference-intake-october-2026)).

### Application Functional Scope - Business Transaction Contexts

A business transaction context validates a business flow across segments, starting from the approved FR-matrix template rather than from one segment. The first context is **Mastercard Credit Purchase**:

```text
BR-01 Mastercard credit financial purchase transaction processing
  -> FR-001 Card Validation ... FR-008 Post Authorization
  -> business scenarios TS-001..TS-037 (template) and TS-038 onward (Test Solution)
  -> Test Solution BR -> TS -> TC -> TD chains (Segments 100, 111, 123, 130, 131 and the financial response)
```

The business BR stays at business level; segment-level Test Solution BRs are traceability evidence under each scenario. The context's trained package, review queue and gates are defined in the [training handbook](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md#transaction-context-training-mastercard-credit-purchase) and tracked under `transactionContextTraining` in `training-status.json`. AI artifacts for this context are validated against that package once an AI package is provisioned.

### Out of Scope

- Auto-approval based only on AI claims.
- Production or live payment data.
- Client approval without recorded evidence.
- Host certification without an approved integration contract.
- Security, penetration, regulatory, localization, accessibility, and mobile certification unless separately commissioned.
- EMV kernel and brand L1/L2/L3 certification; cryptogram (ARQC/TC/ARPC) and CA public key authenticity; issuer-script execution on the card; device receipts, prompts and reports (EMV guide Chapters 3, 7, 8 and 13). These remain external certification evidence, produced through the Fiserv-mandated ICCSimTMat and t3:Cert process.

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

Candidate scenarios are generated from valid combinations of transaction type, card type, entry mode, feature settings, and specification constraints. For EMV, the combinations also include:

- AID type: Global Credit, Global Debit or Common Debit, with its message type, PIN-bypass and cash-back eligibility
- CVM
- Interface: contact, contactless or fallback
- Lifecycle role: original, reversal reason, completion, partial approval or SAF

These dimensions are listed in the [EMV scenario catalog](ATL105-EMV-Implementation-Guide-Training-Knowledge.md#14-emv-test-scenario-catalog). The Test Validation Team independently derives and baselines the required rule/scenario set from the specification pack. AI-reported scenario counts and coverage percentages are evidence under test, not the certification denominator.

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

EMV test data follows these rules from the [EMV companion knowledge](ATL105-EMV-Implementation-Guide-Training-Knowledge.md#15-synthetic-test-data-guidance):

- Use synthetic chip data only. Never use real PANs, track data, PIN blocks, cryptograms, or production CA public keys. The test and production CAPK sets are never interchangeable.
- Element 190 never carries Tag 5A or 57.
- AID tags (84, 9F06, 4F) share one RID.
- Element 188 mirrors Tag 5F34.
- Do not use the ATL105 Appendix R example as a positive oracle for AID or currency consistency.

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
| ICCSimTMat for Fiserv / t3:Cert (external, Fiserv-mandated) | Brand L3 EMV certification evidence: card logs, traces and test plans. Not part of this platform; consumed as evidence only. |

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
- EMV companion-reference rules are dispositioned. Guide-derived `PROVISIONAL` rules and open `Q-EMV-*` queries are not counted as certified coverage.

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
| TD10 | EMV Companion Reference Training Knowledge | [Hash-pinned ingestion of the EMV and Chip Implementation Guide](ATL105-EMV-Implementation-Guide-Training-Knowledge.md): gap register, tag catalog, guide-derived rules, scenario catalog, conflicts, SME queries and implementation backlog |

## Test Environments

| Environment | Purpose |
| --- | --- |
| Local developer environment | Rule and validator development |
| CI validation environment | Repeatable gates and regression |
| AI intake repository | Versioned candidate AI artifacts |
| Test-output repository | Generated reports and evidence |
| Integration environment | Approved API/host contract testing |
| Performance environment | Package-volume and throughput testing |
| Fiserv host simulators / certification environment (external) | Brand certification and TOR testing. Test CAPK set only; it is never interchangeable with production CAPK. Production CAPK is loaded only at go-live. |

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
| R009 | The EMV companion guide is guidance-only and Fiserv-generic across five front ends. Tag meanings vary by brand. It has internal inconsistencies (for example 9F5B, 9F66, the TVR byte 5 table) and names further documents that were not supplied (BuyPass Petroleum Industry Specifications, Petroleum Implementation Guide, USPF AFD fallback best practices). | Guide-derived rules could be over-enforced on BuyPass, or applied with the wrong brand meaning | ATL105 precedence; companion provenance anchors; guide rules stay `PROVISIONAL` (FAIL-candidate or REVIEW) and outside certified coverage until SME confirmation (`Q-EMV-01` to `Q-EMV-11`); missing documents recorded, not inferred | Test/TBA/SME |
| R010 | The Appendix S CAPK oracle accepts exponent `065537` and computes SHA-1 over its hex-decoded bytes, while the guide defines the 6-character exponent as hex `010001` | False pass on synthetic CAPK fixtures and false fail on genuine key records | Correct the value domain and checksum fixtures (backlog B-01) with negative tests; record the ATL105 "216+1" rendering as a specification defect (`Q-EMV-03`) | Test |

## Appendix A: ATL105 Demonstration

The current demonstration uses ATL105 Segment 100 with related Segments 111 and 130, including Sale and Void examples. See [ATL105-Segment100-Demo-Runbook.md](ATL105-Segment100-Demo-Runbook.md). EMV demonstration scenarios are catalogued in the [EMV companion knowledge](ATL105-EMV-Implementation-Guide-Training-Knowledge.md#14-emv-test-scenario-catalog). Examples:

- EMV-TS-006: Element 190 without Tag 84 or 9F06
- EMV-TS-012: Element 188 does not match Tag 5F34
- EMV-TS-017 / 018: fallback entry mode `80` with and without Segment 130
- EMV-TS-024: reversal without EMV data

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

## Appendix C: EMV Companion Reference Summary

| Item | Value |
| --- | --- |
| Companion document | Fiserv EMV and Chip Implementation Guide, Generic Edition v2025.RG20 (12 August 2025), formerly the "First Data EMV Implementation Guide" named in ATL105 §10.14 |
| Binding | SHA-256 `A79B133C950CA2A0734E8D38D07AAE140A254E0069A4FC3F61D653CCC1489EF4`; ingested 2026-10-27 against ATL105 2026-4 (SHA-256 `568C04D3081CAC6C5CC545ADEE9C6F8F3A34334E788FDF5EE0260FFEF57C77AF`) |
| ATL105 scope served | §10.14; Segments 130, 131, 132; Appendices R, S, T; Elements 30, 83, 187-194 in EMV contexts |
| Persistent knowledge (memory) | [ATL105-EMV-Implementation-Guide-Training-Knowledge.md](ATL105-EMV-Implementation-Guide-Training-Knowledge.md) |
| Rule families | Tag catalog (EMVIG-TAG, 14), cross-field (EMVIG-XF, 11), lifecycle (EMVIG-LC, 17), AID/brand/routing (EMVIG-AID, 10), fallback (EMVIG-FB, 7), response (EMVIG-RSP, 6), CAPK (EMVIG-CAPK, 8) |
| Scenarios | EMV-TS-001 to EMV-TS-053, with expected outcomes and readiness (`EXECUTABLE`, `REVIEW_REQUIRED`, `EXTERNAL_FIXTURE_REQUIRED`) |
| Corrections identified | Appendix S exponent `010001` (not `065537`); RID exactly 10 characters; modulus at most 496 hex characters; the Appendix R example is not a valid AID or currency consistency oracle |
| Open items | `Q-EMV-01` to `Q-EMV-11`; `SEG130-SME-010`, `-007`, `-011`; Segment 131/132 wire questions; three further external documents named by the guide but not supplied |
| Governance | ATL105 precedence; guide-derived rules stay `PROVISIONAL` and outside certified coverage until SME/TBA confirmation; code follow-ups B-01 to B-11 must follow the handbook Automatic Update Rule |
