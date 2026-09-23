# Segment 103 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains how Segment 103 (EBT Data Segment) moves from business and logical test-data representation into the serialized ATL105 message, and highlights the one rule in this segment that is genuinely different from every other segment trained so far: **the request and response serialize differently.**

## Three Representations

```text
EBT/WIC business intent
  -> structured test-data JSON (EBT Data Segment container)
  -> serialized ATL105 Segment 103 (request OR response form)
  -> TCP/IP framed payload (Data Section 3 companion of Segment 100)
```

| Representation | Main question | Example validation |
| --- | --- | --- |
| EBT/WIC business intent | Is EBT-specific data actually required for this transaction? | SNAP/EBT, WIC/eWIC, HIP program, clerk/voucher involvement |
| Logical JSON | Are Segment 103 fields represented structurally? | Clerk ID, Voucher ID, WIC Discount Amount, WIC Product Data, EBT Program Data |
| ATL105 message | Are fields encoded in the required order and delimiters, for the correct message direction? | Field separators present (request) vs. absent (response); Segment Length 3 vs. 4 digits |
| TCP/IP payload | Is the message framed for transport? | Message length, TPDU, byte order (inherited from Segment 100) |

## The Request-vs-Response Separator Rule

This is `SEG103-R-007` (request) and `SEG103-R-008` (response), and it is the single most important serialization fact for this segment:

- **Request**: every Segment 103 field is separated by a Field Separator. An empty conditional field still emits its separator so that downstream fields keep their positional meaning.
- **Response**: there is **no** Field Separator between Segment 103 fields.

A converter or validator that assumes symmetric request/response serialization (as is common for many other segments) will silently mis-parse Segment 103 responses. Test data must tag which direction (request or response) a fixture represents, and the serialization check must branch on that tag.

## Segment Length and the 4-Digit Note

Segment Length (Element 84) is normally 3 digits, but the specification explicitly notes: *"Length should be '4' for EBT with eWIC data transactions."* This means:

- A plain EBT (non-eWIC) message may use a 3-digit Segment Length.
- An EBT message carrying eWIC data (WIC Discount Amount and/or WIC Product Data populated) should use a 4-digit Segment Length, because the WIC fields can push the segment well past 999 bytes (WIC Product Data alone allows up to 3,001 bytes).

Do not treat "3 digits" as a universal constant the way Segment 101's base Segment Length is fixed at 3 digits.

## What Not To Assume

- Do not apply the request separator-preservation rule to a response payload.
- Do not assume Segment Length is always 3 digits — check whether eWIC data is present.
- Do not assume WIC Product Data or EBT Program Data subelements are simple scalar strings; they are structured (Total Length + one or more tagged subelements) and must be validated as such.
- Do not compute Element 63 (segment count in Data Section 1) from JSON object counts — count the final wire-serialized segments, same caution as for Segment 101.
