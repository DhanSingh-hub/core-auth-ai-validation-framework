# Segment 111 Companion-Segment Compatibility: SME and TBA Learning Note

## Purpose

Segment 111 is a conditional companion of Segment 100 in Data Section 3. This
note teaches the SME and TBA how to reason about *when* Segment 111 is
required, distinct from the separate question of whether its own envelope is
well-formed (covered by the
[serialization and wire-format note](../serialization-wire-format/serialization-wire-format-sme-tba-note.md)
and the [length note](../variable-information-length-sme-tba-note.md)).

This is a learning and validation note, not a business approval record.

## Core Mental Model

```text
Business condition (AVS, SIC, ZIP, RFID entry mode, MSDI, soft descriptor,
MIT/CIT, digital commerce, transaction fee, ...)
  -> Appendix I Table ID
  -> Segment 111 required
  -> Element 63 includes Segment 111
  -> serialized message validation
```

Segment 100 must not be treated as sufficient on its own for any transaction
that carries one of these conditions. A request can be valid Segment 100 JSON
and still be incomplete because Segment 111 is missing, duplicated, or
inconsistent with the declared segment count.

## SME Questions

1. Does this transaction require AVS, SIC/MCC reporting, ZIP Code reporting,
   RFID POS Entry Mode, FSA/HRA MSDI, a soft merchant descriptor, MIT/CIT
   category data, digital-commerce data, or an additional transaction fee?
2. If more than one condition applies, are all of them carried as separate
   repetitions inside a single Segment 111, or could the source require more
   than one Segment 111 instance? (Tracked as `P-02` — see the top-level
   [SME/TBA learning note](../segment-111-sme-tba-learning-note.md).)
3. Is this an initial request or a lifecycle follow-up, and does the
   follow-up need to repeat the same Variable Information as the original
   transaction?
4. What should Element 63 contain once Segment 111 is added to the segment
   set?

## Compatibility Baseline

| Business condition | Expected companion segment | Segment 111 role | Validation disposition |
| --- | ---: | --- | --- |
| No Appendix I variable-information condition applies | None | Absent | Segment 111 should not appear |
| AVS-eligible transaction | `111` | Required (Table ID varies) | Missing `111` is an error |
| SIC/MCC velocity-processing transaction | `111` | Required (Table ID `007`) | Missing `111` is an error |
| FSA/HRA transaction | `111` | Required (MSDI) | Missing `111` is an error |
| RFID receiver connected at POS | `111` | Required (POS Entry Mode) | Missing `111` is an error |
| Third-party soft-descriptor merchant | `111` | Required (Table ID `047`) | Missing `111` is an error |
| MasterCard MIT/CIT transaction | `111` | Required (Table ID `056`) | Missing `111` is an error |
| Multiple conditions apply simultaneously | `111` | Required, multiple repetitions | Validate every repetition and the combined envelope |
| Source does not define the combination | Unknown | — | `REVIEW_REQUIRED`, not auto-approved |

## Element 63 Rule

Element 63 must equal the number of segments actually serialized, including
Segment 111 when present:

```text
Segment 100 only               -> Element 63 = 01
Segment 100 + 111               -> Element 63 = 02
Segment 100 + 111 + 130         -> Element 63 = 03
```

## TBA Rule Decomposition

```text
BR:
  An FSA/HRA Financial Transaction Request shall contain Segment 111 with a
  repetition carrying the Market-Specific Data Indicator, in addition to
  the required Segment 100, and Element 63 shall equal the number of
  serialized segments.

TS:
  FSA/HRA purchase with Segment 100 and Segment 111 (MSDI repetition).

TC-Positive:
  Submit both required segments and Element 63 = 02. Expected result: PASS.

TC-Negative:
  Omit Segment 111 while retaining the FSA/HRA context. Expected result: FAIL.
```

## Common Analysis Errors

- Assuming Segment 100 alone is sufficient for any AVS, SIC, MSDI, or
  soft-descriptor flow.
- Treating Segment 111 as always optional because it is "variable
  information" rather than confirming the specific condition that requires
  it.
- Counting JSON objects instead of serialized segments when computing
  Element 63.
- Accepting more than one Segment 111 per message without flagging the
  cardinality question for review.
- Validating Segment 111's own envelope rules (`SEG111-R-001..007`) but
  forgetting to check whether it should have been present at all.

## Covered Training So Far

The Segment 111 learning path has covered:

1. Segment 111's role as a conditional Section 3 companion to Segment 100.
2. The envelope-only scope boundary versus the out-of-scope Appendix I
   Table-ID content (~400 business rules).
3. The Variable Information Indicator as a Table ID discriminator.
4. Variable Information Length as a per-repetition structural promise.
5. The repeated-section (991) and total (999) length caps and separator
   placement.
6. Companion-segment compatibility and Element 63 counting for Segment 111.

Remaining open topics are the SME resolution of `P-01` (postal code format)
and `P-02` (cardinality), and any future Appendix I content-validation module.
