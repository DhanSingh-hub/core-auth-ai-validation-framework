# Segment DL7 Lifecycle, Response and Message Correlation Flow

```mermaid
sequenceDiagram
    participant D as Device
    participant H as BUYPASS Host
    Note over H: Supplemental terminal data maintained at the host
    D->>H: Load request (type not defined for DL7 - SEGDL7-SME-004)
    H-->>D: Host response containing '^' + Segment Length + Download Data
    opt Download Data longer than 100 bytes
        H-->>D: Further DL7 segment(s)? - SEGDL7-SME-004
    end
    Note over D: Apply site language (001) and postal code (002)
```

No catalogued lifecycle rule exists yet; the exchange is open under `SEGDL7-SME-004`.

Source: [segment-DL7-rule-catalog.json](coverage/segment-DL7-rule-catalog.json) · Note: [lifecycle-response-correlation-sme-tba-note.md](lifecycle-response-correlation-sme-tba-note.md)
