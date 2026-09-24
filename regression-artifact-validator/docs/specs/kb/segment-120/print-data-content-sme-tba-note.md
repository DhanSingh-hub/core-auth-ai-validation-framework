# Print Data Content (Element 152): SME and TBA Learning Note

## What Print Data Carries

Print Data is the free-text payload that the device prints for the
cardholder or clerk. Its only documented internal convention is the `\`
line delimiter used for Blackhawk phone activation and recharge receipts:

```text
Print Data
  -> plain receipt/print text (general case)
  -> OR '\'-delimited receipt lines (Blackhawk phone activation/recharge)
```

## SME Reasoning

1. Is this response a Blackhawk phone activation/recharge flow, or a general
   large-print-data response?
2. If Blackhawk, does every line of receipt text end with a `\` delimiter,
   including (or excluding) the final line?
3. Are there other card-network- or program-specific internal conventions
   for Print Data beyond the Blackhawk case that the specification does not
   yet document?
4. Does Print Data ever need to represent non-ASCII characters (the field is
   documented as alphanumeric, `01-1,009/a-z/A-Z`, in the segment-level
   length description) — should out-of-range characters be rejected?

## TBA Rule Decomposition

```text
BR:
  Segment 120 Print Data shall be present and shall not exceed 999
  characters; for Blackhawk phone activation/recharge responses, each
  receipt line shall be followed by a '\' delimiter.

TS:
  Blackhawk phone activation response with multi-line receipt text.

TC:
  Submit Print Data with three lines each followed by '\' and confirm
  acceptance; submit a variant missing a delimiter and evaluate whether the
  validator flags or ignores it (currently: ignores, per P-03).

TD:
  variable-info-style fixture (see test-input/ai-solution/test-data/segment-120/)
  with printData = "Line of text\\line of text\\line of text".
```

## Current Validator Boundary

`Segment120PayloadValidator` checks only that Print Data is present and does
not exceed 999 characters (`SEG120-R-003`). It does not parse `\` delimiters
or validate Blackhawk-specific line structure (`SEG120-R-008` is tracked as
`info` severity and `REVIEW_REQUIRED` pending `P-03`). This mirrors how
Segment 111 leaves Appendix I Table-ID content out of its envelope scope.

## Review Checklist

- Is Print Data present whenever Segment 120 is included?
- Is Print Data at most 999 characters?
- If the scenario is a Blackhawk phone activation/recharge flow, is that
  documented in the test data's business context, even though the validator
  does not yet enforce delimiter structure?
- Is the scope question (`P-03`) — should delimiter well-formedness be
  validated here or in a separate module — tracked rather than silently
  decided?
