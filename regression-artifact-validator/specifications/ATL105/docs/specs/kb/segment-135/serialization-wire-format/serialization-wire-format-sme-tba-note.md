# Segment 135 Serialization and Wire-Format Behavior: SME and TBA Learning Note

Field Separators between fields 1-2 and 2-3; segment ends with a trailing separator. The Moneris Data field's internal `<tag><len><data>` sub-structure is NOT separator-delimited — do not insert Field Separators inside it.
