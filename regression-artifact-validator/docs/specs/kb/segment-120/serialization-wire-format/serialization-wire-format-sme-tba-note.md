# Segment 120 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains how Segment 120 moves from a logical 3-field JSON
representation into the serialized ATL105 envelope carried inside a
Financial Transaction Response, and why its serialization rules differ
slightly from every other segment covered in this knowledge base so far.

## Three Representations

```text
Business condition (large print data required)
  -> structured test-data JSON (segmentType, segmentLength, printData)
  -> serialized Segment 120 envelope
```

| Representation | Main question | Example validation |
| --- | --- | --- |
| Business condition | Does this response need large print data? | BR-263-1 |
| Logical JSON | Are the 3 fields structurally represented? | `SEG120-R-001..003` |
| Serialized envelope | Are separators placed correctly, with no trailing separator? | `SEG120-R-005` |

## Segment 120 Serialization Rules

1. Segment Type is fixed as `120`.
2. Segment Length is a numeric value, always exactly 4 digits, zero-padded
   (`P-02-WIDTH` resolved 2026-09-23 — confirmed by Element 84 spec text),
   equal to the computed structural length: `3 (Segment Type) + 4 (Segment
   Length) + len(Print Data) + 2 (separators)`.
3. A field separator follows Field 1 (Segment Type).
4. A field separator follows Field 2 (Segment Length).
5. **No** field separator follows Field 3 (Print Data) — this is the end of
   the segment.
6. Print Data must not exceed 999 characters and the total encoded segment
   must not exceed 1,009 characters — both enforced independently pending a
   residual 1-character arithmetic gap between them (`P-02-RESIDUAL`).

Unlike Segment 111, Segment 120 has no repeating structure, so there is no
"between repetitions" separator question — only the fixed 3-field order.

## SME and TBA Reasoning

1. Should the wire-format string itself be validated (literal separator
   bytes), the way `Segment111SerializationValidator` does for Segment 111,
   or is JSON-structural validation sufficient for this segment given its
   simpler shape?
2. Is the field separator the same character (`\u001c`) used elsewhere in
   ATL105, or does a response message use a different separator convention?
3. Does the converter that produces the response emit `wireFormat` directly,
   or is it expected to be hand-authored in test data?

## TBA Rule Decomposition

```text
BR:
  A serialized Segment 120 shall place exactly one field separator after
  Segment Type and exactly one after Segment Length, with no separator
  following Print Data; the declared Segment Length shall equal the fully
  computed structural length.

TS:
  Segment 120 with a computed length near the 999/1000 boundary, to exercise
  the residual per-field-vs-total cap arithmetic gap (`P-02-RESIDUAL`).

TC:
  Recompute the structural length from the serialized form and compare it
  against the declared segmentLength; count field separators and verify no
  trailing separator exists.

TD:
  Positive fixtures under test-input/ai-solution/test-data/segment-120/ with
  varying Print Data lengths, plus a negative fixture with a
  declared/computed length mismatch.
```

## Positive, Negative, and Boundary Cases

| Case | Expected result |
| --- | --- |
| Correct field order, 2 separators, no trailing separator | Pass |
| Declared segmentLength matches computed structural length | Pass |
| Trailing separator present after Print Data | Fail |
| Separator missing between fields 1/2 or 2/3 | Fail |
| Declared segmentLength does not match computed length | Fail |
| Computed length exactly 1009 characters | Pass (boundary) |
| Computed length 1010 characters | Fail (`SEG120-R-004`) |
| Print Data exactly 999 characters | Pass (boundary) |
| Print Data 1000 characters | Fail (per-field cap; residual `P-02-RESIDUAL` gap with the 1000-character arithmetic result of the total cap) |

## Current Validator Boundary

`Segment120PayloadValidator` validates the JSON-structural representation:
field presence, Segment Type fixed value, Segment Length format/range and
computed-length equality, Print Data presence/length, and the total
1,009-character cap. Literal wire-format separator-byte validation (the
Segment 111-style `wireFormat` string check) is a candidate future
enhancement, not yet implemented for Segment 120 given its simpler,
non-repeating shape — see the
[coverage report](../coverage/segment-120-ai-coverage-report.md) for current
implementation status.

## Review Checklist

- Does the serialized form place separators only after fields 1 and 2, never
  after field 3?
- Does the recomputed structural length equal the declared Segment Length?
- Are boundary cases (999/1000 for Print Data, 1009/1010 for the total)
  represented in test data?
- Is the residual per-field-vs-total cap arithmetic gap (`P-02-RESIDUAL`)
  tracked rather than silently resolved by the test data's own convention?
