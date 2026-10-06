# Segment DL3 Conditional Fields and Cross-Field Dependencies Flow

```mermaid
flowchart TD
    A[DL3 payload] --> B[All seven fields are Required - no C fields]
    B --> C{Day of the Week agrees with Current Date?}
    C -->|No| R1[REVIEW_REQUIRED - derived check, not a stated rule]
    C -->|Yes| D{Current Date is a real calendar date?}
    D -->|No| X1[Fail SEGDL3-R-005]
    D -->|Yes| E{Device uses automatic cut time?}
    E -->|Yes| F[Settlement expected 30 minutes before Cut Time if not done - SEGDL3-R-009]
    E -->|No| G[No automatic settlement]
    F --> H{DL1 Card Type 164 Auto Close also enabled?}
    H -->|Yes| I[Batch closes 30 minutes before default cut time - Appendix E]
    H -->|No| OK[Dependencies recorded]
    G --> OK
    I --> OK
```

Source: [segment-DL3-rule-catalog.json](coverage/segment-DL3-rule-catalog.json) · Note: [conditional-dependency-rules-sme-tba-note.md](conditional-dependency-rules-sme-tba-note.md)
