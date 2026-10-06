# Segment 148 (WEX Available Product Fleet Information Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.33 (page 12-87/301)
**Item Progress:** Item 1 in progress; 0 of 2 SME items resolved — see [SME/TBA Input Register](segment-148-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-148-sme-tba-learning-note.md) · [Flow](segment-148-flow.md) · [AI-vs-Test Comparison](segment-148-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-148-rule-catalog.json) · [SME/TBA Input Register](segment-148-sme-tba-input-register.md)

## Segment Definition

Sends WEX fuel-restriction information to the terminal in a WEX Financial Transaction response. Available Product Information is a fixed 17-character sub-structure with 9 documented fuel-restriction codes.

## Rule Set (4 rules)

WEX-response-only applicability, Segment Type/Length, max-length reconciliation (provisional), fixed sub-structure format.

## Do-Not-Assume Rules

1. Do not certify a maximum length without resolving the 23/17/999 discrepancy.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-148-rule-catalog.json) (4 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 148 |
|---|---|
| SME/TBA learning note | [Learning note](segment-148-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-148-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-148-business-requirements.md](segment-148-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-148-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-148-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-148-ai-vs-test-requirement-comparison.md) |
