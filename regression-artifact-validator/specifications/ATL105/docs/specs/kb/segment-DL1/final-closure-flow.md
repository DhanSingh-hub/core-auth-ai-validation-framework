# Segment DL1 Final Closure Flow

```mermaid
flowchart TD
    A[Structured DL1 values] --> B["Emit '#'"]
    B --> C[Emit fields 2-6 in order, no Field Separators]
    C --> D{Field value shorter than its width?}
    D -->|Yes| R1[Padding rule open - SEGDL1-SME-002 - keep REVIEW_REQUIRED]
    D -->|No| E[Emit Number of Card Types, then N Card Types]
    R1 --> E
    E --> F["Emit '~'"]
    F --> G{Total length <= 399 and no FS/Segment Type/Segment Length?}
    G -->|No| X1[Reject serialization - SEGDL1-R-001 / R-002 / R-006]
    G -->|Yes| H[Place in Table Load Response Data Block 1 after ')']
    H --> I{Block order and End-of-Load correct?}
    I -->|No| X2[Reject message - SEGDL1-R-012]
    I -->|Yes| J{Card Type 173 present?}
    J -->|Yes| K{DL6 present in same response?}
    K -->|No| X3[Reject lifecycle - SEGDL1-R-005]
    K -->|Yes| Z
    J -->|No| Z[Structural and lifecycle closure complete]
    Z --> P{Open provisional items: P-01..P-04}
    P -->|Any open| R[Keep affected rules REVIEW_REQUIRED]
    P -->|None| S[Eligible for sign-off]
```

Serialization rules: `SEGDL1-R-001`, `R-006` · Structure: `R-002`, `R-004`, `R-012` · Lifecycle: `R-005`, `R-008` · Open provisional items: 4
