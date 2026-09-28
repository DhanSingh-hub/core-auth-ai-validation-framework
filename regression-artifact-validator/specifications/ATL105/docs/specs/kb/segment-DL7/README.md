# Segment DL7 — Supplemental Terminal Data Segment

**Specification:** ATL105 2026-3, Section 12.48 · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17588-17637 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-4 blocked on `SEGDL7-SME-001`/`SEGDL7-SME-002`)

## Learning Module Index

- [Segment Flow](segment-DL7-flow.md)
- [SME/TBA Learning Note](segment-DL7-sme-tba-learning-note.md)
- [SME/TBA Input Register](segment-DL7-sme-tba-input-register.md)
- [AI-vs-Test Requirement Comparison](segment-DL7-ai-vs-test-requirement-comparison.md)
- [Coverage Package](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Serialization Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL7 |
| Name | Supplemental Terminal Data Segment |
| Max Length | Not stated (variable, per Segment Length Indicator) |
| Framing | Data Type Indicator `^` + Segment Length Indicator (hybrid — NO End-of-Data Indicator) |
| Content | `<tag><len><data>` TLV Download Data, per Appendix W |

## Field Layout

| Field | Element | Name | Len | R/O/C | Source |
| --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Fixed `^` |
| 2 | 84 | Segment Length Indicator (excl. Data Type Indicator) | 3 | R | Device |
| 3 | 232 | Download Data (`<tag><len><data>` TLV) | 100 | R | — |

## Rule Set

| Rule ID | Title | Class |
| --- | --- | --- |
| SEGDL7-R-001 | Hybrid Data-Type + Segment-Length framing, no End-of-Data marker | structure |
| SEGDL7-R-002 | Segment Length excludes Data Type Indicator's length | field |
| SEGDL7-R-003 | Download Data TLV per Appendix W | field |

## [PROVISIONAL] Items

- P-01: Appendix W (Download Data Layout) not yet in scope (`SEGDL7-SME-001`).
- P-02: No dedicated Segment DL7 AI/Test package located (`SEGDL7-SME-002`).

## Do-Not-Assume Rules

- Do not assume Segment DL7 uses an End-of-Data Indicator like DL1-DL6 — it does not.
- Do not assume the Segment Length Indicator counting convention (exclusive of Data Type Indicator) generalizes to numbered segments without checking each segment's own text.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-DL7-rule-catalog.json) (3 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment DL7 |
|---|---|
| SME/TBA learning note | [Learning note](segment-DL7-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-DL7-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL7-business-requirements.md](segment-DL7-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL7-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL7-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL7-ai-vs-test-requirement-comparison.md) |
