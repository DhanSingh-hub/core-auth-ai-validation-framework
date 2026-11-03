# Milestone 2 Test Plan: ATL105 Closure and ISO8583 Validation

**Planning window:** 1 November-15 December 2026  
**Staffing assumption:** Four dedicated test resources; confirm actual availability at kickoff  
**Specification baselines:** BUYPASS ATL105 2026-3; First Data ISO8583 Global Version 2026-3, with the Global TA Addendum Version 2021-1a treated as a related source requiring applicability and precedence review
**Status:** Draft for team and stakeholder agreement

## 1. Purpose and objective

Milestone 2 delivers the Test Solution's independent capability to validate AI-generated test artifacts for both ATL105 and ISO8583. The Test Solution is the validator and independent evidence producer; it is not responsible for generating the AI Solution's output or approving business interpretations on behalf of an authorized SME.

The Test Solution will derive its own source-anchored rule baseline and use it to assess AI-generated business requirements (BRs), test scenarios (TSs), test cases (TCs), test data (TD), traceability, and coverage. It will report each result as passed, failed, review-required, or blocked, with evidence. Unresolved interpretations must not be silently treated as valid or invalid.

## 2. Scope

### In scope

- Understand, inventory, identify, and hash the in-scope specification versions and their relevant sections, appendices, tables, message/segment definitions, and addenda.
- Independently derive and maintain source-anchored Test Solution rules and BRs. Keep these distinct from AI-generated BRs and from `AIV-*` controls, which describe the validator rather than payment/message behavior.
- Build or extend reusable intake, normalization, schema, traceability, coverage, semantic-validation, reporting, and regression-test capabilities.
- Create specification-specific rule packs for the agreed ATL105 and ISO8583 scope.
- Validate frozen AI deliveries against the independent baseline; retain provenance, input hashes, results, discrepancies, and unresolved questions.
- Manually inspect a risk-stratified 5-10% sample of eligible AI-generated end-to-end flows and record the independent review evidence.
- Retest defects and publish a milestone report with completed scope, results, limitations, and decisions needed.

### Out of scope

- Generating or correcting the AI Solution's BRs, scenarios, cases, or data on its behalf.
- Claiming host, network, processor, or production execution from offline artifact validation.
- Treating structural links, a generated placeholder, AI-reported confidence/coverage, or a manual sample as proof of semantic approval or complete correctness.
- Promoting unresolved source conflicts or business interpretations without an authorized decision.
- Expanding either specification beyond the scope agreed at the 6 November scope gate.

## 3. Planning baseline and controls

The repository currently reports 601 ATL105 catalog rules, while related chain evidence uses different denominators and reports. The current element-chain report counts 555 structurally complete rules, 0 executable non-placeholder chains, and 555 placeholder/review rules. These figures have different scopes and must not be combined as if they were one measure.

During 1-6 November, reconcile the source versions, rule/element denominators, artifact snapshots, and report-generation inputs. Record one dated baseline and its hashes. Until reconciled, mark affected measures as `NOT_RECONCILED`; do not call ATL105 complete based on an older aggregate.

ATL105 technical/source evidence and SME semantic approval are separate gates. ISO8583's two supplied documents also need an explicit source relationship, effective-date/applicability decision, and conflict register before a combined oracle is claimed. If an authorized SME/business decision is unavailable, preserve the item as `REVIEW_REQUIRED` and report the dependency as a schedule risk.

The following related repository standards govern the work:

- [ATL105 Segment Training Handbook](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)
- [Test Solution Framework Ownership](TEST-SOLUTION-FRAMEWORK-OWNERSHIP.md)
- [AI Artifact Validation Controls](AI-ARTIFACT-VALIDATION-CONTROLS.md)
- [SME Decision and Promotion Workflow](SME-DECISION-AND-PROMOTION-WORKFLOW.md)
- [Java Framework Agenda](../java-framework-agenda.md)

## 4. Resource plan

All four test resources (R1-R4) are cross-trained and capable of performing every Milestone 2 activity: specification understanding, independent Test Solution BR/rule authoring, automation and validation, and manual review of AI-generated flows. Resource labels do not denote fixed specialist roles.

At kickoff, assign a rotating activity lead for each work package and a different peer reviewer for its evidence. Rotate leads across ATL105 and ISO8583 and across analysis, authoring, automation, and review as capacity permits. All resources participate in planning, implementation, cross-review, and the final evidence review. Avoid relying on one person as the sole author or reviewer of an oracle, validator control, or acceptance result.

