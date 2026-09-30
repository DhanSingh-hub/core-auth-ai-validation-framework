# Software Load Flow

```mermaid
sequenceDiagram
    participant D as Device
    participant H as BUYPASS
    participant S as Software Load Response
    D->>H: Request: ? + Terminal ID + P + HW + SW + FW
    H->>H: Check merchant load flag SOFT
    alt SOFT enabled
        H-->>D: ) + DL4 Software Dial Load + DL5 Software IP Load
        D->>D: Parse DL4/DL5 by Data Type Indicator and ~ terminator
        D->>D: Apply the applicable software delivery path
    else SOFT not enabled
        H-->>D: Error block + terminating block
    end
```

## Message Shape

The request is positional and contains no Field Separators. It has no `MessageType`, `NumSegments`, Segment 100, or Segment 120 object.

The response begins with `)` and carries the DL4 and DL5 response blocks. DL4 and DL5 are self-delimited by their own Data Type Indicator and End-of-Data Indicator. The validator checks the envelope and block presence; field-level DL4/DL5 validation remains owned by their segment modules.
