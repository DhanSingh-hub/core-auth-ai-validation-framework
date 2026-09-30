# Segment 119 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 119 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG119-R-008 Segment Type is fixed value 119}
    R1 -->|Fail| X1[Reject citing SEG119-R-008]
    R1 -->|Pass| R2{SEG119-R-009 Segment Length is required and includes Segment Type and Field Separators}
    R2 -->|Fail| X2[Reject citing SEG119-R-009]
    R2 -->|Pass| R3{SEG119-R-010 Information Byte is required}
    R3 -->|Fail| X3[Reject citing SEG119-R-010]
    R3 -->|Pass| R4{SEG119-R-011 Terminal Identifier is required}
    R4 -->|Fail| X4[Reject citing SEG119-R-011]
    R4 -->|Pass| R5{SEG119-R-012 Prompt Code is fixed value 990}
    R5 -->|Fail| X5[Reject citing SEG119-R-012]
    R5 -->|Pass| R6{SEG119-R-013 Employee Number is conditional}
    R6 -->|Fail| X6[Reject citing SEG119-R-013]
    R6 -->|Pass| R7{SEG119-R-014 Password is conditional}
    R7 -->|Fail| X7[Reject citing SEG119-R-014]
    R7 -->|Pass| R8{SEG119-R-015 Totals Date is required}
    R8 -->|Fail| X8[Reject citing SEG119-R-015]
    R8 -->|Pass| R9{SEG119-R-016 Hardware Version is required}
    R9 -->|Fail| X9[Reject citing SEG119-R-016]
    R9 -->|Pass| R10{SEG119-R-017 Software Version is required}
    R10 -->|Fail| X10[Reject citing SEG119-R-017]
    R10 -->|Pass| R11{SEG119-R-018 Firmware Version is required}
    R11 -->|Fail| X11[Reject citing SEG119-R-018]
    R11 -->|Pass| R12{SEG119-R-019 Sequence Number is required}
    R12 -->|Fail| X12[Reject citing SEG119-R-019]
    R12 -->|Pass| Z[Rules satisfied]
    Z --> M[8 further rules - see catalog]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-119-rule-catalog.json](coverage/segment-119-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
