# Segment 115 Serialization and Wire-Format Flow

```text
Segment 115 payload ready to serialize (Host-originated, response side)
  |
  v
Do the response-inclusion conditions hold (Element 115 flag + Loyalty
Information Version = 2)? [see response-inclusion-condition-flow.md]
  |
  no  --> omit Segment 115 entirely
  |
  yes
  v
emit SegmentType FS SegmentLength FS PrintData
  (exactly two Field Separators: after field 1, and after field 2 —
   NO separator after field 3 / Print Data; SEG115-R-007)

Segment Length is always 4 digits, valid range 0001-1009
(upper bound provisional, SEG115-SME-001).
```
