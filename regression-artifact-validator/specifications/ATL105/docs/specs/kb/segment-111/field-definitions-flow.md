# Segment 111 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[Segment 111 payload] --> B{Field Definitions and Element Semantics in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG111-R-001 Segment Type fixed 111}
    R1 -->|Fail| X1[Reject citing SEG111-R-001]
    R1 -->|Pass| R2{SEG111-R-002 Segment Length format and computation}
    R2 -->|Fail| X2[Reject citing SEG111-R-002]
    R2 -->|Pass| R3{SEG111-R-003 Indicator length}
    R3 -->|Fail| X3[Reject citing SEG111-R-003]
    R3 -->|Pass| R4{SEG111-R-004 Variable information length}
    R4 -->|Fail| X4[Reject citing SEG111-R-004]
    R4 -->|Pass| R5{SEG111-R-005 Repeated section maximum}
    R5 -->|Fail| X5[Reject citing SEG111-R-005]
    R5 -->|Pass| R6{SEG111-R-006 Total maximum}
    R6 -->|Fail| X6[Reject citing SEG111-R-006]
    R6 -->|Pass| R7{SEG111-R-007 Separator serialization}
    R7 -->|Fail| X7[Reject citing SEG111-R-007]
    R7 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-111-rule-catalog.json](coverage/segment-111-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
