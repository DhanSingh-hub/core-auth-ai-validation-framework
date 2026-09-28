# Segment 134 Transaction Attributes Data Segment: SME and TBA Learning Note

Verified against BUYPASS Platform ATL105 release 2026-3, Section 12.23, Elements 84, 85, 198-200.

## 1. What Segment 134 Means

Segment 134 carries transaction-disposition metadata (settlement type, signature requirement, receipt card description) back from the Host. It is fixed-length with **no Field Separators**, the same wire format as Segment 131.

## 2. Field Layout

| Field | Element | Length | Values |
| --- | ---: | ---: | --- |
| Segment Type | 85 | 3 | Fixed `134` |
| Segment Length | 84 | 4 | `[PROVISIONAL SEG134-SME-001]` — possibly an 8th 4-digit-length segment |
| Settlement Type | 198 | 1 | `D` (Dual message), `S` (Single message), `X` (Non-traditional Signature Debit — availability confirmed per-merchant with First Data) |
| Signature Required | 199 | 1 | `T`, `F`, or Space (device determines) |
| Receipt Card Description | 200 | 10 | Left-justified, space-filled card type text (e.g., "STAR      ") |

Maximum length 19 characters reconciles exactly with the field-length sum (3+4+1+1+10=19), consistent with zero separators.

## 3. The Signature Debit Business Condition

Settlement Type `X` (Non-traditional Signature Debit) is explicitly gated by a business relationship: the specification instructs readers to "check with your First Data Project Manager or Relationship Manager for availability." Do not assume this value is universally valid — treat it as merchant-configuration-dependent (`[PROVISIONAL SEG134-SME-002]`).

## 4. Validator Rules Planned

- Segment Type fixed `134`; no Field Separators expected anywhere.
- Settlement Type in `{D,S,X}`, with `X` flagged `REVIEW_REQUIRED` pending merchant-configuration confirmation.
- Signature Required in `{T,F," "}`.
- Receipt Card Description validated for left-justification/space-fill format, not content.

## Source References

- Section 12.23, Transaction Attributes Data Segment: lines 14374-14420.
- [Segment 134 Rule Catalog](coverage/segment-134-rule-catalog.json).
- [SME/TBA Input Register](segment-134-sme-tba-input-register.md).
