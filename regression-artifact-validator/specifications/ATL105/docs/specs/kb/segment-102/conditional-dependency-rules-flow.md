# Segment 102 Conditional Fields and Cross-Field Dependencies Flow

```mermaid
flowchart TD
    A[Segment 102 payload] --> B{Conditional Fields and Cross-Field Depe… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG102-R-005 Number of Products matches the actual serializ…}
    R1 -->|Fail| X1[Reject citing SEG102-R-005]
    R1 -->|Pass| R2{SEG102-R-007 Fuel products are always the first products in…}
    R2 -->|Fail| X2[Reject citing SEG102-R-007]
    R2 -.->|Provisional| P2[REVIEW_REQUIRED]
    R2 -->|Pass| R3{SEG102-R-008 For EV charging transactions the EV product co…}
    R3 -->|Fail| X3[Reject citing SEG102-R-008]
    R3 -.->|Provisional| P3[REVIEW_REQUIRED]
    R3 -->|Pass| R4{SEG102-R-009 A unique Product Code is sent for each type of…}
    R4 -->|Fail| X4[Reject citing SEG102-R-009]
    R4 -->|Pass| R5{SEG102-R-015 Sum of Product Amounts reconciles with Segment…}
    R5 -->|Fail| X5[Reject citing SEG102-R-015]
    R5 -->|Pass| R6{SEG102-R-016 Tax-coded product totals are reflected in Segm…}
    R6 -->|Fail| X6[Reject citing SEG102-R-016]
    R6 -->|Pass| R7{SEG102-R-017 Fuel merchants send both fuel and nonfuel prod…}
    R7 -->|Fail| X7[Reject citing SEG102-R-017]
    R7 -.->|Provisional| P7[REVIEW_REQUIRED]
    R7 -->|Pass| R8{SEG102-R-024 Multi-fuel OTR transactions list the primary f…}
    R8 -->|Fail| X8[Reject citing SEG102-R-024]
    R8 -.->|Provisional| P8[REVIEW_REQUIRED]
    R8 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-102-rule-catalog.json](coverage/segment-102-rule-catalog.json) · Note: [conditional-dependency-rules-sme-tba-note.md](conditional-dependency-rules-sme-tba-note.md)
