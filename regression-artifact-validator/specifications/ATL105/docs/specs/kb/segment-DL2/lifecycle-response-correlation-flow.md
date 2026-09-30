# Segment DL2 Lifecycle, Response and Message Correlation Flow

```mermaid
sequenceDiagram
    participant D as Device
    participant H as BUYPASS Host
    alt Phone Load
        D->>H: Phone Load Request ('?', Terminal Identifier, Load Type 'P')
        alt Load flag PHON
            H-->>D: Phone Load Response with DL2
        else Flag not set
            H-->>D: Error message block + terminating block
        end
    else Table Load
        D->>H: Table Load Request
        H-->>D: Table Load Response: ')' DL1 [DL2] [DL3] '*'
    end
    Note over D: Store primary and secondary dial strings
    D->>H: Next transaction: dial primary (up to Redial Count times)
    alt Primary attempts exhausted
        D->>H: Dial secondary number
    end
```

Rules: `SEGDL2-R-003`, `SEGDL2-R-008`, `SEGDL2-R-009`. Open: `SEGDL2-SME-001` (error-recovery timing), `SEGDL2-SME-003` (Phone Load framing).

Source: [segment-DL2-rule-catalog.json](coverage/segment-DL2-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
