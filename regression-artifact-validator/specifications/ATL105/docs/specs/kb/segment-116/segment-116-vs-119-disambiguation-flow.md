# Segment 116 vs. Segment 119 Disambiguation Decision Flow

```mermaid
flowchart TD
    A[Source text says '...Request (Data Segment No. 116)'] --> B{Which request is being described?}
    B -->|TransArmor PKI Encryption and Tokenization Load Request| C[Correct: Segment 116 is TransArmor Load Data Segment]
    B -->|Totals with Proprietary Data Load Request| D[Section 11.4.1.2 text says 116 - INCORRECT]
    D --> E[Cross-check Element 85 Segment Type valid-codes table]
    E --> F[Element 85 confirms: 116 = TransArmor, 119 = Totals with Proprietary Data Load]
    F --> G[Cross-check Section 12.17 heading]
    G --> H[Section 12.17 confirms: 'This section describes Data Segment No. 119, Totals with Proprietary Data Load Data...']
    H --> I[Conclusion: Section 11.4.1.2 contains a typo; correct segment number is 119]
    I --> J[Do not certify Section 11.4.1.2's '116' label as Segment 116 evidence]
```
