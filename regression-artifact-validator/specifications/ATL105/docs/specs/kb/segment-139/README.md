# Segment 139 (Moneris Day End Batch Balance (Request) Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.26 (page 12-62/276)
**Item Progress:** Item 1 in progress; 0 of 2 SME items resolved — see [SME/TBA Input Register](segment-139-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-139-sme-tba-learning-note.md) · [Flow](segment-139-flow.md) · [AI-vs-Test Comparison](segment-139-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-139-rule-catalog.json) · [SME/TBA Input Register](segment-139-sme-tba-input-register.md)

## Segment Definition

Retrieves debit totals for the previous period from Moneris, feeding into the day-end batch close (Segment 141). Part of the 139→140→141→142 Moneris day-end sequence.

## Rule Set (4 rules)

Applicability (precedes batch close), Segment Type fixed 139, required fields (Terminal Identifier, SPDH Header, Moneris Terminal/Merchant ID, Batch Number, Language Indicator), separator behavior (provisional).

## `[PROVISIONAL]` Items — Both Open

See [SME/TBA Input Register](segment-139-sme-tba-input-register.md).

## Do-Not-Assume Rules

1. Do not assume Segment 139 can be used independently of the 139→140→141→142 sequence.
2. Do not assume full or absent Field Separator delimiting without SME confirmation.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-139-rule-catalog.json) (4 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 139 |
|---|---|
| SME/TBA learning note | [Learning note](segment-139-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-139-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-139-business-requirements.md](segment-139-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-139-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-139-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-139-ai-vs-test-requirement-comparison.md) |
