# Segment DL2 — Dial String Data Segment

**Specification:** ATL105 2026-3, Section 12.43 (layout), 11.7.1.2 (Table Load), 11.7.2 (Phone Load), 13.2 (elements) · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17207-17300, 9240-9345 · **Rules:** 9 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-3 blocked on `SEGDL2-SME-002`, validator blocked on `SEGDL2-SME-004`)

**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md). Reuse Segment 100's method; do not reuse its Field Separator, Segment Length or trailing-omission rules — DL2 has none of them.

## Learning Module Index

| Segment 100 component | Segment DL2 |
|---|---|
| End-to-end flow | [Flow](segment-DL2-flow.md) |
| SME/TBA learning note | [Learning note](segment-DL2-sme-tba-learning-note.md) |
| Prompt-code analogue (inclusion decision) | [Applicability note](applicability-decision-sme-tba-note.md) · [Flow](applicability-decision-flow.md) |
| Account-number analogue (core data) | [Field definitions note](field-definitions-sme-tba-note.md) · [Flow](field-definitions-flow.md) |
| Partial-approval analogue (conditional feature) | [Conditional dependencies note](conditional-dependency-rules-sme-tba-note.md) · [Flow](conditional-dependency-rules-flow.md) |
| Sequence-lifecycle analogue | [Lifecycle note](lifecycle-response-correlation-sme-tba-note.md) · [Flow](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL2-business-requirements.md](segment-DL2-business-requirements.md) |
| Companion compatibility | [Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Flow](companion-compatibility/companion-segment-compatibility-flow.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL2-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL2-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL2-ai-vs-test-requirement-comparison.md) |

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL2 |
| Name | Dial String Data Segment |
| Max Length | 69 alphanumeric (both blocks at maximum width) |
| Origin | BUYPASS (Host) |
| Framing | Data Type Indicator `!` ... End-of-Data Indicator `~`; no Field Separators, no Segment Length |
| Empty field | Access Code and Pause Indicator are omitted together; the next field immediately follows |
| Messages | Table Load Response Data Block 2 (**C**); Phone Load Response (**R**) |
| Precondition | Phone Load: merchant load flag `PHON`; Table Load: flag `TABL` |

## Field Layout

| Field | Element | Name | Len | R/O/C | Type / valid values | Source |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Fixed `!` | Host |
| 2 | 28 | Dial String Type | 1 | R | Fixed `1` (transaction dial strings) | Host |
| 3 | 82 | Redial Count (primary) | 1 | R | N, 1-3 | Host |
| 4 | 1 | Access Code (primary) | ≤12 | C | Digits and `B` (1-second pauses) | Host |
| 5 | 66 | Pause Indicator (primary) | 1 | C | Fixed `B`; present only with Access Code | Host |
| 6 | 75 | Phone Number (primary) | ≤18 | R | N, variable | Host |
| 7 | 27 | Dial String Terminator | 1 | R | Fixed `A` | Host |
| 8-11 | 82, 1, 66, 75 | Secondary block | as 3-6 | R/C/C/R | Same rules, secondary number | Host |
| 12 | 27 | Dial String Terminator | 1 | R | Fixed `F` | Host |
| 13 | 34 | End-of-Data Indicator | 1 | R | Fixed `~` | Host |

## Rule Set

| Rule ID | Title | Class | Status |
| --- | --- | --- | --- |
| SEGDL2-R-001 | Max length 69, `!`/`~` markers | structure | SPEC_DERIVED |
| SEGDL2-R-002 | Dial String Type `1`; primary block required | field | SPEC_DERIVED |
| SEGDL2-R-003 | Secondary block mirrors primary, ends `F`; fallback order | lifecycle | REVIEW_REQUIRED (P-01) |
| SEGDL2-R-004 | No Field Separators; next field immediately follows | serialization | REVIEW_REQUIRED (P-04) |
| SEGDL2-R-005 | Access Code and Pause Indicator present together or not at all | conditional | REVIEW_REQUIRED (P-04) |
| SEGDL2-R-006 | Redial Count 1-3 | field | SPEC_DERIVED |
| SEGDL2-R-007 | Phone Number ≤ 18 digits; terminators `A`/`F` | field | REVIEW_REQUIRED (P-04) |
| SEGDL2-R-008 | Table Load (C, Block 2) and Phone Load (R) only | applicability | REVIEW_REQUIRED (P-03) |
| SEGDL2-R-009 | Phone Load requires load flag `PHON` | lifecycle | SPEC_DERIVED |

## [PROVISIONAL] Items

- P-01 `SEGDL2-SME-001`: Asynchronous Communications Protocol Specifications (retry/error-recovery timing) scope.
- P-02 `SEGDL2-SME-002`: No dedicated DL2 AI/Test package; approve synthesized fixtures.
- P-03 `SEGDL2-SME-003`: Phone Load Response field 1 `!` versus Element 97 `)`.
- P-04 `SEGDL2-SME-004`: Parse boundary for variable Access Code / Pause / Phone Number.

## Do-Not-Assume Rules

- Do not assume Access Code or Pause Indicator are populated — they are Conditional and travel together.
- Do not assume one `B` means "Pause Indicator" — the Access Code itself may contain `B` pauses.
- Do not assume DL2 is required in a Table Load Response — it is Conditional there and Required only in a Phone Load Response.
- Do not port the fallback timing from another document without `SEGDL2-SME-001`.
