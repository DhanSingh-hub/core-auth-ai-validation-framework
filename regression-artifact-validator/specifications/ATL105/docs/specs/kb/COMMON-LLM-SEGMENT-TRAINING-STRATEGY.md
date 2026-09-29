# ATL105 Segment Training Handbook

**Specification:** BUYPASS ATL105 2026-3
**Applies to:** Every ATL105 data segment, numbered segment, and download segment (`DL1`-`DL8`)
**System under test:** AI Solution artifacts
**Independent oracle:** Core Auth Test Solution rules derived from the specification

> **This is the single training handbook.** Every team member, agent, or automation that trains a segment follows this document and nothing else. It replaces the two `SEGMENT-100-TRAINING-METHODOLOGY.md` files (merged into it on 2026-09-29). The 2026-09-23 improvement-plan pack is archived; see [Handbook History](#handbook-history).

## Contents

1. [How to Train a Segment (Start Here)](#how-to-train-a-segment-start-here)
2. Governance: [Purpose](#purpose), [Standard Ownership](#standard-ownership-and-mandatory-use), [Automatic Update Rule](#automatic-update-rule), [SME Decision Persistence](#sme-decision-persistence), [Communication Register](#communication-register), [Required Training Record](#required-training-record), [Controlled Improvement](#controlled-improvement), [Matching Responsibility](#matching-responsibility)
3. Method: [Artifact Chain](#common-artifact-chain), [LLM Training Rules](#common-llm-training-rules), [Source Anchors](#canonical-source-anchors), [Data Sections](#request-data-section-convention), [BR Taxonomy](#canonical-br-taxonomy), [Nine-Phase Strategy](#common-nine-phase-strategy)
4. Build: [8-Item Framework](#test-solution-implementation-8-item-framework), [Field-Name Conformance](#segment-package-field-name-conformance), [Coverage Denominator](#coverage-denominator)
5. Deliver: [Segment Addendum](#segment-specific-training-addendum), [Training Outputs](#common-training-outputs), [Completion Gate](#completion-gate), [Sign-Off Checklist](#sign-off-checklist)
6. Learn: [Lessons Learned](#lessons-learned), [Segment 100 Reference Implementation](#segment-100-reference-implementation), [Handbook History](#handbook-history)

## How to Train a Segment (Start Here)

1. Read this handbook end to end once. The governance sections are mandatory, not background.
2. Check out the segment branch (`Segment_<NNN>`) from an up-to-date `Develop`. Run the test suite and record any failure that already exists (see [L10](#lessons-learned)).
3. Open `kb/segment-<NNN>/` and the segment's entry in `specifications/ATL105/training-status.json` to see which gates are already passed.
4. Work through the [Nine-Phase Strategy](#common-nine-phase-strategy) in order. Each phase is a gate in `training-status.json`; do not skip one.
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

An agent or automation may perform Phases 1-4 (Source Inventory through Independent BR Derivation) directly from the ATL105 specification text without human involvement, as long as it reads only the specification and cites exact section/page/line evidence; this does not require human assistance. It may not certify its own output: a rule produced this way is `DRAFT_REVIEW_REQUIRED` until an SME/TBA (or formally delegated business approver) reviews and signs off, per the Required Training Record below. Segment training status must reflect this distinction (`IN_PROGRESS` with a `sme-tba-certification-pending` blocker, not `TRAINED_FOR_INTAKE`).

## Automatic Update Rule

This document must be updated in the same change set whenever a training-relevant decision is made, not on request. This applies to (but is not limited to): adding or changing a phase or gate, adding a new artifact type (for example a field-alias crosswalk), changing required package field names or contract shapes, changing coverage-denominator or matching policy, or discovering a segment-package conformance defect. The update happens automatically as part of doing the work; do not wait for an explicit instruction to "update the training strategy."

When aggregating existing Test Solution evidence, standalone `testDataId`/`testCaseId`/`scenarioId` inventories may be normalized into canonical `testData[]` plus review-required TS/TC placeholders. This normalization may not fabricate a BR, source anchor, execution status, or segment identity: derive the segment only from an unambiguous payload `segmentType`; otherwise retain `CONTEXT_REVIEW_REQUIRED` and preserve the original file as provenance.

Knowledge-base verification must run against the ATL105 2026-3 extracted specification text. The automated gate must report catalog count, rule count, version consistency, complete anchors, duplicate anchors, and source-section presence. Passing this gate proves structural/source-address integrity, not business semantic correctness; composite section labels and appendix/title aliases remain `REVIEW_REQUIRED` until SME/TBA confirms the source mapping.

Knowledge-base BR coverage must preserve composite evidence as multiple `sourceEvidenceSections`, resolve only unambiguous title aliases (for example `Totals Request` to ATL105 11.4.1.1), and retain appendix references as explicit evidence. The independent BR package may be generated before TS/TC/TD derivation, but it remains `REVIEW_REQUIRED` until semantic review and the complete BR -> TS -> TC -> request Test Data chain are present.

## SME Decision Persistence

All SME/TBA outcomes for AI-only BRs, draft Test Solution rules, crosswalks, and traceability chains must be recorded in the append-only `test-output/ai-solution-independent-review/ai-only-sme-decision-register.json` before they affect coverage or training status. Each decision records the subject, segment, reviewer, date, evidence, rationale, and decision status. `PENDING` is allowed without review evidence and never counts toward coverage. `CONFIRMED_MATCH` and `NEW_RULE` require canonical source-anchor evidence plus reviewer/date; heuristic similarity or AI confidence is never sufficient. Decisions must not modify immutable AI input files.

The register is validated by `ValidateSmeDecisionRegister`; a decision register is invalid if decision IDs are duplicated, required evidence is absent, a promoted decision has no reviewer/date, or a promoted `CONFIRMED_MATCH`/`NEW_RULE` lacks a complete canonical source anchor.

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
| Test team discussion | `TT-nnnn` | 14 | 12 open, 2 resolved |
| Open topics with the AI developers | `AID-nnnn` | 4 | 4 open |
| Feedback to the AI team | `AIF-nnnn` | 13 | 13 open |

How to use it:

1. Add or update the item in the register JSON. Record the context, the test impact, the evidence, and any related item IDs. Statuses are `OPEN`, `IN_DISCUSSION`, `ANSWERED`, `REOPENED`, `RESOLVED`, `DEFERRED`, `WITHDRAWN`, and `SUPERSEDED`. A closed item needs a resolution, `resolvedBy`, and `resolvedOn`. When you reopen an item, add a `history` entry instead of overwriting the earlier answer.
2. Regenerate the views with `GenerateCommunicationRegisterViews`. It writes the per-segment `kb/segment-<NNN>/segment-<NNN>-sme-tba-input-register.md` files and the channel views in [registers/views/](../../../registers/views/index.md). The views are generated: never edit them by hand.
3. Run `CommunicationRegisterTest`. It fails if an ID is duplicated or malformed, a related item does not exist, a closed item has no resolution, a catalog and the register disagree, or a view is out of date.

An SME answer that changes coverage is still recorded in the [SME decision register](#sme-decision-persistence). The communication register records that the question was answered; the decision register records the coverage decision.

## Required Training Record

For every new or changed rule, the trainer must record:

1. Authoritative source, version, page, and section.
2. Segment, element, rule, applicability, and ambiguity status.
3. Canonical source anchor created or reused before the BR.
4. Condition, behavior, valid and invalid representations, expected result, and exception boundary.
5. BR -> TS -> TC -> request Test Data traceability.
6. Positive, negative, boundary, conditional, lifecycle, or serialization evidence as applicable.
7. Validation result, reviewer, date, and unresolved review items.

Training supplied by a Test Team member to an assistant follows the same record. The assistant must distinguish source fact, derived interpretation, and open question; it must not silently convert an instruction into an approved rule.

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

## Common LLM Training Rules

The LLM shall:

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

For specification-wide training, the Test Solution also maintains `specifications/ATL105/test-output/test-json/all-segments-all-23-transaction-type-training-baseline.json`. This is a 48-segment by 23-code applicability matrix covering all numbered and download segments in the ATL105 inventory. Segment 100 entries are the executable transaction baseline; other segment/code combinations remain `CONTEXT_REVIEW_REQUIRED` until the segment rule catalog, message-family applicability, lifecycle evidence, and request Test Data JSON establish a valid segment-specific training package. This prevents the Test Solution from fabricating applicability merely because a transaction code exists in Appendix G.

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

The evidence for L6-L13 is in [TESTER-NOTE-2026-09-29-STALE-SNAPSHOTS-AND-SPEC-CROSS-CHECKS.md](../../test-validation-strategy/TESTER-NOTE-2026-09-29-STALE-SNAPSHOTS-AND-SPEC-CROSS-CHECKS.md).

## Segment 100 Reference Implementation

Segment 100 was trained first and is the worked example for every phase and item:

- [Core rule catalog](segment-100/coverage/segment-100-rule-catalog.json)
- [BR baseline index](../../../test-output/test-json/knowledge/segment-100-br-baseline-index.json), [field inventory](../../../test-output/test-json/knowledge/segment-100-field-knowledge-inventory.json), [context model](../../../test-output/test-json/knowledge/segment-100-context-model.json)
- [Annexure BR baseline](../../../test-output/test-json/segment-100-annexure-br-baseline-package.json) and [multi-step flow catalog](../../../test-output/test-json/segment-100-multistep-flow-catalog.json)
- [eWIC gap package](../../../test-output/test-json/segment-100-ewic-gap-package.json), [response-code package](../../../test-output/test-json/segment-100-response-code-package.json), [response family matrix](../../../test-output/test-json/atl105-response-code-family-matrix.json)
- [Validation evidence](../../../test-output/traceability-matrix/segment-100/segment-100-validation-evidence.md)
- Code: `src/main/java/com/coreauth/validator/canonical/Segment100*.java`

The overall test strategy (phases, governance, RACI, sign-off) is in [Core-Auth-Regression-Test-Validation-Strategy.md](../../test-validation-strategy/Core-Auth-Regression-Test-Validation-Strategy.md). It describes the programme; this handbook describes how to train a segment.

## Handbook History

| Date | Change |
|---|---|
| 2026-09-29 | Became the single handbook. Merged `docs/SEGMENT-100-TRAINING-METHODOLOGY.md` (8-item framework, mutation set, lessons 1-7, checklist) and `docs/test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md` (matching responsibility, BR taxonomy, Segment 100 evidence map, lessons 6.1-6.8), then deleted both and repointed all links here. |
| 2026-09-29 | Archived the 2026-09-23 improvement-plan pack (gap analysis, roadmap, index, quick reference, executive summary, week-1 checklist) to [docs/archive/2026-09-23-test-solution-improvement-plan/](../../archive/2026-09-23-test-solution-improvement-plan/). Folded in: Test Case structure (Phase 5), mutation classification (Phase 7), and the coverage denominator. Not adopted: the "90% of AI-extracted BRs" target (see L12). Its status figures are a 2026-09-23 snapshot and are out of date. |
| 2026-09-29 | Added the [Communication Register](#communication-register) and L17. The 43 segment SME/TBA registers became generated views, with new registers for Segments 100, 101, 103, 104, 111, and 120. Catalog `provisionalItems` now hold only a `registerId`. The AI feedback corrections became `AIF-` items, and `SME-REVIEW-BACKLOG.md` was archived as SEG100-SME-001. |
| 2026-09-29 | Totals message layouts (Section 11.4): split the merged Totals Request template, added the Approved and Declined Totals Response templates, added message-level rules to Segments 105 and 119 with `TotalsRequestPayloadValidator`, and added L18. |
| 2026-09-29 | Added the [Request Data Section Convention](#request-data-section-convention) (TT-0014) with SME queries SEG109-SME-012, SEG116-SME-007 and SEG132-SME-006, and the rule-writing principle; reworded SEG109-R-001, SEG109-R-003 and SEG116-R-005 by field number and made SEG132-R-001 PROVISIONAL. |
