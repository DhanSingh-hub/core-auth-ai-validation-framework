```text
Comdata transaction with adjusted (tax/discount/coupon-inclusive) product amounts?
  |
  no --> use Segment 102 instead (mutually exclusive, SEG157-R-002)
  yes --> card is Comdata? --no--> FAIL (SEG157-R-001)
          both 102 and 157 present? --yes--> FAIL (SEG157-R-002)
          emit Segment 157: fuel products first, up to 10 products,
            product codes <= 899 (or 955 Cash Back)
          sum(AdjustedProductAmount) == Segment100[41+58+99+17]? --no--> FAIL (SEG157-R-005)
```

## Validator Decision Flow (`Segment157PayloadValidator`, planned)

```text
payload -> card == Comdata? --no--> SEG157-R-001
  -> Segment 102 also present? --yes--> SEG157-R-002 (decline)
  -> any ProductCode > 899 and != 955? --yes--> SEG157-R-003 (decline)
  -> fuel products first, <=10 products? --no--> SEG157-R-004
  -> sum(AdjustedProductAmount) == Segment100 amounts? --no--> SEG157-R-005
  -> Quantity/UnitPrice encode assumed decimals as full digit-width? --no--> SEG157-R-009
  -> delimiters correct ("\\" within product, "▲" between)? --no--> SEG157-R-008
  -> ValidationResult
```
