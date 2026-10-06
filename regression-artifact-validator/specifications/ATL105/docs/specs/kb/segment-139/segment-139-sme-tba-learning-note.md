# Segment 139 Moneris Day End Batch Balance (Request) Segment: SME and TBA Learning Note

Verified against Section 12.26, Elements 84, 85, 102, 206-208, 214-215.

## Purpose

Retrieves debit totals for the previous period, to be used in the subsequent day-end batch close transaction (Segment 141). Sent once per pay point at day end, ahead of the batch close.

## Field Layout (8 fields)

Segment Type(85, fixed 139), Segment Length(84, 3-digit), Terminal Identifier(102, Var.), SPDH Header(206, 48 — MAC encryption data), Moneris Terminal Identifier(207, 8), Moneris Merchant ID(208, 13), Batch Number(214, 3), Language Indicator(215, 1 — from the Moneris Language Matrix).

All fields Device-sourced. `[PROVISIONAL SEG139-SME-001]` — Field Separator placement is not explicitly summarized in Section 12.26.

## Source References

Section 12.26: lines 14522-14597. [Rule Catalog](coverage/segment-139-rule-catalog.json).
