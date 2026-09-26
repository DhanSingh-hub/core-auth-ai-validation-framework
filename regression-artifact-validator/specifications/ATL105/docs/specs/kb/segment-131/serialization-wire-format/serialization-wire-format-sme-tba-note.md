# Segment 131 Serialization and Wire-Format Behavior: SME and TBA Learning Note

## The First No-Separator Segment

Segment 131 is the first segment documented in this KB with **zero** Field Separators. Section 12.21 states plainly: *"Fields are not separated by Field Separators. When a field is not populated, the next field immediately follows."* Every prior segment (108, 111, 114, 130) uses separators throughout; Segment 115 uses separators except a trailing one. Segment 131 uses none.

## Practical Implication for Parsers

Because there are no delimiters, a Segment 131 parser must rely entirely on **fixed field lengths** (or length-prefixed fields, for EMV Chip Data and the EMV Additional Information Section) to know where one field ends and the next begins. An empty field cannot be detected by an empty span between separators — it simply is not present, and the next field's bytes start immediately where the empty field's bytes would have started.

## Two Different Repeating-Section Caps

Segment 130's EMV Additional Information Section caps at 2,000 bytes; Segment 131's caps at 2,800 bytes. Both are structurally the same 3-field repeating group (Indicator, Length, Information), but the caps are independently documented and must not be conflated.

## What Not To Assume

- Do not insert Field Separators anywhere when serializing Segment 131.
- Do not assume the 2,000-byte cap from Segment 130 applies here — it is 2,800.
- Do not assume "Source: Device" for EMV Chip Data Length/EMV Chip Data (fields 4-5) is literal — flag `SEG131-SME-003` before treating it as an enforced provenance rule.
