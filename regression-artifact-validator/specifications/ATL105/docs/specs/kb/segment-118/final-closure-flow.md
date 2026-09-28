# Segment 118 Final Closure Flow

```mermaid
flowchart TD
    A[Build ordered Segment 118 fields] --> B{Field empty?}
    B -->|Middle field| C[Keep field separator]
    B -->|Trailing field| D[No omission allowance catalogued - keep position]
    C --> F
    D --> F
    B -->|No| F[Serialize value]
    F --> G{Segment Length is 4 digits and correct?}
    G -->|No| X1[Reject serialization]
    G -->|Yes| H[Validate repeating-section boundaries]
    H --> I
    I{Lifecycle or response rules catalogued?}
    I -->|No| Z[Structural closure complete]
    I -->|Yes| J[Load paired messages]
    J --> K{Correlated values consistent?}
    K -->|No| X2[Reject lifecycle mismatch]
    K -->|Yes| Z
    Z --> P{Open provisional items: 4}
    P -->|Any open| R[Keep affected rules REVIEW_REQUIRED]
    P -->|None| S[Eligible for sign-off]
```

Serialization rules: 3 · Lifecycle/response rules: 3 · Open provisional items: 4
