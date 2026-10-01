# Segment 131 End-to-End Flow

## 1. Response Assembly Decision

```text
Host finishes processing an EMV Financial Transaction Request (Segment 130 present)
  |
  v
Does this transaction require EMV data to be echoed back?
  |
  no  --> Segment 131 omitted
  |
  yes --> emit Segment 131 (following Segments 112 and 115/120 if also
          present) [PROVISIONAL SEG131-SME-001 on exact Data Section /
          field position]
```

## 2. Segment 131 Field Assembly (No Delimiters)

```text
SegmentType = 131 (fixed, Host-sourced)
SegmentLength = 4-digit encoded length (max 3834, provisional SEG131-SME-002)
CAPublicKeyFileChecksum = ECHOED from Segment 130's request value (SEG131-R-007)
EMVChipDataLength = echoed chip-data length
EMVChipData = echoed EMV chip data
  |
  v
Does this transaction have EMV Additional Information to echo back?
  |
  no  --> message ends here
  |
  yes --> repeat for each item, up to 2,800 bytes total (NOT 2,000 —
          a different cap than Segment 130's request-side section):
            EMVAdditionalInformationIndicator
            EMVAdditionalInformationLength
            EMVAdditionalInformation (element 118, reused a 3rd time)
```

## 3. Response Serialization Flow

```text
Segment 131 fields in positional order, with NO Field Separators anywhere:
  SegmentType SegmentLength CAPublicKeyFileChecksum EMVChipDataLength EMVChipData
  [EMVAdditionalInformationIndicator EMVAdditionalInformationLength EMVAdditionalInformation]*
  |
  v
When a field is not populated, the NEXT field immediately follows — there is
no placeholder delimiter to detect an empty field (SEG131-R-006). This is the
first fully non-delimited segment documented in this KB.
```

## 4. Validator Decision Flow (`Segment131PayloadValidator`, planned)

```text
payload
  |
  v
"EMV Financial Transaction Response" object present? --no--> error
  |
  v
"EMV Response Data Segment" (131) present? --no--> pass (conditional, SEG131-R-002)
  |
  v (if present)
SegmentType == "131"? --no--> SEG131-R-003
SegmentLength matches ^[0-9]{4}$ and within 0001-3834? --no--> SEG131-R-004 / SEG131-R-005 [PROVISIONAL P-02]
CAPublicKeyFileChecksum required, equals the request's Segment 130 value? --no--> SEG131-R-007
EMVChipDataLength / EMVChipData required, consistent with each other? --no--> SEG131-R-008
  |
  v (if EMV Additional Information Section present)
Total section size <= 2800 bytes? --no--> SEG131-R-009
  |
  v
ValidationResult (errors list; empty means PASS)
```
