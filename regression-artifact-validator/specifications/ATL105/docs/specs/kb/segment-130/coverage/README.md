# Segment 130 Coverage Closure

This folder defines the Segment 130 coverage work package, building on the pre-existing Test Team baseline (`test-json/segment-130-core-structure-package.json`, Appendix R, Appendix S) rather than starting from zero.

## Work Package

```text
Authoritative Segment 130 rule catalog (23 rules)
  -> canonical source anchors (Section 12.20, 11.8.1, 10.14.2-10.14.4, Appendix R, S, T)
  -> BR mapping (partially pre-existing: BR-SEG130-*, BR-SEG100-APPR-*, BR-SEG100-APPS-*)
  -> scenario mapping
  -> test-case mapping
  -> test-data mapping (2 items already EXTERNAL_FIXTURE_REQUIRED)
  -> coverage classification (COVERED / PARTIALLY_COVERED / REVIEW_REQUIRED / MISSING / EXTERNAL_FIXTURE_REQUIRED)
  -> approval report
```

## Artifacts

- [Segment 130 Rule Catalog](segment-130-rule-catalog.json)
- [SME and TBA coverage note](segment-130-coverage-sme-tba-note.md)
- [Coverage closure flow](segment-130-coverage-flow.md)
- [SME/TBA Input Register](../segment-130-sme-tba-input-register.md)
- [AI-Generated vs Test-Generated Requirement Comparison](../segment-130-ai-vs-test-requirement-comparison.md)

## Approval Gate

Before implementation, confirm:

1. The Segment 130 rule catalog correctly consolidates the pre-existing `segment-130-core-structure-package.json`, `appendix-r-segment-100-coverage.json`, and `appendix-s-segment-100-coverage.json` BRs without contradiction or duplication.
2. `EXTERNAL_FIXTURE_REQUIRED` is retained as a distinct status (in addition to `COVERED`, `PARTIALLY_COVERED`, `REVIEW_REQUIRED`, `MISSING`) for items that cannot be synthesized (CA key authenticity, cryptogram verification).
3. A rule is not `COVERED` unless BR, scenario, test case, and test data all share the canonical anchor.
4. The Test Team owns the approval decision; the AI output does not approve itself.
5. Rules blocked by `PROVISIONAL` items (P-01 through P-06) remain `REVIEW_REQUIRED` until the SME resolves the underlying question.
