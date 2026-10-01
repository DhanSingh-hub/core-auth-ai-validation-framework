# Segment DL5 Conditional Fields and Cross-Field Dependencies Flow

```mermaid
flowchart TD
    A[DL5 payload] --> B[All eight fields Required - no C fields]
    B --> C{Device BUYPASS-managed?}
    C -->|No| X1[Fail SEGDL5-R-001]
    C -->|Yes| D{DL4 precedes DL5 in the same response?}
    D -->|No| X2[Fail or REVIEW SEGDL5-R-006]
    D -->|Yes| E{DL4 and DL5 share Version, Record ID, Date, Time, Load Type?}
    E -->|No| R1[REVIEW_REQUIRED - derived consistency check]
    E -->|Yes| F{Device connects by IP?}
    F -->|Yes| G[Device uses the DL5 address at Request Date/Time]
    F -->|No| H[Device uses the DL4 phone number - choice rule in SEGDL4-SME-002]
    G --> OK[Dependencies recorded]
    H --> OK
```

Source: [segment-DL5-rule-catalog.json](coverage/segment-DL5-rule-catalog.json) · Note: [conditional-dependency-rules-sme-tba-note.md](conditional-dependency-rules-sme-tba-note.md)
