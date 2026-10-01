# Segment 142 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 142 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG142-R-002 Segment Type and Segment Length are Host-sourced (contrast Segment 140's Device-sourced Segment Type/Length, and Segment 141's fully-Device-sourced request)}
    R1 -->|Fail| X1[Reject citing SEG142-R-002]
    R1 -->|Pass| R2{SEG142-R-003 Terminal Identifier, Moneris Terminal Identifier, Moneris Merchant ID are Device-sourced echoes; SPDH Header, Batch Number, Response Display are Moneris-sourced; MAC (Element 210, 16 characters) is Moneris-sourced and validates the response at the terminal}
    R2 -->|Fail| X2[Reject citing SEG142-R-003]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-142-rule-catalog.json](coverage/segment-142-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
