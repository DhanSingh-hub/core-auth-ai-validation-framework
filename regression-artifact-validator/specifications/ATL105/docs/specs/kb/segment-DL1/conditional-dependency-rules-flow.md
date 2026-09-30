# Segment DL1 Conditional Fields and Cross-Field Dependencies Flow

```mermaid
flowchart TD
    A[DL1 payload] --> B[Read Number of Card Types N - Element 59]
    B --> C{N in 01-99?}
    C -->|No| X4a[Fail SEGDL1-R-004]
    C -->|Yes| D[Read exactly N x 3 characters of Card Type]
    D --> E{"Next character is '~'?"}
    E -->|No: more or fewer codes| X4b[Fail SEGDL1-R-004]
    E -->|Yes| F{Any Card Type = 173?}
    F -->|Yes| G{DL6 in the same Table Load Response?}
    G -->|No| X5a[Fail SEGDL1-R-005 missing DL6]
    G -->|Yes| OK[Dependencies satisfied]
    F -->|No| H{DL6 present anyway?}
    H -->|Yes| X5b[Fail SEGDL1-R-005 unexpected DL6]
    H -->|No| OK
```

Source: [segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json) · Note: [conditional-dependency-rules-sme-tba-note.md](conditional-dependency-rules-sme-tba-note.md)
