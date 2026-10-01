# TransArmor Request Envelope Lifecycle: SME/TBA Learning Note

## Core Idea

ATL105 groups several request message families under one shared paragraph describing what always follows Element 63 (Number of Segments). This paragraph is our best evidence for Segment 116's envelope shape, because Section 11.7.5 itself is a stub.

```text
Element 63 (Number of Segments) processing rule enumerates, sibling by sibling:
  Loyalty Card Transaction Requests        -> Data Section 2: Segment 100, Data Section 3: Segments 108, 114
  ECA/TeleCheck Service Transaction Requests -> Data Section 2: Segment 100, Data Section 3: Segments 110, 111
  Totals Requests                          -> Segment 105 (no Data Section 2/3 split stated)
  Electronic Mail Requests                 -> Data Section 2: Segment 109
  Communications Test Requests             -> Data Section 2: Element 120 (Network Management Message)
  TransArmor PKI Encryption and Tokenization -> Data Section 2: Segment 116
```

## Why the Analogy Matters

Two of the six siblings (Loyalty Card, ECA/TeleCheck) include Segment 100 and a Data Section 3. The other four (Totals, Electronic Mail, Communications Test, TransArmor) do not — their Data Section 2 contains only their own dedicated segment. TransArmor's phrasing ("it is always followed by Data Section No. 2, which contains Data Segment No. 116") matches the excluding pattern, not the including one. This is a reasonable, evidence-based analogy, but it is still an analogy, not a directly shown request table — which is why `SEG116-R-005` carries the `SEG116-SME-002` provisional tag.

## Lifecycle Stages (What We Can Validate Today)

| Stage | Status |
|---|---|
| Data Section 1 (Elements 55, 63) present | Confirmed structural requirement |
| Data Section 2 contains Segment 116 | Confirmed |
| Segment 100 absent | Analogy-based, not directly shown |
| Data Section 3 absent | Analogy-based, not directly shown |
| Response received and correlated | Fields known (Key ID/Key Data Length/Key Data); correlation mechanism not shown |

## SME Questions

1. Does the external TransArmor document show a dedicated request table analogous to the ones shown for Totals, Loyalty Card, Electronic Mail, and ECA/TeleCheck?
2. If Segment 100 or a Data Section 3 segment can ever legitimately accompany Segment 116, under what circumstances?

## TBA Rule Pattern

```text
BR: A TransArmor PKI Encryption and Tokenization Load Request shall contain only Data Section 1 and Data Section 2 (Segment 116).
TS: Device sends a request with an unexpected Segment 100 or Data Section 3 segment present.
TC: Submit the malformed request; expect rejection or REVIEW_REQUIRED once the envelope is fully confirmed.
TD: Synthetic request payloads with and without the extra segments.
```

## Current Boundary

The validator checks that Segment 116 is present and well-formed. It does not yet reject a request that also carries Segment 100 or a Data Section 3 segment, because that would require certifying the analogy above as fact rather than a reasoned inference.
