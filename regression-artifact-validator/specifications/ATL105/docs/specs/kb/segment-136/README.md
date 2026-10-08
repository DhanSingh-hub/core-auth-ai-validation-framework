# Segment 136 (Moneris Data (Response) Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.25 (page 12-61/275)
**Item Progress:** Item 1 in progress; 0 of 4 SME items resolved — see [SME/TBA Input Register](segment-136-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-136-sme-tba-learning-note.md) · [Flow](segment-136-flow.md) · [AI-vs-Test Comparison](segment-136-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-136-rule-catalog.json) · [SME/TBA Input Register](segment-136-sme-tba-input-register.md)

## Segment Definition

Response-side Moneris data segment, used for both financial transactions and Key Load transactions.

## Rule Set (5 rules)

Applicability (Moneris financial/Key Load), Segment Type fixed 136 (sourcing provisional), Segment Length, separator behavior (provisional), and Moneris Data TLV field. Appendix V's response-table layouts are trained in the [Appendix V module](../appendix-v/README.md); this does not resolve Segment 136's separate sourcing or separator questions.

## `[PROVISIONAL]` Items — All 4 Open

See [SME/TBA Input Register](segment-136-sme-tba-input-register.md).

## Do-Not-Assume Rules

1. Do not assume Segment 136's separator behavior mirrors Segment 135's exactly — it is less detailed in the spec.
2. Do not silently "correct" the Device-sourced Segment Type/Length fields without SME confirmation.
3. Use the bounded [Appendix V oracle](../appendix-v/README.md) for the published Moneris table layouts; do not infer additional Moneris implementation rules from those layouts.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-136-rule-catalog.json) (5 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 136 |
|---|---|
| SME/TBA learning note | [Learning note](segment-136-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-136-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-136-business-requirements.md](segment-136-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-136-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-136-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-136-ai-vs-test-requirement-comparison.md) |
