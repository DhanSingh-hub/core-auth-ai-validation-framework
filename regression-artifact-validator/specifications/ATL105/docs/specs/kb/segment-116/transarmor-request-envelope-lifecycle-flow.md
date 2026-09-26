# TransArmor Request Envelope Lifecycle Decision Flow

```mermaid
flowchart TD
    A[Message type declared] --> B{Which family?}
    B -->|Financial Transaction| C[Data Section 1 -> Data Section 2: Segment 100 -> Data Section 3: 101/102/103/104/111]
    B -->|Totals| D[Data Section 1 -> Segment 105]
    B -->|Loyalty Card| E[Data Section 1 -> Data Section 2: 100 -> Data Section 3: 108, 114]
    B -->|Electronic Mail| F[Data Section 1 -> Data Section 2: Segment 109]
    B -->|ECA/TeleCheck| G[Data Section 1 -> Data Section 2: 100 -> Data Section 3: 110, 111]
    B -->|TransArmor Key/Key ID Load| H[Data Section 1 -> Data Section 2: Segment 116]
    H --> I{Segment 100 or Data Section 3 also present?}
    I -->|Yes| J[REVIEW_REQUIRED: SEG116-SME-002, contradicts sibling-family pattern]
    I -->|No| K[Consistent with Totals/Loyalty/Electronic Mail sibling pattern]
```

This flow makes the analogy explicit: the Element 63 (Number of Segments) processing rule lists TransArmor alongside Totals, Loyalty Card, Electronic Mail, and ECA/TeleCheck as sibling message families, each with its own fixed Data Section 2 segment. Every sibling family except ECA/TeleCheck and Financial Transaction excludes Segment 100 and Data Section 3. TransArmor is grouped with the excluding siblings, but this has not been directly confirmed by a dedicated request table.
