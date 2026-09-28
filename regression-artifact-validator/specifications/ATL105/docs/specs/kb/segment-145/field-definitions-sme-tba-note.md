# Segment 145 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 145 — Enhanced Fleet Request Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.31  
**Oracle:** [segment-145-rule-catalog.json](coverage/segment-145-rule-catalog.json) (8 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 145 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (4)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG145-R-005` | Segment Type is fixed value 145, Segment Length includes Segment Type's length; both Device-sourced | 12.31 | 85,84 | SPEC_DERIVED |
| `SEG145-R-006` | Enhanced Fleet Data (Element 239) is required, max 999 characters, containing one or more sub-segments in <tag><len><data> format, cataloged as: Table 001 (Request Flags, incl. Commercial/Retail Flag), Table 002 (Non-Fu… | 12.31 | 239 | REVIEW_REQUIRED |
| `SEG145-R-007` | Table 002 (Non-Fuel Product Data) product categories are authorizer-specific; a documented list applies to WEX OTR transactions (e.g., ADD, ANFR, BRAK, ... WWFL); other authorizers' category lists are not enumerated in… | 12.31 | 239 | SPEC_DERIVED |
| `SEG145-R-008` | Table 004 (Prompt Data) prompt tokens are authorizer-specific and independently cataloged for Voyager EMV (DF-tag-based), Visa Fleet 2.0, Comdata, WEX OTR, and Conexxus (numeric prompt codes, used by MasterCard Enhanced… | 12.31 | 239 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 239 | Enhanced Fleet Data | AN | 999 bytes | Variable length of up to 999 alphanumeric characters | Refer to Data Segment 145— Enhanced Fleet Request Format Note: Visa Fleet 2.0 is a new Fleet EMV Standard that harnesses the additional capabilities… |

## Catalog Notes

- `SEG145-R-006` — PROVISIONAL: Table IDs 003 and 005 are absent from this section's request-side catalog (present instead in Segment 146's response-side catalog as Fuel Product Limits and Customer Information respectively) — confirm this asymmetry is intentional (SEG145-SME-003).
- `SEG145-R-008` — PROVISIONAL: full prompt-token catalogs (5 authorizer-specific tables, dozens of tokens each) were not individually transcribed into machine-readable rules in this pass. Pending SME confirmation (SEG145-SME-004) on whether per-token validation is required for Item 1 sign-off, or whether format-only validation (token exists, length within max) suffices.

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
  -> field position in Segment 145
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 145, Segment Length includes Segment Type's length both Device-sourced
  -> source: ATL105 2026-3 §12.31 (SEG145-R-005)
  -> a violating payload shall fail validation citing SEG145-R-005
```

## Open Provisional Items

- **P-03** (SEG145-R-006): Confirm whether Table IDs 003/005 being request-absent but response-present (Segment 146) is intentional asymmetry.
- **P-04** (SEG145-R-008): Is full per-token validation for all 5 authorizer-specific prompt-token catalogs required for Item 1 sign-off, or is format-only (existence + max length) validation sufficient for this training pass?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment145PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG145-R-005`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
