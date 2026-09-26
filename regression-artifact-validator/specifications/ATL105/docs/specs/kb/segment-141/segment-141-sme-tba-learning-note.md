# Segment 141 Moneris Day End Batch Close (Request) Segment: SME and TBA Learning Note

Verified against Section 12.28, Elements 84, 85, 102, 206-208, 214-215, 217-222.

## Purpose

Closes out each pay point to Moneris; sent once per pay point at day end. Unlike Segment 139 (Balance Request), Segment 141 explicitly documents that **all fields are separated by Field Separator characters** — a clearer wire-format specification than its predecessor.

## Field Layout (14 fields, all Device-sourced)

Segment Type/Length, Terminal Identifier, SPDH Header, Moneris Terminal Identifier, Moneris Merchant ID, Batch Number, Language Indicator, Number of Debits, Debit Dollar Value, Number of Credits, Credit Dollar Value, Number of Corrections, Corrections Dollar Value.

## What Not To Assume

Do not assume Segment 141's field-separator clarity extends backward to Segment 139 — the two segments' documentation quality differs.

## Source References

Section 12.28: lines 14730-14858. [Rule Catalog](coverage/segment-141-rule-catalog.json).
