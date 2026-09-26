# Segment 111 vs. Segment 112 Companion-Segment Compatibility Flow

Segment 111 (Variable Information Data Segment) and Segment 112 (Additional Information Data Segment) are structurally parallel but functionally and directionally independent. This flow exists to prevent test-data authors from confusing or conflating them.

```mermaid
flowchart TD
    A[ATL105 message under construction] --> B{Which side of the exchange?}
    B -->|Financial Transaction Request| C[Segment 111 may apply]
    B -->|Financial Transaction Response| D[Segment 112 may apply]

    C --> C1[Origin: Device]
    C --> C2[Repeats by Variable Information Indicator - Element 111]
    C --> C3[Elements 111/112/113, max 991 bytes combined]
    C --> C4[Segment max length: per Segment 111 rule catalog]

    D --> D1[Origin: Host]
    D --> D2[Repeats by Additional Information Indicator - Element 116]
    D --> D3[Elements 116/117/118, max 990 bytes combined]
    D --> D4[Segment max length: 999 alphanumeric characters]

    C1 --> E{Are Segment 111 and Segment 112 required together?}
    D1 --> E
    E -->|No documented rule requires this| F[Independent: evaluate each on its own trigger]
    F --> F1[Segment 111 trigger: request-side condition per its own rule catalog]
    F --> F2[Segment 112 trigger: Element 115 = 1 on the response]

    F1 --> G[Do not assume Segment 111 presence implies Segment 112, or vice versa]
    F2 --> G
    G --> H[Validate each segment against its own rule catalog independently]
```

## How to Read the Flow

- Segment 111 and Segment 112 share the same repeating-triad shape (Indicator/Length/Value) but are not the same segment, not on the same side of the exchange, and not co-required.
- A test-data author who copies a Segment 111 fixture and merely renumbers the segment type risks importing request-side assumptions (device origin, Element 111 codes, 991-byte cap) into a response-side Segment 112 fixture (host origin, Element 116 codes, 990-byte cap).
- No specification text in Section 12.10 or 12.11 states that one segment's presence requires the other; treat any such assumption as unverified until an SME confirms it with a citation.
