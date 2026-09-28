# Segment 146 Lifecycle, Response and Message Correlation Flow

```mermaid
flowchart TD
    A[Segment 146 payload] --> B{Lifecycle, Response and Message Correla… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-146-rule-catalog.json](coverage/segment-146-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
