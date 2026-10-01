# Segment DL8 Applicability and Message-Family Decision Flow

```mermaid
flowchart TD
    A[Host is building a table load] --> B{Terminal has the EMV floor-limit Special?}
    B -->|No| N1[DL8 must NOT be included - SEGDL8-R-001]
    B -->|Yes| C[DL8 included in the table load]
    C --> D{Position in the Table Load Response?}
    D --> R1[REVIEW_REQUIRED - not in 11.7.1.2 layout, SEGDL8-SME-002]
    A --> E{Request or non-table-load response?}
    E -->|Yes| X1[DL8 not allowed]
```

| Evidence | What it says |
|---|---|
| 12.49 | "Inclusion of this segment in a table load will be dependent on a Special set at the terminal level" |
| Element 24 | `%` identifies DL8 in a host response |
| 11.7.1.2 | Table Load Response layout lists DL1, DL2, DL3, DL6 only |
| Chapter 12 matrix | DL1-DL6 only |

Source: [segment-DL8-rule-catalog.json](coverage/segment-DL8-rule-catalog.json) · Note: [applicability-decision-sme-tba-note.md](applicability-decision-sme-tba-note.md)
