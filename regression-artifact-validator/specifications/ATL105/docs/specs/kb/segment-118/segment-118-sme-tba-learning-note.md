# Segment 118 Proprietary Data Load: SME and TBA Learning Note

## Purpose

Segment 118 is the Proprietary Data Load Segment. It is the vehicle for five distinct "special transaction" data flows that only occur at end of day, during Day Close processing: Custom Receipt Text, Dynamic Card Table, Site Configuration, Host Discount, and Fuel Volume. Despite sharing one segment number and one envelope, each Prompt Code produces a genuinely different payload shape from field 14 onward.

## Business Decision Model

| Prompt Code | Name | Direction | Driven by | Purpose |
|---|---|---|---|---|
| `901` | Custom Receipt Text | Response | Host push | Text printed on all receipts during a date/time window |
| `902` | Dynamic Card Table | Response | Host push | BIN/RULES/RESTRICTIONS/SAF/PROMPT/PRODUCT table refresh |
| `903` | Site Configuration Data | Request | Device push | Site configuration changes reported to host |
| `904` | Host Discount Data | Response | Host push | Discount terms applied to Product Code 941/991 transactions |
| `905` | Fuel Volume Data | Request | Device push | Fuel volume tables reported to host |

The source is explicit that this only happens at end of day: "The Proprietary Data Load functions occur at end of day only as part of the Day Close processing. These functions are prompted as part of End of Day Totals Response and cannot be supported at any time of the day."

## Required Message Shape (Confirmed Envelope)

```text
Proprietary Data Load Request
  Data Section 1
    Element 55: Message Format Version Identifier
    Element 63: Number of Segments (fixed value 1)
  Data Section 3
    Field 3: Segment 118 (Proprietary Data Load Segment)
  (No Data Section 2 / Segment 100 - explicitly excluded)

Proprietary Data Load Response
  Data Section 1
    Response Code, Download Indicator, Initiation Date, Initiation Time, Sequence Number
  Data Section 3
    Field 6: Segment 118
```

## BR to Test-Data Decomposition

```text
BR: A device receiving a Host Discount pending indicator (Response Code D/E/M/N) shall send a Proprietary Data Load Request with Prompt Code 904.
TS: Device sends the request and receives an approved Host Discount Data response.
TC: Validate the envelope, Segment Type, Prompt Code, and the 904-specific field 14-27 layout.
TD: Sanitized converter-ready request/response pair with synthetic discount terms.
```

## Training Boundaries

- Do not present a generic Financial Transaction Request fixture as proof of Segment 118 coverage; its envelope is dedicated and excludes Segment 100.
- Do not mix payload shapes across Prompt Codes; each of 901/902/903/904/905 has its own field 14+ layout, and some are request-only while others are response-only.
- Do not infer Information Byte values; only its purpose (single vs. multimessage) is documented (`SEG118-SME-001`).
- Do not expose real card-table, discount, receipt-text, or site-configuration data in test data.
- Do not claim AI coverage from framework-generated fixtures; the supplied requirement-catalog extraction remains independent evidence under test.
