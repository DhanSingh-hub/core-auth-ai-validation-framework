# Segment DL3 Lifecycle, Response and Message Correlation Flow

```mermaid
sequenceDiagram
    participant D as Device
    participant H as BUYPASS Host
    alt Date and Time Load (no load flag needed)
        D->>H: Date and Time Load Request ('?', Terminal Identifier, Load Type 'D')
        Note over H: Adjust for device time zone and DST
        H-->>D: Date and Time Load Response (':' Day Date Time CutTime Password ['~'])
    else Table Load (flag TABL)
        D->>H: Table Load Request (Load Type 'P')
        H-->>D: Table Load Response: ')' DL1 [DL2] [DL3] '*'
    end
    Note over D: Set clock; store Cut Time and Password
    opt Automatic cut time and not yet settled
        D->>H: Totals Request 30 minutes before Cut Time
    end
    D->>H: Totals / Electronic Mail Request carries Password (Element 65)
```

Rules: `SEGDL3-R-007`, `SEGDL3-R-008`, `SEGDL3-R-009`. Open: `SEGDL3-SME-002` (Password), `SEGDL3-SME-003` (`~`).

Source: [segment-DL3-rule-catalog.json](coverage/segment-DL3-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
