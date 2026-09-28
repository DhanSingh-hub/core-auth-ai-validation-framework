# Segment 157 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## The Dual-Delimiter Scheme

- Field Separator (▲): after Segment Type, Segment Length; after the final Adjusted Product Amount in the segment.
- Product Data Field Delimiter (\\): always after Quantity and Unit Price; after Adjusted Product Amount when NOT the last element.

## Decimal Encoding

Quantity and Unit Price must encode assumed decimal digits as literal digit positions (leading zeros included) — do not strip leading zeros or treat the value as a plain integer.

## What Not To Assume

- Do not treat `\` and `▲` as interchangeable — their placement rules differ from Segment 143's (here `\` is the default per-field delimiter within a product, not merely an "another tax follows" signal).
- Do not omit leading zeros in Quantity/Unit Price — `'25'` for a value that should be `'205'` is explicitly invalid.