| Resource | Capability and assignment model | Expected contribution |
|---|---|---|
| R1 | Cross-trained across all activities; takes rotating work-package lead and peer-review assignments. | Source maps, independent rules/BRs, automation, AI intake, manual review, or verification according to the current assignment. |
| R2 | Cross-trained across all activities; takes rotating work-package lead and peer-review assignments. | Source maps, independent rules/BRs, automation, AI intake, manual review, or verification according to the current assignment. |
| R3 | Cross-trained across all activities; takes rotating work-package lead and peer-review assignments. | Source maps, independent rules/BRs, automation, AI intake, manual review, or verification according to the current assignment. |
| R4 | Cross-trained across all activities; takes rotating work-package lead and peer-review assignments. | Source maps, independent rules/BRs, automation, AI intake, manual review, or verification according to the current assignment. |

### Collaboration, independence, and rotation

- The assigned rule author derives rules from specification evidence before comparing with AI artifacts. A different resource reviews source interpretation and evidence links.
- The assigned automation author implements controls from source-supported or explicitly bounded rules; a different resource reviews the control and its regression evidence, including what it does not prove.
- The assigned manual sample lead selects flows only after AI delivery freeze. Reviewers judge AI content against the independently derived Test Solution oracle, never using AI content to author that oracle.
- Rotate lead and reviewer assignments between work packages and, where practical, between specifications. Record names, roles, and review evidence in the work register.
- Unresolved semantics go to an authorized SME/TBA or delegated approver. Cross-training does not grant business-approval authority; no resource may represent an unapproved assumption as an approval.

## 5. Schedule and work packages

Work begins on Monday, 2 November, following the 1 November milestone start. The schedule assumes the four cross-trained resources are available throughout the window. Work-package leads and peer reviewers rotate; named assignments are recorded at kickoff and in weekly plans. The week of 14-15 December is reserved for final evidence, decisions, and handover.

| Dates | Focus and work | Primary owners | Exit evidence |
|---|---|---|---|
| **1-6 Nov** | Kickoff; confirm names, capacity, stakeholders, AI delivery owners, and SME approvers. Freeze ATL105 baseline inputs and reconcile conflicting counts/scopes. Inventory and hash both ISO8583 PDFs; map their versions, sections, tables, terminology, and addendum relationship. Agree initial in-scope flows and validation outputs. | All four; rotate work-package lead and peer reviewer. | Signed scope/baseline register; source hashes; ATL105 metric reconciliation or explicit unresolved items; ISO source/version/conflict register; dependency and decision log. |
| **7-13 Nov** | Close high-priority ATL105 source/rule gaps and independently authored BRs for agreed scope. Build ISO8583 source map and first atomic rule/BR catalogue. Identify fields, message types, processing codes, conditions, response relationships, and wire/format constraints where stated. Raise unsupported or conflicting interpretations. | All four; rotate source-analysis, rule-authoring, and review assignments. | Reviewed rule/BR inventories with source anchors; explicit `REVIEW_REQUIRED` queue; agreed AI package formats and required metadata. |
| **14-20 Nov** | Complete ATL105 agreed rule/test-design baseline and implement priority semantic, structural, traceability, and coverage checks. Stand up the ISO8583 specification-pack skeleton and implement shared intake/canonicalization plus ISO schema/structure checks for the agreed first scope. | All four; rotate oracle-definition, implementation, and independent verification assignments. | ATL105 runnable control set; ISO8583 pack loading and bounded automated checks; unit/regression evidence; traceability from rules to validator controls. |
| **21-27 Nov** | Integrate frozen or agreed AI deliveries for both specifications. Recalculate coverage independently; run schema, ID, link/orphan, provenance, rule applicability, and supported semantic checks. Triage discrepancies. Select the manual-review denominator and sample using the method in section 6. | All four; rotate intake, automation, analysis, and sample-planning leads. | Reproducible per-spec run reports; discrepancy register; frozen eligible-flow population and auditable sample selection. |
| **28 Nov-4 Dec** | Execute manual end-to-end review of the selected AI-generated flows; cover high-risk and representative conditions. Fix validator defects, not AI artifacts. Add regression cases for confirmed defects and edge cases. Obtain SME/business decisions for high-impact open items. | All four; rotate sample-review, validator-fix, and peer-review assignments while keeping authorship/review distinct. | Completed sample review records; defect/root-cause log; regression tests; recorded SME decisions or explicit blockers. |
| **5-11 Dec** | Re-run both specifications end-to-end on frozen deliveries; verify all in-scope controls, coverage denominators, report reproducibility, review statuses, and previously failed cases. Complete cross-review and resolve release-blocking validator defects. | All four; rotate integrated-run and independent-evidence-check leads. | Clean/reconciled final reports; test-suite results; defect disposition; no silent/unknown outcomes for in-scope controls. |
| **12-15 Dec** | Final acceptance review, document limitations and carryovers, publish handover and Milestone 2 outcome. Decide whether each specification is accepted for its agreed validation scope, conditionally accepted, or blocked. | All four contribute evidence; Test Lead and authorized stakeholders approve. | Signed milestone report, scope/results/limitations, evidence index, open-decision owners and dates, next-milestone recommendation. |

