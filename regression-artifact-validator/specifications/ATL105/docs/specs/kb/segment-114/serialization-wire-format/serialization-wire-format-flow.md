# Segment 114 Serialization and Wire-Format Flow

```text
Segment 114 payload ready to serialize
  |
  v
Was a bar code SKU scanned for this transaction?
  |
  no  --> omit Segment 114 entirely (do not emit an empty Segment 114)
  |
  yes
  v
emit SegmentType FS SegmentLength FS SkuData FS
  (each FS present even when the field between two separators is empty;
   Segment Length is always 4 digits, valid values 0001-1010, confirmed 2026-09-26 —
   unlike Segment 108's 3-digit length)

Repeat the block above once per additional scanned SKU (confirmed repeatable
2026-09-26, SEG114-R-010).
```
