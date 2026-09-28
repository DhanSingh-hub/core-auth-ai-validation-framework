```text
Terminal has EMV Floor Limits Special flag set AND floor-limit data changed at Host --> emit Segment DL8:
  "%" SegmentLengthIndicator(3, excl. DataTypeIndicator)
  repeat(1..24): RID(10) StandInIndicator(1) FloorLimit(12) BuypassRIDCardType(3)
  (no End-of-Data Indicator — framing is by Segment Length Indicator, not markers)
```

## Validator Decision Flow (`SegmentDL8PayloadValidator`, planned)

```text
payload -> DataTypeIndicator == "%"? --no--> error
  -> SegmentLengthIndicator numeric, 3 digits? --no--> error
  -> RID repetitions count <= 24 AND total <= 624 bytes? --no--> SEGDL8-R-003 violation
  -> terminal has EMV Floor Limits Special flag? --no--> SEGDL8-R-001 violation (unexpected segment)
  -> ValidationResult
```
