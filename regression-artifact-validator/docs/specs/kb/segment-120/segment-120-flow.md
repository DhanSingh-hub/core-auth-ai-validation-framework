# Segment 120 End-to-End Flow

This diagram shows the decision path for Segment 120 (Print Data 2 Segment) as a
conditional, **response-side** companion segment in Data Section 3 of a
Financial Transaction Response or EMV Financial Transaction Response. Unlike
Segment 100/101/111, Segment 120 never appears on a request.

```mermaid
flowchart TD
    A[Host prepares Financial Transaction Response] --> B{Does the response require<br/>large amounts of print data?}
    B -->|No| B1[Do not build Segment 120<br/>use Segment 115 Print Data Segment if smaller print data applies]
    B -->|Yes| C[Build Segment 120]

    C --> C1[Segment Type = 120, 3 digits, fixed]
    C -->     C2[Segment Length: always exactly 4 digits, computed from<br/>Segment Type + Print Data + separators]
    C --> C3[Print Data: required, up to 999 characters,<br/>may contain literal '\' line delimiters<br/>for Blackhawk phone activation/recharge receipts]

    C1 --> D[Serialize: Field 1 SEP Field 2 SEP Field 3<br/>— no trailing separator after Print Data]
    C2 --> D
    C3 --> D

    D --> E{Total serialized length <= 1009 characters?}
    E -->|No| X1[REJECT: exceeds 1,009-character maximum]
    E -->|Yes| F{Declared Segment Length equals<br/>computed structural length?}
    F -->|No| X2[REJECT: SegmentLength mismatch]
    F -->|Yes| G[Include Segment 120 in Data Section 3<br/>of the Financial Transaction Response]

    G --> H{Is this message a request?}
    H -->|Yes| X3[REJECT SEG120-R-006: Segment 120 must not appear on a request]
    H -->|No, it is a Response or EMV Response| I{Where in Data Section 3 is Segment 120 placed?}

    I --> I1["Always last (BR-263-2) — AUTHORITATIVE,<br/>hard-enforced (P-01 resolved 2026-09-23)"]
    I -.->|previously appeared to conflict with| I2["AI-generated template listed it 3rd of 9-10 —<br/>now assessed as a numeric-sort extraction artifact,<br/>not genuine wire order"]

    G --> L[Validate Element 63 / segment count consistency<br/>at the Data Section 1 level]
    L --> M{Envelope validation result}
    M -->|Valid| N[Deliver response to POS/device for printing]
    M -->|Invalid| O[Reject with deterministic envelope error]
```

## How to Read the Flow

- The top branch is the host-side business trigger: Segment 120 exists only when the
  response "requires large amounts of print data" (BR-263-1) — a condition the client
  determines, not something this envelope validator can independently confirm.
- The middle branch is the fixed 3-field structure this knowledge base module validates.
- The **ordering question** (`I1` vs `I2`) is now resolved: the AI Solution's own generated
  message templates order segments by ascending segment number, a numeric-sort extraction
  artifact rather than genuine wire-order evidence, so the literal specification sentence
  (`I1`) is authoritative and is hard-enforced. See
  [`SEG120-R-007` / `P-01`](coverage/segment-120-coverage-sme-tba-note.md) for the full
  reasoning, and revisit it if a real message ever contradicts it.
- **This deliverable validates only the envelope** (Segment Type, Segment Length, Print Data
  presence/length, separator placement, response-only applicability). It does not validate
  receipt-formatting business logic beyond the `\` line-delimiter convention.

## Related Segment 100 Decision

Segment 120 is one of the response-side companions implied by the Segment 100
family of message flows once a response is being assembled; see the Segment 100
[end-to-end flow](../segment-100/segment-100-flow.md) for the request-side
counterpart of this decision tree.
