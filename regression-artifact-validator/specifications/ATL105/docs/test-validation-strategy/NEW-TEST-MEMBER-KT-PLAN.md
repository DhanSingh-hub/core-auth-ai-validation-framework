# Knowledge Transfer Plan: New Test Team Member

**Audience:** New Core Auth Test Validation Team member  
**Duration:** 10 working days (two weeks); adjust pace to the member's experience and availability  
**Scope:** Test Solution responsibilities for ATL105 and the ISO8583 Milestone 2 work  
**Owner:** Test Lead or assigned KT coordinator  
**Status:** Reusable onboarding plan

## 1. Purpose

Onboard a new team member to contribute safely and independently to the Test Solution: the Test Team's reusable solution for validating AI-generated test artifacts. The member learns the source-based rule method, common validation framework, specification-specific work, evidence practices, and review gates, then demonstrates these skills on a bounded task.

All test resources are cross-trained. The new member should gain working familiarity with specification understanding, independent Test Solution rule/BR authoring, automation and validation, and manual review of AI-generated flows. Assign a rotating work-package lead and a separate peer reviewer after onboarding; do not make a new member a sole owner or approver.

## 2. Learning objectives

By the end of KT, the member can:

1. Explain the Test Solution's role as an **independent validator and evidence producer**, not the AI artifact generator.
2. Locate the authoritative source, version, repository rule catalog, training status, validator controls, and evidence for a bounded ATL105 or ISO8583 scope.
3. Derive a draft, atomic, source-anchored rule/BR independently of AI output and mark unresolved decisions accurately.
4. Trace and assess the chain **source rule -> Test Solution BR -> scenario -> test case -> test data**, while distinguishing structural links from semantic and execution readiness.
5. Run the relevant build, focused tests, and validation workflow; interpret results and report limitations without overstating what a check proves.
6. Preserve AI deliveries and evidence provenance; identify traceability, schema, semantic, coverage, and review-state issues.
7. Conduct a documented, risk-based manual review of an AI-generated end-to-end flow against the independent Test Solution oracle.
8. Use the team's branch, review, and handoff practices; keep source artifacts, evidence, and generated outputs within repository and organization policies.

## 3. Preparation before Day 1

The KT coordinator should:

- Confirm the member's access, assigned mentor, Test Lead, authorized SME/TBA contact, and available time for the two-week plan.
- Identify a bounded ATL105 segment/element and a bounded ISO8583 message or transaction scope for exercises. Avoid assigning the entire specification as a training task.
- Confirm which source versions are authoritative for the exercises and point out any unresolved version/applicability questions.
- Identify a frozen, approved-for-review AI delivery or synthetic exercise fixture. Preserve the original and its hashes; do not use AI output to create the oracle.
- Confirm Java/Maven and any required Python/Node tools are available. Record existing test failures before attributing failures to the new member's work.
- Give read access to the repository and approved specification sources. Do not copy external or restricted source documents into the repository unless policy explicitly permits it.

## 4. Ten-day learning path

Each day includes explanation, demonstration, and hands-on practice. The member keeps a short learning log linking work to evidence; the mentor reviews it in daily check-ins.

