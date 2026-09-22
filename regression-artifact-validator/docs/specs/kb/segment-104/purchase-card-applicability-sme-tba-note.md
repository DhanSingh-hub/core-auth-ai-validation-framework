# Purchase-Card Applicability: SME and TBA Learning Note

## Purpose

Segment 104 is conditional, not a universal purchase request segment. Section 11.1.1 states that it is sent only on transactions requiring purchase-card data. The Test Team must prove the business condition before requiring it.

## Decision Rule

```text
Purchase-card data required
  -> Segment 100 is present
  -> Segment 104 is present once in Data Section 3
  -> Element 63 counts both serialized segments
```

Do not infer the condition solely from a generic purchase transaction type. Merchant configuration, card program, Level II/III requirements, or the POS flow may determine it.

## TBA Decomposition

| Artifact | Example |
|---|---|
| BR | A request requiring purchase-card data shall contain Segment 104 with Segment 100. |
| Positive TS | Corporate-card purchase requiring Level II/III data. |
| Negative TC | Purchase-card condition asserted but Segment 104 omitted. |
| TD | Segment 100 context, Segment 104 data, Element 63 count, expected outcome. |

Unknown applicability is `REVIEW_REQUIRED`; it must not silently become a pass or rejection.
