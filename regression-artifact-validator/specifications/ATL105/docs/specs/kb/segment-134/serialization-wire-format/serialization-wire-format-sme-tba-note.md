# Segment 134 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## No Field Separators

Segment 134 uses the same no-separator, fixed-length wire format as Segment 131: "The fields are not separated by Field Separators. When a field is not populated, the next field immediately follows."

## What Not To Assume

- Do not insert Field Separators anywhere in Segment 134.
- Do not assume the 4-digit Segment Length classification is settled — flag `SEG134-SME-001`.
