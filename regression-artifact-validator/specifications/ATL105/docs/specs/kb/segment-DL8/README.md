# Segment DL8 — EMV Terminal Floor Limits Data Segment

**Specification:** ATL105 2026-3, Section 12.49 (layout), 13.2 (Elements 24, 84, 233-236) · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17636-17690 · **Rules:** 5 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-3 blocked on `SEGDL8-SME-001`, validator blocked on `SEGDL8-SME-003`)

**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md). Sibling framing: [Segment DL7](../segment-DL7/README.md).

## Learning Module Index

| Segment 100 component | Segment DL8 |
|---|---|
| End-to-end flow | [Flow](segment-DL8-flow.md) |
| SME/TBA learning note | [Learning note](segment-DL8-sme-tba-learning-note.md) |
| Prompt-code analogue (inclusion decision) | [Applicability note](applicability-decision-sme-tba-note.md) · [Flow](applicability-decision-flow.md) |
| Account-number analogue (core data) | [Field definitions note](field-definitions-sme-tba-note.md) · [Flow](field-definitions-flow.md) |
| Partial-approval analogue (conditional feature) | [Conditional dependencies note](conditional-dependency-rules-sme-tba-note.md) · [Flow](conditional-dependency-rules-flow.md) |
| Sequence-lifecycle analogue | [Lifecycle note](lifecycle-response-correlation-sme-tba-note.md) · [Flow](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL8-business-requirements.md](segment-DL8-business-requirements.md) |
| Companion compatibility | [Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Flow](companion-compatibility/companion-segment-compatibility-flow.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL8-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL8-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL8-ai-vs-test-requirement-comparison.md) |

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL8 |
| Name | EMV Terminal Floor Limits Data Segment |
| Max Length | 1 + 3 + up to 624 bytes of Floor Limit Data (24 × 26) |
| Framing | Data Type Indicator `%` + 3-digit Segment Length (exclusive of `%`); **no End-of-Data Indicator** |
| Applicability | Table load, only for terminals with a terminal-level "Special" set; data maintained at the BUYPASS Host |
| Lifecycle | A change to the floor-limit data sets the table download flag for every terminal with the Special |
| Message position | Not defined in 11.7.1.2 or the Chapter 12 matrix (`SEGDL8-SME-002`) |

## Field Layout

| Field | Element | Name | Len | R/O/C | Type / valid values | Source |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Fixed `%` | — |
| 2 | 84 | Segment Length Indicator | 3 | R | N, excludes `%` | Device (sic) |
| 3 | 233 | RID | 10 | R | EMV Registered Application Provider identifier | Host |
| 4 | 234 | Stand-in Indicator | 1 | R | 1 No stand-in, 2 Domestic only, 3 Domestic & Foreign | Host |
| 5 | 235 | Floor Limit | 12 | R | 000000000000-999999999999 (maximum allowable stand-in value) | Host |
| 6 | 236 | BUYPASS RID Card Type | 3 | R | AN; values not listed (`SEGDL8-SME-003`) | Host |

Fields 3-6 repeat as one 26-byte group per RID, 1-24 times.

## Rule Set

| Rule ID | Title | Class | Status |
| --- | --- | --- | --- |
| SEGDL8-R-001 | Special-flag-gated table load; data change sets download flag | lifecycle | REVIEW_REQUIRED (P-02) |
| SEGDL8-R-002 | `%` + Segment Length framing, same as DL7 | structure | REVIEW_REQUIRED (P-02, `SEGDL7-SME-005`) |
| SEGDL8-R-003 | 1-24 groups, ≤ 624 bytes | structure | SPEC_DERIVED |
| SEGDL8-R-004 | RID, Stand-in, Floor Limit, Card Type formats | field | REVIEW_REQUIRED (P-03) |
| SEGDL8-R-005 | Segment Length 3 digits, whole groups | structure | SPEC_DERIVED |

## [PROVISIONAL] Items

- P-01 `SEGDL8-SME-001`: No dedicated DL8 AI/Test package; approve synthesized fixtures.
- P-02 `SEGDL8-SME-002`: Position in the Table Load Response; Special name; Segment Length Device source.
- P-03 `SEGDL8-SME-003`: RID representation; BUYPASS RID Card Type values; Stand-in 1 with non-zero Floor Limit.

## Do-Not-Assume Rules

- Do not assume DL8 is in every table load — only for terminals with the Special.
- Do not expect `~` at the end of DL8.
- Do not assume a partial group is allowed — the content after the Segment Length is a whole number of 26-byte groups.
