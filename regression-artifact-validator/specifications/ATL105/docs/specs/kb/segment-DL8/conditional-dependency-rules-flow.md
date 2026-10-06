# Segment DL8 Conditional Fields and Cross-Field Dependencies Flow

```mermaid
flowchart TD
    A[DL8 payload] --> B[Segment Length L]
    B --> C{"Content after the Segment Length = N x 26 bytes, N in 1-24?"}
    C -->|No| X1[Fail SEGDL8-R-003 / R-005]
    C -->|Yes| D[For each group]
    D --> E{Stand-in Indicator = 1?}
    E -->|Yes| F{Floor Limit = 000000000000?}
    F -->|No| R1[REVIEW_REQUIRED - SEGDL8-SME-003]
    F -->|Yes| G[Consistent]
    E -->|No| G
    G --> H{Same RID repeated in another group?}
    H -->|Yes| R2[REVIEW_REQUIRED - duplicate RID not addressed by source]
    H -->|No| OK[Dependencies satisfied]
```

Source: [segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json) · Note: [conditional-dependency-rules-sme-tba-note.md](conditional-dependency-rules-sme-tba-note.md)
