# Segment 142 Moneris Day End Batch Close (Response) Segment: SME and TBA Learning Note

Verified against Section 12.29, Elements 84, 85, 102, 206-208, 214, 216, 210.

## Purpose

Responds to Segment 141 with a special format: a reiteration of the first 6 request fields, followed by a Moneris text response, followed by a MAC (Message Authentication Code) that validates the response at the terminal.

## Field Layout (9 fields)

Segment Type/Length (Host-sourced — contrast Segment 140's Device-sourced Segment Type/Length), Terminal Identifier/Moneris Terminal ID/Moneris Merchant ID (Device-sourced echoes), SPDH Header/Batch Number/Response Display (Moneris-sourced), MAC (Element 210, 16 characters, Moneris-sourced — validates the response at the terminal).

## What Not To Assume

Do not assume Segment 142's Segment Type/Length sourcing matches Segment 140's — 140 is Device-sourced, 142 is Host-sourced, despite both being response segments in the same Moneris family.

## Source References

Section 12.29: lines 14858-14935. [Rule Catalog](coverage/segment-142-rule-catalog.json).
