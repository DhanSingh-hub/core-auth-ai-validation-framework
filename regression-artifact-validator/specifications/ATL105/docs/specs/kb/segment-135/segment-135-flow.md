# Segment 135 End-to-End Flow

```text
Transaction destined for the Moneris authorizer?
  |
  no --> Segment 135 omitted (SEG135-R-001)
  yes --> emit SegmentType(135) FS SegmentLengthIndicator FS
          MonerisData(<tag><len><data> TLV sub-fields, no internal separators) FS
```

## Validator Decision Flow (`Segment135PayloadValidator`, planned)

```text
payload -> "Moneris-destined transaction"? --no--> pass (conditional)
  -> SegmentType == "135"? --no--> SEG135-R-002
  -> MonerisData present, <=100 chars, valid <tag><len><data> structure? --no--> SEG135-R-005
  -> ValidationResult
```
