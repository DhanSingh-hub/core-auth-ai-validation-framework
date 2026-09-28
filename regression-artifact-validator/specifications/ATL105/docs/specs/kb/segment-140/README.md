# Segment 140 (Moneris Day End Batch Balance (Response) Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.27 (page 12-63/277)
**Item Progress:** Item 1 in progress; 0 of 1 SME item resolved — see [SME/TBA Input Register](segment-140-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-140-sme-tba-learning-note.md) · [Flow](segment-140-flow.md) · [AI-vs-Test Comparison](segment-140-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-140-rule-catalog.json) · [SME/TBA Input Register](segment-140-sme-tba-input-register.md)

## Segment Definition

Response to Segment 139: reiterates the first 6 request fields, then appends Moneris debit/credit/correction totals. Richest field count (14) of any segment in this training batch.

## Rule Set (3 rules)

Reiteration-plus-totals structure, mixed Device/Moneris field sourcing, signed decimal dollar-value format (`+/-9(16)v99`).

## Do-Not-Assume Rules

1. Do not assume uniform field sourcing — Segment 140 genuinely alternates between Device (echo) and Moneris (new data).
2. Do not validate dollar-value fields as plain numerics — they use a signed, implied-decimal format.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-140-rule-catalog.json) (3 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 140 |
|---|---|
| SME/TBA learning note | [Learning note](segment-140-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-140-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-140-business-requirements.md](segment-140-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-140-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-140-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-140-ai-vs-test-requirement-comparison.md) |
