# Segment 145 Serialization and Wire-Format Flow

```text
emit SegmentType SegmentLength EnhancedFleetData(
  for each applicable sub-segment:
    emit TableID(3) TableLength(3) TableData(...)
    (Table 002/004's TableData itself contains "|"-delimited repeating sub-fields)
)
```
