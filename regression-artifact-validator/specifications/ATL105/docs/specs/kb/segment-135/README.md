# Segment 135 (Moneris Data (Request) Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.24 (page 12-60/274)
**Item Progress:** Item 1 in progress; 0 of 3 SME items resolved — see [SME/TBA Input Register](segment-135-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-135-sme-tba-learning-note.md) · [Flow](segment-135-flow.md) · [AI-vs-Test Comparison](segment-135-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-135-rule-catalog.json) · [SME/TBA Input Register](segment-135-sme-tba-input-register.md)

## Segment Definition

Carries Moneris-authorizer-specific data (`<tag><len><data>` TLV format, Appendix V) in a Financial Transaction Request; required only when the transaction is destined for the Moneris authorizer.

## Rule Set (5 rules — see [rule catalog](coverage/segment-135-rule-catalog.json))

Applicability (Moneris-destined only), Segment Type fixed 135, Segment Length (separator-inclusion provisional), separator behavior (per-field + internal TLV exception), Moneris Data TLV field (Appendix V scope provisional).

## `[PROVISIONAL]` Items — All 3 Open

See [SME/TBA Input Register](segment-135-sme-tba-input-register.md).

## Do-Not-Assume Rules

1. Do not treat Segment 135 as universally required — it is Moneris-destination-conditional.
2. Do not insert Field Separators inside the Moneris Data TLV sub-structure.
3. Do not fabricate Appendix V tag/length/data sub-field rules without SME confirmation.
