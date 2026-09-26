# Key and Key ID Load: SME/TBA Learning Note

## Core Idea

Segment 116 exists to let a device request a new TransArmor encryption Key and its associated Key ID. This is the segment's entire reason for being — everything else in this KB module (the envelope, the response, the companion sub-table) exists to support this one operation.

```text
Device needs a new/refreshed encryption key
  -> TransArmor PKI Encryption and Tokenization Load Request
  -> Data Section 2: Segment 116, Segment Type = 116
  -> Host issues Key ID + Key Data in the TransArmor Load Response
  -> Device stores Key ID; reuses it for every subsequent TransArmor transaction
```

## What Triggers a Key/Key ID Load

The current source extract does not state the business trigger (scheduled rotation, device provisioning, a prior decline, a host-initiated push, etc.). Only the mechanics of the request/response pair are confirmed. Do not assume a trigger; treat it as `SEG116-SME-001` until the external TransArmor document is available.

## Source-Confirmed Facts

| Fact | Source |
|---|---|
| Purpose is "Key and Key ID Load" | Element 85 Segment Type processing rule |
| Segment Type (85) fixed value `116`, Required | Element 85 valid-codes table and processing rule |
| Segment 116 sits in Data Section 2, immediately after Data Section 1 (Elements 55, 63) | Element 63 processing rule |
| Maximum serialized length 50 alphanumeric characters | Element 84 length table |

## SME Questions

1. What business event triggers a device to send a Key/Key ID Load request (scheduled rotation vs. on-demand vs. provisioning)?
2. Beyond Segment Type and Segment Length, what other fields exist in the remaining ~44 bytes of the segment?
3. Is Sequence Number (Element 86) genuinely one of those fields, as a low-confidence AI lead suggests, or is that a misapplied generic rule?

## TBA Rule Pattern

```text
BR: A device requesting a new TransArmor encryption key shall send Segment 116 in Data Section 2 of a TransArmor PKI Encryption and Tokenization Load Request.
TS: Device sends a Key/Key ID Load request and receives an approved response.
TC: Validate Segment Type, Segment Length format and maximum, and (once available) the remaining field content.
TD: Sanitized converter-ready request - BLOCKED until the field layout is known (SEG116-SME-001).
```

## Current Boundary

The validator enforces Segment Type, Segment Length format, and the 50-byte maximum. It does not and cannot enforce anything about the remaining request fields, because their layout is not documented in this workspace's source material.
