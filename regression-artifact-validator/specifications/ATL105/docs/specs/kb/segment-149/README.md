# Segment 149 (Fuel Price Update Request Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.34 (page 12-89/302)
**Item Progress:** Item 1 in progress; 0 of 1 SME item resolved

## Learning Module Index

- [SME/TBA Learning Note](segment-149-sme-tba-learning-note.md) · [Flow](segment-149-flow.md) · [AI-vs-Test Comparison](segment-149-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-149-rule-catalog.json) · [SME/TBA Input Register](segment-149-sme-tba-input-register.md)

## Segment Definition

Comdata fuel price update request; pairs with Segment 150 for the response. Field Separators per-field plus trailing; Price Data's internal tag:value pairs are pipe-delimited, not FS-delimited.

## Rule Set (3 rules)

Comdata applicability, separator pattern with internal exception, field layout.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-149-rule-catalog.json) (3 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 149 |
|---|---|
| SME/TBA learning note | [Learning note](segment-149-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-149-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-149-business-requirements.md](segment-149-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-149-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-149-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-149-ai-vs-test-requirement-comparison.md) |
