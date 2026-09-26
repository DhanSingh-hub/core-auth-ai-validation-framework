```mermaid
flowchart TD
    A[Loyalty transaction initiated] --> B{Card present and readable?}
    B -->|Yes, swiped successfully| C[Loyalty Account Number 139 populated from card]
    B -->|No - failed after 3 swipe attempts, or not present| D[Street Address 144 + Phone Number Loyalty 145 keyed instead]
    C --> E{Expiration Date on card?}
    D --> E
    E -->|Yes| F[Expiration Date 146 = real MMYY value]
    E -->|No| G[Expiration Date 146 = default 1249]
    F --> H[Validate Segment 108 fields]
    G --> H
    H --> I{Both 139 and 144/145 populated simultaneously?}
    I -->|Yes| J[Unrealistic scenario - not code-enforced, TBA review]
    I -->|No| K[Pass]
```

## Key Insight

Segment 108's account-identity substitution (139 vs. 144+145) is a genuine either/or business condition, not a validator-enforced exclusivity rule. Expiration Date (146) is the one field in this flow that IS code-enforced (MMYY format), following the 2026-09-22 SME resolution that reversed the earlier "reserved for future use" treatment.
