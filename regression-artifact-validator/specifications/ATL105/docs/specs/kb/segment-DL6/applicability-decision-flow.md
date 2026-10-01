# Segment DL6 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Host is building a download response] --> B{Table Load Response?}
    B -->|No| N1[DL6 must NOT be present - SEGDL6-R-001]
    B -->|Yes| C{DL1 Card Type list contains 173?}
    C -->|No| N2[DL6 must NOT be present - SEGDL6-R-001]
    C -->|Yes| D[DL6 Required as Data Block 4]
    D --> E["Position: after '*' closing block 3, followed by '*' - SEGDL6-R-006"]
```

| Message | DL6 | Evidence |
|---|---|---|
| Table Load Response with DL1 `173` | **Required** (layout says C, condition = `173`) | 11.7.1.2 field 8; 12.47 note |
| Table Load Response without `173` | Not allowed | 12.47 note ("only sent … when") |
| Any other message | Not allowed | Chapter 12 matrix |

Source: [segment-DL6-rule-catalog.json](coverage/segment-DL6-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
