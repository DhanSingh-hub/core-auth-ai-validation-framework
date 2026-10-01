# Segment 115 End-to-End Flow

## 1. Response Assembly Decision

```text
Host finishes processing a Financial Transaction Request (or EMV variant)
  |
  v
Does Element 115 (Additional Information Data Segment Flag) indicate that
additional segment(s) follow? [PROVISIONAL SEG115-SME-002 / P-05 on exact
flag semantics beyond the documented 0/1 values]
  |
  no --> Financial Transaction Response ends after Data Section 1 (no Segment 112/115)
  |
  yes
  v
Does this transaction require Additional Information (balances, AVS/CVV,
tokens, fraud scores, etc.)? --yes--> emit Segment 112 at Field 16/17/18
  |
  v
Does the request's Loyalty Information Version (Element 150) equal 2?
  |
  no  --> Segment 115 is omitted
  |
  yes --> emit Segment 115 at Field 17/18/19 (SEG115-R-002)
```

## 2. EMV Financial Transaction Response Variant

```text
EMV Financial Transaction Response being assembled
  |
  v
Additional Information Data Segment Flag conditions met (as above)?
  |
  yes --> emit Segment 112 (16/17/18), then Segment 115 (17/18/19),
          then Segment 120 Print Data 2 Segment (17/18/19) when the print
          payload exceeds one segment's capacity (SEG115-R-011)
  |
  v
Always followed by Segment 131 (EMV Response Data Segment) at 17/18/19/20
```

## 3. Response Serialization Flow

```text
Segment 115 fields in order:
  SegmentType -> SegmentLength -> PrintData
  |
  v
Field Separator after field 1, Field Separator after field 2 —
NO Field Separator after field 3 (SEG115-R-007; contrast Segment 108/114,
which both send a trailing separator)
Segment Length is always 4 digits (SEG115-R-004), valid range 0001-1009
(upper bound provisional, SEG115-R-005 / P-01)
```

## 4. Validator Decision Flow (`Segment115PayloadValidator`, planned)

```text
payload
  |
  v
"Financial Transaction Response" (or EMV variant) object present? --no--> error
  |
  v
"Print Data Segment" object present? --no--> pass (Segment 115 is conditional; absence is not a failure)
  |
  v (if present)
Response's Additional Information Data Segment Flag / Loyalty Information
Version consistent with SEG115-R-002? --no--> REVIEW_REQUIRED [PROVISIONAL P-05]
  |
  v
SegmentType == "115"? --no--> SEG115-R-003 (hard rule, no ambiguity)
SegmentLength matches ^[0-9]{4}$ and within 0001-1009? --no--> SEG115-R-004 / SEG115-R-005 [PROVISIONAL P-01]
PrintData required, non-empty, <= 999 characters? --no--> SEG115-R-008 [PROVISIONAL P-02 on 999 vs 900]
  |
  v
Response also carries a segment other than 100/112/115/120/131? --yes--> REVIEW_REQUIRED (SEG115-R-010) [PROVISIONAL P-03]
  |
  v
ValidationResult (errors list; empty means PASS)
```
