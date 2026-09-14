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
