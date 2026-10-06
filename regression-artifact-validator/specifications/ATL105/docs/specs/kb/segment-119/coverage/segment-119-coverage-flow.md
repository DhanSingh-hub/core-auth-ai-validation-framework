# Segment 119 Coverage Closure Flow

```text
segment-119-rule-catalog.json (36 rules)
  |
  v
For each rule:
  find BR anchored to the same (specification, version, rule) key
  found? --no--> MISSING (or REVIEW_REQUIRED if provisional)
  find linked Test Scenario   --no--> PARTIALLY_COVERED
  find linked Test Case       --no--> PARTIALLY_COVERED
  find linked Test Data       --no--> PARTIALLY_COVERED
  requires production artifact? --yes--> EXTERNAL_FIXTURE_REQUIRED
  otherwise --> COVERED
```
