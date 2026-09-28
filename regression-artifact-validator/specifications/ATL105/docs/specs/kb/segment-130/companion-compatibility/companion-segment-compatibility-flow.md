# Segment 130 Companion-Segment Compatibility Flow

```text
Candidate message assembled with Segment 130 (EMV Request Data Segment)
  |
  v
Is the message an EMV Financial Transaction Request?
  |
  no --> FAIL: Segment 130 is exclusive to EMV Financial Transaction Requests (SEG130-R-001)
  |
  yes
  v
Is Segment 130 present?
  |
  no --> FAIL: Segment 130 is the only segment required for all EMV financial
  |       transactions (SEG130-R-001)
  yes
  v
Does the message also carry Segment 103 (EBT)?
  |
  yes --> REVIEW_REQUIRED / likely invalid — Segment 103 is not listed among
  |        Segment 130's EMV-context companions (101, 102, 104, 111)
  no
  v
proceed; matches the documented EMV Financial Transaction Request shape
  {100, 130, optionally 101/102/104/111}
```
