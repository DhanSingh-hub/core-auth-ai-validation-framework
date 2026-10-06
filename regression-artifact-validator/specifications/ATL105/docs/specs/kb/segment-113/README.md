# Segment 113 (ECA/TeleCheck® Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Section:** 12.12 ECA/TeleCheck® Data Segment (pages 12-32 to 12-33), 11.3 ECA/TeleCheck® Service Transactions (pages 11-13 to 11-15), 10.8 Check Processing Requirements (pages 10-53 to 10-55), Elements 131-137 (chapter 13.2)
**Training Handbook:** [ATL105 Segment Training Handbook](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) (8-Item Framework)
**Item Progress:** Item 1 — Coverage Closure complete; SME intake held 2026-09-22 (5 of 6 open items resolved, see [SME/TBA Input Register](segment-113-sme-tba-input-register.md))

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

---

## Learning Module Index (mirrors [Segment 100 Learning Module](../segment-100/README.md) topic-note pattern)

- [SME and Technical Business Analysis Note](segment-113-sme-tba-learning-note.md)
- [Segment 113 End-to-End Flow](segment-113-flow.md)
- [Account Number / MICR Relationship Note](account-number-sme-tba-note.md)
- [Account Number / MICR Relationship Flow](account-number-flow.md)
- [Prompt Code / Card Type Routing Note](prompt-code-sme-tba-note.md)
- [Prompt Code / Card Type Routing Flow](prompt-code-flow.md)
- [Lifecycle and Correlation Note](lifecycle-sme-tba-note.md)
- [Lifecycle and Correlation Flow](lifecycle-flow.md)
- [Coverage Closure](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Segment 113 Rule Catalog (authoritative)](coverage/segment-113-rule-catalog.json)
- [SME/TBA Input Register](segment-113-sme-tba-input-register.md)

### Segment 100 Topics Not Mirrored (and Why)

- **Partial Approval** (Segment 100's `partial-approval-*` topic, Element 121): no specification text applies partial-approval capability to check/ECA-TeleCheck transactions (Section 10.8's Check Processing Requirements never mentions it, unlike the credit/debit/EBT sections). Not mirrored here; do not fabricate a Segment 113 partial-approval rule.

---

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 113 | Section 12.12 |
| Segment name | ECA/TeleCheck® Data Segment | Section 12.12 heading |
| Purpose | ECA/TeleCheck® check-service risk-control data carried in the dedicated ECA/TeleCheck® Service Transaction Request | Section 12.12 opening |
| Placement | Can appear in any of the fields in Data Section No. 3 | Section 12.12 opening |
| Origin | Device | Section 12.12 opening |
| Segment length range | 001–156 alphanumeric characters | Section 12.12 opening |
| Included when | ECA/TeleCheck® Service Transaction Request, alongside Segment 110 (required) and Segment 111 (optional) | Section 11.3.1 Data Section 3 table |
| Message family | ECA/TeleCheck® Service Transaction Request (conditional, Field No. 6). Financial Transaction Request presence is **REVIEW_REQUIRED** (P-09, reopened 2026-09-29) | Section 11.3.1; the Chapter 12 matrix marks 113 in the Financial Transaction Request column (like Segment 110) and Section 12.12 says it can appear in any Data Section 3 field; only the Section 11.1.1 table omits it |
| Companion segments | Segment 110 (Check Data Segment, required), Segment 111 (Variable Information Data Segment, optional) | Section 11.3.1 Data Section 3 table |

---

## 2. Field Layout

| # | Element | Name | Type / Len | R/O/C | Rule Anchor | JSON field |
|---|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | `segment-type` (fixed value 113) | `SegmentType` |
| 2 | 84 | Segment Length | N, 3 | R | `segment-length` | `SegmentLength` |
| 3 | 131 | ECA/TeleCheck® Clerk ID | AN, 6 | R | `eca-clerk-id` | `EcaClerkId` |
| 4 | 132 | ECA/TeleCheck® Product Code | AN, 6 | C | `eca-product-code` | `EcaProductCode` |
| 5 | 133 | ECA/TeleCheck® Phone Number | N, 10 | O | `eca-phone-number` | `EcaPhoneNumber` |
| 6 | 134 | ECA/TeleCheck® Trace ID | AN, 22 | C | `eca-trace-id` | `EcaTraceId` |
| 7 | 135 | Merchant Trace ID | AN, 25 | O | `merchant-trace-id` | `MerchantTraceId` |
| 8 | 136 | Denial Record Number | AN, 7 | C | `denial-record-number` | `DenialRecordNumber` |
| 9 | 137 | Extended MICR Data | AN, 65 | C | `extended-micr-data` | `ExtendedMicrData` |

Maximum Segment 113 length is **156 alphanumeric characters**. Unlike Segment 108, there is no field-order reversal quirk — fields 3-9 map directly to elements 131-137 in ascending order.

---

## 3. Rule Set — Approved (Directly Derived from Specification)

Rule ID prefix: `SEG113-R-###`. Every rule carries a canonical source anchor: `spec | version | section | segment | element | rule`. See [the authoritative catalog](coverage/segment-113-rule-catalog.json) for the full machine-readable list (18 rules).

