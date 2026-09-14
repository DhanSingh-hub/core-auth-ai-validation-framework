# Section 1 And Section 2 Business-Requirement Refinement

## Purpose And Status

This is Test Team working knowledge for refining business requirements. It is not an approved Test-BR artifact and must be reviewed by the Test Team before Client submission.

Scope is the minimal, non-EMV ATL105 Financial Transaction Request with no Data Section No. 3 segment.

## Terminology

The TCP/IP Message Header is the transport envelope in Appendix A. It is not ATL105 Data Section No. 1.

For this note:

- Transport Header: TCP/IP Message Header.
- Data Section No. 1: Element 55, Message Format Version Identifier, and Element 63, Number of Segments.
- Data Section No. 2: Segment 100, Standard Message Data Segment.
- Data Section No. 3: Transaction-specific segments, excluded from this scope.

## In-Scope Message Model

```text
TCP/IP Header
+ Data Section No. 1: ATL105 + Number of Segments
+ Data Section No. 2: Segment 100
+ No Data Section No. 3 segment
```

A header without its declared following data is incomplete protocol input and is not a valid BUYPASS request.

## Requirement Categories

### 1. Eligibility And Scope

- A standard Financial Transaction Request may omit Data Section No. 3 only when no transaction-specific data is required.
- The minimal message is not valid for an EMV transaction, because Segment 130 is required.
- The minimal message is not valid when the flow requires NFC tokenization, fleet, product/fuel, EBT, purchase-card, variable information, or Moneris data.
- The `9` transaction type denotes a special transaction rather than a financial transaction and is outside this minimal financial-message scope.

Eligible transaction types, subject to the no-additional-data condition, are `0`, `3`, `4`, `5`, `6`, `7`, `8`, `A`, `B`, `C`, `S`, `U`, and `Z`.

### 2. Transport Header Integrity

- The Message Length is two bytes and represents the length of all following data; it excludes the two Message Length bytes themselves.
- The Message Length must be serialized in network byte order.
- TPDU Protocol ID must be `0x60`.
- TPDU Destination Address and TPDU Source Address are reserved; messages sent to BUYPASS use `0x00` in all four reserved bytes.
- A receiver must account for TCP streaming, including partial receipt of the two-byte Message Length field.

### 3. Data Section No. 1 Integrity

- Message Format Version Identifier must be `ATL105`.
- Number of Segments must equal the number of BUYPASS-defined segments included in the message.
- For the minimal-message scope, Number of Segments is `01`, because only Segment 100 is present.
- A field separator separates Element 55 from Element 63 and follows Element 63.

### 4. Segment 100 Presence And Structure

- Segment 100 must be present once in Data Section No. 2 of every in-scope Financial Transaction Request.
- Segment Type is fixed as `100`.
- Segment Length must represent the encoded Segment 100 length.
- Segment 100 must retain the specified order of its 17 fields.
- Fields within Segment 100 are separated by a field separator.
- A field separator remains required for an unpopulated non-trailing field.
- Unneeded trailing optional fields are not transmitted.

### 5. Core Transaction Identity

- Terminal Identifier identifies the originating transaction device.
- Prompt Code identifies the transaction type and card type.
- Account Number carries the PAN, token, account, or transaction identifier appropriate to the flow.
- Sequence Number identifies and correlates the transaction throughout its lifecycle.
- Partial Approval Indicator is required in Segment 100 and uses the applicable allowed value.

### 6. Conditional Segment 100 Data

- Card Discretionary Block Data is populated only when required by the card-entry method.
- Encrypted PIN Block Data is populated only when PIN entry and applicable PIN/DUKPT processing require it.
- Pump/Lane Number and Fuel Purchase Amount are populated only for applicable fuel or lane processing.
- Nonfuel Amount, Tax Amount, and Cash Amount are populated only when applicable to the requested transaction.
- Approval Number is populated when the transaction flow requires a prior authorization reference.
- Local Date and Local Time is populated when required by the applicable transaction flow.

### 7. Lifecycle Correlation

- A preauthorized completion must reference the applicable original authorization using the required Segment 100 lifecycle data.
- A purchase reversal or void must reference the applicable original purchase or authorization.
- A void of a merchandise return must reference the applicable original refund.
- A time-out reversal must correlate to the request whose completion status is unknown.
- Lifecycle correlation rules must be verified separately for each transaction type; the exact required fields and values remain subject to Test Team source review.

## Business Scenarios To Refine Later

The following scenario groups are candidates for later Test Team discussion. They are not Test-BRs or approved test scenarios.

| Group | Candidate Coverage |
| --- | --- |
| Minimal eligibility | Eligible standard transaction with Section 3 absent; ineligible transaction with required additional data |
| Basic purchase | POS purchase/capture without transaction-specific data |
| Authorization and completion | Authorization-only and separately correlated preauthorized completion |
| Returns and reversals | Refund, purchase void, void of return, and time-out reversal |
| Card-not-present | Mail/phone purchase, authorization-only, and reversal |
| Protocol integrity | Header length, protocol ID, reserved TPDU values, and partial message handling |
| Message integrity | ATL105 identifier, segment count, Segment 100 identity, order, length, and separators |

## Source References

- Appendix A, TCP/IP Message Header: extracted specification lines 25506-25541.
- Section 11.1.1, Financial Transaction Request: lines 7519-7627.
- Section 11.8.1, EMV Financial Transaction Request: lines 9949-10138.
- Section 12.1, Standard Message Data Segment: lines 11209-11224.
- Appendix G, Valid Transaction Type Codes: lines 27269-27340.