## 6. Manual end-to-end AI-flow review

The manual check is an independent quality review of AI-generated flows, not a substitute for automated coverage or an assertion of statistical confidence.

1. **Freeze the population.** R4 records the delivery ID, specification/version, file hashes, intake time, and any exclusions. A flow is eligible only if it can be traced from an AI-generated BR or declared source requirement through scenario and case to its associated test data. Keep incomplete/orphaned flows in the defect population; do not silently exclude them from reporting.
2. **Set the denominator.** Count unique eligible end-to-end flows after deduplicating exact duplicates within the frozen delivery. Report counts separately by specification and by in-scope message/segment or flow family. Report excluded/incomplete flows and reasons separately.
3. **Choose a 7.5% target per specification.** For each specification with at least 10 eligible unique flows, use a count within 5-10%: start with `ceil(0.075 x eligible unique flows)`, cap it at `floor(0.10 x eligible unique flows)`, and ensure it is at least `ceil(0.05 x eligible unique flows)`. If integer rounding leaves no count in the 5-10% band, seek Test Lead approval for a documented exception before reviewing; report the actual percentage. For fewer than 10 eligible flows, the requested percentage band is not achievable with a non-zero whole-flow sample, so the sample gate requires an approved exception or is blocked. A zero or unknown denominator is blocked, never reported as 100%.
4. **Stratify by risk and coverage.** Include both specifications and, where present, high-impact transaction/message families, conditional rules, positive and negative cases, boundary cases, and source/version changes. Prioritize flows with ambiguous claims, complex links, or high-risk data. Record the selection method, seed or deterministic selection rule, and per-stratum counts.
5. **Protect the cap.** If minimum stratum representation would push the selection above 10%, ask the Test Lead to approve a revised sample or record uncovered strata as a limitation. Do not silently exceed the agreed range.
6. **Review independently.** Compare each flow's applicability, BR meaning, source anchor, scenario, expected result, test data, and traceability against the Test Solution oracle and the specification. Record reviewer, evidence, result, defect severity, and whether SME adjudication is required.
7. **Report honestly.** Publish numerator, denominator, percentage, selection method, distribution, findings, and limitations. A 5-10% sample is review evidence, not proof that all unreviewed AI flows are correct.

## 7. Validator capabilities and acceptance criteria

The Test Solution is accepted for a specification only against its agreed and documented scope. A validator pass means the checked control passed; it does not mean the AI package is universally approved.

### Common required capabilities

- **Intake and provenance:** Preserve the original AI delivery, identify producer/package/version, specification/version, generation metadata when supplied, and source hashes; fail or explicitly hold unsupported formats.
- **Canonicalization and schema:** Map supported producer formats without silent information loss; validate required structure, types, identifiers, and specification-specific payload shapes.
- **Independent oracle:** Keep source-derived Test Solution rules and BRs distinct from AI output; every in-scope rule carries source evidence and a status.
- **Traceability:** Validate BR/rule-to-scenario-to-case-to-data links, IDs, duplicates, orphans, unresolved references, and reciprocal link consistency where required.
- **Coverage:** Recompute coverage from eligible artifacts and the independent denominator; compare with producer claims and report gaps without granting credit for placeholders or heuristic matches.
- **Specification checks:** Run only source-backed, implemented checks. Distinguish a bounded format/shape predicate from full semantic, lifecycle, host, or business validation.
- **Review state:** Emit explicit pass/fail/review-required/blocked outcomes; preserve conflicts, missing evidence, and SME dependencies.
- **Evidence and regression:** Produce deterministic, versioned reports and tests demonstrating valid controls, targeted invalid mutations, boundary conditions, and unresolved cases.

### ATL105 exit criteria

- ATL105 source/version, rule denominator, chain scope, and report snapshots are reconciled or explicitly bounded by an approved scope statement.
- Every in-scope rule has a stable source anchor and a recorded status; gaps and SME dependencies are visible.
- The agreed ATL105 AI delivery can be ingested and assessed by the common validator and ATL105 rule pack.
- Structural, traceability, supported semantic, and coverage controls have regression evidence; reports distinguish executable evidence from placeholders and review-only evidence.
- The risk-based manual sample is completed and documented, or the gate is explicitly blocked with an owner and reason.

### ISO8583 exit criteria

