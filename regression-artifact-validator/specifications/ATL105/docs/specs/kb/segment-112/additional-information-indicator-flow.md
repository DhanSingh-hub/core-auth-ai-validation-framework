# Additional Information Indicator (Element 116) Decision Flow

This diagram shows how the host selects which Additional Information triads to include in Segment 112, based on what the Financial Transaction Request/Response context requires.

```mermaid
flowchart TD
    A[Financial Transaction processed] --> B{Balance-eligible card type: gift, EBT, credit, phone?}
    B -->|Yes| B1[Emit triad: Indicator = 001]
    B -->|No| C{Request carried AVS and/or NVS data?}
    C -->|Yes| C1[Emit triad: Indicator = 003]
    C -->|No| D{Request carried CVV/CVV2/CVC2/CID/DTVV?}
    D -->|Yes| D1[Emit triad: Indicator = 004]
    D -->|No| E{Loyalty program in effect?}
    E -->|Version 1| E1[Emit triad: Indicator = 008]
    E -->|Version 2| E2[Emit triad: Indicator = 010]
    E -->|No| F{Visa product result applicable?}
    F -->|Yes| F1[Emit triad: Indicator = 009]
    F -->|No| G{Other documented information type applies - 011-047}
    G -->|Yes| G1[Emit triad: Indicator = matching code]
    G -->|No, reserved/invalid code 014/015/033| H[Reject - resolved 2026-09-26 as reserved, same as code 002]
    G -->|No applicable type| I[No triad for this type]

    B1 --> J[Accumulate triad into Additional Information Section]
    C1 --> J
    D1 --> J
    E1 --> J
    E2 --> J
    F1 --> J
    G1 --> J

    J --> K{More applicable types remain?}
    K -->|Yes| B
    K -->|No| L[Finalize Additional Information Section]
    L --> M{Combined section length <= 990 bytes?}
    M -->|No| N[REVIEW_REQUIRED - no documented overflow behavior]
    M -->|Yes| O[Set Element 115 = 1, append Segment 112]
```

## How to Read the Flow

- Multiple triads can and commonly do coexist in a single Segment 112 (for example, AVS `003` and CVV `004` together).
- Each documented Element 116 code has its own Element 118 shape; some are fixed-length (`012`, `013`, `017`, `018`), most are variable-length governed by Element 117.
- Reserved/invalid codes (`002`, `014`, `015`, `033`) must be rejected outright (resolved 2026-09-26) — see [SEG112-SME-002](../segment-112-sme-tba-input-register.md).
- This flow governs *selection* of triads; the [serialization flow](../serialization-wire-format/serialization-wire-format-flow.md) governs how the selected triads are encoded onto the wire.
