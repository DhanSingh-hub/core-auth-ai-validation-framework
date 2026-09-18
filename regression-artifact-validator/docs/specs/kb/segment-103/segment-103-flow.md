# Segment 103 End-to-End Flow

## 1. Applicability Decision

```text
Financial transaction request assembled
  |
  v
Does the flow involve EBT, SNAP, cash benefit, WIC, or eWIC? --no--> Segment 103 not included
  |
  yes
  v
Is a clerk or preprinted voucher involved? --yes--> populate Clerk ID / Voucher ID
  |
  v
Is the transaction an eWIC operation carrying discounts or product results? --yes--> populate WIC Discount Amount / WIC Product Data
  |
  v
Does the merchant use EBT program data (e.g., HIP)? --yes--> populate EBT Program Data
  |
  v
Assemble Segment 103 as a Data Section 3 companion of Segment 100
```

## 2. Request Serialization Flow

```text
Segment 103 fields in order:
  SegmentType -> SegmentLength -> ClerkId -> VoucherId -> WicDiscountAmount -> WicProductData -> EbtProgramData
  |
  v
For a Financial Transaction REQUEST:
  every field boundary emits a Field Separator, even when the field is empty
  |
  v
For a Financial Transaction RESPONSE:
  fields are concatenated with no Field Separator between them
```

## 3. eWIC Purchase Lifecycle

```text
eWIC Authorization / Benefits Inquiry (3086)
  -> approved products + balance returned (WIC Product Data: EF, EA)
  -> clerk scans eligible items, applies discounts (WIC Discount Amount)
  -> eWIC Purchase Completion (0086)
       -> normal: balances + possible UPC exception/denial info returned
       -> timeout/late response: eWIC Purchase Reversal/Void (8086) instead of a second completion
  -> (offline path) eWIC Voucher Clear (0086) submitted later using Clerk ID / Voucher ID
```

## 4. EBT Program Data (HIP) Flow

```text
HIP-enabled EBT transaction
  |
  v
Program Data TAG 50 (HIP purchase/return amount) or IT (HIP Internet shipping ZIP) sent in request
  |
  v
ACCOUNT TYPE fixed 98 required alongside TAG 50 (not required for TAG IT)
  |
  v
Response carries TAG 51 (HIP incentive earned/returned) and/or TAG 52 (HIP month-to-date incentive earned)
```

## 5. Validator Decision Flow (`Segment103PayloadValidator`)

```text
payload
  |
  v
"Financial Request" object present? --no--> error
  |
  v
"EBT Data Segment" object present? --no--> SEG103-R-002
  |
  v
"Standard Segment" (100) sibling present? --no--> SEG103-R-001
  |
  v
Exactly one "EBT Data Segment" occurrence in the message? --no--> SEG103-R-018
  |
  v
SegmentType == "103"? --no--> SEG103-R-003
SegmentLength matches ^[0-9]{3,4}$ and <= 3334? --no--> SEG103-R-004 / SEG103-R-005
ClerkId / VoucherId numeric <=10 when present? --no--> SEG103-R-009 / SEG103-R-010
WicDiscountAmount blocks match positional format and <=40 bytes? --no--> SEG103-R-011 / SEG103-R-012
WicProductData.totalLength numeric <=2997 and matches data length? --no--> SEG103-R-013
EbtProgramData.totalLength numeric <=264, 1-6 subelements <=44 bytes each,
  TAG in {50, IT, 51, 52}, TAG 50 has accountType 98? --no--> SEG103-R-014 .. SEG103-R-017
  |
  v
ValidationResult (errors list; empty means PASS)
```
