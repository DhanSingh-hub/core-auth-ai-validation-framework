# Segment 134 End-to-End Flow

## 1. Field Assembly

```text
Host prepares a Financial Transaction Response requiring transaction attribute metadata
  |
  v
SegmentType = 134
SegmentLength = 4-character encoded length (max 19; provisional 4-digit
                classification, SEG134-SME-001)
SettlementType = D | S | X (X requires confirmed merchant configuration,
                 SEG134-SME-002)
SignatureRequired = T | F | Space
ReceiptCardDescription = left-justified, space-filled, 10 characters
```

## 2. Serialization Flow

```text
emit SegmentType SegmentLength SettlementType SignatureRequired ReceiptCardDescription
  (NO Field Separators anywhere — same fixed-length pattern as Segment 131)
```

## 3. Validator Decision Flow (`Segment134PayloadValidator`, planned)

```text
payload
  |
  v
SegmentType == "134"? --no--> error
SettlementType in {D,S,X}? --no--> SEG134-R-005
  (X requires a confirmed merchant configuration — REVIEW_REQUIRED pending SEG134-SME-002)
SignatureRequired in {T,F," "}? --no--> SEG134-R-006
ReceiptCardDescription left-justified, space-filled, <=10 chars? --no--> SEG134-R-007
  |
  v
ValidationResult (errors list; empty means PASS)
```
