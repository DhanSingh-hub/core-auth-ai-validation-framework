# Segment 108 (Loyalty Card Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Section:** 12.7 Loyalty Card Data Segment (pages 12-21 to 12-22), 11.2 Loyalty Card Transactions (pages 11-11 to 11-13), 10.9 Loyalty Card Processing Requirements (pages 10-56 to 10-58), Elements 138-151 (chapter 13.2)
**Training Methodology:** [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md) (8-Item Framework)
**Item Progress:** Item 1 — Coverage Closure complete; SME intake held 2026-09-22 (3 of 8 open items resolved, see [SME/TBA Input Register](segment-108-sme-tba-input-register.md))

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

---

## Learning Module Index (mirrors [Segment 100 Learning Module](../segment-100/README.md) topic-note pattern)

- [SME and Technical Business Analysis Note](segment-108-sme-tba-learning-note.md)
- [Segment 108 End-to-End Flow](segment-108-flow.md)
- [Account Number / Card-Not-Present Substitution Note](account-number-sme-tba-note.md)
- [Account Number / Card-Not-Present Substitution Flow](account-number-flow.md)
- [Prompt Code / Card Type Routing Note](prompt-code-sme-tba-note.md)
- [Prompt Code / Card Type Routing Flow](prompt-code-flow.md)
- [Lifecycle and Correlation Note](lifecycle-sme-tba-note.md)
- [Lifecycle and Correlation Flow](lifecycle-flow.md)
- [Coverage Closure](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Segment 108 Rule Catalog (authoritative)](coverage/segment-108-rule-catalog.json)
- [SME/TBA Input Register](segment-108-sme-tba-input-register.md)

### Segment 100 Topics Not Mirrored (and Why)

