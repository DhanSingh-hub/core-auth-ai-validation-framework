# Segment 145 End-to-End Flow

```text
Transaction involves an enhanced fleet card (Wex OTR, Comdata, Voyager EMV,
Visa Fleet 2.0, or MasterCard Enhanced Fleet EMV)?
  |
  no --> Segment 145 omitted; consider Segment 101 (plain Fleet) instead
  |
  yes
  v
Segment 101 (Fleet) also present? --yes--> INVALID combination (SEG145-R-002)
  |
  no
  v
Is this Voyager EMV / Visa Fleet 2.0 / Comdata / MasterCard Enhanced Fleet EMV?
  |
  yes --> ONLY Table 004 (Prompt Data) sub-segment is valid (SEG145-R-003)
  |
  no (e.g., WEX OTR) --> Tables 001/002/004/006/007/008 as applicable
```

## Validator Decision Flow (`Segment145PayloadValidator`, planned)

```text
payload -> SegmentType == "145"? --no--> SEG145-R-005
  -> Segment 101 also present? --yes--> SEG145-R-002 (reject)
  -> authorizer in {Voyager EMV, Visa Fleet 2.0, Comdata, MC Enhanced Fleet EMV}
     AND a non-004 table present? --yes--> SEG145-R-003 (reject)
  -> each sub-segment: valid Table ID, length within max? --no--> SEG145-R-006
  -> ValidationResult
```
