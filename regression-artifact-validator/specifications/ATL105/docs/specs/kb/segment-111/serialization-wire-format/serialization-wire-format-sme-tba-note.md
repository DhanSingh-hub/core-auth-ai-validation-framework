# Segment 111 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains how Segment 111 moves from a logical, repeating
indicator/length/value structure into the serialized ATL105 envelope, and why
that serialization must be validated separately from the parsed JSON
representation.

## Three Representations

```text
Business condition (Table ID selection)
  -> structured test-data JSON (repetitions array)
  -> serialized Segment 111 envelope (wireFormat)
```

| Representation | Main question | Example validation |
| --- | --- | --- |
| Business condition | Which Appendix I Table ID(s) apply? | AVS, SIC, MSDI, soft descriptor, etc. |
| Logical JSON | Are indicator/length/value repetitions structurally represented? | `SEG111-R-001..006` |
| Serialized envelope | Are separators placed correctly in the literal bytes? | `SEG111-R-007` |

## Segment 111 Serialization Rules

1. Segment Type is fixed as `111`.
2. Segment Length is a three-digit textual value that must equal the fully
   computed structural length: `3 + 3 + sum(3 + 3 + len(value)) + 3`.
3. Each repetition is encoded as `indicator(3) + length(3) + value`, with no
   field separator between the indicator, length, and value of a single
   repetition.
4. No field separator appears between two repetitions.
5. Exactly one field separator follows the final repetition (the trailing
   separator), and the wire-format string must end with it.
6. The repeated section (all repetitions combined) must not exceed 991
   characters.
7. The fully computed Segment Length must not exceed 999 characters.

Unlike Segment 100, Segment 111 has no fixed-order optional/required field
list to reason about — its serialization risk is entirely about repetition
boundaries and separator placement, not about omitted middle fields.

## SME and TBA Reasoning

1. Is `testControls.validateSerialization` expected to be enabled for every
   Segment 111 test package, or only for a dedicated serialization test
   suite?
2. Does the converter emit the `wireFormat` string, or is it expected to be
   hand-authored in test data? (Hand-authored wire strings should be treated
   as a lower-confidence source than a converter-emitted one.)
3. Is the field separator always `\u001c`, or does it vary by transport
   configuration? (`Segment111SerializationValidator` reads the separator
   from `testControls.serialization.fieldSeparator`, defaulting to `\u001c`.)
4. Should a malformed separator be rejected before or after Segment Length is
   computed? (Open question — tracked in the top-level
   [SME/TBA learning note](../segment-111-sme-tba-learning-note.md).)

## TBA Rule Decomposition

```text
BR:
  A serialized Segment 111 shall encode each Variable Information repetition
  as indicator, length, and value with no internal separator, no separator
  between repetitions, and exactly one trailing field separator; the
  declared Segment Length shall equal the fully computed structural length.

TS:
  Multi-repetition Segment 111 with testControls.validateSerialization
  enabled and a literal wireFormat string supplied.

TC:
  Recompute the structural length from wireFormat and compare it against the
  declared segmentLength; count field separators and verify trailing
  placement.

TD:
  variable-info-multi-table.synthetic.json (positive, four repetitions,
  computed length 071) and variable-info-length-mismatch.synthetic.json
  (negative, declared/actual length mismatch).
```

## Positive, Negative, and Boundary Cases

| Case | Expected result |
| --- | --- |
| Correct repetition encoding, one trailing separator | Pass |
| Declared segmentLength matches computed structural length | Pass |
| Extra separator inserted between repetitions | Fail |
| Trailing separator missing | Fail |
| Declared segmentLength does not match computed length | Fail |
| Repeated section exceeds 991 characters | Fail |
| Total computed length exceeds 999 characters | Fail |
| `validateSerialization` disabled and no wireFormat supplied | Skipped, not failed — JSON-level checks still apply |
| `validateSerialization` enabled but wireFormat missing | Fail (wireFormat required) |

## Current Validator Boundary

`Segment111SerializationValidator` only runs when
`testControls.validateSerialization` is `true` in the payload's test
controls, and only when a `wireFormat` string is present. When disabled, only
the JSON-structural checks in `Segment111PayloadValidator` apply. This mirrors
the Segment 100
[serialization and wire-format note](../../segment-100/serialization-wire-format/serialization-wire-format-sme-tba-note.md)
principle that a pass at the JSON layer does not imply a pass at the wire
layer, and vice versa.

## Review Checklist

- Is `testControls.validateSerialization` set deliberately, not left at its
  default?
- When enabled, is a literal `wireFormat` string supplied?
- Does the wire-format string contain exactly three field separators?
- Is the last character of the wire-format string the field separator?
- Does the recomputed length from the wire-format string equal the declared
  `segmentLength`?
- Are both the JSON-structural and wire-format validators exercised by test
  data, not only one of them?
