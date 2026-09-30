# Segment 116 (TransArmor Load Data Segment) - Rule Catalog & Specification Anchors

**Specification:** BUYPASS Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Sections:** 11.7.5 TransArmor Key and Key ID Load (page 11-36, **stub only**), 12.15 TransArmor Load Data Segment (page 12-36, **stub only**), Chapter 13 Data Element Descriptions (Elements 55, 63, 84, 85, 155, 156, 157), Appendix I-53/54 (Segment 111 Table ID 052)
**Training Handbook:** [ATL105 Segment Training Handbook](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) (8-item framework)
**Item Progress:** Item 1 - Coverage Closure can only reach a partial baseline (envelope + Segment Type + max length); the field-by-field layout is not in this workspace's source extract. Item 2 - AI Artifact Comparison in progress against the supplied requirement catalog. Items 3-8 blocked, primarily by the missing external TransArmor document (see [Segment 116 SME/TBA Input Register](segment-116-sme-tba-input-register.md)).

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

## ⚠️ Critical Source Limitation

Unlike every other segment trained so far (100, 109, 110, ...), Segment 116's two defining sections are **stubs, not field tables**:

> **Section 12.15, TransArmor Load Data Segment (complete text):**
> "Note: For detailed information on TransArmor, refer the `BUYPASS®_Platform_ATL105_Specification_Updates_for_TransArmor_Processing` document, available on the Direct Platform Specifications Portal."

> **Section 11.7.5, TransArmor Key and Key ID Load (complete text):**
> "Note: For detailed information on TransArmor, refer the `BUYPASS®_Platform_ATL105_Specification_Updates_for_TransArmor_Processing` document, available on the Direct Platform Specifications Portal."

Neither section contains a field table, a request/response message layout, or field lengths/R-O-C designations. Every rule in this catalog is instead assembled from **cross-references** found elsewhere in the document (the Element 63 and Element 85 processing rules, the segment-length valid-codes table, and the Chapter 13 element catalog for the response-side Key/Key Data elements). This produces a much thinner, more provisional rule set than Segment 100, 109, or 110 achieved, and it will remain that way until the external TransArmor document is supplied.

The master message-template catalog retains only a [blocked Segment 116 placeholder](../../../atl105_complete_templates.json) with no field layout. Do not treat it as a usable request template or infer a response template from the available cross-references.

## Learning Module Index

