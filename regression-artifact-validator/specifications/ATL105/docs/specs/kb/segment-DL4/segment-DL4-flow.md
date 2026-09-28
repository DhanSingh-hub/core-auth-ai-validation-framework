```text
Device is BUYPASS-managed AND software update scheduled --> emit Segment DL4:
  "@" NewSoftwareVersion SoftwareTerminalRecordID SoftwareLoadPhoneNumber
      SoftwareLoadRequestDate SoftwareLoadRequestTime SoftwareLoadType "~"
Vendor-managed device? --yes--> Segment DL4 MUST NOT be used
```

## Validator Decision Flow (`SegmentDL4PayloadValidator`, planned)

```text
payload -> DataTypeIndicator == "@"? --no--> error
  -> EndOfDataIndicator == "~"? --no--> error
  -> device is BUYPASS-managed? --no--> SEGDL4-R-001 violation
  -> ValidationResult
```
