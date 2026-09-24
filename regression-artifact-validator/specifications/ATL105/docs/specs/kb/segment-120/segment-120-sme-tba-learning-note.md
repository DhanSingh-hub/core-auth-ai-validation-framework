# Segment 120: SME and Technical Business Analysis Learning Note

## Purpose

Segment 120 (Print Data 2 Segment) is the ATL105 2026-3 Section 12.18 companion
segment that carries large blocks of receipt/print text on a **response**
message. It is a small segment (3 fields) but an instructive one: its
knowledge base was built directly from the same AI Solution pipeline
(`src_Harit_Latest_AI_Sol/src/pipeline`) used to validate the other segments,
and that process surfaced a genuine internal inconsistency in the AI-generated
artifacts themselves — not merely an SME ambiguity. See
[Section 6](#6-the-headline-finding-an-ai-artifact-contradiction) below.

This note is a learning guide, not an approved client requirement set.

## 1. The Correct Mental Model

```text
Host completes transaction processing
  -> response requires large print data (receipt, activation confirmation, etc.)
  -> Segment 120 built
  -> Segment Type, Segment Length, Print Data fields populated
  -> serialized into Data Section 3 of the Financial Transaction Response
  -> delivered to POS/device for printing
```

Unlike Segment 100, 101, or 111 — all of which are request-side — Segment 120
only ever appears in a **Financial Transaction Response** or
**EMV Financial Transaction Response**. There is no request-side analogue.

## 2. SME Reasoning: When Is Segment 120 Actually Used?

A card/POS SME should answer these questions before defining test data:

1. Does this response scenario require print data large enough that the
   smaller [Print Data Segment (No. 115)](../segment-compatibility-matrix.md)
   is insufficient? (The spec draws this distinction but does not give a
   precise byte-count threshold between the two segments — flag as an open
   question if a threshold is needed for test data generation.)
2. Is this a Blackhawk phone activation/recharge flow, where Print Data
   contains `\`-delimited receipt lines? (BR-263-5)
3. Is the response an ordinary Financial Transaction Response or an EMV
   Financial Transaction Response? (Both are valid contexts per the AI
   Solution's relationship evidence: `REQ-SRC-ATL105-PDF-001:3711` and
   `REQ-SRC-ATL105-PDF-001:4068`.)
4. Where does Segment 120 sit relative to the response's other companion
   segments (112, 115, 134, 136, 146, 148, 152, 155)? See Section 6.

## 3. Segment 120 Field Responsibilities

| Field | Element | SME interpretation | Common analysis risk |
| --- | ---: | --- | --- |
| Segment Type | 85 | Identifies Segment 120 | Confusing with the request-side Segment 100 Segment Type field, which is a different element occurrence |
| Segment Length | 84 | Length of the encoded segment, including Segment Type's length and Field Separators; always exactly 4 digits, zero-padded (`P-02-WIDTH` resolved) | Assuming a 3-digit fixed width like Segment 100/101/111 |
| Print Data | 152 | Free-form receipt/print text, up to 999 characters, with an optional `\` line delimiter for Blackhawk flows | Treating the `\` delimiter as a parsing requirement for every transaction rather than a Blackhawk-specific convention |

## 4. Segment 120 Is Response-Only

Segment 100's [companion-compatibility flow](../segment-100/companion-compatibility/companion-segment-compatibility-flow.md)
reasons about which Section 3 segments a **request** needs. Segment 120 sits
outside that flow entirely: it is a **response**-side companion, alongside
Segments 112 (Additional Information), 115 (Print Data), 134 (Transaction
Attributes), 136 (Moneris Response), 146 (Enhanced Fleet Response), 148 (WEX
Available Product Fleet Information), 152 (Response InComm OTC Market Basket
Data), and 155 (Real Time Account Updater Response). See the
[companion-compatibility note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
for the full picture.

## 5. Serialization Thinking

Segment 120 has the simplest serialization shape in this knowledge base so
far: exactly 3 fields, exactly 2 field separators, and — unlike Segment 111 —
**no trailing separator** after the final field. Validate:

1. Segment Type is fixed as `120`.
2. Segment Length is numeric and equals the actual encoded content length.
3. Print Data is present and does not exceed 999 characters.
4. The total encoded segment (Type + Length + Print Data + 2 separators) does
   not exceed 1,009 characters.
5. No separator follows Print Data.

## 6. The Headline Finding: An Apparent AI-Artifact Contradiction, Resolved

Most provisional items in this knowledge base are SME ambiguities — places
where the specification is silent or the business condition needs
confirmation. Segment 120 initially surfaced something different: **the AI
Solution pipeline's own artifacts appeared to disagree with each other** —
resolved on 2026-09-23 by closer reading of those same artifacts.

- `BR-263-2` (spec page 263, `business_rule_derived`, confidence 0.9): "The
  Print Data 2 Segment always appears at the end of a Financial Transaction
  response."
- The same pipeline run turned that statement into scenario `SC-1839` and
  test cases `TC-4305`/`TC-4306`, asserting exactly that ordering.
- The same pipeline run also produced `docs/atl105_complete_templates.json`,
  whose `Financial Transaction Response` message template lists Segment 120
  as the **3rd of 9** segments (and 3rd of 10 in the EMV variant) — well
  before Segments 134, 136, 146, 148, 152, and 155.

**Resolution:** both templates order their segments in strict ascending
segment-number order (112, 115, 120, 134, 136, 146, 148, 152, 155 / EMV
variant inserts 131 before 134) — the same pattern used by the spec's own
numeric cross-reference appendices, and no other spec section documents true
field-by-field wire order for these response messages. This is assessed as a
numeric-sort artifact of AI extraction, not genuine evidence of wire order.
`BR-263-2` is therefore authoritative, and `SEG120-R-007` ("Segment 120 is
the final segment") is now **hard-enforced** in the baseline validator when
an explicit segment order is available in test data — see
[`SEG120-R-007` / `P-01`](coverage/segment-120-rule-catalog.json) and the
[coverage SME note](coverage/segment-120-coverage-sme-tba-note.md) for the
full reasoning. This resolution should be revisited if a real
(non-synthetic) message is ever observed contradicting it.

## 7. Turning Knowledge Into Requirements

Good BR:

```text
For a Financial Transaction Response requiring large print data, Segment 120
shall be present with Segment Type 120, a Segment Length equal to its
computed structural length, and a Print Data value of at most 999
characters; the total encoded segment shall not exceed 1,009 characters.
```

Weak BR:

```text
Print Data 2 Segment should be correct.
```

## 8. Security and Test-Data Guidance

- Print Data may contain receipt text with merchant names, partial account
  references, or loyalty/activation confirmation numbers. Use synthetic
  values only; do not copy real receipt content into test fixtures.
- The `\` line-delimiter convention (Blackhawk) is a structural detail, not a
  place to embed real phone numbers or activation codes.

## Current Validator Boundary

The current Segment 120 validator checks the envelope: field presence,
Segment Type fixed value, Segment Length exactly-4-digit format and
computed-length equality, Print Data presence and length cap, the
two-separator (non-trailing) serialization rule, response-only applicability
where a message-type signal is available, and — following the `P-01`
resolution — "last segment" ordering when an explicit segment order is
available in test data. It does not independently confirm the "large
amounts of print data" business threshold, and does not parse `\`-delimited
receipt line content beyond treating it as opaque text (`P-03`, still open).

## Review Checklist

- Is Segment 120 present only in a Financial Transaction Response / EMV
  Financial Transaction Response context, never a request?
- Does Segment Type equal `120`?
- Is Segment Length always exactly 4 digits, zero-padded?
- Does the declared Segment Length equal the computed structural length?
- Is Print Data present and at most 999 characters?
- Is the total segment at most 1,009 characters?
- Are there exactly two field separators, with none trailing?
- When an explicit segment order is present, is Segment 120 last (`SEG120-R-007`)?
- Is the Blackhawk delimiter scope question (`P-03`) treated as open rather
  than silently assumed?
