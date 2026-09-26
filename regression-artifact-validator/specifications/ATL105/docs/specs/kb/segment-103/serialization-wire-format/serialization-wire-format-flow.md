# Segment 103 Serialization and Wire-Format Flow

```text
Segment 103 payload ready to serialize
  |
  v
Message direction?
  |
  +-- REQUEST --> emit SegmentType FS SegmentLength FS ClerkId FS VoucherId FS
  |                    WicDiscountAmount FS WicProductData FS EbtProgramData
  |               (each FS present even when the field between two separators is empty)
  |
  +-- RESPONSE -> emit SegmentType SegmentLength ClerkId VoucherId
                       WicDiscountAmount WicProductData EbtProgramData
                  (no Field Separator between any of these fields)

Segment Length digit-count decision:
  WIC Discount Amount or WIC Product Data populated (eWIC data present)?
    yes --> use 4-digit Segment Length
    no  --> 3-digit Segment Length is sufficient
```
