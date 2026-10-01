# Segment DL8 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[DL8 payload] --> F1["Field 1 '%' - Element 24"]
    F1 --> F2[Field 2 Segment Length - Element 84, N3, excludes '%']
    F2 --> G[Floor Limit Data group - repeat 1..24]
    G --> F3[Field 3 RID - Element 233, AN 10, EMV RID]
    F3 --> F4[Field 4 Stand-in Indicator - Element 234, N1: 1, 2, 3]
    F4 --> F5[Field 5 Floor Limit - Element 235, N12, 000000000000-999999999999]
    F5 --> F6[Field 6 BUYPASS RID Card Type - Element 236, AN 3]
    F6 --> Q{Group valid?}
    Q -->|Stand-in not 1-3| X4a[Fail SEGDL8-R-004]
    Q -->|Floor Limit non-numeric or not 12| X4b[Fail SEGDL8-R-004]
    Q -->|RID not 10 characters| X4c[Fail SEGDL8-R-004]
    Q -->|RID/Card Type content unknown| R3[REVIEW_REQUIRED - SEGDL8-SME-003]
    Q -->|Valid| N{More groups?}
    N -->|Yes| G
    N -->|No| OK[Fields valid]
```

Source: [segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
