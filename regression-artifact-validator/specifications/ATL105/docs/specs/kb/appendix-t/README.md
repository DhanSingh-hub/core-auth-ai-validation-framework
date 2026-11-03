# Appendix T EMV Additional Information Data Layouts Training

**Specification:** BUYPASS ATL105 2026-3, Appendix T-1 through T-2<br>
**Data segment:** The repeating EMV Additional Information Section (Elements 191 Indicator / 192
Length / 118 Value) carried inside Data Segment No. 130 (request) and No. 131 (response); unlike
Appendix N/O/R/S this is not a standalone artifact — it is a field group within the normal
authorization/response payload, but is not wired into `Segment100PayloadValidator` because it belongs
to Segments 130/131, not Segment 100 itself.<br>
**Terms:** EMV Additional Information Indicator, EMV Additional Information Length, EMV Additional
Information (Value), Table ID, EMV Table Data, CARC (Card Authentication Results Code)<br>
**Status:** `PARTIALLY_COVERED`; five of seven BRs have an independent, bounded implementation and
in-repo test fixtures. Table Length field width and cross-message echo verification stay
`REVIEW_REQUIRED` by design, same bounding used throughout this appendix family.

## Why this appendix was rebuilt rather than extended

Appendix T already had an evidence file in the repo before this training pass
(`appendix-t-segment-100-coverage.json`). Like Appendix S, it was already honest about BR status — all
three existing BRs were `REVIEW_REQUIRED` — but all three `testData` entries still claimed
`EXECUTABLE` readiness despite **no backing Java validator anywhere in `src/`**. It was corrected here
by building a real `AppendixTEmvAdditionalInformationOracle` and matching unit tests, and by re-scoring
the evidence file to reflect only what is actually implemented and tested, following the same
methodology used for Appendix N, O, Q, R, and S.

## Scope and Field Boundaries

Appendix T's own text (source lines ~35557-35616, immediately after Appendix S) describes two table
layouts carried via the repeating EMV Additional Information Section:

- **Table ID 001 (EMV Table Data)** — value is exactly 6 characters, either `EMVYES` or `EMVNOT`.
  Notifies the chip when the MasterCard X-Code system was unable to go online. Per Appendix T-1's own
  response note: "If this field is included in an authorization response, the device will echo it back
  on any subsequent advice or batch upload request" — a cross-message rule.
- **Table ID 002 (CARC)** — value is exactly 1 character ("Maximum Length n6 Len.: 1"). "Identifies the
  CARC value returned by Visa in Bit No. 44.8 of the response." No concrete code catalog is stated
  anywhere in Appendix T.
- **"Table Length" column** — described only as `n / Fixed` for both tables; no digit count or value is
  stated anywhere in the appendix. This is a genuine spec gap and is not fabricated here.

Cross-referenced against the main-body definitions of Element 191 (EMV Additional Information
Indicator), Element 192 (EMV Additional Information Length), and Element 118 (EMV Additional
Information) (source lines ~24420-24465) for field framing, since Appendix T's own text only states
per-table layout attributes, not the Indicator/Length field formats.

**Spec inconsistency found and reconciled:** Element 191's own "Valid Codes/Values" table lists only
`001 EMV table data`, omitting `002`, even though (a) Appendix T itself defines Table ID 002 (CARC) in
full, and (b) the spec's own appendix-overview text (source line ~1799: "This appendix contains data
layout information for EMV Table Data (Code 001) and Card Authentication Results Code (CARC)")
confirms 002 is legitimate. This training treats both `001` and `002` as valid Indicator values,
reconciling Element 191's incomplete table against Appendix T's own authoritative two-table
definition — documented here as a reconciliation decision, not a silent assumption.

Decomposed into seven BRs (three of which reuse pre-existing `BR-SEG100-APPT-*` IDs already referenced
across generated cross-segment reports before this training pass):

