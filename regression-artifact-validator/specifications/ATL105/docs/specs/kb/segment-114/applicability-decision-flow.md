# Segment 114 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 114 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG114-R-001 Segment 114 is exclusive to the Loyalty Card T…}
    R1 -->|Fail| X1[Reject citing SEG114-R-001]
    R1 -->|Pass| R2{SEG114-R-002 Segment 114 is optional in every Loyalty Card…}
    R2 -->|Fail| X2[Reject citing SEG114-R-002]
    R2 -->|Pass| R3{SEG114-R-010 Segment 114 may repeat within a single message…}
    R3 -->|Fail| X3[Reject citing SEG114-R-010]
    R3 -->|Pass| R4{SEG114-R-011 Segment 114 never appears without Segment 108:…}
    R4 -->|Fail| X4[Reject citing SEG114-R-011]
    R4 -->|Pass| R5{SEG114-R-012 The AI-generated relationship 'The Financial T…}
    R5 -->|Fail| X5[Reject citing SEG114-R-012]
    R5 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-114-rule-catalog.json](coverage/segment-114-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
