# ATL105 Section 10.2 Debit Card Processing - Independent Draft BR/TS/TC/TD Supplement

Status: DRAFT_REVIEW_REQUIRED. New debit rules are source-derived independently of AI wording; repeated requirements reuse Section 10.1 draft references only.

New debit-specific draft chains: 29 BR / 29 TS / 29 TC / 29 TD design files.
Section 10.1 draft rules referenced for repeated healthcare/receipt/regulatory behaviors: 42 unique chains; existing independent baseline chains referenced: 3.
The official 601-rule baseline is unchanged. No coverage credit or execution certification is granted. TD files are non-converter-ready planning placeholders.

## Source-Heading Coverage Map

All 27 Section 10.2 source headings are mapped in `section-10-2-coverage-assessment.json` to debit draft chains, reused Section 10.1 chains, existing independent-baseline chains, or an explicit grouping of mapped child headings. Reused entries include concrete BR/TS/TC/TD IDs. This is structural traceability only: all references remain REVIEW_REQUIRED and no semantic approval or execution credit is implied.

## Review gates

1. Review each debit BR against its exact ATL105 Section 10.2 quote and determine applicability, actor, transaction, and lifecycle scope.
2. Review every Section 10.1 cross-reference because those source rules remain DRAFT_REVIEW_REQUIRED until separately approved.
3. Split transaction lists into per-type scenario rows during fixture derivation; the summary BR does not prove each type was exercised.
4. Supply valid POS/card/PIN/network state, real converter-ready debit messages and authorized response/host oracles.
5. Check network mandates, actor timing, regulatory storage, mutation isolation, and BR->TS->TC->TD links; then seek authorized Test Solution approval.
6. Do not begin execution certification or roll these draft rows into the independent baseline until these reviews complete.

See `section-10-2-br-ts-tc-td-draft-package.json` and `section-10-2-coverage-assessment.json` for all rows and cross-references.
