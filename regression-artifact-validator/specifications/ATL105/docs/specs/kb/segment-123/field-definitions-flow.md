# Segment 123 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 123 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG123-R-001 Segment Type is fixed value 123 Element 85 , D…}
    R1 -->|Fail| X1[Reject citing SEG123-R-001]
    R1 -->|Pass| R2{SEG123-R-006 Token PAN Suffix Element 197, 4 chars, Conditi…}
    R2 -->|Fail| X2[Reject citing SEG123-R-006]
    R2 -->|Pass| R3{SEG123-R-007 CAVV, Revised Format Element 195, 20 chars, Op…}
    R3 -->|Fail| X3[Reject citing SEG123-R-007]
    R3 -->|Pass| R4{SEG123-R-008 Cryptogram Token Data Element 202 length is 28…}
    R4 -->|Fail| X4[Reject citing SEG123-R-008]
    R4 -->|Pass| R5{SEG123-R-009 SafeKey Data Element 203, 58 chars is composed…}
    R5 -->|Fail| X5[Reject citing SEG123-R-009]
    R5 -->|Pass| R6{SEG123-R-010 TAVV Cryptogram Element 237, 28 bytes, base64…}
    R6 -->|Fail| X6[Reject citing SEG123-R-010]
    R6 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-123-rule-catalog.json](coverage/segment-123-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