| Day | Focus | Hands-on activity | Expected evidence |
|---|---|---|---|
| **1 - Team and product orientation** | Core Auth purpose, stakeholders, Test Solution vs. AI Solution ownership, milestone goals, repositories, communication, and branch/review practices. | Walk through a recent validation report from intake to disposition. Explain what the report proves and what it does not prove. | New-member access confirmed; environment/readiness checklist; written summary of the independent-validation boundary. |
| **2 - Framework architecture and controls** | Canonical package, schemas, adapters, traceability graph, specification packs, AIV controls, reports, and test suites. Separate validator controls (`AIV-*`) from payment/message BRs. | Trace one validation control to its implementation, regression test, and generated evidence. Run the smallest relevant test. | Control-to-code-to-test-to-report map; captured baseline test result, including any pre-existing failure. |
| **3 - ATL105 source understanding** | Authoritative ATL105 2026-3; section/element/segment structure; canonical source anchors; rule catalogs and training status. | From source alone, draft a small source inventory and one atomic rule with exact section/page/line or repository-supported anchor evidence. Only then compare to existing catalogs. | Draft source-backed rule/BR with provenance, applicability, and open questions; mentor review. |
| **4 - Test design and readiness gates** | BR taxonomy; scenario/case/data chain; positive, negative, boundary, conditional, and flow coverage; status vocabulary; SME/TBA approval boundary. | Assess one existing chain for source continuity, completeness, explicit expected outcome, TD association, review state, and execution evidence. | Chain assessment with evidence and explicit pass/fail/review-required/blocked disposition. |
| **5 - ATL105 validation shadow** | AI intake, immutable delivery handling, package normalization, payload validation, independent coverage, discrepancy reporting, and limitations. | Shadow a mentor running a bounded ATL105 validation. Recalculate one reported metric from the eligible denominator and inspect one discrepancy. | Reproducible command/run notes; independent metric check; one reviewed discrepancy record. |
| **6 - ISO8583 source orientation** | Locate the supplied First Data ISO8583 Global Version 2026-3 and Global TA Addendum Version 2021-1a references. Understand message structure, source navigation, and version/applicability controls. | Map a bounded message/transaction area to source sections and record terms, relationships, and conflicts. Do not merge clauses across documents without an applicability/precedence decision. | Hash-backed source inventory for accessible copies; section map; open-decision/conflict entries where applicable. |
| **7 - Validator engineering and regression** | Existing Java patterns, reusable core vs. specification pack boundaries, focused test conventions, report generation, and failure handling. | Implement or extend one bounded validator predicate from an approved/source-backed rule. Add positive, negative, and boundary tests where the source defines expected outcomes. | Peer-reviewed change with regression test; documented validation boundary and non-claims. |
| **8 - Manual AI-flow review** | Freeze and identify the population, eligibility and denominator, risk stratification, independent oracle, reviewer evidence, and finding classification. | Review one mentor-selected AI BR -> scenario -> case -> data flow only after the independent rule baseline exists. Record the source comparison and outcome. | Completed review record with input identity/hash, anchor, artifacts, expected outcome, finding, and reviewer. |
| **9 - Reverse shadow** | Consolidate analysis, authoring, automation, and review skills. | Lead a small assigned task (for example one source-backed rule plus a linked validator check or one bounded AI-flow review); mentor observes and does not take over unless a safety or evidence boundary is at risk. | Complete work package: source evidence, implementation or assessment, tests/evidence, limitation/status, and handoff notes. |
| **10 - Readiness and next assignment** | Knowledge check, review feedback, gap assessment, SME/escalation path, and next rotation. | Present the work package, explain decisions and unresolved issues, and walk through a reviewer handoff. Agree a 30-day development assignment. | Mentor sign-off or explicit gaps and a follow-up plan; next work-package lead/reviewer assignment recorded. |

## 5. Required principles and guardrails

- **Independence:** Derive the Test Solution oracle from authoritative source material before inspecting AI semantics. AI claims, IDs, confidence, declared approvals, and coverage are evidence under test, not authority.
- **Provenance:** Preserve input identity, source/specification version, hashes where required, and the evidence path supporting each conclusion.
- **No invented behavior:** If source wording is ambiguous, conflicting, context-dependent, or lacks a deterministic oracle, use `REVIEW_REQUIRED`/`NOT_ASSESSED` and submit an authorized decision request.
- **No self-approval:** Drafted or automated rules remain draft/review-required until the required authorized SME/TBA or formally delegated approver records a decision. Cross-training does not grant business-approval authority.
- **Readiness discipline:** Structural completeness is not semantic correctness; a link is not a confirmed match; a schema pass is not business validity; a bounded predicate is not full-message or host execution; sample results do not prove all flows.
- **Separate defect classes:** Identify whether an issue is in AI output, Test Solution validator logic, source interpretation, missing provenance, or an external dependency. Do not silently change a frozen AI delivery while assessing it.
- **Regression:** Every validator defect fix or intentional behavior change needs targeted regression coverage and appropriate broader tests.
- **Controlled evidence:** Follow repository storage/retention rules. Do not publish credentials, restricted source material, sensitive payload values, or oversized artifacts contrary to policy.
- **Peer review:** A different resource reviews source-derived rules, validator changes, and acceptance evidence. Record reviewer and decision.

