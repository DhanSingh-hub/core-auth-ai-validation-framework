```text
InComm OTC transaction --> Segment 151 in any Data Section 3 field slot:
  SegmentType(151) FS SegmentLengthIndicator(4-digit) FS
  MarketBasketData(1 DV dataset + up to 10 PI datasets, external format doc) FS
```

## Validator Decision Flow (`Segment151PayloadValidator`, planned)

```text
payload -> SegmentType == "151"? --no--> error
  -> MarketBasketData present, <=2300/2309/3334 chars (provisional)? --no--> SEG151-R-002/R-005
  -> ValidationResult
```
