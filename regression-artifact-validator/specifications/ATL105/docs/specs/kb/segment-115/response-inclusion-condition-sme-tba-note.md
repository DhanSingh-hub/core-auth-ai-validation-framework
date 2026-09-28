# Segment 115 Response-Inclusion Condition: SME and TBA Note

## Purpose

Segment 115 is the first response-only, conditionally-included segment trained in this KB (Segments 100, 108, 114 are all request-side and either required or simply optional). This note isolates the specific business/technical condition that determines whether Segment 115 appears at all.

## What Is Known

- Element 115 (Additional Information Data Segment Flag) must indicate that a segment follows (documented values: `0` = none follows, `1` = follows).
- The request's Loyalty Information Version (Element 150, defined in Segment 108's catalog) must equal `2`.
- Both conditions are stated together in Section 11.1.2's Data Section 2 layout table for Field No. 17/18/19.

## What Is Not Known (`[PROVISIONAL SEG115-SME-002]`)

- Element 115's binary flag does not, by itself, distinguish "Segment 112 follows" from "Segment 115 follows" — both segments are conditionally listed using the same flag element in the same table, with Segment 115 carrying an *additional* condition (Loyalty Information Version = 2) that Segment 112 does not.
- It is not documented whether Segment 112 and Segment 115 can BOTH follow in the same response (see the related, but distinct, [companion-compatibility note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) for the "no other data segments" ambiguity).

## What Not To Assume

- Do not assume Element 115 has undocumented values (e.g., `2` for "Segment 115 follows") without a citation — the only documented values are `0` and `1`.
- Do not assume Loyalty Information Version = 2 is sufficient on its own — Element 115 must ALSO indicate a segment follows.
- Do not assume this condition applies only to non-EMV Financial Transaction Responses — the EMV Financial Transaction Response uses the same two-condition trigger (Section 11.1.2-EMV).

## Open Question

`SEG115-SME-002` (see the [SME/TBA Input Register](../segment-115-sme-tba-input-register.md)): what is the complete decision logic that determines whether a Financial Transaction Response includes Segment 112, Segment 115, both, or neither?
