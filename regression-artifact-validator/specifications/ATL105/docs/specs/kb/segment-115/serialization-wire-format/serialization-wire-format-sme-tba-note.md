# Segment 115 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains how Segment 115 (Print Data Segment) moves from response-side business intent into the serialized ATL105 message. Segment 115 introduces the first documented **no-trailing-separator** wire format in this KB.

## Three Representations

```text
Host-side print-data intent (Blackhawk T&Cs, or inferred loyalty receipt data)
  -> structured response-data JSON (Print Data Segment container)
  -> serialized ATL105 Segment 115
  -> TCP/IP framed payload (Data Section 2 of the Financial Transaction Response)
```

## The Missing Trailing Separator

Segments 108 and 114 both explicitly document a Field Separator following their **last** field, even when it is empty. Segment 115's specification text is different: **"There is a Field Separator between Field Nos. 1 and 2 and between Field Nos. 2 and 3."** It does not add "and a Field Separator follows Field No. 3" the way Segment 114's note does. This is independently corroborated by the AI Solution Team's own extracted statement (`BR-249-5`). Always serialize Segment 115 with exactly **two** separators, not three.

## The 4-Digit Segment Length

Segment 115 requires a 4-digit Segment Length, one of exactly seven segments across the specification with this requirement (103, 114, 115, 118, 120, 130, 131) — confirmed independently via Segment 120's cross-segment Segment Length rule catalog.

## Segment Length Upper Bound — Three-Way Conflict

`[PROVISIONAL SEG115-SME-001]` — Section 12.14's opening statement says the maximum content length is 1,009 alphanumeric characters, independently corroborated by the AI Solution Team's `BR-249-3`. However:
- The Financial Transaction Response layout table (Section 11.1.2) states **910** for the Segment 115 row.
- The EMV Financial Transaction Response layout table states **999** (which may be conflating the total segment length with Element 152's own field length).

Do not pick a maximum without SME confirmation; the proposed resolution (1,009 authoritative) is not yet final.

## What Not To Assume

- Do not serialize a trailing Field Separator after Print Data — Segment 115 is the first documented exception to that pattern.
- Do not assume the 999 figure from the EMV layout table is the total segment length — it may only describe the Print Data field's own capacity.
- Do not assume Segment 115's origin is the device — every field's source is Host (contrast Segment 114).
