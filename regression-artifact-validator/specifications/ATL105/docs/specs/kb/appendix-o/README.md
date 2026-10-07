# Appendix O Payment Token Terminology Training

**Specification:** BUYPASS ATL105 2026-3, Appendix O-1<br>
**Data segment:** Segment 100 tokenized-transaction context (Account Number, Expiration Date,
security/cryptogram data, and POS Entry Mode fields), applies to both request and response<br>
**Terms:** Cryptogram Token Data, Payment Token, Payment Token Expiration Date, Payment Token
Requestor, Payment Token Service Provider, POS Entry Mode<br>
**Status:** `PARTIALLY_COVERED`; all seven source-anchored BRs have an independent, bounded
implementation and in-repo test fixtures. Token-BIN-range designation, cryptogram cryptographic
authenticity, which specific POS Entry Mode codes represent token presentment, and real
AI-generated tokenized artifacts remain out of scope.

## Scope and Field Boundaries

Appendix O is a glossary of tokenization terms (see source line 6286: "please refer to Appendix O
(Payment Tokenization Terminologies) in this document"), not a code table like Appendix C/E/J or a
fixed layout like Appendix K/N. It defines six terms; two of them ("Payment Token Requestor" and
"Payment Token Service Provider") are pure role/actor definitions with no attached field format,
length, or code values anywhere in the source, so they have no independently assertable check and
are intentionally **not modeled** by this validator. The remaining four terms each carry at least
one checkable representation rule, decomposed into seven BRs:

- **Payment Token** → token representation (13-19 digits), the LUHN check digit, the token-BIN-range
  claim, and the token-vs-PAN inequality rule (four separate BRs, since each is independently
  checkable even though the source describes them in one paragraph).
- **Payment Token Expiration Date** → populated in the PAN Expiration Date field (one BR).
- **Cryptogram Token Data** → the 28/56-byte base64 length rule and the Token Data Block A/B framing
  rule (two separate BRs).
- **POS Entry Mode** → a value identifying the token presentment mode (one BR). Appendix O does not
  itself enumerate which Appendix J POS Entry Mode codes qualify as "token presentment," so this BR
  is bounded to code recognition only, delegating to the existing `AppendixJPosEntryModeOracle`
  rather than inventing a new token-specific code list.

No BIN registry, token vault, or cryptographic verification service is part of Appendix O itself, so
none of those are asserted by this validator — only the representation/format/algorithm rules the
glossary text actually states.

## Test Solution Work Completed

`AppendixOPaymentTokenTerminologyValidator` independently implements and tests all seven BRs:

- **BR-SEG100-APPO-TOKEN-ACCOUNT** — Payment Token must be a 13-19 digit numeric value. Representation
  only; does not certify token-BIN-range membership (no BIN registry is part of Appendix O).
- **BR-SEG100-APPO-TOKEN-LUHN** — Payment Token must pass the LUHN (modulo-10) check digit. Algorithm
  only; token-BIN-range designation remains out of scope, same as above.
- **BR-SEG100-APPO-TOKEN-NOT-PAN** — Payment Token must not equal the cardholder's actual PAN. A
  simple value-inequality check; when no PAN is supplied for comparison, the result is
  `REVIEW_REQUIRED` rather than a false `PASS`, since inequality cannot be evaluated against nothing.
- **BR-SEG100-APPO-TOKEN-EXPIRATION** — Payment Token Expiration Date, when supplied, must be `YYMM`
  with a valid month 01-12 (same shape as PAN Expiration Date). Appendix O's "when token processing
  requires it" is conditional language with no defined trigger, so an absent value is
  `REVIEW_REQUIRED`, not an automatic `FAIL`.
- **BR-SEG100-APPO-CRYPTOGRAM** — Cryptogram Token Data must be valid base64 that decodes to exactly
  28 or 56 bytes. Length/encoding only; cryptographic authenticity of the cryptogram content is not
  evaluated.
- **BR-SEG100-APPO-CRYPTOGRAM-BLOCK-SPLIT** — 56 decoded bytes split into Token Data Block A (first 28
  bytes) and Block B (remaining 28 bytes); 28 decoded bytes are Block A only, with no Block B.
- **BR-SEG100-APPO-ENTRY-MODE** — POS Entry Mode must be a value recognized by the existing Appendix J
  PAN-entry-mode/terminal-capability code oracle. This proves code recognition only; Appendix O does
  not identify which specific Appendix J codes represent a tokenized presentment (for example,
  `10`/Credential on file is a plausible candidate but not textually confirmed as the only one), so
  that narrower semantic claim is not independently assertable from this source alone.

Package totals: **7 BR / 2 TS / 2 TC / 12 TD**, all `COVERED`/`EXECUTABLE` using in-repo fixtures (see
[`AppendixOPaymentTokenTerminologyValidatorTest`](../../../../../../src/test/java/com/coreauth/validator/AppendixOPaymentTokenTerminologyValidatorTest.java)
and
[`appendix-o-segment-100-coverage.json`](../../../../test-output/test-json/appendices/appendix-o-segment-100-coverage.json)).

## Deferred Work

1. Confirm the token BIN registry/designation rules (which BIN ranges are flagged as token ranges)
   against an authoritative source — not part of Appendix O and therefore not independently
   assertable from this appendix alone.
2. Obtain cryptographic verification capability or real cryptogram samples to validate cryptogram
   *authenticity*, beyond the byte-length/block-framing checks implemented here.
3. Confirm with the tokenization implementation exactly which Appendix J POS Entry Mode code(s)
   represent token presentment, so `BR-SEG100-APPO-ENTRY-MODE` can move from code-recognition-only to
   a token-presentment-specific assertion.
4. Obtain specialized AI-generated tokenized artifacts (real token/cryptogram/entry-mode samples) to
   replace the in-repo synthetic fixtures before implementation certification.
5. Decide whether/how to wire `AppendixOPaymentTokenTerminologyValidator` into
   `Segment100PayloadValidator` if a modeled token/cryptogram field is ever added to the Segment 100
   payload schema; today no such field exists, so this validator is intentionally standalone
   (mirroring how Appendix E/F/G/J/N are independent Segment-100-context oracles rather than being
   wired into the payload validator).
6. "Payment Token Requestor" and "Payment Token Service Provider" remain glossary-only; if a future
   appendix or element ties a specific field format to either role, revisit this scope decision.

## Source References

- ATL105 2026-3, Chapter overview introducing payment tokenization and the "refer to Appendix O"
  cross-reference: [extracted specification](../../extracted_text.txt), lines 6267-6286.
- ATL105 2026-3 Appendix O-1 terminology table: [extracted specification](../../extracted_text.txt),
  lines 35089-35126.
- [Segment 100 Appendix family training index](../segment-100/README.md) and
  [Appendix Family Gap Closure Register](../segment-100/appendix-family-gap-closure-register.md).
- [Appendix N Premium Gift Card Training](../appendix-n/README.md) (prior appendix in this family,
  same bounded-independent-validator pattern).
