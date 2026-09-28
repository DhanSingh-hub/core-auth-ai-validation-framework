# Segment 151 (Incomm OTC Market Basket Data (Request) Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.36 (page 12-91/305)
**Item Progress:** Item 1 in progress; 0 of 4 SME items resolved

## Learning Module Index

- [SME/TBA Learning Note](segment-151-sme-tba-learning-note.md) · [Flow](segment-151-flow.md) · [AI-vs-Test Comparison](segment-151-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-151-rule-catalog.json) · [SME/TBA Input Register](segment-151-sme-tba-input-register.md)

## Segment Definition

InComm OTC Market Basket request data; can appear in any Data Section 3 field slot. Full DV/PI dataset format is in an external document not yet transcribed into this KB.

## Rule Set (5 rules)

Any-field-position structure, max-length inconsistency (provisional), full-field-separation, origin conflict (provisional), external-format-document dependency (provisional).

## Do-Not-Assume Rules

1. Do not fabricate DV/PI dataset internal structure.
2. Do not assume Device or Host origin without confirmation.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-151-rule-catalog.json) (5 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 151 |
|---|---|
| SME/TBA learning note | [Learning note](segment-151-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-151-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-151-business-requirements.md](segment-151-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-151-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-151-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-151-ai-vs-test-requirement-comparison.md) |
