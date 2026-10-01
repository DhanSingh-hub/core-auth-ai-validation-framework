# Segment DL8 Coverage Closure

```text
Authoritative rule catalog (5 rules)
  -> canonical source anchors
  -> BR mapping (BR-SEGDL8-001..005)
  -> scenario mapping
  -> test-case mapping (positive + negative per rule)
  -> segment-level test data plus terminal-profile context (Special)
  -> coverage classification
  -> approval report
```

## Artifacts

- [Rule catalog](segment-DL8-rule-catalog.json)
- [Coverage note](segment-DL8-coverage-sme-tba-note.md) · [Coverage flow](segment-DL8-coverage-flow.md)
- [Business requirements](../segment-DL8-business-requirements.md)
- [SME/TBA input register](../segment-DL8-sme-tba-input-register.md) · [AI-vs-Test comparison](../segment-DL8-ai-vs-test-requirement-comparison.md)

## Approval Gate

1. The 5-rule catalog represents the intended DL8 scope.
2. Fixtures must state whether the terminal has the Special.
3. Rules linked to `SEGDL8-SME-002`, `-003` and `SEGDL7-SME-005` stay `REVIEW_REQUIRED`.
