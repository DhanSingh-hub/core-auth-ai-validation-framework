```text
Candidate message assembled with Segment 113 (ECA/TeleCheck Data Segment)
  |
  v
Is the message an ECA/TeleCheck Service Transaction Request?
  |
  no --> FAIL: Segment 113 is exclusive to ECA/TeleCheck Service Transaction Requests (SEG113-R-001)
  |
  yes
  v
Does the message also carry a segment other than 100 (Standard), 110 (Check), or 111 (Variable Information)?
  |
  no --> proceed; matches the documented {100, 110, 111, 113} shape
  |
  yes --> REVIEW_REQUIRED (no specification citation supports or forbids this combination)
```
