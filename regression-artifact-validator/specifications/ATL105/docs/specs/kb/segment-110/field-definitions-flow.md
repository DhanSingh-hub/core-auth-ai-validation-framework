# Segment 110 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 110 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG110-R-004 Segment Type Element 85 is fixed value 110}
    R1 -->|Fail| X1[Reject citing SEG110-R-004]
    R1 -->|Pass| R2{SEG110-R-005 Segment Length Element 84 is required and iden…}
    R2 -->|Fail| X2[Reject citing SEG110-R-005]
    R2 -->|Pass| R3{SEG110-R-008 MICR Data Element 122 is required, alphanumeri…}
    R3 -->|Fail| X3[Reject citing SEG110-R-008]
    R3 -.->|Provisional| P3[REVIEW_REQUIRED]
    R3 -->|Pass| R4{SEG110-R-009 Driver's License Element 123 is conditional, a…}
    R4 -->|Fail| X4[Reject citing SEG110-R-009]
    R4 -.->|Provisional| P4[REVIEW_REQUIRED]
    R4 -->|Pass| R5{SEG110-R-011 Date of Birth Element 125 is conditional, nume…}
    R5 -->|Fail| X5[Reject citing SEG110-R-011]
    R5 -.->|Provisional| P5[REVIEW_REQUIRED]
    R5 -->|Pass| R6{SEG110-R-012 Check Type Element 126 is required, alphanumer…}
    R6 -->|Fail| X6[Reject citing SEG110-R-012]
    R6 -->|Pass| R7{SEG110-R-013 Check Number Element 127 is conditional, alpha…}
    R7 -->|Fail| X7[Reject citing SEG110-R-013]
    R7 -.->|Provisional| P7[REVIEW_REQUIRED]
    R7 -->|Pass| R8{SEG110-R-014 Customer Phone Number Element 128 is optional,…}
    R8 -->|Fail| X8[Reject citing SEG110-R-014]
    R8 -->|Pass| R9{SEG110-R-015 Customer Last Name Element 129 is optional, al…}
    R9 -->|Fail| X9[Reject citing SEG110-R-015]
    R9 -->|Pass| R10{SEG110-R-016 Check Issue Date Element 130 is optional, nume…}
    R10 -->|Fail| X10[Reject citing SEG110-R-016]
    R10 -->|Pass| R11{SEG110-R-017 Alternate MICR IND, documented in Section 12.9…}
    R11 -->|Fail| X11[Reject citing SEG110-R-017]
    R11 -.->|Provisional| P11[REVIEW_REQUIRED]
    R11 -->|Pass| R12{SEG110-R-018 Two MICR encodings are documented in narrative…}
    R12 -->|Fail| X12[Reject citing SEG110-R-018]
    R12 -.->|Provisional| P12[REVIEW_REQUIRED]
    R12 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-110-rule-catalog.json](coverage/segment-110-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
