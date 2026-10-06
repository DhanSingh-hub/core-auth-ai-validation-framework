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
| `SEG145-R-006` | Enhanced Fleet Data (Element 239) is required, max 999 characters, containing one or more sub-segments in <tag><len><data> format, cataloged as: Table 001 (Request Flags, incl. Commercial/Retail Flag), Table 002 (Non-Fuel Product Data, product-category-coded, '|'-delimited repeating), Table 004 (Prompt Data, authorizer-specific prompt tokens, '|'-delimited repeating), Table 006 (Money Code Payee Name), Table 007 (Money Code Check Number, required for all Money Code transactions), Table 008 (Cash Advance Limit) | 12.31 | 239 | REVIEW_REQUIRED |
| `SEG145-R-007` | Table 002 (Non-Fuel Product Data) product categories are authorizer-specific; a documented list applies to WEX OTR transactions (e.g., ADD, ANFR, BRAK, ... WWFL); other authorizers' category lists are not enumerated in this section | 12.31 | 239 | SPEC_DERIVED |
| `SEG145-R-008` | Table 004 (Prompt Data) prompt tokens are authorizer-specific and independently cataloged for Voyager EMV (DF-tag-based), Visa Fleet 2.0, Comdata, WEX OTR, and Conexxus (numeric prompt codes, used by MasterCard Enhanced Fleet EMV); full enumeration of all prompt-token tables is out of scope for this rule catalog pass | 12.31 | 239 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | See [Element 85 reference](#element-85-segment-type) |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits — see [Element 84 reference](#element-84-segment-length) | See [Element 84 reference](#element-84-segment-length) |
| 239 | Enhanced Fleet Data | AN | 999 bytes | Variable length of up to 999 alphanumeric characters | See [Element 239 reference](#element-239-enhanced-fleet-data) |

## Chapter 13 Reference: Full Element Definitions

Full, untruncated Chapter 13.2 text for the elements listed above, transcribed from the ATL105 specification extract (`docs/specs/extracted_text.txt`). The table above links here instead of truncating long value lists.

### Element 85: Segment Type

- **Character type:** N · **Maximum length:** 3 bytes
- **Representation:** Fixed length of three digits
- **Purpose:** Identifies the type of segment being formatted in a Financial Transaction request, a Totals request, a Loyalty Card transaction request, Electronic Mail request, or a TransArmor PKI Encryption and Tokenization Load Request. For TransArmor PKI Encryption and Tokenization, this value indicates that the segment contains Key and Key ID information.
- **Processing rules:** Required for the TransArmor PKI Encryption and Tokenization Load Request.

**Valid Codes/Values**

| Code | Description |
|---|---|
| `100` | Data Segment No. 100, Standard Message Data Segment |
| `101` | Data Segment No. 101, Fleet Data Segment |
| `102` | Data Segment No. 102, Product Code Data Segment |
| `103` | Data Segment No. 103, EBT Data Segment |
| `104` | Data Segment No. 104, Purchase Card Data Segment |
| `105` | Data Segment No. 105, Totals Data Segment |
| `106` | Data Segment No. 106 is reserved for proprietary use. |
| `107` | Data Segment No. 107 is reserved for proprietary use. |
| `108` | Data Segment No. 108, Loyalty Card Data Segment |
| `109` | Data Segment No. 109, Electronic Mail Data Segment |
| `111` | Data Segment No. 111, Variable Information Data Segment |
| `112` | Data Segment No. 112, Additional Information Data Segment |
| `116` | Data Segment No. 116, TransArmor Load Data Segment |
| `118` | Data Segment No 118, Proprietary Data Load Segment |
| `119` | Data Segment No. 119, Totals with Proprietary Data Load Load Data Segment |
| `120` | Data Segment No. 120, Print Data 2 Segment |
| `123` | Data Segment No. 123, NFC Payment Tokenization Data Segment |
| `130` | Data Segment No. 130, EMV Request Data Segment |
| `131` | Data Segment No. 131, EMV Response Data Segment |
| `132` | Data Segment No. 132, CA Public Key File Segment |
| `134` | Data Segment No. 134, Transaction Attributes Data Segment |
| `157` | Data Segment No. 157, Adjusted Product Code Data Segment |
| `DL1` | Data Segment No. DL1, Merchant Data Segment. |
| `DL2` | Data Segment No. DL2, Dial String Data Segment |
| `DL3` | Data Segment No. DL3, Date and Time Data Segment |
| `DL4` | Data Segment No. DL4, Software Dial Load Data Segment |
| `DL5` | Data Segment No. DL5, Software IP Load Data Segment |
| `DL6` | Data Segment No.DL6, Store and Forward Data Segment |

### Element 84: Segment Length

- **Character type:** N · **Maximum length:** 4 bytes
- **Representation:** Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (No. 103) • SKU Data Segment (No. 114) • Print Data Segment (No. 115) • Proprietary Data Load Segment (No. 118) • Print Data 2 Segment (No. 120) • EMV Request Data Segment (No.130) • EMV Response Data Segment (No. 131)
- **Purpose:** Identifies the length of the segment in which it appears.
- **Processing rules:** This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements. (Please refer to chapter 12, “Data Segment Formats,” for additional information about specific data segments—including their composition, data element lengths, Field Separators, and Product Data Field Delimiters.) Note: Segment Length can only have a length of 4 digits when transmitting the following Data Segments. It cannot have a length of 4 digits when sending any other segment. • EBT Data Segment (No. 103) • SKU Data Segment (No. 114) • Print Data Segment (No. 115) • Proprietary Data Load Segment (No. 118) • Print Data 2 Segment (No. 120) • EMV Request Data Segment (No.130) • EMV Response Data Segment (No. 131)

**Valid Codes/Values**

_Source column headings: Data Length Data Segment_

| Code | Description |
|---|---|
| `001–218` | Standard Message Data Segment (No. 100) |
| `001–61` | Fleet Data Segment (No. 101) |
| `001–381` | Product Code Data Segment (No. 102) |
| `001–3334` | EBT Data Segment (No. 103) |
| `001–86` | Purchase Card Data Segment (No. 104) |
| `001–409` | Totals Data Segment (No. 105) |
| `001–142` | Loyalty Card Data Segment (No. 108) |
| `001–232` | Electronic Mail Data Segment (No. 109) |
| `001–168` | Check Data Segment (No. 110) |
| `001–999` | Variable Information Data Segment (No. 111) |
| `001–999` | Additional Information Data Segment (No. 112) |
| `001–156` | ECA/ TeleCheck® Data Segment (No. 113) |
| `0001–1010` | SKU Data Segment (No. 114) |
| `001-1009` | Print Data Segment (No. 115) |
| `01–50` | TransArmor Load Data Segment (No. 116) |
| `0001-3800` | Proprietary Data Load Segment (No. 118) |
| `001-493` | Totals with Proprietary Data Load Data Segment (No. 119) |
| `001-1009` | Print Data 2 Segment (No. 120) |
| `001-186` | NFC Payment Tokenization Data Segment (No. 123) |
| `001-3043` | EMV Request Data Segment (No.130) |
| `001-3834` | EMV Response Data Segment (No. 131) |
| `01-77` | CA Public Key File Data Segment (No. 132) |
| `01-19` | Transaction Attributes Data Segment (No. 134) |
| `001–381` | Adjusted Product Code Data Segment (No. 157) |

### Element 239: Enhanced Fleet Data

- **Character type:** AN · **Maximum length:** 999 bytes
- **Representation:** Variable length of up to 999 alphanumeric characters
- **Purpose:** Contains fleet data that applies to enhanced fleet offerings such as Wex OTR, Visa Fleet 2.0, MasterCard Enhanced Fleet EMV, and Voyager EMV
- **Processing rules:** Used in the Enhanced Fleet Data Segment (No 145). When Element No. 239 (Enhanced Fleet Data) has a value of ‘004’ (Prompt Data Request), the list of enhanced prompts and the associated information is included here.

**Valid Codes/Values**

Refer to Data Segment 145— Enhanced Fleet Request Format Note: Visa Fleet 2.0 is a new Fleet EMV Standard that harnesses the additional capabilities of EMV at the fuel pump in a solution that is straightforward for fuel merchants to implement. In addition to more driver prompt options, this functionality enables commercial clients to restrict card use at fuel Merchants to certain fuel and non-fuel product categories. For example, cards that can be used to purchase diesel fuel or used for EV charging. Availability Notice: This functionality for MasterCard Enhanced transactions is expected to be enabled in First Data production no earlier than June 2026. Until then, this field is intended for development and certification preparation only and should not be used for live production transactions.

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
