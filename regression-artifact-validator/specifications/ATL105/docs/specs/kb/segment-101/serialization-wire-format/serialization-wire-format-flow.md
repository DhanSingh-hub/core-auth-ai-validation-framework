# Segment 101 Serialization and Wire-Format Flow

```mermaid
flowchart TD
    A[Fleet business intent] --> B[Structured Segment 101 test-data JSON]
    B --> C[Validate JSON schema and canonical fields]
    C --> D{Values and applicability valid?}
    D -->|No| X[FAIL: logical Segment 101 test-data defect]
    D -->|Yes| E{Message type: Financial Request or Auth Completion 0220?}
    E -->|Initial Financial Request| F1[Serialize base fields 1..13 only]
    E -->|Auth Completion 0220| G{Host Prompts supported?}
    G -->|No| F1
    G -->|Yes| F2[Serialize base fields 1..13 and any populated Fleet Tag positions 14..18]
    F1 --> H[Encode fields in fixed order]
    F2 --> H
    H --> I[Preserve empty middle separators]
    I --> J[Omit allowed trailing optional suffix]
    J --> K[Encode each Fleet Tag as 3-byte code plus per-code payload]
    K --> L[Calculate Segment Length]
    L --> M{Segment Length within 001..061 base range?}
    M -->|Yes| N[Emit Segment 101]
    M -->|No and no tags present| Y1[FAIL: Segment Length out of range]
    M -->|No and tags present| P[FLAG PROVISIONAL P-01: reconcile with Auth Completion tag payload]
    P --> N
    N --> O[Emit Segment 100 first, then Segment 101, then other Section 3 companions]
    O --> Q[Recount Element 63 to include Segment 101]
    Q --> R[Build TCP/IP framing and message length]
    R --> S{Wire-format checks pass?}
    S -->|No| Y2[FAIL or REVIEW: serialization defect]
    S -->|Yes| T[ATL105 message ready for converter/transport]
    T --> U[Validate lifecycle and expected outcome]
```

## Layered Decision

```text
Fleet business intent
  -> logical Segment 101 JSON
  -> serialized Segment 101 (base fields + optional Fleet Tags)
  -> complete ATL105 payload (Segment 100 + Segment 101 + other companions)
  -> TCP/IP frame
  -> executable test input
```

A failure at any layer must identify the representation, field/segment, rule, and source anchor involved.
