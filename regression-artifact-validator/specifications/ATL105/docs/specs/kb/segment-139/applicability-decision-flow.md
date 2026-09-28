# Segment 139 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 139 payload] --> B{Applicability and Message-Family Decisi… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG139-R-001 Segment 139 is used to retrieve debit totals f…}
    R1 -->|Fail| X1[Reject citing SEG139-R-001]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-139-rule-catalog.json](coverage/segment-139-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
