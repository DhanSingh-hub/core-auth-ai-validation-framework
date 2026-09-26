# Segment 110 End-to-End Flow

```mermaid
flowchart TD
    A[Device begins a check transaction] --> B{Entry method}
    B -->|MICR read| C[Populate MICR Data, element 122]
    B -->|Manually keyed| D[Populate Driver's License, State Code, Check Number]
    C --> E[Build ECA/TeleCheck Service Transaction Request]
    D --> E
    E --> F[Data Section 1: Elements 55 and 63]
    F --> G[Data Section 2: Segment 100]
    G --> H[Data Section 3, Field 4: Segment 110]
    H --> I[Populate 12 fields in source order]
    I --> J[Retain separators for empty fields]
    J --> K[Calculate Segment Length from serialized content]
    K --> L{Segment 110 placement, manually-entered trigger, and element 239 identity approved?}
    L -->|No| M[REVIEW_REQUIRED: do not certify]
    L -->|Yes| N[Send request alongside Segment 111 and Segment 113]
    N --> O[Receive Financial Transaction Response layout]
    O --> P{Check-specific response mapping needed?}
    P -->|Yes| M
    P -->|No| Q[Validate outcome using the generic response oracle]
```

The review paths are intentional. They prevent a syntactically valid Segment 110 fixture from becoming false proof of an approved check-transaction flow, and they prevent the element-239 numbering conflict from silently resolving itself.
