# Financial Transaction Request Sections

Verified against BUYPASS Platform ATL105 Message Format Specifications, release 2026-3, section 11.1.1, section 11.8.1, section 12.1, and Appendix A.

## Terminology

The TCP/IP Message Header is a transport envelope defined in Appendix A. It is not Data Section No. 1 of an ATL105 Financial Transaction Request.

The TCP/IP header contains:

- Message Length: two bytes, network byte order, representing the length of all following message data.
- TPDU Protocol ID: one byte, fixed at `0x60`.
- TPDU Destination Address: two bytes, reserved; use `0x00` when sending to BUYPASS.
- TPDU Source Address: two bytes, reserved; use `0x00` when sending to BUYPASS.

A TCP/IP header without its declared message data is incomplete protocol input, not a valid BUYPASS request.

## Standard Financial Transaction Request

Each standard Financial Transaction Request has three ATL105 data sections.

| Section | Contents | Requirement |
| --- | --- | --- |
| Data Section No. 1 | Element 55, Message Format Version Identifier (`ATL105`), and Element 63, Number of Segments | Required |
| Data Section No. 2 | Data Segment 100, Standard Message Data Segment | Required |
| Data Section No. 3 | Transaction-specific data segments | None, one, or more segments permitted |

Segment 100 is the only data segment required for every standard Financial Transaction Request. It originates at the device and contains the standard data needed for financial processing.

## Section 1 And Segment 100 Only

A non-EMV standard Financial Transaction Request can contain the TCP/IP header, Data Section No. 1, and Segment 100 with no Data Section No. 3 segment, provided that its business flow does not require additional data.

The following transaction types are eligible for this minimal structure when no flow-specific condition requires a Section 3 segment:

| Code | Transaction Type |
| --- | --- |
| `0` | POS Purchase/Capture or preauthorized completion |
| `3` | POS Authorization Only |
| `4` | Customer-activated Purchase/Capture |
| `5` | Customer-activated Authorization Only |
| `6` | Mail/Phone Purchase |
| `7` | Merchandise Return / Refund |
| `8` | Purchase Reversal / Void |
| `A` | Account Verification |
| `B` | Mail/Phone Authorization Only |
| `C` | Mail/Phone Reversal / Void |
| `S` | Cancellation |
| `U` | Void of a Merchandise Return |
| `Z` | Time-out Reversal |

The `9` transaction type is a special transaction category, not a financial transaction category.

## When Data Section No. 3 Is Required

The minimal structure is not sufficient when the flow requires one of these data segments:

| Condition | Required Section 3 Segment |
| --- | --- |
| EMV card transaction | Segment 130, EMV Request Data Segment |
| NFC tokenized transaction | Segment 123, NFC Payment Tokenization Data Segment |
| Fleet data required | Segment 101, Fleet Data Segment |
| Product or fuel data required | Segment 102, Product Code Data Segment |
| EBT data required | Segment 103, EBT Data Segment |
| Purchase-card data required | Segment 104, Purchase Card Data Segment |
| Variable information required | Segment 111, Variable Information Data Segment |
| Moneris authorizer destination | Segment 135, Moneris Data Request Segment |

For EMV Financial Transaction Requests, Data Section No. 3 must contain one or more segments, and Segment 130 is required for every EMV card transaction.

## Segment 100 Model

Segment 100 has 17 ordered fields. Required fields identify the segment, its length, information-byte mode, terminal, prompt code, account or identifier, sequence number, and partial-approval indicator. Other fields are conditionally populated for card, PIN, fuel, amount, approval-reference, and date-time data.

When a Segment 100 field is not populated, its field separator must still be transmitted. Trailing optional fields that are not needed are not transmitted.

## Source References

- Appendix A, TCP/IP Message Header: extracted specification lines 25506-25541.
- Section 11.1.1, Financial Transaction Request: lines 7519-7627.
- Section 11.8.1, EMV Financial Transaction Request: lines 9949-10138.
- Section 12.1, Standard Message Data Segment: lines 11209-11224.
- Appendix G, Valid Transaction Type Codes: lines 27269-27340.