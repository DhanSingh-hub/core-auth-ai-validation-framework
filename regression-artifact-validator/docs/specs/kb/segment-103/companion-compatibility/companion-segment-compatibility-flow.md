# Segment 103 Companion-Segment Compatibility Flow

```text
Candidate message assembled with Segment 103 (EBT Data Segment)
  |
  v
Does the message also carry another Data Section 3 companion segment
(e.g., Segment 101 Fleet, Segment 102 Product Code, Segment 145 Enhanced Fleet)?
  |
  no --> proceed; no compatibility concern
  |
  yes
  v
Is there a specification citation stating these two segments cannot coexist?
  |
  no --> REVIEW_REQUIRED (no documented mutual-exclusion boundary; escalate any claimed conflict for review)
  |
  yes --> FAIL with the citation as the rule anchor (mirrors SEG101-R-003 pattern)
```

This flow intentionally has no enforced rejection branch today: as of this training pass, no citation exists for a Segment 103 mutual-exclusion rule.
