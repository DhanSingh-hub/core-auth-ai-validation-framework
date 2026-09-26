# Segment 100 Training Methodology
## Detailed Reference for the Common LLM Segment Strategy

**Specification:** ATL105 2026-3  
**System under test:** AI Solution artifacts  
**Independent oracle:** Test Solution rules derived from the specification

> The common strategy for all ATL105 segments is [Common LLM Segment Training Strategy](../specs/kb/COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md). This document remains the detailed Segment 100 reference implementation. Other segments should follow the common strategy and maintain their own segment-specific addendum.

## 1. Boundary

The AI Solution generates:

```text
BR -> TS -> TC -> converter-ready request/response JSON
```

The Test Solution independently validates those artifacts. Test Solution fixtures are evidence for validator behavior, not AI output and not Fiserv production test data.

## 1.1 Mandatory Test Solution Training Rule

Every person, agent, or automation process that trains or updates the Test Solution must follow the same evidence-first process. No training instruction, prompt, example, generated artifact, or review decision may bypass a phase or silently change a previously agreed rule.

The required process is:

1. Identify the authoritative ATL105 source and record its page, section, segment, element, rule, applicability, and ambiguity status.
2. Create or update the canonical source anchor before creating a business requirement.
3. State the condition, behavior, valid representation, invalid representation, expected result, and exception boundary.
4. Create the BR -> TS -> TC -> TD chain independently from the AI Solution.
5. Mark unresolved, conflicting, or SME-dependent information as `REVIEW_REQUIRED`; never infer approval.
6. Validate schema, source anchors, links, execution readiness, negative coverage, and mutation detection.
7. Record the evidence and validation result before accepting the training outcome.

This rule also applies when a test team member supplies new knowledge to an AI assistant. The assistant must request or identify the authoritative evidence, preserve the source anchor, distinguish fact from inference, and follow the same sequence. A training statement without evidence is guidance to investigate, not an accepted Test Solution rule.

The Test Solution must remain independent: AI-generated requirements, scenarios, test cases, and test data may be compared after intake, but must not be copied into the Test Solution or used as its training oracle.

## 1.2 Matching Responsibility

Matching AI output to the Test Solution is the responsibility of the Test Solution validation framework. The AI Solution is an input producer and may provide its own local identifiers and claimed mappings, but it must not determine whether its output is covered.

The framework must:

- normalize both producers into the canonical artifact schema;
- compare shared `sourceAnchors` using deterministic canonical keys;
- confirm a match only when the anchor and business rule are equivalent;
- classify partial, ambiguous, conflicting, or heuristic candidates as `REVIEW_REQUIRED`;
- report `AI_ONLY`, `TEST_ONLY`, duplicate-source, malformed, and unresolved records;
- preserve both producer-local IDs and the evidence supporting every disposition.

Text similarity, shared field numbers, matching terminology, and AI-provided confidence may identify candidates for review, but may never produce `CONFIRMED` coverage.

## 2. Canonical BR Taxonomy

Use the baseline index as the organizing contract:

[Segment 100 BR baseline index](../../test-output/test-json/knowledge/segment-100-br-baseline-index.json)

1. `CORE-STRUCTURE`: segment identity, field order, lengths, separators, serialization.
2. `CORE-FIELDS`: terminal, Prompt Code, account, amounts, sequence, approval, time, partial approval.
3. `TRANSACTION-TYPES`: financial transaction types, card types, Prompt Code composition.
4. `LIFECYCLE`: completion, cancellation, reversal, void, refund, timeout, TOR, retries.
5. `EBT-EWIC`: Segment 103, eWIC operations, WIC/EBT program data.
6. `RESPONSES`: response code, approval, decline, partial approval, and response context.
7. `ANNEXURE-CONDITIONAL`: Appendix A through T rules that apply only under a stated condition.
8. `SEPARATE-DOMAINS`: TransArmor administration, CA keys, digital wallets, Premium Gift Card, and Moneris.

