# Appendix E Card Type Training

## Scope

ATL105 Appendix E contains three distinct code families. Keep them separate in BRs, validators, test data, and coverage counts. A code valid in one family is not automatically valid in another.

| Appendix E family | Count | Wire/message use | Current Test Solution evidence |
|---|---:|---|---|
| Table Load Response codes | 56 entries: 43 card-type entries and 13 terminal feature/configuration entries | DL blocks in a Table Load Response; not the same table as financial Prompt Code types | `TableLoadPayloadValidator` checks request/response shape and the code-173 -> DL6 dependency. It does not enforce the full 56-entry Appendix E allowlist. Partial. |
| Financial Transaction Prompt Code card types | 36 codes | Three-digit Element 14 card type combined with an Appendix G transaction type in Prompt Code 78 | 36 per-code BRs in `financial-card-type-business-requirements.md`; `Segment100CardTypeOracle` and tests cover all 36 syntactic card values. Transaction/card business eligibility remains a separate check. |
| Special Transaction Prompt Codes | 11 codes: `900`-`905`, `981`, `990`, `995`-`997` | Three-character special Prompt Code values, separate from financial Prompt Codes | Present in the code-9 special BR draft package. Those BR/TS/TC/TD records remain draft/review-only, not certified execution coverage. |

## Existing BR Audit

The machine-readable [Appendix E coverage package](../../../../test-output/test-json/appendices/appendix-e-segment-100-coverage.json) now has five family-level BRs: financial card types, Prompt Code composition, Conoco's internal-code boundary, special-code separation, and the Table Load family boundary. The [canonical appendix inventory](../../../../test-output/test-json/knowledge/segment-100-canonical-appendix-inventory.json) marks Appendix E `PARTIALLY_COVERED`. The 36 financial card-code BRs are maintained separately in the financial card-type catalog.

- The financial allowlist BR is supported for syntactic recognition of the 36 financial card types. It must not be described as proving every transaction/card combination is business-valid.
- Prompt Code composition checks the financial code namespaces; applicability and product/card restrictions remain separate.
- Conoco's backend code `089` is not the merchant Prompt Code; merchants use `090`, as Appendix E states.
- Special Prompt Code family separation is distinct from the special prompt operations' business behavior.
- The broad BR set does not provide 56 Table Load response code BRs or 11 certified special-prompt BR chains.
- The current Table Load validator test verifies message shape and the `173` -> DL6 dependency; it does not prove all Table Load values are valid.

## Deferred Table Load Coverage

The Appendix E package adds `BR-SEG100-APPE-TABLE-LOAD-FAMILY`, currently `PARTIALLY_COVERED`. It records the family boundary without claiming exhaustive code validation. Next evidence needed:

1. A source-derived enumeration of the 43 Table Load card-type entries and 13 terminal feature/configuration entries, kept distinct from the 36 financial Prompt Code types.
2. Positive tests for each enumerated Table Load value in its proper DL block, plus unknown/incorrect-family negatives.
3. Independent Test Solution TDs linked to the Table Load BR, TS, and TC; AI input fixtures do not count as independent TDs.
4. Explicit testing of feature-code dependencies, including the existing `173` -> DL6 rule, without inferring other dependencies from feature descriptions.

## Deferred Special Prompt Coverage

Appendix E's 11 special Prompt Codes are already described in the code-9 special-flow BR draft baseline. Keep those operation BRs review-required until their prompt directions, request/response message family, payload behavior, and independent physical TDs are validated. Do not merge them into the financial-card oracle.

## Learning Rules

1. Select the Appendix E table by message family before validating a code.
2. Preserve leading zeroes and the source's exact code width.
3. A financial Prompt Code is a transaction type plus a three-digit financial card type; a special Prompt Code is its separate source-defined three-character value.
4. Table Load Response values include both accepted card types and terminal features; do not feed all of them to the financial card-type oracle.
5. Use only source-supported transaction/card combinations. A cross-product generated from two independent allowlists is not proof of business eligibility.
6. Keep unenumerated Table Load values and unvalidated special-prompt flows `REVIEW_REQUIRED`; do not report Appendix E as fully covered while these families are partial or draft.

## Source References

- [ATL105 2026-3 Appendix E](../../extracted_text.txt)
- [Appendix E coverage package](../../../../test-output/test-json/appendices/appendix-e-segment-100-coverage.json)
- [Financial card-type BRs](financial-card-type-business-requirements.md)
- [Special transaction-type BR draft](../../../../test-output/test-json/special-transaction-type-br-baseline.json)
- [Table Load validator](../../../../../../src/main/java/com/coreauth/validator/canonical/TableLoadPayloadValidator.java)
- [Table Load validator tests](../../../../../../src/test/java/com/coreauth/validator/TableLoadPayloadValidatorTest.java)
- [Financial card-type oracle](../../../../../../src/main/java/com/coreauth/validator/canonical/Segment100CardTypeOracle.java)
- [Financial card-type oracle tests](../../../../../../src/test/java/com/coreauth/validator/Segment100CardTypeOracleTest.java)
