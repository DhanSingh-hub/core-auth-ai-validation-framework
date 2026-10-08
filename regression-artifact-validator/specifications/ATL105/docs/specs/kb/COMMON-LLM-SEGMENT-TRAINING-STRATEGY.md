# ATL105 Segment Training Handbook

**Specification:** BUYPASS ATL105 2026-3
**Applies to:** Every ATL105 data segment, numbered segment, and download segment (`DL1`-`DL8`)
**System under test:** AI Solution artifacts
**Independent oracle:** Core Auth Test Solution rules derived from the specification

> **This is the single training handbook.** Every team member, agent, or automation that trains a segment follows this document and nothing else. It replaces the two `SEGMENT-100-TRAINING-METHODOLOGY.md` files (merged into it on 2026-09-29). The 2026-09-23 improvement-plan pack is archived; see [Handbook History](#handbook-history).

## Contents

1. [How to Train a Segment (Start Here)](#how-to-train-a-segment-start-here)
2. Governance: [Purpose](#purpose), [Standard Ownership](#standard-ownership-and-mandatory-use), [Automatic Update Rule](#automatic-update-rule), [SME Decision Persistence](#sme-decision-persistence), [Communication Register](#communication-register), [Required Training Record](#required-training-record), [Controlled Improvement](#controlled-improvement), [Matching Responsibility](#matching-responsibility)
3. Method: [Artifact Chain](#common-artifact-chain), [LLM Training Rules](#common-llm-training-rules), [Source Anchors](#canonical-source-anchors), [Data Sections](#request-data-section-convention), [BR Taxonomy](#canonical-br-taxonomy), [Nine-Phase Strategy](#common-nine-phase-strategy), [Independent Review Process](#independent-review-process)
4. Build: [8-Item Framework](#test-solution-implementation-8-item-framework), [Field-Name Conformance](#segment-package-field-name-conformance), [Coverage Denominator](#coverage-denominator)
5. Deliver: [Segment Addendum](#segment-specific-training-addendum), [Training Outputs](#common-training-outputs), [Completion Gate](#completion-gate), [Sign-Off Checklist](#sign-off-checklist)
6. Learn: [Lessons Learned](#lessons-learned), [Segment 100 Reference Implementation](#segment-100-reference-implementation), [Handbook History](#handbook-history)

## How to Train a Segment (Start Here)

1. Read this handbook end to end once. The governance sections are mandatory, not background.
2. Check out the segment branch (`Segment_<NNN>`) from an up-to-date `Develop`. Run the test suite and record any failure that already exists (see [L10](#lessons-learned)).
3. Open `kb/segment-<NNN>/` and the segment's entry in `specifications/ATL105/training-status.json` to see which gates are already passed.
4. Work through the [Nine-Phase Strategy](#common-nine-phase-strategy) in order. Each phase is a gate in `training-status.json`; do not skip one. For lifecycle or transaction-context training, complete the trainer prompts in the [Reusable Segment Training Questionnaire](../../test-validation-strategy/SEGMENT-TRAINING-QUESTIONNAIRE.md) with a Test Team trainer; unresolved answers remain `REVIEW_REQUIRED`.
5. Build the Test Solution code with the [8-Item Framework](#test-solution-implementation-8-item-framework).
6. Write anything that is specific to the segment in the segment's addendum (its `README.md`), not in this handbook. Record every open question in the [Communication Register](#communication-register).
7. Pass the [Completion Gate](#completion-gate) and the [Sign-Off Checklist](#sign-off-checklist), then commit to the segment branch and merge to `Develop`.
8. If you learn something that would help the next tester, add it to [Lessons Learned](#lessons-learned) in the same change (the [Automatic Update Rule](#automatic-update-rule)).

## Purpose

This is the common training and validation strategy for teaching an LLM to produce reliable BRs, Test Scenarios, Test Cases, and request Test Data JSON for any ATL105 segment.

Each segment uses this common strategy plus a segment-specific training addendum. The common strategy defines the behavior that must never change between segments. The addendum supplies the segment's source references, field rules, message context, examples, exceptions, and unresolved SME/TBA questions.

## Standard Ownership and Mandatory Use

This document is the standard process for training the Test Solution and validating AI Solution output. Every Test Team member, agent, or automation that creates, reviews, updates, or teaches a Test Solution rule must follow the phases and gates below. A segment addendum may add stricter controls, but it may not remove or bypass a common phase or gate.

The Test Solution remains independent from the AI Solution. AI artifacts may be inspected and compared, but they must not be copied into the Test Solution or treated as Test Solution training truth. A claim without authoritative evidence remains `REVIEW_REQUIRED`.

The Test Solution owns matching and coverage decisions. AI-reported counts, confidence, approval labels, and coverage percentages are inputs under test, not acceptance evidence. Only an evidence-supported business-equivalent match may be `CONFIRMED`; heuristic, partial, conflicting, or unresolved matches remain `REVIEW_REQUIRED`.

Coverage dashboards must distinguish producer-internal links, physical payload presence and expected-code declarations from confirmed rule coverage. Group segment views by declared attribution without adding overlapping counts into a run total. Keep exact transaction-target labels separate until a source-backed alias mapping exists. A response-code declaration cannot establish its message-family meaning or validated response behavior. Retain unassigned/undeclared rows, explicit denominators, input hashes and unavailable semantic results; never interpret a linked record or a rendered percentage as approval.

For TC-to-BR semantic assessment, verify the supported interpretation before judging objectives, then inspect intended negative mutations, source-backed expected outcomes and applicable payload validators. A shared BR ID or preserved invalid-looking value is not proof of semantic alignment or effectiveness. Use valid controls and isolated source-supported mutations; report unrelated confounders separately. Bounded source predicates must state what they do not prove (for example six-digit Sequence Number format is not lifecycle correctness). A deterministic first batch is not a statistical sample; missing composite/absence mappings or processor oracles stay `NOT_ASSESSED` / `REVIEW_REQUIRED`.

Distinguish resolved preservation from certified negative objectives. Exact composite agreement and source-required physical absence can be checked without promoting a whole message. Do not infer a universal enumeration from an application-dependent field. Keep missing flow BR links unchanged; sequence agreement is candidate context evidence only. A review-status response matrix that omits a code/family pair can disagree with a broader source clause; record that concern, not automatic invalidity. Predicate-scoped valid controls must never be presented as valid full-message fixtures or host-response oracles.

An agent or automation may perform Phases 1-4 (Source Inventory through Independent BR Derivation) directly from the ATL105 specification text without human involvement, as long as it reads only the specification and cites exact section/page/line evidence; this does not require human assistance. It may not certify its own output: a rule produced this way is `DRAFT_REVIEW_REQUIRED` until an SME/TBA (or formally delegated business approver) reviews and signs off, per the Required Training Record below. Segment training status must reflect this distinction (`IN_PROGRESS` with a `sme-tba-certification-pending` blocker, not `TRAINED_FOR_INTAKE`).

## Automatic Update Rule

Assessment execution readiness always applies the strict execution contract, regardless of a producer's `strictExecutionContract` flag. BRs, scenarios and cases must declare `EXECUTION_READY`; TDs must declare `EXECUTABLE` and contain request/response envelopes. Future availability, unresolved outcomes, missing metadata, failed validators, empty denominators or incomplete strategy maps block readiness. Structural full-chain coverage may still be 100% while readiness is false. These checks validate declared states and contract evidence, not reviewer authenticity or SME approval; independent source/approval binding remains a separate gate.

This document must be updated in the same change set whenever a training-relevant decision is made, not on request. This applies to (but is not limited to): adding or changing a phase or gate, adding a new artifact type (for example a field-alias crosswalk), changing required package field names or contract shapes, changing coverage-denominator or matching policy, or discovering a segment-package conformance defect. The update happens automatically as part of doing the work; do not wait for an explicit instruction to "update the training strategy."

For every change to AI-output validation code, schemas, adapters, canonical matching, coverage denominators/calculations, statuses, or report semantics, update this handbook and the [AI Solution Output Validation Plan](../../test-validation-strategy/AI-Solution-Output-Validation-Plan.md) in the same change set. Update the affected `AIV-*` control and implementation/test evidence in [AI Artifact Validation Controls](../../test-validation-strategy/AI-ARTIFACT-VALIDATION-CONTROLS.md), add or revise regression tests for repeatable behavior, and refresh any affected per-delivery validation result. A validation implementation change without synchronized strategy, handbook, control-evidence, and regression-test updates is incomplete.

Every canonical TD record must declare readiness as `EXECUTABLE`, `EXTERNAL_FIXTURE_REQUIRED`, or `REVIEW_REQUIRED`. Missing or unknown readiness fails appendix/evidence consistency validation. Keep readiness (fixture usability) separate from `expectedValidation` (expected pass/fail result); unresolved dependencies stay `REVIEW_REQUIRED` and are not counted as executable evidence.

Keep unresolved or blocked BRs in a separately identified blocked/review collection with a reason and required evidence. Do not place an unlinked blocked BR in the active canonical `businessRequirements` chain or count it as covered; do not silently discard it. When its blocker is resolved, promote it into the active chain with source anchor and complete links, then update the active BR count and regression test.

When aggregating existing Test Solution evidence, standalone `testDataId`/`testCaseId`/`scenarioId` inventories may be normalized into canonical `testData[]` plus review-required TS/TC placeholders. This normalization may not fabricate a BR, source anchor, execution status, or segment identity: derive the segment only from an unambiguous payload `segmentType`; otherwise retain `CONTEXT_REVIEW_REQUIRED` and preserve the original file as provenance.

Knowledge-base verification must run against the ATL105 2026-3 extracted specification text. The automated gate must report catalog count, rule count, version consistency, complete anchors, duplicate anchors, and source-section presence. Passing this gate proves structural/source-address integrity, not business semantic correctness; composite section labels and appendix/title aliases remain `REVIEW_REQUIRED` until SME/TBA confirms the source mapping.

Knowledge-base BR coverage must preserve composite evidence as multiple `sourceEvidenceSections`, resolve only unambiguous title aliases (for example `Totals Request` to ATL105 11.4.1.1), and retain appendix references as explicit evidence. The independent BR package may be generated before TS/TC/TD derivation, but it remains `REVIEW_REQUIRED` until semantic review and the complete BR -> TS -> TC -> request Test Data chain are present.

For source-backed rule checks, use a versioned, curated assertion manifest that records a contextual value claim and its literal ATL105 phrase. `GenerateAtl105SourceBackedRuleGate` currently checks the Element 83 entry, its one-byte shape, the cited catalog rule, and the approval matrix; it records an exact source quote, source line and SHA-256 fingerprint. It separately reports citation resolution, source-matched assertions, draft cases and SME approval across all catalog rules. A source mismatch stops case derivation for that assertion. Draft positive and invalid-length examples remain `SOURCE_DERIVED_REVIEW_REQUIRED` and must not be inserted into executable TS/TC/TD packages or interpreted as an SME decision. Uncurated or ambiguous rules remain in the review queue. This is evidence within the existing phases, not an additional certification gate.

Every catalog rule also receives a reference-only source-location row. The locator skips dotted table-of-contents entries and declines broad chapter, appendix and composite labels when a unique body heading cannot be established. Its heading, line, excerpt preview and source fingerprint help a reviewer find evidence; `BODY_LOCATED_REFERENCE_ONLY` never counts as an assertion match, a test case or semantic approval. Source headings that change must lose their prior location until rechecked.

For rules spanning multiple sections, the curated manifest must cite both the code-value row and a literal context phrase in the rule's own section. The gate bounds that phrase to the cited section body: matching text elsewhere in the specification cannot qualify. Segment 118 Site Configuration response codes and the Prompt 904 host-discount trigger are `PARTIAL_RULE_CONTEXT` examples. Their draft cases address code/context and one-byte length only; they do not prove Block Number lifecycle, complete Host Discount Data behavior, or transaction-wide applicability. Both assertions and draft cases retain review-required status.

Element 84 width claims use the separate `element-84-width-assertions.json` manifest and `GenerateAtl105Element84WidthGate`. It compares the seven four-digit exceptions in both Chapter 13 lists with the manifest, then checks each segment's own layout row within its section. Its Segment 101, 104, 108, 109, 113, 114, 115, 118, 130, 131, 132, 135, and 136 examples test digit width only, not the calculated Segment Length value, delimiters, or serialization. Segment 135's length-counting convention remains an open SME question despite its explicit three-digit field row. A changed exception list or layout row blocks affected draft cases; no assertion or case becomes SME-approved or execution-ready through this gate.

Element 4 Address Line 2 has a separate, bounded `element-4-layout-assertions.json` manifest and `GenerateAtl105Element4LayoutGate`. The gate checks the 21-character shape and all five positional rows within Element 4's Chapter 13 definition for `SEGDL1-R-010`. Its two review-only examples exercise separator placement, not assigned city names, Appendix D state-code membership, assigned ZIP codes, DL1 parsing, or approval. A missing row or changed length withdraws the whole partial claim and its drafts.

The curated `chapter-13-rule-evidence.json` register and `GenerateAtl105Chapter13RuleEvidence` check existing rule claims only within each named Chapter 13 element definition. `SEGDL1-R-011` requires matched Element 54 phone-shape and Element 98 store-mask/range phrases together; mutation of either definition withdraws its partial backing. `SEGDL2-R-006` checks Element 82's one-digit shape, 1-3 valid-code row and DL2 purpose; it does not prove that both primary and secondary blocks contain the field. `SEGDL2-R-007` checks Element 75's numeric 18-byte maximum, Element 28's fixed `1`, and Element 27's `A`/`F` terminator meanings in their own bounded definitions. `SEGDL2-R-005` checks Element 1's paired-absence condition and optional `B` pauses against Element 66's `B` valid-code row; it does not resolve optional-field parsing. `SEGDL3-R-009` checks Element 23's conditional instruction to start automatic settlement 30 minutes before cut time if the clerk has not completed settlement; it does not observe device timing. These quoted clauses do not validate serialized messages, actual device behavior, or complete segment semantics. This gate creates no draft cases and never grants approval.

`GenerateAtl105FixedSegmentTypeGate` checks a separate, narrow Element 85 pattern: a catalog title must claim a fixed three-digit Segment Type, its source anchor must name the same element, segment, and one Section 12 body, and that section's Field No. 1 must contain exactly one matching numeric `Fixed value`. A missing or conflicting value is `SOURCE_MISMATCH_REVIEW_REQUIRED`. `GenerateAtl105Segment110FieldEvidence` verifies six curated §12.9 claims against the bounded Check Data Segment body: its maximum-length statement and selected field-number/element-number/name/length/R-O rows. It emits no draft cases; row evidence alone does not prove conditional triggers, Check Type values, calendar dates, MICR semantics or serialization. `GenerateAtl105TotalsPromptCodeEvidence` checks the existing Segment 105 and 119 rules against their own bounded §12.6 and §12.17 Field 5 rows. Repeated identical PDF table headers are allowed; a conflicting segment number or fixed value is not. It proves only the `990` Prompt Code in that row and creates no cases. `GenerateAtl105SourceEvidenceProgress` reruns all seven gates against the same catalog and approval matrix and counts distinct rule IDs. Its 49-segment backlog table reconciles to the catalog denominator; it is a prioritization view, not a semantic pass. All gates yield partial, non-executable evidence only; source location, matching phrases, and draft examples cannot certify a whole BR or bypass SME sign-off. The remaining uncurated rules must be checked against their own contextual source claims rather than promoted by shared headings or similar titles.

## SME Decision Persistence

Route each SME outcome to its subject register: AI-to-Test crosswalk decisions go in `test-output/test-solution-independent-review/semantic-br-decision-register.json`; AI-only BR triage goes in `test-output/ai-solution-independent-review/ai-only-sme-decision-register.json`. Keep both registers append-only. Resolve an existing pending subject by appending a revision with an explicit `supersedesDecisionId`; never edit or delete the pending event. Decisions must not modify immutable AI input files.

Use the [SME Decision and Promotion Workflow](../../test-validation-strategy/SME-DECISION-AND-PROMOTION-WORKFLOW.md) for the structured decision input and promotion gates. `ValidateSmeDecisionRegister` checks unique event IDs, linear revisions, reviewer evidence, and outcome-specific requirements. `NEW_RULE` additionally requires independently authored rule text, an explicit independence attestation, and a segment-matching ATL105 source anchor. Promotion uses only each subject's latest effective decision; it creates a BR candidate for chain derivation, not execution-ready coverage. Heuristic similarity, AI confidence, or a generated chain never substitutes for SME/TBA approval.

## Communication Register

Every question, discussion topic, and piece of feedback is recorded once, in [atl105-communication-register.json](../../../registers/atl105-communication-register.json). Do not keep a separate list in a report, README, or catalog.

| Channel | ID | Use it for |
|---|---|---|
| `SME_QUERY` | `SEG<NNN>-SME-<nnn>` | A question only the SME/TBA can answer, such as an ambiguous or conflicting specification rule |
| `TEST_TEAM` | `TT-<nnnn>` | A decision or task for the Test Team |
| `AI_DEV_DISCUSSION` | `AID-<nnnn>` | A topic to agree with the AI developers before either side changes |
| `AI_FEEDBACK` | `AIF-<nnnn>` | A correction the AI Solution Team must make in its next delivery |

Register contents on 2026-09-29. For current figures, see the [register index](../../../registers/views/index.md).

| Channel | IDs | Count | Status |
|---|---|---|---|
| Queries to the SME | `SEG<NNN>-SME-nnn` | 203 | 169 open, 3 reopened, 29 resolved, 2 deferred |
| Test team discussion | `TT-nnnn` | 15 | 13 open, 2 resolved |
| Open topics with the AI developers | `AID-nnnn` | 4 | 4 open |
| Feedback to the AI team | `AIF-nnnn` | 15 | 15 open |

How to use it:

1. Add or update the item in the register JSON. Record the context, the test impact, the evidence, and any related item IDs. Statuses are `OPEN`, `IN_DISCUSSION`, `ANSWERED`, `REOPENED`, `RESOLVED`, `DEFERRED`, `WITHDRAWN`, and `SUPERSEDED`. A closed item needs a resolution, `resolvedBy`, and `resolvedOn`. When you reopen an item, add a `history` entry instead of overwriting the earlier answer.
2. Regenerate the views with `GenerateCommunicationRegisterViews`. It writes the per-segment `kb/segment-<NNN>/segment-<NNN>-sme-tba-input-register.md` files and the channel views in [registers/views/](../../../registers/views/index.md). The views are generated: never edit them by hand.
3. Run `CommunicationRegisterTest`. It fails if an ID is duplicated or malformed, a related item does not exist, a closed item has no resolution, a catalog and the register disagree, or a view is out of date.

An SME answer that changes coverage is still recorded in the [SME decision register](#sme-decision-persistence). The communication register records that the question was answered; the decision register records the coverage decision.

## Required Training Record

For Appendix I table training, use the [Appendix I addendum](appendix-i/README.md)
alongside Segment 111. Preserve the distinction between Element 111 (selector),
Element 112 (length), Element 113 (data), and the Segment 111 envelope. Inventory
actual source-defined IDs rather than assuming every ID up to the largest value
exists. Nested selector occurrences and source navigation spans are not semantic
rules, completed BR/TS/TC/TD chains, or approval. Read preceding notes and page
continuations, cross-check Chapter 13 and Section 12.10, and keep conflicting
table-specific attributes review-gated with a central register reference.
The bounded `AppendixILogicalDataValidator` implementation checks selected
Tables 001-009 representation predicates independently of producer assertions.
Its PASS is a predicate result, not a wire/transaction/AI-match or readiness
decision; missing context remains REVIEW_REQUIRED and unsupported scope remains
NOT_ASSERTABLE. Explicitly separate these predicate tests from complete,
source-reviewed BR/TS/TC/TD chains.
The companion wire predicate validates common ASCII Segment 111 fragments only,
with separate unsupported-encoding and conflicted-framing gates. First-batch
candidate chains retain REVIEW_REQUIRED and never count fragment payloads as
complete requests, approved fixtures, AI-equivalent artifacts, or executed cases.
Remaining-layout examples use explicit logical-fragment records with proposed
predicate outcomes. They need implementation and full-message assembly before
execution claims. Hash and join immutable producer artifacts for intake, report
physical gaps independently, and never treat a linked ID or confidence-gated
producer oracle as semantic acceptance.
Full-message work is offline first, not host acceptance. Inspect referenced
examples before adopting them as seeds: `AppendixIFullMessageSourceInspectionTest`
reproduces Appendix B's Segment 100 length conflict (078 declared, 82 measured).
Use Section 12.1's calculated length for new synthetic candidates while retaining
`SEG111-SME-121`; a corrected length is not proof of a valid complete request.
`GenerateAppendixIScopedTraining` produces canonical review-only logical chains
for selected Tables 010-015 and 017. Its scoped source-anchor identities prevent
partial predicates from being presented as complete source-rule equivalence.
It records real logical findings and targeted mutation catches separately from
full-request execution. Physical TD records remain REVIEW_REQUIRED and their
response envelope explicitly says NOT_EXECUTED. The 302-entry draft-rule ledger
is not a certified source-completeness denominator. Track these nine-phase
gates under `training-status.json`'s `appendixTraining.I`, outside the numbered
segment catalog summary. AI comparison is user-deferred and merging the next
Appendix I checkpoint to Develop requires explicit user approval.

For every new or changed rule, the trainer must record:

1. Authoritative source, version, page, and section.
2. Segment, element, rule, applicability, and ambiguity status.
3. Canonical source anchor created or reused before the BR.
4. Condition, behavior, valid and invalid representations, expected result, and exception boundary.
5. BR -> TS -> TC -> request Test Data traceability.
6. Positive, negative, boundary, conditional, lifecycle, or serialization evidence as applicable.
7. Validation result, reviewer, date, and unresolved review items.
8. For LLM training supplied by a Test Team member, the completed trainer questionnaire, its source evidence, decisions, and unresolved questions.

Training supplied by a Test Team member to an assistant follows the same record. The assistant must distinguish source fact, derived interpretation, and open question; it must not silently convert an instruction into an approved rule.

For parallel Appendix G transaction-type training, use the shared [transaction-type handoff ledger](segment-100/transaction-type-training/README.md). Each transaction code has its own task file under `segment-100/transaction-type-training/codes/`; claim ownership with user, machine, branch, and timestamp before working. Record each substantive action in that code's `workLog` and add an immutable, uniquely named event under `segment-100/transaction-type-training/events/`. This shared Git-backed record is the handoff source across machines; baseline applicability entries are not completion evidence.

## Controlled Improvement

This standard may be improved when evidence shows that a gate is incomplete, ambiguous, inefficient, or unable to detect a defect. Proposed changes must identify the affected phase or gate, the observed evidence, the risk addressed, the compatibility impact, and the regression tests or examples required. The change becomes effective only after Test Team review and documentation in this common strategy; existing segment addenda must then be checked for alignment.

## Matching Responsibility

Matching AI output to the Test Solution is the responsibility of the Test Solution validation framework. The AI Solution is an input producer and may provide its own local identifiers and claimed mappings, but it must not determine whether its output is covered.

The framework must:

- normalize both producers into the canonical artifact schema;
- compare shared `sourceAnchors` using deterministic canonical keys;
- confirm a match only when the anchor and business rule are equivalent;
- classify partial, ambiguous, conflicting, or heuristic candidates as `REVIEW_REQUIRED`;
- report `AI_ONLY`, `TEST_ONLY`, duplicate-source, malformed, and unresolved records;
- preserve both producer-local IDs and the evidence supporting every disposition.

Text similarity, shared field numbers, matching terminology, and AI-provided confidence may identify candidates for review, but may never produce `CONFIRMED` coverage.

## Common Artifact Chain

```text
ATL105 source
  -> segment knowledge model
  -> independent source anchors
  -> Business Requirements
  -> Test Scenarios
  -> Test Cases
  -> request Test Data JSON
  -> traceability and coverage metrics
  -> Test Solution validation
```

The LLM is trained to produce candidate artifacts. The Test Solution independently validates those artifacts and remains the authority for structural, semantic, traceability, and coverage decisions.

For canonical chain counting, `testData[].testCaseIds` is the required TC -> TD edge. `coversBr` may be preserved as a BR-level candidate hint, but it does not identify which TC the data exercises and must not enter canonical `testData[]` coverage. Preserve unlinked records with provenance outside the canonical chain until an author supplies a real TC link. Missing or unknown readiness is `REVIEW_REQUIRED`; a producer `EXECUTABLE` label does not establish a linked or validated chain.

Appendix O/R/S/T/Y packages can be adapted from `sourceAnchor`, `covers`, and `scenarioId` into canonical anchor and link fields. Keep the producer's original status as metadata and set the normalized status to `REVIEW_REQUIRED`; automation must not treat `COVERED` or `PARTIALLY_COVERED` as approval.

A BR-only TD candidate may carry a provenance-only BR -> TS -> TC path when those links are explicit in the normalized source package. This does not become a `testData[].testCaseIds` edge or canonical TD until a payload and expected result are authored.

Synthetic appendix fixture drafts may be generated from source-defined structural examples without waiting for an approval round, but they remain `expectedValidation=REVIEW` and `readiness=REVIEW_REQUIRED` until the applicable behavior is independently validated. Do not synthesize production/network cryptograms or configuration-dependent key fixtures.

## Common LLM Training Rules

The LLM shall:

- Emit the declared producer/schema/contract version, generator version, specification version, and run ID. Do not silently change JSON/workbook shape between runs; publish a versioned schema/adapter contract for any breaking format change.
- Preserve stable local IDs within a run, source fields, approval claims, flags, and links. Never present an automated batch as SME approval; producer approval/verdict fields remain claims for independent validation.
- Include all generated records and declare counts and link totals from the serialized output. If a record cannot be represented in the supported contract, emit an explicit unsupported/review disposition rather than silently omitting or reshaping it.
- Use the exact specification version and source page/section for every rule.
- Preserve segment identity, message family, request/response role, and applicability.
- Distinguish required, optional, conditional, prohibited, and unknown behavior.
- Never infer a universal rule from one transaction, card type, network, or example.
- Keep companion-segment and mutually exclusive-segment rules explicit.
- Separate source-confirmed behavior from derived interpretation and SME/TBA assumptions.
- Generate request Test Data JSON only with synthetic or approved masked values.
- Preserve lifecycle relationships between original and follow-up requests.
- Avoid treating AI confidence, generated counts, or AI coverage as approval.
- Mark unsupported or ambiguous behavior for review instead of inventing values.

The Test Solution intake must distinguish unsupported format, normalization failure, structural failure, semantic mismatch, baseline omission, and incomplete BR -> TS -> TC -> TD chain. Do not collapse these states into `MISSING`, `AI_ONLY`, or a zero-percent coverage result. A passing parser or validator only proves the checks it actually performs; source semantics and required approval gates remain separate.

## Canonical Source Anchors

Every rule in a segment rule catalog (`kb/segment-<NNN>/coverage/segment-<NNN>-rule-catalog.json`) carries a structured `sourceAnchor`:

```json
{ "specification": "ATL105", "version": "2026-3", "section": "11.1.1", "segment": "100", "rule": "segment-100-required-once" }
```

Add `element` when the rule is about a single element. The anchor is created before the BR (Phase 1), is the only key used for AI-to-Test matching, and must resolve to a section present in the extracted ATL105 2026-3 text. Catalog IDs follow `ATL105-SEG<NNN>-RULE-CATALOG-001` and rule IDs follow `SEG<NNN>-R-<nnn>`.

If the rule depends on an interpretation that the specification does not settle, mark it `PROVISIONAL`, keep it `REVIEW_REQUIRED`, and raise an `SME_QUERY` in the [Communication Register](#communication-register). In the catalog's `provisionalItems`, record only `id`, `status`, `impacts`, `blocks`, and `registerId`. The question and answer are written only in the register, and the catalog status must match the register status.

## Request Data Section Convention

Decided by the Test Team on 2026-09-29 (TT-0014) after cross-checking every Chapter 11 layout and Chapter 12 placement statement in the PDF. In every **request** that uses data sections:

| Data section | Contains |
|---|---|
| 1 | Element 55 (Message Format Version Identifier) and Element 63 (Number of Segments) |
| 2 | Segment 100 only, in Field No. 3, when the message has it |
| 3 | Every other segment, starting at Field No. 3 when there is no Segment 100 and Field No. 4 when there is |

Seven request layouts follow this (11.1.1, 11.2.1, 11.3.1, 11.4.1.1, 11.4.1.2, 11.7.6.1 and 11.8.1), as do the Chapter 12 placement statements. The specification labels four cases differently; they are SME queries and do not change the convention:

| Message | Specification label | Query |
|---|---|---|
| Electronic Mail Request | Segment 109 in Data Section 2 (11.5.1, 12.8) | SEG109-SME-012 |
| CA Public Key File Load Request | 11.9.1 text and table contradict each other | SEG132-SME-006 |
| TransArmor Key Load and Communications Test | The Element 63 processing rule puts Segment 116 and Element 120 in Data Section 2 | SEG116-SME-007 |

The convention does not apply to responses, whose Data Section 1 holds response elements (the Financial and EMV responses put Segments 112, 115, 120, 131 and 134 in Data Section 2), or to positional messages without data sections: the Communications Test, the Table, Phone, Date and Time, Software and Moneris Key loads, and the fixed-length responses. For those, follow the section's own layout.

**Writing message-layout rules.** Data sections have no marker on the wire, so a label cannot be validated:

1. State the segment and its field number, for example "Segment 109 is the only segment, in Field No. 3". Take the data-section label from this convention.
2. Where the specification labels the position differently, quote its label in the rule's `note` and cite the SME query. The rule stays enforceable.
3. Validators check segment presence, order and Element 63, never data-section labels.
4. Keep an existing rule's `sourceAnchor` unchanged when rewording it; the anchor is a matching key.

## Canonical BR Taxonomy

Classify every BR into one of these families. Segment 100 uses all of them; other segments use the families that apply. The Segment 100 reference index is [segment-100-br-baseline-index.json](../../../test-output/test-json/knowledge/segment-100-br-baseline-index.json).

1. `CORE-STRUCTURE`: segment identity, field order, lengths, separators, serialization.
2. `CORE-FIELDS`: the segment's own fields (for Segment 100: terminal, Prompt Code, account, amounts, sequence, approval, time, partial approval).
3. `TRANSACTION-TYPES`: financial transaction types, card types, Prompt Code composition.
4. `LIFECYCLE`: completion, cancellation, reversal, void, refund, timeout, TOR, retries.
5. `EBT-EWIC`: Segment 103, eWIC operations, WIC/EBT program data.
6. `RESPONSES`: response code, approval, decline, partial approval, and response context.
7. `ANNEXURE-CONDITIONAL`: Appendix A through T rules that apply only under a stated condition.
8. `SEPARATE-DOMAINS`: TransArmor administration, CA keys, digital wallets, Premium Gift Card, and Moneris.

Do not flatten conditional or separate-domain rules into universal segment rules.

## All-Element Validation Workstream

Use the [all-element module](elements/README.md) and its [source/BR inventory](../../../test-output/test-solution-independent-review/all-element-inventory.json) before introducing element BRs. The source has 231 distinct element IDs and 232 definition occurrences; repeated IDs and different segment meanings must remain separate evidence. Automated element-ID matches only locate existing BR candidates. Compare the source, version, message family, segment, transaction/card context, subtable and lifecycle before declaring equivalence or creating a new BR.

Add contextual value/combination profiles only after source and existing-BR review. Unknown context and unimplemented profiles must yield `REVIEW_REQUIRED`, never implicit PASS. Retain existing IDs and review states. Test valid, invalid, boundary, missing-field and missing-context cases and link BR -> TS -> TC -> TD -> mapping before reporting coverage.

Current state:

- Chapter 13 type/length baselines cover 220 of 231 elements. Only over-length and non-digit numeric values are `INVALID`; everything else is review.
- 67 profiles cover Elements 30, 55, 63, 84, 85 and 86, including generated segment length/type profiles and count/membership combinations.
- A read-only AI intake validates all Run2 payloads.

This does not establish all-element validation or LLM accuracy. When an alias crosswalk contradicts a unique Chapter 13 name, reject it at intake and record the gap. See the module's batch plan, generation review list and limitations.

## Common Nine-Phase Strategy

Each phase is a gate in `training-status.json` `requiredGates`, in this order: `SOURCE_INVENTORY`, `SEGMENT_KNOWLEDGE_MODEL`, `CONTEXT_MATRIX`, `INDEPENDENT_BR_DERIVATION`, `TS_TC_TEST_DATA_CHAIN`, `LIFECYCLE_AND_SPECIALIZED_FLOWS`, `SERIALIZATION_AND_MUTATION`, `INDEPENDENT_AI_ARTIFACT_INTAKE`, `CONVERTER_AND_EXECUTION_READINESS`. A gate passes only with recorded evidence and a reviewer.

### Phase 1: Source Inventory

Capture specification version, section, page, segment, element, rule, applicability, source text, and ambiguity status. Create canonical source anchors before generating BRs.

### Phase 2: Segment Knowledge Model

Model the segment's identity, field order, requiredness, data type, length, valid values, separators, serialization, message family, request/response role, and external dependencies.

Produce a machine-readable field inventory with one row for every ordered field: its canonical source rule, JSON representation, appendix dependencies, and validation status. The Segment 100 reference is [segment-100-field-knowledge-inventory.json](../../../test-output/test-json/knowledge/segment-100-field-knowledge-inventory.json).

Take each element's format (type, length, padding) from its Chapter 13 definition, then cross-check the §11 layout table, the §12 segment section, and the Chapter 12 segment/transaction matrix. Record any disagreement as `PROVISIONAL` (see [L7](#lessons-learned) and [L8](#lessons-learned)).

### Phase 3: Context Matrix

Record the dimensions that change segment behavior:

- Message category and transaction type
- Request or response role
- Card type, payment network, and entry mode
- Companion and mutually exclusive segments
- Lifecycle role and original transaction relationship
- Merchant, terminal, program, or specialized domain
- Appendix, code-table, and external-reference dependencies

Transaction type alone must not determine the complete message. Produce an explicit context envelope for every artifact. Required dimensions are transaction type, card type, payment network, lifecycle role, and message family. Conditional dimensions are entry mode, POS condition, specialized domain, companion segments, and response context. The Segment 100 reference is [segment-100-context-model.json](../../../test-output/test-json/knowledge/segment-100-context-model.json).

### Phase 4: Independent BR Derivation

Each BR must contain:

- Condition and scope
- Required behavior
- Valid representation
- Invalid representation
- Expected result
- Exception or boundary
- Canonical source anchor
- Confidence and review status

### Phase 5: TS, TC, and Test Data Generation

For each accepted BR, generate one or more focused Test Scenarios, Test Cases, and request Test Data JSON records. Include positive, negative, boundary, conditional, lifecycle, compatibility, and serialization cases where applicable.

The request Test Data JSON contains requests, field values, calculated values, and lifecycle data. Response expectations belong to the Test Case or response-validation artifact unless the specification pack explicitly defines a separate response artifact.

Every Test Case must state a precondition (Given), an action (When), an observable outcome (Then), a specific expected result, and a reference to its request Test Data ID. A Test Case missing any of these is `INVALID` for intake, whichever producer created it.

### Phase 6: Lifecycle and Specialized-Flow Training

Train the LLM to preserve message order, sequence identifiers, original references, retries, reversals, completions, voids, cancellations, and follow-up dependencies. Keep specialized domains conditional, including EBT/eWIC, EMV, tokenization, wallets, fleet, loyalty, Moneris, TransArmor, and download flows.

### Phase 7: Serialization and Mutation

Validate field order, separators, segment lengths, message lengths, counts, encoded values, binary/network values, and omission rules. Apply deliberate mutations to valid data and verify that the Test Solution detects the intended rule violation.

Classify each mutation result as `CONFIRMED_CATCH` (the intended rule fired), `MISSED_CATCH` (no rule fired), or `FALSE_POSITIVE` (a different rule fired). The target is at least 85% detection overall and 100% for source-critical mutations. The standard mutation set is in [Item 5](#test-solution-implementation-8-item-framework).

### Phase 8: Independent AI Artifact Intake

Preserve original AI files and metadata. Normalize them into canonical models, then validate:

```text
BR -> TS -> TC -> request Test Data JSON
```

Report missing, duplicate, unsupported, malformed, contradictory, and review-required artifacts. AI-produced coverage is a claim under test, not the denominator.

Source locations and the required AI package layout are in [AI-ARTIFACT-INTAKE.md](../../test-validation-strategy/AI-ARTIFACT-INTAKE.md). Specialized appendix artifacts must declare a domain such as `MONERIS`, `DIGITAL_WALLET`, `PAYMENT_TOKEN`, `TRANSARMOR_ADMIN`, `CA_PUBLIC_KEYS`, or `PREMIUM_GIFT_CARD`. The intake gate must reject undeclared domains and must not certify generic segment JSON as specialized coverage.

### Independent Review Process

Apply this process to every AI delivery review, phase-1 probe, message-template review, and re-review after a producer correction. The review is performed by the Test Solution; producer status labels and self-verification are evidence to inspect, never the verdict.

#### 1. Freeze and scope the evidence

- Record delivery ID/revision, specification version, producer, received date, exact input paths, and hashes when available.
- Preserve the producer's files unchanged. Keep generated review reports separate from producer inputs.
- State the review scope explicitly: segment/message family, BR/TS/TC/TD/mapping stages, exclusions, and whether the sample is representative, exhaustive, or only a probe.
- Do not infer missing stages from filenames, counts, README claims, or another delivery.

#### 2. Establish the independent oracle

- Read the applicable ATL105 PDF sections and cross-check layout tables, element definitions, segment definitions, transaction matrix, and appendices where relevant.
- Read the Test Solution rule catalog, context/applicability model, validators, and current open SME decisions.
- Mark each oracle rule `CONFIRMED`, `PROVISIONAL`/`REVIEW_REQUIRED`, or `OUT_OF_SCOPE`; do not turn unresolved specification conflicts into pass/fail facts.
- Confirm templates against the PDF before using them as expected-message evidence. Templates are derived artifacts, not the specification.

#### 3. Reconstruct the complete producer chain

Follow actual IDs and files, not aggregate claims:

```text
source anchor -> BR -> TS -> TC -> physical TD -> mapping to Test Solution rule
```

For every link, verify identity and cardinality: all BR IDs referenced by each TS; all TS IDs referenced by each TC; all TC IDs resolved to physical TD files; and every mapping resolved against an independently owned rule. Check request/response roles, message family, transaction context, segment set/order, Element 63 where applicable, version continuity, and source-anchor identity. Record orphan, duplicate, fan-out, many-to-many, and missing links rather than silently repairing them.

Open physical payloads. Validate their root/envelope, fields, segment composition, values, lengths, encoding, and conditional/lifecycle rules. Run the relevant Test Solution validator when one exists. A producer `PASS` does not substitute for executing the independent validator.

Before SME approval, the Run2 segment evidence writer reports technical validation separately from certification. A complete technical assessment is `PRE_SME_TECHNICAL_CHECKS_PASSED_REVIEW_REQUIRED`, with `executionCertified: false` and SME approval `NOT_ESTABLISHED_BY_THIS_REPORT`; a failed assessment remains `REVIEW_REQUIRED`. Payload compliance requires a segment-owned validator, at least one linked physical payload containing that segment, a recorded validation outcome for every applicable file, and no invalid or unreadable linked files. An empty batch or a linked file for another segment must not pass. These technical checks do not establish business equivalence or remove the SME sign-off gate.

#### 4. Classify each finding

Use a finding per defect and name its evidence and affected stage. At minimum distinguish:

| Finding class | Meaning |
|---|---|
| `SOURCE_CONFLICT` | ATL105 statements conflict or leave behavior unresolved; raise an SME query. |
| `TEST_SOLUTION_GAP` | The independent catalog, rule, validator, fixture, or mapping oracle is missing/incorrect; do not blame the producer for that gap. |
| `AI_ARTIFACT_DEFECT` | The producer artifact contradicts confirmed source behavior or the agreed artifact contract. |
| `CHAIN_GAP` | A BR/TS/TC/TD/mapping link or physical file is absent, orphaned, duplicated, or inconsistent. |
| `PROVENANCE_OR_VERSION_GAP` | Source, revision, specification version, producer ID, or physical artifact identity cannot be established. |
| `REVIEW_REQUIRED` | Evidence is incomplete, heuristic, ambiguous, or depends on an unresolved decision. It is not a confirmed match or a pass. |

One symptom may have multiple causes. Separate, for example, an AI missing anchor from a Test Solution rule that lacks an element anchor; do not report the former as a producer defect until both sides are checked.

For every `REVIEW_REQUIRED` BR, preserve a stable deferred-item ID, blocker, affected BR/TS/TC/TD IDs, owner or review channel, and the exact evidence needed to close it. Keep dependent scenarios, cases, and data review-gated; do not count them as confirmed coverage or silently promote status. Close the item only after the evidence is recorded, the affected validator/test is rerun, and the disposition is reviewed. Current Segment 100 examples are `APPD-REVIEW-001` (ANSI `00` / Table 041) and `APPD-REVIEW-002` (Element 12 Card Discretionary), recorded in the [Appendix D coverage package](../../../test-output/test-json/appendices/appendix-d-segment-100-coverage.json).

#### 5. Determine the review outcome

Assign an overall outcome and per-chain/per-rule disposition:

- `ACCEPTED`: in-scope required stages exist; physical payloads pass independent checks; mappings are evidence-supported; no unresolved blocker affects the claim.
- `ACCEPTED_WITH_GAPS`: the explicitly accepted scope passes, and remaining gaps are enumerated, bounded, and not represented as coverage.
- `REVIEW_REQUIRED`: a material question or evidence gap prevents a reliable decision.
- `REJECTED_NOT_INTAKE_READY`: a required stage is absent/invalid, physical data fails a confirmed rule, mappings are materially wrong, or a claimed pass cannot be reproduced.
- `NOT_ASSESSED`: scope/evidence is insufficient to make a verdict.

Report denominators and numerators, excluded records, examples, validator/test commands and results, and limitations. Never call AI-output-relative coverage independent specification coverage.

#### 6. Record and route findings

- Record SME questions, Test Team decisions, developer discussion topics, and AI feedback exactly once in the [Communication Register](#communication-register); connect related IDs.
- Put detailed evidence in a machine-readable review report under `specifications/ATL105/test-output/ai-solution-independent-review/`, and give each finding a stable ID, severity, class, evidence paths/IDs, impact, owner/channel, and required disposition.
- Generated views are regenerated from the register; do not edit them by hand.
- Keep producer files immutable. Corrections to the Test Solution oracle are made in Test Solution-owned catalogs/code and recorded as such.

#### 7. Re-review and improve the review process every time

For each completed review, conduct a short reviewer retrospective before closing it:

1. Record which check found each material defect and which checks missed or misclassified one.
2. Decide whether the cause is a source ambiguity, oracle gap, tool defect, evidence problem, or reviewer/process omission.
3. Add or update a focused regression test, schema check, or review step for every repeatable failure mode.
4. Update this handbook in the same change when the reusable process, finding taxonomy, outcome criteria, evidence contract, or a gate changes. Add a numbered lesson for a new generalizable rule; keep delivery-specific examples in the review report.
5. Re-run the affected check against the current delivery and the relevant prior fixture/report. Record the reviewer, date, command, result, and remaining limitation.

The review process is therefore iterative but controlled: improve a gate from evidence, test that improvement, document it, and do not retroactively promote old findings without re-running the new check.

#### Review Deliverable Checklist

- [ ] Scope, delivery revision, source version, producer, inputs, provenance, and exclusions recorded.
- [ ] Independent source/rule oracle identified; unresolved source issues remain review-gated.
- [ ] Actual BR -> TS -> TC -> physical TD -> mapping chain reconstructed.
- [ ] Physical payloads independently validated; producer `PASS` not treated as acceptance evidence.
- [ ] Findings classified, evidenced, severity/impact assigned, and register IDs linked.
- [ ] Outcome, denominators, exclusions, commands, results, and limitations reported.
- [ ] Retrospective completed; reusable process improvements, regression tests, handbook/lesson changes recorded.
- [ ] Re-review checks pass or remaining blockers are explicitly retained.

### Phase 8a: Field-Alias Crosswalk (Evidence-Based, Non-Authoritative)

The Test Solution is trained and certified independently of the AI Solution's schema. The field-alias crosswalk does not change that: it is a separate, evidence-only comparison bridge built after independent training, never a source of Test Solution rules, requirements, or field names.

Purpose: raw JSON key names rarely match between the AI Solution and the Test Solution (different schemas, casing, and nesting). A field-alias crosswalk lets the Test Solution recognize the AI's own field label for a canonical element it already owns, without adopting the AI's schema as truth.

Process:

1. Extract the AI Solution's own observed field-name vocabulary per segment directly from delivered AI artifacts (for example, `test_data.key_values[].element` in a traceability matrix, or the flattened leaf JSON key names of a resolvable AI test-data payload when no explicit field label exists). Record occurrence counts. Never invent a name the AI did not actually produce.
2. Cross-reference each AI field name against the segment's own independently derived rule catalog (`sourceAnchor.element` and rule title). Only record an alias when the correspondence is unambiguous (the AI field name and the Test Solution rule title clearly describe the same specification field).
3. Store the result as `specifications/ATL105/contract/segment-<segment>-field-alias-crosswalk.json`: `aiElementName`, `testElementNumber`, `testRuleId`, `testRule`, and `occurrencesObserved`. Follow the AI Solution's own naming for `aiElementName` (its schema, used only as a join key) — never rename or reshape a Test Solution field to match it.
4. If a segment has no AI evidence for a field, do not create an alias entry for it. Record the gap instead of guessing; an empty or partial crosswalk is expected and must be stated in the segment's coverage note.
5. Use the crosswalk only in comparison/reporting tooling to confirm a TS/TC/test-data-level match (`MATCHED_ELEMENT_CONFIRMED`) or to explain a mismatch. It must never be read by, or influence, the Test Solution's own validators, rule catalogs, or requirement derivation.
6. Re-derive and extend the crosswalk whenever a new AI delivery is analyzed; do not treat a prior delivery's vocabulary as permanent if the AI Solution's schema changes.

### Phase 9: Converter and Execution Readiness

Run the external converter or serializer only after intake, traceability, semantic, and review gates pass. Compare serialized request output with the canonical intent and source rules.

## Test Solution Implementation: 8-Item Framework

The nine phases define what must be known. The eight items define the Java code that proves it, in `regression-artifact-validator`. Build them in order; each item depends on the previous one. Copy the Segment 100 classes (`src/main/java/com/coreauth/validator/canonical/Segment100*.java`) as templates.

| Item | Class (`canonical/`) | Test class | Proves |
|---|---|---|---|
| 1. Coverage closure | `Segment<NNN>PayloadValidator` | `Segment<NNN>PayloadValidatorTest` | Every catalog rule is an explicit check. Valid data passes. |
| 2. AI artifact comparison | `Segment<NNN>ArtifactComparison` | `Segment<NNN>ArtifactComparisonTest` | AI packages normalize to the canonical model (manifest, BRs with anchors, TS->BR, TC->TS, TD->TC). |
| 3. Test-data independence | `Segment<NNN>IndependenceValidator` | `Segment<NNN>IndependenceValidatorTest` | Test data validates on its own: schema, format, enum, cross-field, and semantic checks. |
| 4. Traceability matrix | `Segment<NNN>TraceabilityMatrix` | `Segment<NNN>TraceabilityMatrixTest` | Every catalog rule has TS -> TC -> TD. Gaps are listed, and coverage is computed against the [Coverage Denominator](#coverage-denominator). |
| 5. Mutation definition | `Segment<NNN>MutationTester` | `Segment<NNN>MutationTesterTest` | The 10 standard mutations below are defined for the segment. |
| 6. Mutation execution | `Segment<NNN>MutationTestRunner` | `Segment<NNN>MutationTestRunnerTest` | Every mutation runs against every package, and the detection rate is reported. |
| 7. Validator enhancement | (updates Item 1) | `Segment<NNN>ValidatorDetectionTest` | Detection reaches at least 85%, and every `MISSED_CATCH` is explained or fixed. |
| 8. Consolidated report | `Segment<NNN>ConsolidatedReport` | `Segment<NNN>ConsolidatedReportTest` | Items 1-7 results, blockers, and open SME items are combined into `SEGMENT-<NNN>-CONSOLIDATED-REPORT.txt`. |

Standard mutations (Item 5):

| ID | Violation | Example |
|---|---|---|
| MUT-001 | Identity value changed | `segmentType` 100 -> 101 |
| MUT-002 | Format | numeric field -> non-numeric |
| MUT-003 | Length | 6 digits -> 5 digits |
| MUT-004 | Invalid code | `promptCode` VIS -> BAD |
| MUT-005 | Required field omitted | remove `sequenceNumber` |
| MUT-006 | Pattern / character set | special characters in an alphanumeric field |
| MUT-007 | Type mismatch | number where a string is required |
| MUT-008 | Enumeration out of bounds | value outside the allowed set |
| MUT-009 | Cross-field dependency | Element 63 does not match the actual segment count |
| MUT-010 | Structural requirement | required structural field malformed |

File layout for a segment:

```text
specifications/ATL105/docs/specs/kb/segment-<NNN>/   README.md (addendum), coverage/segment-<NNN>-rule-catalog.json, SME/TBA register
specifications/ATL105/test-output/test-json/         segment-<NNN>-*-package.json (BR/TS/TC/TD packages)
specifications/ATL105/test-output/consolidated-reports/SEGMENT-<NNN>-CONSOLIDATED-REPORT.txt
specifications/ATL105/contract/                      segment-<NNN>-field-alias-crosswalk.json (Phase 8a)
src/main/java/com/coreauth/validator/canonical/      Segment<NNN>*.java (Items 1-8)
src/test/java/com/coreauth/validator/                Segment<NNN>*Test.java
```

Run `mvn clean test` after every item. The report is generated with `mvn exec:java -Dexec.mainClass=com.coreauth.validator.canonical.Segment<NNN>ConsolidatedReport`. If Maven cannot reach the repository, follow [L13](#lessons-learned).

## Segment Package Field-Name Conformance

BR -> TS -> TC -> TD chain matching (Phase 5, Phase 8, Phase 8a) requires every segment's Test Solution package to use the same canonical link fields: `businessRequirements[].id`, `testScenarios[].requirementIds` (array), `testCases[].scenarioIds` (array), and `testData[].testCaseIds` (array) with a real `payload`. A package that uses a different shape (for example `covers` instead of `requirementIds`, a singular `scenarioId` instead of `scenarioIds`, or a narrative `testDataStatus` string instead of a `testData[]` array) silently breaks chain matching even when the rule catalog and prose are correct. `segment-111-core-structure-package.json` is a known example needing this normalization. Verify field-name conformance as part of a segment's completion gate.

Before treating any `TestSolutionJsonFormatTest`-style package-conformance result as authoritative, run `mvn clean test` (not an incremental `test`). A stale compiled test class can report failures for exclusions or field names that the current source already handles correctly, producing a false-positive gap report.

## Coverage Denominator

A coverage percentage is meaningless without a stated denominator. Each segment addendum must declare:

- **In scope:** the rule families that count toward completion (normally all field format, cardinality, conditional-applicability, and rejection rules in the segment catalog).
- **Out of scope:** what the ATL105 message specification does not govern (for example performance, concurrency, and external-system internals).
- **Exempted rules:** rules that are excluded, with the reason and approver.
- **Required counts:** BRs, scenarios, test cases, and test cases with request data needed for completion.

The denominator comes from the Test Solution rule catalog, never from AI output. AI requirements with no Test Solution anchor are reported as `AI_ONLY` and enter the denominator only through a `NEW_RULE` decision in the SME decision register.

For each delivery, confirmed Test Solution BR coverage uses `distinct independent Test Solution BRs with a delivery-bound, evidence-supported CONFIRMED match / in-scope independent Test Solution BRs`. Keep AI-internal BR-to-TC linkage on the separate AI-BR denominator. Missing or stale crosswalks, pending decisions, and reused IDs do not count as matches and must not be labeled `AI_ONLY`; mark those independent rules `NOT_ASSESSED`. Report confirmed-evidence count and assessment status separately: `0/N` confirmed evidence with `NOT_ASSESSED` means no matches are yet validated, not that no equivalent AI rules exist. Never reuse a prior delivery's mapping without revalidating exact source/business identity.

The candidate-discovery population includes **all** AI BR records, regardless of AI producer or SME approval state; approval is not a discovery filter. For a complete delivery, trace each AI BR through BR -> TS -> TC -> TD using the source catalogs and full matrix, validate every hop and physical TD separately, and retain proposed/matrix-only edges as unresolved. Exact shared source-anchor candidates (for ATL105, normalized segment + explicit numeric element source ID) may identify Test Solution rules for review, but are not confirmed business-equivalent matches. Preserve one row per AI BR, including incomplete/unmatchable anchors; only delivery-bound, evidence-reviewed dispositions can contribute to confirmed independent BR coverage.

## Segment-Specific Training Addendum

Each segment addendum should contain only behavior that is different or additional for that segment:

1. Segment name, number, and source sections
2. Message families and placement rules
3. Field inventory and canonical JSON representation
4. Required, optional, conditional, and prohibited fields
5. Companion and mutual-exclusion rules
6. Transaction, card, network, and lifecycle context
7. Appendix and external-reference dependencies
8. Positive, negative, boundary, and mutation examples
9. Known ambiguities and SME/TBA questions
10. Segment-specific coverage denominator and acceptance gates
11. AI artifact examples and expected normalization behavior
12. Segment-specific validator and converter requirements
13. Field-alias crosswalk status: evidence gathered, aliases confirmed, or explicitly "no AI evidence yet"

## Common Training Outputs

Every segment should eventually produce:

- Knowledge model and source-anchor catalog
- Independent rule catalog
- Source-backed assertion register and review-only draft cases where source evidence is unambiguous
- BR catalog
- Scenario catalog
- Test Case catalog
- Request Test Data JSON catalog
- Aggregate independent BR -> TS -> TC -> TD training package with source-file provenance
- Traceability matrix
- Independent coverage report
- Field-alias crosswalk (evidence-based, may be partial or empty; never fabricated)
- Mutation evidence
- Manual-review queue
- Final validation decision

For Segment 100 transaction-context training, the Test Solution must maintain the complete Appendix G baseline of all 23 valid transaction-type codes. The baseline is stored at `specifications/ATL105/test-output/test-json/segment-100-all-23-transaction-type-training-baseline.json` and separates:

- 13 standard financial codes requiring standard financial-flow validation and Segment 100 rules.
- 10 special or non-financial codes requiring specialized message-family validation or explicit review boundaries.

The transaction-type baseline is Test Solution-owned ATL105 evidence. It is not derived from AI output. Lifecycle groups such as authorization completion, purchase reversal/void, refund void-of-return, authorization cancellation, and timeout reversal are maintained separately and must preserve original-sequence correlation where the source requires it.

The generated Segment 100 code-flow BR/TS/TC package is a **starter mapping**, not proof of an independent executable chain: its per-code `testDataFile` references point into AI input, and lifecycle rows reuse a shared catalog that includes source and corrected variants. Never count an AI-owned fixture as independent Test Solution TD evidence. A code-level matrix must record each BR -> TS -> TC -> TD edge and label data ownership (`AI_INPUT`, shared Test Solution catalog, independent Test Solution fixture, or non-executable draft stub).

The ten Appendix G special/nonfinancial types (`9`, `D`, `E`, `K`, `L`, `M`, `N`, `Q`, `T`, `V`) require specialized message-family training; do not force them through the standard financial purchase template. Use the source-backed [special-flow training technique](segment-100/transaction-type-training/special-flow-training-technique.md), the draft [special BR baseline](../../../test-output/test-json/special-transaction-type-br-baseline.md), [special chain draft package](../../../test-output/test-json/special-transaction-type-br-ts-tc-td-draft-package.md), and [combined 23-code matrix](../../../test-output/test-json/combined-23-transaction-type-br-ts-tc-td-matrix.md). Draft TS/TC links and schema-only TD stubs are not execution coverage. Code `D` remains Appendix-G-only for detailed request/response behavior; code `T` remains blocked on the external TransArmor specification. Record such blockers and keep status `REVIEW_REQUIRED` rather than filling protocol details by analogy.

For specification-wide training, the Test Solution also maintains `specifications/ATL105/test-output/test-json/all-segments-all-23-transaction-type-training-baseline.json`. This is a 48-segment by 23-code applicability matrix covering all numbered and download segments in the ATL105 inventory. Segment 100 entries are the standard-financial transaction starter baseline; their fixture ownership and lifecycle references must be checked before claiming independent execution. Other segment/code combinations remain `CONTEXT_REVIEW_REQUIRED` until the segment rule catalog, message-family applicability, lifecycle evidence, and independent request Test Data JSON establish a valid segment-specific training package. This prevents the Test Solution from fabricating applicability merely because a transaction code exists in Appendix G.

## Completion Gate

A segment is training-ready for AI artifact intake only when its addendum has a source catalog, applicability/context matrix, BR-to-TS-to-TC-to-request-data chain, positive and negative examples, lifecycle model where applicable, mutation set, review boundaries, an independent coverage denominator, and canonical link-field names (`requirementIds`, `scenarioIds`, `testData[].testCaseIds`) confirmed across every package file for that segment.

A segment is not certified merely because its folder, README, or rule catalog exists. Final certification still requires actual AI artifacts and, where applicable, the external converter.

## Sign-Off Checklist

Complete this for every segment before merging to `Develop`:

- [ ] **Specification coverage:** every segment rule is in the catalog with a complete `sourceAnchor`; unclear rules are `PROVISIONAL` with an `SME_QUERY` in the communication register.
- [ ] **Communication register:** every question, discussion topic, and feedback item from this work is in the register, the views are regenerated, and `CommunicationRegisterTest` passes.
- [ ] **Specification cross-check:** the layout table, segment section, Chapter 13 element definitions, and Chapter 12 matrix are reconciled, and conflicts are logged ([L7](#lessons-learned)).
- [ ] **Items 1-8:** every class and test class exists and passes.
- [ ] **Mutation detection:** at least 85% overall, 100% for source-critical mutations, and each `MISSED_CATCH` is explained.
- [ ] **Traceability:** every catalog rule has BR -> TS -> TC -> TD, and coverage is reported against the declared denominator.
- [ ] **Field-name conformance:** `requirementIds`, `scenarioIds`, and `testData[].testCaseIds` are used in every package file.
- [ ] **Snapshot assertions:** every test that asserts a catalog size was updated in the same commit as the catalog ([L11](#lessons-learned)).
- [ ] **Failures already on `Develop`:** recorded with their root cause, not ignored ([L10](#lessons-learned)).
- [ ] **Full suite:** `mvn clean test` passes with no new failures, or the fallback in [L13](#lessons-learned) is documented.
- [ ] **Independent review:** when AI artifacts are in scope, the [Independent Review Process](#independent-review-process) deliverable checklist is complete and the verdict is reflected in `training-status.json`.
- [ ] **Training status:** `training-status.json` gates and blockers are updated, and the consolidated report is regenerated.
- [ ] **Handbook:** any new lesson is added to this document in the same change.

## Lessons Learned

Every tester follows these rules. Add new lessons here, numbered in sequence, in the same change that discovered them.

**L1. Mutate at the right JSON level.** Segment 100 mutation detection was 0% because mutations were applied from the payload root. Navigate the real path (`request` -> `dataSection1`/`dataSection2` -> segment object) and check the test-data structure before writing mutation tests.

**L2. The specification is the oracle.** Making a validator pass AI output produces false validation. Build the rule catalog from the specification first, and use AI output only as input under test.

**L3. Catalog cross-field rules explicitly.** Most Segment 100 validator enhancements were cross-field checks (for example Element 63 against the actual segment count). Give interdependencies their own rule category in Phase 2.

**L4. Keep isolated debug tests ready.** When detection drops below 85%, create `Segment<NNN>ValidatorDetectionTest` immediately and test the failing mutation on its own.

**L5. Mark ambiguities on day one.** `PROVISIONAL` items flagged late took longest to resolve. Record the exact question as an `SME_QUERY` in the [Communication Register](#communication-register) while reading the specification.

**L6. Compare lookalike elements before assuming a format.** Element 62 says "always precede single digits with a zero"; Element 63 says "variable length of up to two digits". Read the Chapter 13 entry for every field instead of copying a neighbouring field's pattern.

**L7. Cross-check every table against the element definition.** ATL105 states the same fact in several places, and they disagree:

| Check | Example conflict |
|---|---|
| Message-layout table (§11.x) against the segment section (§12.x) | Segment 101 maximum length: 308 in §11.1.1, 61 in §12.2 |
| Layout table against the element definition (Chapter 13) | Element 63: fixed length 2 in §11.1.1, "up to two digits" in Chapter 13 |
| Layout table against its own prose | §11.3.1 marks Segment 111 `R` but says "none, one, or more" |
| Layout table against the Chapter 12 segment/transaction matrix | Segments 146 and 152 are listed in a request but are response-only |
| The same segment in different messages | Segment 111: 999 in §12.10, 20 in the ECA/TeleCheck request |

Chapter 13 is authoritative for an element's format. Where sources still conflict, record the rule as `PROVISIONAL` with an open SME item rather than choosing one.

**L8. Message templates are derived artifacts.** `atl105_complete_templates.json` is marked `human_review_required`. Check a template's segment list against the specification's layout table before building on it. Segment 113 was wrongly listed in the Financial Transaction Request template.

**L9. Use the repository-wide catalog ID convention.** Catalog IDs are `ATL105-SEG<NNN>-RULE-CATALOG-001`. Do not create or assert the legacy `segment-<nnn>-rule-catalog` form.

**L10. A failing test on `Develop` is a finding, not background noise.** Run the suite before starting work. Record any failure that already exists with its root cause; never mark it expected, skip it, or leave it for someone else.

**L11. Assert invariants, not snapshot counts.** `RuleCatalogBaselineTest` hard-coded `hasSize(17)` and went stale when the repository reached 49 segments. Prefer an invariant such as "one rule catalog per `kb/segment-*` folder". When you change a catalog, search `src/test` for `hasSize(`, `isEqualTo(N)`, and `catalogRules()` and update them in the same commit. If a count is part of the specification, cite the section in the assertion message.

**L12. Coverage percentages need a Test Solution denominator.** A target such as "derive at least 90% of the requirements the AI extracted" makes AI output the oracle. Measure against the segment's own [Coverage Denominator](#coverage-denominator).

**L13. If Maven is unavailable, verify rather than assume.** If the Nexus handshake fails, compile `src/main` with `javac` against the cached jars and run a throwaway harness that reproduces the changed assertions. Report that the JUnit suite itself was not run, and ask someone on the network to run `mvn clean test`.

**L14. An SME answer is not a citation.** Before asking an SME to confirm that a segment is exclusive to one message, check the Chapter 12 segment/transaction matrix and the Element 63 processing rules, and put that evidence in the question. The Segment 108 and 114 "Loyalty-only" decisions were confirmed without either source, and both contradict the matrix. A decision recorded without a section reference stays `REVIEW_REQUIRED`.

**L15. Keep repository paths under 260 characters.** On Windows, git cannot check out a longer path. A contributor whose checkout failed then committed the missing files as deletions (commit `7986b73` removed 66 files, 36 of them long paths), which broke tests that used them. Keep new file names short, and before committing check `git status` for deletions you did not make.

**L16. Read the Chapter 12 matrix from the PDF, not the extracted text.** `extracted_text.txt` collapses the spaces between the matrix's X marks, so you cannot tell which column an X belongs to. Extract the matrix pages (PDF pages 216-219) with a layout-preserving extractor, such as `pypdf`'s `extract_text(extraction_mode="layout")`, and map each X by its horizontal position against rows whose columns are known. This showed that Segment 113 is marked in the Financial Transaction Request column, which the extracted text had hidden.

**L17. Write each question once.** Before the communication register, questions were kept in the catalog, the segment register, reports, and backlog files, and the copies drifted. The Segment 108, 109, and 115 catalogs numbered the same questions differently from their registers. The Segment 104 questions were only in a report. Write the question in the register and link to it everywhere else.

**L18. A checker's label is not evidence; open the payload.** The structure checker reported the 1,680 AI Totals test cases as "payload is a Financial Request". That label was produced for every non-financial test case, whatever the payload held. Opening the files showed an empty `Financial Request` shell with `NumSegments` 0 and no segment. Before reporting a defect, open at least one failing artifact of each kind and describe what it contains.

**L19. Verify the whole producer chain independently.** A producer can mark a BR -> TS -> TC -> TD chain `PASS` while the payload has the wrong root, wrong specification version, no required segment, or a mapping that points to an unrelated message family. Validate the physical test data and compare the complete chain against the Test Solution catalog before treating traceability as coverage.

**L20. Do not generate download-segment notes from a numbered-segment template.** The 2026-09-28 parity notes for DL1-DL8 were generated from a template built for numbered segments. They told testers to "keep the Field Separator for an empty middle field" and to check a "3-digit Segment Length" for DL1-DL6, which have neither. They also reported "no conditional rules" for DL1 although Card Type `173` requires DL6. DL1-DL6 are framed by a Data Type Indicator and `~`, have no Field Separators, and "when a field is not populated, the next field immediately follows". DL7 and DL8 use a Data Type Indicator plus a Segment Length that excludes the indicator. Take the framing from the segment's own Section 12 note and the §11.7 download layouts, then write the notes.

**L21. An unmatched anchor is not a coverage gap; read the rule text before attributing one.** The 2026-09-29 `phase_1_single_leg` review first reported six Test Solution coverage gaps because the producer's element anchors did not match any catalog rule. A second check found zero coverage gaps. For DL2-DL6 the Data Type Indicator was already covered, and more strongly than the producer's requirement: `SEGDL2-R-001`, `SEGDL3-R-001`, `SEGDL4-R-002`, `SEGDL5-R-002` and `SEGDL6-R-002` each pin the exact fixed marker (`!`, `:`, `@`, `$`, `\`), while the producer asserted presence only. The rules simply carried no `sourceAnchor.element`. Element-anchor matching cannot distinguish "not covered" from "covered but not anchored"; it is a discovery aid, not a coverage test. Before reporting a gap against either producer, open the candidate rules and compare meaning. Report an anchoring gap as `ANCHORING_GAP` and fix it by adding element metadata to the existing rule, never by adding a duplicate rule.

**L22. Encode multi-element anchors as a list, not a delimited string.** Rule catalogs store multi-element anchors as a comma-separated string, for example `SEGDL1-R-002` `element: "24,34"` and `SEGDL1-R-003` `element: "53,98,3,4,54"`, while `canonical-anchor.schema.json` types `element` as a single string. Any consumer doing an equality lookup silently fails: a search for `24` does not match `24,34`. This produced a false unmatched verdict for DL1 and Segment 157, and a false matched verdict for Segments 151 and 152 whose rules carry no element at all. Until the encoding is fixed (`TT-0013`), split on `,` before comparing, and never treat an empty `element` as a wildcard.

**L23. Separate execution outcomes from embedded reference text.** User-supplied processed-and-tested reports are legitimate independent Test Solution evidence, but an embedded code-description row is not proof that the execution returned that code. Preserve source hashes, classify actual response fields separately from descriptions and harness status, and keep the field-to-element mapping `REVIEW_REQUIRED` when labels or widths conflict. In particular, ATL105 Element 83 is one byte while Element 8 Authorizer Response Code is two bytes; never train a two-byte `Response Code` report field as Element 83 without source-backed identification. Mask production values, and do not infer universal transaction/card/entry-mode or field-presence rules from a finite run.

The evidence for L6-L13 is in [TESTER-NOTE-2026-09-29-STALE-SNAPSHOTS-AND-SPEC-CROSS-CHECKS.md](../../test-validation-strategy/TESTER-NOTE-2026-09-29-STALE-SNAPSHOTS-AND-SPEC-CROSS-CHECKS.md).

## Segment 100 Reference Implementation

Segment 100 was trained first and is the worked example for every phase and item:

- [Core rule catalog](segment-100/coverage/segment-100-rule-catalog.json)
- [BR baseline index](../../../test-output/test-json/knowledge/segment-100-br-baseline-index.json), [field inventory](../../../test-output/test-json/knowledge/segment-100-field-knowledge-inventory.json), [context model](../../../test-output/test-json/knowledge/segment-100-context-model.json)
- [Annexure BR baseline](../../../test-output/test-json/segment-100-annexure-br-baseline-package.json) and [multi-step flow catalog](../../../test-output/test-json/segment-100-multistep-flow-catalog.json)
- [Single-step and multi-step BR traceability, including invalid-flow findings](segment-100/transaction-type-training/single-multi-step-br-traceability.md)
- [eWIC gap package](../../../test-output/test-json/segment-100-ewic-gap-package.json), [response-code package](../../../test-output/test-json/segment-100-response-code-package.json), [response family matrix](../../../test-output/test-json/atl105-response-code-family-matrix.json)
- [Validation evidence](../../../test-output/traceability-matrix/segment-100/segment-100-validation-evidence.md)
- Code: `src/main/java/com/coreauth/validator/canonical/Segment100*.java`

### Transaction Lifecycle Training Rules

Treat transaction-code eligibility, lifecycle participation, and a complete multi-leg scenario as separate concepts. Use the source-backed [BR traceability and requirement cross-check](segment-100/transaction-type-training/single-multi-step-br-traceability.md) when deriving or reviewing these flows.

Before teaching these rules to the Test LLM, have a Test Team trainer complete the lifecycle prompts in the [Reusable Segment Training Questionnaire](../../test-validation-strategy/SEGMENT-TRAINING-QUESTIONNAIRE.md). Ask the trainer to explain the evidence for valid and invalid paths, not only confirm a proposed answer. Persist decisions in the source/SME register and decision record; keep unsupported answers `REVIEW_REQUIRED`.

The source-supported lifecycle families are:

| Flow | Transaction-code path | Core learning rule |
|---|---|---|
| Authorization completion | `3`, `5`, or `B` -> `0` | Reuse the authorization's Element 86 Sequence Number; completion amount may differ. |
| Authorization-only cancellation | `3`, `5`, or `B` -> `S` | Use Prompt Code `S`; preserve the original Approval Number and Sequence Number. |
| Purchase/capture reversal | `0` or `4` -> `8`; `6` -> `C` | Select reversal code from the original transaction context; do not cross-pair `8` and `C`. |
| Refund void | `7` -> `U` | Correlate the void to the original return/refund; apply other identity checks only where sourced. |
| Timeout reversal | Eligible Purchase/Capture `0`, CAT purchase `4`, or Mail/Phone purchase `6` -> `Z` | Use the original Sequence Number and same device. Distinguish code `0` Purchase/Capture from preauthorized completion and enforce card/product restrictions. |

Do not derive a direct Authorization Only -> code `8` flow. ATL105 specifies `S` for Authorization Only Reversal; purchase reversal/void code `8` is not its substitute. The separate Authorization -> Completion -> Void chain remains `REVIEW_REQUIRED` under [SEG100-SME-002](segment-100/segment-100-sme-tba-input-register.md), including its void target and debit eligibility.

For TOR timing and retries, distinguish the 30-second response interval from the source recommendation to forward a TOR at least 30 seconds after the original transaction has timed out. The retry limit is three unsuccessful attempts on each available connection route, not a global three-attempt cap. Partial-approval amount rules are card/product-specific; do not assert universal amount equality. Debit POS/CAT capture TORs are unsupported.

Do not treat current validator acceptance as source approval: the validator and lifecycle catalog still accept or record invalid direct Authorization Only -> `8`, cross-paired purchase reversal codes, overly broad TOR originals, and blanket TerminalID equality. Check the relevant ATL105 clauses in the [extracted specification](../extracted_text.txt) and keep unresolved cases review-required.

### Payment-Network Type Training

ATL105 Section 5's 16 normalized leaves are a mixed taxonomy of payment methods, check services, card/benefit programs, and stored-value/loyalty products; they are not 16 card brands. Teach each leaf as a classification only. Do not infer Appendix E card codes, transaction eligibility, authorizer routing, or companion segments without a separate source-backed mapping. The Section 5 context-consistency BR is `REVIEW_REQUIRED` until those mappings are evidenced. See [Payment-Network Type Training](segment-100/payment-network-type-training.md), the [16-leaf BR matrix](../../../test-output/test-json/section-5-payment-network-segment-100-matrix.json), and the [BR -> TS -> TC -> TD package](../../../test-output/test-json/payment-network-card-type-br-ts-tc-td-matrix.json).

Receipt Card Type IDs are a separate namespace: ATL105 §10.1.7.3 lists seven named brand/network labels and generic Debit/EBT IDs. Use the [draft Card Type ID BRs](segment-100/card-type-id-business-requirements.md); do not equate those receipt IDs with Appendix E Element 14 card codes or Appendix C authorizer codes.

Appendix E has three distinct families: 56 Table Load Response entries, 36 financial Prompt Code card types, and 11 special Prompt Codes. Keep their message roles and validators separate. Financial and Table Load allowlist checks are implemented; nine special prompts have validator-harness chains, while `900` and `997` remain review-blocked. Feature effects, production configuration, full special-operation behavior, converter readiness, and AI coverage remain separate gates. See the [Appendix E training note](segment-100/appendix-e-card-type-training.md) and [special-prompt chain package](../../../test-output/test-json/appendix-e-special-prompt-chain-package.json).

For family-wise Test Solution backlog across Appendix A-AE, use the [Appendix Family Gap Closure Register](segment-100/appendix-family-gap-closure-register.md). It distinguishes resolvable BR -> TS -> TC -> independent TD work from SME, configuration, external-spec, and external-fixture blockers. Do not claim an appendix is `COVERED` from source BR counts or metadata-only TD placeholders.

The overall test strategy (phases, governance, RACI, sign-off) is in [Core-Auth-Regression-Test-Validation-Strategy.md](../../test-validation-strategy/Core-Auth-Regression-Test-Validation-Strategy.md). It describes the programme; this handbook describes how to train a segment.

## Handbook History

| Date | Change |
|---|---|
| 2026-10-01 | Added the versioned Element 83 source-backed assertion manifest and fail-closed source gate. Separated citation resolution, literal assertion backing, draft case derivation and SME approval; no new certification gate or executable coverage claim. |
| 2026-10-05 | Added Segment 100 single-/multi-step BR traceability and source cross-check findings; clarified authorization cancellation, TOR scope, retry/timing rules, review-required completion-to-void behavior, trainer-led LLM questionnaire use, and the 16-leaf Section 5 payment-network taxonomy. |
| 2026-10-01 | Added conservative body-location triage for all catalog rules; TOC entries and ambiguous chapter/appendix references cannot promote review-only locations into assertions. |
| 2026-10-01 | Added bounded cross-section evidence for Segment 118 Site Configuration and Host Discount response-code contexts. Recorded second-source lines/fingerprints and partial-rule draft cases without certifying the broader workflow. |
| 2026-10-01 | Added a separate Element 84 source gate for Segment 130 four-digit and Segment 132 three-digit width claims. Both Chapter 13 exception lists and segment layout rows must agree; examples remain width-only and review-required. |
| 2026-10-01 | Extended the Element 84 width gate to the existing Segment 118 and 131 four-digit rules; section-specific mutation tests prevent a matching layout row in another segment from masking a change. |
| 2026-10-01 | Expanded the width-only gate to Segments 101 and 115; added a fail-closed Element 85 fixed-type gate and a reconciled 601-rule evidence ledger. Two fixed-type candidates lack matching field-1 evidence and remain mismatches, not approved rules. |
| 2026-10-01 | Backed five more width-only claims (104, 108, 109, 113, 114) against their own Section 12 field-2 rows and the Element 84 exception lists. Added a 49-segment backlog that preserves unresolved and mismatched rules without promotion. |
| 2026-10-01 | Added six bounded Segment 110 §12.9 row/maximum checks without test-case generation. A moved or changed field row is rejected; multi-field behavior and provisional triggers remain review-required. |
| 2026-10-01 | Added bounded Field 5 Prompt Code `990` source checks for existing Segments 105 and 119; repeated table headers do not imply duplicate or contradictory values. No Totals workflow, SME approval, or executable case is inferred. |
| 2026-10-01 | Backed the three-digit Element 84 width rows for Moneris Segments 135 and 136 without resolving Segment 135's length-counting question or the Moneris applicability rules. |
| 2026-10-01 | Added bounded Element 4 Address Line 2 positional evidence for `SEGDL1-R-010`, with fail-closed source mutations and structural review-only examples; external city, state and ZIP validity remain unverified. |
| 2026-10-01 | Checked `SEGDL1-R-011` against both Element 54 and 98 Chapter 13 bodies. This is a partial, two-definition source match only; no executable cases or SME approval are inferred. |
| 2026-10-01 | Added Element 82's bounded one-digit 1-3 redial-code definition for `SEGDL2-R-006`; placement in both dial-string blocks remains unverified. |
| 2026-10-01 | Matched the three bounded Element 75, 28 and 27 definitions for `SEGDL2-R-007`; terminator source mutations withdraw the partial claim without inferring parser behavior. |
| 2026-10-01 | Matched Element 1/66 access-code and pause-indicator phrases for `SEGDL2-R-005`; a changed paired-absence condition withdraws the partial claim, while the DL2 parsing boundary remains open. |
| 2026-10-01 | Matched Element 23's conditional 30-minute automatic cut-time instruction for `SEGDL3-R-009`; observed settlement behavior and DL3 source conflicts remain review-required. |
| 2026-10-01 | Distinguished Run2 pre-SME technical results from execution certification and blocked payload compliance for zero applicable or unaccounted files. Source/semantic approval remains an independent decision. |
| 2026-09-29 | Became the single handbook. Merged `docs/SEGMENT-100-TRAINING-METHODOLOGY.md` (8-item framework, mutation set, lessons 1-7, checklist) and `docs/test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md` (matching responsibility, BR taxonomy, Segment 100 evidence map, lessons 6.1-6.8), then deleted both and repointed all links here. |
| 2026-09-29 | Archived the 2026-09-23 improvement-plan pack (gap analysis, roadmap, index, quick reference, executive summary, week-1 checklist) to [docs/archive/2026-09-23-test-solution-improvement-plan/](../../archive/2026-09-23-test-solution-improvement-plan/). Folded in: Test Case structure (Phase 5), mutation classification (Phase 7), and the coverage denominator. Not adopted: the "90% of AI-extracted BRs" target (see L12). Its status figures are a 2026-09-23 snapshot and are out of date. |
| 2026-09-29 | Added the [Communication Register](#communication-register) and L17. The 43 segment SME/TBA registers became generated views, with new registers for Segments 100, 101, 103, 104, 111, and 120. Catalog `provisionalItems` now hold only a `registerId`. The AI feedback corrections became `AIF-` items, and `SME-REVIEW-BACKLOG.md` was archived as SEG100-SME-001. |
| 2026-09-29 | Totals message layouts (Section 11.4): split the merged Totals Request template, added the Approved and Declined Totals Response templates, added message-level rules to Segments 105 and 119 with `TotalsRequestPayloadValidator`, and added L18. |
| 2026-09-29 | Added the [Request Data Section Convention](#request-data-section-convention) (TT-0014) with SME queries SEG109-SME-012, SEG116-SME-007 and SEG132-SME-006, and the rule-writing principle; reworded SEG109-R-001, SEG109-R-003 and SEG116-R-005 by field number and made SEG132-R-001 PROVISIONAL. |
| 2026-09-30 | Started Section 11.5 Electronic Mail: corrected the Segment 109 envelope validator, split the request/response templates, added six fixtures, and independently rejected the AI BR -> TS -> TC -> TD -> mapping chain as not intake-ready (L19, AIF-0014/AIF-0015). |
| 2026-09-30 | Added the repeatable [Independent Review Process](#independent-review-process), including full-chain evidence checks, finding taxonomy, outcome criteria, required review deliverable, and a retrospective/improvement loop for every review. |
| 2026-09-30 | Download segments DL1-DL8 retrained against Segment 100 and the §11.7 download layouts: rewrote the generated topic notes and flows, added message-placement, lifecycle and element-format rules, and raised the specification conflicts as SME queries. Added L20. |
| 2026-09-30 | Reconciled all 31 Section 11 layout slots against the 2026-3 extract: corrected the Financial and EMV segment tables, added the shared response layouts/aliases and Data Section 1 fields, recorded DL6 and all five PDL prompt directions, and indexed every subsection. Moneris Key Load layouts include the Appendix V SPDH subfields; CA Public Key File Load remains provisional under `SEG132-SME-006`; TransArmor remains blocked on its missing external source. The master catalog remains `EXTRACTED` and `human_review_required`. |
| 2026-09-30 | Started code-by-code Element 83 response training with code `0` (Approved Purchase/Capture). Added a dedicated family/segment coverage module and clarified that transaction-type compatibility is a Test Solution rule rather than an explicit ATL105 cross-product; Segment 100 Approval Number remains conditional on lifecycle context. |
| 2026-09-30 | Expanded Element 83 training to all 31 source-defined code values and seven message families; added 35 source-defined code/family BR meanings and exhaustive validation of all 217 code/family pairs (35 valid, 182 invalid for the selected family). This does not claim the source defines every transaction/card cross-product or that BRs are business-approved. |
| 2026-09-30 | Reviewed the AI `phase_1_single_leg` delivery (2026-09-29): first five-leg delivery, 37 full chains, perfect referential integrity, but 51.5% of composed segments lost in serialization, three empty test-data files reported `PASS`, no expected outcomes and no negative cases. Verdict `CHAIN_STRUCTURE_SOUND_EXECUTION_EVIDENCE_INSUFFICIENT`. A second check retracted six claimed Test Solution coverage gaps as anchoring gaps (zero coverage gaps found) and corrected `TT-0011`/`TT-0012`. Added L21 and L22; raised `TT-0013` and `AIF-0013`. Report: [reports/ai-feedback/](../../../reports/ai-feedback/). |
