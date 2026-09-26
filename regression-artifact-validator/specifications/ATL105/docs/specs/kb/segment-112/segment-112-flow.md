# Segment 112 End-to-End Flow

This diagram shows the business and technical decision path for the ATL105 Additional Information Data Segment, a host-originated, response-only carrier of supplemental transaction data.

```mermaid
flowchart TD
    A[BUYPASS host processes a Financial Transaction Request] --> B{Does any condition require Additional Information?}
    B -->|No| C[Element 115 = 0, no Segment 112]
    B -->|Yes| D[Element 115 = 1, build Segment 112]

    D --> E[Segment Type = 112]
    D --> F[Determine which information types apply]

    F --> F1[Balance / gift / EBT / phone card - 001]
    F --> F2[AVS and/or CVV results - 003 / 004]
    F --> F3[Visa product result - 009]
    F --> F4[Loyalty, tokens, fraud score, and 35+ other types]

    F1 --> G[For each applicable type, emit Indicator/Length/Value triad]
    F2 --> G
    F3 --> G
    F4 --> G

    G --> H[Accumulate triads into the Additional Information Section]
    H --> I{Total section length <= 990 bytes?}
    I -->|No| J[Reject / truncate per host business rule - REVIEW_REQUIRED]
    I -->|Yes| K[Calculate Segment Length]

    K --> L{Total Segment 112 length <= 999 alphanumeric characters?}
    L -->|No| J
    L -->|Yes| M[Append Segment 112 at the end of the Financial Transaction response]
    M --> N[Validate Element 115 = 1 matches Segment 112 presence]
    N --> O{Consistent?}
    O -->|No| P[Reject with deterministic error]
    O -->|Yes| Q[Deliver Financial Transaction Response to device]

    C --> R[Validate Element 115 = 0 matches Segment 112 absence]
    R --> S{Consistent?}
    S -->|No| P
    S -->|Yes| Q
```

## How to Read the Flow

- Segment 112 is entirely response-side and host-authored; the device never constructs it.
- The `Additional Information Section` (Indicator/Length/Value) can repeat multiple times in one Segment 112, once per applicable information type, capped at 990 bytes combined.
- The overall segment cap (999 bytes) is a separate, larger boundary than the repeating-section cap (990 bytes); both must be validated independently.
- Element 115 and Segment 112 presence must agree in both directions: `1` without a segment, or a segment without `1`, are both structural defects.
- "REVIEW_REQUIRED" nodes mark boundary conditions the specification does not fully resolve (see the [SME/TBA Input Register](segment-112-sme-tba-input-register.md)); do not treat them as an automatic pass.
