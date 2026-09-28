# Segment DL8 Lifecycle, Response and Message Correlation Flow

```mermaid
flowchart TD
    A[Segment DL8 payload] --> B{Lifecycle, Response and Message Correla… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEGDL8-R-001 Segment DL8 contains EMV floor limits as RID f…}
    R1 -->|Fail| X1[Reject citing SEGDL8-R-001]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
