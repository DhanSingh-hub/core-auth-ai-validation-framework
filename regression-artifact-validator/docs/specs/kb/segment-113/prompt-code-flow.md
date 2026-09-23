```mermaid
flowchart TD
    A[POS check transaction event] --> B[Identify check-processing service]
    B --> C{Card Type code}
    C -->|041| D[Certegy]
    C -->|045| E[Generic check]
    C -->|046| F[ECA/TeleCheck Service]
    D --> G[ECA/TeleCheck Service Transaction Request shape: 100 + 110 required, 111 optional]
    E --> G
    F --> H[Same shape, plus Segment 113 ECA/TeleCheck risk-control data]
    G --> I{Segment 113 present?}
    H --> I
    I -->|Yes| J[Validate Segment 113 fields]
    I -->|No| K[Conditional per SEG113-R-002 - no error]
    J --> L[Pass, fail, or review]
    K --> L
```

## What the Flow Teaches

- Card Type `041`/`045`/`046` all select the same ECA/TeleCheck Service Transaction Request message shape; only `046` is the literal "ECA/TeleCheck" processor.
- Segment 113's presence is conditional, not tied to a hard Card-Type-driven code rule in the current validator (per SME resolution 2026-09-22).
- A negative test should change the Card Type independently from Segment 113's presence/absence to isolate which condition is actually being tested.
