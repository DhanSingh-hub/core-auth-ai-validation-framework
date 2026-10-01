# Segment 143 Serialization and Wire-Format Flow

```text
emit SegmentType FS SegmentLength FS NumberOfProducts FS
for each product (up to 10, max 360 bytes total):
  emit ProductCode
  emit Tax1: I/E/N flag [+ TaxType + TaxAmount if I/E]
    --> another tax for this product? "\" : FS(next product / end)
  emit Tax2 (if present): I/E/N flag [+ TaxType + TaxAmount if I/E]
    --> another tax for this product? "\" : FS(next product / end)
  emit Tax3 (if present): I/E/N flag [+ TaxType + TaxAmount if I/E]
    --> FS (next product / end of segment)
```
