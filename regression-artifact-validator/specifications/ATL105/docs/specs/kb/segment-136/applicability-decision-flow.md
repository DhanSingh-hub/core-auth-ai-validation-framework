# Segment 136 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 136 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG136-R-001 Segment 136 is used for both financial transac…}
    R1 -->|Fail| X1[Reject citing SEG136-R-001]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-136-rule-catalog.json](coverage/segment-136-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
