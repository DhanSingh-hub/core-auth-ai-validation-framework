# Segment 115 Print Data Segment: SME and TBA Learning Note

Verified against BUYPASS Platform ATL105 release 2026-3, Sections 10.9, 11.1.2 (and its EMV variant), 12.14, and 13.2 Elements 115 and 152.

## 1. What Segment 115 Means

Segment 115 is the **Print Data Segment** — the first *response-side* segment trained so far (Segments 100, 108, 114 are all request-side). It carries a large block of host-originated print data back to the device: either terms & conditions text for Blackhawk phone activation / recharge receipts (explicitly documented), or — plausibly — loyalty receipt information (inferred, not explicitly confirmed; see `[PROVISIONAL SEG115-SME-005]`).

A useful mental model:

```text
Financial Transaction Response
  Data Section 1: response code, amounts, approval number, etc.
  Data Section 2 (conditional):
    Segment 112 (Additional Information Data Segment) — balances, AVS/CVV, tokens, etc.
    Segment 115 (Print Data Segment) — large print payload, host-originated
```

## 2. Segment 115 Layout

| Field | Element | Length | Status | SME meaning |
| --- | ---: | ---: | --- | --- |
| Segment Type | 85 | 3 | Required | Fixed value `115` — explicitly stated in Section 12.14 (contrast Segment 114, which has no such explicit citation) |
| Segment Length | 84 | 4 | Required | Encoded length, 4 digits (like Segments 103/114/118/120/130/131) |
| Print Data | 152 | 999 | Required | The print payload; per Section 12.14's note, this field carries **terms & conditions text for Blackhawk phone activation and recharge receipts** |

Maximum Segment 115 length is **1,009 alphanumeric characters** per Section 12.14's opening statement, independently corroborated by the AI Solution Team's own extracted statement (`BR-249-3`). `[PROVISIONAL SEG115-SME-001]` — the Financial Transaction Response layout table (Section 11.1.2) states a conflicting **910**, and the EMV Financial Transaction Response layout table states **999** (which may simply be restating the Print Data field's own length rather than the total segment length). Do not certify a maximum without resolving this three-way conflict.

### The missing trailing separator

Every other segment documented so far (100, 108, 114) explicitly states that a Field Separator follows the **last** field, even when that field is empty. Segment 115 is different: Section 12.14's note says only **"There is a Field Separator between Field Nos. 1 and 2 and between Field Nos. 2 and 3"** — it does **not** say a separator follows Field No. 3. This is independently corroborated by the AI Solution Team's own extracted statement (`BR-249-5`). Do not assume Segment 115 has a trailing separator just because other segments do.

## 3. When Segment 115 Is Included

Segment 115 is **conditional**, not required. Per Section 11.1.2, it appears at Field No. 17/18/19 of the Financial Transaction Response's Data Section 2 only when:

1. Element 115 (Additional Information Data Segment Flag) indicates that it follows, **and**
2. The **request's** Loyalty Information Version (Element 150, the same field documented in Segment 108's catalog) equals `2`.

`[PROVISIONAL SEG115-SME-002]` — Element 115's own documented valid values are only `0` (none follows) and `1` (follows). This binary flag by itself does not explain how the response distinguishes "Segment 112 follows" from "Segment 115 follows" when both are listed as independently conditional in the same Data Section 2 table. This mirrors Segment 112's own still-open question `SEG112-SME-003`. Do not assume a specific mechanism (e.g., a second undocumented flag value) without SME confirmation.

Segment 115 also appears in the **EMV Financial Transaction Response**, where it may be followed by Segment 120 (Print Data 2 Segment) for overflow print content, and always precedes Segment 131 (EMV Response Data Segment).

