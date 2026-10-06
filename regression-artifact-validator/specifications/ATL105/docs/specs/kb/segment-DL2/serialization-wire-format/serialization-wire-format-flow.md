# Segment DL2 Serialization and Wire-Format Flow

```mermaid
flowchart TD
    A[Business intent: configure dialing] --> B[Structured DL2 JSON: primary + secondary]
    B --> C{Redial 1-3, Phone <=18 digits, Access Code/Pause paired?}
    C -->|No| X1[FAIL: logical test-data defect]
    C -->|Yes| D["Emit '!' '1'"]
    D --> E["Primary: Redial [AccessCode 'B'] Phone 'A'"]
    E --> F["Secondary: Redial [AccessCode 'B'] Phone 'F'"]
    F --> G["Emit '~'"]
    G --> H{Length <= 69?}
    H -->|No| X2[FAIL SEGDL2-R-001]
    H -->|Yes| I[Phone Load Response or Table Load Data Block 2]
```

Synthetic example, 29 characters (primary with access code `9`, secondary without):

```text
!139B5555550100A25555550199F~
```

Maximum-width shape, 69 characters: `!` `1` + (1 + 12 + 1 + 18 + `A`) + (1 + 12 + 1 + 18 + `F`) + `~`.
