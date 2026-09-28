```text
WEX Financial Transaction response --> emit Segment 148 with
  RestrictionCode(00/01/06/07/08/10/11/12/13)=Amount,Quantity UOM
```

## Validator Decision Flow (`Segment148PayloadValidator`, planned)

```text
payload -> SegmentType == "148"? --no--> error
  -> RestrictionCode in documented set? --no--> SEG148-R-004
  -> ValidationResult
```
