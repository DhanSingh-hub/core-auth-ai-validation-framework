# Prompt Code Dependency Flow

```mermaid
flowchart TD
    A[POS/card event] --> B[Identify business intent]
    B --> C[Purchase, authorization, completion, return, reversal, inquiry, or special flow]
    C --> D[Decode Element 78 Prompt Code]
    D --> E[Read first position: transaction type]
    D --> F[Read remaining positions: card type or flow code]
    E --> G{Supported transaction type?}
    G -->|No| X[Reject]
    G -->|Yes| H{Expected context matches?}
    F --> H
    H -->|No| X
    H -->|Yes| I{Financial or special transaction?}
    I -->|Special| J[Apply special-flow rules]
    I -->|Financial| K[Apply Segment 100 financial rules]
    K --> L{Does context require Section 3?}
    L -->|No| M[Segment 100 baseline]
    L -->|Yes| N[Require applicable companion segment]
    J --> O[Route to specialized module]
    M --> P[Validate lifecycle and serialization]
    N --> P
    O --> P
    P --> Q[Pass, fail, or review]
```

## Key Insight

Prompt Code validation is a dependency check, not just a regular-expression check. A syntactically valid Prompt Code can still be business-invalid when it disagrees with the POS event, lifecycle, card type, or required companion segment.

## SME/TBA Decision Flow

```mermaid
flowchart LR
    A[Observed POS event] --> B[SME identifies intent]
    B --> C[TBA records transaction and card context]
    C --> D[Decode Prompt Code]
    D --> E{Code matches context?}
    E -->|No| F[Semantic failure]
    E -->|Yes| G[Derive Segment 100 field conditions]
    G --> H{Specialized data required?}
    H -->|No| I[Segment 100 baseline]
    H -->|Yes| J[Require companion segment]
    I --> K[Generate BR, TS, TC, TD]
    J --> K
    K --> L[Validate serialization and lifecycle]
    L --> M[Pass, fail, or review]
```

## What the Flow Teaches

- The SME/TBA decision starts with business intent, not an AI-generated code string.
- Prompt Code validation has both syntax and semantic stages.
- A correct Prompt Code can still lead to failure when required companion data is missing.
- A negative test should change one meaningful context variable at a time.
- `review` is appropriate when the source or transaction combination does not define a deterministic outcome.
