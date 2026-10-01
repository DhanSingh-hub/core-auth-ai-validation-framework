```text
Comdata fuel price update needed --> emit SegmentType(149) FS SegmentLength FS
  TerminalIdentifier FS PriceData("|TAG:VALUE|TAG:VALUE|...") FS
```

## Validator Decision Flow (`Segment149PayloadValidator`, planned)

```text
payload -> authorizer == Comdata? --no--> SEG149-R-001 (not applicable)
  -> SegmentType == "149"? --no--> error
  -> PriceData valid "|TAG:VALUE|" structure? --no--> SEG149-R-003
  -> ValidationResult
```
