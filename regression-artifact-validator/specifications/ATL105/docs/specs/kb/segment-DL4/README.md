# Segment DL4 — Software Dial Load Data Segment

**Specification:** ATL105 2026-3, Section 12.45 · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17363-17447 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-3 blocked on `SEGDL4-SME-001`)

## Learning Module Index

- [Segment Flow](segment-DL4-flow.md)
- [SME/TBA Learning Note](segment-DL4-sme-tba-learning-note.md)
- [SME/TBA Input Register](segment-DL4-sme-tba-input-register.md)
- [AI-vs-Test Requirement Comparison](segment-DL4-ai-vs-test-requirement-comparison.md)
- [Coverage Package](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Serialization Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL4 |
| Name | Software Dial Load Data Segment |
| Max Length | 52 alphanumeric |
| Origin | BUYPASS (Host) |
| Framing | Data Type Indicator `@` ... End-of-Data Indicator `~` (no Field Separators) |
| Applicability | BUYPASS-managed device management systems only; not used by vendor-managed devices |

## Field Layout

| Field | Element | Name | Len | R/O/C | Source |
| --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Host (fixed `@`) |
| 2 | 57 | New Software Version | 8 | R | Host |
| 3 | 95 | Software Terminal Record ID | 13 | R | Host |
| 4 | 91 | Software Load Phone Number | 18 | R | Host |
| 5 | 92 | Software Load Request Date | 6 | R | Host |
| 6 | 93 | Software Load Request Time | 4 | R | Host |
| 7 | 94 | Software Load Type | 1 | R | Host |
| 8 | 34 | End-of-Data Indicator | 1 | R | Host (fixed `~`) |

## Rule Set

| Rule ID | Title | Class |
| --- | --- | --- |
| SEGDL4-R-001 | BUYPASS-managed devices only | applicability |
| SEGDL4-R-002 | Max length 52, framing markers | structure |
| SEGDL4-R-003 | Software dial load schedule fields required | field |

## [PROVISIONAL] Items

- P-01: No dedicated Segment DL4 AI/Test package located (`SEGDL4-SME-001`).

## Do-Not-Assume Rules

- Do not assume Segment DL4 applies to all devices — vendor-managed devices never use it.
- Do not assume Segment DL4 and DL5 co-occur — they are mutually exclusive delivery mechanisms.
