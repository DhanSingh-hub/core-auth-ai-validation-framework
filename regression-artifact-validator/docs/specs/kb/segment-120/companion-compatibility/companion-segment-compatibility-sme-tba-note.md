# Segment 120 Companion-Segment Compatibility: SME and TBA Learning Note

## Purpose

Segment 120 is a conditional, response-side companion segment in Data
Section 3 of a Financial Transaction Response or EMV Financial Transaction
Response. This note teaches the SME and TBA how to reason about *when*
Segment 120 is required and how it relates to the other response-side
companion segments, distinct from the envelope-only question of whether
Segment 120 itself is well-formed (covered by the
[serialization note](../serialization-wire-format/serialization-wire-format-sme-tba-note.md)).

## Core Mental Model

```text
Response requires large print data (BR-263-1)
  -> Segment 120 required
  -> validated alongside the response's other Section 3 companions
  -> Element 63 / segment count consistency at Data Section 1
```

## The Response-Side Companion Family

Per `docs/atl105_complete_templates.json`, a Financial Transaction Response
carries these Data Section 3 companions (9 total); the EMV variant adds
Segment 131 (10 total):

| Segment | Name | Notes |
| --- | --- | --- |
| 112 | Additional Information Data Segment | |
| 115 | Print Data Segment | Smaller print-data counterpart to Segment 120 |
| **120** | **Print Data 2 Segment** | This module's subject |
| 131 | EMV Response Data Segment | EMV Financial Transaction Response only |
| 134 | Transaction Attributes Segment | |
| 136 | Moneris Data Segment (Response) | |
| 146 | Enhanced Fleet Data Segment (Response) | |
| 148 | WEX Available Product Fleet Information | |
| 152 | Response InComm OTC Market Basket Data | |
| 155 | Real Time Account Updater Response Segment | |

This is structurally different from Segment 100's request-side companion
family (101 Fleet, 102 Product Code, 103 EBT, 104 Purchase Card, 111
Variable Information, 123 NFC Tokenization, 130 EMV Request, 135 Moneris
Request) — response and request companions do not overlap.

## SME Questions

1. Does this response require large print data (Segment 120) versus smaller
   print data (Segment 115) versus no print data at all?
2. Is this a Blackhawk phone activation/recharge response specifically?
3. Which other response companions (112, 115, 131, 134, 136, 146, 148, 152,
   155) apply simultaneously? Segment 120 must be the last of them, per the
   resolved ordering rule (`SEG120-R-007`, `P-01`).
4. Is there a maximum total response size that interacts with Segment 120's
   own 1,009-character cap?

## Compatibility Baseline

| Business condition | Expected companion segment | Segment 120 role | Validation disposition |
| --- | ---: | --- | --- |
| Response does not require large print data | None | Absent | Segment 120 should not appear |
| Response requires large print data | `120` | Required exactly once, last in Data Section 3 | Missing `120`, or `120` not last, is an error |
| Blackhawk phone activation/recharge response | `120` | Required, Print Data uses `\` delimiter, last in Data Section 3 | Missing `120` is an error; delimiter content is informational (`P-03`) |
| Request message of any kind | Segment 120 must be absent | N/A | Presence on a request is an error (`SEG120-R-006`) |

## TBA Rule Decomposition

```text
BR:
  A Financial Transaction Response or EMV Financial Transaction Response
  requiring large print data shall contain Segment 120 in Data Section 3.

TS:
  Response with large print data and Segment 120 present.

TC-Positive:
  Submit the response with Segment 120 populated. Expected result: PASS.

TC-Negative (not yet in the AI Solution's approved catalog):
  Submit a REQUEST message with Segment 120 populated. Expected result:
  FAIL (SEG120-R-006). This gap is called out in the
  [AI coverage report](../coverage/segment-120-ai-coverage-report.md).
```

## Common Analysis Errors

- Assuming Segment 120 and Segment 115 (Print Data) are interchangeable —
  they are documented as distinct segments for different print-data volumes.
- Assuming Segment 120's position relative to other response companions is
  still an open question — it was resolved on 2026-09-23 (`P-01`): Segment
  120 must be last.
- Testing only "Segment 120 present in a response" without ever testing
  "Segment 120 absent from a request," leaving `SEG120-R-006` only
  partially exercised.

## Covered Training So Far

The Segment 120 learning path has covered:

1. Segment 120's role as a response-only, condition-driven Section 3
   companion — the only response-side segment covered in this knowledge
   base so far.
2. The envelope-only scope boundary versus Blackhawk delimiter content
   (`P-03`, still open) and ordering (`P-01`, resolved).
3. Segment Length's confirmed fixed 4-digit field width (`P-02-WIDTH`
   resolved), unlike the 3-digit convention in Segment 100/101/111.
4. Companion-segment compatibility within the response-side segment family,
   including the confirmed "last segment" ordering rule.
5. How an apparent AI Solution internal contradiction can be resolved by
   closer reading of the same artifacts, rather than always requiring
   external SME input.

Remaining open topics are SME resolution of `P-02-RESIDUAL`, `P-03`, and
`P-04`, and generation of negative test cases, since none currently exist
for Segment 120 anywhere in the AI Solution's approved test-case catalog.
