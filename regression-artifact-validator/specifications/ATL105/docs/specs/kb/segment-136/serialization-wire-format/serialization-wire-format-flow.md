# Segment 136 Serialization and Wire-Format Flow

```text
emit SegmentType SegmentLengthIndicator MonerisData(<tag><len><data>...) FS
  (per-field separator placement before "FS" is provisional, SEG136-SME-002)
```
