# Segment DL6 — Store and Forward Data Segment

**Specification:** ATL105 2026-3, Section 12.47 · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17532-17588 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-3 blocked on `SEGDL6-SME-002`)

## Learning Module Index

- [Segment Flow](segment-DL6-flow.md)
- [SME/TBA Learning Note](segment-DL6-sme-tba-learning-note.md)
- [SME/TBA Input Register](segment-DL6-sme-tba-input-register.md)
- [AI-vs-Test Requirement Comparison](segment-DL6-ai-vs-test-requirement-comparison.md)
- [Coverage Package](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Serialization Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL6 |
| Name | Store and Forward Data Segment |
| Max Length | 9 alphanumeric |
| Origin | BUYPASS (Host) |
| Framing | Data Type Indicator `\` ... End-of-Data Indicator `~` (no Field Separators) |
| Applicability | Table Load Response only, and only when Segment DL1's Card Type (Element 14) = `173` |

## Field Layout

| Field | Element | Name | Len | R/O/C | Source |
| --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Host (fixed `\`) |
| 2 | 166 | Start Time (HHMM) | 4 | R | Host |
| 3 | 166 | End Time (HHMM) | 4 | R | — |
| 4 | 34 | End-of-Data Indicator | 1 | R | Host (fixed `~`) |

## Rule Set

| Rule ID | Title | Class |
| --- | --- | --- |
| SEGDL6-R-001 | Conditional on Segment DL1 Card Type 173 | applicability |
| SEGDL6-R-002 | Max length 9, framing markers | structure |
| SEGDL6-R-003 | Start/End Time share Element 166 | field |

## [PROVISIONAL] Items

- P-01: Confirm Element 166 reuse for both Start Time and End Time (`SEGDL6-SME-001`).
- P-02: No dedicated Segment DL6 AI/Test package located (`SEGDL6-SME-002`).

## Do-Not-Assume Rules

- Do not assume Segment DL6 is always present in a Table Load Response — it is strictly conditional on Segment DL1's Card Type 173.
- Do not assume the Element 166 duplication across Start/End Time is a typo without SME confirmation.
