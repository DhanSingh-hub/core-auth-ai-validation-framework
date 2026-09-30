# Segment DL2 Conditional Fields and Cross-Field Dependencies Flow

```mermaid
flowchart TD
    A[Dial string block after Redial Count] --> B{Access Code needed?}
    B -->|No| C[Next field is Phone Number immediately - no Access Code, no Pause Indicator]
    B -->|Yes| D["Access Code (digits, optional 'B' pauses)"]
    D --> E["Pause Indicator 'B' immediately follows"]
    E --> F[Phone Number]
    C --> F
    F --> G{"Terminator: 'A' for primary, 'F' for secondary"}
    G --> H{Validate pairing}
    H -->|Access Code without Pause Indicator| X1[Fail SEGDL2-R-005]
    H -->|Pause Indicator without Access Code| X2[Fail or REVIEW SEGDL2-R-005 - a lone 'B' is indistinguishable from a 1-character Access Code]
    H -->|Both or neither| OK[Pass]
```

The same decision is made independently for the primary (fields 4-5) and the secondary (fields 9-10) block.

Source: [segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json) · Note: [conditional-dependency-rules-sme-tba-note.md](conditional-dependency-rules-sme-tba-note.md)
