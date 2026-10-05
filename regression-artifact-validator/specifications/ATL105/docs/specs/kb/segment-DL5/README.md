# Segment DL5 — Software IP Load Data Segment

**Specification:** ATL105 2026-3, Section 12.46 (layout), 11.7.4 (Software Load), 10.10 (Software Update Processing), 13.2 (elements) · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17447-17530, 9455-9580, 6166-6200 · **Rules:** 7 · **Item Progress:** 5/8 candidate-complete/review-required; Items 6-7 blocked by SME decisions and approved fixtures; consolidated candidate report generated.

**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md). Companion: [Segment DL4](../segment-DL4/README.md) — DL5 is DL4 with an IP/URL address instead of a phone number.

## Learning Module Index

| Segment 100 component | Segment DL5 |
|---|---|
| End-to-end flow | [Flow](segment-DL5-flow.md) |
| SME/TBA learning note | [Learning note](segment-DL5-sme-tba-learning-note.md) |
| Prompt-code analogue (inclusion decision) | [Applicability note](applicability-decision-sme-tba-note.md) · [Flow](applicability-decision-flow.md) |
| Account-number analogue (core data) | [Field definitions note](field-definitions-sme-tba-note.md) · [Flow](field-definitions-flow.md) |
| Partial-approval analogue (conditional feature) | [Conditional dependencies note](conditional-dependency-rules-sme-tba-note.md) · [Flow](conditional-dependency-rules-flow.md) |
| Sequence-lifecycle analogue | [Lifecycle note](lifecycle-response-correlation-sme-tba-note.md) · [Flow](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL5-business-requirements.md](segment-DL5-business-requirements.md) |
| Companion compatibility | [Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Flow](companion-compatibility/companion-segment-compatibility-flow.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL5-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL5-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL5-ai-vs-test-requirement-comparison.md) |

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL5 |
| Name | Software IP Load Data Segment |
| Max Length | 64 alphanumeric (12.46) — 66 in 11.7.4.2 (`SEGDL5-SME-002`) |
| Origin | BUYPASS (Host) |
| Framing | Data Type Indicator `$` ... End-of-Data Indicator `~`; no Field Separators, no Segment Length |
| Applicability | Devices loaded through the BUYPASS device management system only |
| Message | Software Load Response, field 3, **Required, after DL4** |
| Precondition | Merchant load flag `SOFT`; placement conflict with 10.10 tracked in `SEGDL4-SME-002` |

## Field Layout

| Field | Element | Name | Len | R/O/C | Type / valid values | Source |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Fixed `$` | Host |
| 2 | 57 | New Software Version | 8 | R | AN, fixed 8 | Host |
| 3 | 95 | Software Terminal Record ID | 13 | R | AN, fixed 13 | Host |
| 4 | 114 | Software Load IP/URL Address | 30 | R | AN, fixed 30 (content open: `SEGDL5-SME-003`) | Host |
| 5 | 92 | Software Load Request Date | 6 | R | MMDDYY | Host |
| 6 | 93 | Software Load Request Time | 4 | R | HHMM | Host |
| 7 | 94 | Software Load Type | 1 | R | `F` / `P` | Host |
| 8 | 34 | End-of-Data Indicator | 1 | R | Fixed `~` | Host |

Note: 12.46 describes field 2 as "the new software application name in the device management system" while Element 57 and 12.45 call it the new software version number.

## Rule Set

| Rule ID | Title | Class | Status |
| --- | --- | --- | --- |
| SEGDL5-R-001 | BUYPASS-managed devices only | applicability | SPEC_DERIVED |
| SEGDL5-R-002 | Max length 64, `$`/`~` markers | structure | REVIEW_REQUIRED (P-02) |
| SEGDL5-R-003 | DL5 mirrors DL4 except IP/URL vs phone | field | SPEC_DERIVED |
| SEGDL5-R-004 | No Field Separators | serialization | SPEC_DERIVED |
| SEGDL5-R-005 | Element formats; Element 114 fixed 30 | field | REVIEW_REQUIRED (P-03) |
| SEGDL5-R-006 | Software Load Response field 3 after DL4, flag `SOFT` | applicability | REVIEW_REQUIRED (`SEGDL4-SME-002`) |
| SEGDL5-R-007 | Scheduled IP load, three attempts, decline | lifecycle (review) | SPEC_DERIVED |

## [PROVISIONAL] Items

- P-01 `SEGDL5-SME-001`: No dedicated DL5 AI/Test package; approve synthesized fixtures.
- P-02 `SEGDL5-SME-002`: Maximum length 64 vs 66.
- P-03 `SEGDL5-SME-003`: Element 114 content, character set and padding.

## Do-Not-Assume Rules

- Do not assume DL5 replaces DL4 — both are Required in the Software Load Response.
- Do not assume a URL with `.`, `:` or `/` is valid until `SEGDL5-SME-003` is answered.
- Do not assume DL5 applies to vendor-managed devices.
