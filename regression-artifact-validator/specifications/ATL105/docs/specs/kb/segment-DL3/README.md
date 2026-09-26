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
