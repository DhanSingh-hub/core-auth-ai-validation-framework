# Segment 157 Serialization and Wire-Format Flow

```text
emit SegmentType FS SegmentLength FS ServiceLevel NumberOfProducts FS
for each product (fuel first, up to 10, max 370 bytes):
  emit ProductCode UnitOfMeasure Quantity "\" UnitPrice "\" ProductAmount
    --> last element in segment? FS : "\"(if more products follow, continue)
```
