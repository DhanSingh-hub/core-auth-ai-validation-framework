# eWIC Prompt and Lifecycle: SME/TBA Learning Note

## Prompt Code Table

| Prompt Code | Operation |
| --- | --- |
| `3086` | eWIC Authorization / Benefits Inquiry |
| `S086` | eWIC Authorization Cancellation |
| `E086` | eWIC Balance Inquiry |
| `0086` | eWIC Purchase Completion and optional Voucher Clear |
| `8086` | eWIC Purchase Reversal/Void |

Prompt Code is constructed from Transaction Type Code and Card Type, but the eWIC table in Section 10.5.5.2 is the authoritative operation mapping for this module.

## Lifecycle Rules

- eWIC Authorization returns approved products and account balance information.
- Authorization Cancellation is required after timeout or customer cancellation in the specified flow.
- Purchase Completion follows authorization and carries applicable discount/product data.
- Purchase Reversal/Void must complete before another eWIC transaction is sent from the originating device.
- Voucher Clear submits a previously authorized offline voucher.
- eWIC Return is not supported.

## TBA Artifact Pattern

```text
BR: Prompt Code 8086 identifies eWIC Purchase Reversal/Void.
TS: Send a timeout/late-response reversal before another eWIC transaction.
TC: Reject a new eWIC transaction before reversal response or use an unsupported Return.
TD: Prompt Code 8086, original sequence/context, reversal response state.
```

## Review Checklist

- Does Prompt Code match the operation?
- Is the request versus response direction explicit?
- Is the original transaction available for a completion/reversal?
- Does Segment 103 carry the fields required by that lifecycle role?
- Is unsupported eWIC Return rejected?
