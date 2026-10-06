```text
Segment 149 received --> Comdata processes fuel price update --> emit Segment 150
  with TerminalIdentifier echo + DeclineCode (success/failure)
```

## Validator Decision Flow (`Segment150PayloadValidator`, planned)

```text
payload -> SegmentType == "150"? --no--> error
  -> DeclineCode present, 2 chars? --no--> SEG150-R-003
  -> ValidationResult
```
