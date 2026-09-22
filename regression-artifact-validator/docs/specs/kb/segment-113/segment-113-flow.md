# Segment 113 End-to-End Flow

## 1. Message-Family Decision

```text
Transaction initiated at POS
  |
  v
Is this an ECA/TeleCheck check-service transaction?
  |
  no --> Financial Transaction Request (Segment 113 never appears here)
  |
  yes
  v
Assemble ECA/TeleCheck Service Transaction Request:
  Data Section 2: Segment 100 (Standard Message Data Segment)
  Data Section 3: Segment 110 (required, Check Data) + Segment 111 (optional)
                  + Segment 113 (conditional, ECA/TeleCheck risk-control data)
```

## 2. Segment 113 Presence Decision

```text
ECA/TeleCheck Service Transaction Request being assembled
  |
  v
Does the merchant need to send ECA/TeleCheck-specific risk-control data
(Clerk ID, Product Code, Phone Number, Trace ID, Merchant Trace ID,
Denial Record Number, Extended MICR Data)?
  |
  yes --> include Segment 113
  |
  no --> Segment 113 omitted (Data Section 3 has just Segment 110 [+111])
```

## 3. Transaction-Type Decision

```text
Clerk selects an ECA/TeleCheck function at the POS
  |
  +-- Standard check purchase -------> Segment 113 with Clerk ID + Product Code
  +-- Void of a prior transaction ---> Segment 113 with Trace ID populated (spec-required, catalog only)
  +-- Transaction declined ----------> Host returns Denial Record Number (136) for receipt printing
```

## 4. Request Serialization Flow

```text
Segment 113 fields in order:
  SegmentType -> SegmentLength -> EcaClerkId -> EcaProductCode -> EcaPhoneNumber ->
  EcaTraceId -> MerchantTraceId -> DenialRecordNumber -> ExtendedMicrData
  |
  v
Every field boundary emits a Field Separator, even when the field between two
separators is empty. Fields 3-9 map directly to elements 131-137, ascending order.
```

## 5. Validator Decision Flow (`Segment113PayloadValidator`)

```text
payload
  |
  v
"ECA/TeleCheck Service Transaction Request" object present? --no--> error
  |
  v
"Standard Segment" (100) sibling present? --no--> SEG113-R-001
  |
  v
"ECA/TeleCheck Data Segment" object present? --no--> conditional, skip further checks
  |
  v
Exactly one "ECA/TeleCheck Data Segment" occurrence in the message? --no--> SEG113-R-015
  |
  v
SegmentType == "113"? --no--> SEG113-R-003
SegmentLength matches ^[0-9]{3}$ and <= 156? --no--> SEG113-R-004 / SEG113-R-005
EcaClerkId required, alphanumeric <=6? --no--> SEG113-R-008
EcaProductCode/EcaPhoneNumber/EcaTraceId/MerchantTraceId/DenialRecordNumber/
  ExtendedMicrData within documented type/length when present? --no--> SEG113-R-009..014
  |
  v
ValidationResult (errors list; empty means PASS)
```
