# Segment 130 Coverage Closure Flow

```text
segment-130-rule-catalog.json (16 rules, several pre-adopted from
segment-130-core-structure-package.json / appendix-r / appendix-s)
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
  found, but requires a real production artifact (CA key file, EMV kernel/HSM)?
    --yes--> EXTERNAL_FIXTURE_REQUIRED (not COVERED, not MISSING)
  |
  found, synthesizable? --no--> PARTIALLY_COVERED
  |
  yes
  v
  COVERED
```

Two rules (`SEG130-R-005` CA key checksum, `SEG130-R-016` CA key file) are pre-classified `EXTERNAL_FIXTURE_REQUIRED` per the pre-existing Appendix S package — do not attempt to force these to `COVERED` with synthetic data; genuine cryptographic/production-key validation cannot be fabricated. One rule (`SEG130-R-010`, cryptogram authenticity) is permanently `EXTERNAL_FIXTURE_REQUIRED` by design.
