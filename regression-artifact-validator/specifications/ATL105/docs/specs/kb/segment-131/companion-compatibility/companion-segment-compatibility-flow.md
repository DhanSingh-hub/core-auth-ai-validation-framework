# Segment 131 Companion-Segment Compatibility Flow

```text
Candidate EMV Financial Transaction Response assembled with Segment 131
  |
  v
Is Segment 130 present in the corresponding request?
  |
  no --> FAIL: Segment 131 has no meaning without a paired Segment 130 request
  |
  yes
  v
Does Segment 131's CA Public Key File Checksum equal Segment 130's? --no--> FAIL (SEG131-R-007)
  |
  yes
  v
Response also carries Segment 112 and/or Segment 115/120?
  |
  yes --> proceed; matches the documented EMV response sequence (SEG131-R-012)
  no  --> proceed; Segment 131 may appear alone if no additional-info/print-data applies
```
