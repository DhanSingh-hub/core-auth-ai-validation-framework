# Segment 109 (Electronic Mail Data Segment) - Rule Catalog & Specification Anchors

**Specification:** BUYPASS Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)  
**Source Sections:** 10.11 Electronic Mail Processing (pages 10-60 to 10-61), 11.5 Electronic Mail (pages 11-21 to 11-23), and 12.8 Electronic Mail Data Segment (pages 12-23 to 12-24)  
**Training Methodology:** [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md) (8-item framework)  
**Item Progress:** Item 1 - Coverage Closure started; Items 2-8 blocked pending the manual inputs in [Segment 109 SME/TBA Input Register](segment-109-sme-tba-input-register.md).

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

## Learning Module Index

- [Segment 109 Rule Catalog (authoritative)](coverage/segment-109-rule-catalog.json)
- [Segment 109 SME/TBA Input Register](segment-109-sme-tba-input-register.md)
- [Segment 109 SME/TBA Learning Note](segment-109-sme-tba-learning-note.md)
- [Segment 109 End-to-End Flow](segment-109-flow.md)
- [Terminal Identifier SME/TBA Note](terminal-identifier-sme-tba-note.md)
- [Terminal Identifier Flow](terminal-identifier-flow.md)
- [Prompt Code SME/TBA Note](prompt-code-sme-tba-note.md)
- [Prompt Code Flow](prompt-code-flow.md)
- [Sequence and Block Lifecycle SME/TBA Note](sequence-lifecycle-sme-tba-note.md)
- [Sequence and Block Lifecycle Flow](sequence-lifecycle-flow.md)
- [Conditional Authorization Fields SME/TBA Note](conditional-authorization-sme-tba-note.md)
- [Conditional Authorization Fields Flow](conditional-authorization-flow.md)
- [Final Closure SME/TBA Note](final-closure-sme-tba-note.md)
- [Final Closure Flow](final-closure-flow.md)
- [Companion and Envelope Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion and Envelope Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Learning Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Coverage Closure Index](coverage/README.md)
- [Coverage Closure SME/TBA Note](coverage/segment-109-coverage-sme-tba-note.md)
- [Coverage Closure Flow](coverage/segment-109-coverage-flow.md)
- [Segment 109 Canonical Source Anchors](../segment-109-canonical-anchors.md)
- [Segment 109 Knowledge Catalog](../../../../test-output/test-json/knowledge/SEGMENT-109-KNOWLEDGE-CATALOG.json)
- [Segment 109 Field Knowledge Inventory](../../../../test-output/test-json/knowledge/segment-109-field-knowledge-inventory.json)
- [Segment 109 Context Model](../../../../test-output/test-json/knowledge/segment-109-context-model.json)
- [Segment 109 Request/Response Lifecycle Model](../../../../test-output/test-json/knowledge/segment-109-request-response-lifecycle-knowledge.json)
- [Reusable Segment Training Questionnaire](../../../test-validation-strategy/SEGMENT-TRAINING-QUESTIONNAIRE.md)

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 109 | Section 12.8 |
| Segment name | Electronic Mail Data Segment | Section 12.8 heading |
| Purpose | Carries electronic-mail-specific request data for retrieval or submission | Sections 10.11 and 11.5 |
| Placement | Field 3 in Data Section No. 2 of an Electronic Mail Request | Section 11.5.1 |
| Origin | Device | Section 12.8 |
| Segment length range | 001-232 alphanumeric characters | Section 12.8 |
| Request envelope | Data Section 1 has Elements 55 and 63; Data Section 2 carries Segment 109 | Section 11.5.1 |
| Response envelope | Variable-length, positional Electronic Mail Response; no field separators | Section 11.5.2 |

## 2. Field Layout

All Segment 109 fields are field-separated. Empty fields still require their Field Separator.

