# Phone Load Message Templates

**Specification:** ATL105 2026-3, Section 11.7.2

## Flow

```mermaid
sequenceDiagram
    participant D as Device
    participant H as BUYPASS
    D->>H: Phone Load Request: ? + Terminal ID + P
    H-->>D: ! + DL2 Dial String Data
    D->>D: Apply primary/secondary dial-string configuration
```

## Understanding

The request is positional and has no Field Separators. It contains Information Byte `?`, a 13-character Terminal Identifier and Load Type `P`. The response begins with Data Type Indicator `!` and carries DL2. DL2 uses its own self-delimited format: primary terminator `A`, secondary terminator `F`, and End-of-Data `~`.

Primary/secondary fallback behavior remains review-gated by the asynchronous communications protocol input. The independent `PhoneLoadPayloadValidator` checks the message envelope and fixed markers only.
