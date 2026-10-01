# Segment DL1 Lifecycle, Response and Message Correlation Flow

```mermaid
sequenceDiagram
    participant D as Device
    participant H as BUYPASS Host
    D->>H: Financial transaction request
    H-->>D: Response with Download Indicator (Element 30) = 1
    Note over D: Manual download request is also allowed
    D->>H: Table Load Request ('?', Terminal Identifier, Load Type 'P', HW/SW/FW versions)
    alt Merchant load flag = TABL
        H-->>D: Table Load Response: ')' DL1 [DL2] [DL3] '*' [DL6 '*']
        Note over D: DL1 Card Types configure acceptance and features
        Note over D: Card Type 173 -> DL6 blocking window applies
    else Flag not set
        H-->>D: Error message block + terminating block (no DL1)
    end
    D->>H: Very next transaction uses the downloaded data
```

Rules: `SEGDL1-R-005`, `SEGDL1-R-008`, `SEGDL1-R-012`. Open: `SEGDL1-SME-003` (End-of-Load count when DL6 is sent).

Source: [segment-DL1-rule-catalog.json](coverage/segment-DL1-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
