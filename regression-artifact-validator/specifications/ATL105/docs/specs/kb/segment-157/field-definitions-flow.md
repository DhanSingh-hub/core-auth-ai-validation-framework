# Segment 157 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 157 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG157-R-003 Segment 157 does not allow any product codes a…}
    R1 -->|Fail| X1[Reject citing SEG157-R-003]
    R1 -->|Pass| R2{SEG157-R-005 The total of Adjusted Product Amounts in the s…}
    R2 -->|Fail| X2[Reject citing SEG157-R-005]
    R2 -->|Pass| R3{SEG157-R-006 Tax, discount, and coupon amounts are already…}
    R3 -->|Fail| X3[Reject citing SEG157-R-006]
    R3 -->|Pass| R4{SEG157-R-007 Multi-fuel support: BUYPASS can accept transac…}
    R4 -->|Fail| X4[Reject citing SEG157-R-007]
    R4 -->|Pass| R5{SEG157-R-009 Segment Type fixed 157, Segment Length 3 digit…}
    R5 -->|Fail| X5[Reject citing SEG157-R-009]
    R5 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-157-rule-catalog.json](coverage/segment-157-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
