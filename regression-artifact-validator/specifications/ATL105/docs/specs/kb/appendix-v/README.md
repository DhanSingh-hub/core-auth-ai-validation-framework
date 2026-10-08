# Appendix V Moneris Data Layout Training

**Specification:** BUYPASS ATL105 2026-3, Appendix V-1 through V-4
**Segments:** 135 request and 136 response
**Status:** `PARTIALLY_COVERED`; every Appendix V request/response table is represented in the oracle and coverage package. Independently checkable framing, table IDs, lengths, stated alphanumeric/numeric representations, language mappings, and MAC/key block widths have executable synthetic tests.

## Scope

Appendix V describes Moneris Data tables encoded as consecutive 3-digit Table ID, 3-digit Table
Length, and Table Data records. The request layout defines tables `001` through `009`; the response
layout defines tables `001` through `006`.

[`AppendixVMonerisDataOracle`](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixVMonerisDataOracle.java)
checks the framing and the source-stated boundaries for supplied tables. It does not require every
listed table to be present or infer an order because Appendix V does not state those requirements.
The SPDH Header (Table 001) is checked for its 48-character width, its eleven component widths,
alphanumeric `A` fields, and numeric `N` fields. Terminal and Merchant IDs are checked against their
stated alphanumeric widths. Request Table 004 accepts codes `3`, `4`, `5`, and `6`; the oracle also
exposes their full Terminal/PED/Merchant Receipt/Cardholder Receipt language mappings. Request Table
005 is alphanumeric and no longer than 7 characters, but its `an3` data attribute conflicts with
that maximum and remains flagged. Request Tables 006-009 enforce their listed alphanumeric widths;
the allowed values/semantics for Application Account Type and Terminal Capabilities remain
unspecified. Request and response MAC tables are checked for length 16, not cryptographic validity.
Response Table 006 checks a 3-character indicator followed by zero to three 16-unit key blocks (3,
19, 35, or 51 characters); the documented `Y` positions identify MAC Encryption, Data Encryption,
and PIN Encryption keys in that order.

## Explicit limits

- Segment 100 routing and Segment 135/136 presence cannot be derived from Appendix V alone.
- The amount and sequence descriptions do not specify a comparison formula to the originating
  authorization or Segment 100 Element 86. Those cross-message rules stay `REVIEW_REQUIRED`.
- The Appendix V-2 data descriptions conflict for request Table 004 (`an13` vs fixed Table Length
  `001`) and Table 005 (`an3` vs variable maximum length `7`). The oracle validates the listed
  one-character language codes and the stated maximum, but does not claim those conflicting
  attributes have been reconciled.
- Response Table 006 does not state which character indicates an absent key, nor the textual
  encoding of its up-to-three 16-byte keys. The oracle reports only key categories explicitly marked
  `Y`; it does not validate other indicator values or claim key material integrity.
- MAC width is testable; MAC computation/authenticity is not, because Appendix V refers to an
  algorithm supplied by Moneris without specifying it.
- Appendix V does not provide the allowed values or semantics for Application Account Type or
  Terminal Capabilities flags; only their stated lengths are asserted.

The source does not specify whether table IDs must be unique, every listed table must be present, or
the tables must appear in the order printed; the oracle deliberately does not invent those rules.
The synthetic test values exercise these source-derived checks; they are not Moneris-approved
production samples. Broader Segment 135/136 rules such as destination routing, separator behavior,
and segment-length semantics remain governed by their own open SME items and validators.

## Test Solution artifacts

- Oracle tests: [`AppendixVMonerisDataOracleTest`](../../../../../../src/test/java/com/coreauth/validator/AppendixVMonerisDataOracleTest.java)
- Coverage package: [`appendix-v-segment-100-coverage.json`](../../../../test-output/test-json/appendices/appendix-v-segment-100-coverage.json)
- Segment context: [Segment 135](../segment-135/README.md) · [Segment 136](../segment-136/README.md)

## Source

ATL105 2026-3, Appendix V-1 through V-4 in
[`extracted_text.txt`](../../extracted_text.txt), including the Moneris request and response
tables and their Table ID, Table Length, and Table Data definitions.
