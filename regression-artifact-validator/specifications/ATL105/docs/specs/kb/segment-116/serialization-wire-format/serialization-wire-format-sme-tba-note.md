# Segment 116 Serialization and Wire-Format Behavior

## Representations

```text
TransArmor Key/Key ID Load intent
  -> structured request JSON
  -> Section 11.7.5 serialized TransArmor request (layout not shown in this source)
  -> transport payload
  -> TransArmor Load Response (layout not shown in this source)
```

Logical JSON is not a wire-format guarantee. Unlike every other trained segment, the converter/serializer rules below are necessarily incomplete because the field table itself is missing from this workspace's source.

## Request Rules (What We Know)

1. Data Section 1 contains Elements 55 and 63, with a separator between them and after Element 63.
2. Data Section 2 contains Segment 116.
3. Segment Type is `116`.
4. Segment 116 has a maximum serialized length of 50 characters.
5. Segment Length (field 2) is expected by convention but not individually confirmed for Segment 116.
6. **Fields 3 and beyond are unknown.** Do not invent a serialization order for them.

## Response Rules (What We Know)

The TransArmor Load Response is confirmed to carry Key ID (155), Key Data Length (156), and Key Data (157), but its full layout (field order, separators vs. positional, other required fields) is not in this source. Do not invent a response format; validate only the three known field definitions once a response fixture is supplied.

## Mutations to Prepare (Once the Field Layout Is Known)

- Segment Type not `116`.
- Segment Length incorrect or exceeds `50`.
- Segment 116 present in a request envelope other than the TransArmor PKI Encryption and Tokenization Load Request.
- Segment 100 or a Data Section 3 segment present alongside Segment 116 (pending `SEG116-SME-002`).
- Key ID not exactly 11 alphanumeric characters (response).
- Key Data Length outside 000-999 (response).
- Key Data exceeding 999 bytes (response).

**Do not** attempt to define mutations for the unknown request fields until `SEG116-SME-001` is resolved.
