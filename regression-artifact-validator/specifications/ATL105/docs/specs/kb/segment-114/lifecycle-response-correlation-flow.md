# Segment 114 Lifecycle, Response and Message Correlation Flow

```mermaid
flowchart TD
    A[Segment 114 payload] --> B{Lifecycle, Response and Message Correla… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG114-R-013 Segment 114 does not appear in the Loyalty Car…}
    R1 -->|Fail| X1[Reject citing SEG114-R-013]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-114-rule-catalog.json](coverage/segment-114-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
