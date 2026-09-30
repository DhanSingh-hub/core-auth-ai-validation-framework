# Segment 157 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 157 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG157-R-001 Segment 157 is used exclusively and allowed fo…}
    R1 -->|Fail| X1[Reject citing SEG157-R-001]
    R1 -->|Pass| R2{SEG157-R-002 Segment 157 is mutually exclusive with Segment…}
    R2 -->|Fail| X2[Reject citing SEG157-R-002]
    R2 -->|Pass| R3{SEG157-R-004 Fuel products must always be the first product…}
    R3 -->|Fail| X3[Reject citing SEG157-R-004]
    R3 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-157-rule-catalog.json](coverage/segment-157-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
