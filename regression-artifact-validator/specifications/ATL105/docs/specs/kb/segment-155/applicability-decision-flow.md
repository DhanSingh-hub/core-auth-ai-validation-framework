# Segment 155 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 155 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG155-R-001 Segment 155 can only be sent when the transact…}
    R1 -->|Fail| X1[Reject citing SEG155-R-001]
    R1 -->|Pass| R2{SEG155-R-003 Most sub-tables Card Number 001, TransArmor To…}
    R2 -->|Fail| X2[Reject citing SEG155-R-003]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-155-rule-catalog.json](coverage/segment-155-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