- **Partial Approval** (Segment 100's `partial-approval-*` topic, Element 121): no specification text in Section 10.9 (Loyalty Card Processing Requirements) applies partial-approval capability to loyalty transactions. Not mirrored here; do not fabricate a Segment 108 partial-approval rule.

---

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 108 | Section 12.7 |
| Segment name | Loyalty Card Data Segment | Section 12.7 heading |
| Purpose | Loyalty program data carried in the dedicated Loyalty Card Transaction Request | Section 12.7 opening + Section 11.2.1 |
| Placement | Always Field No. 4 in Data Section No. 3 | Section 12.7 opening ("It always appears in Field No. 4 in Data Section No. 3.") |
| Origin | Device | Section 12.7 opening |
| Segment length range | 001–142 alphanumeric characters | Section 12.7 opening + Element 84 valid-values table (`SEG108-SME-001` resolved 2026-09-22) |
| Included when | Every Loyalty Card Transaction Request (Required) | Section 11.2.1 Data Section 3 table |
| Message family | Loyalty Card Transaction Request — NOT a Financial Transaction Request companion | Element 63 processing rules (Financial Transaction Requests list 101/102/103/104/111; Loyalty Card Transaction Requests list 108 only) |
| Sole optional companion | Segment 114 (SKU Data Segment) | Section 11.2.1 Data Section 3 table |

---

## 2. Field Layout

| # | Element | Name | Type / Len | R/O/C | Rule Anchor | JSON field |
|---|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | `segment-type` (fixed value 108) | `SegmentType` |
| 2 | 84 | Segment Length | N, 3 | R | `segment-length` | `SegmentLength` |
| 3 | 138 | Loyalty Program ID | N, 6 | R | `loyalty-program-id` | `LoyaltyProgramId` |
| 4 | 139 | Loyalty Account Number | N, 24 | C | `loyalty-account-number` | `LoyaltyAccountNumber` |
| 5 | 140 | Points to Redeem | N, 6 | O | `points-to-redeem` | `PointsToRedeem` |
| 6 | 141 | Coupon ID | N, 19 | O | `coupon-id` | `CouponId` |
| 7 | 142 | Coupon Amount | N, 8 | O | `coupon-amount` | `CouponAmount` |
| 8 | 143 | Update Code | AN, 1 | C | `update-code-enumeration` | `UpdateCode` |
| 9 | 144 | Street Address | N, 5 | C | `street-address` | `StreetAddress` |
| 10 | 145 | Phone Number, Loyalty | N, 10 | C | `phone-number-loyalty` | `PhoneNumberLoyalty` |
| 11 | 146 | Expiration Date | N, 4 | C | `expiration-date-mmyy` (MMYY, default 1249) | `ExpirationDate` |
| 12 | 148 | Payment Tender Type | AN, 2 | R | `payment-tender-type-enumeration` | `PaymentTenderType` |
| 13 | 147 | Loyalty Track 2 Data | AN, 38 | O | `loyalty-track2-data` | `LoyaltyTrack2Data` |
| 14 | 150 | Loyalty Information Version | N, 1 | O | `loyalty-information-version` | `LoyaltyInformationVersion` |
| 15 | 151 | Unit of Work | N, 19 | O | `unit-of-work` | `UnitOfWork` |

Maximum Segment 108 length is **142 alphanumeric characters**. Note that positional fields 12-13 (elements 148, then 147) are **not** in ascending element-number order — this is a genuine field-ordering quirk of Section 12.7's table, not a transcription error.

### 2.1 Update Code (Element 143) Valid Values

| Code | Meaning |
|---|---|
| A | Add account |
| C | Coupon redemption |
| E | Expiration date update |
| I | Account Inquiry |
| P | Points redemption |
| S | Sale update |
| T | Totals Report, Loyalty |
| U | Update account |

`[PROVISIONAL P-02]` — Section 10.9.3 also describes "Reversal of coupon redeem" and "Reversal of points redeemed" as distinct advice functions, but no Update Code value is documented for them.

### 2.2 Payment Tender Type (Element 148) Valid Values

`AX, CK, CS, DB, DN, DS, EB, EC, FL, GC, JC, MC, PC, PR, VS` — value `CS` means "Loyalty Only" (cash or administrative transaction); any other value implies a two-card swipe (loyalty card + a payment card of that type).

---

## 3. Rule Set — Approved (Directly Derived from Specification)

Rule ID prefix: `SEG108-R-###`. Every rule carries a canonical source anchor: `spec | version | section | segment | element | rule`. See [the authoritative catalog](coverage/segment-108-rule-catalog.json) for the full machine-readable list (24 rules).

| Rule ID | Title | Class |
|---|---|---|
| SEG108-R-001 | Exclusive to Loyalty Card Transaction Request | structure |
| SEG108-R-002 | Required in every Loyalty Card Transaction Request | applicability |
| SEG108-R-003 | Segment Type is 108 | field |
| SEG108-R-004 | Segment Length is 3 digits | field |
| SEG108-R-005 | Maximum length 142 characters | serialization |
| SEG108-R-006 | Field order matches Section 12.7 (148 before 147) | serialization |
| SEG108-R-007 | Field Separators required, including trailing | serialization |
| SEG108-R-008 | Loyalty Program ID numeric max 6, required | field |
| SEG108-R-009 | Loyalty Account Number numeric max 24 | field |
| SEG108-R-010 | Points to Redeem numeric max 6 | field |
| SEG108-R-011 | Coupon ID numeric max 19 | field |
| SEG108-R-012 | Coupon Amount numeric max 8 | field |
| SEG108-R-013 | Update Code enumeration (8 values) | field |
| SEG108-R-014 | Street Address numeric max 5 | field |
| SEG108-R-015 | Phone Number, Loyalty numeric max 10 | field |
| SEG108-R-016 | Expiration Date numeric MMYY, default 1249 | field |
| SEG108-R-017 | Payment Tender Type enumeration (15 values), required | field |
| SEG108-R-018 | Loyalty Track 2 Data alphanumeric max 38 | field |
| SEG108-R-019 | Loyalty Information Version 1 or 2, default 1 | field |
| SEG108-R-020 | Unit of Work numeric fixed 19 | field |
| SEG108-R-021 | Exactly one Segment 108 per message | structure |
| SEG108-R-022 | Originates at the device | metadata |
| SEG108-R-023 | Sole optional companion is Segment 114 | compatibility |
| SEG108-R-024 | Not present in the Loyalty Card Transaction Response | lifecycle |

---

## 4. `[PROVISIONAL]` Items Requiring SME / TBA Input

See the catalog's `provisionalItems` array and the [SME/TBA Input Register](segment-108-sme-tba-input-register.md) for the authoritative, trackable list. Status as of 2026-09-22 intake:

- **Resolved**: P-01 (max length 142), P-05 (Loyalty-Transaction-exclusive scope), P-06 (Street/Phone substitution not code-enforced), P-09 (Expiration Date is a real MMYY field, not reserved).
- **Open**: P-02 (Update Code reversal-function mapping), P-03 (possible response-side presence — disputed without citation), P-04 (Appendix K Table 008/010 receipt layouts in/out of scope), P-07 (real AI artifacts), P-08 (real test data).

---

## 5. Cross-Reference to Segment 103 Framework

| Segment 103 file | Segment 108 counterpart | Status |
|---|---|---|
| `docs/specs/kb/segment-103/README.md` | `docs/specs/kb/segment-108/README.md` | ✅ this file |
| `docs/specs/kb/segment-103/coverage/segment-103-rule-catalog.json` | `docs/specs/kb/segment-108/coverage/segment-108-rule-catalog.json` | ✅ produced |
| `docs/specs/kb/segment-103/segment-103-flow.md` | `docs/specs/kb/segment-108/segment-108-flow.md` | ✅ produced |
| `src/main/java/…/Segment103PayloadValidator.java` | `Segment108PayloadValidator.java` | ⏭ next |
| `src/main/java/…/Segment103ArtifactComparison.java` … `ConsolidatedReport.java` | `Segment108*` (7 more classes) | ⏭ next |

---

## 6. Do-Not-Assume Rules

1. Do not certify a Segment 108 + Segment 101/102/103/104/111 combination as a Financial Transaction Request companion — no specification citation supports this; Segment 108's message family is exclusive (Element 63 processing rules).
2. Do not assume the wire field order follows ascending element numbers — fields 12-13 are elements 148 then 147.
3. Do not certify the Update Code enumeration as covering every advice function named in Section 10.9.3 — the "reversal" functions are `REVIEW_REQUIRED` pending `SEG108-SME-002`.
4. Do not assert Segment 108 never appears in a response without flagging `SEG108-SME-003` as open — the SME intake explicitly disputed this without yet providing a citation.
5. Do not treat Element 146 (Expiration Date) as reserved/inert — SME-confirmed 2026-09-22 (`SEG108-SME-007`/`P-09`) it is a real MMYY field with default value `1249`.
6. Do not use Card Type `060` (Voyager Fleet) for Loyalty fixtures — the correct code is `040` (Loyalty); all Segment 108 fixtures use `040`.
7. Do not assume Segment 100's Partial Approval Indicator (Element 121) applies to loyalty transactions — no specification text supports this; that topic is intentionally not mirrored here.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-108-rule-catalog.json) (24 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 108 |
|---|---|
| SME/TBA learning note | [Learning note](segment-108-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-108-flow.md) |
| Topic deep-dives | [account-number](account-number-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle](lifecycle-sme-tba-note.md) · [prompt-code](prompt-code-sme-tba-note.md) |
| Topic flows | [account-number](account-number-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle](lifecycle-flow.md) · [prompt-code](prompt-code-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-108-business-requirements.md](segment-108-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-108-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-108-sme-tba-input-register.md) |
