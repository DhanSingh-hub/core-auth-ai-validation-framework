# Segment 108 Companion-Segment Compatibility Flow

```text
Candidate message assembled with Segment 108 (Loyalty Card Data Segment)
  |
  v
Is the message a Loyalty Card Transaction Request?
  |
  no --> FAIL: Segment 108 is exclusive to Loyalty Card Transaction Requests (SEG108-R-001)
  |
  yes
  v
Does the message also carry a segment other than 100 (Standard) or 114 (SKU)?
  |
  no --> proceed; matches the documented {100, 108, 114} shape
  |
  yes --> REVIEW_REQUIRED (no specification citation supports or forbids this combination)
```
