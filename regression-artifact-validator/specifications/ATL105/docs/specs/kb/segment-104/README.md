# Segment 104 Learning Module

This folder is the focused learning and analysis module for the ATL105 Purchase Card Data Segment (Segment 104). It follows the complete Segment 100 knowledge-base learning structure, with field modules adapted to Purchase Card data rather than copying unrelated Segment 100 account-entry or partial-approval topics.

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

## Training and Validation Entry Points

- [Segment Training Methodology](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md)
- [SME and Technical Business Analysis Note](segment-104-sme-tba-learning-note.md)
- [Segment 104 End-to-End Flow](segment-104-flow.md)
- [Coverage Closure](coverage/README.md)
- [Purchase-Card Applicability SME/TBA Note](purchase-card-applicability-sme-tba-note.md)
- [Purchase-Card Applicability Decision Flow](purchase-card-applicability-flow.md)
- [Purchase Code SME/TBA Note](purchase-code-sme-tba-note.md)
- [Purchase Code Validation Flow](purchase-code-flow.md)
- [Purchase Amounts SME/TBA Note](purchase-amounts-sme-tba-note.md)
- [Purchase Amounts Validation Flow](purchase-amounts-flow.md)
- [Shipping Data SME/TBA Note](shipping-data-sme-tba-note.md)
- [Shipping Data Validation Flow](shipping-data-flow.md)
- [Direct Marketing Invoice SME/TBA Note](direct-marketing-invoice-sme-tba-note.md)
- [Direct Marketing Invoice Flow](direct-marketing-invoice-flow.md)
- [Lifecycle SME/TBA Note](lifecycle-sme-tba-note.md)
- [Lifecycle Flow](lifecycle-flow.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Final Closure SME/TBA Note](final-closure-sme-tba-note.md)
- [Final Closure Flow](final-closure-flow.md)

## Rule and Artifact Entry Points

- [Segment 104 Rule Catalog (authoritative)](coverage/segment-104-rule-catalog.json)
- [Segment 104 Payload Validation Rules](segment-104-validation-rules.json)
- [AI Business Requirements — JSON](../../../../test-output/ai-artifacts/business-requirements/POC-AI-ATL105-Segment-104-Business-Requirements.json)
- [AI Business Requirements — Markdown](../../../../test-output/ai-artifacts/business-requirements/POC-AI-ATL105-Segment-104-Business-Requirements.md)
- [AI BR Coverage Report](../../../../test-output/ai-artifacts/coverage-reports/POC-AI-Segment-104-BR-Coverage-Report.md)

## Scope

This module covers:

- Segment 104's role as a conditional Data Section 3 companion to Segment 100.
- Purchase-card applicability and the required Segment 100 companion relationship.
- Segment 104 field responsibilities: purchase code, tax/freight/duty amounts, shipping data, and invoice number.
- Field separators, field order, declared Segment Length, and the 86-character segment ceiling.
- Lifecycle correlation through Segment 100 rather than an invented Segment 104 sequence field.
- Business-requirement, scenario, test-case, and test-data decomposition.

It does not claim that Segment 104 is valid for every transaction. It is included only when the flow requires purchase-card data, and unresolved source rules remain `REVIEW_REQUIRED`.

## Authoritative Neighboring Knowledge

- [Segment compatibility matrix](../segment-compatibility-matrix.md)
- [Financial transaction request sections](../11-financial-transaction-request-sections.md)
- [Data element catalog](../13-data-elements.md)
- [Canonical artifact contract](../../../canonical-artifact-contract.md)
- ATL105 2026-3, Section 11.1.1 and Section 12.5.
