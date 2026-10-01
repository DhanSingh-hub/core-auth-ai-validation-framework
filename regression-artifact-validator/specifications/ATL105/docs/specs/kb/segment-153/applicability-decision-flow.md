# Segment 153 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 153 payload] --> B{Applicability and Message-Family Decision in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{"SEG153-R-001 Segment 153 contains Network Token Data for the transaction; may hold multiple TLV-encoded sub-segments (standard sub-segment type, length, value)"}
    R1 -->|Fail| X1[Reject citing SEG153-R-001]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-153-rule-catalog.json](coverage/segment-153-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
