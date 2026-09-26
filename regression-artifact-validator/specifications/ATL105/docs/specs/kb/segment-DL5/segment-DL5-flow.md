```text
Device is BUYPASS-managed AND software update scheduled via IP --> emit Segment DL5:
  "$" NewSoftwareVersion SoftwareTerminalRecordID SoftwareLoadIPURLAddress
      SoftwareLoadRequestDate SoftwareLoadRequestTime SoftwareLoadType "~"
Vendor-managed device? --yes--> Segment DL5 MUST NOT be used
```

## Validator Decision Flow (`SegmentDL5PayloadValidator`, planned)

```text
payload -> DataTypeIndicator == "$"? --no--> error
  -> EndOfDataIndicator == "~"? --no--> error
  -> device is BUYPASS-managed? --no--> SEGDL5-R-001 violation
  -> ValidationResult
```
