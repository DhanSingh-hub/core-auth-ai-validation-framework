# Segment 100: SME and Technical Business Analysis Learning Note

## Purpose

Segment 100 is the standard message data segment in an ATL105 Financial Transaction Request. It is the shared core between the POS business flow and the ATL105 wire message. A card/POS SME explains why the transaction exists and what data is known; a technical business analyst translates that behavior into explicit rules, traceability, and testable outcomes.

This note is a learning guide, not an approved client requirement set.

## 1. The Correct Mental Model

Do not start with the JSON field names. Start with the transaction flow:

```text
Card or POS event
  -> transaction intent
  -> Prompt Code and card context
  -> required Segment 100 fields
  -> additional-data decision
  -> wire serialization
  -> host response and lifecycle correlation
```

The message has layers:

| Layer | Business question | Technical representation |
| --- | --- | --- |
| TCP/IP header | How is the message transported? | Message length, TPDU protocol and addresses |
| Data Section 1 | What ATL105 message is this and how many segments follow? | Element 55 and Element 63 |
| Segment 100 | What standard transaction is being requested? | Standard fields and Prompt Code |
| Section 3 | What specialized data does this flow require? | Segments such as 101, 102, 103, 130 |
| Lifecycle | Which prior transaction does this complete, reverse, or cancel? | Sequence, approval, prompt and reference data |

The TCP/IP header is not Data Section 1. Segment 100 is not the entire transaction when specialized data is required.

## 2. SME Reasoning: Card, POS, and Transaction Intent

A card/POS SME should answer these questions before defining test data:

1. What did the customer or clerk do at the POS?
2. Was the card read, keyed, tokenized, or processed through a special device flow?
3. Is this a purchase, authorization, completion, return, reversal, cancellation, verification, inquiry, or timeout recovery?
4. What card network/type and transaction type are represented in Prompt Code?
5. Is PIN entry involved?
6. Is the transaction EMV, NFC, fleet, fuel, EBT, purchase-card, variable-information, or Moneris-specific?
7. Does the flow require a prior transaction reference?
8. What must the POS display, print, store, or reconcile after the response?

A technical business analyst converts each answer into:

```text
condition -> required field/segment -> valid representation -> expected result
```

Example:

```text
EMV purchase
  -> Segment 130 required
  -> Element 63 includes Segment 100 and Segment 130
  -> missing Segment 130 = invalid request
```

## 3. Segment 100 Field Responsibilities

Segment 100 contains 17 ordered fields. Their SME purpose is more important than their JSON spelling.

| Field | Element | SME interpretation | Common analysis risk |
| --- | ---: | --- | --- |
| Segment Type | 85 | Identifies Segment 100 | Confusing segment type with transaction type |
| Segment Length | 84 | Length of encoded Segment 100 | Counting JSON characters instead of wire content |
| Information Byte | 44 | Single/multimessage or download mode | Treating it as a generic status flag |
| Terminal Identifier | 102 | POS/device identity | Hard-coding observed terminal IDs as the only valid values |
| Prompt Code | 78 | Transaction type plus card type | Validating only the whole string, not its positions |
| Account Number | 2 | PAN, token, account, or flow identifier | Treating every account representation as a plain PAN |
| Card Discretionary Block Data | 12 | Track/card supplemental data | Losing the relationship with account-entry method |
| Encrypted PIN Block Data | 33 | Encrypted PIN/DUKPT material when required | Using fake clear PIN data or ignoring PIN conditions |
| Pump/Lane Number | 79 | Fuel POS location/context | Populating it for ordinary retail purchases |
| Fuel Purchase Amount | 41 | Fuel amount | Confusing it with Nonfuel Amount |
| Nonfuel Amount | 58 | Nonfuel transaction amount | Ignoring transaction/card-specific amount rules |
| Tax Amount | 99 | Tax component | Assuming it is always required |
| Cash Amount | 17 | Cash-back amount | Confusing cash back with total sale amount |
| Sequence Number | 86 | Transaction correlation identifier | Generating a new value during completion/reversal |
| Approval Number | 5 | Prior approval/reference | Populating it on an initial request without a reason |
| Local Date/Time | 21 | Device-local transaction timestamp | Treating it as a universal UTC timestamp |
| Partial Approval Indicator | 121 | Whether partial approval behavior is supported | Treating `1` as a generic approval result |

The exact field names in an AI-generated JSON artifact may differ. Canonical source anchors must identify the meaning.

## 4. Prompt Code Analysis

Prompt Code is not merely a label. It is constructed from transaction and card context.

```text
Prompt Code
  -> position 1: transaction type
  -> remaining position(s): card type or special flow code
```

For every test case, record:

- Prompt Code
- decoded transaction type
- decoded card type or special code
- POS entry/card context
- expected lifecycle behavior

Do not infer that two requests are equivalent just because their amounts and account numbers match. A purchase, authorization-only request, completion, and reversal can share many fields while having different business meaning.

