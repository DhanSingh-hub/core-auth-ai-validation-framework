# Additional Information Indicator (Element 116): SME and TBA Learning Note

## Purpose

Element 116 is the single field that gives every Segment 112 triad its meaning. Without a correct Element 116, Element 118's content cannot be interpreted or validated. This note explains how to reason about the code, its documented values, and its gaps.

## 1. Element 116 Is a Controlled Vocabulary, Not Free Text

Element 116 is a 3-digit numeric code (`N`, max length 3 bytes). It is not a description string; it is an index into a specification-defined table (Section 12.11's opening list, the Section 13.2 processing-rule narrative, and Appendix K's per-code sub-layouts).

## 2. Documented Values (as transcribed from Section 12.11 / Section 13.2)

| Code | Meaning | Element 118 shape |
| --- | --- | --- |
| 001 | Balance (gift/EBT/credit/phone card) | Variable, per Appendix K Table 001 |
| 002 | Reserved | N/A |
| 003 | AVS and/or NVS result | Variable |
| 004 | CVV/CVV2/CVC2/CID/DTVV | Variable |
| 005 | ECA/TeleCheck® Trace ID | Variable |
| 006 | ECA/TeleCheck® Denial Record Number | Variable |
| 007 | ECA/TeleCheck® Return Check Data | Variable |
| 008 | Loyalty Information — Version 1 | Variable |
| 009 | Visa® product result | Variable |
| 010 | Loyalty Information — Version 2 | Variable |
| 011 | User Data Information | Variable |
| 012 | Discover® Network Retrieval Reference Number | **Fixed 15 bytes** |
| 013 | Expiration Date (MMYY) for TransArmor - VeriFone edition | **Fixed 4 bytes** |
| 014 | Reserved (resolved 2026-09-26, same disposition as 002) | **REJECT — see [SEG112-SME-002](../segment-112-sme-tba-input-register.md)** |
| ... | ... | ... |
| 015 | Reserved (resolved 2026-09-26, same disposition as 002) | **REJECT** |
| 016 | PIN-on-Receipt Information | Variable |
| 017 | BUYPASS Host Card Type | **Fixed 3 bytes** |
| 018 | PINless Debit Information | **Fixed numeric 3** |
| 019 | Visa Spend Qualified Indicator Information | Variable |
| 020 | Host Prompts Information | Variable |
| 021 | Re-Price Data Response Information | Variable |
| 022 | CAVV Result | Variable |
| 023 | MCX Reference Number | Variable |
| 024 | Carwash Indicator | Variable |
| 025 | Language Indicator | Variable |
| 026 | DST Response | Variable |
| 027 | Universal Unique Identifier | Variable |
| 028 | Transaction Identifier | Variable |
| 029 | PAR (Payment Account Reference) Data | Variable |
| 030 | Merchant Advice Code | Variable |
| 031 | DAF Indicator | Variable |
| 032 | Agreement ID | Variable |
| 033 | Reserved (resolved 2026-09-26, same disposition as 002) | **REJECT** |
| 034 | Host-based Purchase Restriction | Variable |
| 035 | Cardholder Additional Information Result Code | Variable |
| 036 | Account Type | Variable |
| 037 | Account Funding Source | Variable |
| 038 | Transaction Link Identifier | Variable |
| 039 | Transaction Link Action Indicator | Variable |
| 040 | Fraud Score | Variable |
| 041 | Fraud Score Reason Code | Variable |
| 042 | Authentication Data Quality Indicator | Variable |
| 043 | Token Update First Use Indicator | Variable |
| 044 | Merchant Tran ID | Variable |
| 045 | Applied Special Service | Variable |
| 046 | Voyager Restriction Code | Variable |
| 047 | Visa Category Code | Variable |

The full authoritative business-requirement form of this table lives in [additional-information-value-catalog-business-requirements.md](additional-information-value-catalog-business-requirements.md).

## 3. Common Analysis Errors

- Treating Element 116 as descriptive text and matching on substring similarity instead of the exact 3-digit code.
- Assuming every code has a variable-length Element 118 — four codes (`012`, `013`, `017`, `018`) are fixed-length and must be validated against their exact length, not just "within the max."
- Silently accepting the reserved codes (`002`, `014`, `015`, `033`) instead of rejecting them per the resolved 2026-09-26 SME decision.
- Assuming only one triad is present per Segment 112 — production responses frequently carry two or more (e.g., `003` + `004` together).
- Confusing Element 116 (which type of information) with Element 117 (how long the value is) or Element 118 (the value itself).

## 4. Turning This Into Requirements

```text
BR: Element 116 shall be a 3-digit numeric code from the documented value set
    {001, 003-013, 016-032, 034-047}. Codes 002, 014, 015, and 033 are
    reserved/invalid and shall be rejected (resolved 2026-09-26).

TC-Positive: Element 116 = 004 (CVV), Element 118 length matches Element 117.
TC-Negative: Element 116 = 099 (undocumented, non-reserved) -> reject.
TC-Negative: Element 116 = 014 (reserved) -> reject.
```
