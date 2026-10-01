# Segment 153 (Network Token Data Request Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.38 (page 12-93/307 to 309)
**Item Progress:** Item 1 in progress; 0 of 1 SME item resolved

## Learning Module Index

- [SME/TBA Learning Note](segment-153-sme-tba-learning-note.md) · [Flow](segment-153-flow.md) · [AI-vs-Test Comparison](segment-153-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-153-rule-catalog.json) · [SME/TBA Input Register](segment-153-sme-tba-input-register.md)

## Segment Definition

TLV-of-TLVs container (mirrors Segment 145/146) with 6 fixed-format Network Token sub-tables.

## Rule Set (3 rules)

Multi-sub-segment structure, Segment Type/Length, 6-table catalog (Element 239, shared number with Segments 145/146 — independent meaning).

## Do-Not-Assume Rules

1. Do not conflate Element 239 here with Segments 145/146's Element 239.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-153-rule-catalog.json) (3 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 153 |
|---|---|
| SME/TBA learning note | [Learning note](segment-153-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-153-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-153-business-requirements.md](segment-153-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-153-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-153-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-153-ai-vs-test-requirement-comparison.md) |
