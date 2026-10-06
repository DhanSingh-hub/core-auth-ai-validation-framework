# Appendix L Currency Code Training

## Purpose

Train the Test Solution to recognize, classify, and correctly bound every ATL105 2026-3 Appendix L
(Valid Currency Codes, Element No. 20) value, and to keep every distinct place the specification
reuses that three-digit code set - Segment 100/105/119 Element 20, Segment 103 Element 153, the
Appendix I Table ID 006 Currency Code Information field, the Appendix I Table ID 056 MIT Amount
cross-currency rule, Element 76's implied-decimal dependency, and Appendix M's fixed-840 HIP
subelement - as separately scoped evidence instead of one undifferentiated "currency code" rule.
Appendix L is a flat currency/country/network table, not a classification-range table like
Appendix F: a three-digit value either names a row in this table or it does not, and several codes
are listed more than once with a different Visa/MC/Both value per country.

## Where Currency Code Is Used (Usage Map)

| Field | Segment / Location | Position | Entry | Purpose | Valid values | Key processing rule |
|---|---|---|---|---|---|---|
| Element 20 "Currency Code" (master definition, Section 13.2) | Referenced generically for any Transaction Request/Response | n3, fixed length 3 | Optional | "Identifies the currency type according to country." | See Appendix L | **If not sent, defaults to 840 (US Dollar). The field is ignored during transaction processing; due to totaling restrictions, all transactions for a given merchant use the currency associated with that merchant.** |
| Element 20 in Segment 105 Totals Data Segment | Field 13 | 3 | Optional | Currency of the requested totals | See Appendix L | No separate override text; inherits the Element 20 dictionary rule. |
| Element 20 in Segment 119 Totals with Proprietary Data Load | Field 15 | 3 | Optional | Currency of the proprietary-data totals | See Appendix L | No separate override text; inherits the Element 20 dictionary rule. |
| Element 153 "WIC Discount Amount" | Segment 103 EBT Data Segment, subfield positions 5-7 of a 1-40 digit composite value (1-2 Account Type=97, 3-4 Amount Type=52, 5-7 Currency Code, 8-20 Amount) | n3 | Required within the composite when the field is sent | eWIC merchant discount/cents-off currency | **"See Appendix L. Valid Currency Codes"** - open set, not fixed to one value | `AppendixLCurrencyCodes.isValid` already gates this subfield in `Segment103PayloadValidator`. |
| Appendix I, Variable Information Data Segment (Segment 111), Table ID `006` "Currency Code Information" | Table Data, fixed length 3 | n3 | Conditional table entry | "Identifies the currency of the country in which the transaction originated." | Not explicitly re-pointed at Appendix L in the extracted text, but uses the same n3 currency representation | **Default value: 840 (United States)** when the table is absent/unpopulated. Treat this as a distinct field from Element 20 (country-of-origin currency vs. transaction currency) until a source cross-reference confirms they must agree. |
| Appendix I, Segment 111, Table ID `056` Sub Table ID `03` "MIT Amount" | Sub-table data, n12 | n12 amount | Conditional | Recurring Payment / Installment amount | N/A (amount, not a code) | **"Indicates the amount ... and is in the same currency as Currency Code of Transaction Amount."** This is a cross-field consistency rule tying MIT Amount's currency back to Element 20; it does not introduce a new code set. |
| Element 76 "Product Amount" (Segment 102 Product Code Data / Segment 157 Adjusted Product Code Data) | n.a. | Up to 12 digits, two assumed decimal places | Required/repeated | Monetary value of a purchased product | N/A (amount) | **"The decimal point is implied by the optional Currency Code."** The source does not publish a per-currency decimal-places table (unlike true ISO 4217 minor units); treat any decimal-place inference beyond the stated two-assumed-decimal default as `REVIEW_REQUIRED`. |
| Appendix M, Element 164 "EBT Program Data", HIP purchase/return amount subelement | Program Data subelement (e.g., Segment 103 EBT context) | n3 | Fixed | Currency of a HIP purchase/return amount | **Fixed/example value `840` ("Valid value: 840 for US Dollars") only** - this is not an open Appendix L selection in the documented example. | Do not generalize this fixed-840 example into "any Appendix L code is valid here" without further source confirmation; keep it `REVIEW_REQUIRED` as its own narrower rule. |
| Version history note | Front-matter changelog | n/a | n/a | "Currency Update ... L-9 ... Updated currency name and currency code for Zimbabwe in Appendix L." | n/a | Appendix L content is versioned; a prior release's Zimbabwe row may differ. Any stored expectation must be pinned to specification version `2026-3`, not assumed stable across releases. |

