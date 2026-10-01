# Segment 115 Coverage Closure Flow

```text
segment-115-rule-catalog.json (13 rules)
  |
  v
For each rule:
  find Business Requirement(s) anchored to the same (specification, version, rule) key
  |
  v
  found? --no--> MISSING (or REVIEW_REQUIRED if rule carries a "provisional" tag)
  |
  yes
  v
  find Test Scenario(s) linked to that BR
  |
  v
  found? --no--> PARTIALLY_COVERED
  |
  yes
  v
  find Test Case(s) linked to that scenario
  |
  v
  found? --no--> PARTIALLY_COVERED
  |
  yes
  v
  find Test Data linked to that test case
  |
  v
  found? --no--> PARTIALLY_COVERED
  |
  yes
  v
  COVERED
```

Aggregate result: coverage percentage = COVERED rules / total rules. Rules tagged `provisional` in the catalog (`SEG115-R-002`, `SEG115-R-005`, `SEG115-R-008`, `SEG115-R-010`, `SEG115-R-012`, `SEG115-R-013`) cap at `REVIEW_REQUIRED` regardless of chain completeness, until the SME resolves the corresponding open P-item (P-01 through P-06 — all open).

## Current Baseline (Pre-SME-Intake)

Before this training pass, no confirmed AI-to-Test match existed for any Segment 115 rule (see the [requirement comparison](../segment-115-ai-vs-test-requirement-comparison.md)) — every `BR-249-*` statement was auto-matched against the wrong segment. None of Segment 115's 13 catalog rules can be marked `COVERED` until the open SME items are resolved and real (or approved synthetic) test data is produced.
