# Segment 108 Prompt Code / Card Type Routing: SME and TBA Learning Note

Mirrors [Segment 100's Prompt Code note](../segment-100/prompt-code-sme-tba-note.md), scoped to how Card Type (part of Element 78, Prompt Code) identifies a Loyalty transaction.

## What Routes a Transaction to the Loyalty Message Family

Per Appendix E (Valid Card Type Codes, "Used in Financial Transaction Prompt Codes" and "Used in the Table Load Response"):

| Card Type Code | Description | Category |
| --- | --- | --- |
| `040` | Loyalty | Proprietary |

Card Type `040` is the single documented code identifying a Loyalty transaction. Unlike Segment 113 (which has three related check-processing codes: 041/045/046), Segment 108 has exactly one Card Type value.

**Correction note (2026-09-22):** earlier synthetic fixtures in this KB pass used Card Type `060` (Voyager Fleet) by mistake. All Segment 108 fixtures now use Card Type `040` (Loyalty), the correct and specific trigger.

## SME Review Questions

1. Is the Card Type in the Prompt Code `040`?
2. Does the message use the Loyalty Card Transaction Request shape (Segment 100 + Segment 108 required, Segment 114 optional) rather than a Financial Transaction Request?
3. Is this Loyalty transaction combined with any other payment tender (per Payment Tender Type, Element 148), or is it Loyalty-only (`CS`)?

## Dependency Analysis

```text
Card Type code (040)
  -> Loyalty Card Transaction Request message shape (Segment 100 + 108 + optional 114)
  -> Update Code (143) selects the specific loyalty advice function
  -> Payment Tender Type (148) determines whether a second payment card is also involved
```

A test that only checks "Card Type is numeric" is incomplete. It should also confirm the Card Type is `040` specifically and that the resulting message shape matches Section 11.2.1's Data Section 3 list (Segment 108 + optional Segment 114), not the Financial Transaction Request's companion list (101/102/103/104/111).

## Current Validator Boundary

`Segment108PayloadValidator` does not inspect Standard Segment's Card Type at all — it only validates the Loyalty Card Data Segment's own fields. Confirming that Card Type `040` correlates with the message being routed as a Loyalty Card Transaction Request is a cataloged, TBA-reviewed expectation, not a code-enforced cross-segment rule.

## Review Checklist

- Is the Card Type `040`?
- Does the message use the Loyalty Card Transaction Request shape (Segment 100 + Segment 108 required, Segment 114 optional)?
- Is Payment Tender Type (148) consistent with the transaction being Loyalty-only (`CS`) versus a dual-card transaction?
