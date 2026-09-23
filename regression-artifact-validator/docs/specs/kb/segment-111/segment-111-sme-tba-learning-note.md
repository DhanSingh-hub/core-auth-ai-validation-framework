# Segment 111 SME TBA learning note

**Status:** SME review required (TBA). This note records the learning boundary for the
ATL105 2026-3 Variable Information Data Segment while domain confirmation is pending.

## Confirmed learning

- Segment 111 is the Variable Information Data Segment in Section 12.10.
- The envelope uses a fixed segment type of `111` and a three-digit segment length.
- Each variable-information repetition carries a three-digit indicator and a three-digit
  value length. The declared value length must equal the value length.
- The envelope limit is 999 characters; the repeated section limit is 991 characters.
- Serialization permits one trailing field separator and no separators inside or between
  repetitions.

## SME questions (TBA)

1. Which transaction/table identifiers activate each repetition in production messages?
2. Does the implementation need to validate Appendix I table-specific content in addition
   to the envelope rules?
3. Are blank values, leading zeroes, and Unicode characters permitted for every table?
4. Should malformed separators be rejected before or after segment-length calculation?
5. Which Segment 100 conditions are authoritative when Segment 111 is conditionally absent?

## Validation handling

Until the SME answers are recorded, these questions remain provisional and are tracked as
review items. Automated validation must not infer table-specific semantics from examples.
The canonical rule catalog and JSON rules file are the authoritative executable scope.

**References:** [Segment 111 README](README.md), [validation rules](segment-111-validation-rules.json),
[training methodology](../../../SEGMENT-100-TRAINING-METHODOLOGY.md), and the
[training questionnaire](../../../test-validation-strategy/SEGMENT-TRAINING-QUESTIONNAIRE.md).
## Session-confirmed scope decisions

1. Training is limited to Segment 111 envelope rules: type `111`, computed/structural
   length, the 999 total and 991 repeated-section caps, and field-separator/repetition
   placement. The approximately 400 Appendix I Table-ID business rules for elements 111
   and 113 remain out of scope; `BR-SEG100-APPI-VARIABLE-TRIGGER` is referenced only as
   an external cross-reference and is not duplicated.
2. A dedicated `Segment111SerializationValidator` is used for literal wire-format
   separator placement because JSON structure cannot represent that rule.

## Derived length validation

The implemented formula is:

`segmentLength = 3 + 3 + sum(3 + 3 + len(variableInformation)) + 3`.

The first two terms are the Segment Type and Segment Length fields; each repetition
contributes the three-digit indicator, three-digit value length, and value; the final
`+3` accounts for the two separators before the repeating section and the one trailing
separator. It was validated against the real fixture with entries `047/23`, `005/3`,
`030/10`, and `031/2`: `3+3+(3+3+23)+(3+3+3)+(3+3+10)+(3+3+2)+3 = 71`.

`BR-SEG111-LENGTH`, `BR-SEG111-MAX-LENGTH`, and
`BR-SEG111-REPETITION-SEPARATOR`, previously `PARTIALLY_COVERED`/
`REVIEW_REQUIRED` in the Segment 111 coverage report, are now `COVERED` by the payload
and serialization validators. Appendix I Table-ID rules remain `REVIEW_REQUIRED` and
out of scope.
