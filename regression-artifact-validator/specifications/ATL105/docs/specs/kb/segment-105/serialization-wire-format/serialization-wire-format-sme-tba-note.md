# Segment 105 Serialization and Wire-Format Learning Note

JSON structure, Segment 105 serialization, and TCP/IP transport framing are separate representations. A converter-ready JSON fixture must preserve the Segment 105 field order; the serialized message must calculate Segment Length from encoded content rather than JSON character count.

The Totals Request layout establishes that Segment Length includes the Segment Type and field separators. Conditional Employee Number and Password fields must preserve their positions when an approved later field is present. Currency Code is optional; any trailing omission must follow the source's wire-layout rules.

Validate: fixed Segment Type `105`, fixed Prompt Code `990`, ordered fields, separators for empty non-trailing fields, encoded Segment Length, and applicable outer message framing. Transport-specific byte order remains dependent on the applicable converter contract.