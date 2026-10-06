# CA Public Key File Checksum Lifecycle Flow

```mermaid
flowchart TD
    A[Device holds CA_KEYS file] --> B[Derive current checksum]
    B --> C{Populate Element 187 in Segment 130?}
    C -->|No - field is Optional| D[Segment 130 sent without checksum]
    C -->|Yes| E[Segment 130 carries checksum C1]

    D --> F[Host processes EMV request]
    E --> F

    F --> G[Segment 131 built - Element 187 is Required]
    G --> H{Response checksum equals request checksum?}
    H -->|Yes| I[Device key set is current]
    H -->|No| J[Device key set is stale]

    J --> K[Trigger CA Public Key File Load]
    K --> L[Device downloads new CA_KEYS]
    L --> M[Recompute checksum]
    M --> B

    I --> N{Validating the lifecycle rule?}
    N -->|Single message only| O[Cannot certify - SEG130-R-015<br/>needs paired request and response]
    N -->|Paired messages present| P[Compare actual values]
    P --> Q{Echo correct?}
    Q -->|Yes| R[Rule covered - structural]
    Q -->|No| S[Reject lifecycle mismatch]

    R --> T[Cryptographic authenticity]
    T --> U[EXTERNAL_FIXTURE_REQUIRED<br/>SEG130-SME-002 open]
```

## Request vs Response Field Contract

```text
Segment 130 field 3  Element 187  Optional   <-- device may omit
        |
        v
Segment 131 field 3  Element 187  REQUIRED   <-- host must populate
```

The asymmetry is deliberate and is the reason a naive "present on both sides" assertion is wrong.

## Unresolved Gap

Sections 12.20 and 12.21 do **not** state what the host must echo when the request omits Element 187. Until `SEG130-SME-002` and this follow-up question are answered, that branch stays `REVIEW_REQUIRED`.
