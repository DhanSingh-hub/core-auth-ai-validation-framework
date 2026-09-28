# Segment DL3 — Date and Time Data Segment

**Specification:** ATL105 2026-3, Section 12.44 · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17302-17364 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-3 blocked on `SEGDL3-SME-001`)

## Learning Module Index

- [Segment Flow](segment-DL3-flow.md)
- [SME/TBA Learning Note](segment-DL3-sme-tba-learning-note.md)
- [SME/TBA Input Register](segment-DL3-sme-tba-input-register.md)
- [AI-vs-Test Requirement Comparison](segment-DL3-ai-vs-test-requirement-comparison.md)
- [Coverage Package](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Serialization Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL3 |
| Name | Date and Time Data Segment |
| Max Length | 23 alphanumeric |
| Origin | BUYPASS (Host) |
| Framing | Data Type Indicator `:` ... End-of-Data Indicator `~` (no Field Separators) |

## Field Layout

| Field | Element | Name | Len | R/O/C | Source |
| --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Host (fixed `:`) |
| 2 | 25 | Day of the Week | 1 | R | Host |
| 3 | 21 | Current Date | 6 | R | Host |
| 4 | 22 | Current Time | 4 | R | Host |
| 5 | 23 | Cut Time | 4 | R | Host |
| 6 | 65 | Password | 6 | R | **Device** |
| 7 | 34 | End-of-Data Indicator | 1 | R | Host (fixed `~`) |

## Rule Set

| Rule ID | Title | Class |
| --- | --- | --- |
| SEGDL3-R-001 | Max length 23, framing markers | structure |
| SEGDL3-R-002 | Host-sourced datetime fields required | field |
| SEGDL3-R-003 | Password sole Device-sourced field | field |

## [PROVISIONAL] Items

- P-01: No dedicated Segment DL3 AI/Test package located (`SEGDL3-SME-001`).

## Do-Not-Assume Rules

- Do not assume Password is Host-sourced like every other field in this segment — it is Device-sourced.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-DL3-rule-catalog.json) (3 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment DL3 |
|---|---|
| SME/TBA learning note | [Learning note](segment-DL3-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-DL3-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL3-business-requirements.md](segment-DL3-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL3-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL3-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL3-ai-vs-test-requirement-comparison.md) |
