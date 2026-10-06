# Segment 143 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## The Dual-Delimiter Scheme

Two distinct characters carry structural meaning:
- **Field Separator (▲)**: always follows Segment Type and Segment Length; follows the LAST tax amount for a product (signals next-product or end-of-segment).
- **Tax by Product Field Delimiter (\\)**: follows a tax amount ONLY when another tax amount for the SAME product follows.

## Field Omission on "N"

When Inclusive/Exclusive = `N`, Tax Type and Tax Amount are omitted entirely from the wire format — the next character after `N` is the delimiter (▲ or \\), not empty field placeholders.

## What Not To Assume

- Do not use `\` between products — it is reserved for same-product tax entries only.
- Do not emit empty Tax Type/Tax Amount fields for a "N" tax — they must be structurally absent.
- Do not assume every product has exactly 3 tax entries — 0 to 3 are valid, with early termination via Field Separator.
