```text
Moneris receives Segment 141 --> responds with Segment 142:
  reiterate TerminalIdentifier, SPDHHeader, MonerisTerminalId, MonerisMerchantId,
  BatchNumber (fields 1-6 minus the Language Indicator, which is not echoed),
  then append ResponseDisplay (text) and MAC (Element 210)
```

## Validator Decision Flow (`Segment142PayloadValidator`, planned)

```text
payload -> SegmentType == "142"? --no--> error
  -> reiterated fields match the corresponding Segment 141 request? --no--> SEG142-R-001
  -> MAC present, 16 characters, validates the response? --no--> SEG142-R-003
  -> ValidationResult
```
