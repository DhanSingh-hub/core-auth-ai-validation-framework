# Segment 132 Companion-Segment Compatibility Flow

```text
Candidate message assembled with Segment 132 (CA Public Key File Segment)
  |
  v
Is the message a CA Public Key File Load Request?
  |
  no --> FAIL: Segment 132 is exclusive to this message type (SEG132-R-001)
  |
  yes
  v
Does the message also carry 101/102/104/111?
  |
  yes --> proceed; matches the documented companion list
  no  --> proceed; Segment 132 may appear alone
```
