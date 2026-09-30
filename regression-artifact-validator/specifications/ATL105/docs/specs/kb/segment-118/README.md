# Segment 118 (Proprietary Data Load Segment) - Rule Catalog & Specification Anchors

**Specification:** BUYPASS Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Sections:** 11.7.6 Proprietary Data Load Request (pages 11-36 to 11-37), 11.7.7 Proprietary Data Load Response (pages 11-38 to 11-39), 12.16 Proprietary Data Load Segment (pages 12-37 to 12-45) with subsections 12.16.1-12.16.5, Chapter 13 Data Element Descriptions (Elements 11, 44, 55, 63, 77, 78, 83, 84, 85, 86, 102, 165-186, 201), and Appendix E (Valid Card Type Codes Used in Special Transaction Prompt Codes)
**Training Handbook:** [ATL105 Segment Training Handbook](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) (8-item framework)
**Item Progress:** Item 1 - Coverage Closure substantially complete (30 rules covering the full request/response envelope, all 13 core fields, and all five conditional Prompt-Code payload variants). Item 2 - Independent AI chain review completed with verdict `REJECTED_NOT_INTAKE_READY`: none of the 578 linked Run2 payloads contains Segment 118, and the phase-1 probe does not reach test case or test data. Items 3-8 remain blocked pending manual inputs and AI rework in the [Segment 118 SME/TBA Input Register](segment-118-sme-tba-input-register.md).

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

Unlike Segment 116 (whose sections were stubs), Segment 118's specification sections are **fully documented** in this workspace's source: a complete request/response envelope (11.7.6/11.7.7), a complete 13-field core layout (12.16), and five complete conditional payload layouts, one per Prompt Code (12.16.1-12.16.5). This module is therefore comparable in depth to Segment 109/110, not to Segment 116.

## Learning Module Index

- [Segment 118 Rule Catalog (authoritative)](coverage/segment-118-rule-catalog.json)
- [Segment 118 SME/TBA Input Register](segment-118-sme-tba-input-register.md)
- [Segment 118 SME/TBA Learning Note](segment-118-sme-tba-learning-note.md)
- [Segment 118 End-to-End Flow](segment-118-flow.md)
- [Segment 118 Proprietary Load Business Requirements](segment-118-proprietary-load-business-requirements.md)
- [Card Table Load (Prompt Codes 902) SME/TBA Note](card-table-load-sme-tba-note.md)
- [Card Table Load Flow](card-table-load-flow.md)
- [Custom Receipt Text (Prompt Code 901) SME/TBA Note](custom-receipt-text-sme-tba-note.md)
- [Custom Receipt Text Flow](custom-receipt-text-flow.md)
- [Host Discount Data (Prompt Code 904) SME/TBA Note](host-discount-data-sme-tba-note.md)
- [Host Discount Data Flow](host-discount-data-flow.md)
- [Site Configuration and Fuel Volume (Prompt Codes 903/905) SME/TBA Note](site-configuration-and-fuel-volume-sme-tba-note.md)
- [Site Configuration and Fuel Volume Flow](site-configuration-and-fuel-volume-flow.md)
- [Block Lifecycle and Multi-Message Loads SME/TBA Note](block-lifecycle-sme-tba-note.md)
- [Block Lifecycle and Multi-Message Loads Flow](block-lifecycle-flow.md)
- [Companion and Envelope Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion and Envelope Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Learning Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Coverage Closure Index](coverage/README.md)
- [Coverage Closure SME/TBA Note](coverage/segment-118-coverage-sme-tba-note.md)
- [Coverage Closure Flow](coverage/segment-118-coverage-flow.md)
- [Supplied AI Requirement Catalog Coverage Report](coverage/segment-118-supplied-pipeline-ai-coverage-report.md)
- [Independent AI BR-to-test-data Chain Verification](../../../../test-output/ai-solution-independent-review/segment-118-ai-chain-verification.json)
- [Segment 118 Canonical Source Anchors](../segment-118-canonical-anchors.md)
- [Segment 118 AI Business Requirements](../../../../test-output/ai-artifacts/business-requirements/POC-AI-ATL105-Segment-118-Business-Requirements.md)
- [Segment 118 BR Coverage Report](../../../../test-output/ai-artifacts/coverage-reports/segment-118/POC-AI-Segment-118-BR-Coverage-Report.md)
- [Segment 118 Traceability Matrix](../../../../test-output/traceability-matrix/segment-118/segment-118-coverage.md)
- [Reusable Segment Training Questionnaire](../../../test-validation-strategy/SEGMENT-TRAINING-QUESTIONNAIRE.md)

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 118 | Section 12.16 |
| Segment name | Proprietary Data Load Segment | Section 12.16 heading |
| Purpose | Load (host-to-device) or capture (device-to-host) Customer Specific Proprietary data: Dynamic Card Table, Custom Receipt Text, Host Discounts (host-driven push), Site Configuration, Fuel Volume (device-driven push) | Section 12.16 |
| Placement | Data Section 3, field 3 (request) / field 6 (response); the request explicitly excludes Data Section 2 / Segment 100 | Sections 11.7.6.1, 11.7.7 |
| Origin | Device | Section 12.16 |
| Segment length range (request) | 01-3,800 alphanumeric characters | Section 12.16 |
| Segment length range (response) | 01-3,800 alphanumeric characters | Section 12.16 |
| Trigger | Response Code (Element 83) on an End of Day Totals Response indicates proprietary data is available for loading; occurs only at end of day, as part of Day Close processing | Section 11.7.6 |

