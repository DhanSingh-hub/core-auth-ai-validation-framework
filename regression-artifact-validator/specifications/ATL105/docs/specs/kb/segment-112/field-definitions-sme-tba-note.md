# Segment 112 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 112 — Additional Information Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.11, 13  
**Oracle:** [segment-112-rule-catalog.json](coverage/segment-112-rule-catalog.json) (10 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 112 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (5)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG112-R-001` | Segment Type is 112 | 12.11 | 85 | SPEC_DERIVED |
| `SEG112-R-002` | Segment Length identifies the segment's total length including Segment Type and Field Separators | 12.11 | 84 | SPEC_DERIVED |
| `SEG112-R-007` | Additional Information Indicator (Element 116) identifies the type of additional information being transmitted | 12.11 | 116 | SPEC_DERIVED |
| `SEG112-R-008` | Additional Information Length (Element 117) identifies the length of the following Additional Information (Element 118) | 12.11 | 117 | SPEC_DERIVED |
| `SEG112-R-009` | Additional Information (Element 118) is variable length and carries the value identified by its paired Indicator/Length | 12.11 | 118 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | See [Element 85 reference](#element-85-segment-type) |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits — see [Element 84 reference](#element-84-segment-length) | See [Element 84 reference](#element-84-segment-length) |
| 116 | Additional Information Indicator | N | 3 bytes | Fixed length of 3 digits | See [Element 116 reference](#element-116-additional-information-indicator) |
| 117 | Additional Information Length | N | 3 bytes | Fixed length of 3 digits | 001–985 |
| 118 | Additional Information | AN | 984 bytes | Variable length of up to 984 alphanumeric characters — see [Element 118 reference](#element-118-additional-information) | 001–984 a–z A–Z Please refer to Appendix K. Additional Information Data Layouts. |

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

### Element 116: Additional Information Indicator

- **Character type:** N · **Maximum length:** 3 bytes
- **Representation:** Fixed length of 3 digits
- **Purpose:** Identifies the type of additional information being transmitted in a Financial Transaction Response.
- **Processing rules:** Used in the Additional Information Data Segment (No. 112). BUYPASS initiates transmittal of balance information (001) in a Financial Transaction Response. A Financial Transaction Request initiates transmittal of AVS information (003) and CVV information (004) in a Financial Transaction Response. A Financial Transaction Request initiates transmittal of Visa® product result information (009) in a Financial Transaction Response.

**Valid Codes/Values**

| Code | Description |
|---|---|
| `001` | Balance information for gift card, EBT card, credit card, or phone card transactions |
| `002` | Reserved |
| `003` | Address Verification Service (AVS) information or Name Verification Service (NVS) or both the information |
| `004` | Card verification (CVV, CVV2, CVC2, CID, DTVV) information |
| `005` | ECA/ TeleCheck® Trace ID |
| `006` | ECA/ TeleCheck® Denial Record Number |
| `007` | ECA/ TeleCheck® Return Check Data |
| `008` | Loyalty Information – Version 1 |
| `009` | Visa® product result |
| `010` | Loyalty Information – Version 2 |
| `011` | User Data Information |
| `012` | Discover® Network Retrieval Reference Number |
| `013` | Expiration Date (MMYY) for TransArmor - VeriFone edition processing |
| `016` | PIN-on-Receipt Information |
| `017` | BUYPASS Host Card Type |
| `018` | PINless Debit Information |
| `019` | Visa Spend Qualified Indicator Information |
| `020` | Host Prompts Information |
| `021` | Re-Price Data Response Information |
| `022` | CAVV Result |
| `023` | MCX Reference Number |
| `024` | Carwash Indicator |
| `025` | Language Indicator |
| `026` | DST Response |
| `027` | Universal Unique Identifier |
| `028` | Transaction Identifier |
| `029` | PAR (Payment Account Reference) Data |
| `030` | Merchant Advice Code |
| `031` | DAF Indicator |
| `032` | Agreement ID |
| `034` | Host-based Purchase Restriction |
| `035` | Cardholder Additional Information Result Code |
| `036` | Account Type |
| `037` | Account Funding Source |
| `038` | Transaction Link Identifier |
| `039` | Transaction Link Action Indicator |
| `040` | Fraud Score |
| `041` | Fraud Score Reason Code |
| `042` | Authentication Data Quality Indicator |
| `043` | Token Update First Use Indicator |
| `044` | Merchant Tran ID |
| `045` | Applied Special Service |
| `046` | Voyager Restriction Code |
| `047` | Visa Category Code |

### Element 117: Additional Information Length

- **Character type:** N · **Maximum length:** 3 bytes
- **Representation:** Fixed length of 3 digits
- **Purpose:** Identifies the length of Additional Information (Element No. 118).
- **Processing rules:** Used in the Additional Information Data Segment (No. 112).

**Valid Codes/Values**

001–985

### Element 118: Additional Information

- **Character type:** AN · **Maximum length:** 984 bytes
- **Representation:** Variable length of up to 984 alphanumeric characters Note: Please refer to section 12.11, “Additional Information Data Segment,” for conditions affecting this element’s length.
- **Purpose:** Identifies the additional data being transmitted in the Financial Transaction response.
- **Processing rules:** Used in the Additional Information Data Segment (No. 112). When Element No. 116 (Additional Information Indicator) has a value of 001, gift card, EBT card, credit card, or phone card balance information is included here. When Element No. 116 (Additional Information Indicator) has a value of 003, AVS information is included here. When Element No. 116 (Additional Information Indicator) has a value of 004, card verification value information (CVV, CVV2, CVC2, and CID) is included here. When Element No. 116 (Additional Information Indicator) has a value of 005, ECA/ TeleCheck® Trace ID information is included here. When Element No. 116 (Additional Information Indicator) has a value of 006, ECA/ TeleCheck® Denial Record Number information is included here. When Element No. 116 (Additional Information Indicator) has a value of 007, ECA/ TeleCheck® Return Check Data is included here. When Element No. 116 (Additional Information Indicator) has a value of 008, loyalty information is included here. When Element No. 116 (Additional Information Indicator) has a value of 009, Visa® product result information is included here. When Element No. 116 (Additional Information Indicator) has a value of 010, loyalty information is included here. When Element No. 116 (Additional Information Indicator) has a value of 011, user data information is included here. When Element No. 116 (Additional Information Indicator) has a value of 012, the Discover® Network Retrieval Reference Number is included here. It has a fixed alphanumeric length of 15 bytes. It is included in all Discover® Network follow-on transactions. When Element No. 116 (Additional Information Indicator) has a value of 013, the Expiration Date (MMYY) is included here. It has a fixed length of 4 bytes. When Element No. 116 (Additional Information Indicator) has a value of 016, the PIN-on-Receipt Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 017, the BUYPASS Host Card Type is included here. It has a fixed length of three bytes. When Element No. 116 (Additional Information Indicator) has a value of 018, the PINless transaction processing information is included here. It has a fixed numeric length of 3. When Element No. 116 (Additional Information Indicator) has a value of 019, the Visa Spend Qualified Indicator Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 020, the Host Prompts Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 021, the Re-Price Data Response Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 022, the CAVV Result Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 023, the MCX Reference Number Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 024, the Carwash Indicator Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 025, the Language Indicator Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 026, the DST Response Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 027, the Universal Unique Identifier is included here. When Element No. 116 (Additional Information Indicator) has a value of 028, the Transaction Identifier Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 029, the PAR (Payment Account Reference) Data Information is included here. When Element No. 116 (Additional Information Indicator) has a value of 030, the Merchant Advice code is included here. When Element No. 116 (Additional Information Indicator) has a value of 031, the DAF Indicator is included here. When Element No. 116 (Additional Information Indicator) has a value of 032, the Agreement ID is included here. When Element No. 116 (Additional Information Indicator) has a value of 034, the Host-based Purchase Restriction is included here. When Element No. 116 (Additional Information Indicator) has a value of 035, the Cardholder Additional Information Result Code is included here. When Element No. 116 (Additional Information Indicator) has a value of 036, the Account Type is included here. When Element No. 116 (Additional Information Indicator) has a value of 037, the Account Funding Source is included here. When Element No. 116 (Additional Information Indicator) has a value of 038, the Transaction Link Identifier is included here. When Element No. 116 (Additional Information Indicator) has a value of 039, theTransaction Link Action Indicator is included here. When Element No. 116 (Additional Information Indicator) has a value of 040, the Fraud Score is included here. When Element No. 116 (Additional Information Indicator) has a value of 041, the Fraud Score Reason Code is included here. When Element No. 116 (Additional Information Indicator) has a value of 042, the Authentication Data Quality Indicator is included here. When Element No. 116 (Additional Information Indicator) has a value of 043, the Token Update First Use Indicator is included here. When Element No. 116 (Additional Information Indicator) has a value of 044, the Merchant Tran ID is included here. When Element No. 116 (Additional Information Indicator) has a value of 045, the Applied Special Service is included here. When Element No. 116 (Additional Information Indicator) has a value of 046, the Voyager Restriction Code is included here. When Element No. 116 (Additional Information Indicator) has a value of 047, the Visa Category Code is included here.

**Valid Codes/Values**

001–984 a–z A–Z Please refer to Appendix K. Additional Information Data Layouts.

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
  -> field position in Segment 112
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is 112
  -> source: ATL105 2026-3 §12.11 (SEG112-R-001)
  -> a violating payload shall fail validation citing SEG112-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

`Segment112PayloadValidator` now enforces the Segment 112 header and shared Element 116/117/118 triad structure. Appendix K layout checks are implemented only for bounded Tables 001, 003, and 004; all other assigned table contents remain `NOT_ASSERTABLE` pending their independent layout rules. See the [combined supplemental-information flow](../supplemental-information-111-112-flow.md).

## Review Checklist

- Is every rule traced to its source anchor (`SEG112-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
