# TransArmor Key and Key ID Load Decision Flow

```mermaid
flowchart TD
    A[Device or host initiates TransArmor Key/Key ID Load] --> B[Build Data Section 1: Elements 55, 63]
    B --> C[Build Data Section 2: Segment 116]
    C --> D{Segment Type present and equal to 116?}
    D -->|No| E[Reject: SEG116-R-003]
    D -->|Yes| F{Segment Length present, 3 numeric digits?}
    F -->|No| G[Reject: SEG116-R-004 - pattern-derived]
    F -->|Yes| H{Total serialized length <= 50?}
    H -->|No| I[Reject: SEG116-R-002]
    H -->|Yes| J{Remaining field content known?}
    J -->|No| K[REVIEW_REQUIRED: SEG116-SME-001 - external TransArmor document]
    J -->|Yes| L[Validate remaining fields once documented]
    K --> M[Send request with only Segment Type/Length validated]
    L --> M
    M --> N[Await TransArmor Load Response]
```

This flow deliberately stops at "REVIEW_REQUIRED" rather than inventing a field list. A syntactically valid Segment 116 fixture built from only Segment Type and Segment Length must not be presented as proof of a complete Key/Key ID Load request.
