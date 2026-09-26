# Segment 130 End-to-End Flow

## 1. Message-Family Decision

```text
Transaction initiated at POS with an EMV chip card
  |
  v
Is this transaction being submitted as an EMV Financial Transaction Request?
  |
  no --> standard Financial Transaction Request (Segment 130 not present)
  |
  yes
  v
Assemble EMV Financial Transaction Request:
  Data Section 2: Segment 100 (Standard Message Data Segment)
  Data Section 3: Segment 130 (REQUIRED) + optionally 101/102/104/111
                  (SEG130-R-001)
```

## 2. Segment 130 Field Assembly

```text
SegmentType = 130 (SEG130-R-002)
SegmentLength = 4-digit encoded length (SEG130-R-003, max 3043 provisional SEG130-SME-001)
CAPublicKeyFileChecksum = device's currently-known checksum (optional; echoed back by Segment 131)
EMVCardSequenceNumber = card's sequence number (conditional)
EMVChipDataLength = 3-digit length, 000-999 (required)
EMVChipData = TLV-encoded chip data, length-prefixed, BCD-packed/hex (required)
  |
  v
Does this transaction have EMV Additional Information to transmit
(e.g., Appendix T Table 001 EMVYES/EMVNOT data)?
  |
  no  --> omit the EMV Additional Information Section entirely
  |
  yes --> repeat for each information item, up to 2,000 bytes total:
            EMVAdditionalInformationIndicator (3 digits, identifies table/type)
            EMVAdditionalInformationLength (3 digits)
            EMVAdditionalInformation (variable, element 118 — reused from
              Segment 112's different "Additional Information" field, SEG130-R-012)
```

## 3. Request Serialization Flow

```text
Segment 130 fields in order:
  SegmentType -> SegmentLength -> CAPublicKeyFileChecksum -> EMVCardSequenceNumber ->
  EMVChipDataLength -> EMVChipData -> [EMV Additional Information Section]*
  |
  v
Field Separator after fields 1,2,3,4,5,6 (even when empty) and after field 6
before the EMV Additional Information Section.
NO separators within or between repetitions of the EMV Additional Information
Section; exactly one Field Separator follows the FINAL repetition
(SEG130-R-013 — structurally identical to Segment 111's repeating-section rule).
```

## 4. Validator Decision Flow (`Segment130PayloadValidator`, planned)

```text
payload
  |
  v
"EMV Financial Transaction Request" object present? --no--> error
  |
  v
"EMV Request Data Segment" (130) present? --no--> SEG130-R-001 (required for EMV transactions)
  |
  v
SegmentType == "130"? --no--> SEG130-R-002
SegmentLength matches ^[0-9]{4}$ and within 0001-3043? --no--> SEG130-R-003 / SEG130-R-004 [PROVISIONAL P-01]
EMVChipDataLength required, numeric 000-999? --no--> SEG130-R-007
EMVChipData required, length matches EMVChipDataLength, valid TLV structure? --no--> SEG130-R-008
  |
  v (if EMV Additional Information Section present)
Each repetition: Indicator + Length + Information present, no separators inside/between
  repetitions, total section <= 2000 bytes? --no--> SEG130-R-011 / SEG130-R-013
  |
  v
Cross-field checks (amount/currency/transaction-type vs Segment 100)? --REVIEW_REQUIRED
  pending SEG130-SME-003 (SEG130-R-009)
Cryptogram (9F26) authenticity? --NOT VALIDATED (out of scope, SEG130-R-010)
  |
  v
ValidationResult (errors list; empty means PASS)
```
