# Segment 114 Coverage Closure Flow

```text
segment-114-rule-catalog.json (13 rules)
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

Aggregate result: coverage percentage = COVERED rules / total rules. Rules tagged `provisional` in the catalog (`SEG114-R-005`, `SEG114-R-010`, `SEG114-R-012`, `SEG114-R-013`, plus the enforceability question on `SEG114-R-003`) cap at `REVIEW_REQUIRED` regardless of chain completeness, until the SME resolves the corresponding open P-item (P-01 through P-06 — all open).

## Current Baseline (Pre-SME-Intake)

Before this training pass, independent Test Solution review recorded the following gaps for `ENT-SEG-114` (see [the requirement comparison](../segment-114-ai-vs-test-requirement-comparison.md#3-test-generated-traceability-gap-baseline-before-this-training-pass)):

- `REQUIREMENT_ONLY` (1) — a requirement exists with no generated scenario.
- `SCENARIO_ONLY` (1) — a scenario exists with no generated test case.
- `TEST_CASE_NO_DATA` (1) — test cases exist but no resolvable test data file.

None of Segment 114's 13 catalog rules can be marked `COVERED` until these gaps close and the open SME items are resolved.
