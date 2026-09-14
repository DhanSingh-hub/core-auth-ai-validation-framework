# Segment 100 Descriptive Business Requirements

**Audience:** AI Solution Team, Test Validation Team, Card/POS SMEs, Technical Business Analysts, Client Review Team  
**Specification:** BUYPASS Platform ATL105 Message Format Specification 2026-3  
**Status:** Working Test-BR baseline for review  
**Scope:** Standard ATL105 Financial Transaction Request and Segment 100 compatibility boundary

## 1. Scope and Message Model

These requirements apply to a standard ATL105 Financial Transaction Request:

```text
TCP/IP Header
  -> Data Section No. 1
       -> Element 55: ATL105
       -> Element 63: number of serialized segments
  -> Data Section No. 2
       -> exactly one Segment 100
  -> Data Section No. 3
       -> zero or more applicable companion segments
```

These requirements do not claim to define the complete internal behavior of every companion segment.

## 2. Business Requirements

### BR-SEG100-001: Standard financial request category

**Requirement**  
The solution shall identify whether an input message is a standard ATL105 Financial Transaction Request before applying Segment 100 cardinality rules.

**Acceptance criteria**

- A standard financial request is classified explicitly.
- Non-standard categories are delegated to category-specific rules.
- The validator does not apply standard-financial Segment 100 rules blindly to every ATL105 message category.

**Source anchor**  
ATL105 / 2026-3 / Section 11.1.1 / standard financial transaction request

### BR-SEG100-002: Exactly one Segment 100

**Requirement**  
Every standard ATL105 Financial Transaction Request shall contain exactly one Segment 100.

**Acceptance criteria**

- Zero Segment 100 instances is rejected.
- More than one Segment 100 instance is rejected.
- Exactly one Segment 100 instance permits continued validation.
- Element 63 includes Segment 100 in the serialized segment count.

**Source anchor**  
ATL105 / 2026-3 / Section 12.1 / Segment 100 / `segment-100-required-once`

### BR-SEG100-003: Element 55 identifies ATL105

**Requirement**  
Data Section No. 1 Element 55 shall contain the value `ATL105` for an ATL105 message.

**Acceptance criteria**

- `ATL105` passes.
- A different value fails.
- A missing value fails.

**Source anchor**  
ATL105 / 2026-3 / Section 11.1.1 / Element 55 / `message-format-version-identifier`

### BR-SEG100-004: Element 63 counts serialized segments

**Requirement**  
Data Section No. 1 Element 63 shall equal the number of serialized ATL105 data segments in the request.

**Acceptance criteria**

- Segment 100 only produces count `01`.
- Segment 100 plus one companion produces count `02`.
- A declared count mismatch fails.
- Element 63 uses the required two-digit representation.

**Source anchor**  
ATL105 / 2026-3 / Section 11.1.1 / Element 63 / `number-of-segments`

### BR-SEG100-005: Data Section 1 separators

**Requirement**  
The request shall preserve the required field separator between Elements 55 and 63 and the required separator following Element 63.

**Acceptance criteria**

- Both separators present passes.
- Missing separator between Elements 55 and 63 fails.
- Missing separator after Element 63 fails.

**Source anchor**  
ATL105 / 2026-3 / Section 11.1.1 / Elements 55-63 / `section-1-field-separators`

### BR-SEG100-006: Segment 100 identity

**Requirement**  
Segment 100 shall use Segment Type `100` and a valid Segment Length representation.

**Acceptance criteria**

- Segment Type `100` passes.
- Another segment type fails.
- Segment Length may be represented as a calculated value before serialization.
- A numeric Segment Length shall use the valid 3-4 digit representation.

**Source anchor**  
ATL105 / 2026-3 / Section 12.1 / Elements 84-85 / `segment-type`, `segment-length`

### BR-SEG100-007: Segment 100 field order

**Requirement**  
Segment 100 fields shall remain in the ATL105-defined order.

**Acceptance criteria**

- Ordered fields pass.
- Reordered fields fail.
- Omission is permitted only for an approved trailing optional suffix.

**Source anchor**  
ATL105 / 2026-3 / Section 12.1 / Segment 100 / `segment-100-field-order`

### BR-SEG100-008: Terminal Identifier

**Requirement**  
Element 102 shall contain a valid Terminal Identifier appropriate to the declared flow.

**Acceptance criteria**

- Financial-flow identifiers are 1-22 alphanumeric characters.
- Load-flow identifiers respect the 13-character limit.
- If composite components are declared, the final identifier equals their concatenation.

