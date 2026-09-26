# Segment 115 Companion-Segment Compatibility: SME and TBA Note

## Purpose

Segment 115 sits in the Financial Transaction Response's Data Section 2 alongside Segment 112, and — in the EMV variant — alongside Segments 120 and 131. This note captures what is known and disputed about how these segments combine.

## What Is Known

- Segment 112 (Field 16/17/18) and Segment 115 (Field 17/18/19) are both listed as independently conditional segments in the Section 11.1.2 Financial Transaction Response layout table.
- In the EMV Financial Transaction Response, Segment 115 may be followed by Segment 120 (Print Data 2 Segment) for print-data overflow, and always precedes Segment 131 (EMV Response Data Segment).
- Segment 115 is never listed as a companion in any Request message's Data Section 3 (Financial Transaction Request, ECA/TeleCheck Service Transaction Request, Loyalty Card Transaction Request, or CA Public Key File Load Request) — it is exclusively a response-side segment.

## What Is Disputed (`[PROVISIONAL SEG115-SME-004]`)

Section 12.14 states: **"No other data segments are contained in the Financial Transaction Response"** when Segment 115 is present. Read literally, this would mean Segment 112 and Segment 115 cannot co-occur. However, the Section 11.1.2 layout table depicts both as independently conditional slots in the very same response — which would allow both to be present together. The AI Solution Team's own extracted statement (`BR-249-4`) reads the sentence the same restrictive way, so this is not simply an AI misreading; it is a genuine specification ambiguity.

## What Not To Assume

- Do not certify a "Segment 112 + Segment 115 together" test case as either PASS or FAIL without first resolving `SEG115-SME-004` — treat it as `REVIEW_REQUIRED`.
- Do not assume Segment 115 can appear as a Request-side companion segment — no specification citation supports this in any Request message type.
- Do not assume the EMV-specific Segment 115 + 120 + 131 sequence applies to the non-EMV Financial Transaction Response — Segment 120 and Segment 131 are documented only in the EMV variant's layout table.

## Open Question

`SEG115-SME-004` (see the [SME/TBA Input Register](../segment-115-sme-tba-input-register.md)): does "no other data segments are contained" forbid Segment 112 from co-occurring with Segment 115, or does it only mean nothing besides 112 and 115 can appear?
