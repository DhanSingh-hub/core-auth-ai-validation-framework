# EMV Chip Data and TLV Validation Flow

```mermaid
flowchart TD
    A[EMV Financial Transaction Request assembled] --> B{Reversal or TOR?}
    B -->|Yes| C[EMV data not required - Section 10.14.2.4]
    B -->|No| D{Entry mode?}

    D -->|Contact chip| E[Chip data expected]
    D -->|Contactless chip| E
    D -->|Fallback swipe 80| F[No chip data - magnetic stripe path]
    D -->|MSR swipe 90| G[Not an EMV transaction]

    E --> H[Read Element 189 EMV Chip Data Length]
    H --> I{3 numeric digits in 000-999?}
    I -->|No| X1[Fail SEG130-R-007]
    I -->|Yes| J[Read Element 190 EMV Chip Data]

    J --> K{Byte count equals Element 189?}
    K -->|No| X2[Fail SEG130-R-008 length mismatch]
    K -->|Yes| L[Walk TLV tag stream]

    L --> M{TLV parses to clean termination?}
    M -->|No| X3[Fail SEG130-R-008 malformed TLV]
    M -->|Yes| N[Extract comparable tags]

    N --> O["Compare 9F02/9C/5F2A/9F1A<br/>against Segment 100 context"]
    O --> P{Values agree?}
    P -->|No| Q[REVIEW_REQUIRED - SEG130-R-009<br/>blocked by SEG130-SME-003]
    P -->|Yes| R[Structural validation passed]

    R --> S[Tag 9F26 cryptogram]
    S --> T[NOT VALIDATED - SEG130-R-010<br/>requires certified EMV kernel/HSM]
    T --> U[Forward to issuer for authenticity]

    C --> Z[Rule covered]
    F --> Z
    R --> Z
```

## Validation Boundary Summary

```text
+----------------------------------------------------------+
|  Validated by this framework                             |
|    - Element 189 format and range                        |
|    - Element 189 <-> Element 190 length agreement         |
|    - TLV framing / clean parse                            |
+----------------------------------------------------------+
|  Blocked pending SME input                                |
|    - Mandatory tag set per brand and transaction type     |
|    - Cross-field agreement with Segment 100 (SEG130-R-009)|
+----------------------------------------------------------+
|  Permanently out of scope                                 |
|    - Tag 9F26 cryptogram authenticity (SEG130-R-010)      |
|    - CA key resolution / checksum authenticity            |
+----------------------------------------------------------+
```

The essential point is that Element 190 is validated for **structure and internal consistency**, never for **cryptographic truth**. Any requirement that blurs that line is defective.
