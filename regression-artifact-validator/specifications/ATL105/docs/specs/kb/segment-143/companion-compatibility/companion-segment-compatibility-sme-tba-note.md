# Segment 143 Companion-Segment Compatibility: SME and TBA Note

## What Is Known

- Segment 143 MUST be paired with Segment 102 (Product Code Data Segment) in the same Financial Transaction Request, with matching product entries in the same order.

## What Not To Assume

- Do not certify Segment 143 as valid without a paired Segment 102.
- Do not assume the product order can differ between Segment 102 and Segment 143 — the specification requires the same order.
