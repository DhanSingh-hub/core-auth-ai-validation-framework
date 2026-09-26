# Segment 114 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains how Segment 114 (SKU Data Segment) moves from business and logical test-data representation into the serialized ATL105 message. Segment 114 has the simplest wire format of any segment trained so far — only 3 fields, no field-ordering irregularities, and no interdependent fields — but it has one notable difference from Segment 108: a 4-digit Segment Length.

## Three Representations

```text
Loyalty business intent (a bar code SKU was scanned)
  -> structured test-data JSON (SKU Data Segment container)
  -> serialized ATL105 Segment 114
  -> TCP/IP framed payload (Data Section 3 of the Loyalty Card Transaction Request)
```

| Representation | Main question | Example validation |
| --- | --- | --- |
| Loyalty business intent | Was a bar code SKU actually scanned for this transaction? | If not, Segment 114 is omitted entirely — it is optional, not "present but empty". |
| Logical JSON | Are the 3 Segment 114 fields represented structurally? | SegmentType, SegmentLength, SkuData. |
| ATL105 message | Are fields encoded with the required 4-digit Segment Length and delimiters? | Field separators, 4-digit Segment Length (not 3), SKU Data up to 1000 characters. |
| TCP/IP payload | Is the message framed for transport? | Message length, TPDU, byte order (inherited from Segment 100). |

## The 4-Digit Segment Length

Segment 108 uses a 3-digit Segment Length. Segment 114 requires **4 digits**, valid values `0001`-`1010`. Segment 114 is one of only seven segments across the entire ATL105 specification with this requirement: EBT Data Segment (103), **SKU Data Segment (114)**, Print Data Segment (115), Proprietary Data Load Segment (118), Print Data 2 Segment (120), EMV Request Data Segment (130), and EMV Response Data Segment (131). Always serialize (and expect to deserialize) Segment 114's length as 4 digits — a converter that assumes the more common 3-digit format (as used by Segment 108, and most other segments) will misparse this segment.

## Field Separator Rule

All 3 fields are separated by Field Separators. A Field Separator follows Field No. 3 (the last field) per Section 12.13's note — and by the general segment rule, an empty field still sends its separator.

## Segment Length Upper Bound

**RESOLVED 2026-09-26 (`SEG114-SME-001`)** — Section 12.13's opening statement and its own valid-values range (`001-1010`) both say the maximum content length is 1010 alphanumeric characters; this is confirmed authoritative. The Loyalty Card Transaction Request layout table's conflicting Max. Len. of 1009 (Section 11.2.1) is a spec table typo — the same class of table-vs-body discrepancy already resolved for Segment 108 (142 vs 84).

## What Not To Assume

- Do not serialize Segment 114's length as 3 digits — it is always 4 digits, unlike Segment 108.
- Segment Type fixed value 114 is confirmed enforceable (`SEG114-SME-004`, resolved 2026-09-26) even though Section 12.13, unlike Section 12.14 (Segment 115), does not print an explicit "Fixed value: 114" phrase.
- Do not assume Segment 114 ever needs a "present but empty" wire representation — it is either fully populated (3 fields, all separators present) or entirely omitted from the message.
- Segment 114 can repeat within a message, once per scanned SKU (`SEG114-SME-006`, resolved 2026-09-26) — do not serialize it as a single, non-repeating block.
