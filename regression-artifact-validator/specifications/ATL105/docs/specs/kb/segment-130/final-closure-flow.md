# Segment 130 Final Closure Flow

```mermaid
flowchart TD
    A[Build ordered Segment 130 fields] --> B{Fixed field 1-6 empty?}
    B -->|Yes| C[Emit field separator anyway<br/>Section 12.20 mandate]
    B -->|No| D[Emit value then separator]
    C --> E
    D --> E[All six fixed fields serialized]

    E --> F{Attempting trailing-field omission?}
    F -->|Yes| X1[REJECT - Segment 130 has no<br/>trailing-omission allowance unlike Segment 100]
    F -->|No| G{EMV Additional Information Section present?}

    G -->|No - zero repetitions| H[Valid - section is fully optional]
    G -->|Yes| I[Emit repetitions with NO internal<br/>and NO inter-repetition separators]
    I --> J{Exactly one separator after final repetition?}
    J -->|No| X2[REJECT - separator contract violated]
    J -->|Yes| K{Section total <= 2000 bytes?}
    K -->|No| X3[REJECT SEG130-R-011]
    K -->|Yes| H

    H --> L{Declared Segment Length <= 3043?}
    L -->|No| X4[REJECT - adopted max per 12.20 + Element 84]
    L -->|Yes| M[Serialization validation complete]

    M --> N{Lifecycle rules in scope?}
    N -->|No| Z[Rule covered - structural only]
    N -->|Yes| O[Load paired messages]

    O --> P[Compare Segment 131 Element 187<br/>against Segment 130 Element 187]
    P --> Q[Compare Appendix T 001 echo on<br/>subsequent advice / batch upload]
    Q --> R{Correlation consistent?}
    R -->|Yes| S[Lifecycle covered]
    R -->|No| X5[REJECT lifecycle mismatch]

    S --> T[Cryptographic scope]
    T --> U[EXPLICITLY OUT OF SCOPE<br/>SEG130-R-010 / SEG130-R-016]
    U --> Z
```

## Divergences From Segment 100 To Preserve

```text
Segment 100                          Segment 130
-----------                          -----------
trailing optional fields may be      no trailing-omission allowance;
omitted                              only the whole repeating section
                                     may be omitted

uniform separator rule               separator rule INVERTS inside the
                                     EMV Additional Information Section

lifecycle = original vs follow-up    lifecycle = request/response checksum
financial message                    echo PLUS Appendix T advice echo
```

Reusing the Segment 100 closure validator unmodified will produce both false accepts and false rejects.
