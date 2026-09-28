# Segment DL8 — EMV Terminal Floor Limits Data Segment

**Specification:** ATL105 2026-3, Section 12.49 · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17636-17650 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-3 blocked on `SEGDL8-SME-001`)

## Learning Module Index

- [Segment Flow](segment-DL8-flow.md)
- [SME/TBA Learning Note](segment-DL8-sme-tba-learning-note.md)
- [SME/TBA Input Register](segment-DL8-sme-tba-input-register.md)
- [AI-vs-Test Requirement Comparison](segment-DL8-ai-vs-test-requirement-comparison.md)
- [Coverage Package](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Serialization Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL8 |
| Name | EMV Terminal Floor Limits Data Segment |
| Max Length | Variable, up to 624 bytes of repeating Floor Limit Data (max 24 RIDs) |
| Framing | Data Type Indicator `%` + Segment Length Indicator (hybrid — NO End-of-Data Indicator) |
| Applicability | Gated by a terminal-level "Special" flag; maintained at BUYPASS Host |

## Field Layout

| Field | Element | Name | Len | R/O/C | Source |
| --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Fixed `%` |
| 2 | 84 | Segment Length Indicator (excl. Data Type Indicator) | 3 | R | Device |
| 3 | 233 | RID (repeats 1-24×) | 10 | R | Host |
| 4 | 234 | Stand-in Indicator (repeats 1-24×) | 1 | R | Host |
| 5 | 235 | Floor Limit (repeats 1-24×) | 12 | R | Host |
| 6 | 236 | BUYPASS RID Card Type (repeats 1-24×) | 3 | R | Host |

## Rule Set

| Rule ID | Title | Class |
| --- | --- | --- |
| SEGDL8-R-001 | Special-flag-gated table load inclusion | lifecycle |
| SEGDL8-R-002 | Hybrid framing, same convention as DL7 | structure |
| SEGDL8-R-003 | Per-RID repeating structure, max 24/624 bytes | structure |

## [PROVISIONAL] Items

- P-01: No dedicated Segment DL8 AI/Test package located (`SEGDL8-SME-001`).

## Do-Not-Assume Rules

- Do not assume Segment DL8 is always present in a table load — it is gated by the terminal-level Special flag.
- Do not assume Segment DL8 uses an End-of-Data Indicator — it uses the DL7-style Segment Length Indicator framing instead.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-DL8-rule-catalog.json) (3 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment DL8 |
|---|---|
| SME/TBA learning note | [Learning note](segment-DL8-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-DL8-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL8-business-requirements.md](segment-DL8-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL8-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL8-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL8-ai-vs-test-requirement-comparison.md) |
