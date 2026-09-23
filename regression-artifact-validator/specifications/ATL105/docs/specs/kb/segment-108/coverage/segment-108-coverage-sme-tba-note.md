# Segment 108 Coverage Closure — SME and TBA Note

## Why This Package Exists

Segment 108 (Loyalty Card Data Segment) is unusual among the segments trained so far: it is not an optional Data Section 3 companion of a generic Financial Transaction Request (unlike Segments 101, 102, 103). It belongs to its own dedicated **Loyalty Card Transaction Request** message family, where it is always required and paired only with the optional Segment 114 (SKU Data Segment). Coverage closure proves that every catalog rule (`SEG108-R-###`) is independently backed by a business requirement, a test scenario, a test case, and test data — not merely referenced by an AI-generated artifact.

## Coverage Classification

| Status | Meaning |
| --- | --- |
| `COVERED` | Rule has a BR, scenario, test case, and test data, all sharing the canonical source anchor. |
| `PARTIALLY_COVERED` | Rule has at least one link in the BR→TS→TC→TD chain, but the chain is incomplete. |
| `REVIEW_REQUIRED` | Rule is blocked by a `PROVISIONAL` item pending SME/TBA input. |
| `MISSING` | Rule has no BR, scenario, test case, or test data at all. |

## Segment 108 Specific Notes

- Segment 108's message family is exclusive: it is never listed among the Financial Transaction Request's Data Section 3 companions (Element 63's processing rules enumerate 101/102/103/104/111 for Financial Transaction Requests, and 108 only for Loyalty Card Transaction Requests). Do not certify a "Financial Transaction with embedded loyalty data" scenario without a specification citation.
- Fields 12 and 13 in the wire layout are element 148 (Payment Tender Type) then element 147 (Loyalty Track 2 Data) — the positional field order does NOT match ascending element-number order. Any converter/validator that assumes fields are laid out in element-number order will mis-parse this segment.
- Element 143 (Update Code) is the least-resolved field in this catalog: the specification's advice-function narrative (10.9.3) describes more distinct business functions than it has documented codes for (see `PROVISIONAL P-02`).
- Whether Segment 108 (or loyalty data generally) ever appears on the response side is `PROVISIONAL P-03` — the literal request/response section (11.2.2) says no, but this was explicitly challenged during SME intake without a citation being supplied yet.
