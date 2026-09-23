# Segment 108 End-to-End Flow

## 1. Message-Family Decision

```text
Transaction initiated at POS
  |
  v
Is this a Loyalty Card Transaction (add account, coupon/points redemption,
account inquiry, sale update, totals report, or a related loyalty advice function)?
  |
  no --> Financial Transaction Request (Segment 108 never appears here)
  |
  yes
  v
Assemble Loyalty Card Transaction Request:
  Data Section 2: Segment 100 (Standard Message Data Segment)
  Data Section 3: Segment 108 (required) + Segment 114 (optional, SKU data)
```

## 2. Card-Present vs Card-Not-Present Decision

```text
Loyalty Card Transaction Request being assembled
  |
  v
Is the loyalty card physically present / readable?
  |
  yes --> populate Loyalty Account Number (139) [+ Loyalty Track 2 Data (147) if swiped]
  |
  no --> populate Street Address (144) + Phone Number, Loyalty (145) instead
```

## 3. Update Code Decision

```text
Clerk selects a loyalty function at the POS
  |
  +-- Add account ------------------> Update Code = A
  +-- Coupon redemption ------------> Update Code = C  (+ Coupon ID, Coupon Amount)
  +-- Expiration date update -------> Update Code = E
  +-- Account inquiry --------------> Update Code = I  (host returns Loyalty Print Data)
  +-- Points redemption ------------> Update Code = P  (+ Points to Redeem)
  +-- Sale update ------------------> Update Code = S
  +-- Totals Report, Loyalty -------> Update Code = T
  +-- Update account ---------------> Update Code = U
  +-- Reversal of coupon/points redeemed --> [PROVISIONAL P-02: no documented code]
```

## 4. Request Serialization Flow

```text
Segment 108 fields in order:
  SegmentType -> SegmentLength -> LoyaltyProgramId -> LoyaltyAccountNumber ->
  PointsToRedeem -> CouponId -> CouponAmount -> UpdateCode -> StreetAddress ->
  PhoneNumberLoyalty -> ExpirationDate -> PaymentTenderType -> LoyaltyTrack2Data ->
  LoyaltyInformationVersion -> UnitOfWork
  |
  v
Every field boundary emits a Field Separator, even when the field between two
separators is empty; a Field Separator also follows the last field (field 15).
```

## 5. Validator Decision Flow (`Segment108PayloadValidator`)

```text
payload
  |
  v
"Loyalty Card Transaction Request" object present? --no--> error
  |
  v
"Loyalty Card Data Segment" object present? --no--> SEG108-R-002
  |
  v
"Standard Segment" (100) sibling present? --no--> SEG108-R-001
  |
  v
Exactly one "Loyalty Card Data Segment" occurrence in the message? --no--> SEG108-R-021
  |
  v
SegmentType == "108"? --no--> SEG108-R-003
SegmentLength matches ^[0-9]{3}$ and <= 142? --no--> SEG108-R-004 / SEG108-R-005
LoyaltyProgramId required, numeric <=6? --no--> SEG108-R-008
LoyaltyAccountNumber/PointsToRedeem/CouponId/CouponAmount/StreetAddress/
  PhoneNumberLoyalty/LoyaltyTrack2Data/LoyaltyInformationVersion/UnitOfWork
  within documented type/length when present? --no--> SEG108-R-009..020
UpdateCode in {A,C,E,I,P,S,T,U} when present? --no--> SEG108-R-013
PaymentTenderType required, in documented 15-value set? --no--> SEG108-R-017
  |
  v
ValidationResult (errors list; empty means PASS)
```
