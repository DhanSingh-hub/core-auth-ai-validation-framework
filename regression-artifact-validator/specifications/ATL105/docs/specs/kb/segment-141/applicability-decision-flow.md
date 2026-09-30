# Segment 141 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 141 payload] --> B{Applicability and Message-Family Decision in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG141-R-001 Segment 141 is used to close out each pay point to Moneris; it should be sent once for each pay point at day end}
    R1 -->|Fail| X1[Reject citing SEG141-R-001]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-141-rule-catalog.json](coverage/segment-141-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
