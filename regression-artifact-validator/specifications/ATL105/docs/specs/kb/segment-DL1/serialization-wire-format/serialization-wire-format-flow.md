# Segment DL1 Serialization and Wire-Format Flow

```mermaid
flowchart TD
    A[Business intent: configure merchant device] --> B[Structured DL1 JSON]
    B --> C{All nine fields present and valid?}
    C -->|No| X1[FAIL: logical test-data defect]
    C -->|Yes| D["Emit '#'"]
    D --> E[Concatenate fields 2-7 at element widths - no Field Separators]
    E --> F[Append N x 3-character Card Types]
    F --> G["Append '~'"]
    G --> H{Length <= 399?}
    H -->|No| X2[FAIL SEGDL1-R-001]
    H -->|Yes| I["Prefix ')' as Data Block 1 of Table Load Response"]
    I --> J[Append DL2 / DL3 blocks if configured, then '*']
    J --> K[Append DL6 + '*' when Card Type 173]
    K --> L[Frame for dial or TCP/IP transport]
```

## Layered Decision

```text
Merchant profile -> logical DL1 JSON -> serialized DL1 -> Table Load Response -> transport frame
```

Synthetic example (assuming full-width padding, `SEGDL1-SME-002`), 111 characters:

```text
#SAMPLE MERCHANT STORE   0000000000001234100 TEST STREET         SPRINGFIELD  IL 62701(555)555-010003020011173~
```
