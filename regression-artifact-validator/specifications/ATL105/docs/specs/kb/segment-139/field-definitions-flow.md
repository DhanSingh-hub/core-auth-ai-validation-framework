# Segment 139 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 139 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG139-R-002 Segment Type is fixed value 139, Segment Length is 3 digits, both sourced at the Device}
    R1 -->|Fail| X1[Reject citing SEG139-R-002]
    R1 -->|Pass| R2{"SEG139-R-003 Terminal Identifier (Element 102), SPDH Header (Element 206), Moneris Terminal Identifier (Element 207), Moneris Merchant ID (Element 208), Batch Number (Element 214), and Language Indicator (Element 215) are all required, Device-sourced"}
    R2 -->|Fail| X2[Reject citing SEG139-R-003]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-139-rule-catalog.json](coverage/segment-139-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
