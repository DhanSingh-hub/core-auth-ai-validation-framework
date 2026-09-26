# Prompt Code (Element 78): SME and TBA Learning Note

## What Prompt Code Does

Prompt Code is a compact representation of transaction intent and card or special-flow context in Segment 100.

```text
Prompt Code
  -> first position: transaction type
  -> remaining positions: card type or special code
```

It is not merely a display label. It influences the expected business flow, required fields, lifecycle behavior, and sometimes companion segments.

## SME/TBA Learning Objective

The SME identifies the real POS event and card context. The TBA converts that understanding into explicit, testable rules. Neither role should begin by copying the Prompt Code from an AI artifact.

```text
POS event
  -> transaction intent
  -> card/program context
  -> Prompt Code
  -> Segment 100 field conditions
  -> companion-segment decision
  -> lifecycle expectation
```

The key question is not only “Is the code formatted correctly?” It is:

> Does this Prompt Code describe the transaction that the POS claims to be sending?

## SME Review Questions

1. What did the customer or clerk initiate?
2. Is this purchase, authorization, completion, return, reversal, cancellation, verification, inquiry, or timeout recovery?
3. What card type or special program is involved?
4. Is the transaction financial or special/nonfinancial?
5. Does the Prompt Code agree with the POS event and expected lifecycle?
6. Does the decoded context require another segment?

## Examples

| Prompt Code | Interpretation |
| --- | --- |
| `0020` | Purchase/capture, card type `020` |
| `3020` | Authorization only, card type `020` |
| `7020` | Merchandise return/refund, card type `020` |
| `8020` | Purchase reversal/void, card type `020` |
| `9020` | Special transaction category; not automatically a financial transaction |
| `3086` | eWIC authorization example; detailed eWIC rules belong to the EBT module |

## Dependency Analysis

A Prompt Code test is incomplete if it checks only string shape. The test should also identify:

```text
Prompt Code
  -> transaction type
  -> card type or program
  -> financial/special boundary
  -> lifecycle expectation
  -> companion-segment expectation
```

Examples:

- Authorization-only must not be treated as an ordinary purchase.
- A completion must preserve the original transaction relationship.
- A special transaction code must not be accepted as a normal financial request without a supporting flow.
- An eWIC code requires eWIC-specific rules, not only generic Prompt Code validation.

## Context Classification Table

| Context question | Example answer | TBA consequence |
| --- | --- | --- |
| What did the POS initiate? | Purchase/capture | Use a purchase Prompt Code and initial-request rules |
| Is this a follow-up? | Completion after authorization | Reuse the required original lifecycle references |
| How was the account obtained? | Swiped, keyed, tokenized, or EMV | Validate Account Number and discretionary/PIN data consistently |
| Is special data present? | EMV chip data | Require the applicable companion segment, such as Segment 130 |
| Is the transaction fuel or fleet? | Fuel purchase | Evaluate Pump/Lane, Fuel Amount, and fleet/product segments |
| Is the code special or nonfinancial? | Inquiry or program flow | Route to the specialized rule set instead of ordinary financial rules |

## Valid Shape, Invalid Meaning

A Prompt Code can pass a shape check and still be wrong:

| Situation | Prompt Code | Expected result |
| --- | --- | --- |
| Authorization-only declared, purchase code supplied | `0020` | Fail: context mismatch |
| Purchase with supported card context | `0020` | Continue to field and companion checks |
| Completion with a new unrelated sequence | valid-looking code | Fail or review: lifecycle mismatch |
| EMV context without required EMV companion data | valid-looking code | Fail: compatibility mismatch |
| Unsupported special code used as a normal financial request | `9020` | Fail or route to review |

## TBA Decomposition Pattern

Convert one business observation into independent artifacts:

```text
Observation:
  POS performs an authorization-only transaction for card type 020.

BR:
  An authorization-only request for card type 020 shall use Prompt Code 3020.

TS:
  Authorization-only request with card type 020.

TC:
  Submit Prompt Code 0020 while the declared intent is authorization-only.

TD:
  Segment 100 Prompt Code = 0020; expected result = FAIL.
```

The negative case is valuable because it proves that the validator checks meaning, not only syntax.

## TBA Artifact Design

A good business requirement says:

```text
For a POS authorization-only request using card type 020,
Element 78 shall be 3020 and the request shall be classified as
an authorization-only flow.
```

A weak requirement says:

```text
Prompt Code should be correct.
```

The scenario, test case, and test data should make the dependency visible:

```text
BR -> Prompt Code identifies authorization-only
Scenario -> POS authorization-only with card type 020
Test Case -> reject purchase code 0020 in authorization-only context
Test Data -> Prompt Code 0020, expected FAIL
```

## Current Validator Boundary

The current Segment 100 validator checks:

- 3-4 alphanumeric shape
- supported transaction-type first position
- expected transaction type when declared by test controls
- expected card type when declared by test controls
- special transaction type `9` is not accepted when the flow requires a financial transaction

It does not yet fully validate every Appendix E card type or every transaction-specific business rule. Those are separate knowledge and rule-expansion tasks.

## Review Checklist

- Does the Prompt Code match the business scenario?
- Does its first position match the expected transaction type?
- Do remaining positions match the card/program context?
- Is the request financial or special?
- Are lifecycle messages using the right related Prompt Codes?
- Does the code imply a companion segment or separate module?
- Is the expected result based on behavior rather than string shape alone?

## Training Exercise

For each case, document the transaction intent, Prompt Code interpretation, required Segment 100 fields, companion-segment expectation, and expected result:

1. Retail purchase, card type `020`, no EMV or fuel data.
2. EMV purchase, card type `020`, chip data present.
3. Fleet fuel purchase, pump number and fuel amount present.
4. Authorization-only request with a purchase Prompt Code.
5. Completion that does not preserve the original Sequence Number.

The expected analysis must distinguish:

```text
syntax valid
semantic valid
context valid
lifecycle valid
serialization valid
```