**Source anchor**  
ATL105 / 2026-3 / Section 12.1 / Element 102 / `terminal-identifier`

### BR-SEG100-009: Prompt Code

**Requirement**  
Element 78 shall identify a supported transaction type and the expected card or special-flow context.

**Acceptance criteria**

- Prompt Code has valid 3-4 character shape.
- Transaction type is supported.
- Expected card/flow code matches the scenario when declared.
- Special transaction type `9` is not accepted as a normal financial transaction without applicable rules.

**Source anchor**  
ATL105 / 2026-3 / Section 12.1 / Element 78 / `prompt-code`

### BR-SEG100-010: Account Number and entry method

**Requirement**  
Element 2 Account Number representation shall agree with the declared POS entry method and token/EMV context.

**Acceptance criteria**

- Manual mode uses an appropriate account representation.
- Track 1/Track 2 modes include required track structure.
- EMV context is not represented only by substitute track data.
- Token/TransArmor context is explicit.
- Sensitive test values are synthetic.

**Source anchor**  
ATL105 / 2026-3 / Element 2 / entry-method and tokenization rules

### BR-SEG100-011: Sequence Number

**Requirement**  
Element 86 Sequence Number shall use six digits and identify the transaction through its lifecycle.

**Acceptance criteria**

- Six-digit sequence passes.
- Invalid length or characters fails.
- Follow-up messages reuse the original sequence when required.

**Source anchor**  
ATL105 / 2026-3 / Section 12.1 / Element 86 / `sequence-number`

### BR-SEG100-012: Lifecycle correlation

**Requirement**  
Completion, reversal, void, and timeout follow-up messages shall preserve required original transaction references.

**Acceptance criteria**

- Original and follow-up messages are identifiable.
- Sequence Number is reused.
- Approval Number is present when required.
- A mismatched follow-up fails.

**Source anchor**  
ATL105 / 2026-3 / lifecycle rules / `lifecycle-correlation`

### BR-SEG100-013: Partial Approval Indicator

**Requirement**  
Element 121 shall use an allowed value consistent with the card-present and network context.

**Acceptance criteria**

- Allowed values are `0`, `1`, and `5`.
- Card-present flows provide the indicator.
- Value `5` requires Amex prepaid balance-receipt context.
- The indicator is not treated as the approval result.

**Source anchor**  
ATL105 / 2026-3 / Section 12.1 / Element 121 / `partial-approval-indicator`

### BR-SEG100-014: Non-trailing empty fields

**Requirement**  
An empty non-trailing Segment 100 field shall retain its field separator so subsequent fields remain positional.

**Acceptance criteria**

- Empty middle field with separator passes.
- Removed middle separator fails.
- Later field values remain in their defined positions.

**Source anchor**  
ATL105 / 2026-3 / Section 12.1 / Segment 100 / `non-trailing-empty-field-separator`

### BR-SEG100-015: Trailing optional fields

**Requirement**  
Unneeded trailing optional Segment 100 fields may be omitted as a contiguous suffix and shall not be replaced by internal omissions or reordering.

**Acceptance criteria**

- Valid trailing suffix omission passes.
- Internal omission followed by later fields fails.
- Reordered fields fail.

**Source anchor**  
ATL105 / 2026-3 / Section 12.1 / Segment 100 / `trailing-optional-fields-omitted`

### BR-SEG100-016: Companion-segment compatibility

**Requirement**  
When a standard financial request requires specialized data, Segment 100 shall be accompanied by every required companion segment.

**Acceptance criteria**

- EMV requires Segment 130.
- Fleet requires Segment 101 when fleet data is required.
- Product/fuel requires Segment 102 when required.
- Multiple applicable categories include every required companion.
- Unknown combinations are held for manual review.

**Source anchor**  
ATL105 / 2026-3 / Sections 11 and 12 / compatibility rules

## 3. Required Test Coverage

The artifact package should include:

- Positive cases.
- Negative cases.
- Boundary cases.
- Conditional cases.
- Lifecycle cases.
- Serialization cases.
- Companion-segment compatibility cases.
- Unknown combinations explicitly marked for manual review.

## 4. Traceability Contract

```text
BR-SEG100-xxx
  -> SCN-SEG100-xxx
      -> TC-SEG100-xxx
          -> TD-SEG100-xxx
```

Every artifact must carry the same canonical source anchor even when producer-local IDs differ.

## 5. Approval Status

This document is a descriptive working Test-BR baseline. The Test Team and Client/Business Team must review and approve the wording, source interpretation, exclusions, and acceptance criteria before it becomes an approved requirement baseline.
