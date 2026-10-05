# Segment DL6 Coverage Closure

```text
Authoritative rule catalog (7 rules)
  -> canonical source anchors
  -> BR mapping (BR-SEGDL6-001..007)
  -> scenario mapping
  -> test-case mapping (positive + negative per rule)
  -> Table Load Response test data containing DL1 and DL6
  -> coverage classification
  -> approval report
```

## Artifacts

- [Rule catalog](segment-DL6-rule-catalog.json)
- [Coverage note](segment-DL6-coverage-sme-tba-note.md) · [Coverage flow](segment-DL6-coverage-flow.md)
- [Business requirements](../segment-DL6-business-requirements.md)
- [SME/TBA input register](../segment-DL6-sme-tba-input-register.md) · [AI-vs-Test comparison](../segment-DL6-ai-vs-test-requirement-comparison.md)

## Approval Gate

1. The 7-rule catalog represents the intended DL6 scope.
2. Every DL6 test record must include the DL1 that triggers (or does not trigger) it.
3. Rules linked to `SEGDL6-SME-001`, `-003`, `-004`, `-005` and `SEGDL1-SME-003` stay `REVIEW_REQUIRED`.

## Candidate package outputs

- [Coverage package](../../../../../test-output/test-json/segment-DL6-coverage-package.json) — 7 BRs, 7 scenarios, 14 candidate cases, 14 symbolic request/response pairs; approved/executed/certified counts are 0.
- [Corrected AI candidate](../../../../../test-output/test-json/segment-DL6-ai-corrected-candidate.json) — synthetic, unapproved comparison artifact.
- [Field-alias crosswalk](../../../../../contract/segment-DL6-field-alias-crosswalk.json) — observed AI field names only, comparison-only.
- [AI artifact coverage report](segment-DL6-ai-artifact-coverage-report.md) and [SME decision context](segment-DL6-sme-decision-context.md).
