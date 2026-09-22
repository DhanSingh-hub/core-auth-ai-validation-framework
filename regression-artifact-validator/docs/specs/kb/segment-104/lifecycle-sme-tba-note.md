# Segment 104 Lifecycle: SME and TBA Learning Note

Unlike Segment 100, Segment 104 does not own a sequence number, approval number, or original-transaction identifier. Its lifecycle behavior is therefore subordinate to the Segment 100 request and the applicable financial transaction message type.

## Validation Principle

```text
Original transaction identity
  -> Segment 100 lifecycle fields
  -> applicable follow-up message
  -> Segment 104 inclusion only when purchase-card data remains required
```

Do not copy Segment 104 blindly from an original authorization into completion, reversal, void, or cancellation. The governing source must identify whether purchase-card data is applicable in that follow-up message.

When the source is silent, create a `REVIEW_REQUIRED` scenario instead of claiming the segment is mandatory or forbidden.
