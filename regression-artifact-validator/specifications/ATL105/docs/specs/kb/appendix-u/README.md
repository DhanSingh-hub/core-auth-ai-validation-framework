# Appendix U Visa Digital Wallet Training

**Specification:** BUYPASS ATL105 2026-3, Appendix U-1<br>
**Data segment:** Appendix U is narrative only; the one concrete field anchored to it is the Wallet
Method Indicator, Data Segment No. 111, Table ID 049 (Merchant Supplementary Data), Sub Table ID
"10" - a sub-table of the Segment 111 payload, not part of Segment 100/130 itself.<br>
**Terms:** Pass-through Digital Wallet, Staged Digital Wallet, Digital Wallet Operator (DWO), Staged
Digital Wallet Operator (SDWO), Wallet Method Indicator, Purchase Transaction, Funding Transaction<br>
**Status:** `PARTIALLY_COVERED`; two of eight BRs (Wallet Method Indicator format and category
classification) have an independent, bounded implementation and in-repo test fixtures. The remaining
six BRs describe business-process and cross-message rules with no corresponding ATL105 message field
and stay `REVIEW_REQUIRED` by design, same bounding used throughout this appendix family.

## Why this appendix was rebuilt rather than extended

Appendix U already had an evidence file in the repo before this training pass
(`appendix-u-segment-100-coverage.json`). It was honest about BR status (all seven existing BRs were
`REVIEW_REQUIRED`) and - unlike Appendix S/T - it correctly had **no `testData` array at all**, so it
made no unbacked `EXECUTABLE` claims either. However, it had not identified the one concrete,
independently-testable field that the specification itself anchors to Appendix U: the Wallet Method
Indicator (Sub Table ID 10). That gap is closed here by building a real
`AppendixUVisaDigitalWalletOracle` and matching unit tests for that field, while keeping the six
genuinely out-of-scope business-process BRs `REVIEW_REQUIRED`, following the same methodology used
for Appendix N, O, Q, R, S, and T.

## Scope and Field Boundaries

Appendix U's own text (source lines ~35624-35653, immediately after Appendix T) is entirely narrative:
it describes two Visa Digital Wallet categories and their business-process characteristics, with **no
field layout, code table, or message-format definition of its own**:

- **Pass-through Digital Wallet** - the wallet form factor substitutes the original payment form
  factor; the wallet stores a cardholder-supplied credential (account or token) and completes a
  transaction by transferring that credential to the merchant "without interrupting the flow of
  funds." A pass-through Digital Wallet Operator (DWO) must obtain the cardholder's written consent
  and "must not perform Visa payment services for another DWO."
