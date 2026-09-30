# Segment 145 Lifecycle, Response and Message Correlation Flow

```mermaid
flowchart TD
    A[Segment 145 payload] --> B{Lifecycle, Response and Message Correla… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG145-R-004 MasterCard Enhanced Fleet EMV functionality wa…}
    R1 -->|Fail| X1[Reject citing SEG145-R-004]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-145-rule-catalog.json](coverage/segment-145-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