Do not flatten conditional or separate-domain rules into universal Segment 100 rules.

## 3. Training Sequence

### Phase 1: Source Inventory

Capture specification section, page, segment, element, rule, applicability, and ambiguity status. Create canonical anchors before creating BRs.

### Phase 2: Segment Model

Document required, optional, and conditional fields; lengths; types; enumerations; field order; separators; and request/response differences. Produce a machine-readable field inventory with one row for every ordered field, its canonical source rule, JSON representation, appendix dependencies, and validation status.

For Segment 100, the reference inventory is [segment-100-field-knowledge-inventory.json](../../test-output/test-json/knowledge/segment-100-field-knowledge-inventory.json).

### Phase 3: Context Matrix

Map transaction types, card types, POS entry modes, companion segments, response families, and lifecycle relationships. Transaction type alone must not determine the complete message.

Produce an explicit context envelope for every AI artifact. The Segment 100 reference model is [segment-100-context-model.json](../../test-output/test-json/knowledge/segment-100-context-model.json). Required dimensions include transaction type, card type, payment network, lifecycle role, and message family; conditional dimensions include entry mode, POS condition, specialized domain, companion segments, and response context.

### Phase 4: BR Derivation

Each BR must state condition, behavior, valid representation, invalid representation, expected result, exception boundary, and source anchor.

### Phase 5: TS/TC/TD Generation

Every BR gets at least one scenario, each scenario gets a focused test case, and each case gets executable or reviewable test data. Use separate request and response artifacts where applicable.

### Phase 6: Lifecycle and Specialized Flows

Model original and follow-up messages in order. Validate sequence, terminal, account, amount, approval, Prompt Code, timeout, retry, and companion relationships. Keep eWIC, EMV, token, wallet, Moneris, and TransArmor rules conditional.

### Phase 7: Serialization and Mutation

Validate Element 63, segment length, message length, separators, order, binary/network values, and intentional mutations. Target more than 85% mutation detection and require all source-critical mutations to be detected.

### Phase 8: Independent Intake

Preserve AI files unchanged. Compare source anchors, validate BR→TS→TC→TD links, run request/response validators, and report missing, duplicate, malformed, and review-required artifacts.

Specialized appendix artifacts must declare a domain such as `MONERIS`, `DIGITAL_WALLET`, `PAYMENT_TOKEN`, `TRANSARMOR_ADMIN`, `CA_PUBLIC_KEYS`, or `PREMIUM_GIFT_CARD`. The specialized-domain intake gate must reject undeclared domains and must not certify generic Segment 100 JSON as specialized coverage.

### Phase 9: External Converter Gate

Run the real converter only after AI intake and review gates are complete. Compare serialized output with AI intent and source rules.

## 4. Current Segment 100 Evidence Map

- [Core rule catalog](../../docs/specs/kb/segment-100/coverage/segment-100-rule-catalog.json)
- [Annexure BR baseline](../../test-output/test-json/segment-100-annexure-br-baseline-package.json)
- [Appendix coverage records](../../test-output/test-json/)
- [Multi-step flow catalog](../../test-output/test-json/segment-100-multistep-flow-catalog.json)
- [eWIC gap package](../../test-output/test-json/segment-100-ewic-gap-package.json)
- [Response-code package](../../test-output/test-json/segment-100-response-code-package.json)
- [Response family matrix](../../test-output/test-json/atl105-response-code-family-matrix.json)
- [Validation evidence](../../test-output/traceability-matrix/segment-100/segment-100-validation-evidence.md)

## 5. Completion Gate for a New Segment

A segment is ready for AI artifact intake when its source catalog, applicability matrix, BR→TS→TC→TD chain, positive/negative tests, lifecycle model, response model, mutation set, and review boundaries are documented. Final certification still requires actual AI artifacts and the external converter where applicable.
