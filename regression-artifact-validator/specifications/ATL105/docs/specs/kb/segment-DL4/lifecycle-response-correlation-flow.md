# Segment DL4 Lifecycle, Response and Message Correlation Flow

```mermaid
sequenceDiagram
    participant DMS as BUYPASS device management system
    participant H as BUYPASS Host
    participant D as Device
    Note over H: Profile bit set: device must request a DLL
    D->>H: Financial transaction request
    H-->>D: Response with Download Indicator = 1
    D->>H: Load request (Software Load or Table Load - SEGDL4-SME-002)
    H-->>D: ')' DL4 (dial schedule) + DL5 (IP schedule)
    Note over D: Store version, record ID, phone or IP/URL, date, time
    loop At Request Date/Time, up to 3 attempts
        D->>DMS: Dial Software Load Phone Number, request full application load
    end
    alt Load successful
        D->>H: Table Load Request
        H-->>D: Table Load Response (no profile bit required)
    else 3 attempts failed
        Note over D: Print 'decline' message
    end
```

Rules: `SEGDL4-R-006`, `SEGDL4-R-007`, `SEGDL4-R-008`.

Source: [segment-DL4-rule-catalog.json](coverage/segment-DL4-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
