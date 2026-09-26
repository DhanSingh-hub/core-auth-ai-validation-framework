# Segment 109 Electronic Mail: SME and TBA Learning Note

## Purpose

Segment 109 is not a financial transaction companion segment. It is the electronic-mail-specific body of the separate Section 11.5 Electronic Mail Request envelope. It supports retrieval and submission between a client store POS device and BUYPASS.

## Business Decision Model

| Intent | Prompt Code | Source-confirmed behavior | Review boundary |
|---|---|---|---|
| Retrieve electronic mail | `981` | Device requests data held at BUYPASS. | Information Byte and block lifecycle. |
| Retrieve proprietary-card data | `996` | Specialized retrieval request. | Enablement and data-handling policy. |
| Submit electronic mail | `995` | Device submits data; BUYPASS confirms receipt and stores data. | Confirmation response-code semantics. |

The source limits retrieval print data to 750 bytes per request and submission data to 217 bytes. Segment 109 Text Data itself is conditional and has a maximum length of 150, so multi-block/aggregation behavior must be approved before it is encoded as a validation rule.

## Required Message Shape

```text
Electronic Mail Request
  Data Section 1
    Element 55: Message Format Version Identifier
    Element 63: Number of Segments
  Data Section 2
    Segment 109: Electronic Mail Data Segment
```

The response is not another Segment 109. It is the Section 11.5.2 positional Electronic Mail Response, carrying Response Code, Download Indicator, initiation date/time, Sequence Number, Block Number, and optional mail text.

## BR to Test-Data Decomposition

```text
BR: A submission request shall use Prompt Code 995 and Segment 109 in Data Section 2.
TS: Device submits one approved electronic-mail block.
TC: Validate the request envelope, segment type, prompt code, separators, and matching response identity.
TD: Sanitized converter-ready request/response pair with synthetic terminal, employee, password, and text values.
```

## Training Boundaries

- Do not present a financial `Segment 100` request as Segment 109 coverage.
- Do not treat Text Data Length as proven to equal a character count until encoding and byte-count rules are approved.
- Do not expose passwords or live employee/terminal identifiers in training data.
- Do not mark an unknown response code or Download Indicator as valid or invalid without an approved mapping.
- Do not claim AI coverage from framework-generated fixtures; AI-produced BR, TS, TC, and TD packages remain independent evidence.