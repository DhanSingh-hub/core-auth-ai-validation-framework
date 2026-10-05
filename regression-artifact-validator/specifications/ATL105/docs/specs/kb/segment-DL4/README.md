# Segment DL4 — Software Dial Load Data Segment

**Specification:** ATL105 2026-3, Section 12.45 (layout), 11.7.4 (Software Load), 10.10 (Software Update Processing), 13.2 (elements) · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17363-17445, 9455-9580, 6166-6200 · **Rules:** 8 · **Candidate progress:** 8/8 BR → TS → TC → TD traces with positive and negative test intent. Approval/execution remain incomplete; P-01 through P-03 are open.

**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md). Reuse Segment 100's method; do not reuse its Field Separator or Segment Length rules.

## Learning Module Index

| Segment 100 component | Segment DL4 |
|---|---|
| End-to-end flow | [Flow](segment-DL4-flow.md) |
| SME/TBA learning note | [Learning note](segment-DL4-sme-tba-learning-note.md) |
| Prompt-code analogue (inclusion decision) | [Applicability note](applicability-decision-sme-tba-note.md) · [Flow](applicability-decision-flow.md) |
| Account-number analogue (core data) | [Field definitions note](field-definitions-sme-tba-note.md) · [Flow](field-definitions-flow.md) |
| Partial-approval analogue (conditional feature) | [Conditional dependencies note](conditional-dependency-rules-sme-tba-note.md) · [Flow](conditional-dependency-rules-flow.md) |
| Sequence-lifecycle analogue | [Lifecycle note](lifecycle-response-correlation-sme-tba-note.md) · [Flow](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL4-business-requirements.md](segment-DL4-business-requirements.md) |
| Companion compatibility | [Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Flow](companion-compatibility/companion-segment-compatibility-flow.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL4-rule-catalog.json) · [AI artifact report](coverage/segment-DL4-ai-artifact-coverage-report.md) |
| SME/TBA input register | [Input register](segment-DL4-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL4-ai-vs-test-requirement-comparison.md) |

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL4 |
| Name | Software Dial Load Data Segment |
| Max Length | 52 alphanumeric |
| Origin | BUYPASS (Host) |
| Framing | Data Type Indicator `@` ... End-of-Data Indicator `~`; no Field Separators, no Segment Length |
| Applicability | Devices loaded through the BUYPASS device management system only; never for vendor-managed applications |
| Message | Software Load Response, Data Block 1, **Required, together with DL5** (placement conflict with 10.10: `SEGDL4-SME-002`) |
| Precondition | Merchant load flag `SOFT` (11.7.4); profile "DLL" bit + Download Indicator `1` (10.10) |

## Field Layout

| Field | Element | Name | Len | R/O/C | Type / valid values | Source |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Fixed `@` | Host |
| 2 | 57 | New Software Version | 8 | R | AN, fixed 8 | Host |
| 3 | 95 | Software Terminal Record ID | 13 | R | AN, fixed 13 | Host |
| 4 | 91 | Software Load Phone Number | 18 | R | AN, variable ≤ 18 | Host |
| 5 | 92 | Software Load Request Date | 6 | R | MMDDYY | Host |
| 6 | 93 | Software Load Request Time | 4 | R | HHMM | Host |
| 7 | 94 | Software Load Type | 1 | R | `F` full / `P` partial | Host |
| 8 | 34 | End-of-Data Indicator | 1 | R | Fixed `~` | Host |

## Rule Set

| Rule ID | Title | Class | Status |
| --- | --- | --- | --- |
| SEGDL4-R-001 | BUYPASS-managed devices only | applicability | SPEC_DERIVED |
| SEGDL4-R-002 | Max length 52, `@`/`~` markers | structure | SPEC_DERIVED |
| SEGDL4-R-003 | Schedule fields required | field | SPEC_DERIVED |
| SEGDL4-R-004 | No Field Separators | serialization | REVIEW_REQUIRED (P-03) |
| SEGDL4-R-005 | Element formats; Load Type `F`/`P` | field | REVIEW_REQUIRED (P-03) |
| SEGDL4-R-006 | Software Load Response, Required with DL5, flag `SOFT` | applicability | REVIEW_REQUIRED (P-02) |
| SEGDL4-R-007 | Software update processing sequence | lifecycle | REVIEW_REQUIRED (P-02) |
| SEGDL4-R-008 | Max three load attempts; decline message | lifecycle (review) | SPEC_DERIVED |

## [PROVISIONAL] Items

- P-01 `SEGDL4-SME-001`: No dedicated DL4 AI/Test package; approve synthesized fixtures.
- P-02 `SEGDL4-SME-002`: Table Load (10.10) vs Software Load Response (11.7.4); how the device chooses dial vs IP.
- P-03 `SEGDL4-SME-003`: Padding of the variable-length Software Load Phone Number.

## Do-Not-Assume Rules

- Do not assume DL4 and DL5 are mutually exclusive — 11.7.4.2 marks both Required and 10.10 sends both. (The earlier KB statement had no source and is withdrawn.)
- Do not assume DL4 applies to vendor-managed devices — it never does.
- Do not expect DL4 in a Table Load, Phone Load or Date and Time Load Response until `SEGDL4-SME-002` is answered.
- Candidate package and AI comparison do not count as fixture approval, execution, or certification; see [coverage closure](coverage/README.md).
