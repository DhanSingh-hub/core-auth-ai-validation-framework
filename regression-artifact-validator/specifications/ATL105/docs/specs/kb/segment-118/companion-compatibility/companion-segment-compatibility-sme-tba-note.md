# Segment 118 Companion and Envelope Compatibility: SME/TBA Learning Note

## Core Rule

Segment 118 belongs to its own dedicated Proprietary Data Load Request/Response family (Section 11.7.6/11.7.7). It is confirmed to **exclude** the Standard Message Data Segment (100) and Data Section 2 entirely — a rare, explicit exclusion rather than an inferred analogy (contrast with Segment 116, where the exclusion was only an analogy).

```text
Proprietary Data Load intent
  -> Data Section 1: Elements 55, 63 (fixed value 1)
  -> Data Section 3, field 3: Segment 118
  -> (No Data Section 2 / Segment 100 - explicitly excluded)
  -> Proprietary Data Load Response: Data Section 1 (Response Code, Download Indicator,
     Initiation Date/Time, Sequence Number) + Data Section 3, field 6: Segment 118
```

## Compatibility Checks

| Condition | Expected result |
|---|---|
| Proprietary Data Load Request with Elements 55/63 and Segment 118 in Data Section 3, no Segment 100 | Continue validation |
| Segment 100 present alongside a declared Proprietary Data Load Request | Reject - explicitly excluded per Section 11.7.6.1 |
| Segment 118 present in a generic Financial Transaction Request's Data Section 3 | Reject/review - not part of the generic Financial Transaction Request segment list (Section 11.1.1) |
| Host Discount Data (904) response received without a subsequent Segment 102 Product Code 941/991 in a later transaction | `REVIEW_REQUIRED` - cross-segment application not independently verifiable at Item 1 |

## SME Decision

Confirm whether any product-specific extension ever permits Segment 118 to travel alongside Segment 100 or other companion segments (for example, a combined end-of-day message), or whether the explicit exclusion in Section 11.7.6.1 is absolute across all implementations.
