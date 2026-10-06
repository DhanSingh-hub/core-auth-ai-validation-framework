# Segment DL7 Coverage Closure Flow

```mermaid
flowchart TD
    A[ATL105 12.48 / 13.2 / Appendix W] --> B[DL7 rule catalog - 6 rules]
    B --> C[Canonical source anchor per rule]
    C --> D{BR carries anchor?}
    D -->|No| M[MISSING]
    D -->|Yes| E{Scenario links BR?}
    E -->|No| P[PARTIALLY_COVERED]
    E -->|Yes| F{Test case links scenario and has Given/When/Then?}
    F -->|No| P
    F -->|Yes| G{Test data holds serialized DL7 and structured entries?}
    G -->|No| P
    G -->|Yes| H{Payload proves the behaviour?}
    H -->|No| S[REJECTED_SEMANTIC_MISMATCH]
    H -->|Yes| I{Rule linked to open SME item?}
    I -->|Yes| R[REVIEW_REQUIRED]
    I -->|No| J[COVERED]
```

All six DL7 rules are currently linked to an open SME item and therefore `REVIEW_REQUIRED`.
