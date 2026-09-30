# Segment DL7 Conditional Fields and Cross-Field Dependencies Flow

```mermaid
flowchart TD
    A[DL7 payload] --> B[Segment Length L]
    B --> C{L equals length of content after '^'?}
    C -->|No| X1[Fail SEGDL7-R-002 / R-006]
    C -->|Yes| D[For each entry: Table Length T]
    D --> E{Table Data length equals T?}
    E -->|No| X2[Fail SEGDL7-R-004]
    E -->|Yes| F{Sum of entries = Download Data length?}
    F -->|No| X3[Fail SEGDL7-R-003 - trailing or truncated entry]
    F -->|Yes| G{Download Data <= 100?}
    G -->|No| R1[Split across DL7s? - SEGDL7-SME-004]
    G -->|Yes| OK[Dependencies satisfied]
```

Source: [segment-DL7-rule-catalog.json](coverage/segment-DL7-rule-catalog.json) · Note: [conditional-dependency-rules-sme-tba-note.md](conditional-dependency-rules-sme-tba-note.md)
