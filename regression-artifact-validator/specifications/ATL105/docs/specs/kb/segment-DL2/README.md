# Segment DL2 — Dial String Data Segment

**Specification:** ATL105 2026-3, Section 12.43 · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17208-17302 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-3 blocked on `SEGDL2-SME-002`)

## Learning Module Index

- [Segment Flow](segment-DL2-flow.md)
- [SME/TBA Learning Note](segment-DL2-sme-tba-learning-note.md)
- [SME/TBA Input Register](segment-DL2-sme-tba-input-register.md)
- [AI-vs-Test Requirement Comparison](segment-DL2-ai-vs-test-requirement-comparison.md)
- [Coverage Package](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Serialization Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL2 |
| Name | Dial String Data Segment |
| Max Length | 69 alphanumeric |
| Origin | BUYPASS (Host) |
| Framing | Data Type Indicator `!` ... End-of-Data Indicator `~` (no Field Separators) |

## Field Layout

| Field | Element | Name | Len | R/O/C | Source |
| --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Host (fixed `!`) |
| 2 | 28 | Dial String Type | 1 | R | Host (fixed `1`) |
| 3 | 82 | Redial Count (primary) | 1 | R | Host |
| 4 | 1 | Access Code (primary) | 12 | C | Host |
| 5 | 66 | Pause Indicator (primary) | 1 | C | Host (fixed `B`) |
| 6 | 75 | Phone Number (primary) | 18 | R | Host |
| 7 | 27 | Dial String Terminator (primary) | 1 | R | Host (fixed `A`) |
| 8-11 | 82,1,66,75 | Secondary Phone Number block (mirrors 3-6) | — | R/C | Host |
| 12 | 27 | Dial String Terminator (secondary) | 1 | R | Host (fixed `F`) |
| 13 | 34 | End-of-Data Indicator | 1 | R | Host (fixed `~`) |

## Rule Set

| Rule ID | Title | Class |
| --- | --- | --- |
| SEGDL2-R-001 | Max length 69, framing markers | structure |
| SEGDL2-R-002 | Primary phone number block required | field |
| SEGDL2-R-003 | Secondary phone number fallback logic | lifecycle |

## [PROVISIONAL] Items

- P-01: Asynchronous Communications Protocol Specifications (fallback logic) not yet in scope (`SEGDL2-SME-001`).
- P-02: No dedicated Segment DL2 AI/Test package located (`SEGDL2-SME-002`).

## Do-Not-Assume Rules

- Do not assume the primary→secondary fallback trigger conditions without confirming the Asynchronous Communications Protocol Specifications document.
- Do not assume Access Code/Pause Indicator are always populated — they are Conditional.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-DL2-rule-catalog.json) (3 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment DL2 |
|---|---|
| SME/TBA learning note | [Learning note](segment-DL2-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-DL2-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL2-business-requirements.md](segment-DL2-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL2-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL2-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL2-ai-vs-test-requirement-comparison.md) |