## 6. Core references

Read these in order; the handbook is the mandatory ATL105 method and the milestone plan supplies current cross-specification schedule and scope.

1. [Milestone 2 Test Plan: ATL105 Closure and ISO8583 Validation](MILESTONE-2-TEST-PLAN-ATL105-ISO8583.md)
2. [ATL105 Segment Training Handbook](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)
3. [Test Solution Framework Ownership](TEST-SOLUTION-FRAMEWORK-OWNERSHIP.md)
4. [AI Artifact Validation Controls](AI-ARTIFACT-VALIDATION-CONTROLS.md)
5. [SME Decision and Promotion Workflow](SME-DECISION-AND-PROMOTION-WORKFLOW.md)
6. [Java Framework Agenda](../java-framework-agenda.md)
7. [AI Solution input guidance](../../test-input/ai-solution/README.md)
8. The relevant specification-pack `README.md`, rule catalog, `training-status.json` entry, and the exact frozen AI-delivery review package assigned by the mentor.

## 7. Tools and working routine

- Start with repository guidance and the existing branch policy. Work on an authorized child branch of `Develop`; do not make direct changes on `Develop`.
- From `regression-artifact-validator`, use the framework launcher to inspect and run its supported tasks:

  ```powershell
  .\framework.ps1 -List
  .\framework.ps1 -Task structure
  .\framework.ps1 -Task test -Tests 'FocusedTestClass'
  ```

- Use the smallest focused test for a change, then broaden validation based on its impact. Record command, branch/commit, inputs, and result.
- Before changing a generated artifact, locate its producer and documented refresh order. Do not hand-edit generated reports as a substitute for fixing the source or producer.
- In the daily work log record: objective, source/version, inputs/hashes, files changed, commands/tests, result, reviewer, open questions, and next step.
- Escalate promptly if a required source is missing, a source conflict affects the oracle, a package is unsupported, the denominator cannot be established, or organization policy blocks access.

## 8. Knowledge check and completion criteria

The member is ready for a bounded independent assignment when the mentor confirms the member can demonstrate all of the following:

- Explain why the Test Solution must remain independent of AI-generated requirements and tests.
- Find the correct specification version, source anchor, rule catalog, and current status for an assigned rule.
- Draft one atomic rule/BR from source evidence, state applicability, and distinguish a draft from an approved rule.
- Identify broken traceability, missing/orphan artifacts, placeholders, unresolved semantics, and unsupported coverage claims in an assigned chain.
- Run a focused validator/test workflow and explain the result, failure classification, and limits of the check.
- Complete one manual AI-flow review against the independent oracle without using the AI artifact as the oracle.
- Preserve evidence and communicate a source question or blocker to the correct decision owner.
- Submit work for a separate peer review and incorporate feedback without obscuring prior evidence.

Completion does not mean authorization to approve business semantics or declare a specification complete. If an objective is not met, record it as a development item and keep the next assignment paired or bounded until demonstrated.

## 9. KT record

| Field | Entry |
|---|---|
| New member | |
| KT coordinator / mentor | |
| Test Lead | |
| KT start and target completion | |
| Assigned ATL105 practice scope | |
| Assigned ISO8583 practice scope | |
| Baseline test result / known failures | |
| Day 9 work package and evidence | |
| Peer reviewer | |
| Objectives met / follow-up items | |
| Next assignment and review date | |
