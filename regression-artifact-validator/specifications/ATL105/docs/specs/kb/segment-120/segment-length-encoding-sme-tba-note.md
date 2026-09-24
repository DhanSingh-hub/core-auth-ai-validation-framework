# Segment Length Encoding (Element 84): SME and TBA Learning Note

## Core Idea

Segment Length is **always exactly 4 digits** for Segment 120 — confirmed by
the Element 84 spec text, not a fixed-width convention borrowed from
Segment 100/101/111 (which are always exactly 3 digits).

```text
Print Data value
  -> character count
  -> + Segment Type length (3) + Segment Length's own width (4) + separator count (2)
  -> computed structural length (1 - 1009)
  -> serialized Segment Length value, always 4 digits, zero-padded
```

## Resolution (`P-02-WIDTH`, 2026-09-23)

`regression-artifact-validator/docs/specs/extracted_text.txt` lines
21313-21340 (Element 84, page 13-66/399) states:

> "Segment Length has a length of four digits in the following seven
> instances only: EBT Data Segment (No. 103), SKU Data Segment (No. 114),
> Print Data Segment (No. 115), Proprietary Data Load Segment (No. 118),
> **Print Data 2 Segment (No. 120)**, EMV Request Data Segment (No.130), EMV
> Response Data Segment (No. 131) ... It cannot have a length of 4 digits
> when sending any other segment."

This directly and unambiguously answers the original open question: Segment
120's Segment Length is always 4 digits, zero-padded, regardless of the
actual computed value's magnitude (e.g. a computed length of 24 serializes
as `0024`, not `024`).

## Remaining Open Question (`P-02-RESIDUAL`)

A separate, narrower arithmetic question remains: the field table lists
Print Data's own max length as 999, but `1,009 (total) - 3 (Segment Type) -
4 (Segment Length) - 2 (separators) = 1,000`, not 999. Possible
explanations:

1. The field table's "999" is a typo for "1,000".
2. The true total cap is actually 1,010 (matching SKU Data Segment's
   analogous "0001-1010" 4-digit-length pattern), not 1,009.
3. There is a 1-character overhead this analysis has not yet identified.

## TBA Rule Decomposition

```text
BR:
  Segment 120's Segment Length field shall be serialized as exactly 4
  digits, zero-padded, and shall equal the segment's computed structural
  length (Segment Type length + Segment Length's own width + Print Data
  length + Field Separator count).

TS:
  Segment 120 with a small Print Data value (computed length well under
  1000) to confirm zero-padding to 4 digits is still required.

TC:
  Serialize and confirm the declared Segment Length is exactly 4 characters
  long even when the numeric value would fit in 3 digits.

TD:
  A fixture with computed length 24 and declared segmentLength "0024" (not
  "024") — expected ACCEPT; a variant with segmentLength "024" — expected
  REJECT (wrong width).
```

## Current Validator Boundary

`Segment120PayloadValidator` requires Segment Length to be textual, numeric,
and **exactly 4 digits** (`^[0-9]{4}$`), and checks that the parsed integer
equals the computed structural length. This is now a confirmed, hard rule —
not a placeholder pending SME input.

## Review Checklist

- Is the declared Segment Length always exactly 4 characters, even for small
  values (e.g. `0024`, not `24` or `024`)?
- Does that integer equal the computed structural length for the actual
  Print Data supplied?
- Is a boundary case (computed length exactly 999, and exactly 1000) present
  in test data to exercise the residual `P-02-RESIDUAL` arithmetic question?
- Is a negative case (wrong width, e.g. 3 or 5 digits) present in test data?
  (Currently: no — see the
  [AI coverage report](coverage/segment-120-ai-coverage-report.md).)
