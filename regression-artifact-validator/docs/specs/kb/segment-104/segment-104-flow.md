# Segment 104 End-to-End Flow

```mermaid
flowchart TD
    A[POS purchase-card transaction] --> B{Does the flow require purchase-card data?}
    B -->|No| C[Do not add Segment 104]
    B -->|Yes| D[Build required Segment 100 core]
    D --> E[Add one Purchase Card Data Segment]
    E --> F[Set Segment Type = 104]
    F --> G[Set 3-digit Segment Length]
    G --> H[Populate applicable conditional fields]
    H --> H1[Purchase Code]
    H --> H2[Tax, Freight, Duty amounts]
    H --> H3[Ship-to/from data]
    H --> H4[Direct Marketing Invoice Number]
    H1 --> I[Preserve ordered empty-field positions]
    H2 --> I
    H3 --> I
    H4 --> I
    I --> J[Count final serialized segments in Element 63]
    J --> K[Validate Section 3 ordering and length]
    K --> L{All rules and review gates satisfied?}
    L -->|Yes| M[Submit ATL105 request]
    L -->|No| N[Reject or REVIEW_REQUIRED]
```

Segment 104 has no independent sequence number. Any authorization, completion, reversal, or cancellation correlation is governed by the accompanying Segment 100 and the applicable transaction-message rules.
