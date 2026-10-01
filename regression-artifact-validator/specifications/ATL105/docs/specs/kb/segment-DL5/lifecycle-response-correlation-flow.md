# Segment DL5 Lifecycle, Response and Message Correlation Flow

```mermaid
sequenceDiagram
    participant DMS as BUYPASS device management system
    participant H as BUYPASS Host
    participant D as Device
    D->>H: Financial transaction request
    H-->>D: Response with Download Indicator = 1
    D->>H: Load request (see SEGDL4-SME-002)
    H-->>D: ')' DL4 + DL5
    Note over D: IP-connected device stores the DL5 IP/URL address
    loop At Request Date/Time, up to 3 attempts
        D->>DMS: Connect to Software Load IP/URL Address, request application load
    end
    alt Successful
        D->>H: Table Load Request
    else 3 attempts failed
        Note over D: Print 'decline' message
    end
```

Rules: `SEGDL5-R-006`, `SEGDL5-R-007`. Full step table: [DL4 lifecycle note](../segment-DL4/lifecycle-response-correlation-sme-tba-note.md).

Source: [segment-DL5-rule-catalog.json](coverage/segment-DL5-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
