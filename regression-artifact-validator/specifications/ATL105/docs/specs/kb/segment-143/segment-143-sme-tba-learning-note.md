# Segment 143 Tax by Product Data Segment: SME and TBA Learning Note

Verified against Section 12.30, Elements 62, 77, 84, 85, 223-231, and its 5 worked examples.

## 1. What Segment 143 Means

Segment 143 reports up to 3 taxes per product for up to 10 products, always paired with a Segment 102 (Product Code Data Segment) entry-for-entry in matching order. This is the most structurally intricate segment trained in this session — it uses **two different delimiter characters** for two different structural boundaries.

## 2. The Dual-Delimiter Scheme (Most Distinctive Rule)

- **Field Separator (▲)**: follows the LAST tax amount reported for a product, signaling "move to the next product" (or "end of segment" for the final product).
- **Tax by Product Field Delimiter (\\)**: follows a tax amount ONLY when there is ANOTHER tax amount for the SAME product.

This is the only segment in this KB where a repeating group uses two distinct separator characters for two distinct boundary types (within-product vs between-product), rather than a single separator character or no separators at all.

## 3. The "N" Flag Omits Fields Entirely

When the Inclusive/Exclusive flag is `N` (tax not applicable), the Tax Type and Tax Amount fields are **omitted entirely** — not sent as empty fields. This changes the byte-level structure of that tax sub-entry (2 fewer fields), unlike most "optional" fields elsewhere in this KB which are simply empty-but-present between separators.

## 4. Worked Examples (from Section 12.30)

```text
Single product, one inclusive GST tax, no other tax:
  102.033.S01020L318198\31099\2000.
  143.023.01020IGST200.

Two products, one with no tax (itself a tax), one with 2 taxes:
  143.032.02963N.400EGST25\IPST50.

Nine products, mixed tax scenarios:
  143.097.09963N.102EGST65.400EGST25...
```

## 5. Applicability: Segment 143 Requires Segment 102

A Financial Transaction Request including Segment 143 MUST also include Segment 102, with matching product entries in the same order (`SEG143-R-001`). Do not certify Segment 143 as usable standalone.

## 6. Validator Rules Planned

- Requires paired Segment 102 with matching product count/order.
- Number of Products must match Segment 102's count.
- Per-product: I/E flag in {I,E,N}; if N, Tax Type/Amount omitted; Tax Type in {GST,HST,PST} (Canadian; other jurisdictions provisional, `SEG143-SME-001`).
- Correct dual-delimiter usage (\\ within a product, ▲ between products).
- Only format validation — no value-level business judgement (`SEG143-R-002`).

## Source References

Section 12.30: lines 14935-15191. [Rule Catalog](coverage/segment-143-rule-catalog.json).
