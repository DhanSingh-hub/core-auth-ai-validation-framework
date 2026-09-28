# Segment 110 (Check Data Segment) - Rule Catalog & Specification Anchors

**Specification:** BUYPASS Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Sections:** 11.1.1 Financial Transactions Request (pages 11-3 to 11-4), 11.3.1 ECA/TeleCheck® Service Transaction Request (pages 11-13 to 11-14), 12.9 Check Data Segment (pages 12-25 to 12-29), Chapter 13 Data Element Descriptions (Elements 84, 85, 122-130, 136, 137, 239), and Appendix D (Valid State Codes)
**Training Methodology:** [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md) (8-item framework)
**Item Progress:** Item 1 - Coverage Closure started; Item 2 - AI Artifact Comparison in progress against the supplied requirement catalog; Items 3-8 blocked pending the manual inputs in [Segment 110 SME/TBA Input Register](segment-110-sme-tba-input-register.md).

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

## Learning Module Index

- [Segment 110 Rule Catalog (authoritative)](coverage/segment-110-rule-catalog.json)
- [Segment 110 SME/TBA Input Register](segment-110-sme-tba-input-register.md)
- [Segment 110 SME/TBA Learning Note](segment-110-sme-tba-learning-note.md)
- [Segment 110 End-to-End Flow](segment-110-flow.md)
- [MICR Data SME/TBA Note](micr-data-sme-tba-note.md)
- [MICR Data Flow](micr-data-flow.md)
- [Check Identification Fields SME/TBA Note](check-identification-fields-sme-tba-note.md)
- [Check Identification Fields Flow](check-identification-fields-flow.md)
- [Alternate MICR Indicator SME/TBA Note](alternate-micr-indicator-sme-tba-note.md)
- [Alternate MICR Indicator Flow](alternate-micr-indicator-flow.md)
- [Companion and Envelope Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion and Envelope Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Learning Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Coverage Closure Index](coverage/README.md)
- [Coverage Closure SME/TBA Note](coverage/segment-110-coverage-sme-tba-note.md)
- [Coverage Closure Flow](coverage/segment-110-coverage-flow.md)
- [Supplied AI Requirement Catalog Coverage Report](coverage/segment-110-supplied-pipeline-ai-coverage-report.md)
- [Segment 110 Canonical Source Anchors](../segment-110-canonical-anchors.md)
- [Segment 110 AI Business Requirements](../../../../test-output/ai-artifacts/business-requirements/POC-AI-ATL105-Segment-110-Business-Requirements.md)
- [Segment 110 BR Coverage Report](../../../../test-output/ai-artifacts/coverage-reports/segment-110/POC-AI-Segment-110-BR-Coverage-Report.md)
- [Segment 110 Traceability Matrix](../../../../test-output/traceability-matrix/segment-110/segment-110-coverage.md)
- [Reusable Segment Training Questionnaire](../../../test-validation-strategy/SEGMENT-TRAINING-QUESTIONNAIRE.md)

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 110 | Section 12.9 |
| Segment name | Check Data Segment | Section 12.9 heading |
| Purpose | Carries MICR, manually keyed, and identification data required for check transactions | Section 12.9 |
| Placement (confirmed) | Field 4 in Data Section No. 3 of the ECA/TeleCheck® Service Transaction Request, Required | Section 11.3.1 |
| Placement (general use) | **SME-approved 2026-09-26:** Segment 110 is also treated as valid in any Financial Transaction Request Data Section 3, consistent with the broader Section 12.9 statement ("can appear in any of the fields in Data Section No. 3"), even though the generic Financial Transaction Request Data Section 3 list in Section 11.1.1 does not separately enumerate it | Section 12.9; SME decision 2026-09-26 |
| Origin | Device | Section 12.9 |
| Segment length range | 001-168 alphanumeric characters | Section 12.9 |
| Request envelope (confirmed) | ECA/TeleCheck® Service Transaction Request: Data Section 1 (Elements 55, 63), Data Section 2 (Segment 100), Data Section 3 (Segments 110, 111, 113) | Section 11.3.1 |
| Response envelope | Reuses the Financial Transaction Response layout; no check-specific response fields are defined in Section 12.9 | Section 11.3.2 |

## 2. Field Layout

All Segment 110 fields are field-separated. Empty fields still require their Field Separator.

| # | Element | Name | Type / Len | R/O/C | JSON field |
|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | `segmentType` |
| 2 | 84 | Segment Length | N, 3 | R | `segmentLength` |
| 3 | 122 | MICR Data | AN, 50 | R | `micrData` |
| 4 | 123 | Driver's License | AN, 40 | C | `driversLicense` |
| 5 | 124 | State Code | AN, 2 | C | `stateCode` |
| 6 | 125 | Date of Birth | N, 8 (MMDDYYYY) | C | `dateOfBirth` |
| 7 | 126 | Check Type | AN, 1 | R | `checkType` |
| 8 | 127 | Check Number | AN, 8 | C | `checkNumber` |
| 9 | 128 | Customer Phone Number | N, 10 | O | `customerPhoneNumber` |
| 10 | 129 | Customer Last Name | AN, 24 | O | `customerLastName` |
| 11 | 130 | Check Issue Date | N, 8 (MMDDYYYY) | O | `checkIssueDate` |
| 12 | 239 | Alternate MICR IND | AN, 1 | O | `alternateMicrIndicator` |