- Both supplied source files are inventoried and hash-identified. Their applicable versions, relationship, precedence, and any contradictions are documented; no rule is merged across them without an evidence-backed decision.
- The agreed ISO8583 scope has an independently authored, source-anchored rule/BR catalogue with applicability and unresolved questions recorded.
- A versioned ISO8583 pack is accepted by the shared validator and supports the agreed AI artifact format and scope.
- Automated structural, traceability, coverage, and source-supported semantic checks have regression evidence, including targeted positive, negative, and boundary mutations where the source defines an oracle.
- The ISO8583 AI delivery, manual sample, limitations, and SME dependencies are reported with the same evidence discipline as ATL105.

### Milestone exit criteria by 15 December

- Both specification scopes have a dated, hash-backed baseline and an agreed denominator or an explicit `NOT_CALCULABLE` limitation.
- All agreed in-scope automated controls execute reproducibly; no in-scope artifact is silently dropped or converted into a success-shaped fallback.
- Every failure, unresolved semantic result, unsupported format, and approval dependency is reported and assigned. No unresolved item is promoted to pass.
- Automated regression suites pass, or any remaining failure is documented with impact, owner, and acceptance decision.
- The 5-10% manual-flow review is complete and its sample population and results are auditable.
- The Test Lead and authorized stakeholders record acceptance, conditional acceptance, or blocked status separately for ATL105 and ISO8583.

## 8. Governance, reporting, and change control

- **Daily:** Each resource updates the shared task/decision/defect register with status, evidence link, next action, and blocker.
- **Weekly:** Test Lead reviews scope, progress against dated exits, control/test results, sample readiness, SME decisions, and risks. Publish one concise status per specification.
- **Change control:** Any scope, source-version, denominator, or sample-method change is recorded with reason, impact, approver, and baseline revision. Preserve previous evidence.
- **Defects:** Separate AI artifact defects from Test Solution validator defects and source/SME questions. Correct the validator when its logic is wrong; do not edit frozen AI input during assessment.
- **Release decision:** The authorized stakeholders accept the stated scope and limitations. Technical completion alone does not authorize business semantics or production use.

## 9. Risks and dependencies

| Risk/dependency | Impact | Mitigation / decision |
|---|---|---|
| ATL105 reports use different denominators and current element-chain evidence has 0 executable non-placeholder chains. | A false completion claim or insufficient independent oracle for semantic AI assessment. | Reconcile scope and reports in week 1; keep placeholder evidence separate; prioritize source-backed executable checks and report any unapproved rules as review-required. |
| ISO8583 2021 addendum applicability or precedence against Version 2026-3 is unclear. | Conflicting or incorrectly combined rule baseline. | R1 records source hashes and section-level conflicts; obtain an authorized decision before merging or applying rules. |
| SME/TBA or authorized business reviewer is unavailable. | Ambiguous business rules remain unapproved and may block semantic acceptance. | Submit prioritized questions early; continue bounded technical validation; keep decisions pending and acceptance conditional/blocked. |
| AI deliveries, schema details, or representative end-to-end flows arrive late or change after freeze. | Reduced automation/review time and an unstable denominator. | Agree intake format and delivery dates by 6 November; hash/freeze each run; treat later deliveries as a new run and assess schedule impact. |
| Four cross-trained resources are not fully available or are assigned without rotating ownership/review. | Planned parallel work, independent peer review, and sampling may not fit the window or may become dependent on one person. | Confirm capacity and cross-training at kickoff; rotate work-package leads and reviewers; replan scope at the 6 November gate; retain only approved, measurable scope rather than hiding incomplete work. |
| Source clauses lack a deterministic oracle for a desired validator check. | Automation could encode an unsupported assumption. | Do not implement it as a pass/fail rule; record `REVIEW_REQUIRED` and raise a decision request. |

## 10. Decisions required at kickoff

1. Name the four cross-trained resources, Test Lead, AI delivery contacts, and authorized SME/TBA or delegate for each specification; agree the rotating lead/reviewer assignment method.
2. Confirm whether the ISO8583 deliverable is the full supplied specifications or a bounded, prioritized subset. If full scope is required, confirm the expected AI package, message/transaction inventory, and acceptance denominator by 6 November.
3. Confirm the effective relationship and precedence of the ISO8583 Global TA Addendum 2021-1a relative to Global Version 2026-3.
4. Confirm AI artifact formats, frozen delivery dates, and expected end-to-end flow structure for both specifications.
5. Approve the manual sampling policy: 7.5% target, 5% floor, 10% cap, and escalation where risk-stratum representation cannot fit within the cap.
6. Approve the acceptance vocabulary and authorize the Test Lead to report `CONDITIONAL` or `BLOCKED` rather than assert completion where SME decisions, inputs, or evidence are missing.
