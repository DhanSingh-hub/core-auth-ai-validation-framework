```text
Segment 102 (Product Code) contains an EV fuel code AND card type == Visa?
  |
  no --> Segment 156 not applicable
  yes --> emit Segment 156, Table 01 EVTransactionIndicator MUST be "Y"
          (else BUYPASS declines the transaction), plus applicable tables 02-12
```

## Validator Decision Flow (`Segment156PayloadValidator`, planned)

```text
payload -> card type == Visa AND Segment 102 has EV fuel code? --no--> SEG156-R-001
  -> Table01 (EVTransactionIndicator) == "Y"? --no--> SEG156-R-003 (DECLINE)
  -> time-format tables (02-05) match hhmmss, hh 00-99, mm/ss 00-59? --no--> SEG156-R-004
  -> ChargingReasonCode in documented Visa set? --no--> SEG156-R-005
  -> ConnectorType in documented Visa set? --no--> SEG156-R-006
  -> ValidationResult
```
