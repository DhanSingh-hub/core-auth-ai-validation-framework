# Segment 102 End-to-End Flow

This diagram shows the business and technical decision path for building and validating the Product Code Data Segment within an ATL105 Financial Transaction Request.

```mermaid
flowchart TD
    A[POS or pump event] --> B[Identify products sold]
    B --> C{Does the flow require itemized product data?}
    C -->|No| Z[Segment 102 omitted]
    C -->|Yes| D[Build Segment 102]

    D --> D1[Segment Type = 102]
    D --> D2[Service Level]
    D --> D3[Number of Products]
    D --> D4[Product entries: Code, Unit of Measure, Quantity, Unit Price, Amount]

    D4 --> E{Is a fuel or EV product present?}
    E -->|Yes| E1[Place fuel/EV product first]
    E -->|No| E2[Order is not fuel-constrained]
    E1 --> F
    E2 --> F[Assign unique Product Code per fuel type]

    F --> G{Segment 157 also being sent?}
    G -->|Yes| G1[Reject: 102 and 157 are mutually exclusive]
    G -->|No| H{Card type is Comdata?}
    H -->|Yes| H1[Reject: Segment 102 not valid for Comdata]
    H -->|No| I[Sum Product Amounts]

    I --> J{Sum equals Segment 100 Fuel + Nonfuel + Tax + Cash amounts?}
    J -->|No| J1[Reject: amount reconciliation failure]
    J -->|Yes| K{Tax-coded products present?}
    K -->|Yes| K1{Segment 100 Tax Amount reflects tax product total?}
    K1 -->|No| K2[Reject: tax reconciliation failure]
    K1 -->|Yes| L
    K -->|No| L[Apply serialization rules]

    L --> L1[Field Separator after Segment Type/Length]
    L1 --> L2[Product Data Field Delimiter after Quantity/Unit Price]
    L2 --> L3[Separator or delimiter after Product Amount by position]
    L3 --> M[Update Element 63 segment count]
    M --> N{Validation result}

    N -->|Valid| O[Include Segment 102 in Data Section No. 3]
    N -->|Invalid| P[Reject with deterministic error]
```

## How to Read the Flow

- The left side is POS/pump business analysis: what was sold and how.
- The middle is per-product message construction and ordering rules.
- The right side is cross-segment reconciliation, serialization, and validation outcome.
- Amount reconciliation against Segment 100 is the step most likely to be silently skipped by an AI-generated artifact; treat it as a first-class validation gate, not an optional check.
