# MICR Data: SME/TBA Learning Note

## Core Idea

Element 122 (MICR Data) identifies the data encoded along the bottom of a check. It is required in Segment 110, alphanumeric, and limited to 50 bytes. The specification states two documented reader output formats:

1. **Full MICR Line - TAC Format**, for example `T999999999A999999999999999999C999999`.
2. **Full MICR Line - RAW TOAD Format**, a symbol-substitution format where `T` marks the routing-number field, `O` marks the "On-Us"/account or check-serial field, `A` marks the encoded dollar-amount field, and `D` marks a dash typically in the account-number field.

```text
MICR reader output
  -> TAC format (BUY2) or RAW TOAD format (ALB1)
  -> MICR Data (Element 122), first 50 bytes left-to-right
  -> Extended MICR Data (Element 137) for any overflow, hosting segment unconfirmed
```

## Cross-References Requiring Confirmation

1. Section 12.9 notes that MICR data "is also included in Account Number (Element No. 2) of the Standard Message Data Segment (Segment No. 100)." The source does not state whether the two values must match byte-for-byte.
2. Chapter 13 states Element 137 (Extended MICR Data, 65 bytes) "must be populated in addition to MICR Data (Element No. 122) in Check Data Segment (Segment No. 110) when the raw MICR data exceeds the length," but Element 137 is not one of the 12 fields listed in Section 12.9's Segment 110 table.
3. **Independently confirmed (SEG110-R-020):** Appendix I-17 documents "Manual Check MICR Type" as Table ID `024` inside Segment 111's Variable Information section (Variable Information Indicator = `024`), with a fixed Table Length of 2 and four named format codes: `T$` (`T<aba>A<acct>C<checknum>`), `18` (`<aba><acct>` concatenated), `09` (raw MICR, not touched), and `19` (numerals from the bottom of the check). This is a Segment 111 sub-table, not a Segment 110 field. The supplied AI requirement catalog (`BR-559-4` through `BR-559-7`) captured these same four codes but scoped them loosely as generic "Field" requirements without tying them to Segment 111 or Variable Information Indicator `024`; the independent Test Solution catalog corrects that scoping.

## SME Questions

1. Must MICR Data (Segment 110) and Account Number (Segment 100) match exactly when both are present in the same transaction?
2. Which data segment carries Extended MICR Data (Element 137), and is it mandatory whenever MICR Data reaches its 50-byte limit?
3. Is a machine-checkable grammar available for TAC and RAW TOAD formats beyond the single illustrative example?

## TBA Rule Pattern

```text
BR: MICR Data shall be present and shall not exceed 50 bytes on every check transaction.
TS: Device submits a check transaction with MICR data of exactly 50 bytes and with 51+ bytes (overflow).
TC: Submit valid, boundary, and overflow-length MICR Data; verify overflow pairs with the approved companion field.
TD: Synthetic MICR strings in both TAC and RAW TOAD shapes, sized at 49, 50, and 51 bytes.
```

## Current Boundary

The validator enforces presence, alphanumeric type, and the 50-byte maximum. Overflow handling, cross-segment matching with Segment 100, and format-grammar validation remain `REVIEW_REQUIRED` under `SEG110-SME-002`, `SEG110-SME-006`, and `SEG110-SME-007`.
