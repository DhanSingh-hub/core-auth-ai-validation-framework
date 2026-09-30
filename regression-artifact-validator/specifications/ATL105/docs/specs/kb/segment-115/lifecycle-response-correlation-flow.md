# Segment 115 Lifecycle, Response and Message Correlation Flow

```mermaid
flowchart TD
    A[Segment 115 payload] --> B{Lifecycle, Response and Message Correla… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEG115-R-012 Segment 115 is plausibly the wire-format vehic…}
    R1 -->|Fail| X1[Reject citing SEG115-R-012]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| R2{SEG115-R-013 Whether Segment 115 can appear in the Loyalty…}
    R2 -->|Fail| X2[Reject citing SEG115-R-013]
    R2 -.->|Provisional| P2[REVIEW_REQUIRED]
    R2 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-115-rule-catalog.json](coverage/segment-115-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