## Model Decision Sequence

1. Identify which of the usage sites above the candidate value belongs to before applying any rule. A currency-shaped three-digit value in the Appendix I Table `006` field or the Appendix M HIP subelement is not automatically an Element 20 / Element 153 assertion, and vice versa.
2. Validate the field representation first: Appendix L / Element 20 / Element 153's currency subfield are always exactly three ASCII digits, fixed length, leading zeroes significant (`008` Lek must not be coerced to `8`).
3. Resolve the exact code against the full Appendix L row set (153 distinct codes across 161 rows), not a numeric range or parity heuristic. Return the currency name, country/territory list, and Visa/MC/Both network scope exactly as transcribed.
4. If a code has more than one row, check whether every row agrees on network scope. Codes `230`, `356`, `578`, `710`, `840`, and `978` have rows with different Visa/MC/Both scopes for different countries; keep the aggregate `REVIEW_REQUIRED` with every conflicting claim preserved instead of silently picking one scope (see "Codes Whose Network Scope Varies by Country" below). All other duplicated-row codes (for example `826` Pound Sterling, listed once for the UK/Crown Dependencies group and once for South Georgia and the South Sandwich Islands) agree on scope and are not conflicts.
5. Apply Element 20's own processing rule before treating a missing/absent value as an error: absence defaults to `840` and the field is documented as ignored during processing because totals follow the merchant's bound currency. A missing Element 20 is therefore not, by itself, a defect.
6. Keep Visa/MC/Both network-scope information classification-only. It is not, by itself, evidence that a given card network will accept that code for a specific country/acquirer/merchant configuration; no element in this message carries the transacting country, so network-scope enforcement beyond recognition stays `REVIEW_REQUIRED`.
7. Keep Element 76's implied-decimal dependency, the Table `006` default-840 field, the Table `056` MIT Amount same-currency rule, and the Appendix M fixed-840 HIP example as separate BRs from plain Appendix L code recognition. A code being a valid Appendix L member does not certify any of those dependent behaviors.
8. Emit a source-backed result: `PASS`, `FAIL`, or `REVIEW_REQUIRED`, with the code, currency name/countries/network scope (or conflict set), source anchor (`Appendix L-1` through `L-9`), and reason. Codes that are not listed at all (well-formed but unknown, e.g. `000`, `999`) are `FAIL`; malformed representations (wrong width, non-numeric, signed, Unicode digits) are `FAIL` for format, not `REVIEW_REQUIRED`.

## Codes Whose Network Scope Varies by Country

| Code | Currency | Rows (country -> scope) |
|---|---|---|
| `230` | Ethiopian Birr | Ethiopia -> Both; Eritrea -> MC |
| `356` | Indian Rupee | India -> Both; Bhutan -> MC |
| `578` | Norwegian Krone | Bouvet Island/Norway/Svalbard and Jan Mayen -> Both; Antarctica -> MC |
| `710` | Rand | South Africa -> Both; Lesotho/Namibia -> Visa |
| `840` | U.S. Dollar | American Samoa/United States/Puerto Rico and 14 other territories -> Both; Libyan Arab Jamahiriya/Palestine/Panama -> MC |
| `978` | Euro | Most Eurozone members (Aland Islands through Spain) -> Both; France, Metropolitan -> Visa; Saint Barthelemy/Saint Martin (French part) -> MC |

These are genuine per-country source distinctions, not transcription conflicts. Because Element 20
carries only a currency code and never the transacting country, the Test Solution cannot resolve
which scope applies from the wire value alone; every one of these six codes stays classified as
recognized with `networkScopeConflict=true` and `REVIEW_REQUIRED` until a country/acquirer context
is available to pick a row.

## Important Exceptions and Boundaries

