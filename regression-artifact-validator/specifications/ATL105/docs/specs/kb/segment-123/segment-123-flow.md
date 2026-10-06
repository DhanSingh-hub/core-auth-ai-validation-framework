```text
Transaction involves tokenized data OR MasterCard Token/DSRP OR Visa TAVV? --yes--> emit Segment 123:
  SegmentType(123) FS SegmentLength FS [CAVV]? FS [TokenRequestorID]? FS(after Field 4)
  [TokenPANSuffix]? FS [CryptogramTokenData]? FS [SafeKeyData]? FS [SafeKeyResponse]? FS
  [TAVVCryptogram]? FS [TAVVResultCode]? FS
  |
  v
MasterCard DSRP AND SecureCode/Identity Check AAV both present? --yes-->
  UCAF (Segment 111 Table ID 36) carries AAV; TAVV (Segment 123 Elem 237) carries DSRP cryptogram;
  UCAF Security Level Code positions 1-2 = "21"
```

## Validator Decision Flow (`Segment123PayloadValidator`, planned)

```text
payload -> SegmentType == "123"? --no--> error
  -> SegmentLength includes SegmentType length + all separators? --no--> SEG123-R-002 violation
  -> total length <= 186? --no--> SEG123-R-003 violation
  -> every field (populated or not) followed by its Field Separator? --no--> SEG123-R-004 violation
  -> TAVV Cryptogram populated only on request (not response)? --no--> SEG123-R-010 violation
  -> companion Segment 111 Table ID 36 present AND Segment 123 TAVV present? --yes--> verify UCAF Security Level Code == "21" (SEG123-R-011, pending SEG123-SME-001)
  -> ValidationResult
```
