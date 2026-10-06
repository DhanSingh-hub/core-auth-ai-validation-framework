# Segment DL6 — Store and Forward Data Segment

**Specification:** ATL105 2026-3, Section 12.47 (layout), 11.7.1.2 (Table Load Data Block 4), 13.2 (Element 166), Appendix E (Card Type 173) · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17532-17585, 9180-9236 · **Rules:** 7 · **Item Progress:** 8/8 candidate artifacts drafted; SME approval, approved fixtures, mutation execution, and certification remain blocked by `SEGDL6-SME-001` through `SEGDL6-SME-005` and `SEGDL1-SME-003`

**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md). Trigger segment: [Segment DL1](../segment-DL1/README.md) (Card Type `173`).

## Learning Module Index

| Segment 100 component | Segment DL6 |
|---|---|
| End-to-end flow | [Flow](segment-DL6-flow.md) |
| SME/TBA learning note | [Learning note](segment-DL6-sme-tba-learning-note.md) |
| Prompt-code analogue (inclusion decision) | [Applicability note](applicability-decision-sme-tba-note.md) · [Flow](applicability-decision-flow.md) |
| Account-number analogue (core data) | [Field definitions note](field-definitions-sme-tba-note.md) · [Flow](field-definitions-flow.md) |
| Partial-approval analogue (conditional feature) | [Conditional dependencies note](conditional-dependency-rules-sme-tba-note.md) · [Flow](conditional-dependency-rules-flow.md) |
| Sequence-lifecycle analogue | [Lifecycle note](lifecycle-response-correlation-sme-tba-note.md) · [Flow](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL6-business-requirements.md](segment-DL6-business-requirements.md) |
| Companion compatibility | [Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Flow](companion-compatibility/companion-segment-compatibility-flow.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL6-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL6-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL6-ai-vs-test-requirement-comparison.md) |

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL6 |
| Name | Store and Forward Data Segment |
| Max Length | 9 stated; fields sum to 10 (`SEGDL6-SME-003`) |
| Origin | BUYPASS (Host) |
| Framing | Data Type Indicator `\` ... End-of-Data Indicator `~`; no Field Separators, no Segment Length |
| Applicability | Table Load Response only, Data Block 4, and only when DL1 carries Card Type `173` |
| Meaning | Daily window during which the device blocks store-and-forward processing |

## Field Layout

| Field | Element | Name | Len | R/O/C | Type / valid values | Source |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Fixed `\` | Host |
| 2 | 166 | Start Time | 4 | R | HHMM, 0000-2359 | Host |
| 3 | 166 | End Time | 4 | R | HHMM, 0000-2359 | not stated (`SEGDL6-SME-004`) |
| 4 | 34 | End-of-Data Indicator | 1 | R | Fixed `~` | Host |

## Rule Set

| Rule ID | Title | Class | Status |
| --- | --- | --- | --- |
| SEGDL6-R-001 | Only with DL1 Card Type `173`, Table Load Response | applicability | SPEC_DERIVED |
| SEGDL6-R-002 | Max length 9, `\`/`~` markers | structure | REVIEW_REQUIRED (P-03, P-04) |
| SEGDL6-R-003 | Start/End Time share Element 166 | field | REVIEW_REQUIRED (P-01) |
| SEGDL6-R-004 | No Field Separators | serialization | SPEC_DERIVED |
| SEGDL6-R-005 | HHMM 0000-2359 | field | SPEC_DERIVED |
| SEGDL6-R-006 | Data Block 4 after `*`, followed by `*` | structure | REVIEW_REQUIRED (`SEGDL1-SME-003`) |
| SEGDL6-R-007 | Daily recurring blocking window | lifecycle | REVIEW_REQUIRED (P-05) |

## [PROVISIONAL] Items

- P-01 `SEGDL6-SME-001`: Element 166 reuse for Start and End Time (evidence now recorded).
- P-02 `SEGDL6-SME-002`: No dedicated DL6 AI/Test package; approve synthesized fixtures.
- P-03 `SEGDL6-SME-003`: Maximum length 9 vs 10.
- P-04 `SEGDL6-SME-004`: Field 1 description, Element 34 list and End Time source defects.
- P-05 `SEGDL6-SME-005`: Window across midnight, Start = End, time basis.

## Do-Not-Assume Rules

- Do not assume DL6 is ever present without DL1 Card Type `173`, or absent when `173` is present.
- Do not reject a 10-character DL6 until `SEGDL6-SME-003` is answered.
- Do not assume Start Time < End Time.
