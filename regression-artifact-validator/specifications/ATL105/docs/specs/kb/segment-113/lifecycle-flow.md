```mermaid
flowchart TD
    A[Check purchase original] --> B[Host assigns ECA/TeleCheck Trace ID]
    B --> C{Follow-up needed?}
    C -->|No| D[Transaction complete]
    C -->|Void| E[Void request: Segment 113 Trace ID = original Trace ID]
    C -->|Declined| F[Host returns Denial Record Number]
    E --> G{Trace ID matches original?}
    G -->|Yes| H[Pass lifecycle validation - catalog only, not code-enforced]
    G -->|No or missing| I[REVIEW_REQUIRED]
    F --> J[Denial Record Receipt printed with Denial Record Number]
    H --> K[Complete]
    I --> K
    J --> K
```

## Key Principle

For Segment 113, the Void-to-original correlation identifier is the ECA/TeleCheck Trace ID (Element 134), not Segment 100's Sequence Number alone. Treating a Void as "just another Sequence Number reuse" like a generic Financial Transaction reversal misses this segment-specific rule.
