# Segment 157 Adjusted Product Code Data Segment: SME and TBA Learning Note

Verified against Section 12.41, Elements 62, 76, 77, 81, 84, 85, 87, 106, 107, and Segment 100 Elements 17, 41, 58, 99.

## Key Rules

- **Comdata-exclusive**: any non-Comdata transaction with this segment is declined.
- **Mutually exclusive with Segment 102**: sending both causes a decline.
- **Product code cap**: no codes above 899, except 955 (Cash Back) — violation causes a decline.
- **Fuel-first ordering, max 10 products.**
- **Cross-segment amount reconciliation**: sum of Adjusted Product Amounts must equal Segment 100's Fuel Purchase Amount (41) + Nonfuel Amount (58) + Tax Amount (99) + Cash Amount (17).
- **No separate tax/discount/coupon product codes** — these are folded into each product's adjusted amount.
- **Multi-fuel support**: multiple Fuel Type Codes allowed in one transaction (e.g., Diesel + DEF + Reefer); primary fuel must be first.

## The Dual-Delimiter Scheme (Similar to Segment 143, Roles Adjusted)

- Field Separator (▲): follows Segment Type/Length; follows Adjusted Product Amount ONLY if it's the last element in the segment.
- Product Data Field Delimiter (\\): ALWAYS follows Quantity and Unit Price; follows Adjusted Product Amount when NOT the last element.

## Quantity/Unit Price Decimal Encoding (Error-Prone)

Values must encode assumed decimal places as full-width digits: quantity 0.05 at 2 decimal places = `'205'`, NOT `'25'` (explicitly called out as invalid in the spec).

## Worked Example

```text
157+127+103020U3000010000\3000003000\000000030000\400U3000001000\3000010000\
000000001100\401U3000002000\3000010000\000000002200+
```

## Source References

Section 12.41: lines 16904-17102. [Rule Catalog](coverage/segment-157-rule-catalog.json).
