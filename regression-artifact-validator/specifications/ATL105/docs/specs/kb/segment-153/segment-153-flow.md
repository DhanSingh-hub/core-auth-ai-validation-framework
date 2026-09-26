```text
Network-tokenized transaction --> emit Segment 153 with applicable sub-tables:
  001 (Network Token) 002 (Expiration) 003 (Provisional Fee Indicator)
  004 (Input Indicator) 005 (Eligible Indicator) 006 (PAN Indicator)
```

## Validator Decision Flow (`Segment153PayloadValidator`, planned)

```text
payload -> SegmentType == "153"? --no--> error
  -> each sub-table's TableID/TableLength/TableData matches its fixed spec? --no--> SEG153-R-003
  -> ValidationResult
```
