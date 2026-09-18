# Segment 103 (EBT Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Section:** 12.4 EBT Data Segment (pages 12-15) and Elements 18, 109, 153, 154, 164 (chapter 13.2)
**Training Methodology:** [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../../../SEGMENT-100-TRAINING-METHODOLOGY.md) (8-Item Framework)
**Item Progress:** Item 1 — Coverage Closure complete; Items 2-8 executed with synthesized fixtures (see PROVISIONAL P-07, P-08)

---

## Learning Module Index (mirrors [Segment 101 Learning Module](../segment-101/README.md))

- [SME and Technical Business Analysis Note](segment-103-sme-tba-learning-note.md)
- [Segment 103 End-to-End Flow](segment-103-flow.md)
- [Coverage Closure](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Segment 103 Rule Catalog (authoritative)](coverage/segment-103-rule-catalog.json)

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
| Included when | Transaction flow requires EBT-specific data (PROVISIONAL P-01) | Section 12.4 |
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
| 5–7 | Currency Code | Appendix L valid currency code (PROVISIONAL P-06 for exhaustive list) |
| 8–20 | Amount | 1-char sign (`0`, `C` credit, or `D` debit) + 12-digit amount |

A populated WIC Discount Amount value is one or more 20-byte blocks of this layout, up to a 40-byte (2-block) maximum.

### 2.2 EBT Program Data (Element 164) Subelement Layout

| Subelement | Length | Description |
|---|---|---|
| Total Length | 3 digits, fixed, right-aligned/zero-padded, max value 264 | Count of all bytes in the Program Data subelement(s) that follow |
| Program Data (1–6 occurrences) | up to 44 bytes each | `TAG` (2 chars) + `LEN` (2 digits) + detail data; `ACCOUNT TYPE` fixed `98` required when `TAG=50` |

Documented `TAG` values: `50` (HIP purchase/return amount, request), `IT` (HIP Internet purchase shipping ZIP, request), `51` (HIP incentive earned/returned, response), `52` (HIP month-to-date incentive earned, response). See PROVISIONAL P-03 for the complete catalog.

### 2.3 Important Serialization Rule

For a **Financial Transaction request**, every Segment 103 field is separated by a Field Separator; an empty field still sends its separator. For a **Financial Transaction response**, there is **no** Field Separator between Segment 103 fields. See [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md).

---

## 3. Rule Set — Approved (Directly Derived from Specification)

Rule ID prefix: `SEG103-R-###`. Every rule carries a canonical source anchor: `spec | version | section | segment | element | rule`. See [the authoritative catalog](coverage/segment-103-rule-catalog.json) for the full machine-readable list (21 rules).

| Rule ID | Title | Class |
|---|---|---|
| SEG103-R-001 | Data Section 3 companion, usable in any field slot | structure |
| SEG103-R-002 | Required when the flow needs EBT-specific data | applicability |
| SEG103-R-003 | Segment Type is 103 | field |
| SEG103-R-004 | Segment Length is 3 or 4 digits | field |
| SEG103-R-005 | Maximum length 3,334 characters | serialization |
| SEG103-R-006 | Field order matches Section 12.4 | serialization |
| SEG103-R-007 | Request: separators preserved for empty fields | serialization |
| SEG103-R-008 | Response: no field separators | serialization |
| SEG103-R-009 | Clerk ID numeric max 10 | field |
| SEG103-R-010 | Voucher ID numeric max 10 | field |
| SEG103-R-011 | WIC Discount Amount positional format | field |
| SEG103-R-012 | WIC Discount Amount max 40 bytes | field |
| SEG103-R-013 | WIC Product Data bounded, Total Length subelement | field |
| SEG103-R-014 | EBT Program Data bounded, Total Length subelement | field |
| SEG103-R-015 | EBT Program Data 1-6 subelements, 44 bytes each | field |
| SEG103-R-016 | EBT Program Data TAG enumeration | field |
| SEG103-R-017 | TAG 50 requires ACCOUNT TYPE 98 | field |
| SEG103-R-018 | Exactly one Segment 103 per message | structure |
| SEG103-R-019 | Originates at the device | metadata |
| SEG103-R-020 | eWIC does not support Return | lifecycle |
| SEG103-R-021 | eWIC prompt-code enumeration | compatibility |

---

## 4. `[PROVISIONAL]` Items Requiring SME / TBA Input

See the catalog's `provisionalItems` array for the authoritative list (P-01 through P-08). Highlights:

- **P-01**: exact eligibility conditions that make Segment 103 mandatory for a given transaction.
- **P-02 / P-03**: the WIC Product Data (154) and EBT Program Data (164) subelement catalogs are only partially transcribed in this KB.
- **P-07 / P-08**: no real AI-generated Segment 103 artifacts or real sample JSONs exist yet; this training pass uses synthesized `.synthetic.json` fixtures, matching the precedent set for Segment 101 (P-09/P-10).

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

---

## 6. Do-Not-Assume Rules

1. Do not certify a companion-segment mutual exclusion for Segment 103 — none is documented; treat any such claim as `REVIEW_REQUIRED`.
2. Do not apply request-side field-separator rules to a response payload, or vice versa (`SEG103-R-007` vs `SEG103-R-008`).
3. Do not treat the Element 164 TAG catalog (`50`, `IT`, `51`, `52`) as exhaustive — flag unknown tags as `REVIEW_REQUIRED`, not silently accepted.
4. Do not certify eWIC Return handling from narrative text alone; require the exact Appendix G transaction-type code (P-04).
5. Do not infer Segment 103 applicability from card type alone — a WIC/EBT card present at POS does not by itself require Segment 103 (see the historical flat SME note for the applicability decision tree, now folded into [segment-103-sme-tba-learning-note.md](segment-103-sme-tba-learning-note.md)).
