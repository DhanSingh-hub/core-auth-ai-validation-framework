# Segment 102 Learning Module

This folder is the focused learning and analysis module for the ATL105 Product Code Data Segment (Segment 102), structured the same way as the [Segment 100 module](../segment-100/README.md).

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

## Contents

- [SME and Technical Business Analysis Note](segment-102-sme-tba-learning-note.md)
- [Segment 102 End-to-End Flow](segment-102-flow.md)
- [SME/TBA Input Register](segment-102-sme-tba-input-register.md)
- [AI-vs-Test Requirement Comparison](segment-102-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Serialization Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)

## [PROVISIONAL] Items

- P-01: Product Code enum values pending Appendix F transcription (`SEG102-SME-001`).
- P-02: Fuel/EV-products-first ordering rules pending confirmation (`SEG102-SME-002`).
- P-03: Multi-fuel OTR primary-fuel-first rule pending confirmation (`SEG102-SME-003`).
- P-04: Segment 143 product-order consistency rule pending confirmation (`SEG102-SME-004`).
- P-05: No dedicated Segment 102 AI/Test package located (`SEG102-SME-005`).

## Scope

This module covers:

- Segment 102 layout, field values, and serialization rules
- Fuel/EV product-ordering rules within the segment
- Reconciliation between Segment 102 itemized amounts and Segment 100 aggregate amounts
- Mutual exclusivity with Segment 157 and non-applicability to Comdata cards
- Business-requirement, scenario, test-case, and test-data analysis for Segment 102

It does not claim that Segment 102 alone represents every fuel/product transaction. Segment 100 remains the required core; Segment 102 is a conditional Section 3 companion added when the flow requires itemized product data.

## Authoritative Neighboring Knowledge

- [Segment compatibility matrix](../segment-compatibility-matrix.md)
- [Financial transaction request sections](../11-financial-transaction-request-sections.md)
- [Segment 100 module](../segment-100/README.md)
- [Segment 103 EBT SME note](../segment-103-ebt-sme-note.md)
- [Canonical artifact contract](../../../canonical-artifact-contract.md)
