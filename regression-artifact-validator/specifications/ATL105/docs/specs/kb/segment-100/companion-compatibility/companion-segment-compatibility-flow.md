# Companion-Segment Compatibility Decision Flow

```mermaid
flowchart TD
    A[Receive ATL105 request] --> B[Identify standard financial scope]
    B --> C{Exactly one Segment 100?}
    C -->|No| X[FAIL: Segment 100 cardinality]
    C -->|Yes| D[Decode Prompt Code and POS context]
    D --> E[Identify EMV, NFC, fleet, fuel, EBT, purchase-card, variable-info, and Moneris conditions]
    E --> F[Resolve expected companion segment set]
    F --> G{Combination defined by source?}
    G -->|No| R[REVIEW_REQUIRED: unknown combination]
    G -->|Yes| H[Build expected set: 100 plus applicable companions]
    H --> I[Compare expected set with serialized set]
    I --> J{Required segment missing?}
    J -->|Yes| Y[FAIL: missing companion]
    J -->|No| K{Duplicate or unexpected segment?}
    K -->|Yes| Z[FAIL or REVIEW: occurrence/compatibility]
    K -->|No| L[Validate segment order]
    L --> M[Count serialized segments]
    M --> N{Element 63 equals count?}
    N -->|No| W[FAIL: segment-count mismatch]
    N -->|Yes| P[Validate segment lengths and fields]
    P --> Q[Continue to serialization and lifecycle checks]
```

## Decision Summary

```text
Segment 100 is the baseline.
Companion segments are condition-driven.
Element 63 counts the serialized result.
Unknown combinations require review.
```
