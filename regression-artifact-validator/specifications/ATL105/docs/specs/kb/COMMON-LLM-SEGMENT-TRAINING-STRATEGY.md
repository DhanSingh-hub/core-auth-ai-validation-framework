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

### Phase 9: Converter and Execution Readiness

Run the external converter or serializer only after intake, traceability, semantic, and review gates pass. Compare serialized request output with the canonical intent and source rules.

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

## Common Training Outputs

Every segment should eventually produce:

- Knowledge model and source-anchor catalog
- Independent rule catalog
- BR catalog
- Scenario catalog
- Test Case catalog
- Request Test Data JSON catalog
- Traceability matrix
- Independent coverage report
- Mutation evidence
- Manual-review queue
- Final validation decision

## Completion Gate

A segment is training-ready for AI artifact intake only when its addendum has a source catalog, applicability/context matrix, BR-to-TS-to-TC-to-request-data chain, positive and negative examples, lifecycle model where applicable, mutation set, review boundaries, and independent coverage denominator.

A segment is not certified merely because its folder, README, or rule catalog exists.
