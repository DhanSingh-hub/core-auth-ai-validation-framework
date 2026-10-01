# Segment 151 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Segment 151 payload] --> B{Applicability and Message-Family Decision in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG151-R-001 Segment 151 can appear in ANY field slot of Data Section No. 3 (not a fixed position), originates at the device}
    R1 -->|Fail| X1[Reject citing SEG151-R-001]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-151-rule-catalog.json](coverage/segment-151-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