- **BR-SEG100-APPT-INDICATOR-FORMAT** — Element 191 is exactly 3 numeric digits, value `001` or `002`.
- **BR-SEG100-APPT-LENGTH-FORMAT** — Element 192 is exactly 3 numeric digits, range `001`-`985` (per
  Element 192's own stated Valid Codes/Values).
- **BR-SEG100-APPT-TABLE-ID-MATCH** — the embedded Table ID (start of Element 118's value) must equal
  the Indicator value.
- **BR-SEG100-APPT-EMV-TABLE-DATA** — Table ID 001 value must be `EMVYES` or `EMVNOT` (pre-existing ID,
  reused).
- **BR-SEG100-APPT-CARC** — Table ID 002 value must be exactly 1 character (pre-existing ID, reused);
  format-only, since no concrete code catalog is stated anywhere in Appendix T.
- **BR-SEG100-APPT-TABLE-LENGTH-FIELD** — the "Table Length" field's digit count/value; always
  `REVIEW_REQUIRED` since no width or value is stated anywhere in Appendix T-1/T-2.
- **BR-SEG100-APPT-ECHO** — response EMV Table Data must be echoed into subsequent advice/batch-upload
  requests (pre-existing ID, reused); `REVIEW_REQUIRED` by design, since this is a cross-message rule
  that cannot be verified from a single snapshot, mirroring Appendix R's cross-field BR and Appendix
  Q's flow-consistency BR.

## Test Solution Work Completed

`AppendixTEmvAdditionalInformationOracle` independently implements and tests five of seven BRs:

- **BR-SEG100-APPT-INDICATOR-FORMAT** — `FAIL` if not exactly 3 numeric digits or not `001`/`002`;
  `PASS` otherwise.
- **BR-SEG100-APPT-LENGTH-FORMAT** — `FAIL` if not exactly 3 numeric digits or outside `001`-`985`;
  `PASS` otherwise.
- **BR-SEG100-APPT-TABLE-ID-MATCH** — `FAIL` if the embedded Table ID does not equal the Indicator;
  `PASS` otherwise.
- **BR-SEG100-APPT-EMV-TABLE-DATA** — `PASS` for `EMVYES`/`EMVNOT` with Table ID 001; `FAIL` (with a
  "not applicable" reason) if Table ID isn't 001.
- **BR-SEG100-APPT-CARC** — `PASS` for a 1-character value with Table ID 002; `FAIL` (with a "not
  applicable" reason) if Table ID isn't 002.
- **BR-SEG100-APPT-TABLE-LENGTH-FIELD** — always `REVIEW_REQUIRED`; no digit count/value stated.
- **BR-SEG100-APPT-ECHO** — always `REVIEW_REQUIRED`; cross-message consistency, mirrors Appendix R's
  `assessCrossField()` and Appendix Q's `assessFlowConsistency()` postures.

Package totals: **7 BR / 5 TS / 5 TC / 16 TD** (all 16 `EXECUTABLE`, since even the two always-
`REVIEW_REQUIRED` BRs are deterministic by design and require no external fixture), backed by 22 unit
tests in
[`AppendixTEmvAdditionalInformationOracleTest`](../../../../../../src/test/java/com/coreauth/validator/AppendixTEmvAdditionalInformationOracleTest.java)
and
[`appendix-t-segment-100-coverage.json`](../../../../test-output/test-json/appendices/appendix-t-segment-100-coverage.json).

## Deferred Work

1. If BUYPASS/Visa ever states a concrete digit count or value for the "Table Length" column in
   Appendix T-1/T-2, encode it as a new BR — none is stated today.
2. Resolve EMV Table Data echo verification across an authorization response and subsequent advice or
   batch-upload request if a concrete cross-message test harness is ever built; not assertable from a
   single snapshot today.
3. If a concrete CARC code catalog (values returned in Bit No. 44.8) is ever published, encode it as a
   closed-set BR instead of the current 1-character format-only check.
4. Confirm with the implementation team whether Element 191's "Valid Codes/Values" list should be
   formally corrected to include `002`, since this training's reconciliation (treating both `001` and
   `002` as valid) relies on Appendix T's own definition and the spec's appendix-overview text, not on
   Element 191's own incomplete table.

## Source References

- ATL105 2026-3 Appendix T-1 through T-2 (EMV Additional Information Data Layouts):
  [extracted specification](../../extracted_text.txt), lines ~35557-35616.
- ATL105 2026-3 Element 191/192/118 main-body definitions (Indicator/Length/Value field framing):
  [extracted specification](../../extracted_text.txt), lines ~24420-24465.
- ATL105 2026-3 Data Segment No. 130/131 field layout (Elements 191/192/118 in context):
  [extracted specification](../../extracted_text.txt), lines ~14060-14230.
- [Segment 100 Appendix family training index](../segment-100/README.md) and
  [Appendix Family Gap Closure Register](../segment-100/appendix-family-gap-closure-register.md).
- [Appendix S CA Public Key File Training](../appendix-s/README.md) (prior appendix in this family,
  same bounded-independent-validator pattern).
