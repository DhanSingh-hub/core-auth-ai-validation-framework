```text
Segment 113 payload ready to serialize
  |
  v
emit SegmentType FS SegmentLength FS EcaClerkId FS EcaProductCode FS
     EcaPhoneNumber FS EcaTraceId FS MerchantTraceId FS DenialRecordNumber FS
     ExtendedMicrData FS
  (each FS present even when the field between two separators is empty;
   fields 3-9 map directly to elements 131-137 in ascending order, no reversal quirk)

Segment Length is always 3 digits (no 4-digit variant documented for this segment).
Maximum total segment length is 156 alphanumeric characters.
```
