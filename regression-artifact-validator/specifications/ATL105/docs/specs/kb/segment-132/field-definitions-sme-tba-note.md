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
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 86 | Sequence Number | N | 6 bytes | Fixed length of six digits | 000001–999999 100000-199999 Note: This limited sequence number range (100000-199999) consists of the only allowed values when using the Multithreaded… |
| 102 | Terminal Identifier | AN | 22 bytes | Variable length up to 22 alphanumeric characters | Device Type (Pos. Nos. 1–2) Any valid 2-character Device Type assigned by BUYPASS personnel. For the TransArmor PKI Encryption and Tokenization Load… |
| 48 | Load Type | A | 1 byte | Fixed length of one alpha character | Codes Description P Partial Load (Phone, Table) D Date and Time Load K TransArmor PKI Encryption and Tokenization Load Unsigned Key or Signed Key K C… |
| 43 | Hardware Version | AN | 4/8 bytes | Fixed length of four or eight alphanumeric characters | Any valid hardware version number |
| 96 | Software Version | AN | 8 bytes | Fixed length of eight alphanumeric characters | — |
| 39 | Firmware Version | AN | 8 bytes | Fixed length of eight alphanumeric characters |  |
| 11 | Block Number | N | 3 bytes | Fixed length of three digits | Codes Description 000 The host sends the first data block for CA Public Key File Load. 000 The device sends this value indicating the last block of s… |

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
