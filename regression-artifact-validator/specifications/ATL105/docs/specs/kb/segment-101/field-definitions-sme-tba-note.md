# Segment 101 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 101 — Fleet Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.1, 12, 12.2  
**Oracle:** [segment-101-rule-catalog.json](coverage/segment-101-rule-catalog.json) (26 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 101 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (16)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG101-R-004` | Segment Type is 101 | 12.2 | 85 | SPEC_DERIVED |
| `SEG101-R-005` | Segment Length is 3 digits representing the segment content length | 12.2 | 84 | SPEC_DERIVED |
| `SEG101-R-009` | Odometer, when populated, is numeric with maximum length 8 | 12.2 | 64 | SPEC_DERIVED |
| `SEG101-R-010` | Vehicle Number, when populated, is alphanumeric with maximum length 10 | 12.2 | 108 | SPEC_DERIVED |
| `SEG101-R-011` | Job Number, when populated, is alphanumeric with maximum length 10 | 12.2 | 47 | SPEC_DERIVED |
| `SEG101-R-012` | Driver/Identification Number, when populated, is alphanumeric with maximum length 10 | 12.2 | 31 | SPEC_DERIVED |
| `SEG101-R-013` | Fleet Employee Number, when populated, is alphanumeric with maximum length 10 | 12.2 | 40 | SPEC_DERIVED |
| `SEG101-R-014` | License #, when populated, is alphanumeric with maximum length 10 | 12.2 | 158 | SPEC_DERIVED |
| `SEG101-R-015` | Job ID, when populated, is alphanumeric with maximum length 12 | 12.2 | 159 | SPEC_DERIVED |
| `SEG101-R-016` | Department #, when populated, is alphanumeric with maximum length 12 | 12.2 | 160 | SPEC_DERIVED |
| `SEG101-R-017` | Customer Data, when populated, is alphanumeric with maximum length 12 | 12.2 | 161 | SPEC_DERIVED |
| `SEG101-R-018` | User ID, when populated, is alphanumeric with maximum length 12 and non-zero | 12.2 | 162 | SPEC_DERIVED |
| `SEG101-R-019` | Vehicle ID#, when populated, is alphanumeric with maximum length 8 | 12.2 | 163 | SPEC_DERIVED |
| `SEG101-R-022` | Fleet Tag format is 3-byte code plus up to 31-byte data payload | 12.2 | — | SPEC_DERIVED |
| `SEG101-R-023` | Fleet Tag 3-byte code must be one of the 17 codes in the fleet-tag code table | 12.2 | — | SPEC_DERIVED |
| `SEG101-R-024` | Fleet Tag data payload conforms to the per-code format | 12.2 | — | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | See [Element 85 reference](#element-85-segment-type) |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits — see [Element 84 reference](#element-84-segment-length) | See [Element 84 reference](#element-84-segment-length) |
| 64 | Odometer | N | 8 bytes | Variable length of one to eight digits | 1–99999999 |
| 108 | Vehicle Number | N | 10 bytes | Variable length of up to 10 digits | Any numeric representation |
| 47 | Job Number | N | 10 bytes | Variable length of up to 10 digits | Any numeric representation |
| 31 | Driver/Identification Number | N | 10 bytes | Variable length of up to 10 digits | Any numeric representation |
| 40 | Fleet Employee Number | N | 10 bytes | Variable length of up to 10 digits | Any valid Fleet Employee Number |
| 158 | License # | AN | 10 bytes | Variable maximum length of 10 alphanumeric characters | Any valid License #. |
| 159 | Job ID | AN | 12 bytes | Variable maximum length of 12 alphanumeric characters | Any valid Job ID. |
| 160 | Department # | AN | 12 bytes | Variable maximum length of 12 alphanumeric characters | Any valid Department #. |
| 161 | Customer Data | AN | 12 bytes | Variable maximum length of 12 alphanumeric characters | Any valid Customer Data. |
| 162 | User ID | AN | 12 bytes | Variable maximum length of 12 alphanumeric characters | Any valid User ID, except zero [0]. |
| 163 | Vehicle ID# | AN | 8 bytes | Variable maximum length of 8 alphanumeric characters | Any valid Vehicle ID#. |

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

### Element 64: Odometer

- **Character type:** N · **Maximum length:** 8 bytes
- **Representation:** Variable length of one to eight digits
- **Purpose:** Identifies the odometer reading keyed in by the customer or clerk.
- **Processing rules:** Please refer to the latest release of BUYPASS® Platform Petroleum Industry Processing Specifications for issuer requirements. If required by the issuer, this element is found in the Fleet Data Segment (Segment No. 101).

**Valid Codes/Values**

1–99999999

### Element 108: Vehicle Number

- **Character type:** N · **Maximum length:** 10 bytes
- **Representation:** Variable length of up to 10 digits
- **Purpose:** Identifies the Vehicle Number.
- **Processing rules:** Please refer to the latest release of BUYPASS® Platform Petroleum Industry Processing Specifications for issuer requirements. If required by the issuer, this element is found in the Fleet Data Segment (Segment No. 101).

**Valid Codes/Values**

Any numeric representation

### Element 47: Job Number

- **Character type:** N · **Maximum length:** 10 bytes
- **Representation:** Variable length of up to 10 digits
- **Purpose:** Identifies the Job Number.
- **Processing rules:** Please refer to the latest release of BUYPASS® Platform Petroleum Industry Processing Specifications for issuer requirements. If required by the issuer, this element is found in the Fleet Data Segment (Segment No. 101).

**Valid Codes/Values**

Any numeric representation

### Element 31: Driver/Identification Number

- **Character type:** N · **Maximum length:** 10 bytes
- **Representation:** Variable length of up to 10 digits
- **Purpose:** Used for fleet cards that require an unencrypted Driver/Identification Number.
- **Processing rules:** Please refer to the latest release of BUYPASS® Platform Petroleum Industry Processing Specifications for issuer requirements. If required by the issuer, this number must be an unencrypted Driver/Identification Number. If required by the issuer, this element is found in the Fleet Data Segment (Segment No. 101).

**Valid Codes/Values**

Any numeric representation

### Element 40: Fleet Employee Number

- **Character type:** N · **Maximum length:** 10 bytes
- **Representation:** Variable length of up to 10 digits
- **Purpose:** Used for fleet cards that require a Fleet Employee Number.
- **Processing rules:** Please refer to the latest release of BUYPASS® Platform Petroleum Industry Processing Specifications for issuer requirements. If required by the issuer, this element is found in the Fleet Data Segment (Segment No. 101).

**Valid Codes/Values**

Any valid Fleet Employee Number

### Element 158: License #

- **Character type:** AN · **Maximum length:** 10 bytes
- **Representation:** Variable maximum length of 10 alphanumeric characters
- **Purpose:** If required by the issuer, it identifies the License # of the fleet card user.
- **Processing rules:** Please refer to the latest release of BUYPASS® Platform Petroleum Industry Processing Specifications for issuer requirements. If required by issuer, this element is found in the Fleet Data Segment (Segment No. 101).

**Valid Codes/Values**

Any valid License #.

### Element 159: Job ID

- **Character type:** AN · **Maximum length:** 12 bytes
- **Representation:** Variable maximum length of 12 alphanumeric characters
- **Purpose:** If required by the issuer, it identifies the Job ID.
- **Processing rules:** Please refer to the latest release of BUYPASS® Platform Petroleum Industry Processing Specifications for issuer requirements. If required by issuer, this element is found in the Fleet Data Segment (Segment No. 101).

**Valid Codes/Values**

Any valid Job ID.

### Element 160: Department #

- **Character type:** AN · **Maximum length:** 12 bytes
- **Representation:** Variable maximum length of 12 alphanumeric characters
- **Purpose:** If required by the issuer, it identifies the Department #.
- **Processing rules:** Please refer to the latest release of BUYPASS® Platform Petroleum Industry Processing Specifications for issuer requirements. If required by issuer, this element is found in the Fleet Data Segment (Segment No. 101).

**Valid Codes/Values**

Any valid Department #.

### Element 161: Customer Data

- **Character type:** AN · **Maximum length:** 12 bytes
- **Representation:** Variable maximum length of 12 alphanumeric characters
- **Purpose:** If required by the issuer, it identifies the Customer Data.
- **Processing rules:** Please refer to the latest release of BUYPASS® Platform Petroleum Industry Processing Specifications for issuer requirements. If required by issuer, this element is found in the Fleet Data Segment (Segment No. 101).

**Valid Codes/Values**

Any valid Customer Data.

### Element 162: User ID

- **Character type:** AN · **Maximum length:** 12 bytes
- **Representation:** Variable maximum length of 12 alphanumeric characters
- **Purpose:** If required by the issuer, it identifies the User ID.
- **Processing rules:** Please refer to the latest release of BUYPASS® Platform Petroleum Industry Processing Specifications for issuer requirements. If required by issuer, this element is found in the Fleet Data Segment (Segment No. 101).

**Valid Codes/Values**

Any valid User ID, except zero [0].

### Element 163: Vehicle ID#

- **Character type:** AN · **Maximum length:** 8 bytes
- **Representation:** Variable maximum length of 8 alphanumeric characters
- **Purpose:** If required by the issuer, it identifies the Vehicle ID#.
- **Processing rules:** Please refer to the latest release of BUYPASS® Platform Petroleum Industry Processing Specifications for issuer requirements. If required by issuer, this element is found in the Fleet Data Segment (Segment No. 101).

**Valid Codes/Values**

Any valid Vehicle ID#.

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
  -> field position in Segment 101
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is 101
  -> source: ATL105 2026-3 §12.2 (SEG101-R-004)
  -> a violating payload shall fail validation citing SEG101-R-004
```

## Open Provisional Items

- **P-02** (SEG101-R-024): Confirm DLN carries 'Driver License name' (spec text 'nameation' appears to be a typo).

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

`Segment101PayloadValidator` exists in `src/main`; confirm each rule above has an assertion before marking it covered.

## Review Checklist

- Is every rule traced to its source anchor (`SEG101-R-004`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
