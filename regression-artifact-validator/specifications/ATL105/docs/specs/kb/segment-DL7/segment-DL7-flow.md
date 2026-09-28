```text
Table Load Response includes Supplemental Terminal data --> emit Segment DL7:
  "^" SegmentLengthIndicator(3, excl. DataTypeIndicator) DownloadData(<tag><len><data> TLV, ref Appendix W)
  (no End-of-Data Indicator — framing is by Segment Length Indicator, not markers)
```

## Validator Decision Flow (`SegmentDL7PayloadValidator`, planned)

```text
payload -> DataTypeIndicator == "^"? --no--> error
  -> SegmentLengthIndicator numeric, 3 digits? --no--> error
  -> declared length (excl. Data Type Indicator) matches actual payload length? --no--> SEGDL7-R-002 violation
  -> DownloadData parses as <tag><len><data> TLV per Appendix W? --no--> SEGDL7-R-003 violation (PROVISIONAL pending Appendix W)
  -> ValidationResult
```
