# Segment DL1 — Merchant Data Segment

**Specification:** ATL105 2026-3, Section 12.42 · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17101-17208 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-3 blocked on `SEGDL1-SME-001`)

## Learning Module Index

- [Segment Flow](segment-DL1-flow.md)
- [SME/TBA Learning Note](segment-DL1-sme-tba-learning-note.md)
- [SME/TBA Input Register](segment-DL1-sme-tba-input-register.md)
- [AI-vs-Test Requirement Comparison](segment-DL1-ai-vs-test-requirement-comparison.md)
- [Coverage Package](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Serialization Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL1 |
| Name | Merchant Data Segment |
| Max Length | 399 alphanumeric |
| Origin | BUYPASS (Host) |
| Framing | Data Type Indicator `#` ... End-of-Data Indicator `~` (no Field Separators) |

## Field Layout

| Field | Element | Name | Len | R/O/C | Source |
| --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Host (fixed `#`) |
| 2 | 53 | Merchant Name | 24 | R | Host |
| 3 | 98 | Store Number | 16 | R | Host |
| 4 | 3 | Address Line 1 | 24 | R | Host |
| 5 | 4 | Address Line 2 | 21 | R | Host |
| 6 | 54 | Merchant Phone Number | 13 | R | Host |
| 7 | 59 | Number of Card Types | 2 | R | Host |
| 8 | 14 | Card Type (repeats 01-99×) | 3 | R | Host |
| 9 | 34 | End-of-Data Indicator | 1 | R | Host (fixed `~`) |

## Rule Set

| Rule ID | Title | Class |
| --- | --- | --- |
| SEGDL1-R-001 | Max length 399, origin Host | serialization |
| SEGDL1-R-002 | No Field Separators; Data-Type/End-of-Data markers | structure |
| SEGDL1-R-003 | Merchant identity fields required | field |
| SEGDL1-R-004 | Card Type repeats 01-99 per Number of Card Types | structure |
| SEGDL1-R-005 | Card Type 173 triggers Segment DL6 | lifecycle |

## [PROVISIONAL] Items

- P-01: No dedicated Segment DL1 AI/Test package located (`SEGDL1-SME-001`).

## Do-Not-Assume Rules

- Do not assume the DL-family framing (Data Type/End-of-Data markers) applies to DL7/DL8 — they use a hybrid Segment-Length-based framing instead.
- Do not assume Segment DL6 is always present — it is conditional on Card Type 173 in this segment.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-DL1-rule-catalog.json) (5 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment DL1 |
|---|---|
| SME/TBA learning note | [Learning note](segment-DL1-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-DL1-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL1-business-requirements.md](segment-DL1-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL1-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL1-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL1-ai-vs-test-requirement-comparison.md) |
