# Segment 131 Serialization and Wire-Format Flow

```text
Segment 131 payload ready to serialize
  |
  v
emit SegmentType SegmentLength CAPublicKeyFileChecksum EMVChipDataLength EMVChipData
  (NO Field Separators anywhere — fixed-length/positional encoding, SEG131-R-006)
  |
  v
Does the response have EMV Additional Information items to echo?
  |
  no  --> message ends here
  |
  yes --> for each item: emit Indicator + Length + Information (no separators)
          total section size must not exceed 2,800 bytes (NOT 2,000 —
          Segment 130's cap is different)
```