- [Segment 116 Rule Catalog (authoritative)](coverage/segment-116-rule-catalog.json)
- [Segment 116 SME/TBA Input Register](segment-116-sme-tba-input-register.md)
- [Segment 116 SME/TBA Learning Note](segment-116-sme-tba-learning-note.md)
- [Segment 116 End-to-End Flow](segment-116-flow.md)
- [Segment 116 TransArmor Business Requirements](segment-116-transarmor-business-requirements.md)
- [Key and Key ID Load SME/TBA Note](key-and-key-id-load-sme-tba-note.md)
- [Key and Key ID Load Flow](key-and-key-id-load-flow.md)
- [TransArmor Load Response SME/TBA Note](transarmor-load-response-sme-tba-note.md)
- [TransArmor Load Response Flow](transarmor-load-response-flow.md)
- [Additional TransArmor Data (Segment 111 Companion) SME/TBA Note](additional-transarmor-data-sme-tba-note.md)
- [Additional TransArmor Data (Segment 111 Companion) Flow](additional-transarmor-data-flow.md)
- [TransArmor Request Envelope Lifecycle SME/TBA Note](transarmor-request-envelope-lifecycle-sme-tba-note.md)
- [TransArmor Request Envelope Lifecycle Flow](transarmor-request-envelope-lifecycle-flow.md)
- [Segment 116 vs. Segment 119 Disambiguation SME/TBA Note](segment-116-vs-119-disambiguation-sme-tba-note.md)
- [Segment 116 vs. Segment 119 Disambiguation Flow](segment-116-vs-119-disambiguation-flow.md)
- [Companion and Envelope Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion and Envelope Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Learning Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Coverage Closure Index](coverage/README.md)
- [Coverage Closure SME/TBA Note](coverage/segment-116-coverage-sme-tba-note.md)
- [Coverage Closure Flow](coverage/segment-116-coverage-flow.md)
- [Supplied AI Requirement Catalog Coverage Report](coverage/segment-116-supplied-pipeline-ai-coverage-report.md)
- [Segment 116 Canonical Source Anchors](../segment-116-canonical-anchors.md)
- [Segment 116 AI Business Requirements](../../../../test-output/ai-artifacts/business-requirements/POC-AI-ATL105-Segment-116-Business-Requirements.md)
- [Segment 116 BR Coverage Report](../../../../test-output/ai-artifacts/coverage-reports/segment-116/POC-AI-Segment-116-BR-Coverage-Report.md)
- [Segment 116 Traceability Matrix](../../../../test-output/traceability-matrix/segment-116/segment-116-coverage.md)
- [Reusable Segment Training Questionnaire](../../../test-validation-strategy/SEGMENT-TRAINING-QUESTIONNAIRE.md)

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 116 | Chapter 13, Element 85 valid-codes table |
| Segment name | TransArmor Load Data Segment | Section 12.15 heading |
| Purpose | Key and Key ID Load for TransArmor PKI Encryption and Tokenization | Chapter 13, Element 85 processing rule |
| Placement | Field No. 3 of the TransArmor PKI Encryption and Tokenization Load Request, directly following Data Section 1 (Elements 55, 63). The Element 63 processing rule labels it Data Section 2; under the request data-section convention (TT-0014) it is Data Section 3 (SEG116-SME-007) | Chapter 13, Element 63 processing rule |
| Request envelope shape (analogy, not shown directly) | Data Section 1 (Elements 55, 63) + Segment 116 in Field No. 3; likely no Segment 100 and no other segment, by analogy with the Totals/Loyalty/Electronic Mail/ECA-TeleCheck/Communications-Test requests listed in the same source paragraph | Chapter 13, Element 63 processing rule - see `SEG116-SME-002` |
| Segment length range | 01-50 alphanumeric characters | Chapter 13, Element 84 length table |
| Field-by-field layout | **Not available in this workspace.** Sections 12.15 and 11.7.5 redirect to the external TransArmor specification-updates document | Sections 12.15, 11.7.5 - see `SEG116-SME-001` |
| Response | TransArmor Load Response; contains Key ID (155), Key Data Length (156), Key Data (157) | Chapter 13, Elements 155-157 |

## 2. Field Layout

**Known fields (from cross-reference, not a dedicated table):**

| # | Element | Name | Type / Len | R/O/C | Evidence |
|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3, fixed `116` | R | Chapter 13 Element 85 valid-codes table; explicitly marked "Required for the TransArmor PKI Encryption and Tokenization Load Request" |
| 2 | 84 | Segment Length | N, variable | R (pattern) | Universal Chapter 12 convention observed in every other segment; **not individually confirmed for Segment 116** |
| ? | ? | *(unknown - remaining ~44 bytes of the 50-byte segment)* | ? | ? | **Not in source; requires the external TransArmor document** |

**Response-side elements (TransArmor Load Response, not confirmed to be inside a "Segment 116" response container):**

| Element | Name | Type / Len | R/O/C | Notes |
|---|---|---|---|---|
| 155 | Key ID | AN, fixed 11 | R | Must be reused for all subsequent TransArmor transactions from the device once approved |
| 156 | Key Data Length | N, max 3 (000-999) | R | Length of the following Key Data field |
| 157 | Key Data | AN, max 999 | R | New encryption key + Key ID on approval; error message on decline |

**Companion table (lives in Segment 111, not Segment 116):**

| Table | Sub-Table ID | Name | Length | Required for |
|---|---|---|---|---|
| Additional TransArmor Data (Table ID 052) | 01 | KSN | up to 40 bytes | AES DUKPT, TDES, Ingenico OnGuard |
| Additional TransArmor Data (Table ID 052) | 02 | Device Type | up to 8 bytes | Required for AES DUKPT; optional for TDES, Ingenico OnGuard |

## 3. Source-Derived Rules

The complete machine-readable set is in [coverage/segment-116-rule-catalog.json](coverage/segment-116-rule-catalog.json). The initial Item 1 catalog includes 9 rules: 3 solidly source-confirmed (purpose, max length, Segment Type), 4 provisional/pattern-derived or cross-referenced (Segment Length pattern, request envelope shape, the master field-layout gap, and the response fields), and 2 fully confirmed structural/compatibility notes (the Segment 111 companion table, and the resolved Segment 116-vs-119 source conflict).

