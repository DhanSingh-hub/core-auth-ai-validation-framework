```mermaid
flowchart TD
    A[POS loyalty transaction event] --> B{Card Type code}
    B -->|040| C[Loyalty Card Transaction Request shape: Segment 100 + 108 required, 114 optional]
    C --> D[Update Code selects advice function: A/C/E/I/P/S/T/U]
    D --> E{Payment Tender Type = CS?}
    E -->|Yes| F[Loyalty-only transaction]
    E -->|No| G[Dual-card transaction: loyalty + payment card]
    F --> H[Validate Segment 108 fields]
    G --> H
    H --> I[Pass, fail, or review]
```

## What the Flow Teaches

- Card Type `040` is the single, unambiguous trigger for the Loyalty Card Transaction Request message family (unlike Segment 113's three related check codes).
- Payment Tender Type distinguishes loyalty-only from dual-card transactions independently of Card Type.
- A negative test should change the Card Type independently from Segment 108's presence/absence to isolate which condition is actually being tested.
