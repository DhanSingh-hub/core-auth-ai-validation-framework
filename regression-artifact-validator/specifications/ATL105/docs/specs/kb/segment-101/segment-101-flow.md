# Segment 101 End-to-End Flow

This diagram shows the business and technical decision path for Segment 101 (Fleet Data Segment) as a companion of Segment 100 in an ATL105 fleet-card financial transaction.

```mermaid
flowchart TD
    A[POS or card event] --> B[Identify transaction intent]
    B --> C{Fleet-eligible card AND fleet program active?}
    C -->|No| C1[Do not build Segment 101<br/>proceed with Segment 100 alone or other companions]
    C -->|Yes| D[Build Segment 100 core fields<br/>fleet-eligible Prompt Code]

    D --> E{Is Segment 145 also being built?}
    E -->|Yes| E1[REJECT — Segment 101 and 145<br/>are mutually exclusive]
    E -->|No| F[Build Segment 101 base fields 1-13]

    F --> F1[Segment Type = 101]
    F --> F2[Segment Length = 3 digits]
    F --> F3[Odometer, Vehicle #, Job #, Driver ID,<br/>Fleet Employee #, License #, Job ID,<br/>Department #, Customer Data, User ID,<br/>Vehicle ID#  — populate per fleet-program rules]

    F --> G{Message type = Auth Completion 0220?}
    G -->|No| H[Serialize base Segment 101<br/>base max length 61]
    G -->|Yes| I{Host Prompts supported?}

    I -->|No| H
    I -->|Yes| J[Populate Fleet Tag 1-5<br/>from host-prompt responses]

    J --> J1[Each tag = 3-byte code + up to 31-byte data]
    J --> J2[Code from: DLS DLN PON INV TRP UNT<br/>TLH DOB ZIP END MID VIN TRA HUB TLR CBA VHT]
    J --> J3[Payload conforms to per-code format]
    J1 --> K[Serialize Segment 101 with Fleet Tags]
    J2 --> K
    J3 --> K

    H --> L[Include Segment 101 in Data Section 3<br/>alongside Segment 100]
    K --> L
    L --> M[Wire message to BUYPASS]

    M --> N[Financial response arrives<br/>outer response validated by Segment 100 knowledge]
    N --> O{Response requires fleet-side follow-up?}
    O -->|Yes — Auth then Completion| I
    O -->|No| P[Complete]
```

## Reading The Flow

1. **Eligibility gate.** The very first decision is whether the card and fleet program qualify for Segment 101. Non-fleet card types must not carry Segment 101.
2. **Mutual exclusion.** Before any Segment 101 fields are populated, the message must not already carry Segment 145 (Enhanced Fleet). A combined message is a rejection scenario.
3. **Base fields.** Fields 1–13 apply to every fleet message. Fields 3–13 are conditional; the fleet program (governed by the Petroleum Industry Processing Specifications — see PROVISIONAL P-07) determines which are mandatory.
4. **Fleet Tags branch.** Fields 14–18 (Fleet Tag 1–5) are exclusive to Auth Completion (0220) messages and require Host Prompts support. Anywhere else, the tags are absent.
5. **Serialization.** Base max length is 61 bytes (Element 84 valid values). The reconciliation with tag-carrying messages is PROVISIONAL P-01.
6. **Response side.** ATL105 does not enumerate Segment 101 in Financial Response messages; validation of response semantics is handled by Segment 100 response knowledge (see PROVISIONAL P-05).

## Related Segment 100 Decisions

The Segment 100 [end-to-end flow](../segment-100/segment-100-flow.md) shows the parent decision `Fleet? -> Require Segment 101` as the trigger for this diagram.
