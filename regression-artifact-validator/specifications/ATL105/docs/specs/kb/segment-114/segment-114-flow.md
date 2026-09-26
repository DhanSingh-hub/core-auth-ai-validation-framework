# Segment 114 End-to-End Flow

## 1. Message-Family Decision

```text
Transaction initiated at POS
  |
  v
Is this a Loyalty Card Transaction (see Segment 108's message-family decision)?
  |
  no --> Financial Transaction Request / ECA/TeleCheck / CA Public Key File Load
         (Segment 114 never appears in any of these — SEG114-R-001; the AI-asserted
          Financial Transaction Request relationship was confirmed REJECTED 2026-09-26,
          SEG114-SME-002)
  |
  yes
  v
Assemble Loyalty Card Transaction Request:
  Data Section 2: Segment 100 (Standard Message Data Segment)
  Data Section 3: Segment 108 (required)
                  + Segment 114 (optional, only if a bar code SKU was scanned)
```

## 2. SKU-Present Decision

```text
Loyalty Card Transaction Request being assembled (Segment 108 already populated)
  |
  v
Did the device scan a bar code SKU as part of this transaction?
  |
  no  --> omit Segment 114 entirely (it is optional; there is no "empty" variant)
  |
  yes --> populate Segment 114:
            SegmentType = 114 (confirmed enforceable 2026-09-26, SEG114-R-003)
            SegmentLength = 4-digit encoded length (SEG114-R-004)
            SkuData = scanned bar code value, up to 1000 alphanumeric characters (SEG114-R-008)
          (repeat this block once per additional scanned SKU — confirmed
           repeatable 2026-09-26, SEG114-R-010)
```

## 3. Request Serialization Flow

```text
Segment 114 fields in order:
  SegmentType -> SegmentLength -> SkuData
  |
  v
Every field boundary emits a Field Separator, even when the field between two
separators is empty; a Field Separator also follows the last field (field 3).
Segment Length is always 4 digits (SEG114-R-004) — unlike Segment 108's 3 digits.
```

## 4. Validator Decision Flow (`Segment114PayloadValidator`, planned)

```text
payload
  |
  v
"Loyalty Card Transaction Request" object present? --no--> error
  |
  v
"SKU Data Segment" object present? --no--> pass (Segment 114 is optional; absence is not a failure)
  |
  v (if present)
"Loyalty Card Data Segment" (108) sibling present? --no--> SEG114-R-011
  |
  v
At most one "SKU Data Segment" occurrence in the message? --n/a--> repeatable,
  confirmed 2026-09-26 (SEG114-R-010); one occurrence per scanned SKU is expected
  |
  v
SegmentType == "114"? --no--> SEG114-R-003 (enforced hard rule, confirmed 2026-09-26)
SegmentLength matches ^[0-9]{4}$ and within 0001-1010? --no--> SEG114-R-004 / SEG114-R-005 (1010 upper bound confirmed 2026-09-26)
SkuData required, alphanumeric, <= 1000 characters? --no--> SEG114-R-008
  |
  v
Message also carries a segment other than 100/108/114? --yes--> REVIEW_REQUIRED (SEG114-R-001);
  if the other segment is a Financial Transaction Request context, REJECT outright (SEG114-R-012)
  |
  v
ValidationResult (errors list; empty means PASS)
```
