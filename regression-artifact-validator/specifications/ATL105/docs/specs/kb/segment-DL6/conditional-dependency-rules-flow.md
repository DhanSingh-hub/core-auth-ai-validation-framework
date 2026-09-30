# Segment DL6 Conditional Fields and Cross-Field Dependencies Flow

```mermaid
flowchart TD
    A[Table Load Response] --> B{DL1 Card Type 173?}
    B -->|Yes| C{DL6 present?}
    C -->|No| X1[Fail SEGDL6-R-001 / SEGDL1-R-005]
    C -->|Yes| D[Read Start Time and End Time]
    B -->|No| E{DL6 present?}
    E -->|Yes| X2[Fail SEGDL6-R-001]
    E -->|No| OK1[Pass - no DL6 expected]
    D --> F{Start < End?}
    F -->|Yes| G[Same-day window]
    F -->|Start > End| R1[Window crosses midnight? - SEGDL6-SME-005]
    F -->|Start = End| R2[Meaning unknown - SEGDL6-SME-005]
    G --> OK2[Dependencies satisfied]
```

Source: [segment-DL6-rule-catalog.json](coverage/segment-DL6-rule-catalog.json) · Note: [conditional-dependency-rules-sme-tba-note.md](conditional-dependency-rules-sme-tba-note.md)