## 5. Segment 100 and Companion Segments

Segment 100 is required once for a standard Financial Transaction Request. Then ask whether Section 3 is needed.

| Flow | Minimum segment set |
| --- | --- |
| Standard non-EMV purchase with no special data | 100 |
| EMV purchase | 100 + 130 |
| Fleet flow requiring fleet data | 100 + 101 |
| Fuel/product flow requiring product data | 100 + 102 |
| EBT flow requiring EBT data | 100 + 103 |
| Purchase-card flow requiring purchase-card data | 100 + 104 |
| Variable information flow | 100 + 111 |
| NFC tokenized flow | 100 + 123 |
| Moneris authorizer flow | 100 + 135 |
| Multiple applicable categories | 100 + every required companion segment |
| Unmapped combination | expected set is unknown; review required |

Transaction type alone does not determine the segment set. Card technology, POS configuration, merchant setup, and flow-specific data also matter.

## 6. Serialization Thinking

A structured JSON representation is not automatically a valid wire message.

Validate these separately:

1. JSON structure and data types.
2. Semantic field values.
3. Segment presence and order.
4. Field separators.
5. Segment Length.
6. Message Length.
7. Element 63 segment count.
8. Binary/network byte order.

For Segment 100:

- Segment Type is fixed as `100`.
- Segment Length represents encoded Segment 100 content.
- Empty non-trailing fields retain their separators.
- Unneeded trailing optional fields are omitted.
- Segment 100 must retain the specification field order.

A test control such as `trailingOptionalFieldsOmitted: true` is only a claim until the actual serialized representation proves it.

## 7. Lifecycle and POS Business Analysis

Segment 100 carries data that links related requests. A test analyst should model the messages as a flow, not isolated documents.

| Flow | What must remain correlated |
| --- | --- |
| Authorization -> completion | Original sequence and approval/reference data |
| Purchase -> reversal/void | Original transaction identity and sequence |
| Refund -> void of return | Original refund identity and sequence |
| Timeout -> timeout reversal | Request whose final host result is unknown |
| eWIC authorization -> completion | Authorization context, products, balance and discounts |

A new sequence number or missing approval reference may make a message syntactically valid but business-invalid.

## 8. Turning Knowledge Into Requirements

Good BR:

```text
For an EMV Financial Transaction Request, Segment 130 shall be present
in addition to Segment 100, and Element 63 shall equal the number of
serialized segments.
```

Weak BR:

```text
The system should support EMV.
```

A good requirement identifies:

- actor or system boundary
- condition
- required behavior
- data element or segment
- expected outcome
- source anchor
- exception or review boundary

## 9. Turning Requirements Into Tests

Each scenario should define a business variation. Each test case should isolate one verifiable behavior. Each test data document should instantiate that behavior.

```text
BR: EMV requires Segment 130
  -> Scenario: EMV purchase with required Section 3 data
      -> Test Case: omit Segment 130
          -> Test Data: Segment 100 only, EMV flag true
              -> Expected: FAIL
```

Use canonical anchors instead of relying on matching producer IDs:

```text
ATL105 | 2026-3 | 11.8.1 | 130 | | section-3-required-for-emv
```

## 10. Review Checklist for an AI Deliverable

### Business meaning

- Does the requirement describe a real card/POS behavior?
- Is the transaction type and card context explicit?
- Are initial and lifecycle messages distinguished?
- Are merchant/configuration assumptions visible?

### Message construction

- Is Segment 100 present exactly once?
- Are all required core fields represented?
- Is Prompt Code decoded correctly?
- Are conditional fields populated only when applicable?
- Is Element 63 consistent with actual segments?

### Companion data

- Are EMV, NFC, fleet, fuel, EBT, purchase-card, variable-information, and Moneris triggers considered?
- Is a missing required companion segment tested negatively?
- Are unknown combinations marked for review rather than guessed?

### Serialization

- Are field order, separators, Segment Length, and Message Length tested?
- Are empty non-trailing fields retained?
- Are trailing optional fields omitted only when appropriate?
- Is the representation distinguishable from actual wire serialization?

### Traceability

- Does every artifact have a source anchor?
- Does BR -> Scenario -> Test Case -> Test Data resolve?
- Are local IDs allowed to differ between producers?
- Are duplicates, orphans, and anchor mismatches rejected?

## Source References

- Appendix A, TCP/IP Message Header: extracted specification lines 25506-25541.
- Section 11.1.1, Financial Transaction Request: lines 7519-7627.
- Section 11.8.1, EMV Financial Transaction Request: lines 9949-10138.
- Section 12.1, Standard Message Data Segment: lines 11209-11224.
- Appendix G, Valid Transaction Type Codes: lines 27269-27340.
- [Segment 100 canonical anchors](../segment-100-canonical-anchors.md).
- [Segment compatibility matrix](../segment-compatibility-matrix.md).
- [Segment 100 test package](../../../../test-output/test-json/segment-100-compatibility-package.json).
