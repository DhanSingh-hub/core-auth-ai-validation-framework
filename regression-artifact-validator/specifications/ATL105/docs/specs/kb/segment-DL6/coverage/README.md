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
