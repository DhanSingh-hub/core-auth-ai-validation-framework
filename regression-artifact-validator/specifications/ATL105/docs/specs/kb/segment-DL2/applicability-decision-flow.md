# Segment DL2 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Host is building a download response] --> B{Which response?}
    B -->|Software Load / Date and Time Load| N1[DL2 must NOT be present - SEGDL2-R-008]
    B -->|Phone Load| C{Load flag PHON?}
    C -->|No| N2[Error block + terminating block; DL2 absent - SEGDL2-R-009]
    C -->|Yes| D[DL2 Required]
    D --> D1{"Leading ')' or '!'?"}
    D1 --> R1[REVIEW_REQUIRED - SEGDL2-SME-003]
    B -->|Table Load| E{Load flag TABL?}
    E -->|No| N3[No DL1/DL2]
    E -->|Yes| F{Dial strings configured for this load?}
    F -->|Yes| G[DL2 as Data Block 2 after DL1]
    F -->|No| H[DL2 omitted - allowed, Conditional]
```

| Message | DL2 | Evidence |
|---|---|---|
| Table Load Response | **C**, Data Block 2 | 11.7.1.2 field 4 |
| Phone Load Response | **R**, only segment | 11.7.2.2 field 2 |
| Date and Time / Software Load Response | Not allowed | 11.7.3.2, 11.7.4.2 layouts |
| Any request | Not allowed | DL2 originates at BUYPASS |

Source: [segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
