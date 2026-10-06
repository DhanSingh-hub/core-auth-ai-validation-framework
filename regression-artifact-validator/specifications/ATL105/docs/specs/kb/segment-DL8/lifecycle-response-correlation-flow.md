# Segment DL8 Lifecycle, Response and Message Correlation Flow

```mermaid
sequenceDiagram
    participant Ops as BUYPASS floor-limit maintenance
    participant H as BUYPASS Host
    participant D as Terminal with Special
    Ops->>H: Change EMV floor-limit data
    H->>H: Set table download flag for every terminal with the Special
    D->>H: Next transaction
    H-->>D: Response with Download Indicator = 1 (per 11.7)
    D->>H: Table Load Request
    H-->>D: Table load including DL8 (position - SEGDL8-SME-002)
    Note over D: Store per-RID stand-in rule and floor limit
```

Rules: `SEGDL8-R-001`. The Download Indicator step is inferred from 11.7 and is `REVIEW_REQUIRED` for DL8.

Source: [segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
