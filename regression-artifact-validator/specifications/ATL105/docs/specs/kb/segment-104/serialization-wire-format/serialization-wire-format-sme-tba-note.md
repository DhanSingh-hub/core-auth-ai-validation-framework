# Segment 104 Serialization and Wire-Format Behavior: SME and TBA Learning Note

A logical JSON object is not an ATL105 wire segment. Section 12.5 requires Segment 104 fields in order, separated by Field Separators; an empty non-trailing field still emits its separator.

## Segment 104 Wire Order

```text
85 Segment Type
FS
84 Segment Length
FS
80 Purchase Code
FS
74 PC Tax Amount
FS
73 PC Freight Amount
FS
72 PC Duty Amount
FS
89 Ship-to Country Code
FS
90 Ship-to Postal Code
FS
88 Ship-from Postal Code
FS
29 Direct Marketing Invoice Number
```

## Rules

1. Segment Type is `104`.
2. Segment Length is the encoded segment length, not JSON character count.
3. The declared length must be three digits and within `001–086`.
4. Empty middle fields retain separators.
5. Trailing empty values may only be omitted as a trailing suffix when the converter contract permits it.
6. Element 63 counts Segment 100, Segment 104, and every other serialized companion segment.

The Test Team must use converter output to validate actual bytes and separators. A payload object alone cannot prove declared segment length correctness.
