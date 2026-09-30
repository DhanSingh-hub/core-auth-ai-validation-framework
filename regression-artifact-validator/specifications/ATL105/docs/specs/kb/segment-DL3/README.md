# Segment DL3 — Date and Time Data Segment

**Specification:** ATL105 2026-3, Section 12.44 (layout), 11.7.1.2 (Table Load), 11.7.3 (Date and Time Load), 13.2 (elements) · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17302-17361, 9347-9453 · **Rules:** 9 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-3 blocked on `SEGDL3-SME-001`, validator blocked on `SEGDL3-SME-003`)

**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md). Reuse Segment 100's method; do not reuse its Field Separator or Segment Length rules.

## Learning Module Index

| Segment 100 component | Segment DL3 |
|---|---|
| End-to-end flow | [Flow](segment-DL3-flow.md) |
| SME/TBA learning note | [Learning note](segment-DL3-sme-tba-learning-note.md) |
| Prompt-code analogue (inclusion decision) | [Applicability note](applicability-decision-sme-tba-note.md) · [Flow](applicability-decision-flow.md) |
| Account-number analogue (core data) | [Field definitions note](field-definitions-sme-tba-note.md) · [Flow](field-definitions-flow.md) |
| Partial-approval analogue (conditional feature) | [Conditional dependencies note](conditional-dependency-rules-sme-tba-note.md) · [Flow](conditional-dependency-rules-flow.md) |
| Sequence-lifecycle analogue | [Lifecycle note](lifecycle-response-correlation-sme-tba-note.md) · [Flow](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL3-business-requirements.md](segment-DL3-business-requirements.md) |
| Companion compatibility | [Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Flow](companion-compatibility/companion-segment-compatibility-flow.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL3-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL3-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL3-ai-vs-test-requirement-comparison.md) |

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL3 |
| Name | Date and Time Data Segment |
| Max Length | 23 alphanumeric (all fields fixed width) |
| Origin | BUYPASS (Host) |
| Framing | Data Type Indicator `:` ... End-of-Data Indicator `~`; no Field Separators, no Segment Length |
| Messages | Table Load Response Data Block 3 (**C**); Date and Time Load Response (its fields, `~` open under `SEGDL3-SME-003`) |
| Precondition | Date and Time Load: device-initiated, Load Type `D`, no load flag needed |

## Field Layout

| Field | Element | Name | Len | R/O/C | Type / valid values | Source |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Fixed `:` | Host |
| 2 | 25 | Day of the Week | 1 | R | 0-6 (0 = Sunday) | Host |
| 3 | 21 | Current Date | 6 | R | MMDDYY | Host |
| 4 | 22 | Current Time | 4 | R | HHMM, time-zone/DST adjusted | Host |
| 5 | 23 | Cut Time | 4 | R | HHMM | Host |
| 6 | 65 | Password | 6 | R | N, ≤ 6 digits, right-aligned, space-filled | Device (12.44) / Host (11.7.3.2) — conflict |
| 7 | 34 | End-of-Data Indicator | 1 | R | Fixed `~` | Host |

## Rule Set

| Rule ID | Title | Class | Status |
| --- | --- | --- | --- |
| SEGDL3-R-001 | Max length 23, `:`/`~` markers | structure | REVIEW_REQUIRED (P-03) |
| SEGDL3-R-002 | Day/date/time/cut time required, Host-sourced | field | SPEC_DERIVED |
| SEGDL3-R-003 | Password sole Device-sourced field | field | REVIEW_REQUIRED (P-02) |
| SEGDL3-R-004 | No Field Separators | serialization | SPEC_DERIVED |
| SEGDL3-R-005 | Day 0-6, MMDDYY, HHMM formats | field | REVIEW_REQUIRED (P-04) |
| SEGDL3-R-006 | Password numeric, right-aligned, space-filled | field | REVIEW_REQUIRED (P-02) |
| SEGDL3-R-007 | Table Load (C, Block 3) and Date and Time Load Response only | applicability | REVIEW_REQUIRED (P-03) |
| SEGDL3-R-008 | Date and Time Load: device-initiated, Load Type `D`, no flag | lifecycle | SPEC_DERIVED |
| SEGDL3-R-009 | Automatic cut time: settle 30 minutes before Cut Time | lifecycle (review) | SPEC_DERIVED |

## [PROVISIONAL] Items

- P-01 `SEGDL3-SME-001`: No dedicated DL3 AI/Test package; approve synthesized fixtures.
- P-02 `SEGDL3-SME-002`: Password source and meaning conflict (12.44 Device vs 11.7.3.2 Host).
- P-03 `SEGDL3-SME-003`: Date and Time Load Response lists no `~`.
- P-04 `SEGDL3-SME-004`: HHMM valid ranges (01-24 / 01-60 versus 0000-2359).

## Do-Not-Assume Rules

- Do not assume the Password is Device-sourced — the two source sections disagree.
- Do not assume the Date and Time Load needs a load flag — it does not.
- Do not use the default password from Element 65 or any production password in test data.
- Do not assume DL3 is always in a Table Load Response — it is Conditional there.
