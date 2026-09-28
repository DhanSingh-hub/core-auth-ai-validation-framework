# ATL105 Segment 116 TransArmor Business Requirements

## Scope and provenance

This catalog defines the business requirements for the TransArmor Key and Key ID Load Request (Data Segment No. 116) that are independently confirmed or safely pattern-derived from the ATL105 2026-3 extract available in this workspace. It also records the requirements that remain open pending the external `BUYPASS®_Platform_ATL105_Specification_Updates_for_TransArmor_Processing` document.

Unlike [ATL105 Segment 100 Financial Card-Type Business Requirements](../segment-100/financial-card-type-business-requirements.md), which catalogs a large confirmed value set (36 card-type codes), this catalog is necessarily small: Segment 116's own specification sections are stubs (see the [Segment 116 README](README.md)), so most of what would normally be a rich field-by-field requirement list is instead a small confirmed core plus a longer list of explicitly open questions.

These are requirements for the TransArmor Key/Key ID Load dimension of the platform. They do not replace Segment 100, Segment 109, Segment 110, or Segment 111 requirements, which are trained independently.

## Common requirements (confirmed)

| ID | Requirement | Acceptance criteria |
|---|---|---|
| TA-116-001 | A TransArmor PKI Encryption and Tokenization Load Request shall contain Segment 116 in Data Section 2. | `TransArmor Request.Data Section 2.SegmentType` is `116`. |
| TA-116-002 | Segment 116's Segment Type shall be the fixed value `116`. | `SegmentType == "116"`. |
| TA-116-003 | Segment 116's serialized length shall not exceed 50 alphanumeric characters. | `len(serialized Segment 116) <= 50`. |
| TA-116-004 | Segment 116 shall not be confused with Segment 119 (Totals with Proprietary Data Load Data Segment), regardless of the Section 11.4.1.2 label. | A fixture labeled "(Data Segment No. 116)" for a Totals-with-load request is rejected or relabeled to 119. |
| TA-116-005 | An approved Key/Key ID Load shall return a Key ID (Element 155) that the device reuses for all subsequent TransArmor transactions. | `KeyId` is exactly 11 alphanumeric characters and is persisted by the device. |
| TA-116-006 | A TransArmor Load Response shall include Key Data Length (156) and Key Data (157) alongside Key ID (155). | All three fields present; `KeyDataLength` numeric 000-999; `KeyData` alphanumeric up to 999 bytes. |
| TA-116-007 | Additional TransArmor Data (Segment 111, Table ID 052) shall include a KSN sub-table for AES DUKPT, TDES, and Ingenico OnGuard encryption types. | Sub-Table 01 present, up to 40 bytes. |
| TA-116-008 | Additional TransArmor Data shall include a Device Type sub-table for AES DUKPT encryption; it is optional for TDES and Ingenico OnGuard. | Sub-Table 02 present (required) or absent (optional per encryption type), up to 8 bytes. |

## Requirements pending the external TransArmor document

| ID | Requirement (proposed, unconfirmed) | Why it cannot be certified yet |
|---|---|---|
| TA-116-P01 | Segment 116 shall carry [unknown fields] beyond Segment Type and Segment Length within its 50-byte maximum. | Sections 12.15 and 11.7.5 are stubs; no field table is available (`SEG116-SME-001`). |
| TA-116-P02 | The TransArmor Load Request shall exclude Segment 100 and Data Section 3. | This is a structural analogy with sibling message families, not a directly shown request table (`SEG116-SME-002`). |
| TA-116-P03 | The TransArmor Load Response shall be [a Segment 116 container / a separate positional structure]. | The response's container structure is not shown in this source (`SEG116-SME-003`). |
| TA-116-P04 | Additional TransArmor Data (Segment 111, Table 052) shall/shall not always accompany a Segment 116 request. | The relationship between the two is not stated (`SEG116-SME-004`). |
| TA-116-P05 | Sequence Number (Element 86) shall/shall not be a Segment 116 field. | Only a generalized, low-certainty AI lead exists; no segment-specific confirmation (see the [BR Coverage Report](../../../../test-output/ai-artifacts/coverage-reports/segment-116/POC-AI-Segment-116-BR-Coverage-Report.md)). |

## Traceability

Every confirmed requirement (`TA-116-001` through `TA-116-008`) traces to a rule in [coverage/segment-116-rule-catalog.json](coverage/segment-116-rule-catalog.json) (`SEG116-R-001` through `SEG116-R-009`). Every pending requirement (`TA-116-P01` through `TA-116-P05`) traces to an entry in the [Segment 116 SME/TBA Input Register](segment-116-sme-tba-input-register.md). Do not promote a pending requirement to confirmed status without recording the source or SME decision that resolved it.
