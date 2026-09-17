# Segment 101 Companion-Segment Compatibility Decision Flow

```mermaid
flowchart TD
    A[Receive ATL105 fleet-card request] --> B{Exactly one Segment 100 present?}
    B -->|No| X1[FAIL: Segment 100 cardinality]
    B -->|Yes| C{Segment 101 present?}
    C -->|No| X2[FAIL: SEG101-R-002 fleet requires Segment 101]
    C -->|Yes at least once| D{Segment 101 appears more than once?}
    D -->|Yes| X3[FAIL: SEG101-R-026 at most one Segment 101 per message]
    D -->|No| E{Segment 145 also present?}
    E -->|Yes| X4[FAIL: SEG101-R-003 mutual exclusion with Segment 145]
    E -->|No| F[Decode Prompt Code and POS context]
    F --> G[Identify EMV, fuel/product, EBT, purchase-card, variable-info, NFC, Moneris conditions]
    G --> H[Resolve expected companion segment set]
    H --> I{Combination defined by source?}
    I -->|No| R[REVIEW_REQUIRED: unknown fleet + companion combination]
    I -->|Yes| J[Build expected set: 100 + 101 + applicable companions]
    J --> K[Compare expected set with serialized set]
    K --> L{Required feature companion missing?}
    L -->|Yes| Y1[FAIL: missing feature companion e.g. 102, 130]
    L -->|No| M{Duplicate or unexpected companion segment?}
    M -->|Yes| Y2[FAIL or REVIEW: occurrence/compatibility]
    M -->|No| N[Validate segment order per Section 12 layout]
    N --> O[Count serialized segments]
    O --> P{Element 63 equals count?}
    P -->|No| Y3[FAIL: segment-count mismatch]
    P -->|Yes| Q[Validate Segment 101 fields and length]
    Q --> S[Continue to serialization and lifecycle checks]
```

## Decision Summary

```text
Segment 100 is the baseline for every ATL105 request.
Segment 101 is required for every fleet-card financial transaction.
Segment 145 is mutually exclusive with Segment 101.
Feature companions are condition-driven (102, 130, 103, 104, 111, 123, 135).
Element 63 counts the serialized result.
Unknown combinations require review.
```
