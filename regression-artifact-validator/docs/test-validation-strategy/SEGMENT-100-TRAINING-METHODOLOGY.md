# Segment 100 Training Methodology
## Reusable Process for Other Segments

**Specification:** ATL105 2026-3  
**System under test:** AI Solution artifacts  
**Independent oracle:** Test Solution rules derived from the specification

## 1. Boundary

The AI Solution generates:

```text
BR -> TS -> TC -> converter-ready request/response JSON
```

The Test Solution independently validates those artifacts. Test Solution fixtures are evidence for validator behavior, not AI output and not Fiserv production test data.

## 2. Canonical BR Taxonomy

Use the baseline index as the organizing contract:

[Segment 100 BR baseline index](../../test-output/test-json/segment-100-br-baseline-index.json)

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

Document required, optional, and conditional fields; lengths; types; enumerations; field order; separators; and request/response differences.

### Phase 3: Context Matrix

Map transaction types, card types, POS entry modes, companion segments, response families, and lifecycle relationships. Transaction type alone must not determine the complete message.

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
