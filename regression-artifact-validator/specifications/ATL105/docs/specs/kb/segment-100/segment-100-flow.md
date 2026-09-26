# Segment 100 End-to-End Flow

This diagram shows the business and technical decision path for a standard ATL105 Financial Transaction Request.

```mermaid
flowchart TD
    A[POS or card event] --> B[Identify transaction intent]
    B --> B1{Purchase, authorization, completion, return, reversal, inquiry, or timeout?}
    B1 --> C[Decode Prompt Code]
    C --> C1[Transaction type]
    C --> C2[Card type or special flow]
    C1 --> D[Build Segment 100 core fields]
    C2 --> D

    D --> D1[Segment Type = 100]
    D --> D2[Terminal Identifier]
    D --> D3[Account or token data]
    D --> D4[Amounts, sequence, approval, date/time]
    D --> D5[Partial Approval Indicator]

    D --> E{Does the flow require Section 3 data?}
    E -->|No| F[Segment set = 100]
    E -->|Yes| G{Which conditions apply?}

    G -->|EMV| G1[Require Segment 130]
    G -->|Fleet| G2[Require Segment 101]
    G -->|Fuel or product| G3[Require Segment 102]
    G -->|EBT| G4[Require Segment 103]
    G -->|Purchase card| G5[Require Segment 104]
    G -->|Variable information| G6[Require Segment 111]
    G -->|NFC tokenization| G7[Require Segment 123]
    G -->|Moneris| G8[Require Segment 135]
    G -->|Unknown condition| G9[Mark combination for review]

    G1 --> H[Combine Segment 100 with all required companions]
    G2 --> H
    G3 --> H
    G4 --> H
    G5 --> H
    G6 --> H
    G7 --> H
    G8 --> H
    G9 --> R[Review before acceptance]

    F --> I[Set Element 63 to actual segment count]
    H --> I
    I --> J[Validate segment identity and order]
    J --> K[Validate field values and conditional fields]
    K --> L[Preserve empty non-trailing separators]
    L --> M[Omit unneeded trailing optional fields]
    M --> N[Calculate Segment Length]
    N --> O[Calculate TCP/IP Message Length]
    O --> P{Validation result}

    P -->|Valid| Q[Send request / continue lifecycle]
    P -->|Invalid| S[Reject with deterministic error]
    Q --> T{Lifecycle follow-up?}
    T -->|Completion| U[Reuse required original correlation data]
    T -->|Reversal or void| V[Reference original transaction]
    T -->|No| W[Await response and reconcile]
```

## How to Read the Flow

- The left side is POS/card business analysis.
- The middle is message construction.
- The right side is serialization, validation, and lifecycle behavior.
- The `Unknown condition` path is intentionally not an automatic pass or fail; it requires specification or business review.
- Element 63 counts the final segment set, not merely the number of business conditions.
