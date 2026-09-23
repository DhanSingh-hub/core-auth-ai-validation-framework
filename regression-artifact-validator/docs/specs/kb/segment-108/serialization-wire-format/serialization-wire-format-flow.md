# Segment 108 Serialization and Wire-Format Flow

```text
Segment 108 payload ready to serialize
  |
  v
emit SegmentType FS SegmentLength FS LoyaltyProgramId FS LoyaltyAccountNumber FS
     PointsToRedeem FS CouponId FS CouponAmount FS UpdateCode FS StreetAddress FS
     PhoneNumberLoyalty FS ExpirationDate FS PaymentTenderType FS LoyaltyTrack2Data FS
     LoyaltyInformationVersion FS UnitOfWork FS
  (each FS present even when the field between two separators is empty;
   note the positional order is PaymentTenderType (148) BEFORE LoyaltyTrack2Data (147),
   the reverse of their element numbers)

Segment Length is always 3 digits (no 4-digit variant documented for this segment).
```
