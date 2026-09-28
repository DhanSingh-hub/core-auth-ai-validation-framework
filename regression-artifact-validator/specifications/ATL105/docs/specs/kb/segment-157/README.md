# Segment 157 (Adjusted Product Code Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.41 (page 12-106/320 to 323)
**Item Progress:** Item 1 in progress; 0 of 1 SME item resolved

## Learning Module Index

- [SME/TBA Learning Note](segment-157-sme-tba-learning-note.md) · [Flow](segment-157-flow.md) · [AI-vs-Test Comparison](segment-157-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-157-rule-catalog.json) · [SME/TBA Input Register](segment-157-sme-tba-input-register.md)

## Segment Definition

Comdata-exclusive, mutually exclusive with Segment 102, repeating per-product structure (fuel-first, max 10) with a dual-delimiter scheme and mandatory cross-segment amount reconciliation against Segment 100.

## Rule Set (9 rules — see [rule catalog](coverage/segment-157-rule-catalog.json))

Comdata-exclusivity (decline rule), Segment 102 mutual exclusion (decline rule), product-code cap (decline rule), fuel-first/max-10 structure, cross-segment amount reconciliation, no separate tax/discount/coupon codes, multi-fuel support, dual-delimiter scheme, field layout with error-prone decimal encoding.

## Do-Not-Assume Rules

1. Do not combine Segment 157 with Segment 102.
2. Do not certify Segment 157 for non-Comdata cards.
3. Do not strip leading zeros from Quantity/Unit Price — assumed-decimal encoding requires full digit-width.
4. Do not use `\` and `▲` interchangeably.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-157-rule-catalog.json) (9 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 157 |
|---|---|
| SME/TBA learning note | [Learning note](segment-157-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-157-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-157-business-requirements.md](segment-157-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-157-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-157-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-157-ai-vs-test-requirement-comparison.md) |
