# Segment 108 Coverage Closure Flow

```text
segment-108-rule-catalog.json (24 rules)
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

Aggregate result: coverage percentage = COVERED rules / total rules. Rules tagged `provisional` in the catalog (currently `SEG108-R-013` and `SEG108-R-024`) cap at `REVIEW_REQUIRED` regardless of chain completeness, until the SME resolves the corresponding open P-item (P-02, P-03).
