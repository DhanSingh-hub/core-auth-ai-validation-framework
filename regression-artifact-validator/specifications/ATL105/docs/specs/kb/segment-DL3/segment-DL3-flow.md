```text
Table Load Response includes Date/Time data --> emit Segment DL3:
  ":" DayOfWeek CurrentDate CurrentTime CutTime Password "~"
```

## Validator Decision Flow (`SegmentDL3PayloadValidator`, planned)

```text
payload -> DataTypeIndicator == ":"? --no--> error
  -> EndOfDataIndicator == "~"? --no--> error
  -> CurrentDate/CurrentTime/CutTime well-formed? --no--> error
  -> ValidationResult
```
