# Segment 123 — NFC Payment Tokenization Data Segment

**Specification:** ATL105 2026-3, Section 12.19 · **Source:** [extracted_text.txt](../../extracted_text.txt) lines 13816-13992 · **Item Progress:** 1/8 (Coverage Closure in progress; Items 2-3 blocked on `SEG123-SME-002`; Item 4 blocked on `SEG123-SME-001`)

## Learning Module Index

- [Segment Flow](segment-123-flow.md)
- [SME/TBA Learning Note](segment-123-sme-tba-learning-note.md)
- [SME/TBA Input Register](segment-123-sme-tba-input-register.md)
- [AI-vs-Test Requirement Comparison](segment-123-ai-vs-test-requirement-comparison.md)
- [Coverage Package](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Serialization Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## Segment Definition

| Attribute | Value |
| --- | --- |
| Segment No. | 123 |
| Name | NFC Payment Tokenization Data Segment |
| Max Length | 186 alphanumeric |
| Origin | Device |
| Framing | Segment Type/Length header, then all fields Field-Separator-delimited (even when unpopulated) |
| Applicability | Required on all initial/recurring tokenized-data transactions and any transaction with MasterCard Token/DSRP or Visa TAVV data |

## Field Layout

| Field | Element | Name | Len | R/O/C | Source |
| --- | --- | --- | --- | --- | --- |
| 1 | 85 | Segment Type | 3 | R | Device (fixed `123`) |
| 2 | 84 | Segment Length | 3 | R | Device |
| 3 | 195 | CAVV, Revised Format | 20 | O | Device |
| 4 | 196 | Token Requestor ID | 11 | O | Device |
| 5 | 197 | Token PAN Suffix | 4 | C | Issuer/Authorizer |
| 6 | 202 | Cryptogram Token Data | 28 or 56 | O | Device |
| 7 | 203 | SafeKey Data | 58 | O | Device |
| 8 | 204 | SafeKey Response | 1 | O | Authorizer |
| 9 | 237 | TAVV Cryptogram | 28 | O | Device (request-only, base64) |
| 10 | 238 | TAVV Result Code | 1 | O | Authorizer |

## Rule Set

| Rule ID | Title | Class |
| --- | --- | --- |
| SEG123-R-001 | Segment Type fixed 123 | field |
| SEG123-R-002 | Segment Length includes type+separators | serialization |
| SEG123-R-003 | Max length 186, origin Device | structure |
| SEG123-R-004 | All fields Field-Separator-delimited | serialization |
| SEG123-R-005 | Required for tokenized/MC-DSRP/Visa-TAVV transactions | applicability |
| SEG123-R-006 | Token PAN Suffix conditional return | field |
| SEG123-R-007 | CAVV Revised Format (Verified by Visa, ATN) | field |
| SEG123-R-008 | Cryptogram Token Data 28-or-56 bytes | field |
| SEG123-R-009 | SafeKey Data composition, space-filled | field |
| SEG123-R-010 | TAVV Cryptogram request-only, base64 | field |
| SEG123-R-011 | TAVV vs UCAF split for MC DSRP + AAV | compatibility |

## [PROVISIONAL] Items

- P-01: Confirm UCAF Security Level Code '21' requirement scope (`SEG123-SME-001`).
- P-02: No dedicated Segment 123 AI/Test package located; existing `Segment_123_LLM_Training` branch has no content (`SEG123-SME-002`).

## Do-Not-Assume Rules

- Do not assume TAVV Cryptogram may be populated in the response — it is request-only.
- Do not assume UCAF (Segment 111) and TAVV (Segment 123) are interchangeable — they carry distinct cryptogram types and may both be required simultaneously.
- Do not assume unpopulated optional fields omit their Field Separator — the separator is always sent.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-123-rule-catalog.json) (11 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 123 |
|---|---|
| SME/TBA learning note | [Learning note](segment-123-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-123-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-123-business-requirements.md](segment-123-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-123-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-123-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-123-ai-vs-test-requirement-comparison.md) |