| # | Element | Name | Type / Len | R/O/C | JSON field |
|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | `segmentType` |
| 2 | 84 | Segment Length | N, 3 | R | `segmentLength` |
| 3 | 44 | Information Byte | N, 1 | R | `informationByte` |
| 4 | 102 | Terminal Identifier | AN, variable | R | `terminalIdentifier` |
| 5 | 78 | Prompt Code | N, 3 | R | `promptCode` |
| 6 | 11 | Block Number | N, 3 | R | `blockNumber` |
| 7 | 32 | Employee Number | N, 4 | C | `employeeNumber` |
| 8 | 65 | Password | AN, 6 | C | `password` |
| 9 | 86 | Sequence Number | N, 6 | R | `sequenceNumber` |
| 10 | 50 | Local Time | N, 4 | C | `localTime` |
| 11 | 36 | Extract Date | N, 6 | C | `extractDate` |
| 12 | 37 | Extract Time | N, 4 | C | `extractTime` |
| 13 | 104 | Text Data Length | N, 3 | C | `textDataLength` |
| 14 | 103 | Text Data | AN, 150 | C | `textData` |

## 3. Source-Derived Rules

The complete machine-readable set is in [coverage/segment-109-rule-catalog.json](coverage/segment-109-rule-catalog.json). The initial Item 1 catalog includes 22 direct rules covering request placement, ordered fields, fixed/maximum lengths, prompt-code applicability, retrieval/submission payload limits, response correlation, and separator behavior.

## 4. Training Status

| Methodology Item | Status | Evidence / gate |
|---|---|---|
| 1. Coverage closure | PARTIALLY_COVERED | 22 source-derived rules cataloged; Item 1 validator and 9 baseline tests pass. Conditional semantics await SME input. |
| 2. AI artifact comparison | BLOCKED | Requires AI-produced Segment 109 BR/TS/TC/TD artifacts. |
| 3. Test-data independence | BLOCKED | Requires approved request/response examples and conditional-field policy. |
| 4. Traceability matrix | BLOCKED | Requires approved business rules and AI artifacts. |
| 5. Mutation definition | BLOCKED | Requires Information Byte, Block Number, and conditional-field value catalogs. |
| 6. Mutation execution | NOT_STARTED | Requires Item 5 plus approved test packages. |
| 7. Validator enhancement | NOT_STARTED | Runs after measurable mutation execution. |
| 8. Consolidated report | NOT_STARTED | Requires all prior gates and SME decisions. |

## 5. Do-Not-Assume Rules

1. Do not invent valid Information Byte, Block Number, terminal-identifier, employee-number, or password values; the extracted layout provides lengths but not their complete value catalogs.
2. Do not treat `981`, `996`, and `995` as interchangeable. They identify retrieval, proprietary-card-data retrieval, and submission respectively.
3. Do not infer whether conditional fields are required from a field name. Their business triggers require a source/configuration-owner decision.
4. Do not record or use real passwords, employee numbers, terminal credentials, or mail content in test data. Use masked or synthetic values.
5. Do not use Segment 109 alone as proof of a financial-transaction request. It belongs to the distinct Electronic Mail Request envelope in Section 11.5.
6. Do not certify response semantics beyond the positional layout and its documented fields until the response-code and download-indicator mappings are confirmed.

## 6. Next Training Actions

1. Provide answers and approved references for the items in the input register.
2. Add sanitized, converter-ready request/response examples for each enabled electronic-mail flow.
3. Replace the in-memory Item 1 baseline data with approved fixture shapes once they are supplied.
4. Continue Items 2-8 using the Segment 100 methodology without treating synthetic fixtures as AI-artifact evidence.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-109-rule-catalog.json) (22 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 109 |
|---|---|
| SME/TBA learning note | [Learning note](segment-109-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-109-flow.md) |
| Topic deep-dives | [conditional-authorization](conditional-authorization-sme-tba-note.md) · [prompt-code](prompt-code-sme-tba-note.md) · [sequence-lifecycle](sequence-lifecycle-sme-tba-note.md) · [terminal-identifier](terminal-identifier-sme-tba-note.md) |
| Topic flows | [conditional-authorization](conditional-authorization-flow.md) · [prompt-code](prompt-code-flow.md) · [sequence-lifecycle](sequence-lifecycle-flow.md) · [terminal-identifier](terminal-identifier-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-109-business-requirements.md](segment-109-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-109-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-109-sme-tba-input-register.md) |
