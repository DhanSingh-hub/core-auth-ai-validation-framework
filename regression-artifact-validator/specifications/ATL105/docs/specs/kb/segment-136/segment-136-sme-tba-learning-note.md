# Segment 136 Moneris Data (Response) Segment: SME and TBA Learning Note

Verified against Section 12.25, Elements 84, 85, 213.

## What Segment 136 Means

Segment 136 carries Moneris-authorizer response data, used for both financial transactions and Key Load transactions.

## Field Layout

| Field | Element | Length | Notes |
| --- | ---: | ---: | --- |
| Segment Type | 85 | 3 | Fixed `136`; Source listed as "Device" despite this being a response segment (`[PROVISIONAL SEG136-SME-001]`, mirrors the Segment 131 precedent) |
| Segment Length Indicator | 84 | 3 | Includes Segment Type's length |
| Moneris Data | 213 | 100 | `<tag><len><data>` TLV; Appendix V scope provisional (`SEG136-SME-003`) |

## Serialization

Section 12.25 states only "A Field Separator will follow the segment" — less detail than Segment 135's note. `[PROVISIONAL SEG136-SME-002]`: whether per-field separators (1-2, 2-3) also apply, or only the single trailing one, is unconfirmed.

## Source References

- Section 12.25: lines 14471-14522.
- [Segment 136 Rule Catalog](coverage/segment-136-rule-catalog.json).