- **Staged Digital Wallet** - couples two transactions, each retaining the character of its own
  underlying legacy transaction method: a **Purchase Transaction** (pays the retailer using the
  account assigned by the Staged Digital Wallet Operator/SDWO) and a **Funding Transaction** (uses the
  cardholder's Visa account number to fund or reimburse the wallet), completed "in more than one
  stage, in any order."

Searching the full specification for any field referencing "Digital Wallet" or Appendix U surfaced
exactly one concrete anchor (main-body text, source lines ~28543-28565): the **Wallet Method
Indicator**, Sub Table ID "10" of Data Segment 111's Table ID 049 (Merchant Supplementary Data):

- **Sub-Table ID**: `an2`, fixed value `10`.
- **Sub-Table Length**: `an3`, fixed length `001`.
- **Sub-Table Data**: `a`, exactly 1 character. Valid Codes/Values: `S` = Staged Digital Wallet,
  `P` = Pass-through Wallet. The field's own text says "Refer Appendix U for more details on Digital
  Wallet," confirming this is the intended structural link.

No other element, table, or sub-table anywhere in the specification references Appendix U or either
wallet category by name. (The "Digital Wallet Payment Token" references found in Appendix Y's
Verified-by-Visa/TAVV-cryptogram tables are a *separate* appendix's content about a different topic -
3-D Secure cryptogram selection - and are out of scope here.)

Decomposed into eight BRs (all seven pre-existing `BR-SEG100-APPU-*` IDs from before this training
pass are reused unchanged, plus one new structural ID):

- **BR-SEG100-APPU-WALLET-INDICATOR-FORMAT** (new) - Sub-Table ID/Length fixed values and a 1-character
  `S`/`P` value.
- **BR-SEG100-APPU-CATEGORY-CLASSIFICATION** (pre-existing ID, reused) - maps a valid Wallet Method
  Indicator value to its Appendix U category name.
- **BR-SEG100-APPU-PASS-THROUGH** (pre-existing ID, reused) - credential preservation; `REVIEW_REQUIRED`
  since no field records the originally-stored credential.
- **BR-SEG100-APPU-DWO-CONSENT** (pre-existing ID, reused) - written consent; `REVIEW_REQUIRED` DWO-side
  compliance record.
- **BR-SEG100-APPU-DWO-EXCLUSIVITY** (pre-existing ID, reused) - DWO exclusivity; `REVIEW_REQUIRED`
  network/operator-registration constraint.
- **BR-SEG100-APPU-STAGED-PURCHASE** (pre-existing ID, reused) - SDWO-assigned account usage;
  `REVIEW_REQUIRED`, requires SDWO's own configuration.
- **BR-SEG100-APPU-STAGED-FUNDING** (pre-existing ID, reused) - cardholder-account funding linkage;
  `REVIEW_REQUIRED`, requires SDWO's own configuration.
- **BR-SEG100-APPU-STAGED-CORRELATION** (pre-existing ID, reused) - Purchase/Funding stage correlation;
  `REVIEW_REQUIRED`, cross-message rule.

## Test Solution Work Completed

`AppendixUVisaDigitalWalletOracle` independently implements and tests two of eight BRs:

- **BR-SEG100-APPU-WALLET-INDICATOR-FORMAT** - `FAIL` if the Sub-Table ID isn't `10`, the Sub-Table
  Length isn't `001`, or the value isn't exactly 1 character equal to `S` or `P`; `PASS` otherwise.
- **BR-SEG100-APPU-CATEGORY-CLASSIFICATION** - given a valid Wallet Method Indicator value, returns
  the matching Appendix U category name (`Staged Digital Wallet` for `S`, `Pass-through Digital
  Wallet` for `P`); `FAIL` (cascading on the same malformed-value check) for any other value. Whether
  the category-specific processing rules were actually applied downstream remains explicitly out of
  scope of this format/classification check.
- The remaining six BRs (`BR-SEG100-APPU-PASS-THROUGH`, `-DWO-CONSENT`, `-DWO-EXCLUSIVITY`,
  `-STAGED-PURCHASE`, `-STAGED-FUNDING`, `-STAGED-CORRELATION`) always return `REVIEW_REQUIRED`,
  independent of the Wallet Method Indicator value, since none has a corresponding ATL105 message
  field - each is a DWO/SDWO-side compliance, account-configuration, or cross-message correlation rule.

Package totals: **8 BR / 3 TS / 3 TC / 13 TD** (all 13 `EXECUTABLE`, since even the six always-
`REVIEW_REQUIRED` BRs are deterministic by design and require no external fixture), backed by 19 unit
tests in
[`AppendixUVisaDigitalWalletOracleTest`](../../../../../../src/test/java/com/coreauth/validator/AppendixUVisaDigitalWalletOracleTest.java)
and
[`appendix-u-segment-100-coverage.json`](../../../../test-output/test-json/appendices/appendix-u-segment-100-coverage.json).

## Deferred Work

1. If BUYPASS/Visa ever defines a Segment 111/123 element carrying DWO-consent evidence, token/
   credential linkage, or staged-wallet stage-correlation identifiers, encode the corresponding BR as
   a structural rule instead of `REVIEW_REQUIRED` - none exists today.
2. Obtain real AI-generated Wallet Method Indicator artifacts (Sub Table ID 10) to replace the in-repo
   synthetic fixtures before implementation certification.
3. Confirm with the implementation team whether any production system records the originally-stored
   pass-through wallet credential anywhere accessible to this validator; Appendix U's own text gives
   no such field.
4. Do not conflate this appendix's "Digital Wallet" scope with Appendix Y's "Digital Wallet Payment
   Token" (TAVV cryptogram) tables - those are a separate 3-D Secure/cryptogram-selection topic under
   a different appendix and out of scope here.

## Source References

- ATL105 2026-3 Appendix U-1 (Visa Digital Wallet categories and business-process rules):
  [extracted specification](../../extracted_text.txt), lines ~35624-35653.
- ATL105 2026-3 Data Segment No. 111, Table ID 049, Sub Table ID "10" (Wallet Method Indicator field
  definition): [extracted specification](../../extracted_text.txt), lines ~28543-28565.
- [Segment 100 Appendix family training index](../segment-100/README.md) and
  [Appendix Family Gap Closure Register](../segment-100/appendix-family-gap-closure-register.md).
- [Appendix T EMV Additional Information Data Layouts Training](../appendix-t/README.md) (prior
  appendix in this family, same bounded-independent-validator pattern).
