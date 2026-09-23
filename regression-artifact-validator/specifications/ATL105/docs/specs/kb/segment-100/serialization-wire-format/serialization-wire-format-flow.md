# Segment 100 Serialization and Wire-Format Flow

```mermaid
flowchart TD
    A[Business transaction intent] --> B[Structured test-data JSON]
    B --> C[Validate JSON schema and canonical fields]
    C --> D{Values and applicability valid?}
    D -->|No| X[FAIL: logical test-data defect]
    D -->|Yes| E[Resolve companion segment set]
    E --> F[Order Data Section 1 and segments]
    F --> G[Encode Segment 100 fields in fixed order]
    G --> H[Preserve empty middle separators]
    H --> I[Omit allowed trailing optional suffix]
    I --> J[Encode binary, hex, or TLV fields]
    J --> K[Calculate segment lengths]
    K --> L[Calculate Element 63 from serialized segments]
    L --> M[Build TCP/IP framing and message length]
    M --> N{Wire-format checks pass?}
    N -->|No| Y[FAIL or REVIEW: serialization defect]
    N -->|Yes| O[ATL105 message ready for converter/transport]
    O --> P[Validate lifecycle and expected outcome]
```

## Layered Decision

```text
Business meaning
  -> logical JSON
  -> serialized Segment 100
  -> complete ATL105 payload
  -> TCP/IP frame
  -> executable test input
```

A failure at any layer must identify the representation, field/segment, rule, and source anchor involved.
