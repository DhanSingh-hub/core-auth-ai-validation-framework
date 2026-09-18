# Segment 105 Learning Module

This folder is the focused learning and analysis module for the ATL105 Totals Data Segment (Segment 105).

## Training Status

Phase 1, specification analysis, is in progress. The initial rule catalog is derived from the ATL105 2026-3 Totals Request layout. It is not a production sign-off and does not confirm behavior that needs merchant, terminal, or settlement-policy knowledge.

## Scope

- Totals Request message using Segment 105.
- Segment 105 field order, requiredness, fixed values, and basic representation rules.
- Totals Date request codes and their source-defined response values.
- Request/response traceability and source-anchor vocabulary.

Out of scope until independently sourced and approved:

- Merchant-specific settlement cutoff and end-of-day operational policy.
- Employee/password authorization policy.
- Totals aggregation, reconciliation, retry, duplicate-request, and failure behavior.
- The Segment 119 proprietary-data-load alternative and its selection rule.

## Source Evidence

- ATL105 2026-3, Totals Request layout, Segment 105, pages 227-229.
- ATL105 2026-3, Element 105 Totals Date definition.
- `docs/atl105_complete_templates.json`, `Totals Request` template.

## Contents

- [Canonical anchors](segment-105-canonical-anchors.md)
- [SME/TBA input register](segment-105-sme-tba-input-register.md)
- [Machine-readable rule catalog](coverage/segment-105-rule-catalog.json)
- [Segment 105 SME/TBA learning note](segment-105-sme-tba-learning-note.md)
- [Segment 105 end-to-end flow](segment-105-flow.md)
- [Totals Date learning note](totals-date-sme-tba-note.md)
- [Totals Date decision flow](totals-date-flow.md)
- [Operator authorization learning note](operator-authorization-sme-tba-note.md)
- [Operator authorization flow](operator-authorization-flow.md)
- [Terminal and version learning note](terminal-device-sme-tba-note.md)
- [Terminal and version flow](terminal-device-flow.md)
- [Totals aggregation learning note](totals-aggregation-sme-tba-note.md)
- [Totals aggregation flow](totals-aggregation-flow.md)
- [Request-response lifecycle learning note](sequence-lifecycle-sme-tba-note.md)
- [Request-response lifecycle flow](sequence-lifecycle-flow.md)
- [Serialization and wire-format learning note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and wire-format flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Coverage closure](coverage/README.md)
- [Final closure learning note](final-closure-sme-tba-note.md)
- [Final closure flow](final-closure-flow.md)

## Required Artifact Chain

```text
BR -> TS -> TC -> converter-ready Totals Request / Totals Response JSON
```

Every artifact must retain a canonical Segment 105 source anchor. Preserve AI-generated artifacts unchanged after intake; Test Solution fixtures do not prove AI coverage.

## Next Training Gate

Use the SME/TBA input register to resolve all `REVIEW_REQUIRED` items. After the answers are recorded, create baseline BRs and test scenarios before implementing the Segment 105 validator.