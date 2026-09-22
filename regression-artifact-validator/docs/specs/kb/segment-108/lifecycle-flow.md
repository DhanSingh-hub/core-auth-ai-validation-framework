```mermaid
flowchart TD
    A[Coupon or points redemption original] --> B{Follow-up needed?}
    B -->|No| C[Transaction complete]
    B -->|Reversal| D[Reversal request: Segment 108 Unit of Work = original Unit of Work]
    D --> E{Update Code value for reversal?}
    E -->|Not documented| F[SEG108-SME-002 OPEN - do not fabricate a code]
    D --> G{Unit of Work matches original?}
    G -->|Yes| H[Pass lifecycle validation - catalog only, not code-enforced]
    G -->|No or missing| I[REVIEW_REQUIRED]
    F --> J[Complete - flagged as gap]
    H --> J
    I --> J
```

## Key Principle

For Segment 108, the reversal-to-original correlation identifier is Unit of Work (Element 151), not Segment 100's Sequence Number alone. The Update Code value that would identify a reversal request is genuinely undocumented (`SEG108-SME-002`) — treat this as an open gap, not a solved problem.
