```text
Day end at a pay point --> send Segment 141 once for this pay point:
  TerminalIdentifier SPDHHeader MonerisTerminalId MonerisMerchantId
  BatchNumber LanguageIndicator NumberOfDebits DebitDollarValue
  NumberOfCredits CreditDollarValue NumberOfCorrections CorrectionsDollarValue
  (all Field-Separator delimited, SEG141-R-002)
```

## Validator Decision Flow (`Segment141PayloadValidator`, planned)

```text
payload -> SegmentType == "141"? --no--> error
  -> exactly one Segment 141 per pay point per day? --no--> SEG141-R-001
  -> all 14 fields present and separator-delimited? --no--> SEG141-R-002/R-003
  -> ValidationResult
```
