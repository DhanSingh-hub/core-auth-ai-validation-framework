# Sequence Number and Lifecycle Correlation: SME/TBA Learning Note

## Why Sequence Number Matters

Element 86 is not just a six-digit field. It is the transaction identity used to correlate related lifecycle messages.

```text
Initial request
  -> host response
  -> completion, reversal, void, or timeout recovery
  -> same original transaction identity
```

A message can be structurally valid and still be business-invalid if it uses a new or unrelated Sequence Number.

## Question 1: What Is the Original Transaction?

The original transaction is the earlier request that established the business operation and the correlation identity used by a later lifecycle message. It is not simply any earlier message in the file and it is not identified by the Prompt Code alone.

Identify it from the transaction flow:

| Lifecycle flow | Original transaction | Follow-up |
| --- | --- | --- |
| Authorization -> completion | Authorization-only request | Preauthorized completion |
| Authorization-only reversal | POS, CAT, or Mail/Phone authorization-only request | Cancellation, Prompt Code `S` |
| Purchase -> reversal/void | Purchase/capture request | Purchase reversal or void |
| Refund -> void of return | Merchandise return/refund request | Void of merchandise return |
| Request timeout -> reversal | Request whose final host outcome is unknown | Time-out reversal |
| Preauthorization -> completion | Preauthorization request | Completion request |

The original transaction normally supplies the identity and context that the follow-up must reference, including the original Sequence Number and any required approval, account, amount, or transaction context.

### How to Identify It

Ask these questions in order:

1. Which message first initiated the business operation?
2. Which later message claims to complete, reverse, void, or recover that operation?
3. Which message contains the original transaction identity?
4. Is the host response known, delayed, missing, or declined?
5. Does the follow-up explicitly reference the earlier message, or is the relationship only inferred?

### Important Boundary

An authorization request is the original transaction for a completion or authorization-only cancellation. A purchase request is the original transaction for a purchase reversal or void. A refund is the original transaction for a void of return. A timed-out request remains the original transaction even when no final host response was received.

For Authorization Only Reversal, ATL105 specifies Prompt Code `S` (cancellation), not Purchase Reversal/Void code `8`. Element 86 identifies original authorization types `3`, `B`, and `5`; the reversal must carry the same Approval Number and Sequence Number when those values are present in the original. Do not model authorization-only reversal as a separate code-8 flow.

If the available messages do not establish which request is original, classify the lifecycle relationship as `REVIEW_REQUIRED`; do not guess from file order or matching amounts alone.

### TBA Rule

```text
For every lifecycle follow-up, the test data shall identify the earlier
request that established the operation and shall preserve the required
correlation identity and business context from that request.

## Question 3: Which Sequence Number Must Be Reused?

The follow-up must reuse the original transaction's Segment 100 Element 86, the Sequence Number assigned to the original request.

```text
Original Element 86
  =
Follow-up Element 86
```

The Sequence Number is a six-digit numeric value. The source knowledge identifies normal values in the range `000001`-`999999` and a multithreaded range of `100000`-`199999`, subject to the applicable protocol rules.

Examples:

| Original message | Original Element 86 | Follow-up | Expected result |
| --- | --- | --- | --- |
| Authorization | `842262` | Completion with `842262` | Pass this correlation check |
| Purchase | `842271` | Reversal with `842271` | Pass this correlation check |
| Refund | `842280` | Void of return with `842280` | Pass this correlation check |
| Authorization | `842262` | Completion with `842999` | Fail: sequence mismatch |

Do not generate a new Sequence Number for the follow-up merely because it is a new message. The follow-up is a new message in the same lifecycle, not a new transaction identity.

This answer establishes the primary correlation rule only. Approval Number usage, account and amount changes, delayed responses, transaction ordering, and product/card-specific behavior remain parked as open questions.

### TBA Rule

```text
For a lifecycle follow-up, Segment 100 Element 86 shall equal the
Sequence Number of the original transaction unless an explicit,
source-supported exception applies.

## Question 4: Is Approval Number Required?

The source-supported answer is conditional, not universal.

### Confirmed ATL105 Rules

- Approval Number is **Element 5**.
- It is an alphanumeric field with a six-byte length and may be space-filled.
- Element 5 is **required on reversals**.
- When present in a purchase request, it indicates a preauthorization.
- Approval Number is populated when the transaction flow requires a prior-authorization reference.

### What We Must Not Assume

The current source evidence does not support these broader claims:

- Approval Number is required on every initial purchase.
- Approval Number is required on every completion.
- Approval Number is always identical across every product or card flow.
- A missing Approval Number is invalid when the flow does not require a prior-authorization reference.

### Test Rule

```text
If the lifecycle flow is a reversal, Element 5 Approval Number is required.
If the purchase flow carries a prior-authorization reference, Element 5
represents that reference. For other lifecycle cases, requirement status
must be determined from the applicable source rule; otherwise use
REVIEW_REQUIRED.
```

