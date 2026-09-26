# Segment 143 End-to-End Flow

## 1. Applicability Gate

```text
Financial Transaction Request needs tax-by-product reporting?
  |
  no --> Segment 143 omitted
  yes --> Segment 102 (Product Code Data Segment) MUST also be present
          (SEG143-R-001) — Segment 143 cannot appear alone
```

## 2. Per-Product Tax Entry Decision (repeated up to 10 times)

```text
For each product (matching Segment 102's entries, same order):
  emit ProductCode
  |
  v
  Tax 1 applicable? --no--> emit "N", then FS (fields 6-7 OMITTED, not blank)
                    --yes--> emit I/E flag, TaxType, TaxAmount
  |
  v (if Tax 1 emitted)
  Tax 2 applicable? --no--> emit FS, move to next product
                    --yes--> emit "\" (Tax by Product Field Delimiter),
                              then I/E flag, TaxType, TaxAmount for Tax 2
  |
  v (if Tax 2 emitted)
  Tax 3 applicable? --no--> emit FS, move to next product
                    --yes--> emit "\", then Tax 3's fields, then FS
```

## 3. Validator Decision Flow (`Segment143PayloadValidator`, planned)

```text
payload -> Segment 102 present with matching product count/order? --no--> SEG143-R-001
  -> SegmentType == "143"? --no--> SEG143-R-003
  -> NumberOfProducts matches Segment 102's count? --no--> SEG143-R-004
  -> for each product: I/E flag valid (I/E/N)? --no--> SEG143-R-006
     if "N": TaxType/TaxAmount fields absent? --no--> SEG143-R-006
     TaxType in {GST,HST,PST} when present? --no--> SEG143-R-007 [PROVISIONAL P-01]
     delimiter between same-product taxes is "\", not FS? --no--> SEG143-R-008
  -> ValidationResult
```
