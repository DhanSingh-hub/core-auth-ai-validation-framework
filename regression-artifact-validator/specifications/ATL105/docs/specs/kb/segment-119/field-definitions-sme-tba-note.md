# Segment 119 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 119 — Totals with Proprietary Data Load Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.4, 11.4.1.2, 12.17, 9  
**Oracle:** [segment-119-rule-catalog.json](coverage/segment-119-rule-catalog.json) (36 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 119 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (20)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG119-R-008` | Segment Type is fixed value 119 | 12.17 | 85 | SPEC_DERIVED |
| `SEG119-R-009` | Segment Length is required and includes Segment Type and Field Separators | 12.17 | 84 | SPEC_DERIVED |
| `SEG119-R-010` | Information Byte is required | 12.17 | 44 | SPEC_DERIVED |
| `SEG119-R-011` | Terminal Identifier is required | 12.17 | 102 | SPEC_DERIVED |
| `SEG119-R-012` | Prompt Code is fixed value 990 | 12.17 | 78 | SPEC_DERIVED |
| `SEG119-R-013` | Employee Number is conditional | 12.17 | 32 | SPEC_DERIVED |
| `SEG119-R-014` | Password is conditional | 12.17 | 65 | SPEC_DERIVED |
| `SEG119-R-015` | Totals Date is required | 12.17 | 105 | SPEC_DERIVED |
| `SEG119-R-016` | Hardware Version is required | 12.17 | 43 | SPEC_DERIVED |
| `SEG119-R-017` | Software Version is required | 12.17 | 96 | SPEC_DERIVED |
| `SEG119-R-018` | Firmware Version is required | 12.17 | 39 | SPEC_DERIVED |
| `SEG119-R-019` | Sequence Number is required | 12.17 | 86 | SPEC_DERIVED |
| `SEG119-R-020` | Device Card Table Version is required | 12.17 | 176 | SPEC_DERIVED |
| `SEG119-R-021` | Host Discount Timestamp is required and uses CCYYMMDDHHMM | 12.17 | 179 | SPEC_DERIVED |
| `SEG119-R-022` | Currency Code is optional | 12.17 | 20 | SPEC_DERIVED |
| `SEG119-R-023` | Grand Total is required | 12.17 | 42 | SPEC_DERIVED |
| `SEG119-R-024` | Card Label is required for each card bucket | 12.17 | 13 | SPEC_DERIVED |
| `SEG119-R-025` | Card Type Total Count is required for each card bucket | 12.17 | 16 | SPEC_DERIVED |
| `SEG119-R-026` | Card Type Total Amount is required for each card bucket | 12.17 | 15 | SPEC_DERIVED |
| `SEG119-R-036` | Segment 119 synthetic fixtures are replaced or explicitly accepted for training | 11.4.1.2,12.17 | — | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 44 | Information Byte | AN | 1 byte | Fixed length of one alphanumeric character | Character Description ? Download Request 0 Single-message transmission 1 Multimessage transmission |
| 102 | Terminal Identifier | AN | 22 bytes | Variable length up to 22 alphanumeric characters | Device Type (Pos. Nos. 1–2) Any valid 2-character Device Type assigned by BUYPASS personnel. For the TransArmor PKI Encryption and Tokenization Load… |
| 78 | Prompt Code | AN | 4 bytes | Variable length of three or four alphanumeric characters | In the case of a financial transaction, position no. 1 is the Transaction Type (For a list of valid entries, please see Appendix G. Valid Transaction… |
| 32 | Employee Number | N | 4 bytes | Fixed length of four digits | Valid employee numbers. Note: Use 1111 if no employee numbers are assigned. |
| 65 | Password | N | 6 bytes | Variable length of up to six digits | Default password: 123456. |
| 105 | Totals Date | N | 6 bytes | Fixed length of six digits | Request Response Description MMDDYY YYMMDD Identifies a request for totals for a specified settlement date. 111111 111111 Identifies a request for to… |
| 43 | Hardware Version | AN | 4/8 bytes | Fixed length of four or eight alphanumeric characters | Any valid hardware version number |
| 96 | Software Version | AN | 8 bytes | Fixed length of eight alphanumeric characters | — |
| 39 | Firmware Version | AN | 8 bytes | Fixed length of eight alphanumeric characters |  |
| 86 | Sequence Number | N | 6 bytes | Fixed length of six digits | 000001–999999 100000-199999 Note: This limited sequence number range (100000-199999) consists of the only allowed values when using the Multithreaded… |
| 20 | Currency Code | N | 3 bytes | Fixed length of three digits | Please see Appendix L. Valid Currency Codes. |
| 42 | Grand Total | N | 8 bytes | Fixed length of eight digits with two assumed decimal places | 00000000–99999999 |
| 13 | Card Label | AN | 4 bytes | Fixed length of four alphanumeric characters; left-aligned and space-filled | Card Type Code MasterCard®/ Visa® CC Amex® TE Discover® Network, Diners Club®, JCB® DS Authorization only AO Debit DB Fleet FL Cash CS Proprietary PR… |
| 16 | Card Type Total Count | N | 5 bytes | Fixed length of five digits | 00001–99999 |
| 15 | Card Type Total Amount | N | 8 bytes | Fixed length of eight digits with two assumed decimal places | 00000000–99999999 |

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
  -> field position in Segment 119
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 119
  -> source: ATL105 2026-3 §12.17 (SEG119-R-008)
  -> a violating payload shall fail validation citing SEG119-R-008
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment119PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG119-R-008`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