### Evidence Status

```text
SOURCE_SUPPORTED:
  Element 5 definition, reversal requirement, and preauthorization note.

NOT ESTABLISHED BY CURRENT EVIDENCE:
  Universal completion behavior and product/card-specific exceptions.
```

## Question 5: Must Account and Amount Remain Unchanged?

The source-supported answer is **not a universal unchanged-value rule**. The requirements depend on the lifecycle and transaction condition.

### Confirmed Amount Rule for Partial Approval

For a partially approved transaction:

- The reversal amount must equal the amount approved in the original transaction response.
- The TOR amount must equal the amount requested in the original transaction response, as specified for the partial-approval case.
- BUYPASS supports reversal of a partially approved original authorization, not a partial reversal of the original transaction.
- The original request's Sequence Number must be reused.

The source also states that an approved amount may differ from the requested amount. Therefore, the validator must not automatically compare a reversal with the original requested amount when the source requires comparison with the approved amount.

### Account Number Status

The current source evidence establishes that Element 2 identifies the card or account and that its representation depends on the flow and entry method. It does not establish a universal rule that the exact Account Number value must remain unchanged across every completion, reversal, refund, or product/card flow.

### Test Rule

```text
For a partial-approval reversal, compare the reversal amount with the
approved amount from the original response. Reject a partial reversal.

For other lifecycle flows, compare account and amount values only when an
applicable source rule defines the relationship. Otherwise classify the
unresolved comparison as REVIEW_REQUIRED.
```

### Evidence Status

```text
SOURCE_SUPPORTED:
  Partial-approval reversal/TOR amount rules and original Sequence Number.

NOT ESTABLISHED BY CURRENT EVIDENCE:
  Universal account-number equality and universal amount equality for all
  lifecycle flows.
```

## Question 6: What Happens If the Host Response Is Delayed or Missing?

The ATL105 source explicitly defines a timeout flow for the customer host/controller/POS:

1. A timeout occurs when the 30-second response interval expires without a BUYPASS response, or when the response arrives after that interval.
2. A late response to the original transaction request is ignored by the customer host/controller/POS in this flow.
3. The customer host/controller/POS formats a Time-out Reversal (TOR).
4. The TOR is queued and must be sent from the same device as the original financial transaction request.
5. When the next financial transaction starts on that device, the queued TOR is sent before the new financial request.
6. If no new financial transaction occurs before end-of-day settlement, the TOR is sent immediately before the settlement request.
7. A TOR response that is either approved or declined is considered a valid response and clears the TOR from the queue.
8. The TOR is retransmitted, subject to the source health/retry rules, until a valid response is received. After the documented retry limit, an error is logged and the TOR is cleared.

### Source-Supported Correlation Rule

The source identifies the following elements as matching keys for a request and its response:

- Sequence Number
- Account Number
- Dollar amount

It also states that the TOR Sequence Number must equal the Sequence Number of the financial transaction being reversed.

This gives a stronger timeout correlation rule than Sequence Number alone, but it is specifically documented for the timeout/TOR flow.

### Important Distinction

The source also says that each new financial transaction request forwarded to BUYPASS receives a new Sequence Number. This applies to the new financial request; it does not replace the original Sequence Number carried by the queued TOR that reverses the timed-out transaction.

### Evidence Status

```text
SOURCE_SUPPORTED:
  30-second timeout, late-response handling, TOR creation and queueing,
  same-device rule, send-before-next-transaction rule, matching keys,
  TOR Sequence Number reuse, and valid-response clearing behavior.

PARKED FOR SME REVIEW:
  Product/card-specific timeout variations and any flow that differs from
  the documented customer host/controller/POS timeout procedure.
```

## Question 7: Is a Timeout Reversal Permitted?

Yes, but only where the ATL105 source permits it. A Time-out Reversal (TOR) is created for an unsuccessful financial transaction; it does not initiate a new financial transaction request.

### Confirmed ATL105 Scope

The source identifies TOR generation for unsuccessful:

- POS Purchase/Capture transactions
- CAT Purchase/Capture transactions
- Mail/Phone Purchase transactions

The source explicitly states that TOR is not supported for:

- Debit POS Capture transactions
- Debit CAT Capture transactions
- Debit completion transactions

The source also states that a TOR is for financial transactions. To remove the hold on an Authorization Only transaction, the device must send a cancellation transaction instead of a TOR.

Additional source-supported controls:

- A timeout occurs after the 30-second response interval expires. The source separately recommends forwarding the TOR at least 30 seconds after the original transaction has timed out; model these as two timing intervals, not one.
- If no valid TOR response is received, the source retry limit is three attempts on each available connection route, subject to health-message rules. Do not impose a global three-attempt limit across all routes.
- A partially approved TOR must use the amount required for the applicable card/product rule; it is not universally the original request amount or the approved amount.
- Approval or decline is a successful termination of the TOR; timeout or no response is not.
- Multiple transactions and TORs may be in flight, but the device must track outstanding transactions and their queue.

