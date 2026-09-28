```text
Moneris receives Segment 139 --> responds with Segment 140:
  reiterate TerminalIdentifier, SPDHHeader, MonerisTerminalId, MonerisMerchantId,
  BatchNumber (fields 1-6 of the request), then append:
    ResponseDisplay (text) NumberOfDebits DebitDollarValue
    NumberOfCredits CreditDollarValue NumberOfCorrections CorrectionsDollarValue
```

## Validator Decision Flow (`Segment140PayloadValidator`, planned)

```text
payload -> SegmentType == "140"? --no--> error
  -> first 6 fields match the corresponding Segment 139 request? --no--> SEG140-R-001
  -> DebitDollarValue/CreditDollarValue/CorrectionsDollarValue match +/-9(16)v99? --no--> SEG140-R-003
  -> ValidationResult
```
