# Segment 103 (EBT Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Section:** 12.4 EBT Data Segment (pages 12-15) and Elements 18, 109, 153, 154, 164 (chapter 13.2)
**Training Methodology:** [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../../../SEGMENT-100-TRAINING-METHODOLOGY.md) (8-Item Framework)
**Item Progress:** All spec-groundable provisional items (P-01 through P-06) are resolved directly from the ATL105 text — applicability matrix, full WIC Product Data and EBT Program Data layouts, eWIC Return prohibition, eWIC prompt-code enumeration, and Appendix L currency codes. Items 1, 3, 5, 6, and 7 are executable. Item 2 and the external-data replacement in Item 4 remain open only on P-07/P-08 (real AI artifacts / real test data), which are not resolvable from the specification and require external delivery.

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

---

## Learning Module Index (mirrors [Segment 100 Learning Module](../segment-100/README.md))

- [Segment Training Methodology](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md)
- [Reusable Segment Training Questionnaire](../../../test-validation-strategy/SEGMENT-TRAINING-QUESTIONNAIRE.md)
- [SME and Technical Business Analysis Note](segment-103-sme-tba-learning-note.md)
- [Segment 103 End-to-End Flow](segment-103-flow.md)
- [Coverage Closure](coverage/README.md)
- [Applicability SME/TBA Note](applicability-sme-tba-note.md)
- [Applicability Decision Flow](applicability-flow.md)
- [Voucher Lifecycle SME/TBA Note](voucher-lifecycle-sme-tba-note.md)
- [Voucher Lifecycle Flow](voucher-lifecycle-flow.md)
- [WIC Product Data SME/TBA Note](wic-product-data-sme-tba-note.md)
- [WIC Product Data Flow](wic-product-data-flow.md)
- [EBT Program Data SME/TBA Note](ebt-program-data-sme-tba-note.md)
- [EBT Program Data Flow](ebt-program-data-flow.md)
- [eWIC Prompt/Lifecycle SME/TBA Note](ewic-prompt-lifecycle-sme-tba-note.md)
- [eWIC Prompt/Lifecycle Flow](ewic-prompt-lifecycle-flow.md)
- [Sequence/Lifecycle Correlation SME/TBA Note](sequence-lifecycle-sme-tba-note.md)
- [Sequence/Lifecycle Flow](sequence-lifecycle-flow.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Final Closure SME/TBA Note](final-closure-sme-tba-note.md)
- [Final Closure Flow](final-closure-flow.md)
- [Segment 103 Rule Catalog (authoritative)](coverage/segment-103-rule-catalog.json)
- [Segment 103 Core Structure Package](../../../../test-output/test-json/segment-103-core-structure-package.json)
- [Segment 103 Validation Rules](coverage/segment-103-rule-catalog.json)
- [Segment 103 Consolidated Report](../../../../SEGMENT-103-CONSOLIDATED-REPORT.txt)
- **AI Solution Coverage Report**
	- [JSON](../../../../test-output/ai-artifacts/coverage-reports/POC-AI-Segment-103-BR-Coverage-Crosswalk.json)
	- [Markdown](../../../../test-output/ai-artifacts/coverage-reports/POC-AI-Segment-103-BR-Coverage-Report.md)
	- [Visual HTML](../../../../test-output/ai-artifacts/coverage-reports/POC-AI-Segment-103-BR-Coverage-Ratio-Report.html)

---

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 103 | Section 12.4 |
| Segment name | EBT Data Segment | Section 12.4 heading |
| Purpose | EBT/SNAP/cash-benefit/WIC/eWIC companion data carried alongside Segment 100 | Section 12.4 opening |
| Placement | Any field in Data Section No. 3 | Section 12.4 opening ("This data segment can appear in any of the fields in Data Section No. 3.") |
| Origin | Device | Section 12.4 opening |
| Segment length range | 001–3,334 alphanumeric characters | Element 84 valid-values table |
| Included when | Per the resolved applicability matrix in Section 7 below (`SEG103-R-024`) | Section 10.5.2–10.5.6 |
| Compatibility | No documented mutual exclusion with another Data Section 3 segment | Section 12.4 (silent on this point; do not assume a conflict) |

