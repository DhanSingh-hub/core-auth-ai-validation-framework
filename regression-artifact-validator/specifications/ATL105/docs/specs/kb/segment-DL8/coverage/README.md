# Segment DL8 Coverage Closure

```text
Authoritative rule catalog (5 rules)
  -> canonical source anchors
  -> BR mapping (BR-SEGDL8-001..005)
  -> scenario mapping
  -> test-case mapping (positive + negative per rule)
  -> symbolic request/response test data (10 candidate pairs)
  -> candidate-only traceability and coverage classification
  -> independent AI artifact comparison and sample validation
```

**Current result:** 5 source-anchored oracle rules, 5 candidate scenarios, 10 candidate cases/data pairs, with positive and negative candidate intent for each rule. No fixtures are approved; no cases are executed; no rules are certified. The supplied AI evidence contains 0 DL8-tagged catalog records and one representative phase-one gap chain that produced no test data.

## Artifacts

- [Rule catalog](segment-DL8-rule-catalog.json)
- [Coverage note](segment-DL8-coverage-sme-tba-note.md) · [Coverage flow](segment-DL8-coverage-flow.md)
- [Business requirements](../segment-DL8-business-requirements.md)
- [SME/TBA input register](../segment-DL8-sme-tba-input-register.md) · [AI-vs-Test comparison](../segment-DL8-ai-vs-test-requirement-comparison.md)
- [Candidate coverage package](../../../../../test-output/test-json/segment-DL8-coverage-package.json)
- [Corrected AI-derived candidate (not approved)](../../../../../test-output/test-json/segment-DL8-ai-corrected-candidate.json)
- [Observed AI field-alias crosswalk (comparison-only)](../../../../../contract/segment-DL8-field-alias-crosswalk.json)
- [SME decision context](segment-DL8-sme-decision-context.md)
- [Generated AI artifact coverage report](segment-DL8-ai-artifact-coverage-report.md) · [JSON](segment-DL8-ai-artifact-coverage-report.json)
- [Consolidated readiness report](../../../../../test-output/consolidated-reports/SEGMENT-DL8-CONSOLIDATED-REPORT.txt)
- Generator: `src/main/java/com/coreauth/validator/coverage/GenerateSegmentDl8AiArtifactCoverageReport.java`
- Focused tests: `src/test/java/com/coreauth/validator/coverage/GenerateSegmentDl8AiArtifactCoverageReportTest.java`

## Approval Gate

1. The 5-rule catalog represents the independent ATL105 oracle denominator; AI catalog matches never define it.
2. A complete candidate chain is not executed coverage. Keep approved pairs, executed cases, and certified rules at zero until their gates pass.
3. Rules linked to `SEGDL8-SME-002`, `SEGDL8-SME-003`, and shared `SEGDL7-SME-005` stay `REVIEW_REQUIRED`.
4. Fixtures must state the terminal Special and Table Load placement; approval remains blocked on `SEGDL8-SME-001` and `SEGDL8-SME-002`.
5. Structured candidate JSON cannot prove RID representation, card-type valid values, or all stand-in/floor-limit combinations pending `SEGDL8-SME-003`.
