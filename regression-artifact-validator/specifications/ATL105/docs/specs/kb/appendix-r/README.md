# Appendix R EMV Chip Data Training

**Specification:** BUYPASS ATL105 2026-3, Appendix R-1<br>
**Data segment:** Element 189 (EMV Chip Data Length) and Element 190 (EMV Chip Data), carried in
Segment 130 for EMV transactions<br>
**Terms:** EMV Chip Data, Tag/Length/Value (TLV), Application Identifier (AID), Dedicated File (DF)
Name, PAN, Track 2 equivalent data<br>
**Status:** `PARTIALLY_COVERED`; six of seven BRs have an independent, bounded implementation and
in-repo test fixtures, including a golden fixture transcribed byte-for-byte from the Appendix R-1
worked example. Cross-field agreement with Segment 100 stays `REVIEW_REQUIRED` by design, same
bounding used throughout this appendix family.

## Why this appendix was rebuilt rather than extended

Appendix R already had an evidence file in the repo before this training pass
(`appendix-r-segment-100-coverage.json`), but it declared BRs `COVERED`/`PARTIALLY_COVERED` and test
data `EXECUTABLE` with **no backing Java validator anywhere in `src/`** — a claim of tested coverage
with zero implementation behind it. This is a more serious gap than the earlier documentation-style
difference noted between Appendix N/O and Appendix E/F/G/J/I/L; it was corrected here by building a
real `AppendixREmvChipDataOracle` and matching unit tests, and by re-scoring the evidence file to
reflect only what is actually implemented and tested, following the same methodology used for
Appendix N, O, and Q.

## Scope and Field Boundaries

Appendix R's own text (source lines ~35403-35436, between Appendix Q and Appendix S) is short: it
states only two field attributes — Length (`N3`, e.g. `0nnn`) and Tag/length/value data (`ANSB`, up to
999 bytes, "EMV chip tag fields included as applicable") — followed by a worked example of one EMV
chip data packet. It explicitly disclaims completeness:

> Note: The below table does not contain complete list of tags. For complete list of EMV tags, please
> contact your BUYPASS representative.

This is the opposite posture from Appendix Q's exhaustive code tables, so **no BR here depends on a
closed tag catalog**. The actual, concrete, independently-assertable rules come from the main-body
definitions of the two elements Appendix R exemplifies (source lines ~24341-24383, immediately before
the "For an example of this data element, please see Appendix R" cross-reference):

- **Element 189 (EMV Chip Data Length)** — fixed length of three digits, valid codes `000`-`999`,
  identifying the length of the following field.
- **Element 190 (EMV Chip Data)** — variable length up to 999 bytes, binary TLV sub-elements:
  - A tag is two bytes only when the last five bits of its first byte are all `1`; otherwise one byte.
  - A length byte with its high bit set carries a byte count for a following big-endian length value
    (long form); otherwise its low seven bits are the length itself (short form).
  - "The Application ID (AID) TLV (tag 9F06) or Dedicated File Name (tag 84) must be included."
  - "Tag '5A' (PAN) and Tag '57' (Track 2 equivalent data) must not be included."
  - "If the same data element appears several times ... this field must contain the last value that
    was present on the card to terminal interface during the transaction."

Decomposed into seven BRs:

- **BR-SEG100-APPR-EMV-COMPANION** — Element 189/190 presence given an EMV-transaction flag.
- **BR-SEG100-APPR-CHIP-LENGTH** — three-digit format plus byte-count consistency with the encoded
  chip data.
- **BR-SEG100-APPR-TLV-FORMAT** — binary TLV decomposition with no truncated field and no leftover
  bytes.
- **BR-SEG100-APPR-MANDATORY-IDENTIFIER** — AID (9F06) or DF Name (84) presence.
- **BR-SEG100-APPR-PROHIBITED-SENSITIVE-TAG** — PAN (5A) and Track 2 equivalent data (57) absence.
- **BR-SEG100-APPR-DUPLICATE-TAG-REVIEW** — duplicate-tag detection; `REVIEW_REQUIRED` rather than
  `FAIL` because the correctness of the resulting "last value" cannot be independently verified from a
  submitted snapshot alone.
