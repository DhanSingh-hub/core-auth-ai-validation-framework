# Segment DL8 Companion-Segment Compatibility Decision Flow

```mermaid
flowchart TD
    A[Table load for a terminal] --> B{Terminal has the Special?}
    B -->|No| C{DL8 present?}
    C -->|Yes| X1[Fail SEGDL8-R-001]
    C -->|No| OK1[Compatible]
    B -->|Yes| D{Exactly one DL8?}
    D -->|No| R1[REVIEW_REQUIRED - missing or duplicate DL8]
    D -->|Yes| E{Position relative to DL1-DL6 and '*'}
    E --> R2[REVIEW_REQUIRED - SEGDL8-SME-002]
```

DL8 has no trigger from another segment's value. It shares its framing with DL7 and relates semantically to EMV transactions (Segment 130), which it does not accompany in any message.
