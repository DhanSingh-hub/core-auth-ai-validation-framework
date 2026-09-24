# Segment 119 Request/Response Lifecycle: SME/TBA Note

## Lifecycle Chain

```text
proprietary-load selection
  -> Segment 119 Totals Request
  -> sequence number
  -> host totals response
  -> card-bucket and Grand Total processing
  -> next totals/settlement action
```

Sequence Number identifies the transaction throughout its life. Request-response correlation, retry, duplicate request, and failure behavior are distinct policy layers and remain review-gated unless an approved operating rule is supplied.

## Manual Review Items

- Whether a duplicate request is idempotent or rejected;
- retry timing and retry count;
- response matching fields beyond Sequence Number;
- settlement cutoff and timezone;
- how proprietary-load response data affects the next totals request.
