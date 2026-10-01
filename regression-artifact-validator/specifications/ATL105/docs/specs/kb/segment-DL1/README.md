# Segment DL1 — Merchant Data Segment

**Specification:** ATL105 2026-3, Section 12.42 (layout), 11.7.1 (Table Load), 13.2 (elements), Appendix E (card types) · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 17101-17206, 8968-9236 · **Rules:** 12 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-3 blocked on `SEGDL1-SME-001`, validator blocked on `SEGDL1-SME-002`)

**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md). Segment 100 is a device-originated, Field-Separator-delimited request segment; DL1 is a host-originated, separator-free download segment. Reuse the Segment 100 *method* (source anchor → BR → TS → TC → TD, SME/TBA questions, closure gates), never its separator, length or trailing-omission rules.

## Learning Module Index

| Segment 100 component | Segment DL1 |
|---|---|
| End-to-end flow | [Flow](segment-DL1-flow.md) |
| SME/TBA learning note | [Learning note](segment-DL1-sme-tba-learning-note.md) |
| Prompt-code analogue (inclusion decision) | [Applicability note](applicability-decision-sme-tba-note.md) · [Flow](applicability-decision-flow.md) |
| Account-number analogue (core data) | [Field definitions note](field-definitions-sme-tba-note.md) · [Flow](field-definitions-flow.md) |
| Partial-approval analogue (conditional feature) | [Conditional dependencies note](conditional-dependency-rules-sme-tba-note.md) · [Flow](conditional-dependency-rules-flow.md) |
| Sequence-lifecycle analogue | [Lifecycle note](lifecycle-response-correlation-sme-tba-note.md) · [Flow](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-DL1-business-requirements.md](segment-DL1-business-requirements.md) |
| Companion compatibility | [Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Flow](companion-compatibility/companion-segment-compatibility-flow.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-DL1-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-DL1-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-DL1-ai-vs-test-requirement-comparison.md) |

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | DL1 |
| Name | Merchant Data Segment |
| Max Length | 399 alphanumeric (`#` + 100 fixed characters + 3 × Card Types (01-99) + `~`) |
| Origin | BUYPASS (Host) |
| Framing | Data Type Indicator `#` ... End-of-Data Indicator `~`; **no Field Separators, no Segment Type, no Segment Length** |
| Empty field | "When a field is not populated, the next field immediately follows" — no placeholder is kept |
| Message | Table Load Response only, Data Block No. 1, **Required** |
| Precondition | Merchant load flag `TABL`; otherwise only an error block and a terminating block are returned |

## Field Layout

| Field | Element | Name | Len | R/O/C | Type / valid values | Source |
| --- | --- | --- | --- | --- | --- | --- |
| 1 | 24 | Data Type Indicator | 1 | R | Fixed `#` | Host |
| 2 | 53 | Merchant Name | 24 | R | AN, "fixed length of up to 24" | Host |
| 3 | 98 | Store Number | 16 | R | N, 0000000000000001-9999999999999999 | Host |
| 4 | 3 | Address Line 1 | 24 | R | AN, "fixed length of up to 24" | Host |
| 5 | 4 | Address Line 2 | 21 | R | City(1-12) space State(14-15) space ZIP(17-21) | Host |
| 6 | 54 | Merchant Phone Number | 13 | R | `(nnn)nnn-nnnn` | Host |
| 7 | 59 | Number of Card Types | 2 | R | N, 01-99; count of field 8 | Host |
| 8 | 14 | Card Type (repeats 01-99×) | 3 | R | Appendix E Table Load codes | Host |
| 9 | 34 | End-of-Data Indicator | 1 | R | Fixed `~` | Host |

## Rule Set

| Rule ID | Title | Class | Status |
| --- | --- | --- | --- |
| SEGDL1-R-001 | Max length 399, origin Host | serialization | SPEC_DERIVED |
| SEGDL1-R-002 | `#` / `~` markers, no Segment Type/Length | structure | SPEC_DERIVED |
| SEGDL1-R-003 | Merchant identity fields required | field | SPEC_DERIVED (padding: P-02) |
| SEGDL1-R-004 | Card Type repeats 01-99 per Number of Card Types | structure | SPEC_DERIVED |
| SEGDL1-R-005 | Card Type 173 triggers Segment DL6 | lifecycle | SPEC_DERIVED |
| SEGDL1-R-006 | No Field Separators; next field immediately follows | serialization | SPEC_DERIVED (padding: P-02) |
| SEGDL1-R-007 | Table Load Response Data Block 1, Required, only message | applicability | SPEC_DERIVED |
| SEGDL1-R-008 | Load flag `TABL` precondition | lifecycle | SPEC_DERIVED |
| SEGDL1-R-009 | Card Type must be an Appendix E Table Load code | field | REVIEW_REQUIRED (P-04) |
| SEGDL1-R-010 | Address Line 2 positional layout | field | SPEC_DERIVED |
| SEGDL1-R-011 | Phone format and Store Number range | field | SPEC_DERIVED |
| SEGDL1-R-012 | Table Load Response block order | structure | REVIEW_REQUIRED (P-03) |

## [PROVISIONAL] Items

- P-01 `SEGDL1-SME-001`: No dedicated Segment DL1 AI/Test package; approve synthesized fixtures or provide one.
- P-02 `SEGDL1-SME-002`: Padding convention for "fixed length of up to N" fields in a separator-free segment.
- P-03 `SEGDL1-SME-003`: End-of-Load `*` after DL3 and again after DL6 — once or twice?
- P-04 `SEGDL1-SME-004`: Appendix E range text versus table content for Table Load Card Types.

## Do-Not-Assume Rules

- Do not port Segment 100's "keep the separator for an empty middle field" rule — DL1 has no separators.
- Do not assume DL1 has a Segment Length (Element 84) — only DL7 and DL8 in the DL family carry one.
- Do not assume Segment DL6 is always present — it is conditional on Card Type `173` in this segment.
- Do not treat Card Types 127-174 as payment cards — they switch device features and never appear in Prompt Codes.
- Do not expect DL1 in a Phone Load, Date and Time Load or Software Load Response.
