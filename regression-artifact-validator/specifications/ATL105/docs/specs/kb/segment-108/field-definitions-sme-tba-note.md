# Segment 108 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 108 — Loyalty Card Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 10.9.1.2, 11.2.1, 11.2.2, 12, 12.7, 13.2  
**Oracle:** [segment-108-rule-catalog.json](coverage/segment-108-rule-catalog.json) (24 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 108 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (15)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG108-R-003` | Segment Type is 108 | 12.7 | 85 | SPEC_DERIVED |
| `SEG108-R-004` | Segment Length is 3 digits representing the segment content length | 12.7 | 84 | SPEC_DERIVED |
| `SEG108-R-008` | Loyalty Program ID, required, is numeric with maximum length 6 | 12.7 | 138 | SPEC_DERIVED |
| `SEG108-R-009` | Loyalty Account Number, when populated, is numeric with maximum length 24 | 12.7 | 139 | SPEC_DERIVED |
| `SEG108-R-010` | Points to Redeem, when populated, is numeric with maximum length 6 | 12.7 | 140 | SPEC_DERIVED |
| `SEG108-R-011` | Coupon ID, when populated, is numeric with maximum length 19 | 12.7 | 141 | SPEC_DERIVED |
| `SEG108-R-012` | Coupon Amount, when populated, is numeric with maximum length 8 | 12.7 | 142 | SPEC_DERIVED |
| `SEG108-R-013` | Update Code, when populated, is exactly one alphanumeric character from the documented set A, C, E, I, P, S, T, U | 13.2 | 143 | REVIEW_REQUIRED |
| `SEG108-R-014` | Street Address, when populated, is numeric with maximum length 5 | 12.7 | 144 | SPEC_DERIVED |
| `SEG108-R-015` | Phone Number, Loyalty, when populated, is numeric with maximum length 10 | 12.7 | 145 | SPEC_DERIVED |
| `SEG108-R-016` | Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card | 10.9.1.2,13.2 | 146 | SPEC_DERIVED |
| `SEG108-R-017` | Payment Tender Type is required, exactly 2 alphanumeric characters from the documented set AX, CK, CS, DB, DN, DS, EB, EC, FL, GC, JC, MC, PC, PR, VS | 13.2 | 148 | SPEC_DERIVED |
| `SEG108-R-018` | Loyalty Track 2 Data, when populated, is alphanumeric with maximum length 38 | 12.7 | 147 | SPEC_DERIVED |
| `SEG108-R-019` | Loyalty Information Version, when populated, is numeric length 1 with valid values 1 or 2; defaults to 1 when not sent | 13.2 | 150 | SPEC_DERIVED |
| `SEG108-R-020` | Unit of Work, when populated, is numeric with fixed length 19; required on loyalty reversals to match the original purchase | 13.2 | 151 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 138 | Loyalty Program ID | N | 6 bytes | Variable length of up to 6 digits | ERN |
| 139 | Loyalty Account Number | N | 24 bytes | Variable length of up to 24 digits | — |
| 140 | Points to Redeem | N | 6 bytes | Variable length of up to 6 digits | — |
| 141 | Coupon ID | N | 19 bytes | Variable length of up to 19 digits |  |
| 142 | Coupon Amount | N | 8 bytes | Variable length of up to 8 digits | — |
| 143 | Update Code | AN | 1 byte | Fixed length of 1 alphanumeric character | Value Description A Add account C Coupon redemption E Expiration date update I Account Inquiry P Points redemption S Sale update T Totals Report, Loy… |
| 144 | Street Address | N | 5 bytes | Variable length of up to 5 digits |  |
| 145 | Phone Number, Loyalty | N | 10 bytes | Variable length of up to 10 digits | — |
| 146 | Expiration Date | N | 4 bytes | Variable length of 4 digits (MMYY) | — |
| 148 | Payment Tender Type | AN | 2 bytes | Fixed length of 2 alphanumeric characters | Value Description AX American Express CK Check CS Loyalty Only—either a cash payment or an administrative transaction (card activation or account inq… |
| 147 | Loyalty Track 2 Data | AN | 38 bytes | Variable length of up to 38 alphanumeric characters |  |
| 150 | Loyalty Information Version | N | 1 byte | Fixed length of 1 byte | Value Description 1 Loyalty information, Table ID 008 2 Loyalty Information, Table ID 010 |
| 151 | Unit of Work | N | 19 bytes | Fixed length of 19 bytes | — |

## Catalog Notes

- `SEG108-R-016` — SME-resolved 2026-09-22 (SEG108-SME-007): Section 12.7's field table note ('reserved for future use') is superseded by Section 13.2's full element definition and Section 10.9.1.2's documented manual-entry default-value behavior (1249 MMYY); treated as a real, format-validated field, not inert.

## SME Reasoning

Ask:

1. Which element number does each field carry, and does the payload preserve it?
2. Is the value fixed-length or variable-length, and is the length measured in bytes or characters?
3. Is the field Required, Optional, or Conditional, and what triggers the condition?
4. Does the valid-value set come from Chapter 13 or from an appendix table?
5. Is any value cardholder- or key-sensitive and therefore synthetic-only in test data?

## TBA Dependency Chain

```text
element definition (Chapter 13)
  -> field position in Segment 108
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is 108
  -> source: ATL105 2026-3 §12.7 (SEG108-R-003)
  -> a violating payload shall fail validation citing SEG108-R-003
```

## Open Provisional Items

- **P-02** (SEG108-R-013): Section 10.9.3 describes 9-10 distinct loyalty advice functions (including 'Reversal of coupon redeem' and 'Reversal of points redeemed') but Element 143 only documents 8 Update Code values (A,C,E,I,P,S,T,U). Which code(s) represent the two reversal functions?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

`Segment108PayloadValidator` exists in `src/main`; confirm each rule above has an assertion before marking it covered.

## Review Checklist

- Is every rule traced to its source anchor (`SEG108-R-003`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
