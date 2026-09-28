# Segment 116 Companion and Envelope Compatibility: SME/TBA Learning Note

## Core Rule

Segment 116 belongs to the TransArmor PKI Encryption and Tokenization Load Request family (Section 11.7.5). It is not a Data Section 3 companion of Segment 100, and it must not be confused with Segment 119 (Totals with Proprietary Data Load Data Segment), despite a source-text label error in Section 11.4.1.2.

```text
TransArmor Key/Key ID Load intent
  -> Data Section 1: Elements 55 and 63
  -> Data Section 2: Segment 116
  -> (no Segment 100, no Data Section 3 - structural analogy, not directly shown)
  -> TransArmor Load Response: Key ID, Key Data Length, Key Data
```

## Compatibility Checks

| Condition | Expected result |
|---|---|
| TransArmor request with Elements 55/63 and Segment 116 in Data Section 2 | Continue validation |
| Segment 116 absent from a declared TransArmor request | Reject |
| Segment 100 or a Data Section 3 segment present in a TransArmor request | `REVIEW_REQUIRED` - contradicts the structural analogy in `SEG116-SME-002` |
| A "Totals with Proprietary Data Load Request" fixture labeled as Segment 116 | Reject/relabel - the correct segment is 119, per the Element 85 valid-codes table and Section 12.17 |
| Segment 111 "Additional TransArmor Data" (Table ID 052) present without a Segment 116 request | `REVIEW_REQUIRED` under `SEG116-SME-004` |

## SME Decision

Confirm the complete envelope for the TransArmor PKI Encryption and Tokenization Load Request family, including whether any product-specific extension permits additional segments beyond the ones implied by the current cross-references. The external TransArmor document is the authoritative source for this; the current source extract only supports the structural analogy captured above.
