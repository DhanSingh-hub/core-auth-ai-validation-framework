# Segment 145 (Enhanced Fleet Request Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105, Release 2026-3 | **Source:** Section 12.31 (pages 12-72/286 to 294)
**Item Progress:** Item 1 in progress; 0 of 5 SME items resolved — see [SME/TBA Input Register](segment-145-sme-tba-input-register.md)

## Learning Module Index

- [SME/TBA Learning Note](segment-145-sme-tba-learning-note.md) · [Flow](segment-145-flow.md) · [AI-vs-Test Comparison](segment-145-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md) · [Companion-Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [Serialization](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Rule Catalog](coverage/segment-145-rule-catalog.json) · [SME/TBA Input Register](segment-145-sme-tba-input-register.md)

## Segment Definition

Carries enhanced fleet data (Wex OTR, Comdata, Voyager EMV, Visa Fleet 2.0, MasterCard Enhanced Fleet EMV) as a multi-sub-segment TLV-of-TLVs container (Element 239). Mutually exclusive with Segment 101. For 4 of the 5 authorizers, only the Prompt Table (004) sub-segment is valid.

## Rule Set (8 rules — see [rule catalog](coverage/segment-145-rule-catalog.json))

Multi-sub-segment structure, mutual exclusion with Segment 101, Prompt-Table-only restriction (4 authorizers), MasterCard availability notice, Segment Type/Length, 6-table catalog (001/002/004/006/007/008), WEX OTR product categories, authorizer-specific prompt tokens (scope provisional).

## `[PROVISIONAL]` Items — All 5 Open

See [SME/TBA Input Register](segment-145-sme-tba-input-register.md).

## Do-Not-Assume Rules

1. Do not combine Segment 145 with Segment 101 — explicitly prohibited.
2. Do not assume non-Prompt-Table sub-segments are valid for Voyager EMV/Visa Fleet 2.0/Comdata/MasterCard Enhanced Fleet EMV.
3. Do not assume all 5 authorizers share the same prompt-token catalog.
4. Do not assume MasterCard Enhanced Fleet EMV's June 2026 availability notice is still current without confirmation.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-145-rule-catalog.json) (8 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 145 |
|---|---|
| SME/TBA learning note | [Learning note](segment-145-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-145-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-145-business-requirements.md](segment-145-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-145-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-145-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-145-ai-vs-test-requirement-comparison.md) |
