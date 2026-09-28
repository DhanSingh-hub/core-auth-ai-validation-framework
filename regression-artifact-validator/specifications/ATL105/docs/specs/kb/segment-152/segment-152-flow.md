```text
Segment 151 received --> emit Segment 152 with MarketBasketData
  (1 DV + up to 10 PI + up to 10 PU datasets) FS
```

## Validator Decision Flow (`Segment152PayloadValidator`, planned)

```text
payload -> SegmentType == "152"? --no--> error -> ValidationResult
```
