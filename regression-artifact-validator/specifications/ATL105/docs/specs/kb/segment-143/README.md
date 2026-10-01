# Segment 143 (Tax by Product Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.30 (page 12-68/282 to 286)
**Item Progress:** Item 1 in progress; 0 of 2 SME items resolved — see [SME/TBA Input Register](segment-143-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-143-sme-tba-learning-note.md) · [Flow](segment-143-flow.md) · [AI-vs-Test Comparison](segment-143-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-143-rule-catalog.json) · [SME/TBA Input Register](segment-143-sme-tba-input-register.md)

## Segment Definition

The most structurally complex segment trained in this session: reports up to 3 taxes per product for up to 10 products, mandatorily paired with Segment 102 (same order). Uses a **dual-delimiter scheme** — Field Separator between products, backslash (`\`) between same-product tax entries — and an **"N" flag that structurally omits** the following 2 fields rather than leaving them blank.

## Rule Set (9 rules — see [rule catalog](coverage/segment-143-rule-catalog.json))

Requires paired Segment 102 (same order), format-only validation (no value judgement), Segment Type/Length, Number of Products, per-product repeating tax structure, I/E/N flag semantics with field omission, Tax Type enumeration (Canadian-confirmed, others provisional), dual-delimiter scheme, early-termination rule.

## `[PROVISIONAL]` Items — Both Open

See [SME/TBA Input Register](segment-143-sme-tba-input-register.md): Tax Type scope beyond Canada; missing AI/Test artifact package.

## Do-Not-Assume Rules

1. Do not certify Segment 143 without a paired Segment 102 in matching order.
2. Do not emit empty Tax Type/Amount fields for an "N" tax — they must be structurally omitted.
3. Do not use `\` between products, or Field Separator between same-product tax entries — the two delimiters are not interchangeable.
4. Do not add value-level business-rule validation — the specification explicitly limits this segment to format-only checks.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-143-rule-catalog.json) (9 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 143 |
|---|---|
| SME/TBA learning note | [Learning note](segment-143-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-143-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-143-business-requirements.md](segment-143-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-143-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-143-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-143-ai-vs-test-requirement-comparison.md) |
