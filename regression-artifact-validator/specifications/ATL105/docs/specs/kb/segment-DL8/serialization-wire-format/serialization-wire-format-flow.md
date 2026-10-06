# Segment DL8 Serialization and Wire-Format Flow

```mermaid
flowchart TD
    A[Business intent: set EMV floor limits per RID] --> B[Structured DL8 groups]
    B --> C{1-24 groups; RID 10, Stand-in 1-3, Floor Limit 12 digits, Card Type 3?}
    C -->|No| X1[FAIL: logical test-data defect]
    C -->|Yes| D[Concatenate 26-byte groups]
    D --> E["Emit '%' + Segment Length (3) + groups"]
    E --> F[No End-of-Data Indicator]
```

Synthetic example, two groups (Segment Length counting group data only — one reading of `SEGDL7-SME-005`), 56 characters:

```text
%052A0000000033000000005000020A0000000042000000002500020
```

| Group | RID | Stand-in | Floor Limit | Card Type |
|---|---|---|---|---|
| 1 | `A000000003` | `3` | `000000005000` | `020` |
| 2 | `A000000004` | `2` | `000000002500` | `020` |
