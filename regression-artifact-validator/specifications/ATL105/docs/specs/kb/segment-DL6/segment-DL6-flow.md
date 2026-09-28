```text
Table Load Response AND Segment DL1 Card Type includes 173? --yes--> emit Segment DL6:
  "\" StartTime(HHMM) EndTime(HHMM) "~"
Segment DL1 Card Type != 173 for all repetitions? --> Segment DL6 MUST NOT be sent
```

## Validator Decision Flow (`SegmentDL6PayloadValidator`, planned)

```text
payload -> DataTypeIndicator == "\\"? --no--> error
  -> EndOfDataIndicator == "~"? --no--> error
  -> StartTime/EndTime match HHMM format? --no--> error
  -> companion Segment DL1 has CardType 173? --no--> SEGDL6-R-001 violation (unexpected segment)
  -> ValidationResult
```
