```text
Table Load Response includes Dial String data --> emit Segment DL2:
  "!" DialStringType(=1)
  Primary: RedialCount AccessCode? PauseIndicator?("B") PhoneNumber Terminator("A")
  Secondary: RedialCount AccessCode? PauseIndicator?("B") PhoneNumber Terminator("F")
  "~"
```

## Validator Decision Flow (`SegmentDL2PayloadValidator`, planned)

```text
payload -> DataTypeIndicator == "!"? --no--> error
  -> DialStringType == "1"? --no--> error
  -> Primary Terminator == "A"? --no--> error
  -> Secondary Terminator == "F"? --no--> error
  -> EndOfDataIndicator == "~"? --no--> error
  -> ValidationResult
```
