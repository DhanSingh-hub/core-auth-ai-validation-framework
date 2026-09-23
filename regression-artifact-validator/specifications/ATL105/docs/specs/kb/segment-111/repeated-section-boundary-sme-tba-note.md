# Repeated-Section and Separator Boundary: SME and TBA Learning Note

## Why This Is a Separate Topic From Length

[Variable Information Length](variable-information-length-sme-tba-note.md)
covers whether one declared length matches one value. This note covers a
different question: whether the *serialized envelope as a whole* — the
repeating block plus its separators — is well-formed and within its size
caps.

```text
repetition 1 | repetition 2 | ... | repetition N | trailing separator
             ^ no separator here                 ^ exactly one separator here
```

## SME Reasoning

1. Is there ever a legitimate reason for zero repetitions (an "empty"
   Segment 111), or does the business condition that triggers Segment 111
   always imply at least one repetition?
2. Must repetitions be serialized in the order the business logic decided
   they are needed, or is any order acceptable to the receiving host?
3. Can the same Table ID legitimately repeat more than once in one Segment
   111 (open question, tracked as `P-02` cardinality alongside the top-level
  [SME/TBA learning note](segment-111-sme-tba-learning-note.md))?
4. Should a malformed separator be rejected before or after Segment Length is
   computed? (Open question 4 in the top-level note.)

## TBA Rule Decomposition

```text
BR:
  A serialized Segment 111 shall contain no field separator inside or
  between Variable Information repetitions and shall contain exactly one
  field separator following the final repetition.

TS:
  Multi-repetition Segment 111 with an extra separator inserted between two
  repetitions.

TC:
  Serialize the message and count field separators; verify the last
  character is the separator.

TD:
  wireFormat string with 4 separators instead of 3; expected result = FAIL.
```

## Boundary Cases

| Case | Expected result |
| --- | --- |
| Single repetition, one trailing separator | Pass |
| Multiple repetitions, no separator between them, one trailing separator | Pass |
| Separator inserted between two repetitions | Fail |
| Separator missing at the end | Fail |
| Repeated section exactly 991 characters | Pass (boundary) |
| Repeated section 992 characters | Fail (`SEG111-R-005`) |
| Total computed length exactly 999 characters | Pass (boundary) |
| Total computed length 1000 characters | Fail (`SEG111-R-006`) |
| Declared Segment Length differs from computed length | Fail (`SEG111-R-002`) |

## Current Validator Boundary

- `Segment111PayloadValidator` enforces the 991-character repeated-section
  cap (`SEG111-R-005`) and the 999-character total cap (`SEG111-R-006`) from
  the JSON structural representation.
- `Segment111SerializationValidator` enforces the literal wire-format
  separator count and trailing-separator placement (`SEG111-R-007`), but only
  when `testControls.validateSerialization` is explicitly enabled and a
  `wireFormat` string is supplied. It is not evaluated from JSON structure
  alone, because separator placement is a property of the serialized bytes,
  not of the parsed object.
- Cardinality (whether more than one Segment 111 may appear per message) is
  not yet enforced; it remains `REVIEW_REQUIRED` pending `P-02`.

## Review Checklist

- Does the wire-format string contain zero separators inside or between
  repetitions?
- Does it contain exactly one trailing separator?
- Is the repeated-section total at or below 991 characters?
- Is the fully computed Segment Length at or below 999 characters?
- Is a boundary case (exactly 991, exactly 999) represented in test data, not
  only comfortably-under-the-limit cases?
- Is cardinality above one flagged for review rather than silently accepted?
