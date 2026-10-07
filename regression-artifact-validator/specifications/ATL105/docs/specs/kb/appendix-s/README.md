# Appendix S CA Public Key File Training

**Specification:** BUYPASS ATL105 2026-3, Appendix S-1 through S-2<br>
**Data segment:** Standalone CA Public Key File (CA_KEYS), exchanged via the separate Segment 132
(CA Public Key File Load Segment) request/response; not a field carried inside the Segment 100/130
authorization payload<br>
**Terms:** Certification Authority (CA) Public Key, RID (Registered Application Provider Identifier),
Index, Modulus, Exponent, Check Sum, SHA-1, RSA<br>
**Status:** `PARTIALLY_COVERED`; nine of ten BRs have an independent, bounded implementation and
in-repo test fixtures, including a genuine SHA-1 checksum recomputation against a golden fixture.
EMV AID-to-key configuration consistency stays `REVIEW_REQUIRED` by design, same bounding used
throughout this appendix family.

## Why this appendix was rebuilt rather than extended

Appendix S already had an evidence file in the repo before this training pass
(`appendix-s-segment-100-coverage.json`). Unlike Appendix R, it was already honest about BR status —
all three existing BRs were `REVIEW_REQUIRED` — but two of its three `testData` entries still claimed
`EXECUTABLE` readiness despite **no backing Java validator anywhere in `src/`**. That is a smaller but
still real gap than Appendix R's: a claim of "ready to execute" test data with zero implementing code.
It was corrected here by building a real `AppendixSCaPublicKeyFileOracle` and matching unit tests, and
by re-scoring the evidence file to reflect only what is actually implemented and tested, following the
same methodology used for Appendix N, O, Q, and R.

## Scope and Field Boundaries

Appendix S's own text (source lines ~35448-35558, immediately after Appendix R) describes a standalone
ASCII file: comma-separated fields, CRLF (`\r\n`) terminated records. One header record (File Name
fixed `CA_KEYS`, File Version `N4`) is followed by repeating key records, each with eight fields:

