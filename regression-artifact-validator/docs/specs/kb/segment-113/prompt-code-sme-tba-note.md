# Segment 113 Prompt Code / Card Type Routing: SME and TBA Learning Note

Mirrors [Segment 100's Prompt Code note](../segment-100/prompt-code-sme-tba-note.md), scoped to how Card Type (part of Element 78, Prompt Code) determines whether a transaction is routed as an ECA/TeleCheck Service Transaction Request at all, and whether Segment 113 specifically should be populated.

## What Routes a Transaction to the ECA/TeleCheck Message Family

Per Appendix E (Valid Card Type Codes, "Used in Financial Transaction Prompt Codes"), three Card Type codes identify check processing:

| Card Type Code | Description | Check Processor |
| --- | --- | --- |
| `041` | Certegy (Formerly Telecredit) | Check |
| `045` | Generic check | Check |
| `046` | ECA/TeleCheck® Service (ECA and Paper Warranty/Verification) | Check |

All three are "Check" card types and all three transactions use the ECA/TeleCheck Service Transaction Request message shape (Segment 100 + Segment 110 [+ 111] [+ 113]) per Section 11.3.1 — the message family is not literally named after Card Type 046 alone; it is the umbrella format for all check-service processing. However, Segment 113 itself carries **ECA/TeleCheck-specific** risk-control data (Clerk ID, Product Code, Trace ID, Denial Record Number), so it is most meaningfully populated when the actual processor in use is ECA/TeleCheck (`046`), per Section 10.8.1's processor list (SCAN (ETC), ECA/TeleCheck® service, Certegy).

**Correction note (2026-09-22):** earlier synthetic fixtures in this KB pass used Card Type `070` (Valero Fleet) by mistake. All Segment 113 fixtures now use Card Type `046` (ECA/TeleCheck Service), the correct and specific trigger.

## SME Review Questions

1. What did the POS/clerk initiate — a check purchase, a check Void, or a Denial Record scenario?
2. Which check-processing service is configured for this merchant: SCAN (ETC), Certegy, or ECA/TeleCheck®?
3. Does the Card Type in the Prompt Code (`041`/`045`/`046`) match the configured processor?
4. If the processor is not ECA/TeleCheck (`046`), should Segment 113 still be sent, or does the merchant's check-processing configuration omit it? (Cataloged as `SEG113-R-002`'s applicability condition — resolved as conditional per SME intake 2026-09-22, not required for every check-processing Card Type.)

## Dependency Analysis

```text
Card Type code (041 / 045 / 046)
  -> check-processing service identity
  -> ECA/TeleCheck Service Transaction Request message shape (Segment 100 + 110 [+111] [+113])
  -> Segment 113 populated when the specific processor is ECA/TeleCheck (046)
```

A test that only checks "Card Type is numeric" is incomplete. It should also confirm the Card Type is one of the documented check-processing codes and that the resulting message shape (companion segments) matches Section 11.3.1's Data Section 3 list.

## Current Validator Boundary

`Segment113PayloadValidator` does not inspect Standard Segment's Card Type at all — it only validates the ECA/TeleCheck Data Segment's own fields when that segment is present. Confirming that Card Type `046` (specifically) correlates with Segment 113's presence is a cataloged, TBA-reviewed expectation, not a code-enforced cross-segment rule (same treatment as the Extended MICR Data cross-segment note).

## Review Checklist

- Is the Card Type one of `041`, `045`, or `046`?
- Does the message use the ECA/TeleCheck Service Transaction Request shape (Segment 100 + Segment 110 required, Segment 111 optional, Segment 113 conditional)?
- If Card Type is `046`, is Segment 113 populated with ECA/TeleCheck-specific data (Clerk ID at minimum)?
