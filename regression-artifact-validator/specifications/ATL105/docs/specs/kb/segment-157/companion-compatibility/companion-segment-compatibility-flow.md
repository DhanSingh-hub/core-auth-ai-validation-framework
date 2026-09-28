# Segment 157 Companion-Segment Compatibility Flow

```text
Candidate message assembled with Segment 157
  |
  v
Card == Comdata? --no--> FAIL (SEG157-R-001)
  |
  yes
  v
Segment 102 also present? --yes--> FAIL (SEG157-R-002)
  |
  no --> proceed; verify amount reconciliation against Segment 100
```
