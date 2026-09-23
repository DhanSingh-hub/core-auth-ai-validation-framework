# Segment 111 End-to-End Flow

This diagram shows the decision path for Segment 111 (Variable Information Data
Segment) as a conditional companion of Segment 100 in an ATL105 Financial
Transaction Request. Segment 111 is a repeating envelope: any number of
Indicator/Length/Value repetitions, each keyed by an Appendix I Table ID.

```mermaid
flowchart TD
    A[POS or card event] --> B[Segment 100 core built]
    B --> C{Does any Appendix I condition apply?<br/>AVS, SIC, ZIP, RFID POS Entry Mode,<br/>MSDI, Soft Descriptor, MIT/CIT, etc.}
    C -->|No| C1[Do not build Segment 111]
    C -->|Yes| D[Build Segment 111 envelope]

    D --> D1[Segment Type = 111]
    D --> D2[Segment Length = 3 digits]
    D --> D3[Repeat: Variable Information Indicator<br/>Table ID, 3 digits]
    D3 --> D4[Repeat: Variable Information Length<br/>3 digits]
    D4 --> D5[Repeat: Variable Information value<br/>length must equal declared length]
    D5 --> E{More repetitions required?}
    E -->|Yes| D3
    E -->|No| F[Close repeated section]

    F --> G{Repeated section <= 991 chars?}
    G -->|No| X1[REJECT: repeated-section max exceeded]
    G -->|Yes| H{Total Segment 111 length <= 999 chars?}
    H -->|No| X2[REJECT: total length exceeded]
    H -->|Yes| I[Compute SegmentLength =<br/>3+3+sum(3+3+len(value))+3]
    I --> J{Declared SegmentLength equals computed value?}
    J -->|No| X3[REJECT: SegmentLength mismatch]
    J -->|Yes| K[Serialize one trailing field separator;<br/>no separators inside or between repetitions]
    K --> L[Include Segment 111 in Data Section 3<br/>alongside Segment 100 and other companions]
    L --> M[Update Element 63 segment count]
    M --> N{Envelope validation result}
    N -->|Valid| O[Continue to Appendix I Table-ID<br/>content validation — out of current scope]
    N -->|Invalid| P[Reject with deterministic envelope error]
```

## How to Read the Flow

- The left branch is the business trigger: Segment 111 only exists when a specific
  Appendix I condition requires it (examples grounded in the approved requirement
  catalog: AVS data, SIC Information at Table ID `007`, ZIP Code at Table ID `003`,
  RFID POS Entry Mode, MSDI for FSA/HRA, Soft Descriptor Merchant Name at Table ID
  `047`, MIT/CIT category data at Table ID `056`).
- The middle branch is the repeating envelope structure that this knowledge base
  module validates.
- The right branch is serialization and the Element 63 handoff back to the
  Segment 100 companion-compatibility decision.
- **This deliverable validates only the envelope.** Table-ID-specific content
  rules (whether Table ID `007` carries a valid MCC, whether Table ID `056`
  Sub Table ID `09` carries a valid MIT/CIT subcategory, and so on) belong to
  Appendix I and are intentionally out of scope; they are tracked as
  `REVIEW_REQUIRED` in the [coverage report](coverage/README.md), not silently
  passed.

## Related Segment 100 Decision

The Segment 100 [end-to-end flow](../segment-100/segment-100-flow.md) shows the
parent decision `Variable information? -> Require Segment 111` as the trigger for
this diagram.
