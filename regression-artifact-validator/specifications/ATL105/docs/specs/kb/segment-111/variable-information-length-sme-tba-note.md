# Variable Information Length (Element 112): SME and TBA Learning Note

## Core Idea

Every Variable Information repetition declares its own length. That declared
length is a structural promise: it must equal the actual character length of
the value that follows it, so a receiver that only understands fixed-width
indicator/length pairs can still parse a variable-width value correctly.

```text
Variable Information value
  -> actual character length
  -> Variable Information Length (declared, 3 digits)
  -> must be equal, or the envelope is malformed
```

## Derived Length Formula

The implemented formula, confirmed against the real ATL105 fixture used
during training, is:

```text
segmentLength = 3 + 3 + sum(3 + 3 + len(variableInformation)) + 3
```

- The first `3 + 3` is the Segment Type and Segment Length fields themselves.
- Each repetition contributes `3 + 3 + len(value)` for its indicator, length,
  and value.
- The final `+3` accounts for the two separators before the repeating section
  and the one trailing separator.

Validated example (four repetitions, Table IDs `047/23`, `005/3`, `030/10`,
`031/2`):

```text
3 + 3 + (3+3+23) + (3+3+3) + (3+3+10) + (3+3+2) + 3 = 71
```

This matches the
[`variable-info-multi-table.synthetic.json`](../../../../test-input/ai-solution/test-data/segment-111/lifecycle/variable-info-multi-table.synthetic.json)
fixture, where `segmentLength` is declared as `071`.

## SME Reasoning

1. Is the declared length measuring the value's character count, or was it
   copied from an unrelated field (byte count, encoded length, or a
   Table-ID-specific sub-length)?
2. Does the value contain multi-byte or Unicode characters where "length"
   might mean bytes rather than characters? (Open SME question — see the
  top-level [SME/TBA learning note](segment-111-sme-tba-learning-note.md),
   question 3.)
3. Does exceeding the 991-character repeated-section cap force the
   transaction to omit or split a repetition, and if so, what is the required
   behavior?
4. Is the total computed length ever allowed to exceed 999, or is that always
   a hard reject?

## TBA Rule Decomposition

```text
BR:
  For every Variable Information repetition, the Variable Information Length
  shall equal the character length of the Variable Information value that
  follows it.

TS:
  Single-repetition Segment 111 with a declared length that does not match
  the value length.

TC:
  Submit Variable Information Indicator = 005, Variable Information Length =
  004, Variable Information value = "XYZ" (3 characters).

TD:
  segment-111/rejection/variable-info-length-mismatch.synthetic.json;
  expected result = REJECT.
```

## Current Validator Boundary

`Segment111PayloadValidator` enforces:

- `SEG111-R-004`: declared length present, three digits, and equal to the
  actual value length.
- A per-repetition structural bound of 985 characters (derived from the
  991-character repeated-section cap minus the 6-character indicator/length
  overhead).
- `SEG111-R-005`: the summed repeated section must not exceed 991 characters.
- `SEG111-R-002` / `SEG111-R-006`: the declared Segment Length must equal the
  computed structural length, and that computed value must not exceed 999
  characters.

`Segment111SerializationValidator` independently re-derives the same computed
length from the literal `wireFormat` string when `testControls.validateSerialization`
is enabled, so a package cannot pass by declaring a correct JSON length while
shipping a mismatched wire-format string.

## Review Checklist

- Does the declared length equal `len(value)` for every repetition?
- Does the repeated-section running total stay at or below 991?
- Does the fully computed Segment Length stay at or below 999?
- Does the declared Segment Length equal the computed value exactly?
- Is a length-mismatch case represented as a negative fixture with an explicit
  `REJECT` expectation, not only as a positive example?
