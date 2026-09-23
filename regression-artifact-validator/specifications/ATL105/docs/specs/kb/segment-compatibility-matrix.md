# ATL105 Segment Compatibility Matrix

Verified against ATL105 release 2026-3, sections 11.1.1, 11.8.1, 12.1, and the current Section 1/Section 2 knowledge notes.

This matrix defines which segments may accompany the required Segment 100 in a standard Financial Transaction Request. It is a validation aid, not a claim that every segment combination is valid for every merchant configuration.

## Legend

| Code | Meaning |
| --- | --- |
| `R` | Required when the condition in the row is true |
| `C` | Conditional; required only when the corresponding data or flow is present |
| `O` | Optional or flow-dependent; source rules must be consulted before inclusion |
| `N` | Not required by this condition |
| `U` | Not yet determined by the current knowledge base; do not auto-reject |

Segment 100 is required in every standard Financial Transaction Request and is omitted from the companion-segment columns because it is the baseline segment.

## Condition Matrix

| Transaction or condition | 101 Fleet | 102 Product/Fuel | 103 EBT | 104 Purchase Card | 111 Variable Info | 123 NFC Token | 130 EMV | 135 Moneris |
| --- | ---: | ---: | ---: | ---: | ---: | ---: | ---: | ---: |
| Standard non-EMV transaction with no additional-data requirement | N | N | N | N | N | N | N | N |
| Fleet data is required by the flow | R | C | N | N | C | N | C | N |
| Product or fuel data is required by the flow | C | R | N | N | C | N | C | N |
| EBT data is required by the flow | N | C | R | N | C | N | C | N |
| Purchase-card data is required by the flow | N | C | N | R | C | N | C | N |
| Variable information is required by the flow | N | C | C | C | R | N | C | N |
| NFC tokenized transaction | N | C | C | C | C | R | C | N |
| EMV transaction | N | C | C | C | C | C | R | N |
| Moneris authorizer destination | N | C | C | C | C | N | C | R |

`C` means that another condition may independently require that segment; it does not mean the segment is automatically required for every transaction in that row.

## Transaction-Type Baseline

The following transaction types can use the minimal structure of Segment 100 only when no condition in the matrix requires a Section 3 segment:

| Code | Transaction type | Segment 100 only allowed? |
| --- | --- | --- |
| `0` | POS Purchase/Capture or preauthorized completion | Yes, conditionally |
| `3` | POS Authorization Only | Yes, conditionally |
| `4` | Customer-activated Purchase/Capture | Yes, conditionally |
| `5` | Customer-activated Authorization Only | Yes, conditionally |
| `6` | Mail/Phone Purchase | Yes, conditionally |
| `7` | Merchandise Return / Refund | Yes, conditionally |
| `8` | Purchase Reversal / Void | Yes, conditionally |
| `A` | Account Verification | Yes, conditionally |
| `B` | Mail/Phone Authorization Only | Yes, conditionally |
| `C` | Mail/Phone Reversal / Void | Yes, conditionally |
| `S` | Cancellation | Yes, conditionally |
| `U` | Void of a Merchandise Return | Yes, conditionally |
| `Z` | Time-out Reversal | Yes, conditionally |

Transaction type alone does not prove that a companion segment is unnecessary. Card technology, product/fuel context, merchant configuration, authorizer destination, and flow-specific data must also be evaluated.

## Hard Compatibility Rules

1. Segment 100 must occur exactly once for this standard Financial Transaction Request scope.
2. Segment 130 is required for every EMV Financial Transaction Request.
3. A non-EMV minimal request must not claim an EMV-only Segment 130 requirement is satisfied without Segment 130.
4. A Section 3 trigger must not be represented only as metadata; the required segment must be present in the serialized request.
5. Element 63, `Number of Segments`, must equal the number of serialized segments, including Segment 100 and all Section 3 segments.
6. Segment ordering must follow the applicable ATL105 message layout. This matrix does not authorize arbitrary reordering.
7. Absence of a row condition is not evidence that a segment is forbidden. Where the knowledge base says `U`, the validator should report a review item rather than silently accept or reject the combination.

## Validator Decision Procedure

For each AI-generated test-data document:

1. Identify the transaction type from Segment 100 Element 78, Prompt Code.
2. Identify flow conditions such as EMV, NFC, fleet, product/fuel, EBT, purchase-card, variable information, and Moneris destination.
3. Determine the expected companion segments from the applicable rows.
4. Compare expected segments with the actual serialized segments.
5. Validate Element 63 against the actual segment count.
6. Emit a deterministic error for a missing required segment or a declared count mismatch.
7. Emit `REVIEW_REQUIRED` and hold an unresolved `U` compatibility decision for manual review. It must not be auto-approved or auto-rejected.

## Current Knowledge Boundary

The current knowledge base explicitly maps these Section 3 triggers: Segments 101, 102, 103, 104, 111, 123, 130, and 135. ATL105 defines additional segment types and transaction-specific layouts, but their complete compatibility rules are not yet transcribed here. They must not be treated as universally compatible or universally forbidden until their source rules are added.

## Source References

- Section 11.1.1, Financial Transaction Request: extracted specification lines 7519-7627.
- Section 11.8.1, EMV Financial Transaction Request: lines 9949-10138.
- Section 12.1, Standard Message Data Segment: lines 11209-11224.
- Appendix G, Valid Transaction Type Codes: lines 27269-27340.
- [Financial Transaction Request Sections](11-financial-transaction-request-sections.md).