- **BR-SEG100-APPR-CROSS-FIELD** — agreement between decoded tag values (e.g. `9F02` Amount
  Authorized, `5F2A` Transaction Currency Code, `9C` Transaction Type, `9F1A` Terminal Country Code)
  and the corresponding Segment 100 elements. Neither Appendix R nor Element 189/190's own
  definitions state this formula, so it stays `REVIEW_REQUIRED` by design (same bounding used for
  Appendix Q's `BR-SEG100-APPQ-FLOW-CONSISTENCY`).

Whether Segment 130 as a whole is present (as opposed to just Element 189/190) is a Segment 130
structural question, not something Appendix R's own one-page text states, so it is not modeled beyond
the narrow field-presence check in `BR-SEG100-APPR-EMV-COMPANION`.

## Test Solution Work Completed

`AppendixREmvChipDataOracle` independently implements and tests all seven BRs:

- **BR-SEG100-APPR-CHIP-LENGTH** — `FAIL` if the length field is not exactly three numeric digits, or
  if the EMV Chip Data value is not valid hex, or if the stated length does not match the actual
  decoded byte count; `PASS` otherwise.
- **BR-SEG100-APPR-TLV-FORMAT** — decomposes the chip data value into TLV entries using Element 190's
  binary encoding rules (two-byte vs one-byte tag detection via the last-five-bits test, short/long
  form length decoding); `FAIL` on any truncated tag/length/value field or leftover bytes, `PASS` on
  clean full decomposition.
- **BR-SEG100-APPR-MANDATORY-IDENTIFIER** / **BR-SEG100-APPR-PROHIBITED-SENSITIVE-TAG** /
  **BR-SEG100-APPR-DUPLICATE-TAG-REVIEW** — all three read the decoded TLV entry list; each is `FAIL`
  (cascading) if the TLV structure itself is invalid, since tag-level rules cannot be evaluated on
  malformed input.
- **BR-SEG100-APPR-EMV-COMPANION** — takes a `TransactionContext`; `REVIEW_REQUIRED` if the
  EMV-transaction flag is unknown, `PASS`/`FAIL` depending on confirmed EMV status and field presence
  otherwise.
- **BR-SEG100-APPR-CROSS-FIELD** — always `REVIEW_REQUIRED`; no cross-field formula is stated by
  either source.

A golden fixture (`GOLDEN_CHIP_DATA`/`GOLDEN_LENGTH` in the test class) is transcribed byte-for-byte
from the Appendix R-1 worked example's 16 Tag/Length/Value rows (the example's leading "01\06
EMV/Chip Data length (BCD packed)" row represents Element 189's own length prefix, not part of Element
190's TLV value, so it is supplied separately as the length field). This fixture decodes cleanly to 16
entries including both the AID and DF Name tags, exercises both the two-byte tag form (e.g. `9F06`,
`5F2A`) and the one-byte tag form (e.g. `82`, `9C`, `9A`), and contains no PAN/Track 2/duplicate tags —
so it passes every BR under an EMV-transaction context, confirming the oracle's TLV decoding logic
against a real spec-sourced example rather than only synthetic data.

Package totals: **7 BR / 5 TS / 5 TC / 15 TD** (14 `EXECUTABLE`/`REVIEW_REQUIRED`-by-design plus one
`EXTERNAL_FIXTURE_REQUIRED` for cryptogram authenticity), backed by 20 unit tests in
[`AppendixREmvChipDataOracleTest`](../../../../../../src/test/java/com/coreauth/validator/AppendixREmvChipDataOracleTest.java)
and
[`appendix-r-segment-100-coverage.json`](../../../../test-output/test-json/appendices/appendix-r-segment-100-coverage.json).

## Deferred Work

1. Confirm with the implementation team whether a concrete formula exists linking decoded EMV tag
   values to Segment 100 context (amount, currency, transaction type, terminal country) — none is
   stated in Appendix R or Element 189/190 today, so `BR-SEG100-APPR-CROSS-FIELD` cannot move past
   `REVIEW_REQUIRED` without an SME-confirmed rule or a canonical anchor elsewhere in the spec.
2. Cryptographic verification of the Application Cryptogram (tag `9F26`, ARQC/TC) requires a certified
   EMV kernel or HSM and cannot be synthesized; tracked as `EXTERNAL_FIXTURE_REQUIRED` and explicitly
   out of scope until such a fixture is available.
3. Appendix R itself states its tag list is not complete ("contact your BUYPASS representative" for
   the full list); if a closed EMV tag catalog is ever required for a future BR, it must come from
   BUYPASS directly, not be inferred from this appendix's illustrative example.
4. Obtain real AI-generated EMV Chip Data artifacts to replace the in-repo synthetic/golden fixtures
   before implementation certification.
5. If a modeled EMV Chip Data field is ever added to the Segment 100 payload schema, decide whether to
   wire `AppendixREmvChipDataOracle` into `Segment100PayloadValidator`; today no such field exists, so
   this validator is intentionally standalone (same posture as Appendix J/N/O/Q).

## Source References

- ATL105 2026-3 Appendix R-1 (EMV Chip Data Example): [extracted specification](../../extracted_text.txt),
  lines ~35403-35436.
- ATL105 2026-3 Elements 189-190 (EMV Chip Data Length / EMV Chip Data field definitions Appendix R
  exemplifies): [extracted specification](../../extracted_text.txt), lines ~24341-24383.
- [Segment 100 Appendix family training index](../segment-100/README.md) and
  [Appendix Family Gap Closure Register](../segment-100/appendix-family-gap-closure-register.md).
- [Appendix Q National POS Condition Code Training](../appendix-q/README.md) (prior appendix in this
  family, same bounded-independent-validator pattern).
