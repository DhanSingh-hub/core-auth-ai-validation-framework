# Segment 143 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 143 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG143-R-003 Segment Type is fixed value 143, Segment Lengt…}
    R1 -->|Fail| X1[Reject citing SEG143-R-003]
    R1 -->|Pass| R2{SEG143-R-004 Number of Products Element 62 is required, 2 d…}
    R2 -->|Fail| X2[Reject citing SEG143-R-004]
    R2 -->|Pass| R3{SEG143-R-006 Inclusive/Exclusive flag values: 'I' inclusive…}
    R3 -->|Fail| X3[Reject citing SEG143-R-006]
    R3 -->|Pass| R4{SEG143-R-007 Tax Type is one of GST/HST/PST for Canadian tr…}
    R4 -->|Fail| X4[Reject citing SEG143-R-007]
    R4 -.->|Provisional| P4[REVIEW_REQUIRED]
    R4 -->|Pass| R5{SEG143-R-009 A merchant may end a product's tax entry early…}
    R5 -->|Fail| X5[Reject citing SEG143-R-009]
    R5 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-143-rule-catalog.json](coverage/segment-143-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
