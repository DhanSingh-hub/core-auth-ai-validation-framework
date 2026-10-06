```text
emit SegmentType(102) FS SegmentLength FS ServiceLevel FS NumberOfProducts FS
     repeat(1..10): ProductCode Quantity[assumed-decimal] "Delim" UnitOfMeasure UnitPrice[assumed-decimal] "Delim" ProductAmount(separator position-dependent)
```
