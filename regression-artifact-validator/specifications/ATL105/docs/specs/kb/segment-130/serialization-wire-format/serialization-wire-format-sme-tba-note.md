# Segment 130 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains Segment 130's wire format, including its repeating EMV Additional Information Section — the same repetition pattern already documented for Segment 111's Variable Information Section.

## Fixed Fields vs Repeating Section

Segment 130 has 6 fixed fields (Segment Type, Segment Length, CA Public Key File Checksum, EMV Card Sequence Number, EMV Chip Data Length, EMV Chip Data) followed by zero or more repetitions of a 3-field EMV Additional Information Section (Indicator, Length, Information).

## Field Separator Rules

- A Field Separator appears between every pair of fixed fields (1-2, 2-3, 3-4, 4-5, 5-6), even when a field is empty.
- A Field Separator appears between field 6 and the start of the EMV Additional Information Section.
- **No** Field Separators appear within a repetition (between Indicator/Length/Information) or between repetitions.
- Exactly **one** Field Separator follows the **final** repetition (i.e., it is not duplicated per repetition).

This is structurally identical to Segment 111's Variable Information Section rule (`SEG111-R-007`) — reuse that validator logic pattern rather than reinventing it.

## The 4-Digit Segment Length

Segment 130 requires a 4-digit Segment Length, one of exactly seven segments across the specification with this requirement (103, 114, 115, 118, 120, 130, 131).

## Maximum Length

Section 12.20 states 3,043 alphanumeric characters. Independent arithmetic (6 fixed fields + up to 2,000-byte repeating section + 7 separators) closely corroborates this. `[PROVISIONAL SEG130-SME-001]` — a possible second figure in the generic Financial Transaction Request layout table's Segment 130 row was OCR-ambiguous and could not be confidently transcribed; flagged for visual confirmation rather than asserted as a real conflict.

## What Not To Assume

- Do not emit a Field Separator between EMV Additional Information Section repetitions — only one follows the last.
- Do not assume the repeating section is required — it is entirely optional (zero repetitions is valid).
- Do not assume Segment 130's TLV-encoded EMV Chip Data can be validated for cryptographic authenticity without a certified EMV kernel/HSM (`SEG130-R-010`).
