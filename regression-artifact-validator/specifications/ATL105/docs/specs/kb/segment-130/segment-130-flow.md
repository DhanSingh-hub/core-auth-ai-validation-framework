# Segment 130 End-to-End Flow

## 1. Message-Family Decision

```text
Transaction initiated at POS with an EMV chip card
  |
  v
Was a viable AID obtained from the chip? (Section 10.14.4)
  |
  no --> FALLBACK: Entry Mode 80, magnetic-stripe path, NO Segment 130
  |      (SEG130-R-019)
  yes
  v
Is this an EMV Reversal or Time-out Reversal?
  |
  yes --> EMV data waived (Section 10.14.2.4); Segment 130 may be absent
  |       (SEG130-R-017)
  no
  v
Assemble EMV Financial Transaction Request:
  Data Section 1: Element 55 + Element 63 (cannot exceed 06)
  Data Section 2: Segment 100 (Standard Message Data Segment)
  Data Section 3: Field Nos. 4-8 (FIVE slots only)
                  Segment 130 (REQUIRED) + up to FOUR of
                  {101, 102, 103, 104, 111, 123, 135, 143,
                   145, 146, 151, 152, 153}
                  (SEG130-R-001, SEG130-R-018)
```

> **Note:** Segment 103 (EBT) **is** a permitted companion. The abbreviated prose
> list in Section 11.8.1 omits it, but the normative Data Section 3 table and the
> Chapter 12 matrix both include it. A prior revision of this flow was incorrect.

## 2. Segment 130 Field Assembly

```text
SegmentType = 130 (SEG130-R-002)
SegmentLength = 4-digit encoded length, 0001-3043 (SEG130-R-003, SEG130-R-004)
  NOTE: Section 11.8.1's table prints 9999 - a genuine specification defect.
        3043 is adopted (Section 12.20 + Element 84 valid-codes table).
CAPublicKeyFileChecksum = device's currently-known checksum (OPTIONAL here;
  REQUIRED and echoed back in Segment 131 - SEG130-R-015)
EMVCardSequenceNumber = card's sequence number (conditional)
EMVChipDataLength = 3-digit length, 000-999 (required)
EMVChipData = TLV-encoded chip data; byte count MUST equal EMVChipDataLength (required)
  |
  v
Does this transaction have EMV Additional Information to transmit?
  |
  no  --> omit the EMV Additional Information Section entirely (valid)
  |
  yes --> repeat for each information item, up to 2,000 bytes total:
            EMVAdditionalInformationIndicator (3 digits - Appendix T catalog:
              001 = EMV Table Data, values EMVYES / EMVNOT, length 6
              002 = CARC, length 1; request-side legality open SEG130-SME-007)
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
