# Segment 132 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 132 — CA Public Key File Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.9.1, 12.22  
**Oracle:** [segment-132-rule-catalog.json](coverage/segment-132-rule-catalog.json) (12 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 132 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (8)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG132-R-002` | Segment Type is fixed value 132, sourced at the Device | 12.22 | 85 | SPEC_DERIVED |
| `SEG132-R-003` | Segment Length is 3 digits (not 4 — Segment 132 is NOT one of the seven 4-digit-length segments) | 12.22 | 84 | SPEC_DERIVED |
| `SEG132-R-005` | Sequence Number (Element 86) is required, 6 digits; a limited range (100000-199999) applies ONLY when using the Multithreaded Dial Protocol Communications header for a CAPK File Load | 12.22 | 86 | SPEC_DERIVED |
| `SEG132-R-006` | Terminal Identifier (Element 102) is required, 13 characters; for EMV, the first two characters (Device Type) must be '+*' regardless of the actual device type | 12.22 | 102 | SPEC_DERIVED |
| `SEG132-R-007` | Load Type (Element 48) is required, fixed value 'K' (indicates Public Key information is requested) | 12.22 | 48 | SPEC_DERIVED |
| `SEG132-R-008` | Hardware Version (Element 43), Software Version (Element 96), and Firmware Version (Element 39) are all required device-version identifiers | 12.22 | 43,96,39 | SPEC_DERIVED |
| `SEG132-R-009` | CA Public Key File Checksum (Element 187) is required in Segment 132, identifying the checksum currently in use — a THIRD segment (after 130 request, 131 response) referencing this same element | 12.22 | 187 | SPEC_DERIVED |
| `SEG132-R-010` | Block Number (Element 11) is required, 3 digits, identifying the specific data block requested by a device or sent by the host — implying a multi-block CA key file transfer protocol not fully detailed in this section | 12.22 | 11 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | See [Element 85 reference](#element-85-segment-type) |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits — see [Element 84 reference](#element-84-segment-length) | See [Element 84 reference](#element-84-segment-length) |
| 86 | Sequence Number | N | 6 bytes | Fixed length of six digits | See [Element 86 reference](#element-86-sequence-number) |
| 102 | Terminal Identifier | AN | 22 bytes | Variable length up to 22 alphanumeric characters | See [Element 102 reference](#element-102-terminal-identifier) |
| 48 | Load Type | A | 1 byte | Fixed length of one alpha character | See [Element 48 reference](#element-48-load-type) |
| 43 | Hardware Version | AN | 4/8 bytes | Fixed length of four or eight alphanumeric characters | Any valid hardware version number |
| 96 | Software Version | AN | 8 bytes | Fixed length of eight alphanumeric characters | — |
| 39 | Firmware Version | AN | 8 bytes | Fixed length of eight alphanumeric characters |  |
| 11 | Block Number | N | 3 bytes | Fixed length of three digits | See [Element 11 reference](#element-11-block-number) |

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

### Element 86: Sequence Number

- **Character type:** N · **Maximum length:** 6 bytes
- **Representation:** Fixed length of six digits
- **Purpose:** Note: Identifies a transaction for the life of the transaction. Sequence Number must persist in all the subsequent transactions that are associated with the initial authorization request. In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture identifies the Sequence Number in the transaction’s Authorization Only request.
- **Processing rules:** Each transaction has a unique Sequence Number. It is included on all transaction types, and originates at the device. In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture is the same Sequence Number as in the transaction’s Authorization Only request. In the case of a purchase reversal/void or time-out reversal transaction, the Sequence Number must be the same as in the original transaction request. In the case of an Authorization Only Reversal, this element— present in the original transaction—must be present and identical in the Authorization Only Reversal transaction. (Note: The Authorization Only Reversal transaction enables a merchant to reverse the following credit card transactions within required timeframes: POS Authorization Only [Transaction Type code “3”]); POS Mail/Phone Authorization Only [Transaction Type code “B”]); and CAT Authorization Only [Transaction Type code “5”]). For TransArmor PKI Encryption and Tokenization, this element is included in a new data segment (No. 116 [TransArmor Load Data Segment]).

**Valid Codes/Values**

000001–999999 100000-199999 Note: This limited sequence number range (100000-199999) consists of the only allowed values when using the Multithreaded Dial Protocol Communications header for a CAPK File Load for the ATL105 message format

### Element 102: Terminal Identifier

- **Character type:** AN · **Maximum length:** 22 bytes
- **Representation:** Variable length up to 22 alphanumeric characters
- **Purpose:** Identifies the transaction device by Device Type, State Code (ANSI® Code), BUYPASS Merchant Number, and Device Number.
- **Processing rules:** Required for all transactions and messages. The Terminal Identifier must be assigned by and set up at BUYPASS. Note: In the case of a Table Load Request, Phone Load Request, Date and Time Load Request, or Software Load Request, the field length is 13 bytes; otherwise, the field length is a maximum of 22 bytes. For the TransArmor PKI Encryption and Tokenization Load Request, the first two characters (Device Type) of this element’s value must be “++” regardless of the actual device type. For the EMV Key Load Request, the first two characters (Device Type) of this element’s value must be “+*” regardless of the actual device type.

**Valid Codes/Values**

Device Type (Pos. Nos. 1–2) Any valid 2-character Device Type assigned by BUYPASS personnel. For the TransArmor PKI Encryption and Tokenization Load Request, this element’s value must be “++” regardless of the actual device type. For the EMV Key Load Request, this element’s value must be “+*” regardless of the actual device type. State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code). Please see Appendix D. Valid State Codes, for a list of valid ANSI® Code entries. BUYPASS Merchant Number (Starts immediately in Pos. No. 5.) Any valid 6- to 15-character BUYPASS Merchant Number assigned by BUYPASS personnel. The Device Number immediately follows the BUYPASS Merchant Number. Note: If using TransArmor, the first 6 digits of the Merchant Number are used in Error! Reference source not found.. P lease refer to the description of Element No. 2 (Account Number) for additional information. Device Number (Starts immediately after the BUYPASS Merchant Number.) Any valid 3-character Device Number assigned by BUYPASS personnel.

### Element 48: Load Type

- **Character type:** A · **Maximum length:** 1 byte
- **Representation:** Fixed length of one alpha character
- **Purpose:** Identifies the load type. For TransArmor PKI Encryption and Tokenization, it indicates that Key information is requested in the Key and Key ID request. For EMV, it indicates that CA Public Key File information is requested in the CA Public Key File Load Request.
- **Processing rules:** Required for TransArmor PKI Encryption and Tokenization. Required for EMV Public Key loads.

**Valid Codes/Values**

| Codes | Description |
|---|---|
| `P` | Partial Load (Phone, Table) |
| `D` | Date and Time Load |
| `K` | TransArmor PKI Encryption and Tokenization Load Unsigned Key or Signed Key |
| `K` | CA Public Key File Load Note: BUYPASS Device Type must be “+*” for the |
| `CA` | Public Key File Load Request. |
| `S` | Signing Key ID |

### Element 43: Hardware Version

- **Character type:** AN · **Maximum length:** 4/8 bytes
- **Representation:** Fixed length of four or eight alphanumeric characters
- **Purpose:** Identifies the device’s hardware version.
- **Processing rules:** Required for Totals Requests, Table Load Request, and TransArmor PKI Encryption and Tokenization Load Request. For Total Requests, some devices send 8 bytes of length.

**Valid Codes/Values**

Any valid hardware version number

### Element 96: Software Version

- **Character type:** AN · **Maximum length:** 8 bytes
- **Representation:** Fixed length of eight alphanumeric characters
- **Purpose:** Identifies the device’s software version at the customer location.
- **Processing rules:** Required for Totals Request, Table Load Request, and TransArmor PKI Encryption and Tokenization Load Request.

### Element 39: Firmware Version

- **Character type:** AN · **Maximum length:** 8 bytes
- **Representation:** Fixed length of eight alphanumeric characters
- **Purpose:** Identifies the device’s firmware version at the customer location.
- **Processing rules:** Required for Totals Requests, Table Load Request, and TransArmor PKI Encryption and Tokenization Load Request.

### Element 11: Block Number

- **Character type:** N · **Maximum length:** 3 bytes
- **Representation:** Fixed length of three digits
- **Purpose:** Identifies the specific proprietary or public key data block requested by a device or sent by the host.
- **Processing rules:** The host always uses a Block Number to track the data block it sends in an Electronic Mail response and a CA Public Key File Load response. If response processing is interrupted, the host knows the Block Number for the last complete data block sent in the response. A device is not required to track the Block Number it receives in an Electronic Mail response. If it is required to do so, and processing between the host and device is interrupted, the device can reinstate transmission of the interrupted data block using that Block Number in the next Electronic Mail request it sends to the host. A device always uses a Block Number to track the data block it sends in a Proprietary Data Load request. If request processing is interrupted, the host knows the Block Number for the last complete data block sent in the request. A device is required to track the Block Number it receives in a CA Public Key File Load response. If CA Public Key File Load processing between the host and device is interrupted, the device reinstates transmission of the interrupted data block using that Block Number in the next request it sends to the host.

**Valid Codes/Values**

| Codes | Description |
|---|---|
| `000` | The host sends the first data block for CA Public Key File Load. |
| `000` | The device sends this value indicating the last block of site / fuel volume data is being sent in the Proprietary Data Load request. |
| `001` | The host restarts data block processing, and sends the block that was Block No. 1 in the Electronic Mail response as the next block. <specific Block No.> The device can request a particular block by sending that Block Number in the next Electronic Mail request or CA Public Key File Load request. |

## Catalog Notes

- `SEG132-R-010` — PROVISIONAL: the block-by-block transfer mechanism (how multiple blocks of the CA key file are requested/delivered) is not detailed in Section 12.22 itself. Pending SME confirmation (SEG132-SME-003) on whether this is in scope for this training pass.

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
  -> field position in Segment 132
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 132, sourced at the Device
  -> source: ATL105 2026-3 §12.22 (SEG132-R-002)
  -> a violating payload shall fail validation citing SEG132-R-002
```

## Open Provisional Items

- **P-03** (SEG132-R-010): Is the multi-block CA key file transfer protocol (Block Number, Element 11) in scope for this training pass, or a separate workstream?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment132PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG132-R-002`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
