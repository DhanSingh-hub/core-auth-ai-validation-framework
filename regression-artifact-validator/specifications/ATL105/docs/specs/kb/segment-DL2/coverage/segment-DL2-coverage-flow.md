# Segment DL2 Coverage Closure Flow

```mermaid
flowchart TD
    A[ATL105 12.43 / 11.7.2 / 13.2] --> B[DL2 rule catalog - 9 rules]
    B --> C[Canonical source anchor per rule]
    C --> D{BR carries anchor?}
    D -->|No| M[MISSING]
    D -->|Yes| E{Scenario links BR?}
    E -->|No| P[PARTIALLY_COVERED]
    E -->|Yes| F{Test case links scenario and has Given/When/Then?}
    F -->|No| P
    F -->|Yes| G{Test data holds the load request and response?}
    G -->|No| P
    G -->|Yes| H{Payload round-trips and proves the behaviour?}
    H -->|No| S[REJECTED_SEMANTIC_MISMATCH]
    H -->|Yes| I{Rule linked to open SEGDL2-SME item?}
    I -->|Yes| R[REVIEW_REQUIRED]
    I -->|No| J[COVERED]
```

Rules currently `REVIEW_REQUIRED` by design: `SEGDL2-R-003` (P-01), `R-008` (P-03), `R-004`, `R-005`, `R-007` (P-04).
