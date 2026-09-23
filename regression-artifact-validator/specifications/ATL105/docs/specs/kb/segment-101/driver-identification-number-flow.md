# Driver / Identification Number Dependency Flow

```mermaid
flowchart TD
    A[Fleet-card Financial Transaction Request] --> B{Does the fleet program require a driver identifier?}
    B -->|No| B1[Segment 101 field 6 stays empty<br/>Field Separator preserved]
    B -->|Yes| C[Device prompts driver / reads Host Prompt / telematics]
    C --> D{Is the value alphanumeric and <= 10 bytes?}
    D -->|No| X[Reject: violates SEG101-R-012 length/type constraint]
    D -->|Yes| E{Is the value transmitted unencrypted?}
    E -->|No| Y[Reject: encrypted value violates BR-374-1]
    E -->|Yes| F[Place value in Segment 101 field 6]
    F --> G{Is Fleet Employee Number field 7 also populated?}
    G -->|Yes with duplicate value| Z[Review: possible overload — SME confirms both fields intentionally carry the same identity]
    G -->|Yes with distinct value| H[OK — two distinct identifiers]
    G -->|No| H
    H --> I[Compute Segment Length including driver ID]
    I --> J{Segment Length within 001-061 base range?}
    J -->|No| W[Reject: violates SEG101-R-006 base length]
    J -->|Yes| K[Serialize Segment 101 with driver ID]
    K --> L[Include Segment 101 in Data Section 3 alongside Segment 100]
```

## Key Insight

Driver / Identification Number is a **conditional, unencrypted alphanumeric** field. A syntactically valid encrypted string is still a defect for this element. Populating both Driver ID and Fleet Employee Number with the same value is a review item — the program may intentionally duplicate or may have mis-mapped an identifier.
