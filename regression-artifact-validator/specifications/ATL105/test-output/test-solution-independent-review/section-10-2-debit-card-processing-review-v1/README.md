# ATL105 Section 10.2 Debit Card Processing - Independent Draft BR/TS/TC/TD Supplement

Status: DRAFT_REVIEW_REQUIRED. New debit rules are source-derived independently of AI wording; repeated requirements reuse Section 10.1 draft references only.

New debit-specific draft chains: 28 BR / 28 TS / 28 TC / 28 TD design files.
Section 10.1 draft rules referenced for repeated healthcare/receipt/regulatory behaviors: 39.
The official 601-rule baseline is unchanged. No coverage credit or execution certification is granted. TD files are non-converter-ready planning placeholders.

## Review gates

1. Review each debit BR against its exact ATL105 Section 10.2 quote and determine applicability, actor, transaction, and lifecycle scope.
2. Review every Section 10.1 cross-reference because those source rules remain DRAFT_REVIEW_REQUIRED until separately approved.
3. Split transaction lists into per-type scenario rows during fixture derivation; the summary BR does not prove each type was exercised.
4. Supply valid POS/card/PIN/network state, real converter-ready debit messages and authorized response/host oracles.
5. Check network mandates, actor timing, regulatory storage, mutation isolation, and BR->TS->TC->TD links; then seek authorized Test Solution approval.
6. Do not begin execution certification or roll these draft rows into the independent baseline until these reviews complete.

See `section-10-2-br-ts-tc-td-draft-package.json` and `section-10-2-coverage-assessment.json` for all rows and cross-references.
