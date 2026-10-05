# Segment DL7 — Supplemental Terminal Data Segment

**Specification:** ATL105 2026-3, Section 12.48 (layout), Appendix W (Download Data Layout), 13.2 (Elements 24, 84, 232) · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17587-17634, 35814-35830 · **Rules:** 6 · **Item Progress:** candidate package/report complete, review required; mutation execution and executable validator remain blocked by open SME decisions and approved fixtures.

**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md). Sibling framing: [Segment DL8](../segment-DL8/README.md).

## Learning Module Index

| Segment 100 component | Segment DL7 |
|---|---|
| End-to-end flow | [Flow](segment-DL7-flow.md) |
| SME/TBA learning note | [Learning note](segment-DL7-sme-tba-learning-note.md) |
| Prompt-code analogue (inclusion decision) | [Applicability note](applicability-decision-sme-tba-note.md) · [Flow](applicability-decision-flow.md) |
| Account-number analogue (core data) | [Field definitions note](field-definitions-sme-tba-note.md) · [Flow](field-definitions-flow.md) |
| Partial-approval analogue (conditional feature) | [Conditional dependencies note](conditional-dependency-rules-sme-tba-note.md) · [Flow](conditional-dependency-rules-flow.md) |
| Sequence-lifecycle analogue | [Lifecycle note](lifecycle-response-correlation-sme-tba-note.md) · [Flow](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL7-business-requirements.md](segment-DL7-business-requirements.md) |
| Companion compatibility | [Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Flow](companion-compatibility/companion-segment-compatibility-flow.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL7-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL7-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL7-ai-vs-test-requirement-comparison.md) |

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL7 |
| Name | Supplemental Terminal Data Segment |
| Max Length | Not stated; 1 + 3 + Download Data (≤ 100) |
| Framing | Data Type Indicator `^` + 3-digit Segment Length (exclusive of `^`); **no End-of-Data Indicator** |
| Content | Download Data as `<tag><len><data>`: Table ID n3 + Table Length n3 + Table Data (Appendix W) |
| Message | Not defined in 11.7.1.2 or the Chapter 12 matrix (`SEGDL7-SME-004`) |

## Field Layout

| Field | Element | Name | Len | R/O/C | Type / valid values | Source |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Fixed `^` | — |
| 2 | 84 | Segment Length Indicator | 3 | R | N, excludes `^` | Device (sic — `SEGDL7-SME-005`) |
| 3 | 232 | Download Data | ≤ 100 | R | `<tag><len><data>` per Appendix W | — |

## Appendix W Tables

| Table ID | Name | Table Length | Table Data |
|---|---|---|---|
| `001` | Site Language | `003` | ISO 639-2 language code of the terminal |
| `002` | Postal Code | `013` | International postal code of the transmitting device |

## Rule Set

| Rule ID | Title | Class | Status |
| --- | --- | --- | --- |
| SEGDL7-R-001 | `^` + Segment Length framing, no `~` | structure | REVIEW_REQUIRED (P-04) |
| SEGDL7-R-002 | Segment Length excludes `^` | field | REVIEW_REQUIRED (P-05) |
| SEGDL7-R-003 | Download Data TLV per Appendix W | field | REVIEW_REQUIRED (P-01) |
| SEGDL7-R-004 | Appendix W tables 001 and 002 | field | REVIEW_REQUIRED (P-03) |
| SEGDL7-R-005 | Download Data AN ≤ 100, "all or some" | field | REVIEW_REQUIRED (P-04) |
| SEGDL7-R-006 | Segment Length 3 digits, matches content | structure | REVIEW_REQUIRED (P-05) |

## [PROVISIONAL] Items

- P-01 `SEGDL7-SME-001`: Appendix W scope (now transcribed).
- P-02 `SEGDL7-SME-002`: No dedicated DL7 AI/Test package; approve synthesized fixtures.
- P-03 `SEGDL7-SME-003`: Appendix W `an1` attribute vs Table Lengths 003/013.
- P-04 `SEGDL7-SME-004`: Which message carries DL7; can Download Data span several DL7s.
- P-05 `SEGDL7-SME-005`: Segment Length counting and Device source.

## Do-Not-Assume Rules

- Do not expect `~` at the end of DL7 — it has none.
- Do not assume DL7 belongs to the Table Load Response — the layout does not list it.
- Do not assume Table IDs other than `001` and `002` are valid.
