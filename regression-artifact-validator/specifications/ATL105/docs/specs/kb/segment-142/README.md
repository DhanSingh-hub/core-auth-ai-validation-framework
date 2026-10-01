# Segment 142 (Moneris Day End Batch Close (Response) Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.29 (page 12-67/281)
**Item Progress:** Item 1 in progress; 0 of 1 SME item resolved — see [SME/TBA Input Register](segment-142-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-142-sme-tba-learning-note.md) · [Flow](segment-142-flow.md) · [AI-vs-Test Comparison](segment-142-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-142-rule-catalog.json) · [SME/TBA Input Register](segment-142-sme-tba-input-register.md)

## Segment Definition

Final segment in the Moneris day-end sequence: responds to Segment 141 with a reiteration of the first 6 fields, a Moneris text response, and a MAC that validates the response at the terminal.

## Rule Set (3 rules)

Reiteration-plus-text-response structure, Host-sourced Segment Type/Length (contrast Segment 140), mixed field sourcing with MAC.

## Do-Not-Assume Rules

1. Do not assume Segment 142's Segment Type/Length sourcing matches Segment 140's — they differ (Host vs Device).
2. Do not validate the MAC field only for length — cryptographic validation is a genuine, separate concern (potentially `EXTERNAL_FIXTURE_REQUIRED`).

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-142-rule-catalog.json) (3 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 142 |
|---|---|
| SME/TBA learning note | [Learning note](segment-142-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-142-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-142-business-requirements.md](segment-142-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-142-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-142-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-142-ai-vs-test-requirement-comparison.md) |