`[PROVISIONAL SEG115-SME-004]` — Section 12.14 also states: **"No other data segments are contained in the Financial Transaction Response"** when Segment 115 is present. Read literally (and this is exactly how the AI Solution Team's own `BR-249-4` statement reads it), this would mean Segment 115 and Segment 112 can never co-occur — yet the Section 11.1.2 layout table shows both as independently conditional fields in the very same response. Do not silently pick one reading; this is a genuine, evidence-backed contradiction requiring SME clarification.

## 4. The Loyalty Print Data Connection (Inferred, Not Confirmed)

Section 10.9 (Loyalty Card Processing Requirements) describes an Account Inquiry (Segment 108 Update Code `I`) and a Totals Report (Update Code `T`) where **"the host returns the Loyalty Print Data."** Segment 115's own trigger condition — Loyalty Information Version = 2 — strongly suggests Segment 115 is the wire-format vehicle for that Loyalty Print Data. `[PROVISIONAL SEG115-SME-005]` — however, neither Section 10.9 nor Section 12.14 explicitly cross-references the other; this is an inference, not a citation. This question is directly linked to Segment 108's still-open `SEG108-SME-003` (does loyalty data ever appear in a response?). Do not certify this connection as fact until SME input arrives.

## 5. Validator Rules Planned (`Segment115PayloadValidator`, not yet implemented)

- Segment 115 is conditional; its absence is never a failure by itself (`SEG115-R-002`).
- When present, Segment Type equals `115` (`SEG115-R-003`, no enforceability ambiguity — unlike Segment 114).
- When present, Segment Length is 4 digits within `0001-1009` (`SEG115-R-004`, `SEG115-R-005`, upper bound pending `SEG115-SME-001`).
- When present, Print Data is required and non-empty, max length pending `SEG115-SME-003` (999 vs 900).
- Segment 115 does NOT require (and should not enforce) a trailing Field Separator (`SEG115-R-007`).
- Segment 115 alongside Segment 112 is `REVIEW_REQUIRED` pending `SEG115-SME-004`, not silently rejected or silently accepted (`SEG115-R-010`).
- Segment 115 alongside Segment 120 and preceding Segment 131 is expected in the EMV Financial Transaction Response context (`SEG115-R-011`).

## 6. Suggested Segment 115 Test Scenarios

| ID | Scenario | Expected result |
| --- | --- | --- |
| PRT-115-001 | Financial Transaction Response with valid Segment 115 type, 4-digit length, and print data | Pass |
| PRT-115-002 | Financial Transaction Response omits Segment 115 entirely | Pass (Segment 115 is conditional) |
| PRT-115-003 | Segment 115 present with Segment Length only 3 digits | Fail (`SEG115-R-004`) |
| PRT-115-004 | Segment 115 uses invalid type (e.g., `112`) | Fail (`SEG115-R-003`) |
| PRT-115-005 | Segment 115 present with a trailing Field Separator after Print Data | Review — spec does not document one; flag as a potential wire-format defect (`SEG115-R-007`) |
| PRT-115-006 | Segment Length / Print Data exceeds the documented maximum | Fail (`SEG115-R-005`, boundary pending 1,009 vs 910 vs 999 resolution) |
| PRT-115-007 | Segment 115 present together with Segment 112 in the same response | Review pending `SEG115-SME-004` — apparent spec contradiction |
| PRT-115-008 | EMV Financial Transaction Response with Segment 115, Segment 120, and Segment 131 in sequence | Pass (`SEG115-R-011`) |
| PRT-115-009 | Segment 115 present in a Loyalty Card Transaction Response | Review pending `SEG115-SME-005` |
| PRT-115-010 | Segment 115 present in any Request message | Fail (`SEG115-R-001` — response-only) |

## 7. SME Checklist

When reviewing an AI-generated Segment 115 artifact, ask:

- Does the artifact treat Segment 115 as response-only, never appearing in a Request?
- Does it avoid emitting a trailing Field Separator after Print Data?
- Does it avoid asserting a specific maximum length without flagging the 1,009 vs 910 vs 999 conflict?
- Does it avoid asserting Segment 112 and Segment 115 are mutually exclusive (or co-occurring) without flagging the "no other data segments" ambiguity?
- Does it avoid asserting Segment 115 carries Loyalty Print Data as settled fact, rather than an open inference?

## Source References

- Section 10.9, Loyalty Card Processing Requirements (Loyalty Print Data narrative): lines 6039-6165.
- Section 11.1.2, Financial Transaction Response (Segment 112/115 conditional layout): lines 7930-7990.
- Section 11.1.2-EMV, EMV Financial Transaction Response (Segment 115/120/131 layout): lines 10380-10440.
- Section 12.14, Print Data Segment: lines 12911-12960.
- Elements 115, 152: `13-data-elements.md` rows.
- Segment-length-family cross-reference: lines 10996, 21371 (also used by Segment 120's rule catalog).
- [Segment 115 Rule Catalog](coverage/segment-115-rule-catalog.json).
- [SME/TBA Input Register](segment-115-sme-tba-input-register.md).
- [AI-Generated vs Test-Generated Requirement Comparison](segment-115-ai-vs-test-requirement-comparison.md).
