# Proprietary Data Load Flow

```mermaid
sequenceDiagram
    participant D as Device
    participant H as BUYPASS Host
    participant T as End of Day Totals Response
    T-->>D: Proprietary data pending response code
    D->>H: Proprietary Data Load Request
    Note over D,H: Data Section 1: MessageType + NumSegments=1
    Note over D,H: Data Section 3 Field 3: Segment 118
    alt Prompt 903 Site Configuration or 905 Fuel Volume
        D->>H: Segment 118 request payload
        H-->>D: Proprietary Data Load Response
    else Prompt 901, 902 or 904 host-to-device payload
        H-->>D: Proprietary Data Load Response with Segment 118
    end
    Note over H,D: Response Code, Download Indicator, initiation data and sequence precede Segment 118
    D->>D: Process block number and pending prompt lifecycle
```

## Position and Framing

The request has a segmented envelope: Data Section 1, no Data Section 2/Segment 100, then Segment 118 in Data Section 3 Field 3. Request Segment 118 is field-separated.

The response has positional Data Section 1 fields followed by Segment 118 in Data Section 3 Field 6. Response Segment 118 has no Field Separators.

## Prompt Direction

- `901`, `902`, `904`: host-to-device response payloads.
- `903`, `905`: device-to-host request payloads.

The exact payload blocks are prompt-specific and must not be collapsed into one generic Segment 118 shape.
