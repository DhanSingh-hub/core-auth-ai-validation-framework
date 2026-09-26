# Segment 114 Companion-Segment Compatibility Flow

```text
Candidate message assembled with Segment 114 (SKU Data Segment)
  |
  v
Is the message a Loyalty Card Transaction Request?
  |
  no --> FAIL: Segment 114 is exclusive to Loyalty Card Transaction Requests (SEG114-R-001)
  |       Confirmed 2026-09-26 (SEG114-SME-002): a Financial Transaction Request context is
  |       an outright REJECT (SEG114-R-012), not a review case — the AI Solution Team's
  |       assertion that this combination is valid was confirmed to be an AI defect.
  yes
  v
Is Segment 108 (Loyalty Card Data Segment) also present in the message?
  |
  no --> FAIL: Segment 114 never appears without Segment 108 (SEG114-R-011)
  |
  yes
  v
Does the message also carry a segment other than 100 (Standard), 108 (Loyalty Card),
or 114 (SKU) itself?
  |
  no --> proceed; matches the documented {100, 108, 114} shape (114 may repeat once per
  |      scanned SKU — confirmed 2026-09-26, SEG114-R-010)
  |
  yes --> REVIEW_REQUIRED (no specification citation supports or forbids this combination)
```
