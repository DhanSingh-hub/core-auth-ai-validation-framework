# Segment 119 Card-Bucket Totals Flow

```mermaid
flowchart TD
    A[Read Grand Total] --> B[Read card bucket sequence]
    B --> C{Bucket index 1-15?}
    C -->|Yes| D[Require bucket]
    C -->|No| E{Data occurs?}
    E -->|Yes| F[Include bucket]
    E -->|No| G[Omit bucket]
    D --> H[Validate label/count/amount]
    F --> H
    G --> I[Continue to end separator]
    H --> J[Apply non-financial and reconciliation policy]
