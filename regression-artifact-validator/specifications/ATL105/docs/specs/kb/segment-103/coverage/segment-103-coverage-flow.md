# Segment 103 Coverage Closure Flow

```text
segment-103-rule-catalog.json (21 rules)
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

Aggregate result: coverage percentage = COVERED rules / total rules. P-01 through P-06 are resolved (see the rule catalog's `provisionalItems`); only rules gated on P-07/P-08 (real AI artifacts / real test data) remain capped at `REVIEW_REQUIRED`.