### Test Rule

```text
If an eligible financial transaction times out, a TOR may be generated
after the 30-second timeout, subject to the applicable transaction type
and card/product rules.

If the flow is Authorization Only, use the source-defined cancellation
transaction instead of a TOR. If the flow is an explicitly unsupported
debit capture or debit completion, reject the TOR expectation.
```

### Evidence Status

```text
SOURCE_SUPPORTED:
  General TOR permission, eligible purchase flows, authorization-only
  cancellation distinction, debit exclusions, timeout threshold, and retry
  termination rules.

PARKED FOR SME REVIEW:
  Any product/card-specific timeout behavior not covered by these explicit
  source rules.
```

## Question 8: Can Another Transaction Be Sent Before the Reversal Is Acknowledged?

The ATL105 source gives a conditional answer.

### Confirmed ATL105 Rules

- Multiple transactions and multiple TORs may be in flight at the same time.
- The terminal/device is responsible for tracking the status and maintaining the queue of outstanding transactions.
- A queued TOR is transmitted ahead of the next financial transaction initiated from that device.
- For a completion transaction, the device must finalize TOR processing before resubmitting the completion.
- Resubmitting the completion before TOR processing is complete risks voiding the resubmitted completion.
- When multiple TORs are queued, submission is throttled to no more than one TOR every 30 seconds.

### Practical Answer

```text
Another independent transaction may be in flight only under the source's
queue and device-tracking rules. A new financial request must not bypass a
queued TOR, and a completion must not be resent before its TOR is finalized.
```

This is not equivalent to “block every transaction until every reversal is acknowledged.” The source explicitly allows multiple transactions in flight, while imposing ordering and completion-specific safeguards.

### Evidence Status

```text
SOURCE_SUPPORTED:
  Multiple in-flight transactions, queue ownership, TOR-before-next-request,
  completion resubmission restriction, and TOR throttling.

PARKED FOR SME REVIEW:
  Product/card-specific queue behavior or any operational policy beyond the
  source-defined device and TOR processing rules.
```
```
```

## Lifecycle Examples

| Flow | Dependency |
| --- | --- |
| Authorization -> completion | Completion reuses the original Sequence Number and approval context |
| Purchase -> reversal/void | Reversal references the original purchase identity |
| Refund -> void of return | Void references the original refund identity |
| Timeout -> timeout reversal | Reversal identifies the request with unknown completion status |
| Preauthorization -> completion | Completion carries the original authorization relationship |

## SME Questions

1. What is the original transaction?
2. Which message is the follow-up?
3. Which values must remain unchanged?
4. Is an Approval Number required for this follow-up?
5. Is the lifecycle relationship keyed by Sequence Number alone, or also by approval/account/amount?
6. What happens if the response is late or missing?
7. Can the POS send another transaction before the reversal is acknowledged?

## TBA Translation

```text
Business condition
  -> lifecycle key
  -> original Sequence Number
  -> required follow-up fields
  -> expected validation result
```

Good requirement:

```text
For a completion associated with authorization AUTH-001, Element 86
shall equal the original authorization Sequence Number and the required
Approval Number shall be present.
```

Weak requirement:

```text
The system should support reversals.
```

## Current Validator Boundary

The current validator checks:

- Six-digit Sequence Number format
- Declared lifecycle key consistency within the package
- Explicit expected original Sequence Number
- Approval Number presence when declared as required

It does not yet validate every lifecycle-specific combination of account, amount, Prompt Code, approval, and host response. Those should be added as separate lifecycle scenarios.

## Review Checklist

- Is the initial transaction identified?
- Is the follow-up message type correct?
- Is the original Sequence Number reused?
- Is Approval Number present where required?
- Are account and amount references consistent?
- Is timeout/retry behavior represented?
- Does the test data show the actual related messages, not merely metadata?
- Are lifecycle mismatches classified as deterministic failures?

## Parked Lifecycle Questions

The following questions are intentionally not converted into unconditional Test Team rules. They require SME/TBA clarification or additional source evidence:

| Question | Current status |
| --- | --- |
| Product/card-specific lifecycle behavior | `REVIEW_REQUIRED` |
| Approval Number usage outside the confirmed reversal rule | `REVIEW_REQUIRED` |
| Account and amount changes outside confirmed partial-approval rules | `REVIEW_REQUIRED` |
| Late or missing responses outside the documented TOR flow | `REVIEW_REQUIRED` |
| Sending another transaction under product/card-specific queue policies | `REVIEW_REQUIRED` |

These parked questions must remain visible in the coverage report. They must not be counted as `COVERED`, and they must not be silently converted into assumptions or auto-approved rules.
