# Partial Approval Dependency Flow

```mermaid
flowchart TD
    A[Card-present transaction] --> B[Determine merchant/POS capability]
    B --> C{Partial approval supported?}
    C -->|No| D[Indicator = 0]
    C -->|Yes| E[Indicator = 1]
    C -->|Amex prepaid balance receipt| F[Indicator = 5]
    C -->|Unsupported context| X[Reject]

    D --> G[Host evaluates request]
    E --> G
    F --> G
    G --> H{Approved amount less than requested?}
    H -->|No| I[Normal completion]
    H -->|Yes| J[POS handles remaining balance/split tender]
    J --> K[Receipt and lifecycle reconciliation]
```

The key distinction is between the **capability indicator** and the **approval result**. A partial approval indicator does not itself approve or decline the transaction.
