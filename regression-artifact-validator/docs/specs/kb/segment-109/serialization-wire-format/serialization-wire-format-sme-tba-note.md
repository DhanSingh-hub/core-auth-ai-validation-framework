# Segment 109 Serialization and Wire-Format Behavior

## Representations

```text
Electronic-mail intent
  -> structured request JSON
  -> Section 11.5 serialized request
  -> transport payload
  -> positional Electronic Mail Response
```

Logical JSON is not a wire-format guarantee. The converter or serializer must apply the specification order, Field Separators, and computed lengths.

## Request Rules

1. Data Section 1 contains Elements 55 and 63, with a separator between them and after Element 63.
2. Data Section 2 contains Segment 109 as field 3.
3. Segment 109 fields are serialized in the Section 12.8 order.
4. Empty fields retain Field Separators; a later populated field must not shift left.
5. Segment Type is `109`.
6. Segment Length is derived from serialized content and includes Segment Type and Field Separators.
7. Segment 109 has a maximum serialized length of 232 characters.
8. Text Data Length and Text Data are conditional and must agree once byte/character encoding is confirmed.

## Response Rules

The Electronic Mail Response is variable-length and has no Field Separators. It is positional:

| Position | Element | Field | Requiredness |
|---|---|---|---|
| 1 | 83 | Response Code | Required |
| 2 | 30 | Download Indicator | Required |
| 3 | 45 | Initiation Date | Required |
| 9 | 46 | Initiation Time | Required |
| 13 | 86 | Sequence Number | Required |
| 19 | 11 | Block Number | Required |
| 22 | 52 | Mail Text Data Length | Conditional |
| 25 | 51 | Mail Text Data | Conditional |

Response-code meaning, Download Indicator values, and response-to-request correlation must be supplied through `SEG109-SME-007` before acceptance criteria are finalized.

## Mutations to Prepare

- Segment Type not `109`.
- Segment Length incorrect or exceeds `232`.
- Required Data Section 1 element missing.
- Missing separator for an empty middle field.
- Invalid or mismatched Prompt Code and operation intent.
- Numeric length/type violations.
- Text Data Length does not agree with Text Data.
- Response includes Field Separators.
- Response Sequence Number or Block Number fails approved correlation.