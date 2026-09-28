# Segment DL2 Lifecycle, Response and Message Correlation Flow

```mermaid
flowchart TD
    A[Segment DL2 payload] --> B{Lifecycle, Response and Message Correla… in scope?}
    B -->|No rules in catalog| G[Record gap - confirm with SME - do not invent]
    B -->|Yes| R1{SEGDL2-R-003 Secondary Phone Number block fields 8-11 mirro…}
    R1 -->|Fail| X1[Reject citing SEGDL2-R-003]
    R1 -.->|Provisional| P1[REVIEW_REQUIRED]
    R1 -->|Pass| Z[Rules satisfied]
```

Rules are evaluated in catalog order. Provisional rules are shown with a dotted branch: they are documented but must not be certified as covered until the linked SME item is resolved.

Source: [segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