---

## 2. Field Layout

| # | Element | Name | Type / Len | R/O/C | Rule Anchor | JSON field |
|---|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | `segment-type` (fixed value 103) | `SegmentType` |
| 2 | 84 | Segment Length | N, 3 or 4 | R | `segment-length` (4 digits required for EBT-with-eWIC) | `SegmentLength` |
| 3 | 18 | Clerk ID | N, 10 | C | `clerk-id` | `ClerkId` |
| 4 | 109 | Voucher ID | N, 10 | C | `voucher-id` | `VoucherId` |
| 5 | 153 | WIC Discount Amount | N, 40 | C | `wic-discount-amount-format` / `wic-discount-amount-max-040` | `WicDiscountAmount` |
| 6 | 154 | WIC Product Data | AN, 3001 | C | `wic-product-data-total-length` | `WicProductData` (object: `totalLength`, `data`) |
| 7 | 164 | EBT Program Data | AN, 267 | C | `ebt-program-data-total-length` / `-subelement-count` | `EbtProgramData` (object: `totalLength`, `subelements[]`) |

Maximum Segment 103 length is **3,334 alphanumeric characters** (Section 12.4).

### 2.1 WIC Discount Amount (Element 153) Positional Layout

| Position | Content | Valid value |
|---|---|---|
| 1–2 | Account Type | Fixed `97` |
| 3–4 | Amount Type | Fixed `52` |
| 5–7 | Currency Code | Appendix L valid currency code (full table transcribed into `AppendixLCurrencyCodes.java`, resolves P-06) |
| 8–20 | Amount | 1-char sign (`0`, `C` credit, or `D` debit) + 12-digit amount |

A populated WIC Discount Amount value is one or more 20-byte blocks of this layout, up to a 40-byte (2-block) maximum.

### 2.2 EBT Program Data (Element 164) Subelement Layout

| Subelement | Length | Description |
|---|---|---|
| Total Length | 3 digits, fixed, right-aligned/zero-padded, max value 264 | Count of all bytes in the Program Data subelement(s) that follow |
| Program Data (1–6 occurrences) | up to 44 bytes each | `TAG` (2 chars) + `LEN` (2 digits) + detail data; `ACCOUNT TYPE` fixed `98` required when `TAG=50` |

Documented `TAG` values: `50` (HIP purchase/return amount, request), `IT` (HIP Internet purchase shipping address/zip, request), `51` (HIP incentive earned/returned, response), `52` (HIP month-to-date incentive earned, response). Full positional layout (LEN, AMOUNT TYPE, CURRENCY CODE 840, AMOUNT DESCRIPTOR, DETAIL, or IT address/zip) confirmed against both Appendix M worked examples and enforced by `SEG103-R-022` (resolves P-03).

### 2.3 Important Serialization Rule

For a **Financial Transaction request**, every Segment 103 field is separated by a Field Separator; an empty field still sends its separator. For a **Financial Transaction response**, there is **no** Field Separator between Segment 103 fields. See [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md).

---

## 3. Rule Set — Approved (Directly Derived from Specification)

Rule ID prefix: `SEG103-R-###`. Every rule carries a canonical source anchor: `spec | version | section | segment | element | rule`. See [the authoritative catalog](coverage/segment-103-rule-catalog.json) for the full machine-readable list (24 rules).

