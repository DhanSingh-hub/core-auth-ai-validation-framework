# Segment 115 Coverage Closure

This folder defines the Segment 115 coverage work package: turning the current test artifacts into an auditable coverage decision. It mirrors the [Segment 114 Coverage Closure](../../segment-114/coverage/README.md) package.

## Work Package

```text
Authoritative Segment 115 rule catalog (13 rules)
  -> canonical source anchors (Section 12.14, Elements 115, 150, 152)
  -> BR mapping
  -> scenario mapping
  -> test-case mapping
  -> test-data mapping
  -> coverage classification (COVERED / PARTIALLY_COVERED / REVIEW_REQUIRED / MISSING)
  -> approval report
```

## Artifacts

- [Segment 115 Rule Catalog](segment-115-rule-catalog.json)
- [SME and TBA coverage note](segment-115-coverage-sme-tba-note.md)
- [Coverage closure flow](segment-115-coverage-flow.md)
- [SME/TBA Input Register](../segment-115-sme-tba-input-register.md)
- [AI-Generated vs Test-Generated Requirement Comparison](../segment-115-ai-vs-test-requirement-comparison.md)

## Approval Gate

Before implementation, confirm:

1. The Segment 115 rule catalog represents the intended scope (segment identity/length, the 3-field Print Data Segment layout, response-side conditional inclusion, and companion-segment scope).
2. `COVERED`, `PARTIALLY_COVERED`, `REVIEW_REQUIRED`, and `MISSING` are sufficient statuses.
3. A rule is not `COVERED` unless BR, scenario, test case, and test data all share the canonical anchor.
4. `REVIEW_REQUIRED` must not be treated as a passing certification result.
5. The Test Team owns the approval decision; the AI output does not approve itself.
6. Rules blocked by `PROVISIONAL` items (P-01 through P-06, all open) remain `REVIEW_REQUIRED` until the SME resolves the underlying question.
