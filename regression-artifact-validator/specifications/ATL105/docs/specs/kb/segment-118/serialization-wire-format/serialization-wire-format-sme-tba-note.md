# Segment 118 Serialization and Wire-Format Behavior

## Representations

```text
Proprietary data load intent
  -> structured request/response JSON
  -> Section 11.7.6/11.7.7 serialized message
  -> transport payload
```

## Request Rules

1. Data Section 1 contains Elements 55 and 63 (Number of Segments, fixed value 1), with separators as usual.
2. Data Section 3 contains Segment 118 at field 3 only; there is no Data Section 2 and no Segment 100.
3. Fields 1-13 are separated by Field Separators, including empty fields, with a Field Separator following field 13.
4. Segment Type is `118`; Segment Length is exactly 4 numeric digits (unusual: most other segments use 3).
5. When Prompt Code is `903` or `905`, field 14 follows, with its own Field Separator after it.
6. When Prompt Code is `901`, `902`, or `904`, there is no request-side field 14 - those Prompt Codes only produce response payloads.
7. Segment 118 has a maximum serialized length of 3,800 characters, in both request and response.

## Response Rules

1. Data Section 1 is positional: Response Code (len 1), Download Indicator (len 1), Initiation Date (len 6), Initiation Time (len 4), Sequence Number (len 6) - no Field Separators.
2. Data Section 3 contains Segment 118 at field 6; fields within Segment 118 are also positional (no separators) in the response.
3. When Prompt Code is `901`, fields 14-20 follow the Custom Receipt Text layout, with a repeated Text Length/Text Data block.
4. When Prompt Code is `902`, field 14 is Card Table Data, whose length is derived from Segment Length (field 2), not a separate length field.
5. When Prompt Code is `904`, fields 14-27 follow the Host Discount layout, with a repeated 12-field discount block.

## Mutations to Prepare

- Segment Type not `118`.
- Segment Length not exactly 4 digits, or exceeding 3,800.
- Segment 100 or a Data Section 2 present alongside Segment 118 (violates the explicit exclusion).
- Prompt Code not one of 901-905.
- Prompt Code, Pending not one of 0901/0902/0904/0981.
- Card Table Type (902 payload) not one of 0001-0006.
- Response Code outside the documented Proprietary Data Load set (H, O, T, U, V, W, X, Y).
- Request-side field 14 present when Prompt Code is 901/902/904 (response-only codes).
- Response-side field 14+ present when Prompt Code is 903/905 (request-only codes).
- Number of Receipt Text Lines / Number of Discounts not matching the actual repeat-block count.
