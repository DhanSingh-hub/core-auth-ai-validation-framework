# Segment 100 Multi-Step Transaction Requirements
## AI Solution Implementation Note

**Specification:** ATL105 2026-3  
**Segment:** 100 Standard Message Data Segment  
**Audience:** AI Solution Team  
**Purpose:** Define the multi-step transaction artifacts the AI Solution must generate so the Test Solution can validate them independently.

---

## 1. Important Boundary

The AI Solution is the system under test.

The AI Solution must generate the following artifacts:

```text
Business Requirements
  -> Test Scenarios
      -> Test Cases
          -> Ready-to-use ATL105 JSON test data
```

The Test Solution validates those AI-generated artifacts. It does not create the production test package for Fiserv and its independent fixtures must not be presented as AI output.

Each AI-generated transaction JSON file must be usable by the ATL105 JSON-to-message converter.

---

## 2. Required Multi-Step Flows

### 2.1 Authorization to Completion

```text
Authorization request -> Completion request
```

Expected transaction types:

- Original: `3`, `5`, or `B`
- Follow-up: `0`

Required behavior:

- Follow-up reuses the original `SequenceNumber`.
- Original and completion use the same transaction identity.
- Required approval/reference data is preserved when applicable.
- Completion must not use an unrelated sequence number.

Required test cases:

- Positive: matching original and completion.
- Negative: completion with a different sequence number.
- Negative: missing required approval/reference data where the flow requires it.

---

### 2.2 Authorization to Cancellation

```text
Authorization request -> Cancellation request
```

Expected transaction types:

- Original: `3`, `5`, or `B`
- Follow-up: `S`

Required behavior:

- Cancellation is the recovery action for an authorization-only flow where the source requires cancellation.
- Cancellation reuses the original transaction identity and `SequenceNumber`.
- Do not model Authorization-only timeout recovery as a TOR unless the specification explicitly allows it.

Required test cases:

- Positive: matching authorization and cancellation.
- Negative: cancellation with a different sequence number.
- Negative: wrong follow-up transaction type.

---

### 2.3 Authorization to Void

```text
Authorization request -> Void request
```

Expected transaction types:

- Original: `3`, `5`, or `B`
- Follow-up: `8` where the source permits the flow.

Required behavior:

- Follow-up reuses the original `SequenceNumber`.
- Reversal/void follow-ups require the applicable `ApprovalNumber`.
- Original and follow-up must retain the same terminal/device identity.

Required test cases:

- Positive: matching authorization and void.
- Negative: sequence mismatch.
- Negative: missing approval number.
- Negative: different terminal identity.

---

### 2.4 Purchase/Sale to Void or Reversal

```text
Purchase/capture request -> Reversal or void request
```

Expected transaction types:

- Original: `0`, `4`, or `6`
- Follow-up: `8` or `C`, depending on the transaction context.

Required behavior:

- Follow-up reuses the original `SequenceNumber`.
- Reversal/void requires the applicable approval/reference data.
- Account identity, amount, and terminal/device context must remain consistent where the source requires them.

Required test cases:

- Positive: matching purchase and void/reversal.
- Negative: sequence mismatch.
- Negative: wrong follow-up code.
- Negative: missing approval number.
- Negative: account or amount mismatch where the source rule applies.

---

### 2.5 Refund to Void of Return

```text
Refund/return request -> Void of return request
```

Expected transaction types:

- Original: `7`
- Follow-up: `U`

Required behavior:

- Follow-up reuses the original refund `SequenceNumber`.
- Follow-up references the original refund identity.
- Account, amount, and terminal consistency must be validated when required by the source.

Required test cases:

- Positive: matching refund and void-of-return.
- Negative: sequence mismatch.
- Negative: wrong follow-up code.
- Negative: mismatched original identity.

---

## 3. Three-Message Timeout Flow

### 3.1 Timeout, TOR, and Next Transaction

```text
1. Original eligible financial request times out
2. Time-Out Reversal (TOR) is created and sent
3. Next financial transaction is sent
```

Eligible original transaction types:

- `0` POS Purchase/Capture
- `4` Customer-activated/CAT Purchase/Capture
- `6` Mail/Phone Purchase

TOR transaction type:

- `Z`

Required behavior:

- Wait until the documented 30-second timeout has expired.
- TOR reuses the original request `SequenceNumber`.
- TOR preserves the original account identity and dollar amount.
- TOR is sent from the same device/terminal.
- TOR is sent before the next financial request from that device.
- The next financial transaction receives a new `SequenceNumber`.
- A valid approved or declined TOR response clears the queued TOR.

Required test cases:

- Positive: original timeout -> TOR -> next transaction.
- Negative: TOR uses a new sequence number.
- Negative: next transaction reuses the original sequence number.
- Negative: TOR uses a different terminal.
- Negative: TOR account or amount does not match the original.
- Negative: TOR is sent before the timeout period.
- Negative: TOR is sent after the next transaction.

---

## 4. TOR Retry Behavior

A TOR may be attempted up to **three times** when no valid response is received.

```text
TOR attempt 1 -> no valid response
TOR attempt 2 -> no valid response
TOR attempt 3 -> no valid response
```

All three attempts represent the same TOR and must preserve:

- Transaction type `Z`
- Original `SequenceNumber`
- Account identity
- Dollar amount
- Terminal/device identity

After the third unsuccessful attempt:

- Log an error.
- Clear the TOR according to the source retry rule.
- Do not generate a fourth attempt.