- Element 20's own text says the field "will be ignored during transaction processing" and that a merchant's totaling currency wins regardless of what (if anything) is transmitted. Do not build a BR that fails a transaction solely for omitting Element 20 or for a mismatch with assumed merchant currency; that reconciliation is external/merchant-configuration-bound.
- Element 153's currency subfield explicitly cites "See Appendix L," so it is validated against the full code set (via `AppendixLCurrencyCodes` / `AppendixLCurrencyCodeCatalog`), unlike Appendix M's HIP subelement, which the source only illustrates with a fixed `840` value. Do not merge these two into one rule.
- The Appendix I Table `006` "Currency Code Information" field's default-840 behavior is documented independently of Element 20's default-840 behavior. Treat them as two separately sourced defaults unless a future source passage says they must be identical for the same transaction.
- Footnote 2/3 identify CFA Franc BCEAO and CFA Franc BEAC's responsible monetary authorities; they do not change code membership or network scope and should not be modeled as additional currency rows.
- The Euro table carries two dated footnotes: Bulgaria's currency became Euro (`978`) effective 1 January 2026, and Croatia's became Euro effective 1 January 2023. Appendix L does not define any transaction-date-sensitive currency logic, so do not derive an "only valid after date X" rule from these footnotes; they are historical/contextual annotations only. Flag any date-dependent currency assertion as `REVIEW_REQUIRED` pending SME confirmation of whether BUYPASS enforces effective dates.
- Element 76 Product Amount's "decimal point is implied by the optional Currency Code" is the only place the specification ties a monetary amount's decimal interpretation to Element 20. The source gives a single default (two assumed decimal places) and does not publish ISO 4217-style per-currency minor-unit exceptions (for example zero-decimal Yen or three-decimal Dinar currencies). Do not invent a minor-units table; keep any such distinction `REVIEW_REQUIRED` until sourced.
- A code's presence in Appendix L is never, by itself, proof that a specific card network, acquirer, or merchant configuration will accept it for a live transaction; that eligibility question is explicitly out of scope for this classification evidence (`transactionEligibilityCertified: false` in the chain package).

## Test Solution Training Method

Build the training/evaluation set in layers rather than asking the model to memorize a prose list:

1. **Source oracle:** [`AppendixLCurrencyCodeCatalog`](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixLCurrencyCodeCatalog.java) stores all 161 source rows (153 distinct codes) with currency name, country/territory list, Visa/MC/Both scope, and page anchor (`L-1` through `L-9`), grouped by code so multi-row codes are preserved instead of collapsed.
2. **Boundary and conflict examples:** cover every one of the 153 distinct codes once, and specifically exercise the six network-scope-conflict codes (`230`, `356`, `578`, `710`, `840`, `978`) plus the non-conflicting duplicate-row code `826` (Pound Sterling) to prove duplicate rows are not automatically treated as conflicts.
3. **Unknown-but-well-formed negatives:** three-digit values that are never assigned in Appendix L (for example `000`, `001`, `999`) must fail classification, not be treated as a format error.
4. **Malformed-format negatives:** missing/null, empty, wrong width (`84`, `0840`), non-numeric (`USD`, `84O`), signed (`-840`), padded (`" 840"`, `"840 "`), and Unicode-digit (Arabic-indic `840`) values must fail format independent of the classification table.
5. **Context examples:** pair codes with their usage site (Element 20 in Segment 100/105/119, Element 153 in Segment 103, Table `006` in Segment 111, Element 76's implied-decimal dependency, Appendix M's fixed-840 HIP subelement) and mark unsupported cross-site assumptions `REVIEW_REQUIRED` rather than fabricating a pass/fail label.
6. **Independent test data:** every claimed BR -> TS -> TC chain needs a concrete TD with a three-character (or deliberately malformed) code and a fully specified expected result. Metadata-only TDs do not establish coverage.
7. **Leakage control:** score exact classification (code, currency name, scope or conflict set) and format recognition independently; a plausible explanation cannot override a wrong label.

## Current Coverage and Open Work

The [classification chain package](../../../../test-output/test-json/appendix-l-currency-code-chain-package.json) contains 155 BRs (153 per-code BRs plus one unknown-code BR and one malformed-format BR), 173 scenarios, 173 cases, and 173 executable synthetic TDs: one classification chain for every one of the 153 distinct Appendix L codes (six of which are `REVIEW_REQUIRED` network-scope-conflict chains), ten unknown-but-well-formed negatives, and ten malformed-format negatives.

`AppendixLCurrencyCodeCatalog.classify(code)` returns every matching row, whether the code is recognized, and whether its rows disagree on network scope. The chain package's result always states `transactionEligibilityCertified: false`: a classification `PASS` or `REVIEW_REQUIRED` means only that the code (and, where applicable, its conflicting claims) were preserved from the source table - it is not authorization that any specific card network, country, or merchant will accept that code in production.

Expected labels are generated from the independently owned source transcription, not from classifier output. Tests compare every catalog row's currency name and code against the specification text, exercise the six network-scope-conflict codes and the `826` non-conflict duplicate explicitly, and replay every TD with resolved BR -> TS -> TC -> TD IDs and matching anchors. This is source-derived harness evidence, not SME approval.

