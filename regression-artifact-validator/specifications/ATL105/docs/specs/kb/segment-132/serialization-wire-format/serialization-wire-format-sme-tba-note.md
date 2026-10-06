# Segment 132 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

Segment 132 uses a 3-digit Segment Length (not 4 — it is not among the seven 4-digit-length segments). Field Separator behavior is only explicitly shown for fields 1-2 and 2-3 via inline markers; no summary sentence documents the remaining fields (`[PROVISIONAL SEG132-SME-004]`).

## What Not To Assume

- Do not assume Segment 132 uses a 4-digit Segment Length — it uses 3.
- Do not assume full delimiter coverage across all 10 fields without SME confirmation.
- Do not assume the 77-byte maximum length is fully reconciled with the 74-byte field-length sum — flag the 3-byte gap.
