# Segment 156 (EV Charging Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.40 (page 12-100/314 to 319)
**Item Progress:** Item 1 in progress; 0 of 1 SME item resolved

## Learning Module Index

- [SME/TBA Learning Note](segment-156-sme-tba-learning-note.md) · [Flow](segment-156-flow.md) · [AI-vs-Test Comparison](segment-156-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-156-rule-catalog.json) · [SME/TBA Input Register](segment-156-sme-tba-input-register.md)

## Segment Definition

Visa-only EV charging data; fixed-length, no separators; EV Transaction Indicator MUST be "Y" or the transaction is declined. 12 sub-tables covering timing, power, reason codes, connector type, and environmental metrics.

## Rule Set (7 rules)

Visa/EV-fuel-code applicability, no separators, mandatory EV indicator (decline rule), hhmmss time format (4 tables), Visa charging-reason enumeration, Visa connector-type enumeration, numeric measurement tables.

## Do-Not-Assume Rules

1. Do not certify Segment 156 present without EV Transaction Indicator = "Y".
2. Do not insert Field Separators anywhere.
