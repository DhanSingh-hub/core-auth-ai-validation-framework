# Segment 150 (Fuel Price Update Response Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.35 (page 12-90/303)
**Item Progress:** Item 1 in progress; 0 of 2 SME items resolved

## Learning Module Index

- [SME/TBA Learning Note](segment-150-sme-tba-learning-note.md) · [Flow](segment-150-flow.md) · [AI-vs-Test Comparison](segment-150-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-150-rule-catalog.json) · [SME/TBA Input Register](segment-150-sme-tba-input-register.md)

## Segment Definition

Responds to Segment 149 with a Decline Code indicating success/failure of the fuel price update.

## Rule Set (3 rules)

Response-to-149 applicability, separator behavior (provisional), field layout.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-150-rule-catalog.json) (3 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 150 |
|---|---|
| SME/TBA learning note | [Learning note](segment-150-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-150-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-150-business-requirements.md](segment-150-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-150-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-150-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-150-ai-vs-test-requirement-comparison.md) |
