# Segment 119 Serialization and Wire-Format: SME/TBA Note

## Two Serialization Zones

Segment 119 has an unusual internal boundary:

```text
Fields 1-17: separator-delimited, including empty fields
Fields 18-20: concatenated inside each card bucket
Final field 20: followed by a Field Separator
```

The parser must know whether it is reading the header/aggregate zone or the repeated card-bucket zone.

## Common Failure Modes

- omitting an empty separator in fields 1-17;
- inserting separators between Card Label, Count, and Amount;
- forgetting the separator after the final Card Type Total Amount;
- calculating Segment Length without separators;
- accepting a 4-digit Segment Length when Element 84 is 3 digits for Segment 119;
- treating Segment 119 as a normal Financial Transaction Section 2 segment.

## Converter Boundary

A JSON artifact can be structurally plausible while its wire representation is invalid. Request fixtures must retain message-section context, field order, separators, and bucket repetition metadata.
