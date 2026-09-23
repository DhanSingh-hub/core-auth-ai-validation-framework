# Variable Information Indicator (Element 111): SME and TBA Learning Note

## What the Indicator Does

The Variable Information Indicator is a compact discriminator, not a free-form
code. Its three-digit value is an Appendix I Table ID that selects the layout
governing the accompanying Variable Information value.

```text
Business condition
  -> Appendix I Table ID
  -> Variable Information Indicator (3 digits)
  -> Variable Information Length (3 digits)
  -> Variable Information value (Table-ID-specific grammar)
```

It is not merely a label to echo back. A syntactically valid three-digit
indicator can still be business-invalid if the accompanying value does not
follow the grammar defined for that Table ID in Appendix I.

## SME Reasoning

Ask, in order:

1. What business condition triggers this repetition — AVS, SIC/MCC reporting,
   POS Entry Mode, a soft merchant descriptor, MIT/CIT recurring-transaction
   data, a digital-commerce indicator, or another Appendix I condition?
2. Which Table ID corresponds to that condition?
3. Does the card network, transaction type, or program (for example MasterCard
   MIT/CIT, Discover installments, FSA/HRA) additionally require a specific
   Sub Table ID inside the value?
4. Is more than one repetition required for this transaction (for example ZIP
   Code and SIC Information together)?
5. Is the same Table ID ever repeated more than once in a single message, and
   if so, is that combination defined by the source specification?

## TBA Dependency Chain

```text
Business condition
  -> Table ID selection
  -> Variable Information Indicator value
  -> Variable Information Length
  -> Variable Information value grammar
  -> repeated-section and total-length envelope caps
```

A requirement such as "Variable Information Indicator is valid" is too vague.
A useful requirement names the Table ID and the condition that activates it.

Example, grounded in the approved requirement catalog:

```text
For a transaction reported under SIC velocity processing, Segment 111 shall
include a repetition with Variable Information Indicator = 007 (SIC
Information) and a Variable Information value equal to the merchant's MCC/SIC
code for the applicable channel (5542 CAT/outside, 5541 inside).
```

## SME Questions (TBA)

1. Which transaction/table identifiers activate each repetition in production
   messages? (Recorded as open in the top-level
   [SME/TBA learning note](../segment-111-sme-tba-learning-note.md).)
2. Does the implementation need to validate Appendix I table-specific content
   in addition to the envelope rules, or is envelope-only validation the
   confirmed release scope?
3. Can a single Segment 111 legally repeat the same Table ID more than once?
4. Are blank values, leading zeroes, and Unicode characters permitted for
   every Table ID, or only for specific ones?

## Current Validator Boundary

`Segment111PayloadValidator` checks, for every repetition:

- The indicator is present and is a textual three-digit field (`SEG111-R-003`).
- The declared Variable Information Length is present, textual, and three
  digits (`SEG111-R-004`).
- The declared length equals the actual value length (`SEG111-R-004`).
- The per-repetition value does not exceed the structural bound of 985
  characters implied by the 991-character repeated-section cap.

It does **not** decode the Table ID or validate Table-ID-specific value
grammar (for example, that Table ID `005` carries a valid Appendix J
Point-of-Service Entry Mode code, or that Table ID `056` Sub Table ID `09`
carries a valid MIT/CIT subcategory). Those approximately 400 Appendix I
business rules remain `REVIEW_REQUIRED` and out of scope for this module, per
the session-confirmed scope decision recorded in the
[SME/TBA learning note](../segment-111-sme-tba-learning-note.md).

## Review Checklist

- Is the indicator present and exactly three digits?
- Does the indicator correspond to a real Appendix I Table ID for the stated
  business condition?
- Does the declared length equal the actual value length?
- Is the repetition's contribution counted correctly toward the 991-character
  repeated-section cap and the 999-character total cap?
- Is Table-ID-specific content validation explicitly marked out of scope
  rather than silently passed?
