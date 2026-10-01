```text
First Data Auth Optimizer service used AND transaction qualifies?
  |
  no --> Segment 155 omitted
  yes --> merchant sent Account Updater Request Indicator (Segment 111,
          Table 060 Sub-table 01) = "Y" (or "I" for some fields)?
    no --> Segment 155 omitted or minimal
    yes --> emit applicable sub-tables (001-006) per card network (Visa/MasterCard)
```

## Validator Decision Flow (`Segment155PayloadValidator`, planned)

```text
payload -> Auth Optimizer service used? --no--> SEG155-R-001 (not applicable)
  -> Segment 111 Table 060/01 Indicator == "Y"/"I"? --no--> sub-tables should be absent
  -> CardStatus in {A,E,Q,C,U}? --no--> SEG155-R-004
  -> ResultCode in documented VAU set? --no--> SEG155-R-005
  -> ValidationResult
```
