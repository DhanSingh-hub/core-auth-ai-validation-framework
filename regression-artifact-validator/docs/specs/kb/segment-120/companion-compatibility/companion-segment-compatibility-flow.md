# Segment 120 Companion-Segment Compatibility Decision Flow

```mermaid
flowchart TD
    A[Host assembles Financial Transaction Response] --> B[Identify response-side Section 3 companions]
    B --> C["112 Additional Information<br/>115 Print Data<br/>120 Print Data 2<br/>131 EMV Response (EMV only)<br/>134 Transaction Attributes<br/>136 Moneris Response<br/>146 Enhanced Fleet Response<br/>148 WEX Available Product Fleet Info<br/>152 Response InComm OTC Market Basket<br/>155 Real Time Account Updater Response"]

    C --> D{Does this response require<br/>large print data?}
    D -->|No| E[Segment 120 absent; other applicable<br/>companions still included as needed]
    D -->|Yes| F[Segment 120 required]

    F --> G{Segment 120 present in Data Section 3?}
    G -->|No| X[FAIL: missing required companion]
    G -->|Yes| H[Validate Segment 120 envelope<br/>SEG120-R-001..005, R-008]

    H --> I{Message is Financial Transaction Response<br/>or EMV Financial Transaction Response?}
    I -->|No, it's a request| Y[FAIL SEG120-R-006: response-only segment on a request]
    I -->|Yes| J[Continue]

    J --> K[Segment 120 must be the final segment<br/>of Data Section 3 — BR-263-2, hard-enforced<br/>when an explicit segment order is present]
    K --> L[Continue to Element 63<br/>and other Section 3 companion validation]
```

## Decision Summary

```text
Segment 100 is the request-side baseline (validated by the Segment 100 module).
Segment 120 is a condition-driven response-side companion — never on a request.
Segment 120 validates its own envelope AND its position: it must be the
final segment of Data Section 3 (P-01 resolved 2026-09-23).
```

## Related Diagram

Segment 120 sits alongside a different companion family than Segment 100's
request-side companions (101, 102, 103, 104, 111, 123, 130, 135). See the
Segment 100 [companion-segment compatibility flow](../../segment-100/companion-compatibility/companion-segment-compatibility-flow.md)
for the request-side decision tree that this response-side flow complements.
