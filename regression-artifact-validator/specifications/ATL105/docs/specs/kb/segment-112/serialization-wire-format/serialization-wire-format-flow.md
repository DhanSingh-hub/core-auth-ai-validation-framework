# Segment 112 Serialization and Wire-Format Flow

```mermaid
flowchart TD
    A[Host selects Additional Information triads] --> B[For each triad: encode Indicator - 3 digits]
    B --> C[Encode Length - 3 digits, must equal actual Value byte length]
    C --> D[Encode Value - variable, or exact fixed length for codes 012/013/017/018]
    D --> E{More triads?}
    E -->|Yes| B
    E -->|No| F[Sum all triad byte lengths]

    F --> G{Combined triad length <= 990 bytes?}
    G -->|No| H[REVIEW_REQUIRED - no documented overflow behavior]
    G -->|Yes| I[Prepend Segment Type = 112 and Segment Length]

    I --> J[Calculate Segment Length = encoded Segment 112 content length]
    J --> K{Segment 112 total <= 999 alphanumeric characters?}
    K -->|No| H
    K -->|Yes| L[Append Segment 112 as the final segment of the Financial Transaction Response]

    L --> M[Set Element 115 = 1 in the Financial Transaction Response]
    M --> N{Element 115 and Segment 112 presence agree?}
    N -->|No| O[Reject with deterministic error]
    N -->|Yes| P[Deliver response to device]
```

## How to Read the Flow

- Encoding happens per-triad first (Indicator/Length/Value), then the triads are summed and bounded (990 bytes), then the whole segment is bounded (999 bytes) — three independent checks, not one.
- Fixed-length codes must match their documented exact length; do not treat their Element 117 as "any value up to the max."
- The final placement and Element 115 consistency check happens after the segment itself is valid — a syntactically perfect Segment 112 in the wrong position, or without a matching Element 115, is still a defect.
