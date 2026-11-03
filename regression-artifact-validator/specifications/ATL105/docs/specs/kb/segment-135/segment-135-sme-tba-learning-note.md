# Segment 135 Moneris Data (Request) Segment: SME and TBA Learning Note

Verified against Section 12.24, Elements 84, 85, 213.

## What Segment 135 Means

Segment 135 carries Moneris-authorizer-specific data in a Financial Transaction Request, required only when the transaction is destined for Moneris (per the generic Financial Transaction Request companion table's own note).

## Field Layout

| Field | Element | Length | Notes |
| --- | ---: | ---: | --- |
| Segment Type | 85 | 3 | Fixed `135`, Device-sourced |
| Segment Length Indicator | 84 | 3 | Includes Segment Type's length — separator inclusion is `[PROVISIONAL SEG135-SME-001]` |
| Moneris Data | 213 | 100 | `<tag><len><data>` TLV sub-structure; request tables covered in [Appendix V training](../appendix-v/README.md); Table 004/005 source-width conflicts remain review-gated |

## Serialization

Field Separators appear between fields 1-2 and 2-3; the segment should end with a trailing separator. Data **within** field 3 (the TLV sub-fields) are NOT separated by Field Separators — they use their own internal tag/length/data framing.

## Validator Rules Planned

- Conditional on Moneris-destined transaction (`SEG135-R-001`).
- Segment Type fixed `135`; Moneris Data TLV-structured, max 100 chars.

## Source References

- Section 12.24: lines 14419-14471.
- [Segment 135 Rule Catalog](coverage/segment-135-rule-catalog.json).
- Appendix V request-table layouts: [Appendix V training](../appendix-v/README.md).
