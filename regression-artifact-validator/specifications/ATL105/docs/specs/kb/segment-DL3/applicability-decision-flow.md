# Segment DL3 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Host is building a download response] --> B{Which response?}
    B -->|Phone Load / Software Load| N1[DL3 must NOT be present - SEGDL3-R-007]
    B -->|Date and Time Load| C{Request Load Type = 'D'?}
    C -->|No| N2[Not a Date and Time Load - SEGDL3-R-008]
    C -->|Yes| D[Return DL3 fields - no load flag required]
    D --> D1{'~' at the end?}
    D1 --> R1[REVIEW_REQUIRED - SEGDL3-SME-003]
    B -->|Table Load| E{Load flag TABL?}
    E -->|No| N3[No Table Load]
    E -->|Yes| F{Date/time data included in this load?}
    F -->|Yes| G[DL3 as Data Block 3 after DL1 / DL2]
    F -->|No| H[DL3 omitted - allowed, Conditional]
```

| Message | DL3 | Evidence |
|---|---|---|
| Table Load Response | **C**, Data Block 3 | 11.7.1.2 field 6 |
| Date and Time Load Response | Its fields form the response | 11.7.3.2; Chapter 12 matrix |
| Phone / Software Load Response | Not allowed | 11.7.2.2, 11.7.4.2 |

Source: [segment-DL3-rule-catalog.json](coverage/segment-DL3-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
