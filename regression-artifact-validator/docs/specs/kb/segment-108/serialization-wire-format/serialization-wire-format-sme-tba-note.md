# Segment 108 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains how Segment 108 (Loyalty Card Data Segment) moves from business and logical test-data representation into the serialized ATL105 message, and highlights the field-ordering quirk unique to this segment.

## Three Representations

```text
Loyalty business intent
  -> structured test-data JSON (Loyalty Card Data Segment container)
  -> serialized ATL105 Segment 108
  -> TCP/IP framed payload (Data Section 3 of the Loyalty Card Transaction Request)
```

| Representation | Main question | Example validation |
| --- | --- | --- |
| Loyalty business intent | Is this actually a Loyalty Card Transaction, not a Financial Transaction? | Add account, coupon/points redemption, account inquiry, sale update, totals report |
| Logical JSON | Are Segment 108 fields represented structurally? | 15 fields, Loyalty Program ID through Unit of Work |
| ATL105 message | Are fields encoded in the required *positional* order and delimiters? | Field separators, Segment Length, the 148-before-147 field order |
| TCP/IP payload | Is the message framed for transport? | Message length, TPDU, byte order (inherited from Segment 100) |

## The Field-Order Quirk

Section 12.7's layout table lists fields in this order: ... Field 11 = Expiration Date (146), **Field 12 = Payment Tender Type (148)**, **Field 13 = Loyalty Track 2 Data (147)**, Field 14 = Loyalty Information Version (150), Field 15 = Unit of Work (151).

Fields 12 and 13 are element 148 then element 147 — the *reverse* of their element-number order. This is the only field-order irregularity in Segment 108's layout, and it is easy for a converter or validator that assumes "fields appear in ascending element-number order" to get wrong. Always serialize (and expect to deserialize) by **field position**, not by element number.

## Field Separator Rule

All 15 fields are separated by Field Separators. A Field Separator also follows Field No. 13 per the specification note ("with a Field Separator following Field No. 13") — and by the general segment rule, empty fields still send their separator, all the way through field 15.

## Segment Length

Segment Length (Element 84) is a fixed 3 digits for Segment 108 (unlike Segment 103, which sometimes needs 4 digits for eWIC data). The maximum content length is 142 alphanumeric characters (`SEG108-R-005`, confirmed authoritative over a conflicting "84" figure found elsewhere in the spec — see `SEG108-SME-001`).

## What Not To Assume

- Do not serialize fields 12-13 in element-number order (147 before 148) — the correct positional order is 148 then 147.
- Do not assume Segment 108 ever needs a 4-digit Segment Length — no eWIC-style variant is documented for this segment.
- Do not assume Segment 108 appears in the Loyalty Card Transaction Response without checking `SEG108-SME-003` (open) — the literal spec text says the response mirrors the generic Financial Transaction Response (implying Segment 108 is request-only), but this was disputed during SME intake without a citation yet supplied.
