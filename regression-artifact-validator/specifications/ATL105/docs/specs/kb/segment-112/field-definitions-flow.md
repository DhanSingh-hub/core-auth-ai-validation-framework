# Segment 112 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 112 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG112-R-001 Segment Type is 112}
    R1 -->|Fail| X1[Reject citing SEG112-R-001]
    R1 -->|Pass| R2{SEG112-R-002 Segment Length identifies the segment's total…}
    R2 -->|Fail| X2[Reject citing SEG112-R-002]
    R2 -->|Pass| R3{SEG112-R-007 Additional Information Indicator Element 116 i…}
    R3 -->|Fail| X3[Reject citing SEG112-R-007]
    R3 -->|Pass| R4{SEG112-R-008 Additional Information Length Element 117 iden…}
    R4 -->|Fail| X4[Reject citing SEG112-R-008]
    R4 -->|Pass| R5{SEG112-R-009 Additional Information Element 118 is variable…}
    R5 -->|Fail| X5[Reject citing SEG112-R-009]
    R5 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-112-rule-catalog.json](coverage/segment-112-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