## 4. Training Status

| Methodology Item | Status | Evidence / gate |
|---|---|---|
| 1. Coverage closure | BLOCKED_INSUFFICIENT_SOURCE (partial baseline only) | Only envelope shape, Segment Type, and max length are validated. The remainder of the segment's fields cannot be cataloged without the external TransArmor document. |
| 2. AI artifact comparison | IN_PROGRESS | 15 AI-generated Segment 116 requirements extracted and cross-walked against the 9-rule oracle (see [coverage report](coverage/segment-116-supplied-pipeline-ai-coverage-report.md)). |
| 3. Test-data independence | BLOCKED | Requires the external document (`SEG116-SME-001`) and fixtures (`SEG116-SME-006`). |
| 4. Traceability matrix | BLOCKED | Requires the external document and AI artifact sign-off. |
| 5. Mutation definition | BLOCKED | Cannot define field-level mutations without the field list. |
| 6. Mutation execution | NOT_STARTED | Requires Item 5. |
| 7. Validator enhancement | NOT_STARTED | Requires Item 6. |
| 8. Consolidated report | NOT_STARTED | Requires all prior gates. |

## 5. Do-Not-Assume Rules

1. Do not invent Segment 116's field list. Only Segment Type (fixed `116`) is textually confirmed; Segment Length is pattern-derived, not confirmed; everything else occupying the 50-byte maximum is unknown without the external TransArmor document.
2. Do not use Section 11.4.1.2's "(Data Segment No. 116)" label for "Totals with Proprietary Data Load Request" as evidence of Segment 116 content. The authoritative Element 85 valid-codes table and Section 12.17 both confirm that segment is actually **Data Segment No. 119**; this is a resolved source inconsistency, not a genuine Segment 116 fact.
3. Do not assume the TransArmor Load Request excludes Segment 100/Data Section 3 as a certified fact; it is a structural analogy with sibling message types, not a shown table (`SEG116-SME-002`).
4. Do not assume Key ID/Key Data Length/Key Data (155/156/157) are serialized inside a Segment 116 response container; this is unconfirmed (`SEG116-SME-003`).
5. Do not treat the Segment 111 "Additional TransArmor Data" sub-table (052) as part of Segment 116 itself; it is a companion table in a different segment.
6. Do not record or use real encryption keys, Key IDs, or KSN values in test data. Use masked or synthetic values.
7. Do not claim AI coverage from framework-generated fixtures; AI-produced BR, TS, TC, and TD packages remain independent evidence.

## 6. Next Training Actions

1. **Obtain the external `BUYPASS®_Platform_ATL105_Specification_Updates_for_TransArmor_Processing` document** from the Direct Platform Specifications Portal. This is the single highest-priority action; almost every subsequent gate depends on it.
2. Once obtained, extract the full Segment 116 field table and the Section 11.7.5 request/response message layouts, and rebuild the rule catalog to Segment-110-level depth.
3. Resolve `SEG116-SME-002` and `SEG116-SME-003` using the external document.
4. Add sanitized, converter-ready TransArmor Key/Key ID Load request/response examples once the field layout is known.
5. Continue Items 3-8 using the Segment 100 methodology without treating synthetic fixtures as AI-artifact evidence.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-116-rule-catalog.json) (9 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 116 |
|---|---|
| SME/TBA learning note | [Learning note](segment-116-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-116-flow.md) |
| Topic deep-dives | [additional-transarmor-data](additional-transarmor-data-sme-tba-note.md) · [key-and-key-id-load](key-and-key-id-load-sme-tba-note.md) · [transarmor-load-response](transarmor-load-response-sme-tba-note.md) · [transarmor-request-envelope-lifecycle](transarmor-request-envelope-lifecycle-sme-tba-note.md) |
| Topic flows | [additional-transarmor-data](additional-transarmor-data-flow.md) · [key-and-key-id-load](key-and-key-id-load-flow.md) · [transarmor-load-response](transarmor-load-response-flow.md) · [transarmor-request-envelope-lifecycle](transarmor-request-envelope-lifecycle-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-116-transarmor-business-requirements.md](segment-116-transarmor-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-116-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-116-sme-tba-input-register.md) |