**Valid values - Check Type (Element 126):** `P` Personal, `C` Company (Section 12.9 / Chapter 13 element catalog).

**Valid values - State Code (Element 124):** the 76 alphabetical codes in Appendix D covering the 50 US states, the District of Columbia, US territories (`GU`, `PR`, `VI`), military designations (`AA`, `AE`, `AP`, `XX`), the 10 Canadian provinces/territories (`AB`, `BC`, `MB`, `NB`, `NL`, `NS`, `NT`, `NU`, `ON`, `PE`, `QC`, `SK`, `YT`), and the non-US/non-Canada value `NA`.

**Valid values - Alternate MICR IND (Element 239, Segment 110 context only):** `Y` (Alternate/RAW TOAD MICR format is being sent). No other value is documented; absence of the field implies the default TAC/BUY2 format.

## 3. Source-Derived Rules

The complete machine-readable set is in [coverage/segment-110-rule-catalog.json](coverage/segment-110-rule-catalog.json). The initial Item 1 catalog includes 20 direct rules covering placement, fixed/maximum lengths, field order, separator behavior, conditional-field presence, the Check Type and State Code value catalogs, the Alternate MICR IND field, and three cross-reference/compatibility notes (Extended MICR Data, Segment 100 Account Number, and the Segment 111 Manual Check MICR Type sub-table independently confirmed in Appendix I-17).

## 4. Training Status

| Methodology Item | Status | Evidence / gate |
|---|---|---|
| 1. Coverage closure | SUBSTANTIALLY_COMPLETE | 20 source-derived rules cataloged; Item 1 validator and 11 baseline tests pass. Placement scope and the Element 239 conflict were resolved by SME decision on 2026-09-26; the manually-entered trigger remains open. |
| 2. AI artifact comparison | IN_PROGRESS | 94 AI-generated Segment 110 requirements extracted from the supplied requirement catalog and cross-walked against the 19-rule oracle (see [coverage report](coverage/segment-110-supplied-pipeline-ai-coverage-report.md)). |
| 3. Test-data independence | BLOCKED | Requires approved request examples and conditional-field policy (`SEG110-SME-003`, `SEG110-SME-004`, `SEG110-SME-009`). |
| 4. Traceability matrix | BLOCKED | Requires approved placement scope and AI artifact sign-off. |
| 5. Mutation definition | BLOCKED | Requires the manually-entered trigger and the Element 239 resolution. |
| 6. Mutation execution | NOT_STARTED | Requires Item 5 plus approved test packages. |
| 7. Validator enhancement | NOT_STARTED | Runs after measurable mutation execution. |
| 8. Consolidated report | NOT_STARTED | Requires all prior gates and SME decisions. |

## 5. Do-Not-Assume Rules

1. Segment 110 is **approved (2026-09-26)** as valid in any Financial Transaction Request Data Section 3, not restricted to the ECA/TeleCheck® envelope. Retain the ECA/TeleCheck®-specific Field 4 ordering as the confirmed case, but do not reject Segment 110 solely for appearing in a generic Financial Transaction Request.
2. Do not invent the machine-testable condition for "manually entered"/"manually keyed" check data. It gates Driver's License, State Code, and Check Number, but its trigger field is not in the extracted Segment 110 table. **Remains open (`SEG110-SME-003`).**
3. Element 239 is **approved (2026-09-26)** to be modeled as two distinct, segment-scoped entities: "Alternate MICR IND" for Segment 110 field 12, and "Enhanced Fleet Data" for Segment 145. This is an intentional, permanent `REVIEW_REQUIRED` design choice, not a gap to close.
4. Do not assume Extended MICR Data (Element 137) lives inside Segment 110. Section 12.9's field table has only 12 fields; Element 137 is documented in the Chapter 13 catalog as a companion of MICR Data without a confirmed hosting segment.
5. Do not record or use real MICR line data, driver's license numbers, dates of birth, or phone numbers in test data. Use masked or synthetic values.
6. Do not treat MICR Data (Segment 110) and Account Number (Segment 100) as required to be byte-identical without SME confirmation; the source only states that MICR data "is also included" there.
7. Do not certify a response-code mapping for check transactions; Section 11.3.2 states the response reuses the generic Financial Transaction Response layout, which is out of Segment 110's scope.

## 6. Next Training Actions

1. Provide answers and approved references for the items in the input register.
2. Resolve the Element 239 (Alternate MICR IND vs. Enhanced Fleet Data) source conflict before any mutation or enhancement work depends on it.
3. Add sanitized, converter-ready MICR-read and manually keyed check-transaction examples (personal and company Check Type) for each enabled flow.
4. Continue Items 3-8 using the Segment 100 methodology without treating synthetic fixtures as AI-artifact evidence.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-110-rule-catalog.json) (20 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 110 |
|---|---|
| SME/TBA learning note | [Learning note](segment-110-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-110-flow.md) |
| Topic deep-dives | [alternate-micr-indicator](alternate-micr-indicator-sme-tba-note.md) · [check-identification-fields](check-identification-fields-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [micr-data](micr-data-sme-tba-note.md) |
| Topic flows | [alternate-micr-indicator](alternate-micr-indicator-flow.md) · [check-identification-fields](check-identification-fields-flow.md) · [field-definitions](field-definitions-flow.md) · [micr-data](micr-data-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-110-business-requirements.md](segment-110-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-110-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-110-sme-tba-input-register.md) |