## 2. Field Layout (Core Fields 1-13)

Request fields are separated by Field Separators (including empty fields); a Field Separator follows field 13. Response fields have no separators (positional).

| # | Element | Name | Type / Len | R/O/C | JSON field |
|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3, fixed `118` | R | `segmentType` |
| 2 | 84 | Segment Length | N, **4** | R | `segmentLength` |
| 3 | 86 | Sequence Number | N, 6 | R | `sequenceNumber` |
| 4 | 44 | Information Byte | N, 1 | R | `informationByte` |
| 5 | 102 | Terminal Identifier | AN, 13 | R | `terminalIdentifier` |
| 6 | 78 | Prompt Code | N, 3 | R | `promptCode` |
| 7 | 182 | Prompt Code, Pending | N, 4 | R | `promptCodePending` |
| 8 | 176 | Device Card Table Version | N, 35 (7×5-digit sub-versions; `99999...` = no card table) | R | `deviceCardTableVersion` |
| 9 | 177 | Card Table Load Version | N, 35 | R | `cardTableLoadVersion` |
| 10 | 174 | Card Table Type | N, 4 | R | `cardTableType` |
| 11 | 178 | Load Control Key | AN, 60 | R | `loadControlKey` |
| 12 | 179 | Host Discount Timestamp | N, 12 (CCYYMMDDHHMM) | R | `hostDiscountTimestamp` |
| 13 | 11 | Block Number | N, 3 | C | `blockNumber` |

**Valid values - Prompt Code (Element 78):** `901` Custom receipt text, `902` Dynamic card table, `903` Site configuration data, `904` Host discount data, `905` Fuel volume data (Appendix E).

**Valid values - Prompt Code, Pending (Element 182):** `0901` Custom Receipt Text Data, `0902` Proprietary Data Load, `0904` Host Discount Data, `0981` Electronic Mail.

**Valid values - Card Table Type (Element 174):** `0001` BIN Table, `0002` RULES Table, `0003` RESTRICTIONS Table, `0004` SAF Table, `0005` PROMPT Table, `0006` PRODUCT Table.

## 3. Conditional Payloads (Fields 14+, by Prompt Code)

| Prompt Code | Name | Direction | Fields | Max length |
|---|---|---|---|---|
| 901 | Custom Receipt Text Data | Response | 14-20 (Start/End Date/Time, Number of Lines, repeated Text Length/Data) | up to 220 bytes |
| 902 | Dynamic Card Table Data | Response | 14 (Card Table Data) | up to 3,600 bytes |
| 903 | Site Configuration Data | Request | 14 (Site Configuration Data) | up to 3,600 bytes |
| 904 | Host Discount Data | Response | 14-27 (Timestamp, Number of Discounts, repeated discount block) | up to 3,600 bytes |
| 905 | Fuel Volume Data | Request | 14 (Fuel Volume Data) | up to 3,600 bytes |

## 4. Source-Derived Rules

The complete machine-readable set is in [coverage/segment-118-rule-catalog.json](coverage/segment-118-rule-catalog.json). The Item 1 catalog includes 30 rules: 28 solidly source-confirmed (purpose, lengths, envelope, all 13 core fields, all five conditional payloads, the proprietary-load-specific Response Code catalog, the Site Configuration block lifecycle, and the Host Discount cross-reference to Segment 102), and 2 with a narrow provisional gap (the Information Byte value catalog, and a formatting nuance in the Custom Receipt Text repeat block).

