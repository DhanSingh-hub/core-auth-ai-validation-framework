# Segment 113 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains how Segment 113 (ECA/TeleCheck® Data Segment) moves from business and logical test-data representation into the serialized ATL105 message.

## Three Representations

```text
ECA/TeleCheck business intent
  -> structured test-data JSON (ECA/TeleCheck Data Segment container)
  -> serialized ATL105 Segment 113
  -> TCP/IP framed payload (Data Section 3 of the ECA/TeleCheck Service Transaction Request)
```

| Representation | Main question | Example validation |
| --- | --- | --- |
| Business intent | Is this actually an ECA/TeleCheck Service Transaction, not a Financial Transaction? | Check purchase, Void, denial-record decline |
| Logical JSON | Are Segment 113 fields represented structurally? | 9 fields, Segment Type through Extended MICR Data |
| ATL105 message | Are fields encoded in the required order and delimiters? | Field separators, Segment Length, ascending element order |
| TCP/IP payload | Is the message framed for transport? | Message length, TPDU, byte order (inherited from Segment 100) |

## Field Order

Unlike Segment 108, Segment 113 has **no field-order reversal quirk**. Fields 3 through 9 map directly to elements 131 through 137 in ascending numeric order. Only the universal Segment Type (85) / Segment Length (84) pair is out of numeric order, which is standard across every ATL105 segment, not a Segment-113-specific anomaly.

## Field Separator Rule

All 9 fields are separated by Field Separators. When a field is not populated, its Field Separator is still sent.

## Segment Length

Segment Length (Element 84) is a fixed 3 digits for Segment 113. The maximum content length is 156 alphanumeric characters (`SEG113-R-005`).

## Cross-Segment Note (Extended MICR Data)

Element 137 (Extended MICR Data), though it lives in Segment 113, is documented as a supplement to Element 122 (MICR Data) in a **different** segment — Segment 110 (Check Data Segment) — when the raw MICR data exceeds 50 bytes. This is a genuine cross-segment wire-format dependency. Per SME direction (`SEG113-SME-004`), the payload validator does not enforce this relationship; it is cataloged only.

## What Not To Assume

- Do not assume Segment 113 ever needs a 4-digit Segment Length — no such variant is documented for this segment.
- Do not assume Element 137 belongs logically to Segment 110 just because it supplements Segment 110's Element 122 — it is wire-formatted as part of Segment 113's own field layout (field 9).
- Do not assume Segment 113 appears in the ECA/TeleCheck Service Transaction Response — Section 11.3.2 states the response mirrors the generic Financial Transaction Response layout, and this was not disputed during SME intake (unlike Segment 108's response-presence question).
