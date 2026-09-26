# Odometer Dependency Flow

```mermaid
flowchart TD
    A[Fleet-card Financial Transaction Request] --> B{Is odometer capture required by the fleet program?}
    B -->|No| B1[Odometer field 3 stays empty<br/>Field Separator preserved]
    B -->|Yes| C[Device prompts driver or reads telematics odometer]
    C --> D{Is the captured value numeric?}
    D -->|No| X[Reject: violates SEG101-R-009 type constraint]
    D -->|Yes| E{Length within 8 digits?}
    E -->|No| X
    E -->|Yes| F[Place value in Segment 101 field 3]
    F --> G[Compute Segment Length including odometer]
    G --> H{Segment Length within 001-061 base range?}
    H -->|No| Y[Reject: violates SEG101-R-006 base length]
    H -->|Yes| I[Serialize Segment 101 with the odometer value]
    I --> J[Include Segment 101 in Data Section 3 alongside Segment 100]
    J --> K[Send request]
    K --> L{Follow-up lifecycle message?}
    L -->|Auth Completion 0220| M[Reuse odometer context from initial request or Host Prompt]
    L -->|Reversal / Void| N[Reuse original odometer]
    L -->|None| O[Complete]
```

## Key Insight

Odometer validation is a **conditional field check** driven by the fleet program, not a universal Segment 101 requirement. A missing Odometer is only a defect when the fleet program mandated it — otherwise, an empty field with a preserved separator is the correct wire representation.
