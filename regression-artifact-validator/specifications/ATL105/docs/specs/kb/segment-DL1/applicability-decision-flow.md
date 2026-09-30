# Segment DL1 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Host is building a download response] --> B{Which load?}
    B -->|Phone Load / Date and Time Load / Software Load| N1[DL1 must NOT be present - SEGDL1-R-007]
    B -->|Table Load| C{Merchant load flag = TABL?}
    C -->|No| N2[Error message block + terminating block only; DL1 absent - SEGDL1-R-008]
    C -->|Yes| D["Emit ')' Start-of-Data Block Indicator"]
    D --> E[Emit DL1 as Data Block 1 - Required]
    E --> F{DL1 present and first block?}
    F -->|No| X1[Fail SEGDL1-R-007]
    F -->|Yes| G[Continue with DL2 / DL3 / End-of-Load / DL6 decisions - SEGDL1-R-012]
```

| Message | DL1 | Evidence |
|---|---|---|
| Table Load Response (11.7.1.2) | **R**, Data Block 1 | Layout field 2; Chapter 12 matrix |
| Phone Load Response (11.7.2.2) | Not allowed | Layout lists DL2 only |
| Date and Time Load Response (11.7.3.2) | Not allowed | Layout lists DL3 fields only |
| Software Load Response (11.7.4.2) | Not allowed | Layout lists DL4 and DL5 only |
| Any request message | Not allowed | DL1 originates at BUYPASS |

Source: [segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
