```text
Table Load Response includes Merchant Data --> emit Segment DL1:
  "#" MerchantName StoreNumber AddressLine1 AddressLine2 MerchantPhoneNumber
  NumberOfCardTypes [CardType]*(01-99 times) "~"
  |
  v
Any CardType == "173"? --yes--> Segment DL6 (Store and Forward) also required
```

## Validator Decision Flow (`SegmentDL1PayloadValidator`, planned)

```text
payload -> DataTypeIndicator == "#"? --no--> error
  -> EndOfDataIndicator == "~"? --no--> SEGDL1-R-002
  -> CardType repeated NumberOfCardTypes times, 1-99? --no--> SEGDL1-R-004
  -> any CardType == "173"? --yes--> require paired Segment DL6 (SEGDL1-R-005)
  -> ValidationResult
```
