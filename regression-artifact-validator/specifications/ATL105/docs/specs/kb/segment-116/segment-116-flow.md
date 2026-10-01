# Segment 116 End-to-End Flow

```mermaid
flowchart TD
    A[Device or host initiates TransArmor Key/Key ID Load] --> B[Build TransArmor PKI Encryption and Tokenization Load Request]
    B --> C[Data Section 1: Elements 55 and 63]
    C --> D[Data Section 2: Segment 116, Segment Type = 116]
    D --> E{Full field layout known?}
    E -->|No| F[REVIEW_REQUIRED: SEG116-SME-001 - obtain external TransArmor document]
    E -->|Yes| G[Populate remaining fields per external document]
    F --> H[Send request with only Segment Type + Segment Length validated]
    G --> H
    H --> I[Receive TransArmor Load Response]
    I --> J[Key ID, element 155]
    I --> K[Key Data Length, element 156]
    I --> L[Key Data, element 157]
    J --> M{Approved?}
    K --> M
    L --> M
    M -->|Yes| N[Store new Key ID; use for all subsequent TransArmor transactions]
    M -->|No| O[Key Data carries error message]
```

The `REVIEW_REQUIRED` branch is intentional. It prevents a syntactically valid Segment 116 fixture — built from only Segment Type and Segment Length — from being presented as proof of a complete, approved TransArmor Key/Key ID Load flow.
