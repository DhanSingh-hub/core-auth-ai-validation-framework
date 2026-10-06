# Segment 101 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 101 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG101-R-004 Segment Type is 101}
    R1 -->|Fail| X1[Reject citing SEG101-R-004]
    R1 -->|Pass| R2{SEG101-R-005 Segment Length is 3 digits representing the segment content length}
    R2 -->|Fail| X2[Reject citing SEG101-R-005]
    R2 -->|Pass| R3{SEG101-R-009 Odometer, when populated, is numeric with maximum length 8}
    R3 -->|Fail| X3[Reject citing SEG101-R-009]
    R3 -->|Pass| R4{SEG101-R-010 Vehicle Number, when populated, is alphanumeric with maximum length 10}
    R4 -->|Fail| X4[Reject citing SEG101-R-010]
    R4 -->|Pass| R5{SEG101-R-011 Job Number, when populated, is alphanumeric with maximum length 10}
    R5 -->|Fail| X5[Reject citing SEG101-R-011]
    R5 -->|Pass| R6{SEG101-R-012 Driver/Identification Number, when populated, is alphanumeric with maximum length 10}
    R6 -->|Fail| X6[Reject citing SEG101-R-012]
    R6 -->|Pass| R7{SEG101-R-013 Fleet Employee Number, when populated, is alphanumeric with maximum length 10}
    R7 -->|Fail| X7[Reject citing SEG101-R-013]
    R7 -->|Pass| R8{SEG101-R-014 License #, when populated, is alphanumeric with maximum length 10}
    R8 -->|Fail| X8[Reject citing SEG101-R-014]
    R8 -->|Pass| R9{SEG101-R-015 Job ID, when populated, is alphanumeric with maximum length 12}
    R9 -->|Fail| X9[Reject citing SEG101-R-015]
    R9 -->|Pass| R10{SEG101-R-016 Department #, when populated, is alphanumeric with maximum length 12}
    R10 -->|Fail| X10[Reject citing SEG101-R-016]
    R10 -->|Pass| R11{SEG101-R-017 Customer Data, when populated, is alphanumeric with maximum length 12}
    R11 -->|Fail| X11[Reject citing SEG101-R-017]
    R11 -->|Pass| R12{SEG101-R-018 User ID, when populated, is alphanumeric with maximum length 12 and non-zero}
    R12 -->|Fail| X12[Reject citing SEG101-R-018]
    R12 -->|Pass| Z[Rules satisfied]
    Z --> M[4 further rules - see catalog]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-101-rule-catalog.json](coverage/segment-101-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
