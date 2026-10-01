# Segment 102 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 102 — Segment 102  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.3, 12.30, Appendix F  
**Oracle:** [segment-102-rule-catalog.json](coverage/segment-102-rule-catalog.json) (25 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 102 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (8)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG102-R-001` | Segment Type is 102 | 12.3 | 85 | SPEC_DERIVED |
| `SEG102-R-003` | Service Level uses an allowed value | 12.3 | 87 | SPEC_DERIVED |
| `SEG102-R-004` | Number of Products is 01-10 and zero-padded | 12.3 | 62 | SPEC_DERIVED |
| `SEG102-R-010` | Product Code is a valid value per Appendix F | Appendix F | 77 | REVIEW_REQUIRED |
| `SEG102-R-011` | Unit of Measure uses an allowed value | 12.3 | 106 | SPEC_DERIVED |
| `SEG102-R-012` | Quantity preserves the assumed-decimal-place leading digit | 12.3 | 81 | SPEC_DERIVED |
| `SEG102-R-013` | Unit Price preserves the assumed-decimal-place leading digit | 12.3 | 107 | SPEC_DERIVED |
| `SEG102-R-014` | Product Amount is present for every product entry | 12.3 | 76 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | See [Element 85 reference](#element-85-segment-type) |
| 87 | Service Level | A | 1 byte | Fixed length of one alpha character | See [Element 87 reference](#element-87-service-level) |
| 62 | Number of Products | N | 2 bytes | Fixed length of two digits | 01–10 |
| 77 | Product Code | N | 3 bytes | Fixed length of three digits | Please refer to Appendix F. Valid Payment Systems Product Codes, for lists of valid codes. |
| 106 | Unit of Measure | A | 1 byte | Fixed length of one alpha character | See [Element 106 reference](#element-106-unit-of-measure) |
| 81 | Quantity | N | 9 bytes | Variable length of up to nine digits with a maximum of three assumed decimal places | See [Element 81 reference](#element-81-quantity) |
| 107 | Unit Price | N | 9 bytes | Variable length of up to nine digits with a maximum of three assumed decimal places | 000000.000–999999.999 For an example of this data element, please see Appendix B. POS Purchase Example. |
| 76 | Product Amount | N | 12 bytes | Variable length of up to 12 digits with two assumed decimal places | 1–999999999999 |

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

### Element 87: Service Level

- **Character type:** A · **Maximum length:** 1 byte
- **Representation:** Fixed length of one alpha character
- **Purpose:** Identifies the sale type for a product in Data Segment No. 102, Product Code Data Segment or Data Segment No. 157, Adjusted Product Code Data Segment.

**Valid Codes/Values**

| Value | Description |
|---|---|
| `F` | Full serve |
| `S` | Self-serve |
| `N` | Mini-serve |
| `X` | Maxi-serve |
| `H` | High-Speed Dispense |
| `O` | Other/Fuel not present. |
| `0–9` | Reserved for private use. |
| `Other` | Reserved for future use. |

### Element 62: Number of Products

- **Character type:** N · **Maximum length:** 2 bytes
- **Representation:** Fixed length of two digits
- **Purpose:** Identifies the count of products reported for a transaction.
- **Processing rules:** Always precede single digits (1–9) with a zero (01–09).

**Valid Codes/Values**

01–10

### Element 77: Product Code

- **Character type:** N · **Maximum length:** 3 bytes
- **Representation:** Fixed length of three digits
- **Purpose:** Identifies the type of product in Data Segment No. 102, Product Code Data Segment or Data Segment No. 157, Adjusted Product Code Data Segment. In the case of a Proprietary Load Response (Prompt Code 904), the first instance identifies the type of product which is eligible for discount, and the second instance identifies the corresponding discount product code to send in Data Segment No. 102, Product Code Data Segment.
- **Processing rules:** This data element, along with the following data elements, is repeated for up to a maximum of 10 products in Data Segment No. 102 or Data Segment No. 157: • Unit of Measure (No. 106) • Quantity (No. 81) • Unit Price (No. 107) • Product Amount (No. 76) Note: A unique Product Code must be sent for each type of fuel purchase. When a device uses a Dynamic Card Table, only the product codes defined in the table are valid for transaction processing. In the case of a Proprietary Load Response (Host Discount Data, Prompt Code 904), the value in the second or third positions of the Product Code may contain a wild card character of *. If that occurs, any digit is allowed in that position. For example, if the value is 01*, product codes 010, 011, 012, 013, 014, 015, 016, 017, 018 and 019 are all available for discount.

**Valid Codes/Values**

Please refer to Appendix F. Valid Payment Systems Product Codes, for lists of valid codes.

### Element 106: Unit of Measure

- **Character type:** A · **Maximum length:** 1 byte
- **Representation:** Fixed length of one alpha character
- **Purpose:** Identifies the measurement type for a product in Data Segment No. 102, Product Code Data Segment.
- **Processing rules:** This data element, along with the following data elements, is repeated for up to 10 products in Data Segment No. 102, Product Code Data Segment or Data Segment No. 157, Adjusted Product Code Data Segment: • Product Code (No. 77) • Quantity (No. 81) • Unit Price (No. 107) • Product Amount (No. 76)

**Valid Codes/Values**

| Value | Description |
|---|---|
| `C` | Case/Carton |
| `G` | Gallon |
| `H` | Hours |
| `I` | Imperial Gallons |
| `K` | Kilogram |
| `L` | Liter |
| `M` | Charging Minutes (EV) |
| `P` | Pound |
| `Q` | Quart |
| `U` | Unit |
| `W` | Kilowatt per hour (EV) |
| `Z` | Ounce |
| `O` | Other/Unknown unit |
| `0–9` | Reserved for private use. |
| `Other` | Reserved for future use. |

### Element 81: Quantity

- **Character type:** N · **Maximum length:** 9 bytes
- **Representation:** Variable length of up to nine digits with a maximum of three assumed decimal places
- **Purpose:** Identifies the number of product units sold in Data Segment No. 102, Product Code Data Segment or Data Segment No. 157, Adjusted Product Code Data Segment.
- **Processing rules:** The leading number indicates the number of assumed decimal places. There is a maximum number of three assumed decimal places. This data element, along with the following data elements, is repeated for up to 10 products in Data Segment No. 102, Product Code Data Segment or Data Segment No. 157, Adjusted Product Code Data Segment: • Product Code (No. 77) • Unit of Measure (No. 106) • Unit Price (No. 107) • Product Amount (No. 76)

**Valid Codes/Values**

00000000. 01–399999999 Examples: 010 = 10, where 0 is the number of assumed decimal places and 10 represents the quantity of 10. 34170 = 4.170, where 3 is the number of assumed decimal places and 4170 represents the quantity of 4.170. For an example of this data element, please see Appendix B. POS Purchase Example.

### Element 107: Unit Price

- **Character type:** N · **Maximum length:** 9 bytes
- **Representation:** Variable length of up to nine digits with a maximum of three assumed decimal places
- **Purpose:** Identifies the price per Unit of Measure of a product in Data Segment No. 102, Product Code Data Segment or Data Segment No. 157, Adjusted Product Code Data Segment.
- **Processing rules:** The leading number indicates the number of assumed decimal places. There is a maximum number of three assumed decimal places. This data element, along with the following data elements, is repeated for up to 10 products in Data Segment No. 102, Product Code Data Segment or Data Segment No. 157, Adjusted Product Code Data Segment: • Product Code (No. 77) • Unit of Measure (No. 106) • Quantity (No. 81) • Product Amount (No. 76)

**Valid Codes/Values**

000000.000–999999.999 For an example of this data element, please see Appendix B. POS Purchase Example.

### Element 76: Product Amount

- **Character type:** N · **Maximum length:** 12 bytes
- **Representation:** Variable length of up to 12 digits with two assumed decimal places
- **Purpose:** Identifies the monetary value of product purchased in Data Segment No. 102, Product Code Data Segment or Data Segment No. 157, Adjusted Product Code Data Segment.
- **Processing rules:** The decimal point is implied by the optional Currency Code. The default value has two assumed decimal places. This data element, along with the following data elements, is repeated for up to a maximum of 10 products in Data Segment No. 102 or Data Segment No. 157: • Product Code (No. 77) • Unit of Measure (No. 106) • Quantity (No. 81) • Unit Price (No. 107)

**Valid Codes/Values**

1–999999999999

## Catalog Notes

_No catalog notes are recorded against these rules._

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
  -> field position in Segment 102
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is 102
  -> source: ATL105 2026-3 §12.3 (SEG102-R-001)
  -> a violating payload shall fail validation citing SEG102-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment102PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG102-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
