# Segment 115 Companion-Segment Compatibility Flow

```text
Candidate Financial Transaction Response assembled with Segment 115 (Print Data Segment)
  |
  v
Is the message a Financial Transaction Response or EMV Financial Transaction Response?
  |
  no --> FAIL: Segment 115 is response-only (SEG115-R-001)
  |
  yes
  v
Does the response also carry Segment 112 (Additional Information Data Segment)?
  |
  yes --> REVIEW_REQUIRED [PROVISIONAL SEG115-SME-004]: Section 12.14 states "no other
  |        data segments," but the Section 11.1.2 layout table depicts both as
  |        independently conditional — do not certify PASS or FAIL until resolved.
  |
  no
  v
Is this the EMV variant, also carrying Segment 120 and/or Segment 131?
  |
  yes --> proceed; matches the documented EMV {100, 112?, 115, 120, 131} shape (SEG115-R-011)
  |
  no --> proceed; matches the documented non-EMV {100, 115} shape
```
