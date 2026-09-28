# Segment 123 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 123 — NFC Payment Tokenization Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.19, AppendixY  
**Oracle:** [segment-123-rule-catalog.json](coverage/segment-123-rule-catalog.json) (11 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 123 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (6)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG123-R-001` | Segment Type is fixed value 123 (Element 85), Device-sourced | 12.19 | 85 | SPEC_DERIVED |
| `SEG123-R-006` | Token PAN Suffix (Element 197, 4 chars, Conditional, Issuer/Authorizer-sourced) — the last 4 digits of the cardholder PAN — is returned in the response only if supplied by the authorizer | 12.19 | 197 | SPEC_DERIVED |
| `SEG123-R-007` | CAVV, Revised Format (Element 195, 20 chars, Optional, Device-sourced) identifies Verified By Visa data with an Authentication Tracking Number (ATN) replacing XID | 12.19 | 195 | SPEC_DERIVED |
| `SEG123-R-008` | Cryptogram Token Data (Element 202) length is 28 or 56 bytes — Block A alone, or Block A and B combined | 12.19 | 202 | SPEC_DERIVED |
| `SEG123-R-009` | SafeKey Data (Element 203, 58 chars) is composed of a fixed 'SK' indicator (2 bytes), AEVV (28 bytes), and AESK Transaction Identifier (28 bytes); unused 28-byte portions must be space-filled rather than omitted | 12.19 | 203 | SPEC_DERIVED |
| `SEG123-R-010` | TAVV Cryptogram (Element 237, 28 bytes, base64 alphabet a-z/A-Z/0-9/+/=// ) may only be populated by the merchant during the transaction request, not the response | 12.19 | 237 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 237 | TAVV Cryptogram | AN | 28 bytes | Fixed length of 28 bytes. | — |

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
  -> field position in Segment 123
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 123 Element 85 , Device-sourced
  -> source: ATL105 2026-3 §12.19 (SEG123-R-001)
  -> a violating payload shall fail validation citing SEG123-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment123PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG123-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