| Rule ID | Title | Class |
|---|---|---|
| SEG103-R-001 | Data Section 3 companion, usable in any field slot | structure |
| SEG103-R-002 | Required per the applicability matrix (see R-024) | applicability |
| SEG103-R-003 | Segment Type is 103 | field |
| SEG103-R-004 | Segment Length is 3 or 4 digits | field |
| SEG103-R-005 | Maximum length 3,334 characters | serialization |
| SEG103-R-006 | Field order matches Section 12.4 | serialization |
| SEG103-R-007 | Request: separators preserved for empty fields | serialization |
| SEG103-R-008 | Response: no field separators | serialization |
| SEG103-R-009 | Clerk ID numeric max 10 | field |
| SEG103-R-010 | Voucher ID numeric max 10 | field |
| SEG103-R-011 | WIC Discount Amount positional format + Appendix L currency | field |
| SEG103-R-012 | WIC Discount Amount max 40 bytes | field |
| SEG103-R-013 | WIC Product Data bounded, Total Length subelement | field |
| SEG103-R-014 | EBT Program Data bounded, Total Length subelement | field |
| SEG103-R-015 | EBT Program Data 1-6 subelements, 44 bytes each | field |
| SEG103-R-016 | EBT Program Data TAG enumeration | field |
| SEG103-R-017 | TAG 50 requires ACCOUNT TYPE 98 | field |
| SEG103-R-018 | Exactly one Segment 103 per message | structure |
| SEG103-R-019 | Originates at the device | metadata |
| SEG103-R-020 | eWIC does not support Return (hard prohibition) | lifecycle |
| SEG103-R-021 | eWIC prompt-code enumeration (literal table) | compatibility |
| SEG103-R-022 | EBT Program Data full Appendix M positional layout | field |
| SEG103-R-023 | WIC Product Data subelement catalog (EF/EA/PS) | field |
| SEG103-R-024 | Segment 103 applicability matrix | applicability |

---

## 4. `[PROVISIONAL]` Items Requiring SME / TBA Input

P-01 through P-06 are **resolved** directly from the ATL105 specification text; see the catalog's `provisionalItems` array for the exact citations and resolutions. Only two items remain open, and both require artifacts external to the specification rather than SME interpretation:

- **P-07**: no real AI-generated Segment 103 artifacts exist yet; Item 2 (AI Artifact Comparison) runs against a placeholder package until the producer team delivers real output.
- **P-08**: no real production Segment 103 sample JSONs exist yet; training continues on the existing synthesized `.synthetic.json` fixtures, matching the precedent set for Segment 101 (P-09/P-10).

---

## 5. Cross-Reference to Segment 101 Framework

| Segment 101 file | Segment 103 counterpart | Status |
|---|---|---|
| `docs/specs/kb/segment-101/README.md` | `docs/specs/kb/segment-103/README.md` | ✅ this file |
| `docs/specs/kb/segment-101/coverage/segment-101-rule-catalog.json` | `docs/specs/kb/segment-103/coverage/segment-103-rule-catalog.json` | ✅ produced |
| `docs/specs/kb/segment-101/segment-101-flow.md` | `docs/specs/kb/segment-103/segment-103-flow.md` | ✅ produced |
| `src/main/java/…/Segment101PayloadValidator.java` | `Segment103PayloadValidator.java` | ✅ produced |
| `src/main/java/…/Segment101ArtifactComparison.java` | `Segment103ArtifactComparison.java` | ✅ produced |
| `src/main/java/…/Segment101IndependenceValidator.java` | `Segment103IndependenceValidator.java` | ✅ produced |
| `src/main/java/…/Segment101TraceabilityMatrix.java` | `Segment103TraceabilityMatrix.java` | ✅ produced |
| `src/main/java/…/Segment101MutationTester.java` | `Segment103MutationTester.java` | ✅ produced |
| `src/main/java/…/Segment101MutationTestRunner.java` | `Segment103MutationTestRunner.java` | ✅ produced |
| `src/main/java/…/Segment101ConsolidatedReport.java` | `Segment103ConsolidatedReport.java` | ✅ produced |
| `src/main/java/…/Segment103WireFormatValidator.java` | `Segment103WireFormatValidator.java` | ✅ produced; covers request/response separators, field order, device origin, eWIC prompt-code boundary, and the hard eWIC-Return prohibition |
| — | `Segment103ApplicabilityValidator.java` | ✅ produced; encodes the resolved SEG103-R-002/SEG103-R-024 applicability matrix |
| — | `AppendixLCurrencyCodes.java` | ✅ produced; full Appendix L currency-code table used by SEG103-R-011 |

## 5.1 Segment 100-Style Training Coverage

