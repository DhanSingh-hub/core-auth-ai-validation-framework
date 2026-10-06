```text
Day-end period arrives at a pay point
  --> Device sends Segment 139 to retrieve prior-period debit totals from Moneris
  --> Host/Moneris responds with Segment 140 (reiteration + totals)
  --> Device later sends Segment 141 (Batch Close) to close out the pay point
  --> Host/Moneris responds with Segment 142 (reiteration + MAC-secured text)
```

## Validator Decision Flow (`Segment139PayloadValidator`, planned)

```text
payload -> SegmentType == "139"? --no--> SEG139-R-002
  -> all 6 required fields (TerminalIdentifier, SPDHHeader, MonerisTerminalId,
     MonerisMerchantId, BatchNumber, LanguageIndicator) present? --no--> SEG139-R-003
  -> ValidationResult
```
