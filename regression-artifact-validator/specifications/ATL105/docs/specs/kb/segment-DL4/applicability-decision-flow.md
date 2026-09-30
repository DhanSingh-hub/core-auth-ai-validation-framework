# Segment DL4 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Host is building a download response] --> B{Device application managed by BUYPASS device management system?}
    B -->|No - vendor-managed| N1[DL4 must NOT be sent - SEGDL4-R-001]
    B -->|Yes| C{Which response?}
    C -->|Software Load Response| D{Merchant load flag SOFT?}
    D -->|No| N2[Error block + terminating block; no DL4 - SEGDL4-R-006]
    D -->|Yes| E["')' + DL4 + DL5 - both Required"]
    C -->|Table Load Response| F[10.10 step 4 says DL4/DL5 answer a Table Load request]
    F --> R1[REVIEW_REQUIRED - SEGDL4-SME-002]
    C -->|Phone / Date and Time Load| N3[DL4 must NOT be present]
```

| Message | DL4 | Evidence |
|---|---|---|
| Software Load Response | **R**, with DL5 | 11.7.4.2 field 2; Chapter 12 matrix |
| Table Load Response | Conflict | 10.10 step 4 vs 11.7.1.2 layout (no DL4) |
| Phone / Date and Time Load Response | Not allowed | 11.7.2.2, 11.7.3.2 |

Source: [segment-DL4-rule-catalog.json](coverage/segment-DL4-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
