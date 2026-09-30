# Segment DL4 Conditional Fields and Cross-Field Dependencies Flow

```mermaid
flowchart TD
    A[DL4 payload] --> B[All eight fields Required - no C fields]
    B --> C{Device BUYPASS-managed?}
    C -->|No| X1[Fail SEGDL4-R-001]
    C -->|Yes| D{DL5 in the same response?}
    D -->|No| X2[Fail or REVIEW SEGDL4-R-006]
    D -->|Yes| E{DL4 and DL5 share Version, Record ID, Date, Time, Load Type?}
    E -->|No| R1[REVIEW_REQUIRED - derived consistency check]
    E -->|Yes| F{Request Date/Time in the future relative to device clock?}
    F -->|No| R2[REVIEW_REQUIRED - source does not say]
    F -->|Yes| OK[Dependencies satisfied]
```

Source: [segment-DL4-rule-catalog.json](coverage/segment-DL4-rule-catalog.json) · Note: [conditional-dependency-rules-sme-tba-note.md](conditional-dependency-rules-sme-tba-note.md)
