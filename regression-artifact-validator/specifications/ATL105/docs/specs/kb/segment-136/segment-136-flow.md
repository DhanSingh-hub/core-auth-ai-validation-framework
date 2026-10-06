# Segment 136 End-to-End Flow

```text
Financial or Key Load transaction with Moneris authorizer response due
  --> emit SegmentType(136) SegmentLength MonerisData(<tag><len><data>)
      [FS placement between fields 1-2/2-3 provisional, SEG136-SME-002]
      trailing FS follows the segment
```

## Validator Decision Flow (`Segment136PayloadValidator`, planned)

```text
payload -> SegmentType == "136"? --no--> SEG136-R-002
  -> MonerisData present, <=100 chars, valid TLV? --no--> SEG136-R-005
  -> ValidationResult
```
