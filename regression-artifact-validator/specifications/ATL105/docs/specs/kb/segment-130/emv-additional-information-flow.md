# EMV Additional Information Section Flow

```mermaid
flowchart TD
    A[Segment 130 fixed fields 1-6 serialized] --> B{Any EMV additional information to send?}
    B -->|No| C[Omit section entirely - valid]
    B -->|Yes| D[Emit Field Separator after field 6]

    D --> E[Start repetition]
    E --> F[Element 191 Indicator - 3 digits]
    F --> G{Indicator in Appendix T allow-list?}
    G -->|001 EMV Table Data| H[Value must be EMVYES or EMVNOT - length 6]
    G -->|002 CARC| I[Value length must be 1]
    G -->|Other| X1[Fail - unknown indicator]

    H --> J[Element 192 Length - 3 digits]
    I --> J
    J --> K[Element 118 Information - variable]
    K --> L{Element 192 equals Element 118 byte count?}
    L -->|No| X2[Fail - desynchronizes all later repetitions]
    L -->|Yes| M{Running total under 2000 bytes?}

    M -->|No| X3[Fail SEG130-R-011 - request cap exceeded]
    M -->|Yes| N{More repetitions?}
    N -->|Yes - no separator emitted| E
    N -->|No| O[Emit exactly ONE Field Separator]

    O --> P[Segment 130 complete]
    C --> P
```

## Appendix T Indicator Catalog

```text
Indicator 001  EMV Table Data
               len 6, values: EMVYES | EMVNOT
               request-side device capability flag
               if returned in a response, device echoes it on
               subsequent advice / batch upload requests

Indicator 002  Card Authentication Results Code (CARC)
               len 1
               value returned by Visa in Bit No. 44.8 of the response
               [SEG130-SME-007] request-side legality unconfirmed
```

## Separator Contract

```text
field 6 <FS> [191][192][118][191][192][118] <FS>
             \__________/ \__________/
              repetition 1  repetition 2
              no internal FS, no FS between repetitions,
              exactly one FS after the last one
```

## Length Cap Differs By Direction

```text
Segment 130 (request)  ->  2,000 bytes
Segment 131 (response) ->  2,800 bytes
```

Applying the request cap to responses produces false rejections.