| Rule ID | Title | Class |
|---|---|---|
| SEG113-R-001 | Exclusive to ECA/TeleCheck Service Transaction Request (PROVISIONAL, P-09 reopened) | structure |
| SEG113-R-002 | Conditional member of that request's Data Section 3 | applicability |
| SEG113-R-003 | Segment Type is 113 | field |
| SEG113-R-004 | Segment Length is 3 digits | field |
| SEG113-R-005 | Maximum length 156 characters | serialization |
| SEG113-R-006 | Field order matches Section 12.12 (no reversal quirk) | serialization |
| SEG113-R-007 | Field Separators required, including for empty fields | serialization |
| SEG113-R-008 | ECA/TeleCheck Clerk ID alphanumeric max 6, required | field |
| SEG113-R-009 | ECA/TeleCheck Product Code alphanumeric max 6, free-form | field |
| SEG113-R-010 | ECA/TeleCheck Phone Number numeric max 10 | field |
| SEG113-R-011 | ECA/TeleCheck Trace ID alphanumeric max 22 (Void-required, catalog only) | field |
| SEG113-R-012 | Merchant Trace ID alphanumeric max 25 | field |
| SEG113-R-013 | Denial Record Number alphanumeric max 7 | field |
| SEG113-R-014 | Extended MICR Data alphanumeric max 65 (cross-segment note) | field |
| SEG113-R-015 | Exactly one Segment 113 per message | structure |
| SEG113-R-016 | Originates at the device | metadata |
| SEG113-R-017 | Companions are Segment 110 (required) and Segment 111 (optional) | compatibility |
| SEG113-R-018 | Not present in the ECA/TeleCheck Service Transaction Response | lifecycle |

---

## 4. SME/TBA Resolutions (2026-09-22 Intake)

See the [SME/TBA Input Register](segment-113-sme-tba-input-register.md) for the full table. Summary:

- **P-01** (Element 63 vs Section 11.3.1 discrepancy) → Section 11.3.1's table governs; Segment 113 is real.
- **P-02** (Product Code enumeration) → confirmed free-form, no external code table.
- **P-03** (Void-requires-Trace-ID enforcement) → catalog only, not code-enforced.
- **P-04** (cross-segment Extended MICR Data enforcement) → catalog only, not code-enforced.
- **P-05** (real AI artifacts) → still OPEN, pending intake.
- **P-06** (real test data) → resolved to proceed with synthetic placeholders.
- **P-09** (Financial Transaction Request presence) → **OPEN**, reopened 2026-09-29: the Chapter 12 matrix and Section 12.12 allow it; only the Section 11.1.1 table omits it.
- **P-10** (are ECA/TeleCheck voids supported?) → **OPEN**: Section 10.8.2 lists Purchase only, but the Trace ID field is required on Void requests.

---

## 5. Cross-Reference to Segment 108 Framework

| Segment 108 file | Segment 113 counterpart | Status |
|---|---|---|
| `docs/specs/kb/segment-108/README.md` | `docs/specs/kb/segment-113/README.md` | ✅ this file |
| `docs/specs/kb/segment-108/coverage/segment-108-rule-catalog.json` | `docs/specs/kb/segment-113/coverage/segment-113-rule-catalog.json` | ✅ produced |
| `docs/specs/kb/segment-108/segment-108-flow.md` | `docs/specs/kb/segment-113/segment-113-flow.md` | ✅ produced |
| `src/main/java/…/Segment108PayloadValidator.java` | `Segment113PayloadValidator.java` | ⏭ next |
| `src/main/java/…/Segment108ArtifactComparison.java` … `ConsolidatedReport.java` | `Segment113*` (7 more classes) | ⏭ next |

---

## 6. Do-Not-Assume Rules

1. Do not certify or reject a Financial Transaction Request carrying Segment 113 until P-09 is answered: the Chapter 12 matrix and Section 12.12 allow it, while the Section 11.1.1 table omits it.
2. Do not treat Element 63's incomplete summary as authoritative over Section 11.3.1's explicit layout table — SME-confirmed resolution favors 11.3.1.
3. Do not invent an enumeration for Element 132 (ECA/TeleCheck Product Code) — confirmed free-form.
4. Do not enforce the Void-requires-Trace-ID or cross-segment Extended-MICR-Data business conditions in the payload validator — both are cataloged only per SME direction.
5. Do not assert Segment 113 appears in the ECA/TeleCheck Service Transaction Response — Section 11.3.2 is unambiguous and undisputed (unlike Segment 108's P-03).
6. Do not use Card Type `070` (Valero Fleet) for ECA/TeleCheck fixtures — the correct check-processing codes are `041` (Certegy), `045` (Generic check), and `046` (ECA/TeleCheck Service); all Segment 113 fixtures use `046`.
7. Do not treat Section 11.3.1's field-table "R" (Required) marking for Segment 113 as overriding its own narrative "none, one, or more" applicability language — SME-confirmed 2026-09-22 to keep Segment 113 conditional, not required (`SEG113-SME-007`).
8. Do not assume Segment 100's Partial Approval Indicator (Element 121) applies to check/ECA-TeleCheck transactions — no specification text supports this; that topic is intentionally not mirrored here.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-113-rule-catalog.json) (18 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 113 |
|---|---|
| SME/TBA learning note | [Learning note](segment-113-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-113-flow.md) |
| Topic deep-dives | [account-number](account-number-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle](lifecycle-sme-tba-note.md) · [prompt-code](prompt-code-sme-tba-note.md) |
| Topic flows | [account-number](account-number-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle](lifecycle-flow.md) · [prompt-code](prompt-code-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-113-business-requirements.md](segment-113-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-113-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-113-sme-tba-input-register.md) |
