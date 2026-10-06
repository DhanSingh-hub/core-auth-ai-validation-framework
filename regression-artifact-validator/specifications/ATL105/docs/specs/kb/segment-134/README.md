# Segment 134 (Transaction Attributes Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3
**Source Section:** 12.23 Transaction Attributes Data Segment (pages 12-59/273 to 274)
**Training Handbook:** [ATL105 Segment Training Handbook](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)
**Item Progress:** Item 1 in progress; 0 of 3 SME items resolved — see [SME/TBA Input Register](segment-134-sme-tba-input-register.md)

## Learning Module Index

- [SME and Technical Business Analysis Note](segment-134-sme-tba-learning-note.md)
- [Segment 134 End-to-End Flow](segment-134-flow.md)
- [AI-Generated vs Test-Generated Requirement Comparison](segment-134-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md)
- [Companion-Segment Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) / [Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format](serialization-wire-format/serialization-wire-format-sme-tba-note.md) / [Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Segment 134 Rule Catalog](coverage/segment-134-rule-catalog.json)
- [SME/TBA Input Register](segment-134-sme-tba-input-register.md)

## 1. Segment Definition

| Attribute | Value |
|---|---|
| Segment number | 134 |
| Purpose | Carries transaction-disposition metadata (settlement type, signature requirement, receipt card description) |
| Origin | BUYPASS (Host) |
| Segment length range | 01–19 alphanumeric characters (exact field-sum reconciliation) |
| Wire format | No Field Separators — fixed-length, same pattern as Segment 131 |

## 2. Field Layout (5 fields — 7 rules total, see [rule catalog](coverage/segment-134-rule-catalog.json))

Segment Type(85, fixed 134), Segment Length(84, `[PROVISIONAL]` possibly 4-digit), Settlement Type(198: D/S/X), Signature Required(199: T/F/Space), Receipt Card Description(200, 10 chars left-justified space-filled).

## 3. `[PROVISIONAL]` Items — All 3 Open

See [SME/TBA Input Register](segment-134-sme-tba-input-register.md): 4-digit Segment Length classification, Signature Debit merchant-configuration scope, missing AI/Test artifact package.

## 4. Do-Not-Assume Rules

1. Do not insert Field Separators anywhere in Segment 134.
2. Do not treat Settlement Type `X` (Non-traditional Signature Debit) as universally valid — it is merchant-configuration-gated.
3. Do not silently add Segment 134 to the confirmed "seven 4-digit-length segments" family without SME confirmation.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-134-rule-catalog.json) (7 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 134 |
|---|---|
| SME/TBA learning note | [Learning note](segment-134-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-134-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-134-business-requirements.md](segment-134-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-134-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-134-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-134-ai-vs-test-requirement-comparison.md) |
