# Host Discount Data (Prompt Code 904): SME/TBA Learning Note

## Core Idea

Prompt Code 904 delivers discount programs the host wants applied at the pump/POS for matching card BIN ranges and product codes, valid over a date/time window. This is the most structurally complex payload in Segment 118 - a 12-field repeating block.

```text
Totals Response signals Host Discount pending (Response Code D/E or M/N)
  -> Device sends Proprietary Data Load Request, Prompt Code 904
  -> Host responds with one or more discount programs
  -> Device applies host discount timestamp
  -> Later Financial Transaction Requests matching the BIN/Card Type/Product Code
     carry the discount via Segment 102 (Product Code Data Segment), using
     Product Code 941 (post-pay, negative amount) or 991 (pre-pay, administrative amount)
```

## Source-Confirmed Facts

| Field | Element | Type / Length | Role |
|---|---|---|---|
| Host Discount Timestamp | 179 | N, 12 (CCYYMMDDHHMM) | New timestamp to use after a successful load |
| Number of Discounts | 171 | N, 2 | Count of repeat blocks that follow |
| Start Date / Start Time | 165 / 166 | N, 8 / N, 4 | Discount validity window start |
| End Date / End Time | 165 / 166 | N, 8 / N, 4 | Discount validity window end |
| BUYPASS Card Type | 173 | N, 4 | Matched against the BIN table's Card Type to determine applicability |
| Card BIN Range, Beginning | 183 | N, 12 | Start of the BIN range the discount applies to |
| Card BIN Range, Ending | 184 | N, 12 | End of the BIN range |
| Product Code | 77 | N, 3 | The product the discount applies to |
| Product Discount Amount | 172 | N, 5 | The discount amount for that product |
| Product Code (second) | 77 | N, 3 | The product code the device sends in Segment 102 to identify the discount type |
| Discount Quantity Limit | 185 | N, 3 | Quantity limit for the discount |
| Discount Program Description | 186 | AN, 15 | Human-readable description |

**Cross-segment rule:** the actual discount amount applied to a transaction travels in a *different* segment (102, Product Code Data Segment) using Product Code `941` (negative amount, post-pay) or `991` (administrative amount, pre-pay) — not inside Segment 118 itself.

## SME Questions

1. Element 77 (Product Code) appears twice in the same block (fields 23 and 25) with different descriptions — "the Product Code to which a Discount applies" vs. "the Product Code for the device to send". Are these ever different values in practice, or is the second always a copy/derivation of the first?
2. Is there a maximum Number of Discounts beyond what the 3,600-byte total implies?
3. What determines whether Product Code 941 vs. 991 is used for a given discount program — is it a per-program flag, or a fixed environment-wide (post-pay vs. pre-pay) setting?

## TBA Rule Pattern

```text
BR: A Financial Transaction Request applying a Host Discount shall include the associated Product Code (941 or 991) in Segment 102, matching a discount program previously loaded via Prompt Code 904.
TS: Device loads one discount program, then submits a matching Financial Transaction Request.
TC: Validate the discount block field count/lengths and the Segment 102 cross-reference.
TD: Sanitized converter-ready 904 response plus a matching Financial Transaction Request fixture.
```

## Current Boundary

The validator checks the fixed-position header fields (timestamp, count) and the per-block field formats. It does not yet cross-validate against a companion Segment 102 fixture, since that requires a full transaction-level test harness beyond Item 1.
