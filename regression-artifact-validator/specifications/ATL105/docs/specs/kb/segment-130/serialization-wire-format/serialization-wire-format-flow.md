# Segment 130 Serialization and Wire-Format Flow

```text
Segment 130 payload ready to serialize
  |
  v
emit SegmentType FS SegmentLength FS CAPublicKeyFileChecksum FS
     EMVCardSequenceNumber FS EMVChipDataLength FS EMVChipData FS
  (each FS present even when the field between two separators is empty)
  |
  v
Does the transaction have EMV Additional Information items to send?
  |
  no  --> message ends here (no EMV Additional Information Section)
  |
  yes --> for each item:
            emit Indicator + Length + Information (NO separators between
            these three fields, and NO separator between consecutive
            repetitions)
          after the LAST repetition: emit exactly one FS
  |
  v
Segment Length is always 4 digits, valid range 0001-3043
(upper bound corroborated by field-length arithmetic; see
serialization-wire-format-sme-tba-note.md).
```
