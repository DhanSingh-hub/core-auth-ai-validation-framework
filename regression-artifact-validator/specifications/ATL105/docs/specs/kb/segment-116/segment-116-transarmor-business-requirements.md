# ATL105 Segment 116 TransArmor Business Requirements

## Scope and provenance

This catalog defines the business requirements for the TransArmor Key and Key ID Load Request (Data Segment No. 116) that are independently confirmed or safely pattern-derived from the ATL105 2026-3 extract available in this workspace. It also records the requirements that remain open pending the external `BUYPASS®_Platform_ATL105_Specification_Updates_for_TransArmor_Processing` document.

Unlike [ATL105 Segment 100 Financial Card-Type Business Requirements](../segment-100/financial-card-type-business-requirements.md), which catalogs a large confirmed value set (36 card-type codes), this catalog is necessarily small: Segment 116's own specification sections are stubs (see the [Segment 116 README](README.md)), so most of what would normally be a rich field-by-field requirement list is instead a small confirmed core plus a longer list of explicitly open questions.

These are requirements for the TransArmor Key/Key ID Load dimension of the platform. They do not replace Segment 100, Segment 109, Segment 110, or Segment 111 requirements, which are trained independently.

## Common requirements (confirmed)

| ID | Requirement | Acceptance criteria |
|---|---|---|
| TA-116-001 | A TransArmor PKI Encryption and Tokenization Load Request shall contain Segment 116 in Field No. 3. | `TransArmor Request.Segment 116.SegmentType` is `116`. |
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

## Catalog-derived requirements

Rules that no `TA-116-*` requirement restates, derived from the rule catalog in the standard segment format.

| ID | Class | Requirement | Acceptance criteria | Source | Status |
|---|---|---|---|---|---|
| BR-SEG116-001 | structure | Segment 116 (TransArmor Load Data Segment) is used for Key and Key ID Load in TransArmor PKI Encryption and Tokenization processing, as stated in the Element 63 and Element 85 processing rules and valid-codes tables | The segment's structural position and composition match the rule. | `SEG116-R-001` §Chapter13-Element63,Chapter13-Element85 | SPEC_DERIVED |
| BR-SEG116-004 | field | Segment Length (Element 84) is Segment 116 field 2, following the Chapter 12 data-segment convention; not confirmed by a Segment 116 table because Section 12.15 is a stub | The element value conforms to the stated rule; a non-conforming value fails validation citing the rule ID. | `SEG116-R-004` §12.15(stub)+Chapter12-convention | REVIEW_REQUIRED |

| ID | Violates | Mutation class | Expected result |
|---|---|---|---|
| BR-SEG116-NEG-001 | `SEG116-R-001` | MUT-010 structural requirement | Validation error citing SEG116-R-001 |
| BR-SEG116-NEG-004 | `SEG116-R-004` | MUT-005 required field omitted | Held at REVIEW_REQUIRED — do not assert until resolved |

## Rule-catalog crosswalk

The rule each existing requirement restates. `PENDING` rows stay unconfirmed until the linked SME item is resolved.

| Requirement | Rule | Match |
|---|---|---|
| TA-116-001 | `SEG116-R-005` | EQUIVALENT (Segment 116 in Field No. 3) |
| TA-116-002 | `SEG116-R-003` | EQUIVALENT |
| TA-116-003 | `SEG116-R-002` | EQUIVALENT |
| TA-116-004 | `SEG116-R-009` | EQUIVALENT |
| TA-116-005 | `SEG116-R-007` | EQUIVALENT (Key ID) |
| TA-116-006 | `SEG116-R-007` | EQUIVALENT (Key Data Length and Key Data) |
| TA-116-007 | `SEG116-R-008` | EQUIVALENT (KSN sub-table) |
| TA-116-008 | `SEG116-R-008` | EQUIVALENT (Device Type sub-table) |
| TA-116-P01 | `SEG116-R-006` | PENDING (`SEG116-SME-001`) |
| TA-116-P02 | `SEG116-R-005` | PENDING (Segment 100 exclusion, `SEG116-SME-002`) |

## Traceability

Every confirmed requirement (`TA-116-001` through `TA-116-008`) traces to a rule in [coverage/segment-116-rule-catalog.json](coverage/segment-116-rule-catalog.json) (`SEG116-R-001` through `SEG116-R-009`); the crosswalk above records the exact rule. Every pending requirement (`TA-116-P01` through `TA-116-P05`) traces to an entry in the [Segment 116 SME/TBA Input Register](segment-116-sme-tba-input-register.md). Do not promote a pending requirement to confirmed status without recording the source or SME decision that resolved it.
