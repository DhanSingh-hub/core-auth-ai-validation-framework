# Segment 149 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 149 payload] --> B{Applicability and Message-Family Decision in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG149-R-001 Segment 149 contains data required by the Comdata authorizer for fuel price updates}
    R1 -->|Fail| X1[Reject citing SEG149-R-001]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-149-rule-catalog.json](coverage/segment-149-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
