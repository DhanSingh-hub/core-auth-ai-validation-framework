# Segment DL5 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Host is building a download response] --> B{Device application managed by BUYPASS device management system?}
    B -->|No| N1[DL5 must NOT be sent - SEGDL5-R-001]
    B -->|Yes| C{Which response?}
    C -->|Software Load Response| D{Load flag SOFT?}
    D -->|No| N2[Error block + terminating block]
    D -->|Yes| E["')' DL4 then DL5 - both Required"]
    C -->|Table Load Response| R1[REVIEW_REQUIRED - SEGDL4-SME-002]
    C -->|Phone / Date and Time Load| N3[DL5 must NOT be present]
```

| Message | DL5 | Evidence |
|---|---|---|
| Software Load Response | **R**, field 3 after DL4 | 11.7.4.2; Chapter 12 matrix |
| Table Load Response | Conflict | 10.10 step 4 (`SEGDL4-SME-002`) |
| Others | Not allowed | 11.7.1.2, 11.7.2.2, 11.7.3.2 |

Source: [segment-DL5-rule-catalog.json](coverage/segment-DL5-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
