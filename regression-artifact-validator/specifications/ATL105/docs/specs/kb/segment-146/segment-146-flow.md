# Segment 146 End-to-End Flow

```text
Host processes Segment 145 Enhanced Fleet Request
  |
  v
emit Segment 146 with applicable response tables:
  001 (Settlement Indicator flags) 002 (Non-Fuel Limits, skip if Comdata)
  003 (Fuel Limits, Appendix F codes) 004 (Prompt Formats, edit masks)
  005 (Customer Information) 010 (Additional Response Data)
```

## Validator Decision Flow (`Segment146PayloadValidator`, planned)

```text
payload -> SegmentType == "146"? --no--> error
  -> authorizer == Comdata AND Table 002 present? --yes--> SEG146-R-003 (reject)
  -> Table 004 present? --yes--> validate edit-mask grammar (scope: SEG146-SME-002)
  -> Table 003 present? --yes--> validate product codes against Appendix F (scope: SEG146-SME-003)
  -> ValidationResult
```