- **Expiry Date** (`N8`, `MMDDYYYY`)
- **Certification Authority Hash Algorithm Indicator** (`N2`, fixed `01` — SHA-1; "no other value is
  currently supported")
- **Certification Authority Public Key Algorithm Indicator** (`N2`, fixed `01` — RSA; "no other value
  is currently supported")
- **Registered Application Provider Identifier (RID)** (`AN`, 1-10 characters, e.g. `A000000003`)
- **Index** (`Hex2`)
- **Modulus** (`Hex`, variable length)
- **Exponent** (`N`, 2 or 6 characters — value `03` or `065537`/2^16+1; "no other value is defined")
- **Certification Authority Public Key Check Sum** (`Hex40`) — "a check value calculated on the
  concatenation of all parts of the Certification Authority Public Key ... using SHA-1"

Cross-checked against Data Segment No. 132 (CA Public Key File Load Segment, source lines ~14234-14360,
which only carries a checksum/version summary field, `CA Public Key File Checksum`) this file's full
content is confirmed to be **external to the Segment 100/130 payload** — the same standalone-artifact
posture as Appendix N (tokens), Appendix O (tokenization), and Appendix R (EMV chip data).

Decomposed into ten BRs (three of which reuse pre-existing `BR-SEG100-APPS-*` IDs already referenced
across generated cross-segment reports before this training pass):

- **BR-SEG100-APPS-CA-FILE-HEADER** — `CA_KEYS` file name + 4-digit File Version, comma-separated,
  CRLF-terminated (pre-existing ID, reused).
- **BR-SEG100-APPS-CA-KEY-RECORD** — exactly the 8 defined fields per key record, comma-separated,
  CRLF-terminated (pre-existing ID, reused).
- **BR-SEG100-APPS-EXPIRY-DATE** — 8 numeric digits, structurally valid `MMDDYYYY` calendar date.
- **BR-SEG100-APPS-HASH-ALGORITHM** — fixed value `01`.
- **BR-SEG100-APPS-PUBKEY-ALGORITHM** — fixed value `01`.
- **BR-SEG100-APPS-RID-FORMAT** — 1-10 alphanumeric characters.
- **BR-SEG100-APPS-INDEX-FORMAT** — exactly 2 hex characters.
- **BR-SEG100-APPS-MODULUS-FORMAT** — non-empty, even-length hex (whole bytes).
- **BR-SEG100-APPS-EXPONENT-VALUE** — `03` or `065537`.
- **BR-SEG100-APPS-CHECKSUM-VERIFICATION** — SHA-1(RID‖Index‖Modulus‖Exponent) over hex-decoded bytes,
  independently recomputed (the genuinely cryptographic rule, mirroring Appendix N's Luhn check digit
  and Appendix O's Luhn token check digit).
- **BR-SEG100-APPS-CA-EMV-LINK** — EMV AID-to-key configuration consistency (pre-existing ID, reused);
  `REVIEW_REQUIRED` by design, since Appendix S states only the file/record layout, not an AID-to-key
  resolution formula, and genuine resolution requires a real production key file.

## Test Solution Work Completed

`AppendixSCaPublicKeyFileOracle` independently implements and tests nine of ten BRs:

- **BR-SEG100-APPS-CA-FILE-HEADER** — `FAIL` if the file name is not `CA_KEYS`, the version is not 4
  numeric digits, the record is not CRLF-terminated, or the field count is wrong; `PASS` otherwise.
- **BR-SEG100-APPS-CA-KEY-RECORD** — `FAIL` if a key record does not have exactly 8 comma-separated,
  CRLF-terminated fields; `PASS` otherwise.
- **BR-SEG100-APPS-EXPIRY-DATE** — `FAIL` if not 8 numeric digits or not a structurally valid calendar
  date (including Feb-29 leap-year handling); `PASS` otherwise.
- **BR-SEG100-APPS-HASH-ALGORITHM** / **BR-SEG100-APPS-PUBKEY-ALGORITHM** — fixed-value `01` checks.
- **BR-SEG100-APPS-RID-FORMAT** — 1-10 alphanumeric character length/charset check.
- **BR-SEG100-APPS-INDEX-FORMAT** — exactly 2 hex characters.
- **BR-SEG100-APPS-MODULUS-FORMAT** — non-empty, even-length hex.
- **BR-SEG100-APPS-EXPONENT-VALUE** — value-set check against `03`/`065537`.
- **BR-SEG100-APPS-CHECKSUM-VERIFICATION** — hex-decodes RID, Index, Modulus, and Exponent, computes
  `SHA-1(RID || Index || Modulus || Exponent)` via `MessageDigest.getInstance("SHA-1")`, and compares
  against the declared Check Sum; `FAIL` (with a clear reason) if any of the four fields is not valid
  even-length hex before recomputation is even attempted, since RID is spec-typed `AN` (alphanumeric)
  and is not guaranteed to be even-length hex.
- **BR-SEG100-APPS-CA-EMV-LINK** — always `REVIEW_REQUIRED`; no AID-to-key resolution formula is
  stated by Appendix S.

A golden fixture uses RID `A000000003` (Appendix S's own worked example value, "eg. A000000003"), a
synthetic-but-internally-self-consistent 100-hex-character modulus, and both exponent forms (`03` and
`065537`), with checksums computed externally via SHA-1 and cross-verified by the oracle for both
forms — confirming the checksum recomputation logic against values that are not hard-coded into the
production code path.

Package totals: **11 BR / 5 TS / 5 TC / 21 TD** (20 `EXECUTABLE` plus one `EXTERNAL_FIXTURE_REQUIRED`
for AID-to-key resolution), backed by 26 unit tests in
[`AppendixSCaPublicKeyFileOracleTest`](../../../../../../src/test/java/com/coreauth/validator/AppendixSCaPublicKeyFileOracleTest.java)
and
[`appendix-s-segment-100-coverage.json`](../../../../test-output/test-json/appendices/appendix-s-segment-100-coverage.json).

## Deferred Work

1. Confirm with the implementation team whether a concrete formula exists linking an EMV AID to its
   configured CA key (RID + Index) — none is stated in Appendix S today, so
   `BR-SEG100-APPS-CA-EMV-LINK` cannot move past `REVIEW_REQUIRED` without an SME-confirmed rule or a
   canonical anchor elsewhere in the spec.
2. If RSA key-strength policy (e.g. minimum modulus bit length) is ever required, source it from
   BUYPASS/network key-management policy, not from Appendix S's own text, which states only field
   format/length, not a minimum strength requirement.
3. Obtain the real CA Public Key File / key-load artifact (via Segment 132) to replace the in-repo
   synthetic/golden fixture before implementation certification; a synthetic key cannot validate
   genuine production AID-to-key resolution.
4. Do not treat Appendix S as part of ordinary Segment 100/130 financial JSON validation — it is a
   standalone file artifact exchanged via the separate Segment 132 CA Public Key File Load
   request/response; no such field exists in the Segment 100 payload schema today, so this validator
   is intentionally standalone (same posture as Appendix J/N/O/Q/R).

## Source References

- ATL105 2026-3 Appendix S-1 through S-2 (CA Public Key File record layout):
  [extracted specification](../../extracted_text.txt), lines ~35448-35558.
- ATL105 2026-3 Data Segment No. 132 (CA Public Key File Load Segment, confirms this file's content is
  external to Segment 100/130): [extracted specification](../../extracted_text.txt), lines ~14234-14360.
- [Segment 100 Appendix family training index](../segment-100/README.md) and
  [Appendix Family Gap Closure Register](../segment-100/appendix-family-gap-closure-register.md).
- [Appendix R EMV Chip Data Training](../appendix-r/README.md) (prior appendix in this family, same
  bounded-independent-validator pattern).