The [legacy Appendix L package](../../../../test-output/test-json/appendices/appendix-l-segment-100-coverage.json) retains its three transaction-context BRs (currency format, network context, eWIC currency) and now references this separate classification evidence via `classificationEvidence`. Keep Appendix L `PARTIALLY_COVERED`: the Element 76 implied-decimal dependency, the Table `006`/Table `056` MIT Amount cross-currency rule, the Appendix M fixed-840 HIP subelement, and all per-transaction network/country eligibility remain open and are intentionally outside this package's scope.

### Regeneration

Run from the `regression-artifact-validator` module directory:

```powershell
mvn -q -DskipTests compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAppendixLCurrencyCodeChains'
mvn '-Dtest=AppendixLCurrencyCodeCatalogTest' test
```

The generator rewrites only the dedicated chain package. Any changed source row, newly discovered duplicate code, or newly discovered network-scope conflict must be reviewed before accepting regenerated evidence.

## SME Review Handoff

Keep these questions open for the next SME/Test Team review. Do not promote Appendix L beyond `PARTIALLY_COVERED` based only on the classification package.

| Review item | Current evidence | Question to resolve |
|---|---|---|
| Network-scope conflict codes | `230`, `356`, `578`, `710`, `840`, `978` each have rows with different Visa/MC/Both scopes keyed by country; Element 20 carries no country. | Is a country/acquirer context available elsewhere in the message flow that could resolve these to a single scope per transaction, or must they remain permanently `REVIEW_REQUIRED` by design (as Segment 110's Element 239 dual-entity case was resolved)? |
| Table `006` vs. Element 20 relationship | Appendix I's Table `006` "Currency Code Information" and Element 20 both default to `840` and both describe a currency "according to country"/"of the country in which the transaction originated," but the source never states they must carry the same value. | Confirm whether Table `006` and Element 20 are the same logical currency per transaction or two independently settable fields. |
| Element 76 implied-decimal rule | "The decimal point is implied by the optional Currency Code," with a stated two-assumed-decimal default and no per-currency minor-unit table. | Confirm whether BUYPASS applies a per-currency minor-unit table (as ISO 4217 does) or always uses two assumed decimals regardless of Currency Code. |
| Appendix M fixed-840 HIP subelement | The Appendix M worked examples hardcode Currency Code `840` ("Valid value: 840 for US Dollars") inside the Element 164 HIP purchase/return amount subelement. | Confirm whether this subelement ever legitimately carries a non-840 Appendix L code, or whether it is fixed to `840` by program design (WIC/HIP being a US-specific benefit program). |
| Table `056` Sub Table `03` MIT Amount currency rule | "Indicates the amount of the Recurring Payment or Installment, and is in the same currency as Currency Code of Transaction Amount." | Confirm the enforcement point: is this a Test Solution-checkable cross-field assertion (MIT Amount's implied currency equals Element 20), or guidance to the originating device only? |
| Versioned content | The changelog records a Zimbabwe currency name/code update in Appendix L-9. | Confirm there is no need to support multiple specification-version Appendix L tables concurrently; if there is, this catalog must be keyed by specification version. |

**Current disposition:** The 153-code catalog and its 173 independent classification/negative TD chains are source-classification evidence only. Preserve `REVIEW_REQUIRED` for the six network-scope-conflict codes and for every cross-site dependency (Element 76, Table `006`, Table `056`, Appendix M) until SME/source review closes them.

## Source References

- [ATL105 2026-3 Appendix L](../../extracted_text.txt)
- [ATL105 2026-3 Element 20 (Section 13.2)](../../extracted_text.txt)
- [ATL105 2026-3 Element 153 WIC Discount Amount](../../extracted_text.txt)
- [ATL105 2026-3 Element 76 Product Amount](../../extracted_text.txt)
- [ATL105 2026-3 Appendix I Table ID 006 / Table ID 056](../../extracted_text.txt)
- [ATL105 2026-3 Appendix M Element 164 EBT Program Data](../../extracted_text.txt)
- [Appendix L coverage package](../../../../test-output/test-json/appendices/appendix-l-segment-100-coverage.json)
- [Appendix L classification chains](../../../../test-output/test-json/appendix-l-currency-code-chain-package.json)
- [Catalog](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixLCurrencyCodeCatalog.java)
- [Simple WIC currency allowlist used by Segment103PayloadValidator](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixLCurrencyCodes.java)
- [Source generator](../../../../../../src/main/java/com/coreauth/validator/coverage/GenerateAppendixLCurrencyCodeChains.java)
- [Independent catalog and chain tests](../../../../../../src/test/java/com/coreauth/validator/AppendixLCurrencyCodeCatalogTest.java)
- [Appendix family gap closure register](../segment-100/appendix-family-gap-closure-register.md)
