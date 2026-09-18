# Fleet Tag Decision Flow

```mermaid
flowchart TD
    A[Segment 101 build in progress] --> B{Is the message an Auth Completion 0220?}
    B -->|No| B1[Do not populate any Fleet Tag<br/>fields 14..18 remain absent]
    B -->|Yes| C{Are Host Prompts supported?}
    C -->|No| C1[Do not populate any Fleet Tag<br/>fields 14..18 remain absent]
    C -->|Yes| D[Read Host-Prompt responses captured at the pump]
    D --> E{Any Host-Prompt data to send?}
    E -->|No| E1[Fleet Tag positions may be empty<br/>base fields still sent]
    E -->|Yes| F[Select up to 5 Fleet Tag positions]
    F --> G[For each populated position: decode tag code]
    G --> H{Tag code in the 17-code enumeration?<br/>DLS DLN PON INV TRP UNT TLH DOB ZIP END MID VIN TRA HUB TLR CBA VHT}
    H -->|No| X[Reject: violates SEG101-R-023 code enumeration]
    H -->|Yes| I{Payload matches the per-code format?}
    I -->|No| Y[Reject: violates SEG101-R-024 per-code format]
    I -->|Yes| J[Serialize tag: 3-byte code + payload]
    J --> K[Aggregate all populated Fleet Tag positions into Segment 101]
    K --> L{Segment Length rule with tags applies}
    L -->|PROVISIONAL P-01| M[Escalate: reconcile 001..061 base cap with tags occupying up to 5x34 bytes]
    L -->|Base only| N[Segment Length within 001..061]
    M --> O[Include Segment 101 in Data Section 3 alongside Segment 100]
    N --> O
    O --> P[Send Auth Completion]
```

## Reading The Flow

1. **Two hard gates** — Auth Completion message type **and** Host Prompts capability — must both hold before any Fleet Tag can be populated. Missing either is a hard reject.
2. **Enumeration and format** — the 3-byte code and the per-code payload format are validated independently. A valid code with an out-of-format payload is still invalid.
3. **Length reconciliation** — the base Segment Length range of 001..061 does not obviously accommodate five populated Fleet Tags of up to 34 bytes each; this is tracked as `PROVISIONAL P-01` and requires SME resolution before Item 5 mutation testing.
4. **Ordering** — the flow does not require any specific ordering of tag codes across positions 1..5; the fleet program owns the ordering decision.
