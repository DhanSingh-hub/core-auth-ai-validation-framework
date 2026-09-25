# Common LLM Segment Training Strategy

**Specification:** BUYPASS ATL105 2026-3
**Applies to:** Every ATL105 data segment, numbered segment, and download segment (`DL1`-`DL8`)
**System under test:** AI Solution artifacts
**Independent oracle:** Core Auth Test Solution rules derived from the specification

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

All SME/TBA outcomes for AI-only BRs, draft Test Solution rules, crosswalks, and traceability chains must be recorded in the append-only `ai-only-sme-decision-register.json` before they affect coverage or training status. Each decision records the subject, segment, reviewer, date, evidence, rationale, and decision status. `PENDING` is allowed without review evidence and never counts toward coverage. `CONFIRMED_MATCH` and `NEW_RULE` require canonical source-anchor evidence plus reviewer/date; heuristic similarity or AI confidence is never sufficient. Decisions must not modify immutable AI input files.

The register is validated by `ValidateSmeDecisionRegister`; a decision register is invalid if decision IDs are duplicated, required evidence is absent, a promoted decision has no reviewer/date, or a promoted `CONFIRMED_MATCH`/`NEW_RULE` lacks a complete canonical source anchor.

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

## Common Nine-Phase Strategy

### Phase 1: Source Inventory

Capture specification version, section, page, segment, element, rule, applicability, source text, and ambiguity status. Create canonical source anchors before generating BRs.

### Phase 2: Segment Knowledge Model

Model the segment's identity, field order, requiredness, data type, length, valid values, separators, serialization, message family, request/response role, and external dependencies.

### Phase 3: Context Matrix

Record the dimensions that change segment behavior:

- Message category and transaction type
- Request or response role
- Card type, payment network, and entry mode
- Companion and mutually exclusive segments
- Lifecycle role and original transaction relationship
- Merchant, terminal, program, or specialized domain
- Appendix, code-table, and external-reference dependencies

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

### Phase 6: Lifecycle and Specialized-Flow Training

Train the LLM to preserve message order, sequence identifiers, original references, retries, reversals, completions, voids, cancellations, and follow-up dependencies. Keep specialized domains conditional, including EBT/eWIC, EMV, tokenization, wallets, fleet, loyalty, Moneris, TransArmor, and download flows.

### Phase 7: Serialization and Mutation

Validate field order, separators, segment lengths, message lengths, counts, encoded values, binary/network values, and omission rules. Apply deliberate mutations to valid data and verify that the Test Solution detects the intended rule violation.

### Phase 8: Independent AI Artifact Intake

Preserve original AI files and metadata. Normalize them into canonical models, then validate:

```text
BR -> TS -> TC -> request Test Data JSON
```

Report missing, duplicate, unsupported, malformed, contradictory, and review-required artifacts. AI-produced coverage is a claim under test, not the denominator.

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

## Segment Package Field-Name Conformance

BR -> TS -> TC -> TD chain matching (Phase 5, Phase 8, Phase 8a) requires every segment's Test Solution package to use the same canonical link fields: `businessRequirements[].id`, `testScenarios[].requirementIds` (array), `testCases[].scenarioIds` (array), and `testData[].testCaseIds` (array) with a real `payload`. A package that uses a different shape (for example `covers` instead of `requirementIds`, a singular `scenarioId` instead of `scenarioIds`, or a narrative `testDataStatus` string instead of a `testData[]` array) silently breaks chain matching even when the rule catalog and prose are correct. `segment-111-core-structure-package.json` is a known example needing this normalization. Verify field-name conformance as part of a segment's completion gate.

Before treating any `TestSolutionJsonFormatTest`-style package-conformance result as authoritative, run `mvn clean test` (not an incremental `test`). A stale compiled test class can report failures for exclusions or field names that the current source already handles correctly, producing a false-positive gap report.

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

A segment is not certified merely because its folder, README, or rule catalog exists.
