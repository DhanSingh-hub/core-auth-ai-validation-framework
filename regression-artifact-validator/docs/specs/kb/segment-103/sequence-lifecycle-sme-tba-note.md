# Segment 103 Sequence and Lifecycle Correlation: SME/TBA Learning Note

## Why Correlation Matters

Segment 103 is a companion of the standard Segment 100 transaction. A completion, reversal, timeout reversal, or voucher-clear flow must be interpreted with the related Segment 100 transaction and message direction.

```text
original Segment 100 context
  -> eWIC/EBT lifecycle role
  -> Segment 103 data
  -> response or reversal
  -> completion gate before next transaction
```

## Required Analysis

- Identify the original transaction and its Prompt Code.
- Preserve the original sequence and account context where the lifecycle requires it.
- Distinguish Purchase Completion (`0086`) from Purchase Reversal/Void (`8086`).
- Do not send another eWIC transaction before the required reversal response.
- Do not use a valid-looking Segment 103 payload to hide a missing original lifecycle message.

## Test Pattern

```text
BR: eWIC Purchase Reversal/Void completes before another eWIC transaction.
TS: Timeout or late response followed by reversal and next transaction.
TC: Send the next transaction before reversal response.
TD: Original Prompt Code, reversal Prompt Code 8086, response state, expected FAIL.
```

The current structural framework validates the segment and prompt boundary; full multi-message correlation requires paired request/response fixtures.