## 5. Training Status

| Methodology Item | Status | Evidence / gate |
|---|---|---|
| 1. Coverage closure | SUBSTANTIALLY_COMPLETE | 30 source-derived rules cataloged; payload tests cover core fields, request/response direction, and all five Prompt Code variants using synthetic Test Solution fixtures. `Segment118WireFormatValidator` checks request separators (including empty fields and the separator after field 13) and positional response encoding for core fields 1-13. |
| 2. AI artifact comparison | REJECTED_NOT_INTAKE_READY | 107 Run1 BRs and 608 Run2 trace rows reviewed; zero of 578 linked physical payloads contains Segment 118, all rows declare 2025-3 instead of 2026-3, and phase-1 stops before TC/TD. See the [BR mapping report](coverage/segment-118-supplied-pipeline-ai-coverage-report.md) and [independent chain verification](../../../../test-output/ai-solution-independent-review/segment-118-ai-chain-verification.json). |
| 3. Test-data independence | BLOCKED | Requires the Information Byte value catalog and approved fixtures (`SEG118-SME-001`, `SEG118-SME-004`). |
| 4. Traceability matrix | BLOCKED | Requires AI artifact sign-off. |
| 5. Mutation definition | BLOCKED | Requires the Information Byte value catalog. |
| 6. Mutation execution | NOT_STARTED | Requires Item 5 plus approved test packages. |
| 7. Validator enhancement | NOT_STARTED | Runs after measurable mutation execution. |
| 8. Consolidated report | NOT_STARTED | Requires all prior gates and SME decisions. |

## 6. Do-Not-Assume Rules

1. Do not treat Segment 118 as a Data Section 3 companion of a generic Financial Transaction Request. Its request explicitly excludes Segment 100 and travels in its own dedicated Proprietary Data Load Request envelope.
2. Do not assume every Prompt Code (901-905) payload shares the same direction. 901, 902, and 904 are response-only payloads; 903 and 905 are request-only payloads.
3. Do not invent Information Byte (44) values; the source states its purpose but not its value catalog (`SEG118-SME-001`).
4. Do not assume Receipt Text Data (170) is always exactly 20 bytes; its actual content length is separately given by Receipt Text Data Length (169) (`SEG118-SME-002`).
5. Do not certify Response Code (83) values beyond the ones explicitly tied to Proprietary Data Load, Host Discount, and Custom Receipt Text pending states (H, O, T, U, V, W, X, Y, plus D/E/M/N as pending indicators); other Response Code values belong to unrelated message families.
6. Do not record or use real card-table data, host-discount terms, site-configuration data, or receipt text in test data beyond synthetic placeholders.
7. Do not claim AI coverage from framework-generated fixtures; AI-produced BR, TS, TC, and TD packages remain independent evidence under test.

## 7. Next Training Actions

1. Provide answers for the items in the SME/TBA input register (Information Byte value catalog, Receipt Text Data encoding nuance, AI artifact authoritative source, and sanitized fixtures for each Prompt Code variant).
2. Obtain SME-approved sanitized, converter-ready request/response examples for each Prompt Code flow; the current synthetic fixtures are oracle regression data only.
3. Require AI rework to produce valid Segment 118 BR -> TS -> TC -> TD -> mapping evidence, then repeat independent intake. Continue Items 3-8 using the Segment 100 methodology without treating synthetic fixtures as AI-artifact evidence.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-118-rule-catalog.json) (30 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 118 |
|---|---|
| SME/TBA learning note | [Learning note](segment-118-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-118-flow.md) |
| Topic deep-dives | [block-lifecycle](block-lifecycle-sme-tba-note.md) · [card-table-load](card-table-load-sme-tba-note.md) · [custom-receipt-text](custom-receipt-text-sme-tba-note.md) · [host-discount-data](host-discount-data-sme-tba-note.md) · [site-configuration-and-fuel-volume](site-configuration-and-fuel-volume-sme-tba-note.md) |
| Topic flows | [block-lifecycle](block-lifecycle-flow.md) · [card-table-load](card-table-load-flow.md) · [custom-receipt-text](custom-receipt-text-flow.md) · [host-discount-data](host-discount-data-flow.md) · [site-configuration-and-fuel-volume](site-configuration-and-fuel-volume-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-118-proprietary-load-business-requirements.md](segment-118-proprietary-load-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-118-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-118-sme-tba-input-register.md) |