| Segment 100 learning concern | Segment 103 training package | Coverage |
|---|---|---|
| Account/entry dependency | Voucher lifecycle and EBT applicability | Voucher ID, local approval, and required/optional/prohibited decisions |
| Sequence/lifecycle | Sequence/lifecycle correlation and eWIC prompt lifecycle | Authorization, completion, reversal/void, cancellation, voucher clear |
| Partial approval/context | Applicability and WIC/EBT context | EBT/eWIC data presence drives Segment 103 inclusion |
| Prompt Code | eWIC Prompt/Lifecycle package | `3086`, `S086`, `E086`, `0086`, `8086` |
| Companion compatibility | Companion compatibility package | No undocumented mutual exclusion invented |
| Serialization | Serialization/wire-format package | Request separators versus response concatenation |
| Final closure | Final closure package | Rule -> BR -> TS -> TC -> TD -> validator -> mutation evidence |

---

## 6. Do-Not-Assume Rules

1. Do not certify a companion-segment mutual exclusion for Segment 103 — none is documented; treat any such claim as `REVIEW_REQUIRED`.
2. Do not apply request-side field-separator rules to a response payload, or vice versa (`SEG103-R-007` vs `SEG103-R-008`).
3. Do not treat the Element 164 TAG catalog (`50`, `IT`, `51`, `52`) as extensible without a spec citation — unknown tags remain rejected, not silently accepted (`SEG103-R-016`).
4. eWIC Return is an absolute prohibition per Section 10.5.5.1 (`SEG103-R-020`) — `Segment103WireFormatValidator` raises a hard error, not a warning, whenever one is attempted.
5. Do not infer Segment 103 applicability from card type alone — use the resolved applicability matrix (`SEG103-R-024`, `Segment103ApplicabilityValidator`): `REQUIRED` for Food Stamp Electronic Voucher and eWIC Purchase Completion/Voucher Clear, `OPTIONAL` for all other listed EBT/eWIC transaction types, `PROHIBITED` for eWIC Return.

## 7. Segment 103 Applicability Matrix (resolves SEG103-R-002 / SEG103-R-024)

| Transaction / Lifecycle Role | Applicability | Basis |
|---|---|---|
| Food Stamp Purchase | OPTIONAL | Section 10.5.3; Clerk ID/HIP data are conditional, not mandated by the transaction definition |
| Food Stamp Reversal (Void) | OPTIONAL | Section 10.5.3 |
| Food Stamp Return | OPTIONAL | Section 10.5.3; HIP Incentive Returned is state-dependent (Section 10.5.4.8, 10.5.6) |
| Cash Benefit Purchase | OPTIONAL | Section 10.5.3 |
| Cash Benefit Reversal (Void) | OPTIONAL | Section 10.5.3 |
| Food Stamp Balance Inquiry | OPTIONAL | Section 10.5.3 |
| Food Stamp Electronic Voucher | **REQUIRED** (Voucher ID) | Section 10.5.2.3: "the manually entered Voucher Number must appear on the receipt" |
| Cash Benefit Purchase with Cash Back | OPTIONAL | Section 10.5.3 |
| Cash Benefit Balance Inquiry | OPTIONAL | Section 10.5.3 |
| Time-out Reversal | OPTIONAL | Section 10.5.3; mirrors the original transaction's Segment 103 presence |
| Food Stamp Void of a Merchandise Return | OPTIONAL | Section 10.5.3 |
| eWIC Authorization / Benefits Inquiry | OPTIONAL (request); WIC Product Data appears in the response | Section 13.2 Element 154 |
| eWIC Authorization Cancellation | OPTIONAL | Section 10.5.5.1 |
| eWIC Balance Inquiry | OPTIONAL (request); WIC Product Data appears in the response | Section 13.2 Element 154 |
| eWIC Purchase Completion | **REQUIRED** (WIC Discount Amount / WIC Product Data) | Section 10.5.5.6: these are the new eWIC-specific Segment 103 fields |
| eWIC Purchase Reversal/Void | OPTIONAL | Section 10.5.5.1 |
| eWIC Voucher Clear | **REQUIRED** (WIC Discount Amount / WIC Product Data) | Section 10.5.5.1, 10.5.5.6 |
| eWIC Return | **PROHIBITED** | Section 10.5.5.1: "These specifications do not support Return transactions." |
