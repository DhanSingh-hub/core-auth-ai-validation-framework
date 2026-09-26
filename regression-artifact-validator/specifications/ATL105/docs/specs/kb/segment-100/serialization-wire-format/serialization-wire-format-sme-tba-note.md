# Segment 100 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## Purpose

This lesson explains how Segment 100 moves from business and logical test-data representation into the serialized ATL105 request consumed by the Fiserv converter or transport layer.

A JSON object can be syntactically valid and semantically plausible while the serialized ATL105 message is still invalid. Serialization is therefore a separate validation responsibility.

## Three Representations

Keep these representations separate:

```text
Business intent
  -> structured test-data JSON
  -> serialized ATL105 message
  -> TCP/IP framed payload
```

| Representation | Main question | Example validation |
| --- | --- | --- |
| Business intent | What transaction is being requested? | Purchase, EMV, fleet, completion |
| Logical JSON | Are fields and values represented structurally? | Segment 100 fields and values |
| ATL105 message | Are fields encoded in the required order and delimiters? | Separators, lengths, padding |
| TCP/IP payload | Is the message framed for transport? | Message length, TPDU, byte order |

The logical JSON is input to the converter. It is not automatically an ATL105 wire message.

## Message Layers

For a standard Financial Transaction Request, reason through the layers in order:

```text
TCP/IP header
  -> STX and message framing where applicable
  -> Data Section 1
      -> Element 55: message format identifier, ATL105
      -> Element 63: number of serialized segments
  -> Segment 100
  -> Section 3 companion segments, when required
  -> ETX/framing where applicable
```

The exact transport framing and control-byte contract must be taken from the applicable ATL105 specification and converter interface. Do not invent a header from a JSON sample.

## Segment 100 Serialization Rules

Segment 100 fields have a fixed order. The converter must serialize them in that order even when some values are empty.

Current validation rules include:

1. Segment Type is fixed as `100`.
2. Segment Length represents the encoded Segment 100 content, not JSON text length.
3. Empty non-trailing fields retain their field separators.
4. Unneeded trailing optional fields may be omitted only as a trailing suffix.
5. A later field must never appear after an omitted field without preserving the omitted field's position.
6. Numeric fields use the specified width and padding rules.
7. Element 63 counts the final serialized segments, including Segment 100.
8. Segment order follows the applicable message layout.
9. Binary or hexadecimal fields use the converter's declared encoding rules.
10. TCP/IP message length is calculated from the required payload representation.

## Empty Fields and Separators

The separator preserves position. If field 7 is empty but field 8 is populated, field 7 still needs its separator:

```text
field-6 FS empty-field-7 FS field-8
```

Do not confuse these cases:

| Case | Expected behavior |
| --- | --- |
| Empty middle field | Preserve its separator |
| Unused trailing optional fields | Omit the unused suffix when allowed |
| Reordered field | Reject |
| Missing field followed by later field | Reject or parse as a shifted message |
| Extra separator in an invalid location | Reject or review according to the specification |

The converter should generate the separators. Test data should express the field value and applicability clearly rather than manually embedding delimiter characters unless the converter contract explicitly requires that.

## Length Rules

Length values must be derived from the serialized representation.

```text
Logical JSON length != Segment Length
Segment Length != TCP/IP Message Length
```

Validation should verify:

- Segment 100 length matches encoded Segment 100 content.
- Each companion segment length matches its encoded content.
- Element 63 matches the actual serialized segment count.
- TCP/IP message length matches the payload-length definition.
- Byte order is correct for numeric transport fields.

A hard-coded length copied from a sample is not sufficient for a generated test case.

## SME Questions

The SME or business analyst should confirm:

1. Which fields are logically empty versus absent because they are trailing optional fields?
2. Does the transaction require a companion segment?
3. Is the data text, numeric, binary, hexadecimal, or EMV TLV?
4. Does the expected result concern business meaning, serialization, transport, or all three?
5. Is the message a request or response with different separator rules?
6. Does the lifecycle follow-up preserve the original serialized identity?

## TBA Artifact Decomposition

```text
BR:
  Segment 100 fields shall be serialized in specification order, preserving
  separators for empty non-trailing fields.

TS:
  Segment 100 with an empty conditional field followed by a populated field.

TC:
  Serialize the message and inspect the field positions and separators.

TD:
  Structured Segment 100 values with field 7 empty and field 8 populated.

Expected:
  PASS when the field-7 position is preserved; FAIL when field 8 shifts left.
```

## Positive, Negative, and Boundary Cases

| Case | Expected result |
| --- | --- |
| Correct field order and separators | Pass |
| Empty middle field retains separator | Pass |
| Trailing optional suffix omitted | Pass when permitted |
| Middle field removed and later field retained | Fail |
| Segment Type changed from `100` to `101` | Fail |
| Segment Length does not match encoded content | Fail |
| Element 63 does not match serialized count | Fail |
| Segment included in JSON but missing from serialized output | Fail |
| Binary/TLV value treated as ordinary text | Fail or review |
| Unspecified framing rule | `REVIEW_REQUIRED` |

## Test-Solution Validation Responsibilities

The independent Test Solution should validate each layer separately:

```text
JSON schema
  -> canonical field semantics
  -> segment compatibility
  -> field ordering and separators
  -> segment lengths
  -> Element 63
  -> transport framing
```

A pass at one layer does not imply a pass at the next layer. For example, valid JSON does not prove valid Segment 100 serialization.

## Covered Training So Far

The Segment 100 training path has now covered:

1. Segment 100's role as the standard transaction core.
2. TCP/IP header, Data Section 1, Segment 100, and Section 3 boundaries.
3. Ordered fields and required, conditional, and optional behavior.
4. Prompt Code and transaction context.
5. Companion-segment compatibility and Element 63 counting.
6. Logical JSON versus serialized ATL105 wire-format behavior.
7. Field separators, empty positions, trailing omission, lengths, order, and framing.
8. SME/TBA decomposition into BR, TS, TC, and TD.

Remaining major training topics are lifecycle correlation and the end-to-end independent validation of AI-generated artifacts.
