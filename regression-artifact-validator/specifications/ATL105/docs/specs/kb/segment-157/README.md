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
