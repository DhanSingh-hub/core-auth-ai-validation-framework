# Segment 150 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 150 payload] --> B{Applicability and Message-Family Decision in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG150-R-001 Segment 150 contains an indication of success or failure of the Segment 149 Fuel Price Update Request}
    R1 -->|Fail| X1[Reject citing SEG150-R-001]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-150-rule-catalog.json](coverage/segment-150-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
