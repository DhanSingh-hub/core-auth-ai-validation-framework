```mermaid
flowchart TD
    A[Check presented at POS] --> B[MICR read via swipe or keyed manually]
    B --> C[Standard Segment Element 2: Account Number populated with MICR-derived value]
    C --> D[Check Data Segment 110 Element 122: MICR Data, up to 50 bytes]
    D --> E{Raw MICR > 50 bytes?}
    E -->|No| F[Segment 113 Element 137 not required]
    E -->|Yes| G[Segment 113 Element 137: Extended MICR Data required]
    F --> H[Validate Segment 100 + Segment 110 fields]
    G --> H
    H --> I{Extended MICR Data present when needed?}
    I -->|No, but needed| J[Cataloged gap - not code-enforced, TBA review]
    I -->|Yes or not needed| K[Pass]
```

## Key Insight

Unlike Segment 108's field-order quirk or Segment 103's WIC subelements, Segment 113's account-number-related nuance is a **cross-segment** one (100 <-> 110 <-> 113), not a within-segment field rule. The payload validator only checks Segment 113's own Extended MICR Data length bound; the cross-segment MICR consistency is documented here for manual/TBA review, per `SEG113-SME-004`.
