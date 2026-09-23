# Segment 119 End-to-End Flow

```mermaid
flowchart TD
    A[Device requires Totals plus proprietary data load context] --> B[Build Data Section 1]
    B --> C[Message Format Version Identifier + Number of Segments=01]
    C --> D[Omit Data Section 2 / Segment 100]
    D --> E[Place Segment 119 in Data Section 3 Field 3]
    E --> F[Populate fields 1-17 with Field Separators]
    F --> G[Append card bucket fields 18-20 without separators]
    G --> H[Send Totals with Proprietary Data Load Request]
    H --> I[Correlate response and totals lifecycle]
    I --> J[Apply review-gated reconciliation, retry, and cutoff policies]
```

Segment 119 is a totals-specific alternative message path. It is not an ordinary Financial Transaction companion segment: ATL105 Section 11.4.1.2 explicitly says the request contains Data Sections 1 and 3 and does not contain Data Section 2.
