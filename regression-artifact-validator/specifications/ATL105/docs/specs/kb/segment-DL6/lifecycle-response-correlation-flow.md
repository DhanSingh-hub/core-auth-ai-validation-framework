# Segment DL6 Lifecycle, Response and Message Correlation Flow

```mermaid
sequenceDiagram
    participant D as Device
    participant H as BUYPASS Host
    D->>H: Table Load Request (Load Type 'P')
    H-->>D: ')' DL1 (Card Types incl. 173) [DL2] [DL3] '*' DL6 '*'
    Note over D: Store daily blocking window Start-End
    loop Every day
        alt Device time within Start-End
            Note over D: Store-and-forward blocked
        else Outside window
            Note over D: Store-and-forward allowed
        end
    end
```

Rules: `SEGDL6-R-001`, `SEGDL6-R-006`, `SEGDL6-R-007`. Open: `SEGDL6-SME-005`, `SEGDL1-SME-003`.

Source: [segment-DL6-rule-catalog.json](coverage/segment-DL6-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
