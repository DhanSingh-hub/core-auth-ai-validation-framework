# Segment 113 Account Number and MICR Relationship: SME and TBA Learning Note

Mirrors [Segment 100's Account Number and Entry Method note](../segment-100/account-number-sme-tba-note.md), scoped to the check/ECA/TeleCheck context.

## Core Idea

Segment 113 has no Account Number field of its own. The cardholder/check-writer's account identity for a check transaction lives in **Standard Segment (100)'s Element 2 (Account Number)**, but for check transactions its representation is different from a card PAN: it is derived from the check's MICR (Magnetic Ink Character Recognition) line.

```text
Check swiped or keyed at POS
  -> MICR data captured (routing number + account number + check number)
  -> Standard Segment Element 2 (Account Number) carries a MICR-derived value
  -> Check Data Segment (110) Element 122 (MICR Data) carries the full/raw MICR line
  -> if raw MICR > 50 bytes, Segment 113 Element 137 (Extended MICR Data) carries the remainder
```

## Why This Matters for Segment 113

Element 122's own processing rule makes the cross-segment relationship explicit: *"MICR Data is also included in Account Number (Element No. 2) of the Standard Message Data Segment... If MICR data > 50 bytes and the prompt code is ECA/TeleCheck® check service then refer ECA/TeleCheck® Data Segment (Segment No. 113)."* This ties together three separate elements across two segments (100 and 110) plus a conditional overflow into a third (113):

| Element | Segment | Role |
| --- | --- | --- |
| Element 2 (Account Number) | Segment 100 (Standard) | MICR-derived account identity, same value family as Element 122 |
| Element 122 (MICR Data) | Segment 110 (Check Data) | Up to 50 bytes of raw MICR data |
| Element 137 (Extended MICR Data) | Segment 113 (ECA/TeleCheck Data) | Supplements Element 122 when raw MICR exceeds 50 bytes |

## SME Reasoning

1. Was the check swiped (MICR read electronically) or manually keyed?
2. Does the raw MICR data exceed 50 bytes? If so, Extended MICR Data (137) in Segment 113 must also be populated.
3. Is the Card Type (Element 14, via Prompt Code) one of the check-processing codes (`041` Certegy, `045` Generic check, `046` ECA/TeleCheck Service)? See [Prompt Code / Card Type Routing](prompt-code-sme-tba-note.md).
4. Does Standard Segment Element 2 (Account Number) look like a MICR-derived value rather than a card PAN, consistent with a check transaction?

## TBA Dependency Chain

```text
Check entry method (swiped/manual)
  -> MICR captured
  -> Standard Segment Element 2 (Account Number, MICR-derived)
  -> Check Data Segment Element 122 (MICR Data, up to 50 bytes)
  -> raw MICR > 50 bytes?
       yes -> Segment 113 Element 137 (Extended MICR Data) required
       no  -> Segment 113 Element 137 not needed
```

## Current Validator Boundary

Per SME direction (`SEG113-SME-004`), the payload validator does **not** cross-check Segment 100's Account Number or Segment 110's MICR Data against Segment 113's Extended MICR Data. It only validates Extended MICR Data's own type/length bound (`SEG113-R-014`) when the field is present. The cross-segment consistency (does 122 + 137 actually reconstruct a valid MICR line?) remains a manual/TBA review item, not a code-enforced rule.

## Review Checklist

- Is the Account Number (Segment 100, Element 2) consistent with a MICR-derived value for this transaction?
- If raw MICR exceeds 50 bytes, is Extended MICR Data (Segment 113, Element 137) populated?
- Does the Card Type in the Prompt Code match one of the documented check-processing codes (041/045/046)?
