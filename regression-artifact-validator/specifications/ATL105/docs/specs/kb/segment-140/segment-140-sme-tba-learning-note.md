# Segment 140 Moneris Day End Batch Balance (Response) Segment: SME and TBA Learning Note

Verified against Section 12.27, Elements 84, 85, 102, 206-208, 214, 216-222.

## Purpose

Reiterates the first 6 fields from the Segment 139 request, then appends a Moneris text response and the totals of all debit/credit/correction transactions for the batch.

## Field Layout (14 fields — richest in this batch)

Segment Type/Length (Device-sourced), Terminal Identifier/Moneris Terminal ID/Moneris Merchant ID (Device-sourced echoes), SPDH Header/Batch Number (Moneris-sourced in the response, contrast Device-sourced in the request), Response Display (16 chars, Moneris text), Number of Debits(4)/Debit Dollar Value(19)/Number of Credits(4)/Credit Dollar Value(19)/Number of Corrections(4)/Corrections Dollar Value(19) — all Moneris-sourced, dollar values formatted `+/-9(16)v99`.

## What Not To Assume

Do not assume uniform sourcing — fields genuinely alternate between Device (echoed) and Moneris (new) within this single segment.

## Source References

Section 12.27: lines 14597-14730. [Rule Catalog](coverage/segment-140-rule-catalog.json).
