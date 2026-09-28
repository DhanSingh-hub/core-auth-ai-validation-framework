# Segment 131 Lifecycle, Response and Message Correlation Flow

```mermaid
flowchart TD
    A[Segment 131 payload] --> B{Lifecycle, Response and Message Correla… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG131-R-007 CA Public Key File Checksum Element 187 is req…}
    R1 -->|Fail| X1[Reject citing SEG131-R-007]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-131-rule-catalog.json](coverage/segment-131-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
