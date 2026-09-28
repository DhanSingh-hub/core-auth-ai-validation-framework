# Segment 113 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 113 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG113-R-003 Segment Type is 113}
    R1 -->|Fail| X1[Reject citing SEG113-R-003]
    R1 -->|Pass| R2{SEG113-R-004 Segment Length is 3 digits representing the se…}
    R2 -->|Fail| X2[Reject citing SEG113-R-004]
    R2 -->|Pass| R3{SEG113-R-008 ECA/TeleCheck® Clerk ID, required, is alphanum…}
    R3 -->|Fail| X3[Reject citing SEG113-R-008]
    R3 -->|Pass| R4{SEG113-R-009 ECA/TeleCheck® Product Code, when populated, i…}
    R4 -->|Fail| X4[Reject citing SEG113-R-009]
    R4 -->|Pass| R5{SEG113-R-010 ECA/TeleCheck® Phone Number, when populated, i…}
    R5 -->|Fail| X5[Reject citing SEG113-R-010]
    R5 -->|Pass| R6{SEG113-R-011 ECA/TeleCheck® Trace ID, when populated, is al…}
    R6 -->|Fail| X6[Reject citing SEG113-R-011]
    R6 -->|Pass| R7{SEG113-R-012 Merchant Trace ID, when populated, is alphanum…}
    R7 -->|Fail| X7[Reject citing SEG113-R-012]
    R7 -->|Pass| R8{SEG113-R-013 Denial Record Number, when populated, is alpha…}
    R8 -->|Fail| X8[Reject citing SEG113-R-013]
    R8 -->|Pass| R9{SEG113-R-014 Extended MICR Data, when populated, is alphanu…}
    R9 -->|Fail| X9[Reject citing SEG113-R-014]
    R9 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-113-rule-catalog.json](coverage/segment-113-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
