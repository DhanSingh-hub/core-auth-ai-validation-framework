# Segment DL6 Field Definitions and Element Semantics Flow

```mermaid
flowchart TD
    A[DL6 payload] --> F1["Field 1 '\' - Element 24"]
    F1 --> F2[Field 2 Start Time - Element 166, N4 HHMM 0000-2359]
    F2 --> F3[Field 3 End Time - Element 166, N4 HHMM 0000-2359]
    F3 --> F4["Field 4 '~' - Element 34"]
    F4 --> Q{Each field within its definition?}
    Q -->|Hour > 23 or minute > 59| X5[Fail SEGDL6-R-005]
    Q -->|Non-digit| X5b[Fail SEGDL6-R-005]
    Q -->|Element number reuse question| R1[REVIEW_REQUIRED - SEGDL6-SME-001]
    Q -->|Valid| OK[Fields valid]
```

Source: [segment-DL6-rule-catalog.json](coverage/segment-DL6-rule-catalog.json) · Note: [field-definitions-sme-tba-note.md](field-definitions-sme-tba-note.md)