Required test cases:

- Positive: one, two, and three consistent attempts.
- Negative: fourth attempt generated.
- Negative: retry changes sequence number.
- Negative: retry changes account or amount.
- Negative: retry uses a different terminal.

---

## 5. JSON Structure Required from AI Solution

Each transaction must be a complete converter-ready ATL105 JSON file, not a reduced validator fixture.

Minimum structure:

```json
{
  "Financial Request": {
    "MessageType": "ATL105",
    "NumSegments": "3",
    "Standard Segment": {
      "SegmentType": "100",
      "SegmentLength": "086",
      "InformationByte": "0",
      "TerminalID": "HC375003",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "TrackData": "...",
      "PINBlockData": "",
      "NonFuelAmount": "00001400",
      "SequenceNumber": "842262",
      "ApprovalNumber": "",
      "LocalDateTime": "2608101430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Info Segment": {
      "SegmentType": "111",
      "SegmentLength": "071",
      "VarInfoTable": []
    },
    "EMV Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "203",
      "EMVChipData": "..."
    }
  }
}
```

The exact companion segments depend on the transaction context. Do not add or omit EMV, fleet, fuel, EBT, purchase-card, variable-information, NFC, or other segments without specification evidence.

---

## 6. BR to Test Data Traceability

Every flow must have a complete chain:

```text
BR
  -> TS
      -> TC
          -> Test Data JSON file(s)
```

For a two-step flow, the test case should reference two files:

```json
{
  "testDataFiles": [
    "authorization.json",
    "completion.json"
  ]
}
```

For the three-message timeout flow, the test case should reference three files:

```json
{
  "testDataFiles": [
    "original-timeout.json",
    "timeout-reversal.json",
    "next-financial-request.json"
  ]
}
```

For TOR retries, reference each attempt or provide an ordered attempt collection:

```json
{
  "testDataFiles": [
    "tor-attempt-1.json",
    "tor-attempt-2.json",
    "tor-attempt-3.json"
  ]
}
```

Each BR, TS, TC, and test-data record must include the same canonical source anchor, for example:

```json
{
  "specification": "ATL105",
  "version": "2026-3",
  "section": "10",
  "segment": "100",
  "element": "86",
  "rule": "lifecycle-correlation"
}
```

---

## 7. Repository Reference Paths

### Independent validation rules and lifecycle knowledge

- [Segment 100 lifecycle learning note](specs/kb/segment-100/sequence-lifecycle-sme-tba-note.md)
- [Segment 100 lifecycle flow](specs/kb/segment-100/sequence-lifecycle-flow.md)
- [Segment 100 business flow](specs/kb/segment-100/segment-100-sme-tba-learning-note.md)
- [Segment 100 transaction-type catalog](../test-output/test-json/segment-100-transaction-type-coverage.json)

### Existing lifecycle JSON examples and fixtures

- [Multi-step flow catalog](../test-output/test-json/segment-100-multistep-flow-catalog.json)
- [Authorization to Completion source](../test-input/ai-solution/test-data/segment-100/lifecycle/authorization-completion-authorization.source.json)
- [Authorization to Completion follow-up](../test-input/ai-solution/test-data/segment-100/lifecycle/authorization-completion-completion.source.json)
- [Authorization to Void source](../test-input/ai-solution/test-data/segment-100/lifecycle/authorization-void-authorization.source.json)
- [Refund to Void of Return source](../test-input/ai-solution/test-data/segment-100/lifecycle/refund-void-refund.source.json)
- [Sale to Void source](../test-input/ai-solution/test-data/segment-100/lifecycle/sale-void-sale.source.json)
- [Timeout/TOR synthetic fixture](../test-input/ai-solution/test-data/segment-100/lifecycle/timeout-tor-original.synthetic.json)

### AI Team output location

Place the actual AI-generated artifacts under:

```text
specifications/ATL105/test-input/ai-solution/
```

Recommended structure:

```text
business-requirements/
test-scenarios/
test-cases/
test-data/segment-100/lifecycle/
traceability/
metadata/
```

Do not modify the original AI files after intake. The Test Solution validates them as received.

### Validation report

- [Current Segment 100 validation evidence](../test-output/traceability-matrix/segment-100/segment-100-validation-evidence.md)
- [Current Segment 100 evidence JSON](../test-output/traceability-matrix/segment-100/segment-100-validation-evidence.json)

---

## 8. Current Automation Coverage

The independent Test Solution currently validates:

- Authorization → Completion
- Authorization → Cancellation
- Authorization → Void
- Refund → Void of Return
- Sale/Purchase → Void
- Timeout → TOR
- Timeout → TOR → Next Transaction
- TOR attempts 1 through 3
- Sequence reuse and new-sequence rules
- Terminal continuity
- Account/track-data continuity for the three-message timeout flow
- Amount continuity for the three-message timeout flow
- Approval requirements for reversal/void
- Wrong-flow and mismatch detection

The AI Team must supply the actual artifacts before AI-output coverage can be certified.

The current repository fixtures are validation fixtures and examples. They are not a replacement for AI Solution output.

---

## 9. Explicitly Out of Current Credit-Card Scope

### eWIC Authorization → Completion

This flow is documented as a possible multi-step flow, but it requires the separate EBT/eWIC artifact set, including product, balance, discount, receipt, and benefit context. It must be supplied as AI-generated output or separately approved as a synthetic fixture before certification.

Do not claim eWIC coverage from generic Card Type `020` or generic credit-card JSON.
