# Segment 131 EMV Response Data Segment: SME and TBA Learning Note

Verified against BUYPASS Platform ATL105 release 2026-3, Sections 11.1.2-EMV and 12.21, Elements 84, 85, 118, 187, 189-192.

## 1. What Segment 131 Means

Segment 131 is the **EMV Response Data Segment** — the response-side counterpart to Segment 130, echoing EMV chip data and CA Public Key File Checksum information back to the device.

## 2. A Genuine Placement Contradiction

Section 12.21 opens by stating Segment 131 **"always appears in Field No. 4 in Data Section No. 3."** However, the EMV Financial Transaction Response's own message-layout table (documented while researching Segment 115) places Segment 131 at **Field No. 17/18/19/20 of Data Section No. 2**, following Segments 112, 115, and 120. The AI Solution Team's own extracted statement agrees with the layout table ("The EMV Response Data Segment (Segment 131) follows in Field No. 17/18/19/20 when EMV data is required"), not with Section 12.21's opening sentence. `[PROVISIONAL SEG131-SME-001]` — do not silently pick one interpretation; this is a genuine, evidence-backed specification ambiguity.

## 3. Segment 131 Layout (No Field Separators)

| Field | Element | Length | Status | Source | SME meaning |
| --- | ---: | ---: | --- | --- | --- |
| Segment Type | 85 | 3 | Required | Host | Fixed value `131` |
| Segment Length | 84 | 4 | Required | Host | 4-digit encoded length |
| CA Public Key File Checksum | 187 | 25 | Required | Device/Host | **Echoed from the Request** (explicit citation — confirms the handshake pattern inferred for Segment 130) |
| EMV Chip Data Length | 189 | 3 | Required | Device (sic) | Echoed chip-data length |
| EMV Chip Data | 190 | 999 | Required | Device (sic) | Echoed EMV chip data |
| *(repeating)* EMV Additional Information Indicator | 191 | 3 | Required (when section present) | Host | Type/table identifier |
| *(repeating)* EMV Additional Information Length | 192 | 3 | Required (when section present) | Host | Length of the following field |
| *(repeating)* EMV Additional Information | 118 | Var. | Required (when section present) | Host | Payload — repeats up to **2,800 bytes** total (not 2,000 — see below) |

**Critical wire-format fact**: unlike every other segment documented so far, Segment 131 uses **no Field Separators at all**. Section 12.21 states: *"Fields are not separated by Field Separators. When a field is not populated, the next field immediately follows."* This makes Segment 131 fixed-length/positional, not delimited.

`[PROVISIONAL SEG131-SME-002]` — maximum length is stated as **3,834** alphanumeric characters, corroborated by the AI Solution Team's own extraction (`BR-269-2`). An early, OCR-uncertain reading of a related layout table suggested a possible shorter figure that could not be confidently transcribed; flag for visual confirmation, do not treat as a confirmed conflict.

`[PROVISIONAL SEG131-SME-003]` — Fields 4-5 (EMV Chip Data Length, EMV Chip Data) list their Source as "Device" even though Section 12.21's opening states the segment "originates at BUYPASS." This mirrors a previously-resolved sourcing inconsistency in Segment 108 (Loyalty Card Data Segment's Segment Type field was similarly transcribed as "Device" when it should have said "Host" — `SEG108-SME-001`-adjacent precedent). Do not silently "fix" this without SME confirmation.

### Do not reuse Segment 130's 2,000-byte cap

Segment 130's EMV Additional Information Section caps at 2,000 bytes; Segment 131's caps at **2,800 bytes**. These are independently documented, different limits for a structurally identical repeating group — do not copy one onto the other.

## 4. The CA Public Key File Checksum Echo (Confirmed)

Segment 131's field table explicitly states the CA Public Key File Checksum **"is echoed from the Request."** This directly confirms the request/response handshake relationship inferred (but not explicitly cited) for Segment 130's own CA Public Key File Checksum field (`SEG130-R-015`) — the device sends its currently-known checksum in Segment 130, and the host echoes it back unchanged in Segment 131, allowing the device to detect when its CA key file is stale.

## 5. Validator Rules Planned (`Segment131PayloadValidator`, not yet implemented)

- Segment 131 is conditional (`SEG131-R-002`); its absence is never a failure by itself.
- Segment Type fixed `131` (`SEG131-R-003`); Segment Length 4-digit within `0001-3834` (`SEG131-R-004`, `SEG131-R-005`, pending `SEG131-SME-002`).
- **Do not expect or require Field Separators** anywhere in this segment (`SEG131-R-006`).
- CA Public Key File Checksum must equal the corresponding request's Segment 130 value (`SEG131-R-007`).
- EMV Additional Information Section capped at 2,800 bytes, not 2,000 (`SEG131-R-009`).

## 6. SME Checklist

When reviewing an AI-generated Segment 131 artifact, ask:

- Does it avoid inserting Field Separators anywhere in this segment?
- Does it correctly use 2,800 bytes (not 2,000) as the EMV Additional Information Section cap?
- Does it verify the CA Public Key File Checksum matches the corresponding request's Segment 130 value, rather than treating it as independent data?
- Does it avoid asserting a specific Data Section/field placement without flagging the 12.21-vs-layout-table contradiction?

## Source References

- Section 11.1.2-EMV, EMV Financial Transaction Response (Segment 131 layout table placement): lines 10380-10440.
- Section 12.21, EMV Response Data Segment: lines 14145-14200.
- [Segment 131 Rule Catalog](coverage/segment-131-rule-catalog.json).
- [SME/TBA Input Register](segment-131-sme-tba-input-register.md).
- [AI-Generated vs Test-Generated Requirement Comparison](segment-131-ai-vs-test-requirement-comparison.md).
